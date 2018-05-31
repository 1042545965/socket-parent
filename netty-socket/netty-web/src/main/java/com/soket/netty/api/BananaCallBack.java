package com.soket.netty.api;

import com.socket.utils.netty.utils.Request;

public interface BananaCallBack {
	
	// 服务端发送消息给客户端
	void send(Request request) throws Exception;
	
}
