package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListContainersCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
	   private static final String MAIL_TRANSER = "Mail Transfer";
	   private static final Log LOGGER = LogFactory.getLogger("List containers command ");
	   
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
  OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
  Collection<ErrorVO> errors = new ArrayList();
  ArrayList<ErrorVO> errorList = new ArrayList<ErrorVO>();
  LogonAttributes logonAttributes = getLogonAttribute();
 // DefaultScreenSessionImpl screenSession= getScreenSession();
  ResponseVO responseVO = new ResponseVO();
  List<OutboundModel> results = new ArrayList();
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
	operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(), logonAttributes);
	if (outboundModel.getMailAcceptance().getRdtDate() != null && outboundModel.getMailAcceptance().getRdtDate().trim().length() > 0) {
		LocalDate rqdDlvTim = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		StringBuilder reqDeliveryTime = new StringBuilder(outboundModel.getMailAcceptance().getRdtDate());
		if (outboundModel.getMailAcceptance().getReqDeliveryTime() != null && outboundModel.getMailAcceptance().getReqDeliveryTime().trim().length() > 0) {
			reqDeliveryTime.append(" ").append(outboundModel.getMailAcceptance().getReqDeliveryTime()).append(":00");
			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
		} else {
			rqdDlvTim.setDate(reqDeliveryTime.toString());
		}
		operationalFlightVO.setReqDeliveryTime(rqdDlvTim);
	}

	int displayPage=outboundModel.getContainerDisplayPage();
	 FlightValidationVO flightValidationVO =new FlightValidationVO();
	Page<ContainerDetailsVO> containerDetails = null;
	 MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
	 
	 
	 Collection<WarehouseVO> warehouseVOs =  findWarehouses(logonAttributes);
		String warehouseCode = "";
		if(warehouseVOs != null && warehouseVOs.size() > 0){
			for(WarehouseVO warehouseVO:warehouseVOs){
				warehouseCode = warehouseVO.getWarehouseCode();
				break;
			}
		}
		Collection<String> transactionCodes = new ArrayList<String>();
		transactionCodes.add("warehouse.defaults.defaultmaillocation");
		Map<String,Collection<String>> locationsMap = null;
		
		LocationEnquiryFilterVO filterVO=new LocationEnquiryFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setAirportCode(logonAttributes.getAirportCode());
		filterVO.setWarehouseCode(warehouseCode);
		filterVO.setTransactionCodes(transactionCodes);
		try{
			locationsMap = new MailTrackingDefaultsDelegate().findWarehouseTransactionLocations(filterVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		String warehouseLocation = "";
		for(String key:locationsMap.keySet()){
			Collection<String> locations = locationsMap.get(key);
			if(locations != null && locations.size() > 0){
				warehouseLocation = ((ArrayList<String>)locations).get(0);
			}
			break;
		}
		//mailAcceptanceSession.setWarehouse(warehouseVOs);
		//mailAcceptanceForm.setLocation(warehouseLocation);
	 
	/* if(outboundModel.getFlightCarrierflag().equals("F")) {
	 
	  flightFilterVO = handleFlightFilterVO(
			 operationalFlightVO,logonAttributes);
	    flightFilterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		flightFilterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
		flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				
				flightValidationVOs =new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);    				
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if ( flightValidationVOs.size() == 1) {
	 			//log.log(Log.FINE, "flightValidationVOs has one VO");
	 			try {
	 				for (FlightValidationVO flightValidVO : flightValidationVOs) {
	 					BeanHelper.copyProperties(flightValidationVO,
	 							flightValidVO);
	 					//carditEnquirySession.setFlightValidationVO(flightValidationVO);
	 					//break;
	 				}
	 			} catch (SystemException systemException) {
	 				systemException.getMessage();
	 			}
	 		}
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				try {
					//log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
					flightValidationVOs =
							new MailTrackingDefaultsDelegate().validateMailFlight(flightFilterVO);
					if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
						for (FlightValidationVO flightValidVO : flightValidationVOs) {
							flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
							flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						}
					}
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			//screenSession.setAttribute("flightValidationVO", flightValidationVO);
	 }
	 */
		//Added by A-8893 for IASCB-56003 starts
		if((outboundModel.getFlightCarrierflag()!=null&&outboundModel.getFlightCarrierflag().equals("F")) && (outboundModel.getActionType()!=null && outboundModel.getActionType().equals(MAIL_TRANSER))){
		 boolean flightStatus=false;
	    	try{
     		
	    	  flightStatus = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
	    	
	    	}catch (BusinessDelegateException businessDelegateException) {
	    		errorList = (ArrayList<ErrorVO>)handleDelegateException(businessDelegateException);
			}
			
			if(flightStatus){
	    		Object[] obj = {operationalFlightVO.getCarrierCode(),
	    				operationalFlightVO.getFlightNumber(),
	    				operationalFlightVO.getFlightDate().toString().substring(0,11)};
	    		errorList.add(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj));
	    	}
	    	if(errorList != null && !errorList.isEmpty()) {
	    		actionContext.addAllError(errorList);
				return;
	    	}
		}
		if(outboundModel.getFlightCarrierflag()!=null && outboundModel.getFlightCarrierflag().equals("F")) {
			FlightCarrierFilter  filter =outboundModel.getFlightCarrierFilter();
			if(filter!=null) {
			if(filter.getDestination()!=null && filter.getDestination().trim().length()>0) {
				operationalFlightVO.setDestination(filter.getDestination());
			} else {
				operationalFlightVO.setDestination(null);
			}
			if(filter.getPou()!=null && filter.getPou().trim().length()>0) {
				operationalFlightVO.setPou(filter.getPou());
			} else {
				operationalFlightVO.setPou(null);
			}
			} else {
				operationalFlightVO.setDestination(null);
				operationalFlightVO.setPou(null);
			}
		}
	 try {
		 containerDetails = new MailTrackingDefaultsDelegate().getAcceptedContainers(operationalFlightVO,displayPage);
    }catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
	  }
	//Added by A-8893 for IASCB-56003 ends
	 //added by A-7815 as part of IASCB-35985
	 if((containerDetails==null || containerDetails.isEmpty()) && displayPage >1 && (errors==null || errors.isEmpty())) {
		 displayPage =  displayPage-1;
		 outboundModel.setContainerDisplayPage(displayPage);
		 try {
			 containerDetails = new MailTrackingDefaultsDelegate().getAcceptedContainers(operationalFlightVO,displayPage);
	    }catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
		  }
	 }
	
	     List<ContainerDetails> containerList = MailOutboundModelConverter.constructContainerDetails(containerDetails,logonAttributes);
      	        PageResult<ContainerDetails> containersPage= new PageResult<ContainerDetails>(containerDetails,containerList);
      	      if(containerList.size()>0) {
	        outboundModel.getMailAcceptance().setContainerPageInfo(containersPage);
		 			}
		 			else {
		 				 outboundModel.getMailAcceptance().setContainerPageInfo(null);
		 			}
      	    boolean hasPrivilegeToViewEstimatedCharge = false;
    		try {
    			hasPrivilegeToViewEstimatedCharge = SecurityAgent.getInstance()
    					.checkBusinessPrivilege("mail.operations.privilegerequiredforestimatedcharge");
    		} catch (SystemException systemException) {
    			systemException.getMessage();
    			LOGGER.log(Log.INFO,systemException);
    		}
    		outboundModel.getMailAcceptance().setEstimatedChargePrivilage(hasPrivilegeToViewEstimatedCharge);
	       
	      //  outboundModel.setWareHouses(warehouseVOs);
			results.add(outboundModel);
	        responseVO.setResults(results);
	        responseVO.setStatus("success");
	        actionContext.setResponseVO(responseVO);
	

	}
	
	public Collection<WarehouseVO> findWarehouses(LogonAttributes logonAttributes) {
		Collection<WarehouseVO> warehouseVOs = null;
		Collection<ErrorVO> errors = null;
		try{
			warehouseVOs = new MailTrackingDefaultsDelegate().findAllWarehouses(
					logonAttributes.getCompanyCode(),logonAttributes.getAirportCode());
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return warehouseVOs;
	}
	/* private FlightFilterVO handleFlightFilterVO(
			 OperationalFlightVO operationalFlightVO,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			flightFilterVO.setStation(operationalFlightVO.getPol());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setActiveAlone(false);
			flightFilterVO.setStringFlightDate(operationalFlightVO.getFlightDate().toString());
			LocalDate date = new LocalDate(operationalFlightVO.getPol(),Location.ARP,false);
	 		flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
	 		//flightFilterVO.setFlightDate(date);
	 		return flightFilterVO;
		}*/
}
