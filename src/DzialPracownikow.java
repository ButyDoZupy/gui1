import java.io.*;
import java.util.ArrayList;

public class DzialPracownikow {
    private String nazwa;
    private static ArrayList<DzialPracownikow> listaDzialow = new ArrayList<>();
    private ArrayList<Pracownik> listaPracownikow = new ArrayList<>();
    private static int counter = 0;
    private int id;

    private DzialPracownikow(String nazwa) {
        this.nazwa = nazwa;
        this.id = ++counter;
    }

    // Metoda tworzaca nowy dzial
    public static DzialPracownikow createDzial(String nazwa) {
        for (DzialPracownikow dzial : listaDzialow) {
            if (dzial.getNazwa().equals(nazwa)) {
                try {
                    throw new NotUniqueNameException("Dzial o takiej nazwie juz istnieje.");
                } catch (NotUniqueNameException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        DzialPracownikow dzial = new DzialPracownikow(nazwa);
        listaDzialow.add(dzial);
        DzialPracownikow.wpisDoBD();
        return dzial;
    }

    public static DzialPracownikow odczytDzial(String nazwa) {
        for (DzialPracownikow dzial : listaDzialow) {
            if (dzial.getNazwa().equals(nazwa)) {
                try {
                    throw new NotUniqueNameException("Dzial o takiej nazwie juz istnieje.");
                } catch (NotUniqueNameException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        DzialPracownikow dzial = new DzialPracownikow(nazwa);
        listaDzialow.add(dzial);
        return dzial;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getDane() {
        StringBuilder str = new StringBuilder(nazwa + "\n");
        for (Pracownik pracownik : listaPracownikow) {
            str.append(pracownik.getId()).append(" ");
        }
        return str.toString();
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
        DzialPracownikow.wpisDoBD();
    }

    public int getId() {
        return id;
    }

    public int getCounter() {
        return counter;
    }

    // Metoda podnoszaca wyjatek tej samej nazwy dzialu
    private static class NotUniqueNameException extends Exception {
        public NotUniqueNameException(String message) {
            super(message);
        }
    }

    public void dodajPracownik(Pracownik pracownik) {
        listaPracownikow.add(pracownik);
        DzialPracownikow.wpisDoBD();
    }

    public void usunPracownikaZDzialu(Pracownik pracownik) {
        listaPracownikow.remove(pracownik);
        DzialPracownikow.wpisDoBD();
    }

    public ArrayList<Pracownik> getListaPracownikow() {
        return listaPracownikow;
    }

    public static ArrayList<DzialPracownikow> getListaDzialow() {
        return listaDzialow;
    }

    @Override
    public String toString() {
        return "Dzial o nazwie " + nazwa;
    }

    public static void wpisDoBD() {
        try {
            new FileWriter("src/dane/dane-dzialow.txt", false).close();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/dane/dane-dzialow.txt", true)));
            for (DzialPracownikow dzial : listaDzialow) {
                out.println(dzial.getDane());
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void odczyt() {
        DzialPracownikow.listaDzialow = new ArrayList<>();
        DzialPracownikow.counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/dane/dane-dzialow.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                DzialPracownikow.odczytDzial(line.split(" ")[0]);
                reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}