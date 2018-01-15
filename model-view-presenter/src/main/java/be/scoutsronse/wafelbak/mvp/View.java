package be.scoutsronse.wafelbak.mvp;

import org.springframework.context.MessageSource;
import be.scoutsronse.wafelbak.mvp.i18n.i18n;

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

    public String message(i18n property, Object... args) {
        return messageSource.getMessage(property.name(), args, locale);
    }

    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}