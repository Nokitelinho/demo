package com.ibsplc.icargo.services.addons.mail.operations;

import javax.ejb.EJBLocalHome;


public interface AddonsMailOperationsServicesLocalHome extends EJBLocalHome {

	
	public AddonsMailOperationsLocalServices create()
			throws javax.ejb.CreateException, java.rmi.RemoteException;
}
