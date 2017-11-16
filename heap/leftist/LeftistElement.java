package heap.leftist;

import heap.Element;
import heap.EmptyHeapException;

public class LeftistElement <T extends Comparable<T>> implements Element{
    private int npl;
    private Comparable<T> value;
    private LeftistHeap<T> heap;
    private LeftistElement<T> parent;
    private LeftistElement<T> leftChild;
    private LeftistElement<T> rightChild;

    public LeftistElement(Comparable value, LeftistHeap<T> heap) {
        this.value = value;
        this.heap = heap;
    }


    @Override
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() throws EmptyHeapException {
        heap.remove(this);
    }

    @Override
    public void update(Comparable value) {
        try {
            heap.remove(this);
        } catch (EmptyHeapException ex){
            //TODO Kijk eens naar die exeptions
        }
        heap.insert(this);
    }

    public void setRightChild(LeftistElement<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(LeftistElement<T> parent) {
        this.parent = parent;
    }

    public LeftistElement<T> getRightChild() {
        return rightChild;
    }

    public LeftistElement<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(LeftistElement<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setNpl(int npl) {
        this.npl = npl;
    }

    public LeftistElement<T> getParent() {
        return parent;
    }

    public int getNpl() {
        return npl;
    }

}
