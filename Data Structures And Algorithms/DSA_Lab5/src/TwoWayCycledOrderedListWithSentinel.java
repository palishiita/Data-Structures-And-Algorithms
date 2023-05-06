import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

    private class Element{
        // fields
        E object;
        Element next = null;
        Element prev = null;
        // getters and setters
        public void setNext(Element next) {
            this.next = next;
        }
        public Element getNext() {
            return next;
        }
        public Element getPrev() {
            return prev;
        }
        public void setPrev(Element prev) {
            this.prev = prev;
        }
        // constructor1
        public Element(E e) {
            object = e;
        }
        // constructor2
        public Element(E e, Element next, Element prev) {
            object = e;
            this.next = next;
            this.prev = prev;
        }
        // add element elem after "this"
        public void addAfter(Element elem) {
            elem.setNext(this.next);
            elem.setPrev(this);
            this.next.setPrev(elem);
            this.setNext(elem);
        }
        // setNext=this.next
        public void addBefore(Element elem){
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }
        // assert it is NOT a sentinel
        public void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }

    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E>{
        Element current;
        int position;
        public InnerIterator() {
            position = 0;
            current = sentinel.getNext();
        }
        @Override
        public boolean hasNext() {
            return position < size;
        }
        @Override
        public E next() {
            E retElem= current.object;
            current = current.getNext();
            position++;
            return retElem;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element current; // current
        int position;
        public InnerListIterator() {
            position = 0;
            current = sentinel.getNext();
        }
        @Override
        public boolean hasNext() {
            return position < size;
        }
        @Override
        public E next() {
            E retElem = current.object;
            current = current.getNext();
            position++;
            return retElem;
        }
        @Override
        public boolean hasPrevious() {
            return position > 0;
        }
        @Override
        public E previous() {
            current = current.getPrev();
            position--;
            E retElem = current.object;
            return retElem;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
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
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }

    // constructor
    public TwoWayCycledOrderedListWithSentinel() {
        sentinel = new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
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
    public int indexOf(E element) {
        Element current = sentinel.getNext();
        int counter=0;
        while(current!=sentinel && !current.object.equals(element)){
            counter++;
            current=current.getNext();
        }
        if(current==sentinel)
            return -1;
        return counter;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)!=-1;
    }

    private Element getElement(int index) {
        if(index<0 || index>=size) throw new NoSuchElementException(); // index bounds
        Element current = sentinel.getNext();
        int counter = 0;
        while(current!=sentinel && counter<index){
            counter++;
            current=current.getNext();
        }
        if(current == sentinel) throw new IndexOutOfBoundsException();
        return current;
    }
    private Element getElement(E obj) {
        Element current = sentinel.getNext();
        while(current!=sentinel && !obj.equals(current.object)){
            current = current.getNext();
        }
        if(current == sentinel)
            return null;
        return current;

    }

    @Override
    public E get(int index) {
        Element elem = getElement(index);
        return elem.object;
    }

    @Override
    public E remove(int index) {
        if(index<0 || index>=size) throw new NoSuchElementException(); // index bounds
        Element current = getElement(index);
        current.remove();
        size--;
        return current.object;
    }
    @Override
    public boolean remove(E e) {
        Element current = getElement(e);
        if(current == null) return false;
        current.remove();
        size--;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.getNext() == sentinel;
    }

    @Override
    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    // use compareTo methods: elements in sorted order
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        Element current = sentinel.getNext();
        Element newElem = new Element(e);
        while(current!=sentinel &&((Comparable<E>)current.object).compareTo(e) <= 0) {// current BEFORE newElement
            // (e).compareTo((Comparable<E>)current.object) >= 0
            current = current.getNext();
        }
        current.addBefore(newElem); // newElement added before current after while loop is completed
        size++;
        return true;
    }

    /*
    - this list and another list are sorted lists
    - Add another list to this list.
    - After the operation the another list has to be empty, all element will be in this list and ordered.
    - Elements from another list with this same name have to be placed after elements from this list. (compareTo==0)
    - If the another list is the same as this list – do nothing.
    - The complexity of this method have to be O(n).
     */
    // addl <listNo> command in main
    @SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> another) {
        // If the another list is the same as this list – do nothing.
        if(this == another)
            return;
        // After the operation the another list has to be empty, remove another list
        if(another.isEmpty()) {
            another.clear();
            return;
        }
        // all element from another list is added to this list and ordered.
        if(!(this.isEmpty())) { //if this list is not empty

            Element thisPointer = this.sentinel.next;
            Element anotherPointer = another.sentinel.next;

            // complexity O(n) (1 loop)
            while(thisPointer!=this.sentinel && anotherPointer!=another.sentinel) {
                // thisPointer BEFORE otherPointer
                if(((Comparable<E>)thisPointer.object).compareTo(anotherPointer.object)<=0){
                    thisPointer = thisPointer.next; // traversing though thisList
                } else { // otherPointer BEFORE thisPointer
                    anotherPointer = anotherPointer.next; // traversing through anotherList
                    thisPointer.addBefore(anotherPointer.prev);
                }
            }
            size = this.size + another.size;
        } else{
            // if this list is empty transfer elements from another linked list to this empty linked list
            sentinel.next = another.sentinel.next;
            sentinel.prev = another.sentinel.prev;
            size = another.size;
        }
        another.clear();
    }

    // "removeAll(new Link(idStr))" command
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        Element elem = getElement(e);
        if(elem==null)
            return;
        while(elem!=sentinel && ((Comparable)elem.object).compareTo(e)==0) {
            elem.remove();
            elem=elem.next;
            size--;
        }
    }

}