package test;

import heap.Heap;
import heap.binomial.BinomialHeap;

public class BinomialHeapTest extends HeapTest {
    @Override
    public Heap getHeap() {
        return new BinomialHeap();
    }
}
