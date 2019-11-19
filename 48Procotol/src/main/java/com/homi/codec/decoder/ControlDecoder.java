package com.homi.codec.decoder;

import com.homi.bean.MSG48ForRSP;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class ControlDecoder extends AbstractDecoder {

    @Override
    public void decodeBody(ByteBuf buf, MSG48ForRSP msg48) {
        Map<String, Object> map = new HashMap<>();
        msg48.setData(map);
        byte cmdId = buf.readByte();
        switch (cmdId) {
            case 0:
                byte onoff = buf.readByte();
                System.out.println("收到APP的下发指令：cmdID=1，onoff_power=" + onoff);
                map.put("onoff_power", onoff);
                break;
            case 2:
                byte[] data = new byte[4];
                buf.readBytes(data);
                String t = new String(data);
                System.out.println("收到APP的下发指令：cmdID=2，tem=" + t);

        }
    }
}
