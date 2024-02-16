package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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

public class CloseFlightCommand extends AbstractCommand {
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
Collection<MailAcceptance> mailAcceptance = new ArrayList<MailAcceptance>();
List<MailAcceptance> mailFlights=outboundModel.getMailAcceptanceList();
MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
Collection<ContainerDetailsVO> emptyContainers = new ArrayList();
if(mailFlights == null) {
	mailFlights= new ArrayList<MailAcceptance>();
	if(outboundModel.getMailAcceptance()!= null) {
		mailFlights.add(outboundModel.getMailAcceptance());
	}
}

for(MailAcceptance mailFlight:mailFlights) {
OperationalFlightVO operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(mailFlight,logonAttributes);

MailAcceptanceVO mailacceptancevo= new MailAcceptanceVO();
mailacceptancevo = MailOutboundModelConverter.constructMailAcceptanceVO(mailFlight, logonAttributes);
	LocalDate flightDate = new LocalDate(mailacceptancevo.getPol(), Location.ARP, false);
	flightDate.setDate(mailFlight.getStd());
	operationalFlightVO.setFlightDate(flightDate);
      MailManifestVO mailManifestVO = new MailManifestVO();
      
      boolean isFlightClosed = false;
      try
      {
        isFlightClosed = delegate.isFlightClosedForMailOperations(operationalFlightVO).booleanValue();
      }
      catch (BusinessDelegateException businessDelegateException)
      {
	errorVOs = handleDelegateException(businessDelegateException);
      }
      if ((errorVOs != null) && (errorVOs.size() > 0)) {
        return;
      }
      if (isFlightClosed)
      {
        Object[] obj = {mailacceptancevo.getFlightCarrierCode() + 
          "" + mailacceptancevo.getFlightNumber(), 
          mailacceptancevo.getStrFlightDate()!=null ? mailacceptancevo.getStrFlightDate() :
        	  (mailacceptancevo.getFlightDate()!=null ? mailacceptancevo.getFlightDate().toDisplayDateOnlyFormat():"")};
        actionContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed", obj));
        return;
      }
      try
      {
        mailManifestVO = delegate.findContainersInFlightForManifest(operationalFlightVO);
      }
      catch (BusinessDelegateException businessDelegateException)
      {
	errorVOs = handleDelegateException(businessDelegateException);
      }
      Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
      if ((containerDetailsVOs != null) && (containerDetailsVOs.size() > 0)) {
        for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
          if (("U".equals(containerDetailsVO.getContainerType())) && 
            (containerDetailsVO.getTotalBags() == 0) && 
            ("N".equals(containerDetailsVO.getAcceptedFlag()))) {
            emptyContainers.add(containerDetailsVO);
          }
        }
      }
      if ((emptyContainers != null) && (emptyContainers.size() > 0)) {
        return;
      }
      try
      {
        delegate.closeFlightAcceptance(operationalFlightVO, mailacceptancevo);
      }
      catch (BusinessDelegateException businessDelegateException)
      {
	errorVOs = handleDelegateException(businessDelegateException);
	actionContext.addAllError(errorVOs);
        return;
      }
      Object[] obj = {operationalFlightVO.getCarrierCode(),
    		  operationalFlightVO.getFlightNumber(),
    		  operationalFlightVO.getFlightDate().toString().substring(0,11)};
      ErrorVO error = new ErrorVO("mailtracking.defaults.flightClosedSuccesfully",obj);
	  error.setErrorDisplayType(ErrorDisplayType.INFO);
      actionContext.addError(error);
}
results.add(outboundModel);
responseVO.setResults(results);
actionContext.setResponseVO(responseVO);
}
	
	
	

	
}
	
