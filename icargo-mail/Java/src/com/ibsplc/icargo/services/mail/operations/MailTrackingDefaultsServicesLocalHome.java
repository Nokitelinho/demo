/*
 * MailTrackingDefaultsServicesLocalHome.java Created on May 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.operations;
/*
 * @author
 */
/**
 * Home interface for Enterprise Bean: MailTrackingDefaults
 */
public interface MailTrackingDefaultsServicesLocalHome extends javax.ejb.EJBLocalHome {
    /**
     * Creates a default instance of Session Bean: MailTrackingDefaults 
     * @return
     * @throws CreateException
     * @throws RemoteException
     */
    public com.ibsplc.icargo.services.mail.operations.MailTrackingDefaultsLocalServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
}
