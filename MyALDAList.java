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
    private int size;
    public MyALDAList() {
        //sätter listans pekare till initiala lägen, delas med clear();
        setToDefaultMode();
    }
    private void setToDefaultMode(){
        lead = new Node(null);
        end = new Node(null);
        lead.next = end;
        first = lead;
        last = end;
        size = 0;
    }
    private boolean isEmpty(){
        if(first==lead)
            return true;
        return false;

    }
    private Node getNode(int index){
        //får inte vara större än antalet element, negativ eller om first = vår lead objekt så är listan tom
        if (index > size-1 || index < 0 || isEmpty())
            throw new IndexOutOfBoundsException();
        //om index är 0 så skicka direkt första elementets data
        if(index ==0 && !isEmpty())
            return first; //early exit
        //iterera tills vi kollat på ett index antal element
        Node returnNode = first;
        for(int i=0;i<index;i++){
            returnNode = returnNode.next;
        }
        return returnNode;
    }

    @Override
    public T get(int index) {
        //hämta rätt nod
        if(index > size-1 || index < 0)
            throw new IndexOutOfBoundsException();
        if(index >= 0){
            Node returnNode = getNode(index);
        //det är ju inte noden utan dess data vi vill returnera
            T data = (T) returnNode.data;
            return data;
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T element) {
        Node<T> newNode = new Node(element);
        if(isEmpty()){
            lead.next =newNode;
            newNode.next = end;
            first = newNode;
            last = newNode;
        }else{
            last.next = newNode;
            last = newNode;
            newNode.next = end;
        }
        size++;
    }
    private boolean checkAddIndexBounds(int index) {
        if(index < 0 || index > size+1)
            return false;
        return true;
    }
    @Override
    public String toString() {
       //om den är tom returnera
       if(isEmpty())
           return "[]";
       //början av strängen
       String str = "[";
       //alla noderna, tills vi stöter på end
       for(Node node = first; node !=end;node=node.next ) {
           T data = (T) node.data;
           //addera elementets tostring till vår str
           str +=  data.toString();
           //om det inte är sista addera bara ett komma och space
           if (node != last)
               str += ", ";
           else //det är sista så avsluta strängen
               str += "]";
       }
       return str;
    }

    @Override
    public void add(int index, T element) {
        //Ny nod
        Node create = new Node(element);
        //fall med index ==0
        if (index == 0) {
            if(isEmpty()){
                //om det är tomt gör vi vanlig add
                add(element);
            }else{
                //korrigera pekarna
                create.next = lead.next;
                lead.next = create;
                first = create;
            }
            //obvious
            size++;
            return;
        }
        //hämta noden som är innan det index där vi vill sätta in vår nod
        Node current = getNode(index-1);
        //om det är den sista vi hämtat modifiera last pekaren
        if(current.next==end)
            last = create;
        //vanliga modiferare
        create.next = current.next;
        current.next = create;
        size++;
    }

    @Override
    public void clear() {
       //ska tillbaka till samma läge som när man skapar den för första gången
       setToDefaultMode();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        //-todo den här är next in line
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>();
    }
    private class ListIterator<T> implements Iterator<T>{
        /*
        * Aautogenererade skalmetoder och engelska kommentarer från javadoc via editorn
        * Koden i metodernas body och på svenska är vårt eget
        */

         /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            return null;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         */
        @Override
        public void remove() {

        }
    }
    //Inre nod klassen kopierad från föreläsning
    private static class Node<T>{
        T data;
        Node next;
        private Node(T data) {
            this.data = data;
        }
    }
}
//backupkod vi raderat men kanske upptäcker att vi behöver senare
/*
        if(index > 0 && checkAddIndexBounds(index-1)){
            Node before = getNode(index-1);
            create.next = before.next;
            before.next = create;
        }else{
            throw new IndexOutOfBoundsException();
        }*/
/*
        if(index >=0 && index <= size+1){
            // om index är 0 ska vi ha första ej hämta negativt item

            Node returnedNode = null;
            if(index ==0){
                returnedNode = lead;
            }else{
                returnedNode = getNode(index);
            }
            Node newNode = new Node(element);
            returnedNode.next = newNode;
            newNode.next = returnedNode.next;

            //om vi måste redigera våra first och last pekare

            if(returnedNode == last){
//                returnedNode.next = newNode;
                last = newNode;
            }else if(index == 0){
                lead.next = newNode;
//                newNode.next = first;
                first = newNode;
            }
            size++;
        }*/
