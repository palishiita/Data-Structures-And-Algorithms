package Lab2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E>{

    // inner class Element
    private class Element{
        // constructor Element
        E object;
        public Element(E e) {
            this.object = e;
        }
        Element next = null; // field
        public E getObject() {
            return object;
        }
        public void setObject(E object) {
            this.object = object;
        }
        public Element getNext() {
            return next;
        }
        public void setNext(Element next) {
            this.next = next;
        }
    }
    Element sentinel; // field


    // inner class InnerIterator
    private class InnerIterator implements Iterator<E> {

        Element actElem;
        // constructor of InnerIterator
        public InnerIterator() {
            actElem = sentinel;
        }

        @Override
        public boolean hasNext() {
            return actElem != null;
        }

        @Override
        public E next() {
            E value = actElem.getObject();
            actElem = actElem.getNext();
            return value;
        }
    }

    // constructor of main outer class in inner class
    public OneWayLinkedList() {
        // make a sentinel
        sentinel = new Element(null);
    }

    // abstract methods from IList
    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        // TODO Auto-generated method stub
        Element newElem=new Element(e);
        if(sentinel==null){
            sentinel=newElem;
            return true;
        }
        Element tail = sentinel;
        while(tail.getNext()!=null)
            tail=tail.getNext();
        tail.setNext(newElem);
        return true;
    }

    private Element getElement(int index){
        Element actElem = sentinel;
        while(index>0 && actElem!=null){
            index--;
            actElem=actElem.getNext();
        }
        return actElem;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        // TODO Auto-generated method stub
        Element newElem=new Element(element);
        if(index==0) {
            newElem.setNext(sentinel);
            sentinel=newElem;
        }
        Element actElem=getElement(index-1);
        if(actElem==null)
            return;
        newElem.setNext(actElem.getNext());
        actElem.setNext(newElem);
    }

    @Override
    public void clear() {
        sentinel = null;
    }

    @Override
    public boolean contains(E element) {
        // TODO Auto-generated method stub
        if (element == null)
            return false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E current = iterator.next();
            if(current.equals(element))
                return true;
        }
        return false;
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        // TODO Auto-generated method stub
        Element actElem=getElement(index);
        return actElem==null?null:actElem.getObject();
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        // TODO Auto-generated method stub
        Element actElem=getElement(index);
        if(actElem==null)
            return null;
        E elemData=actElem.getObject();
        actElem.setObject(element);
        return elemData;
    }

    @Override
    public int indexOf(E element) {
        // TODO Auto-generated method stub
        int pos=0;
        Element actElem= sentinel;
        while(actElem!=null) {
            if(actElem.getObject()==element)
                return pos;
            pos++;
            actElem=actElem.getNext();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel == null;
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        // TODO Auto-generated method stub
        if(sentinel ==null)
            return null;
        if(index==0){
            E retValue= sentinel.getObject();
            sentinel = sentinel.getNext();
            return retValue;
        }
        Element actElem=getElement(index-1);
        if(actElem==null || actElem.getNext()==null)
            return null;
        E retValue=actElem.getNext().getObject();
        actElem.setNext(actElem.getNext().getNext());
        return retValue;
    }

    @Override
    public boolean remove(E e) {
        // TODO Auto-generated method stub
        if(sentinel ==null)
            return false;
        if(sentinel.getObject().equals(e)){
            sentinel = sentinel.getNext();
            return true;
        }
        Element actElem= sentinel;
        while(actElem.getNext()!=null && !actElem.getNext().getObject().equals(e))
            actElem=actElem.getNext();
        if(actElem.getNext()==null)
            return false;
        actElem.setNext(actElem.getNext().getNext());
        return true;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        int pos=0;
        Element actElem= sentinel;
        while(actElem!=null) {
            pos++;
            actElem=actElem.getNext();
        }
        return pos;
    }

}

