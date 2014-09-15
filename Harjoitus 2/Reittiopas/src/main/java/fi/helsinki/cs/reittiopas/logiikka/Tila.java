package fi.helsinki.cs.reittiopas.logiikka;


public class Tila {

    /**
     * Tila, josta tähän tilaan päädyttiin.
     */
    private final Tila edellinen;

    /**
     * Aika tässä tilassa.
     */
    private final int nykyinenAika;
    /**
     * Pysäkki, jossa tässä tilassa ollaan.
     */
    private final Pysakki pysakki;

    public Tila(Pysakki pysakki, Tila edellinen, int nykyinenAika) {
        this.pysakki = pysakki;
        this.edellinen = edellinen;
        this.nykyinenAika = nykyinenAika;
    }

    public Tila getEdellinen() {
        return edellinen;
    }

    public Pysakki getPysakki() {
        return pysakki;
    }

    @Override
    public String toString() {
        return "[" + aikaMerkkijonona() + "]: " + this.pysakki.toString();
    }

    private String aikaMerkkijonona() {
        if (this.nykyinenAika >= 60) {
            return (this.nykyinenAika - (this.nykyinenAika%60))/60 + "h" + (this.nykyinenAika%60) + "min";
        }

        return this.nykyinenAika + "min";

    }

    public int getNykyinenAika() {
        return nykyinenAika;
    }

}
