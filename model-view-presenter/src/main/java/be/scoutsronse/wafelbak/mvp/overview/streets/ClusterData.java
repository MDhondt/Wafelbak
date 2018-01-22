package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;

class ClusterData extends ClusterItem {

    private ClusterId id;
    private Collection<StreetData> streets = new ArrayList<>();

    ClusterData(Cluster cluster) {
        super(cluster.name());
        id = cluster.id();
    }

    ClusterId id() {
        return id;
    }

    void setStreets(Collection<StreetData> streetData) {
        streets.clear();
        streets.addAll(streetData);
    }

    public Collection<StreetData> streets() {
        return newArrayList(streets);
    }
}