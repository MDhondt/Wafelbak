package be.scoutsronse.wafelbak.domain.entity;

import be.scoutsronse.wafelbak.domain.id.UndocumentedSaleId;

import javax.persistence.Entity;

import static be.scoutsronse.wafelbak.domain.id.UndocumentedSaleId.anUndocumentedSaleId;

@Entity
public class UndocumentedSale extends AbstractEntity<UndocumentedSaleId> {

    private Short year;
    private Short amount;
    private Float money;

    private UndocumentedSale() {}

    public UndocumentedSale(Short year) {
        this.year = year;
        this.amount = 0;
        this.money = 0f;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public UndocumentedSaleId id() {
        return anUndocumentedSaleId(id);
    }
}