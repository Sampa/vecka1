import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        MyALDAList<String> list = new MyALDAList<String>();
        try{
            Iterator<String> it = list.iterator();
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
}
