/*
 * Utils.java Created on 20-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.utils;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			20-Jan-2016       		Jens J P 			First Draft
 */

import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Pattern;

/**
 * @author A-2394
 */
public class Utils {

    static Pattern PATTERN_SQL_CVAR = Pattern.compile("'([^']*)'", Pattern.MULTILINE);
    static Pattern PATTERN_SQL_NUM = Pattern.compile("[^A-Za-z]?-?\\d+(\\.\\d+)?[^A-Za-z]?", Pattern.MULTILINE);

    /**
     * Returns human readable format for byte size.
     *
     * @param bytes
     * @param si
     * @return
     */
    public static String formatBytes(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String findSqlHash(String sql) {
        String _sql = PATTERN_SQL_CVAR.matcher(sql).replaceAll("?");
        _sql = PATTERN_SQL_NUM.matcher(_sql).replaceAll("?");
        return DigestUtils.md5Hex(_sql);
    }

}
