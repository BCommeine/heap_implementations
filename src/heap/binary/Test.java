package heap.binary;


import heap.Heap;
import org.junit.*;


public class Test extends heap.Test{

    @Override
    public Heap createHeap() {
        return new BinaryHeap();
    }

}
