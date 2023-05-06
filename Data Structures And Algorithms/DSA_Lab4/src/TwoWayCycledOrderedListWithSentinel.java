import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

    private class Element{
        E object;
        Element next=null;
        Element prev=null;

        public Element(E e) {
            this.object=e;
            this.next=this;
            this.prev=this;
        }
        public Element(E e, Element next, Element prev) {
            this.object=e;
            this.next=next;
            this.prev=prev;
        }
        // add element e after this
        public void addAfter(Element elem) {
            elem.next=this.next;
            elem.prev=this;
            this.next.prev=elem;
            this.next=elem;
        }
        // assert it is NOT a sentinel
        public void remove() {
            this.next.prev=this.prev;
            this.prev.next=this.next;
        }
    }


    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E>{
        Element p;
        int pos=0;
        public InnerIterator() {
            pos=0;
            p=sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return pos<size;
        }

        @Override
        public E next() {
            E elem=p.object;
            p=p.next;
            pos++;
            return elem;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element p; // current
        int pos=0;
        public InnerListIterator() {
            pos=0;
            p=sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return pos<size;
        }

        @Override
        public E next() {
            E elem=p.object;
            p=p.next;
            pos++;
            return elem;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }
        @Override
        public boolean hasPrevious() {
            return pos>0;
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public E previous() {
            p=p.prev;
            pos--;
            E elem = p.object;
            return elem;
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
        sentinel=new Element(null);
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        Element p=sentinel.next;
        while(p!=sentinel && ((Comparable<E>)p.object).compareTo(e)<=0)
            p=p.next;
        p.prev.addAfter(new Element(e));
        size++;
        return true;
    }

    private Element getElement(int index) {
        checkIndex(index);
        Element p=sentinel.next;
        while(index>0) {
            index--;
            p=p.next;
        }
        return p;
    }

    private Element getElement(E obj) {
        Element p=sentinel.next;
        while(p!=sentinel) {
            if(p.object.equals(obj))
                return p;
            p=p.next;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();

    }

    private void checkIndex(int index) {
        if(index<0 || index>=size) throw new NoSuchElementException();
    }

    @Override
    public void clear() {
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
        size=0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)!=-1;
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {

        Element p=sentinel.next;
        int counter=0;
        counter++;
        while (p!=sentinel) {
            if(p.object.equals(element))
                return counter;
            else {
                counter++;
                p=p.next;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next==sentinel;
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
        Element p=getElement(index);
        E retValue=p.object;
        p.remove();
        size--;
        return retValue;
    }

    @Override
    public boolean remove(E e) {
        Element p=getElement(e);
        if(p==null)
            return false;
        p.remove();
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    public String toStringReverse() {
        InnerListIterator iter = new InnerListIterator();
        int count = 0;
        String retStr="";

        while(iter.hasNext())
            iter.next();

        while(iter.hasPrevious()) {
            retStr+=iter.previous().toString();
            count++;
            if(count==10) {
                retStr+="\n";
                count = 0;
            }
            else
                retStr+=" ";
        }

        return retStr;

    }

    // addl command -> addl <listNo>
    @SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> anotherList) {
        if(this==anotherList)
            return;
        if(anotherList.isEmpty())
            return;
        if(isEmpty()) { //if this is empty
            sentinel.next=anotherList.sentinel.next;
            sentinel.prev=anotherList.sentinel.prev;
            size=anotherList.size;

            anotherList.clear();

            Element p = sentinel;

            while(p.next.object != null) {
                p = p.next;
            }

            p.next = sentinel;

            System.out.println("");
        } else{ //if both are not empty
            Element e1 = sentinel.next;
            Element e2 = anotherList.sentinel.next;

            while(e1!=sentinel && e2!=anotherList.sentinel) {
                if(((Comparable<E>)e1.object).compareTo(e2.object)<=0){
                    e1=e1.next;
                }
                else {
                    e2=e2.next;
                    e1.prev.addAfter(e2.prev);
                }
            }
            while(e2!=anotherList.sentinel) {
                e2=e2.next;
                e1.prev.addAfter(e2.prev);
            }
            size+=anotherList.size;
            anotherList.clear();
        }
    }

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
