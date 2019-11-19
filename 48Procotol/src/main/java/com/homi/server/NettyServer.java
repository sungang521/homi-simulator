package com.homi.server;

import com.homi.codec.Decoder;
import com.homi.codec.Encoder;
import com.homi.device.IDevice;
import com.homi.handler.ControlSuccessHandler;
import com.homi.handler.NettyHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.HashMap;
import java.util.Map;

public class NettyServer {
    public static Map<String, ChannelHandlerContext> map = new HashMap<>();

    private final String host;
    private final int port;
    private Channel channel;

    public IDevice getDevice() {
        return device;
    }

    public void setDevice(IDevice device) {
        this.device = device;
    }

    private IDevice device;
    //连接服务端的端口号地址和端口号
    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
                .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new Decoder());
                        pipeline.addLast(new ControlSuccessHandler(device));
                        pipeline.addLast(new NettyHandler());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new Encoder());
                    }
                });
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(host, port).sync();

        future.addListener((ChannelFutureListener) arg0 -> {
            if (future.isSuccess()) {
                System.out.println("连接服务器成功");

            } else {
                System.out.println("连接服务器失败");
                future.cause().printStackTrace();
                group.shutdownGracefully(); //关闭线程组
            }
        });

        this.channel = future.channel();
    }

    public Channel getChannel() {
        return channel;
    }

}
