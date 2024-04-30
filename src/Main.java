import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args){

        DzialPracownikow.odczyt();
        System.out.println(DzialPracownikow.getListaDzialow());
        for (DzialPracownikow dzial:
             DzialPracownikow.getListaDzialow()) {
            System.out.println(dzial.getId());
        }
//        Pracownik.odczytListy();
//        System.out.println(Pracownik.getListaPracownikow());


//        DzialPracownikow zmianaPoranna = DzialPracownikow.createDzial("Poranna");
//        DzialPracownikow zmianaWieczorna = DzialPracownikow.createDzial("Wieczorna");
//        DzialPracownikow zmianaPopoludniowa = DzialPracownikow.createDzial("Popoludniowa");
//        System.out.println("Utworzono dzialy pracownikow: ");
//        System.out.println("1) " + zmianaPoranna);
//        System.out.println("2) " + zmianaWieczorna);
//        System.out.println("3) " + zmianaPopoludniowa);
//        zmianaWieczorna.setNazwa("Zmiana Nocna");
//        System.out.println(zmianaPopoludniowa.getListaPracownikow());
//        System.out.println(DzialPracownikow.getListaDzialow());
//
//        Trener trener1 = new Trener("Jakub", "Dzik", new Date(1999, 5, 20), zmianaPoranna, "klatka");
//        Trener trener2 = new Trener("Lukasz", "Koks", new Date(2001, 3, 5), zmianaPopoludniowa, "plecy");
//        Trener trener3 = new Trener("Michal", "Byk", new Date(1992, 9, 13), zmianaWieczorna, "nogi");
//        System.out.println("Utworzono trenerow: ");
//        System.out.println("1) " + trener1);
//        System.out.println("2) " + trener2);
//        System.out.println("3) " + trener3);
//        trener1.setDataUrodzenia(new Date(1989, 5, 20));
//        System.out.println(trener2.getSpecjalizacja());
//        trener3.setSpecjalizacja("lydki");
//
//        Recepcjonista recepcjonista1 = new Recepcjonista("Maria", "Fit", new Date(2003, 9, 30), zmianaPoranna, "Fitmar", "pass1");
//        Recepcjonista recepcjonista2 = new Recepcjonista("Tomasz", "Kowalski", new Date(1997, 7, 16), zmianaPopoludniowa, "KowTom", "abcdef");
//        Recepcjonista recepcjonista3 = new Recepcjonista("Zuzanna", "Szybka", new Date(2000, 1, 6), zmianaWieczorna, "SzybZuz", "h@slo");
//        System.out.println("Utworzono recepcjonistów: ");
//        System.out.println("1) " + recepcjonista1);
//        System.out.println("2) " + recepcjonista2);
//        System.out.println("3) " + recepcjonista3);
//        recepcjonista1.setDzialPracownikow(zmianaWieczorna);
//        recepcjonista2.setLogin("SilnyTom");
//        recepcjonista3.setHaslo("P@ssword");
//
//        Manager manager1 = new Manager("Jakub", "Szefowy", new Date(1978, 11, 2), zmianaPoranna, "SzefJa", "tajnehaslo", new ArrayList<>());
//        Manager manager2 = new Manager("Jan", "Kowalski", new Date(1958, 4, 7), zmianaPopoludniowa, "Kowalsky", "trudnehaslo", new ArrayList<>());
//        Manager manager3 = new Manager("Tomasz", "Nowak", new Date(1993, 2, 23), zmianaWieczorna, "NowakT", "123abc", new ArrayList<>());
//        System.out.println("Utworzono menagerow: ");
//        System.out.println("1) " + manager1);
//        System.out.println("2) " + manager2);
//        System.out.println("3) " + manager3);
//        manager1.setCzyZdrowy(false);
//        System.out.println(manager2.getListaZespolow());
//        System.out.println(manager3.compareTo(manager1));
//
//        Zespol zespol1 = new Zespol("Zespol1", manager1, new ArrayList<>(List.of(trener1)));
//        Zespol zespol2 = new Zespol("Zespol2", manager2, new ArrayList<>(Arrays.asList(trener2, recepcjonista2)));
//        Zespol zespol3 = new Zespol("Zespol3", manager3, new ArrayList<>(Arrays.asList(trener3, recepcjonista1, recepcjonista3)));
//        System.out.println("Utworzono zespoly: ");
//        System.out.println("1) " + zespol1);
//        System.out.println("2) " + zespol2);
//        System.out.println("3) " + zespol3);
//        zespol1.dodajPracownika(recepcjonista1);
//        ArrayList<Pracownik> listaPracownikowDoZespoluDrugiego = new ArrayList<>();
//        listaPracownikowDoZespoluDrugiego.add(recepcjonista3);
//        listaPracownikowDoZespoluDrugiego.add(manager1);
//        zespol2.dodajPracownika(listaPracownikowDoZespoluDrugiego);
//        zespol3.setNazwa("Zespol3v2");
//        System.out.println(zespol1.getManager());
//        zespol2.setManager(manager2);
//
//        Zadanie zadanie1 = new Zadanie("Zumba", "Trening zumby dla grupy", false);
//        Zadanie zadanie2 = new Zadanie("Crossfit", "Trening crossfit dla grupy", true);
//        Zadanie zadanie3 = new Zadanie("Rozgrzewka", "Rozgrzewka przed treningiem", true);
//        Zadanie zadanie4 = new Zadanie("Rejestracja klientow");
//        Zadanie zadanie5 = new Zadanie("Trening indywidualny");
//        Zadanie zadanie6 = new Zadanie("Rozliczenia");
//        System.out.println(zadanie1.getNazwa());
//        zadanie6.setNazwa("Liczenie zyskow");
//        System.out.println(zadanie2.getOpis());
//        zadanie3.setOpis("Rozgrzewka przed treningiem - absolutnie niezbedna!");
//        System.out.println(zadanie4.getStan());
//        System.out.println(zadanie5.getDataUtworzenia());
//        System.out.println(zadanie1.getCzasWykonania());
//        zadanie6.setCzasWykonania(50);
//        zadanie2.setCzasWykonania(6);
//        zadanie1.setZespol(zespol1);
//        zadanie2.setZespol(zespol2);
//        zadanie3.setZespol(zespol3);
//        zadanie4.setZespol(zespol1);
//        zadanie6.setZespol(zespol3);
//        System.out.println(zadanie2.getZatwierdzenie());
//        zadanie4.setZatwierdzenie(false);
//
//        Praca praca1 = new Praca("Praca zespolu pierwszego ", zespol1);
//        Praca praca2 = new Praca("Praca zespolu drugiego ", zespol2);
//        Praca praca3 = new Praca("Praca zespolu trzeciego ", null);
//        System.out.println(Praca.pozyskajZadaniaPoID(6));
//        System.out.println(Praca.pozyskajZadaniaPoID(10));
//        praca1.setOpis("Praca zespolu numer 1 ");
//        System.out.println(praca2.getOpis());
//        praca3.setZespol(zespol3);
//
//        System.out.println(DzialPracownikow.getListaDzialow());
//        System.out.println(Pracownik.getListaPracownikow());
//        System.out.println(Zespol.getListaZespolow());
//        System.out.println(Zadanie.getListaZadan());
//
//        zadanie3.start();
//        try {
//            zadanie3.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread thread1 = new Thread(praca1);
//        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread thread2 = new Thread(praca2);
//        thread2.start();
//        try {
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread thread3 = new Thread(praca3);
//        thread3.start();
    }
}