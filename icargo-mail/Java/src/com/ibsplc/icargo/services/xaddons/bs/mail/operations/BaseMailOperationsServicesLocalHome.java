
package com.ibsplc.icargo.services.xaddons.bs.mail.operations;

import javax.ejb.EJBLocalHome;


public interface BaseMailOperationsServicesLocalHome extends EJBLocalHome {

	
	public BaseMailOperationsLocalServices create()
			throws javax.ejb.CreateException, java.rmi.RemoteException;
}
