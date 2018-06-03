package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.view.model.MapModel;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Collection;

import static be.scoutsronse.wafelbak.tech.util.FXUtils.executeOnFXThread;
import static java.lang.Double.MAX_VALUE;
import static java.lang.System.getProperty;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Paths.get;
import static javafx.scene.paint.Color.RED;

public class MapView {

    private com.sothawo.mapjfx.MapView map;
    private MapModel model;

    public MapView(MapModel model) {
        this.model = model;
        this.model.setView(this);

        map = new com.sothawo.mapjfx.MapView();
        map.setCustomMapviewCssURL(getClass().getResource("/css/mapview.css"));
        map.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                map.setZoom(14);
                map.setCenter(new Coordinate(50.748, 3.6078));
                changeBorder(RED, true);
            }
        });

        OfflineCache offlineCache = map.getOfflineCache();
        String cacheDir = getProperty("user.home") + "/Wafelbak/cache";
        try {
            createDirectories(get(cacheDir));
            offlineCache.setCacheDirectory(cacheDir);
            offlineCache.setActive(true);
        } catch (IOException e) {
        }

        executeOnFXThread(() -> map.initialize());
        map.setMaxSize(MAX_VALUE, MAX_VALUE);
        map.setMinSize(750,750);
    }

    public com.sothawo.mapjfx.MapView getMap() {
        return map;
    }

    public void changeBorder(Color color, boolean visible) {
        CoordinateLine borderOfRonse = model.borderOfRonse();
        map.removeCoordinateLine(borderOfRonse);
        borderOfRonse.setColor(color);
        map.addCoordinateLine(borderOfRonse);
        borderOfRonse.setVisible(visible);
    }

    public void deselectStreets(Collection<CoordinateLine> streets) {
        streets.forEach(street -> map.removeCoordinateLine(street));
    }

    public void selectStreets(Collection<CoordinateLine> streets, Color color) {
        streets.forEach(street -> {
            street.setColor(color);
            map.addCoordinateLine(street);
            street.setVisible(true);
        });
    }
}