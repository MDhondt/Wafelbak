package be.scoutsronse.wafelbak.mvp;

import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class View<PRESENTER extends Presenter<? extends Model<? extends View<PRESENTER>>, ? extends View<PRESENTER>>> {

    private PRESENTER presenter;
    private MessageSource messageSource;
    private static final Locale locale = new Locale("nl", "BE");

    public View(PRESENTER presenter) {
        this.presenter = presenter;
    }

    protected PRESENTER presenter() {
        return presenter;
    }

    protected String message(String property, Object... args) {
        return messageSource.getMessage(property, args, locale);
    }

    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}