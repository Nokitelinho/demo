package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.addons.mail.operations.AddonsMailDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class AttachMailAWBCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "addonsmail.operations";
	private static final String SCREEN_ID = "addons.mail.operations.mailawbbooking";
	private static final String STATUS_EXCECUTED = "E";
	private static final String STATUS_VOID= "V";
	private static final String NOT_EXECUTED="AWB is in executed status.Hence cannot proceed";
	private static final String VOID_STATUS_ERROR="AWB is in void status.Hence cannot proceed";
	private static final String AWB_DESTINATION_ERRORTYPE = "mail.operations.awbdestination.mismatch.errortype";
	private static final String MAIL_COUNT_EXCEED = "mailtracking.defaults.searchconsignment.selectedmailcountexceeded";
	private static final String XADNS_MAIL_COUNT_EXCEED = "xaddons.mail.operations.searchconsignment.attach.selectedmailcountexceeded";
	private static final String AWB_DEST_MISMATCH_ERR = "addons.mail.operations.searchconsignment.attach.awbdestinationmismatch.error";
	private static final String AWB_DEST_MISMATCH_WRN = "addons.mail.operations.searchconsignment.attach.awbdestinationmismatch.warning";
	private Log log = LogFactory.getLogger("OPERATIONS AttachMailAWBCommand");
	
	public void execute(ActionContext actionContext){
		
		log.entering("AttachMailAWBCommand", "execute");
		
		LogonAttributes logonAttributes = getLogonAttribute(); 
		MailAwbBookingModel mailAwbBookingModel= (MailAwbBookingModel) actionContext.getScreenModel();
    	AddonsMailDelegate delegate = new AddonsMailDelegate();
    	Collection<ErrorVO> errors = new ArrayList<>();
    	CarditEnquiryFilterVO carditEnquiryFilterVO = null;
    	ResponseVO responseVO = new ResponseVO();

    	ArrayList<Mailbag> selectedMailbags = (ArrayList<Mailbag>) mailAwbBookingModel.getSelectedMailBags();
    	Collection<Mailbag> selectedMailbag = selectedMailbags;
    	Collection<MailbagVO> selectedMailBagVOs = MailOperationsModelConverter.constructMailBagVOs(selectedMailbag,logonAttributes);
    	Collection<MailBookingDetailVO> selectedMailBookingVOs = new ArrayList<>();
    	
    	if(mailAwbBookingModel.getCarditFilter()!=null){
    		carditEnquiryFilterVO=MailOperationsModelConverter.constructCarditFilterVO(
    				mailAwbBookingModel.getCarditFilter(), logonAttributes);
    	}
    	
    	if(mailAwbBookingModel.getMailBookingDetailsCollection()!=null){
    		selectedMailBookingVOs= MailOperationsModelConverter.convertMailBookingModelCollectionToVos(
    				mailAwbBookingModel.getMailBookingDetailsCollection());
    	}
    	
    	String errorType = findSystemParameter();
    	    
    	MailBookingDetailVO mailBookingDetailVO=selectedMailBookingVOs.iterator().next(); 
    	
    	
    	try {
			delegate.validateMailTags(selectedMailBagVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
    	
		if(errors!=null&&!errors.isEmpty()){
			actionContext.addAllError((List<ErrorVO>) errors);	
    		return;
		}

		for (MailBookingDetailVO mailDetailVO : selectedMailBookingVOs) {

			mailBookingDetailVO = mailDetailVO;
			String status = mailBookingDetailVO.getShipmentStatus();
			if (STATUS_EXCECUTED.equals(status)) {
				errors = new ArrayList<>();
				ErrorVO error = null;
				error = new ErrorVO(NOT_EXECUTED);
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}else if(STATUS_VOID.equals(status)){
				errors = new ArrayList<>();
				ErrorVO error = null;
				error = new ErrorVO(VOID_STATUS_ERROR);
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}

		}
       
       mailBookingDetailVO.setDestinationCheckReq(true);
       if("N".equals(errorType) || errorType==null||"C".equals(mailAwbBookingModel.getWarningFlag())){
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
		if (errors != null && !errors.isEmpty()) {
			Collection<ErrorVO> error = evaluateErrors(errors,carditEnquiryFilterVO);
			actionContext.addAllError((List<ErrorVO>) error);	
		}
		
		if (mismatchCount > 0) {
			Object[] obj = { mismatchCount };
			ErrorVO error = checkingForWarningOrError(errorType,obj);
				errors = new ArrayList<>();
				errors.add(error);
				actionContext.addAllError((List<ErrorVO>) errors);	
	    		return;
		}
		
		List<MailAwbBookingModel> results = new ArrayList<>();
		results.add(mailAwbBookingModel);
		responseVO.setResults(results);  
	    responseVO.setStatus("success");
	    actionContext.setResponseVO(responseVO);
	    log.exiting("AttachMailAWBCommand","execute");
	    return;
	}

	private String findSystemParameter() {
		Collection<String> systemParameterCodes = new ArrayList<>(); 
    	systemParameterCodes.add(AWB_DESTINATION_ERRORTYPE);

    	Map<String, String> result = null;
    	String errorType=null;
		try {
			result = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		if(result!=null && !result.isEmpty()){
			 errorType  = result.get(AWB_DESTINATION_ERRORTYPE);
		}
		return errorType;
	}

	private ErrorVO checkingForWarningOrError(String errorType, Object[] obj) {
		if ("E".equals(errorType)) {
			ErrorVO error = new ErrorVO(
					AWB_DEST_MISMATCH_ERR, obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			return error;
		} else if ("W".equals(errorType)) {
			ErrorVO error = new ErrorVO(
					AWB_DEST_MISMATCH_WRN, obj);
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			return error;
		}
		return null;
	}

	private Collection<ErrorVO> evaluateErrors(Collection<ErrorVO> errors, CarditEnquiryFilterVO carditEnquiryFilterVO) {
		for (ErrorVO err : errors) {
			if (MAIL_COUNT_EXCEED
					.equalsIgnoreCase(err.getErrorCode())) {
				Object[] obj = { carditEnquiryFilterVO.getMailCount() };
				ErrorVO error = new ErrorVO(
						XADNS_MAIL_COUNT_EXCEED, obj);

				errors = new ArrayList<>();
				errors.add(error);
			} else {
	    		return errors;
			}
		}
		return errors;
	}
}
