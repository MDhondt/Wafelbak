package be.scoutsronse.wafelbak.mvp;

import org.springframework.context.MessageSource;

public abstract class View<PRESENTER extends Presenter<? extends Model<? extends View<PRESENTER>>, ? extends View<PRESENTER>>> {

    private PRESENTER presenter;
    private MessageSource messageSource;

    public View(PRESENTER presenter, MessageSource messageSource) {
        this.presenter = presenter;
        this.messageSource = messageSource;
    }

    protected PRESENTER presenter() {
        return presenter;
    }

    protected MessageSource messageSource() {
        return messageSource;
    }
}