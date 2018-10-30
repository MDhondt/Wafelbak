package be.scoutsronse.wafelbak.domain.dto.dialog;

import be.scoutsronse.wafelbak.domain.id.StreetId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.hash;

public class EndSale {

    private final Integer actualAmountSold;
    private final Float money;
    private final LocalTime endTime;
    private final Collection<StreetId> doneStreets;

    public EndSale(Integer actualAmountSold, Float money, LocalTime endTime, Collection<StreetId> doneStreets) {
        this.actualAmountSold = actualAmountSold;
        this.money = money;
        this.endTime = endTime;
        this.doneStreets = doneStreets;
    }

    public Integer getActualAmountSold() {
        return actualAmountSold;
    }

    public Float getMoney() {
        return money;
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.of(LocalDate.now(), endTime);
    }

    public Collection<StreetId> getDoneStreets() {
        return doneStreets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndSale endSale = (EndSale) o;
        return Objects.equals(actualAmountSold, endSale.actualAmountSold) &&
                Objects.equals(money, endSale.money) &&
                Objects.equals(endTime, endSale.endTime) &&
                Objects.equals(doneStreets, endSale.doneStreets);
    }

    @Override
    public int hashCode() {
        return hash(actualAmountSold, money, endTime, doneStreets);
    }
}