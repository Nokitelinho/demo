/*
 * SqlPrincipalAgent.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.sql.spy.agents;


/*
 *
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Jan-2016       		Jens J P 			First Draft
 */

import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;

/**
 * @author A-2394
 */
public interface SqlPrincipalAgent {
    /**
     * Determine if any of the jdbc or sql loggers are turned on.
     *
     * @return true if any of the jdbc or sql loggers are enabled at error level
     * or higher.
     */
     boolean isAgentActive();

    /**
     * Called when a spied upon method throws an Exception.
     *
     * @param spy        the Spy wrapping the class that threw an Exception.
     * @param methodCall a description of the name and call parameters of the method
     *                   generated the Exception.
     * @param e          the Exception that was thrown.
     * @param sql        optional sql that occured just before the exception occured.
     * @param execTime   optional amount of time that passed before an exception was
     *                   thrown when sql was being executed. caller should pass -1 if
     *                   not used
     */
     void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime);

    /**
     * Called when spied upon method call returns.
     *
     * @param spy        the Spy wrapping the class that called the method that
     *                   returned.
     * @param methodCall a description of the name and call parameters of the method
     *                   that returned.
     * @param returnMsg  return value converted to a String for integral types, or
     *                   String representation for Object return types this will be
     *                   null for void return types.
     */
     void methodReturned(Spy spy, String methodCall, String returnMsg);

    /**
     * Called when a spied upon object is constructed.
     *
     * @param spy              the Spy wrapping the class that called the method that
     *                         returned.
     * @param constructionInfo information about the object construction
     */
     void constructorReturned(Spy spy, String constructionInfo);

    /**
     * Special call that is called only for JDBC method calls that contain SQL.
     *
     * @param spy        the Spy wrapping the class where the SQL occured.
     * @param methodCall a description of the name and call parameters of the method
     *                   that generated the SQL.
     * @param sql        sql that occured.
     */
     SpyContext onSqlExecutionStart(Spy spy, String methodCall, String sql);

    /**
     *
     * @param context
     */
    void onSqlExecutionEnd(SpyContext context);

    /**
     * Called whenever a new connection spy is created.
     *
     * @param spy ConnectionSpy that was created.
     */
     void connectionOpened(Spy spy);

    /**
     * Called whenever a connection spy is closed.
     *
     * @param spy ConnectionSpy that was closed.
     */
     void connectionClosed(Spy spy);

    /**
     * Log a Setup and/or administrative log message for log4jdbc.
     *
     * @param msg message to log.
     */
     void debug(String msg);

}