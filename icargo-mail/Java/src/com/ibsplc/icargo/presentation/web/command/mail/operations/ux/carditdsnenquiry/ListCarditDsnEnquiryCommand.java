package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry.ListCarditDsnEnquiryCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Sep-2019		:	Draft
 */
public class ListCarditDsnEnquiryCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("OPERATIONS CARDITDSNENQUIRY");
	private static final String NO_DSN_SELECTED = "mail.operations.carditdsnenquiry.err.nodsnpresent";
	private static final String MAIL_COUNT_LIMIT_SYSPAR = "mail.operations.mailcountlimitforawbhandling";
	public void execute(ActionContext actionContext){
		
		log.entering("ListCarditDsnEnquiryCommand","execute"); 
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		CarditDsnEnquiryModel carditDsnEnquiryModel=
				(CarditDsnEnquiryModel) actionContext.getScreenModel();
		CarditFilter carditFilter=
				(CarditFilter) carditDsnEnquiryModel.getCarditFilter();
		DSNEnquiryFilterVO dsnEnquiryFilterVO=null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		Collection<DSNDetails> dsnDetailsCollection = null;  
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ResponseVO responseVO = new ResponseVO(); 
		Page<DSNVO> dsnvos=null;
		CarditEnquiryFilterVO carditEnquiryFilterVO =null;
		String[] totalWeightAndCount=null;
		
		dsnEnquiryFilterVO=
				MailOperationsModelConverter.constructDsnEnquiryFilter(carditFilter, logonAttributes);
		
		if(carditDsnEnquiryModel.getDisplayPage() !=null &&
				carditDsnEnquiryModel.getDisplayPage().trim().length()>0){
			dsnEnquiryFilterVO.setPageNumber(Integer.parseInt(carditDsnEnquiryModel.getDisplayPage()));
		}
		else{
			dsnEnquiryFilterVO.setPageNumber(1);
		}
		
		if(carditDsnEnquiryModel.getPageSize() !=null &&
				carditDsnEnquiryModel.getPageSize().trim().length()>0){
			dsnEnquiryFilterVO.setPageSize(Integer.parseInt(carditDsnEnquiryModel.getPageSize()));
		}
		else{
			dsnEnquiryFilterVO.setPageSize(10);
		}
		
		carditDsnEnquiryModel.setTotalCount("0");
		carditDsnEnquiryModel.setTotalWeight("0");
	  	//added by A-8353 for ICRD-348355 starts
		Collection<String> systemParameterCodes = new ArrayList<String>();   
    	systemParameterCodes.add(MAIL_COUNT_LIMIT_SYSPAR);
        String mailCount = findSystemParameterByCodes(systemParameterCodes).get(MAIL_COUNT_LIMIT_SYSPAR);
    	if(mailCount==null){    
    		mailCount="0";
    	}
    	carditDsnEnquiryModel.setMailCountFromSyspar(mailCount);
    	//added by A-8353 for ICRD-348355 ends
		try {
			dsnvos=mailTrackingDefaultsDelegate.listCarditDsnDetails(dsnEnquiryFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			errors = handleDelegateException(businessDelegateException); 
		}
		
		if (errors != null && errors.size() > 0) {
    		actionContext.addAllError((List<ErrorVO>) errors); 
    	    return;
		}
		
		if(dsnvos==null || dsnvos.isEmpty()){
			actionContext.addError(new ErrorVO(NO_DSN_SELECTED));
			return;
		}
		else{
			carditEnquiryFilterVO=
					MailOperationsModelConverter.constructCarditFilterVO(carditFilter, logonAttributes);
			try {
				totalWeightAndCount=mailTrackingDefaultsDelegate.findGrandTotals(carditEnquiryFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				errors = handleDelegateException(businessDelegateException); 
			}
			
			if (errors != null && errors.size() > 0) {
	    		actionContext.addAllError((List<ErrorVO>) errors); 
	    	    return;
			}
			else{
				if(totalWeightAndCount!=null && totalWeightAndCount.length==2){
					carditDsnEnquiryModel.setTotalCount(totalWeightAndCount[0]);
					carditDsnEnquiryModel.setTotalWeight(totalWeightAndCount[1]);
				}
			}
			
			dsnDetailsCollection=MailOperationsModelConverter.convertDsnVosToDsnModel(dsnvos);
			PageResult<DSNDetails> dsnDetailsCollectionPage = 
					new PageResult<DSNDetails>(dsnvos,(ArrayList<DSNDetails>) dsnDetailsCollection);
			carditDsnEnquiryModel.setDsnDetailsCollectionPage(dsnDetailsCollectionPage);	
		}
		ArrayList<CarditDsnEnquiryModel> result=
				 new ArrayList<>();  
		 result.add(carditDsnEnquiryModel);    
		 responseVO.setResults(result);  
	     responseVO.setStatus("success");
	     actionContext.setResponseVO(responseVO); 
	     return;
			
	}
	/**
	 * @author A-8353
	 * @param systemParameterCodes
	 * 
	 */
	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.entering("UploadMailDetailsCommand","findSystemParameterByCodes");
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		log.exiting("UploadMailDetailsCommand","findSystemParameterByCodes");
		return results;
	}

}
