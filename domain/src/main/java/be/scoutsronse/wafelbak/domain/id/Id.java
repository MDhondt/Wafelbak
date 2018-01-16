package be.scoutsronse.wafelbak.domain.id;

import java.io.Serializable;
import java.util.Objects;

public abstract class Id implements Serializable {

    private Long id;

    Id(Long id) {
        this.id = id;
    }

    public Long value() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + value() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }
}