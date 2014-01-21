import java.util.Iterator;

/**
 * Created by Daniel van den Berg and Eleni...fan ska man komma ihåg det i huvet? on 2014-01-21.
 *Starten är direkt kopierad från föreläsning ett (first,last,add metoden och den inre node klassen.
 */
public class MyALDAList<T> implements ALDAList<T> {
    private Node first;
    private Node last;

    @Override
    public void add(T data) {
        if(first==null){
            first = new Node<T>(data);
            last = first;
        }else{
            last.next = new Node<T>(data);
            last = last.next;
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }



    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T element) {
        return false;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public boolean contains(T element) {
        return false;
    }

    @Override
    public int indexOf(T element) {
        return 0;
    }

    /**
     * Returns an iterator over a set of elements of type T.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    /*
           copied from lecture one example
        */
    private static class Node<T>{
        T data;
        Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
