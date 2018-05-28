package com.soket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soket.web.api.WSMessageService;


@Controller
@RequestMapping("/message")
public class MessageController{
    //websocket服务层调用类
    @Autowired
    private WSMessageService wSMessageService;

  //请求入口
    @RequestMapping(value="/TestWS",method=RequestMethod.POST)
    @ResponseBody
    public String TestWS(@RequestParam(value="userId",required=true) Long userId,
        @RequestParam(value="message",required=true) String message){
        if(wSMessageService.sendToAllTerminal(userId, message)){
            return "发送成功";
        }else{
            return "发送失败";
        }   
    }
}
