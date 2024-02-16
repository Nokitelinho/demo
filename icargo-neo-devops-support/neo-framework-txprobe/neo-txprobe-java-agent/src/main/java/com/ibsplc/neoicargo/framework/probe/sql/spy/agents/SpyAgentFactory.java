/*
 * SpyAgentFactory.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql.spy.agents;

/**
 * A provider for a SpyLogDelegator. This allows a single switch point to
 * abstract away which logging system to use for spying on JDBC calls.
 *
 * The SLF4J logging facade is used, which is a very good general purpose facade
 * for plugging into numerous java logging systems, simply and easily.
 *
 * @author Arthur Blake
 */
public class SpyAgentFactory {
    /**
     * Do not allow instantiation. Access is through static method.
     */
    private SpyAgentFactory() {
    }

    /**
     * The logging system of choice.
     */
    private static final ProxySqlAgent agent = initAgent();

    private static ProxySqlAgent initAgent() {
        return new ProxySqlAgent(new DummySqlAgent());
    }

    /**
     *
     * @param agent
     */
    public static synchronized void setSpyAgent(SqlPrincipalAgent agent) {
        SpyAgentFactory.agent.setActual(agent);
    }

    /**
     * Get the default SpyLogDelegator for logging to the logger.
     *
     * @return the default SpyLogDelegator for logging to the logger.
     */
    public static SqlPrincipalAgent _getSpyAgent() {
        return SpyAgentFactory.agent;
    }
}
