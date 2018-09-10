package com.soket.netty.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ext.cloud.core.base.controller.CoCoController;
/**
    * @ClassName: DemoController
    * @Description: TODO(Demo路由)
    * @author Remiel_Mercy xuefei_fly@126.com
    * @date  2017年11月16日 下午5:20:44 
    *
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends CoCoController{
	
	

	
	
	@RequestMapping(value="/todemo/{userId}")
	public String demoString(@PathVariable String userId , Model model){
		model.addAttribute("userId",userId);
		return "nettysocket/banana.html";
	}
	
	@RequestMapping(value="/demolist")
	public String demolist(){
		return "userlist.html";
	}
	
}
