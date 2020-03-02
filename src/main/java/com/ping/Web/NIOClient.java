package com.ping.Web;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {
        //1.通道
        SocketChannel fe = SocketChannel.open();
        //2.设置阻塞方式
         fe.configureBlocking(false);
         //3.提供服务端IP，端口号
        InetSocketAddress address=new InetSocketAddress("127.0.0.1",9999);
        //4.连接服务器
        if(!fe.connect(address)){
           while(!fe.finishConnect()){
               System.out.println("Client:连接服务器还可以干其他事");
           }
        }
        //5.发数据
        String s="hello server";
        ByteBuffer buf=ByteBuffer.wrap(s.getBytes());
        fe.write(buf);
        System.in.read();
    }
}
