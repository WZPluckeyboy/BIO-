package com.ping.多人聊天案例;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

//服务器端业务
public class ChatServerHander extends SimpleChannelInboundHandler<String> {
    public static List<Channel> channels=new ArrayList<>();
    @Override
    //通道到就绪
    public void channelActive(ChannelHandlerContext ctx)  {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1)+" 离线");
    }
    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s)  {
        Channel inChannel = channelHandlerContext.channel();
        for(Channel channel:channels){
            if(channel!=inChannel){
                System.out.println("["+inChannel.remoteAddress().toString().substring(1)+"]"+"说"+s+"\n");
            }
        }
    }
     @Override
    //通道未就绪
    public void channelInactive(ChannelHandlerContext ctx)  {
         Channel inChannel = ctx.channel();
         channels.add(inChannel);
         System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1)+"上线了");
    }

}
