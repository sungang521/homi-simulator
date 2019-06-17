package com.homi.codec.decoder;

import com.homi.bean.MSG48ForRSP;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class ControlDecoder extends AbstractDecoder {

    @Override
    public void decodeBody(ByteBuf buf, MSG48ForRSP msg48) {
        Map<String,Object> map = new HashMap<>();
        msg48.setData(map);
      byte cmdId = buf.readByte();
      switch (cmdId){
          case 1:
              byte onoff = buf.readByte();
              map.put("onoff",onoff);
              break;

      }
    }
}
