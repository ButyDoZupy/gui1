import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Praca implements Runnable {
    private static Map<Integer, Zadanie> mapaZadan = new HashMap<>();
    private String opis;
    private Zespol zespol;
    private static int counter = 0;
    private int id;
    private static ArrayList<Praca> listaPrac = new ArrayList<>();
    public Praca (String opis, Zespol zespol){
        this.opis = opis;
        this.zespol = zespol;
        this.id = ++counter;
        listaPrac.add(this);
        wpisDoBD();
    }

    public static Zadanie pozyskajZadaniaPoID(int id){
        for (int i = 1; i <= mapaZadan.size(); i++) {
            Zadanie zadanie = mapaZadan.get(i);
            if (zadanie.getId() == id){
                return zadanie;
            }
        }
        return null;
    }

    // metoda dodajaca zadanie do hashmapy - wywolywana w konstruktorze klasy zadanie
    public static void dodajZadanie(int id, Zadanie zadanie){
        mapaZadan.put(id, zadanie);
        wpisDoBD();
    }

    public void setOpis(String opis){
        this.opis = opis;
        wpisDoBD();
    }

    public String getOpis(){
        return opis;
    }

    public void setZespol(Zespol zespol){
        this.zespol = zespol;
        Praca.wpisDoBD();
    }

    public Zespol getZespol(){
        if (zespol == null){
            return null;
        }
        return zespol;
    }

    public int getId(){
        return id;
    }

    public int getCounter(){
        return counter;
    }

    public void run(){
        if (!zespol.getManager().getCzyZdrowy()) {
            System.out.println("Nie mozna wykonac pracy " + opis + " , gdy manager jest chory");
        } else {
            System.out.println(opis + " rozpoczyna sie");
            for (Zadanie zadanie : mapaZadan.values()) {
                boolean niezdrowyPracownik = false;
                if (zadanie.getZespol() == this.zespol) {
                    if (zadanie.getStan() != Zadanie.Stan.Utworzone || !zadanie.getZatwierdzenie()) {
                        if (zadanie.getStan() != Zadanie.Stan.Utworzone) {
                            System.out.println("Nie mozna rozpoczac zadania " + zadanie.getNazwa() + " w momencie, gdy jest ono juz w trakcie wykonywania albo jest juz zakonczone");
                        }
                        if (!zadanie.getZatwierdzenie()) {
                            System.out.println("Nie mozna wykonac niezatwierdzonego zadania " + zadanie.getNazwa());
                        }
                        continue;
                    }

                    for (Pracownik pracownik : zespol.getListaPracownikow()) {
                        if (!pracownik.getCzyZdrowy()) {
                            System.out.println("Nie mozna wykonac zadania " + zadanie.getNazwa() + " w tym zespole, gdy co najmniej 1 pracownik jest chory");
                            niezdrowyPracownik = true;
                        }
                    }
                    if (niezdrowyPracownik)
                        break;
                    zadanie.setStan(Zadanie.Stan.Rozpoczete);
                    System.out.println("Zadanie o nazwie " + zadanie.getNazwa() + " rozpoczyna sie.");
                    try {
                        for (int i = 0; i < zadanie.getCzasWykonania(); i++) {
                            Thread.sleep(1000);
                            System.out.println("Zadanie o nazwie " + zadanie.getNazwa() + " jest w trakcie wykonywania. Obecnie jest w " + (i + 1) + " sekundzie wykonywania.");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    zadanie.setDataZakonczenia(LocalDateTime.now());
                    zadanie.setStan(Zadanie.Stan.Zakonczone);
                    System.out.println("Zadanie o nazwie " + zadanie.getNazwa() + " zostalo zakonczone");
                }
            }
            Praca.wpisDoBD();
            System.out.println(opis + " konczy sie");
        }
    }

    @Override
    public String toString() {
        return "Praca trwa. Dzisiaj bedzie polegac na: " + opis + " Bedzie za nia odpowiedzialny zespol " + zespol.toString() + ". Do wykonania beda mieli nastepujaca kolekcje zadan: " + mapaZadan.toString();
    }

    public static void wpisDoBD(){
        try {
            new FileWriter("src/dane/lista-prac.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/lista-prac.txt", true)));
            for (Praca praca : Praca.listaPrac) {
                out.println(praca.opis + " " + (praca.getZespol() == null ? null : praca.getZespol().getId()));
                for (Zadanie zadanie : mapaZadan.values()) {
                    if (zadanie.getZespol() == praca.getZespol())
                    out.write(zadanie.getId() + " ");
                }
                out.println();
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}