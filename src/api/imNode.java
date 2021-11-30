package api;

public class  imNode implements NodeData {

    GeoLocation _pos;
    int _id;
    double _weight;
    String _info;
    int _tag;

    public imNode(){

    }


    @Override
    public int getKey() {
        return _id;
    }

    @Override
    public GeoLocation getLocation() {
        return _pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        _pos=p;
    }

    @Override
    public double getWeight() {
        return _weight;
    }

    @Override
    public void setWeight(double w) {
            _weight=w;
    }

    @Override
    public String getInfo() {
        return _info;
    }

    @Override
    public void setInfo(String s) {
        _info=s;
    }

    @Override
    public int getTag() {
        return _tag;
    }

    @Override
    public void setTag(int t) {
        _tag=t;
    }
}
