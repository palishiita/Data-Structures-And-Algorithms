import java.util.Objects;

public class Link{
    public String ref;
    public Link(String ref) {
        this.ref = ref;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Link)) return false;
        Link link = (Link) obj;
        return Objects.equals(ref, link.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }

    @Override
    public String toString() {
        return ref;
    }
}
