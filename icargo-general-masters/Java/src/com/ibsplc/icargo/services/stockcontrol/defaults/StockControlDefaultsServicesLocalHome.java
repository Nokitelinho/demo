/*
 * StockControlDefaultsServicesLocalHome.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.stockcontrol.defaults;

import javax.ejb.EJBLocalHome;

/**
 * @author A-1358
 *
 */
public interface StockControlDefaultsServicesLocalHome extends EJBLocalHome {
    
    /**
     * Creates a default instance of Session Bean: ProductDefaultsServices
     * @return
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */
    public StockControlDefaultsLocalServices create()
        throws javax.ejb.CreateException,java.rmi.RemoteException;
}
