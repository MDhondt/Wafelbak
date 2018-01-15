package be.scoutsronse.wafelbak.tech.util;

import static javafx.application.Platform.isFxApplicationThread;
import static javafx.application.Platform.runLater;

public class FXUtils {

    public static void executeOnFXThread(Runnable runnable) {
        if (isFxApplicationThread()) {
            runnable.run();
        } else {
            runLater(runnable);
        }
    }
}