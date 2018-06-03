package be.scoutsronse.wafelbak.domain.id;

public class ClusterId extends Id {

    private ClusterId(Long id) {
        super(id);
    }

    public static ClusterId aClusterId(Long id) {
        return new ClusterId(id);
    }
}