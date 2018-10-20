package be.scoutsronse.wafelbak.domain.dto.dialog;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class StartSale implements Serializable {

    private final Integer amount;
    private final String salesMan;
    private final String contact;
    private final List<String> salesTeam;
    private final LocalTime startTime;

    public StartSale(Integer amount, String salesMan, String contact, List<String> salesTeam, LocalTime startTime) {
        this.amount = amount;
        this.salesMan = salesMan;
        this.contact = contact;
        this.salesTeam = salesTeam;
        this.startTime = startTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public String getContact() {
        return contact;
    }

    public List<String> getSalesTeam() {
        return salesTeam;
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.of(LocalDate.now(), startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartSale startSale = (StartSale) o;
        return Objects.equals(amount, startSale.amount) &&
                Objects.equals(salesMan, startSale.salesMan) &&
                Objects.equals(contact, startSale.contact) &&
                Objects.equals(salesTeam, startSale.salesTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, salesMan, contact, salesTeam);
    }
}