package com.ping.多人聊天案例;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {
    private final  String host;
    private  final  int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        //1.创建一个线程组,接收客户端
        EventLoopGroup group = new NioEventLoopGroup();
        //2。启动助手
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast(new ChatClientHander());
                }
            });

            ChannelFuture cf = b.connect(host, port).sync();
            Channel channel= cf.channel();
            System.out.println("["+channel.localAddress().toString().substring(1)+"]");
            Scanner sc=new Scanner(System.in);
            while(sc.hasNext()){
                String s = sc.nextLine();
                channel.writeAndFlush(s+"\r\n");

            }
            cf.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws  Exception{
        new ChatClient("127.0.0.1",9999).run();
    }
}
