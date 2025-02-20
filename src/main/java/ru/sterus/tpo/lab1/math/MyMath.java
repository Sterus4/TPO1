package ru.sterus.tpo.lab1.math;

public class MyMath {
    private static double mySecCoeff(double x, int c){
        return c * Math.PI / (Math.pow(c * Math.PI / 2, 2) - Math.pow(x, 2));
    }

    public static double mySec(double x, double eps){
        while (x > 2 * Math.PI){
            x -= 2 * Math.PI;
        }
        while (x < -2 * Math.PI){
            x += 2 * Math.PI;
        }
        int coef = 1;
        double result = mySecCoeff(x, coef++) - mySecCoeff(x, ++coef);
        double toAdd;
        double current_eps = Math.abs(mySecCoeff(x, coef));
        while(current_eps > eps / 2){
            coef += 2;
            toAdd = mySecCoeff(x, coef++) - mySecCoeff(x, ++coef);
            current_eps = Math.abs(mySecCoeff(x, coef));
            result += toAdd;
        }
        if (result > 1E10) return Double.POSITIVE_INFINITY;
        if (result < -1E10) return Double.NEGATIVE_INFINITY;
        return result;
    }

    public static double sec(double x){
        double res = 1.0 / Math.cos(x);
        if (res > 1E10) return Double.POSITIVE_INFINITY;
        if (res < -1E10) return Double.NEGATIVE_INFINITY;
        return res;
    }
}
