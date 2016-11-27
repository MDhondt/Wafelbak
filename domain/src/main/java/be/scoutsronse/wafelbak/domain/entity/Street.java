package be.scoutsronse.wafelbak.domain.entity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_UNIQUE", columnNames = {"name", "cluster_id"})})
public class Street extends AbstractEntity {

    @Column(nullable = false)
    private String name;
    @Column
    private Integer numberOfHouses;
    @ManyToOne
    private Cluster cluster;

    public void setName(String name) {
        this.name = name;
    }
}