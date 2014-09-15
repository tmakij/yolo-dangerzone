package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Tila;
import java.util.Comparator;

public final class Vertailija implements Comparator<Tila> {

    private final Pysakki maali;

    public Vertailija(Pysakki maaliPysakki) {
        this.maali = maaliPysakki;
    }

    /**
     * Toteuta metodi heuristiikka siten, että se palauttaa parametrina annetun
     * pysäkin ja tämän luokan sisällä määritellyn maalipysäkin koordinaattien
     * välisen euklidisen etäisyyden jaettuna luvulla 260. Oletamme siis, että
     * ratikka liikkuu keskimäärin 260 koordinaattipistettä minuutissa, ja
     * arvioimme tässä metodissa maalipisteeseen jäljellä olevan ajan.
     *
     * @param pysakki
     * @return Arvioitu jäljelläoleva aika
     */
    public double heuristiikka(final Pysakki pysakki) {
        final double fx = Math.abs(pysakki.getX() - maali.getX());
        final double fy = Math.abs(pysakki.getY() - maali.getY());
        return Math.sqrt(fx * fx + fy * fy) / 260D;
    }

    /**
     * Toteuta metodi compare siten, että se palauttaa jonkin positiivisen luvun
     * silloin kun Tilan t1 aika+heuristiikka on suurempi kuin Tilan t2
     * aika+heuristiikka, ja vastaavasti jonkin negatiivisen luvun, kun Tilan t2
     * aika+heuristiikka on suurempi kuin Tilalla t1. Palauta 0 jos molemmilla
     * solmuista aika+heuristiikka on yhtä suuri.
     *
     * Tätä metodia käytetään tilojen järjestämiseen A*-haussa käytettävän
     * prioriteettijonon sisällä, jolloin jonosta saadaan ulos aina se tila,
     * joka todennäköisimmin on osa lyhintä reittiä maaliin.
     *
     * @param t1
     * @param t2
     * @return vertailun tulos
     */
    @Override
    public int compare(final Tila t1, final Tila t2) {
        double tila1 = heuristiikka(t1.getPysakki()) + t1.getNykyinenAika();
        double tila2 = heuristiikka(t2.getPysakki()) + t2.getNykyinenAika();
        if (tila1 > tila2) {
            return 1;
        } else if (tila1 < tila2) {
            return -1;
        }
        return 0;
    }
}
