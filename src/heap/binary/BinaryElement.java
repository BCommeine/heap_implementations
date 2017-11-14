package heap.binary;

import heap.Element;
import heap.EmptyHeapException;

public class BinaryElement<T extends Comparable<T>> implements Element {
    private T value;
    private BinaryHeap heap;
    private int position;

    public BinaryElement(T value, BinaryHeap heap, int position){
        this.value = value;
        this.heap = heap;
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public void remove() {
        try {
            heap.removeElement(this);
        } catch (EmptyHeapException e) {
        }
    }

    @Override
    public void update(Comparable value) {

        if(this.value.compareTo((T) value) == 0) {
            return;
        } else if (this.value.compareTo( (T) value) < 0){
            this.value = (T) value;
            this.heap.percolateDown(position);
        } else {
            this.value = (T) value;
            this.heap.percolateUp(position);
        }
    }
}
