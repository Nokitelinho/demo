/*
 * MailTrackingMRAServicesHome.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.mra;

import javax.ejb.EJBHome;

/**
 * @author A-1556
 *
 */
public interface MailTrackingMRAServicesHome extends EJBHome {

	    /**
	     * Creates a default instance of Session Bean: MailTrackingMRAServicesEJB
	     * @throws java.rmi.RemoteException
	     * @throws javax.ejb.CreateException
	     */
	    public MailTrackingMRAServices create()
	        throws javax.ejb.CreateException,
	        java.rmi.RemoteException;


}
