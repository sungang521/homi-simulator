package com.homi.codec;

import com.homi.bean.MSG48;
import com.homi.codec.encoder.AbstractEncoder;
import com.homi.codec.encoder.HeartEncoder;
import com.homi.codec.encoder.LoginEncoder;
import com.homi.codec.encoder.ReportEncoder;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class EncoderProcessor {
  private static Map<Byte, AbstractEncoder> map = new HashMap<>();
  static {
      map.put((byte)0x01,new LoginEncoder());
      map.put((byte)0x0B,new HeartEncoder());
      map.put((byte)0x09,new ReportEncoder());
  }

  public void encode(ByteBuf byteBuf, MSG48 msg48){
    AbstractEncoder encoder = map.get(msg48.getType());
    if (encoder == null){
      System.out.println("找不到合适的编码器");
      return;
    }
    encoder.encode(byteBuf,msg48);

  }

}
