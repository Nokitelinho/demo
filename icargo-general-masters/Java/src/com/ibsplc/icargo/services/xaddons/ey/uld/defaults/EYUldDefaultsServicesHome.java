package com.ibsplc.icargo.services.xaddons.ey.uld.defaults;

import javax.ejb.EJBHome;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.ey.uld.defaults.EYUldDefaultsServicesHome.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */
public interface EYUldDefaultsServicesHome extends EJBHome {
	public EYUldDefaultsServices create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
