package be.scoutsronse.wafelbak.mvp.util;

import org.springframework.stereotype.Service;

@Service
public class CurrentSaleService {

    private Integer currentYear;

    public synchronized void setCurrentYear(Integer year) {
        this.currentYear = year;
    }

    public synchronized Integer getCurrentYear() {
        return this.currentYear;
    }
}