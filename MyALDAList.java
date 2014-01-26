/**
 * Daniel van den Berg (dava4507@dsv.su.se)
 * Eleni Kiriakou (elki8004@dsv.su.se)
 * Den inre node klassen är från föreläsningsbilder
 * Iteratorklassen har vi tittat på i boken för att kunna lösa,och av naturliga skäl blir den väldigt lik
 * Vi upptäckte när vi var klara att ha både first+last och lead+end troligtvis var onödigt för en enkelläktad lista
 * Men vi ville inte riskera att komma på motsatsen halvvägs. Med lite mer tid hade vi valt att försöka skriva om klassen
 * och även utforska möjligheterna för att optimera varje operation tidsmässigt.
 */
//autohandled by IntelliJ
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class MyALDAList<T> implements ALDAList<T> {
    private Node<T> first;
    private Node<T> last;
    final private Node<T> lead = new Node<>(null);
    final private Node<T> end = new Node<>(null);
    private int size;
    private int modCount=0;

    public MyALDAList() {
        //s�tter listans pekare till initiala l�gen, delas med clear();
        setToDefaultMode();
    }
    private void setToDefaultMode(){
        first=  new Node<>(null);
        last =  new Node<>(null);
        lead.next = first;
        last.next = end;
        size = 0;
    }
    private boolean isEmpty(){
        return size == 0;
    }
    private Node<T> getNode(int index){
        //f�r inte vara st�rre �n antalet element, negativ eller om first = v�r lead objekt s� �r listan tom

        if ((index > size-1 && size >1) || index < 0 || isEmpty())
            throw new IndexOutOfBoundsException();
        //om index �r 0 s� skicka direkt f�rsta noden
        if(index ==0 && !isEmpty())
            return first; //early exit
        if(index == size-1)
            return last;
        //iterera tills vi kollat p� INDEX antal element
        Node<T> returnNode = first;
        for(int i=0;i<index;i++){
            if(returnNode.next !=end)
                returnNode = returnNode.next;
        }
        return returnNode;
    }

    @Override
    public T get(int index) {
        //h�mta r�tt nod
        if(!checkIndexOk(index))
            throw new IndexOutOfBoundsException();
        Node<T> returnNode = getNode(index);
        //det �r ju inte noden utan dess data vi vill returnera
        return returnNode.data;
    }

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        //pekarna modifieras olika beroende på om det är en tom lista eller inte
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
        modCount++;
    }
    private boolean checkIndexOk(int index) {
        return index >= 0 && index < size;
    }
    @Override
    public String toString() {
        //om den �r tom returnera
        if(isEmpty())
            return "[]";
        //b�rjan av str�ngen
        String str = "[";
        //alla noderna, tills vi st�ter p� end
        for(Node<T> node = first; node !=end;node=node.next ) {
            T data = node.data;
            //addera elementets tostring till v�r str
            str +=  data.toString();
            //om det inte �r sista addera bara ett komma och space
            if (node != last)
                str += ", ";

        }
        str += "]";
        return str;
    }

    @Override
    public void add(int index, T element) {
        //Ny nod
        Node<T> create = new Node<>(element);
        //fall med index ==0
        if (index == 0) {
            if(isEmpty()){
                //om det �r tomt g�r vi vanlig add
                add(element);
                //om vi returnerar här slipper vi garanterat dubbla size++
                return;
            }else{
                //korrigera pekarna eftersom vi lägger till på första platsen
                create.next = lead.next;
                lead.next = create;
                first = create;
            }
        }else{
            //h�mta noden som �r innan det index d�r vi vill s�tta in v�r nod
            Node<T> current = getNode(index-1);
            //om det �r den sista vi h�mtat modifiera last pekaren
            if(current.next==end)
                last = create;
            //vanliga modiferare
            create.next = current.next;
            current.next = create;
        }
        size++;
        modCount++;
    }

    @Override
    public void clear() {
        //ska tillbaka till samma l�ge som n�r man skapar den f�r f�rsta g�ngen
        setToDefaultMode();
        modCount ++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        for (int i = 0; i < size ; i++) {
            T current = get(i);
            if(current.equals(element))
                return true;//hittade elementet s� skippa resten av iterationen
        }
        return false;
    }

    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            T current = get(i);
            if(current.equals(element))
                return i;
        }
        //finns inte men vi m�ste returnera n�got och eftersom alla positiva tal kan vara ett index s�
        return -1;
    }

    @Override
    public T remove(int index) {
        //den  vi ska ta bort
        Node<T> remove = getNode(index);
        //korrigera pekarna om det är första
        if (index == 0) {
            first = remove.next;
            lead.next = first;
        }else{
            //vi behöver hämta den innan
            Node<T> before=getNode(index-1);
            //om vi tar bort sista elementet dirrigera slutpekaren
            if(index== size-1){
                before.next = end;
                last = before;
            }else{
               //fyll tomrummet
                before.next = remove.next;
            }
        }
        size--;
        modCount ++;
        //h�mta det borttagna objektets data
        return remove.data;
    }

    @Override
    public boolean remove(T element) {
        //h�mta index s� vi kan återanvända v�r remove(int) metod
        int index = indexOf(element);
        if(index < 0)
            return false;
        try{
            remove(index);
        }catch (Exception e){
            //allt som kan g� fel,g�r fel, och kan det inte g� fel g�r det fel �nd�
            //dvs n�got ok�nt testfall sket sig
            return false;
        }
        return true;
    }
    //Inre nod klassen kopierad fr�n f�rel�sning
    private static class Node<T>{
        T data;
        Node<T> next;
        private Node(T data) {
            this.data = data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ALDAListIterator();
    }
    private class ALDAListIterator implements Iterator<T>{
        /*
        * Naturligtvis väääldigt "inspirerat" av boken.
        *
        */
        //börja alltid på första
        private Node<T> current=first;
        private int expectedModCount = modCount;
        //false tills next() metoden kontrollerat att ett objekt kan tas bort
        public boolean okToRemove= false;
        /*
            Kollar om listan har ytterligare ett element
         */
        public boolean hasNext(){
            //försöker iterera på en tom lista, go home nervsystem
            if(isEmpty())
                throw new NoSuchElementException();
            /*
                current !=end kommer vara true tills vi nått slutet på listan (noden end)
            */
            return current!=end;
        }

        public T next(){
            /*
                Kontrollerar att vi inte försöker modifiera/iterera samma lista från fler än en tråd samtidigt
            */
            if(modCount!= expectedModCount)
                throw new ConcurrentModificationException();
            /*
                För att vi inte ska försöka leta reda på data som inte finns
             */
            if(!hasNext())
                throw new NoSuchElementException();
            /*
              Nu kan vi gå till nästa nod, och eftersom felscenariona ovan inte inträffade
              är det även ok att ta bort ett element
             */
            T nextData =  current.data;
            current= current.next;
            okToRemove= true;
            return nextData;
        }

        public void remove(){
             /*
                Kontrollerar att vi inte försöker modifiera/iterera samma lista från fler än en tråd samtidigt
            */
            if(modCount!= expectedModCount)
                throw new ConcurrentModificationException();
             /*
              Om inte next() körts lyckosamt
            */
            if (!okToRemove)
                throw new IllegalStateException();

            //hämta indexet av elementet i listan
            int currentIndex= indexOf(current.data);
            if (currentIndex < 0){
                MyALDAList.this.remove(last.data);
            }else{
                MyALDAList.this.remove(currentIndex-1);
            }
            //öka antalet gånger tråden som itererar på listan körts
            expectedModCount++;
            //måste återställas nu när vi tagit bort ett element ifall remove skulle köras senare igen
            okToRemove=false;
        }
    }
}

