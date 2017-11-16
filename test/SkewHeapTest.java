package test;

import heap.Heap;
import heap.skew.SkewHeap;

public class SkewHeapTest extends HeapTest {
    @Override
    public Heap getHeap() {
        return new SkewHeap();
    }
}
