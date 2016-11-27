package be.scoutsronse.wafelbak.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "VERKOOP")
public class Sale extends AbstractEntity {

    @Column(name = "AANTAL")
    private Integer amount;
    @ManyToMany
    private Set<Street> streets;
    @Column(name = "VERKOPER")
    private String salesMan;
    @ElementCollection
    private List<String> salesTeam;
    @Column(name = "CONTACT_INFO")
    private String contact;
    @Column(name = "START", nullable = false)
    private LocalDateTime start;
    @Column(name = "EINDE")
    private LocalDateTime end;
}