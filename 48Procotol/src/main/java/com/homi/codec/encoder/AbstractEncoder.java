package com.homi.codec.encoder;

import com.homi.bean.MSG48;
import com.homi.util.SnManager;
import io.netty.buffer.ByteBuf;

public abstract class AbstractEncoder {
    protected SnManager snManager = new SnManager();
    protected int commonLength = 5;
    public void encode(ByteBuf buf, MSG48 msg48) {
        encodeHead(buf, msg48);
        encodeBody(buf,msg48);
        encodeCode(buf,msg48);
    }
    private void encodeHead(ByteBuf buf, MSG48 msg48) {
        buf.writeByte(0x48);
        buf.writeByte(calLength());
        buf.writeByte(msg48.getType());
        buf.writeByte(snManager.getNextSn());
    }

    protected abstract int calLength();

    protected abstract void encodeBody(ByteBuf buf, MSG48 msg48);

    private void encodeCode(ByteBuf buf, MSG48 msg48) {
        short sum = 0;
        buf.markReaderIndex();
        while (buf.readableBytes()>0){
            sum+=buf.readByte();
        }
        if(sum > 255){
            buf.writeByte(((byte)sum) & 0xff);
        }else {
            buf.writeByte(sum);
        }
        buf.resetReaderIndex();
    }
}
