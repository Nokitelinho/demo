/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.SurchargeDetailsCommand.java
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
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.SurchargeDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.SurchargeDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 4, 2019	:	Draft
 */
public class SurchargeDetailsCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("MAIL MRA GPABILLING");
	private static final String NOMAILSSELECTED="mailtracking.mra.gpabilling.billingentries.msg.err.nomailselected";
	private static final String MULTIPLESELECTED="mailtracking.mra.gpabilling.billingentries.msg.err.multipleselected";
	private static final String NOSURCHARGESELECTED="mailtracking.mra.gpabilling.billingentries.msg.err.nosurchargeselected";
	private static final String SURCHARGECHARGEHEAD_ONETIM = "mailtracking.mra.surchargeChargeHead";
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String OVERRIDEROUNDING = "mailtracking.mra.overrideroundingvalue";
	private static final String STATUS_SUCCESS = "success";
	private static final String EMPTY_STRING ="";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("SurchargeDetailsCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		CN51CN66FilterVO cn51cn66FilterVO=null;
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Map<String, String> systemParameters = null;
		double totalAmount = 0.0;
		Collection<SurchargeBillingDetailVO> surchargeBillingDetailVOs = null;
		Collection<String> parameterTypes = new ArrayList<String>(); 
		    parameterTypes.add(BASE_CURRENCY);
		    parameterTypes.add(OVERRIDEROUNDING);	    
		Collection<GPABillingEntryDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedBillingDetails();
		Collection<DocumentBillingDetailsVO> selectedDetailsVOs = MailMRAModelConverter.constructDocumentBillingDetailsVOs(selectedBilling, logonAttributes);
    	log.log(Log.INFO, "selectedDetailsVOs....in ReRateCommand....",
    			selectedDetailsVOs);
		 try{
	   	      oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(companyCode,getOneTimeParameterTypes());
	   	      systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(parameterTypes);
	   	  }catch (BusinessDelegateException e){
	   	      actionContext.addAllError(handleDelegateException(e));
	   	  }	 
			Collection<OneTimeVO> chargeHeadOneTime = oneTimeValues.get(SURCHARGECHARGEHEAD_ONETIM);			 
			String baseCurrency = systemParameters.get(BASE_CURRENCY);
			String overrideRounding = systemParameters.get(OVERRIDEROUNDING);	
			
    	if(selectedDetailsVOs!=null && !selectedDetailsVOs.isEmpty()){
    		if(selectedDetailsVOs.size() > 1){
            	actionContext.addError(new ErrorVO(MULTIPLESELECTED));
            	return;
    		}
    		DocumentBillingDetailsVO documentBillingDetailsVO = selectedDetailsVOs.iterator().next();
    		if(documentBillingDetailsVO!=null){
    		if(documentBillingDetailsVO.getSurChg().getAmount()==0.0){  
            	actionContext.addError(new ErrorVO(NOSURCHARGESELECTED));
            	return;
    		} 
    		cn51cn66FilterVO = new CN51CN66FilterVO();
			cn51cn66FilterVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
			cn51cn66FilterVO.setBaseCurrency(baseCurrency);
			cn51cn66FilterVO.setInvoiceNumber(documentBillingDetailsVO.getInvoiceNumber());
			if(documentBillingDetailsVO.getMailSequenceNumber()!=0){
				cn51cn66FilterVO.setMailSeqnum(documentBillingDetailsVO.getMailSequenceNumber());
			}else{
			cn51cn66FilterVO.setSequenceNumber(0);
			}
			cn51cn66FilterVO.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
			cn51cn66FilterVO.setGpaCode(documentBillingDetailsVO.getGpaCode());
			cn51cn66FilterVO.setConsigneeDocumentNumber(documentBillingDetailsVO.getCsgDocumentNumber());
			cn51cn66FilterVO.setMcaNumber(documentBillingDetailsVO.getCcaRefNumber());  
    		}
    		
			surchargeBillingDetailVOs = new MailTrackingMRADelegate().findSurchargeBillableDetails(cn51cn66FilterVO);
			if (surchargeBillingDetailVOs != null&&surchargeBillingDetailVOs.size()>0) {
				for (SurchargeBillingDetailVO surchargeBillingDetailVO : surchargeBillingDetailVOs) {
					if (surchargeBillingDetailVO.getChargeAmt() != null) {
						totalAmount = totalAmount+ surchargeBillingDetailVO.getChargeAmt().getAmount();
					}
					if (surchargeBillingDetailVO.getChargeHead() != null && !EMPTY_STRING.equals(surchargeBillingDetailVO.getChargeHead())) {
						if (chargeHeadOneTime != null) {
							for (OneTimeVO oneTimeVO : chargeHeadOneTime) {
								if (oneTimeVO.getFieldValue().equals(surchargeBillingDetailVO.getChargeHead())) {
									surchargeBillingDetailVO.setChargeHeadDesc(oneTimeVO.getFieldDescription());
									break;
								}
							}
						}
					}
/*					if(!MailConstantsVO.FLAG_NO.equals(overrideRounding)){
						surchargeBillingDetailVO.setTotalAmount(Double.toString(totalAmount));
					}else{
						double totalAmount_rounded= getScaledValue(totalAmount,2);
						surchargeBillingDetailVO.setTotalAmount(Double.toString(totalAmount_rounded));
					}	*/				
			} 
			}
			
			ArrayList<SurchargeDetails> surchargeDetails= MailMRAModelConverter.constructSurchargeDetails(surchargeBillingDetailVOs);
			if(surchargeDetails!=null && !surchargeDetails.isEmpty()){
			gpaBillingEnquiryModel.setSurchargeDetails(surchargeDetails);
			}
			gpaBillingEnquiryModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
			results.add(gpaBillingEnquiryModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);
    	}else{
        	actionContext.addError(new ErrorVO(NOMAILSSELECTED));
        	return;
    	}
		
	}
	/**
	 * 	Method		:	SurchargeDetailsCommand.getScaledValue
	 *	Added by 	:	A-4809 on Mar 4, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param value
	 *	Parameters	:	@param precision
	 *	Parameters	:	@return 
	 *	Return type	: 	double
	 */
  	private double getScaledValue(double value, int precision) {

  		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
  		return bigDecimal.setScale(precision,
  				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
  	}

	/**
	 * 	Method		:	ListBillingEntryCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-4809 on Feb 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	  private Collection<String> getOneTimeParameterTypes(){
		    Collection<String> parameterTypes = new ArrayList<String>();
		    parameterTypes.add(SURCHARGECHARGEHEAD_ONETIM);
		    return parameterTypes;
		  }
}
