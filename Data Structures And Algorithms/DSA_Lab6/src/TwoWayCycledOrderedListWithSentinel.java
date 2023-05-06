import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<E>> implements IList<E> {
    private class Element {
        public Element(E e) {
            this.object = e;
            this.next = this;
            this.prev = this;
        }

        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        // add element e after this
        public void addAfter(Element elem) {
            elem.prev = this;
            elem.next = this.next;
            elem.next.prev = elem;
            this.next = elem;
        }

        // assert it is NOT a sentinel
        public void remove() {
            if (this.next != this) {
                this.next.prev = this.prev;
                this.prev.next = this.next;
            }
        }

        E object;
        Element next = null;
        Element prev = null;
    }

    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E> {
        Element pos;
        public InnerIterator() {
            pos = sentinel;
        }
        @Override
        public boolean hasNext() {
            return (pos.next != sentinel);
        }
        @Override
        public E next() {
            if (this.hasNext()) {
                pos = pos.next;
                return pos.object;
            }
            return null;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element pos;
        public InnerListIterator() {
            pos = sentinel;
        }
        @Override
        public boolean hasNext() {
            return (pos.next != sentinel);
        }
        @Override
        public E next() {
            if (this.hasNext()) {
                pos = pos.next;
                return pos.object;
            }
            return null;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }
        @Override
        public boolean hasPrevious() {
            return (pos != sentinel);
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public E previous() {
            if (this.hasPrevious()) {
                pos = pos.prev;
                return pos.next.object;
            }
            return null;
        }
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }

    public TwoWayCycledOrderedListWithSentinel() {
        sentinel = new Element(null);
        size = 0;
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        if (this.isEmpty()) {
            sentinel.addAfter(new Element(e));
        } else {
            Element pos = sentinel;
            while (pos.next != sentinel
                    && e.compareTo(pos.next.object) >= 0) {
                pos = pos.next;
            }
            pos.addAfter(new Element(e));
        }
        size++;
        return true;
    }

    private Element getElement(int index) {
        Element pos = sentinel.next;
        int i = 0;
        while (i < index && pos != sentinel) {
            pos = pos.next;
            i++;
        }
        if (i == index && pos != sentinel) {
            return pos;
        }
        return null;
    }

    private Element getElement(E obj) {
        Element pos = sentinel.next;
        while (pos != sentinel
                && !pos.object.equals(obj)) {
            pos = pos.next;
        }
        if (pos != sentinel
                && pos.object.equals(obj)) {
            return pos;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        Element pos = this.getElement(element);
        return (pos != null);
    }

    @Override
    public E get(int index) {
        Element pos = this.getElement(index);
        if (pos != null) {
            return pos.object;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        Iterator<E> it = this.iterator();
        for (int i = 0; it.hasNext(); i++) {
            if (it.next().equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return (sentinel.next == sentinel);
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) {
        Element pos = this.getElement(index);
        if (pos != null) {
            E value = pos.object;
            pos.remove();
            size--;
            return value;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean remove(E e) {
        Element pos = this.getElement(e);
        if (pos != null) {
            pos.remove();
            size--;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (this == other || other.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            this.sentinel = other.sentinel;
            other.sentinel = new Element(null);
        }
        Iterator<E> it = other.iterator();
        while(it.hasNext()) {
            this.add(it.next());
        }
        other.clear();
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        Element pos = this.getElement(e);
        if (pos == null) {
            return;
        }
        while (pos != sentinel
                && pos.object.equals(e)) {
            pos = pos.next;
            pos.prev.remove();
            size--;
        }
    }

}