package com.homi;

import com.homi.device.DefaultDevice;
import com.homi.device.IDevice;
import com.homi.server.NettyServer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        NettyServer server = new NettyServer("hub.aihomi.com",93);
        IDevice device = new DefaultDevice(server);
        device.login("gz00L010kf000093","gz00W010G30100k5");

        Map<String,Object> map = new HashMap<>();
        map.put("cmdId",1);
        map.put("onoff",0);
        device.report(map);
    }
}
