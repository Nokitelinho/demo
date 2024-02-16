/*
 * JsonBuilder.java Created on 15-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

import static com.ibsplc.neoicargo.framework.core.util.ClassesUtil.isUnassignedValue;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			15-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * A simple object to json serializer class
 */
class JsonBuilder {

    static final char LB = '\n';
    static final int MAX_SIZE = Integer.getInteger("neo.txprobe.maxArgSize", 1024 * 1024 * 1); // defaulted to 1MB

    private final StringBuilder sbul;
    private int intend;
    private Set<Object> dejavu = Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>(8));
    private boolean markerInserted;

    private NumberFormat numberFormat;

    public JsonBuilder(int size) {
        this.sbul = new StringBuilder(size);
    }

    public JsonBuilder(StringBuilder sbul) {
        this.sbul = sbul;
    }

    public String serializeObject(Object... args) {
        if (args == null || args.length == 0)
            return "";
        sbul.append('{').append(LB);
        intend++;
        for (int x = 0; x < args.length; x++) {
            if (x > 0) {
                sbul.append(',').append(LB);
            }
            applyIntend();
            Object arg = args[x];
            sbul.append('\"').append(x).append('\"').append(':');
            serializeArg(arg);
        }
        intend--;
        sbul.append('}');
        return sbul.toString();
    }

    protected void applyIntend() {
        for (int x = 0; x < intend; x++)
            sbul.append(' ');
    }

    protected void serializeArg(Object arg) {
        // check if the size has exceeded
        if (!builderOpen(true)) {
            return;
        } else if (arg == null) {
            sbul.append("null");
        } else {
            boolean isDejavu = !this.dejavu.add(arg);
            if (isDejavu)
                sbul.append("\"${cyclic reference of ").append(arg.getClass().getName()).append("}\"");
            else if (arg instanceof Collection<?>)
                serializeCollection(Collection.class.cast(arg));
            else if (arg instanceof Map<?, ?>)
                serializeMap(Map.class.cast(arg));
            else if (arg instanceof String || arg instanceof Character)
                sbul.append('\"').append(arg).append('\"');
            else if (arg instanceof Number || arg instanceof Boolean)
                sbul.append(arg);
            else if (arg.getClass().isArray() && arg instanceof Object[])
                serializeArray((Object[]) arg);
            else
                sbul.append('\"').append(arg).append('\"');
            if (!isDejavu)
                this.dejavu.remove(arg);
        }
    }

    public void serializeMap(Map<?, ?> map) {
        sbul.append('{').append(LB);
        intend++;
        applyIntend();
        boolean isFirst = true;
        Iterator<?> itr = map.entrySet().iterator();
        while (itr.hasNext() && builderOpen(false)) {
            Entry<?, ?> e = (Entry<?, ?>) itr.next();
            if (isFirst)
                isFirst = false;
            else
                sbul.append(',');
            sbul.append('\"').append(e.getKey()).append('\"').append(':');
            serializeArg(e.getValue());
        }
        sbul.append(LB);
        intend--;
        applyIntend();
        sbul.append('}');
    }

    public void serializeCollection(Collection<?> list) {
        sbul.append('[').append(LB);
        intend++;
        applyIntend();
        boolean isFirst = true;
        Iterator<?> itr = list.iterator();
        while (itr.hasNext() && builderOpen(false)) {
            Object e = itr.next();
            if (isFirst)
                isFirst = false;
            else
                sbul.append(',');
            serializeArg(e);
        }
        sbul.append(LB);
        intend--;
        applyIntend();
        sbul.append(']');
    }

    public void serializeArray(Object[] list) {
        sbul.append('[').append(LB);
        intend++;
        applyIntend();
        boolean isFirst = true;
        for (int x = 0; x < list.length && builderOpen(false); x++) {
            Object e = list[x];
            if (isFirst)
                isFirst = false;
            else
                sbul.append(',');
            serializeArg(e);
        }
        sbul.append(LB);
        intend--;
        applyIntend();
        sbul.append(']');
    }

    public void serializeAbstractVO(Object bean) {
        Method[] methods = bean.getClass().getMethods();
        sbul.append('{').append(LB);
        intend++;
        applyIntend();
        sbul.append("\"class\" : \"").append(bean.getClass().getName()).append('\"');
        for (int x = 0; x < methods.length && builderOpen(false); x++) {
            Method meth = methods[x];
            String name = meth.getName();
            if (meth.getParameterTypes().length > 0)
                continue;
            if ("getClass".equals(name))
                continue;
            boolean isGetter = name.startsWith("get") || name.startsWith("is") || name.startsWith("has");
            if (!isGetter)
                continue;
            Object value = ReflectionUtils.invokeMethod(meth, bean);
            if (isUnassignedValue(value))
                continue;
            sbul.append(':').append(LB);
            applyIntend();
            sbul.append('\"').append(name).append('\"').append(':');
            serializeArg(value);
        }
        intend--;
        sbul.append(LB);
        applyIntend();
        sbul.append('}');
    }

    private boolean builderOpen(boolean skipKey) {
        boolean sizeNotExed = this.sbul.length() < MAX_SIZE;
        if (!sizeNotExed) {
            if (!this.markerInserted && skipKey)
                this.sbul.append("\"${truncated as size exceeds ").append(MAX_SIZE).append(" chars}\"");
            else if (!this.markerInserted && !skipKey)
                this.sbul.append("\"_comment_\":  \"${truncated as size exceeds ").append(MAX_SIZE).append(" chars}\"");
            this.markerInserted = true;
        }
        return sizeNotExed;
    }
}
