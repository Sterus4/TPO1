package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;
import ru.sterus.tpo.lab1.structures.FibonacciHeap;

import java.util.*;
import java.util.stream.Stream;

@DisplayName("Fibonacci Heap test")
public class HeapTest {
    private static final Integer MAX_COUNT_OF_ELEM = 100;
    private FibonacciHeap<Integer> fibonacciHeap;
    private Integer countOfElems;
    Random random = new Random();

    @BeforeEach
    void instantNewHeap(){
        fibonacciHeap = new FibonacciHeap<>();
        countOfElems = random.nextInt(MAX_COUNT_OF_ELEM) + 1;
    }

    @Test
    @DisplayName("Empty Heap test")
    void emptyHeapTest(){
        Assertions.assertTrue(fibonacciHeap.isEmpty());
    }

    @RepeatedTest(100)
    @DisplayName("Main Property of Heap (extract min)")
    void mainHeapProperty(){
        Assertions.assertTrue(fibonacciHeap.isEmpty());
        List<Integer> data        = Stream.generate(() -> random.nextInt()).limit(countOfElems).toList();
        List<Integer> sortedData  = data.stream().sorted().toList();
        data.forEach(a -> fibonacciHeap.enqueue(a, a));
        Assertions.assertEquals(fibonacciHeap.size(), countOfElems);
        sortedData.forEach(a -> Assertions.assertEquals(fibonacciHeap.dequeueMin().getValue(), a));
        Assertions.assertTrue(fibonacciHeap.isEmpty());
    }
}
