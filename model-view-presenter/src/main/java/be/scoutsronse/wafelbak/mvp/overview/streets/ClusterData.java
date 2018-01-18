package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class ClusterData {

    private ClusterId id;
    private StringProperty name = new SimpleStringProperty();

    ClusterData(Cluster cluster) {
        id = cluster.id();
        name.setValue(cluster.name());
    }

    ClusterId id() {
        return id;
    }

    StringProperty name() {
        return name;
    }
}