package be.scoutsronse.wafelbak.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Street {

    @Id
    @Column(name = "Name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}