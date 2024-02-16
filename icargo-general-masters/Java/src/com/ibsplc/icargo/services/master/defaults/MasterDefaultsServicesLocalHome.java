package com.ibsplc.icargo.services.master.defaults;

import javax.ejb.EJBLocalHome;


public interface MasterDefaultsServicesLocalHome extends EJBLocalHome{
	/**
     * Creates a default instance of Session Bean: MasterDefaultsServices
     * @throws CreateException
     */
    public com.ibsplc.icargo.services.master.defaults.MasterDefaultsLocalServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
}
