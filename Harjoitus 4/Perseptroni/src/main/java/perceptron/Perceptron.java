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
            final int example = rand.nextInt(5000);

            final Image img = images.get(example);
            final int charClass = img.characterClass;
            if (charClass != targetChar && charClass != oppositeChar) {
                continue;
            }
            step++;

            final double[] vector = img.vec;
            double z = charClass == 3 ? 1D : -1D;
            if (z >= 0 && charClass == targetChar) {
                for (int i = 0; i < w.length; i++) {
                    w[i] += vector[i];
                }
            }
            if (z < 0 && charClass == oppositeChar) {
                for (int i = 0; i < w.length; i++) {
                    w[i] -= vector[i];
                }
            }
        }
        return w;
    }
}
