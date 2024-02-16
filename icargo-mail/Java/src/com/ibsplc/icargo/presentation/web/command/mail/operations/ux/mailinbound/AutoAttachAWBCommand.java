package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.AutoAttachAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	01-Nov-2018		:	Draft
 */
public class AutoAttachAWBCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String NO_MAILS_TO_ATTACH ="mailtracking.defaults.attachawb.msg.err.nomailstoattach";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS AutoAttachAWBCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("AutoAttachAWBCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel =  
				(MailinboundModel) actionContext.getScreenModel();
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		ResponseVO responseVO = new ResponseVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO=null;
		Collection<ContainerDetailsVO> containerDetailsVOs=null;

		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
				new MailTrackingDefaultsDelegate();
		
		OperationalFlightVO operationalFlightVO=null;
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			containerDetailsVOs=mailArrivalVO.getContainerDetails();
		}
		
		if (containerDetailsVOs == null || containerDetailsVOs.size() ==0) {
			actionContext.addError(new ErrorVO(NO_MAILS_TO_ATTACH));
			return;
		}
		
		
		Collection<ContainerDetailsVO> awbAttachedVOs = new ArrayList<ContainerDetailsVO>();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			try {
				awbAttachedVOs = mailTrackingDefaultsDelegate.autoAttachAWBDetails(
						containerDetailsVOs,operationalFlightVO);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
			
			log.log(Log.INFO, "awbAttachedVOs---->>", awbAttachedVOs);			
		}
		
		ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
    	responseVO.setResults(result);
        responseVO.setStatus("success");
        ErrorVO error = new ErrorVO("mail.operations.succ.autoattachsuccess");       
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
        actionContext.setResponseVO(responseVO);
    	log.exiting("AutoAttachAWBCommand","execute");
		
		
	}
		
		

}
