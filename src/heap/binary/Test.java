package heap.binary;

import heap.EmptyHeapException;

public class Test {

    public static void main(String[] args) throws EmptyHeapException {
        BinaryHeap test = new BinaryHeap();
        test.insert(1);
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        test.insert(7);
        test.insert(8);
        test.insert(9);
        test.insert(2);
        test.removeMin();
        /*test.insert(10);
        test.insert(11);
        test.insert(12);
        test.insert(13);
        test.insert(14);
        test.insert(15);*/
    }
}
