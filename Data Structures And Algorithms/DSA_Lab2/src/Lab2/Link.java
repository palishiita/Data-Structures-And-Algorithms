package Lab2;

import java.util.Objects;

public class Link {
    // in the future there will be more fields
    public String ref;

    public Link(String ref) {
        this.ref = ref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link link)) return false;
        return Objects.equals(ref, link.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }
}
