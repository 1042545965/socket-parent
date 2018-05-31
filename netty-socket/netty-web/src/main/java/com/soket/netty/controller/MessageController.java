package com.soket.netty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping("/message")
public class MessageController{

//	@Autowired
//	private BaseWebSocketServerHandler baseWebSocketServerHandler;
  //请求入口
    @RequestMapping(value="/nettymessage",method=RequestMethod.POST)
    @ResponseBody
    public String nettyMessage(@RequestParam(value="userId",required=true) String userId,
        @RequestParam(value="message",required=true) String message){
			return message;
    	//
//    	try {
//    		BaseWebSocketServerHandler.pushOne(userId, message);
//    		return "发送成功";
//		} catch (Exception e) {
//			return "发送失败";
//		}
    	
			
    }
}
