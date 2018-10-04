package be.scoutsronse.wafelbak.view.component.treeview;

import java.io.Serializable;

abstract class ClusterItem implements Serializable {

    private String name;

    public ClusterItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}