/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.builder.MRAAuditBuilder.java
 *
 *	Created by	:	a-4809
 *	Created on	:	Apr 1, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.builder;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DSNAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingAuditVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MCAAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN66Details;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.BatchSettlementAuditVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.MailbagSettlementAuditVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.builder.MRAAuditBuilder.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-4809	:	Apr 1, 2014	:	Draft
 */
public class MRAAuditBuilder extends AbstractActionBuilder{
	private Log log = LogFactory.getLogger("MRA AUDIT BUILDER");
	private static final String CLASSNAME ="MRAAuditBuilder";
	private static final String BLANK = "";
	private static final String INV_GEN="INVGEN";
	private static final String OUTBLB="OUTBLB";
	private static final String REVIEWD="REVIEWD";
	private static final String ONHOLD="ONHOLD";
	private static final String MEMREJ="MEMREJ";
	private static final String EXPACP="EXPACP";
	private static final String EXPASG="EXPASG";
	private static final String OH="OH";
	private static final String DSNLEVELIMPORT="mailtracking.defaults.DsnLevelImportToMRA";
	//Added by A-6991 for ICRD-211662 Starts
	private static final String MAIL_ONHOLD="MAIL_OH";
	private static final String MAIL_BILLABLE="MAIL_BB";
	//Added by A-6991 for ICRD-211662 Ends
	private static final String UPURATACT ="UPU Rateline Activated";//added by a-7871 for ICRD-231567
	private static final String APP="MCA Approved";
	private static final String REJ="MCA Rejected";
	private static final String DEL="MCA Deleted";
	
	private static final String BTHSTL_SAVE ="BTHSTL_SAVE";
	private static final String BTHSTL_UPDATE ="BTHSTL_UPDATE";
	private static final String BTHSTL_DELETE ="BTHSTL_DELETE";
	private static final String BTHSTL_EDIT ="BTHSTL_EDIT";
	private static final String PAYMENT_BTCH ="Mail Payment Batch ";
	private static final String FORTHEDATE =" for the date ";
	private static final String BILMTRX_ID="The Billing Matrix ID ";
	private static final String STA_ACT = "Active";
	
	private static final String STA_INA = "Inactive";
	
	private static final String STA_EXP = "Expired";
	
	private static final String STA_CAN = "Cancelled";
	
	private static final String STATUS_ACTIVE = "A";

	/** The Constant STATUS_INACTIVE. */
	private static final String STATUS_INACTIVE = "I";

	/** The Constant STATUS_EXPIRED. */
	private static final String STATUS_EXPIRED = "E";

	/** The Constant STATUS_CANCELLED. */
	private static final String STATUS_CANCELLED = "C";
	
	public void auditInvoiceGeneration(String invoiceNumber)
			throws SystemException{ 
		log.entering(CLASSNAME, "auditInvoiceGeneration");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<MailDetailVO> mailDetailVOs =null;
		String dsnLevelImport=null;
		Map<String,String> systemParameterMap =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add(DSNLEVELIMPORT);
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "proxy exception caught");
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0){
			dsnLevelImport =systemParameterMap.get(DSNLEVELIMPORT);
		}
		if(invoiceNumber!=null&& invoiceNumber.trim().length()>0){
			mailDetailVOs = CN66Details.findMailDetailsforAudit(invoiceNumber,logonAttributes.getCompanyCode());
		}
		if(MailDetailVO.FLAG_YES.equals(dsnLevelImport)){
			if(mailDetailVOs!=null && mailDetailVOs.size()>0){
				Collection<DSNAuditVO> dSNAuditVOs = null;
				for(MailDetailVO mailDetailVO:mailDetailVOs){
					dSNAuditVOs = new ArrayList<DSNAuditVO>(); 
					DSNAuditVO auditVO = new DSNAuditVO(DSNAuditVO.MOD_NAM, DSNAuditVO.SUB_MOD_MRA, BLANK);	
					auditVO.setCompanyCode(mailDetailVO.getCompanyCode());
					auditVO.setMailCategoryCode(mailDetailVO.getMailCategoryCode());
					auditVO.setMailSubclass(mailDetailVO.getMailSubclass());
					auditVO.setOriginExchangeOffice(mailDetailVO.getOriginOfficeOfExchange());
					auditVO.setDestinationExchangeOffice(mailDetailVO.getDestinationOfficeOfExchange());
					auditVO.setDsn(mailDetailVO.getDsn());
					auditVO.setYear(mailDetailVO.getYear());
					auditVO.setActionCode(INV_GEN);
					auditVO.setStationCode(logonAttributes.getStationCode());
					LocalDate txnDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
					auditVO.setTxnTime(txnDate.toCalendar());
					LocalDate invDate = mailDetailVO.getRcvDate();
					String date = TimeConvertor.toStringFormat(invDate.toCalendar(), "dd-MMM-yyyy");
					StringBuilder remarks = new StringBuilder("Invoice No: ").append(invoiceNumber)
							.append(" dated on ").append(date).append(" is generated for Despatch ").append(mailDetailVO.getMailId());
					auditVO.setAdditionalInformation(remarks.toString());
					dSNAuditVOs.add(auditVO);
					try {
						new MailTrackingDefaultsProxy ().auditDSNsForMRA (dSNAuditVOs);
					} catch (ProxyException e) {
						log.log(Log.SEVERE, "proxy exception caught here",e.getErrors());
					}
				}  
			}
		}else if(MailDetailVO.FLAG_NO.equals(dsnLevelImport)){
			if(mailDetailVOs!=null && mailDetailVOs.size()>0){
				Collection<MailbagAuditVO> mailbagAuditVOs = null;
				for(MailDetailVO mailDetailVO:mailDetailVOs){
					mailbagAuditVOs = new ArrayList<MailbagAuditVO>();
					MailbagAuditVO auditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,BLANK);
					auditVO.setCompanyCode(mailDetailVO.getCompanyCode());
					auditVO.setMailCategoryCode(mailDetailVO.getMailCategoryCode());
					auditVO.setMailSubclass(mailDetailVO.getMailSubclass());
					auditVO.setOriginExchangeOffice(mailDetailVO.getOriginOfficeOfExchange());
					auditVO.setDestinationExchangeOffice(mailDetailVO.getDestinationOfficeOfExchange());
					auditVO.setDsn(mailDetailVO.getDsn());
					auditVO.setYear(mailDetailVO.getYear());
					auditVO.setActionCode(INV_GEN);
					auditVO.setStationCode(logonAttributes.getStationCode());
					auditVO.setMailbagId(mailDetailVO.getMailId());
					LocalDate txnDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
					auditVO.setTxnTime(txnDate.toCalendar());
					LocalDate invDate = mailDetailVO.getRcvDate();
					String date = TimeConvertor.toStringFormat(invDate.toCalendar(), "dd-MMM-yyyy");
					StringBuilder remarks = new StringBuilder("Invoice No: ").append(invoiceNumber)
							.append(" dated on ").append(date).append(" is generated for mailbag ").append(mailDetailVO.getMailId());
					auditVO.setAdditionalInformation(remarks.toString());
					mailbagAuditVOs.add(auditVO);
					try {
						new MailTrackingDefaultsProxy ().auditMailbagsForMRA(mailbagAuditVOs);
					} catch (ProxyException e) {
						log.log(Log.SEVERE, "proxy exception caught here",e.getErrors());
					}
				}
			}
		  
	  }
	}
	/**
	 * 	Method		:	MRAAuditBuilder.performAirlineBillingAudit
	 *	Added by 	:	A-4809 on Jan 2, 2015
	 * 	Used for 	:	Audit from airline billing 
	 *	Parameters	:	@param rejectionMemo
	 *	Parameters	:	@param actionCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void performAirlineBillingAudit(RejectionMemoVO rejectionMemo, String actionCode)
			throws SystemException{
		log.entering(CLASSNAME, "performAirlineBillingAudit");
		AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(AirlineBillingAuditVO.AUDIT_MODULENAME, AirlineBillingAuditVO.AUDIT_SUBMODULENAME, AirlineBillingAuditVO.AUDIT_ENTITY);
		//RejectionMemoPK rejectionMemoPK = rejectionMemo.getRejectionMemoPK();
		airlineBillingAuditVO.setCompanyCode(rejectionMemo.getCompanycode());
		airlineBillingAuditVO.setAirlineIdentifier(rejectionMemo
				.getAirlineIdentifier());
		airlineBillingAuditVO.setAirlineCode(rejectionMemo.getAirlineCode());
		airlineBillingAuditVO.setClearancePeriod(rejectionMemo
				.getInwardClearancePeriod());
		airlineBillingAuditVO.setMemoCode(rejectionMemo.getMemoCode());
		airlineBillingAuditVO.setActionCode(actionCode);
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("Airline ")
		.append(rejectionMemo.getAirlineCode()).append(
				" with invoice number ").append(
				rejectionMemo.getInwardInvoiceNumber()).append(
				" and memo code ")
		.append(rejectionMemo.getMemoCode()).append(" ");
		if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_CAPTURED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_CREATE_ACTION);
		} else if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_DELETED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_DELETE_ACTION);
		} else if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_UPDATED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_UPDATE_ACTION);
		}	
		airlineBillingAuditVO.setAdditionalInformation(additionalInfo
		.toString());
		log.log(Log.INFO, "inftn ", airlineBillingAuditVO.getAdditionalInformation());
		AuditUtils.performAudit(airlineBillingAuditVO);		
		int csgseqnumber	= rejectionMemo.getCsgSeqNum();
		String csgdocnum	= rejectionMemo.getCsgDocNum();
		String companyCode	=rejectionMemo.getCompanycode();
        String billingBasis	= rejectionMemo.getBillingBasis();
        String poaCode		= rejectionMemo.getPoaCode();
        //Added by A-7794 as part of MRA revamp
        long mailSeqNumber 	= rejectionMemo.getMailSequenceNumber();
		if (csgseqnumber != 0 && csgdocnum != null && companyCode != null && 
				billingBasis != null && poaCode != null){
			try {
				//Modified by A-7794 as part of MRA revamp
				MRABillingMaster billingMaster = MRABillingMaster.find 
								(companyCode,mailSeqNumber);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
						MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO.setCompanyCode(companyCode);
				mailbagAuditVO.setMailbagId(billingBasis);
				mailbagAuditVO.setDsn(billingMaster.getDsn());
				mailbagAuditVO.setOriginExchangeOffice(billingMaster.getOrgExchangeOffice());
				mailbagAuditVO.setDestinationExchangeOffice(billingMaster.getDstExchangeOffice());
				mailbagAuditVO.setMailSubclass(billingMaster.getMailSubClass());
				mailbagAuditVO.setMailCategoryCode(billingMaster.getMailCatagoryCode());
				mailbagAuditVO.setYear((billingMaster.getYear()));
				StringBuffer additionalInfoForMail = new StringBuffer();
				additionalInfoForMail.append("Rejection Memo:");
				additionalInfoForMail.append(rejectionMemo.getMemoCode()).append(" issued");
				mailbagAuditVO.setActionCode(MEMREJ);
				mailbagAuditVO.setAdditionalInformation(additionalInfoForMail.toString());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
				mailbagAuditVO.setTxnTime(new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
				mailbagAuditVO.setAuditRemarks(rejectionMemo.getRemarks());	
				AuditUtils.performAudit(mailbagAuditVO);
			} catch (FinderException e) {
				throw new SystemException (e.getMessage());
			}
		}	
		//Airline Auditing for mailbag ends
		log.exiting(CLASSNAME, "performAirlineBillingAudit");
	}
	/**
	 * 	Method		:	MRAAuditBuilder.auditDSNForRejectionMemo
	 *	Added by 	:	A-4809 on Jan 2, 2015
	 * 	Used for 	:	Audit for DSN
	 *	Parameters	:	@param rejectionVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void auditDSNForRejectionMemo(RejectionMemoVO rejectionMemoVO)
			throws SystemException{
		log.entering(CLASSNAME, "auditDSNForRejectionMemo");
		if (rejectionMemoVO == null) {
			return;
		}
		int csgseqnumber	= rejectionMemoVO.getCsgSeqNum();
		String csgdocnum	= rejectionMemoVO.getCsgDocNum();
		String companyCode	=rejectionMemoVO.getCompanycode();
        String billingBasis	= rejectionMemoVO.getBillingBasis();
        String poaCode		= rejectionMemoVO.getPoaCode();
        long mailSeqNumber 	= 0;
		if (csgseqnumber != 0 && csgdocnum != null && companyCode != null
				&& billingBasis != null && poaCode != null){
			try {
				// DSN Pks to be retreived from MTKMRABLGMST using billing basis 
				//Modified by A-7794 as part of MRA revamp
				MRABillingMaster billingMaster = MRABillingMaster.find 
								(companyCode,mailSeqNumber);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				// Populate DSN Audit VO from Business VO
				Collection<DSNAuditVO> auditVOs = new ArrayList<DSNAuditVO>();
				DSNAuditVO auditVO = new DSNAuditVO (DSNAuditVO.MOD_NAM, DSNAuditVO.SUB_MOD_MRA, BLANK);
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
		log.exiting(CLASSNAME, "auditDSNForRejectionMemo");
	}
	
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	public void auditMCADetails(CCAdetailsVO detailsVO)throws SystemException{
		log.entering(CLASSNAME, "auditMCADetails");
		Map<String,String> systemParameterMap =null;
		String workflowEnabled="";
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.workflowneededforMCA");
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "proxy exception caught");
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0){
			workflowEnabled =systemParameterMap.get("mailtracking.mra.workflowneededforMCA");
		}
		if(!"N".equals(workflowEnabled)){
			MCAAuditVO mcaAuditVO = new MCAAuditVO(MCAAuditVO.AUDIT_MODULENAME,
					MCAAuditVO.AUDIT_SUBMODULENAME,MCAAuditVO.AUDIT_ENTITY);
			StringBuilder addInfo=new StringBuilder("");
			String mcaType = "A".equals(detailsVO.getCcaType())?"Actual":"Internal";
			String mailType=detailsVO.getBillingBasis().length()>=29?"Mail Bag":"Despatch";
			String mcaNumber = detailsVO.getCcaRefNumber();
			mcaAuditVO.setCompanyCode(detailsVO.getCompanyCode());
			mcaAuditVO.setCcaRefNumber(detailsVO.getCcaRefNumber());
			mcaAuditVO.setBillingBasis(detailsVO.getBillingBasis());
			mcaAuditVO.setMailSequenceNumber(detailsVO.getMailSequenceNumber());
			if("N".equals(detailsVO.getCcaStatus())){
				if("Actual".equals(mcaType))
					{
					mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_CREATED);
					}
				else
					{
					mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_CREATED);
					}
				mcaAuditVO.setAuditRemarks("MCA Saved");
				addInfo.append(mcaType).append(" MCA ").append(mcaNumber).append(" saved for the ").
				append(mailType).append(" ").append(detailsVO.getBillingBasis());
			}else if("A".equals(detailsVO.getCcaStatus())){
				if("Actual".equals(mcaType))
					{
					mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_APPROVED);
					}
				else
					{
					mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_APPROVED);
					}
				mcaAuditVO.setAuditRemarks(APP);
				addInfo.append(mcaType).append(" MCA ").append(mcaNumber).append(" Approved for the ").
				append(mailType).append(" ").append(detailsVO.getBillingBasis());
			}else if("R".equals(detailsVO.getCcaStatus())){
				if("Actual".equals(mcaType))
					{
					mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_REJECTED);
					}
				else
					{
					mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_REJECTED);
					}
				mcaAuditVO.setAuditRemarks(REJ);
				addInfo.append(mcaType).append(" MCA ").append(mcaNumber).append(" Rejected for the ").
				append(mailType).append(" ").append(detailsVO.getBillingBasis());
				//Modified the below code by A-8527 for ICRD-350435 starts
			}else if("C".equals(detailsVO.getCcaStatus())){
					if("Actual".equals(mcaType))
						{
						mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_ACCEPTED);
						}
					else
						{
						mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_ACCEPTED);
						}
					mcaAuditVO.setAuditRemarks("MCA Accepted");
					addInfo.append(mcaType).append(" MCA ").append(mcaNumber).append(" Accepted for the ").
					append(mailType).append(" ").append(detailsVO.getBillingBasis());
					//Modified the below code by A-8527 for ICRD-350435 Ends
			}else{
				if("Actual".equals(mcaType))
					{
					mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_DELETED);
					}
				else
					{
					mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_DELETED);
					}
				mcaAuditVO.setAuditRemarks(DEL);
				addInfo.append(mcaType).append(" MCA ").append(mcaNumber).append(" Deleted for the ").
				append(mailType).append(" ").append(detailsVO.getBillingBasis());
			}
			mcaAuditVO.setAdditionalInformation(addInfo.toString());
			AuditUtils.performAudit(mcaAuditVO);
			
			auditDetailsforMailbag(detailsVO);
		}
		log.exiting(CLASSNAME, "auditMCADetails");
	}
	
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	private void auditDetailsforMailbag(CCAdetailsVO detailsVO) throws SystemException {
		 log.entering(CLASSNAME, "saveMCADetailsAtMailbagLevel ");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		 Calendar time = null;
		 String mailType=detailsVO.getBillingBasis().length()>=29?"Mail Bag":"Despatch";
		 StringBuilder addInfo=new StringBuilder("");
		 String mcaType = "A".equals(detailsVO.getCcaType())?"Actual":"Internal";
		 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
		//MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
		          if(detailsVO!=null){
			
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO.setCompanyCode(detailsVO.getCompanyCode());
				mailbagAuditVO.setMailbagId(detailsVO.getBillingBasis());     
				mailbagAuditVO.setMailSequenceNumber(detailsVO.getMailSequenceNumber());
				
				
				if("Actual".equals(mcaType))
				{
				mailbagAuditVO.setActionCode("MTKMRACCACRT_ACTUAL");
				}
			else
				{
				mailbagAuditVO.setActionCode("MTKMRACCACRT_INTERNAL");
				}
					
					if("N".equals(detailsVO.getCcaStatus())){
						
					
						addInfo.append(mcaType).append(" MCA ").append(detailsVO.getCcaRefNumber()).append(" saved for the ").
						append(mailType).append(" ").append(detailsVO.getBillingBasis());
			    mailbagAuditVO.setAdditionalInformation(addInfo.toString());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
				mailbagAuditVO.setAuditRemarks("MCA Saved");
				
			}
			else if("R".equals(detailsVO.getCcaStatus())){
				
	addInfo.append(mcaType).append(" MCA ").append(detailsVO.getCcaRefNumber()).append(" rejected for the ").
	append(mailType).append(" ").append(detailsVO.getBillingBasis());
	    mailbagAuditVO.setAdditionalInformation(addInfo.toString());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
		mailbagAuditVO.setAuditRemarks(REJ);
				
			}
			else if("A".equals(detailsVO.getCcaStatus())){
				
	addInfo.append(mcaType).append(" MCA ").append(detailsVO.getCcaRefNumber()).append(" Approved for the ").
	append(mailType).append(" ").append(detailsVO.getBillingBasis());
	    mailbagAuditVO.setAdditionalInformation(addInfo.toString());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
		mailbagAuditVO.setAuditRemarks(APP);
				
		}
		else  if("D".equals(detailsVO.getCcaStatus())){
			
	addInfo.append(mcaType).append(" MCA ").append(detailsVO.getCcaRefNumber()).append(" Deleted for the ").
	append(mailType).append(" ").append(detailsVO.getBillingBasis());
	    mailbagAuditVO.setAdditionalInformation(addInfo.toString());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
		mailbagAuditVO.setAuditRemarks(DEL);	
			}
					mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
					mailbagAuditVO.setTxnLocalTime(time);
					mailbagAuditVO.setTxnTime(time);
					mailbagAuditVO.setAuditTriggerPoint("MRA072");
					
					AuditUtils.performAudit(mailbagAuditVO); 
				  } 
	     log.exiting(CLASSNAME, "saveMCADetailsAtMailbagLevel ");	

	
	}
		
	
	/**
	 * 
	 * @author A-5255
	 * @param billingMatrixVO
	 * @throws SystemException
	 */
	public void auditBillingMatrix(BillingMatrixVO billingMatrixVO)
			throws SystemException {
		log.entering(CLASSNAME, "auditBillingMatrix");
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(
				BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME,
				BillingMatrixAuditVO.AUDIT_ENTITY);
		billingMatrixAuditVO.setCompanyCode(billingMatrixVO.getCompanyCode());
		billingMatrixAuditVO.setBillingmatrixID(billingMatrixVO
				.getBillingMatrixId());
		StringBuilder addInsertInfo = null;
		StringBuilder surChargeInfo = null;
		StringBuilder addDeleteInfo = null;
		int i=0;
		boolean isFirstInDelete = false;
		boolean isFirstInInsert = false;
		if (billingMatrixVO != null
				&& billingMatrixVO.getBillingLineVOs() != null
				&& billingMatrixVO.getBillingLineVOs().size() > 0) {

			for (BillingLineVO billingLineVO : billingMatrixVO
					.getBillingLineVOs()) {
				if (BillingLineVO.OPERATION_FLAG_INSERT.equals(billingLineVO
						.getOperationFlag())) {
					if (!isFirstInInsert) {
						isFirstInInsert = true;
						addInsertInfo = new StringBuilder();
					}
					addInsertInfo.append("Rate Line ID:  ");
					addInsertInfo.append(billingLineVO
							.getBillingLineSequenceNumber() + "<br/>");
					if ("R".equals(billingLineVO.getRevenueExpenditureFlag())) {
						addInsertInfo.append("Receivable=Y, Blg Pty=");
						if (billingLineVO.getAirlineCode() != null
								&& !"".equals(billingLineVO.getAirlineCode())) {
							addInsertInfo.append("Airline, Bill To=");
							addInsertInfo.append(billingLineVO.getAirlineCode()
									+ "");
						} else if (billingLineVO.getPoaCode() != null
								&& !"".equals(billingLineVO.getPoaCode())) {
							addInsertInfo
									.append("Postal Administration Code, Bill To=");
							addInsertInfo.append(billingLineVO.getPoaCode());
						}

					} else if ("P".equals(billingLineVO
							.getRevenueExpenditureFlag())) {
						addInsertInfo.append("Payable=Y, Blg By=");
						addInsertInfo.append(billingLineVO.getAirlineCode());
					}
					if (billingLineVO.getBillingLineParameters() != null && 
							billingLineVO.getBillingLineParameters().size()>0) {    
					for(BillingLineParameterVO billingLineParameterVO:billingLineVO.getBillingLineParameters()){//added by a7531 for icrd 224979
						if("AGT".equals(billingLineParameterVO.getParameterCode()))
						{
							addInsertInfo.append(",Agent:");
							if("N".equals(billingLineParameterVO.getExcludeFlag())){
								addInsertInfo.append(" Include=");
							}else{
								addInsertInfo.append(" Exclude=");
							}
							addInsertInfo.append(billingLineParameterVO.getParameterValue());
						}
					}
					}
					if (billingLineVO.getBillingSector() != null) {
						addInsertInfo.append("<br/>Billed Sector=");
						addInsertInfo.append(billingLineVO.getBillingSector());
					}
					if (billingLineVO.getBillingBasis() != null) {
						addInsertInfo.append(",Billing Basis=");
						addInsertInfo.append(billingLineVO.getBillingBasis());
					}
					if (billingLineVO.getUnitCode() != null) {
						addInsertInfo.append(",Weight Unit=");
						addInsertInfo.append(billingLineVO.getUnitCode());
					}
					if(billingLineVO
							.isTaxIncludedInRateFlag()){
					addInsertInfo.append(",Rate inclusive of service tax=");
					addInsertInfo.append(billingLineVO
							.isTaxIncludedInRateFlag() ? "Y" : "N");
					}
					if (billingLineVO.getCurrencyCode() != null) {
						addInsertInfo.append(",Currency=");
						addInsertInfo.append(billingLineVO.getCurrencyCode());
					}

					if (billingLineVO.getBillingLineDetails() != null
							&& billingLineVO.getBillingLineDetails().size() > 0) {
						i=0;
						for (BillingLineDetailVO billingLineDetailVO : billingLineVO
								.getBillingLineDetails()) {
							if (BillingLineDetailVO.MAIL_CHARGETYPE
									.equals(billingLineDetailVO.getChargeType())) {
								addInsertInfo.append("<br/>Mail Charges=Y, ");
								addInsertInfo.append("Rating Basis=");
								addInsertInfo.append(billingLineDetailVO.getRatingBasis()+"<br/>");
								if (billingLineDetailVO.getBillingLineCharges() != null
										&& billingLineDetailVO
												.getBillingLineCharges().size() > 0) {
									if(BillingLineDetailVO.WEIGHT_BREAK.equals(billingLineDetailVO.getRatingBasis())){
										for (BillingLineChargeVO billingLineChargeVO : billingLineDetailVO
												.getBillingLineCharges()) {
											if(billingLineChargeVO.getFrmWgt()==-1){
												addInsertInfo.append("Normal Rate=");
												addInsertInfo.append(billingLineChargeVO.getApplicableRateCharge());
												addInsertInfo.append(",");
											}else if(billingLineChargeVO.getFrmWgt()==0){
												addInsertInfo.append("Min.Charge=");
												addInsertInfo.append(billingLineChargeVO.getApplicableRateCharge());
												addInsertInfo.append(",");
											}else{
											addInsertInfo.append("Weight=");
											addInsertInfo.append(billingLineChargeVO.getFrmWgt());
											addInsertInfo.append(",");
											addInsertInfo.append("Rate=");
											addInsertInfo.append(billingLineChargeVO.getApplicableRateCharge());
											addInsertInfo.append("<br/>");
											}
										}
									}else if(BillingLineDetailVO.FLAT_RATE.equals(billingLineDetailVO.getRatingBasis())){
										addInsertInfo.append("Flat Rate=");
										addInsertInfo
												.append(billingLineDetailVO
														.getBillingLineCharges()
														.iterator()
														.next()
														.getApplicableRateCharge());
										addInsertInfo.append("<br/>");
									}else if(BillingLineDetailVO.FLAT_CHARGE.equals(billingLineDetailVO.getRatingBasis())){
										addInsertInfo.append("Flat Charge=");
										addInsertInfo
												.append(billingLineDetailVO
														.getBillingLineCharges()
														.iterator()
														.next()
														.getApplicableRateCharge());
										addInsertInfo.append("<br/>");
									}
									
									
								}
							}else if (BillingLineDetailVO.SECURITY_SURCHARGE
									.equals(billingLineDetailVO.getChargeType())||BillingLineDetailVO.FUEL_SURCHARGE
									.equals(billingLineDetailVO.getChargeType())||BillingLineDetailVO.HANDLING_CHARGE
									.equals(billingLineDetailVO.getChargeType())) {
								if(surChargeInfo==null){
									surChargeInfo=new StringBuilder();
								}
								surChargeInfo.append("<br/>Surcharge=Y, ");
								surChargeInfo.append("Charge head=");
								surChargeInfo.append(billingLineDetailVO.getChargeType());
								surChargeInfo.append(",Rating Basis=");
								surChargeInfo.append(billingLineDetailVO.getRatingBasis()+"<br/>");
								if (billingLineDetailVO.getBillingLineCharges() != null
										&& billingLineDetailVO
												.getBillingLineCharges().size() > 0) {
									if(BillingLineDetailVO.WEIGHT_BREAK.equals(billingLineDetailVO.getRatingBasis())){
										for (BillingLineChargeVO billingLineChargeVO : billingLineDetailVO
												.getBillingLineCharges()) {
											if(billingLineChargeVO.getFrmWgt()==-1){
												surChargeInfo.append("Normal Rate=");
												surChargeInfo.append(billingLineChargeVO.getApplicableRateCharge());
												surChargeInfo.append(",");
											}else if(billingLineChargeVO.getFrmWgt()==0){
												surChargeInfo.append("Min.Charge=");
												surChargeInfo.append(billingLineChargeVO.getApplicableRateCharge());
												surChargeInfo.append(",");
											}else{
												surChargeInfo.append("Weight=");
												surChargeInfo.append(billingLineChargeVO.getFrmWgt());
												surChargeInfo.append(",");
												surChargeInfo.append("Rate=");
												surChargeInfo.append(billingLineChargeVO.getApplicableRateCharge());
												surChargeInfo.append("<br/>");
											}
										}
									}else if(BillingLineDetailVO.FLAT_RATE.equals(billingLineDetailVO.getRatingBasis())){
										surChargeInfo.append("Flat Rate=");
										surChargeInfo
												.append(billingLineDetailVO
														.getBillingLineCharges()
														.iterator()
														.next()
														.getApplicableRateCharge());
										surChargeInfo.append("<br/>");
									}else if(BillingLineDetailVO.FLAT_CHARGE.equals(billingLineDetailVO.getRatingBasis())){
										surChargeInfo.append("Flat Charge=");
										surChargeInfo
												.append(billingLineDetailVO
														.getBillingLineCharges()
														.iterator()
														.next()
														.getApplicableRateCharge());
										surChargeInfo.append("<br/>");
									}
									
									
								}
							
								
							}
							
							if (i+1 == billingLineVO.getBillingLineDetails().size()) {
								if(surChargeInfo!=null){
									addInsertInfo.append(surChargeInfo.toString());
								}
						    }
							i++;
						}
					}
				} else if (BillingLineVO.OPERATION_FLAG_DELETE
						.equals(billingLineVO.getOperationFlag())) {
					if (!isFirstInDelete) {
						isFirstInDelete = true;
						addDeleteInfo = new StringBuilder();
						addDeleteInfo
								.append("Rate lines with following IDs are Deleted: ");
					}
					addDeleteInfo.append(billingLineVO
							.getBillingLineSequenceNumber());

				}
			}
		}
		/**
		 * To Audit for Insertion
		 */
		if (addInsertInfo != null) {
			billingMatrixAuditVO.setAdditionalInformation(addInsertInfo
					.toString());
			billingMatrixAuditVO.setActionCode("CREATED the rate line");
			AuditUtils.performAudit(billingMatrixAuditVO);
		}
		/**
		 * To Audit for Deletion
		 */
		if (addDeleteInfo != null) {
			billingMatrixAuditVO.setAdditionalInformation(addDeleteInfo
					.toString());
			billingMatrixAuditVO.setActionCode("DELETED the rate line");
			AuditUtils.performAudit(billingMatrixAuditVO);
		}

		log.exiting(CLASSNAME, "auditBillingMatrix");
	}
	/**
	 * Auditing Airline Billing Status Change
	 * @author A-6245
	 * @param documentBillingDetailsVOs
	 * @throws SystemException
	 */
	public void auditChangeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException{
		 log.entering(CLASSNAME, "auditChangeStatus");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		if(documentBillingDetailsVOs!=null&&documentBillingDetailsVOs.size()>0){
			for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){
			mailbagAuditVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(documentBillingDetailsVO.getBillingBasis());
			mailbagAuditVO.setDsn(documentBillingDetailsVO.getDsn());
			mailbagAuditVO.setOriginExchangeOffice(documentBillingDetailsVO.getOrgOfficeOfExchange());
			mailbagAuditVO.setDestinationExchangeOffice(documentBillingDetailsVO.getDestOfficeOfExchange());
			mailbagAuditVO.setMailSubclass(documentBillingDetailsVO.getSubClass());
			mailbagAuditVO.setMailCategoryCode(documentBillingDetailsVO.getCategory());
			mailbagAuditVO.setYear(Integer.parseInt(documentBillingDetailsVO.getYear()));
			StringBuffer additionalInfo = new StringBuffer();
			if(MRAConstantsVO.BLGTYPE_GPA.equalsIgnoreCase(documentBillingDetailsVO.getIntblgType())){
				if(MRAConstantsVO.ONHOLD.equals(documentBillingDetailsVO.getBillingStatus())){
					mailbagAuditVO.setActionCode(MAIL_ONHOLD);
				}else{
					mailbagAuditVO.setActionCode(MAIL_BILLABLE);
				}
			}else{
				if(OH.equals(documentBillingDetailsVO.getBillingStatus())){
					additionalInfo.append("Interline Outward Billing status updated from OB-Billable to On-Hold");
					mailbagAuditVO.setActionCode(ONHOLD);
				}else{
					additionalInfo.append("Mail marked as OB-Billable");
					mailbagAuditVO.setActionCode(OUTBLB);
				}	
			}
			
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
			mailbagAuditVO.setTxnTime(new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
			mailbagAuditVO.setAuditRemarks(documentBillingDetailsVO.getRemarks());
			AuditUtils.performAudit(mailbagAuditVO); 
			}
		}
         log.exiting(CLASSNAME, "auditChangeStatus");	
	}
	/**
	 * Auditing Airline Billing Marked as Reviewed
	 * @author A-6245
	 * @param documentBillingDetailsVOs
	 * @throws SystemException
	 */
	public void auditChangeReview(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException{
		 log.entering(CLASSNAME, "auditChangeReview");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		if(documentBillingDetailsVOs!=null&&documentBillingDetailsVOs.size()>0){
			for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){		
			mailbagAuditVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(documentBillingDetailsVO.getBillingBasis());
			mailbagAuditVO.setDsn(documentBillingDetailsVO.getDsn());
			mailbagAuditVO.setOriginExchangeOffice(documentBillingDetailsVO.getOrgOfficeOfExchange());
			mailbagAuditVO.setDestinationExchangeOffice(documentBillingDetailsVO.getDestOfficeOfExchange());
			mailbagAuditVO.setMailSubclass(documentBillingDetailsVO.getSubClass());
			mailbagAuditVO.setMailCategoryCode(documentBillingDetailsVO.getCategory());
			mailbagAuditVO.setYear(Integer.parseInt(documentBillingDetailsVO.getYear()));
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append("OB-Billable mail marked as Reviewed for generating Outward Billing Invoice");
			mailbagAuditVO.setActionCode(REVIEWD);
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
			mailbagAuditVO.setTxnTime(new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
			mailbagAuditVO.setAuditRemarks(documentBillingDetailsVO.getRemarks());
			AuditUtils.performAudit(mailbagAuditVO); 
			}
		}
         log.exiting(CLASSNAME, "auditChangeReview");	
	}
	/**
	 * @author A-6245
	 * @param airlineExceptionsVOs
	 * @throws SystemException
	 */
	public void auditAcceptedAirlineDsns (Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws SystemException{
		 log.entering(CLASSNAME, "auditAcceptedAirlineDsns ");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		if(airlineExceptionsVOs!=null&&airlineExceptionsVOs.size()>0){
			for(AirlineExceptionsVO airlineExceptionsVO:airlineExceptionsVOs){		
			mailbagAuditVO.setCompanyCode(airlineExceptionsVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(airlineExceptionsVO.getBillingBasis());
			mailbagAuditVO.setDsn(airlineExceptionsVO.getDespatchSerNo());
			mailbagAuditVO.setOriginExchangeOffice(airlineExceptionsVO.getOrgOfficeOfExchange());
			mailbagAuditVO.setDestinationExchangeOffice(airlineExceptionsVO.getDestOfficeOfExchange());
			mailbagAuditVO.setMailSubclass(airlineExceptionsVO.getMailSubClass());
			mailbagAuditVO.setMailCategoryCode(airlineExceptionsVO.getMailCategoryCode());
			mailbagAuditVO.setYear(airlineExceptionsVO.getYear());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append("Exception has been accepted by user");
			mailbagAuditVO.setActionCode(EXPACP);
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
			mailbagAuditVO.setTxnTime(new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
			mailbagAuditVO.setAuditRemarks(airlineExceptionsVO.getRemark());
			AuditUtils.performAudit(mailbagAuditVO); 
			}	
		}
         log.exiting(CLASSNAME, "auditAcceptedAirlineDsns ");	
	}
/**
 * @author A-6245
 * @param airlineExceptionsVOs
 * @throws SystemException
 */
	public void auditSaveAirlineExceptions (Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws SystemException{
		 log.entering(CLASSNAME, "auditSaveAirlineExceptions ");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		if(airlineExceptionsVOs!=null&&airlineExceptionsVOs.size()>0){
		for(AirlineExceptionsVO airlineExceptionsVO:airlineExceptionsVOs){
				if(MailConstantsVO.OPERATION_FLAG_UPDATE.equals(airlineExceptionsVO.getOperationalFlag())){
			mailbagAuditVO.setCompanyCode(airlineExceptionsVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(airlineExceptionsVO.getBillingBasis());
			mailbagAuditVO.setDsn(airlineExceptionsVO.getDespatchSerNo());
			mailbagAuditVO.setOriginExchangeOffice(airlineExceptionsVO.getOrgOfficeOfExchange());
			mailbagAuditVO.setDestinationExchangeOffice(airlineExceptionsVO.getDestOfficeOfExchange());
			mailbagAuditVO.setMailSubclass(airlineExceptionsVO.getMailSubClass());
			mailbagAuditVO.setMailCategoryCode(airlineExceptionsVO.getMailCategoryCode());
			mailbagAuditVO.setYear(airlineExceptionsVO.getYear());
			StringBuffer additionalInfo = new StringBuffer();
			if(airlineExceptionsVO.getAssigneeCode()!=null&&
					airlineExceptionsVO.getAssigneeCode().trim().length()>0){
			additionalInfo.append("Exception has been assigned to User:");
			additionalInfo.append(airlineExceptionsVO.getAssigneeCode());
			}else{
				additionalInfo.append("Exception saved without Assignee");
			}
			mailbagAuditVO.setActionCode(EXPASG);
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
			mailbagAuditVO.setTxnTime(new LocalDate (LocalDate.NO_STATION, Location.NONE, true));
			mailbagAuditVO.setAuditRemarks(airlineExceptionsVO.getRemark());
			AuditUtils.performAudit(mailbagAuditVO); 
				}
		 	}
	  }
         log.exiting(CLASSNAME, "auditSaveAirlineExceptions ");	
	}
	/**
	 * @author A-7871 
	 * @param rateLineVOs
	 * added for ICRD-231567
	 * @throws SystemException
	 */
		public void saveRateLineDetails(Collection<RateLineVO> rateLineVOs ,boolean isBulkActivation)
				throws SystemException{
			 log.entering(CLASSNAME, "saveRateLineDetails ");
			 Calendar time = null;// Added by a-7871 for ICRD-231567
			 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();// Added by a-7871 for ICRD-231567
			 boolean errorsExist = false;
			 for (RateLineVO rateLineVo : rateLineVOs) {
				 if(rateLineVo.getErrorVO() != null){
					 errorsExist = true;
					 break;
				 }
			 }
			 if(!errorsExist){
				 for (RateLineVO rateLineVo : rateLineVOs) {
				   StringBuilder additionalInfo = new StringBuilder();
				   RateLineAuditVO rateLineAuditVO= new RateLineAuditVO(RateLineAuditVO.AUDIT_MODULENAME, RateLineAuditVO.AUDIT_SUBMODULENAME, RateLineAuditVO.AUDIT_ENTITY);
				   rateLineAuditVO.setOrigin(rateLineVo.getOrigin());
				   rateLineAuditVO.setDestination(rateLineVo.getDestination());
				   rateLineAuditVO.setRateCardID(rateLineVo.getRateCardID());
				   rateLineAuditVO.setLastUpdateDate(rateLineVo.getLastUpdateTime());
				   rateLineAuditVO.setLastUpdateUser(rateLineVo.getLastUpdateUser());
				   rateLineAuditVO.setStationCode(logonAttributes.getStationCode());
				   rateLineAuditVO.setRateLineSerNum(rateLineVo.getRatelineSequenceNumber());
				   rateLineAuditVO.setLevel((rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("C")) ? "C" : "A");
				   rateLineAuditVO.setActionCode(UPURATACT);
				   rateLineAuditVO.setLastUpdateTime(time);  
				   String level = (rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("A")) ? "(ARP)" : "(CITY)";
				   rateLineAuditVO.setAdditionalInformation(additionalInfo.append("Rate lines with following Origin and Destination pair are activated ")
						   .append(rateLineAuditVO.getOrigin()).append(level).append(",").append(rateLineAuditVO.getDestination()).append(level).toString());
				   AuditUtils.performAudit(rateLineAuditVO);
				 }
			 }
		     log.exiting(CLASSNAME, "saveRateLineDetails ");	
		}
		//Added by A-8527 for Bug IASCB-25921 starts
		public void updateRateLineDetails(Collection<RateLineVO> rateLineVOs )
				throws SystemException{
			 log.entering(CLASSNAME, "updateRateLineDetails ");
			 Calendar time = null;
			 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
			 boolean errorsExist = false;
			 String rateLineSatatus="";
			 for (RateLineVO rateLineVo : rateLineVOs) {
				 if(rateLineVo.getErrorVO() != null){
					 errorsExist = true;
					 break;
				 }
				 rateLineSatatus= rateLineVo.getRatelineStatus();
			 }
			 if("I".equals(rateLineSatatus)){
				 rateLineSatatus="Inactivated" ;
			 }else if("C".equals(rateLineSatatus)){
				 rateLineSatatus="Cancelled" ;
			 } 
			 log.log(Log.INFO, "updateRateLineDetails rateLineSatatus :",rateLineSatatus);
			 if(!errorsExist){
				 for (RateLineVO rateLineVo : rateLineVOs) {
				   StringBuilder additionalInfo = new StringBuilder();
				   RateLineAuditVO rateLineAuditVO= new RateLineAuditVO(RateLineAuditVO.AUDIT_MODULENAME, RateLineAuditVO.AUDIT_SUBMODULENAME, RateLineAuditVO.AUDIT_ENTITY);
				   rateLineAuditVO.setOrigin(rateLineVo.getOrigin());
				   rateLineAuditVO.setDestination(rateLineVo.getDestination());
				   rateLineAuditVO.setRateCardID(rateLineVo.getRateCardID());
				   rateLineAuditVO.setLastUpdateDate(rateLineVo.getLastUpdateTime());
				   rateLineAuditVO.setLastUpdateUser(rateLineVo.getLastUpdateUser());
				   rateLineAuditVO.setStationCode(logonAttributes.getStationCode());
				   rateLineAuditVO.setRateLineSerNum(rateLineVo.getRatelineSequenceNumber());
				   rateLineAuditVO.setLevel((rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("C")) ? "C" : "A");
				   String status = "I".equals(rateLineVo.getRatelineStatus()) ? "Inactivated" :"Cancelled";
				   rateLineAuditVO.setActionCode(new StringBuilder("UPU Rateline ").append(status).toString());
				   rateLineAuditVO.setLastUpdateTime(time);
				   String level = (rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("A")) ? "(ARP)" : "(CITY)";
				   rateLineAuditVO.setAdditionalInformation(additionalInfo.append("Rate lines with following Origin and Destination pair are ")
						   .append(rateLineSatatus).append(" ").append(rateLineAuditVO.getOrigin()).append(level).append(",")
						   .append(rateLineAuditVO.getDestination()).append(level).toString());
				   AuditUtils.performAudit(rateLineAuditVO);
				 }
			 }
		     log.exiting(CLASSNAME, "updateRateLineDetails ");	
		}
		//Added by A-8527 for Bug IASCB-25921 Ends
		/**
		 * 
		 * 	Method		:	MRAAuditBuilder.auditAutoMCADetails
		 *	Added by 	:	A-7929 on 02-Aug-2018
		 * 	Used for 	:
		 *	Parameters	:	@param detailsVO
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	void
		 */
		public void auditAutoMCADetails(Collection <CCAdetailsVO> CCAdetailsVOs,GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)throws SystemException{
			log.entering(CLASSNAME, "auditAutoMCADetails");
			Map<String,String> systemParameterMap =null;
			String workflowEnabled="";
			Collection<String> systemParCodes = new ArrayList<String>();
			systemParCodes.add("mailtracking.mra.workflowneededforMCA");
			try {
				systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
			} catch (ProxyException e) {
				log.log(Log.SEVERE, "proxy exception caught");
			}
			if(systemParameterMap !=null && systemParameterMap.size()>0){
				workflowEnabled =systemParameterMap.get("mailtracking.mra.workflowneededforMCA");
			}
			if(!"N".equals(workflowEnabled)){
				for(CCAdetailsVO detailsVO : CCAdetailsVOs){
					//Added by A-8527 for ICRD-350435| condition to check autoMCA flag
					if("Y".equals(detailsVO.getAutoMca())){
				MCAAuditVO mcaAuditVO = new MCAAuditVO(MCAAuditVO.AUDIT_MODULENAME,
						MCAAuditVO.AUDIT_SUBMODULENAME,MCAAuditVO.AUDIT_ENTITY);
				StringBuilder addInfo=new StringBuilder("");
				int count =0;
				String mcaType = "A".equals(detailsVO.getCcaType())?"Actual":"Internal";
				String mailType=detailsVO.getBillingBasis().length()>=29?"Mail Bag":"Despatch";
				String mcaNumber = detailsVO.getCcaRefNumber();
				mcaAuditVO.setCompanyCode(detailsVO.getCompanyCode());
				mcaAuditVO.setCcaRefNumber(detailsVO.getCcaRefNumber());
				mcaAuditVO.setBillingBasis(detailsVO.getBillingBasis());
				mcaAuditVO.setMailSequenceNumber(detailsVO.getMailSequenceNumber());//A-8061 Added for ICRD-278208
				 //Modified by A-7794 as part of ICRD-286237
				if("N".equals(detailsVO.getCcaStatus())){
					mcaAuditVO.setAutoMCAUpdatedUser("SYSTEM");
					
					mcaAuditVO.setActionCode(MCAAuditVO.AUTO_MCA_CREATED);
					mcaAuditVO.setAuditRemarks("MCA Saved");
					addInfo.append("Auto MCA: ").append(mcaNumber).append(" has been created for ");
					if(detailsVO.getCurrChangeInd().equals("Y")){
						addInfo.append("Change in Currency");
						count++;
					}
					if(detailsVO.getGpaChangeInd().equals("Y")){
						if(count != 0){
							addInfo.append(", ");
						}
						addInfo.append("Change in GPA Code");
						count++;
					}
					if(detailsVO.getWeightChargeChangeInd().equals("Y")){
						if(count != 0){
							addInfo.append(", ");
						}
						addInfo.append("Change in Mail Charge/Surcharge");
						count++;
					}
					if(detailsVO.getRateChangeInd().equals("Y")){
						if(count != 0){
							addInfo.append(", ");
						}
						addInfo.append("Change in Mail Rate/Surcharge");
						count++;
					}
					//append(mailType).append(" ").append(detailsVO.getBillingBasis());
				}else if("A".equals(detailsVO.getCcaStatus())){
					mcaAuditVO.setActionCode(MCAAuditVO.AUTO_MCA_APPROVED);
					mcaAuditVO.setAuditRemarks(APP);
					addInfo.append("Auto MCA: ").append(mcaNumber).append(" has been Approved ");
				}else if("R".equals(detailsVO.getCcaStatus())){
					mcaAuditVO.setActionCode(MCAAuditVO.AUTO_MCA_REJECTED);
					mcaAuditVO.setAuditRemarks(REJ);
					addInfo.append("Auto MCA: ").append(mcaNumber).append(" has been Rejected ");
				}else{
					mcaAuditVO.setActionCode(MCAAuditVO.AUTO_MCA_DELETED);
					mcaAuditVO.setAuditRemarks(DEL);
					addInfo.append("Auto MCA: ").append(mcaNumber).append(" has been deleted ");
				}
				mcaAuditVO.setAdditionalInformation(addInfo.toString());
				
				mailbagLevelAuditForAutoMCA(detailsVO);
				
				AuditUtils.performAudit(mcaAuditVO);
			}
			}
		}	
			log.exiting(CLASSNAME, "auditAutoMCADetails");
		}
			
		/**
		 * 
		 * @param cCAdetailsVOs
		 * @throws SystemException
		 */
		
		
		
		private void mailbagLevelAuditForAutoMCA(CCAdetailsVO detailsVO) throws SystemException {
			log.entering(CLASSNAME, "saveAutoMCADetailsAtMailbagLevel ");
			 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			 Calendar time = null;
			
			 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
			//MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
			          if(detailsVO!=null){
				
					
					StringBuilder addInfo=new StringBuilder("");
				    int count =0;
					MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
					mailbagAuditVO.setCompanyCode(detailsVO.getCompanyCode());
					mailbagAuditVO.setMailbagId(detailsVO.getBillingBasis());     
					mailbagAuditVO.setMailSequenceNumber(detailsVO.getMailSequenceNumber());
					if("N".equals(detailsVO.getCcaStatus())){
						mailbagAuditVO.setActionCode("MTKMRAMCACRT_AUTO");
						mailbagAuditVO.setAuditRemarks("MCA Saved");
						addInfo.append("Auto MCA: ").append(detailsVO.getCcaRefNumber()).append(" has been created for ");
						if(detailsVO.getCurrChangeInd().equals("Y")){
							addInfo.append("Change in Currency");
							count++;
						}
						if(detailsVO.getGpaChangeInd().equals("Y")){
							if(count != 0){
								addInfo.append(", ");
							}
							addInfo.append("Change in GPA Code");
							count++;
						}
						if(detailsVO.getWeightChargeChangeInd().equals("Y")){
							if(count != 0){
								addInfo.append(", ");
							}
							addInfo.append("Change in Mail Charge/Surcharge");
							count++;
						}
						if(detailsVO.getRateChangeInd().equals("Y")){
							if(count != 0){
								addInfo.append(", ");
							}
							addInfo.append("Change in Mail Rate/Surcharge");
							count++;
						}
					}
			        mailbagAuditVO.setAdditionalInformation(addInfo.toString());
				    mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
				  
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
					
					mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
					mailbagAuditVO.setTxnLocalTime(time);
					mailbagAuditVO.setTxnTime(time);
					mailbagAuditVO.setAuditTriggerPoint("MRA072");
					
					AuditUtils.performAudit(mailbagAuditVO); 
				
				
			          }
		     log.exiting(CLASSNAME, "saveAutoMCADetailsAtMailbagLevel ");	
	
		}		
		
		/**
		 * @author A-7871 
		 * @param rateLineVOs
		 * added for ICRD-231567
		 * @throws SystemException
		 */
			public void saveSettlementsAtMailbagLevel(MailbagSettlementAuditVO mailbagSettlementAuditVO)
					throws SystemException{
				 log.entering(CLASSNAME, "saveSettelementsatMailbaglevel ");
				 Calendar time = null;
				 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();// Added by a-7871 for ICRD-231567
				
				
				 MailbagSettlementAuditVO mailbagsettlementAuditVO= new MailbagSettlementAuditVO(MailbagSettlementAuditVO.AUDIT_MODULENAME, MailbagSettlementAuditVO.AUDIT_SUBMODULENAME, MailbagSettlementAuditVO.AUDIT_ENTITY);
				 mailbagsettlementAuditVO.setInvoiceNumber(mailbagSettlementAuditVO.getInvoiceNumber());
				 mailbagsettlementAuditVO.setSerialNumber(mailbagSettlementAuditVO.getSerialNumber());
				 mailbagsettlementAuditVO.setInvoiceNumber(mailbagSettlementAuditVO.getInvoiceNumber());
				 mailbagsettlementAuditVO.setLastUpdateDate(mailbagSettlementAuditVO.getLastUpdateTime());
				 mailbagsettlementAuditVO.setLastUpdateUser(mailbagSettlementAuditVO.getLastUpdateUser());
				 mailbagsettlementAuditVO.setStationCode(logonAttributes.getStationCode());
				 mailbagsettlementAuditVO.setActionCode("Settled");
				 mailbagsettlementAuditVO.setLastUpdateTime(time);  
					   AuditUtils.performAudit(mailbagsettlementAuditVO);
					 
				 
			     log.exiting(CLASSNAME, "saveSettelementsatMailbaglevel ");	
		}
			/**
			 * @author A-7929 
			 * @param invoicDetailsVOs
			 * added for IASCB-1266
			 * @throws SystemException
			 */
			 public void saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVOs)throws SystemException,RemoteException{
					 log.entering(CLASSNAME, "saveClaimDetails ");
					 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
					 Calendar time = null;
					 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
					//MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
					if(invoicDetailsVOs!=null && invoicDetailsVOs.size()>0){  
						for(InvoicDetailsVO invoicDetailsVO:invoicDetailsVOs){
							MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
							mailbagAuditVO.setCompanyCode(invoicDetailsVO.getCompanyCode());
							mailbagAuditVO.setMailbagId(invoicDetailsVO.getMailIdr());     
							mailbagAuditVO.setMailSequenceNumber(invoicDetailsVO.getMailSequenceNumber());
							mailbagAuditVO.setActionCode("CLAIM AMOUNT UPDATED");
							StringBuilder additionalInformation = new StringBuilder("Claim amount changed from ").append(invoicDetailsVO.getOrgClaimAmount()).append(" to ").append(invoicDetailsVO.getClaimamount());	
							mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());
							mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());  //last updated user
							mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
							mailbagAuditVO.setTxnLocalTime(time);//transaction time
							mailbagAuditVO.setTxnTime(time);
							mailbagAuditVO.setAuditTriggerPoint("MRA077");
							
							AuditUtils.performAudit(mailbagAuditVO); 
						}
					}
					 
				     log.exiting(CLASSNAME, "saveClaimDetails ");	
			}
			 /**
				 * @author A-7929 
				 * @param invoicDetailsVOs,raiseClaimFlag
				 * added for IASCB-1266
				 * @throws SystemException
				 */
			 public void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String raiseClaimFlag) throws SystemException {
						 log.entering(CLASSNAME, "saveClaimDetails ");
						 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
						 Calendar time = null;
						 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
						//MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
						if(invoicDetailsVOs!=null && invoicDetailsVOs.size()>0){
							for(InvoicDetailsVO invoicDetailsVO:invoicDetailsVOs){
								MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
								mailbagAuditVO.setCompanyCode(invoicDetailsVO.getCompanyCode());
								mailbagAuditVO.setMailbagId(invoicDetailsVO.getMailIdr());     
								mailbagAuditVO.setMailSequenceNumber(invoicDetailsVO.getMailSequenceNumber());
								if("Y".equals(raiseClaimFlag)){
								mailbagAuditVO.setActionCode("USPS RAISE CLAIM");
								StringBuilder additionalInformation = new StringBuilder("Claim raised for amount of ").append(invoicDetailsVO.getClaimamount());	
								mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());
								}else if ("R".equals(raiseClaimFlag)){
									mailbagAuditVO.setActionCode("USPS INVOIC Rejection");
									StringBuilder additionalInformation = new StringBuilder("Mailbag moved to Overpay Rejected bucket from Overpay bucket as part of over payment Rejection");	
									mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());
								}else{
									StringBuilder additionalInformation = new StringBuilder("");
								if(invoicDetailsVO.getClaimamount() == null || invoicDetailsVO.getClaimamount().getAmount() == 0){
									mailbagAuditVO.setActionCode("USPS INVOIC Acceptance");
									if("OP".equals(invoicDetailsVO.getInvoicPayStatus())){
										additionalInformation = new StringBuilder("Mailbag moved to Accepted Overpay bucket from Overpay bucket as a part of over payment acceptance");
									}else{
										additionalInformation = new StringBuilder("Mailbag moved to Accepted Shortpay bucket from Shortpay bucket as a part of short payment acceptance");
									}
								}else{
								mailbagAuditVO.setActionCode("USPS INVOIC Acceptance");
								additionalInformation = new StringBuilder("USPS INVOIC amount of ").append(invoicDetailsVO.getInvoicamount()).append(" accepted");
								}
								mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());
								}
								mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());  //last updated user
								mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
								mailbagAuditVO.setTxnLocalTime(time);//transaction time
								mailbagAuditVO.setTxnTime(time);
								mailbagAuditVO.setAuditTriggerPoint("MRA077");
								
								AuditUtils.performAudit(mailbagAuditVO); 
							}
						}
						 
					     log.exiting(CLASSNAME, "saveClaimDetails ");	
				}
			 
			 /**
			  * 
			  * 	Method		:	MRAAuditBuilder.auditRateCardDetails
			  *	Added by 	:	A-5219 on 22-Oct-2020
			  * 	Used for 	:
			  *	Parameters	:	@param rateCardVO
			  *	Parameters	:	@throws SystemException 
			  *	Return type	: 	void
			  */
			 public void auditRateCardDetails(RateCardVO rateCardVO) throws SystemException{
				 log.entering(CLASSNAME, "auditRateCardDetails ");
				 if(rateCardVO.getRateLineVOss() != null && !rateCardVO.getRateLineVOss().isEmpty()){
					 Calendar time = null;
					 Collection<RateLineVO> rateLineVOs = rateCardVO.getRateLineVOss();
					 StringBuilder additionalInfo = null;
					 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
					 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();// Added by a-7871 for ICRD-231567
					  for (RateLineVO rateLineVo : rateLineVOs) {
						  if(rateLineVo.getOperationFlag() != null && rateLineVo.getOperationFlag().trim().length() > 0
								  && !"NOOP".equals(rateLineVo.getOperationFlag())){
							   String level ="";
							   additionalInfo = new StringBuilder("");
							   RateLineAuditVO rateLineAuditVO= new RateLineAuditVO(RateLineAuditVO.AUDIT_MODULENAME, RateLineAuditVO.AUDIT_SUBMODULENAME, RateLineAuditVO.AUDIT_ENTITY);
							   rateLineAuditVO.setLastUpdateDate(rateLineVo.getLastUpdateTime());
							   rateLineAuditVO.setLastUpdateUser(rateLineVo.getLastUpdateUser());
							   rateLineAuditVO.setStationCode(logonAttributes.getStationCode());
							   rateLineAuditVO.setLastUpdateTime(time); 
							   rateLineAuditVO.setOrigin(rateLineVo.getOrigin());
							   rateLineAuditVO.setDestination(rateLineVo.getDestination());
							   rateLineAuditVO.setRateCardID(rateLineVo.getRateCardID());
							   rateLineAuditVO.setLastUpdateUser(logonAttributes.getUserId());
							   rateLineAuditVO.setLastUpdateDate(rateLineVo.getLastUpdateTime());
							   rateLineAuditVO.setRateLineSerNum(rateLineVo.getRatelineSequenceNumber());
							   level = (rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("A")) ? "(ARP)" : "(CITY)";
							   rateLineAuditVO.setLevel((rateLineVo.getOrgDstLevel() != null && rateLineVo.getOrgDstLevel().startsWith("C")) ? "C" : "A");
							   additionalInfo.append("Rate Card ID:").append(rateLineVo.getRateCardID()).append(" with Origin ").append(rateLineAuditVO.getOrigin())
							   .append(level).append(" - ").append("Destination ").append(rateLineAuditVO.getDestination()) 
							   .append(level);
							   if(RateLineVO.OPERATION_FLAG_INSERT.equals(rateLineVo.getOperationFlag())){
								   rateLineAuditVO.setActionCode("UPU Rateline Created");
								   rateLineAuditVO.setAuditRemarks("UPU Rateline Created");
								   rateLineAuditVO.setAdditionalInformation(additionalInfo.append(" Created").toString());
								   AuditUtils.performAudit(rateLineAuditVO);
							   }else if(RateLineVO.OPERATION_FLAG_UPDATE.equals(rateLineVo.getOperationFlag())){
								   rateLineAuditVO.setActionCode("UPU Rateline Updated");
								   rateLineAuditVO.setAuditRemarks("UPU Rateline Updated");
								   rateLineAuditVO.setAdditionalInformation(additionalInfo.append(" Updated").toString());
								   AuditUtils.performAudit(rateLineAuditVO);
							   }else if(RateLineVO.OPERATION_FLAG_DELETE.equals(rateLineVo.getOperationFlag())){
								   rateLineAuditVO.setActionCode("UPU Rateline Deleted");
								   rateLineAuditVO.setAuditRemarks("UPU Rateline Deleted");
								   rateLineAuditVO.setAdditionalInformation(additionalInfo.append(" Deleted").toString());
								   AuditUtils.performAudit(rateLineAuditVO);
							   }
					  		}
						 }
				 }
			     log.exiting(CLASSNAME, "auditRateCardDetails ");	
			}
			 /**
			  * 
			  * 	Method		:	MRAAuditBuilder.auditBatchSettlement
			  *	Added by 	:	A-10647 on 10-Jan-2022
			  * 	Used for 	: creating batchSettlementAuditVO
			  *	Parameters	:	@param detailVO
			  *	Parameters	:	@param actionCode
			  *	Parameters	:	@throws SystemException 
			  *	Return type	: 	void
			  */
			 public void auditBatchSettlement(PaymentBatchDetailsVO detailVO,String actionCode) throws SystemException{
				 log.entering(CLASSNAME, "auditBatchSettlement");
				 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				 BatchSettlementAuditVO batchSettlementAuditVO = new BatchSettlementAuditVO(BatchSettlementAuditVO.AUDIT_MODULENAME,
						 BatchSettlementAuditVO.AUDIT_SUBMODULENAME,BatchSettlementAuditVO.ENTITY) ;
				 batchSettlementAuditVO.setCompanyCode(detailVO.getCompanyCode());
				 batchSettlementAuditVO.setBatchID(detailVO.getBatchID());
				 batchSettlementAuditVO.setActionCode(actionCode);
				 batchSettlementAuditVO.setUserId(logonAttributes.getUserId());
				 batchSettlementAuditVO.setStationCode(logonAttributes.getStationCode());
				 StringBuilder additionalInfo = new StringBuilder();
				 if(actionCode.equalsIgnoreCase(BTHSTL_SAVE)){ 
					 additionalInfo.append(PAYMENT_BTCH).append(detailVO.getBatchID()).append(FORTHEDATE)
					 .append(detailVO.getBatchDate().toDisplayDateOnlyFormat()).append(" created for the amount ").append(detailVO.getBatchAmount())
							.append(" ") .append(detailVO.getBatchCurrency());
				 } else if(actionCode.equalsIgnoreCase(BTHSTL_UPDATE)){
					 additionalInfo.append("Applied ").append(detailVO.getAuditAmount()).append(" ") 
					 .append(detailVO.getBatchCurrency()).append(" on Mail Invoices ").append(detailVO.getProcessedInvoice());
				 } else if(actionCode.equalsIgnoreCase(BTHSTL_DELETE)){
					 additionalInfo.append(PAYMENT_BTCH).append(detailVO.getBatchID()).append(FORTHEDATE)
					 .append(detailVO.getBatchDate().toDisplayDateOnlyFormat()).append(" deleted for the amount ").append(detailVO.getAuditAmount())
					 .append(" ").append(detailVO.getBatchCurrency());
				 } else if(actionCode.equalsIgnoreCase(BTHSTL_EDIT)){
					 additionalInfo.append(PAYMENT_BTCH).append(detailVO.getBatchID()).append(FORTHEDATE)
					.append(detailVO.getBatchDate().toDisplayDateOnlyFormat()) .append(" with original value ").append(detailVO.getCurrentBatchAmount())
					.append(" ").append(detailVO.getBatchCurrency()).append(" updated to ").append(detailVO.getBatchAmount())
					 .append(" ").append(detailVO.getBatchCurrency()); 
				 } else{
					 additionalInfo.append(detailVO.getAuditRemark());
				 }  
				 batchSettlementAuditVO.setAdditionalInformation(additionalInfo.toString());
				 batchSettlementAuditVO.setAuditRemarks(additionalInfo.toString());
				 batchSettlementAuditVO.setAuditTriggerPoint(detailVO.getSource());
				   AuditUtils.performAudit(batchSettlementAuditVO);  
				   log.exiting(CLASSNAME, "auditBatchSettlement ");	 
			}
			
			 
	public void changeBillingMatrixStatusUpdate(BillingMatrixFilterVO billingMatrixFilterVO, String statusandTranx)
			throws ProxyException, SystemException {
		log.entering(CLASSNAME, "changeBillingMatrixStatusUpdate");
		String[] txnInfo = statusandTranx.split("-");
		String status = txnInfo[0];
		String matrixId = billingMatrixFilterVO.getBillingMatrixId();
		String convertedStatus = statusConvertion(status);
		StringBuilder auditAdInfo = new StringBuilder("");
		String actionCode = "Modified Billing matrix";
		auditAdInfo = auditAdInfo.append(BILMTRX_ID).append(":").append(matrixId).append(" Status updated to ")
				.append(convertedStatus);
		auditLogEntry(matrixId, billingMatrixFilterVO.getCompanyCode(), actionCode, auditAdInfo);
		log.exiting(CLASSNAME, "changeBillingMatrixStatusUpdate");
	}
				
				
	public void changeEnddate(Collection<BillingLineVO> billingLineVos, String changedate) throws SystemException {
		log.entering(CLASSNAME, "changeEnddate");
		StringBuilder information = new StringBuilder("Valid end date updated to ") .append(changedate).append(" for the following Rate line IDs : ");
		StringBuilder sequenceNumber = new StringBuilder("");
		for (BillingLineVO billingLineVo : billingLineVos) {
			sequenceNumber.append(billingLineVo.getBillingLineSequenceNumber()).append(",");
		}
		information.append(sequenceNumber.substring(0, sequenceNumber.length() - 1));
		String matrixId = billingLineVos.iterator().next().getBillingMatrixId();
		String companyCode = billingLineVos.iterator().next().getCompanyCode();
		String actionCode = "Modified the Rate line";
		auditLogEntry(matrixId, companyCode, actionCode, information);
		log.exiting(CLASSNAME, "changeEnddate");
	}
	
	private String statusConvertion(String status) {
		if (status.equalsIgnoreCase(STATUS_ACTIVE)) {
			return STA_ACT;
		} else if (status.equalsIgnoreCase(STATUS_CANCELLED)) {
			return STA_CAN;
		} else if (status.equalsIgnoreCase(STATUS_INACTIVE)) {
			return STA_INA;
		} else if (status.equalsIgnoreCase(STATUS_EXPIRED)) {
			return STA_EXP;
		} else {
			return status;
		}

	}

	public void auditLogEntry(String matrixId, String companyCode, String actionCode, StringBuilder adInfo)
			throws SystemException {
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME, BillingMatrixAuditVO.AUDIT_ENTITY);
		billingMatrixAuditVO.setBillingmatrixID(matrixId);
		billingMatrixAuditVO.setCompanyCode(companyCode);
		billingMatrixAuditVO.setAdditionalInformation(adInfo.toString());
		billingMatrixAuditVO.setActionCode(actionCode);
		AuditUtils.performAudit(billingMatrixAuditVO);

	}
}
