package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.id.ClusterId;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static be.scoutsronse.wafelbak.domain.id.ClusterId.aClusterId;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Cluster extends AbstractEntity<ClusterId> {

    private String name;
    @OneToMany(fetch = EAGER, cascade = PERSIST)
    private Collection<Street> streets;
    @OneToMany(fetch = EAGER)
    private Collection<ClusterState> states;

    private Cluster() {}

    public Cluster(String name, Collection<Street> streets) {
        this.name = name;
        this.streets = new HashSet<>(streets);
        this.states = new HashSet<>();
        streets.forEach(street -> street.setCluster(this));
    }

    @Override
    public ClusterId id() {
        return aClusterId(id);
    }

    public String name() {
        return name;
    }

    public Collection<Street> streets() {
        return new HashSet<>(streets);
    }

    public Collection<ClusterState> states() {
        return new HashSet<>(states);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                       "name='" + name + '\'' +
                       ", id=" + id +
                       '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster cluster = (Cluster) o;
        return Objects.equals(name, cluster.name) &&
                       Objects.equals(streets, cluster.streets) &&
                       Objects.equals(states, cluster.states);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, streets, states);
    }
}