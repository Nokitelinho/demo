package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
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

public class ReopenFlightCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
	   private  static final String SUCCESS_MESSAGE="mail.operations.err.reopensuccess";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
FlightFilterVO flightFilterVO = null;
flightFilterVO = new FlightFilterVO();
List<ErrorVO> errorVOs = null;
LogonAttributes logonAttributes = getLogonAttribute();
ResponseVO responseVO = new ResponseVO();
List<OutboundModel> results = new ArrayList();
List<MailAcceptance> mailFlights = outboundModel.getMailAcceptanceList();
if(mailFlights == null) {
	mailFlights= new ArrayList<MailAcceptance>();
	if(outboundModel.getMailAcceptance()!= null) {
		mailFlights.add(outboundModel.getMailAcceptance());
	}
}

for (MailAcceptance mailFlight: mailFlights) {
OperationalFlightVO operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(mailFlight,logonAttributes);

try{
	 new MailTrackingDefaultsDelegate().reopenFlight(operationalFlightVO);
}catch(BusinessDelegateException businessDelegateException){
	errorVOs = handleDelegateException(businessDelegateException);
	actionContext.addAllError(errorVOs);
}
}
results.add(outboundModel);
responseVO.setResults(results);
responseVO.setStatus("success");
ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);      
error.setErrorDisplayType(ErrorDisplayType.INFO);
actionContext.addError(error);    
actionContext.setResponseVO(responseVO);
}
	
	
	

	
}
	
