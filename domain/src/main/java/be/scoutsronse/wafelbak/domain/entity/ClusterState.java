package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.ClusterStatus;
import be.scoutsronse.wafelbak.domain.id.ClusterStateId;

import javax.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static be.scoutsronse.wafelbak.domain.id.ClusterStateId.aClusterStateId;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity
public class ClusterState extends AbstractEntity<ClusterStateId> {

    private Short year;
    @ManyToOne(fetch = EAGER)
    private Cluster cluster;
    @Enumerated(STRING)
    private ClusterStatus status;
    @OneToMany(fetch = EAGER)
    private Collection<Street> streetsDone;
    @OneToMany(fetch = EAGER)
    private Collection<Sale> sales;

    private ClusterState() {}

    @Override
    public ClusterStateId id() {
        return aClusterStateId(id);
    }

    public Cluster cluster() {
        return cluster;
    }

    public ClusterStatus status() {
        return status;
    }

    public Collection<Street> streetsDone() {
        return new HashSet<>(streetsDone);
    }

    public Collection<Sale> sales() {
        return new HashSet<>(sales);
    }

    @Override
    public String toString() {
        return "ClusterState{" +
                       "cluster=" + cluster +
                       ", status=" + status +
                       ", year=" + year +
                       '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterState that = (ClusterState) o;
        return Objects.equals(cluster, that.cluster) &&
                       status == that.status &&
                       Objects.equals(year, that.year) &&
                       Objects.equals(streetsDone, that.streetsDone) &&
                       Objects.equals(sales, that.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cluster, status, year, streetsDone, sales);
    }
}