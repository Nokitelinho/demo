package com.ibsplc.icargo.services.xaddons.ey.uld.defaults;

import javax.ejb.EJBLocalHome;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.ey.uld.defaults.EYUldDefaultsServicesLocalHome.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */
public interface EYUldDefaultsServicesLocalHome extends EJBLocalHome {
	public EYUldDefaultsLocalServices create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
