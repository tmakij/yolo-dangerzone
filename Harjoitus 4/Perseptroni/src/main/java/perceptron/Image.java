package perceptron;

public class Image implements Comparable<Image> {

    public double[] vec;
    public int characterClass;

    public Image() {
    }

    public int compareTo(Image i1) {
        return this.characterClass - i1.characterClass;
    }
}
