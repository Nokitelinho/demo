package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound.SendULDAnnounceCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Feb-2021		:	Draft
 */
public class SendULDAnnounceCommand extends AbstractCommand  {
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS SendULDAnnounceCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
	
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		OutboundModel outboundModel = 
				(OutboundModel)actionContext.getScreenModel();
		List<MailAcceptance> mailFlights=
				outboundModel.getMailAcceptanceList();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		MailManifestVO mailManifestVO = new MailManifestVO();
		List<ErrorVO> errorVOs = null;
		List<OutboundModel> results = new ArrayList();
		ResponseVO responseVO = new ResponseVO();
		
		if(Objects.isNull(mailFlights)) {
			mailFlights= new ArrayList<MailAcceptance>();
			if(!Objects.isNull(outboundModel.getMailAcceptance())) {
				mailFlights.add(outboundModel.getMailAcceptance());
			}
		}
		for(MailAcceptance mailFlight:mailFlights) {
			OperationalFlightVO operationalFlightVO = MailOutboundModelConverter
					.constructOperationalFlightVO(mailFlight,logonAttributes);
			try{
				mailManifestVO = mailTrackingDefaultsDelegate.
						findContainersInFlightForManifest(operationalFlightVO);
				if(!Objects.isNull(mailManifestVO) 
						&& !Objects.isNull(mailManifestVO.getContainerDetails())){
					mailManifestVO.getContainerDetails().forEach(containerDetailsVO -> {
						containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
						containerDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
					});
					mailTrackingDefaultsDelegate.
						sendULDAnnounceForFlight(mailManifestVO);
				}
			}
			catch (BusinessDelegateException businessDelegateException){
				errorVOs = handleDelegateException(businessDelegateException);
			}
			
		}
		if(Objects.isNull(errorVOs)){
			ErrorVO error = new ErrorVO("mail.operations.uldannouncesuccessfull");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
		    actionContext.addError(error);
		} else{
			actionContext.addAllError(errorVOs);
		}
		
		results.add(outboundModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
	}

}
