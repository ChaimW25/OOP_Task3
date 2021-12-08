import apiImplementations.Geo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoTest {

    Geo geo = new Geo("1.0,2.0,3.0");

    @Test
    void x() {
        assertEquals(geo.x(), 1.0);
    }

    @Test
    void y() {
        assertEquals(geo.y(),2.0);
    }

    @Test
    void z() {
        assertEquals(geo.z(), 3.0);
    }

    @Test
    void distance() {
        Geo geo0 = new Geo("0.0,0.0,0.0");
        Geo geo1 = new Geo("1.0,1.0,1.0");
        Geo geo2 = new Geo("1.0,2.0,3.0");
        Geo geo3 = new Geo("12.0,43.0,78.0");
        assertEquals(0, geo1.distance(geo1));
        assertEquals(Math.sqrt(14), geo0.distance(geo2));
        assertEquals(Math.sqrt(7427), geo2.distance(geo3));
    }

//    @Test
//    void testToString() {
//
//    }
}
