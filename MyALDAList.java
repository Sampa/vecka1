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
           if (node != last) {
               str += ", ";
           } else {
               //det är sista så avsluta strängen
               str+="]";
           }
       }
       return str;
    }

    @Override
    public void add(int index, T element) {
        Node create = new Node(element);
        if (index == 0) {
            if(isEmpty()){
                add(element);
            }else{
                create.next = lead.next;
                lead.next = create;
                first = create;
            }
            size++;
            return;
        }
        Node current = getNode(index-1);
        if(current.next==end)
            last = create;
        create.next = current.next;
        current.next = create;

//        System.out.println(current);

        size++;
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
            //todo- se till så att vi inte alltid måste göra specialfall vid index = 0

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
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return size;
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
