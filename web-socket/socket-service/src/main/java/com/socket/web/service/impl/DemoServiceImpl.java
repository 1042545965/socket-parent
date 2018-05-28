package com.socket.web.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ext.cloud.core.plugin.activerecord.MM;
import com.soket.web.api.DemoService;

@Service("demoService")
public class DemoServiceImpl implements DemoService{

	@Override
	@Transactional
	public void saveTest(MM mm) {
		// TODO Auto-generated method stub
		
	}

}
