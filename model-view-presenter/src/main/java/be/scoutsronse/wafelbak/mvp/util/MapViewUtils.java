package be.scoutsronse.wafelbak.mvp.util;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Double.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

public class MapViewUtils {

    public static Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)).lines()) {
            List<Coordinate> coordinates = lines.map(line -> line.split(";"))
                    .filter(array -> array.length == 2)
                    .map(values -> new Coordinate(valueOf(values[0]), valueOf(values[1])))
                    .collect(toList());
            return of(new CoordinateLine(coordinates));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return empty();
    }
}