package fi.helsinki.cs.reittiopas;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Reittiopas {

    /**
     * Toteuta leveyssuuntainen haku. Palauta reitti taaksepäin linkitettynä
     * listana Tila-olioita, joista ensimmäinen osoittaa maalipysäkkiin ja
     * jokainen tuntee pysäkin ja tilan, josta kyseiseen tilaan päästiin
     * (viimeisen solmun Pysäkki on lähtöpysäkki ja edellinen tila on null).
     *
     * Voit selvittää pysäkin naapuripysäkit, eli pysäkit joihin pysäkiltä on
     * suora yhteys, kutsumalla pysäkin getNaapurit() -metodia.
     *
     * @param lahto Lahtopysakin koodi
     * @param maali Maalipysakin koodi
     * @return Tila-olioista koostuva linkitetty lista maalista lähtötilaan
     */
    public Tila haku(Pysakki lahto, Pysakki maali) {
        Tila tila = new Tila(lahto, null);
        Queue<Tila> kasiteltavat = new ArrayDeque<>();
        Set<Pysakki> lapiKaydyt = new HashSet<>();
        lapiKaydyt.add(lahto);
        kasiteltavat.add(tila);
        while (!kasiteltavat.isEmpty()) {
            Tila nykyinen = kasiteltavat.poll();
            Pysakki kasiteltava = nykyinen.getPysakki();
            for (Pysakki viereinen : kasiteltava.getNaapurit()) {
                if (lapiKaydyt.contains(viereinen)) {
                    continue;
                }
                lapiKaydyt.add(viereinen);
                Tila uusi = new Tila(viereinen, nykyinen);
                kasiteltavat.add(uusi);
                if (viereinen.equals(maali)) {
                    return uusi;
                }
            }
        }
        return null;
    }
}
