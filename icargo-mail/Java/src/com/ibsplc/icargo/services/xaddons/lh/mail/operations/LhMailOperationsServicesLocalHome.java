
package com.ibsplc.icargo.services.xaddons.lh.mail.operations;

import javax.ejb.EJBLocalHome;


public interface LhMailOperationsServicesLocalHome extends EJBLocalHome {

	
	public LhMailOperationsServices create()
			throws javax.ejb.CreateException, java.rmi.RemoteException;
}
