package be.scoutsronse.wafelbak.tech.thread;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ThreadFactory;

class NamedThreadFactory implements ThreadFactory {

    private String prefix;
    private volatile int counter;

    NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(ExceptionLoggingRunnable.create(r), prefix + " - " + ++counter);
    }

    private static final class ExceptionLoggingRunnable implements Runnable {

        private static final Logger LOGGER = LogManager.getLogger(ExceptionLoggingRunnable.class);

        private Runnable delegate;

        private ExceptionLoggingRunnable(Runnable delegate) {
            this.delegate = delegate;
        }

        public static ExceptionLoggingRunnable create(Runnable delegate) {
            return new ExceptionLoggingRunnable(delegate);
        }

        @Override
        public void run() {
            try {
                delegate.run();
            } catch (RuntimeException | Error e) {
                LOGGER.error("An unexpected exception occured!", e);
                throw e;
            }
        }
    }
}