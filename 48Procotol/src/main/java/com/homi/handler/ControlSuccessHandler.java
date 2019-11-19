package com.homi.handler;


import com.homi.bean.MSG48;
import com.homi.device.IDevice;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ControlSuccessHandler extends ChannelInboundHandlerAdapter {
    private IDevice device;
    public ControlSuccessHandler(IDevice device){
        this.device = device;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg!=null){
            MSG48 msg48 = (MSG48) msg;
            if(msg48.getType()==0x07){
//                device.report(msg48.getData());
//                System.out.println("即将上报数据");
            }


        }
        ctx.fireChannelRead(msg);
    }
}
