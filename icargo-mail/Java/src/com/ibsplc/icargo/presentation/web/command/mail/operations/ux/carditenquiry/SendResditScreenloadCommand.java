package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditenquiry;

import java.util.ArrayList;


import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.CarditEnquirySession;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SendResditScreenloadCommand  extends AbstractCommand{
	  
	public static final String CONST_MESSAGE_INFO_SEND_SUCCESS = "mailtracking.defaults.carditenquiry.msg.info.sendsuccessfully";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.carditenquiry";
	   
	private static final Log LOGGER = LogFactory.getLogger("OPERATIONS CARDITENQUIRY");
	@Override
	public void execute(ActionContext actionContext)
	    throws BusinessDelegateException {
		LOGGER.entering("SendResditScreenloadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		CarditEnquiryModel carditEnquiryModel = (CarditEnquiryModel)actionContext.getScreenModel();
		List<CarditEnquiryModel> results = new ArrayList<>();
		 ResponseVO responseVO = new ResponseVO();
		Collection<Mailbag> selectedMailbags = carditEnquiryModel.getSelectedMailbags();
    	Collection<MailbagVO> selectedMailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);
    	List<String> selectedResdits=carditEnquiryModel.getSelectedResdits();
    	String selectedResditVersion=carditEnquiryModel.getSelectedResditVersion();
    	CarditEnquirySession carditEnquirySession=getScreenSession(MODULE_NAME,
				SCREEN_ID); 
    	
    	carditEnquirySession.setSelectedResdits(selectedResdits);
    	carditEnquirySession.setSelectedResditVersion(selectedResditVersion);
    	carditEnquirySession.setSelectedMailbags(selectedMailbagVOs);
    	
          results.add(carditEnquiryModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
		 LOGGER.exiting("SendResditScreenloadCommand","execute");

	}

}
