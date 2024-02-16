/*
 * EmbargoServicesHome.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.reco.defaults;

import javax.ejb.EJBLocalHome;

/**
 * @author A-1358
 *
 */
public interface EmbargoRulesServicesLocalHome extends EJBLocalHome {
    
    /**
     * Creates a default instance of Session Bean
     * @throws CreateException
     * @throws RemoteException
     */
    public EmbargoRulesLocalServices create()
        throws javax.ejb.CreateException,java.rmi.RemoteException;
}
