package apiImplementations;

import api.*;
import gui.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is the main class for Ex2 - your implementation will be tested
 * using this class.
 */

public class Ex2 {
    // = "data/G1.json";

    public static void main(String[] args) {
        //  /home/bar/Desktop/Ex2_OOP/Ex2_OOP/1000Nodes.json"
        //  D:\\Filipe\\Project Final\\src\\database_lotto.txt
        //String json_file =  "C:\\Users\\USER\\Downloads\\OOP--Ex2-Dvir\\OOP--Ex2-main\\data\\G1.json";
        // String json_file = "/home/Downloads/OOP--Ex2-Dvir/OOP--Ex2-main/G1.json"; //= args[0];
        runGUI("C:\\Users\\USER\\Downloads\\OOP--Ex2-Dvir\\OOP--Ex2-main\\data\\G1.json");
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     **/
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph graph;
        try {
            graph = readGRaphFromJson(json_file);
        } catch (Exception e) {
            e.printStackTrace();
            graph = new DWGraph();
        }
        return graph;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph graph = getGrapg(json_file);
        DirectedWeightedGraphAlgorithms graphAlgo = new DWGAlgo();
        graphAlgo.init(graph);
        return graphAlgo;
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms graphAlgo = getGrapgAlgo(json_file);
        new Frame(graphAlgo);
    }

    public static DWGraph readGRaphFromJson(String json_file)
            throws FileNotFoundException, IOException, ParseException {
        DWGraph ans = new DWGraph();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(json_file));
        JSONObject jobj = (JSONObject) obj;
        JSONArray edges = (JSONArray) jobj.get("Edges");
        JSONArray nodes = (JSONArray) jobj.get("Nodes");
        for (Object o : nodes) {
            JSONObject temp = (JSONObject) o;
            NodeData n = new Node(Integer.parseInt(temp.get("id").toString()), temp.get("pos").toString());
            ans.addNode(n);
        }
        for (Object o : edges) {
            JSONObject temp = (JSONObject) o;
            if ((temp.get("src") != null) && temp.get("dest") != null && temp.get("w") != null) {
                int src = Integer.parseInt(temp.get("src").toString());
                int dest = Integer.parseInt(temp.get("dest").toString());
                double w = Double.parseDouble(temp.get("w").toString());
                ans.connect(src, dest, w);
            }
        }
        return ans;
    }


}