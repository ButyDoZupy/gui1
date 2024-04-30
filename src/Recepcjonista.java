import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Recepcjonista extends Pracownik implements IDobryPracownik{
    private String login, haslo, initial;
    public Recepcjonista(String imie, String nazwisko, Date dataUrodzenia, DzialPracownikow dzialPracownikow, String login, String haslo) {
        super(imie, nazwisko, dataUrodzenia, dzialPracownikow);
        this.login = login;
        this.haslo = haslo;
        this.initial = generateInitial(this.getImie(), this.getNazwisko());
        wpisDoBD();
    }
    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
        wpisDoBD();
    }

    public String getHaslo(){
        return haslo;
    }

    public void setHaslo(String haslo){
        this.haslo = haslo;
        wpisDoBD();
    }

    public String getInitial(){
        return initial;
    }

    //Metoda odpowiedzialna za tworzenie metody initial
    public String generateInitial(String imie, String nazwisko){
        return String.valueOf(imie.charAt(0))+String.valueOf(nazwisko.charAt(0));
    }

    @Override
    public void setImie(String imie){
        super.setImie(imie);
        this.initial = generateInitial(imie, getNazwisko());
        wpisDoBD();
    }

    @Override
    public void setNazwisko(String nazwisko){
        super.setNazwisko(nazwisko);
        this.initial = generateInitial(getImie(),nazwisko);
        wpisDoBD();
    }

    @Override
    public String toString() {
        return "Recepcjonista o imieniu " + getImie() + ", nazwisku " + getNazwisko() + ", loginie " + login + ", hasle " + haslo + ", initalu " + initial +
                ", urodzony " + getDataUrodzenia() + ". Czy jest zdrowy? " + getCzyZdrowy();
    }

    @Override
    public void przywitanie() {
        System.out.println("Czesc, w czym moge pomoc?");
    }

    @Override
    public void praca() {
        System.out.println("Dzisiaj zajmuje sie obsluga klienta i administracja.");
    }

    @Override
    public void pozegnanie() {
        System.out.println("Czesc!");
    }

    @Override
    public String getDane(){
        StringBuilder result = new StringBuilder("R " + getImie() + " " + getNazwisko() + " " + getDataUrodzenia() + " " + getDzialPracownikow().getId() + " " + login + " " + haslo + " " + getCzyZdrowy() + "\n");
        if (getListaZadan() != null) {
            for (Zadanie zadanie : getListaZadan()) {
                result.append(zadanie.getId()).append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public void wpisDoBD() {
        try {
            new FileWriter("src/dane/dane-pracownikow.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/dane-pracownikow.txt", true)));
            for (Pracownik pracownik:
                    Pracownik.getListaPracownikow()) {
                out.println(pracownik.getDane());
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
