package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OffloadModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload.OffloadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	20-March-2019	:	Draft
 */
public class SaveOffloadCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("Mail operations ux MTK066 offload ");
	private static final String MODULE_NAME = "mail.operations ux MTK066";
	private static final String SCREENID = "mail.operations.ux.offload";
	
	private static final String OUTBOUND = "O";
	private static final String CONST_OVERRIDE_FLAG = "OVERRIDE";
	private static final String BULK = "B";
	private static final String DEPARTED_FLIGHT_WARN="mailtracking.defaults.warn.flightdeparted";
	private static final String FLIGHT_DEPARTED="flight_departed";
	private static final String OFFLOAD_SUCCESS = "mail.operations.offload.info.offloadmailsuccess";
	private static final String OFFLOAD_SUCCESS_FOR_CONTAINER = "mail.operations.offload.info.offloadsuccessforcontainer";
	private static final String FLAG_OFFLOADED = "offloaded";
	private static final String FLAG_NORMAL = "NORMAL";
	private static final String BULK_OFFLOAD = "mail.operations.ux.err.bulkoffloadnotpossible";
	private static final String OFFLOAD_REASON = "mail.operations.ux.err.nooffloadreason";
	private static final String SELECT_ROW = "mail.operations.ux.err.selectrow";
	
	
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
	
		log.entering("OffloadCommand MTK066", "execute");
		OffloadModel offloadModel = (OffloadModel) actionContext.getScreenModel();
		OffloadFilter offloadFilterFromUI = (OffloadFilter)offloadModel.getOffloadFilter();
		Collection<OffloadDetails> selectedOffloadDetails = offloadModel.getSelectedOffloadDetails();
		
		ResponseVO responseVO = new ResponseVO();   
		LogonAttributes logonAttributes = getLogonAttribute();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
		if(selectedOffloadDetails == null || selectedOffloadDetails.size() == 0) {
			ErrorVO err = new ErrorVO(SELECT_ROW);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(err);
			return;
		}
		
		/*if(BULK.equals(offloadFilterFromUI.getContainerType()) && "U".equals(offloadFilterFromUI.getType())){
			ErrorVO err = new ErrorVO(BULK_OFFLOAD);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(err);
			return;
		}
		if(!("M".equals(offloadFilterFromUI.getType()))){
		for(OffloadDetails offloadDetails :selectedOffloadDetails){
			if(BULK.equals(offloadDetails.getContainerType())){
				ErrorVO err = new ErrorVO(BULK_OFFLOAD);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(err);
				return;
			}	 
			}	 
		}*/
		for(OffloadDetails offloadDetails :selectedOffloadDetails){
			if((offloadDetails.getOffloadReason()==null ||offloadDetails.getOffloadReason().trim().length() == 0)){
				ErrorVO err = new ErrorVO(OFFLOAD_REASON);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(err);
				return;
			}	 
		}
		String flightCarrierCode = offloadFilterFromUI.getFlightCarrierCode();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					flightCarrierCode.trim().toUpperCase(Locale.ENGLISH));

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && !errors.isEmpty()) {             			
    			errors = new ArrayList<>();
    			Object[] obj = {flightCarrierCode.toUpperCase(Locale.ENGLISH)};
				ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.invalidCarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
    	}
		
		
		
		FlightFilterVO flightFilterVO = handleFlightFilterVO(offloadFilterFromUI,logonAttributes);
		if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {  
		flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase(Locale.ENGLISH));
		}
		if(airlineValidationVO != null){
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		}
		Collection<FlightValidationVO> flightValidationVOs = null; 
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ",flightFilterVO);
			flightValidationVOs =
				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		String offloadtype = offloadFilterFromUI.getType();
		OffloadVO offloadVO = updateOffloadVO(offloadtype,selectedOffloadDetails,offloadFilterFromUI,flightValidationVOs);
		OffloadVO newOffloadVO =offloadVO;
		
		//if (CONST_OVERRIDE_FLAG.equals(offloadFilterFromUI.getFlightStatus())) {
			// set a boolean as departed check overrided
			newOffloadVO.setDepartureOverride(true);
		//}
		
		
		
		//Delegate call
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		try {
			log.log(Log.FINE, "NewOffloadVO for offloading-----==-----> ",newOffloadVO);
			containerDetailsVOs = mailTrackingDefaultsDelegate.offload(newOffloadVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			boolean flightDeparted = false;
			for(ErrorVO errorVO : errors) {
				if(DEPARTED_FLIGHT_WARN.equalsIgnoreCase(errorVO.getErrorCode())) {
					offloadFilterFromUI.setFlightStatus(FLIGHT_DEPARTED);
					flightDeparted = true;
					break;
				}
			}
			if(!flightDeparted) {
				offloadFilterFromUI.setFlightStatus("");
			}
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		else{
			 responseVO.setStatus("offload_success");
			 ErrorVO error;
			 if("U".equals(offloadFilterFromUI.getType())){
			  error=new ErrorVO(OFFLOAD_SUCCESS_FOR_CONTAINER);
			 }
			 else{
			  error = new ErrorVO(OFFLOAD_SUCCESS);	 
			 }
				 
			 error.setErrorDisplayType(ErrorDisplayType.INFO);
			 actionContext.addError(error);
		}
		  
		
		log.log(Log.FINE, "containerDetailsVOs ------------>>",containerDetailsVOs);
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
		
			offloadFilterFromUI.setFlightStatus("emptyuld");
			offloadFilterFromUI.setStatus("OFFLOAD");				
	  		
		}

		offloadFilterFromUI.setFlightStatus(FLAG_OFFLOADED);
		offloadFilterFromUI.setStatus(FLAG_NORMAL);
		offloadFilterFromUI.setMode(FLAG_NORMAL);
		
		offloadModel.setOffloadFilter(offloadFilterFromUI);
	    ArrayList<OffloadModel> result= new ArrayList<OffloadModel>();
		result.add(offloadModel);
	    responseVO.setResults(result);
	    actionContext.setResponseVO(responseVO);
		log.exiting("OffloadCommand MTK066","execute");
		
	}
   
	/**
	 * @author A-7929
	 * @param offloadtype
	 * @param offloadContainerDetails
	 * @param offloadFilterFromUI 
	 * @param flightValidationVOs 
	 * @return
	 */
	private OffloadVO updateOffloadVO(String offloadtype, Collection<OffloadDetails> offloadContainerDetails, OffloadFilter offloadFilterFromUI, Collection<FlightValidationVO> flightValidationVOs) {
		log.entering("SaveOffloadCommand","updateOffloadVO");
		
		OffloadVO offloadVO = constructOffloadVO(offloadFilterFromUI,flightValidationVOs,offloadContainerDetails);
		return offloadVO;
	}
    /**
     * @author A-7929
     * @param offloadFilterFromUI
     * @param flightValidationVOs 
     * @param offloadContainerDetails 
     * @return
     */
	private OffloadVO constructOffloadVO(OffloadFilter offloadFilterFromUI, Collection<FlightValidationVO> flightValidationVOs, Collection<OffloadDetails> offloadContainerDetails) {
		
		LogonAttributes logonAttributes = getLogonAttribute();
		Collection<ContainerVO> containerVOs =  new ArrayList<ContainerVO>();
		//Collection<MailbagVO>  mailbagVOs = new ArrayList<MailbagVO>();
		Page<MailbagVO> mailbagVOs = new Page<>(new ArrayList<MailbagVO>(),1,1,1,0,0,false);
		//FlightValidationVO flightValidationVO = (FlightValidationVO) flightValidationVOs.iterator();
		int carrierId=0; 
		long flightSeqNumber=0;
		int legSerialNumber= 0;
		String carrierCode=null;
		for(FlightValidationVO flightValidationVO:flightValidationVOs){
			 carrierId = flightValidationVO.getFlightCarrierId();
			 flightSeqNumber = flightValidationVO.getFlightSequenceNumber();
			 
			 if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) && offloadContainerDetails!=null && !offloadContainerDetails.isEmpty()){
				legSerialNumber=offloadContainerDetails.iterator().next().getLegSerialNumber();	
			 }else{
			 legSerialNumber = flightValidationVO.getLegSerialNumber();
			 }
			carrierCode = flightValidationVO.getCarrierCode();
		}
		
		OffloadVO offloadVO = new OffloadVO();
		offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
		offloadVO.setCarrierCode(carrierCode);
		offloadVO.setCarrierId(carrierId);
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);		 		
		offloadVO.setFlightDate(date.setDate(offloadFilterFromUI.getFlightDate()));
		offloadVO.setFlightNumber(offloadFilterFromUI.getFlightNumber());
		offloadVO.setFlightSequenceNumber(flightSeqNumber);
		offloadVO.setLegSerialNumber(legSerialNumber);
		offloadVO.setPol(offloadFilterFromUI.getUpliftAirport());
		offloadVO.setOffloadType(offloadFilterFromUI.getType());
		offloadVO.setUserCode(logonAttributes.getUserId().toUpperCase());
		if("U".equals(offloadFilterFromUI.getType())){
		
			for(OffloadDetails offloadDetails:offloadContainerDetails){
			ContainerVO  containerVO = new ContainerVO();
			containerVO.setAcceptanceFlag("Y");
			containerVO.setAssignedPort(offloadDetails.getPol());
			containerVO.setBags(Integer.parseInt((offloadDetails.getAcceptedBags())));
			containerVO.setCarrierCode(offloadDetails.getCarrierCode());
			containerVO.setCarrierId(offloadDetails.getCarrierId());
			containerVO.setCompanyCode(logonAttributes.getCompanyCode());
			containerVO.setContainerNumber(offloadDetails.getContainerNo());
			containerVO.setFinalDestination(offloadDetails.getDestination());
			containerVO.setFlightNumber(offloadDetails.getFlightNumber());
			LocalDate flightdate = new LocalDate(logonAttributes.getAirportCode(),ARP,false);		 		
			containerVO.setFlightDate(flightdate.setDate(offloadVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
			containerVO.setFlightSequenceNumber(offloadDetails.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(offloadDetails.getLegSerialNumber());
			containerVO.setPou(offloadDetails.getPou());
			containerVO.setSegmentSerialNumber(offloadDetails.getSegmentSerialNumber());
			containerVO.setType(offloadDetails.getType());
			containerVO.setOffloadedReason(offloadDetails.getOffloadReason());
			containerVO.setOffloadedRemarks(offloadDetails.getRemarks());
			Measure weight=new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(offloadDetails.getAcceptedWeight()));
			containerVO.setWeight(weight);
			containerVO.setOffload(true);
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setMailSource("MTK066");
			containerVO.setAssignedPort(offloadDetails.getAssignedPort());
			containerVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
			containerVO.setAcceptanceFlag(offloadDetails.getAcceptanceFlag());
			containerVO.setPaBuiltFlag(offloadDetails.getPaBuildFlag());
			containerVO.setUldFulIndFlag(offloadDetails.getUldFulIndFlag());
			containerVO.setActWgtSta(offloadDetails.getActWgtSta());
			
			containerVOs.add(containerVO);
			}
			
		offloadVO.setOffloadContainers(containerVOs);
		}
		else{
			for(OffloadDetails offloadDetails:offloadContainerDetails){
				MailbagVO mailbagVO = new MailbagVO();
				
				mailbagVO.setScannedUser(logonAttributes.getUserId());
				mailbagVO.setScannedPort(offloadDetails.getScannedPort());
				mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), ARP, true));
				mailbagVO.setIsoffload(true);
				mailbagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				mailbagVO.setMailSource("MTK066");
				mailbagVO.setPol(offloadDetails.getPol());
				//mailbagVO.setAcceptedBags(Integer.parseInt((offloadDetails.getAcceptedBags())));
				mailbagVO.setCarrierCode(offloadDetails.getCarrierCode());
				mailbagVO.setCarrierId(offloadDetails.getCarrierId());
				mailbagVO.setCompanyCode(logonAttributes.getCompanyCode()); 
				mailbagVO.setContainerNumber(offloadDetails.getContainerNo());
				mailbagVO.setDestination(offloadDetails.getDestination());
				mailbagVO.setFlightNumber(offloadDetails.getFlightNumber());
				LocalDate flightdate = new LocalDate(logonAttributes.getAirportCode(),ARP,false);		 		
				mailbagVO.setFlightDate(flightdate.setDate(offloadVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
				mailbagVO.setFlightSequenceNumber(offloadDetails.getFlightSequenceNumber());
				mailbagVO.setLegSerialNumber(offloadDetails.getLegSerialNumber());
				mailbagVO.setPou(offloadDetails.getPou());
				mailbagVO.setSegmentSerialNumber(offloadDetails.getSegmentSerialNumber());
				mailbagVO.setOffloadedRemarks(offloadDetails.getRemarks());
				Measure weight=new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(offloadDetails.getAcceptedWeight()));
				mailbagVO.setWeight(weight);
				mailbagVO.setOoe(offloadDetails.getOoe());
				mailbagVO.setDoe(offloadDetails.getDoe());
				mailbagVO.setAcceptanceFlag(offloadDetails.getAcceptanceFlag());
				mailbagVO.setPaBuiltFlag(offloadDetails.getPaBuildFlag());
				mailbagVO.setFinalDestination(offloadDetails.getFinalDestination());
				mailbagVO.setLatestStatus("OFL");
				mailbagVO.setMailCategoryCode(offloadDetails.getMailCategoryCode());
				mailbagVO.setMailSubclass(offloadDetails.getSubClass());
				mailbagVO.setOperationalStatus(offloadDetails.getOperationalStatus());
				mailbagVO.setYear(Integer.parseInt((offloadDetails.getYear())));
				mailbagVO.setContainerType(offloadDetails.getContainerType());
				mailbagVO.setDespatchSerialNumber(offloadDetails.getDsn());
				mailbagVO.setMailbagId(offloadDetails.getMailbagId());  
				mailbagVO.setMailClass(offloadDetails.getMailClass());
				mailbagVO.setOffloadedReason(offloadDetails.getOffloadReason());
				mailbagVO.setOffloadedDescription(offloadDetails.getOffloadReason());
				mailbagVO.setPaCode(offloadDetails.getPacode());
				mailbagVOs.add(mailbagVO);
			}
			offloadVO.setOffloadMailbags(mailbagVOs);
				
			}
			
		
		return offloadVO;
	}
	/**
	 * @author A-7929
     * Method to create the filter vo for flight validation
     * @param offloadFilterFromUI
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		OffloadFilter offloadFilterFromUI,
			LogonAttributes logonAttributes){

    	log.entering("SaveOffloadCommand MTK066","handleFlightFilterVO");

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(offloadFilterFromUI.getFlightNumber()); 
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		if(!("").equals(offloadFilterFromUI.getFlightDate())&&!("").equals(offloadFilterFromUI.getFlightCarrierCode())&&!("").equals(offloadFilterFromUI.getFlightNumber())){
		flightFilterVO.setStringFlightDate(offloadFilterFromUI.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(offloadFilterFromUI.getFlightDate()));
		}
 		
 		log.exiting("SaveOffloadCommand MTK066","handleFlightFilterVO");

		return flightFilterVO;
	}

}
