/*
 * PayloadUnmarshallHandler.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.inbound;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ibsplc.icargo.txprobe.aggregator.PayloadHolder;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayInputStream;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayOutputStream;
import com.ibsplc.icargo.txprobe.aggregator.utils.ReusableOutputStream;
import com.ibsplc.icargo.txprobe.api.FieldMetadata;
import com.lmax.disruptor.EventHandler;
import org.iq80.snappy.Snappy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.Entry;
import static java.util.Map.entry;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@Singleton
@Named("in.payloadUnmarshaller")
public class ProbePayloadUnmarshallHandler implements EventHandler<PayloadHolder> {

    static final Logger logger = LoggerFactory.getLogger(ProbePayloadUnmarshallHandler.class);

    static final int SIZE_CMP_BUFF = 1024 * 1024 * 25; // 25Mib
    static final int SIZE_RAW_BUFF = 1024 * 1024 * 50; // 50Mib

    // reusable buffers
    private ReusableOutputStream rawBufferCache = new FasterByteArrayOutputStream(SIZE_RAW_BUFF);
    private ReusableOutputStream compressBufferCache = new FasterByteArrayOutputStream(SIZE_CMP_BUFF);
    private final JsonFactory jsonFactory;
    private ArrayList<Map.Entry<String, Object>> jsonFields = new ArrayList<>(32);

    public ProbePayloadUnmarshallHandler() {
        this.jsonFactory = JsonFactory.builder().build();
    }

    /* (non-Javadoc)
     * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
     */
    @Override
    public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
        if (event.isJson())
            unmarshallJson(event, this.rawBufferCache);
        else
            unmarshallObject(event, this.rawBufferCache, this.compressBufferCache);
    }

    private void unmarshallJson(PayloadHolder event, ReusableOutputStream rawBuffer)
            throws Exception {
        byte[] buffer;
        if (event.getExtra() != null) {
            if (event.getSize() > rawBuffer.byteArray().length) {
                logger.warn("Json rawBuffer resized to : {} ", event.getSize());
                rawBuffer = new FasterByteArrayOutputStream(event.getSize() + 8);
            }
            event.write(rawBuffer.byteArray());
            buffer = rawBuffer.byteArray();
        } else
            buffer = event.getBuffer();
        try (JsonParser parser = jsonFactory.createParser(buffer, 0, event.getSize())) {
            Object[][] answer = parseJsonTree(parser);
            event.setData(answer);
            event.setRawSize(event.getSize());
        }
    }

    private Object[][] parseJsonTree(JsonParser parser) throws IOException {
        this.jsonFields.clear();
        String field = null;
        for (JsonToken token = parser.nextToken(); token != JsonToken.END_OBJECT; token = parser.nextToken()) {
            switch (token) {
                case FIELD_NAME:
                    field = parser.getCurrentName();
                    break;
                case VALUE_NULL:
                    field = null;
                    break;
                case VALUE_NUMBER_FLOAT:
                    if (field != null) {
                        this.jsonFields.add(entry(field, parser.getValueAsDouble()));
                        field = null;
                    }
                    break;
                case VALUE_NUMBER_INT:
                    if (field != null) {
                        if(FieldMetadata.START_TIME.equals(field))
                            this.jsonFields.add(entry(field, parser.getValueAsLong()));
                        else
                            this.jsonFields.add(entry(field, parser.getValueAsInt()));
                        field = null;
                    }
                    break;
                case VALUE_STRING:
                    if (field != null) {
                        this.jsonFields.add(entry(field, parser.getValueAsString()));
                        field = null;
                    }
                    break;
                case VALUE_FALSE:
                case VALUE_TRUE:
                    if (field != null) {
                        this.jsonFields.add(entry(field, parser.getValueAsBoolean()));
                        field = null;
                    }
                    break;
                default:
                    break;
            }
        }
        Object[][] tuple = new Object[this.jsonFields.size()][2];
        for (int x = 0; x < tuple.length; x++) {
            Entry<String, Object> e = this.jsonFields.get(x);
            tuple[x][0] = e.getKey();
            tuple[x][1] = e.getValue();
        }
        return tuple;
    }


    private static void unmarshallObject(PayloadHolder event, ReusableOutputStream rawBuffer, ReusableOutputStream compressBuffer)
            throws Exception {
        compressBuffer.reset();
        byte[] compressed = null;
        int maxUnCompressSize = Snappy.getUncompressedLength(event.getBuffer(), 0);
        if (maxUnCompressSize > rawBuffer.byteArray().length) {
            logger.warn("rawBuffer resized to : {}", maxUnCompressSize);
            rawBuffer = new FasterByteArrayOutputStream(maxUnCompressSize + 1024);
        }
        if (event.getSize() > compressBuffer.byteArray().length) {
            logger.warn("compressBuffer resized to : {}", event.getSize());
            compressBuffer = new FasterByteArrayOutputStream(event.getSize());
        }
        // check if we need a byte copy
        if (event.getExtra() == null)
            compressed = event.getBuffer();
        else {
            event.write(compressBuffer.byteArray());
            compressed = compressBuffer.byteArray();
        }

        int unCompressSize = Snappy.uncompress(compressed, 0, event.getSize(), rawBuffer.byteArray(), 0);
        ObjectInputStream ois = new ObjectInputStream(new FasterByteArrayInputStream(rawBuffer.byteArray(), 0, unCompressSize));
        Object[][] answer = (Object[][]) ois.readUnshared();
        event.setData(answer);
        ois.close();
        event.setRawSize(unCompressSize);
    }
}
