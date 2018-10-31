package be.scoutsronse.wafelbak.domain.dto;

import be.scoutsronse.wafelbak.domain.id.ClusterId;

import java.io.Serializable;
import java.util.Objects;

public class ClusterDetail implements Serializable {

    private final ClusterId clusterId;
    private final String clusterName;
    private final Integer sold;
    private final Float money;

    public ClusterDetail(ClusterId clusterId, String clusterName, Integer sold, Float money) {
        this.clusterId = clusterId;
        this.clusterName = clusterName;
        this.sold = sold;
        this.money = money;
    }

    public ClusterId getClusterId() {
        return clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public Integer getSold() {
        return sold;
    }

    public Float getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterDetail that = (ClusterDetail) o;
        return Objects.equals(clusterId, that.clusterId) &&
                Objects.equals(clusterName, that.clusterName) &&
                Objects.equals(sold, that.sold) &&
                Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterId, clusterName, sold, money);
    }
}