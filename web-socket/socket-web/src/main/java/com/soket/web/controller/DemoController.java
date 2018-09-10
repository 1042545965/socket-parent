package com.soket.web.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ext.cloud.core.base.controller.CoCoController;
import com.socket.utils.websocket.utils.WebsocketDemo;
import com.soket.web.api.DemoService;
/**
    * @ClassName: DemoController
    * @Description: TODO(Demo路由)
    * @author dkz
    * @date  2018-9-8 20:54:35
    *
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends CoCoController{
	
	@Autowired DemoService demoService;
	
	public List<Long> listuser = new ArrayList<Long>();
//	@ResponseBody
//	@RequestMapping("/queryNewsList")
//	public MM queryNewsList(){
//		try {
//			List<MM> list=demoService.queryNewsList();
//			return getSuccessMessage(list);
//		} catch (Exception e) {
//			return getErrorMessage("load data error!");
//		}
//
//	}
	@RequestMapping(value="/totestwebsocket/{userId}")
	public String totestwebsocket(@PathVariable String userId , Model model){
		model.addAttribute("userId",userId);
		return "testwebsocket.html";
	}
	
	@RequestMapping(value="/todemo/{userId}")
	public String demoString(@PathVariable String userId , Model model){
		model.addAttribute("userId",userId);
		return "liaotian.html";
	}
	
	@RequestMapping(value="/demolist")
	public String demolist(){
		return "userlist.html";
	}
	
	@RequestMapping(value="/togame/{userId}")
	public String togame(@PathVariable Long userId , Model model){
		model.addAttribute("userId",userId);
		return "game/game01.html";
	}
	
	@RequestMapping(value="/mateuser")
	@ResponseBody
	public Map<String, Object> mateuser(Long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("star", false);
		//获取所有在线用户
		WebsocketDemo websocketDemo = new WebsocketDemo();
		//进行游戏匹配
		Map<Long, Set<WebsocketDemo>> userSocket = websocketDemo.getUserSocket();
		if (!userSocket.isEmpty()) {
			for (Long key : userSocket.keySet()) {
				if (key != userId) {//规则 ①不能自己和自己匹配 
					if (listuser.size() > 0 ) {
						for (int i = 0; i < listuser.size(); i++) {
							if (!listuser.contains(key)) {//② 匹配的队友不能再游戏当中
								map.put("star", true);
								listuser.add(userId);//设置自己成为游戏中
								listuser.add(key); // 设置对方进入游戏中
								map.put("sheuser", key);
								return map;
							}
						}
					}else {//没有人的话,那就是随意匹配谁都可以
						map.put("star", true);
						listuser.add(userId);//设置自己成为游戏中
						listuser.add(key); // 设置对方进入游戏中
						map.put("sheuser", key);
						return map;
					}
					
				}
			}
		}
		return map;
	}
	
	
	@RequestMapping(value="/removeGame")
	@ResponseBody
	public String removeGame(Long userId , Model model){
		
		Iterator<Long> it = listuser.iterator();
		while(it.hasNext()){
		    Long x = it.next();
		    if(x == userId){
		        it.remove();
		    }
		}
		return "game/game01.html";
	}
	
}
