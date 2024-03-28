import java.util.Iterator;

public class MyLinkedList<E> implements
        Iterator<E> {

    int size = 0;
    Node<E> first;
    Node<E> last;


    private static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E item, Node<E> next, Node<E> prev){
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

    }

    public MyLinkedList() {
    }

    public int size(){
        return size;
    }

    public void add(E e){
        Node<E> l = last;
        Node<E> newNode = new Node<>(e, null, l);
        last = newNode;

        if (l == null){
            first = newNode;
        }else{
            l.next = newNode;
        }
        size++;
    }

    public E get(int i){
        rangeCheck(i);

        return node(i).item;
    }

    private void rangeCheck(int i) {
        if (i<0 || i >= size){
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<E> node(int i){
        Node<E> x;

        if(i < (size/2)){
            x = first;
            for(int k=0 ; k<i ; k++){
                x = x.next;
            }
        }else{
            x = last;
            for (int k=size-1 ; k>i; k--){
                x = x.prev;
            }
        }
        return x;
    }

    public E delete(int i){
        rangeCheck(i);
        unLink(node(i));
    }

    private void unLink(Node<E> x){


        x.item = null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }
}
