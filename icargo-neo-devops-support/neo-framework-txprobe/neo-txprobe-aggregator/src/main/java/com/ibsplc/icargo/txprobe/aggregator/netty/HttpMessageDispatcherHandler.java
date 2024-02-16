/*
 * HttpMessageDispatcherHandler.java Created on 07/01/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.txprobe.aggregator.netty;

import com.ibsplc.icargo.txprobe.aggregator.Dispatcher;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author jens
 */
@Singleton
@ChannelHandler.Sharable
@Named("httpDispatcherEndpoint")
public class HttpMessageDispatcherHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    static final Logger logger = LoggerFactory.getLogger(HttpMessageDispatcherHandler.class);

    static final DefaultFullHttpResponse RES_OK = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    static final DefaultFullHttpResponse RES_BAD = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

    static final String URI = "/txprobe/probe-json";
    static final String AUTH_HEADER = "TxProbeAuthToken";

    private final String authHeaderToken;
    private final Dispatcher<FasterByteArrayOutputStream, ByteBuf> dispatcher;

    @Inject
    public HttpMessageDispatcherHandler(@ConfigProperty(name = "httpServer.authHeaderToken") Optional<String> authHeaderToken,
                                        @Named("dispatcher.in") Dispatcher<FasterByteArrayOutputStream, ByteBuf> dispatcher) {
        this.authHeaderToken = authHeaderToken.orElse(null);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (fullHttpRequest.method() != HttpMethod.PUT || !URI.equals(fullHttpRequest.uri())) {
            logger.warn("Bad Request {}", fullHttpRequest.toString());
            writeError(channelHandlerContext, HttpUtil.isKeepAlive(fullHttpRequest), "E0");
        } else {
            if (logger.isDebugEnabled())
                logger.debug("Message Headers {}", fullHttpRequest.headers());
            // do auth with pre shared keys
            if (authHeaderToken != null && !authHeaderToken.isEmpty()) {
                String authHeaderClientValue = fullHttpRequest.headers().get(AUTH_HEADER);
                if (!authHeaderToken.equals(authHeaderClientValue)) {
                    writeError(channelHandlerContext, HttpUtil.isKeepAlive(fullHttpRequest), "E1");
                    return;
                }
            }
            dispatcher.dispatchJson(fullHttpRequest.content());
            writeSuccess(channelHandlerContext, HttpUtil.isKeepAlive(fullHttpRequest));
        }
    }


    static void writeError(ChannelHandlerContext channelHandlerContext, boolean keepalive, String message) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        HttpUtil.setKeepAlive(response, true);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        if (keepalive) {
            channelHandlerContext.writeAndFlush(response);
        } else {
            channelHandlerContext.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    static void writeSuccess(ChannelHandlerContext channelHandlerContext, boolean keepalive) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, 0);
        //response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        if (keepalive) {
            HttpUtil.setKeepAlive(response, true);
            channelHandlerContext.writeAndFlush(response, channelHandlerContext.voidPromise());
        } else {
            channelHandlerContext.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);
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
}
