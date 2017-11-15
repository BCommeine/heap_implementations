package heap.binary;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

public class BinaryHeap<T extends Comparable<T>> implements Heap {
    private BinaryElement[] heap;
    private int currentSize;
    static final int DEFAULT_CAPACITEIT = 10;

    public BinaryHeap() {
        heap = new BinaryElement[DEFAULT_CAPACITEIT];
        currentSize = -1;
    }

    @Override
    public BinaryElement findMin() throws EmptyHeapException {
        if(currentSize < 0){
            throw new EmptyHeapException();
        }
        return heap[0];
    }

    @Override
    public Element insert(Comparable value) {
        if (currentSize + 1 == heap.length){
            increaseHeap();
        }
        int emptySpace = ++currentSize;
        if(currentSize != 0){
                while (value.compareTo(heap[(emptySpace - 1)/2].value()) < 0 && emptySpace != 0){
                    setElement(heap[(emptySpace - 1) /2], emptySpace);
                    emptySpace = (emptySpace - 1) / 2;
            }
        }
        BinaryElement inserted = new BinaryElement(value, this, emptySpace);
        setElement(inserted, emptySpace);
        return inserted;
    }

    private void increaseHeap(){
        BinaryElement[] original = heap;
        heap = new BinaryElement[2 * original.length];
        for (int i = 0; i < original.length; i++){
            // Hier gebruik ik niet setElement omdat de positie in de elementen blijft kloppen, en dit dus overbodige complexiteit zou betekenen
            heap[i] = original[i];
        }
    }

    // Aangezien de volgorde/positie zowel in de array als in de elementen wordt opgeslagen leek een functie me de beste optie
    public void setElement(BinaryElement el, int position){
        el.setPosition(position);
        heap[position] = el;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinaryElement minimum = findMin();
        setElement(heap[currentSize--], 0);
        percolateDown(0);
        return minimum.value();
    }

    public void percolateUp(int position){
        if(position == 0){ return; }
        BinaryElement tmp = heap[position];
        int parent = (position - 1)/2;
        boolean OK = true;
        while(OK && ( tmp.value().compareTo(heap[parent].value()) < 0 )){
            setElement(heap[parent], position);
            position = parent;
            if( position != 0 ){
                parent = (position - 1)/2;
            } else {
                OK = false;
            }
        }
        setElement(tmp, position);
    }

    public void percolateDown(int position){
        if(2 * position + 1 > currentSize){ return; }
        BinaryElement tmp = heap[position];
        int child = smallestChild(position);
        boolean OK = true;
        while(OK && ( heap[child].value().compareTo(tmp.value()) < 0 )){
            setElement(heap[child], position);
            position = child;
            if( 2 * position + 1 <= currentSize){
                child = smallestChild(position);
            } else {
                OK = false;
            }
        }
        setElement(tmp, position);
    }

    private int smallestChild(int position){
        int child = 2 * position + 1;
        if (child != currentSize){
            if ((heap[child+1]).value().compareTo(heap[child].value()) < 0 ){
                child++;
            }
        }
        return child;
    }

    public void removeElement(BinaryElement<T> BinaryElement) throws EmptyHeapException{
        int position = BinaryElement.getPosition();
        setElement(heap[currentSize--], position);
        percolateDown(position);
    }

    public void print(){
    }
}
