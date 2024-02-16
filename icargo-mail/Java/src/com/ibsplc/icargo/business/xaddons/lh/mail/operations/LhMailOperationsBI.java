/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.LhMailOperationsBI.java
 *
 *	Created by	:	203168
 *	Created on	:	17-Oct-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;


/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.LhMailOperationsBI .java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	17-Oct-2022	:	Draft
 */
public interface LhMailOperationsBI {
	
	/**
	 * rgdf
	 * 	Method		:	MailTrackingDefaultsBI.findMailbagIdForMailTag
	 *	Added by 	:	203168
	 * 	Used for 	:
	 *	Parameters	:	@param hbaMarkingVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	MailbagVO
	 */
	public void markHba(
			HbaMarkingVO hbaMarkingVO)
				throws SystemException, RemoteException;
	
	public HbaMarkingVO findHbaDetails(HbaMarkingVO hbaMarkingVO) throws SystemException, RemoteException;
	public Collection<DespatchDetailsVO> findDespatchDetails(
			DespatchDetailsFilterVO despatchDetailsFilter) throws SystemException,RemoteException;
	

}
