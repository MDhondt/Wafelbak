package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.i18n.MessageTag;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static java.util.Optional.ofNullable;

abstract class AbstractView {

    private MessageSource messageSource;
    public static final Locale locale = new Locale("nl", "BE");

    AbstractView() {}

    AbstractView(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String message(MessageTag property, Object... args) {
        return ofNullable(messageSource).map(source -> source.getMessage(property.name(), args, locale))
                                        .orElse("No message source found");
    }
}