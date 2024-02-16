/*
 * RdbmsSpecifics.java Created on 20-Sep-2013
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.sql.spy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulate sql formatting details about a particular relational database
 * management system so that accurate, useable SQL can be composed for that
 * RDMBS.
 */
public class RdbmsSpecifics {

    public static final RdbmsSpecifics DEFAULT = new RdbmsSpecifics();

    /**
     * Default constructor.
     */
    RdbmsSpecifics() {
    }

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");

    /**
     * Format an Object that is being bound to a PreparedStatement parameter,
     * for display. The goal is to reformat the object in a format that can be
     * re-run against the native SQL client of the particular Rdbms being used.
     * This class should be extended to provide formatting instances that format
     * objects correctly for different RDBMS types.
     *
     * @param object jdbc object to be formatted.
     * @return formatted dump of the object.
     */
    String formatParameterObject(Object object) {
        if (object == null) {
            return "NULL";
        } else {
            if (object instanceof String) {
                return quote(escapeString(object.toString()));
            } else if (object instanceof Date) {
                return quote(dateFormat.format(object));
            } else if (object instanceof Boolean) {
                return Boolean.class.cast(object).toString();
            } else {
                return object.toString();
            }
        }
    }

    protected String quote(String in) {
        StringBuilder out = new StringBuilder(in.length() + 2);
        out.append('\'').append(in).append('\'');
        return out.toString();
    }

    /**
     * Make sure string is escaped properly so that it will run in a SQL query
     * analyzer tool. At this time all we do is double any single tick marks. Do
     * not call this with a null string or else an exception will occur.
     *
     * @return the input String, escaped.
     */
    String escapeString(String in) {
        if (in.indexOf('\'') < 0)
            return in;
        StringBuilder out = new StringBuilder(in.length() + 2);
        for (int i = 0, j = in.length(); i < j; i++) {
            char c = in.charAt(i);
            if (c == '\'') {
                out.append(c);
            }
            out.append(c);
        }
        return out.toString();
    }

}
