package com.zhengqing.demo;

public class DoubleTest {
    public static void main(String[] args) {
        System.out.println(0.1 + 0.2);
        System.out.println(1.0 - 0.8);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);

        double amount1 = 3.15;
        double amount2 = 2.10;
        if (amount1 - amount2 == 1.05) {
            System.out.println("OK");
        }
    }
}
