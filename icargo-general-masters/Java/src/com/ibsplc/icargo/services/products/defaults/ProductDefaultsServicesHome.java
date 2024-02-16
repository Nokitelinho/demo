package com.ibsplc.icargo.services.products.defaults;


/**
 * Home interface for Enterprise Bean: ProductDefaultsServices
 */
public interface ProductDefaultsServicesHome extends javax.ejb.EJBHome {
    /**
     * Creates a default instance of Session Bean: ProductDefaultsServices
     * @throws RemoteException
     * @throws CreateException
     */
    public ProductDefaultsServices create()
        throws javax.ejb.CreateException,java.rmi.RemoteException;
}