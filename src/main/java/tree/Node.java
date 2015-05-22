package tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by daniel on 17.05.15.
 */
public class Node {
    private String name;
    private String uri;
    private Set<Node> children = new HashSet<>();
    private Set<Relation> relations = new HashSet<>();
    private List<String> attributes = new ArrayList<>();

    public Node(String uri, String name) {
        this.name = name;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public void addChild(Node node) {
        if(!children.contains(node)) {
            System.out.println(name + " ist_ein " + node.getName());
        }
        children.add(node);
    }

    public Set<Relation> getRelations() {
        return relations;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
        System.out.println("Added relation: " + name + " -> " + relation.getName() + " -> " + relation.getRange().getName());
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return uri.equals(node.uri);

    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }
}
