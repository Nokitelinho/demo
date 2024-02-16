package com.ibsplc.icargo.services.master.defaults;

import javax.ejb.EJBHome;


public interface MasterDefaultsServicesHome extends EJBHome{
	/**
     * Creates a default instance of Session Bean: MasterDefaultsServices
     * @throws CreateException
     */
    public com.ibsplc.icargo.services.master.defaults.MasterDefaultsServices create()
        throws javax.ejb.CreateException,
        java.rmi.RemoteException;
}
