/**
 *	Java file	: 	com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.MailOperationsDelegate.java
 *
 *	Created by	:	A-7531
 *	Created on	:	23-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;

import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.MailOperationsDelegate.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	23-Aug-2017	:	Draft
 */

@Module("bsmail")
@SubModule("operations")
public class BaseMailOperationsDelegate extends BusinessDelegate{
	
    private Log log = LogFactory.getLogger("MAILOPERATIONS_DEFAULTS");
	
	private static final String MODULE = "BaseMailOperationsDelegate";
	
	
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDelegate.findMailBookingAWBs
	 *	Added by 	:	a-7531 on 10-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	MailBookingFilterVO
	 * @throws BusinessDelegateException 
	 */
	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber ) throws BusinessDelegateException {
		return despatchRequest("findMailBookingAWBs",mailBookingFilterVO,pageNumber);
	}


	/**
	 * 	Method		:	MailOperationsDelegate.saveMailBookingDetails
	 *	Added by 	:	A-7531 on 25-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailBagVO 
	 *	Return type	: 	void
	 */
	public int saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,MailBookingDetailVO mailBookingDetailVO,CarditEnquiryFilterVO carditEnquiryFilterVO)throws BusinessDelegateException 
	{
		log.entering(MODULE, "saveMailBookingDetails");
		 return despatchRequest("saveMailBookingDetails", selectedMailBagVO,mailBookingDetailVO,carditEnquiryFilterVO);
		
	}


	/**
	 * 	Method		:	BaseMailOperationsDelegate.dettachMailBookingDetails
	 *	Added by 	:	A-7531 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailBagVO 
	 *	Return type	: 	void
	 */
	public void dettachMailBookingDetails(
			Collection<MailbagVO> selectedMailBagVO,CarditEnquiryFilterVO carditEnquiryFilterVO) throws BusinessDelegateException{
		// TODO Auto-generated method stub
		log.entering(MODULE, "dettachMailBookingDetails");
		 despatchRequest("dettachMailBookingDetails", selectedMailBagVO,carditEnquiryFilterVO);
		
	}


	/**
	 * 	Method		:	BaseMailOperationsDelegate.fetchBookedFlightDetails
	 *	Added by 	:	A-7531 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber 
	 *	Return type	: 	void
	 * @return 
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode,
			String shipmentPrefix, String masterDocumentNumber)throws BusinessDelegateException {
		
		return despatchRequest("fetchBookedFlightDetails",companyCode,shipmentPrefix, masterDocumentNumber);
	}


	/**
	 * 	Method		:	BaseMailOperationsDelegate.saveMailBookingFlightDetails
	 *	Added by 	:	A-7531 on 11-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailBagVO
	 *	Parameters	:	@param mailBookingDetailVO 
	 *	Return type	: 	void
	 * @throws BusinessDelegateException 
	 */
	public void saveMailBookingFlightDetails(
			Collection<MailbagVO> selectedMailBagVO,
			Collection<MailBookingDetailVO> selectedMailBookingDetailVOs) throws BusinessDelegateException {
		log.entering(MODULE, "saveMailBookingFlightDetails");
		 despatchRequest("saveMailBookingFlightDetails", selectedMailBagVO,selectedMailBookingDetailVOs);
		
	}

	/**
	 * @author A-8061
	 * @param selectedMailBagVO
	 * @throws BusinessDelegateException
	 */
	public void validateMailTags(Collection<MailbagVO> selectedMailBagVO) throws BusinessDelegateException{
		log.entering(MODULE, "validateMailTags");
		 despatchRequest("validateMailTags", selectedMailBagVO);

	}


}
