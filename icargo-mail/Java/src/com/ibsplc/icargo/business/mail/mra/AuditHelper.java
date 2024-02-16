/*
 * AuditHelper.java Created on Oct 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.DSNAuditVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log; 
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 * Holds helper methods for auditing DSNs from mra.
 */
public class AuditHelper {
	
	private static final String CLASS_NAME = "AuditHelper";
	
	private static final String MOD_NAM = "mail";
	
	private static final String SUB_MOD_NAM = "mra";
	
	private static final String BLANK = "";
	
	private static final String BILLING_STATUS="mra.airlinebilling.billingstatus";
	
	private static final String CCA_TYPE="mra.defaults.ccatype";
	
	private static final String GPA_BILLING_STATUS="mailtracking.mra.gpabilling.gpabillingstatus";
	
	
	private static LogonAttributes logon = null;
	
	/**
	 * Method to perform audit 
	 * @return
	 * @throws SystemException
	 */
	public static void auditDSNForRejectionMemo (RejectionMemoVO rejectionMemoVO) 
			throws SystemException {
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		
		log.entering (CLASS_NAME, "auditDSNForRejectionMemo");
		
		if (rejectionMemoVO == null) {
			return;
		}
		
		int csgseqnumber	= rejectionMemoVO.getCsgSeqNum();
		String csgdocnum	= rejectionMemoVO.getCsgDocNum();
		String companyCode	=rejectionMemoVO.getCompanycode();
        String billingBasis	= rejectionMemoVO.getBillingBasis();
        String poaCode		= rejectionMemoVO.getPoaCode();
        long mailSequenceNumber		= rejectionMemoVO.getMailSequenceNumber();
		
		if (csgseqnumber != 0 && csgdocnum != null && companyCode != null
				&& billingBasis != null && poaCode != null){
			
			try {
				
				// DSN Pks to be retreived from MTKMRABLGMST using billing basis 
				MRABillingMaster billingMaster = MRABillingMaster.find 
								(companyCode, mailSequenceNumber);
				
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				
				// Populate DSN Audit VO from Business VO
				Collection<DSNAuditVO> auditVOs = new ArrayList<DSNAuditVO>();
				DSNAuditVO auditVO = new DSNAuditVO (MOD_NAM, SUB_MOD_NAM, BLANK);
				
				// From that entity populate the DSNAuditVOs DSN pk details
				auditVO.setCompanyCode (companyCode);
				auditVO.setDsn (billingMaster.getDsn());
				auditVO.setOriginExchangeOffice (billingMaster.getOrgExchangeOffice());
				auditVO.setDestinationExchangeOffice (billingMaster.getDstExchangeOffice());
				auditVO.setMailCategoryCode (billingMaster.getMailCatagoryCode());
				
				String mailClass = billingMaster.getMailSubClass() == null 
								? "" : billingMaster.getMailSubClass().substring(0,1);
				auditVO.setMailSubclass (billingMaster.getMailSubClass());
				auditVO.setMailClass (mailClass);
				auditVO.setYear (billingMaster.getYear());
				
				StringBuilder remarks  =  new StringBuilder ("DSN Status Changed to Rejected");
	  			auditVO.setAdditionalInformation (remarks.toString());
	  			
	  			auditVO.setAuditRemarks (rejectionMemoVO.getRemarks());
	  			
				auditVO.setLastUpdateUser (logonAttributes.getUserId());
				auditVO.setStationCode (logonAttributes.getStationCode());
				auditVO.setActionCode(DSNAuditVO.DSN_REJMEM);
				auditVO.setTxnTime (new LocalDate (LocalDate.NO_STATION, Location.NONE, true));

				auditVOs.add (auditVO);
				
				// Call Mailtracking defaults auditDSNsForMRA for puuting entries in to MTKDSNAUD tables
				new MailTrackingDefaultsProxy ().auditDSNsForMRA (auditVOs);				
				
			} catch (FinderException e) {
				throw new SystemException (e.getMessage());
			} catch (ProxyException e) {
				throw new SystemException (e.getMessage());
			}
		}
	
		log.exiting (CLASS_NAME, "auditDSNForRejectionMemo");
		
	}
	
	

	/**
	 * Method to perform audit while changing billing status from GPA Invoice Enquiry screen
	 * @author Meenu Harry A-2565
	 * @param CN66DetailsVO cN66DetailsVO
	 * @throws SystemException
	 */
	public static  void auditDSNForBillingStatus (CN66DetailsVO cN66DetailsVO) 
			throws SystemException {
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		
		log.entering (CLASS_NAME, "auditDSNForBillingStatus");
		
		if (cN66DetailsVO == null) {
			return;
		}
		
		String companyCode = cN66DetailsVO.getCompanyCode();
  		String gpaCode = cN66DetailsVO.getGpaCode();
  		String billingBasis = cN66DetailsVO.getBillingBasis();
  		String conDocNum = cN66DetailsVO.getConsDocNo();
  		int conSeqNum = Integer.parseInt(cN66DetailsVO.getConsSeqNo());
  		long mailSequenceNumber		= cN66DetailsVO.getMailSequenceNumber();
  		MRABillingMaster mraBillingMaster = null;
  		 		
  		if (conSeqNum != 0 && conDocNum != null && companyCode != null
				&& billingBasis != null && gpaCode != null){
  		
		  		try
		      	{
		  			//find the MTKMRABLGMST entity
		  			mraBillingMaster = MRABillingMaster.find(companyCode,mailSequenceNumber) ;
		  			LogonAttributes logonAttributes  =  ContextUtils.getSecurityContext().getLogonAttributesVO();
		  			//create the DSNAuditVO
		  			DSNAuditVO dSNAuditVO  = new DSNAuditVO(MOD_NAM, MOD_NAM, BLANK);
		  			dSNAuditVO.setCompanyCode (companyCode);
		  			dSNAuditVO.setDsn(mraBillingMaster.getDsn());
		  			dSNAuditVO.setOriginExchangeOffice(mraBillingMaster.getOrgExchangeOffice());
		  			dSNAuditVO.setDestinationExchangeOffice(mraBillingMaster.getDstExchangeOffice());
		  			dSNAuditVO.setMailCategoryCode(mraBillingMaster.getMailCatagoryCode());
		  			dSNAuditVO.setMailSubclass(mraBillingMaster.getMailSubClass());
		  			String mailClass = mraBillingMaster.getMailSubClass() == null 
					? "" : mraBillingMaster.getMailSubClass().substring(0,1);
		  			dSNAuditVO.setMailClass(mailClass);
		  			dSNAuditVO.setYear(mraBillingMaster.getYear());
		  			dSNAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		  			dSNAuditVO.setStationCode(logonAttributes.getStationCode());
		  			dSNAuditVO.setActionCode(DSNAuditVO.DSN_STACHG);
		  			dSNAuditVO.setTxnTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		  			Map<String, String> billingStatusMap = populateOneTimeVOs(GPA_BILLING_STATUS);
		  			String billingStatusDescription=billingStatusMap.get(cN66DetailsVO.getBillingStatus());
		  	  		StringBuilder additionalInformation  =  new StringBuilder ("DSN Status Changed to ");
		  	  		additionalInformation = additionalInformation.append(billingStatusDescription);
		  	  		dSNAuditVO.setAdditionalInformation(additionalInformation.toString());
		  			dSNAuditVO.setAuditRemarks(cN66DetailsVO.getRemarks());
		  			ArrayList<DSNAuditVO> dSNAuditVOs = new ArrayList<DSNAuditVO>();
		  			
		  			dSNAuditVOs.add(dSNAuditVO);
		  			//call the proxy method for Audit
		  			new MailTrackingDefaultsProxy().auditDSNsForMRA(dSNAuditVOs);
		      	}
		  		catch (FinderException finderException) {
		  			throw new SystemException(finderException.getMessage());
		  		} catch (ProxyException proxyException) {
		  			throw new SystemException(proxyException.getMessage());
				}
		  		
  		}		
  		
  		log.exiting (CLASS_NAME, "auditDSNForBillingStatus");
	}
	/**
	 * @author A-3252
	 * @param documentBillingDetailsVO
	 */
	public static void auditDSNForBillingBasis(DocumentBillingDetailsVO documentBillingDetailsVO)
	throws SystemException{
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		log.entering (CLASS_NAME, "auditDSNForBillingBasis");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		Collection<DSNAuditVO> auditVOs = new ArrayList<DSNAuditVO>();
		try {
			DSNAuditVO auditVO = new DSNAuditVO(DSNAuditVO.MOD_NAM, DSNAuditVO.SUB_MOD_MRA, BLANK);
			
			auditVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
			auditVO.setDsn(documentBillingDetailsVO.getDsn());
			auditVO.setMailCategoryCode(documentBillingDetailsVO.getCategory());
			auditVO.setMailSubclass(documentBillingDetailsVO.getSubClass());
			auditVO.setYear(Integer.parseInt(documentBillingDetailsVO.getYear()));
			auditVO.setOriginExchangeOffice(documentBillingDetailsVO.getOrigin());
			auditVO.setDestinationExchangeOffice(documentBillingDetailsVO.getDestination());
			auditVO.setMailClass(auditVO.getMailSubclass().substring(0,1));
			
			auditVO.setActionCode(DSNAuditVO.DSN_STACHG);
			auditVO.setLastUpdateUser (logonAttributes.getUserId());
			auditVO.setStationCode (logonAttributes.getStationCode());
			auditVO.setTxnTime (new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
			auditVO.setAuditRemarks(documentBillingDetailsVO.getRemarks());
			
			Map<String, String> billingStatusMap = populateOneTimeVOs(BILLING_STATUS);
			String billingStatusDescription=billingStatusMap.get(documentBillingDetailsVO.getBillingStatus());
				
			auditVO.setAdditionalInformation("DSN status changed to "+billingStatusDescription);
			
			log.log(Log.FINE, " auditVO---->>" + auditVO);
			
			auditVOs.add(auditVO);
			
			//	 Call Mailtracking defaults auditDSNsForMRA for puuting entries in to MTKDSNAUD tables
			new MailTrackingDefaultsProxy ().auditDSNsForMRA (auditVOs);				
			
		}  catch (ProxyException e) {
			throw new SystemException (e.getMessage());
		}
		log.exiting (CLASS_NAME, "auditDSNForBillingBasis");
	}
	/**
	 * @author A-3252
	 * @return
	 * @throws SystemException 
	 */
	private static Map<String,String> populateOneTimeVOs(String parameterCode)
	throws SystemException{
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> parameterTypes = new ArrayList<String>();
		
		
 		parameterTypes.add(parameterCode);
 		String companyCode = logonAttributes.getCompanyCode();
 		Map<String, String> oneTimeValuesNew = new HashMap <String, String>();
		
 			try {
				oneTimeValues = sharedDefaultsProxy.findOneTimeValues(
						companyCode, parameterTypes);
			} catch (ProxyException e) {
				
				e.getMessage();
			}
 			log.log(Log.FINE, " One Time Values---->>" + oneTimeValues);

 		
 				
 		for(OneTimeVO oneTimeVO : oneTimeValues.get(parameterCode)){
 			
 			oneTimeValuesNew.put(oneTimeVO.getFieldValue(),oneTimeVO.getFieldDescription());
 			log.log(Log.FINE, " NEW Values---->>" + oneTimeValuesNew.get(oneTimeVO.getFieldValue()));
 		}
 		
 		return oneTimeValuesNew;
	}



	/**
	 * Method to perform audit while CCA save,accept or reject
	 * @author Meenu Harry A-2565
	 * @param detailsVO
	 * @throws SystemException 
	 */
	public static void auditDSNForCCAIssue(CCAdetailsVO detailsVO) throws SystemException {
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		
		log.entering (CLASS_NAME, "auditDSNForCCAIssue");
		
		if (detailsVO == null) {
			return;
		}
		
		String compCode = detailsVO.getCompanyCode();
  		String gpaCode = detailsVO.getPoaCode();
  		String billingBasis = detailsVO.getBillingBasis();
  		String conDocNum = detailsVO.getCsgDocumentNumber();
  		int conSeqNum = detailsVO.getCsgSequenceNumber();
  		long mailSequenceNumber = detailsVO.getMailSequenceNumber();
  		MRABillingMaster mraBillingMaster = null;
  		if (conSeqNum != 0 && conDocNum != null && compCode != null
				&& billingBasis != null && gpaCode != null){
		  		try
		      	{
		  			//find the MTKMRABLGMST entity
		  			mraBillingMaster = MRABillingMaster.find(compCode,mailSequenceNumber) ;
		  			LogonAttributes logonAttributes  =  ContextUtils.getSecurityContext().getLogonAttributesVO();
		  			//create the DSNAuditVO
		  			DSNAuditVO dSNAuditVO  = new DSNAuditVO(MOD_NAM, MOD_NAM, BLANK);
		  			dSNAuditVO.setCompanyCode (compCode);
		  			dSNAuditVO.setDsn(mraBillingMaster.getDsn());
		  			dSNAuditVO.setOriginExchangeOffice(mraBillingMaster.getOrgExchangeOffice());
		  			dSNAuditVO.setDestinationExchangeOffice(mraBillingMaster.getDstExchangeOffice());
		  			dSNAuditVO.setMailCategoryCode(mraBillingMaster.getMailCatagoryCode());
		  			dSNAuditVO.setMailSubclass(mraBillingMaster.getMailSubClass());
		  			String mailClass = mraBillingMaster.getMailSubClass() == null 
					? "" : mraBillingMaster.getMailSubClass().substring(0,1);
		  			dSNAuditVO.setMailClass(mailClass);
		  			dSNAuditVO.setYear(mraBillingMaster.getYear());
		  			Map<String, String> ccaStatusMap = populateOneTimeVOs(CCA_TYPE);
		  			String ccaStatusDescription=ccaStatusMap.get(detailsVO.getCcaType());
		  			StringBuilder remarks  =  new StringBuilder ("CCA type - ");
		  	  		remarks = remarks.append(ccaStatusDescription).append(",Audited Charge - ").
		  	  				  append(detailsVO.getRevChgGrossWeight()).append(",Weight - ").append(detailsVO.getRevGrossWeight());
		  	  		dSNAuditVO.setAdditionalInformation(remarks.toString());
		  			dSNAuditVO.setAuditRemarks(detailsVO.getCcaRemark());
		  			dSNAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		  			dSNAuditVO.setStationCode(logonAttributes.getStationCode());
		  			dSNAuditVO.setActionCode(DSNAuditVO.DSN_CCA);
		  			dSNAuditVO.setTxnTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		  			ArrayList<DSNAuditVO> dSNAuditVOs = new ArrayList<DSNAuditVO>();
		  			
		  			dSNAuditVOs.add(dSNAuditVO);
		  			//call the proxy method for Audit
		  			new MailTrackingDefaultsProxy().auditDSNsForMRA(dSNAuditVOs);
		      	}
		  		catch (FinderException finderException) {
		  			throw new SystemException(finderException.getMessage());
		  		} catch (ProxyException proxyException) {
		  			throw new SystemException(proxyException.getMessage());
				}
  		}
  		
  		log.exiting (CLASS_NAME, "auditDSNForCCAIssue");
		
		
	}


	/**
	 * Method to perform audit while Rate Audit
	 * @author Meenu Harry A-2565
	 * @param detailsVO
	 * @throws SystemException 
	 */
	public static void auditDSNForRateAudit(RateAuditVO newRateAuditVO) throws SystemException {
		
		Log log = LogFactory.getLogger("MAILTRACKING MRA");
		
		log.entering (CLASS_NAME, "auditDSNForRateAudit");
		
		if (newRateAuditVO == null) {
			return;
		}
		
		String compCode = newRateAuditVO.getCompanyCode();
  		String gpaCode = newRateAuditVO.getGpaCode();
  		String billingBasis = newRateAuditVO.getBillingBasis();
  		String conDocNum = newRateAuditVO.getConDocNum();
  		int conSeqNum = newRateAuditVO.getConSerNum();
  		long mailSequenceNumber = newRateAuditVO.getMailSequenceNumber();
  		MRABillingMaster mraBillingMaster = null;
  		if (conSeqNum != 0 && conDocNum != null && compCode != null
				&& billingBasis != null && gpaCode != null){
		  		try
		      	{
		  			//find the MTKMRABLGMST entity
		  			mraBillingMaster = MRABillingMaster.find(compCode,mailSequenceNumber) ;
		  			LogonAttributes logonAttributes  =  ContextUtils.getSecurityContext().getLogonAttributesVO();
		  			//create the DSNAuditVO
		  			DSNAuditVO dSNAuditVO  = new DSNAuditVO(MOD_NAM, MOD_NAM, BLANK);
		  			dSNAuditVO.setCompanyCode (compCode);
		  			dSNAuditVO.setDsn(mraBillingMaster.getDsn());
		  			dSNAuditVO.setOriginExchangeOffice(mraBillingMaster.getOrgExchangeOffice());
		  			dSNAuditVO.setDestinationExchangeOffice(mraBillingMaster.getDstExchangeOffice());
		  			dSNAuditVO.setMailCategoryCode(mraBillingMaster.getMailCatagoryCode());
		  			dSNAuditVO.setMailSubclass(mraBillingMaster.getMailSubClass());
		  			String mailClass = mraBillingMaster.getMailSubClass() == null 
					? "" : mraBillingMaster.getMailSubClass().substring(0,1);
		  			dSNAuditVO.setMailClass(mailClass);
		  			dSNAuditVO.setYear(mraBillingMaster.getYear());
		  			StringBuilder remarks  =  new StringBuilder ("DSN Rate Audited with ");
		  	  		remarks = remarks.append(",Charge - ").
		  	  				  append(newRateAuditVO.getAuditedWtCharge()).append(",Weight - ").append(newRateAuditVO.getGrossWt());
		  	  		dSNAuditVO.setAdditionalInformation(remarks.toString());
		  			dSNAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		  			dSNAuditVO.setStationCode(logonAttributes.getStationCode());
		  			dSNAuditVO.setActionCode(DSNAuditVO.DSN_RATAUD);
		  			dSNAuditVO.setTxnTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		  			ArrayList<DSNAuditVO> dSNAuditVOs = new ArrayList<DSNAuditVO>();
		  			
		  			dSNAuditVOs.add(dSNAuditVO);
		  			//call the proxy method for Audit
		  			new MailTrackingDefaultsProxy().auditDSNsForMRA(dSNAuditVOs);
		      	}
		  		catch (FinderException finderException) {
		  			throw new SystemException(finderException.getMessage());
		  		} catch (ProxyException proxyException) {
		  			throw new SystemException(proxyException.getMessage());
				}
  		}
  		
  		log.exiting (CLASS_NAME, "auditDSNForRateAudit");
		
		
	}
	
}
