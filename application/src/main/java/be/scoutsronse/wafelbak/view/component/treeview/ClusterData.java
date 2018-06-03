package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.id.ClusterId;

class ClusterData extends ClusterItem {

    private ClusterId id;

    public ClusterData(String name, ClusterId id) {
        super(name);
        this.id = id;
    }
}