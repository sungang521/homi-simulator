package com.homi.codec.encoder;

import com.homi.bean.MSG48;
import com.homi.util.ByteUtil;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class LoginEncoder extends AbstractEncoder {

    @Override
    protected int calLength() {
        return commonLength+32;
    }

    @Override
    protected void encodeBody(ByteBuf buf, MSG48 msg48) {
        Map<String,Object> map = msg48.getData();
        ByteUtil.writenShort(buf,map.get("prodKey").toString().getBytes());
        ByteUtil.writenShort(buf,map.get("devTid").toString().getBytes());
    }
}
