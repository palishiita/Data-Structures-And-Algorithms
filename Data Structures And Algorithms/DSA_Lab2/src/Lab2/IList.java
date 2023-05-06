package Lab2;

import java.util.Iterator; // abstract methods: hasNext(), next(), remove()
import java.util.ListIterator; // traverse the list in both direction https://docs.oracle.com/javase/7/docs/api/java/util/ListIterator.html
import java.util.NoSuchElementException; // next throws exception if the iteration has no more elements
import java.lang.UnsupportedOperationException; // // remove() throws UnsupportedOperationException if the remove operation is not supported by this iterator

public interface IList<E> extends Iterable<E> { // iterable class

    Iterator<E> iterator(); //https://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html
    ListIterator<E> listIterator() throws UnsupportedOperationException; // for ListIterator
    boolean add(E e); // add element to the end of list
    void add(int index, E element) throws NoSuchElementException; // add element on position index
    void clear(); // delete all elements
    boolean contains(E element); // is list containing an element (equals())
    E get(int index) throws NoSuchElementException; //get element from position
    E set(int index, E element) throws NoSuchElementException; // set new value on position
    int indexOf(E element); // where is element (equals())
    boolean isEmpty();
    E remove(int index) throws NoSuchElementException; // remove element from position index
    boolean remove(E e); // remove element
    int size();

}

