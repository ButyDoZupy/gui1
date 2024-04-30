import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Manager extends Recepcjonista implements IDobryPracownik{
    private ArrayList<Zespol> listaZespolow = new ArrayList<>();
    public Manager(String imie, String nazwisko, Date dataUrodzenia, DzialPracownikow dzialPracownikow, String login, String haslo, ArrayList<Zespol> listaZespolow) {
        super(imie, nazwisko, dataUrodzenia, dzialPracownikow, login, haslo);
        this.listaZespolow = listaZespolow;
    }

    public void dodajZespol (Zespol zespol){
        listaZespolow.add(zespol);
        wpisDoBD();
    }

    public ArrayList<Zespol> getListaZespolow(){
        return listaZespolow;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Manager o imieniu " + getImie() + ", nazwisku " + getNazwisko() + ", loginie " + getLogin() + ", hasle " + getHaslo() + ", inicjale " + getInitial() + ", urodzony " + getDataUrodzenia() + ", liscie zespolow [");
        if (listaZespolow != null) {
            for (Zespol zespol : listaZespolow)
                str.append(zespol.getNazwa()).append(", ");
        }
        str.append("] Czy jest zdrowy? ").append(getCzyZdrowy());
        return str.toString();
    }

    public void przywitanie() {
        System.out.println("Dzien dobry. Jestem kierownikiem tej silowni, jak moge Panstwu pomoc?");
    }

    public void praca() {
        System.out.println("Hop hop hop hop hop");
    }

    public void pozegnanie() {
        System.out.println("Do widzenia i zapraszamy ponownie.");
    }

    @Override
    public String getDane(){
        StringBuilder result = new StringBuilder("M " + getImie() + " " + getNazwisko() + " " + getDataUrodzenia() + " " + getDzialPracownikow().getId() + " " + getLogin() + " " + getHaslo() + " " + getCzyZdrowy() + "\n");
        if (getListaZadan() != null) {
            for (Zadanie zadanie : getListaZadan()) {
                result.append(zadanie.getId()).append(" ");
            }
        }
        result.append("\n");
        if (listaZespolow != null) {
            for (Zespol zespol : listaZespolow) {
                result.append(zespol.getId()).append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public void wpisDoBD() {
        try {
            new FileWriter("src/dane/dane-pracownikow.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/dane-pracownikow.txt", true)));
            for (Pracownik pracownik : Pracownik.getListaPracownikow()) {
                out.println(pracownik.getDane());
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}