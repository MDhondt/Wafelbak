package be.scoutsronse.wafelbak.domain.id;

public class UndocumentedSaleId extends Id {

    private UndocumentedSaleId(Long id) {
        super(id);
    }

    public static UndocumentedSaleId anUndocumentedSaleId(Long id) {
        return new UndocumentedSaleId(id);
    }
}