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
                while (value.compareTo(heap[(vrijePlaats - 1)/2].value()) < 0 && vrijePlaats != 0){
                    setElement(heap[(vrijePlaats - 1) /2], vrijePlaats);
                    vrijePlaats = (vrijePlaats - 1) / 2;
            }
        } else {
            vrijePlaats = ++huidigeGrootte;

        }
        BinaryElement inserted = new BinaryElement(value, this, vrijePlaats);
        setElement(inserted, vrijePlaats);
        return inserted;
    }

    private void verdubbelHoop(){
        BinaryElement[] origineel = heap;
        heap = new BinaryElement[2 * origineel.length];
        for (int i = 0; i < origineel.length; i++){
            // Hier gebruik ik niet setElement omdat de positie in de elementen blijft kloppen, en dit dus overbodige complexiteit zou betekenen
            heap[i] = origineel[i];
        }
    }

    // Aangezien de volgorde/positie zowel in de array als in de elementen wordt opgeslagen leek een functie me de beste optie
    public void setElement(BinaryElement el, int position){
        el.setPosition(position);
        heap[position] = el;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinaryElement kleinste = findMin();
        setElement(heap[huidigeGrootte--], 0);
        percolateDown(0);
        return kleinste.value();
    }

    public void percolateUp(int plaats){
        if(plaats == 0){
            return;
        }
        BinaryElement tmp = heap[plaats];
        int parent = (plaats - 1)/2;
        boolean OK = true;
        while(OK && ( tmp.value().compareTo(heap[parent].value()) < 0 )){
            setElement(heap[parent], plaats);
            plaats = parent;
            if( plaats != 0 ){
                parent = (plaats - 1)/2;
            } else {
                OK = false;
            }
        }
        setElement(tmp, plaats);
    }

    public void percolateDown(int plaats){
        if(2 * plaats + 1 > huidigeGrootte){ return; }
        BinaryElement tmp = heap[plaats];
        int kind = kleinsteKind(plaats);
        boolean OK = true;
        while(OK && ( heap[kind].value().compareTo(tmp.value()) < 0 )){
            setElement(heap[kind], plaats);
            plaats = kind;
            if( 2 * plaats + 1 <= huidigeGrootte){
                kind = kleinsteKind(plaats);
            } else {
                OK = false;
            }
        }
        setElement(tmp, plaats);
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
        // TODO check als het nieuwe veld sneller is dan 2 keer opvragen van positie
        int positie = BinaryElement.getPosition();
        setElement(heap[huidigeGrootte--], positie);
        percolateDown(positie);
    }
}
