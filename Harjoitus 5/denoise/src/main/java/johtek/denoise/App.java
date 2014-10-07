package johtek.denoise;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            new Denoise().run();
        } catch (IOException ex) {
        }
    }
}
