import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public abstract class Pracownik implements Comparable<Pracownik>{
    private static ArrayList<Pracownik> listaPracownikow = new ArrayList<>();
    private String imie, nazwisko;
    private Date dataUrodzenia;
    private DzialPracownikow dzialPracownika;
    private boolean czyZdrowy = true;
    private static int counter = 0;
    private int id;
    private ArrayList<Zadanie> listaZadan = new ArrayList<>();
    public Pracownik(String imie, String nazwisko, Date dataUrodzenia, DzialPracownikow dzialPracownikow) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzenia = dataUrodzenia;
        this.dzialPracownika = dzialPracownikow;
        dzialPracownikow.dodajPracownik(this);
        this.id = ++counter;
        listaPracownikow.add(this);
    }

    public static ArrayList<Pracownik> getListaPracownikow(){
        return listaPracownikow;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie){
        this.imie = imie;
        wpisDoBD();
    }

    public String getNazwisko(){
        return nazwisko;
    }

    public void setNazwisko(String nazwisko){
        this.nazwisko = nazwisko;
        wpisDoBD();
    }

    public String getDataUrodzenia(){
        return dataUrodzenia.getYear() + " " + dataUrodzenia.getMonth() + " " + dataUrodzenia.getDay();
    }

    public void setDataUrodzenia(Date dataUrodzenia){
        this.dataUrodzenia = dataUrodzenia;
        wpisDoBD();
    }

    public DzialPracownikow getDzialPracownikow(){
        return dzialPracownika;
    }

    public void setDzialPracownikow(DzialPracownikow dzialPracownikow){
        dzialPracownika.usunPracownikaZDzialu(this);
        dzialPracownika = dzialPracownikow;
        dzialPracownikow.dodajPracownik(this);
        wpisDoBD();
    }

    public boolean getCzyZdrowy(){
        return czyZdrowy;
    }

    public void setCzyZdrowy(boolean czyZdrowy){
        this.czyZdrowy = czyZdrowy;
        wpisDoBD();
    }

    public int getId(){
        return id;
    }

    public int getCounter(){
        return counter;
    }

    @Override
    public int compareTo(Pracownik innyPracownik) {
        int porownanieDatUrodzenia = this.dataUrodzenia.compareTo(innyPracownik.dataUrodzenia);
        if (porownanieDatUrodzenia != 0){
            return porownanieDatUrodzenia;
        }
        int porownanieNazwisk = this.nazwisko.compareTo(innyPracownik.getNazwisko());
        if (porownanieNazwisk != 0){
            return porownanieNazwisk;
        }
        return this.imie.compareTo(innyPracownik.getImie());
    }

    public void dodajZadanie(Zadanie zadanie){
        listaZadan.add(zadanie);
        wpisDoBD();
    }

    public ArrayList<Zadanie> getListaZadan (){
        return listaZadan;
    }

    @Override
    public String toString() {
        return "Pracownik o imieniu " + imie + ", nazwisku " + nazwisko + ", urodzony " + getDataUrodzenia() + ". Czy jest zdrowy? " + czyZdrowy;
    }

    public String getDane(){
        StringBuilder result = new StringBuilder(imie + " " + nazwisko + " " + getDataUrodzenia() + " " + dzialPracownika.getId() + " "  + czyZdrowy + "\n");
        if (getListaZadan() != null) {
            for (Zadanie zadanie : getListaZadan()) {
                result.append(zadanie.getId()).append(" ");
            }
        }
        return result.toString();
    }

    public void wpisDoBD(){

    }

    public static void odczytListy(){
        try {
            Path path = Paths.get("src/dane/dane-pracownikow.txt");
            String file = String.valueOf(Files.readAllLines(path));
            file = file.replace("[", "");
            file = file.replace("]", "");

            System.out.println(file);
            for (String line:
                 file.split(", ")) {
                if (line.split(" ")[0].equals("T")) {
                        Trener temp = new Trener(
                                line.split(" ")[1], line.split(" ")[2],
                                new Date(Integer.parseInt(line.split(" ")[3]), Integer.parseInt(line.split(" ")[4]), Integer.parseInt(line.split(" ")[5])),
                                DzialPracownikow.getListaDzialow().get(Integer.parseInt(line.split(" ")[6])-1), line.split(" ")[7]
                        );
//                    for (String i:
//                         line.split(" ")) {
//                        temp.dodajZadanie(listaZadan.get(Integer.valueOf(i)));
//                    }
                        listaPracownikow.add(temp);
                    } else if (line.split(" ")[0].equals("M")) {
                        Manager temp = new Manager(
                                line.split(" ")[1], line.split(" ")[2],
                                new Date(Integer.parseInt(line.split(" ")[3]), Integer.parseInt(line.split(" ")[4]), Integer.parseInt(line.split(" ")[5])),
                                DzialPracownikow.getListaDzialow().get(Integer.parseInt(line.split(" ")[6])-1), line.split(" ")[7], line.split(" ")[8], new ArrayList<Zespol>());
//                    for (String i:
//                            line.split(" ")) {
//                        temp.dodajZadanie(listaZadan.get(Integer.valueOf(i)));
//                    }
//                    for (String i:
//                            line.split(" ")) {
//                        temp.dodajZespol(listaZespolow.get(Integer.valueOf(i)));
//                    }
                        listaPracownikow.add(temp);
                    } else if (line.split(" ")[0].equals("R")){
                        Recepcjonista temp = new Recepcjonista(
                                line.split(" ")[1], line.split(" ")[2],
                                new Date(Integer.parseInt(line.split(" ")[3]), Integer.parseInt(line.split(" ")[4]), Integer.parseInt(line.split(" ")[5])),
                                DzialPracownikow.getListaDzialow().get(Integer.parseInt(line.split(" ")[6])-1), line.split(" ")[6], line.split(" ")[7]);
//                    for (String i:
//                            line.split(" ")) {
//                        temp.dodajZadanie(listaZadan.get(Integer.valueOf(i)));
//                    }
                        listaPracownikow.add(temp);
                    }
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}