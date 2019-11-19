package com.homi;

import com.homi.device.DefaultDevice;
import com.homi.device.IDevice;
import com.homi.server.NettyServer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        NettyServer server = new NettyServer("hub.aihomi.com", 93);
        IDevice device = new DefaultDevice(server);

        //是否启用心跳
       // device.isOpenHeart();
        //设备登录
        device.login("gz00x010it0300i1", "gz00v010rW0200c8");

         while (true) {
        Thread.sleep(5000);
        //上报
        Map<String, Object> map = new HashMap<>();
        map.put("cmdId", 6);
        map.put("device_on_off", 1);
        map.put("d_states",0);
        map.put("light_on_off", 0);
        map.put("temp", "31.0");
        map.put("humi", "19.0");
        map.put("set_temp_up",1);
        map.put("set_humi_up",1);
        map.put("comp_temp", "+00");
        map.put("comp_humi", "+00");

        device.report(map);
        }
    }
}
