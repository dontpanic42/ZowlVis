package tree;

/**
 * Created by daniel on 17.05.15.
 */
public class Relation {
    private Node domain;
    private Node range;
    private String name;
    private String uri;

    public Relation(Node domain, Node range, String uri, String name) {
        this.domain = domain;
        this.name = name;
        this.range = range;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Node getDomain() {
        return domain;
    }

    public Node getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;

        Relation relation = (Relation) o;

        if (domain != null ? !domain.equals(relation.domain) : relation.domain != null) return false;
        if (range != null ? !range.equals(relation.range) : relation.range != null) return false;
        if (name != null ? !name.equals(relation.name) : relation.name != null) return false;
        return !(uri != null ? !uri.equals(relation.uri) : relation.uri != null);

    }

    @Override
    public int hashCode() {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (range != null ? range.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
