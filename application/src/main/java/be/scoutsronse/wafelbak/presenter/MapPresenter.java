package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.model.MapModel;
import be.scoutsronse.wafelbak.osm.OSMService;
import be.scoutsronse.wafelbak.repository.StreetRepository;
import be.scoutsronse.wafelbak.service.WayService;
import be.scoutsronse.wafelbak.view.MapView;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Component
public class MapPresenter {

    @Inject
    private OSMService osmService;
    @Inject
    private StreetRepository streetRepository;
    @Inject
    private WayService wayService;

    private MapView view;
    private MapModel model;

    void init() {
        model = new MapModel(osmService.borderOfRonse());
        view = new MapView(model);
    }

    public com.sothawo.mapjfx.MapView getMap() {
        return view.getMap();
    }

    public void selectStreets(Collection<StreetId> streets, Color color) {
        Collection<CoordinateLine> lines = streetRepository.findByIds(streets)
                                                             .stream()
                                                             .map(Street::wayIds)
                                                             .flatMap(Collection::stream)
                                                             .collect(collectingAndThen(toList(), wayService::findCoordinateLinesBy));
        model.setSelectedStreets(lines, color);
    }

    public void changeBorder(Color color, boolean visible) {
        view.changeBorder(color, visible);
    }
}