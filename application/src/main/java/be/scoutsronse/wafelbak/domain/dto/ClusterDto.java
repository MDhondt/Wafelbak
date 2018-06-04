package be.scoutsronse.wafelbak.domain.dto;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static java.util.Objects.hash;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class ClusterDto implements Comparable {

    public final ClusterId id;
    public final String name;
    public final Collection<StreetDto> streets;

    public ClusterDto(Cluster cluster) {
        this.id = cluster.id();
        this.name = cluster.name();
        this.streets = cluster.streets().stream().map(StreetDto::new).collect(collectingAndThen(toSet(), Collections::unmodifiableCollection));
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((ClusterDto) o).name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterDto that = (ClusterDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(streets, that.streets);
    }

    @Override
    public int hashCode() {
        return hash(id, name, streets);
    }
}