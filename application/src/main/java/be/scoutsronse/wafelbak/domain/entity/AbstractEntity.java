package be.scoutsronse.wafelbak.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
abstract class AbstractEntity<I extends Serializable> implements Serializable {

    @Id
    @GenericGenerator(name = "sequenceGenerator", strategy = "enhanced-sequence", parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    protected Long id;

    public abstract I id();
}