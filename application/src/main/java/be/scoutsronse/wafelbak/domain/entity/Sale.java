package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.id.SaleId;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

    public Sale(Integer amount, String salesMan, String contact, LocalDateTime start, Collection<Street> streets, ClusterState state, Collection<String> salesTeam) {
        this.amount = amount.shortValue();
        this.salesMan = salesMan;
        this.contact = contact;
        this.start = start;
        this.streets = streets;
        this.clusterState = state;
        this.salesTeam = salesTeam;
    }

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

    public void setAmount(Integer amount) {
        this.amount = amount.shortValue();
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public void setStreets(Collection<Street> streets) {
        this.streets = streets;
    }

    public void setClusterState(ClusterState clusterState) {
        this.clusterState = clusterState;
    }

    public void setSalesTeam(Collection<String> salesTeam) {
        this.salesTeam = salesTeam;
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