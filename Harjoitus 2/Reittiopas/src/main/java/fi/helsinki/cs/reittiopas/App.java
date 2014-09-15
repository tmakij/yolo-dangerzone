package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Pysakkiverkko;
import fi.helsinki.cs.reittiopas.logiikka.Tila;

public class App {

    /**
     * A*-haku
     *
     * Tämä on samankaltainen pohja kuin viime viikon tehtävässä, mutta tällä
     * kertaa tehtävänä on toteuttaa A*-haku.
     *
     * Toteuta ensin luokassa Vertailija olevat metodit 'heuristiikka' ja
     * 'compare', sen jälkeen toteuta A*-haku Reittiopas-luokkaan.
     *
     * Toteuta luokka Reittiopas siten, että etsittäessä reittiä kahden pysäkin
     * väliltä se palauttaa valmiin reitin Tila-olioina taaksepäin linkitettynä
     * listana siten, että palautettu "Tila" osoittaa maalipysäkkiin.
     * Esimerkiksi kutsuttaessa reittiopasta lähtöpysäkillä
     * "1150435(Meilahdentie)" ja maalipysäkillä "1130446(Caloniuksenkatu)" ja
     * aloitusajalla "4" saadaan palautuksena Tila-olio, jonka pysäkki on
     * "1130446(Caloniuksenkatu)" ja "edellinen" osoittaa toiseksi viimeiseen
     * pysäkkiin jne.
     *
     * Kelattaessa palautuksena saatu linkitetty lista lopusta alkuun saadaan
     * kokonaisuudessaan seuraava reitti:
     *
     * [23min]: 1130446(Caloniuksenkatu) [MAALI] -> [19min]:
     * 1130442(Apollonkatu) -> [17min]: 1140447(Töölöntori) -> [13min]:
     * 1140436(Ooppera) -> [12min]: 1140439(Töölön halli) -> [10min]:
     * 1140440(Kansaneläkelaitos) -> [8min]: 1150431(Töölön tulli) -> [7min]:
     * 1150433(Meilahden sairaala) -> [4min]: 1150435(Meilahdentie) [LÄHTÖ]
     *
     * Linkitetyn listan viimeisen Tilan pysäkki on lähtöpysäkki "1150435" ja
     * "edellinen" on null.
     *
     * Projekti sisältää JUnit-yksikkötestit tälle ja parille muulle
     * esimerkkireitille.
     *
     * @param args args
     */
    public static void main(String[] args) {

        Pysakkiverkko pysakkiverkko = new Pysakkiverkko("verkko.json", "linjat.json");
        Reittiopas reittiopas = new Reittiopas();
        int aloitusAika = 4;
        Pysakki lahto = pysakkiverkko.getPysakki("1150435"); //Meilahdentie
        Pysakki maali = pysakkiverkko.getPysakki("1130446"); //Caloniuksenkatu
        //Pysakki lahto = pysakkiverkko.getPysakki("1250429");
        //Pysakki maali = pysakkiverkko.getPysakki("1121480");
        Tila tila = reittiopas.haku(lahto, maali, aloitusAika);
        while (tila != null) {
            System.out.println(tila);
            tila = tila.getEdellinen();
        }
    }
}
