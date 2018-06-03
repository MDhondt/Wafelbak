package be.scoutsronse.wafelbak.mvp;

import be.scoutsronse.wafelbak.i18n.MessageTag;
import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class View<PRESENTER extends Presenter<? extends Model<? extends View<PRESENTER>>, ? extends View<PRESENTER>>> {

    private PRESENTER presenter;
    private MessageSource messageSource;
    public static final Locale locale = new Locale("nl", "BE");

    public View(PRESENTER presenter) {
        this.presenter = presenter;
    }

    protected PRESENTER presenter() {
        return presenter;
    }

    public String message(MessageTag property, Object... args) {
        return messageSource.getMessage(property.name(), args, locale);
    }

    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}