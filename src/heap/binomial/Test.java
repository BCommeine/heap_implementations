package heap.binomial;


import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;
import heap.binary.BinaryHeap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Test {
    public static void main(String [] args) throws EmptyHeapException    {
    /*Heap testHeap = createHeap();
        int[] testAddValues = {13, 21, 16, 24, 31, 19, 68, 65, 26, 32, 14};
        for(int i: testAddValues){
            testHeap.insert(i);
        }
        Element updateEl = (Element) testHeap.insert(22);
        updateEl.update(11);
        updateEl.remove();*/


        BinomialHeap testHeap = new BinomialHeap();
        Random rand = new Random();
        ArrayList<Integer> intlist = new ArrayList<Integer>();
        ArrayList<Element> removelist = new ArrayList<Element>();
        for(int i = 0; i<10; i++){
            int randomint1 = rand.nextInt(20) + 1;
            intlist.add(randomint1);
            System.out.println(randomint1);
            Element tmp = (Element) testHeap.insert(randomint1);
            /*int randomint2 = rand.nextInt(20) + 1;
            System.out.println(randomint2);
            Element tmp2 = (Element) testHeap.insert(randomint2);
            removelist.add(tmp2);*/
        }
        System.out.println("added");
        testHeap.print();
       /* for(Element i: removelist){
            i.remove();
        }*/

        Collections.sort(intlist);
        for(int i: intlist){
            if(i != (int) testHeap.removeMin()){
                System.out.println("wrong");
            } else {
                System.out.println("go");
            }
        }
    }
}
