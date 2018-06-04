package be.scoutsronse.wafelbak.view.component.treeview;

abstract class ClusterItem {

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