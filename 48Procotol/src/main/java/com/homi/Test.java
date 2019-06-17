package com.homi;

public class Test {
    public void test1(){
        test2();
        System.out.println("test1");
    }
    public void test2(){
        return;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.test1();
    }
}
