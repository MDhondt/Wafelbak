package be.scoutsronse.wafelbak.mvp;

public abstract class Model<VIEW extends View<? extends Presenter<? extends Model<VIEW>, VIEW>>> {

    private VIEW view;

    public Model(VIEW view) {
        this.view = view;
    }

    protected VIEW view() {
        return view;
    }
}