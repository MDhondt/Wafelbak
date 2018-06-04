package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.id.StreetId;

import java.util.Objects;

import static java.util.Objects.hash;

class StreetData extends ClusterItem {

    private StreetId id;

    public StreetData(String name, StreetId id) {
        super(name);
        this.id = id;
    }

    public StreetId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetData that = (StreetData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}