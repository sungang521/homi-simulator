package com.homi.codec;

import com.homi.bean.MSG48;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.xml.bind.DatatypeConverter;

public class Encoder extends MessageToByteEncoder<MSG48> {
    private EncoderProcessor processor = new EncoderProcessor();
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MSG48 msg48, ByteBuf byteBuf) throws Exception {
        processor.encode(byteBuf,msg48);
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        String str = DatatypeConverter.printHexBinary(data);
        System.out.println("发送字节："+str);
        channelHandlerContext.writeAndFlush(str);
    }
}
