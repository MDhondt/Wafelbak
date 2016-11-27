package be.scoutsronse.wafelbak.mvp.domain.presenter;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.domain.model.WafelbakModel;
import be.scoutsronse.wafelbak.mvp.domain.view.WafelbakView;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class WafelbakPresenter extends Presenter<WafelbakModel, WafelbakView> {

    @Inject
    private StreetRepository streetRepository;

    public void persist(Street street) {
        streetRepository.save(street);
    }
}