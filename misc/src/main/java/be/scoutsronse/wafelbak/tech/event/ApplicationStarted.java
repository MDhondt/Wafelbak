package be.scoutsronse.wafelbak.tech.event;

public class ApplicationStarted {

    private static final ApplicationStarted APPLICATION_STARTED = new ApplicationStarted();

    private ApplicationStarted() {
    }

    public static ApplicationStarted applicationStarted() {
        return APPLICATION_STARTED;
    }
}