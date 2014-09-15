/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Pysakkiverkko;
import fi.helsinki.cs.reittiopas.logiikka.Tila;
import static junit.framework.Assert.*;
import org.junit.*;

/**
 *
 * @author mikko
 */
public class ReittiopasTest {

    private Reittiopas reittiopas;
    private Pysakkiverkko verkko;

    @Before
    public void setUp() {

        verkko = new Pysakkiverkko("verkko.json", "linjat.json");
        reittiopas = new Reittiopas();
    }

    @Test
    public void etsiReittiPalauttaaAStarHaullaOikeinOsa1() {
        String[] oikeatPysakit = {"1130446",
            "1130442",
            "1140447",
            "1140436",
            "1140439",
            "1140440",
            "1150431",
            "1150433",
            "1150435"};
        int[] oikeatAjat = {
            23,
            19,
            17,
            13,
            12,
            10,
            8,
            7,
            4
        };
        int aika = 4;
        Pysakki lahto = verkko.getPysakki("1150435"); //Meilahdentie
        Pysakki maali = verkko.getPysakki("1130446"); //Caloniuksenkatu
        Tila tila = reittiopas.haku(lahto, maali, aika);
        for (int i = 0; i < oikeatPysakit.length; i++) {
            if (tila == null) {
                fail("Palautuksesi oli liian lyhyt. Palauttamasi listan pituus oli " + i + ", lyhimmän mahdollisen reitin pituus on " + oikeatPysakit.length);
            }

            if (tila.getPysakki() == null) {
                fail("Palautuksesi sisälsi tilan jossa pysäkki oli null");
            }

            int pysakkiNro = oikeatPysakit.length - i;
            assertEquals("Pysäkki nro " + pysakkiNro + " väärin:", oikeatPysakit[i], tila.getPysakki().getKoodi());
            assertEquals("Kellonaika pysäkillä nro " + pysakkiNro + " väärin:", oikeatAjat[i], tila.getNykyinenAika());
            tila = tila.getEdellinen();
        }
        assertNull("Palauttamasi reitti oli pidempi kuin " + oikeatPysakit.length, tila);

    }

    @Test
    public void etsiReittiPalauttaaAStarHaullaOikeinOsa2() {
        String[] oikeatPysakit = {"1130446",
            "1130442",
            "1140447",
            "1140436",
            "1140439",
            "1140440",
            "1150431",
            "1150433",
            "1150435"};
        int[] oikeatAjat = {
            33,
            29,
            27,
            23,
            22,
            20,
            18,
            17,
            9
        };
        int aika = 9;
        Pysakki lahto = verkko.getPysakki("1150435"); //Meilahdentie
        Pysakki maali = verkko.getPysakki("1130446"); //Caloniuksenkatu
        Tila tila = reittiopas.haku(lahto, maali, aika);
        for (int i = 0; i < oikeatPysakit.length; i++) {
            if (tila == null) {
                fail("Palautuksesi oli liian lyhyt. Palauttamasi listan pituus oli " + i + ", lyhimmän mahdollisen reitin pituus on " + oikeatPysakit.length);
            }

            if (tila.getPysakki() == null) {
                fail("Palautuksesi sisälsi tilan jossa pysäkki oli null");
            }

            int pysakkiNro = oikeatPysakit.length - i;
            assertEquals("Pysäkki nro " + pysakkiNro + " väärin:", oikeatPysakit[i], tila.getPysakki().getKoodi());
            assertEquals("Kellonaika pysäkillä nro " + pysakkiNro + " väärin:", oikeatAjat[i], tila.getNykyinenAika());
            tila = tila.getEdellinen();
        }
        assertNull("Palauttamasi reitti oli pidempi kuin " + oikeatPysakit.length, tila);

    }

    @Test
    public void etsiReittiPalauttaaAStarHaullaOikeinOsa3() {
        String[] oikeatPysakit = {"1121480",
            "1121438",
            "1220414",
            "1220416",
            "1220418",
            "1220420",
            "1220426",
            "1173416",
            "1173423",
            "1250425",
            "1250427",
            "1250429"};
        int[] oikeatAjat = {
            25,
            24,
            22,
            21,
            20,
            19,
            18,
            17,
            15,
            14,
            13,
            3
        };
        int aika = 3;
        Pysakki lahto = verkko.getPysakki("1250429");
        Pysakki maali = verkko.getPysakki("1121480");
        Tila tila = reittiopas.haku(lahto, maali, aika);
        for (int i = 0; i < oikeatPysakit.length; i++) {
            if (tila == null) {
                fail("Palautuksesi oli liian lyhyt. Palauttamasi listan pituus oli " + i + ", lyhimmän mahdollisen reitin pituus on " + oikeatPysakit.length);
            }

            if (tila.getPysakki() == null) {
                fail("Palautuksesi sisälsi tilan jossa pysäkki oli null");
            }

            int pysakkiNro = oikeatPysakit.length - i;
            assertEquals("Pysäkki nro " + pysakkiNro + " väärin:", oikeatPysakit[i], tila.getPysakki().getKoodi());
            assertEquals("Kellonaika pysäkillä nro " + pysakkiNro + " väärin:", oikeatAjat[i], tila.getNykyinenAika());
            tila = tila.getEdellinen();
        }
        assertNull("Palauttamasi reitti oli pidempi kuin " + oikeatPysakit.length, tila);

    }
}
