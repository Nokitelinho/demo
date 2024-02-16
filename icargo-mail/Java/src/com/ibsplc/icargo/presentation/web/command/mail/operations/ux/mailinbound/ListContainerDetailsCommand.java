package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	26-Sep-2018		:	Draft
 */
public class ListContainerDetailsCommand extends AbstractCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String INBOUND = "I";
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListContainerDetailsCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ListContainerDetailsCommand", "execute");
		
	
		LogonAttributes logonAttributes =   
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel(); 
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();  
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<FlightValidationVO> flightValidationVOs = null;
		ResponseVO responseVO = new ResponseVO();
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<ContainerDetailsVO> containerDetailsVOs= 
				new Page<ContainerDetailsVO>();
		
		FlightValidationVO filghtValidationVO= null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(mailinboundDetails.getPort()!=null && mailinboundDetails.getPort().trim().length()>0) {
			flightFilterVO.setStation(mailinboundDetails.getPort());
		} else {
		flightFilterVO.setStation(mailinboundDetails.getPort()!=null?mailinboundDetails.getPort():logonAttributes.getAirportCode());
		}
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		flightFilterVO.setActiveAlone(false);
		String flightCarrierCode = mailinboundDetails.getCarrierCode();
		/**
		 * Validating flight carrier code
		 */
		if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
			flightFilterVO.setCarrierCode(mailinboundDetails.getCarrierCode().toUpperCase());
			flightFilterVO.setFlightCarrierId(Integer.parseInt(mailinboundDetails.getCarrierId()));
			flightFilterVO.setFlightNumber(mailinboundDetails.getFlightNo());
			flightFilterVO.setFlightSequenceNumber(Long.parseLong(mailinboundDetails.getFlightSeqNumber()));
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			filghtValidationVO=flightValidationVOs.iterator().next();
		}
		MailArrivalFilterVO mailArrivalFilterVO=
				new MailArrivalFilterVO();
		mailArrivalFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailArrivalFilterVO.setCarrierId(Integer.parseInt(mailinboundDetails.getCarrierId()));
		mailArrivalFilterVO.setFlightNumber(mailinboundDetails.getFlightNo());
		mailArrivalFilterVO.setFlightSequenceNumber(Integer.parseInt(mailinboundDetails.getFlightSeqNumber()));
		mailArrivalFilterVO.setPou(mailinboundDetails.getPort()); 
		if(mailinboundDetails.getPageNumber()==null|| 
				("".equals(mailinboundDetails.getPageNumber().trim()))){
			mailArrivalFilterVO.setPageNumber(1);
		}
		else{
			mailArrivalFilterVO.setPageNumber(Integer.parseInt(mailinboundDetails.getPageNumber()));
		}
		if(mailinboundDetails.getPageSize()==null|| 
				("".equals(mailinboundDetails.getPageSize().trim()))){ 
			mailArrivalFilterVO.setDefaultPageSize(10);    
		}
		else{
			mailArrivalFilterVO.setDefaultPageSize(Integer.parseInt(mailinboundDetails.getPageSize()));
		}
		
		mailArrivalFilterVO.setContainerNumber(mailinboundDetails.getContainerNumber());
		mailArrivalFilterVO.setPol(mailinboundDetails.getPol());
		mailArrivalFilterVO.setDestination(mailinboundDetails.getDestination());
		try {
			containerDetailsVOs = 
					new MailTrackingDefaultsDelegate().findArrivedContainersForInbound(mailArrivalFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException); 
		}
		
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors); 
    	    return;
    	}
		
		
		
		
		
		LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		String dateToSave=date.toDisplayDateOnlyFormat();
		String time=date.toDisplayTimeOnlyFormat(true);
		mailinboundModel.setCurrentDate(dateToSave);
		mailinboundModel.setCurrentTime(time);
		
		if(null==containerDetailsVOs){
			ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>(); 
			mailinboundModel.setMailinboundDetails(mailinboundDetails);    
			result.add(mailinboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
			log.exiting("ListContainerDetailsCommand", "execute");
			return;  
		}
		PageResult<ContainerDetails> containerDetailsPageList = 
				MailInboundModelConverter.convertContainerDetailsToModel(containerDetailsVOs,mailinboundDetails.getCarrierCode());
		mailinboundModel.setContainerDetailsCollectionPage(containerDetailsPageList);
		List<com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails> containerList = MailOutboundModelConverter.constructContainerDetailsForInbound(containerDetailsVOs,logonAttributes,filghtValidationVO);
		mailinboundModel.setContainerList(containerList);
		ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>(); 
		mailinboundModel.setMailinboundDetails(mailinboundDetails);    
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");

		if(("Y").equals(mailinboundDetails.getDeliveryFlag())){
			ErrorVO error = new ErrorVO("mail.operations.succ.deliversuccess");       
			error.setErrorDisplayType(ErrorDisplayType.INFO);
	        actionContext.addError(error);	
		}
		actionContext.setResponseVO(responseVO);
		log.exiting("ListContainerDetailsCommand", "execute");
		
	}
	


	
	
	
}
