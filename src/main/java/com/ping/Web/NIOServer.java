package com.ping.Web;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //1.得到serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.得到selector
        Selector selector= Selector.open();
        //3.绑定端口号
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",9999));
        //4。设置阻塞
        serverSocketChannel.configureBlocking(false);
        //5注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6.发数据
        while (true){
            //6.1监控
            int select = selector.select(2000);//设置超时时间
            if(select==0){
                System.out.println("Server:客户端无连接");
                continue;
            }
            //6.2得到selectonkey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                System.out.println("accept");
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                   ByteBuffer attachment = (ByteBuffer) key.attachment();
                   channel.read(attachment);
                    System.out.println("客服端发数据"+new String(attachment.array()));

                }
                //手动从集合移除，防止重复
               iterator.remove();

            }


        }

    }
}
