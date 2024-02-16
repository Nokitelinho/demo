package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Preadvice;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class PreadviceCommand  extends AbstractCommand {
	   private static final String OUTBOUND = "O";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
FlightFilterVO flightFilterVO = null;
flightFilterVO = new FlightFilterVO();
List<ErrorVO> errorVOs = null;
LogonAttributes logonAttributes = getLogonAttribute();
ResponseVO responseVO = new ResponseVO();
List<OutboundModel> results = new ArrayList();

OperationalFlightVO operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(),logonAttributes);
Preadvice preadvice = null;
PreAdviceVO preadvicevo = null;
try{
	preadvicevo =new MailTrackingDefaultsDelegate().findPreAdvice(operationalFlightVO);
}catch(BusinessDelegateException businessDelegateException){
	errorVOs = handleDelegateException(businessDelegateException);
	actionContext.addAllError(errorVOs);
}
preadvice = MailOutboundModelConverter.constructPreadvice(preadvicevo);
outboundModel.getMailAcceptance().setPreadvice(preadvice);
//outboundModel.setPreadvice(preadvice);
results.add(outboundModel);
responseVO.setResults(results);
responseVO.setStatus("success");
actionContext.setResponseVO(responseVO);
}
	
	
	

	
}
	