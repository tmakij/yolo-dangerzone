package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Pysakkiverkko;
import fi.helsinki.cs.reittiopas.logiikka.Tila;
import static junit.framework.Assert.*;
import org.junit.*;

/**
 *
 * @author kumikumi
 */
public class VertailijaTest {

    private Pysakkiverkko verkko;
    private Pysakki pysakki1;
    private Pysakki maali;
    private Pysakki meilahdentie;
    private Pysakki caloniuksenkatu;
    private Vertailija vertailija;

    @Before
    public void setUp() {
        verkko = new Pysakkiverkko("verkko.json", "linjat.json");
        pysakki1 = verkko.getPysakki("1250429");
        maali = verkko.getPysakki("1121480");

        meilahdentie = verkko.getPysakki("1150435"); //Meilahdentie
        caloniuksenkatu = verkko.getPysakki("1130446"); //Caloniuksenkatu
    }

    @Test
    public void heuristiikkaToimii() {
        vertailija = new Vertailija(maali);
        assertEquals(11.9873, vertailija.heuristiikka(pysakki1), 0.01);
    }

    @Test
    public void heuristiikkaToimii2() {
        vertailija = new Vertailija(caloniuksenkatu);
        assertEquals(9.9933, vertailija.heuristiikka(meilahdentie), 0.01);
    }

    @Test
    public void vertailuToimii() {
        vertailija = new Vertailija(maali);
        Tila tila1 = new Tila(pysakki1, null, 20);
        Tila tila2 = new Tila(pysakki1, null, 10);
        assertTrue(vertailija.compare(tila1, tila2) > 0);
    }

    @Test
    public void vertailuToimii2() {
        vertailija = new Vertailija(maali);
        Tila tila1 = new Tila(pysakki1, null, 20);
        Tila tila2 = new Tila(pysakki1, null, 10);
        assertTrue(vertailija.compare(tila2, tila1) < 0);
    }

    @Test
    public void vertailuToimii3() {
        vertailija = new Vertailija(maali);
        Tila tila1 = new Tila(pysakki1, null, 10);
        Tila tila2 = new Tila(pysakki1, null, 10);
        assertEquals(0, vertailija.compare(tila1, tila2));
    }

    @Test
    public void vertailuToimii4() {
        vertailija = new Vertailija(maali);
        Tila tila1 = new Tila(caloniuksenkatu, null, 10);
        Tila tila2 = new Tila(pysakki1, null, 10);
        assertTrue(vertailija.compare(tila2, tila1) > 0);
    }

    @Test
    public void vertailuToimii5() {
        vertailija = new Vertailija(maali);
        Tila tila1 = new Tila(caloniuksenkatu, null, 10);
        Tila tila2 = new Tila(pysakki1, null, 10);
        assertTrue(vertailija.compare(tila1, tila2) < 0);
    }

}
