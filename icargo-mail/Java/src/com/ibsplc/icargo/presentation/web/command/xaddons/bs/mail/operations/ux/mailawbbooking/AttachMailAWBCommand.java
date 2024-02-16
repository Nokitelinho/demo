package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.MailAwbBookingPopupModel;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailawbbooking.AttachMailAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	27-Sep-2019		:	Draft
 */
public class AttachMailAWBCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "bsmail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.searchconsignment";
	private static final String STATUS_EXCECUTED = "E";
	private static final String NOT_EXECUTED="xaddons.bs.mail.operations.awb.status.excecuted";
	private static final String AWB_DESTINATION_ERRORTYPE = "mail.operations.awbdestination.mismatch.errortype";
	private static final String MAIL_COUNT_EXCEED = "mailtracking.defaults.searchconsignment.selectedmailcountexceeded";
	private static final String XADNS_MAIL_COUNT_EXCEED = "xaddons.mail.operations.searchconsignment.attach.selectedmailcountexceeded";
	private static final String AWB_DEST_MISMATCH_ERR = "xaddons.mail.operations.searchconsignment.attach.awbdestinationmismatch.error";
	private static final String AWB_DEST_MISMATCH_WRN = "xaddons.mail.operations.searchconsignment.attach.awbdestinationmismatch.warning";
	private Log log = LogFactory.getLogger("OPERATIONS AttachMailAWBCommand");
	
	public void execute(ActionContext actionContext){
		
		log.entering("AttachMailAWBCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailAwbBookingPopupModel mailAwbBookingPopupModel=
				(MailAwbBookingPopupModel) actionContext.getScreenModel();
    	Collection<MailbagVO> selectedMailBagVOs =
    			mailAwbBookingPopupModel.getSelectedMailBagVOs();
    	BaseMailOperationsDelegate delegate = 
    			new BaseMailOperationsDelegate();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	CarditEnquiryFilterVO carditEnquiryFilterVO = null;
    	ResponseVO responseVO = new ResponseVO();

    	selectedMailBagVOs =mailAwbBookingPopupModel.getSelectedMailBagVOs();
    	Collection<MailBookingDetailVO> selectedMailBookingVOs = 
    			new ArrayList<MailBookingDetailVO>();
    	Collection<String> systemParameterCodes = new ArrayList<String>(); 
    	
    	if(mailAwbBookingPopupModel.getCarditFilter()!=null){
    		carditEnquiryFilterVO=MailOperationsModelConverter.constructCarditFilterVO(
    				mailAwbBookingPopupModel.getCarditFilter(), logonAttributes);
    	}
    	
    	if(mailAwbBookingPopupModel.getMailBookingDetailsCollection()!=null){
    		selectedMailBookingVOs= MailOperationsModelConverter.convertMailBookingModelCollectionToVos(
    				mailAwbBookingPopupModel.getMailBookingDetailsCollection());
    	}
    	
    	
    	systemParameterCodes.add(AWB_DESTINATION_ERRORTYPE);
    	String errorType = 
    			findSystemParameterByCodes(systemParameterCodes).get(AWB_DESTINATION_ERRORTYPE);

    	
    	int i=1;      
    	MailBookingDetailVO mailBookingDetailVO=selectedMailBookingVOs.iterator().next(); 
    	
    	
    	try {
			delegate.validateMailTags(selectedMailBagVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
    	
		if(errors!=null&&errors.size()>0){
			actionContext.addAllError((List<ErrorVO>) errors);	
    		return;
		}

		for (MailBookingDetailVO mailDetailVO : selectedMailBookingVOs) {

			mailBookingDetailVO = mailDetailVO;
			String status = mailBookingDetailVO.getShipmentStatus();
			if (STATUS_EXCECUTED.equals(status)) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				error = new ErrorVO(NOT_EXECUTED);
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}

		}
       
       mailBookingDetailVO.setDestinationCheckReq(true);
       if("N".equals(errorType) || errorType==null||"C".equals(mailAwbBookingPopupModel.getWarningFlag())){
    	   mailBookingDetailVO.setDestinationCheckReq(false);
       } 
       
       
       int mismatchCount=0;
		try {
			mismatchCount = delegate.saveMailBookingDetails(selectedMailBagVOs, mailBookingDetailVO,
					carditEnquiryFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		if (errors != null && errors.size() > 0) {
			for (ErrorVO err : errors) {
				if (MAIL_COUNT_EXCEED
						.equalsIgnoreCase(err.getErrorCode())) {
					Object[] obj = { carditEnquiryFilterVO.getMailCount() };
					ErrorVO error = new ErrorVO(
							XADNS_MAIL_COUNT_EXCEED, obj);

					errors = new ArrayList<ErrorVO>();
					errors.add(error);
					actionContext.addAllError((List<ErrorVO>) errors);	
		    		return;
				} else {
					actionContext.addAllError((List<ErrorVO>) errors);	
		    		return;
				}
			}

		}
		
		if (mismatchCount > 0) {
			Object[] obj = { mismatchCount };

			if ("E".equals(errorType)) {
				ErrorVO error = new ErrorVO(
						AWB_DEST_MISMATCH_ERR, obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors = new ArrayList<ErrorVO>();
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);	
	    		return;
			} else if ("W".equals(errorType)) {
				ErrorVO error = new ErrorVO(
						AWB_DEST_MISMATCH_WRN, obj);
				error.setErrorDisplayType(ErrorDisplayType.WARNING);
				errors = new ArrayList<ErrorVO>();
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);	
			}

		}
		
		List<MailAwbBookingPopupModel> results = new ArrayList<MailAwbBookingPopupModel>();
		results.add(mailAwbBookingPopupModel);
		responseVO.setResults(results);  
	    responseVO.setStatus("success");
	    actionContext.setResponseVO(responseVO);
	    log.exiting("AttachMailAWBCommand","execute");
	    return;
    	

	}
	
	private Map<String, String> findSystemParameterByCodes(
			Collection<String> systemParameterCodes) {
		
		log.entering("UploadMailDetailsCommand", "findSystemParameterByCodes");
		Map<String, String> results = null;
		try {
			results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		log.exiting("UploadMailDetailsCommand", "findSystemParameterByCodes");
		return results;
	}
	
	
}
