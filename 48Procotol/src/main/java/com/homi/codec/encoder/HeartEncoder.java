package com.homi.codec.encoder;

import com.homi.bean.MSG48;
import io.netty.buffer.ByteBuf;

public class HeartEncoder extends AbstractEncoder {
    @Override
    protected int calLength() {
        return 5;
    }

    @Override
    protected void encodeBody(ByteBuf buf, MSG48 msg48) {

    }
}
