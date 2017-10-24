package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;
import heap.binary.BinaryElement;

public class BinomialHeap<T extends Comparable<T>> implements Heap {

    private BinomialElement<T> head;

    public BinomialHeap(BinomialElement<T> head){
        this.head = head;
    }

    @Override
    public Element insert(Comparable value) {
        BinomialElement<T> element = new BinomialElement<>(value);
        //TODO plaats in of merge met head
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        if (head == null) {
            return null;
        } else {
            BinomialElement<T> min = head;
            BinomialElement<T> next = head.getSibling();
            while (next != null) {
                if (next.value().compareTo(min.value()) < 0) {
                    min = next;
                }
                next = next.getSibling();
            }
            return min;
        }
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinaryElement<T> min = (BinaryElement<T>) findMin();
        min.remove();
        return min.value();
    }
}
