/*
 * MailtrackingMRAProxy.java Created on AUG 12, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
//import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadDetailVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
/*
*
* Revision History
* Version	 	Date      		    Author			Description
* 0.1			AUG 12, 2008 	  	A-3227			Initial draft
*
*/
@Module("mail")
@SubModule("mra")
public class MailtrackingMRAProxy extends SubSystemProxy{
	private Log log = LogFactory.getLogger("MailtrackingMRAProxy");

    private static final String SERVICE_NAME = "MAIL_MRA";

    private MailTrackingMRABI constructService()
            throws ServiceNotAccessibleException {
    	   return (MailTrackingMRABI) getService(SERVICE_NAME);
    }

    /**
     * @author A-3227 RENO K ABRAHAM
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws ProxyException
     */
	public Collection<FlownMailSegmentVO> findFlightDetails(FlownMailFilterVO filterVO)
	throws SystemException,ProxyException{
		log.entering("MailtrackingMRAProxy", "findFlightDetails");
		
		Collection<FlownMailSegmentVO> flownMailSegmentVOs=null;
        try {
        	flownMailSegmentVOs = constructService()
                    .findFlightDetails(filterVO);
        } catch(RemoteException e) {
            throw new SystemException(e.getMessage(), e);
        } catch(ServiceNotAccessibleException e) {
            throw new ProxyException(e.getMessage(), e);
        }
        log.exiting("MailtrackingMRAProxy", "findFlightDetails");
        return flownMailSegmentVOs;
	}
	
	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param flightValidationVO
	 * @param flownMailSegmentVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void importFlownMails(FlightValidationVO flightValidationVO,Collection<FlownMailSegmentVO> flownMailSegmentVOs)
	throws SystemException,ProxyException{
		log.entering("MailtrackingMRAProxy", "importFlownMails");
		try {
			constructService().importFlownMails(flightValidationVO, flownMailSegmentVOs,null,null);
        } catch(RemoteException e) {
            throw new SystemException(e.getMessage(), e);
        } catch(ServiceNotAccessibleException e) {
            throw new ProxyException(e.getMessage(), e);
        }
        log.exiting("MailtrackingMRAProxy", "importFlownMails");
	}
	/**
	 * @author a-2107
	 * @param offloadDetailVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void saveIrregularityDetails(Collection<OffloadDetailVO> offloadDetailVOs)
	throws SystemException,ProxyException{
//		log.entering("MailtrackingMRAProxy", "saveIrregularityDetails");
//		try{
//			constructService().saveIrregularityDetails(offloadDetailVOs);
//		 }catch(RemoteException e) {
//            throw new SystemException(e.getMessage(), e);
//        } catch(ServiceNotAccessibleException e) {
//            throw new ProxyException(e.getMessage(), e);
//        }
        log.exiting("MailtrackingMRAProxy", "saveIrregularityDetails");
	}
	
	/**
	 * @author a-2107
	 * @param 
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void updateIrregularityForReassign(Collection<OffloadDetailVO> offloaddetailsvos)
	throws SystemException,ProxyException{
		log.entering("MailtrackingMRAProxy", "updateIrregularityForReassign");
//		try{
//			constructService().updateIrregularityForReassign(offloaddetailsvos);
//		 }catch(RemoteException e) {
//            throw new SystemException(e.getMessage(), e);
//        } catch(ServiceNotAccessibleException e) {
//            throw new ProxyException(e.getMessage(), e);
//        }
        log.exiting("MailtrackingMRAProxy", "updateIrregularityForReassign");
	}

	/**
	 * 
	 * 	Method		:	MailtrackingMRAProxy.findMailbagBillingStatus
	 *	Added by 	:	a-8331 on 25-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	
	public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException,ProxyException, RemoteException {
		
		
		log.entering("MailtrackingMRAProxy", "findMailbagBillingStatus");
		try {
			return constructService().findMailbagBillingStatus(mailbagvo);
        } catch(ServiceNotAccessibleException e) {
            throw new ProxyException(e.getMessage(), e);
        }
		
	}
	/**
	 * 
	 * 	Method		:	MailtrackingMRAProxy.voidMailbags
	 *	Added by 	:	a-8331 on 25-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingDetails
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */

	public void voidMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetails) throws RemoteException, SystemException, ProxyException {
		log.entering("MailtrackingMRAProxy", "voidMailbags");
		try {
			constructService().voidMailbags((Collection<DocumentBillingDetailsVO>) documentBillingDetails);
        } catch(ServiceNotAccessibleException e) {
            throw new ProxyException(e.getMessage(), e);
        }
		
		
	}	
	
	/**
	 * 	Method		:	MailOperationsMRAProxy.importConsignmentDataToMra
	 *	Added by 	:	A-4809 on Nov 20, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentDocumentVO 
	 *	Return type	: 	void
	 */
	public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO)
	throws SystemException,ProxyException{
		log.entering("MailtrackingMRAProxy", "importConsignmentDataToMra");
		try {
			constructService().importConsignmentDataToMra(consignmentDocumentVO);
        } catch(RemoteException e) {
            throw new SystemException(e.getMessage(), e);
        } catch(ServiceNotAccessibleException e) {
            throw new ProxyException(e.getMessage(), e);
        }
        log.exiting("MailtrackingMRAProxy", "importConsignmentDataToMra");
	}
	
	
	/**
	 * 
	 * 	Method		:	MailtrackingMRAProxy.isMailbagInMRA
	 *	Added by 	:	A-5219 on 07-May-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param mailSeq
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	boolean
	 */
	public boolean isMailbagInMRA(String companyCode, long mailSeq) throws SystemException,ProxyException{
		
		boolean isMailbagPresent = false;
		try{
			isMailbagPresent = constructService().isMailbagInMRA(companyCode,mailSeq);
		}catch(RemoteException e) {
			isMailbagPresent = false;
        } catch(ServiceNotAccessibleException e) {
        	isMailbagPresent = false;
        }
		return isMailbagPresent;
	}
	
}
