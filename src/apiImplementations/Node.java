package apiImplementations;

import api.GeoLocation;
import api.NodeData;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Node implements NodeData {

    private GeoLocation _pos;
    private int _id;
    private double _weight = 0;
    private String _info = "";
    private int _tag = 0;
    private GeoLocation _oldPos;

    public Node(int id, String pos){
        this._pos = new Geo(pos);
        this._id = id;
        this._oldPos = new Geo(pos);
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
            NodeData n = new Node(id,pos);
            return n;
        }

        @Override
        public JsonElement serialize(NodeData n, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject nodeJson = new JsonObject();
            nodeJson.addProperty("pos", n.getLocation().toString());
            nodeJson.addProperty("id", n.getKey());
            return nodeJson;
        }
    }
}
