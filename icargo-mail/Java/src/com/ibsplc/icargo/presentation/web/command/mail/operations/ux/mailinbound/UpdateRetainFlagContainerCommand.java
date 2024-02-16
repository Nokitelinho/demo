package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class UpdateRetainFlagContainerCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("OPERATIONS UpdateRetainFlagContainerCommand");
	private  static final String SUCCESS_MESSAGE="mail.operations.err.retaincontainersuccess";

	public void execute(ActionContext actionContext) throws BusinessDelegateException {

		this.log.entering("UpdateRetainFlagContainerCommand", "execute");
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection = mailinboundModel.getContainerDetailsCollection();
		
		for(ContainerDetails containerDetail : containerDetailsCollection){
			if(containerDetail.getTransitFlag().equals("N")){
				actionContext.addError(new ErrorVO("mail.operations.err.uldalreadyaquited"));
				return;
			}
		}

		for(ContainerDetails containerDetail : containerDetailsCollection){
			MailInboundModelConverter converter = new MailInboundModelConverter();
			ContainerVO containerVO =  converter.convertContainerDetailsToContainerVO(containerDetail, logonAttributes);
			containerVO.setRetainFlag("Y");
			try {
				new MailTrackingDefaultsDelegate().updateRetainFlagForContainer(containerVO);
			} catch (BusinessDelegateException e) {
	
				}
		}

		ArrayList<MailinboundModel> mailinboundModels=
				new ArrayList<MailinboundModel>();
		mailinboundModels.add(mailinboundModel);
		ResponseVO responseVO = new ResponseVO();	  
		responseVO.setStatus("success");
		responseVO.setResults(mailinboundModels);
		ErrorVO error = new ErrorVO(SUCCESS_MESSAGE); 
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		actionContext.addError(error); 
		actionContext.setResponseVO(responseVO);  
		log.exiting("UpdateRetainFlagContainerCommand","execute");
	}

}
