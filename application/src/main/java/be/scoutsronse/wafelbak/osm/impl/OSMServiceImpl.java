package be.scoutsronse.wafelbak.osm.impl;

import be.scoutsronse.wafelbak.osm.OSMService;
import be.scoutsronse.wafelbak.osm.domain.Node;
import be.scoutsronse.wafelbak.osm.domain.OSM;
import be.scoutsronse.wafelbak.osm.domain.Way;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.operation.overlay.validate.OffsetPointGenerator;
import org.springframework.stereotype.Service;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static be.scoutsronse.wafelbak.osm.impl.OSMParser.parse;
import static java.lang.Double.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
class OSMServiceImpl implements OSMService {

    private final OSM ronse;

    private OSMServiceImpl() {
        ronse = parse(getClass().getResourceAsStream("/ronse.osm"));
    }

    @Override
    public Map<Way, CoordinateLine> getStreetsOfRonse() {
        Map<Way, List<Coordinate>> coordinatesByWay = coordinatesByWay(ronse);

        Path2D borderOfRonse = enlargedBorderOfRonse();
        Map<Way, CoordinateLine> coordinateLinesByWay = ronse.ways()
                                                             .stream()
                                                             .collect(toMap(identity(),
                                                                     way -> new CoordinateLine(coordinatesByWay.get(way)
                                                                                                               .stream()
                                                                                                               .filter(coordinate -> borderOfRonse.contains(new Point2D.Double(coordinate.getLatitude(), coordinate.getLongitude())))
                                                                                                               .collect(toList()))));

        return coordinateLinesByWay.keySet().stream().filter(onlyStreets()).collect(toMap(identity(), coordinateLinesByWay::get));
    }

    @Override
    public CoordinateLine borderOfRonse() {
        return loadCoordinateLine(getClass().getResource("/coordinates/ronseBorder.csv"));
    }

    private Map<Way, List<Coordinate>> coordinatesByWay(OSM ronse) {
        Map<Long, Coordinate> nodeCoordinatesById = ronse.nodes().stream().collect(toMap(Node::id, node -> new Coordinate(node.latitude(), node.longitude())));
        return ronse.ways().stream().collect(toMap(identity(), way -> way.nds().stream().map(nd -> nodeCoordinatesById.get(nd.reference())).collect(toList())));
    }

    private Path2D enlargedBorderOfRonse() {
        List<Coordinate> border = borderOfRonse().getCoordinateStream().collect(toList());

        Polygon ronsePolygon = new GeometryFactory().createPolygon(border.stream()
                                                                         .map(co -> new com.vividsolutions.jts.geom.Coordinate(co.getLatitude(), co.getLongitude()))
                                                                         .collect(toList())
                                                                         .toArray(new com.vividsolutions.jts.geom.Coordinate[border.size()]));
        List<com.vividsolutions.jts.geom.Coordinate> points = new OffsetPointGenerator(ronsePolygon).getPoints(-0.0005);

        Path2D borderPath = new Path2D.Double();
        borderPath.moveTo(points.get(0).x, points.get(0).y);
        for (int i = 2; i < points.size(); i += 2) {
            borderPath.lineTo(points.get(i).x, points.get(i).y);
        }
        borderPath.closePath();

        return borderPath;
    }

    private CoordinateLine loadCoordinateLine(URL url) {
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)).lines()) {
            List<Coordinate> coordinates = lines.map(line -> line.split(";"))
                                                .filter(array -> array.length == 2)
                                                .map(values -> new Coordinate(valueOf(values[0]), valueOf(values[1])))
                                                .collect(toList());
            return new CoordinateLine(coordinates);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Predicate<Way> onlyStreets() {
        return way -> way.tags().keySet().contains("highway") &&
                !way.tags().get("highway").equals("track") &&
                !way.tags().get("highway").equals("cycleway") &&
                !way.tags().get("highway").equals("bridleway") &&
                !way.tags().get("highway").equals("steps") &&
                !way.tags().get("highway").equals("platform") &&
                !way.tags().get("highway").equals("path") &&
                !way.tags().get("highway").equals("service") &&
                !way.tags().get("highway").equals("footway") &&
                !way.tags().get("highway").equals("pedestrian");
    }
}