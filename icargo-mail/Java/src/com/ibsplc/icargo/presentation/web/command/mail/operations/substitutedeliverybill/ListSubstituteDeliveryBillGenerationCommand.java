/*
 * ListSubstituteDeliveryBillGenerationCommand.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.substitutedeliverybill;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SubstituteDeliveryBillGenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3217
 *
 */
public class ListSubstituteDeliveryBillGenerationCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("SUBSTITUTE DELIVERY BILL GENERATION");

	/** Target Constants **/
	private static final String LISTSUBSTITUTEDELIVERYBILLGEN_SUCCESS = "substitutelist_success";

	private static final String LISTSUBSTITUTEDELIVERYBILLGEN_FAILURE = "substitutelist_failure";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";
	
	private static final String OUTBOUND = "O";

	

	/**
	 * @author A-3217
	 * @throws CommandInvocationException
	 * @param invocationContext
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {

		log.entering("ListSubstituteDeliveryBillGenerationCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm = (SubstituteDeliveryBillGenForm) invocationContext.screenModel;

		SubstituteDeliveryBillGenSession substituteDeliveryBillGenSession = getScreenSession(MODULE_NAME, SCREEN_ID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;

//		 VALIDATING FORM
		errors = validateForm(substituteDeliveryBillGenForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			substituteDeliveryBillGenSession.removeAllAttributes();
			invocationContext.target = LISTSUBSTITUTEDELIVERYBILLGEN_FAILURE;
			return;
		}
		
		ConsignmentFilterVO consignmentFilterVO = populateConsignmentFilterVO(substituteDeliveryBillGenForm,logonAttributes);
		
		try {
			consignmentDocumentVOs = new MailTrackingDefaultsDelegate().findDeliverBill(consignmentFilterVO);
			if(consignmentDocumentVOs != null && consignmentDocumentVOs.size() >0){
				substituteDeliveryBillGenSession.setConsignmentDocumentVOs(consignmentDocumentVOs);
			}
			
			 if(consignmentDocumentVOs == null || (consignmentDocumentVOs != null && consignmentDocumentVOs.size() == 0)) {
				   errorVO = new ErrorVO("mailtracking.defaults.deliverybill.msg.err.nodata");
				   errors.add(errorVO);
				   invocationContext.addAllError(errors);
				   substituteDeliveryBillGenSession.setConsignmentDocumentVOs(null);
				   invocationContext.target = LISTSUBSTITUTEDELIVERYBILLGEN_FAILURE;
				   return;
			   }
			
		} catch (BusinessDelegateException businessDelegateException) {
			errorVO = new ErrorVO("mailtracking.defaults.deliverybill.msg.err.noFlightCarrierCode");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			substituteDeliveryBillGenSession.removeAllAttributes();
			invocationContext.target = LISTSUBSTITUTEDELIVERYBILLGEN_FAILURE;
			return;
		}
		
		substituteDeliveryBillGenForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LISTSUBSTITUTEDELIVERYBILLGEN_SUCCESS;
		
		log.exiting("ListSubstituteDeliveryBillGenerationCommand", "execute");

	}
	
	
	/**
	 * This method is used for validating the form for the particular action
	 * @param substituteDeliveryBillGenForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		
		String opertionMode = substituteDeliveryBillGenForm.getRadioInboundOutbound(); 
		if (substituteDeliveryBillGenForm.getCarrierCode() == null
				|| ("").equals(substituteDeliveryBillGenForm.getCarrierCode())) {
			errorVO = new ErrorVO("mailtracking.defaults.deliverybill.msg.err.noFlightCarrierCode");
			formErrors.add(errorVO);
		}
		if (substituteDeliveryBillGenForm.getFlightNumber() == null
				|| ("").equals(substituteDeliveryBillGenForm.getFlightNumber())) {
			errorVO = new ErrorVO(
					"mailtracking.defaults.deliverybill.msg.err.noFlightNumber");
			formErrors.add(errorVO);
		}
		if(OUTBOUND.equals(opertionMode)){
			
			if (substituteDeliveryBillGenForm.getDepartureDate() == null
					|| ("").equals(substituteDeliveryBillGenForm.getDepartureDate())) {
				errorVO = new ErrorVO(
						"mailtracking.defaults.deliverybill.msg.err.noDeptDate");
				formErrors.add(errorVO);
			}
			
			if (substituteDeliveryBillGenForm.getDeparturePort() == null
					|| ("").equals(substituteDeliveryBillGenForm.getDeparturePort())) {
				errorVO = new ErrorVO(
						"mailtracking.defaults.deliverybill.msg.err.noDeptPort");
				formErrors.add(errorVO);
			}
		}else{
			if (substituteDeliveryBillGenForm.getArrivalDate() == null
					|| ("").equals(substituteDeliveryBillGenForm.getArrivalDate())) {
				errorVO = new ErrorVO(
						"mailtracking.defaults.deliverybill.msg.err.noArrDate");
				formErrors.add(errorVO);
			}
			
			if (substituteDeliveryBillGenForm.getArrivalPort() == null
					|| ("").equals(substituteDeliveryBillGenForm.getArrivalPort())) {
				 errorVO = new ErrorVO(
						"mailtracking.defaults.deliverybill.msg.err.noArrPort");
				formErrors.add(errorVO);
			}
		}
		

		return formErrors;
	}
	
	/**
	 * 
	 * @param substituteDeliveryBillGenForm
	 * @param logonAttributes
	 * @return
	 */
	private ConsignmentFilterVO populateConsignmentFilterVO(SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm,
			LogonAttributes logonAttributes){
		
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setCarrierCode(substituteDeliveryBillGenForm.getCarrierCode());
		consignmentFilterVO.setFltNumber(substituteDeliveryBillGenForm.getFlightNumber());
		if(OUTBOUND.equals(substituteDeliveryBillGenForm.getRadioInboundOutbound())){
			consignmentFilterVO.setScannedPort(substituteDeliveryBillGenForm.getDeparturePort());
			consignmentFilterVO.setFltDate(substituteDeliveryBillGenForm.getDepartureDate());
		}else{
			consignmentFilterVO.setScannedPort(substituteDeliveryBillGenForm.getArrivalPort());
			consignmentFilterVO.setFltDate(substituteDeliveryBillGenForm.getArrivalDate());
		}
		consignmentFilterVO.setOperationMode(substituteDeliveryBillGenForm.getRadioInboundOutbound());
		return consignmentFilterVO;
	}
	
	
}
