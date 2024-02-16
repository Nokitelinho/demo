/*
 * @(#) WSMessageInterceptor.java 1.0 Dec 03, 2010
 * Copyright 2004 -2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.neoicargo.framework.probe.ws;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Revision History
 * Revision 		Date      		Author			    Description
 * 1.0          Dec 03, 2010 		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public abstract class WSMessageInterceptor extends AbstractPhaseInterceptor<Message> {

    static final String INCOMING = "INC";
    static final String OUTGOING = "OUT";

    static final Logger logger = LoggerFactory.getLogger(WSMessageInterceptor.class);

    /**
     * @param phase
     */
    public WSMessageInterceptor(String phase) {
        super(phase);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.cxf.interceptor.Interceptor#handleFault(org.apache.cxf.message
     * .Message)
     */
    public void handleFault(Message message) {
        // fault has occurred in this invocation
        WSInterceptorContext ctx = interceptorContext(message, true);
        ctx.update(message);
        ctx.setFault(true);
        try {
            doHandle(message, true);
        } catch (Exception e) {
            logger.error("handleFault", e);
        }
    }

    private void handleInComing(InputStream is, Message message, boolean isFault) throws IOException {
        CachedOutputStream bos = new CachedOutputStream(Long.MAX_VALUE);// just a threshold
        IOUtils.copy(is, bos);
        bos.flush();
        is.close();
        message.setContent(InputStream.class, bos.getInputStream());
        // Use the XML encoding in the message to create the XML String
        StringBuilder builder = new StringBuilder(1024);
        String encoding = (message.get(Message.ENCODING) == null) ? "UTF-8" : (String) message.get(Message.ENCODING);
        //org.apache.commons.io.IOUtils.copy(bos.getInputStream(), writer, encoding);
        bos.writeCacheTo(builder, encoding, Long.MAX_VALUE);
        bos.close();
        process(message, builder.toString(), INCOMING, isFault);
    }

    private void handleInComing(Reader reader, Message message, boolean isFault) throws IOException {
        CachedWriter cachedWriter = new CachedWriter(Long.MAX_VALUE);
        IOUtils.copyAndCloseInput(reader, cachedWriter);
        message.setContent(Reader.class, cachedWriter.getReader());

        int size = (int) cachedWriter.size();
        size = size < 0 ? Integer.MAX_VALUE : size;
        StringBuilder sbul = new StringBuilder(size);
        cachedWriter.writeCacheTo(sbul, Long.MAX_VALUE);
        process(message, sbul.toString(), INCOMING, isFault);
    }

    private void handleOutgoing(OutputStream os, Message message, boolean isFault) {
        // Write the output while caching it for the log message
        CacheAndWriteOutputStream newOut = null;
        if (os instanceof CacheAndWriteOutputStream) {
            newOut = CacheAndWriteOutputStream.class.cast(os);
        } else {
            newOut = new CacheAndWriteOutputStream(os);
            message.setContent(OutputStream.class, newOut);
        }
        newOut.setThreshold(Long.MAX_VALUE);
        newOut.registerCallback(new OutgoingInterceptorCallback(message, isFault, os));
    }

    private void handleOutgoing(Writer writer, Message message, boolean isFault) {
        // Write the output while caching it for the log message
        OutgoingInterceptorWriterCallback cb = new OutgoingInterceptorWriterCallback(message, isFault);
        OutgoingInterceptorWriter newOut = null;
        if (writer instanceof OutgoingInterceptorWriter) {
            newOut = OutgoingInterceptorWriter.class.cast(writer);
            newOut.registerCallbacks(cb);
        } else {
            newOut = new OutgoingInterceptorWriter(writer, cb);
            message.setContent(Writer.class, newOut);
        }

       // resolveTypes(message);
    }

    private void resolveTypes(Message message) {
        List<Object> types = null;
        try {
            types = retrieveTypes(message);
        } catch (Exception e) {
            logger.error("resolveTypes", e);
        }
        // store the arguments/response in the current context
        if (types != null) {
            WSInterceptorContext ctx = interceptorContext(message, true);
            ctx.setXmlTypes(types);
        }

    }

    /**
     * Method to retrieve the argument/return types.
     *
     * @param message
     * @return List<Object> the argument/return depending on the current
     * invocation
     * @author A-2394
     */
    private List<Object> retrieveTypes(Message message) {
        Exchange exchange = message.getExchange();
        BindingOperationInfo operation = (BindingOperationInfo) exchange.get(BindingOperationInfo.class.getName());
        // if not able to resolve operation exit
        if (operation == null)
            return null;
        MessageContentsList objs = MessageContentsList.getContentsList(message);
        if (objs == null || objs.isEmpty())
            return null;
        boolean isRequestor = Boolean.TRUE.equals(message
                .containsKey(Message.REQUESTOR_ROLE));
        List<MessagePartInfo> parts = null;
        BindingMessageInfo bmsg = null;
        if (!isRequestor) {
            if (operation.getOutput() != null) {
                bmsg = operation.getOutput();
                parts = bmsg.getMessageParts();
            } else {
                // partial response to oneway
                return null;
            }
        } else {
            bmsg = operation.getInput();
            parts = bmsg.getMessageParts();
        }
        if (parts == null || parts.isEmpty() || objs == null)
            return null;
        List<Object> types = new ArrayList<>(parts.size());
        for (MessagePartInfo part : parts) {
            if (objs.hasValue(part))
                types.add(objs.get(part));
        }
        return types;
    }

    private void process(Message cxfMessage, String message, String direction, boolean isFault) {
        // being invoked before fault being generated.
        /*if (message == null || message.trim().length() == 0)
            return;*/
        try {
            if (isFault)
                processFault(cxfMessage, message, direction);
            else
                processMessage(cxfMessage, message, direction);
        } finally {
            // unset current context after processing
            clearInterceptorCtx(null);
        }
    }

    protected abstract void processMessage(String message, String direction);

    protected abstract void processFault(String message, String direction);

    protected void processMessage(Message cxfMessage, String message, String direction) {
        processMessage(message, direction);
    }

    protected void processFault(Message cxfMessage, String message, String direction) {
        processFault(message, direction);
    }

    private void doHandle(Message message, boolean isFault) throws IOException {
        OutputStream os = message.getContent(OutputStream.class);
        Writer writer = message.getContent(Writer.class);
        if (os != null || writer != null) {
            if (os != null)
                handleOutgoing(os, message, isFault);
            else
                handleOutgoing(writer, message, isFault);
        } else {
            InputStream is = message.getContent(InputStream.class);
            Reader reader = message.getContent(Reader.class);
            if (is != null)
                handleInComing(is, message, isFault);
            else if (reader != null)
                handleInComing(reader, message, isFault);

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
     * .Message)
     */
    public void handleMessage(Message message) throws Fault {
        ExchangeImpl exchange = (ExchangeImpl)message.getExchange();
        try {
            WSInterceptorContext ctx = interceptorContext(message, false);
            boolean isFault = ctx != null && ctx.isFault();
            doHandle(message, isFault);
        } catch (Exception e) {
            logger.error("handleMessage", e);
        }
    }

    protected String wsInterceptorContextKey() {
        return "WSMessageInterceptor.WSInterceptorContext.key";
    }

    protected WSInterceptorContext interceptorContext(Message message, boolean create) {
        WSInterceptorContext ctx = (WSInterceptorContext) message.getExchange().get(wsInterceptorContextKey());
        if (ctx == null && create) {
            ctx = new WSInterceptorContext(message);
            message.getExchange().put(wsInterceptorContextKey(), ctx);
        }
        return ctx;
    }

    protected void clearInterceptorCtx(Message message) {
        //threadContext.set(null);
    }

    public interface MessageEventHandler {

        void onMessage(String message);

    }

    /**
     * Callback impl
     *
     * @author A-2394
     */
    private class OutgoingInterceptorCallback implements CachedOutputStreamCallback {

        private final boolean isFault;
        private final Message cxfMessage;
        private final OutputStream original;

        public OutgoingInterceptorCallback(Message cxfMessage, boolean isFault, OutputStream orig) {
            this.isFault = isFault;
            this.cxfMessage = cxfMessage;
            this.original = orig;
        }

        public void onFlush(CachedOutputStream cos) {
            // ignore
        }

        public void onClose(CachedOutputStream cos) {
            String encoding = (String) cxfMessage.get(Message.ENCODING);
            StringBuilder payload = new StringBuilder();
            try {
                if (StringUtils.isEmpty(encoding))
                    cos.writeCacheTo(payload, Long.MAX_VALUE);
                else
                    cos.writeCacheTo(payload, encoding, Long.MAX_VALUE);
            } catch (IOException e) {
                logger.error("Error in onClose", e);
            }
            WSMessageInterceptor.this.process(this.cxfMessage, payload.toString(), OUTGOING, this.isFault);
            if (!(this.original instanceof CacheAndWriteOutputStream)) {
                try {
                    cos.lockOutputStream();
                    cos.resetOut(null, false);
                } catch (Exception ex) {
                    logger.error("Error in onClose", ex);
                }
                this.cxfMessage.setContent(OutputStream.class, this.original);
            }
        }
    }

    protected class OutgoingInterceptorWriterCallback {

        private final Message cxfMessage;
        private final boolean isFault;

        public OutgoingInterceptorWriterCallback(Message cxfMessage, boolean isFault) {
            super();
            this.cxfMessage = cxfMessage;
            this.isFault = isFault;
        }

        public void message(String message) {
            WSMessageInterceptor.this.process(cxfMessage, message, OUTGOING, isFault);
        }

        /**
         * @return the cxfMessage
         */
        public Message getCxfMessage() {
            return cxfMessage;
        }

    }

    protected static class OutgoingInterceptorWriter extends FilterWriter {

        StringBuilder sbul;
        OutgoingInterceptorWriterCallback defaultCallback;
        List<OutgoingInterceptorWriterCallback> auxCallbacks;

        public OutgoingInterceptorWriter(Writer writer, OutgoingInterceptorWriterCallback callback) {
            super(writer);
            if (!(writer instanceof StringWriter))
                this.sbul = new StringBuilder();
            this.defaultCallback = callback;
        }

        public void registerCallbacks(OutgoingInterceptorWriterCallback callback) {
            if (auxCallbacks == null)
                auxCallbacks = new ArrayList<>(3);
            auxCallbacks.add(callback);
        }

        public void write(int c) throws IOException {
            super.write(c);
            if (sbul != null)
                sbul.append(c);
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            super.write(cbuf, off, len);
            if (sbul != null)
                sbul.append(cbuf, off, len);
        }

        public void write(String str, int off, int len) throws IOException {
            super.write(str, off, len);
            if (sbul != null)
                sbul.append(str, off, len);
        }

        public void close() throws IOException {
            String answer = null;
            if (sbul != null) {
                answer = sbul.toString();
            } else
                answer = super.out.toString();
            defaultCallback.message(answer);
            defaultCallback.getCxfMessage().setContent(Writer.class, super.out);
            // notify rest
            if (this.auxCallbacks != null) {
                for (OutgoingInterceptorWriterCallback cbs : this.auxCallbacks) {
                    cbs.message(answer);
                }
            }
            super.close();
        }
    }

}
