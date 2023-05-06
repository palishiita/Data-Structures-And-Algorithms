import java.util.Objects;
public class Link implements Comparable<Link>{
    public String ref;
    public int weight;
    // constructor for object: Link=QeW without weight
    public Link(String ref) {
        this.ref = ref;
        weight = 1;
    }
    // constructor for object: link=abc(12) with weight
    public Link(String ref, int weight) {
        this.ref=ref;
        this.weight=weight;
    }
    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Link)) return false;
        Link link = (Link) obj;
        return weight == link.weight && Objects.equals(ref, link.ref);
    }
    // hashcode
    @Override
    public int hashCode() {
        return Objects.hash(ref, weight);
    }
    // link=abc(12) or Link=QeW
    @Override
    public String toString() {
        return ref + "(" + weight + ")";
    }
    // sorted according to first letter in link a, b, c, ...
    // If we want to compare two links, only fields ref will be compared.
    @Override
    public int compareTo(Link another) {
        return ref.compareTo(another.ref);
    }
}
