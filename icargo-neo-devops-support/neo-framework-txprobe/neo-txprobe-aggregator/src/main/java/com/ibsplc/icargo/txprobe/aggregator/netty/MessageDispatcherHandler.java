/*
 * MessageDispatcherHandler.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.netty;

import com.ibsplc.icargo.txprobe.aggregator.Dispatcher;
import com.ibsplc.icargo.txprobe.aggregator.utils.ByteArrayOutputStreamPool;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
@Sharable
@Singleton
@Named("dispatcherEndpoint")
public class MessageDispatcherHandler extends SimpleChannelInboundHandler<FasterByteArrayOutputStream>{

	static final Logger logger = LoggerFactory.getLogger(MessageDispatcherHandler.class);

	private final Dispatcher<FasterByteArrayOutputStream, ByteBuf> dispatcher;

	@Inject
	public MessageDispatcherHandler(@Named("dispatcher.in") Dispatcher<FasterByteArrayOutputStream, ByteBuf> dispatcher){
		this.dispatcher = dispatcher;
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FasterByteArrayOutputStream msg) throws Exception {
		try{
			if(logger.isDebugEnabled())
				logger.debug("message received : {} bytes", msg.size());
			dispatcher.dispatch(msg);
		}finally{
			ByteArrayOutputStreamPool.IOBUFFER_POOL.returnToPool(msg);
		}
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}

	/**
	 * @return the dispatcher
	 */
	public Dispatcher<FasterByteArrayOutputStream, ByteBuf> getDispatcher() {
		return dispatcher;
	}

	
}
