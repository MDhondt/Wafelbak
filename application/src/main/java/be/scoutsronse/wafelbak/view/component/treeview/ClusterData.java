package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.id.ClusterId;

import java.util.Objects;

import static java.util.Objects.hash;

class ClusterData extends ClusterItem {

    private ClusterId id;

    public ClusterData(String name, ClusterId id) {
        super(name);
        this.id = id;
    }

    public ClusterId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterData that = (ClusterData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}