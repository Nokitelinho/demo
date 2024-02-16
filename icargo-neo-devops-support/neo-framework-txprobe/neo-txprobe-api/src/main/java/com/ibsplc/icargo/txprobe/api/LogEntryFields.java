/*
 * LogEntry.java Created on 15/01/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.txprobe.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jens
 */
public interface LogEntryFields {

    String INDEX_NAME = "icargo_log_index";

    String LTIMESTAMP = "@timestamp";
    String LTHREADID = "thread_name";
    String LSTACKTRACE = "stack_trace";
    String LMESSAGE = "message";
    String LHOST = "host";
    String LLEVEL = "level";
    String LLOGGER = "logger_name";
    String LLEVEL_VALUE = "level_value";
    String LVERSION = "@version";


    static Map<String, FieldMetadata> getFieldMetadata(){
        Map<String, FieldMetadata> fmd = new HashMap<String, FieldMetadata>(16);
        fmd.put(LTIMESTAMP, new FieldMetadata(LTIMESTAMP, Date.class.getName(), true, false, false));
        fmd.put(FieldMetadata.CORRELATION_ID, new FieldMetadata(FieldMetadata.CORRELATION_ID, String.class.getName(), true, false, false));
        fmd.put(LTHREADID, new FieldMetadata(LTHREADID, String.class.getName(), true, false, false));
        fmd.put(LSTACKTRACE, new FieldMetadata(LSTACKTRACE, String.class.getName(), true, false, true));
        fmd.put(LMESSAGE, new FieldMetadata(LMESSAGE, String.class.getName(), true, false, true));
        fmd.put(LHOST, new FieldMetadata(LHOST, String.class.getName(), true, false, false));
        fmd.put(LLEVEL, new FieldMetadata(LLEVEL, String.class.getName(), true, false, false));
        fmd.put(LLOGGER, new FieldMetadata(LLOGGER, String.class.getName(), true, false, true));
        fmd.put(LLEVEL_VALUE, new FieldMetadata(LLEVEL_VALUE, Integer.class.getName(), true, false, false));
        fmd.put(LVERSION, new FieldMetadata(LVERSION, String.class.getName(), true, false, false));
        fmd.put(FieldMetadata.SERVICE_APP_NAME, new FieldMetadata(FieldMetadata.SERVICE_APP_NAME, String.class.getName(), true, false, false));
        fmd.put(FieldMetadata.PROBE_TYPE, new FieldMetadata(FieldMetadata.PROBE_TYPE, String.class.getName(), true, false, false));
        return fmd;
    }

}
