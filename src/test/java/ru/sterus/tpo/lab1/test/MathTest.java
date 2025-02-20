package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.sterus.tpo.lab1.math.MyMath;

import java.util.List;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Secant test")
public class MathTest implements TestLifecycleLogger{

    private static final double DELTA = 0.01d;
    private static final double DEFAULT_EPS = 0.01d;
    private static final double POSITIVE_END = 500.0d;
    private static final double NEGATIVE_END = -500.0d;

    private String errorMessage(double dot, double eps){
        return String.format("Test failed on: dot = %f, eps = %f\n\tActual value: \t%f\n\tYour value: \t%f", dot, eps, MyMath.sec(dot), MyMath.mySec(dot, eps));
    }

    private static Stream<Double> infinityDotsGenerator(){
        return Stream.iterate(-15, a -> a < 17, a -> a + 2).map(a -> a * Math.PI / 2);
    }

    private boolean checkDot(double dot, double eps){
        double myVal =  MyMath.mySec(dot, eps);
        double val =    MyMath.sec(dot);
        if(myVal == val) return true;
        return Math.abs(val - myVal) <= eps;
    }

    @DisplayName("Positive numbers test")
    @Test
    public void testPositiveSec(){
        List<Double> eps = List.of(1.0, 0.5, 0.1, 0.01, 0.001);
        List<Double> dots = Stream.iterate(0d, aDouble -> aDouble < POSITIVE_END, aDouble -> aDouble + DELTA).toList();
        for(Double ep : eps){
            for(Double d : dots){
                Assertions.assertTrue(checkDot(d, ep), () -> errorMessage(d, ep));
            }
        }
    }
    @DisplayName("Negative numbers test")
    @Test
    public void testNegativeSec(){
        List<Double> eps = List.of(1.0, 0.5, 0.1, 0.01, 0.001);
        List<Double> dots = Stream.iterate(0d, aDouble -> aDouble > NEGATIVE_END, aDouble -> aDouble - DELTA).toList();
        for(Double ep : eps){
            for(Double d : dots){
                Assertions.assertTrue(checkDot(d, ep), () -> errorMessage(d, ep));
            }
        }
    }

    @DisplayName("Infinite numbers test")
    @ParameterizedTest
    @MethodSource("infinityDotsGenerator")
    public void testInfinity(double d){
        Assertions.assertTrue(checkDot(d, DEFAULT_EPS), () -> errorMessage(d, DEFAULT_EPS));
    }
}