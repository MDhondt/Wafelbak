package be.scoutsronse.wafelbak.domain.id;

public class SaleId extends Id {

    private SaleId(Long id) {
        super(id);
    }

    public static SaleId aSaleId(Long id) {
        return new SaleId(id);
    }
}