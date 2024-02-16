/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.bs.mail.operations.MailOperationsServicesEJB.java
 *
 *	Created by	:	A-7531
 *	Created on	:	23-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.xaddons.bs.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseXaddonMailController;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseMailOperationsBI;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;




import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.bs.mail.operations.BaseMailOperationsEJB.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	23-Aug-2017	:	Draft
 */
public class BaseMailOperationsEJB extends AbstractFacadeEJB
implements BaseMailOperationsBI{


	private Log log = LogFactory.getLogger("mail.operations");

	private static final String MODULE = "BaseMailOperationsEJB";

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findMailBookingAWBs(com.ibsplc.icargo.business.mail.operations.vo.MailBookingFilterVO)
	 *	Added by 			: a-7531 on 11-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Page<MailBookingDetailVO> findMailBookingAWBs( 
			MailBookingFilterVO mailBookingFilterVO,int pageNumber)
				throws SystemException, RemoteException {
		log.entering("BaseMailOperationsEJB", "findMailBookingAWBs");
		return new BaseXaddonMailController().findMailBookingAWBs(mailBookingFilterVO,pageNumber);
 }


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.xaddons.bs.mail.operations.MailOperationsBI#saveMailBookingDetails(java.util.Collection)
	 *	Added by 			: A-7531 on 25-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailBagVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws MailHHTBusniessException
	 *	Parameters	:	@throws MailMLDBusniessException
	 *	Parameters	:	@throws MailTrackingBusinessException 
	 */
	public int saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,MailBookingDetailVO mailBookingDetailVO,CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, RemoteException {
		log.entering("BaseMailOperationsEJB", "saveMailBookingDetails");
		return new BaseXaddonMailController().saveMailBookingDetails(selectedMailBagVO,mailBookingDetailVO,carditEnquiryFilterVO);
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseMailOperationsBI#dettachMailBookingDetails(java.util.Collection)
 *	Added by 			: A-7531 on 11-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param selectedMailBagVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
	
	public void dettachMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, RemoteException {
		log.entering("BaseMailOperationsEJB", "dettachMailBookingDetails");
		new BaseXaddonMailController().dettachMailBookingDetails(selectedMailBagVO,carditEnquiryFilterVO);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseMailOperationsBI#fetchBookedFlightDetails(java.lang.String, java.lang.String, java.lang.String)
	 *	Added by 			: A-7531 on 11-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode,
			String shipmentPrefix, String masterDocumentNumber)
				throws SystemException, RemoteException {
		log.entering("BaseMailOperationsEJB", "fetchBookedFlightDetails");
		return new BaseXaddonMailController().fetchBookedFlightDetails( companyCode,
				 shipmentPrefix,  masterDocumentNumber);
	
}
	
	/**
	 * 
	 * 	Method		:	BaseMailOperationsEJB.saveMailBookingFlightDetails
	 *	Added by 	:	A-7531 on 11-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedMailBagVO
	 *	Parameters	:	@param mailBookingDetailVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void saveMailBookingFlightDetails(Collection<MailbagVO> selectedMailBagVO,Collection<MailBookingDetailVO> selectedMailBookingDetailVO)
			throws SystemException, RemoteException {
		log.entering("BaseMailOperationsEJB", "saveMailBookingFlightDetails");
		new BaseXaddonMailController().saveMailBookingFlightDetails(selectedMailBagVO,selectedMailBookingDetailVO);
	}
	
	/**
	 * @author A-8061
	 * @param selectedMailBagVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws MailTrackingBusinessException
	 */
	public void  validateMailTags(Collection<MailbagVO> selectedMailBagVO) 
			   throws SystemException,RemoteException{
		new BaseXaddonMailController().validateMailTags(selectedMailBagVO);
		
	}
	
	public boolean saveContainerforReassigns(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO)  throws SystemException, RemoteException{
		log.entering("BaseMailOperationsEJB", "saveContainerforReassigns");
 		return new BaseXaddonMailController().saveContainerforReassigns(containerDetailsVO,mailFlightSummaryVO);
	}
	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO)  throws SystemException, RemoteException{
		log.entering("BaseMailOperationsEJB", "saveContainerforReassigns");
 		return new BaseXaddonMailController().saveContainerTransfer(containerDetailsVO,mailFlightSummaryVO);
	}

}
