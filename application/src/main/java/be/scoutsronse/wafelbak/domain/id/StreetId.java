package be.scoutsronse.wafelbak.domain.id;

public class StreetId extends Id {

    private StreetId(Long id) {
        super(id);
    }

    public static StreetId aStreetId(Long id) {
        return new StreetId(id);
    }
}