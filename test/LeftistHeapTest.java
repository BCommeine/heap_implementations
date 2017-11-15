package test;

import heap.Heap;
import heap.leftist.LeftistHeap;

public class LeftistHeapTest extends HeapTest {
    @Override
    public Heap getHeap() {
        return new LeftistHeap();
    }
}
