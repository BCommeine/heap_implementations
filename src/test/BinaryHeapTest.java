package test;

import heap.Heap;
import heap.binary.BinaryHeap;

public class BinaryHeapTest extends HeapTest {
    @Override
    public Heap getHeap() {
        return new BinaryHeap();
    }
}
