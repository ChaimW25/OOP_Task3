package Implementations;

import api.GeoLocation;
import api.NodeData;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Node implements NodeData {

    GeoLocation _pos;
    private int _id;
    private double _weight = 0;
    private String _info = "";
    private int _tag = 0;

    public Node(int id, String pos){
        this._pos = new Geo(pos);
        this._id = id;
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

    public String toString(){
        return "Node: id=" + _id + ", pos=" + _pos.toString() + " + tag:" + _tag;
    }

    static class NodeDataJson implements JsonDeserializer<NodeData>, JsonSerializer<NodeData> {

        @Override
        public NodeData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            int id = jsonElement.getAsJsonObject().get("id").getAsInt();
            String pos = jsonElement.getAsJsonObject().get("pos").getAsString();
//            String [] arr=geoL.split(",");
//            double x=Double.parseDouble(arr[0]);
//            double y=Double.parseDouble(arr[1]);
//            double z=Double.parseDouble(arr[2]);
//            NodeData n=new Node(key,x,y,z);
            NodeData n = new Node(id,pos);
            return n;
        }

        @Override
        public JsonElement serialize(NodeData n, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject nodeJson = new JsonObject();
//            nodeJson.addProperty("pos", n.getLocation().toString());
            nodeJson.addProperty("pos", n.getLocation().toString());
            nodeJson.addProperty("id", n.getKey());
            return nodeJson;
        }
    }
}
