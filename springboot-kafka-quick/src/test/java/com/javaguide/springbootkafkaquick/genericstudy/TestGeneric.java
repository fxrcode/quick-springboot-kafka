package com.javaguide.springbootkafkaquick.genericstudy;


import org.junit.jupiter.api.Test;

public class TestGeneric {
    @Test
    private void test1() {
        Plate<Fruit> p = new Plate<>(new Apple());
        int hashCode = p.get().hashCode();
        System.out.println("test p:" + hashCode);
    }
}
