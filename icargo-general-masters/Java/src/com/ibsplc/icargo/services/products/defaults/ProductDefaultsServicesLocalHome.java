package com.ibsplc.icargo.services.products.defaults;


/**
 * Home interface for Enterprise Bean: ProductDefaultsServices
 */
public interface ProductDefaultsServicesLocalHome extends javax.ejb.EJBLocalHome {
    /**
     * Creates a default instance of Session Bean: ProductDefaultsServices
     * @throws RemoteException
     * @throws CreateException
     */
    public ProductDefaultsLocalServices create()
        throws javax.ejb.CreateException,java.rmi.RemoteException;
}