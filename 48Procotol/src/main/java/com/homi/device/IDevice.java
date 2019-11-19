package com.homi.device;

import com.homi.bean.MSG48;

import java.util.Map;

public interface IDevice {
    MSG48 login(String prodKey,String devTid);
    MSG48 heart();
    MSG48 report(Map<String,Object> map);
    void isOpenHeart();
}
