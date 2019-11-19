package com.homi.codec.encoder;

import com.homi.bean.MSG48;
import com.homi.util.ByteUtil;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class ReportEncoder extends AbstractEncoder {

    @Override
    protected int calLength() {
        return commonLength+20;
    }

    @Override
    protected void encodeBody(ByteBuf buf, MSG48 msg48) {
        Map<String,Object> map = msg48.getData();
        String cmdId = map.get("cmdId").toString();
        switch (cmdId){
            /**
             * map.put("device_on_off", 1);
             *         map.put("d_states",0);
             *         map.put("light_on_off", 0);
             *         map.put("temp", "31.0");
             *         map.put("humi", "31.0");
             *         map.put("set_temp_up",1);
             *         map.put("set_humi_up",1);
             *         map.put("comp_temp", "+00");
             *         map.put("comp_humi", "+00");
             */
            case "6":
                ByteUtil.writeShort(buf,(byte) 0x06);
                //设备开关机状态
                int device_on_off = (Integer) map.get("device_on_off");
                ByteUtil.writeShort(buf,(byte) device_on_off);

                int d_states = (Integer) map.get("d_states");
                ByteUtil.writeShort(buf,(byte) d_states);

                int light_on_off = (Integer) map.get("light_on_off");
                ByteUtil.writeShort(buf,(byte) light_on_off);

                String temp =  map.get("temp").toString();
                buf.writeBytes(temp.getBytes());

                String humi = map.get("humi").toString();
                buf.writeBytes(humi.getBytes());

                int set_temp_up = (Integer) map.get("set_temp_up");
                ByteUtil.writeShort(buf,(byte) set_temp_up);

                int set_humi_up = (Integer) map.get("set_humi_up");
                ByteUtil.writeShort(buf,(byte) set_humi_up);

                String comp_temp =  map.get("comp_temp").toString();
                buf.writeBytes(comp_temp.getBytes());

                String comp_humi =  map.get("comp_humi").toString();
                buf.writeBytes(comp_humi.getBytes());



        }
//        ByteUtil.writenShort(buf,map.get("prodKey").toString().getBytes());
//        ByteUtil.writenShort(buf,map.get("devTid").toString().getBytes());
    }
}
