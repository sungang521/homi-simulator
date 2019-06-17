package com.homi.util;

import io.netty.buffer.ByteBuf;

public class ByteUtil {
    public static void  writeShort(ByteBuf byteBuf,byte data){
        byteBuf.writeByte(data & 0xff);
    }
    public static void  writenShort(ByteBuf byteBuf,byte[] data){
        for(byte x : data){
            byteBuf.writeByte(x & 0xff);
        }

    }
    public static short readShort(byte data){
        return (short) (data & 0xff);
    }

    public static String toHex(ByteBuf buf){
        StringBuffer sb = new StringBuffer();
        buf.markReaderIndex();
        while (buf.readableBytes()>0){
            sb.append(Integer.toHexString(buf.readByte()));
            sb.append(" ");
        }
        buf.resetReaderIndex();
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(Integer.toHexString(158));
    }
}
