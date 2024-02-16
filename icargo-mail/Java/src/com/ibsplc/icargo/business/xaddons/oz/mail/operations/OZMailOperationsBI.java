/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.oz.mail.operations.MailOperationsBI.java
 *
 *	Created by	:	A-7531
 *	Created on	:	23-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.oz.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFilterVO;


/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.oz.mail.operations.MailOperationsBI.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	23-Aug-2017	:	Draft
 */
public interface OZMailOperationsBI {
	
	/**
	 * rgdf
	 * 	Method		:	MailTrackingDefaultsBI.findMailbagIdForMailTag
	 *	Added by 	:	a-7531 on 11-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	MailbagVO
	 */
	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber)
				throws SystemException, RemoteException;
/**
 * 
 * 	Method		:	MailOperationsBI.saveMailBookingDetails
 *	Added by 	:	A-7531 on 25-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param validScannedMailVOs
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws MailHHTBusniessException
 *	Parameters	:	@throws MailMLDBusniessException
 *	Parameters	:	@throws MailTrackingBusinessException 
 *	Return type	: 	void
 */
	public void saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,MailBookingDetailVO mailBookingDetailVO)
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException;


/**
 * 
 * 	Method		:	OZMailOperationsBI.dettachMailBookingDetails
 *	Added by 	:	A-7531 on 08-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param selectedMailBagVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws MailHHTBusniessException
 *	Parameters	:	@throws MailMLDBusniessException
 *	Parameters	:	@throws MailTrackingBusinessException 
 *	Return type	: 	void
 */

	public void dettachMailBookingDetails(Collection<MailbagVO> selectedMailBagVO)
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException;

/**
 * 
 * 	Method		:	OZMailOperationsBI.fetchBookedFlightDetails
 *	Added by 	:	A-7531 on 08-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param shipmentPrefix
 *	Parameters	:	@param masterDocumentNumber
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 *	Return type	: 	Collection<MailBookingDetailVO>
 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode,
			String shipmentPrefix, String masterDocumentNumber)
				throws SystemException, RemoteException;
	
/**
 * 	
 * 	Method		:	OZMailOperationsBI.saveMailBookingFlightDetails
 *	Added by 	:	A-7531 on 11-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param selectedMailBagVO
 *	Parameters	:	@param mailBookingDetailVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws MailHHTBusniessException
 *	Parameters	:	@throws MailMLDBusniessException
 *	Parameters	:	@throws MailTrackingBusinessException 
 *	Return type	: 	void
 */
	public void saveMailBookingFlightDetails(Collection<MailbagVO> selectedMailBagVO,Collection<MailBookingDetailVO> selectedMailBookingDetailVOs)
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException;


}
