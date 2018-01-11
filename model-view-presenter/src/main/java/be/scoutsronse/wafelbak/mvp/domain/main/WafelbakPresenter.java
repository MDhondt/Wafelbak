package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class WafelbakPresenter extends Presenter<WafelbakModel, WafelbakView> {

    @Inject
    private StreetRepository streetRepository;
}