/*
 * ULDDefaultsServicesLocalHome.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.uld.defaults;
/**
 * @author 
 * Home interface for Enterprise Bean: ULDDefaultsEJB
 */
public interface ULDDefaultsServicesLocalHome extends javax.ejb.EJBLocalHome {
   /**
    * Creates a default instance of Session Bean: ULDDefaultsEJB
    * 
    * @return
    * @throws javax.ejb.CreateException
    * @throws java.rmi.RemoteException
    */
    public com.ibsplc.icargo.services.uld.defaults.ULDDefaultsLocalServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
    
}