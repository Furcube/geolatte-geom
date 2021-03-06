package org.geolatte.geom.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.geolatte.geom.*;
import org.geolatte.geom.crs.CoordinateReferenceSystem;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

/**
 * A Jackson databind Module for Geolatte Geometries.
 * <p>
 * Created by Karel Maesen, Geovise BVBA on 08/09/17.
 */
public class GeolatteGeomModule extends SimpleModule {


    private final Features features = new Features();


    public GeolatteGeomModule() {
        this(WGS84);
    }

    @SuppressWarnings("unchecked")
    public <P extends Position> GeolatteGeomModule(CoordinateReferenceSystem<P> defaultCrs) {

        super("GeolatteGeomModule", new Version(1, 0, 0, "", "org.geolatte", "geolatte-json"));

        Context<P> ctxt = new Context<>(defaultCrs, this.features);
        addSerializer(Geometry.class, new GeometrySerializer(ctxt)); //use raw to get this compiled

        addDeserializer(Geometry.class, new GeometryParser<>(ctxt));
        addDeserializer(Point.class, new PointParser<>(ctxt));
        addDeserializer(LineString.class, new LineStringParser<>(ctxt));
        addDeserializer(Polygon.class, new PolygonParser<>(ctxt));
        addDeserializer(MultiPoint.class, new MultiPointParser<>(ctxt));
        addDeserializer(MultiLineString.class, new MultiLineStringParser<>(ctxt));
        addDeserializer(MultiPolygon.class, new MultiPolygonParser<>(ctxt));
        addDeserializer(GeometryCollection.class, new GeometryCollectionParser<>(ctxt));;
    }

    public void setFeature(Feature feature, boolean value) {
        features.override(feature, value);
    }


}
