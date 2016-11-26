package be.scoutsronse.wafelbak.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Street extends AbstractEntity {

    @Column(name = "Name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}