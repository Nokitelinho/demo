package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.carditdsnenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.carditdsnenquiry.DettachMailAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	24-Sep-2019		:	Draft
 */
public class DetachMailAWBCommand extends AbstractCommand  {
	
	private Log log = LogFactory.getLogger("OPERATIONS CARDITDSNENQUIRY");
	private static final String NO_DSN_SELECTED = "mail.operations.carditdsnenquiry.err.nodsnseleted";
	private static final String AWB_ACCEPTED = "mail.operations.carditdsnenquiry.err.awbaccepted";
	private static final String SUCCESS = "success";
	
	public void execute(ActionContext actionContext){
	
		log.entering("DetachMailAWBCommand","execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		CarditDsnEnquiryModel carditDsnEnquiryModel=
				(CarditDsnEnquiryModel) actionContext.getScreenModel();
		CarditFilter carditFilter=
				(CarditFilter)carditDsnEnquiryModel.getCarditFilter();
		BaseMailOperationsDelegate baseMailOperationsDelegate=
				new BaseMailOperationsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ResponseVO responseVO = new ResponseVO();
		CarditEnquiryFilterVO carditEnquiryFilterVO=null;
		Collection<MailbagVO> selectedMailBagVO=
				carditDsnEnquiryModel.getMailbagVoCollection();
		
		if(selectedMailBagVO==null || selectedMailBagVO.size()==0){
			actionContext.addError(new ErrorVO(NO_DSN_SELECTED));
			return;
		}
		
		carditEnquiryFilterVO=
				MailOperationsModelConverter.constructCarditFilterVO(carditFilter, logonAttributes);
		if (selectedMailBagVO != null && selectedMailBagVO.size() > 0) {
			for (MailbagVO mailbagVO : selectedMailBagVO) {
				if("Y".equals(mailbagVO.getAccepted())){
					ErrorVO error = new ErrorVO(AWB_ACCEPTED);
					errors.add(error);
					actionContext.addAllError((List<ErrorVO>) errors);
		            log.exiting("DettachMailAWBCommand","isacceptedcondition");
					return;
				}
			}
			try {
				baseMailOperationsDelegate.dettachMailBookingDetails(selectedMailBagVO,carditEnquiryFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
		}
		if (errors != null && errors.size() > 0) {
    		actionContext.addAllError((List<ErrorVO>) errors); 
    	    return;
		}
		
		ArrayList<CarditDsnEnquiryModel> result=
				new ArrayList<>();  
		result.add(carditDsnEnquiryModel);    
		responseVO.setResults(result);  
		responseVO.setStatus(SUCCESS);
		actionContext.setResponseVO(responseVO); 
		return;
		
		
	}

}
