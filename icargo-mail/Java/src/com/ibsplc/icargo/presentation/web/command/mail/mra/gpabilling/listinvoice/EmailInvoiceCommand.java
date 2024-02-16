/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.EmailInvoiceCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	09-Jan-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.EmailInvoiceCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	09-Jan-2014	:	Draft
 */
public class EmailInvoiceCommand  extends BaseCommand{

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	private static final String SUCCESS="send_success";
	private static final String FAILURE="send_failure";
	private Log log = LogFactory.getLogger("MAILTRACKING MRA-GPABILLING");
	// Added by A-8527 for IASCB-22915
	private static final String UNITCODDISP_ONTIME ="mail.mra.defaults.weightunit";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-4809 on 09-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		String counter = listGPABillingInvoiceForm.getSelectedRow();
		if(counter!=null && !counter.contains(",")){     
			counter.concat(",");    
		}
		
		String companyCode = logonAttributesVO.getCompanyCode();
		String[] selectedRows=counter.split(",");
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
		.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
		cN51SummaryVOs);
		Collection<CN51CN66VO> cN51CN66VOs = new ArrayList<CN51CN66VO>();
		Map<String,CN51CN66VO> selectedInvoiceMap =
		          new HashMap<String,CN51CN66VO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(selectedRows!=null )
		for(int index=0;index<selectedRows.length;index++){
			//Modified as part of ICRD-234283 by A-5526 
			String selectedRecord = counter.split(",")[index];
			String parent = selectedRecord.split("~")[0];
			String child="";
			if(selectedRecord.contains("~"))
			 child = selectedRecord.split("~")[1];
			CN51CN66VO cN51CN66VO = null;
			CN51SummaryVO cnSummaryVO =null;
			CN51CN66FilterVO cN51CN66FilterVO = new CN51CN66FilterVO();
			if(parent!=null && !parent.isEmpty()){
				cnSummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(parent));    
			cN51CN66FilterVO.setCompanyCode(cnSummaryVO.getCompanyCode());    
			cN51CN66FilterVO.setGpaCode(cnSummaryVO.getGpaCode());
			cN51CN66FilterVO.setInvoiceNumber(cnSummaryVO.getInvoiceNumber());
			}

			Collection<CN51DetailsVO> cN51DetailsVOs =null;
			Collection<CN66DetailsVO> cN66DetailsVOs =null;
			// Added by A-8527 for IASCB-22915 starts
			Map<String, Collection<OneTimeVO>> hashMap = null;
			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(UNITCODDISP_ONTIME);
			try {
				hashMap = new SharedDefaultsDelegate().findOneTimeValues(
						companyCode, oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			// Added by A-8527 for IASCB-22915 Ends
			if (cnSummaryVO.getRebillInvoiceDetails() != null && !cnSummaryVO.getRebillInvoiceDetails().isEmpty() && child.trim().length()>0) {
				ArrayList<CN51SummaryVO> rebillList = new ArrayList<CN51SummaryVO>(
						cnSummaryVO.getRebillInvoiceDetails());    

			try {
					cN51DetailsVOs = mailTrackingMRADelegate.generateCN51ReportPrint(cN51CN66FilterVO);
				} catch (BusinessDelegateException e) {
					log.log(Log.SEVERE, "Exception Caught" + e);
					handleDelegateException(e);
				}
			// Added by A-8527 for IASCB-22915 Starts
			if (hashMap != null && cN51DetailsVOs != null && hashMap.get(UNITCODDISP_ONTIME)!= null ) { 
				for (CN51DetailsVO cn51VO : cN51DetailsVOs) {
					String unitcode = cn51VO.getUnitCode();
						for(OneTimeVO onetimeVO :hashMap.get(UNITCODDISP_ONTIME)){
							if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
								cn51VO.setUnitCode(onetimeVO.getFieldDescription());
							}
						}
				}
			}
			// Added by A-8527 for IASCB-22915 Ends
				cnSummaryVO = rebillList.get(Integer.parseInt(child));
				//Added as part of ICRD-234283 by A-5526 starts
				if (cnSummaryVO.getRebillInvoiceNumber() != null) {
					cN51CN66VO = new CN51CN66VO();
					cN51CN66VO.setCompanyCode(cnSummaryVO.getCompanyCode());
					cN51CN66VO.setGpaCode(cnSummaryVO.getGpaCode());
					cN51CN66VO.setInvoiceNumber(cnSummaryVO.getRebillInvoiceNumber());
					cN51CN66VO.setRebillInvoice(true);
					cN51CN66VO.setCn51DetailsVOsColln(cN51DetailsVOs);
					if (!selectedInvoiceMap.containsKey(cN51CN66VO.getInvoiceNumber()))
						selectedInvoiceMap.put(cN51CN66VO.getInvoiceNumber(), cN51CN66VO);

				} //Added as part of ICRD-234283 by A-5526 ends

			} else {

				try {
				cN51DetailsVOs =mailTrackingMRADelegate.generateCN51ReportPrint(cN51CN66FilterVO);
				cN66DetailsVOs =mailTrackingMRADelegate.generateCN66ReportPrint(cN51CN66FilterVO);
				if(cN51DetailsVOs!= null && cN51DetailsVOs.size()>0){
					cN51CN66VO = new CN51CN66VO();
					cN51CN66VO.setCompanyCode(logonAttributesVO.getCompanyCode());
						cN51CN66VO.setInvoiceNumber(cnSummaryVO.getInvoiceNumber());
						cN51CN66VO.setGpaCode(cnSummaryVO.getGpaCode());
						// Added by A-8527 for IASCB-22915 Starts
						if (hashMap != null &&cN66DetailsVOs!=null && cN51DetailsVOs != null && hashMap.get(UNITCODDISP_ONTIME)!= null ) { 
							for (CN51DetailsVO cn51VO : cN51DetailsVOs) {
								String unitcode = cn51VO.getUnitCode();
									for(OneTimeVO onetimeVO :hashMap.get(UNITCODDISP_ONTIME)){
										if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
											cn51VO.setUnitCode(onetimeVO.getFieldDescription());
										}
									}
							}
							for (CN66DetailsVO cn66VO : cN66DetailsVOs) {
								String unitcode = cn66VO.getUnitcode();
									for(OneTimeVO onetimeVO :hashMap.get(UNITCODDISP_ONTIME)){
										if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
											cn66VO.setUnitcode(onetimeVO.getFieldDescription());
										}
									}
							}
						}
						// Added by A-8527 for IASCB-22915 Ends
					cN51CN66VO.setCn51DetailsVOsColln(cN51DetailsVOs);
					cN51CN66VO.setCn66DetailsVOsColln(cN66DetailsVOs);

						if (!selectedInvoiceMap.containsKey(cN51CN66VO.getInvoiceNumber()))
							selectedInvoiceMap.put(cN51CN66VO.getInvoiceNumber(), cN51CN66VO);
				}
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "Exception Caught"+e);
				handleDelegateException(e);
			}
			}

			// CN51SummaryVO
			// cn51SummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(selectedRows[index]));

		}
		if(selectedInvoiceMap!=null)
		cN51CN66VOs=selectedInvoiceMap.values();
		if(cN51CN66VOs!=null && cN51CN66VOs.size()>0){
			for(CN51CN66VO cN51CN66VO : cN51CN66VOs){
				try {
					mailTrackingMRADelegate.sendEmailInvoice(cN51CN66VO);
				} catch (BusinessDelegateException e) {
					log.log(Log.SEVERE, "Exception Caught"+e);
					handleDelegateException(e);
					errors = handleDelegateException(e);
				}
			}
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=FAILURE;
		}else{
			invocationContext.target=SUCCESS;
		}
	}

}
