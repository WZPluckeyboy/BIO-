package com.ping.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//服务器端业务
public class NettyServerHander extends ChannelInboundHandlerAdapter {
    //读取数据
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读数据");
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("客户端发来消息"+buf.toString(CharsetUtil.UTF_8));
    }
     //数据读取完毕
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.writeAndFlush(Unpooled.copiedBuffer("就是没钱",CharsetUtil.UTF_8)) ;
    }
    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
