package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachRoutingDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveAttachRoutingCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String OUTBOUND = "O";
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS SaveAttachRoutingCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
		OutboundModel outboundModel = 
				(OutboundModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		ArrayList<ContainerDetails> containerDetailsCollection= 
				outboundModel.getContainerDetailsCollection();
		ArrayList<DespatchDetails> dsnList = outboundModel.getDespatchDetailsList();
		AttachRoutingDetails attachRoutingDetails=
				(AttachRoutingDetails) outboundModel.getAttachRoutingDetails();
		ConsignmentDocumentVO consignmentDocumentVO = null;
	    List<ErrorVO> errors = null;
	    Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
		if(containerDetailsCollection != null) {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		for(ContainerDetails containerDetails: containerDetailsCollection) {
	         ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
	         containers.add(containerDetailsVO);
		}
		 for(ContainerDetailsVO containerDetsVO :containers) {
			 if(containerDetsVO.getTotalBags()>0) {
		try{
			containerDetailsVOs=new MailTrackingDefaultsDelegate().findMailbagsInContainer(containers);
		}catch(BusinessDelegateException businessDelegateException){
		}
			 }
		 }
	if(containerDetailsVOs!=null) {
		ArrayList<MailbagVO> mailbagVOs =null;
		ArrayList<DSNVO> dsnVos=null;
		 for(ContainerDetailsVO containerDetsVO :containerDetailsVOs) {
			 mailbagVOs=  (ArrayList)containerDetsVO.getMailDetails();
			 dsnVos=(ArrayList)new MailTrackingDefaultsDelegate().getDSNsForContainer(containerDetsVO);
			 containerDetsVO.setMailDetails(mailbagVOs);
			 containerDetsVO.setDsnVOs(dsnVos);
			 new MailTrackingDefaultsDelegate().getRoutingInfoforDSN(dsnVos,containerDetsVO);
		 }
	}
		
		 ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(
					attachRoutingDetails.getConsignemntNumber().toUpperCase());
			consignmentFilterVO.setPaCode(attachRoutingDetails.getPaCode().toUpperCase());
			consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate().
				        findConsignmentDocumentDetails(consignmentFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			if(consignmentDocumentVO == null) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setOperationFlag("I");
			}else {
				consignmentDocumentVO.setOperationFlag("U");
				
			}
			mailVOs=createMailInConsignmentVOs(containerDetailsVOs,consignmentFilterVO);
			Page<MailInConsignmentVO> mailbagVOs = 
					new Page<MailInConsignmentVO>((ArrayList<MailInConsignmentVO>)
							mailVOs,1,1,1,0,0,false);
		    consignmentDocumentVO.setMailInConsignmentVOs(mailbagVOs);
			consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentDocumentVO.setAirportCode(logonAttributes.getAirportCode());
			consignmentDocumentVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			consignmentDocumentVO.setConsignmentNumber(
					attachRoutingDetails.getConsignemntNumber().toUpperCase());
			consignmentDocumentVO.setPaCode(
					attachRoutingDetails.getPaCode().toUpperCase());
			String conDocNo = attachRoutingDetails.getConsignemntNumber();
			String paCode = attachRoutingDetails.getPaCode();
			String direction = "O";
			String conDate = attachRoutingDetails.getConsignmentDate();
			String type = attachRoutingDetails.getConsignmentType();
			String remarks = attachRoutingDetails.getRemarks();
			if (conDocNo != null && conDocNo.trim().length() > 0) {
				consignmentDocumentVO.setConsignmentNumber(conDocNo.toUpperCase());
			}
			if (paCode != null && paCode.trim().length() > 0) {
				consignmentDocumentVO.setPaCode(paCode.toUpperCase());
			}
			if (direction != null && direction.trim().length() > 0) {
				consignmentDocumentVO.setOperation(direction);
			}
			LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			if (conDate != null && conDate.trim().length() > 0) {
				consignmentDocumentVO.setConsignmentDate(cd.setDate(conDate));
			}
			if (type != null && type.trim().length() > 0) {
				consignmentDocumentVO.setType(type);
			}
			if (remarks != null && remarks.trim().length() > 0) {
				consignmentDocumentVO.setRemarks(remarks);
			}
			if(null!=attachRoutingDetails.getOnwardRouting()&&attachRoutingDetails.getOnwardRouting().size()>0 ){ 
				Collection<RoutingInConsignmentVO> newRoutingVOs =null;			
				newRoutingVOs=populateRoutingVos(attachRoutingDetails.getOnwardRouting(),consignmentDocumentVO);
				consignmentDocumentVO.setRoutingInConsignmentVOs(newRoutingVOs);
			}
			errors = validateForm(attachRoutingDetails);
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}else{
				//validateConsignment(attachRoutingDetails,actionContext,
						//consignmentDocumentVO,logonAttributes);
				//validateRoutingDetails(attachRoutingDetails,actionContext,
						//consignmentDocumentVO,logonAttributes);
				//if(actionContext.getErrors() != null && actionContext.getErrors().size() > 0){				
					//return;
				//}
		try {
			new MailTrackingDefaultsDelegate().saveConsignmentForManifestedDSN(consignmentDocumentVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}else{
			
			ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
			outboundModel.setAttachRoutingDetails(attachRoutingDetails);
			result.add(outboundModel);
			ResponseVO responseVO = new ResponseVO();
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
	    	log.exiting("SaveAttachRoutingCommand","execute");
			
		}
			}
		
	}
	}
	public Collection<MailInConsignmentVO> createMailInConsignmentVOs(
			Collection<ContainerDetailsVO> containerDetailsVOs,ConsignmentFilterVO consignmentFilterVO){
		log.entering("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
				if(dsnVOs != null && dsnVOs.size() > 0){
					dsnVOs.addAll(containerDetailsVO.getDsnVOs());
				}else{
					dsnVOs = containerDetailsVO.getDsnVOs();
				}
			}
		}
		Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		Collection<String> dsnKey = new ArrayList<String>();
		if (dsnVOs != null && dsnVOs.size() != 0) {
			for (DSNVO dsnVO : dsnVOs) {
				String dsnpk = dsnVO.getOriginExchangeOffice()
				+dsnVO.getDestinationExchangeOffice()
				+dsnVO.getMailCategoryCode()
				+dsnVO.getMailSubclass()
				+dsnVO.getDsn()
				+dsnVO.getYear();
				if(!dsnKey.contains(dsnpk)){
					dsnKey.add(dsnpk);
				}		
				if(!newDsnVOs.contains(dsnVO)){
					newDsnVOs.add(dsnVO);
				}
			}
		}
		try {
			containerDetailsVOs = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOs);
		}catch (BusinessDelegateException businessDelegateException) {
			log
					.log(
							Log.SEVERE,
							"BusinessDelegateException---findMailbagsInContainerForManifest",
							businessDelegateException.getMessage());
		}
		for(ContainerDetailsVO contVO : containerDetailsVOs){
			if(contVO.getDsnVOs() != null && contVO.getDsnVOs().size() > 0){
				for(DSNVO dsn : contVO.getDsnVOs()){
					String dsnPKKey  = dsn.getOriginExchangeOffice()
					+dsn.getDestinationExchangeOffice()
					+dsn.getMailCategoryCode()
					+dsn.getMailSubclass()
					+dsn.getDsn()
					+dsn.getYear();
					for(String key : dsnKey){						
						if(key.equals(dsnPKKey)){
							if(dsn.getMailbags() != null && dsn.getMailbags().size() > 0){
								for(MailbagVO bagVO : dsn.getMailbags()){
									mailVOs.add(createMailInConsignmentVO(bagVO,consignmentFilterVO));
								}		
							}
							if(contVO.getDesptachDetailsVOs() != null && 
									contVO.getDesptachDetailsVOs().size() > 0){
								for(DespatchDetailsVO despatchVO : contVO.getDesptachDetailsVOs()){
									String despatchKey  = despatchVO.getOriginOfficeOfExchange()
									+despatchVO.getDestinationOfficeOfExchange()
									+despatchVO.getMailCategoryCode()
									+despatchVO.getMailSubclass()
									+despatchVO.getDsn()
									+despatchVO.getYear();
									if(key.equals(despatchKey)){
										mailVOs.add(createMailInConsignmentVO(despatchVO,consignmentFilterVO));
									}
								}
							}
						}						
					}
				}
			}
		}
		log.exiting("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		return mailVOs;
	}
	private MailInConsignmentVO createMailInConsignmentVO(MailbagVO bagVO,ConsignmentFilterVO consignmentFilterVO) {
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setPaCode(consignmentFilterVO.getPaCode());
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setConsignmentNumber(consignmentFilterVO.getConsignmentNumber());
		mailInConsignmentVO.setDsn(bagVO.getDespatchSerialNumber());
		mailInConsignmentVO.setOriginExchangeOffice(bagVO.getOoe());
		mailInConsignmentVO.setDestinationExchangeOffice(bagVO.getDoe());
		mailInConsignmentVO.setMailClass(bagVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(bagVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(bagVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(bagVO.getYear());
		mailInConsignmentVO.setStatedBags(1);
		mailInConsignmentVO.setStatedWeight(bagVO.getWeight());
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		mailInConsignmentVO.setReceptacleSerialNumber(bagVO.getReceptacleSerialNumber());
		mailInConsignmentVO.setMailId(bagVO.getMailbagId());
		mailInConsignmentVO.setHighestNumberedReceptacle(bagVO.getHighestNumberedReceptacle());
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(bagVO.getRegisteredOrInsuredIndicator());
		mailInConsignmentVO.setMailSource("MTK060");
		return mailInConsignmentVO;
	}   
	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(DespatchDetailsVO despatchVO,ConsignmentFilterVO consignmentFilterVO) {
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setConsignmentNumber(consignmentFilterVO.getConsignmentNumber());
		mailInConsignmentVO.setConsignmentNumber(despatchVO.getConsignmentNumber());
		mailInConsignmentVO.setPaCode(consignmentFilterVO.getPaCode());
		mailInConsignmentVO.setPaCode(despatchVO.getPaCode());
		mailInConsignmentVO.setDsn(despatchVO.getDsn());
		mailInConsignmentVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
		mailInConsignmentVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
		mailInConsignmentVO.setMailClass(despatchVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(despatchVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(despatchVO.getYear());
		if(despatchVO.getStatedBags() > 0){
			//This is a manifested despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getStatedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getStatedWeight());
		}else{
			//This is a found despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getReceivedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getReceivedWeight());
		}
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		return mailInConsignmentVO;
	}   
private List<ErrorVO> validateForm(AttachRoutingDetails attachRoutingDetails) {
		String conDocNo = attachRoutingDetails.getConsignemntNumber();
		String paCode = attachRoutingDetails.getPaCode();
		String conDate = attachRoutingDetails.getConsignmentDate();
		String type = attachRoutingDetails.getConsignmentType();
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}
		if(conDate == null || ("".equals(conDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condate.empty"));
		}
		if(type == null || ("".equals(type.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.type.empty"));
		}
		if(attachRoutingDetails.getOnwardRouting()!=null){
		}
		return errors;
	}
	private FlightFilterVO handleFlightFilterVO(
			RoutingInConsignmentVO routingVO,
			LogonAttributes logonAttributes){
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		flightFilterVO.setStation(routingVO.getPol());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate(routingVO.getOnwardFlightDate());
		flightFilterVO.setCarrierCode(routingVO.getOnwardCarrierCode());
		flightFilterVO.setFlightNumber(routingVO.getOnwardFlightNumber());
		log.log(Log.FINE, " ****** flightFilterVO***** ", flightFilterVO);
		return flightFilterVO;
	}
	private void validateConsignment(AttachRoutingDetails attachRoutingDetails,
			ActionContext actionContext, ConsignmentDocumentVO consignmentDocumentVO, LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = null;
		//		validate PA code
		String pacode = attachRoutingDetails.getPaCode();
		if (pacode != null && pacode.trim().length() > 0) {
			log.log(Log.FINE, "Going To validate PA code ...in command");
			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
			try {
				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
						logonAttributes.getCompanyCode(),pacode.toUpperCase());
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (postalAdministrationVO == null) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
						new Object[]{pacode.toUpperCase()}));
				return;
			}
		}
		//		VALIDATING Type with Mail Category Code
		int invalidCategory = 0;
		Collection<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentcollVOs();
		String type = consignmentDocumentVO.getType();
		if(mailVOs != null && mailVOs.size() > 0) {
			for(MailInConsignmentVO mailbagVO:mailVOs) {
				if(!"D".equals(mailbagVO.getOperationFlag())){
					String category = mailbagVO.getMailCategoryCode();
					if("CN41".equals(type)){
						if(!"B".equals(category)){
							invalidCategory = 1;
						}
					}
					if("CN38".equals(type)){
						if("B".equals(category)){
							invalidCategory = 2;
						}
					}
				}
			}
		}
		if(invalidCategory == 1){
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchsal"));
			return;
		}
		if(invalidCategory == 2){
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchnosal"));
			return;
		}
	}
	private Collection<RoutingInConsignmentVO> populateRoutingVos(ArrayList<OnwardRouting> routingDetails,ConsignmentDocumentVO consignmentDocumentVO) {
		Collection<RoutingInConsignmentVO> newRoutingVOs =
				new ArrayList<RoutingInConsignmentVO>();
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		for(OnwardRouting onwardRouting:routingDetails){
			RoutingInConsignmentVO newRoutingVO = new RoutingInConsignmentVO();
			if(null!=onwardRouting.getCarrierCode()){
				
				newRoutingVO.setOnwardCarrierCode(onwardRouting.getCarrierCode().toUpperCase());
			}
			if(null!=onwardRouting.getFlightDate()){
				
				newRoutingVO.setOnwardFlightDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)
						.setDate(onwardRouting.getFlightDate().toUpperCase()));
			}
			if(null!=onwardRouting.getFlightNumber()){
				
				newRoutingVO.setOnwardFlightNumber(onwardRouting.getFlightNumber().toUpperCase());
			}
			if(null!=onwardRouting.getPol()){
				
				newRoutingVO.setPol(onwardRouting.getPol().toUpperCase());
			}
			if(null!=onwardRouting.getPou()){
				
				newRoutingVO.setPou(onwardRouting.getPou().toUpperCase());
			}
			if(null!=onwardRouting.getOperationalStatus()){
				newRoutingVO.setOperationFlag(MailConstantsVO.MRA_STATUS_UPDATE);
			}
			if(null!=onwardRouting.getOperationFlag()){
				newRoutingVO.setOperationFlag(onwardRouting.getOperationFlag());
			}
			newRoutingVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
			newRoutingVO.setRoutingSerialNumber(onwardRouting.getRoutingSerialNumber());
			newRoutingVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			newRoutingVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			newRoutingVO.setPaCode(consignmentDocumentVO.getPaCode());
			newRoutingVOs.add(newRoutingVO);
		}
		return newRoutingVOs;
	}
	private void validateRoutingDetails(AttachRoutingDetails attachRoutingDetails, ActionContext actionContext,
			ConsignmentDocumentVO consignmentDocumentVO, LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = null;
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
//		VALIDATING CarrierCode
		String invalidCarrierCode = "";
		Collection<RoutingInConsignmentVO> routingVOs =  consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						routingVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber().toUpperCase());
						routingVO.setPaCode(consignmentDocumentVO.getPaCode().toUpperCase());
						if(routingVO.getOnwardCarrierCode()!=null && routingVO.getOnwardCarrierCode().trim().length()>0){
						String carrierCode = routingVO.getOnwardCarrierCode().toUpperCase();
						AirlineValidationVO airlineValidationVO = null;
						airlineValidationVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),carrierCode);
						if (airlineValidationVO == null) {
							if("".equals(invalidCarrierCode)){
								invalidCarrierCode = carrierCode;
							}else{
								invalidCarrierCode = new StringBuilder(invalidCarrierCode).append(",").append(carrierCode).toString();
							}
						}else{
							routingVO.setOnwardCarrierId(airlineValidationVO.getAirlineIdentifier());
						}
					}
					}
					String flightNumber = (routingVO.getOnwardFlightNumber());
					routingVO.setOnwardFlightNumber(flightNumber);
					routingVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if(!"".equals(invalidCarrierCode)){
			formErrors.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
					new Object[]{invalidCarrierCode.toUpperCase()}));
			actionContext.addAllError((List<ErrorVO>) formErrors);
			return;
		}
		Collection<RoutingInConsignmentVO> validRoutingVos=new ArrayList<RoutingInConsignmentVO>();
		for(RoutingInConsignmentVO routing:routingVOs){
			if((routing.getOnwardCarrierCode()!=null && routing.getOnwardCarrierCode().trim().length()>0) &&
					(routing.getOnwardFlightNumber()!=null && routing.getOnwardFlightNumber().trim().length()>0)){
				validRoutingVos.add(routing);        
			}
		}
		consignmentDocumentVO.setRoutingInConsignmentVOs(validRoutingVos);
		errors = null;
//		VALIDATING POL
		String invalidPOL = "";
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						if(routingVO.getPol()!=null && routingVO.getPol().trim().length()>0){
						String pol = routingVO.getPol().toUpperCase();
						AirportValidationVO airportValidationVO = null;
						airportValidationVO = new AreaDelegate().validateAirportCode(
								logonAttributes.getCompanyCode(),pol);
						if (airportValidationVO == null) {
							if("".equals(invalidPOL)){
								invalidPOL = pol;
							}else{
								invalidPOL = new StringBuilder(invalidPOL).append(",").append(pol).toString();
							}
						}
					}
				}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if(!"".equals(invalidPOL)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpol",
					new Object[]{invalidPOL.toUpperCase()}));
			actionContext.addAllError((List<ErrorVO>) formErrors);
			return;
		}
		errors = null;
		//VALIDATING POU
		String invalidPOU = "";
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						if(routingVO.getPou()!=null && routingVO.getPou().trim().length()>0){
						String pou = routingVO.getPou().toUpperCase();
						AirportValidationVO airportValidationVO = null;
						airportValidationVO = new AreaDelegate().validateAirportCode(
								logonAttributes.getCompanyCode(),pou);
						if (airportValidationVO == null) {
							if("".equals(invalidPOU)){
								invalidPOU = pou;
							}else{
								invalidPOU = new StringBuilder(invalidPOU).append(",").append(pou).toString();
							}
						}
					}
				}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if(!"".equals(invalidPOU)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpou",
					new Object[]{invalidPOU.toUpperCase()}));
			actionContext.addAllError((List<ErrorVO>) formErrors);
			return;
		}
//		Check for same POL and POU
		int sameAP = 0;
		if(routingVOs != null && routingVOs.size() > 0) {
			StringBuilder errFlightNumbers = null;
			for(RoutingInConsignmentVO routingVO:routingVOs) {
				log.log(Log.FINE, " routingVO ", routingVO);
				if(!"D".equals(routingVO.getOperationFlag())){
					log.log(Log.FINE, " D.equals(routingVO.getOperationFlag()");
					String pol="";
					String pou="";
					if(routingVO.getPol()!=null && routingVO.getPol().trim().length()>0){
					pol = routingVO.getPol().toUpperCase();           
					pou = routingVO.getPou().toUpperCase();
					if (pol.equals(pou)) {
						sameAP = 1;
						formErrors.add(new ErrorVO("mailtracking.defaults.consignment.sameairport",
								new Object[]{new StringBuilder(routingVO.getOnwardCarrierCode())
								.append("").append(routingVO.getOnwardFlightNumber()).toString()}));
					}
					}
//					VALIDATE FLIGHT CARRIER CODE
					AirlineDelegate airlineDelegate = new AirlineDelegate();
					AirlineValidationVO airlineValidationVO = null;
					String flightCarrierCode = routingVO.getOnwardCarrierCode();
					errors = null;
					if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
						try {
							airlineValidationVO = airlineDelegate.validateAlphaCode(
									logonAttributes.getCompanyCode(),
									flightCarrierCode.trim().toUpperCase());
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {flightCarrierCode.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidCarrier"
									,obj));
						}
					}
					//Checking Flight number  validation
					if(flightCarrierCode!=null && flightCarrierCode.equals(logonAttributes.getOwnAirlineCode())){
						Collection<FlightValidationVO> flightValidationVOs = new ArrayList<FlightValidationVO>();
						FlightFilterVO flightFilterVO = handleFlightFilterVO(
								routingVO,logonAttributes);
						flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
						log.log(Log.FINE,
								"routingVO.getIsDuplicateFlightChecked() ",
								routingVO.getIsDuplicateFlightChecked());
							try {
								flightValidationVOs =
									new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
								log.log(Log.FINE, "flightValidationVOs ",
										flightValidationVOs);
							}catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
						if (errors != null && errors.size() > 0) {
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {flightCarrierCode.toUpperCase(),
									routingVO.getOnwardFlightNumber(),
									routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
									,obj));
						}
						if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
							Object[] obj = {flightCarrierCode.toUpperCase(),
									routingVO.getOnwardFlightNumber(),
									routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
									,obj));
						}else if (flightValidationVOs.size()== 1) {
							List<FlightValidationVO> validflightvos = (List<FlightValidationVO>)flightValidationVOs;
							FlightValidationVO flightValidationVO = validflightvos.get(0);
							//Validating whether POU present in the Flight Route
							String route ="";
							if(flightValidationVO!=null){
							route = flightValidationVO.getFlightRoute();          
							}
							if(route != null && route.length() >0) {
								StringTokenizer stationTokens = new StringTokenizer(route,"-");
								boolean isPouInRoute = false;
								while(stationTokens.hasMoreTokens()) {
									if(pou.equals(stationTokens.nextToken()) && formErrors.size() == 0 ) {
										isPouInRoute = true;
										break;
									}        				
								}
								if(!isPouInRoute) {
									Object[] obj = {pou,
											flightCarrierCode.toUpperCase(),
											routingVO.getOnwardFlightNumber(),
											route};
									formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflightRoute",obj));
								}        				
							}
							//A-5249 from ICRD-84046
							if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
				                    FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
				                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
								if(errFlightNumbers==null){
									errFlightNumbers = new StringBuilder();
								}else{
									errFlightNumbers.append(", ");
								}
								errFlightNumbers.append(flightCarrierCode.toUpperCase()).append(flightValidationVO.getFlightNumber());
							}
							routingVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
						}else {
							/*duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)(flightValidationVOs));
							duplicateFlightSession.setParentScreenId(SCREEN_ID);
							duplicateFlightSession.setFlightFilterVO(flightFilterVO);
							maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
							mailManifestForm.setDuplicateFlightStatus(FLAG_YES);
							routingVO.setIsDuplicateFlightChecked(FLAG_YES);
							break;*/ 
							//skipped 
						}
					}else{
						routingVO.setOnwardCarrierSeqNum(MailConstantsVO.DESTN_FLT);
					}
//					VALIDATE POL & POU
					Collection<ErrorVO> polErrors = new ArrayList<ErrorVO>();
					Collection<ErrorVO> pouErrors = new ArrayList<ErrorVO>();
					String org = routingVO.getPol();
					String dest = routingVO.getPou();
					AreaDelegate areaDelegate = new AreaDelegate();
					AirportValidationVO airportValidationVO = null;
					errors = null;
					if (org != null && !"".equals(org)) {
						try {
							airportValidationVO = areaDelegate.validateAirportCode(
									logonAttributes.getCompanyCode(),
									org.toUpperCase());
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							polErrors.addAll(errors);
						}
						if (polErrors != null && polErrors.size() > 0) {
							Object[] obj = {org.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOL"
									,obj));
						}}
					errors = null;
					if (dest != null && !"".equals(dest)) {
						try {
							airportValidationVO = areaDelegate.validateAirportCode(
									logonAttributes.getCompanyCode(),
									dest.toUpperCase());
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							pouErrors.addAll(errors);
						}
						if (pouErrors != null && pouErrors.size() > 0) {
							Object[] obj = {dest.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOU"
									,obj));
						}
					}
					
				}
			}
			if(errFlightNumbers!=null){
				Object[] obj = {errFlightNumbers};
				ErrorVO err = new ErrorVO("mailtracking.defaults.err.flightintbcortba",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(err);			
				return;
			}
		}
		log.log(Log.FINE, " out formErrors ", formErrors);
		if(formErrors.size()>0){
			actionContext.addAllError((List<ErrorVO>) formErrors);
		}
		if(sameAP == 1){
			return;
		}
		/**
		 * Added to check whether connection flights are continuous
		 */
		if(routingVOs != null && routingVOs.size() > 1) {
			List<RoutingInConsignmentVO>	routingVOList = (List<RoutingInConsignmentVO>)routingVOs;
			int routingVOSize = routingVOList.size();
			log.log(Log.FINE, " routingVOList.size() = ", routingVOSize);
			for(int i=0;i<routingVOSize;i++) {
				int nextvo = (i+1);
				if(nextvo < routingVOSize){
					log.log(Log.FINE, "nextvo = ", nextvo);
					RoutingInConsignmentVO routingInConsignmentVOOne = (RoutingInConsignmentVO)routingVOList.get(i);
					RoutingInConsignmentVO routingInConsignmentVOTwo = (RoutingInConsignmentVO)routingVOList.get(i+1);
					if(routingInConsignmentVOOne.getOperationFlag()!=null && "D".equals(routingInConsignmentVOOne.getOperationFlag())){
						continue;
					}
					if(!routingInConsignmentVOOne.getPou().equals(routingInConsignmentVOTwo.getPol())){
						actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.mismatchinconnectionflights"));
						return;
					}
				}
			}
		}
	}
}
