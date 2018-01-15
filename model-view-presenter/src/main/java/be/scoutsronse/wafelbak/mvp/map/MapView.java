package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.offline.OfflineCache;

import java.io.IOException;

import static be.scoutsronse.wafelbak.tech.util.FXUtils.executeOnFXThread;
import static java.lang.Double.MAX_VALUE;
import static java.lang.System.getProperty;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Paths.get;

public class MapView extends View<MapPresenter> {

    private com.sothawo.mapjfx.MapView map;

    public MapView(MapPresenter presenter) {
        super(presenter);
        createMap();
    }

    public com.sothawo.mapjfx.MapView getMap() {
        return map;
    }

    private void createMap() {
        map = new com.sothawo.mapjfx.MapView();
        map.setCustomMapviewCssURL(getClass().getResource("/css/mapview.css"));
        map.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                map.setZoom(14);
                map.setCenter(new Coordinate(50.749077, 3.599492));
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
}