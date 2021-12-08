package Implementations;

import api.EdgeData;
import com.google.gson.*;
import java.lang.reflect.Type;

public class Edge implements EdgeData {
    private int _src;
    private int _dest;
    private double _w;
    private String _info;
    private int _tag;


    public Edge(int src, int dest, double w){
        _src = src;
        _dest = dest;
        _w = w;
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

    public String toString(){
        return "src=" + _src + ", dest=" + _dest + ", weight=" +_w;
    }

    static class EdgeDataJson implements JsonDeserializer<EdgeData>, JsonSerializer<EdgeData>
    {
        @Override
        public EdgeData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            int src = jsonElement.getAsJsonObject().get("src").getAsInt();
            double weight = jsonElement.getAsJsonObject().get("w").getAsDouble();
            int dest = jsonElement.getAsJsonObject().get("dest").getAsInt();
            return new Edge(src,dest,weight);
        }


        @Override
        public JsonElement serialize(EdgeData e, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject edgeJson = new JsonObject();
            edgeJson.addProperty("src", e.getSrc());
            edgeJson.addProperty("w",e.getWeight());
            edgeJson.addProperty("dest",e.getDest());
            return edgeJson;
        }
    }
}
