import java.util.Iterator;

/**
 * Created by Daniel van den Berg and Eleni...fan ska man komma ihåg det i huvet? on 2014-01-21.
 *Starten är direkt kopierad från föreläsning ett (first,last,add metoden och den inre node klassen.
 */
public class MyALDAList<T> implements ALDAList<T> {
    private Node first;
    private Node last;
    private Node lead;
    private Node end;

    @Override
    public void add(T element) {

    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private static class Node<T>{
        T data;
        Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
