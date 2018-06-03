package be.scoutsronse.wafelbak.domain.id;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WayId implements Serializable {

    private Long id;

    private WayId() {}

    private WayId(Long id) {
        this.id = id;
    }

    public static WayId aWayId(Long id) {
        return new WayId(id);
    }

    public Long value() {
        return id;
    }

    @Override
    public String toString() {
        return "WayId{" + value() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayId wayId = (WayId) o;
        return Objects.equals(id, wayId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}