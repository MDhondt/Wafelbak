package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.id.StreetId;

class StreetData extends ClusterItem {

    private StreetId id;

    public StreetData(String name, StreetId id) {
        super(name);
        this.id = id;
    }
}