package be.scoutsronse.wafelbak.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class StreetId implements Serializable {

    private String value;

    private StreetId(String value) {
        this.value = value;
    }

    public static StreetId aStreetId(String value) {
        return new StreetId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetId streetId = (StreetId) o;
        return Objects.equals(value, streetId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}