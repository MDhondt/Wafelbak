package be.scoutsronse.wafelbak.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "CLUSTER")
public class Cluster extends AbstractEntity {

    @OneToMany(mappedBy = "cluster")
    Collection<Street> streets;
}