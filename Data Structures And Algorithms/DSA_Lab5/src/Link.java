import java.util.Objects;

public class Link implements Comparable<Link>{

    public String ref;
    public int weight;
    public String getRef() {
        return ref;
    }
    public int getWeight() {
        return weight;
    }

    public Link(String ref) {
        this.ref=ref;
        weight=1;
    }
    public Link(String ref, int weight) {
        this.ref=ref;
        this.weight=weight;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Link))
            return false;
        Link link = (Link) obj;
        return this.weight == link.weight && Objects.equals(ref, link.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref, weight);
    }

    @Override
    public String toString() {
        return ref+"("+weight+")";
    }

    @Override
    public int compareTo(Link another) {
        return ref.compareTo(another.ref);
    }

}

