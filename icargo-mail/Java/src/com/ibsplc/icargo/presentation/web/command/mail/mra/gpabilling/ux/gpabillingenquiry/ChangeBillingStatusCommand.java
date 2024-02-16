/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ChangeBillingStatusCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Mar 4, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ChangeBillingStatusCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 4, 2019	:	Draft
 */
public class ChangeBillingStatusCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAIL MRA GPABILLING");
	private static final String STATUS_SUCCESS = "success";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String NOMAILSSELECTED="mailtracking.mra.gpabilling.billingentries.msg.err.nomailselected";
	private static final String CORRECTSTATUS="mailtracking.mra.gpabilling.billingentries.msg.err.correctstatus";
	private static final String SAMESTATUS="mailtracking.mra.gpabilling.billingentries.msg.err.samestatus";
	private static final String BILLED="BILLED";
	private static final String REPORTED="REPORTED";
	private static final String Billable="BILLABLE";
	private static final String On_Hold="ON HOLD";
	private static final String Proforma_Billed="PROFOMA BILLED";
	private static final String To_be_Reported="TO BE REPORTED";
	private static final String TO_BE_RERATED="TO BE RE-RATED";
	private static final String WITHDRAWN="WITHDRAWN";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ChangeBillingStatusCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Map<String, Collection<OneTimeVO>> oneTimeValueStatus = null;
		ResponseVO responseVO = new ResponseVO();
		List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		boolean isSame = true;
		String status = null;
		String remarks =null;
		StringBuilder sbul = null;
		int length = 0;
		int count = 0;
		Collection<GPABillingEntryDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedBillingDetails();
		GPABillingEntryFilter gpaBillingEntryFilter = (GPABillingEntryFilter)gpaBillingEnquiryModel.getGpaBillingEntryFilter();
		Collection<DocumentBillingDetailsVO> selectedDetailsVOs = MailMRAModelConverter.constructDocumentBillingDetailsVOs(selectedBilling, logonAttributes);
    	log.log(Log.INFO, "selectedDetailsVOs....in ReRateCommand....",
    			selectedDetailsVOs);
		 try{
	   	      oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(companyCode,getOneTimeParameterTypes());
	   	  }catch (BusinessDelegateException e){
	   	      actionContext.addAllError(handleDelegateException(e));
	   	  }	
		 Collection<OneTimeVO> billingStatus = oneTimeValues.get(KEY_BILLING_TYPE_ONETIME);
		 oneTimeValueStatus = findOneTimeValuesForStatus(billingStatus);
		if(selectedDetailsVOs!=null && !selectedDetailsVOs.isEmpty()){
			status = selectedDetailsVOs.iterator().next().getBillingStatus();
			remarks=selectedDetailsVOs.iterator().next().getRemarks();
			length = selectedDetailsVOs.size();
			gpaBillingEnquiryModel.setBillingStatus(status);
			gpaBillingEnquiryModel.setRemarks(remarks);
			for(DocumentBillingDetailsVO dtlVO :selectedDetailsVOs){
				if(BILLED.equals(dtlVO.getBillingStatus())|| Proforma_Billed.equals(dtlVO.getBillingStatus())
						||To_be_Reported.equals(dtlVO.getBillingStatus())||TO_BE_RERATED.equals(dtlVO.getBillingStatus())||REPORTED.equals(dtlVO.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(CORRECTSTATUS));
    	        	return;	  
				}
				if(!status.equals(dtlVO.getBillingStatus())){
					 isSame = false;	
					 break;
				}
				if(Billable.equals(dtlVO.getBillingStatus())||(On_Hold.equals(dtlVO.getBillingStatus()))
						||WITHDRAWN.equals(dtlVO.getBillingStatus())){
					 sbul=new StringBuilder(dtlVO.getDsn());
					 count++;
					 if(count<length){
						 sbul.append(","); 
					 }
				}
			}
			if(!isSame){
	        	actionContext.addError(new ErrorVO(SAMESTATUS));
	        	return;	
			}
			if(sbul!=null){
				gpaBillingEnquiryModel.setDsn(sbul.toString());
			}
		}else{
        	actionContext.addError(new ErrorVO(NOMAILSSELECTED));
        	return;
		}
		gpaBillingEnquiryModel.setOneTimeValuesStatus(MailMRAModelConverter.constructOneTimeValues(oneTimeValueStatus));
		gpaBillingEnquiryModel.setGpaBillingEntryFilter(gpaBillingEntryFilter);
		results.add(gpaBillingEnquiryModel);
		responseVO.setResults(results);
		responseVO.setStatus(STATUS_SUCCESS);
		actionContext.setResponseVO(responseVO);
	}
	/**
	 * 	Method		:	ChangeBillingStatusCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-4809 on Mar 4, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {
	    Collection<String> parameterTypes = new ArrayList<String>();
	    parameterTypes.add(KEY_BILLING_TYPE_ONETIME);
	    return parameterTypes;
	}
	/**
	 * 	Method		:	ChangeBillingStatusCommand.findOneTimeValuesForStatus
	 *	Added by 	:	A-4809 on Mar 14, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param billingStatus
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeValuesForStatus(Collection<OneTimeVO> billingStatus){
		Map<String, Collection<OneTimeVO>> oneTimeValueStatus = new HashMap<>();
		HashMap<Integer, OneTimeVO > hashmap=new HashMap() ;
		Collection<OneTimeVO> oneTimeVos=new ArrayList<OneTimeVO>();
		int i=0;
 		for(OneTimeVO oneTimeVO:billingStatus){
 			hashmap.put(i, oneTimeVO); 
 			if (!((MRAConstantsVO.BILLABLE).equals(oneTimeVO.getFieldValue())||(MRAConstantsVO.ONHOLD).equals(oneTimeVO.getFieldValue())
 					)){
 				hashmap.keySet().remove(i);
 			}
 			i++;
 		}
 		log.log(Log.FINE, " hashmap after remove---->>", hashmap);
		for(OneTimeVO  onetimeVO:hashmap.values()){
 			oneTimeVos.add(onetimeVO);
 		}		
		oneTimeValueStatus.put(KEY_BILLING_TYPE_ONETIME,oneTimeVos);
		return oneTimeValueStatus;
	}

}
