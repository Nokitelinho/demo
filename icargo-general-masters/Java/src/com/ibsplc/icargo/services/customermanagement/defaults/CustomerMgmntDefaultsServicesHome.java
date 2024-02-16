

package com.ibsplc.icargo.services.customermanagement.defaults;
/**
 * Home interface for Enterprise Bean: CustomerMgmntDefaultsServicesEJB
 */
public interface CustomerMgmntDefaultsServicesHome extends javax.ejb.EJBHome {
    /**
     * Creates a default instance of Session Bean: CustomerMgmntDefaultsServicesEJB
     */
	/**
	 * @return
	 * 
	 * @exception javax.ejb.CreateException
	 * @exception java.rmi.RemoteException
	 * 
	 */
    public com.ibsplc.icargo.services.customermanagement.defaults.CustomerMgmntDefaultsServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
}