package be.scoutsronse.wafelbak.entity;

import javax.persistence.*;

@Entity
@Table(name = "STRAAT", uniqueConstraints = {@UniqueConstraint(name = "STRAATNAAM_UNIQUE", columnNames = {"STRAATNAAM"})})
public class Street extends AbstractEntity {

    @Column(name = "STRAATNAAM", nullable = false)
    private String name;
    @Column(name = "AANTAL_HUIZEN")
    private Integer numberOfHouses;
    @ManyToOne
    private Cluster cluster;

    public void setName(String name) {
        this.name = name;
    }
}