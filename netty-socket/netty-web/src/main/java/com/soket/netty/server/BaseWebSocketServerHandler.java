package com.soket.netty.server;

import com.socket.utils.netty.utils.constant.Constant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


/**
 * 发消息方式 抽象出来
 * 
 * */
public abstract class BaseWebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

    
    /**
     * 推送单个
     * ChannelHandlerContext ==>从map当中取出对应的管子进行通信
     * */
    public static final void push(final ChannelHandlerContext ctx,final String message){
        
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        ctx.channel().writeAndFlush(tws);
        
    }
    
    /**
     * 推送单个
     * ChannelHandlerContext ==>从map当中取出对应的管子进行通信
     * */
    public static final void pushOne(final String userId,final String message){
    	ChannelHandlerContext ctx = Constant.pushCtxMap.get(userId);
    	push(ctx , message);
    }
    /**
     * 群发
     * 
     * */
    public static final void push(final ChannelGroup ctxGroup,final String message){
        
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        ctxGroup.writeAndFlush(tws);
        
    }
}