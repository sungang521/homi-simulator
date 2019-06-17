package com.homi.util;

public class SnManager {
    private int sn = 0;
    public int getNextSn(){

       return sn++;
    }

    public static void main(String[] args) {

        short x = 300;
        System.out.println(x & 0xff);
    }
}
