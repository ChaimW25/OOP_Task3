package api;

public class imEdge implements EdgeData {
    int _src;
    int _dest;
    double _w;
    String _info;
    int _tag;


    public imEdge(){

    }

    @Override
    public int getSrc() {
        return _src;
    }

    @Override
    public int getDest() {
        return _dest;
    }

    @Override
    public double getWeight() {
        return _w;
    }

    @Override
    public String getInfo() {
        return _info;
    }

    @Override
    public void setInfo(String s) {
        _info = s;

    }

    @Override
    public int getTag() {
        return _tag;
    }

    @Override
    public void setTag(int t) {
        _tag = t;
    }


}
