package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.domain.id.WayId;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static be.scoutsronse.wafelbak.domain.id.StreetId.aStreetId;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Street extends AbstractEntity<StreetId> {

    private String name;
    @ElementCollection(fetch = EAGER)
    private Set<WayId> osmWayIds = new HashSet<>();
    @ManyToOne(fetch = EAGER)
    private Cluster cluster;

    private Street() {}

    @Override
    public StreetId id() {
        return aStreetId(id);
    }

    public Set<WayId> wayIds() {
        return new HashSet<>(osmWayIds);
    }

    public String name() {
        return name;
    }

    public Cluster cluster() {
        return cluster;
    }

    @Override
    public String toString() {
        return "Street{" +
                       "name='" + name + '\'' +
                       ", osmWayIds=" + osmWayIds +
                       ", id=" + id +
                       '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(name, street.name) &&
                       Objects.equals(osmWayIds, street.osmWayIds) &&
                       Objects.equals(cluster, street.cluster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, osmWayIds, cluster);
    }
}