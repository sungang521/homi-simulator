package com.homi.util;

public class SnManager {
    private int sn = 0;
    public int getNextSn(){
      if(sn>=255){
          sn=0;
      }
       return sn++;
    }
}
