package api;

public class Geo implements GeoLocation{

    private double _x;
    private double _y;
    private double _z;

    public Geo(String s){
        String[] arr;
        arr = s.split(",");
        _x = (Double.parseDouble(arr[0]));
        _y = (Double.parseDouble(arr[1]));
        _z = (Double.parseDouble(arr[2]));
    }

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
    //calculate the distance between 2 Geo's
    @Override
    public double distance(GeoLocation g) {
        double dx = _x - g.x();
        double dy = _y - g.y();
        double dz = _z - g.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }

    public String toString(){
        return "x=" + _x + ", y=" + _y + ", z=" +_z;
    }
}
