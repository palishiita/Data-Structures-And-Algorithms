
public class Link implements Comparable<Link> {

    // fields
    public String ref;
    public int weight;

    // constructor 1 with no weights 1 by default
    public Link(String ref) {
        this.ref = ref;
        weight = 1;
    }

    // constructor 2 with weights
    public Link(String ref, int weight) {
        this.ref = ref;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Link) obj).ref.equalsIgnoreCase(ref);
    }

    @Override
    public String toString() {
        return ref + "(" + weight + ")";
    }

    @Override
    public int compareTo(Link another) {
        return ref.compareTo(another.ref);
    }
}