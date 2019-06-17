package com.homi.codec.decoder;


import com.homi.bean.MSG48ForRSP;
import io.netty.buffer.ByteBuf;

public abstract class AbstractDecoder {
    public abstract void decodeBody(ByteBuf buf,MSG48ForRSP msg48);

}
