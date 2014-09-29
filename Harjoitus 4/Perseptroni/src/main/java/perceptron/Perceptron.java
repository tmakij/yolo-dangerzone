package perceptron;

import java.util.List;
import java.util.Random;

/**
 *
 * @author mikko
 */
public class Perceptron {

    private final List<Image> images;

    public Perceptron(List<Image> images) {
        this.images = images;
    }

    /**
     * Täydennä perseptroni-toteutus tähän.
     *
     * @param targetChar
     * @param oppositeChar
     * @param steps
     * @return
     */
    public double[] train(int targetChar, int oppositeChar, int steps) {
        Random rand = new Random();
        double[] w = new double[28 * 28];

        for (int step = 0; step < steps;) {
            final int example = rand.nextInt(6000);

            final Image img = images.get(example);
            final int charClass = img.characterClass;
            if (charClass != targetChar && charClass != oppositeChar) {
                continue;
            }
            step++;

            final double[] vector = img.vec;
            final double z = charClass == targetChar ? 1D : -1D;
            for (int i = 0; i < w.length; i++) {
                w[i] += z * vector[i];
            }
        }
        return w;
    }
}
