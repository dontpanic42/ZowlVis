package tree;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by daniel on 17.05.15.
 */
public class NodeCache {
    public Map<String, Node> nodes = new HashMap<>();

    public void clear() {
        nodes.clear();
    }

    public Node getOrCreateNode(String uri, String name) {
        if(nodes.containsKey(uri)) {
            return nodes.get(uri);
        }

        System.out.println("Creating node: " + name);
        Node newNode = new Node(uri, name);
        nodes.put(uri, newNode);
        return newNode;
    }

    public boolean exists(String uri) {
        return nodes.containsKey(uri);
    }
}
