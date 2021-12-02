package api;

public class Geo implements GeoLocation{

    double _x;
    double _y;
    double _z;


    @Override
    public double x() {
        return _x;
    }

    @Override
    public double y() {
        return _y;
    }

    @Override
    public double z() {
        return _z;
    }

    @Override
    public double distance(GeoLocation g) {
        return 0;
    }
}
