package com.soket.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ext.cloud.core.base.controller.CoCoController;
import com.ext.cloud.core.plugin.activerecord.MM;
import com.soket.web.api.DemoService;
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
	
	@Autowired DemoService demoService;
	
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
	
	
	@RequestMapping(value="/todemo/{userId}")
	public String demoString(@PathVariable String userId , Model model){
		model.addAttribute("userId",userId);
		return "liaotian.html";
	}
	
	@RequestMapping(value="/demolist")
	public String demolist(){
		return "userlist.html";
	}
	
}
