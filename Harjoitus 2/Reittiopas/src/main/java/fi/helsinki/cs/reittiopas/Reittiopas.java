package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Tila;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Reittiopas {

    /**
     * Toteuta A*-haku. Palauta reitti taaksepäin linkitettynä listana
     * Tila-olioita, joista ensimmäinen osoittaa maalipysäkkiin ja jokainen
     * tuntee pysäkin ja tilan, josta kyseiseen tilaan päästiin (viimeisen
     * solmun Pysäkki on lähtöpysäkki ja edellinen tila on null). Pidä myös
     * reitin ajallisesta kestosta kirjaa Tila-olioissa.
     *
     * Voit selvittää pysäkin naapuripysäkit, eli pysäkit joihin pysäkiltä on
     * suora yhteys, kutsumalla pysäkin getNaapurit() -metodia.
     *
     * Voit selvittää pysäkiltä nopeimman siirtymän keston jollekin sen
     * naapuripysäkeistä kutsumalla pysäkin nopeinSiirtyma(Pysakki p, int aika)
     * -metodia, joka ottaa parametrina naapuripysäkin ja tähän saakka kuljetun
     * reitin keston.
     *
     * Alussa luodaan Vertailija-olio (jonka toiminnallisuuden olet toteuttanut)
     * ja annetaan se konstruktorin parametrina PriorityQueue:lle. Nyt
     * PriorityQueue käyttää kirjoittamaasi vertailijaa ja palauttaa tilat
     * A*-haun kannalta järkevässä järjestyksessä.
     *
     * @param lahto Lahtopysakin koodi
     * @param maali Maalipysakin koodi
     * @param aikaAlussa Aika lähtöhetkellä
     * @return Tila-olioista koostuva linkitetty lista maalista lähtötilaan
     */
    public static Tila haku(final Pysakki lahto, final Pysakki maali, final int aikaAlussa) {
        final Vertailija vertailija = new Vertailija(maali);
        final Set<Pysakki> tutkitut = new HashSet<>();
        final Queue<Tila> tutkittavat = new PriorityQueue<>(vertailija);
        tutkittavat.add(new Tila(lahto, null, aikaAlussa));
        while (!tutkittavat.isEmpty()) {
            final Tila nykyinen = tutkittavat.poll();
            final Pysakki nykyinenPysakki = nykyinen.getPysakki();
            for (final Pysakki viereinen : nykyinenPysakki.getNaapurit()) {
                if (tutkitut.contains(viereinen)) {
                    continue;
                }
                final int nykAika = nykyinen.getNykyinenAika();
                final Tila uusi = new Tila(viereinen, nykyinen, nykAika + nykyinenPysakki.nopeinSiirtyma(viereinen, nykAika));
                if (viereinen.equals(maali)) {
                    return uusi;
                }
                tutkittavat.add(uusi);
                tutkitut.add(viereinen);
            }
        }
        return null;
    }
}
