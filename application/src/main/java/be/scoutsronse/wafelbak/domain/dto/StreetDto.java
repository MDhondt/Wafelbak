package be.scoutsronse.wafelbak.domain.dto;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.domain.id.WayId;

import java.util.Collection;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.hash;

public class StreetDto {

    public final StreetId id;
    public final String name;
    public final ClusterId clusterId;
    public final Collection<WayId> wayIds;

    public StreetDto(Street street) {
        this.id = street.id();
        this.name = street.name();
        this.clusterId = street.cluster().id();
        this.wayIds = unmodifiableCollection(street.wayIds());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetDto streetDto = (StreetDto) o;
        return Objects.equals(id, streetDto.id) &&
                Objects.equals(name, streetDto.name) &&
                Objects.equals(clusterId, streetDto.clusterId) &&
                Objects.equals(wayIds, streetDto.wayIds);
    }

    @Override
    public int hashCode() {
        return hash(id, name, clusterId, wayIds);
    }
}