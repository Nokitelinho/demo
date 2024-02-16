

package com.ibsplc.icargo.services.customermanagement.defaults;
/**
 * Home interface for Enterprise Bean: CustomerMgmntDefaultsServicesEJB
 */
public interface CustomerMgmntDefaultsServicesLocalHome extends javax.ejb.EJBLocalHome {
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
    public com.ibsplc.icargo.services.customermanagement.defaults.CustomerMgmntDefaultsLocalServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
}