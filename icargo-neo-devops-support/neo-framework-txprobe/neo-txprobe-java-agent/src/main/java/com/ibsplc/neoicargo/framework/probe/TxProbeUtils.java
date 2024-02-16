/*
 * TxProbeUtils.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;


import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.probe.http.HttpProbeConfigMXBean;
import com.ibsplc.neoicargo.framework.probe.sql.SqlProbeConfigMXBean;
import com.ibsplc.neoicargo.framework.probe.ws.WebServiceProbeConfigMXBean;
import org.apache.commons.io.output.StringBuilderWriter;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * Set of util helper methods
 */
public class TxProbeUtils {

    private TxProbeUtils() {
        // util class
    }

    public static final Object[] EMPTY_ARRAY = new Object[0];
    public static final String TXPROBE_CORRELATION_HEADER = "TxProbeCorrelationId";

    public static String renderGenericArguments(Object... args) {
        if (args == null || args.length == 0)
            return "";
        StringBuilder sbul = stringBuilder(500);
        JsonBuilder jbul = new JsonBuilder(sbul);
        return jbul.serializeObject(args);
    }

    public static String renderMap(Map<?, ?> map) {
        if (map == null)
            return "null";
        if (map.isEmpty())
            return "{}";
        StringBuilder sbul = stringBuilder(250);
        JsonBuilder jbul = new JsonBuilder(sbul);
        jbul.serializeMap(map);
        return sbul.toString();
    }

    public static String renderException(Throwable t) {
        StringBuilder sbul = stringBuilder(15000);
        renderException(t, sbul);
        return sbul.toString();
    }

    private static void renderException(Throwable t, StringBuilder sbul) {
        StringBuilderWriter sbw = new StringBuilderWriter(sbul);
        PrintWriter pw = new PrintWriter(sbw);
        t.printStackTrace(pw);
        pw.flush();
    }

    public static boolean isIncluded(String suspect, String[] includes, String[] excludes) {
        boolean included = (includes == null || includes.length == 0) || Arrays.binarySearch(includes, suspect) >= 0;
        boolean excluded = (excludes != null) && Arrays.binarySearch(excludes, suspect) >= 0;
        return included && !excluded;
    }

    static void registerMBean(MBeanServer mbeanServer, TxProbeConfig configBean) throws JMException {
        Hashtable<String, String> props = new Hashtable<>(2); // NOSONAR
        props.put("type", TxProbeConfigMXBean.TYPE);
        props.put("tenant", configBean.getTenant());
        ObjectName objectName = new ObjectName(TxProbeConfigMXBean.DOMAIN, props);
        mbeanServer.registerMBean(configBean, objectName);
        // http
        if (configBean.getHttpProbeConfig() != null) {
            props = new Hashtable<>(2);
            props.put("type", HttpProbeConfigMXBean.TYPE);
            props.put("tenant", configBean.getTenant());
            objectName = new ObjectName(TxProbeConfigMXBean.DOMAIN, props);
            mbeanServer.registerMBean(configBean.getHttpProbeConfig(), objectName);
        }
        // webservice
        if (configBean.getWebServiceProbeConfig() != null) {
            props = new Hashtable<>(2);
            props.put("type", WebServiceProbeConfigMXBean.TYPE);
            props.put("tenant", configBean.getTenant());
            objectName = new ObjectName(TxProbeConfigMXBean.DOMAIN, props);
            mbeanServer.registerMBean(configBean.getWebServiceProbeConfig(), objectName);
        }
        // sql probe
        if (configBean.getSqlProbeConfig() != null) {
            props = new Hashtable<>(2);
            props.put("type", SqlProbeConfigMXBean.TYPE);
            props.put("tenant", configBean.getTenant());
            objectName = new ObjectName(TxProbeConfigMXBean.DOMAIN, props);
            mbeanServer.registerMBean(configBean.getSqlProbeConfig(), objectName);
        }
    }

    public static String renderLogonAttribute(LoginProfile profile) {
        StringBuilder sbul = stringBuilder(1200);
        Map<String, Object> map = map(64);
        map.put("expires_in", profile.getExpires_in());
        map.put("airport_code", profile.getAirport_code());
        map.put("default_warehouse_code", profile.getDefault_warehouse_code());
        map.put("own_airline_code", profile.getOwn_airline_code());
        map.put("own_airline_identifier", profile.getOwn_airline_identifier());
        map.put("station_code", profile.getStation_code());
        map.put("language", profile.getLanguage());
        map.put("country", profile.getCountry());
        map.put("stationCode", profile.getStationCode());
        map.put("ownAirlineCode", profile.getOwnAirlineCode());
        map.put("airportCode", profile.getAirportCode());
        map.put("defaultWarehouseCode", profile.getDefaultWarehouseCode());
        map.put("companyCode", profile.getCompanyCode());
        map.put("roleGroupCode", profile.getRoleGroupCode());
        map.put("userId", profile.getUserId());
        map.put("ownAirlineIdentifier", profile.getOwnAirlineIdentifier());
        map.put("icgToken", profile.getIcgToken());
        new JsonBuilder(sbul).serializeMap(map);
        map.clear();
        return sbul.toString();
    }

    /**
     * Returns a shared instance of StringBuilder. Care must be taken that this instance is not reused in
     * a recursive call.
     *
     * @param size
     * @return
     */
    private static StringBuilder stringBuilder(int size) {
        Thread thread = Thread.currentThread();
        if (Thread.currentThread() instanceof ExecutorServiceProvider.ProbeThread) {
            ExecutorServiceProvider.ProbeThread pthread = (ExecutorServiceProvider.ProbeThread) thread;
            return pthread.stringBuilder();
        }
        return new StringBuilder(size);
    }

    private static Map<String, Object> map(int size) {
        Thread thread = Thread.currentThread();
        if (Thread.currentThread() instanceof ExecutorServiceProvider.ProbeThread) {
            ExecutorServiceProvider.ProbeThread pthread = (ExecutorServiceProvider.ProbeThread) thread;
            return pthread.hashMap();
        }
        return new HashMap<>(size);
    }
}
