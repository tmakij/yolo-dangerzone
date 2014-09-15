package fi.helsinki.cs.reittiopas.logiikka;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;


public class Linja {

    private String koodi;
    private String lyhytKoodi;
    private String nimi;
    private int[] x;
    private int[] y;
    private String[] psKoodit;
    private int[] psAjat;
    
    private Pysakki[] pysakit;

    /**
     * Konstruktori, joka luo tyhjän olion.
     */
    public Linja() {
        this.koodi = "";
        this.lyhytKoodi = "";
        this.nimi = "";
        this.x = null;
        this.y = null;
        this.psKoodit = null;
        this.psAjat = null;
    }

    /**
     * Palauttaa lyhyen linjakoodin.
     * @return lyhyt linjakoodi, esim. 4, 3T. Sama molempiin suuntiin.
     */
    public String getLyhytKoodi() {

        return this.lyhytKoodi;
    }

    /**
     * Palauttaa linjan pitkän koodin.
     * @return  linjan yksiselitteinen koodi, joka kertoo myös suunnan.
     * Käytä tätä tunnistaaksesi linja ja sen suunta yksikäsitteisesti. 
     */
    public String getKoodi() {

        return this.koodi;
    }

    /**
     * Palauttaa linjan nimen.
     * @return Linjan nimi, esim. Kaivopuisto - Kallio - Eläintarha.
     */
    public String getNimi() {

        return this.nimi;
    }

    /**
     * Palauttaa linjan pysäkkien koodit.
     * @return linjan pysäkkien yksikäsitteiset koodit.
     */
    public String[] getPysakkikoodit() {

        return this.psKoodit;
    }

    /**
     * Palauttaa linjan pysäkkien x-koordinaatit.
     * 
     * @return linjan kaikkien pysäkkien x-koordinaatit. Järjestys on sama
     * kuin metodin getpsKoodit arvoilla.
     */
    public int[] getXKoordinaatit() {

        return this.x;
    }

    /**
     * Palauttaa linjan pysäkkien y-koordinaatit.
     * 
     * @return linjan kaikkien pysäkkien y-koordinaatit. Järjestys on sama
     * kuin metodin getpsKoodit arvoilla.
     */
    public int[] getYKoordinaatit() {

        return this.y;
    }

    /**
     * Palauttaa linjan pysähtymisajat.
     * 
     * @return ajat lähtöhetkestä laskien, jolloin linja on kullakin pysäkillä.
     * Järjestys on sama kuin metodin getpsKoodit arvoilla.
     */
    public int[] getPysahtymisajat() {

        return this.psAjat;
    }
    
    public void setPysakit(Pysakki[] pysakit) {
        this.pysakit = pysakit;
    }
    
    public Pysakki[] getPysakit() {
        return this.pysakit;
    }
    
}

