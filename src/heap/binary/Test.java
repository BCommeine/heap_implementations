package heap.binary;

import heap.EmptyHeapException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws EmptyHeapException {
        /*BinaryHeap testAdd = new BinaryHeap();
        int[] testAddValues = {13, 21, 16, 24, 31, 19, 68, 65, 26, 32, 14};
        for(int i: testAddValues){
            testAdd.insert(i);
        }
        BinaryElement updateEl = (BinaryElement) testAdd.insert(22);
        System.out.println("fuuuck");
        updateEl.update(11);
        System.out.println("this");
        System.out.println(testAdd);
        updateEl.remove();
        System.out.println("shit");*/

        BinaryHeap testAdd = new BinaryHeap();
        Random rand = new Random();
        ArrayList<Integer> intlist = new ArrayList<Integer>();
        for(int i = 0; i<10000; i++){
            int randomint1 = rand.nextInt(100000) + 1;
            BinaryElement tmp = (BinaryElement) testAdd.insert(randomint1);
            int randomint2 = rand.nextInt(100000) + 1;
            tmp.update(randomint2);
            intlist.add(randomint2);
        }
        Collections.sort(intlist);
        for(int i: intlist){
            if(i != (int) testAdd.removeMin()){
                System.out.println("Wrong");
            }
        }
    }
}
