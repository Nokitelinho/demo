/*
 * NetworkClientHandler.java Created on 30-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher.handlers;

import com.ibsplc.neoicargo.framework.probe.AggregationServerConfig;
import com.ibsplc.neoicargo.framework.probe.PayloadHolder;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlerState;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
@Sharable
public class NetworkClientHandler extends SimpleChannelInboundHandler<byte[]> 
	implements HandlingStateAwareEventHanlder<PayloadHolder>, Runnable, ChannelFutureListener{
	
	public static final byte[] FRAME_HEADER = new byte[]{'T', 'X', 'P', 'H'};//54 58 50 48
	public static final byte[] FRAME_TRAILER = new byte[]{'T', 'X', 'P', 'T'};//54 58 50 54

	static Logger logger = LoggerFactory.getLogger(NetworkClientHandler.class);
	
	private final Object mutex = new Object();
	private final int flushSize;
	private long counter;
	private AggregationServerConfig config;
	private EventLoopGroup eventLoop;
	private Scheduler scheduler;
	private ByteBufAllocator bufferAllocator;
	private AtomicReference<ChannelHandlerContext> client = new AtomicReference<ChannelHandlerContext>(null);
	private List<ClusterMemberAddress> clusterMembers;
	private int connectMember = 0;
	
	public enum ChannelType {
		nio, oio
	};
	
	public NetworkClientHandler(AggregationServerConfig config){
		this.config = config;
		this.flushSize = config.getFlushSize();
		init();
	}
	
	public void init(){
		ChannelType ctype = resolveChannelType(this.config.getChannelType());
		switch(ctype){
			case nio:
				this.eventLoop = new NioEventLoopGroup(this.config.getWorkerCount());
				break;
			case oio:
				this.eventLoop = new OioEventLoopGroup();
				break;
		}
		this.scheduler = createScheduler(ctype, this.eventLoop);
		this.bufferAllocator = new PooledByteBufAllocator(false);
		this.clusterMembers = resolveClusterAddress(this.config.getAddress());
		run();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#stop()
	 */
	@Override
	public void stop() {
		this.scheduler.stop();
		ChannelHandlerContext answer = client.get();
		if(answer != null)
			answer.close();
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
		ChannelHandlerContext clientCtx = getClientHandlerContext(); // block till the connection is established
		if(clientCtx != null){
			int dataSize = event.getCompressedSize();
			ByteBuf buff = clientCtx.alloc().buffer(dataSize + 4 * 3);
			buff.writeBytes(FRAME_HEADER);
			buff.writeInt(dataSize);
			buff.writeBytes(FRAME_TRAILER);
			event.write(buff);
			counter++;
			if(endOfBatch || counter%flushSize == 0){
				ChannelFuture future = clientCtx.writeAndFlush(buff);
				// put back pressure on the chain
				if(!config.isSendAsync())
					future.sync();
			}else{
				clientCtx.write(buff, clientCtx.voidPromise());				
			}
		}else
			logger.warn("Discarding message...");
	}
	
	/**
	 * blocks till a connection is obtained
	 * @return
	 */
	protected ChannelHandlerContext getClientHandlerContext(){
		ChannelHandlerContext answer = client.get();
		if(answer != null)
			return answer;
		while((answer = client.get()) == null){
			synchronized(this.mutex){
				try {
					this.mutex.wait();
				} catch (InterruptedException e) {
					// ignored
				}
			}
		}
		return answer;
	}
	
	protected void setClientHandlerContext(ChannelHandlerContext ctx){
		client.getAndSet(ctx);
		synchronized(this.mutex){
			this.mutex.notifyAll();
		}
	}
	
	/* (non-Javadoc)
	 * @see io.netty.util.concurrent.GenericFutureListener#operationComplete(io.netty.util.concurrent.Future)
	 */
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if(future.isSuccess()){
			logger.info("Successfully connected to remote aggregation server.");
		}else{
			logger.warn("Connection failed.", future.cause());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("Attempting to establish connection.");
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(eventLoop);
		ChannelType ctype = resolveChannelType(this.config.getChannelType());
		switch(ctype){
			case nio:
				bootstrap.channel(NioSocketChannel.class);
				break;
			case oio:
				bootstrap.channel(OioSocketChannel.class);
				break;
		}
		bootstrap.option(ChannelOption.ALLOCATOR, this.bufferAllocator);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, config.isTcpKeepAlive());
		bootstrap.option(ChannelOption.TCP_NODELAY, config.isTcpNoDelay());
		ClusterMemberAddress address = getConnectAddress();
		bootstrap.remoteAddress(address.host, address.port);
		bootstrap.handler(this);
		bootstrap.connect().addListener(this);
	}

	private ChannelType resolveChannelType(String channelType){
		if("auto".equals(channelType)){
			return ChannelType.nio;
		}	
		return ChannelType.valueOf(channelType);
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelUnregistered(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		setClientHandlerContext(null);
		logger.info("Disconnected : {}", ctx.channel().remoteAddress());
		this.scheduler.schedule(this, config.getReconnectInterval(), TimeUnit.MILLISECONDS);
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Connected to : {}", ctx.channel().remoteAddress());
		setClientHandlerContext(ctx);
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		setClientHandlerContext(null);
		logger.info("Disconnected from : {}", ctx.channel().remoteAddress());
		ctx.close();
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		// nothing to read
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warn("Error in the txProbe network client", cause);
		ctx.close();
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#handleState()
	 */
	@Override
	public HandlerState handleState() {
		return HandlerState.TRANSPORT;
	}

	protected synchronized ClusterMemberAddress getConnectAddress(){
		ClusterMemberAddress address = this.clusterMembers.get(connectMember);
		int nextMember = ++connectMember;
		if(this.clusterMembers.size() <= nextMember)
			nextMember = 0;
		this.connectMember = nextMember;
		return address;
	}
	
	protected List<ClusterMemberAddress> resolveClusterAddress(String address){
		String[] addressPairs = address.split(",");
		ArrayList<ClusterMemberAddress> answer = new ArrayList<ClusterMemberAddress>(addressPairs.length);
		ClusterMemberAddress local = null;
		for(String pair : addressPairs){
			ClusterMemberAddress ca = new ClusterMemberAddress(pair);
			if(local == null && ca.isThisBox())
				local = ca;
			else
				answer.add(ca);
		}
		if(local != null){
			// randomly connect to cluster members to distribute load
			Collections.shuffle(answer);
			// give weightage to the local address 
			answer.add(0, local);
		}
		return answer;
	}
	
	protected Scheduler createScheduler(ChannelType type, final EventLoopGroup eventLoop){
		if(type == ChannelType.oio){
			return new Scheduler() {
				boolean stopped;
				final ScheduledExecutorService service = Executors.newScheduledThreadPool(0);
				@Override
				public void schedule(Runnable run, long delay, TimeUnit unit) {
					if(!stopped)
						service.schedule(run, delay, unit);
				}
				/* (non-Javadoc)
				 * @see com.ibsplc.icargo.framework.probe.dispatcher.handlers.NetworkClientHandler.Scheduler#stop()
				 */
				@Override
				public void stop() {
					this.stopped = true;
					this.service.shutdown();
				}
			};
		}else
			return new EventLoopScheduler(eventLoop);
	}
	
	/**
	 * @return the config
	 */
	public AggregationServerConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(AggregationServerConfig config) {
		this.config = config;
	}
	

	/**
	 * @author A-2394
	 * Scheduler which uses the underlying eventloop thread pool.
	 */
	private static class EventLoopScheduler implements Scheduler{
		final EventLoopGroup eventLoop;
		boolean stopped;
		
		public EventLoopScheduler(EventLoopGroup eventLoop) {
			super();
			this.eventLoop = eventLoop;
		}

		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.dispatcher.handlers.NetworkClientHandler.Scheduler#schedule(java.lang.Runnable, long, java.util.concurrent.TimeUnit)
		 */
		@Override
		public void schedule(Runnable run, long delay, TimeUnit unit) {
			if(!stopped)
				this.eventLoop.schedule(run, delay, unit);
		}

		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.dispatcher.handlers.NetworkClientHandler.Scheduler#stop()
		 */
		@Override
		public void stop() {
			this.stopped = true;
			this.eventLoop.shutdownGracefully();
		}
	
	}
	
	/**
	 * @author A-2394
	 *
	 */
	private interface Scheduler {
		
		/**
		 * Schedule the task  
		 * @param run
		 * @param delay
		 * @param unit
		 */
		void schedule(Runnable run, long delay, TimeUnit unit);
		
		/**
		 * Stops the scheduler and reject further work.
		 */
		void stop();
	}
	
	/**
	 * @author A-2394
	 * 
	 */
	private static final class ClusterMemberAddress {
		InetAddress host;
		int port;
		
		public ClusterMemberAddress(String address){
			String[] pair = address.split(":");
			try {
				this.host = InetAddress.getByName(pair[0]);
			} catch (UnknownHostException e) {
				throw new IllegalArgumentException("invalid address", e);
			}
			this.port = Integer.parseInt(pair[1]);
		}
		
		public boolean isThisBox(){
			if(this.host.isAnyLocalAddress() || this.host.isLoopbackAddress())
				return true;
			try {
				return NetworkInterface.getByInetAddress(this.host) != null;
			} catch (SocketException e) {
				return false;
			}
		}
	}
	
}
