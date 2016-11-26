package be.scoutsronse.wafelbak.mvp;

public abstract class View<PRESENTER extends Presenter<? extends Model<? extends View<PRESENTER>>, ? extends View<PRESENTER>>> {

    private PRESENTER presenter;

    public View(PRESENTER presenter) {
        this.presenter = presenter;
    }

    protected PRESENTER presenter() {
        return presenter;
    }
}