import tree.Node;
import tree.Relation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 17.05.15.
 */
public class Renderer {

    final static String color_relation = "#e74c3c";
    final static String color_isa = "#3498db";
    final static String color_concept = "#ecf0f1";

    StringBuilder output = new StringBuilder();
    Set<String> uriRendered = new HashSet<>();

    Set<String> rendered = new HashSet<>();

    private final static String is_aLabel = "ist_ein";

    public String render(Node root) {
        output.append("digraph ontology {\n");
        output.append("rankdir=\"LR\"\n");

        renderToStringBuilder(root);

        output.append("}\n");

        return output.toString();
    }

    private void renderToStringBuilder(Node root) {
//        B -> C [ label = "Edge B to C" ];
//        A [label="Node A"];

        if(rendered.contains(root.getUri())) {
            return;
        }

        rendered.add(root.getUri());

        renderNodeLabel(root);

        for(Node child : root.getChildren()) {
            renderSubClass(root, child);
            System.out.println("Rendere isa: " + root.getName() + " - " + child.getName());
            renderToStringBuilder(child);
        }

        for(Relation relation : root.getRelations()) {
            renderRelation(relation);
        }
    }

    private void renderSubClass(Node parent, Node child) {
//        B -> C [ label = "Edge B to C" ];
        output.append(String.format("%s -> %s [ label = \"%s\" color=\"%s\"]\n",
                getId(child), getId(parent), is_aLabel, color_isa));
    }

    private void renderRelation(Relation r) {
//        B -> C [ label = "Edge B to C" ];
        output.append(String.format("%s -> %s [ label = \"%s\" color=\"%s\"]\n",
                getId(r.getDomain()), getId(r.getRange()), r.getName(), color_relation));
    }

    private void renderNodeLabel(Node node) {
        if(!uriRendered.contains(node.getUri())) {
            output.append(String.format("%s [label=\"%s\" style=filled fillcolor=\"%s\"] \n",
                    getId(node), node.getName(), color_concept));
            uriRendered.add(node.getUri());
        }
    }

    private String getId(Node node) {
        int hash = node.getUri().hashCode();
        String id = "node";
        if(hash < 0) {
            //DOT erlaubt keine sonderzeichen (hier: minus, "-") in den ids...
            id += (-hash) + "n";
        } else {
            id += (hash);
        }

        return id;
    }
}
