package test;

import heap.*;
import heap.leftist.LeftistElement;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class HeapTest {

    public abstract Heap getHeap();

    @Test
    public void makeHeap(){
        Heap heap = getHeap();
        Assert.assertNotEquals(heap, null);
    }

    @Test
    public void fillHeapRemoveMinimum() throws EmptyHeapException {
        //Datastructuren initialiseren:
        Heap heap = getHeap();
        List<Integer> values = new ArrayList();

        //Opvullen
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            int integer = random.nextInt(30) + 1;
            heap.insert(integer);
            values.add(integer);
            heap.print();
        }

        //Overlopen removemin
        Collections.sort(values);
        for(int i: values){
            Assert.assertEquals(i, heap.removeMin());
        }

    }

/*    @Test
    public void removeAndInsertElements() throws EmptyHeapException {
        //Datastructuren initialiseren:
        Heap heap = getHeap();
        List<Element> elements = new ArrayList<>();
        List<Integer> values = new ArrayList();

        //Opvullen
        Random random = new Random();
        for(int i = 0; i < 10000; i++){
            int integer = random.nextInt(50000) + 1;
            heap.insert(integer);
            values.add(integer);
        }

        //Opvullen
        for(int i = 0; i < 10000; i++){
            int integer = random.nextInt(50000) + 1;
            Element element = heap.insert(integer);
            elements.add(element);
        }

        for( Element i: elements){
            i.remove();
        }

        //Opvullen
        for(int i = 0; i < 10000; i++){
            int integer = random.nextInt(50000) + 1;
            heap.insert(integer);
            values.add(integer);
        }

        //Overlopen removemin
        Collections.sort(values);
        for(int i: values){
            Assert.assertEquals(i, heap.removeMin());
        }

    }


    @Test
    public void fillHeapUpdateValues() throws EmptyHeapException {
        //Datastructuren initialiseren:
        Heap heap = getHeap();
        List<Integer> values = new ArrayList();

        //Opvullen
        Random random = new Random();
        for(int i = 0; i < 20; i++){
            //Put random integer in heap
            int integer = random.nextInt(100) + 1;
            Element el = heap.insert(integer);
            // Get new random integer, update element and add to list
            integer = random.nextInt(100) + 1;
            values.add(integer);
            el.update(integer);
        }

        //Overlopen removemin
        Collections.sort(values);
        for(int i: values){
            Assert.assertEquals(i, heap.removeMin());
        }
    }

    @Test
    public void fillHeapRemoveValues() throws EmptyHeapException {
        //Datastructuren initialiseren:
        Heap heap = getHeap();
        List<Integer> values = new ArrayList();
        List<Element<Integer>> verwijderen = new ArrayList();

        //Opvullen
        Random random = new Random();
        for(int i = 0; i < 10000; i++){
            //Put random integer in heap
            int integer = random.nextInt(50000) + 1;
            heap.insert(integer);
            values.add(integer);
            // Maak tweede integer aan om te verwijderen
            integer = random.nextInt(50000) + 1;
            verwijderen.add(heap.insert(integer));
        }

        for( Element i: verwijderen){
            i.remove();
        }
        //Overlopen removemin
        Collections.sort(values);
        for(int i: values){
            Assert.assertEquals(i, heap.removeMin());
        }
    }*/
}
