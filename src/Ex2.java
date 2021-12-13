import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import apiImplementations.DWGAlgo;
import gui.gui;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithms graphAlgo = new DWGAlgo();
        graphAlgo.load(json_file);
        return graphAlgo.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms graphAlgo = new DWGAlgo();
        graphAlgo.load(json_file);
        return graphAlgo;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms graphAlgo = getGrapgAlgo(json_file);
        gui g = new gui(graphAlgo.getGraph());
        g.setVisible(true);
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\data\\";
        runGUI(path + args[0]);
    }
}

