package be.scoutsronse.wafelbak.mvp.overview.streets;

abstract class ClusterItem {

    private String name;

    ClusterItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}