package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.DeliverMailCommand.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-5526 :
 * 24-Jan-2018 : Draft
 */
public class ReadyForDeliveryCommand extends AbstractCommand {

	private static final String SUCCESS_MESSAGE = "mail.operations.err.readyfordeliverysuccess";
	private static final String SYS_PAR_AUTO_ARRIVAL_OFFSET = "mail.operations.autoarrivaloffset";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_MARKED = "mailtracking.defaults.mailarrival.readyfordeliverymarkedalready";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED = "mailtracking.defaults.mailarrival.readyfordeliveryresdittrigerred";
    private static final String INVALID_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidDeliveryAirportException";
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ReadyForDeliveryCommand");
	//Added by A-7540 for ICRD-335994
	public static final String INVALID_READYFOR_DELIVERY_AIRPORT ="mailtracking.defaults.InvalidReadyForDeliveryAirportException";
	public void execute(ActionContext actionContext) throws BusinessDelegateException {

		this.log.entering("ArriveMailCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailinboundModel mailinboundModel = (MailinboundModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LocalDate flightAta = null;
		ArrayList<MailBagDetails> mailbagDetailsVoFromScreen = null;
		Collection<ContainerDetails> containerDetailsVosFromScreen = new ArrayList<ContainerDetails>();
		MailArrivalVO mailArrivalVO = null;
		boolean containerSelected = false;
		HashMap<String, ContainerDetailsVO> containerDetailsMap = new HashMap<String, ContainerDetailsVO>();
//		HashMap<String, MailBagDetails> mailDetailsMap = new HashMap<String, MailBagDetails>();
		Collection<ContainerDetailsVO> containerDetailsVos = null;
		Collection<ContainerDetailsVO> contDtlsForSave = new ArrayList<ContainerDetailsVO>();
		OperationalFlightVO operationalFlightVO = null;
		Map<String, String> systemParameterValues = null;
		Collection<String> systemparameterTypes = new ArrayList<String>();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
    	systemparameterTypes.add(SYS_PAR_AUTO_ARRIVAL_OFFSET);
    	int autoArrivalOffset=0;
    	HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		ContainerDetails containerDetailsCollection = 
				 mailinboundModel.getContainerDetail();
		
    	
    	  	
		systemParameterValues=sharedDefaultsDelegate.
					findSystemParameterByCodes(systemparameterTypes);
		if(systemParameterValues!=null && systemParameterValues.containsKey(SYS_PAR_AUTO_ARRIVAL_OFFSET)){
			String offset=systemParameterValues.get(SYS_PAR_AUTO_ARRIVAL_OFFSET);
			if(offset!=null)
			autoArrivalOffset=Integer.parseInt(offset);
		}

		/*
		 * When mailbag(s) is selected for readyForDelivery marking,
		 * mailinboundModel.getContainerDetail() is having container info &
		 * mailbag info
		 * (mailinboundModel.getContainerDetail().getMailBagDetailsCollection())
		 */
		ContainerDetails containerDetailsCollFromScreen = mailinboundModel.getContainerDetail();
		// Flight level info will be avaiable in mailinboundDetails
		MailinboundDetails mailinboundDetails = (MailinboundDetails) mailinboundModel.getMailinboundDetails();
		// Constructing operational flight VO from mailinboundDetails
		operationalFlightVO = MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails,
				logonAttributes);
		// IF mailbag level operation,fetching container info first
		if (containerDetailsCollFromScreen != null) {
			// If container info is present,fetching selected mailbag info(s)
			mailbagDetailsVoFromScreen = containerDetailsCollFromScreen.getMailBagDetailsCollection();
			// If mailbag info is present,confirms that it is mailbag level,
			// else it is container level marking
			if (mailbagDetailsVoFromScreen.size()==0) {
				containerSelected = true;
			}
		} // if containerDetailsCollFromScreen is null means ,it is container
			// level marking
		else {
			containerSelected = true;
			containerDetailsVosFromScreen = mailinboundModel.getContainerDetailsCollection();

		}

		// Fetching the mailArrival info of the selected flight
		try {
			mailArrivalVO = new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		// If mail arrival info is present
		if (null != mailArrivalVO) {
			// fetching container info(s)
			containerDetailsVos = mailArrivalVO.getContainerDetails();

			
			
			for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
				containerDetailsVoMap.put(
						containerDetailsVO.getContainerNumber(), containerDetailsVO);
			}
			
		if(containerDetailsCollection!=null){
				
				if(containerDetailsVoMap.containsKey(containerDetailsCollection.getContainerno())){
					mailDetailsMap.clear();
					ContainerDetailsVO containerDetailsVO=
							containerDetailsVoMap.get(containerDetailsCollection.getContainerno());
					for(MailbagVO mailbagVO:containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).getMailDetails()){
						String mailKey=mailbagVO.getMailbagId();
						mailDetailsMap.put(mailKey, mailbagVO);	
					}
					Collection<MailbagVO> selectedMailBagVos=
							 new ArrayList<MailbagVO>();
					selectedMailBagVos.clear();
					if(null!=containerDetailsCollection.getDsnDetailsCollection()&&containerDetailsCollection.getDsnDetailsCollection().size()>0){
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						Collection<DSNVO> selectedDsnVos=
								 new ArrayList<DSNVO>();
						selectedDsnVos.clear();
						for(DSNDetails dsnDetails:containerDetailsCollection.getDsnDetailsCollection()){
							String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
									.append(dsnDetails.getDestinationExchangeOffice())
										.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
											.append(dsnDetails.getYear()).toString();
							
							for(MailbagVO mailbagVO:containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).getMailDetails()){
								String mailKey=new StringBuilder(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getOoe())
										.append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass())
										.append(mailbagVO.getYear()).toString();
								if(mailKey.equals(dsnKey)){
									selectedMailBagVos.add(mailbagVO);
								}
							}  
							if(dsnDetailsMap.containsKey(dsnKey)){
								selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
							}
						}
						containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).setDsnVOs(selectedDsnVos);
					}
					else if(null!=containerDetailsCollection.getMailBagDetailsCollection()&&containerDetailsCollection.getMailBagDetailsCollection().size()>0){
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						Collection<DSNVO> selectedDsnVos=
								 new ArrayList<DSNVO>();
						selectedDsnVos.clear();
						for(MailBagDetails mailBagDetails:containerDetailsCollection.getMailBagDetailsCollection()){
							String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
									.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
									.append(mailBagDetails.getYear()).toString();   
							
							if(mailDetailsMap.containsKey(mailBagDetails.getMailBagId())){  
								selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailBagId())); 
							} 
							if(dsnDetailsMap.containsKey(mailBagKey)){
								selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));
							}
						}
						containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).setDsnVOs(selectedDsnVos);
					}
					else{
						selectedMailBagVos.addAll(containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).getMailDetails());
					}
					containerDetailsVoMap.get(containerDetailsCollection.getContainerno()).setMailDetails(selectedMailBagVos);
					containerDetailsVosSelected.add(containerDetailsVoMap.get(containerDetailsCollection.getContainerno()));
				}
			}
			

			ContainerDetailsVO containerDtlsVO = null;
			if (containerDetailsVos != null && !containerDetailsVos.isEmpty())
				containerDtlsVO = containerDetailsVos.iterator().next();
			// Flight validation starts to find out the ATA if present
			AirlineValidationVO airlineValidationVO = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			Collection<FlightValidationVO> flightValidationVOs = null;

			FlightFilterVO flightFilterVO = handleFlightFilterVO(containerDtlsVO, logonAttributes);
			String flightCarrierCode = mailinboundDetails.getCarrierCode();

			/**
			 * Validating flight carrier code
			 */
			if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(),
							flightCarrierCode.trim().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				flightFilterVO.setCarrierCode(mailinboundDetails.getCarrierCode().toUpperCase());
				flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
				flightFilterVO.setFlightSequenceNumber(Long.parseLong(mailinboundDetails.getFlightSeqNumber()));

				try {
					log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
					flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);

				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				// Validating the flight to find out the Actual time of Arrival
				if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
					for (FlightValidationVO flightVO : flightValidationVOs) {

						flightAta = flightVO.getAta();
						break;
					}
				}
			}

			/*
			 * 
			 * For getting the selected container details
			 */
			// Existing container details of the flight is keeping in
			// containerDetailsMap
			if (containerDetailsVos != null && !containerDetailsVos.isEmpty()) {
				for (ContainerDetailsVO containerDetailsVO : containerDetailsVos) {
					containerDetailsMap.put(containerDetailsVO.getContainerNumber(), containerDetailsVO);
				}
				// The selected mailbag(s) info is keeping in mailDetailsMap
//				if (mailbagDetailsVoFromScreen != null && !mailbagDetailsVoFromScreen.isEmpty()) {
//					mailDetailsMap.clear();
//					for (MailBagDetails selectedMailbag : mailbagDetailsVoFromScreen) {
//						mailDetailsMap.put(selectedMailbag.getMailBagId(), selectedMailbag);
//
//					}
//				}
			}
			// If mailbag level transaction,check if the data is exists and the
			// selected container is present in the existing container map.
//			if (containerDetailsCollFromScreen != null
//					&& containerDetailsMap.get(containerDetailsCollFromScreen.getContainerno()) != null) {
//				
			if (containerDetailsVosSelected != null && containerDetailsVosSelected.size()>0) {
					for(ContainerDetailsVO containerDetailsVO :containerDetailsVosSelected){
				
				// Updating the operational flag
				containerDetailsVO.setOperationFlag("U");
				containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				// Creating new collection for mailbagVos for updating the RFD.
				Collection<MailbagVO> mailbagsForSave = new ArrayList<MailbagVO>();
				// Fecthing the existing mailbag(s) info
				if (containerDetailsVO != null && containerDetailsVO.getMailDetails() != null
						&& !containerDetailsVO.getMailDetails().isEmpty()){
					// Filtering out the selected mailbags from existing Mail
					// map.
					for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
						if (mailDetailsMap != null && mailDetailsMap.get(mailbagVO.getMailbagId()) != null) {
							mailbagVO.setScannedPort(logonAttributes.getAirportCode());
							// From screen value
							mailbagVO.setOperationalFlag("U");
							mailbagVO.setArrivedFlag("Y");
							mailbagVO.setCarrierCode(mailinboundDetails.getCarrierCode());
								mailbagVO.setScannedDate(
										new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
							
							
							/*
							 * If the data is entered in the RFD popup,the date
							 * and time will be avaiable in
							 * mailbagInfoFromScreen.getReadyForDeliveryDate()
							 * &&
							 * mailbagInfoFromScreen.getReadyForDeliveryTime();
							 */
							if ( containerDetailsCollection.getDsnDetailsCollection().size()>0){
								for(DSNDetails dsnData:containerDetailsCollection.getDsnDetailsCollection()){
									if(dsnData.getReadyForDeliveryDate() != null) {
						
								LocalDate readyForDeliveryDate = new LocalDate(mailbagVO.getScannedPort(), Location.ARP,
										true).setDate(dsnData.getReadyForDeliveryDate());
								readyForDeliveryDate.setTime("00:00:00");
								String readyForDeliveryTime = dsnData.getReadyForDeliveryTime();
								int readyForDeliveryTimeInhours = 0;
								int readyForDeliveryTimeInMinutes = 0;
								// Converting the coming time ("00:00") from
								// String to integer
								if (readyForDeliveryTime != null && readyForDeliveryTime.contains(":")) {
									if (readyForDeliveryTime.length() == 5) {
										if (readyForDeliveryTime.substring(0, 2) != null) {
											readyForDeliveryTimeInhours = Integer
													.parseInt(readyForDeliveryTime.substring(0, 2));
										}
										if (readyForDeliveryTime.substring(3, 5) != null) {
											readyForDeliveryTimeInMinutes = Integer
													.parseInt(readyForDeliveryTime.substring(3, 5));
										}

									}
								}
								// Updating the time to readyForDeliveryDate
								readyForDeliveryDate.addHours(readyForDeliveryTimeInhours);
								readyForDeliveryDate.addMinutes(readyForDeliveryTimeInMinutes);
								mailbagVO.setResditEventDate(readyForDeliveryDate);
							}}}
							// Setting the RFD date and time in mailbagVO
							mailbagVO.setResditEventPort(mailbagVO.getScannedPort());
							mailbagsForSave.add(mailbagVO);
						}
					}
				containerDetailsVO
						.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false));
				containerDetailsVO.setMailDetails(mailbagsForSave);
				contDtlsForSave.add(containerDetailsVO);
			}}}
			// If container level RFD
			else if (containerSelected) {
				// fetching the container info from mailinboundModel
				containerDetailsVosFromScreen = mailinboundModel.getContainerDetailsCollection();
				// If data exists,fetching the current container details from
				// existing container map and updating with new info
				if (containerDetailsVosFromScreen != null && !containerDetailsVosFromScreen.isEmpty()) {
					for (ContainerDetails containerDetails : containerDetailsVosFromScreen) {
						if (containerDetailsMap.get(containerDetails.getContainerno()) != null) {
							ContainerDetailsVO containerDetailsVO = containerDetailsMap
									.get(containerDetails.getContainerno());
							containerDetailsVO.setOperationFlag("U");
							containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
							// Fetching the netire mailbags of the container and
							// updating the RFD & arrival info
							if (containerDetailsVO != null && containerDetailsVO.getMailDetails() != null
									&& !containerDetailsVO.getMailDetails().isEmpty())
								for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
										mailbagVO.setScannedPort(logonAttributes.getAirportCode());
										// From screen value
										mailbagVO.setOperationalFlag("U");
										mailbagVO.setArrivedFlag("Y");
										mailbagVO.setCarrierCode(mailinboundDetails.getCarrierCode());	
										mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
										mailbagVO.setScannedDate(
												new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
									
									// Updating RFD date and time
									if (containerDetails.getReadyForDeliveryDate() != null) {
										LocalDate readyForDeliveryDate = new LocalDate(LocalDate.NO_STATION,
												Location.NONE, false)
														.setDate(containerDetails.getReadyForDeliveryDate());
										readyForDeliveryDate.setTime("00:00:00");
										String readyForDeliveryTime = containerDetails.getReadyForDeliveryTime();
										int readyForDeliveryTimeInhours = 0;
										int readyForDeliveryTimeInMinutes = 0;
										if (readyForDeliveryTime != null && readyForDeliveryTime.contains(":")) {
											if (readyForDeliveryTime.length() == 5) {
												if (readyForDeliveryTime.substring(0, 2) != null) {
													readyForDeliveryTimeInhours = Integer
															.parseInt(readyForDeliveryTime.substring(0, 2));
												}
												if (readyForDeliveryTime.substring(3, 5) != null) {
													readyForDeliveryTimeInMinutes = Integer
															.parseInt(readyForDeliveryTime.substring(3, 5));
												}

											}
										}
										readyForDeliveryDate.addHours(readyForDeliveryTimeInhours);
										readyForDeliveryDate.addMinutes(readyForDeliveryTimeInMinutes);
										mailbagVO.setResditEventDate(readyForDeliveryDate);
									}
									mailbagVO.setResditEventPort(mailbagVO.getScannedPort());

								}
							containerDetailsVO.setLastUpdateTime(
									new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false));
							contDtlsForSave.add(containerDetailsVO);
						}

					}
				}
			}
			// Updating mailArrivalVO with the modified container info only
			mailArrivalVO.setContainerDetails(contDtlsForSave);

			mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
			// Mail source not set in mailarrival
			mailArrivalVO.setFlightDate(operationalFlightVO.getFlightDate());
			Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);

			if (locks == null || locks.size() == 0) {
				locks = null;
			}

			try {
				new MailTrackingDefaultsDelegate().onStatustoReadyforDelivery(mailArrivalVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				for (ErrorVO err : errors) {
					if (ERROR_ALREADY_READY_FOR_DELIVED_MARKED.equalsIgnoreCase(err.getErrorCode())) {
						ErrorVO error = new ErrorVO(ERROR_ALREADY_READY_FOR_DELIVED_MARKED);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
					//Modified by A-7540 for ICRD-335994 Starts 
					if (INVALID_READYFOR_DELIVERY_AIRPORT.equalsIgnoreCase(err.getErrorCode())) {
						ErrorVO error = new ErrorVO(INVALID_READYFOR_DELIVERY_AIRPORT);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
					//Added by A-8527 for ICRD-332101 Ends 
					if (ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED.equalsIgnoreCase(err.getErrorCode())) {
						ErrorVO error = new ErrorVO(ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
				}

			}

			ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
			result.add(mailinboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			//ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);
			//error.setErrorDisplayType(ErrorDisplayType.INFO);   
			//actionContext.addError(error);
			actionContext.setResponseVO(responseVO);
		}
	}

	/**
	 * @author A-5526
	 * @param mailArrivalVO
	 * @return
	 */
	private Collection<LockVO> prepareLocksForSave(MailArrivalVO mailArrivalVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();

		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO conVO : containerDetailsVOs) {
				if (conVO.getOperationFlag() != null && conVO.getOperationFlag().trim().length() > 0) {
					ArrayList<MailbagVO> mailbagvos = new ArrayList<MailbagVO>(conVO.getMailDetails());
					if (mailbagvos != null && mailbagvos.size() > 0) {
						for (MailbagVO bagvo : mailbagvos) {
							if (bagvo.getOperationalFlag() != null && bagvo.getOperationalFlag().trim().length() > 0) {
								ULDLockVO lock = new ULDLockVO();
								lock.setAction(LockConstants.ACTION_MAILARRIVAL);
								lock.setClientType(ClientType.WEB);
								lock.setCompanyCode(logonAttributes.getCompanyCode());
								// lock.setScreenId(SCREEN_ID);
								lock.setStationCode(logonAttributes.getStationCode());
								if (bagvo.getContainerForInventory() != null) {
									lock.setUldNumber(bagvo.getContainerForInventory());
									lock.setDescription(bagvo.getContainerForInventory());
									lock.setRemarks(bagvo.getContainerForInventory());
									log.log(Log.FINE, "\n lock------->>", lock);
									locks.add(lock);
								}
							}

						}
					}

				}
			}

		}
		return locks;
	}

	/**
	 * @author A-5526
	 * @param containerDtlsVO
	 * @param logonAttributes
	 * @return
	 */
	private FlightFilterVO handleFlightFilterVO(ContainerDetailsVO containerDtlsVO, LogonAttributes logonAttributes) {

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(containerDtlsVO.getFlightNumber());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate(containerDtlsVO.getFlightDate());

		return flightFilterVO;
	}
}
