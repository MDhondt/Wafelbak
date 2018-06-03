package be.scoutsronse.wafelbak.service.impl;

import be.scoutsronse.wafelbak.service.OpenedSaleService;
import org.springframework.stereotype.Service;

@Service
class OpenedSaleServiceImpl implements OpenedSaleService {

    private Integer currentYear;

    @Override
    public synchronized void setCurrentYear(Integer year) {
        this.currentYear = year;
    }

    @Override
    public synchronized Integer getCurrentYear() {
        return this.currentYear;
    }
}