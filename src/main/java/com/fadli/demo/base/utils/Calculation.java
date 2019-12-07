package com.fadli.demo.base.utils;

public class Calculation {

    static Double doubleValue;
    static Integer integerValue;

    public Calculation(Double value) {
        this.doubleValue = value;
    }

    public Calculation(Integer value) {
        this.integerValue = value;
    }

    public static Double add(Double value) {
        return doubleValue + value;
    }

    public static Integer add(Integer value) {
        return integerValue + value;
    }

    public static Double substract(Double value) {
        return doubleValue - value;
    }

    public static Integer substract(Integer value) {
        return integerValue + value;
    }

    public static Double divide(Double value) {
        return doubleValue / value;
    }

    public static Integer divide(Integer value) {
        return integerValue / value;
    }

    public static Double multiple(Double value) {
        return doubleValue * value;
    }

    public static Integer multiple(Integer value) {
        return integerValue * value;
    }
}
