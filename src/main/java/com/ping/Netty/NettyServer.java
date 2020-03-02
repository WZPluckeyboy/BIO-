package com.ping.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1.创建一个线程组,接收客户端
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //2，创建一个线程组,处理网络操作
        NioEventLoopGroup workerGroup= new NioEventLoopGroup();
        //3.配置
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_BACKLOG,128);
        b.childOption(ChannelOption.SO_KEEPALIVE,true);
        b.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NettyServerHander());
            }
        });
        System.out.println("已经准备完了");
        ChannelFuture cf = b.bind(9999).sync();
        System.out.println( "已经启动");
        cf.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
