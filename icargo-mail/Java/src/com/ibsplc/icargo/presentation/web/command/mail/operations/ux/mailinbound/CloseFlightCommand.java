package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CloseFlightCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS,CloseFlightCommand");
	private static final String INBOUND = "I";
	private  static final String SUCCESS_MESSAGE="mail.operations.err.closesuccess";
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		log.entering("CloseFlightCommand","execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel =  
				(MailinboundModel) actionContext.getScreenModel();  
		ArrayList<MailinboundDetails> mailinboundDetailsCollection=   
				mailinboundModel.getMailinboundDetailsCollection();    
		Collection<OperationalFlightVO> operationalFlightVOs=
				new ArrayList<OperationalFlightVO>();
		
		
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO(); 
		
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    
		
    	for(MailinboundDetails mailinboundDetailsInLoop: mailinboundDetailsCollection){
    		
    		String flightCarrierCode = null;
		    flightCarrierCode = 
		    		mailinboundDetailsInLoop.getCarrierCode().trim().toUpperCase(); 
		   		
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
			operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());  
			operationalFlightVO.setPou(mailinboundDetailsInLoop.getPort()); 
				
			operationalFlightVO.setFlightNumber(mailinboundDetailsInLoop.getFlightNo()); 
			operationalFlightVO.setCarrierCode(flightCarrierCode); 
			operationalFlightVO.setCarrierId(Integer.parseInt(mailinboundDetailsInLoop.getCarrierId()));  
			operationalFlightVO.setFlightDate((new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(mailinboundDetailsInLoop.getFlightDate().split(" ")[0])));   
			operationalFlightVO.setLegSerialNumber(Integer.parseInt(mailinboundDetailsInLoop.getLegSerialNumber()));
			operationalFlightVO.setFlightSequenceNumber(Long.parseLong(mailinboundDetailsInLoop.getFlightSeqNumber()));
			operationalFlightVO.setFlightRoute(mailinboundDetailsInLoop.getFlightRoute());
			operationalFlightVO.setDirection(INBOUND);
			operationalFlightVO.setPol(mailinboundDetailsInLoop.getLegOrigin());
    		
			operationalFlightVOs.add(operationalFlightVO);
    	}
    	
    	try{
    		delegate.closeInboundFlights(operationalFlightVOs);
    	}catch(BusinessDelegateException businessDelegateException){
    		errors = handleDelegateException(businessDelegateException);
    	}
		
		mailinboundModel.setMessageStatus("Y");   
		ArrayList<MailinboundModel> result=
				new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
	    responseVO.setStatus("success"); 
	    ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);    
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);  
	    actionContext.setResponseVO(responseVO);     
	}
	
	
}
