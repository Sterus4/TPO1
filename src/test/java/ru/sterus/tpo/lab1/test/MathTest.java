package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.sterus.tpo.lab1.math.MyMath;

import java.util.List;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MathTest {
    {
        System.out.println("Math test class is created, starting tests.");
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
        Double end = 500.0d;
        List<Double> dots = Stream.iterate(0d, aDouble -> aDouble < end, aDouble -> aDouble + 0.1d).toList();
        for(Double ep : eps){
            for(Double d : dots){
                Assertions.assertTrue(checkDot(d, ep));
            }
        }
    }
    @DisplayName("Negative numbers test")
    @Test
    public void testNegativeSec(){
        List<Double> eps = List.of(1.0, 0.5, 0.1, 0.01, 0.001);
        Double end = -500.0d;
        List<Double> dots = Stream.iterate(0d, aDouble -> aDouble > end, aDouble -> aDouble - 0.1d).toList();
        for(Double ep : eps){
            for(Double d : dots){
                Assertions.assertTrue(checkDot(d, ep));
            }
        }
    }
    private static Stream<Double> infinityDotsGenerator(){
        return Stream.iterate(-15, a -> a < 17, a -> a + 2).map(a -> a * Math.PI / 2);
    }
    @DisplayName("Infinite numbers test")
    @ParameterizedTest
    @MethodSource("infinityDotsGenerator")
    public void testInfinity(double d){
        double eps = 0.01;
        Assertions.assertTrue(checkDot(d, eps));
    }
}