/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.bs.mail.operations.BaseMailOperationsServicesHome.java
 *
 *	Created by	:	A-7531
 *	Created on	:	30-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.xaddons.bs.mail.operations;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.bs.mail.operations.BaseMailOperationsServicesHome.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	30-Aug-2017	:	Draft
 */
public interface BaseMailOperationsServicesHome extends javax.ejb.EJBHome{
	
	 public com.ibsplc.icargo.services.xaddons.bs.mail.operations.BaseMailOperationsServices create()
		        throws javax.ejb.CreateException,
		        java.rmi.RemoteException;

}
