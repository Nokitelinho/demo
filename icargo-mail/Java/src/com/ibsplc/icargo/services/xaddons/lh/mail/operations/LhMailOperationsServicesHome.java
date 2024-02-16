/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.lh.mail.operations.LhMailOperationsServicesHome.java
 *
 *	Created by	:	203168
 *	Created on	:	20-sep-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.xaddons.lh.mail.operations;


public interface LhMailOperationsServicesHome extends javax.ejb.EJBHome{
	
	 public com.ibsplc.icargo.services.xaddons.lh.mail.operations.LhMailOperationsServices create()
		        throws javax.ejb.CreateException,
		        java.rmi.RemoteException;

}
