package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;
import ru.sterus.tpo.lab1.structures.Entry;
import ru.sterus.tpo.lab1.structures.FibonacciHeap;

import java.util.*;
import java.util.stream.Stream;

@DisplayName("Fibonacci Heap test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeapTest implements TestLifecycleLogger{
    private static final Integer MAX_COUNT_OF_ELEM = 100;
    private static final String EMPTY_HEAP_ERROR = "Fibonacci heap has to be empty";
    private static final String HEAP_SIZE_ERROR = "Fibonacci heap has wrong amount of elements";
    private static final String HEAP_DEGREE_ERROR = "Max degree of any node in fibonacci heap must be log(n) + 1";
    private static final String HEAP_STRUCTURE_ERROR = "Fibonacci heap has not correct structure";

    private final Random random = new Random();
    private FibonacciHeap<Integer> fibonacciHeap;
    private Entry<Integer> fibonacciEntry;
    private Integer countOfElems;

    private String errorOnData(List<Integer> data){
        return String.format("Fibonacci heap property of sorted output not met on data: \n\t%s", data.toString());
    }

    private void debugEntry(Entry<?> entry, int level){
        System.out.print("----".repeat(level) + " ");
        System.out.println(entry.mDegree);
        if(entry.mChild == null) return;
        Entry<?> temp = entry.mChild;
        do{
            debugEntry(temp, level + 1);
            temp = temp.mNext;
        } while (temp != entry.mChild);
    }

    @BeforeEach
    void instantNewHeap(){
        fibonacciHeap = new FibonacciHeap<>();
        countOfElems = random.nextInt(MAX_COUNT_OF_ELEM) + 1;
    }

    @Test
    @DisplayName("Try to extract min from empty Heap")
    void extractMinFromEmptyHeapTest(){
        Assertions.assertNull(fibonacciHeap.dequeueMin());
    }

    @Test
    @DisplayName("Empty Heap test")
    void emptyHeapTest(){
        Assertions.assertTrue(fibonacciHeap.isEmpty(), EMPTY_HEAP_ERROR);
    }

    @RepeatedTest(100)
    @DisplayName("Main Property of Heap (extract min)")
    void mainHeapProperty(){
        Assertions.assertTrue(fibonacciHeap.isEmpty(), EMPTY_HEAP_ERROR);
        List<Integer> data        = Stream.generate(random::nextInt).limit(countOfElems).toList();
        List<Integer> sortedData  = data.stream().sorted().toList();
        data.forEach(a -> fibonacciHeap.enqueue(a, a));
        Assertions.assertEquals(fibonacciHeap.size(), countOfElems, HEAP_SIZE_ERROR);
        sortedData.forEach(a -> Assertions.assertEquals(fibonacciHeap.dequeueMin().getValue(), a, () -> errorOnData(data)));
        Assertions.assertTrue(fibonacciHeap.isEmpty(), EMPTY_HEAP_ERROR);
    }

    private int getMaxDegree(Entry<?> entry){
        if(entry.mDegree == 0) return 0;
        int result = -1;
        Entry<?> tmp = entry.mChild;
        do{
            if(getMaxDegree(tmp) > result) result = getMaxDegree(tmp);
            tmp = tmp.mNext;
        } while (tmp != entry.mChild);
        return Math.max(result, entry.mDegree);
    }

    @RepeatedTest(100)
    @DisplayName("Degree of node test")
    void degreeHeapProperty(){
        Assertions.assertTrue(fibonacciHeap.isEmpty(), EMPTY_HEAP_ERROR);
        List<Integer> data        = Stream.generate(random::nextInt).limit(countOfElems * countOfElems + 2).toList();
        data.forEach(a -> fibonacciHeap.enqueue(a, a));
        fibonacciHeap.dequeueMin();
        Assertions.assertEquals(fibonacciHeap.size(), countOfElems * countOfElems + 1, HEAP_SIZE_ERROR);
        System.out.println(fibonacciHeap.size());
        Entry<Integer> min = fibonacciHeap.min();
        Entry<Integer> tmp = min;
        int result = 0;
        do{
            if(getMaxDegree(tmp) > result) result = getMaxDegree(tmp);
            tmp = tmp.mNext;
        } while (tmp != min);
        System.out.println(result);
        Assertions.assertTrue(result <= Math.log(fibonacciHeap.size())/Math.log(2), HEAP_DEGREE_ERROR);
    }

    private boolean checkLayer(Entry<?> node, int limit){
        Entry<?> tmp = node;
        Set<Integer> degrees = new HashSet<>();
        int maxDegree = -1;
        do {
            degrees.add(tmp.mDegree);
            if(tmp.mDegree > maxDegree) maxDegree = tmp.mDegree;
            tmp = tmp.mNext;
        } while (tmp != node);
        return degrees.size() == limit && maxDegree < limit;
    }

    private boolean checkTree(Entry<?> node){
        if(node.mChild == null) return true;
        Entry<?> tmp = node.mChild;
        if(!checkLayer(tmp, node.mDegree)) return false;
        do {
            if(!checkTree(tmp)) return false;
            tmp = tmp.mNext;
        } while(tmp != node.mChild);
        return true;
    }

    @RepeatedTest(100)
    @DisplayName("Test of correct Fibonacci Heap structure")
    void structureTest(){
        Assertions.assertTrue(fibonacciHeap.isEmpty(), EMPTY_HEAP_ERROR);
        List<Integer> data        = Stream.generate(random::nextInt).limit(countOfElems + 2).toList();
        data.forEach(a -> fibonacciHeap.enqueue(a, a));
        fibonacciHeap.dequeueMin();
        Assertions.assertEquals(fibonacciHeap.size(), countOfElems + 1, HEAP_SIZE_ERROR);

        Entry<Integer> tmp = fibonacciHeap.min();
        int countNodesInFirstLayer = 0;
        Set<Integer> degreesOfFirstLayer = new HashSet<>();
        do {
            countNodesInFirstLayer++;
            degreesOfFirstLayer.add(tmp.mDegree);
            tmp = tmp.mNext;
        } while (tmp != fibonacciHeap.min());
        Assertions.assertEquals(countNodesInFirstLayer, degreesOfFirstLayer.size(), HEAP_STRUCTURE_ERROR);
        tmp = fibonacciHeap.min();
        do {
            Assertions.assertTrue(checkTree(tmp));
            tmp = tmp.mNext;
        } while (tmp != fibonacciHeap.min());
    }

    @BeforeEach
    void entryInstant(){
        fibonacciEntry = new Entry<>(10, 2);
    }

    @Test
    @DisplayName("Check if empty entry in double linked list")
    void emptyEntry(){
        Assertions.assertAll("Единственная пустая созданная запись должна быть единственной в двусвязном циклическом списке",
                () -> Assertions.assertSame(fibonacciEntry.mNext, fibonacciEntry),
                () -> Assertions.assertSame(fibonacciEntry.mPrev, fibonacciEntry));
    }

    @Test
    @DisplayName("No Child test")
    void noChildTest(){
        Assertions.assertNull(fibonacciEntry.mChild);
    }
}
