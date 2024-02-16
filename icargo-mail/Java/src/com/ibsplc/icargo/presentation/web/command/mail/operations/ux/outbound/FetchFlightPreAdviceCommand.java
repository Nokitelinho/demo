package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 
public class FetchFlightPreAdviceCommand extends AbstractCommand {

	private static final String CLASS_NAME = "FetchFlightPreAdviceCommand";
	private static final Log LOG = LogFactory.getLogger(CLASS_NAME);
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");
		
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		List<MailAcceptance> mailAcceptanceList = outboundModel.getMailAcceptanceList();
		FlightCarrierFilter flightcarrierfilter = null;
		flightcarrierfilter = outboundModel.getFlightCarrierFilter();
		Collection<FlightFilterVO> flightFilterVOs = new ArrayList<>();
	
		for (MailAcceptance acceptanceList : mailAcceptanceList) {

			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(acceptanceList.getCompanyCode());
			flightFilterVO.setFlightCarrierId(acceptanceList.getCarrierId());
			flightFilterVO.setFlightNumber(acceptanceList.getFlightNumber());
			flightFilterVO.setFlightSequenceNumber(acceptanceList.getFlightSequenceNumber());
			LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(), Location.ARP, false);
			date.setDate(acceptanceList.getFlightDate());
			flightFilterVO.setFlightDate(date);
			flightFilterVOs.add(flightFilterVO);
		}

		Collection<MailAcceptanceVO> preAdviceFlightVOs = null;
		Collection<MailAcceptanceVO> preAdviceFlightVOList = new ArrayList<>();

		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		try {
			preAdviceFlightVOs = delegate.fetchFlightPreAdviceDetails(flightFilterVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}

		StringBuilder preAdviceKey = null;

		HashMap<String, MailAcceptanceVO> flightPreAdviceHashMap = new HashMap<>();
	
		
		  for (MailAcceptanceVO preAdviceFlightVO :preAdviceFlightVOs) {
		  
		  preAdviceKey = new StringBuilder(preAdviceFlightVO.getFlightNumber()).append(preAdviceFlightVO.
		  getCarrierId()) .append(preAdviceFlightVO.getFlightSequenceNumber());
		  preAdviceFlightVOList.add(preAdviceFlightVO);
		  flightPreAdviceHashMap.put(preAdviceKey.toString(),preAdviceFlightVO);
		  
		  }
		 
		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList<>();
		outboundModel.setFlightPreAdviceDetails(flightPreAdviceHashMap);
		results.add(outboundModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		LOG.exiting("CLASS_NAME","execute");

	}
}
