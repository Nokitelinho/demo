/*
 * TxProbeFrameDecoder.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.netty;

import com.ibsplc.icargo.txprobe.aggregator.utils.ByteArrayOutputStreamPool;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@Singleton
@Named("frameDecoder")
public class TxProbeFrameDecoder extends ByteToMessageDecoder {

    public static final byte[] FRAME_HEADER = new byte[]{'T', 'X', 'P', 'H'};//54 58 50 48
    public static final byte[] FRAME_TRAILER = new byte[]{'T', 'X', 'P', 'T'};//54 58 50 54

    /* (non-Javadoc)
     * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 12) {
            return;
        }
        in.markReaderIndex();
        byte[] frame = new byte[4];
        in.readBytes(frame);
        if (!Arrays.equals(FRAME_HEADER, frame)) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("header mismatch");
        }
        int size = in.readInt();
        frame = new byte[4];
        in.readBytes(frame);
        if (!Arrays.equals(FRAME_TRAILER, frame)) {
            in.resetReaderIndex();
            throw new CorruptedFrameException("trailer mismatch");
        }
        if (in.readableBytes() < size) {
            in.resetReaderIndex();
            return;
        }
        FasterByteArrayOutputStream bos = ByteArrayOutputStreamPool.IOBUFFER_POOL.getFromPool();
        bos.reset();
        in.readBytes(bos, size);
        out.add(bos);
    }

}
