package fi.helsinki.cs.reittiopas.logiikka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Älä muokkaa tätä luokkaa. Sisältää yksittäisen pysäkin tiedot ja tuntee
 * naapurinsa.
 */
public class Pysakki {

    /**
     * Pysäkin yksiselitteinen koodi. ID, jota voit käyttää pysäkin
     * tunnistamiseen.
     */
    private String koodi;

    /**
     * Pysäkin osoite. Jotkin osoitteet sisältävät kadun ja numeron, toiset vain
     * kadun nimen.
     */
    private String osoite;

    /**
     * Pysäkin nimi. Tämä ei välttämättä ole yksiselitteinen.
     */
    private String nimi;

    /**
     * Pysäkin x-koordinaatti.
     */
    private int x;

    /**
     * Pysäkin y-koordinaatti.
     */
    private int y;

    /**
     * Pysäkin naapuripysäkit ja kaikki ko. pysäkille kulkevat linjat (niiden
     * yksikäsitteiset koodit). key: Pysakki.koodi, value: Linja.koodi-taulukko.
     *
     */
    private HashMap<String, String[]> naapurit;

    private Map<Pysakki, Linja[]> naapureihinKulkevatLinjat;

    private Collection<Pysakki> naapuriPysakit;

    private Collection<Linja> linjat;

    public Pysakki() {
        this.koodi = "";
        this.osoite = "";
        this.nimi = "";
        this.x = 0;
        this.y = 0;
        this.naapurit = new HashMap<String, String[]>();
    }

    @Override
    public String toString() {
        return this.koodi + "(" + this.nimi + ")";
    }

    public String getKoodi() {
        return koodi;
    }

    public Collection<String> getNaapuriPysakkiKoodit() {
        return this.naapurit.keySet();
    }

    /**
     * Palauttaa tämän pysäkin naapuripysäkit eli pysäkit, joihin tästä
     * pysäkistä on suora yhteys.
     *
     * @return naapurit
     */
    public Collection<Pysakki> getNaapurit() {
        return this.naapuriPysakit;
    }

    public void lisaaLinja(Linja linja) {
        if (this.linjat == null) {
            this.linjat = new ArrayList<>();
        }
        linjat.add(linja);
    }

    public void setLinjat(Collection<Linja> linjat) {
        this.linjat = linjat;
    }

    public Collection<Linja> getLinjat() {
        return this.linjat;
    }

//    /**
//     * Palauttaa pysäkin naapuritilat.
//     */
//    public Collection<Tila> getNaapuriTilat(int aika) {
//        for (Linja l : this.linjat) {
//            
//        }
//        
//        return null;
//    }
    /**
     * Palauttaa pysäkin x-koordinaatin
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Palauttaa pysäkin y-koordinaatin
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.koodi);
        hash = 97 * hash + Objects.hashCode(this.osoite);
        hash = 97 * hash + Objects.hashCode(this.nimi);
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pysakki other = (Pysakki) obj;
        if (!Objects.equals(this.koodi, other.koodi)) {
            return false;
        }
        if (!Objects.equals(this.osoite, other.osoite)) {
            return false;
        }
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    public void setNaapurit(Collection<Pysakki> pysakit) {
        this.naapuriPysakit = pysakit;
    }

    public Map<String, String[]> getNaapureihinKulkevienLinjojenKoodit() {
        return this.naapurit;
    }

    public void setNaapureihinKulkevatLinjat(Map<Pysakki, Linja[]> linjat) {
        this.naapureihinKulkevatLinjat = linjat;
    }

    public Linja[] getNaapuriinKulkevatLinjat(Pysakki p) {
        return this.naapureihinKulkevatLinjat.get(p);
    }

    public int nopeinSiirtyma(Pysakki naapuri, int aika) {
        
        if (!this.naapuriPysakit.contains(naapuri)) {
            throw new IllegalArgumentException("Yritettiin kutsua nopeinSiirtymä-metodia pysäkille, joka ei ole tämän pysäkin naapuri!");
        }
        
        int odotusAika = 1000;
        int matkaAika = 1000;

        for (Linja l : this.getNaapuriinKulkevatLinjat(naapuri)) {
            for (int i = 0; i < l.getPysakkikoodit().length; i++) {

                if (l.getPysakit()[i].equals(this)) {
                    // Lasketaan odotusaika modulo 10
                    int odotus = (l.getPysahtymisajat()[i] % 10) - (aika % 10);
                    odotus = odotus < 0 ? odotus + 10 : odotus;
                    int matka = l.getPysahtymisajat()[i + 1] - l.getPysahtymisajat()[i];
                    // Jos odotus + matka-aika on pienempi kuin aikaisempi,
                    // vaihdetaan nopeinta matkustuslinjaa
                    if (odotusAika + matkaAika > (odotus + matka)) {
                        odotusAika = odotus;
                        matkaAika = matka;
                    }
                }
            }
        }
        return odotusAika + matkaAika;
    }

}
