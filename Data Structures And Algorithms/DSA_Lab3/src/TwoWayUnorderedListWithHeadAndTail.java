import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayUnorderedListWithHeadAndTail <E> implements IList<E>{

    private class Element{
        public Element(E e) {
            this.object = e;
            this.next = null;
            this.prev = null;
        }
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        E object;
        Element next;
        Element prev;
    }

    Element head;
    Element tail;
    int size = 0;


    private class InnerIterator implements Iterator<E> {
        Element pos;

        public InnerIterator() {
            this.pos = head;
        }

        @Override
        public boolean hasNext() {
            return this.pos.next != null && this.pos.next != tail;
        }

        @Override
        public E next() {
            this.pos = this.pos.next;
            return pos.object;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element p = head;

        @Override
        public boolean hasNext() {
            return this.p.next != null && this.p.next != tail;
        }

        @Override
        public boolean hasPrevious() {
            return this.p.prev != null && this.p.prev != head;
        }

        @Override
        public E next() {
            if (hasNext()) {
                this.p = this.p.next;
                return this.p.object;
            } else {
                return null;
            }
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                this.p = this.p.prev;
                return this.p.object;
            } else {
                return null;
            }
        }

        @Override
        public void set(E e) {
            this.p.object = e;
        }

        @Override
        public void add(E e) {throw new UnsupportedOperationException();
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void remove() {throw new UnsupportedOperationException();
        }

    }

    public TwoWayUnorderedListWithHeadAndTail() {
        // make a head and a tail
        head = new Element(null, tail, null);
        tail = new Element(null, null, head);
        clear();
    }

    @Override
    public boolean add(E e) {
        InnerListIterator listIterator = new InnerListIterator();
        Element newElement = new Element(e);
        while (listIterator.hasNext()) {
            listIterator.next();
        }
        newElement.prev = listIterator.p;
        newElement.next = tail;
        listIterator.p.next = newElement;
        tail.prev = newElement;
        return true;
    }

    @Override
    public void add(int index, E element) {
        InnerListIterator it = new InnerListIterator();
        Element newEl = new Element(element);
        while (it.hasNext() && index > 0) {
            it.next();
            index--;
        }
        if (it.p == null || index > 0) {
            throw new NoSuchElementException();
        } else {
            newEl.prev = it.p;
            newEl.next = it.p.next;
            it.p.next.prev = newEl;
            it.p.next = newEl;
        }
    }

    @Override
    public void clear() {
        this.head.next = this.tail;
        this.tail.prev = this.head;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        InnerListIterator it = new InnerListIterator();
        while (it.hasNext()) {
            it.next();
            if (it.p.object.equals(element))
                return true;
        }
        return false;
    }

    @Override
    public E get(int index) {
        InnerListIterator it = new InnerListIterator();
        while (it.hasNext() && index >= 0) {
            it.next();
            index--;
        }
        if (it.p == null || index >=  0) throw new NoSuchElementException();
        else
            return it.p.object;
    }

    @Override
    public E set(int index, E element) {
        E value;
        InnerListIterator it = new InnerListIterator();
        Element newElement = new Element(element);
        while (it.hasNext() && index >= 0) {
            it.next();
            index--;
        }
        if (it.p.equals(head) || it.p.equals(tail) || index >= 0) {throw new NoSuchElementException();
        } else {
            value = it.p.object;
            newElement.next = it.p.next;
            newElement.prev = it.p.prev;
            it.p.next.prev = newElement;
            it.p.prev.next = newElement;
        }
        return value;
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        InnerListIterator it = new InnerListIterator();
        while (it.hasNext()) {
            it.next();
            if (it.p.object.equals(element))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == this.tail && this.tail.prev == this.head;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }
    @Override
    public ListIterator<E> listIterator() { //NO
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        E value;
        InnerListIterator listIterator = new InnerListIterator();
        while (listIterator.hasNext() && index >= 0) {
            listIterator.next();
            index--;
        }
        if (listIterator.p == null || index >= 0) {
            throw new NoSuchElementException();
        } else {
            value = listIterator.p.object;
            listIterator.p.prev.next = listIterator.p.next;
            listIterator.p.next.prev = listIterator.p.prev;
        }
        return value;
    }

    @Override
    public boolean remove(E e) {
        InnerListIterator it = new InnerListIterator();
        while (it.hasNext()) {
            it.next();
            if (it.p.object.equals(e)) {
                it.p.prev.next = it.p.next;
                it.p.next.prev = it.p.prev;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        int pos = 0;
        InnerListIterator listIterator = new InnerListIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
            pos++;
        }
        return pos;
    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> otherList) {
        if (!this.equals(otherList)) {
            Element TailOfThisList = this.tail.prev;
            Element HeadOfOtherList = otherList.head.next;

            TailOfThisList.next = HeadOfOtherList;
            HeadOfOtherList.prev = TailOfThisList;

            this.tail.prev = otherList.tail.prev;
            otherList.tail.prev.next = this.tail;

            otherList.clear();
        }
    }

    public String toStringReverse() {
        InnerListIterator listIterator = new InnerListIterator();
        StringBuilder retStr= new StringBuilder();
        while(listIterator.hasNext()){
            listIterator.next();
        }
        retStr.append("\n");
        while (listIterator.hasPrevious()) {
            retStr.append(listIterator.p.object.toString()).append("\n");
            listIterator.previous();
        }
        if (!this.isEmpty())
            retStr.append(listIterator.p.object.toString());
        else
            return "";
        return retStr.toString();
    }

}