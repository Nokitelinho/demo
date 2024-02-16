/*
 * MailTransfer.java Created on October 4, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
 
import com.ibsplc.icargo.business.mail.operations.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.AssignedFlight;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightPK;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegment;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegmentPK;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.ULDAtAirport;
import com.ibsplc.icargo.business.mail.operations.ULDAtAirportPK;
import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
//import com.ibsplc.icargo.business.mail.operations.MLDController;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.ReassignController;
import com.ibsplc.icargo.business.mail.operations.PartnerCarrier;
import com.ibsplc.icargo.business.mail.operations.Cardit;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
/*import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;*/
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 *
 */
public class MailTransfer {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String INVALID_FLIGHT_NO = "-1";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";

	/**
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */

	/*
	 * Added By Karthick V as the part of the ANZ Mail Tracking CR 409.. Note:
	 * This method is intentinally made to return a Map<String ,Object> where
	 * this object can catually be a Collection<ContainerVo> and Collection<ContainerDetailsVo>
	 * for the keys CONT and CONTDTL respectively Since the method is expected
	 * to return both of these ...
	 *
	 * Collection<ContainerVo> is being used for the
	 * saveContaniners-ReassignContainers-TransferContainers (Method calls)
	 * Collection<ContainerDetailsVo> is required to fetch the DSNs required
	 * for the Transfer Manifest Report Generation..
	 */
	public Map<String, Object> transferContainers(
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO,String printFlag) throws SystemException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException {
		log.entering("MailTransfer", "transferContainers");
	    Map<MailbagPK, MailbagVO> mailMap = new HashMap<MailbagPK, MailbagVO>();
		Collection<ULDForSegmentVO> uLDForSegmentVOs = saveArrivalDetailsForTransfer(
				containerVOs, operationalFlightVO,mailMap);
		Map<String, Object> mapForReturn = new HashMap<String, Object>();
		// default to destn assignment
		int toFlightSegSerialNum = -1;
		if (operationalFlightVO.getFlightSequenceNumber() > 0) {
			new MailController().calculateContentID(containerVOs, operationalFlightVO);
			toFlightSegSerialNum = saveOutboundDetailsForTransfer(containerVOs,
					operationalFlightVO, uLDForSegmentVOs);

			//For CR :AirNZ404 : Mail Allocation STARTS
			for(ULDForSegmentVO uldForSegmentVO : uLDForSegmentVOs){
				ULDForSegmentVO uldForSegVO = new ULDForSegmentVO();
				BeanHelper.copyProperties(uldForSegVO, uldForSegmentVO);
				uldForSegVO.setPou(operationalFlightVO.getPou());
				uldForSegVO.setCarrierId(operationalFlightVO.getCarrierId());
				uldForSegVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				uldForSegVO.setFlightDate(operationalFlightVO.getFlightDate());
				uldForSegVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				//Updating Booking Details of Current Flight
				new ReassignController().updateBookingForFlight(
						uldForSegVO,operationalFlightVO,"UPDATE_BOOKING_TO_FLIGHT");
			}
			//CR :AirNZ404 :Changes ENDS
			//Added by A-7794 as part of ICRD-208677
			for (ContainerVO containerVO : containerVOs) {
				if(MailConstantsVO.ULD_TYPE.equals(containerVO.getType())||containerVO.isUldTobarrow()){
	           
					if (isULDIntegrationEnabled() && OPERATION_FLAG_INSERT.equals(containerVO
	                    .getOperationFlag())) {
				updateULDForOperations(containerVOs, operationalFlightVO, false);
			}
			}
			}
		} else {
			saveDestAssignednDetailsForTransfer(containerVOs, uLDForSegmentVOs,
					operationalFlightVO);
		}

		Collection<ContainerVO> transferContainers = updateTransferredContainers(
				containerVOs, operationalFlightVO, toFlightSegSerialNum);

		Collection<MailbagVO> transferredMails = updateMailBagInULD(mailMap,
				operationalFlightVO, toFlightSegSerialNum,containerVOs);
		flagResditsForContainerTransfer(transferredMails, containerVOs,
				operationalFlightVO);
		//Added by A-8527 for IASCB-34446 start
		String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
		if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
		//Added by A-8527 for IASCB-34446 Ends	
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
		mailController.flagMLDForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		}
		//importMRAData
		boolean provisionalRateImport =false;
		Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(operationalFlightVO ,((ArrayList<ContainerVO>) containerVOs).get(0),transferredMails,MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport) ;
		log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
		 if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
			 String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			 	//Modified by A-7794 as part of ICRD-232299
	        	if(importEnabled!=null && (importEnabled.contains("A") || importEnabled.contains("D"))){
        try {
			new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
		 }
		 // import Provisonal rate Data to malmraproint for upront rate Calculation
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
			String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
			if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
	      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(operationalFlightVO ,((ArrayList<ContainerVO>) containerVOs).get(0),transferredMails,MailConstantsVO.MAIL_STATUS_TRANSFERRED,provisionalRateImport) ;
	      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
	        try {
	        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (ProxyException e) {      
					throw new SystemException(e.getMessage(), e);     
	        }
	        }
		}
//Added as part of CRQ ICRD-93584 by A-5526 starts
		 //new MLDController().flagMLDForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		//Added as part of CRQ ICRD-93584 by A-5526 ends
		if (transferContainers != null && transferContainers.size() > 0) {
			//set the container Vos tat contains the latset container information in the map.
			mapForReturn.put(MailConstantsVO.CONST_CONTAINER,
					transferContainers);
		}

		/*
		 * Added By karthick V as the part of the Air NewZealand Cr Reports has
		 * to be Generated For all then Dsns that has been Transferred ... This
		 * method is used to collect the DSNVos from the UldforSegmentVos .. and
		 * this in turn will be used to construct the Transfer Manifest
		 * Details..
		 *
		 */
		//Modified as part of bug IASCB-71597 by A-5526
		if (uLDForSegmentVOs != null && uLDForSegmentVOs.size() > 0) {      
			mapForReturn.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
					createContainerDtlsForTransfermanifest(uLDForSegmentVOs,transferredMails));
		}
		log.exiting("MailTransfer", "transferContainers");
		return mapForReturn;
	}




	/**
	 * @author A-3227
	 * @param doe
	 * @param companyCode
	 * @param deliveredPort
	 * @param cityCache
	 * @return
	 * @throws SystemException
	 */
	private boolean isValidDeliveryAirport(String doe, String companyCode,
			String deliveredPort, Map<String, String> cityCache)
			throws SystemException {
		log.entering("MailTransfer", "isValidDeliveryAirport");
		Collection<String> officeOfExchanges = new ArrayList<String>();
		if(doe !=null && doe.length() > 0) {
			officeOfExchanges.add(doe);
		}
		/*
	     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
	     * the inner collection contains the values in the order :
	     * 0.OFFICE OF EXCHANGE
	     * 1.CITY NEAR TO OE
	     * 2.NEAREST AIRPORT TO CITY
	     */
		String deliveryCityCode = null;
		String nearestAirport =  null;
		String nearestAirportToCity = null;
		log.log(Log.FINE, "----officeOfExchanges---", officeOfExchanges);
		Collection<ArrayList<String>> groupedOECityArpCodes = new MailController().findCityAndAirportForOE(companyCode, officeOfExchanges);
		log
				.log(Log.FINE, "----groupedOECityArpCodes---",
						groupedOECityArpCodes);
		if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
			for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
				if(cityAndArpForOE.size() == 3) {
					if(doe != null && doe.length() > 0 && doe.equals(cityAndArpForOE.get(0))) {
						deliveryCityCode = cityAndArpForOE.get(1);
						nearestAirportToCity = cityAndArpForOE.get(2);
					}
				}
			}
		}
		/*
		 * take from the map first if not present, does proxy call and adds to
		 * cache
		 */
		if (cityCache != null) {
			nearestAirport = cityCache.get(deliveryCityCode);
		}
//		if (nearestAirport == null) {// nearestarp not found in cache
//			Collection<String> cityCodes = new ArrayList<String>();
//			cityCodes.add(deliveryCityCode);
//
//			Map<String, CityVO> cityVOMap = null;
//			try {
//				cityVOMap = new SharedAreaProxy().validateCityCodes(
//						companyCode, cityCodes);
//			} catch (SharedProxyException ex) {
//				log.log(Log.SEVERE, "sharedproxyException " + ex);
//			}
//
//			CityVO cityVO = cityVOMap.get(deliveryCityCode);
//			nearestAirport = cityVO.getNearestAirport();
//
//			if (nearestAirport != null) {
//				cityCache.put(deliveryCityCode, nearestAirport);
//			}
//		}
		if (nearestAirport == null && nearestAirportToCity != null ) {// nearest arp not found in cache
			nearestAirport = nearestAirportToCity;
			if (nearestAirport != null) {
				cityCache.put(deliveryCityCode, nearestAirport);
			}
		}
		if (nearestAirport != null && nearestAirport.equals(deliveredPort)) {
			log.log(Log.FINE, "----nearestAirport---", nearestAirport);
			log.log(Log.FINE, "----deliveredPort---", deliveredPort);
			return true;
		}
		return false;
	}

	/**
	 * TODO Purpose Feb 1, 2007, A-1739
	 *
	 * @param mailbagVOs
	 * @param airportCode
	 * @throws SystemException
	 */
	private void removeTerminatingMailbags(Collection<MailbagVO> mailbagVOs,
			String airportCode) throws SystemException {

		Collection<MailbagVO> mailbagsToRem = new ArrayList<MailbagVO>();
		Map<String, String> cityCache = new HashMap<String, String>();

		for (MailbagVO mailbagVO : mailbagVOs) {
			if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO
					.getCompanyCode(), airportCode, cityCache)) {
				mailbagsToRem.add(mailbagVO);
			}
		}
		mailbagVOs.removeAll(mailbagsToRem);
	}

	/**
	 * @author a-1936 Added By Karthick V as the part of the NCA Mail Tracking
	 *         CR .. Reset the Flight Pks to Carrier Needed for the Reassign ..
	 * @param mailBags
	 * @param carrierID
	 * @param airportCode
	 * @throws SystemException
	 */

	private Collection<MailbagVO> checkArrivedMailbagsInTransfer(
			Collection<MailbagVO> mailBags) throws SystemException {
		Collection<MailbagVO> arrivedMails = new ArrayList<MailbagVO>();
		for (MailbagVO mailBag : mailBags) {
			// works since mailbags are fetched before transferdetails are
			// updated
			if (MailbagVO.FLAG_YES.equals(mailBag.getArrivedFlag())|| "ARR".equals(mailBag.getLatestStatus())) {
				MailbagVO arrivedMailbagVO = new MailbagVO();
				BeanHelper.copyProperties(arrivedMailbagVO, mailBag);
				arrivedMailbagVO.setFinalDestination(null);
				arrivedMailbagVO.setFlightNumber(String
						.valueOf(MailConstantsVO.DESTN_FLT));
				arrivedMailbagVO
						.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				arrivedMailbagVO
						.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
				arrivedMailbagVO.setPou(null);
				/*arrivedMailbagVO.setContainerNumber(mailBag
						.getInventoryContainer());*/
				
			  /*if(MailConstantsVO.ULD_TYPE.equals(mailBag.getContainerType())){//A-8061 added for ULD transfer without arriving 
					arrivedMailbagVO.setUldNumber(mailBag.getUldNumber());
				}else{*/
				arrivedMailbagVO.setUldNumber(MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(mailBag.getCarrierCode()));
				//}
				
				arrivedMailbagVO.setContainerNumber(arrivedMailbagVO.getUldNumber());
				//Changed by A-4809 containerType to be set from Mailbag ContainerType
				arrivedMailbagVO.setContainerType(mailBag
						.getContainerType());
				arrivedMails.add(arrivedMailbagVO);
			}
		}
		return arrivedMails;
	}


	/**
	 * @param mailbagVO
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	private AssignedFlightPK createMailbagInboundFlightPK(MailbagVO mailbagVO,
			String airportCode) throws SystemException {
		log.entering("MailTransfer", "createMailbagInboundFlightPK");
		AssignedFlightPK inboundFlightPK = new AssignedFlightPK();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		Collection<FlightValidationVO> flightValidationVOs =  Proxy.getInstance().get(FlightOperationsProxy.class).validateFlightForAirport(flightFilterVO);
		FlightValidationVO validVO = null;
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()) {
					validVO = flightValidationVO;
					break;
				}
			}
		}
		if(validVO!=null && mailbagVO.getLegSerialNumber() == 0)
		{
			inboundFlightPK.setLegSerialNumber(validVO.getLegSerialNumber());
		}
		else
		{
			inboundFlightPK.setLegSerialNumber(mailbagVO.getLegSerialNumber());
		}
		inboundFlightPK.setCompanyCode(mailbagVO.getCompanyCode());
		inboundFlightPK.setCarrierId(mailbagVO.getCarrierId());
		inboundFlightPK.setFlightNumber(mailbagVO.getFlightNumber());
		inboundFlightPK.setFlightSequenceNumber(mailbagVO
				.getFlightSequenceNumber());
		inboundFlightPK.setAirportCode(airportCode);
		log.exiting("MailTransfer", "createMailbagInboundFlightPK");
		return inboundFlightPK;
	}
	/**
	 * returns InboundFlight and mailbags map
	 *
	 * @param mailbagVOs
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	private Map<AssignedFlightPK, Collection<MailbagVO>> constructFlightMailbagMap(
			Collection<MailbagVO> mailbagVOs, String airportCode)
			throws SystemException {
		log.entering("MailTransfer", "constructFlightMailbagMap");
		Map<AssignedFlightPK, Collection<MailbagVO>> flightMailbagMap = new HashMap<AssignedFlightPK, Collection<MailbagVO>>();
		Collection<MailbagVO> newMailbagVos = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			AssignedFlightPK inboundFlightPK = createMailbagInboundFlightPK(
					mailbagVO, airportCode);
			newMailbagVos = flightMailbagMap.get(inboundFlightPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				flightMailbagMap.put(inboundFlightPK, newMailbagVos);
			}
			newMailbagVos.add(mailbagVO);
		}
		log.exiting("MailTransfer", "constructFlightMailbagMap");
		return flightMailbagMap;
	}


	/**
	 * Inbound mailbags
	 *
	 * @param mailbagVOs
	 * @param containerVO
	 * @param dsnAtAirportMap
	 * @throws SystemException
	 */
	public void saveMailbagsInboundDtlsForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "saveMailbagsInboundDtlsForTransfer");
		Map<AssignedFlightPK, Collection<MailbagVO>> flightMailbagVOsMap = constructFlightMailbagMap(
				mailbagVOs, containerVO.getAssignedPort());
		for (AssignedFlightPK inboundFlightPK : flightMailbagVOsMap.keySet()) {
			//Modified for ICRD-263680
			if(inboundFlightPK.getFlightNumber()!=null && !StringUtils.equals(inboundFlightPK.getFlightNumber(), INVALID_FLIGHT_NO)){
			AssignedFlight inboundFlight = findOrCreateInboundFlight(inboundFlightPK);
			inboundFlight.saveMailArrivalDetailsForTransfer(flightMailbagVOsMap
					.get(inboundFlightPK), containerVO);
			//to update transit flag as N, IASCB-85196
			if(containerVO.isFromDeviationList()) {
			  inboundFlight.releaseContainer(containerVO);
			  }
			}
		}
		log.exiting("MailTransfer", "saveMailbagsInboundDtlsForTransfer");
	}



	/**
	 * @param containerVO
	 * @return
	 */
	private AssignedFlightPK constructAssignedFlightPKForMail(
			ContainerVO containerVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(containerVO.getAssignedPort());
		assignedFlightPk.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(containerVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(containerVO.getCarrierId());
		return assignedFlightPk;
	}

	/**
	 * This method is used to create the assignedFlight
	 *
	 * @param operationalFlightVO
	 * @return AssignedFlight
	 * @throws SystemException
	 */
	private AssignedFlight createAssignedFlightForMail(ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "createAssignedFlightForMail");
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(containerVO.getAssignedPort());
		assignedFlightVO.setCarrierCode(containerVO.getCarrierCode());
		assignedFlightVO.setCarrierId(containerVO.getCarrierId());
		assignedFlightVO.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightVO.setFlightDate(containerVO.getFlightDate());
		assignedFlightVO.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
		log.exiting("MailTransfer", "createAssignedFlightForMail");
		return assignedFlight;
	}

	/**
	 * @param containerVO
	 * @throws SystemException
	 */
	private void validateAndCreateAsgdFltForMailbag(ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "validateAndCreateAsgdFltForMailbag");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPKForMail(containerVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.log(Log.INFO, "FINDER EXCEPTION IS THROWN");
			assignedFlight = createAssignedFlightForMail(containerVO);
		}
		log.exiting("MailTransfer", "validateAndCreateAsgdFltForMailbag");
	}


	/**
	 * @param containerVO
	 * @param segmentSerialNumber
	 * @return
	 */
	private AssignedFlightSegmentVO constructAssignedFltSegmentVOForMail(
			ContainerVO containerVO, int segmentSerialNumber) {
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		assignedFlightSegmentVO.setCarrierId(containerVO.getCarrierId());
		assignedFlightSegmentVO.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightSegmentVO.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightSegmentVO.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		assignedFlightSegmentVO.setSegmentSerialNumber(segmentSerialNumber);
		assignedFlightSegmentVO.setPol(containerVO.getAssignedPort());
		assignedFlightSegmentVO.setPou(containerVO.getPou());
		return assignedFlightSegmentVO;
	}

	/**
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 */
	private AssignedFlightSegment findOrCreateAsgdFltSegmentForMail(
			ContainerVO containerVO) throws SystemException,
			InvalidFlightSegmentException {
		log.entering("MailTransfer", "findOrCreateAsgdFltSegmentForMail");
		AssignedFlightSegment assignedFlightSegment = null;
		int segmentSerialNumber = findFlightSegment(containerVO
				.getCompanyCode(), containerVO.getCarrierId(), containerVO
				.getFlightNumber(), containerVO.getFlightSequenceNumber(),
				containerVO.getAssignedPort(), containerVO.getPou());
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(containerVO.getCarrierId());
		assignedFlightSegmentPK.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightSegmentPK.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		try {
			assignedFlightSegment = AssignedFlightSegment
					.find(assignedFlightSegmentPK);
		} catch (FinderException finderException) {
			log.log(Log.SEVERE, "Catching FinderException ");
			AssignedFlightSegmentVO assignedFlightSegmentVO = constructAssignedFltSegmentVOForMail(
					containerVO, segmentSerialNumber);
			assignedFlightSegment = new AssignedFlightSegment(
					assignedFlightSegmentVO);
		}
		log.exiting("MailTransfer", "findOrCreateAsgdFltSegmentForMail");
		return assignedFlightSegment;

	}

	/**
	 * @author A-3227 RENO K ABRAHAM,  Added on 24/09/08
	 * @param mailbagVOs
	 * @param despatchDetailsVOs
	 * @param containerVO
	 * @throws SystemException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	private void updateBookingForToFlight(Collection<MailbagVO> mailbagVOs,
			Collection<DespatchDetailsVO> despatchDetailsVOs,
			ContainerVO containerVO) throws SystemException, CapacityBookingProxyException, MailBookingException{
		log.entering("MailTransfer", "updateBookingForFlight");
		Collection<MailbagVO> flightAssignedMailbags = null;
		Collection<DespatchDetailsVO> flightAssignedDespatches = null;
		if(mailbagVOs != null && mailbagVOs.size()>0 &&
				containerVO !=null){
			flightAssignedMailbags =  new ArrayList<MailbagVO>();
			for(MailbagVO mailbagVO : mailbagVOs){
				MailbagVO mailVO = new MailbagVO();
				BeanHelper.copyProperties(mailVO, mailbagVO);
				mailVO.setCompanyCode(containerVO.getCompanyCode());
				mailVO.setCarrierId(containerVO.getCarrierId());
				mailVO.setFlightNumber(containerVO.getFlightNumber());
				mailVO.setFlightDate(containerVO.getFlightDate());
				mailVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				mailVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				mailVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				mailVO.setPou(containerVO.getPou());
				mailVO.setPol(containerVO.getAssignedPort());
				mailVO.setContainerNumber(containerVO.getContainerNumber());
				mailVO.setUldNumber(containerVO.getContainerNumber());
				mailVO.setContainerType(containerVO.getType());
				flightAssignedMailbags.add(mailVO);
			}
		}
		if(despatchDetailsVOs != null && despatchDetailsVOs.size()>0 &&
				containerVO !=null){
			flightAssignedDespatches =  new ArrayList<DespatchDetailsVO>();
			for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs){
				DespatchDetailsVO despatchVO = new DespatchDetailsVO();
				BeanHelper.copyProperties(despatchVO, despatchDetailsVO);
				despatchVO.setCompanyCode(containerVO.getCompanyCode());
				despatchVO.setCarrierId(containerVO.getCarrierId());
				despatchVO.setFlightNumber(containerVO.getFlightNumber());
				despatchVO.setFlightDate(containerVO.getFlightDate());
				despatchVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				despatchVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				despatchVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				despatchVO.setPou(containerVO.getPou());
				despatchVO.setAirportCode(containerVO.getAssignedPort());
				despatchVO.setContainerNumber(containerVO.getContainerNumber());
				despatchVO.setUldNumber(containerVO.getContainerNumber());
				despatchVO.setContainerType(containerVO.getType());
				flightAssignedDespatches.add(despatchVO);
			}
		}
//		new MailController().updateBookingToFlight(flightAssignedMailbags,
//				flightAssignedDespatches,containerVO.getBookingTimeVOs());
		log.exiting("MailTransfer", "updateBookingForFlight");
	}

	/**
	 * @param mailbagVOs
	 * @param containerVO
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	private void saveOutboundMailsFlightForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws SystemException, InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		log.entering("MailTransfer", "saveOutboundMailsFlightForTransfer");
		validateAndCreateAsgdFltForMailbag(containerVO);
		AssignedFlightSegment assignedFlightSegment = findOrCreateAsgdFltSegmentForMail(containerVO);
		assignedFlightSegment.saveOutboundMailsFlightForTransfer(mailbagVOs,
				containerVO);
		//Updating Booking For DSNS in Flight
		updateBookingForToFlight(mailbagVOs, null,containerVO);

		log.exiting("MailTransfer", "saveOutboundMailsFlightForTransfer");
	}


	private String constructBulkULDNumber(String bulkPou) {
		return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(
				MailConstantsVO.SEPARATOR).append(bulkPou).toString();
	}
	/**
	 * @param containerVO
	 * @return
	 */
	private ULDAtAirportVO constructULDAtAirportVO(ContainerVO containerVO) {
		ULDAtAirportVO uLDAtAirportVO = new ULDAtAirportVO();
		uLDAtAirportVO.setCompanyCode(containerVO.getCompanyCode());
		uLDAtAirportVO.setUldNumber(containerVO.getContainerNumber());
		uLDAtAirportVO.setAirportCode(containerVO.getAssignedPort());
		uLDAtAirportVO.setCarrierId(containerVO.getCarrierId());
		uLDAtAirportVO.setCarrierCode(containerVO.getCarrierCode());
		uLDAtAirportVO.setLastUpdateUser(containerVO.getLastUpdateUser());
		uLDAtAirportVO.setLastUpdateTime(containerVO.getLastUpdateTime());
		uLDAtAirportVO.setFinalDestination(containerVO.getFinalDestination());
		if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			uLDAtAirportVO.setUldNumber(constructBulkULDNumber(containerVO
					.getFinalDestination()));
		} else {
			uLDAtAirportVO.setRemarks(containerVO.getRemarks());
			uLDAtAirportVO.setLocationCode(containerVO.getLocationCode());
			uLDAtAirportVO.setWarehouseCode(containerVO.getWarehouseCode());
		}
		return uLDAtAirportVO;
	}

	/**
	 * @author a-1739
	 * @param mailbags
	 * @return
	 */
	private double calculateMailbagsWeight(Collection<MailbagVO> mailbags) {
		double totalWeight = 0;
		for (MailbagVO mailbagVO : mailbags) {
			totalWeight += mailbagVO.getWeight().getSystemValue();
		}
		return totalWeight;
	}
	/**
	 * @param mailbagVOs
	 * @param containerVO
	 * @throws SystemException
	 */
	private void saveOutboundMailsDestnForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "saveMailsDestAssignDtlsForTransfer");
		ULDAtAirport uLDAtAirport = null;
		ULDAtAirportVO uLDAtAirportVO = constructULDAtAirportVO(containerVO);
		ULDAtAirportPK uLDAtAirportPK = constructULDAtAirportPK(uLDAtAirportVO);
		try {
			uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
		} catch (FinderException finderException) {
			uLDAtAirport = new ULDAtAirport(uLDAtAirportVO);
		}
		/*uLDAtAirport.setNumberOfBags(uLDAtAirport.getNumberOfBags()
				+ mailbagVOs.size());
		uLDAtAirport.setTotalWeight(uLDAtAirport.getTotalWeight()
				+ calculateMailbagsWeight(mailbagVOs));*/
		uLDAtAirport.saveDestAssignedMailsForTransfer(mailbagVOs, containerVO);
		log.exiting("MailTransfer", "saveMailsDestAssignDtlsForTransfer");

	}


	/**
	 * TODO Purpose Oct 11, 2006, a-1739
	 *
	 * @param mailbagVOs
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> updateMailbagsForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "updateMailbagDetailsForTransfer");
		Collection<MailbagVO> transferredMails = new Mailbag()
				.updateMailbagsForTransfer(mailbagVOs, containerVO);
		log.exiting("MailTransfer", "updateMailbagDetailsForTransfer");
		return transferredMails;
	}


	/**
	 * TODO Purpose Oct 11, 2006, a-1739
	 *
	 * @param transferredMails
	 * @param companyCode
	 * @param ownAirlineCode
	 * @param eventPort
	 * @param carrierCode
	 * @param isFlightAssigned
	 * @param ownAirlineId
	 * @param carrierId
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void flagResditsForMailbagTransfer(
			Collection<MailbagVO> transferredMails, ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTransfer", "flagResditsForTransfer");
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagResditsForMailbagTransfer(transferredMails,
				containerVO);
	/*	String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		new ResditController().flagResditsForMailbagTransfer(transferredMails,
				containerVO);
		}*/
		log.exiting("MailTransfer", "flagResditsForTransfer");

	}
	/**
	 *
	 * @param mailbagVOs
	 * @param containerVO
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	public void transferMailbags(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO) throws SystemException,
			InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		log.entering("MailTransfer", "transferMailbags");
		Collection<String> systemParamterCodes = null;
		Map<String, String> systemParamterMap = null;
		Collection<MailbagVO> mailbagsForAcceptance = new ArrayList<>();
		Collection<MailbagVO> mailbagsForTransfer = new ArrayList<>();
		removeTerminatingMailbags(mailbagVOs, containerVO.getAssignedPort());
		log.log(Log.FINE, "THE MAILBAGS AFTER REMOVING TERMINATING ",
				mailbagVOs);
		Collection<MailbagVO> mailbagsToRem = checkArrivedMailbagsInTransfer(mailbagVOs);
		if (mailbagsToRem != null && mailbagsToRem.size() > 0) {
			new ReassignController().reassignMailFromDestination(mailbagsToRem);
		}
		
       if(!containerVO.isFoundTransfer() && !containerVO.isFromDeviationList() && !containerVO.isFromCarditList() && !"EXPFLTFIN_ACPMAL".equals(containerVO.getMailSource()) ){//added by a-8353 for IASCB-34167 for blocking inbound flow in case of found transfer
		saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
       }
		/*
		 * Added By Karthick V as the part of the NCA Mail Tracking Cr
		 *
		 * Method will be invoked to 1. Transfer Mails To Flight .. 2.Tranfer
		 * Mail Bags From the Inventory .. 3.Transfer Mail Bags to the Other
		 * Carriers ..
		 *
		 * In case of the (2) and (3) there is no need to save the OutBound
		 * Details .. as we are under the assumption that in CASE 2: we are
		 * marking transfer for the MailBags that are already arrived and remove
		 * those mailbags from the inventory .. No outbound Assignment happens
		 *
		 * CASE 3:In this Case we need to just mark the Transfer for all arrived
		 * /Not Already arrived Mailbags and update the Master ,History Details ..
		 * Need not Do the Outbound Process as we are under the Assumption that
		 * the MailBags are Tranferred Out From Our System ..
		 *
		 * Note:- Case 2 and Case 3 are Handled with the Container Number
		 * intentionally being null so that the Outbound Assignments can be
		 * avoided ..
		 */

		if (containerVO.getContainerNumber() != null) {
			if (containerVO.getFlightSequenceNumber() > -1) {
				saveOutboundMailsFlightForTransfer(mailbagVOs, containerVO);
			} else {
				saveOutboundMailsDestnForTransfer(mailbagVOs, containerVO);
			}
			//Added as part of CRQ ICRD-93584 by A-5526 starts
			//Added by A-8527 for IASCB-34446 start
			String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
			//Added by A-8527 for IASCB-34446 Ends	
				
				for(MailbagVO mailbagVO: mailbagVOs){
					if(mailbagVO.getTransferFromCarrier()!=null){
						mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
						if (containerVO.getPou()!=null && mailbagVO.getPou()==null){
							mailbagVO.setPou(containerVO.getPou());
							}
						if(mailbagVO.getContainerNumber()==null){
							mailbagVO.setContainerNumber(containerVO.getContainerNumber());
							}
						mailbagsForAcceptance.add(mailbagVO);
					}else{
						mailbagsForTransfer.add(mailbagVO);
					}
				}
				if(!mailbagsForAcceptance.isEmpty()){
					MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
					mailAcceptanceVO.setFlightNumber(containerVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					mailAcceptanceVO.setFlightDate(containerVO.getFlightDate());
					mailAcceptanceVO.setFlightStatus(containerVO.getFlightStatus());
					Collection<ContainerDetailsVO> containerdetailsVOs= new ArrayList<>();
					ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
					containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
					containerDetailsVO.setContainerType(containerVO.getType());
					containerdetailsVOs.add(containerDetailsVO);
					mailAcceptanceVO.setContainerDetails(containerdetailsVOs);
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagsForAcceptance);
				}
				if(!mailbagsForTransfer.isEmpty()){
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagMLDForMailbagTransfer(mailbagsForTransfer,containerVO, null);
			}
			 //new MLDController().flagMLDForMailbagTransfer(mailbagVOs, containerVO,null);
			 //Added as part of CRQ ICRD-93584 by A-5526 ends
			}
		}
		else{
			//Added for CRQ ICRD-135130 by A-8061 starts
			//Added by A-8527 for IASCB-34446 start
			String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
			//Added by A-8527 for IASCB-34446 Ends	
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagMLDForMailbagTransfer(mailbagVOs,containerVO, null);
			}
			//Added for CRQ ICRD-135130 by A-8061 end
			
		}
		Collection<MailbagVO> transferredMails = updateMailbagsForTransfer(
				mailbagVOs, containerVO);
		//updateDSNAtAirportForTransfer(dsnAtAirportMap, containerVO);
		LogonAttributes logonVO = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		if (mailbagVOs!=null&&mailbagVOs.size() >0){
			 for(MailbagVO mailbagVO:mailbagVOs){
			  if(mailbagVO.getScannedUser()==null){
				  mailbagVO.setScannedUser(logonVO.getUserId());
			     }
			   }
			 }
		mailController.flagHistoryForTransfer(mailbagVOs, containerVO);
		mailController.flagAuditForTransfer(mailbagVOs,containerVO);
		if(containerVO.isFromDeviationList()) {	          
			flagArrivalHistoryForDeviationMailbagsTransfer(mailbagVOs, containerVO);
		}
		 boolean isFromTruck=false;//RFT change
		 if (mailbagVOs!=null&&mailbagVOs.size() >0){
		 for(MailbagVO mailbagVO:mailbagVOs){
		  if("AA".equals(mailbagVO.getCompanyCode())&&mailbagVO.getIsFromTruck()!=null&&MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())){
        	  isFromTruck=true;//RFT change
		     }
		   }
		 }
		if(!isFromTruck){
		flagResditsForMailbagTransfer(mailbagVOs, containerVO);
		}
		//importMRAData
		boolean provisionalRateImport =false;
        Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(containerVO,mailbagVOs,MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport) ;
        log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
        if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
        	String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);//Modified by A-7794 as part of ICRD-323606
	        	if(importEnabled!=null && (importEnabled.contains("M") || importEnabled.contains("D"))){
        try {
			new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
        // import Provisonal rate Data to malmraproratint for upront rate Calculation
		String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
			provisionalRateImport = true;
      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(containerVO,mailbagVOs,MailConstantsVO.MAIL_STATUS_TRANSFERRED,provisionalRateImport) ;
      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
        try {
        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
			} catch (ProxyException e) {      
				throw new SystemException(e.getMessage(), e);     
        }
        }
	}
		/*
		 * Added By A-1936 to include the NCA-CR For all the MailBags tat has
		 * been Transferred the Cardit Message Has to be sent to the Transferred
		 * Carrier ..
		 */

		if (!(containerVO.getCarrierCode().equals(containerVO
				.getOwnAirlineCode()))
				&& !validateCarrierCodeFromPartner(
						containerVO.getCompanyCode(), containerVO
								.getOwnAirlineCode(), containerVO
								.getAssignedPort(), containerVO
								.getCarrierCode())) {
			systemParamterCodes = new ArrayList<String>();
			systemParamterCodes.add(MailConstantsVO.NCA_RESDIT_PROC);
			systemParamterMap = new SharedDefaultsProxy()
					.findSystemParameterByCodes(systemParamterCodes);
			if (systemParamterMap != null
					&& systemParamterMap.size() > 0
					&& systemParamterMap.get(MailConstantsVO.NCA_RESDIT_PROC)
							.equals(MailConstantsVO.FLAG_YES)) {
				flagCarditsForTransferCarrier(mailbagVOs, containerVO);
			}
		}

		/*
		 * Added By Karthick V as the part of the NCA Mail Tracking CR If a
		 * MailBag is arriveed but Not Delivered or Transferred then that Mail
		 * Bag lies in the Inventory List at that Airport..Once all the Mail
		 * Bags has been Delivered or TRansferred These mailBags has to removed
		 * From the Inventory as well.. Assume if the MailBag has been arrived
		 * Before but just now got Tranferred so Remove them from the Inventory
		 * as well ..
		 *
		 */

		log.exiting("MailTransfer", "transferMailbags");
	}


	/**
	 * 
	 * @param mailbagVOs
	 * @param containerVO
	 * @throws SystemException
	 */

	private void flagArrivalHistoryForDeviationMailbagsTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) throws SystemException {
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>(); 
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO(); 
		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setContentId(containerVO.getContentId());
		containerDetailsVO.setContainerType(containerVO.getType());
		containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setFlightDate(containerVO.getFlightDate());
		containerDetailsVO.setAssignedPort(containerVO.getAssignedPort());
		containerDetailsVO.setPol(containerVO.getPol());
		containerDetailsVO.setPou(containerVO.getPou());
		containerDetailsVO.setAcceptedFlag(containerVO.getAcceptanceFlag());
		containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
		//containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetails.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containerDetails);
		mailArrivalVO.setCompanyCode(containerVO.getCompanyCode());
		Collection<MailbagVO> mailbagVosForArrival = new ArrayList<>();
		MailbagVO mailbagVOForArrival = null;
		for(MailbagVO mailbagVO: mailbagVOs) {
			mailbagVOForArrival = new MailbagVO();
			BeanHelper.copyProperties(mailbagVOForArrival , mailbagVO);
			mailbagVOForArrival.setArrivedFlag(MailConstantsVO.FLAG_YES);
			mailbagVOForArrival.setAutoArriveMail(MailConstantsVO.FLAG_YES);
			mailbagVOForArrival.setScannedPort(containerVO.getPou());
			mailbagVosForArrival.add(mailbagVOForArrival);
		}
		containerDetailsVO.setMailDetails(mailbagVosForArrival);
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagMailbagAuditForArrival(mailArrivalVO);
		mailController.flagMailbagHistoryForArrival(mailArrivalVO);
	}


	/**
	 * @author A-1936 added as the Part of NCACR This method is used to
	 *         construct the Key for the HashMap which will be of the Form
	 *         (CMPCOD-CDTSEQNUM-STNCOD)
	 * @param mailBagVo
	 * @return
	 */
	private String constructCarditKey(MailbagVO mailBagVo) {
		return new StringBuilder(mailBagVo.getCompanyCode()).append("-")
				.append(mailBagVo.getCarditSequenceNumber()).append("-")
				.append(mailBagVo.getStationCode()).toString();
	}

	/**
	 * @author A-1936 Added as the Part of NCA-CR This method is use dto group
	 *         the MailBags Based on the Key (CMPCOD-CDTSEQNUM-STNCOD)
	 * @param mailBagVos
	 * @return
	 */
	private Map<String, Collection<MailbagVO>> groupMailBagsForCardit(
			Collection<MailbagVO> mailBagVos) {
		Map<String, Collection<MailbagVO>> carditMap = new HashMap<String, Collection<MailbagVO>>();
		Collection<MailbagVO> newMailbagVos = null;
		String carditMapKey = null;
		for (MailbagVO mailBag : mailBagVos) {
			carditMapKey = constructCarditKey(mailBag);
			newMailbagVos = carditMap.get(carditMapKey);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				carditMap.put(carditMapKey, newMailbagVos);
			}
			newMailbagVos.add(mailBag);
		}
		return carditMap;
	}



	/**
	 * @author A-1936 ADDED AS THE PART OF THE NCA-CR This method is used to
	 *         edit the Receipient Message From the Message Standards .. The
	 *         Recepient Id is overridden to be Transferred Carrier in the
	 *         Message From the MSGMSGMST as we are sending the Cardit to the
	 *         Transferred Carrier ..
	 *
	 * @param originalMessage
	 * @return
	 */
	private String createMessageForCardit(String originalMessage,
			String transferCarrier) {
		int upIndex = originalMessage.indexOf("UP+");
		int endIndex = originalMessage.indexOf(":", upIndex);
		String recepientID = originalMessage.substring(upIndex + 3, endIndex);
		String newMessage = originalMessage.replaceFirst(recepientID,
				transferCarrier);
		log.log(Log.FINE, "THE RECEPIENT ID IS ", recepientID);
		log.log(Log.FINE, "THE TRANSFER CARRIER IS ", transferCarrier);
		log.log(Log.FINE, "THE  ORIGINAL MESASGE   IS ", originalMessage);
		log.log(Log.FINE, "THE  NEW  MESASGE   IS ", newMessage);
		return newMessage;
	}

	/**
	 * @author A-1936 This method is used to Flag the Cardits for the Transfer
	 *         Carrier whenever the Transfer of MailBags takes Place .. Note:
	 *         Flagging of The Cardits can happen Only if the System Parameter
	 *         for the
	 *         Same(SHRSYSPARCOD-'mailtracking.defaults.resdit.ncaspecificcheckneeded')
	 *         is found to be 'Y'.
	 * @param transferredMails
	 * @throws SystemException
	 */
	private void flagCarditsForTransferCarrier(
			Collection<MailbagVO> transferredMails, ContainerVO containerVO)
			throws SystemException {
		log.entering("MailTranfer", "flagCarditsForTransferCarrier");
		Collection<MailbagVO> carditMailBags = null;
		Collection<MessageDespatchDetailsVO> despatchDetailsVos = null;
		Collection<String> pous = null;
		String toStationCode = null;
		MessageDespatchDetailsVO despatchDetailVo = null;
		String[] splitKey = null;
		String companyCode = null;
		int carditSequenceNumber = 0;
		String stationCode = null;
		Map<String, Collection<MailbagVO>> carditMap = null;
		MessageVO messageVo = null;
		MailbagVO carditMailBag = null;
		if (transferredMails != null && transferredMails.size() > 0) {
			carditMailBags = new ArrayList<MailbagVO>();
			for (MailbagVO mailBag : transferredMails) {
				carditMailBag = Cardit.findCarditDetailsForAllMailBags(mailBag
						.getCompanyCode(), Mailbag.findMailSequenceNumber(mailBag.getMailbagId(), mailBag.getCompanyCode()));
				log.log(Log.FINE, "THE CARDIT MAIL BAG VO", carditMailBag);
				if (carditMailBag != null) {
					carditMailBags.add(carditMailBag);
				}
			}

			if (carditMailBags != null && carditMailBags.size() > 0) {
				carditMap = groupMailBagsForCardit(carditMailBags);
				if (carditMap != null && carditMap.size() > 0) {
					MsgBrokerMessageProxy messageBrokerProxy = new MsgBrokerMessageProxy();
					for (String key : carditMap.keySet()) {
						splitKey = key.split("-");
						companyCode = splitKey[0];
						carditSequenceNumber = Integer.parseInt(splitKey[1]);
						stationCode = splitKey[2];
						messageVo = messageBrokerProxy.findMessageDetails(
								companyCode, stationCode,
								MailConstantsVO.MESSAGETYPE_CARDIT,
								carditSequenceNumber);

						if (messageVo != null) {
							messageVo.setRawMessage(createMessageForCardit(
									messageVo.getRawMessage(), containerVO
											.getCarrierCode()));
							despatchDetailsVos = new ArrayList<MessageDespatchDetailsVO>();
							despatchDetailVo = new MessageDespatchDetailsVO();
							despatchDetailVo
									.setPartyType(MailConstantsVO.MSG_PARTYTYPE_ARL);
							despatchDetailVo.setParty(containerVO
									.getCarrierCode());
							despatchDetailsVos.add(despatchDetailVo);
							messageVo.setDespatchDetails(despatchDetailsVos);
							if (containerVO.getFlightSequenceNumber() > 0) {
								toStationCode = containerVO.getPou();
							} else {
								toStationCode = containerVO
										.getFinalDestination();
							}
							log.log(Log.FINE,
									"<<<<<<THE  TOSTATIONCODE IS>>>> ",
									toStationCode);
							if (toStationCode != null) {
								if (messageVo.getPous() != null
										&& messageVo.getPous().size() > 0) {
									messageVo.getPous().add(toStationCode);
								} else {
									pous = new ArrayList<String>();
									pous.add(toStationCode);
									messageVo.setPous(pous);
								}
								log.log(Log.FINE, "The MessageVo is ",
										messageVo);
								log.log(Log.FINE, "The MessageVo is ",
										messageVo.getPous().size());
							}
							messageBrokerProxy.sendMessageText(messageVo);
						}
					}
				}
			}
		}
	}

	/**
	 * @author A-1936 This methos is used to validate whether the CarrierCode of
	 *         the Accepted Mail is same as any of the Partners. If same ,return
	 *         true else false.
	 * @param companyCode
	 * @param ownCarrierCode
	 * @param airportCode
	 * @param carrierCode
	 * @return
	 * @throws SystemException
	 */
	private boolean validateCarrierCodeFromPartner(String companyCode,
			String ownCarrierCode, String airportCode, String carrierCode)
			throws SystemException {
		log.entering("MailAcceptance", "ValidateCarrierCodeFromPartner");
		boolean isValid = false;
		Collection<PartnerCarrierVO> partnerCarrierVos = PartnerCarrier
				.findAllPartnerCarriers(companyCode, ownCarrierCode,
						airportCode);
		if (partnerCarrierVos != null && partnerCarrierVos.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarrierVos) {
				log.log(Log.FINE, "The Carrier Code is ", carrierCode);
				log.log(Log.FINE, "The  Partner Carrier Code is ",
						partnerCarrierVO.getPartnerCarrierCode());
				if (carrierCode
						.equals(partnerCarrierVO.getPartnerCarrierCode())) {
					isValid = true;
					break;
				}
			}
		}
		log.log(Log.FINE, "<<<<Can HandedOverResditFlagged>>>>", isValid);
		return isValid;
	}

	/**
	 * @author a-1936 This method is used to construct the Container Details
	 *         Containing the DSNVs that is actually Required for the Generation
	 *         of the Transfer Manifest Report ...
	 * @param uldForSegmentVos
	 * @return
	 * @throws SystemException 
	 */
	private Collection<ContainerDetailsVO> createContainerDtlsForTransfermanifest(
			Collection<ULDForSegmentVO> uldForSegmentVos,Collection<MailbagVO>mailbagVOs) throws SystemException {
		log.entering("MailTransfer", "createContainerDtlsForTransfermanifest");
		Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetails = null;
		//Map<String,DSNVO>dsnPKs=new HashMap<String,DSNVO>();
		if(uldForSegmentVos!=null&uldForSegmentVos.size()>0){
			for(ULDForSegmentVO uldForSegmentVO:uldForSegmentVos){
			Collection<DSNVO>dsnVOs=new ArrayList<>();
			DSNVO dsnVo = null;
			containerDetails = new ContainerDetailsVO();
		if(mailbagVOs!=null&&mailbagVOs.size()>0){
		for (MailbagVO mailbagVO : mailbagVOs) {
			 /*currDSNPK=mailbagVO.getMailSequenceNumber();
			dsnVo=dsnPKs.get(currDSNPK);*/
				/*if(dsnVo==null){*/
			if(uldForSegmentVO.getUldNumber().equals(mailbagVO.getUldNumber())){
					dsnVo = new DSNVO();
					dsnVo.setCompanyCode(mailbagVO.getCompanyCode());
					dsnVo.setOriginExchangeOffice(mailbagVO
							.getOoe());
					dsnVo.setDestinationExchangeOffice(mailbagVO
							.getDoe());
					dsnVo.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					dsnVo.setBags(1);
					dsnVo.setWeight(mailbagVO.getWeight());
					dsnVo.setYear(mailbagVO.getYear());
					dsnVo.setDsn(mailbagVO.getDespatchSerialNumber());
					dsnVo.setMailSubclass(mailbagVO.getMailSubclass());
					dsnVo.setMailClass(mailbagVO.getMailClass());
					dsnVo.setContainerNumber(mailbagVO.getUldNumber());
					dsnVo.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
					dsnVo.setMailbagId(mailbagVO.getMailbagId());
					dsnVo.setScannedUser(mailbagVO.getScannedUser());
					dsnVOs.add(dsnVo);
				/*}else{
					dsnVo.setBags(dsnVo.getBags()+1);
					//dsnVo.setWeight(dsnVo.getWeight()+mailbagVO.getWeight());
					try {
						dsnVo.setWeight(Measure.addMeasureValues(dsnVo.getWeight(), mailbagVO.getWeight()));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						throw new SystemException(e.getErrorCode());
					}//added by A-7371
				}
				dsnPKs.put(currDSNPK,dsnVo);*/
			  }
			}
		}
		containerDetails.setDsnVOs(dsnVOs);	
		containerDetails.setCompanyCode(uldForSegmentVO.getCompanyCode());
		containerDetails.setContainerNumber(uldForSegmentVO.getUldNumber());
			containerDetailsColl.add(containerDetails);
			}
		}
		log
				.log(Log.FINE, "THE CONTAINER DETAILS CONSTRUCTED",
						containerDetails);
		return containerDetailsColl;

	}



	/**
	 * Utilty for finding syspar Mar 23, 2007, A-1739
	 *
	 * @param syspar
	 * @return
	 * @throws SystemException
	 */
	private String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap =Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/**
	 * TODO Purpose Feb 6, 2007, A-1739
	 *
	 * @param transferredMails
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void flagResditsForContainerTransfer(
			Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("MailTransfer", "flagResditsForContainerTransfer");
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagResditsForContainerTransfer(
				transferredMails, containerVOs, operationalFlightVO);
		/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		new ResditController().flagResditsForContainerTransfer(
				transferredMails, containerVOs, operationalFlightVO);
		}*/
		log.exiting("MailTransfer", "flagResditsForContainerTransfer");

	}

	/**
	 *
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 */
	private Container findContainer(ContainerVO containerVO)
			throws FinderException, SystemException {
		log.entering("MailTransfer", "findContainer");
		Container container = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(containerVO.getAssignedPort());
		containerPK.setCarrierId(containerVO.getCarrierId());
		containerPK.setCompanyCode(containerVO.getCompanyCode());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		containerPK.setFlightNumber(containerVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		container = Container.find(containerPK);
		log.exiting("MailTransfer", "findContainer");
		return container;
	}

	/**
	 * @param container
	 * @param containerAuditVO
	 * @param operationalFlightVO
	 * @throws SystemException 
	 */
	private void collectContainerAuditDetails(Container container,
			ContainerAuditVO containerAuditVO,
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("MailTransfer", "collectContainerAuditDetails");
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		StringBuffer additionalInfo = new StringBuffer();
		containerAuditVO.setCompanyCode(container.getContainerPK()
				.getCompanyCode());
		containerAuditVO.setContainerNumber(container.getContainerPK()
				.getContainerNumber());
		containerAuditVO.setAssignedPort(container.getContainerPK()
				.getAssignmentPort());
		containerAuditVO
				.setCarrierId(container.getContainerPK().getCarrierId());
		containerAuditVO.setFlightNumber(container.getContainerPK()
				.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(container.getContainerPK()
				.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(container.getContainerPK()
				.getLegSerialNumber());
		containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_TRANSFER);
		containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_TRANSF);
		containerAuditVO.setUserId(container.getLastUpdateUser());
		additionalInfo.append("Transferred to ");
		if(!"-1".equals(operationalFlightVO.getFlightNumber())){
			additionalInfo.append(operationalFlightVO.getCarrierCode()).append(" ").append(operationalFlightVO.getFlightNumber()).append(", ");
		}else{
			additionalInfo.append(operationalFlightVO.getCarrierCode()).append(", ");
		}
		additionalInfo.append(new LocalDate(operationalFlightVO.getPol(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(operationalFlightVO.getFlightNumber())){
			additionalInfo.append(operationalFlightVO.getPol()).append(" - ").append(operationalFlightVO.getPou()).append(" ");
		}
		additionalInfo.append("in ").append(operationalFlightVO.getPol());
		containerAuditVO.setAdditionalInformation(additionalInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		log.exiting("MailTransfer", "collectContainerAuditDetails");
	}


	/**
	 * TODO Purpose Oct 10, 2006, a-1739
	 *
	 * @param containerVO
	 * @param operationalFlightVO
	 * @param toFlightSegSerialNum
	 */
	private void modifyContainerVO(ContainerVO containerVO,
			OperationalFlightVO operationalFlightVO, int toFlightSegSerialNum) {
		
		LogonAttributes logon=null;;
		try {
			logon = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		
		containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		containerVO.setFlightDate(operationalFlightVO.getFlightDate());
		containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerVO.setCarrierId(operationalFlightVO.getCarrierId());
		containerVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		containerVO
				.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		containerVO.setSegmentSerialNumber(toFlightSegSerialNum);
		containerVO.setAssignedPort(operationalFlightVO.getPol());
		containerVO.setAssignedDate(new LocalDate(operationalFlightVO.getPol(),
				Location.ARP, true));

		containerVO.setPou(operationalFlightVO.getPou());
		containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		containerVO.setOffloadFlag(MailConstantsVO.FLAG_NO);
		containerVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
		containerVO.setAssignedUser(operationalFlightVO.getOperator());
		containerVO.setMailbagPresent(true);
		if (logon.getOwnAirlineIdentifier() != containerVO.getCarrierId()
				&& MailConstantsVO.DESTN_FLT_STR.equals(containerVO.getFlightNumber())) {
			containerVO.setTransitFlag(MailConstantsVO.FLAG_NO);//ICRD-356336
		}
		
	}

	/**
	 * @author A-5991
	 * @param containerVO
	 * @param isOutbound
	 * @return
	 */
	private UldInFlightVO constructUldInFlightVO(ContainerVO containerVO,
			boolean isOutbound) {
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerVO.getCompanyCode());
		uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
		uldInFlightVO.setPou(containerVO.getPou());
		uldInFlightVO.setAirportCode(containerVO.getAssignedPort());
		uldInFlightVO.setCarrierId(containerVO.getCarrierId());
		if (containerVO.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerVO.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerVO
					.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		}
		if (isOutbound) {
			uldInFlightVO
					.setFlightDirection(MailConstantsVO.OPERATION_OUTBOUND);
		} else {
			uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		}
		return uldInFlightVO;
	}

	/**
	 * TODO Purpose Oct 10, 2006, a-1739
	 *
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param toFlightSegSerialNum
	 * @throws SystemException
	 */
	private Collection<ContainerVO> updateTransferredContainers(
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, int toFlightSegSerialNum)
			throws SystemException, ULDDefaultsProxyException {
		log.entering("MailTransfer", "updateTransferredContainers");
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
		Collection<ContainerVO> transferredCons = new ArrayList<ContainerVO>();
		ULDInFlightVO uldInFlightVO = null;
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		FlightDetailsVO flightDetailsVO = null;
		boolean isUld=false;
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		if (isUldIntegrationEnbled) {
			uldInFlightVOs = new ArrayList<ULDInFlightVO>();
			flightDetailsVO = new FlightDetailsVO();
			flightDetailsVO
					.setCompanyCode(operationalFlightVO.getCompanyCode());
		}

		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		Collection<UldInFlightVO> operationalULDs = new ArrayList<UldInFlightVO>();

		for (ContainerVO containerVO : containerVOs) {
			if(MailConstantsVO.ULD_TYPE.equals(containerVO.getType())){
				isUld=true;
			}
			Container container = null;
			try {
				container = findContainer(containerVO);
			} catch (FinderException exception) {
				// invliad case
				//throw new SystemException(exception.getErrorCode(), exception);
				container =new Container(containerVO);
				if(!"B".equals(containerVO.getType()))
				{
				mailController.flagMLDForMailOperationsInULD(containerVO,MailConstantsVO.MLD_STG);
				}
			}
			/*
			 * Added by Karthick V as the part of the Optimistic Locking
			 *
			 *
			 */
			container.setLastUpdateTime(containerVO.getLastUpdateTime());
			container.setArrivedStatus(MailConstantsVO.FLAG_YES);
			container.setTransferFlag(MailConstantsVO.FLAG_YES);
			//Code added for icrd-95515 for updateing the fields for the transferFromflight
			container.setFinalDestination(containerVO.getFinalDestination());
			//container.setPou(containerVO.getPou());
			//Code added for icrd-95515 for updateing the fields for the transferFromflight
			/* Audit implementation of Container */
			ContainerAuditVO containerAuditVO = new ContainerAuditVO(
					ContainerVO.MODULE, ContainerVO.SUBMODULE,
					ContainerVO.ENTITY);
			collectContainerAuditDetails(container, containerAuditVO,
					operationalFlightVO);
			//Commented by A-7794 as part of ICRD-226494
			//containerAuditVO.setAuditRemarks(containerVO.getRemarks());

			// to avoid old containerVO from being changed
			// old flight details needed for flagging online resdits
			ContainerVO toContainerVO = new ContainerVO();
			BeanHelper.copyProperties(toContainerVO, containerVO);

			modifyContainerVO(toContainerVO, operationalFlightVO,
					toFlightSegSerialNum);
			transferredCons.add(toContainerVO);
			Container toContainer = null;
			try {
				toContainer = findContainer(toContainerVO);
			} catch (FinderException exception) {
				// container maynot exist
				new Container(toContainerVO);
				if(!"B".equals(toContainerVO.getType()))
				{
				mailController.flagMLDForMailOperationsInULD(toContainerVO,MailConstantsVO.MLD_STG);
				}
			}
			if (toContainer != null) {
				toContainer.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				if(MailConstantsVO.FLAG_NO.equals(toContainerVO.getTransitFlag())){
					toContainer.setTransitFlag(toContainerVO.getTransitFlag());//ICRD-356336
				}
			}
			AuditUtils.performAudit(containerAuditVO);
			if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				//Modified by A-7794 as part of ICRD-208677
				if (isUldIntegrationEnbled && OPERATION_FLAG_INSERT.equals(containerVO
	                    .getOperationFlag())) {
					uldInFlightVO = new ULDInFlightVO();
					//Modified by A-7794 as part of ICRD-208677
					uldInFlightVO
							.setUldNumber(containerVO.getContainerNumber());
					uldInFlightVO.setPointOfLading(containerVO
							.getAssignedPort());
					uldInFlightVO.setPointOfUnLading(containerVO.getPou());
					//Modified by A-7794 as part of ICRD-226494
					uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_TRANSF);
					uldInFlightVOs.add(uldInFlightVO);
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO
							.getCarrierId());
					flightDetailsVO.setCarrierCode(operationalFlightVO
							.getCarrierCode());
					flightDetailsVO.setFlightNumber(operationalFlightVO
							.getFlightNumber());
					flightDetailsVO.setFlightDate(operationalFlightVO
							.getFlightDate());
					flightDetailsVO.setFlightSequenceNumber(operationalFlightVO
							.getFlightSequenceNumber());
					flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
					flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
					//Modified by A-7794 as part of ICRD-226494
					flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_TRANSF);
					new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);
				}

			}
			if (isUldIntegrationEnbled) {
				if (uldInFlightVOs.size() > 0) {
					

				}
			}
		}
		

		if (isOprUldEnabled && isUld && operationalULDs != null && operationalULDs.size() > 0) {
				new OperationsFltHandlingProxy()
						.saveOperationalULDsInFlight(operationalULDs);
		}
		log.exiting("MailTransfer", "updateTransferredContainers");
		return transferredCons;

	}


	/**
	 * @param uldForSegmentVO
	 * @param containerVO
	 * @param operationalFlightVO
	 * @return
	 */
	private ULDAtAirportVO constructULDAtAirportVO(
			ULDForSegmentVO uldForSegmentVO, ContainerVO containerVO,
			OperationalFlightVO operationalFlightVO) {
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldAtAirportVO.setUldNumber(uldForSegmentVO.getUldNumber());
		uldAtAirportVO.setAirportCode(operationalFlightVO.getPol());
		uldAtAirportVO.setCarrierId(operationalFlightVO.getCarrierId());
		uldAtAirportVO.setNumberOfBags(uldForSegmentVO.getNoOfBags());
		uldAtAirportVO.setFinalDestination(containerVO.getFinalDestination());
		uldAtAirportVO.setTotalWeight(uldForSegmentVO.getTotalWeight());
		uldAtAirportVO.setRemarks(containerVO.getRemarks());
		uldAtAirportVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		uldAtAirportVO.setTransferFromCarrier(uldForSegmentVO
				.getTransferFromCarrier());
		// TODO lastUpdatedUser uLDAtAirportVO
		return uldAtAirportVO;
	}

	/**
	 * @param uLDAtAirportVO
	 * @return
	 */
	private ULDAtAirportPK constructULDAtAirportPK(ULDAtAirportVO uLDAtAirportVO) {
		ULDAtAirportPK uLDAtAirportPK = new ULDAtAirportPK();
		uLDAtAirportPK.setAirportCode(uLDAtAirportVO.getAirportCode());
		uLDAtAirportPK.setCarrierId(uLDAtAirportVO.getCarrierId());
		uLDAtAirportPK.setCompanyCode(uLDAtAirportVO.getCompanyCode());
		uLDAtAirportPK.setUldNumber(uLDAtAirportVO.getUldNumber());
		return uLDAtAirportPK;
	}
	/**
	 * @param containerVOs
	 * @param uLDForSegmentVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void saveDestAssignednDetailsForTransfer(
			Collection<ContainerVO> containerVOs,
			Collection<ULDForSegmentVO> uLDForSegmentVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("MailTransfer", "saveDestAssignednDetailsForTransfer");
		ULDAtAirport uLDAtAirport = null;
		for (ULDForSegmentVO uLDForSegmentVO : uLDForSegmentVOs) {
			for (ContainerVO containerVO : containerVOs) {
				if (uLDForSegmentVO.getUldNumber().equals(
						containerVO.getContainerNumber())) {
					ULDAtAirportVO uLDAtAirportVO = constructULDAtAirportVO(
							uLDForSegmentVO, containerVO, operationalFlightVO);
					ULDAtAirportPK uLDAtAirportPK = constructULDAtAirportPK(uLDAtAirportVO);
					try {
						uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
						/*uLDAtAirport.setNumberOfBags(uLDAtAirport
								.getNumberOfBags()
								+ uLDForSegmentVO.getNoOfBags());
						uLDAtAirport.setTotalWeight(uLDAtAirport
								.getTotalWeight()
								+ uLDForSegmentVO.getTotalWeight());*/
						uLDAtAirport.setTransferFromCarrier(containerVO
								.getCarrierCode());
					} catch (FinderException finderException) {
						uLDAtAirport = new ULDAtAirport(uLDAtAirportVO);
					}
					uLDAtAirport
							.saveDestAssignedDetailsForTransfer(uLDForSegmentVO
									.getMailbagInULDForSegmentVOs());
				}
			}
		}
		log.exiting("MailTransfer", "saveDestAssignednDetailsForTransfer");
	}


	/**
	 * @param dSNMap
	 * @param operationalFlightVO
	 * @param toFlightSegSerNum
	 *            TODO
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> updateMailBagInULD(Map<MailbagPK, MailbagVO> mailMap,
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<ContainerVO> containerVOs)throws SystemException {

		Collection<MailbagVO> transferredMails = new ArrayList<MailbagVO>();
		try {
			for (MailbagPK mailbagPK : mailMap.keySet()) {
				Mailbag mailbag = Mailbag.find(mailbagPK);

				if ("M".equals(mailbag.getMailType()) && !(MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbag.getLatestStatus()))) {
					MailbagVO mailbagVO = updateMailbagsForTransfer(operationalFlightVO,toFlightSegSerNum,containerVOs,mailbag);
					if(mailbagVO!=null){
					transferredMails.add(mailbagVO);
					}
				}
				//Added by A-8893 for IASCB-38903 starts
				if(containerVOs.size()>0){
					for(ContainerVO containerVo:containerVOs){
						if(containerVo.isUldTobarrow()){
						if(containerVo.getContainerNumber().equals(mailbag.getUldNumber())){
							mailbag.setContainerType("B");
						}
						}
					}
				}
				//Added by A-8893 for IASCB-38903 ends
				/*DSNAuditVO dsnAuditVO = new DSNAuditVO(MODULE_NAME,
						SUBMODULE_NAME, ENTITY_NAME);*/
				/*performAuditForDespatch(dsnAuditVO, dsn, operationalFlightVO,
						dsnVO);*/
				//dsn.updateDSNArpForTransfer(dsnVO, operationalFlightVO);
				//AuditUtils.performAudit(dsnAuditVO);
			}
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");     
    	mailController.flagAuditForContainerTransfer(operationalFlightVO, toFlightSegSerNum,transferredMails);
    	mailController.flagHistoryForContainerTransfer(operationalFlightVO, toFlightSegSerNum,transferredMails);
		return transferredMails;
	}

	/**
	 * Inbound containers
	 *
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param dSNMap
	 * @return Collection<ULDForSegmentVO>
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	private Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO,Map<MailbagPK,MailbagVO>mailMap)
			throws SystemException, ULDDefaultsProxyException {
		log.entering("MailTransfer", "saveArrivalDetailsForTransfer");
		Collection<ULDForSegmentVO> allULDForSegmentVOs = new ArrayList<ULDForSegmentVO>();
		Map<AssignedFlightPK, Collection<ContainerVO>> flightContainersMap = constructFlightContainerMap(
				containerVOs, operationalFlightVO.getPol());
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		for (AssignedFlightPK inboundFlightPK : flightContainersMap.keySet()) {
			AssignedFlight inboundFlight = findOrCreateInboundFlight(inboundFlightPK);
			Collection<ULDForSegmentVO> uLDForSegmentVOs = inboundFlight
					.saveArrivalDetailsForTransfer(flightContainersMap
							.get(inboundFlightPK),operationalFlightVO,mailMap);
			if (isUldIntegrationEnbled) {
				updateULDForOperations(
						flightContainersMap.get(inboundFlightPK),
						operationalFlightVO, true);
			}
			allULDForSegmentVOs.addAll(uLDForSegmentVOs);
		}
		log.exiting("MailTransfer", "saveArrivalDetailsForTransfer");
		return allULDForSegmentVOs;
	}



	/**
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param isImport
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	private void updateULDForOperations(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, boolean isImport)
			throws SystemException, ULDDefaultsProxyException {
		log.entering("MailTransfer", "updateULDForOperations");
		if (containerVOs != null && containerVOs.size() > 0) {
			ULDInFlightVO uldInFlightVO = null;
			Collection<ULDInFlightVO> uldInFlightVOs = null;
			FlightDetailsVO flightDetailsVO = null;
			uldInFlightVOs = new ArrayList<ULDInFlightVO>();
			flightDetailsVO = new FlightDetailsVO();
			flightDetailsVO
					.setCompanyCode(operationalFlightVO.getCompanyCode());
			flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO
					.getCarrierId());
			flightDetailsVO
					.setCarrierCode(operationalFlightVO.getCarrierCode());
			flightDetailsVO.setFlightNumber(operationalFlightVO
					.getFlightNumber());
			if(operationalFlightVO.getFlightDate()!=null){
			flightDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
			}
			else{
				flightDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
			}
			flightDetailsVO.setFlightSequenceNumber(operationalFlightVO
					.getFlightSequenceNumber());
			flightDetailsVO.setDirection(isImport ? MailConstantsVO.IMPORT
					: MailConstantsVO.EXPORT);
			for (ContainerVO containerVO : containerVOs) {
				uldInFlightVO = new ULDInFlightVO();
				uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
				uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
				uldInFlightVO.setPointOfUnLading(containerVO.getPou());
				uldInFlightVO.setRemark(isImport ? MailConstantsVO.MAIL_ULD_ARRIVED :  MailConstantsVO.MAIL_ULD_ASSIGNED);
				uldInFlightVOs.add(uldInFlightVO);
			}
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
			//Added by A-7794 as part of ICRD-226494
			flightDetailsVO.setRemark(isImport ? MailConstantsVO.MAIL_ULD_ARRIVED :  MailConstantsVO.MAIL_ULD_ASSIGNED);

			flightDetailsVO.setAction(isImport ? FlightDetailsVO.ARRIVAL
					: FlightDetailsVO.ACCEPTANCE);
			new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);

		}
		log.exiting("MailTransfer", "updateULDForOperations");
	}

	/**
	 * @param inboundFlightPK
	 * @return
	 * @throws SystemException
	 */
	private AssignedFlight findOrCreateInboundFlight(
			AssignedFlightPK inboundFlightPK) throws SystemException {
		log.entering("MailTransfer", "findOrCreateInboundFlight");
		AssignedFlight inboundFlight = null;
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPK);
		} catch (FinderException exception) {
			AssignedFlightVO inboundFlightVO = validateOperationalFlight(inboundFlightPK);
			inboundFlight = new AssignedFlight(inboundFlightVO);
		}
		log.exiting("MailTransfer", "findOrCreateInboundFlight");
		return inboundFlight;
	}





	/**
	 * This a FlightProductProxyCall .This method is used to validate the
	 * FlightForAirport
	 *
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException {
		log.entering("MailTransfer", "validateFlight");
		return Proxy.getInstance().get(FlightOperationsProxy.class) .validateFlightForAirport(flightFilterVO);

	}



	/**
	 * @param inboundFlightPK
	 * @return
	 * @throws SystemException
	 */
	private AssignedFlightVO validateOperationalFlight(
			AssignedFlightPK inboundFlightPK) throws SystemException {
		log.entering("MailTransfer", "validateOperationalFlight");
		AssignedFlightVO inboundFlightVO = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(inboundFlightPK.getCompanyCode());
		flightFilterVO.setFlightCarrierId(inboundFlightPK.getCarrierId());
		flightFilterVO.setFlightNumber(inboundFlightPK.getFlightNumber());
		//Commented as part of BUg ICRD-105099 by A-5526 starts
		//flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		//flightFilterVO.setStation(inboundFlightPK.getAirportCode());
		//Commented as part of BUg ICRD-105099 by A-5526 ends
		Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
		FlightValidationVO validVO = null;
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == inboundFlightPK
						.getFlightSequenceNumber()) {
					validVO = flightValidationVO;
					break;
				}
			}
		}
		if (validVO != null) {
			inboundFlightVO = new AssignedFlightVO();
			inboundFlightVO.setCompanyCode(inboundFlightPK.getCompanyCode());
			inboundFlightVO.setCarrierId(inboundFlightPK.getCarrierId());
			inboundFlightVO.setFlightNumber(inboundFlightPK.getFlightNumber());
			inboundFlightVO.setFlightSequenceNumber(inboundFlightPK
					.getFlightSequenceNumber());
			inboundFlightVO.setLegSerialNumber(inboundFlightPK
					.getLegSerialNumber());
			inboundFlightVO.setAirportCode(inboundFlightPK.getAirportCode());
			inboundFlightVO.setCarrierCode(validVO.getCarrierCode());
			inboundFlightVO.setFlightDate(validVO
					.getApplicableDateAtRequestedAirport());
			inboundFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
			inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
			//Added by A-5945  for ICRD-118205 starts
			LogonAttributes logonVO = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
			inboundFlightVO.setLastUpdateTime(new LocalDate(logonVO.getAirportCode(),ARP,true));
			inboundFlightVO.setLastUpdateUser(logonVO.getUserId());
			//Added by A-5945 for ICRD-118205 ends
		}
		log.exiting("MailTransfer", "validateOperationalFlight");
		return inboundFlightVO;
	}

	/**
	 * This method checks whether ULD integration Enabled or not
	 *
	 * @return
	 * @throws SystemException
	 */
	private boolean isULDIntegrationEnabled() throws SystemException {
		boolean isULDIntegrationEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		HashMap<String, String> systemParameterMap = null;
		systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap
						.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
			isULDIntegrationEnabled = true;
		}
		log
				.log(Log.FINE, " isULDIntegrationEnabled :",
						isULDIntegrationEnabled);
		return isULDIntegrationEnabled;
	}

	/**
	 * A-1883
	 *
	 * @param containerVO
	 * @param airportCode
	 * @return InboundFlightPK
	 */
	private AssignedFlightPK createInboundFlightPK(ContainerVO containerVO,
			String airportCode) {
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightPK.setCarrierId(containerVO.getCarrierId());
		assignedFlightPK.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(containerVO
				.getFlightSequenceNumber());
		assignedFlightPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		assignedFlightPK.setAirportCode(airportCode);
		return assignedFlightPK;
	}

	/**
	 * This method groups containers based on the flight
	 *
	 * @param containerVOs
	 * @param airportCode
	 * @return Map<InboundFlightPK,Collection<ContainerVO>>
	 * @throws SystemException
	 */
	private Map<AssignedFlightPK, Collection<ContainerVO>> constructFlightContainerMap(
			Collection<ContainerVO> containerVOs, String airportCode)
			throws SystemException {
		log.entering("MailTransfer", "constructFlightContainerMap");
		Map<AssignedFlightPK, Collection<ContainerVO>> flightContainersMap = new HashMap<AssignedFlightPK, Collection<ContainerVO>>();
		Collection<ContainerVO> containers = null;
		for (ContainerVO containerVO : containerVOs) {
			AssignedFlightPK assignedFlightPK = createInboundFlightPK(
					containerVO, airportCode);
			containers = flightContainersMap.get(assignedFlightPK);
			if (containers == null) {
				containers = new ArrayList<ContainerVO>();
				flightContainersMap.put(assignedFlightPK, containers);
			}
			containers.add(containerVO);
		}
		log.exiting("MailTransfer", "constructFlightContainerMap");
		return flightContainersMap;
	}



	/**
	 * TODO Purpose
	 * Oct 10, 2006, a-1739
	 * @param operationalFlightVO
	 * @param toFlightSegSerNum TODO
	 * @param dsnContainers the containers containing mailbags of this DSN
	 * @return
	 * @throws SystemException
	 */
	public MailbagVO updateMailbagsForTransfer(
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<ContainerVO> containerVOs,Mailbag mailbag)
	throws SystemException {

		log.entering("DSN", "updateMailbagsForTransfer");
		MailbagVO mailbagVOToRet=null;
		for(ContainerVO  containerVO:containerVOs){
	        	if(containerVO != null &&
	        			containerVO.getContainerNumber()!=null &&
	        			containerVO.getContainerNumber().equals(mailbag.getUldNumber())) {

		        	/* Auditing */
//		        	MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
//							MailbagVO.MODULE, MailbagVO.SUBMODULE,
//							MailbagVO.ENTITY);
//		        	mailbagAuditVO.setActionCode(MailbagAuditVO.TRANSFER_ACTION);
//		        	collectTransferDetailsForAudit(mailbagAuditVO,
//		        			mailbag,operationalFlightVO);

		        	mailbag.updateULDTransferDetails(
		        			operationalFlightVO, toFlightSegSerNum);

		        	/*
	                 *
	                 * Added BY Karthick V as the part of the NCA Mail Tracking CR
	                 * After Update of the Mail Bag ,The Status Has to be chenged to the Transferred ..
	                 * If the MailBag is Delivereed ,then it cannot be Transfeereed ..
	                 * Added These Checks to ignore the MailBags which are already Delivered..
	                 *
	                 */
		        	if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getLatestStatus())||MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getLatestStatus())){  
	                	mailbagVOToRet=mailbag.retrieveVO();
	                	if(mailbagVOToRet.getUldNumber()==null){
		                	mailbagVOToRet.setUldNumber(containerVO.getContainerNumber());
		                }
	                }
		        	if(mailbagVOToRet!=null){
	                mailbagVOToRet.setCarrierCode(
	                    operationalFlightVO.getCarrierCode());
	                if(containerVO.getMailSource()!=null && mailbagVOToRet.getMailSource()==null){
	                	mailbagVOToRet.setMailSource(containerVO.getMailSource());
	                }
	                if(MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())){
	                mailbagVOToRet.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
	                }
	                
		        	}
//		        	AuditUtils.performAudit(mailbagAuditVO);
	        	}

		}
		log.exiting("DSN", "updateMailbagsForTransfer");
		return mailbagVOToRet;
	}

	/**
	 * For Outbound containers
	 *
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param uLDForSegmentVOs
	 * @return
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 */
	private int saveOutboundDetailsForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO,
			Collection<ULDForSegmentVO> uLDForSegmentVOs)
			throws SystemException, InvalidFlightSegmentException,
			CapacityBookingProxyException {
		log.entering("MailTransfer", "saveOutboundFlightForTransfer");
		validateAndCreateAssignedFlight(operationalFlightVO);
		AssignedFlightSegment assignedFlightSegment = findOrCreateAssignedFlightSegment(operationalFlightVO);
		
	    LocalDate GHTtime=new ULDForSegment().findGHTForMailbags(operationalFlightVO); //IASCB-48967    
	    containerVOs.forEach(containerVO->containerVO.setGHTtime(GHTtime));//IASCB-48967

		assignedFlightSegment.saveOutboundDetailsForTransfer(containerVOs,
				uLDForSegmentVOs);
		log.exiting("MailTransfer", "saveOutboundFlightForTransfer");
		return assignedFlightSegment.getAssignedFlightSegmentPK()
				.getSegmentSerialNumber();
	}

	/**
	 * @author a-1936 This method is used to validateflight for closed status
	 *         and if not exists create
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @return AssignedFlight
	 * @throws ContainerAssignmentException
	 */
	private AssignedFlight validateAndCreateAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("MailTransfer", "validateAndCreateAssignedFlight");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.log(Log.INFO, "FINDER EXCEPTION IS THROWN");
			assignedFlight = createAssignedFlight(operationalFlightVO);
		}
		log.exiting("MailTransfer", "validateAndCreateAssignedFlight");
		return assignedFlight;
	}


	/**
	 * find the segment serial number of the segment to which this container is
	 * assigned A-1739
	 *
	 * @param companyCode
	 * @param carrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param pol
	 * @param pou
	 * @return the segmentserialnumber for the pol-pou of the flight
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 */
	private int findFlightSegment(String companyCode, int carrierId,
			String flightNumber, long flightSequenceNumber, String pol,
			String pou) throws SystemException, InvalidFlightSegmentException {
		log.entering("MailTransfer", "findFlightSegment");
		Collection<FlightSegmentSummaryVO> flightSegments = null;

		flightSegments = new FlightOperationsProxy().findFlightSegments(
				companyCode, carrierId, flightNumber, flightSequenceNumber);

		String operationFlightSegment = new StringBuilder().append(pol).append(
				pou).toString();
		String flightSegment = null;
		int segSerialNum = 0;

		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			flightSegment = new StringBuilder().append(
					segmentSummaryVO.getSegmentOrigin()).append(
					segmentSummaryVO.getSegmentDestination()).toString();
			log.log(Log.FINEST, "from proxy -- >", flightSegment);
			log.log(Log.FINEST, "from container  -- >", operationFlightSegment);
			if (flightSegment.equals(operationFlightSegment)) {
				segSerialNum = segmentSummaryVO.getSegmentSerialNumber();
			}
		}
		if (segSerialNum == 0) {
			throw new InvalidFlightSegmentException(
					new Object[] { operationFlightSegment });
		}
		log.exiting("MailTransferd", "findFlightSegment");
		return segSerialNum;
	}

	/**
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return AssignedFlightSegment
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 */
	private AssignedFlightSegment findOrCreateAssignedFlightSegment(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			InvalidFlightSegmentException {
		log.entering("MailTransfer", "findOrCreateAssignedFlightSegment");
		AssignedFlightSegment assignedFlightSegment = null;
		int segmentSerialNumber = findFlightSegment(operationalFlightVO
				.getCompanyCode(), operationalFlightVO.getCarrierId(),
				operationalFlightVO.getFlightNumber(), operationalFlightVO
						.getFlightSequenceNumber(), operationalFlightVO
						.getPol(), operationalFlightVO.getPou());
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK
				.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightSegmentPK.setCompanyCode(operationalFlightVO
				.getCompanyCode());
		assignedFlightSegmentPK.setFlightNumber(operationalFlightVO
				.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		try {
			assignedFlightSegment = AssignedFlightSegment
					.find(assignedFlightSegmentPK);
		} catch (FinderException finderException) {
			log.log(Log.SEVERE, "Catching FinderException ");
			AssignedFlightSegmentVO assignedFlightSegmentVO = constructAssignedFlightSegmentVO(
					operationalFlightVO, segmentSerialNumber);
			assignedFlightSegment = new AssignedFlightSegment(
					assignedFlightSegmentVO);
		}
		log.exiting("MailTransfer", "findOrCreateAssignedFlightSegment");
		return assignedFlightSegment;

	}
	/**
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return AssignedFlightPK
	 */
	private AssignedFlightPK constructAssignedFlightPK(
			OperationalFlightVO operationalFlightVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO
				.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		return assignedFlightPk;
	}


	/**
	 * @param operationalFlightVO
	 * @param segmentSerialNumber
	 * @return
	 */
	private AssignedFlightSegmentVO constructAssignedFlightSegmentVO(
			OperationalFlightVO operationalFlightVO, int segmentSerialNumber) {
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		assignedFlightSegmentVO
				.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightSegmentVO.setCompanyCode(operationalFlightVO
				.getCompanyCode());
		assignedFlightSegmentVO.setFlightNumber(operationalFlightVO
				.getFlightNumber());
		assignedFlightSegmentVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightSegmentVO.setSegmentSerialNumber(segmentSerialNumber);
		assignedFlightSegmentVO.setPol(operationalFlightVO.getPol());
		assignedFlightSegmentVO.setPou(operationalFlightVO.getPou());
		return assignedFlightSegmentVO;
	}


	/**
	 *
	 * @author a-1936 This method is used to create the assignedFlight
	 * @param operationalFlightVO
	 * @return AssignedFlight
	 * @throws SystemException
	 */
	private AssignedFlight createAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("MailTransfer", "createAssignedFlight");
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(operationalFlightVO
				.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
		log.exiting("MailTransfer", "createAssignedFlight");
		return assignedFlight;
	}

	/**
	    * @author a-1936
	    * This method is used to find the Transfer Manifest for the Different Transactions
	    * @param tranferManifestFilterVo
	    * @return
	    * @throws SystemException
	    */
	   public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
	   throws SystemException{
		   return    TransferManifest.findTransferManifest(tranferManifestFilterVo);


}
	   /**
	    * 
	    * Method	:	MailTrackingDefaultsServicesEJB.transferContainersAtExport
		* Added by 	:	A-7371 on 05-Jan-2018
		* Used for 	:	ICRD-133987
	    * @param containerVOs
	    * @param operationalFlightVO
	    * @param printFlag
	    * @return
	    * @throws SystemException
	    * @throws InvalidFlightSegmentException
	    * @throws ULDDefaultsProxyException
	    * @throws CapacityBookingProxyException
	    * @throws MailBookingException
	    */
	   public Map<String, Object> transferContainersAtExport(
				Collection<ContainerVO> containerVOs,
				OperationalFlightVO operationalFlightVO,String printFlag) throws SystemException,
				InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,ContainerAssignmentException {
			log.entering("MailTransfer", "transferContainers");
			Map<String, Object> mapForReturn = new HashMap<String, Object>();
		    Map<MailbagPK, MailbagVO> mailMap = new HashMap<MailbagPK, MailbagVO>();
			Collection<ULDForSegmentVO> uLDForSegmentVOs = new ArrayList<ULDForSegmentVO>();
			// default to destn assignment
			int toFlightSegSerialNum = -1;
			if(!("-1".equals(operationalFlightVO.getFlightNumber()))){
			 uLDForSegmentVOs = saveArrivalDetailsForTransfer(
					containerVOs, operationalFlightVO,mailMap);	
			if (operationalFlightVO.getFlightSequenceNumber() > 0) {
				toFlightSegSerialNum = saveOutboundDetailsForTransfer(containerVOs,
						operationalFlightVO, uLDForSegmentVOs);
				for(ULDForSegmentVO uldForSegmentVO : uLDForSegmentVOs){
					ULDForSegmentVO uldForSegVO = new ULDForSegmentVO();
					BeanHelper.copyProperties(uldForSegVO, uldForSegmentVO);
					uldForSegVO.setPou(operationalFlightVO.getPou());
					uldForSegVO.setCarrierId(operationalFlightVO.getCarrierId());
					uldForSegVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					uldForSegVO.setFlightDate(operationalFlightVO.getFlightDate());
					uldForSegVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					new ReassignController().updateBookingForFlight(
							uldForSegVO,operationalFlightVO,"UPDATE_BOOKING_TO_FLIGHT");
				}
				for (ContainerVO containerVO : containerVOs) {
					if(MailConstantsVO.ULD_TYPE.equals(containerVO.getType())){
		            if (isULDIntegrationEnabled() && OPERATION_FLAG_INSERT.equals(containerVO
		                    .getOperationFlag())) {
					updateULDForOperations(containerVOs, operationalFlightVO, false);
				}
				}
				}
			} 
			} else {
				ContainerVO containerVo =new ContainerVO();
				if(containerVOs!= null && containerVOs.size()>0){
					containerVo= new ArrayList<ContainerVO>(containerVOs).get(0);
			}
				mailMap=saveDestDetailsForTransfer(containerVo,operationalFlightVO,mailMap);
				ULDForSegmentVO uldForSegVO = new ULDForSegmentVO();
				uldForSegVO.setPou(operationalFlightVO.getPou());
				uldForSegVO.setCarrierId(operationalFlightVO.getCarrierId());
				uldForSegVO.setUldNumber(containerVo.getContainerNumber());
				uldForSegVO.setLastUpdateUser(containerVo.getLastUpdateUser());
				uldForSegVO.setNoOfBags(containerVo.getBags());
				uLDForSegmentVOs.add(uldForSegVO);

			}
			Collection<ContainerVO> transferContainers = updateTransferredContainers(
					containerVOs, operationalFlightVO, toFlightSegSerialNum);

			Collection<MailbagVO> transferredMails = updateMailBagInULDForExportTransfer(mailMap,
					operationalFlightVO, toFlightSegSerialNum,containerVOs);
			flagResditsForContainerTransfer(transferredMails, containerVOs,
					operationalFlightVO);
			//Added by A-8527 for IASCB-34446 start
			String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
			//Added by A-8527 for IASCB-34446 Ends	
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagMLDForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
			}
			//Added by A-7794 as part of ICRD-232299
			//importMRAData
			boolean provisionalRateImport =false;
			Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(operationalFlightVO ,((ArrayList<ContainerVO>) containerVOs).get(0),transferredMails,MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport) ;
			log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
			if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
				String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
				if(importEnabled!=null && importEnabled.contains("D")){
					try {
						new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
				}
			}
			 // import Provisonal rate Data to malmraproint for upront rate Calculation
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
			String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
			if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
				provisionalRateImport = true;
	      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(operationalFlightVO ,((ArrayList<ContainerVO>) containerVOs).get(0),transferredMails,MailConstantsVO.MAIL_STATUS_TRANSFERRED,provisionalRateImport) ;
	      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
	        try {
	        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (ProxyException e) {      
					throw new SystemException(e.getMessage(), e);     
	        }
	        }
		}
			if (transferContainers != null && transferContainers.size() > 0) {
				//set the container Vos tat contains the latset container information in the map.
				mapForReturn.put(MailConstantsVO.CONST_CONTAINER,
						transferContainers);
			}

			/*
			 * Added By karthick V as the part of the Air NewZealand Cr Reports has
			 * to be Generated For all then Dsns that has been Transferred ... This
			 * method is used to collect the DSNVos from the UldforSegmentVos .. and
			 * this in turn will be used to construct the Transfer Manifest
			 * Details..
			 *
			 */
			if (uLDForSegmentVOs != null && uLDForSegmentVOs.size() > 0 && MailConstantsVO.FLAG_YES.equals(printFlag)) {
				mapForReturn.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
						createContainerDtlsForTransfermanifest(uLDForSegmentVOs,transferredMails));
			}
			
			return mapForReturn;
		}	
	   
	   private Map<MailbagPK, MailbagVO> saveDestDetailsForTransfer(
			   ContainerVO containerVo,
			OperationalFlightVO operationalFlightVO,
			Map<MailbagPK, MailbagVO> mailMap) throws SystemException{
			ULDAtAirport uldAtAirport = null;
			ULDAtAirportVO uLDAtAirportVO = constructULDAtAirportVO(containerVo);
			ULDAtAirportPK uLDAtAirportPK = constructULDAtAirportPK(uLDAtAirportVO);
			try {
				uldAtAirport = ULDAtAirport.find(uLDAtAirportPK);
			} catch (FinderException finderException) {
				uldAtAirport = new ULDAtAirport(uLDAtAirportVO);
			}
			//ULDAtAirportVO uldAtAirportVO = null;
			if (MailConstantsVO.ULD_TYPE.equals(containerVo.getType())) {
				if(uldAtAirport!=null){
					uLDAtAirportVO = uldAtAirport.retrieveVO();
					uldAtAirport.remove();
				}
				new ReassignController().updateULDAtAirportVO(uLDAtAirportVO, operationalFlightVO,containerVo);
				log.log(Log.FINEST, "retrieved VO -->", uLDAtAirportVO);
				new ULDAtAirport(uLDAtAirportVO);
			}
			if (uLDAtAirportVO!=null && uLDAtAirportVO.getMailbagInULDAtAirportVOs() != null
					&& uLDAtAirportVO.getMailbagInULDAtAirportVOs().size() > 0) {
				mailMap = constructMailbagVOsFromAirport(
						uLDAtAirportVO.getMailbagInULDAtAirportVOs(),
						operationalFlightVO.getCompanyCode(),mailMap);
				//new ReassignController().updateDSNForConReassign(mailbagVOs, operationalFlightVO,containerVo,
				//		MailConstantsVO.DESTN_FLT, null);
			}
		   return mailMap;
	   }
	   private Map<MailbagPK, MailbagVO> constructMailbagVOsFromAirport(
				Collection<MailbagInULDAtAirportVO> mailBagInUldToReassign,String companyCode,Map<MailbagPK, MailbagVO> mailMap) throws SystemException {
			log.entering("ReassignController", "getDSNVOsAtAirport");
			for (MailbagInULDAtAirportVO mailbagInULDAtAirportVO : mailBagInUldToReassign) {
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(companyCode);
				mailbagPK.setMailSequenceNumber(mailbagInULDAtAirportVO
						.getMailSequenceNumber());
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(mailbagPK);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "FinderException Caught");
				}
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(companyCode);
				mailbagVO.setMailSequenceNumber(mailbagInULDAtAirportVO
						.getMailSequenceNumber());
				if(mailbag!=null){
					mailbagVO.setMailbagId(mailbag.getMailIdr());
				}
				mailbagVO
						.setAcceptedBags(mailbagInULDAtAirportVO.getAcceptedBags());
				mailbagVO.setAcceptedWeight(mailbagInULDAtAirportVO
						.getAcceptedWgt());
				mailbagVO.setUldNumber(mailbagInULDAtAirportVO.getContainerNumber());
				mailMap.put(mailbagPK, mailbagVO);
			}
			log.exiting("ReassignController", "getDSNVOsAtAirport");
			return mailMap;
		}
	   /**
	    * 
	    * Method	:	MailTrackingDefaultsServicesEJB.updateMailBagInULDForExportTransfer
		* Added by 	:	A-7371 on 05-Jan-2018
		* Used for 	:	ICRD-133987
		* @param dSNMap
		* @param operationalFlightVO
		* @param toFlightSegSerNum
		*            TODO
		* @return Collection<MailbagVO>
		* @throws SystemException
		*/
		private Collection<MailbagVO> updateMailBagInULDForExportTransfer(Map<MailbagPK, MailbagVO> mailMap,
				OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
				Collection<ContainerVO> containerVOs)throws SystemException {

			Collection<MailbagVO> transferredMails = new ArrayList<MailbagVO>();
			try {
				for (MailbagPK mailbagPK : mailMap.keySet()) {
					Mailbag mailbag = Mailbag.find(mailbagPK);

					if ("M".equals(mailbag.getMailType())) {
						transferredMails.add(updateMailbagsForTransfer(operationalFlightVO,toFlightSegSerNum,containerVOs,mailbag));
					}
				}
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode());
			}
			
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");     
	    	mailController.flagAuditForContainerTransferAtExport(operationalFlightVO, toFlightSegSerNum,transferredMails);
	    	mailController.flagHistoryForContainerTransferAtExport(operationalFlightVO, toFlightSegSerNum,transferredMails);
			return transferredMails;
		}
		
		
		/**
		 *
		 * @param mailbagVOs
		 * @param containerVO
		 * @throws SystemException
		 * @throws InvalidFlightSegmentException
		 * @throws CapacityBookingProxyException
		 * @throws MailBookingException
		 * @throws FlightClosedException 
		 */
		public void transferMailbagsAtExport(Collection<MailbagVO> mailbagVOs,
				ContainerVO containerVO) throws SystemException,
				InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException, FlightClosedException {
			log.entering("MailTransfer", "transferMailbags");
			Collection<String> systemParamterCodes = null;
			Map<String, String> systemParamterMap = null;
		/*removeTerminatingMailbags(mailbagVOs, containerVO.getAssignedPort());
			log.log(Log.FINE, "THE MAILBAGS AFTER REMOVING TERMINATING ",
					mailbagVOs);
			Collection<MailbagVO> mailbagsToRem = checkArrivedMailbagsInTransfer(mailbagVOs);*/
			/*if (mailbagsToRem != null && mailbagsToRem.size() > 0) {*/
			Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
			Collection<MailbagVO> destAssignedMailbags = new ArrayList<MailbagVO>();
			if (mailbagVOs != null && !mailbagVOs.isEmpty() ) {
			if (new ReassignController().isReassignableMailbags(mailbagVOs,
					flightAssignedMailbags, destAssignedMailbags)) {
				if (!flightAssignedMailbags.isEmpty()) {
				  new ReassignController().reassignMailFromFlight(flightAssignedMailbags);
				}
				if (!destAssignedMailbags.isEmpty()) {
					new ReassignController().reassignMailFromDestination(destAssignedMailbags);
				}
			 }
			}
            //saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
			/*
			 * Added By Karthick V as the part of the NCA Mail Tracking Cr
			 *
			 * Method will be invoked to 1. Transfer Mails To Flight .. 2.Tranfer
			 * Mail Bags From the Inventory .. 3.Transfer Mail Bags to the Other
			 * Carriers ..
			 *
			 * In case of the (2) and (3) there is no need to save the OutBound
			 * Details .. as we are under the assumption that in CASE 2: we are
			 * marking transfer for the MailBags that are already arrived and remove
			 * those mailbags from the inventory .. No outbound Assignment happens
			 *
			 * CASE 3:In this Case we need to just mark the Transfer for all arrived
			 * /Not Already arrived Mailbags and update the Master ,History Details ..
			 * Need not Do the Outbound Process as we are under the Assumption that
			 * the MailBags are Tranferred Out From Our System ..
			 *
			 * Note:- Case 2 and Case 3 are Handled with the Container Number
			 * intentionally being null so that the Outbound Assignments can be
			 * avoided ..
			 */

			if (containerVO.getContainerNumber() != null) {
				if (containerVO.getFlightSequenceNumber() > -1) {
					saveOutboundMailsFlightForTransfer(mailbagVOs, containerVO);
				} else {
					saveOutboundMailsDestnForTransfer(mailbagVOs, containerVO);
				}
				//Added by A-8527 for IASCB-34446 start
				String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
				//Added by A-8527 for IASCB-34446 Ends	
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				mailController.flagMLDForMailbagTransfer(mailbagVOs,containerVO, null);
				}
				
			}
			Collection<MailbagVO> transferredMails = updateMailbagsForTransfer(
					mailbagVOs, containerVO);
			containerVO.setExportTransfer(true);
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
			if(mailbagVOs!=null) {
				LogonAttributes logonVO = ContextUtils.getSecurityContext()
						.getLogonAttributesVO();
				for(MailbagVO mailbagvo:mailbagVOs) {
					if(mailbagvo.getScannedUser()==null) {
						mailbagvo.setScannedUser(logonVO.getUserId());
					}
				}
			}
			mailController.flagHistoryForMailTransferAtExport(mailbagVOs, containerVO);
			mailController.flagAuditForMailTransferAtExport(mailbagVOs, containerVO);
			flagResditsForMailbagTransfer(mailbagVOs, containerVO);
			//Added by A-7794 as part of ICRD-232299
			//importMRAData
			boolean provisionalRateImport =false;
	        Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(containerVO,mailbagVOs,MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport) ;
	        if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
	        	String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		        	if(importEnabled!=null &&(importEnabled.contains("D") ||importEnabled.contains("M"))){
	        try {
				new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage(), e);
			}
			}
	        }
	        // import Provisonal rate Data to malmraproint for upront rate Calculation
			String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
			if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
				provisionalRateImport = true;
	      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(containerVO,mailbagVOs,MailConstantsVO.MAIL_STATUS_TRANSFERRED,provisionalRateImport) ; 
	      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
	        try {
	        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage(), e);
			}
			}
	        }
			
			/*
			 * Added By A-1936 to include the NCA-CR For all the MailBags tat has
			 * been Transferred the Cardit Message Has to be sent to the Transferred
			 * Carrier ..
			 */

			if (!(containerVO.getCarrierCode().equals(containerVO
					.getOwnAirlineCode()))
					&& !validateCarrierCodeFromPartner(
							containerVO.getCompanyCode(), containerVO
									.getOwnAirlineCode(), containerVO
									.getAssignedPort(), containerVO
									.getCarrierCode())) {
				systemParamterCodes = new ArrayList<String>();
				systemParamterCodes.add(MailConstantsVO.NCA_RESDIT_PROC);
				systemParamterMap = new SharedDefaultsProxy()
						.findSystemParameterByCodes(systemParamterCodes);
				if (systemParamterMap != null
						&& systemParamterMap.size() > 0
						&& systemParamterMap.get(MailConstantsVO.NCA_RESDIT_PROC)
								.equals(MailConstantsVO.FLAG_YES)) {
					flagCarditsForTransferCarrier(mailbagVOs, containerVO);
				}
			}

			/*
			 * Added By Karthick V as the part of the NCA Mail Tracking CR If a
			 * MailBag is arriveed but Not Delivered or Transferred then that Mail
			 * Bag lies in the Inventory List at that Airport..Once all the Mail
			 * Bags has been Delivered or TRansferred These mailBags has to removed
			 * From the Inventory as well.. Assume if the MailBag has been arrived
			 * Before but just now got Tranferred so Remove them from the Inventory
			 * as well ..
			 *
			 */

			log.exiting("MailTransfer", "transferMailbags");
		}
		public Map<String, Object> saveArrivalBeforeTransferOut(Collection<ContainerVO> containerVOs,OperationalFlightVO operationalFlightVO) throws ULDDefaultsProxyException, SystemException{
			Map<MailbagPK, MailbagVO> mailMap = new HashMap<MailbagPK, MailbagVO>();
			operationalFlightVO.setTransferOutOperation(true);
			Collection<ULDForSegmentVO> uLDForSegmentVOs =new ArrayList<>();
			if(!operationalFlightVO.isTransferStatus()){
					uLDForSegmentVOs=saveArrivalDetailsForTransfer(containerVOs, operationalFlightVO,mailMap);
			}
			Collection<MailbagVO> transferredMails = new ArrayList<>();
			Map<String, Object> mapForReturn = new HashMap<String, Object>();
			try {
				for (MailbagPK mailbagPK : mailMap.keySet()) {
					Mailbag mailbag = Mailbag.find(mailbagPK);
					MailbagVO mailbagVO=mailbag.retrieveVO();
					mailbagVO.setScannedPort(operationalFlightVO.getPol());
					mailbagVO.setScannedUser(operationalFlightVO.getOperator());
					mailbagVO.setUldNumber(mailbag.getUldNumber());
					if(operationalFlightVO.getOperationTime()!=null){
					mailbagVO.setScannedDate(
			        		 new LocalDate(operationalFlightVO.getPol(), Location.ARP, operationalFlightVO.getOperationTime().toCalendar(),true));
					}else{
					mailbagVO.setScannedDate(new LocalDate(operationalFlightVO.getPol(),Location.ARP,true));
					}
					if(containerVOs!=null&&!containerVOs.isEmpty()&& containerVOs.iterator().next().getMailSource()!=null){
					mailbagVO.setMailSource(containerVOs.iterator().next().getMailSource());
					}
					mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
					mailbag.updateArrivalDetails(mailbagVO);
					transferredMails.add(mailbagVO);
				}
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode());
			}
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");     
	    	mailController.flagHistoryForContainerTransfer(operationalFlightVO, -1,transferredMails);
	    	if(!operationalFlightVO.isTransferStatus()){
	    	flagResditsForContainerTransfer(transferredMails, containerVOs,
					operationalFlightVO);
	    	}
	    	if (uLDForSegmentVOs != null &&! uLDForSegmentVOs.isEmpty()) {      
	    		mapForReturn.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
						createContainerDtlsForTransfermanifest(uLDForSegmentVOs,transferredMails));
			}
	    	return mapForReturn;
		}
		
		
	


}
