package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;
import ru.sterus.tpo.lab1.structures.Entry;
import ru.sterus.tpo.lab1.structures.FibonacciHeap;

import java.util.*;
import java.util.stream.Stream;

@DisplayName("Fibonacci Heap test")
public class HeapTest implements TestLifecycleLogger{
    private static final Integer MAX_COUNT_OF_ELEM = 100;
    private static final String EMPTY_HEAP_ERROR = "Fibonacci heap has to be empty";
    private static final String HEAP_SIZE_ERROR = "Fibonacci heap has wrong amount of elements";
    private static final String HEAP_DEGREE_ERROR = "Max degree of any node in fibonacci heap must be log(n) + 1";

    private final Random random = new Random();
    private FibonacciHeap<Integer> fibonacciHeap;
    private Integer countOfElems;

    private String errorOnData(List<Integer> data){
        return String.format("Fibonacci heap property of sorted output not met on data: \n\t%s", data.toString());
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

}
