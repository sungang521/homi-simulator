package com.homi.codec;

import com.homi.bean.Contant;
import com.homi.bean.MSG48ForRSP;
import com.homi.codec.decoder.*;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class DecoderProcessor {
    private static final Integer HALF_PACKAGE = 0;
    private static final Integer COMPLETE_PACKAGE = 1;
    private static final Integer OVER_PACKAGE = 2;
    private static Map<Byte, AbstractDecoder> map = new HashMap<>();

    static {
        map.put((byte) 0x01, new Response_02_Decoder());
        map.put((byte) 0x02, new Response_02_Decoder());
        map.put((byte) 0x0C, new Response_0C_Decoder());
        map.put((byte) 0x0A, new Response_0A_Decoder());
        map.put((byte) 0x07, new ControlDecoder());
    }

    public MSG48ForRSP decode(ByteBuf buf) {
        MSG48ForRSP msg48 = new MSG48ForRSP();
        buf.markReaderIndex();
        if (checkPackage(buf)) {
            int packageInfo = checkComplete(buf);
            switch (packageInfo) {
                case 0:
                    System.out.println("收到的包信息不完整");
                    break;
                case 1:
                case 2:
                    if (checkCode(buf)) {
                        decodeHead(buf, msg48);
                        AbstractDecoder decoder = map.get(msg48.getType());
                        if (decoder == null) {
                           throw new RuntimeException("找不到适合的设备解码器");
                        }
                        decoder.decodeBody(buf, msg48);
                        msg48.setCode(buf.readByte());
                    }
                    break;
            }

            return msg48;
        } else {
            return null;
        }
    }

    /**
     * 解码头部
     */
    private void decodeHead(ByteBuf buf, MSG48ForRSP msg48) {
        msg48.setSign(buf.readByte());
        msg48.setLength(buf.readByte());
        msg48.setType(buf.readByte());
        msg48.setNumber(buf.readByte());
        if(msg48.getType()!=0x07){
            int rcode =
                    (buf.readByte() & 0xff) << 24 |
                            (buf.readByte() & 0xff) << 16 |
                            (buf.readByte() & 0xff) << 8 |
                            buf.readByte() & 0xff;
            msg48.setRspCode(rcode);
        }


    }

    /**
     * 校验 校验码的正确
     * @param buf
     * @return
     */
    private boolean checkCode(ByteBuf buf) {
        buf.markReaderIndex();
        buf.skipBytes(1);
        int length = buf.readByte();
        buf.resetReaderIndex();
        int sum = 0;
        for (int i = 0;i<length-1;i++){
            sum = sum + buf.readByte();
        }
        int code = buf.readByte();
        if(sum>255){
          sum = ((byte)sum) & 0xff;
        }
        System.out.println("计算出来的校验码是："+sum);
        int _code = code & 0xff;
        buf.resetReaderIndex();
        if( _code == sum){
            return true;
        }else {
            System.out.println("校验码错误");
            return false;
        }
    }

    /**
     * 检查包完整
     *
     * @param buf
     */
//    private void checkComplete(ByteBuf buf) {
//        buf.markReaderIndex();
//        int realLength = buf.readableBytes();
//        buf.skipBytes(1);
//        Byte length = buf.readByte();
//        if (length > realLength) {
//            //半包
//            buf.resetReaderIndex();
//            return;
//        } else if (length == realLength) {
//            int sum = 0;
//            while (buf.readableBytes() - 1 > 0) {
//                sum = sum + buf.readByte();
//            }
//            if (sum < 255) {
//                if (sum != buf.readByte()) {
//                    System.out.println("校验码错误");
//                    return;
//                } else {
//                    buf.resetReaderIndex();
//                }
//            } else {
//                //
//            }
//        } else {
//
//        }
//    }
    private Integer checkComplete(ByteBuf buf) {
        buf.markReaderIndex();
        int realLength = buf.readableBytes();
        buf.skipBytes(1);
        Byte length = buf.readByte();
        buf.resetReaderIndex();
        if (length > realLength) {
            return HALF_PACKAGE;
        } else if (length == realLength) {
            return COMPLETE_PACKAGE;
        } else {
            return OVER_PACKAGE;
        }

    }

    /**
     * 校验包
     */
    private boolean checkPackage(ByteBuf buf) {
        Byte sign = buf.readByte();
        if (sign.byteValue() != Contant.SIGN.byteValue()) {
            System.out.println("包数据标示位错误");
            return false;
        }
        buf.resetReaderIndex();
        return true;
    }

}
