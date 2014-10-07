package johtek.denoise;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Kohinanpoistotehtävä python-tehtäväpohjaa mukaillen.
 *
 * @author kumikumi
 */
public class Denoise {

    private final double z = Math.sqrt(0.5);

    public void run() throws IOException {

        final BufferedImage im = ImageIO.read(this.getClass().getResource("/kuva.png"));

        int cols = im.getWidth(null);
        int rows = im.getHeight(null);

        int levels = (int) (Math.log(rows) / Math.log(2)) - 2;

        DataBufferByte imi = ((DataBufferByte) (im.getRaster().getDataBuffer()));

        double[][] imf = new double[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                imf[row][col] = imi.getElem(col * rows + row);
            }
        }

        dwt2d(imf, levels); // forward transform

        final double thres = 15.0D; //set threshold

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                final double c = imf[row][col]; //coefficient

                // add your magic here
                if (c > thres) {
                    imf[row][col] = c - thres;
                } else if (c < -thres) {
                    imf[row][col] = c + thres;
                } else {
                    imf[row][col] = 0D;
                }
            }
        }

        idwt2d(imf, levels); //inverse transform

        //update pixels
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                imi.setElem(col * rows + row, (byte) imf[row][col]);
            }
        }

        //display image
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(im, 0, 0, this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(im.getWidth(), im.getHeight());
            }
        });
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * one pass of the forward transform
     */
    private double[] dwt(double[] vec) {
        double[] ret = new double[vec.length];

        for (int i = 0; i < vec.length / 2; i++) {
            ret[i] = z * (vec[i * 2] + vec[i * 2 + 1]);
            ret[vec.length / 2 + i] = z * (vec[i * 2 + 1] - vec[i * 2]);
        }

        return ret;
    }

    /**
     * one pass of the inverse transform
     */
    private double[] idwt(double[] vec) {
        double[] ret = new double[vec.length];

        for (int i = 0; i < vec.length; i++) {
            ret[i] = z * (vec[i / 2] + ((i & 1) * 2 - 1) * vec[vec.length / 2 + i / 2]);
        }
        return ret;
    }

    /**
     * recursive 2d forward transform, lev interations image size should be more
     * than 2^lev
     */
    private void dwt2d(double[][] arr, int lev) {
        for (int iter = 0; iter < lev; iter++) {
            int rows = arr.length >> iter;
            int cols = arr[0].length >> iter;

            for (int row = 0; row < rows; row++) {
                double[] vec = new double[cols];
                System.arraycopy(arr[row], 0, vec, 0, cols);
                vec = dwt(vec);
                for (int col = 0; col < cols; col++) {
                    arr[row][col] = (int) (vec[col]);
                }
            }

            for (int col = 0; col < cols; col++) {
                double[] vec = new double[rows];
                for (int row = 0; row < rows; row++) {
                    vec[row] = arr[row][col];
                }
                vec = dwt(vec);
                for (int row = 0; row < rows; row++) {
                    arr[row][col] = vec[row];
                }
            }

        }
    }

    /**
     * recursive 2d inverse transform, lev interations image size should be more
     * than 2^lev
     */
    private void idwt2d(double[][] arr, int lev) {
        for (int iter = lev - 1; iter >= 0; iter--) {
            int rows = arr.length >> iter;
            int cols = arr[0].length >> iter;
            for (int col = 0; col < cols; col++) {
                double[] vec = new double[rows];
                for (int row = 0; row < rows; row++) {
                    vec[row] = arr[row][col];
                }
                vec = idwt(vec);
                for (int row = 0; row < rows; row++) {
                    arr[row][col] = vec[row];
                }
            }

            for (int row = 0; row < rows; row++) {
                double[] vec = new double[cols];
                System.arraycopy(arr[row], 0, vec, 0, cols);
                vec = idwt(vec);
                for (int col = 0; col < cols; col++) {
                    arr[row][col] = (int) (vec[col]);
                }
            }

        }
    }
}
