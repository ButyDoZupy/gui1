import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Trener extends Pracownik implements IDobryPracownik{
    private String specjalizacja;
    public Trener(String imie, String nazwisko, Date dataUrodzenia, DzialPracownikow dzialPracownikow, String specjalizacja) {
        super(imie, nazwisko, dataUrodzenia, dzialPracownikow);
        this.specjalizacja = specjalizacja;
        wpisDoBD();
    }

    public String getSpecjalizacja(){
        return specjalizacja;
    }

    public void setSpecjalizacja(String specjalizacja){
        this.specjalizacja = specjalizacja;
        wpisDoBD();
    }

    @Override
    public String toString() {
        return "Trener o imieniu " + getImie() + ", nazwisku " + getNazwisko() + ", o specjalizacji " + specjalizacja + ", urodzony " + getDataUrodzenia() + ". Czy jest zdrowy? " + getCzyZdrowy();
    }

    @Override
    public String getDane(){
        StringBuilder result = new StringBuilder("T " + getImie() + " " + getNazwisko() + " " + getDataUrodzenia() + " " + getDzialPracownikow().getId() + " " + getCzyZdrowy() + " " + specjalizacja + "\n");
        if (getListaZadan() != null) {
            for (Zadanie zadanie : getListaZadan()) {
                result.append(zadanie.getId()).append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public void przywitanie() {
        System.out.println("Czesc, dzisiaj bede Twoim trenerem");
    }

    @Override
    public void praca() {
        System.out.println("Jako ze nogi robi sie podczas chodzenia to na silowni robimy klate");
    }

    @Override
    public void pozegnanie() {
        System.out.println("Siema");
    }

    @Override
    public void wpisDoBD(){
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