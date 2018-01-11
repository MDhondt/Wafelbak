package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.mvp.Presenter;
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

    public void test() {
        System.out.println(streetRepository.findAll());
        System.out.println("testing");
    }
}