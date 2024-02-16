package com.ibsplc.icargo.services.addons.mail.operations;


public interface AddonsMailOperationsServicesHome extends javax.ejb.EJBHome{
	
	 public com.ibsplc.icargo.services.addons.mail.operations.AddonsMailOperationsServices create()
		        throws javax.ejb.CreateException,
		        java.rmi.RemoteException;

}
