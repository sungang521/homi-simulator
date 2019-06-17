package com.homi.codec;

import com.homi.bean.MSG48ForRSP;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class Decoder extends MessageToMessageDecoder<String> {
    private DecoderProcessor processor = new DecoderProcessor();


    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("收到响应：" + msg);
        ByteBuf byteBuf = Unpooled.buffer(msg.length() % 2);
        for (byte g : hexStringToByteArray(msg)) {
            byteBuf.writeByte(g & 0xff);
        }
        MSG48ForRSP msg48 = processor.decode(byteBuf);
        if (msg != null) {
            out.add(msg48);
        }


    }

    public static void main(String[] args) {
        String h = "00155CDB";
        System.out.println();
        for (byte g : hexStringToByteArray(h)) {

        }
    }

    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
