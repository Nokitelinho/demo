/*
 * TxProbeContext.java Created on 25/11/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jens
 */
public class TxProbeContext {

    static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new ThreadLocal<>() {

        /* (non-Javadoc)
         * @see java.lang.ThreadLocal#initialValue()
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(4);
        }

    };

    /**
     * Get the value associated with the current execution thread
     *
     * @param key
     * @return
     */
    public static <T> T threadContext(String key) {
        return (T) THREAD_CONTEXT.get().get(key);
    }

    /**
     * Get the value associated with the current execution thread or the default
     * value
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public static <T> T threadContext(String key, T defaultVal) {
        return (T) THREAD_CONTEXT.get().getOrDefault(key, defaultVal);
    }

    /**
     * Applies the value in the current thread context
     */
    public static void threadContextPut(String key, Object val) {
        THREAD_CONTEXT.get().put(key, val);
    }

    /**
     * Removes the key-value from the current thread context
     */
    public static Object threadContextRemove(String key) {
        return THREAD_CONTEXT.get().remove(key);
    }


    /**
     * Clears the current thread context
     */
    public static void threadContextClear() {
        THREAD_CONTEXT.get().clear();
    }

}
