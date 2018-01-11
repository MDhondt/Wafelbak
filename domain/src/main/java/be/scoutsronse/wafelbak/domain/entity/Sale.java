package be.scoutsronse.wafelbak.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Sale extends AbstractEntity {

    @Column
    private Integer amount;
    @ManyToMany
    private Set<Street> streets;
    @Column
    private String salesMan;
    @ElementCollection
    private List<String> salesTeam;
    @Column
    private String contact;
    @Column(nullable = false)
    private LocalDateTime start;
    @Column
    private LocalDateTime end;
    @Column
    private Float money;
}