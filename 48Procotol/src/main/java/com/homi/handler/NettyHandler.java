package com.homi.handler;

import com.homi.bean.Cache;
import com.homi.bean.MSG48;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg!=null){
            MSG48 msg48 = (MSG48) msg;
            MSG48 request = null;
            /**
             * 当服务端返回错误的时候，命令ID是与发送ID一样的的
             */
            if(Cache.map.keySet().contains(""+((MSG48) msg).getType())){
                request = Cache.map.get(((MSG48) msg).getType().toString());
            }else {
                switch (msg48.getType()){
                    case 0x02:
                        request = Cache.map.get("1");
                        break;
                    case 0x0C:
                        request = Cache.map.get(0x0B+"");
                        break;
                    case 0x0A:
                        request = Cache.map.get(0x09+"");
                        break;
                }
            }
            if(request!=null){
                synchronized (request){
                    Cache.map.put(""+request.getType(),msg48);
                    request.notifyAll();
                }
            }



        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("close");
        ctx.close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
