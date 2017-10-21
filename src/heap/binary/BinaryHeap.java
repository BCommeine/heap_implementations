package heap.binary;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

public class BinaryHeap<T extends Comparable<T>> implements Heap {
    private BinaryElement[] heap;
    private int huidigeGrootte;
    static final int DEFAULT_CAPACITEIT = 10;

    public BinaryHeap() {
        heap = new BinaryElement[DEFAULT_CAPACITEIT + 1];
        huidigeGrootte = -1;
    }

    public boolean isEmpty(){
        return huidigeGrootte == -1;
    }

    @Override
    public BinaryElement findMin() throws EmptyHeapException {
        return heap[0];
    }

    @Override
    public Element insert(Comparable value) {
        if (huidigeGrootte + 1 == heap.length){
            verdubbelHoop();
        }
        int vrijePlaats;
        if(!isEmpty()){
            vrijePlaats = ++huidigeGrootte;
            while ((value).compareTo(heap[(vrijePlaats - 1)/2].value())<0){
                heap[vrijePlaats] = heap[(vrijePlaats - 1) /2];
                vrijePlaats = (vrijePlaats - 1) / 2;
            }
        } else {
            vrijePlaats = ++huidigeGrootte;

        }
        BinaryElement inserted = new BinaryElement(value, this, vrijePlaats);
        heap[vrijePlaats] = inserted;
        return inserted;
    }

    private void verdubbelHoop(){
        BinaryElement[] origineel = heap;
        heap = new BinaryElement[2 * origineel.length];
        for (int i = 0; i < origineel.length; i++){
            heap[i] = origineel[i];
        }
    }

    public void setElement(BinaryElement el, int position){
        el.setPosition(position);
        heap[position] = el;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinaryElement kleinste = findMin();
        heap[0] = heap[huidigeGrootte--];
        percolateDown(0);
        return kleinste.value();
    }

    private void percolateDown(int plaats){
        if(2 * (plaats + 1) > huidigeGrootte - 1){
            return;
        }
        BinaryElement tmp = heap[plaats];
        int kind = kleinsteKind(plaats);
        boolean OK = true;
        while(OK && ( heap[kind].value().compareTo(tmp.value()) < 0 )){
            heap[plaats] = heap[kind];
            plaats = kind;
            if( 2 * plaats + 1 <= huidigeGrootte){
                kind = kleinsteKind(plaats);
            } else {
                OK = false;
            }
        }
        heap[plaats] = tmp;
    }

    private int kleinsteKind(int plaats){
        int kind = 2 * plaats + 1;
        if (kind != huidigeGrootte){
            if ((heap[kind+1]).value().compareTo(heap[kind].value()) < 0 ){
                kind++;
            }
        }
        return kind;
    }

    public void fixHeap(){
        for( int i = huidigeGrootte/2; i > 0; i--){
            percolateDown(i);
        }
    }

    public void removeElement(BinaryElement<T> BinaryElement) {
        int positie = BinaryElement.getPosition();
        heap[positie] = heap[huidigeGrootte--];
        percolateDown(positie);
    }

/*
    public BinaryElement getParent(BinaryElement el){
        return heap[(el.position - 1)/2];
    }
*/
}
