import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Zadanie extends Thread{
    public enum Stan {
        Utworzone, Rozpoczete, Zakonczone
    }

    private String nazwa, opis;
    private Stan stan;
    private LocalDateTime dataUtworzenia;
    private LocalDateTime dataZakonczenia;
    private int czasWykonania;
    private static int counter = 0;
    private int id;
    private Zespol zespol = null;
    private boolean zatwierdzenie;
    private static ArrayList<Zadanie> listaZadan = new ArrayList<>();
    public Zadanie(String nazwa, String opis, boolean zatwierdzenie){
        this.nazwa = nazwa;
        this.opis = opis;
        this.zatwierdzenie = zatwierdzenie;
        this.stan = Stan.Utworzone;
        this.dataUtworzenia = LocalDateTime.now();
        this.czasWykonania = losowyCzasWykonania();
        this.id = ++counter;
        Praca.dodajZadanie(id, this);
        listaZadan.add(this);
    }

    public long getId(){
        return id;
    }

    public Zadanie(String nazwa){
        this.nazwa = nazwa;
        this.opis = "";
        this.zatwierdzenie = true;
        this.stan = Stan.Utworzone;
        this.dataUtworzenia = LocalDateTime.now();
        this.czasWykonania = losowyCzasWykonania();
        this.id = ++counter;
        Praca.dodajZadanie(id, this);
        listaZadan.add(this);
    }

    private int losowyCzasWykonania(){
        return (int) (Math.random() * 5) +3;
    }

    // Metoda odpowiadaja za wykonywanie zadania - najpierw sprawdza mozliwosc wykonania a potem wykonuje za pomoca watku
    public void run() {
        if (stan != Stan.Utworzone || !zatwierdzenie || zespol == null || !zespol.getManager().getCzyZdrowy()) {
            if (stan != Stan.Utworzone) {
                System.out.println("Nie mozna rozpoczac zadania " + nazwa + " w momencie, gdy jest ono juz w trakcie wykonywania albo jest juz zakonczone");
            }
            if (!zatwierdzenie) {
                System.out.println("Nie mozna wykonac niezatwierdzonego zadania" + nazwa);
            }
            if (zespol == null) {
                System.out.println("Nie mozna wykonac zadania " + nazwa + " bez przypisania zespolu. Przypisz zespol");
            }
            if (!zespol.getManager().getCzyZdrowy()) {
                System.out.println("Nie mozna wykonac zadania " + nazwa + " w tym zespole, gdy manager jest chory");
            }
            return;
        }
        for (Pracownik pracownik : zespol.getListaPracownikow()) {
            if (!pracownik.getCzyZdrowy()) {
                System.out.println("Nie mozna wykonac zadania " + nazwa + " w tym zespole, gdy co najmniej 1 pracownik jest chory");
                return;
            }
        }
        stan = Stan.Rozpoczete;
        System.out.println("Zadanie o nazwie " + nazwa + " rozpoczyna sie.");
        try {
            for (int i = 0; i < czasWykonania; i++) {
                Thread.sleep(1000);
                System.out.println("Zadanie o nazwie " + nazwa + " jest w trakcie wykonywania. Obecnie jest w " + (i + 1) + " sekundzie wykonywania.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataZakonczenia = LocalDateTime.now();
        stan = Stan.Zakonczone;
        wpisDoBD();
        System.out.println("Zadanie o nazwie " + nazwa + " zostalo zakonczone");
    }

    public String getNazwa(){
        return nazwa;
    }

    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
        wpisDoBD();
    }

    public String getOpis(){
        return opis;
    }

    public void setOpis(String opis){
        this.opis = opis;
        wpisDoBD();
    }

    public Stan getStan() {
        return stan;
    }

    public void setStan(Stan stan){
        this.stan = stan;
        wpisDoBD();
    }

    public LocalDateTime getDataUtworzenia(){
        return dataUtworzenia;
    }

    public LocalDateTime getDataZakonczenia(){
        if (dataZakonczenia == null){
            return null;
        }
        return dataZakonczenia;
    }

    public void setDataZakonczenia (LocalDateTime localDateTime){
        this.dataZakonczenia = localDateTime;
    }

    public int getCzasWykonania(){
        return czasWykonania;
    }

    public void setCzasWykonania(int czasWykonania){
        if(czasWykonania>2 && czasWykonania<9){
            this.czasWykonania=czasWykonania;
            wpisDoBD();
        } else if (czasWykonania<3){
            System.out.println("Podano zbyt krotki czas wykonania");
        } else {
            System.out.println("Podano zbyt dlugi czas wykonania");
        }
    }

    public int getCounter(){
        return counter;
    }

    public Zespol getZespol(){
        if (zespol == null){
            return null;
        }
        return zespol;
    }

    public void setZespol(Zespol zespol){
        this.zespol = zespol;
        zespol.dodajZadanie(this);
        for (Pracownik pracownik : zespol.getListaPracownikow()) {
            pracownik.dodajZadanie(this);
        }
        this.zespol.getManager().dodajZadanie(this);
        wpisDoBD();
    }

    public boolean getZatwierdzenie(){
        return zatwierdzenie;
    }

    public void setZatwierdzenie(boolean zatwierdzenie){
        this.zatwierdzenie=zatwierdzenie;
        wpisDoBD();
    }

    public static ArrayList<Zadanie> getListaZadan(){
        return listaZadan;
    }

    public String toString(){
        String zakonczenie = ".";
        if (stan == Stan.Zakonczone){
            zakonczenie = ", zostalo zakonczone " + dataZakonczenia.toString() + ".";
        }
        return "Zadanie o nazwie " + nazwa + ", z opisem: " + opis + ", o stanie " + stan.toString() + ", zostalo utworzone " + getDataZakonczenia() + ", o czasie wykonania " + czasWykonania + " s, przypisany do zespolu " + getZespol()+zakonczenie;
    }

    public void wpisDoBD(){
        try {
            new FileWriter("src/dane/lista-zadan.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/lista-zadan.txt", true)));
            for (Zadanie zadanie : listaZadan) {
                String result = zadanie.nazwa + " " + zadanie.opis + " " + zadanie.stan + " " +
                        zadanie.dataUtworzenia.getYear() + " " + zadanie.dataUtworzenia.getMonth() + " " + zadanie.dataUtworzenia.getDayOfMonth() + " ";
                if (zadanie.getDataZakonczenia() != null){
                    result += zadanie.getDataZakonczenia().getYear() + " " + zadanie.getDataZakonczenia().getMonth() + " " + zadanie.getDataZakonczenia().getDayOfMonth() + " ";
                }
                if (zadanie.getZespol() != null){
                    result += zadanie.getZespol().getId() + " ";
                }
                result += zadanie.zatwierdzenie;
                out.println(result);
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void odczyt(){
        Zadanie.listaZadan = new ArrayList<>();
        Zadanie.counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/dane/lista-zadan.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.split(" ").length > 7){
                    String tempOpis = "";
                    for (int i = 1 ; i < line.split(" ").length - 5 ; i ++)
                        tempOpis += line.split(" ")[i];
                    Zadanie.listaZadan.add(new Zadanie(line.split(" ")[0], tempOpis, line.split(" ")[]))
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}