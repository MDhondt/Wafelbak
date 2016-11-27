package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.ClusterStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

import static javax.persistence.EnumType.STRING;

@Entity
@Table
public class Cluster extends AbstractEntity {

    @OneToMany(mappedBy = "cluster")
    private Collection<Street> streets;
    @Enumerated(STRING)
    private ClusterStatus status;
    @Column(nullable = false)
    private LocalDate date;
}