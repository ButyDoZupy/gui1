import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Zespol{
    private String nazwa;
    private Manager manager;
    private ArrayList<Pracownik> listaPracownikow = new ArrayList<>();
    private static int counter = 0;
    private int id;
    private ArrayList<Zadanie> listaZadan = new ArrayList<>();
    private static ArrayList <Zespol> listaZespolow = new ArrayList<>();
    public Zespol(String nazwa, Manager manager, ArrayList<Pracownik> listaPracownikow) {
        this.nazwa = nazwa;
        this.manager = manager;
        manager.dodajZespol(this);
        this.listaPracownikow = listaPracownikow;
        this.id = ++counter;
        listaZespolow.add(this);
        wpisDoBD();
    }

    // Dodawanie pracownika do zespolu
    public void dodajPracownika (Pracownik pracownik){
        if (pracownik.getClass() != Manager.class){
            listaPracownikow.add(pracownik);
            wpisDoBD();
        } else {
            System.out.println("Manager nie moze zostac dodany do zespolu jako szeregowy pracownik.");
        }
    }

    // Dodawanie listy pracownikow do zepolu
    public void dodajPracownika (ArrayList<Pracownik> listaPracownikow){
        for (Pracownik pracownik : listaPracownikow) {
            dodajPracownika(pracownik);
        }
    }

    public String getNazwa(){
        return nazwa;
    }
    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
        wpisDoBD();
    }

    public Manager getManager(){
        return manager;
    }

    public void setManager(Manager manager){
        this.manager = manager;
        this.manager.dodajZespol(this);
        wpisDoBD();
    }

    public int getId(){
        return id;
    }

    public int getCounter(){
        return counter;
    }

    public void dodajZadanie (Zadanie zadanie){
        listaZadan.add(zadanie);
        wpisDoBD();
    }

    public ArrayList<Pracownik> getListaPracownikow(){
        return listaPracownikow;
    }

    public static ArrayList<Zespol> getListaZespolow(){
        return listaZespolow;
    }

    @Override
    public String toString() {
        return "Zespol o nazwie " + nazwa + ", pod kierownictwem " + manager.getImie() + " " + manager.getNazwisko() +", w ktorym pracuja nastepujacy pracownicy: "+listaPracownikow.toString();
    }

    public void wpisDoBD(){
        try {
            new FileWriter("src/dane/dane-zespolow.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/dane-zespolow.txt", true)));
            for (Zespol zespol : listaZespolow){
                out.println(zespol.nazwa + " " + zespol.manager.getId());
                for (Pracownik pracownik : zespol.listaPracownikow)
                    out.write(pracownik.getId() + " ");
                out.println();
                for (Zadanie zadanie : zespol.listaZadan)
                    out.write(zadanie.getId() + " ");
                out.println();
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
