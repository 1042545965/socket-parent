package com.soket.netty.server;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.soket.netty.init.AfterSpringBegin;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 启动服务
 * */

public class WebSocketServer extends AfterSpringBegin{
	private static final Logger LOG = Logger.getLogger(WebSocketServer.class);
    //用于客户端连接请求
    @Autowired
    private EventLoopGroup bossGroup;
    
    //用于处理客户端I/O操作
    @Autowired
    private EventLoopGroup workerGroup;
    
    //服务器的辅助启动类
    @Autowired
    private ServerBootstrap serverBootstrap;
    
    //BS的I/O处理类
    private ChannelHandler childChannelHandler;
    
    private ChannelFuture channelFuture;
    
    //服务端口
    private int port ;
    
    public WebSocketServer(){
    	System.err.println("初始化");
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public ServerBootstrap getServerBootstrap() {
        return serverBootstrap;
    }

    public void setServerBootstrap(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public ChannelHandler getChildChannelHandler() {
        return childChannelHandler;
    }

    public void setChildChannelHandler(ChannelHandler childChannelHandler) {
        this.childChannelHandler = childChannelHandler;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("===============port==>"+port+"====================");
        	bulid(port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
   /* public void bulid(int port) throws Exception{
        
        try {
            
            //（1）boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
            //（2）配置客户端的channel类型
            //(3)配置TCP参数，握手字符串长度设置
            //(4)TCP_NODELAY是一种算法，为了充分利用带宽，尽可能发送大块数据，减少充斥的小块数据，true是关闭，可以保持高实时性,若开启，减少交互次数，但是时效性相对无法保证
            //(5)开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
            //(6)netty提供了2种接受缓存区分配器，FixedRecvByteBufAllocator是固定长度，但是拓展，AdaptiveRecvByteBufAllocator动态长度
            //(7)绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            serverBootstrap.group(bossGroup,workerGroup)
                           .channel(NioServerSocketChannel.class)
                           .option(ChannelOption.SO_BACKLOG, 1024)
                           .option(ChannelOption.TCP_NODELAY, true)
                           .childOption(ChannelOption.SO_KEEPALIVE, true)
                           .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                           .childHandler(childChannelHandler);
            
            System.out.println("===成功===");
            channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            // TODO: handle exception
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            
        }

    }*/
    
	public void bulid(int port) throws Exception{
		//boss线程监听端口和客户端的连接
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//worker线程负责处理客户端的读写
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel channel) throws Exception {
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
					pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装大小
					pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
					pipeline.addLast("handler", new BananaWebSocketServerHandler()); // WebSocket服务端Handler
				}
			});
			
			Channel channel = b.bind(port).sync().channel();
			LOG.info("WebSocket 已经启动，端口：" + port + ".");
			channel.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
    
    //执行之后关闭
    @PreDestroy
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        
        
    }
}