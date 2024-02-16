/*
 * HttpNetworkServer.java Created on 07/01/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
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
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author jens
 */
@Singleton
@Named("httpNetworkServer")
public class HttpNetworkServer extends ChannelInitializer<SocketChannel> implements NettyComponent, ChannelFutureListener {

    static final Logger logger = LoggerFactory.getLogger(HttpNetworkServer.class);

    @Inject
    @Named("httpDispatcherEndpoint")
    ChannelInboundHandler dispatcherEndpoint;

    @ConfigProperty(name = "httpServer.port")
    int serverPort;

    @ConfigProperty(name = "httpServer.acceptorThreads", defaultValue = "2")
    int acceptorThreads;

    @ConfigProperty(name = "httpServer.channelType", defaultValue = "nio")
    String channelType;

    private ServerBootstrap serverBootstrap;
    private SocketChannel socketChannel;

    public enum ChannelType {
        nio, nativeio
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.netty.NettyComponent#start()
     */
    @Override
    public void start() {
        serverBootstrap = new ServerBootstrap();
        TCPNetworkServer.ChannelType ctype = resolveChannelType();
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

    private TCPNetworkServer.ChannelType resolveChannelType(){
        boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");
        if("auto".equals(channelType)){
            //return isLinux ? ChannelType.nativeio : ChannelType.nio;
            return TCPNetworkServer.ChannelType.nio;
        }
        TCPNetworkServer.ChannelType ctype = TCPNetworkServer.ChannelType.valueOf(channelType);
        if(TCPNetworkServer.ChannelType.nativeio == ctype){
            // nativeio only supported in linux x86_64
            if(!isLinux)
                ctype = TCPNetworkServer.ChannelType.nio;
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
        ch.pipeline().addLast(new HttpRequestDecoder());
        ch.pipeline().addLast(new HttpResponseEncoder());
        ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024, false)); // 512 kb is the max message size
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
     * @param dispatcherEndpoint the dispatcherEndpoint to set
     */
    public void setDispatcherEndpoint(ChannelInboundHandler dispatcherEndpoint) {
        this.dispatcherEndpoint = dispatcherEndpoint;
    }

    /**
     * @return the serverPort
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the acceptorThreads
     */
    public int getAcceptorThreads() {
        return acceptorThreads;
    }

    /**
     * @param acceptorThreads the acceptorThreads to set
     */
    public void setAcceptorThreads(int acceptorThreads) {
        this.acceptorThreads = acceptorThreads;
    }

    /**
     * @return the channelType
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * @param channelType the channelType to set
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
