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
    public static void  writenShort(ByteBuf byteBuf,int data){
        byte[] x = chaiFenDataIntTo2Byte(data);
        writenShort(byteBuf,x);

    }


    /**
     * 整型数据拆分为长度为2的字节数组，低8位存放在序号小的元素，高8位存放在序号大的元素中(小端存储)
     *
     * @param data
     * @return
     */
    public static byte[] chaiFenDataIntTo2Byte(int data) {
        byte[] byteArray = new byte[2];
        byteArray[1] = (byte) data;
        byteArray[0] = (byte) (data >> 8);
        return byteArray;
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
