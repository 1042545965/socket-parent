package com.soket.netty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.socket.utils.netty.utils.CODE;
import com.socket.utils.netty.utils.Request;
import com.soket.netty.api.BananaCallBack;
import com.soket.netty.server.BananaService;

import io.netty.channel.ChannelHandlerContext;




@Controller
@RequestMapping("/message")
public class MessageController{

//	@Autowired
//	private BaseWebSocketServerHandler baseWebSocketServerHandler;
  //请求入口
    @RequestMapping(value="/nettymessage")
    @ResponseBody
    public void nettyMessage(@RequestParam(value="userId",required=true) String userId,
        @RequestParam(value="message",required=true) String message){
		//获取用户提交的id，作为key去获取channel
    	BananaCallBack ctx = BananaService.bananaWatchMap.get(userId);
    	Request serviceRequest = new Request();
		serviceRequest.setServiceId(CODE.receive_message.code);
		serviceRequest.setRequestId(userId);
		serviceRequest.setName("dkz==");
		serviceRequest.setMessage(message);
    	try {
			ctx.send(serviceRequest);//在使用netty将数据返回
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
