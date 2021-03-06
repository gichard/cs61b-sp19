package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private KDTree searchTree;
    private Map<Point, Node> pNodes;
    private MyTrieSet cleanNames;
    private Map<String, String> fullNames;
    private Map<String, List<Node>> nodeSearcher;
    private Map<Long, Node> indexedNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> lNodes = this.getNodes();
        pNodes = new HashMap<>();
        cleanNames = new MyTrieSet();
        fullNames = new HashMap<>();
        nodeSearcher = new HashMap<>();
        indexedNodes = new HashMap<>();

        for (Node n: lNodes
             ) {
            if (neighbors(n.id()).size() > 0) {
                Point newP = new Point(n.lon(), n.lat());
                pNodes.put(newP, n);
            }

            if (n.name() != null) {
                String cName = cleanString(n.name());
                cleanNames.add(cName);
                fullNames.put(cName, n.name());
                List<Node> nodeList = nodeSearcher.get(cName);
                if (nodeList == null) {
                    nodeList = new LinkedList<>();
                }
                nodeList.add(n);
                nodeSearcher.put(cName, nodeList);
            }

            indexedNodes.put(n.id(), n);
        }
        searchTree = new KDTree(new LinkedList<Point>(pNodes.keySet()));
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pNodes.get(searchTree.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> result = cleanNames.keysWithPrefix(cleanString(prefix));
        for (int i = 0; i < result.size(); i++) {
            result.set(i, fullNames.get(result.get(i)));
        }

        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> matchNodes = nodeSearcher.get(cleanString(locationName));
        List<Map<String, Object>> res = new LinkedList<>();
        Map<String, Object> response;
        for (Node n: matchNodes
             ) {
            response = new HashMap<>();
            response.put("lat", n.lat());
            response.put("lon", n.lon());
            response.put("name", n.name());
            response.put("id", n.id());

            res.add(response);
        }
        return res;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * For part IV. retrieve Node by its id*/
    public Node getNode(long id) {
        return indexedNodes.get(id);
    }

    /** returns name of the way from v to w
     * this is the correct way to get the name of ways. many vertex has name null,
     * despite it is a part of a way*/
    public String getEdgeName(long v, long w) {
        String res = null;
        for (WeightedEdge<Long> e: neighbors(v)
             ) {
            if (e.to().equals(w)) {
                res = e.getName();
            }
        }

        return res;
    }
}
