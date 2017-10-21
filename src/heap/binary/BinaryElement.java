package heap.binary;

import heap.Element;

public class BinaryElement<T extends Comparable<T>> implements Element {
    private Comparable value;
    private BinaryHeap heap;
    private int position;

    public BinaryElement(Comparable value, BinaryHeap heap, int position){
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
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() {
        heap.removeElement(this);
    }

    @Override
    public void update(Comparable value) {
        this.value = value;
    }
}
