package com.homi.device;

import com.homi.bean.Cache;
import com.homi.bean.MSG48;
import com.homi.bean.MSG48ForRSP;
import com.homi.server.NettyServer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultDevice implements IDevice {
    private boolean isOpen = false;

    public void setOpen(boolean open) {
        isOpen = open;
    }

    private NettyServer server;
    private ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    public DefaultDevice(NettyServer server){
        this.server = server;
        init();
    }
    public void init(){
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHeart(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                heart();
            }
        };
        pool.scheduleWithFixedDelay(r,0,3, TimeUnit.SECONDS);
    }
    @Override
    public MSG48 login(String prodKey,String devTid) {
        MSG48 msg48 = new MSG48();
        msg48.setType((byte)0x01);
        Map<String,Object> map = new HashMap<>();
        msg48.setData(map);
        map.put("prodKey",prodKey);
        map.put("devTid",devTid);
        MSG48ForRSP x = (MSG48ForRSP)getRSP(msg48);
        System.out.println("登录应答："+x);
        if (x.getRspCode()==1200000){
            if(isOpen) {
                startHeart();
            }
        }
        return x ;
    }

    @Override
    public MSG48 heart() {
        MSG48 msg48 = new MSG48();
        msg48.setType((byte)0x0B);
        MSG48 msg481 = getRSP(msg48);
        System.out.println("心跳应答："+msg481);
        return msg481;
    }

    @Override
    public MSG48 report(Map<String, Object> map) {
        MSG48 msg48 = new MSG48();
        msg48.setType((byte)0x09);
        msg48.setData(map);
        MSG48 msg481 = getRSP(msg48);
        System.out.println("上报应答："+msg481);
        return msg481;
    }

    private MSG48 getRSP(MSG48 msg48){
        server.getChannel().writeAndFlush(msg48);
        synchronized (msg48){
            try {
                Cache.map.put(""+msg48.getType(),msg48);
                msg48.wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Cache.map.get(""+msg48.getType());
    }
}
