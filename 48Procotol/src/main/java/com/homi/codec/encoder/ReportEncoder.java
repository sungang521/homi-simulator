package com.homi.codec.encoder;

import com.homi.bean.MSG48;
import com.homi.util.ByteUtil;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class ReportEncoder extends AbstractEncoder {

    @Override
    protected int calLength() {
        return commonLength+2;
    }

    @Override
    protected void encodeBody(ByteBuf buf, MSG48 msg48) {
        Map<String,Object> map = msg48.getData();
        String cmdId = map.get("cmdId").toString();
        switch (cmdId){
            case "1":
                //开关状态
                ByteUtil.writeShort(buf,(byte) 0x01);
                int switchStstus = (Integer) map.get("onoff");
                ByteUtil.writeShort(buf,(byte) switchStstus);
        }
//        ByteUtil.writenShort(buf,map.get("prodKey").toString().getBytes());
//        ByteUtil.writenShort(buf,map.get("devTid").toString().getBytes());
    }
}
