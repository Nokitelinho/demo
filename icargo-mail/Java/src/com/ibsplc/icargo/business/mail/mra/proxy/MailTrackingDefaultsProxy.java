/*
 * MailTrackingDefaultsProxy.java Created on Mar 9, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI;
import com.ibsplc.icargo.business.mail.operations.vo.DSNAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1945
 */

/*
 *
 * Revision History
 * Version	 	Date      		    Author			Description
 * 0.1			Mar 9, 2007 	  	A-1945			Initial draft
 *
 */
public class MailTrackingDefaultsProxy extends SubSystemProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");

	private static final String SERVICE_NAME = "MAIL_OPERATIONS";

	private MailTrackingDefaultsBI constructService()
	throws ServiceNotAccessibleException {
		return (MailTrackingDefaultsBI) getService(SERVICE_NAME);
	}

	/**
	 *
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public PostalAdministrationVO findPoaDetails(String companyCode,
			String officeOfExchange)
	throws ProxyException, SystemException {
		log.entering("MailTrackingDefaultsProxy", "findPoaDetails");
		PostalAdministrationVO postalAdministrationVO = null;
		try {
			postalAdministrationVO = constructService()
			.findPADetails(companyCode, officeOfExchange);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "findPoaDetails");
		return postalAdministrationVO;
	}

	/**
	 * @author a-2049
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public PostalAdministrationVO findPostalAdminDetails(String companyCode, String paCode)
	throws SystemException, ProxyException {

		log.entering("MailTrackingDefaultsProxy", "findPostalAdminDetails");

		PostalAdministrationVO postalAdministrationVO = null;

		try {
			postalAdministrationVO = constructService()
			.findPACode(companyCode, paCode);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}

		log.exiting("MailTrackingDefaultsProxy", "findPostalAdminDetails");
		return postalAdministrationVO;

	}
	/**
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public String findPAForOfficeOfExchange(
			String companyCode, String officeOfExchange)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "findPAForOfficeOfExchange");
		String poaCode=null;
		try {
			poaCode = constructService()
			.findPAForOfficeOfExchange(companyCode,officeOfExchange);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "findPAForOfficeOfExchange");
		return poaCode;
	}

	/**
	 * @param companyCode
	 * @param subclass
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public String validateMailSubClass(
			String companyCode,String subclass)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "validateMailSubClass");
		String flag="N";
		boolean fl;
		try {
			fl = constructService().validateMailSubClass(companyCode,subclass);
			if(fl){
				flag="Y"; 
			}
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "validateMailSubClass");
		return flag;
	}
	
	/**
	 * Audit DSN called from MRA . 
	 * Collection of DSNs as params passed and inserted to MTKDSNAUD table. 
	 * @param companyCode
	 * @param subclass
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void auditDSNsForMRA (Collection<DSNAuditVO> dsnAuditVOs)
	throws SystemException,ProxyException{
		
		log.entering("MailTrackingDefaultsProxy", "auditDSNsForMRA");
		/*try {
			constructService().auditDSNsForMRA (dsnAuditVOs);
			
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
		log.exiting("MailTrackingDefaultsProxy", "auditDSNsForMRA");
		
	}

	public PostalAdministrationDetailsVO validatePoaDetailsForBilling(PostalAdministrationDetailsVO postalAdministrationDetailsVO)throws SystemException,ProxyException {
		
		log.entering("MailTrackingDefaultsProxy", "validatePoaDetailsForBilling");
		PostalAdministrationDetailsVO pADetailsResultVO = null;
		try {
			pADetailsResultVO = constructService()
			.validatePoaDetails(postalAdministrationDetailsVO);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "validatePoaDetailsForBilling");
		return pADetailsResultVO;
	
	}

	/**
	 * @param companyCode
	 * @param subclass
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public PostalAdministrationVO findPACode(
			String companyCode, String paCode)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "validateMailSubClass");
		PostalAdministrationVO postalAdministrationVO = null;
		try {
			postalAdministrationVO = constructService().findPACode(companyCode,paCode);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "validateMailSubClass");
		return postalAdministrationVO;
	}
	/**
	 * @param companyCode
	 * @param subclass
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Page<PostalAdministrationVO>  findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultSize)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "validateMailSubClass");
		Page<PostalAdministrationVO> postalAdministrationVOs=null;
		try {
			postalAdministrationVOs = constructService().findPALov( companyCode,
	    			 paCode,  paName,  pageNumber, defaultSize);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("MailTrackingDefaultsProxy", "validateMailSubClass");
		return postalAdministrationVOs;
	}
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "findAllPACodes");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			postalAdministrationVOs = constructService().findAllPACodes( generateInvoiceFilterVO);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}  
		log.exiting("MailTrackingDefaultsProxy", "findAllPACodes");
		return postalAdministrationVOs;
	}
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsProxy.auditMailbagsForMRA
	 *	Added by 	:	a-4809 on Apr 3, 2014
	 * 	Used for 	:   to stamp audit entries for mailbag
	 *	Parameters	:	@param dsnAuditVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void auditMailbagsForMRA (Collection<MailbagAuditVO> mailbagAuditVOs)
	throws SystemException,ProxyException{
		log.entering("MailTrackingDefaultsProxy", "auditMailbagsForMRA");
		/*try {
			constructService().auditMailbagsForMRA (mailbagAuditVOs);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
		log.exiting("MailTrackingDefaultsProxy", "auditMailbagsForMRA");
	}
	
	
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsProxy.findMailSubClass
	 *	Added by 	:	A-7929 on Jan 22, 2019
	 * 	Used for 	: 
	 *	Parameters	:	@param companyCode,code
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<MailSubClassVO> 
	 * @throws ServiceNotAccessibleException 
	 * @throws RemoteException 
	 */
	public Collection<MailSubClassVO>  findMailSubClass (String companyCode, String code)
	throws SystemException,ProxyException, RemoteException, ServiceNotAccessibleException{
		log.entering("MailTrackingDefaultsProxy", "findMailSubClass");
		Collection<MailSubClassVO> mailSubClassVOs = null;
		mailSubClassVOs = constructService().findMailSubClassCodes( companyCode,code);
		return mailSubClassVOs;
		
		
	}
/**
 * @author A-5526
 * @param mailIdr
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws ProxyException
 */
	public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) throws SystemException,ProxyException{
		long mailsequenceNumber=0;
		try {
		mailsequenceNumber=constructService().findMailBagSequenceNumberFromMailIdr(mailIdr,companyCode);
		} catch(RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch(ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		return mailsequenceNumber;
	}
	/***
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws RemoteException 
	 */
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws RemoteException{
		String processStatus = null;
		try {
			processStatus = constructService().processMailDataFromExcel(fileUploadFilterVO);
		} catch (PersistenceException | SystemException
				| ServiceNotAccessibleException e) {
		}
				return processStatus;
	}	
  /**
	 * 
	 * 	Method		:	MailTrackingDefaultsProxy.findMailbagHistories
	 *	Added by 	:	A-7929 on Jan 25, 2019
	 * 	Used for 	: 
	 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<MailSubClassVO> 
	 * @throws SystemException 
	 * @throws ServiceNotAccessibleException 
	 * @throws RemoteException 
	 */
	

	public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ServiceNotAccessibleException {
		log.entering("MailTrackingDefaultsProxy", "findMailbagHistories");
		Collection<MailbagHistoryVO> mailbagHistoryVOs = null;
		mailbagHistoryVOs = constructService().findMailbagHistories( companyCode,mailIdr,mailSeqNum);
		return mailbagHistoryVOs;
	}
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsProxy.validateFrmToDateRange
	 *	Added by 	:	A-8527 on March 21, 2019
	 * 	Used for 	: 
	 *	Parameters	:	@param uSPSPostalCalendarFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<USPSPostalCalendarVO> 
	 * @throws SystemException 
	 * @throws ServiceNotAccessibleException 
	 * @throws RemoteException 
	 */
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange( USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws RemoteException, SystemException, ServiceNotAccessibleException {
		log.entering("MailTrackingDefaultsProxy", "validateFrmToDateRange");
		Collection<USPSPostalCalendarVO>uspsPostalCalanderyVOs = null;
		uspsPostalCalanderyVOs = constructService().validateFrmToDateRange(uSPSPostalCalendarFilterVO);
		return uspsPostalCalanderyVOs;
	}
/**
 * @author A-7371
 * @param uspsPostalCalendarFilterVO
 * @return
 * @throws RemoteException
 * @throws SystemException
 * @throws ServiceNotAccessibleException
 */
	public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO) throws RemoteException, SystemException, ServiceNotAccessibleException {
		log.entering("MailTrackingDefaultsProxy", "findInvoicPeriodDetails");
		USPSPostalCalendarVO uspsPostalCalanderVO = null;
		uspsPostalCalanderVO = constructService().findInvoicPeriodDetails(uspsPostalCalendarFilterVO);
		return uspsPostalCalanderVO; 
	}
	
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsProxy.findApprovedForceMajeureDetails
	 *	Added by 	:	A-5526 on Jun 15, 2020
	 * 	Used for 	: 
	 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<ForceMajeureRequestVO> 
	 * @throws SystemException 
	 * @throws ServiceNotAccessibleException 
	 * @throws RemoteException 
	 */
	

	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ServiceNotAccessibleException {
		log.entering("MailTrackingDefaultsProxy", "findApprovedForceMajeureDetails");
		Collection<ForceMajeureRequestVO> forceMajeureRequestVOs = null;
		forceMajeureRequestVOs = constructService().findApprovedForceMajeureDetails( companyCode,mailIdr,mailSeqNum);
		return forceMajeureRequestVOs;
	}
	
	public boolean validateMailbag(Collection<MailbagVO> mailbagVOs)
			throws RemoteException, SystemException, ServiceNotAccessibleException{
		boolean isValidMailbags = false;
		try {
			isValidMailbags = constructService().validateMailBags(mailbagVOs);
		} catch (MailTrackingBusinessException | SystemException  e) {
			isValidMailbags = false;
			log.log(Log.FINE, e);
		}
		return isValidMailbags;
	}


	public long insertMailbagAndHistory(MailbagVO mailbagVO)
			throws RemoteException, SystemException, ServiceNotAccessibleException{
		return constructService().insertMailbagAndHistory(mailbagVO);
	}
}
