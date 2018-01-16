package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.id.SaleId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static be.scoutsronse.wafelbak.domain.id.SaleId.aSaleId;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Sale extends AbstractEntity<SaleId> {

    private Short amount;
    private String salesMan;
    private String contact;
    private LocalDateTime start;
    private LocalDateTime end;
    private Float money;
    @ManyToMany(fetch = EAGER)
    private Collection<Street> streets;
    @ManyToOne(fetch = EAGER)
    private ClusterState clusterState;
    @ElementCollection(fetch = EAGER)
    private Collection<String> salesTeam;

    private Sale() {}

    @Override
    public SaleId id() {
        return aSaleId(id);
    }

    public Integer amount() {
        return amount.intValue();
    }

    public String salesMan() {
        return salesMan;
    }

    public Collection<String> salesTeam() {
        return new HashSet<>(salesTeam);
    }

    public String contact() {
        return contact;
    }

    public LocalDateTime start() {
        return start;
    }

    public LocalDateTime end() {
        return end;
    }

    public Float money() {
        return money;
    }

    public ClusterState state() {
        return clusterState;
    }

    public Collection<Street> streets() {
        return new HashSet<>(streets);
    }

    @Override
    public String toString() {
        return "Sale{" +
                       "amount=" + amount +
                       ", salesMan='" + salesMan + '\'' +
                       ", contact='" + contact + '\'' +
                       ", start=" + start +
                       ", clusterState=" + clusterState +
                       '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(amount, sale.amount) &&
                       Objects.equals(salesMan, sale.salesMan) &&
                       Objects.equals(contact, sale.contact) &&
                       Objects.equals(start, sale.start) &&
                       Objects.equals(end, sale.end) &&
                       Objects.equals(money, sale.money) &&
                       Objects.equals(streets, sale.streets) &&
                       Objects.equals(clusterState, sale.clusterState) &&
                       Objects.equals(salesTeam, sale.salesTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, salesMan, contact, start, end, money, streets, clusterState, salesTeam);
    }
}