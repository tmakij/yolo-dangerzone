package perceptron;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Application {

    /**
     * Toteuta perseptronialgoritmi luokkaan Perceptron. Sen jälkeen voit
     * kokeilla eri numeroparien erottelua muuttamalla main-metodissa muuttujien
     * targetChar ja oppositeChar arvoja.
     *
     * @param args
     */
    public static void main(String[] args) {
        int steps = 100;

        if (args.length > 0) {
            steps = Integer.parseInt(args[0]);
        }

        int targetChar = 3; // tämä on plus-luokka
        int oppositeChar = 5; // tämä on miinus-luokka

        System.out.println("Learning to classify " + targetChar + " vs " + oppositeChar);
        List<Image> images = readImages();
        testInput(images); //tekee testikuvan (test100.bmp) projektin juureen 

        Perceptron perceptron = new Perceptron(images);
        System.out.print("Perceptron learning algorithm, " + steps + " iterations...");
        double[] w = perceptron.train(targetChar, oppositeChar, steps);
        visualizeWeights(w); //tallentaa kuvan (weights.bmp) projektin juureen
        System.out.println(" complete");
        System.out.println("Failure rate: " + 100 * test(w, images, targetChar, oppositeChar) + " %");
    }

    // lukee x- ja y-tiedostot
    static List<Image> readImages() {
        String xfilename = "mnist-x.data";
        String yfilename = "mnist-y.data";
        List<Image> images = new ArrayList<Image>();
        Scanner xscanner = new Scanner(Perceptron.class.getClassLoader().getResourceAsStream(xfilename));
        Scanner yscanner = new Scanner(Perceptron.class.getClassLoader().getResourceAsStream(yfilename));
        while (xscanner.hasNextLine()) {
            Image i = new Image();
            String line = xscanner.nextLine();
            int characterClass = yscanner.nextInt();
            String splitarr[] = line.split(",");
            i.vec = new double[28 * 28];
            int j = 0;
            for (String s : splitarr) {
                if (s.equals("1")) {
                    i.vec[j++] = 1.0;
                } else {
                    i.vec[j++] = -1.0;
                }
            }
            i.characterClass = characterClass;
            images.add(i);
        }
        return images;
    }

    /**
     * Otetaan sata ensimmäistä, järjestetään characterClassin mukaan, ja
     * piirretään iso kuva, josta voi tarkistaa, että samat numeroa esittavat
     * kuvat tulevat peräkkäin.
     *
     * @param images
     */
    static void testInput(List<Image> images) {

        List<Image> i100 = new ArrayList<Image>();
        for (int i = 0; i < 100; ++i) {
            i100.add(images.get(i));
        }

        Collections.sort(i100);

        BufferedImage bi = new BufferedImage(28 * 100, 28,
                BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < 100; ++i) {
            for (int y = 0; y < 28; ++y) {
                for (int x = 0; x < 28; ++x) {
                    int ind = y * 28 + x;
                    bi.setRGB(x + i * 28, y,
                            (i100.get(i).vec[ind] > 0.5
                                    ? 0 : 0xffffff));
                }
            }
        }
        try {
            ImageIO.write(bi, "BMP", new File("test100.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Piirretään kuva, jossa 28 x 28 painovektorin arvot kuvataan
     * harmaasavyiksi.
     *
     * @param w
     */
    static void visualizeWeights(double[] w) {

        BufferedImage bi = new BufferedImage(28, 28,
                BufferedImage.TYPE_3BYTE_BGR);

        for (int y = 0; y < 28; ++y) {
            for (int x = 0; x < 28; ++x) {
                int ind = y * 28 + x;
                float w01 = .01f + .98f / (1.0f + (float) Math.exp(-w[ind]));
                bi.setRGB(x, y, (new Color(w01, w01, w01)).getRGB());
            }
        }
        try {
            ImageIO.write(bi, "BMP", new File("weights.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Testaa opittua perseptronia (painovektorilla w) viimeiseen 1000 x,y
     * -pariin. (laskee vain ne, jotka kuuluvat joko plus- tai miinus-luokkaan)
     *
     * @param w
     * @param targetChar
     * @param oppositeChar
     * @return epäonnistuneitten osuus
     */
    static double test(double[] w, List<Image> images, int targetChar, int oppositeChar) {
        int success = 0;
        int trials = 0;

        for (int example = 5000; example < images.size(); example++) {

            // valitetaan vain + ja - -luokista.
            if (images.get(example).characterClass != targetChar
                    && images.get(example).characterClass != oppositeChar) {
                continue;
            }

            // laske z
            double z = 0.0;
            for (int j = 0; j < 28 * 28; j++) {
                z += images.get(example).vec[j] * w[j];
            }

            // oliko luokitus oikein?
            if ((z >= 0 && images.get(example).characterClass == targetChar)
                    || (z < 0 && images.get(example).characterClass == oppositeChar)) {
                success++;
            }
            trials++;
        }

        return (double) (trials - success) / trials;
    }

};
