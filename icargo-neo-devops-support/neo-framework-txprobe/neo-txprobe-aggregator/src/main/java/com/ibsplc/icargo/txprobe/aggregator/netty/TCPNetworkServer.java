/*
 * TCPNetworkServer.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
@Named("tcpNetworkServer")
public class TCPNetworkServer extends ChannelInitializer<SocketChannel> implements NettyComponent, ChannelFutureListener {
	
	static final Logger logger = LoggerFactory.getLogger(TCPNetworkServer.class);

	private final ChannelInboundHandler dispatcherEndpoint;
	private final int serverPort;
	private final int acceptorThreads;
	private final String channelType;
	
	private ServerBootstrap serverBootstrap;
	private SocketChannel socketChannel;

	@Inject
	public TCPNetworkServer(@Named("dispatcherEndpoint") ChannelInboundHandler dispatcherEndpoint,
							@ConfigProperty(name = "networkServer.port") int serverPort,
							@ConfigProperty(name = "networkServer.acceptorThreads", defaultValue = "1") int acceptorThreads,
							@ConfigProperty(name = "networkServer.channelType", defaultValue = "nio") String channelType) {
		this.dispatcherEndpoint = dispatcherEndpoint;
		this.serverPort = serverPort;
		this.acceptorThreads = acceptorThreads;
		this.channelType = channelType;
	}

	public enum ChannelType {
		nio, nativeio
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.netty.NettyComponent#start()
	 */
	@Override
	public void start() {
		serverBootstrap = new ServerBootstrap();
		ChannelType ctype = resolveChannelType();
		switch(ctype){
			case nativeio:
				logger.info("using epoll native transport channel.");
				serverBootstrap.channel(EpollServerSocketChannel.class);
				serverBootstrap.group(new EpollEventLoopGroup(getAcceptorThreads()), new EpollEventLoopGroup());
				break;
			case nio:
				logger.info("using nio transport channel.");
				serverBootstrap.channel(NioServerSocketChannel.class);
				serverBootstrap.group(new NioEventLoopGroup(getAcceptorThreads()), new NioEventLoopGroup());
				break;
		}
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false));
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.childHandler(this);
        serverBootstrap.bind(getServerPort()).addListener(this);
	}
	
	private ChannelType resolveChannelType(){
		boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");
		if("auto".equals(channelType)){
			//return isLinux ? ChannelType.nativeio : ChannelType.nio;
			return ChannelType.nio;
		}
		ChannelType ctype = ChannelType.valueOf(channelType);
		if(ChannelType.nativeio == ctype){
			// nativeio only supported in linux x86_64
			if(!isLinux)
				ctype = ChannelType.nio;
		}
		return ctype;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.netty.NettyComponent#stop()
	 */
	@Override
	public void stop() {
		logger.info("closing server channels.");
		if(this.socketChannel != null)
			this.socketChannel.close();
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		this.socketChannel = ch;
		ch.pipeline().addLast(new TxProbeFrameDecoder());
		ch.pipeline().addLast(getDispatcherEndpoint());
	}
	
	/* (non-Javadoc)
	 * @see io.netty.util.concurrent.GenericFutureListener#operationComplete(io.netty.util.concurrent.Future)
	 */
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if(future.isSuccess()){
			logger.info("server started listening on port : {}", getServerPort());
		}else{
			future.cause().printStackTrace();
		}
	}

	/**
	 * @return the dispatcherEndpoint
	 */
	public ChannelInboundHandler getDispatcherEndpoint() {
		return dispatcherEndpoint;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @return the acceptorThreads
	 */
	public int getAcceptorThreads() {
		return acceptorThreads;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

}
