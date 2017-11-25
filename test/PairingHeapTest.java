package test;

import heap.Heap;
import heap.pairing.PairingHeap;

public class PairingHeapTest extends HeapTest {
    @Override
    public Heap getHeap() {
        return new PairingHeap();
    }
}
