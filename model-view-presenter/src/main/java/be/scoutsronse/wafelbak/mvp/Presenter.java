package be.scoutsronse.wafelbak.mvp;

import java.lang.reflect.InvocationTargetException;

import static be.scoutsronse.wafelbak.tech.reflection.Reflection.getActualTypeArguments;

public abstract class Presenter<MODEL extends Model<VIEW>, VIEW extends View<? extends Presenter<MODEL, VIEW>>> {

    private MODEL model;
    private VIEW view;

    public Presenter() {
        try {
            view = ((Class<VIEW>) getActualTypeArguments(this.getClass())[1]).getDeclaredConstructor(getClass()).newInstance(this);
            model = ((Class<MODEL>) getActualTypeArguments(this.getClass())[0]).getDeclaredConstructor((Class<VIEW>) getActualTypeArguments(this.getClass())[1]).newInstance(view);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public MODEL model() {
        return model;
    }

    public VIEW view() {
        return view;
    }
}