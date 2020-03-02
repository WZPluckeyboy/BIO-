package com.ping.Netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient  {
    public static void main(String[] args) throws InterruptedException {
        //1.创建一个线程组,接收客户端
        NioEventLoopGroup group = new NioEventLoopGroup();
        //2。启动助手
        Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NettyClientHander());
            }
        });
        System.out.println("客户端已经准备好");
        ChannelFuture cf = b.connect("127.0.0.1", 9999).sync();
        cf.channel().closeFuture().sync();
        group.shutdownGracefully();

    }
}
