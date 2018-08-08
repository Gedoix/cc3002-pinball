import gui.PinballGameApplication;

/**
 * Main runnable class for the pinball app.
 *
 * It's main method calls {@link PinballGameApplication}'s main method, only through this minimalistic launcher.
 *
 * @author Diego Ortego Prieto
 *
 * @see PinballGameApplication
 */
public class Main {

    /**
     * Executes launch() in {@link PinballGameApplication}.
     *
     * @param args  Will be ignored, should be left empty.
     */
    public static void main(String[] args) {
        PinballGameApplication.main(null);
    }

}
