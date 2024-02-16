							  
/*
 * ResditController.java Created on Aug 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.RESDIT_RECEIVED;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.cache.MailEventCache;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditFileLogVO;
import com.ibsplc.icargo.business.mail.operations.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentValidationVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingDefaultsProxy;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;


/**
 * The controller class for ALL ResditEvents.
 * All resdit flagging is through this class
 * @author A-1739
 *
 */
/*
 *  Revision History
 * ---------------------------------------------------------------
 *  Revision 			Date          Author			Description
 * ----------------------------------------------------------------
 *  0.1					Sep 1, 2006      A-1739          TODO
 *  0.2					Feb 2, 2007		A-1739			Incorporated NCACR
 */
public class ResditController {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String ACP_ULDS = "ACPULDS";
	private static final String CLASS ="ResditController";
	private static final String PA_ULDS = "PAULDS";
	private static final String CARR_ULDS = "CARRULDS";
	private static final String PA_MAILS = "PAMAILS";
	private static final String CARR_MAILS = "CARRMAILS";
	private static final String HAS_DEP = "HASDEP";
	private static final String IS_HNDOVER = "ISHNDOVR";
	private static final String IS_FLTASG = "ISFLTASG";
	private static final String NEWARR_MAILS = "NEWMAILS";
	private static final String OLIN_MAILS = "ONLINEMAILS";
	private static final String DLVD_ULDS = "DLVD_ULDS";
	private static final String DLVD_MAILS = "DLVDMAILS";
	private static final String RETN_MAILS = "RETNMAILS";
	private static final String RESDIT_TO_POST_US = "US101";
	private static final String RESDIT_EXD_AIRLINE_LIST = "mailtracking.defaults.resditcopyexcludeairlines";
	private static final String RESDIT_TO_AIRLINE_LIST = "mailtracking.defaults.resdittoairlinelist";
	private static final String RESDIT_TO_AIRLINE_AA = "AA";
	private static final String RESDIT_TO_AIRLINE_JL = "JL";
    public static final String IS_COTERMINUS_CONFIGURED="mailtracking.defaults.iscoterminusconfigured";//Added by A-7871 for ICRD-240184
    private static final String AUTOARRIVALOFFSET="mail.operations.autoarrivaloffset";
	private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";

	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
		
	/**
	 * May 23, 2007, a-1739
	 * @param mailbagVO
	 * @param txnId
	 * @throws SystemException
	 */
	public void updateResditEventTimes(MailbagVO mailbagVO,
			String txnId) throws SystemException {

		log.entering(CLASS, "updateResditEventTimes");
		if(checkForResditConfig()) {
			ResditTransactionDetailVO txnDetailVO =
				findResditConfigurationForTxn(
						mailbagVO.getCompanyCode(),
						mailbagVO.getCarrierId(),
						txnId);
			if(txnDetailVO != null) {
				if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReceivedResditFlag())) {
					updateResditTimeForEvent(
							mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				}
				if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getLoadedResditFlag())) {
					updateResditTimeForEvent(
							mailbagVO, MailConstantsVO.RESDIT_LOADED);
				}
				if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getUpliftedResditFlag())) {
					updateResditTimeForEvent(
							mailbagVO, MailConstantsVO.RESDIT_UPLIFTED);
				}
				if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getHandedOverResditFlag())) {
					updateResditTimeForEvent(
							mailbagVO, MailConstantsVO.RESDIT_HANDOVER_ONLINE);
				}
				if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getAssignedResditFlag())) {
					updateResditTimeForEvent(
							mailbagVO, MailConstantsVO.RESDIT_ASSIGNED);
				}
			}
		} else {
			//if not based on configuration, these are the possible resdits flagged..
			if(MailConstantsVO.TXN_ACP.equals(txnId)) {
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_ASSIGNED);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_HANDOVER_OFFLINE);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_UPLIFTED);
			} else if(MailConstantsVO.TXN_ARR.equals(txnId)){
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				updateResditTimeForEvent(
						mailbagVO, MailConstantsVO.RESDIT_DELIVERED);
			}
		}
		log.exiting(CLASS, "updateResditEventTimes");

	}
	/**
	 * TODO Purpose
	 * May 23, 2007, a-1739
	 * @param mailbagVO
	 * @param resdit_received
	 * @throws SystemException
	 */
	private void updateResditTimeForEvent(MailbagVO mailbagVO,
			String resditEvent) throws SystemException {
		List<MailResdit> mailResdits =
			MailResdit.findMailResditsForEvent(mailbagVO, resditEvent);
		if(mailResdits != null && mailResdits.size() > 0) {
			for(MailResdit mailResdit : mailResdits) {
				mailResdit.setEventDate(mailbagVO.getScannedDate());
				mailResdit.setUtcEventDate(
						mailbagVO.getScannedDate().toGMTDate());
			}
		}
	}
	/**
	 * TODO Purpose
	 * Feb 2, 2007, A-1739
	 * @return
	 * @throws SystemException
	 */
	private boolean checkForResditConfig() throws SystemException {
		log.entering(CLASS, "checkForResditConfig");
		Collection<String> paramNames = new ArrayList<String>();
		paramNames.add(MailConstantsVO.RESDIT_CONFIG_CHECK);
		Map<String, String> paramValMap =
			new SharedDefaultsProxy().findSystemParameterByCodes(
				paramNames);
		log.exiting(CLASS, "checkForResditConfig");
		return MailConstantsVO.FLAG_YES.equals(
				paramValMap.get(MailConstantsVO.RESDIT_CONFIG_CHECK));
	}

	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param companyCode
	 * @param carrierId
	 * @param txnId
	 * @return
	 * @throws SystemException
	 */
	private ResditTransactionDetailVO findResditConfigurationForTxn(
			String companyCode, int carrierId, String txnId)
	throws SystemException {
		log.entering(CLASS, "findResditConfigurationForTxn");
		return ResditConfiguration.findResditConfigurationForTxn(companyCode,
				carrierId, txnId);
	}


	/**
	 * @author A-1739
	 * This methodis used to flagResditsForAcceptance
	 * @param mailAcceptanceVO
	 * @param hasFlightDeparted
	 * @param acceptedMailbags
	 * @param acceptedUlds
	 * @throws SystemException
	 */
	public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			boolean hasFlightDeparted, Collection<MailbagVO> acceptedMailbags,
			Collection<ContainerDetailsVO> acceptedUlds) throws SystemException {
		log.entering(CLASS, "flagResditsForAcceptance");

		String companyCode = mailAcceptanceVO.getCompanyCode();
		String ownAirlineCode = mailAcceptanceVO.getOwnAirlineCode();
		String pol = mailAcceptanceVO.getPol();
		String carrierCode = mailAcceptanceVO.getFlightCarrierCode();
		/** Changes for CR QF1410 on 15Dec2009 */
		//boolean isHandover = false;
		boolean isHandoverOnline = false;
		boolean isHandoverOffline = false;
		boolean isFlightAssign = false;
		boolean isFromAssign=false;
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);//Added by a-7871 for ICRD-240184
		String paCode = null;
		boolean isCoterminus;
		String orgAirport=null;
		OfficeOfExchangeVO originOfficeOfExchangeVO;
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO:acceptedMailbags){
			if("Y".equals(mailAcceptanceVO.getIsFromTruck())){ 
				mailbagVO.setIsFromTruck("Y");
			}
			mailbagVO.setFromDeviationList(mailAcceptanceVO.isFromDeviationList());
			
            if(!mailAcceptanceVO.isAssignedToFlight()){
                      mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);	
                      mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
                      mailbagVO.setFlightDate(null);
                      mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
                      mailAcceptanceVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
                      mailAcceptanceVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
                      hasFlightDeparted=false;
               }
               else if(mailAcceptanceVO.isAssignedToFlight()){
            	   isFromAssign=true;
               }
			
			
		}
		// Commented as part of bug 58098 since for other airlines also handeover should not be send as
		// part acceptance operation
//		if (!(mailAcceptanceVO.getFlightCarrierCode().equals(
//				mailAcceptanceVO.getOwnAirlineCode()))
//				&& !validateCarrierCodeFromPartner(companyCode, ownAirlineCode,
//						pol, carrierCode)) {
//			isHandover = true;
//		}
		if (mailAcceptanceVO.getFlightSequenceNumber() !=
			MailConstantsVO.DESTN_FLT) {
			isFlightAssign = true;
		}
		 Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
		 Collection<MailbagVO> handoverRcvdMailbags = new ArrayList<MailbagVO>();
		 Collection<MailbagVO> handoverOnMailbags = new ArrayList<MailbagVO>();
		 Collection<MailbagVO> handoverOffMailbags = new ArrayList<MailbagVO>();
		 if(acceptedMailbags != null && acceptedMailbags.size() > 0) {
			 groupPACarrMailbags(acceptedMailbags,paMailbags,handoverRcvdMailbags,handoverOnMailbags,
					 handoverOffMailbags, mailAcceptanceVO.getOwnAirlineCode(), mailAcceptanceVO.getFlightCarrierCode());
		 }

		 Collection<ContainerDetailsVO> paContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		 Collection<ContainerDetailsVO> handoverRcvdContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		 Collection<ContainerDetailsVO> handoverOnContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		 Collection<ContainerDetailsVO> handoverOffContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		 if(acceptedUlds != null && acceptedUlds.size() > 0) {
			 groupPACarrULDs(acceptedUlds,paContainerDetailsVOs,
					 handoverRcvdContainerDetailsVOs,handoverOnContainerDetailsVOs,
					 handoverOffContainerDetailsVOs, mailAcceptanceVO.getOwnAirlineCode(), mailAcceptanceVO.getFlightCarrierCode());
		 }

		 if((handoverOnMailbags != null && handoverOnMailbags.size() > 0)
			||(handoverOnContainerDetailsVOs != null && handoverOnContainerDetailsVOs.size() > 0)) {
			 isHandoverOnline = true;
		 }

		 if((handoverOffMailbags != null && handoverOffMailbags.size() > 0)
		   ||(handoverOffContainerDetailsVOs != null && handoverOffContainerDetailsVOs.size() > 0)) {
			 isHandoverOffline = true;
		 }

		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			valuesMap.put(ACP_ULDS, acceptedUlds);
			valuesMap.put(PA_ULDS, paContainerDetailsVOs);
			valuesMap.put(CARR_ULDS, handoverRcvdMailbags);
			valuesMap.put(PA_MAILS, paMailbags);
			valuesMap.put(CARR_MAILS, handoverRcvdContainerDetailsVOs);
			valuesMap.put(IS_FLTASG, isFlightAssign);
			//if(!isHandover) {
			valuesMap.put(HAS_DEP, hasFlightDeparted);
			//}
			//CR QF1410 is specific and checkForResditConfig is not required for QF
			// CR QF1410 is not tested with  checkForResditConfig
			flagConfiguredResdits(companyCode, mailAcceptanceVO.getCarrierId(),
							MailConstantsVO.TXN_ACP, mailAcceptanceVO.getPol(),
							acceptedMailbags, valuesMap);

			// CR QF1410 is specific and checkForResditConfig is not required for QF
			// isHandoverOnline is not handled here
			if(isHandoverOffline) {
				valuesMap.put(IS_HNDOVER, isHandoverOffline);
				flagConfiguredHandoverResdit(companyCode,
						mailAcceptanceVO.getOwnAirlineId(),
						MailConstantsVO.TXN_ACP, mailAcceptanceVO.getPol(),
						acceptedMailbags, valuesMap);
			}
		} else {
			if(!isFromAssign){
			flagHandedoverReceivedForMailbags(handoverRcvdMailbags, pol);
			if(!paMailbags.isEmpty()){
			//Added by A-7871 for ICRD-240184 starts
			paCode = new MailController().findPAForOfficeOfExchange(paMailbags.iterator().next().getCompanyCode(),  paMailbags.iterator().next().getOoe());
			originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(paMailbags.iterator().next().getCompanyCode(), paMailbags.iterator().next().getOoe());
			String orginAirport=null;
			if(paMailbags.iterator().next().getPol()!=null && !paMailbags.iterator().next().getPol().isEmpty()){
				orginAirport=paMailbags.iterator().next().getPol();
			}else if(paMailbags.iterator().next().getOrigin()!=null && !paMailbags.iterator().next().getOrigin().isEmpty()){
				orginAirport=paMailbags.iterator().next().getOrigin();
			}else if(mailAcceptanceVO!=null){
				orginAirport=mailAcceptanceVO.getPol();
			}

			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			if(MailConstantsVO.MLD.equals(mailAcceptanceVO.getMailSource()) || MailConstantsVO.ONLOAD_MESSAGE.equals(mailAcceptanceVO.getMailSource())){
			orgAirport=	mailAcceptanceVO.getPol();
			}else{
			orgAirport =  logonAttributes.getStationCode(); 
			}
			
			Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(paMailbags.iterator().next().getCompanyCode());
			mailbagPk.setMailSequenceNumber(paMailbags.iterator().next().getMailSequenceNumber()>0?
					paMailbags.iterator().next().getMailSequenceNumber():findMailSequenceNumber(paMailbags.iterator().next().getMailbagId(), paMailbags.iterator().next().getCompanyCode())  );
			try {
				mailbagToFindPA = Mailbag.find(mailbagPk);
			} catch (FinderException e) {							
				e.getMessage();
			}
			String orginAirportOfMailbag=null;
			if(mailbagToFindPA.getOrigin()!=null){
				orginAirportOfMailbag=mailbagToFindPA.getOrigin();
			}else{
				if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
					String originOfficeOfExchange = originOfficeOfExchangeVO.getCode();
					orginAirportOfMailbag = new MailController().findNearestAirportOfCity(paMailbags.iterator().next().getCompanyCode(), originOfficeOfExchange);
				} else {
					orginAirportOfMailbag = originOfficeOfExchangeVO != null ? originOfficeOfExchangeVO.getAirportCode() : null;
				}
			}
			if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
				paCode=mailbagToFindPA.getPaCode();
			}//Added by A-8164 for ICRD-342541 starts
			LocalDate dspDate = new LocalDate(orgAirport, Location.ARP, true);
			
			if (mailbagToFindPA != null) {
				dspDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbagToFindPA.getDespatchDate(), false);
			}
			isCoterminus=	new MailController().validateCoterminusairports(orginAirportOfMailbag,orgAirport,MailConstantsVO.RESDIT_RECEIVED,paCode,dspDate) ||
					new MailController().validateCoterminusairports(orginAirportOfMailbag,orgAirport,MailConstantsVO.RESDIT_UPLIFTED,paCode,dspDate);
			if(isCoterminusConfigured!=null && "Y".equals(isCoterminusConfigured))
			{
				//Modified by A-7540
				if(orgAirport.equalsIgnoreCase(orginAirportOfMailbag) || new MailController().checkReceivedFromTruckEnabled(orginAirport,orginAirportOfMailbag,paCode,dspDate)||isCoterminus){// modified by A-8353 for ICRD-336294
			flagReceivedResditForMailbags(paMailbags, pol);
				}}else{
				flagReceivedResditForMailbags(paMailbags, pol);
			}
			//Added by A-7871 for ICRD-240184 ends
		}
			flagReceivedResditForUlds(acceptedUlds, pol);
			flagHandedoverReceivedForULDs(handoverRcvdContainerDetailsVOs, pol);
			
			
		}
			// flagAssignedResdit
			if (isFlightAssign) {
				flagAssignedResditForMailbags(acceptedMailbags, pol);
				flagAssignedResditForUlds(acceptedUlds, pol);
				//added for ICRD-82147
				flagLoadedResditForMailbags(acceptedMailbags, pol);
				flagLoadedResditForUlds(acceptedUlds, pol);
			}
			// flagHandedOverOfflineResdit
			if (isHandoverOffline) {
				flagHandedOverResditForMailbags(handoverOffMailbags, pol);
				flagHandedOverResditForUlds(handoverOffContainerDetailsVOs, pol);
			}

			//flagHandedOveronlineResdit
			if (isHandoverOnline) {
				flagOnlineHandedoverResditForMailbags(handoverOnMailbags, pol);
				flagOnlineHandedoverResditForUlds(handoverOnContainerDetailsVOs, pol);
			}

			//else {
			//flagUpliftedResdit only if own carrier
			
			if (hasFlightDeparted) {
				flagUpliftedResditForMailbags(acceptedMailbags, pol);
				flagUpliftedResditForUlds(acceptedUlds, pol);
			}
			
		}
		log.exiting(CLASS, "flagResditsForAcceptance");
	}
	/**
	 * This method is used to flagUpliftedResditForUlds.
	 *
	 * @author A-1936
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	private void flagUpliftedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.entering("ResditControler", "flagUpliftedResditForUlds");

		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailVo : acceptedUlds) {
				UldResditVO resditVO = constructUldResditVO(containerDetailVo, pol,
						MailConstantsVO.RESDIT_UPLIFTED);
				resditVO.setPaOrCarrierCode(containerDetailVo.getCarrierCode());
                if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_UPLIFTED, resditVO)){
                	 new UldResdit(resditVO);
                }
			}
		}

		log.exiting("ResditControler", "flagUpliftedResditForUlds");
	}


	/**
	 * If ULDResdit already sent then no need to flag again.
	 * but need to flag if not sent or in N status
	 * Jan 22, 2007, a-1876
	 * @param eventCode
	 * @param uldResditVO
	 * @throws SystemException
	 */
	private boolean canFlagResditForULDEvent(String eventCode,UldResditVO uldResditVO) throws SystemException {
 	      log.entering(CLASS, "canFlagResditFor Event");

 	       boolean canFlag = false;
	       uldResditVO.setEventCode(eventCode);

	       if(MailConstantsVO.RESDIT_ASSIGNED .equals(uldResditVO.getEventCode())
				    || MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(uldResditVO.getEventCode())
				      || MailConstantsVO.RESDIT_UPLIFTED.equals(uldResditVO.getEventCode())
				    || MailConstantsVO.RESDIT_LOADED.equals(uldResditVO.getEventCode())){

	    	   Collection<UldResditVO> uldResditVOs =  UldResdit.findULDResditStatus(uldResditVO);
			   if(uldResditVOs != null && uldResditVOs.size() > 0){
				   for( UldResditVO uldResditStatusVO : uldResditVOs) {
					   if(MailConstantsVO.FLAG_NO.equals(uldResditStatusVO.getResditSentFlag())){
						   //make sure no entry in timer
						   if(!MailConstantsVO.FLAG_YES.equals(uldResditStatusVO.getProcessedStatus())) {
							   List<UldResdit> uldResdits =  UldResdit.findUldResdit(uldResditVO,eventCode);
							   if(uldResdits != null && uldResdits.size() > 0){
								   UldResdit uldResdit = uldResdits.get(0);
								   uldResdit.remove();
								   /*
								    * Flagging should be allowed only when
								    * RDTSND="N" && PROSTA !="Y" in MTKULDRDT
								    */
								   canFlag = true;
							   }
						   }
					   }
		   }
			   } else {
				   canFlag = true;
			   }
		   } else {
			   //all other events
			   canFlag = true;
		   }
        return canFlag;


	}

	/**
	 * @author A-1936 This method is used to construct the UldResditVo from the
	 *         ContainerDetailsVo
	 * @param containerDetailsVO
	 * @param eventAirport
	 * @param eventCode
	 * @return
	 */
	private UldResditVO constructUldResditVO(
			ContainerDetailsVO containerDetailsVO, String eventAirport,
			String eventCode) {
		log.entering(CLASS, "getMailResditVO");
		UldResditVO uldResditVO = new UldResditVO();
		uldResditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldResditVO.setEventAirport(eventAirport);
		uldResditVO.setEventCode(eventCode);
		uldResditVO.setCarrierId(containerDetailsVO.getCarrierId());
		if (!MailConstantsVO.RESDIT_PENDING.equals(eventCode)) {
			uldResditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			uldResditVO.setFlightSequenceNumber(containerDetailsVO
					.getFlightSequenceNumber());
			uldResditVO.setSegmentSerialNumber(containerDetailsVO
					.getSegmentSerialNumber());
		}
		uldResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		uldResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		uldResditVO.setUldNumber(containerDetailsVO.getContainerNumber());
		if(MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)){
			uldResditVO.setEventDate(findResditEvtDateForULD(containerDetailsVO,eventAirport));
		}
		else{
		uldResditVO
				.setEventDate(new LocalDate(eventAirport, Location.ARP, true));
		}
		uldResditVO.setContainerJourneyId(containerDetailsVO.getContainerJnyId());
		uldResditVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
		log.exiting(CLASS, "constructUldResditVO");
		return uldResditVO;
	}

	/**
	 * @author A-1739
	 * This method is used to flagUpliftedResditForMailbags
	 *
	 * @param mailbags
	 * @param pol
	 * @throws SystemException
	 */
	public void flagUpliftedResditForMailbags(Collection<MailbagVO> mailbags,
			String pol) throws SystemException {
		log.entering(CLASS, "flagUpliftedResdit");
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		if (mailbags != null && mailbags.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

			for (MailbagVO mailbagVO : mailbags) {
                MailResditVO mailResditVO = constructMailResditVO(mailbagVO, pol,
							MailConstantsVO.RESDIT_UPLIFTED, false);
                mailResditVOs.add(mailResditVO);
//                if(canFlagResditForEvent(MailConstantsVO.RESDIT_UPLIFTED,
//						mailResditVO, mailbagVO)) {
//					new MailResdit(mailResditVO);
//				}
			}
			
			mailResditVOs = canFlagResditForEvents(
					MailConstantsVO.RESDIT_UPLIFTED, mailResditVOs, mailbags);
			stampResdits(mailResditVOs);
			//Added by A-8893 for IASCB-49931 starts
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			for (MailbagVO mailbagVO : mailbags) {
				if(mailbagVO.getLastUpdateUser()==null) {
					mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
				}
				Collection<FlightValidationVO> flightVOs=null;
				FlightFilterVO	flightFilterVO=new FlightFilterVO();      
				flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);  
				flightFilterVO.setPageNumber(1);      
				flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
				flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				flightFilterVO.setStation(pol);
					flightVOs=new MailController().validateFlight(flightFilterVO);
					if(flightVOs!=null && !flightVOs.isEmpty() &&  mailbagVO!=null){
					mailController.flagHistoryforFlightDeparture(mailbagVO,flightVOs);
					mailController.flagAuditforFlightDeparture(mailbagVO,flightVOs);
				}
			}
					
					//Added by A-8893 for IASCB-49931 ends
			for(MailbagVO mailbagVO: mailbags) {
				if(new MailController().isUSPSMailbag(mailbagVO)){
					uspsMailbags.add(mailbagVO);
				}
			}
			if(uspsMailbags.size()>0) {
				Collection<RateAuditDetailsVO> rateAuditVOs =new MailController().createRateAuditVOsFromMailbag(uspsMailbags);
			  try {
					new MailOperationsMRAProxy().recalculateDisincentiveData(rateAuditVOs);
				} catch (ProxyException e) {     
					throw new SystemException(e.getMessage(), e);     
				}
			}
					
		}
		log.entering(CLASS, "flagUpliftedResdit");
	}


	/**
	 * @author SAP15
	 * @param mailResditVOs
	 * @throws SystemException
	 */
	private void stampResdits (Collection<MailResditVO> mailResditVOs)
	throws SystemException{

		if (mailResditVOs == null || mailResditVOs.size() <= 0)
			{
			return;
			}
		String preCheckEnabled = findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED);
		for (MailResditVO mailResditVO : mailResditVOs){
			if (MailConstantsVO.FLAG_YES.equals(preCheckEnabled) && !canStampResdits(mailResditVO)) {
				continue;
			}
			new MailResdit (mailResditVO);
		}
	}

	/**
	 * @author A-5991
	 * @param carrContainerDetailsVOs
	 * @param eventPort
	 * @throws SystemException
	 */
	private void flagOnlineHandedoverResditForUlds(
			Collection<ContainerDetailsVO> carrContainerDetailsVOs, String eventPort)
				throws SystemException {
		MailResditVO mailResditVO=null;
		for (ContainerDetailsVO containerDetailsVO : carrContainerDetailsVOs) {
			UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_ONLINE);
			// For MTK558
			//if("I".equals(containerDetailsVO.getOperationFlag())){
				if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_ONLINE, uldResditVO)){
					new UldResdit(uldResditVO);
			    }
			//}
	    }
	}


	/**
	 * @author a-1936
	 * Added By Karthick V as the part of the Mail Tracking CR ..
	 * @param eventCode
	 * @param mailResditVO
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	/*
	 * The Resdits has to be Flagged for the Following Events satisfying the following Condtions are  :-
	 *
	 *  Received 1.mailtag,Port and the Event Code + Dependant Event Code..
     *  Assigned 1.mailtag,2.flightid,3.port, 4.outbound 5.eventCode
     *  uplifted/load 1.mailtag,2.flightid,3.port,4.outbound 5.eventCode
     *  pending  1.mailtag,2.port ,3.eventCode
     *  returned 1.mailtag 2.port 3.eventCode
     *  delivered 1.mailtag 2.port 3.eventCode
     *  handover off 1.mailtag 2.port 3.eventCode 4.outbound
     *  handover onl 1.mailtag,2.flightid,3.port, 4.outbound 5.eventCode
     *  handover rcv  1.mailtag 2.port 3.eventCode 4.outbound
	 *  Note:
	 *   When the event is  RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   When the event is  HANDED OVER RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   The Method checks wether the Resdit is already flagged for the Particular Event ..If the
	 *   DependantEventCode in MailResditVo is also being  present then  this method checks wether there is any Resdit
	 *   flagged for either the Resdit Event or the Dependant Event and Returns True if flagged for either of these or all
	 *   Else
	 *   False
	 *
	 *   The Method fetches the Entity corresponding to the
	 *   MailTag ,Event Code and the Port for all these Events .
	 *   Then the Event Specific Checks are Done .
	 *
	 *   If No Results From Query we can Flag the Resdit
	 *
	 *   If the Returned Resdit Mailidr and its Condition are same as the One Queried then Return False..
	 *   Else
	 *   If the Event Specific Conditions are Different and the RDTSND Flag is 'N' then remove that and can Resdit send is True.
	 *   else if RDTSND Flag is 'Y' set the Can Flag Resdit Flag tio True
	 *
	 */
/*/**************Modified by JOji at TRV on 03-Jun-08 to avoid object queries without updates
*********************//////////////
	private Collection<MailResditVO> canFlagResditForEvents(String eventCode,Collection<MailResditVO> mailResditVOs,
			Collection<MailbagVO> mailbagVOs) throws SystemException {

		log.entering(CLASS, "can FlagResditForEvent");

		if (mailResditVOs == null || mailResditVOs.size() <= 0)
			{
			return null;
			}

		boolean canFlag = false;
		/*
		 * Sets the Event-Code to the Mail-Resdit Vo..
		 */

		
		HashMap<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs){
			mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
			for(MailResditVO resditVO : mailResditVOs){
				if(resditVO.getMailId()!=null && resditVO.getMailId().equals(mailbagVO.getMailbagId()))
					resditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber()>0? mailbagVO.getMailSequenceNumber():Mailbag.findMailBagSequenceNumberFromMailIdr(resditVO.getMailId(), resditVO.getCompanyCode()));
			}
		}

		Collection<MailResditVO> mailResditVOsForReturn = new ArrayList<MailResditVO>();
		Collection<MailResditVO> mailResditVOsFirst = new ArrayList<MailResditVO>();
		Collection<MailResditVO> mailResditVOsSecond = new ArrayList<MailResditVO>();

		MailbagVO mailbagVO = null;
		for (MailResditVO mailResditVO : mailResditVOs){

			mailResditVO.setEventCode(eventCode);
			if(MailConstantsVO.RESDIT_ASSIGNED.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(mailResditVO.getEventCode())){

				mailbagVO = mailbagVOMap.get(mailResditVO.getMailId());
				if (mailbagVO != null){
					if(MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())
						&& MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {

						mailResditVOsForReturn.add(mailResditVO);

					}else{
						mailResditVOsFirst.add(mailResditVO);
					}
				}
			}else{
				mailResditVOsSecond.add(mailResditVO);
			}
		}

		Collection<MailResditVO> fetchedMailResditVOs = null;
		StringBuilder keyBuilder 	= null;

		if (mailResditVOsFirst != null && mailResditVOsFirst.size()>0){

			HashMap<String,Collection<MailResditVO>> eventResditsMap =
				MailResdit.findResditFlightDetailsForMailbagEvents(mailResditVOsFirst);

			for(MailResditVO malResditVO : mailResditVOsFirst) {

				keyBuilder = new StringBuilder(malResditVO.getMailId())
					.append(malResditVO.getEventAirport()).append(malResditVO.getEventCode());
				fetchedMailResditVOs = eventResditsMap.get(keyBuilder.toString());

				if (fetchedMailResditVOs == null || fetchedMailResditVOs.size() <= 0){
					mailResditVOsForReturn.add (malResditVO);
				} else {

					canFlag = true;
					for (MailResditVO fetchedMailResditVO : fetchedMailResditVOs) {
						if (fetchedMailResditVO.getFlightNumber() != null
								&& fetchedMailResditVO.getFlightNumber().equals(malResditVO.getFlightNumber())
								&& fetchedMailResditVO.getCarrierId() == malResditVO.getCarrierId()
								&& fetchedMailResditVO.getFlightSequenceNumber() == malResditVO.getFlightSequenceNumber()
								&& fetchedMailResditVO.getSegmentSerialNumber() == malResditVO.getSegmentSerialNumber() ) {

							if((MailConstantsVO.RESDIT_UPLIFTED.equals(malResditVO.getEventCode())||MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(malResditVO.getEventCode())) && 
									MailConstantsVO.FLAG_NO.equals(fetchedMailResditVO.getResditSentFlag())
									&& MailConstantsVO.FLAG_YES.equals(fetchedMailResditVO.getProcessedStatus())){
								
								MailResditPK  mailResditPK = getMailResditPK (fetchedMailResditVO);
								MailResdit mailResdit;
								try {
									mailResdit = MailResdit.find(mailResditPK);
									mailResdit.remove();
								} catch (FinderException e) {
									log.log(Log.INFO, " error msg \n\n ", e.getMessage());
							}
								
							}else{
							
							canFlag = false;
							break;
							}
						}
						if (MailConstantsVO.FLAG_NO.equals(fetchedMailResditVO.getResditSentFlag())
								&& !MailConstantsVO.FLAG_YES.equals(fetchedMailResditVO.getProcessedStatus())){

								MailResditPK  mailResditPK = getMailResditPK (fetchedMailResditVO);
								MailResdit mailResdit;
								try {
									mailResdit = MailResdit.find(mailResditPK);
									mailResdit.remove();
								} catch (FinderException e) {
									// TODO Auto-generated catch block

									log.log(Log.INFO, " error msg \n\n ", e.getMessage());
							}
						}
					}

					if (canFlag)
						{
						mailResditVOsForReturn.add (malResditVO);
						}
				}
			}
		}

		if (mailResditVOsSecond != null && mailResditVOsSecond.size()>0){

			for(MailResditVO malResditVO : mailResditVOsSecond) {

				boolean isResditExisting = MailResdit.checkResditExists(malResditVO, false);
				if(MailConstantsVO.RESDIT_RECEIVED.equals(malResditVO.getEventCode())){
					log.log(Log.FINE,"THE STATUS IS RECEIVED");

					//for bug 88789. 74 need to be sent only from orgin port.
					// chnaged as part of hotfix, 74 will be stamped , rest all validation to be done on procedure.
					if(!isResditExisting) {
						mailResditVOsForReturn.add (malResditVO);
					}

				} else {
					if(MailConstantsVO.RESDIT_LOADED.equals(malResditVO.getEventCode())){
						boolean isResditExistingFromReassign = MailResdit.checkResditExistsFromReassign(malResditVO, false);

						if(!isResditExistingFromReassign){
							mailResditVOsForReturn.add (malResditVO);
						//}else if(isResditExisting) {

						}else{

						 mailResditVOsForReturn.add (malResditVO);

						}
					}else{
						if (!isResditExisting) {
						 mailResditVOsForReturn.add (malResditVO);
						}
					}
				}
			}
		}
		log.exiting(CLASS, "canFlag ResditForEvent"+ canFlag);
		return mailResditVOsForReturn;

	}


	/**
	 * A-2521
	 *
	 * @param mailResditVO
	 * @throws SystemException
	 */
	private MailResditPK getMailResditPK (MailResditVO mailResditVO) throws SystemException {

		log.entering("MailResdit", "populatePK");
		log.log(Log.FINE, "THE MAILRESDIT VO>>>>>>>>>>" + mailResditVO);
		MailResditPK mailResditPK = new MailResditPK();
		mailResditPK.setCompanyCode(   mailResditVO.getCompanyCode());
		//mailResditPK.setMailId(   mailResditVO.getMailId());
		mailResditPK.setMailSequenceNumber(mailResditVO.getMailSequenceNumber()>0 ?
				mailResditVO.getMailSequenceNumber():findMailSequenceNumber(mailResditVO.getMailId(), mailResditVO.getCompanyCode()) );
		mailResditPK.setEventCode(   mailResditVO.getEventCode());
		mailResditPK.setSequenceNumber (mailResditVO.getResditSequenceNum());
		log.exiting("MailResdit", "populatepK");

		return mailResditPK;

	}

	/**
	 * A-1739
	 *
	 * @param mailbagVO
	 * @param eventAirport
	 * @param eventCode
	 * @param isResditSent
	 * @param dsnVO
	 * @return
	 * @throws SystemException
	 */
	private MailResditVO constructMailResditVO(MailbagVO mailbagVO,
			String eventAirport, String eventCode, boolean isResditSent) throws SystemException {
		log.entering(CLASS, "constructMail ResditVO");
		//Added for CRQ ICRD-111886 by A-5526 starts
String partyIdentifier;

		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		//Added for CRQ ICRD-111886 by A-5526 ends
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setFromDeviationList(mailbagVO.isFromDeviationList());
		if (eventAirport != null) {
			mailResditVO.setEventAirport(eventAirport);
		}
		mailResditVO.setEventCode(eventCode);
			mailResditVO.setCarrierId(mailbagVO.getCarrierId());
			mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
			mailResditVO.setFlightSequenceNumber(mailbagVO
					.getFlightSequenceNumber());

			mailResditVO.setSegmentSerialNumber(mailbagVO
					.getSegmentSerialNumber());

			//temporary fix for bug ICRD-160796 by A-5526 as issue is not getting replicated starts
			 if(mailResditVO.getSegmentSerialNumber()==0 && MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)){
					mailResditVO.setSegmentSerialNumber(1);
				}
			//temporary fix for bug ICRD-160796 by A-5526 as issue is not getting replicated starts.Communicated with Santhi K
			mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());

		if (isResditSent) {
			mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_YES);
		} else {
			mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		}
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getUldNumber());

		/*
		 * Added By Karthick V ..
		 * A kind of the Work Around.. Needed to remove
		 *
		 */
		//		mailResditVO.setEventDate(mailbagVO.getScannedDate()!=null?mailbagVO.getScannedDate():new LocalDate(
		//				eventAirport,Location.ARP,true));

		/*
		 * commented as part of BUG 49994 event date must be the date +time when each event happens for a mail bag not the date+time when it was scanned
		 */
		/*
		 * reverting as per BUG 66218
		 */
		log.log(Log.FINE, "Resdit event date construction..mailbagVO.getScannedDate()"+mailbagVO.getScannedDate());
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)||MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(eventCode)) {
				if(MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)){
					mailResditVO.setEventDate(findResditEvtDate(mailbagVO,eventAirport));
				}else{
		    mailResditVO.setEventDate(mailbagVO.getResditEventDate()!=null?mailbagVO.getResditEventDate():new LocalDate(eventAirport,Location.ARP,true));//Added by A-4213 for bug 105903
				}
		}else if (MailConstantsVO.RESDIT_ARRIVED.equals(eventCode)||MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode)) {
			 mailResditVO.setEventDate(mailbagVO.getResditEventDate()!=null?mailbagVO.getResditEventDate():mailbagVO.getScannedDate()!=null?mailbagVO.getScannedDate():new LocalDate(eventAirport,Location.ARP,true));
		}
				  
				else{
			mailResditVO.setEventDate(mailbagVO.getScannedDate()!=null?mailbagVO.getScannedDate():new LocalDate(eventAirport,Location.ARP,true));
		}
		
		String mailboxIdFromConfig = MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);
		if (mailboxIdFromConfig!=null && !mailboxIdFromConfig.isEmpty() ) {
			mailResditVO.setMailboxID(mailboxIdFromConfig);
		} 

		
		//Added for CRQ ICRD-111886 by A-5526 starts
if(mailbagVO.getMailbagId()!=null && mailbagVO.getMailbagId().length()==29){
			String paCode="";
			
			//Added for ICRD-318999 by A-8923 starts
			
			//Added for ICRD-318999 by A-8923 ends
			
			exchangeOfficeDetails = new MailController().findOfficeOfExchange(
						mailbagVO.getCompanyCode(), mailbagVO.getMailbagId().substring(0, 6),1);
			if(exchangeOfficeDetails!=null && !exchangeOfficeDetails.isEmpty()){
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
//Edited for ICRD-318999 by A-8923 starts
			   
//Edited for ICRD-318999 by A-8923 ends
				Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
				String poaCode=null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ?
						mailbagVO.getMailSequenceNumber(): findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()) );
				try {
					mailbagToFindPA = Mailbag.find(mailbagPk);
				} catch (FinderException e) {							
					e.getMessage();
				}
				if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
					poaCode=mailbagToFindPA.getPaCode();
				}
				else{
					poaCode=officeOfExchangeVO.getPoaCode(); 
				} //Added by A-8164 for ICRD-342541 ends
				
				//Co Terminus Check Start
				if(officeOfExchangeVO.getAirportCode()!=null&&(!officeOfExchangeVO.getAirportCode().equals(eventAirport))){
					log.log(Log.FINE, "OOE Airport Code: "+officeOfExchangeVO.getAirportCode());
					LocalDate dspDate = new LocalDate(eventAirport, Location.ARP, true);

					if (mailbagToFindPA != null) {
						dspDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbagToFindPA.getDespatchDate(), false);
					}
					boolean coTerminusCheck= new MailController().validateCoterminusairports(eventAirport,officeOfExchangeVO.getAirportCode(),eventCode,poaCode,dspDate);
					log.log(Log.FINE, "coTerminusCheck: "+coTerminusCheck);
					log.log(Log.FINE, "eventAirport bfre coTerminus Check: "+eventAirport);
					if(coTerminusCheck && new MailController().checkReceivedFromTruckEnabled(eventAirport,officeOfExchangeVO.getAirportCode(),paCode,dspDate)
							&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())){
						eventAirport=officeOfExchangeVO.getAirportCode();
					}
					log.log(Log.FINE, "eventAirport after coTerminus Check: "+eventAirport);
					mailResditVO.setEventAirport(eventAirport);
				}
				//Co Terminus Check End
				
			}
			//Changed as part of bug ICRD-157758 by A-5526 starts
			if(eventCode==null || (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode) ||MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode) ) )
			{
			 paCode = new MailController().findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(6, 12));
			}else{
				 paCode = new MailController().findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(0, 6));
			}
			//Changed as part of bug ICRD-157758 by A-5526 ends
			if(paCode!=null){
				partyIdentifier=findPartyIdentifierForPA(mailbagVO.getCompanyCode(),paCode);
			mailResditVO.setPartyIdentifier(partyIdentifier);
			}
			//Added by U-1317 in case of resdit Loading
			mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ?
						mailbagVO.getMailSequenceNumber(): findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()) );

		}
//Added for CRQ ICRD-111886 by A-5526 ends
		log.exiting(CLASS, "construct MailResditVO");
		return mailResditVO;
	}



	//TODO flag along with transfer
	public void flagOnlineHandedoverResditForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventPort)
				throws SystemException {
		MailResditVO mailResditVO=null;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

		for (MailbagVO mailbagVO : mailbagVOs) {
			MailbagVO mailbagVo=null;
			try {
				mailbagVo = new MailController().constructOriginDestinationDetails(mailbagVO);
			} catch (SystemException e) {
				e.getMessage();
			}
			if(mailbagVo!=null){
			mailbagVO.setOrigin(mailbagVo.getOrigin());	 
			mailbagVO.setDestination(mailbagVo.getDestination());
			}
			 mailResditVO=constructMailResditVO(mailbagVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_ONLINE, false);
			 mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
			 mailResditVOs.add(mailResditVO);
//			 if(canFlagResditForEvent(MailConstantsVO.RESDIT_HANDOVER_ONLINE, mailResditVO, mailbagVO)){
//	             new MailResdit(mailResditVO);
//			 }
		}

		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_ONLINE, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
	}
	/**
	 * This method is used to flagHandedOverResditForUlds.
	 *
	 * @author A-1936
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	private void flagHandedOverResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.entering("ResditControler", "flagUpliftedResditForUlds");

		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailVo : acceptedUlds) {
				UldResditVO resditVO = constructUldResditVO(containerDetailVo, pol,
						MailConstantsVO.RESDIT_HANDOVER_OFFLINE);
				resditVO.setPaOrCarrierCode(containerDetailVo.getCarrierCode());
				// For MTK558
				//if("I".equals(containerDetailVo.getOperationFlag())){
	                if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, resditVO)){
	                	 new UldResdit(resditVO);
	                }
				//}
			}
		}

		log.exiting("ResditControler", "flagUpliftedResditForUlds");
	}


	/**
	 * @author A-1936
	 * This method is used to flagHandedOverResditForMailbags
	 * @param mailbags
	 * @param pol
	 * @throws SystemException
	 */
	public void flagHandedOverResditForMailbags(
			Collection<MailbagVO> mailbags, String pol) throws SystemException {
		log.entering(CLASS, "flagHandedOverResdit");
		log.log(Log.FINE, "The Pol is " + pol);
		 Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		if (mailbags != null && mailbags.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbags) {
				MailResditVO resditVO = constructMailResditVO(mailbagVO, pol,
						MailConstantsVO.RESDIT_HANDOVER_OFFLINE, false);
				resditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
				mailResditVOs.add(resditVO);
//                if(canFlagResditForEvent(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, resditVO, mailbagVO)){
//                	 new MailResdit(resditVO);
//                }
			}

			mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, mailResditVOs, mailbags);
			stampResdits(mailResditVOs);
		}
		for(MailbagVO mailbagVO: mailbags) {
			if(new MailController().isUSPSMailbag(mailbagVO)){
				uspsMailbags.add(mailbagVO);
			}
		}
		if(uspsMailbags.size()>0) {
			Collection<RateAuditDetailsVO> rateAuditVOs =new MailController().createRateAuditVOsFromMailbag(uspsMailbags);
		  try {
				new MailOperationsMRAProxy().recalculateDisincentiveData(rateAuditVOs);
			} catch (ProxyException e) {     
				throw new SystemException(e.getMessage(), e);     
			}
		}
		log.entering(CLASS, "flagHandedOverResdit");
	}

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * This method will flag the Loaded Resdit (48) For SB ULDs
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	private void flagLoadedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
	throws SystemException {
		log.entering("ResditControler", "flagLoadedResditForUlds");

		String eventCode = MailConstantsVO.RESDIT_LOADED;
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						pol, eventCode);
//				if("I".equals(containerDetailsVO.getOperationFlag())){
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.log(Log.INFO,
								"Flagging the LOADED Resdits---uldResditVO--",
								uldResditVO);
						log.log(Log.INFO,"Flagging the LOADED Resdits");
						new UldResdit(uldResditVO);
					}
//				}
			}
		}
		log.exiting("ResditControler", "flagLoadedResditForUlds");
	}

	/**
	 * Flags the USPS loaded (48) event
	 * Feb 5, 2007, A-1739
	 * @param mailbagVOs
	 * @param eventPort
	 * @throws SystemException
	 */
	private void flagLoadedResditForMailbags(Collection<MailbagVO> mailbagVOs,
			String eventPort) throws SystemException {
		log.entering(CLASS, "flagDepartedResditForMailbags");
		MailResditVO mailResditVO= null;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

			for (MailbagVO mailbagVO : mailbagVOs) {
				mailResditVO=constructMailResditVO(mailbagVO, eventPort,
						MailConstantsVO.RESDIT_LOADED, false);
//				if(canFlagResditForEvent(mailResditVO.getEventCode(), mailResditVO, mailbagVO)){
//					new MailResdit(mailResditVO);
//				}
				mailResditVOs.add(mailResditVO);
			}

		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_LOADED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
			log.exiting(CLASS, "flagDepartedResditForMailbags");
	}
	/**
	 * @author A-1936
	 * This method is used to flagAssignedResditForUlds
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	private void flagAssignedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		String eventCode = MailConstantsVO.RESDIT_ASSIGNED;

		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {

				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						pol, eventCode);
				log.log(Log.FINE, " uldResditVO " + uldResditVO);
				removeFlaggedUnsentULDResdit(uldResditVO);
				if(canFlagResditForULDEvent(eventCode, uldResditVO)){
//				    removeFlaggedUnsentResdit(containerDetailsVO, pol,
//						MailConstantsVO.RESDIT_PENDING);
				     new UldResdit(uldResditVO);
				}
			}
		}
	}




	/**@author A-3227 RENO K ABRAHAM
	 * This method is used to remove the Unsent Pending ULD Resdits
	 * @param uldResditVO
	 * @throws SystemException
	 */
	private void removeFlaggedUnsentULDResdit(UldResditVO uldResditVO)
	throws SystemException{

		String eventCode = MailConstantsVO.RESDIT_PENDING;
		/*List<UldResdit> uldResdits =  UldResdit.findPendingUldResdit(uldResditVO,eventCode);
		if(uldResdits != null && uldResdits.size() > 0){
			UldResdit uldResdit = uldResdits.get(0);
			uldResdit.remove();
		}*/
	}


	/**
	 * @author A-1739
	 * This method is used to flagAssignedResditForMailbags
	 * @param mailbagVOs
	 * @param eventAirport
	 * @throws SystemException
	 */
	private void flagAssignedResditForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventAirport)
			throws SystemException {
		log.entering(CLASS, "flagAssignedResdit");
        String eventCode = MailConstantsVO.RESDIT_ASSIGNED;

		if (mailbagVOs != null && mailbagVOs.size() > 0) {

			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {

				mailbagVO.setScannedPort(eventAirport);
				MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
						eventAirport, eventCode, false);
				log.log(Log.FINE, " mailResditVO " + mailResditVO);
				/*If Assigned immediately after Offloading, no need to send PENDING RESDIT
				 * Remove those Unsent Pending Resdits.
				 */
			//Commenting this out since this not critical and for usps
			//anyway the assigned resdit is not needed
			/*removeFlaggedUnsentResdit(mailbagVO, eventAirport,
					MailConstantsVO.RESDIT_PENDING);*/

				mailResditVOs.add(mailResditVO);
//				if(canFlagResditForEvent(eventCode, mailResditVO, mailbagVO)){
//				     new MailResdit(mailResditVO);
//				}
			}

			mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
			stampResdits(mailResditVOs);
		}
		log.exiting(CLASS, "flagAssignedResdit");
	}
	/**
	 * @author A-5991
	 * @param carrContainerDetailsVOs
	 * @param eventPort
	 * @throws SystemException
	 */
	private void flagHandedoverReceivedForULDs(
			Collection<ContainerDetailsVO> carrContainerDetailsVOs, String eventPort)
				throws SystemException {
		log.entering(CLASS, "flagHandedoverReceivedForULDs");

		for (ContainerDetailsVO containerDetailsVO : carrContainerDetailsVOs) {
			UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
			if("I".equals(containerDetailsVO.getOperationFlag())){
				if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_RECEIVED, uldResditVO)){
					new UldResdit(uldResditVO);
			    }
			}
	    }

		log.exiting(CLASS, "flagHandedoverReceivedForULDs");
	}
	/**
	 * @author A-1936 This method is used to flagReceivedResditForUlds
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	private void flagReceivedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.entering("ResditControler", "flagReceivedResditForUlds");

		String eventCode = RESDIT_RECEIVED;
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						                                        pol, eventCode);
				if("I".equals(containerDetailsVO.getOperationFlag())){
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.log(Log.INFO,"Flagging the Received Resdits---uldResditVO--"+uldResditVO);
						log.log(Log.INFO,"Flagging the Received Resdits");
						new UldResdit(uldResditVO);
					}
				}
			}
		}

	}

	/**
	 * A-1739
	 *
	 * @param eventPort
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagReceivedResditForMailbags(
			Collection<MailbagVO> mailbagVOs,String eventPort)
			throws SystemException {
		log.entering("MailResdit", "flagReceivedResditForMailbags");
		String eventCode = RESDIT_RECEIVED;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

		for (MailbagVO mailbagVO : mailbagVOs) {
			MailResditVO mailResditVO=null;
			if(! mailbagVO.isBlockReceivedResdit()){//added by A-7371 as part of ICRD-256798
			 mailResditVO = constructMailResditVO(mailbagVO,
					eventPort, eventCode, false);
			    mailResditVO.setPaOrCarrierCode(
			    		new MailController().findPAForOfficeOfExchange(
								mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
			    mailResditVO.setDependantEventCode(MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
			    /*
			     * Modified By Karthick V a-1936
			     */
			    if(!MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getLatestStatus()))
			    	{
			    	mailResditVOs.add(mailResditVO);
			    	}
//			if (canFlagResditForEvent(MailConstantsVO.RESDIT_RECEIVED, mailResditVO, mailbagVO)) {
//				log.log(Log.INFO,"Flagging the Received Resdits");
//				 new MailResdit(mailResditVO);
//			}
			}else{
				eventCode =MailConstantsVO.RESDIT_HANDOVER_RECEIVED;
				 mailResditVO = constructMailResditVO(mailbagVO,
						eventPort, eventCode, false);
				    mailResditVO.setPaOrCarrierCode(
				    		new MailController().findPAForOfficeOfExchange(
									mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
				    if(!MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getLatestStatus())){
				    	mailResditVOs.add(mailResditVO);
			        }
		}
			if("Y".equals(mailbagVO.getIsFromTruck())){
				if(mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightNumber().trim().length()>0 ){
					mailbagVO.setStdOrStaTruckFlag("STD");
				mailResditVO.setEventDate(findResditEvtDate(mailbagVO,mailbagVO.getScannedPort()));
				}
			}
		}
		mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.exiting("MailResdit", "flagReceivedResditForMailbags");
	}

	public void flagHandedoverReceivedForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventPort)
				throws SystemException {
		log.entering(CLASS, "flagHandedoverReceivedForMailbags");
		 Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
            MailResditVO mailResditVO = constructMailResditVO(mailbagVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_RECEIVED, false);
            mailResditVO.setPaOrCarrierCode(mailbagVO.getTransferFromCarrier());
			log.log(Log.FINE,"THE PA OR CARRIER CODE"+mailResditVO.getPaOrCarrierCode());
			/*
			 * Added  By Karthick V a-1936
			 */
			mailResditVO.setDependantEventCode(MailConstantsVO.RESDIT_RECEIVED);
			mailResditVOs.add(mailResditVO);
//			if(canFlagResditForEvent(MailConstantsVO.RESDIT_HANDOVER_RECEIVED, mailResditVO, mailbagVO)){
//               new MailResdit(mailResditVO);
//			}

	    }

		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_RECEIVED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.exiting(CLASS, "flagHandedoverReceivedForMailbags");
	}

	/**
	 *Flags resdits according to configuration
	 * Feb 2, 2007, A-1739
	 * @param companyCode
	 * @param carrierId
	 * @param txnId
	 * @param eventPort TODO
	 * @param mailbagVO TODO
	 * @param valuesMap TODO
	 * @param args
	 * @throws SystemException
	 */
	private void flagConfiguredResdits(String companyCode, int carrierId,
			String txnId, String eventPort, Collection<MailbagVO> mailbagVOs,
			Map<String, Object> valuesMap)
		throws SystemException {
		log.entering(CLASS, "flagConfiguredResdits");

			ResditTransactionDetailVO txnDetailVO =
				findResditConfigurationForTxn(companyCode, carrierId, txnId);
			if(txnDetailVO != null) {
				flagResditsForTransaction(companyCode, txnDetailVO,
						eventPort, mailbagVOs, valuesMap);
			} else {
				log.log(Log.INFO, "Transactiondetails not configured");
			}

		log.exiting(CLASS, "flagConfiguredResdits");

	}




	/**
	 * TODO Purpose
	 * Feb 2, 2007, A-1739
	 * @param companyCode
	 * @param txnDetailVO
	 * @param eventPort TODO
	 * @param mailbagVOs TODO
	 * @param valuesMap TODO
	 * @param args
	 * @throws SystemException
	 */
	private void flagResditsForTransaction(
			String companyCode, ResditTransactionDetailVO txnDetailVO,
			String eventPort, Collection<MailbagVO> mailbagVOs,
			Map<String, Object> valuesMap)
	throws SystemException {
		log.entering(CLASS, "flagResditsForTransaction");

		log.log(Log.FINEST, "map --->> " + valuesMap);
		log.log(Log.FINEST, "txnDtl -->> " + txnDetailVO);

 	    boolean isSpecificResdit = false;

		log.log(Log.FINEST, "isSpecificResdit -->> " + isSpecificResdit);
		Map<String, Collection<CarditTransportationVO>> carditCache =
			new HashMap<String, Collection<CarditTransportationVO>>();
		boolean isLookupCarditDone = false;
		Collection<ContainerDetailsVO> containerDetails =
			(Collection<ContainerDetailsVO>)valuesMap.get(ACP_ULDS);

		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReceivedResditFlag())) {
						Collection<MailbagVO> paMailbags =
				(Collection<MailbagVO>)valuesMap.get(PA_MAILS);
			if(paMailbags != null && paMailbags.size() > 0) {
				log.log(Log.FINEST, " flagging recevd");
				flagReceivedResditForMailbags(paMailbags, eventPort);
			}
			Collection<MailbagVO> newArrvMails =
				(Collection<MailbagVO>)valuesMap.get(NEWARR_MAILS);
			if(newArrvMails != null && newArrvMails.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.log(Log.FINEST, " flagging recevd for arrivalmails cardit");
					flagReceivedResditForCarditDetails(newArrvMails,  carditCache,
							eventPort);
				} else {
					log.log(Log.FINEST, " flagging recevd for arrivalmails  normal");
					flagReceivedResditForMailbags(newArrvMails, eventPort);
				}
			}


			/**
			 * Added By Roopak For Sending received resdits for ULD
			 */
			Collection<ContainerDetailsVO> paContainerDetails =
				(Collection<ContainerDetailsVO>)valuesMap.get(PA_ULDS);
			if(paContainerDetails != null && paContainerDetails.size() > 0) {
				flagReceivedResditForUlds(paContainerDetails, eventPort);
			}
			//TODO carrierCode for containerDetaills
		}

		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getHandedOverReceivedResditFlag())) {
			Collection<MailbagVO> carrMailbags =
				(Collection<MailbagVO>)valuesMap.get(CARR_MAILS);
			log.log(Log.FINEST, " flagging handover recevd");
			if(carrMailbags != null && carrMailbags.size() > 0) {
				flagHandedoverReceivedForMailbags(carrMailbags, eventPort);
			}
			Collection<ContainerDetailsVO> carrContainerDetails =
				(Collection<ContainerDetailsVO>)valuesMap.get(CARR_ULDS);
			if(carrContainerDetails != null && carrContainerDetails.size() > 0) {
				flagHandedoverReceivedForULDs(carrContainerDetails, eventPort);
			}
		}

		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getAssignedResditFlag())) {
			Boolean asgFlg = (Boolean)valuesMap.get(IS_FLTASG);
			boolean isFlightAsgn = false;
			if(asgFlg != null) {
				isFlightAsgn = asgFlg.booleanValue();
			}
			if(isFlightAsgn) {
				if(mailbagVOs != null && mailbagVOs.size() > 0) {
					log.log(Log.FINEST, " flagging assigned ");
					flagAssignedResditForMailbags(mailbagVOs, eventPort);
				}
				if(containerDetails != null && containerDetails.size() > 0) {
					flagAssignedResditForUlds(containerDetails, eventPort);
				}
			}
		}
		Boolean depFlg = (Boolean)valuesMap.get(HAS_DEP);
		boolean hasDept = false;
		if(depFlg != null) {
			hasDept = depFlg.booleanValue();
		}
		if(hasDept && MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getUpliftedResditFlag())) {
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.log(Log.FINEST, " flagging uplifed w/ cardit ");
					flagResditOnDepartForCarditDetails(
							mailbagVOs, carditCache, eventPort,
							MailConstantsVO.RESDIT_UPLIFTED);
				} else {
					log.log(Log.FINEST, " flagging uplifed ");
					flagUpliftedResditForMailbags(mailbagVOs, eventPort);
				}
			}
			if(containerDetails != null && containerDetails.size() > 0) {
				flagUpliftedResditForUlds(containerDetails, eventPort);
			}
		}
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getLoadedResditFlag())) {
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.log(Log.FINEST, " flagging loaded w/ cardit ");
					flagResditOnDepartForCarditDetails(
							mailbagVOs, carditCache, eventPort,
							MailConstantsVO.RESDIT_LOADED);
				} else {
					log.log(Log.FINEST, " flagging loaded ");
					flagLoadedResditForMailbags(mailbagVOs, eventPort);
				}
			}
			if(containerDetails != null && containerDetails.size() > 0) {
				flagLoadedResditForUlds(containerDetails, eventPort);
			}
		}
	/**
	 * TODO needs more discussion.
	 *	Currently based on ownairlines' config
	 *
	 */
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getHandedOverResditFlag())) {
			Collection<MailbagVO> onlineTransferMails =
				(Collection<MailbagVO>)valuesMap.get(OLIN_MAILS);
			if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
				log.log(Log.FINEST, " flagging online h/ov ");
				flagOnlineHandedoverResditForMailbags(onlineTransferMails, eventPort);
			}
			//TODO Ulds
			/**
			 * Added By Roopak for sending Handover_Received Resdits for ULDs
			 */
			if(containerDetails != null && containerDetails.size() > 0) {
				flagOnlineHandedoverResditForUlds(containerDetails, eventPort);
			}

		}

		/*
		 * Added By RENO K ABRAHAM FOR AirNZ CR-504
		 * Flagging DELIVERED RESDIT,based on the DELIVERED RESDIT MASTER Configuration
		 */
		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getDeliveredResditFlag())){
			Collection<MailbagVO> deliveredMails =
				(Collection<MailbagVO>)valuesMap.get(DLVD_MAILS);

			Collection<ContainerDetailsVO> deliveredContainers =
				(Collection<ContainerDetailsVO>)valuesMap.get(DLVD_ULDS);

			if(deliveredMails != null && deliveredMails.size() >0){
				log.log(Log.FINEST, " flagging delivered");
				flagDeliveredResditForMailbags(deliveredMails, eventPort);
			}

			if(deliveredContainers!=null && deliveredContainers.size()>0){
				flagDlvdResditsForUldFromArrival(deliveredContainers, eventPort);
			}



		}

		/*
		 * Added By RENO K ABRAHAM FOR AirNZ CR-504
		 * Flagging RETURNED RESDIT,based on the RETURNED RESDIT MASTER Configuration
		 */
		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReturnedResditFlag())){
			Collection<MailbagVO> mailbags =
				(Collection<MailbagVO>)valuesMap.get(RETN_MAILS);
			if(mailbags != null && mailbags.size() >0){
				log.log(Log.FINEST, " flagging returned");
				flagReturnedResditForMailbags(mailbags);
			}
		}

		log.exiting(CLASS, "flagResditsForTransaction");
	}


	/**
	 * TODO Purpose
	 * Feb 2, 2007, A-1739
	 * @param acceptedMailbags
	 * @param paMailbags
	 * @param carrMailbags
	 */
	private void groupPACarrMailbags(Collection<MailbagVO> acceptedMailbags,
			Collection<MailbagVO> paMailbags, Collection<MailbagVO> handoverRcvdMailbags, Collection<MailbagVO> handoverOnMailbags,
			Collection<MailbagVO> handoverOffMailbags, String ownAirlineCOde, String flightCarrierCOde) {
		for(MailbagVO mailbagVO : acceptedMailbags) {

			// Added last 2 conditions since if transfer carrier code is own airline then no need to consider as transfer.
			//While accepting to other airline flight we may have to specify ownairline as transfer carrier.

			if(mailbagVO.getTransferFromCarrier() != null &&
					mailbagVO.getTransferFromCarrier().trim().length() > 0 &&
					ownAirlineCOde != null &&
					flightCarrierCOde != null ){
				if(ownAirlineCOde.equals(flightCarrierCOde) && flightCarrierCOde.equals(mailbagVO.getTransferFromCarrier())) {
					//handoverOnMailbags.add(mailbagVO);
					paMailbags.add(mailbagVO);
				}else if(ownAirlineCOde.equals(flightCarrierCOde) && !flightCarrierCOde.equals(mailbagVO.getTransferFromCarrier())) {
					handoverRcvdMailbags.add(mailbagVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && ownAirlineCOde.equals(mailbagVO.getTransferFromCarrier())) {
					handoverOffMailbags.add(mailbagVO);
					paMailbags.add(mailbagVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && !ownAirlineCOde.equals(mailbagVO.getTransferFromCarrier())) {
//					Operation is between both OAL ;  74 to be sent.
					handoverRcvdMailbags.add(mailbagVO);
					paMailbags.add(mailbagVO);
				}
			} else {
				paMailbags.add(mailbagVO);
			}
		}
	}



	/**
	 * TODO Purpose
	 * Jan 22, 2008, A-1876
	 * @param acceptedUlds
	 * @param paContainerDetailsVOs
	 * @param carrContainerDetailsVOs
	 */
	private void groupPACarrULDs(Collection<ContainerDetailsVO> acceptedUlds,
			Collection<ContainerDetailsVO> paContainerDetailsVOs,
			Collection<ContainerDetailsVO> handoverRcvdContainerDetailsVOs, Collection<ContainerDetailsVO> handoverOnContainerDetailsVOs,
			Collection<ContainerDetailsVO> handoverOffContainerDetailsVOs, String ownAirlineCOde, String flightCarrierCOde) {
		for(ContainerDetailsVO containerDetailsVO : acceptedUlds) {


			// Added last 2 conditions since if transfer carrier code is own airline then no need to consider as transfer.
			//While accepting to other airline flight we may have to specify ownairline as transfer carrier.

			if(containerDetailsVO.getTransferFromCarrier() != null &&
					containerDetailsVO.getTransferFromCarrier().trim().length() > 0 &&
					ownAirlineCOde != null &&
					flightCarrierCOde != null ){
				if(ownAirlineCOde.equals(flightCarrierCOde) && flightCarrierCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverOnContainerDetailsVOs.add(containerDetailsVO);
					paContainerDetailsVOs.add(containerDetailsVO);
				}else if(ownAirlineCOde.equals(flightCarrierCOde) && !flightCarrierCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverRcvdContainerDetailsVOs.add(containerDetailsVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && ownAirlineCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverOffContainerDetailsVOs.add(containerDetailsVO);
					paContainerDetailsVOs.add(containerDetailsVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && !ownAirlineCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					//Operation is between both OAL ; no resdit sent.
				}
			} else {
				paContainerDetailsVOs.add(containerDetailsVO);
			}
		}
	}

	/**
	 * Flags Handedover for ownairline according to configuration
	 * Feb 5, 2007, A-1739
	 * @param companyCode
	 * @param ownAirlineId
	 * @param txnId
	 * @param eventPort
	 * @param mailbagVOs
	 * @param valuesMap
	 * @throws SystemException
	 */
	private void flagConfiguredHandoverResdit(String companyCode,
			int ownAirlineId, String txnId, String eventPort,
			Collection<MailbagVO> mailbagVOs, Map<String, Object> valuesMap)
		throws SystemException {
		log.entering(CLASS, "flagConfiguredHandoverResdit");

		ResditTransactionDetailVO resditTxnVO =
			findResditConfigurationForTxn(companyCode, ownAirlineId, txnId);

		if(resditTxnVO != null) {
			if(MailConstantsVO.FLAG_YES.equals(
					resditTxnVO.getHandedOverResditFlag())) {
				flagHandedOverResditForMailbags(mailbagVOs, eventPort);

				// For MTK558
				if(valuesMap.containsKey(ACP_ULDS)){
					Collection<ContainerDetailsVO> containerDetails =
						(Collection<ContainerDetailsVO>)valuesMap.get(ACP_ULDS);
					if(containerDetails != null && containerDetails.size() > 0) {
						flagHandedOverResditForUlds(containerDetails, eventPort);
					}
				}
			}
		}
		log.exiting(CLASS, "flagConfiguredHandoverResdit");

	}

	/**
	 * TODO Purpose
	 * Feb 6, 2007, A-1739
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	private void updateCarditDetailsForMailbags(
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		for(MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setCarditKey(
					Cardit.findCarditForMailbag(mailbagVO.getCompanyCode(),
							mailbagVO.getMailbagId()));
		}
	}


	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param newArrvMails
	 * @param carditCache
	 * @param eventPort
	 * @throws SystemException
	 */
	private void flagReceivedResditForCarditDetails(
			Collection<MailbagVO> newArrvMails,
			Map<String, Collection<CarditTransportationVO>> carditCache,
			String eventPort) throws SystemException {
		log.entering(CLASS, "flagReceivedResditForCardit");

		Collection<MailbagVO> noCarditMails = new ArrayList<MailbagVO>();

		boolean hasCardit = false;
		for(MailbagVO mailbagVO : newArrvMails) {
			hasCardit = false;
			if(mailbagVO.getCarditKey() != null) {
				Collection<CarditTransportationVO> transportationVOs =
					carditCache.get(mailbagVO.getCarditKey());
				if(transportationVOs == null) {
					transportationVOs =
					Cardit.findCarditTransportationDetails(mailbagVO.getCompanyCode(),
							mailbagVO.getCarditKey());
				}

				if(transportationVOs != null) {
					int idx = 0;
					for(CarditTransportationVO transportationVO : transportationVOs) {
						if(mailbagVO.getCarrierId() == transportationVO.getCarrierID()) {
							hasCardit = true;
							//if our carrier is the first itinery the we have to send 74
							if(idx == 0) {
								flagReceivedResditForCarditMail(mailbagVO, transportationVO);
								break;
								//else got to send 43
							} else {
								flagHandedoverReceivedForCarditMail(
										mailbagVO, transportationVO);
								break;
							}
						}
						idx++;
					}
				}
			}
			if(!hasCardit) {
				noCarditMails.add(mailbagVO);
			}
		}
		if(noCarditMails.size() > 0) {
			flagReceivedResditForMailbags(noCarditMails, eventPort);
		}
		log.exiting(CLASS, "flagReceivedResditForCardit");
	}



	/**
	 * TODO Purpose
	 * Feb 6, 2007, A-1739
	 * @param mailbagVOs
	 * @param carditCache
	 * @param eventPort
	 * @param eventCode TODO
	 * @throws SystemException
	 */
	void flagResditOnDepartForCarditDetails(
			Collection<MailbagVO> mailbagVOs,
			Map<String, Collection<CarditTransportationVO>> carditCache,
			String eventPort, String eventCode)
		throws SystemException {
		log.entering(CLASS, "flagDepartedResditForCarditDetails");

		Collection<MailbagVO> noCarditMails =
			new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO : mailbagVOs) {
			if(mailbagVO.getCarditKey() != null) {
				Collection<CarditTransportationVO> transportationVOs =
					carditCache.get(mailbagVO.getCarditKey());
				if(transportationVOs == null) {
					transportationVOs =
					Cardit.findCarditTransportationDetails(mailbagVO.getCompanyCode(),
							mailbagVO.getCarditKey());
				}

				if(transportationVOs != null) {
					CarditTransportationVO transportationVOToFlag = null;
					for(CarditTransportationVO transportationVO : transportationVOs) {
						if(mailbagVO.getCarrierId() == transportationVO.getCarrierID()) {
							//trt may be specified for both inb and outbound
							//so need to find correct segment
							if(MailConstantsVO.OPERATION_INBOUND.equals(
								//FIXED BY JOJI @ NRT ON 10-JULY-2007
									mailbagVO.getOperationalStatus()) &&
									mailbagVO.getScannedPort().equals(
											transportationVO.getArrivalPort())) {
								//if our carrier is found then send apprp details in RESDIT
								transportationVOToFlag = transportationVO;
								break;
							} else if(MailConstantsVO.OPERATION_OUTBOUND.equals(
								//FIXED BY JOJI @ NRT ON 10-JULY-2007
										mailbagVO.getOperationalStatus()) &&
										mailbagVO.getScannedPort().equals(
												transportationVO.getDeparturePort())) {
								//if our carrier is found then send apprp details in RESDIT
								transportationVOToFlag = transportationVO;
								break;
							}
						}
					}
					if(transportationVOToFlag != null) {
						if(MailConstantsVO.RESDIT_LOADED.equals(eventCode)) {
							flagLoadedResditForCarditMail(
									mailbagVO, transportationVOToFlag);
						} else {
							flagUpliftedResditForCarditMail(
									mailbagVO, transportationVOToFlag);
						}

					} else {
						//no matching transportData
						if(MailConstantsVO.OPERATION_OUTBOUND.equals(
								mailbagVO.getOperationalStatus())) {
							noCarditMails.add(mailbagVO);
						}
					}
				}
			} else {
				//no matching cardit
				if(MailConstantsVO.OPERATION_OUTBOUND.equals(
						mailbagVO.getOperationalStatus())) {
					noCarditMails.add(mailbagVO);
				}
			}
		}
//		no cardit found
		if(noCarditMails.size() > 0) {
			if(MailConstantsVO.RESDIT_LOADED.equals(eventCode)) {
				flagLoadedResditForMailbags(noCarditMails, eventPort);
			} else if(MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
				flagUpliftedResditForMailbags(noCarditMails, eventPort);
			}
		}
		log.exiting(CLASS, "flagDepartedResditForCarditDetails");

	}


	/**
	 * @author a-1936
	 * This method is used to flag the Delivered Resdits for the  Mail Bags
	 * @param deliveredMailbags
	 * @param airportCode
	 * @throws SystemException
	 */
	public void flagDeliveredResditForMailbags(
			Collection<MailbagVO> deliveredMailbags,
			String airportCode) throws SystemException {
		log.entering(CLASS, "flagDeliveredResdit");
		//Added for Mail 4
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		HashMap<String, String> systemParameterMap =
				new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
		HashMap<String, String> mailPAmap = null;
		HashMap<String, String> exgOfcPAMap = new HashMap<String, String> ();
		ResditConfigurationVO resditConfigurationVO = null;
		Collection<MailResditVO> r21MailResditVOs = new ArrayList<MailResditVO>();
		Collection<MailResditVO> r23MailResditVOs = new ArrayList<MailResditVO>();
		String eventCode = null;
		/**
		 * If system parametere MailConstantsVO.SYSPAR_USPS_ENHMNT value is Y only thee enhancement should be
		 * considered otherwise no change is required.
		 */

				// Fetching business time configuration for Arrival Airport
				//resditConfigurationVO = MailResdit.findDeliveryResditTimeConfigForPort (companyCode,airportCode);
				// Finding PA for Mailbags . If cardit exists then sender of cardit else PA of OOE
				int mailbagSize = deliveredMailbags.size();
		int limit = 999;
		if(mailbagSize > limit) {
			int startIndex = 0;
			int endIndex = limit;
			mailPAmap = new HashMap<>();
			while(startIndex < endIndex) {
				ArrayList<MailbagVO> deliveredMailbags1 = (ArrayList<MailbagVO>) deliveredMailbags;
				mailPAmap.putAll(MailResdit.findPAForMailbags (deliveredMailbags1.subList(startIndex, endIndex)));
				startIndex = endIndex;
				if(endIndex+limit < mailbagSize) {
					endIndex = endIndex+limit;
				} else {
					endIndex = mailbagSize;
				}
			}
		} else {
				mailPAmap = MailResdit.findPAForMailbags (deliveredMailbags);
		}
				String paCode = null;
				String dstPaCode = null;
				MailResditVO mailResditVO = null;
				//eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY ;
				for (MailbagVO mailbagVO : deliveredMailbags) {

					paCode = exgOfcPAMap.get(mailbagVO.getOoe());
					dstPaCode = exgOfcPAMap.get(mailbagVO.getDoe());

					// To optimise DB fetch this logic is introduced.
					 if (paCode == null || paCode.length() <= 0){
						 paCode = new MailController().findPAForOfficeOfExchange(
									mailbagVO.getCompanyCode(), mailbagVO.getOoe());
						 exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
					 }

					 if(dstPaCode == null || dstPaCode.length() <= 0){
						 dstPaCode = new MailController().findPAForOfficeOfExchange(
									mailbagVO.getCompanyCode(), mailbagVO.getDoe());
						 exgOfcPAMap.put(mailbagVO.getDoe(), dstPaCode);
					 }
					mailResditVO =
						constructMailResditVO(mailbagVO, airportCode,
								eventCode, false);
					mailResditVO.setPaOrCarrierCode(paCode);
					if (systemParameterMap != null
							&& (MailConstantsVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
									//||MailConstantsVO.FLAG_ACTIVE.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
								)
						) {
						if(mailPAmap !=null && mailPAmap.size()>0 &&
								mailPAmap.get(mailbagVO.getMailbagId()).equals(MailConstantsVO.USPOST)){
							eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY ;
							mailResditVO.setEventCode(eventCode);
							r23MailResditVOs.add(mailResditVO);

						}else{
							eventCode = MailConstantsVO.RESDIT_DELIVERED;
							mailResditVO.setPaOrCarrierCode(dstPaCode);
							mailResditVO.setEventCode(eventCode);
							r21MailResditVOs.add(mailResditVO);
						}
					}else{
						eventCode = MailConstantsVO.RESDIT_DELIVERED;
						mailResditVO.setPaOrCarrierCode(dstPaCode);
						mailResditVO.setEventCode(eventCode);
						r21MailResditVOs.add(mailResditVO);

					}


				}


if(r23MailResditVOs!=null && r23MailResditVOs.size() >0){
		r23MailResditVOs = canFlagResditForEvents(eventCode, r23MailResditVOs, deliveredMailbags);
		stampResdits(r23MailResditVOs);
		}
		if(r21MailResditVOs!=null && r21MailResditVOs.size() >0){
		r21MailResditVOs = canFlagResditForEvents(eventCode, r21MailResditVOs, deliveredMailbags);
		stampResdits(r21MailResditVOs);
		}
		log.exiting(CLASS, "flagDeliveredResdit");
}


	/**
	 * @author A-1739
	 * This method is used to flagReturnedResditForMailbags
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	private void flagReturnedResditForMailbags(
			Collection<MailbagVO> mailbagVOs)
			throws SystemException {
		log.entering(CLASS, "flagReturnedResditForMailbags");
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

		for (MailbagVO mailbagVO : mailbagVOs) {
			MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
					mailbagVO.getScannedPort(),
					MailConstantsVO.RESDIT_RETURNED, false);

			//for return damage details is mandatory
			Collection<DamagedMailbagVO> mailbagDamages =
				mailbagVO.getDamagedMailbags();
			String returnPACode = null;
			for(DamagedMailbagVO damagedMailbagVO : mailbagDamages) {
				returnPACode = damagedMailbagVO.getPaCode();
			}
			mailResditVO.setPaOrCarrierCode(returnPACode);
//            if(canFlagResditForEvent(MailConstantsVO.RESDIT_RETURNED, mailResditVO, mailbagVO)){
//            	new MailResdit(mailResditVO);
//            }

            mailResditVOs.add(mailResditVO);

		}
		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_RETURNED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.exiting(CLASS, "flagReturnedResditForMailbags");
	}


	private void flagDlvdResditsForUldFromArrival(Collection<ContainerDetailsVO> deliveredContainers ,String eventPort)
			 throws SystemException{
				Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
				if (deliveredContainers != null && deliveredContainers.size() > 0) {
					for (ContainerDetailsVO containerDetailsVO : deliveredContainers) {
						UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
								eventPort, MailConstantsVO.RESDIT_DELIVERED);
						uldResditVOs.add(uldResditVO);
					}
					if (uldResditVOs != null && uldResditVOs.size() > 0) {
						flagDeliveredResditForULDs(uldResditVOs,
								eventPort);
					}
				}
			}


	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param mailbagVO
	 * @param transportationVO
	 * @throws SystemException
	 */
	private void flagReceivedResditForCarditMail(
			MailbagVO mailbagVO, CarditTransportationVO transportationVO)
	throws SystemException {
		log.entering(CLASS, "flagReceivedResditForCarditMail");
		MailResditVO mailResditVO =
			constructTRTResditVO(mailbagVO, transportationVO,
					MailConstantsVO.RESDIT_RECEIVED);

		if(canFlagResditForEvent(MailConstantsVO.RESDIT_RECEIVED,
				mailResditVO, mailbagVO)) {
			Collection<String> paramNames = new ArrayList<String>();
			paramNames.add(MailConstantsVO.RESDIT_RCVD_CONFTIM);
			Map<String, String> paramValMap =
				new SharedDefaultsProxy().findSystemParameterByCodes(
					paramNames);
			int minutes = Integer.parseInt(
					paramValMap.get(MailConstantsVO.RESDIT_RCVD_CONFTIM));
			mailResditVO.setEventDate(
					transportationVO.getDepartureTime().addMinutes(-minutes));
			mailResditVO.setEventCode(MailConstantsVO.RESDIT_RECEIVED);
			mailResditVO.setPaOrCarrierCode(
					new MailController().findPAForOfficeOfExchange(
							mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
			if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
					&& !canStampResdits(mailResditVO)) {
				return;
			}
			new MailResdit(mailResditVO);
		}

		log.exiting(CLASS, "flagReceivedResditForCarditMail");
	}



	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param mailbagVO
	 * @param transportationVO
	 * @throws SystemException
	 */
	private void flagHandedoverReceivedForCarditMail(
			MailbagVO mailbagVO, CarditTransportationVO transportationVO)
		throws SystemException {
		log.entering(CLASS, "flagHandedoverReceivedForCarditMail");
		MailResditVO mailResditVO =
			constructTRTResditVO(mailbagVO, transportationVO,
					MailConstantsVO.RESDIT_HANDOVER_RECEIVED);

		Collection<String> paramNames = new ArrayList<String>();
		paramNames.add(MailConstantsVO.RESDIT_RCVD_CONFTIM);
		Map<String, String> paramValMap =
			new SharedDefaultsProxy().findSystemParameterByCodes(
				paramNames);
		int minutes = Integer.parseInt(
				paramValMap.get(MailConstantsVO.RESDIT_RCVD_CONFTIM));

		mailResditVO.setEventDate(
				transportationVO.getDepartureTime().addMinutes(-minutes));

		mailResditVO.setEventCode(
				MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
		mailResditVO.setPaOrCarrierCode(mailbagVO.getTransferFromCarrier());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.exiting(CLASS, "flagHandedoverReceivedForCarditMail");
	}


	/**
	 * TODO Purpose
	 * Feb 6, 2007, A-1739
	 * @param mailbagVO
	 * @param transportationVO
	 * @throws SystemException
	 */
	private void flagLoadedResditForCarditMail(MailbagVO mailbagVO,
			CarditTransportationVO transportationVO) throws SystemException {
		log.entering(CLASS, "flagDepartedResditForCarditMail");
		MailResditVO mailResditVO =
			constructTRTResditVO(mailbagVO, transportationVO,
					MailConstantsVO.RESDIT_LOADED);
		mailResditVO.setEventDate(
				transportationVO.getDepartureTime());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.exiting(CLASS, "flagDepartedResditForCarditMail");

	}


	/**
	 * TODO Purpose
	 * Feb 8, 2007, A-1739
	 * @param mailbagVO
	 * @param transportationVOToFlag
	 * @throws SystemException
	 */
	private void flagUpliftedResditForCarditMail(MailbagVO mailbagVO,
			CarditTransportationVO transportationVO) throws SystemException {
		log.entering(CLASS, "flagUpliftedResditForCarditMail");
		MailResditVO mailResditVO =
			constructTRTResditVO(mailbagVO, transportationVO,
					MailConstantsVO.RESDIT_UPLIFTED);
		mailResditVO.setEventDate(
				transportationVO.getDepartureTime());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.exiting(CLASS, "flagUpliftedResditForCarditMail");

	}

	/**
	 *
	 * @param uldResditVOs
	 * @param airportCode
	 * @throws SystemException
	 */
	private void flagDeliveredResditForULDs(
			Collection<UldResditVO> uldResditVOs, String airportCode)
		throws SystemException {
		log.entering(CLASS, "flagDeliveredResditForULDs");

		String eventCode = MailConstantsVO.RESDIT_DELIVERED;
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
		HashMap<String, String> systemParameterMap =
				new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
		HashMap<String, String> uldPAmap = null;
		HashMap<String, String> uldPACarditmap = null;
		String paCode = null;
//		ResditConfigurationVO resditConfigurationVO = null;
//		Collection<MailResditVO> r21ULDResditVOs = new ArrayList<MailResditVO>();
//		Collection<MailResditVO> r23ULDResditVOs = new ArrayList<MailResditVO>();
		/**
		 * If system parametere MailConstantsVO.SYSPAR_USPS_ENHMNT value is Y only thee enhancement should be
		 * considered otherwise no change is required.
		 */
		if (systemParameterMap != null
				&& (MailConstantsVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
						//||MailConstantsVO.FLAG_ACTIVE.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
					)
			) {
				// Fetching business time configuration for Arrival Airport
//				resditConfigurationVO = MailResdit.findDeliveryResditTimeConfigForPort (airportCode);
				// Finding PA for Mailbags . If cardit exists then sender of cardit else PA of OOE
				uldPACarditmap = UldResdit.findPAForShipperbuiltULDs (uldResditVOs, true);
				uldPAmap = UldResdit.findPAForShipperbuiltULDs (uldResditVOs, false);

				if (uldResditVOs != null && uldResditVOs.size() > 0) {

					for (UldResditVO uldResditVO : uldResditVOs) {

						paCode = uldPACarditmap.get(uldResditVO.getUldNumber());
						paCode = paCode == null ? uldPAmap.get(uldResditVO.getUldNumber()): paCode;

						if (MailConstantsVO.USPOST.equals(paCode)){
							eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY ;
						}else{
							eventCode = MailConstantsVO.RESDIT_DELIVERED ;
						}

						uldResditVO.setEventCode(eventCode);
						uldResditVO.setShipperBuiltCode(paCode);
						if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
							log.log(Log.INFO,"Flagging the RESDIT_DELIVERED");
							new UldResdit(uldResditVO);
						}
					}
				}
		}else{
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				for (UldResditVO uldResditVO : uldResditVOs) {
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.log(Log.INFO,"Flagging the RESDIT_DELIVERED");
						new UldResdit(uldResditVO);
					}
				}
			}
		}

		log.exiting(CLASS, "flagDeliveredResditForULDs");
	}

	/**
	 * Constructs the MailResditVO from the transportInformation
	 * Feb 5, 2007, A-1739
	 * @param mailbagVO
	 * @param transportationVO
	 * @param eventCode the eventCode to construct
	 * @return
	 * @throws SystemException
	 */
	private MailResditVO constructTRTResditVO(MailbagVO mailbagVO,
			CarditTransportationVO transportationVO, String eventCode) throws SystemException {
		//Added for CRQ ICRD-111886 by A-5526 starts
		String partyIdentifier;

		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		//Added for CRQ ICRD-111886 by ends
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getUldNumber());
		mailResditVO.setFromDeviationList(mailbagVO.isFromDeviationList());
		//from transportationVO
		mailResditVO.setEventAirport(transportationVO.getDeparturePort());
		mailResditVO.setCarrierId(transportationVO.getCarrierID());
		mailResditVO.setFlightNumber(transportationVO.getFlightNumber());
		mailResditVO.setFlightSequenceNumber(
				transportationVO.getFlightSequenceNumber());
		mailResditVO.setPaOrCarrierCode(transportationVO.getCarrierCode());
//		mailResditVO.setSegmentSerialNumber(transportationVO
//				.getSegmentSerialNumber());
		mailResditVO.setEventCode(eventCode);

		mailResditVO.setSegmentSerialNumber(
				transportationVO.getSegmentSerialNum());

		//temporary fix for bug ICRD-160796 by A-5526 as issue is not getting replicated starts
		 if(mailResditVO.getSegmentSerialNumber()==0 && MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)){
			mailResditVO.setSegmentSerialNumber(1);
		}
		//temporary fix for bug ICRD-160796 by A-5526 as issue is not getting replicated starts.Communicated with Santhi K
		//Added for CRQ ICRD-111886 by A-5526 starts
		if(mailbagVO.getMailbagId()!=null && mailbagVO.getMailbagId().length()==29){

			String paCode="";
			exchangeOfficeDetails = new MailController().findOfficeOfExchange(
						mailbagVO.getCompanyCode(), mailbagVO.getMailbagId().substring(0, 6),1);
			if(exchangeOfficeDetails!=null && !exchangeOfficeDetails.isEmpty()){
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
				mailResditVO.setMailboxID(officeOfExchangeVO.getMailboxId());
			}
			//Changed as part of bug ICRD-157758 by A-5526 starts
			if(eventCode==null || (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode) ||MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode) ) )
			{
			 paCode = new MailController().findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(6, 12));
			}else{
				 paCode = new MailController().findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(0, 6));
			}
			//Changed as part of bug ICRD-157758 by A-5526 ends
			if(paCode!=null){
				partyIdentifier=findPartyIdentifierForPA(mailbagVO.getCompanyCode(),paCode);
			mailResditVO.setPartyIdentifier(partyIdentifier);
			}


		}
		//Added for CRQ ICRD-111886 by A-5526 ends
		return mailResditVO;
	}

	/*
	 * The Resdits has to be Flagged for the Following Events satisfying the following Condtions are  :-
	 *
	 *  Received 1.mailtag,Port and the Event Code + Dependant Event Code..
     *  Assigned 1.mailtag,2.flightid,3.port, 4.outbound 5.eventCode
     *  uplifted/load 1.mailtag,2.flightid,3.port,4.outbound 5.eventCode
     *  pending  1.mailtag,2.port ,3.eventCode
     *  returned 1.mailtag 2.port 3.eventCode
     *  delivered 1.mailtag 2.port 3.eventCode
     *  handover off 1.mailtag 2.port 3.eventCode 4.outbound
     *  handover onl 1.mailtag,2.flightid,3.port, 4.outbound 5.eventCode
     *  handover rcv  1.mailtag 2.port 3.eventCode 4.outbound
	 *  Note:
	 *   When the event is  RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   When the event is  HANDED OVER RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   The Method checks wether the Resdit is already flagged for the Particular Event ..If the
	 *   DependantEventCode in MailResditVo is also being  present then  this method checks wether there is any Resdit
	 *   flagged for either the Resdit Event or the Dependant Event and Returns True if flagged for either of these or all
	 *   Else
	 *   False
	 *
	 *   The Method fetches the Entity corresponding to the
	 *   MailTag ,Event Code and the Port for all these Events .
	 *   Then the Event Specific Checks are Done .
	 *
	 *   If No Results From Query we can Flag the Resdit
	 *
	 *   If the Returned Resdit Mailidr and its Condition are same as the One Queried then Return False..
	 *   Else
	 *   If the Event Specific Conditions are Different and the RDTSND Flag is 'N' then remove that and can Resdit send is True.
	 *   else if RDTSND Flag is 'Y' set the Can Flag Resdit Flag tio True
	 *
	 */
/*/**************Modified by JOji at TRV on 03-Jun-08 to avoid object queries without updates
*********************//////////////
	private boolean canFlagResditForEvent(String eventCode,MailResditVO mailResditVO,
			MailbagVO mailbagVO) throws SystemException {
	 	      log.entering(CLASS, "canFlagResditForEvent");
		      boolean canFlag = false;
		      /*
		       * Sets the Event-Code to the Mail-Resdit Vo..
		       */
		       mailResditVO.setEventCode(eventCode);
		       mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());

		       /*
		        * This method does whether RESDIT exists or not
		        */
		      if(MailConstantsVO.RESDIT_ASSIGNED .equals(mailResditVO.getEventCode())
					    || MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())
					    || MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())){
					//Modified by Joji on 25-Mar for found mailbag
					if(MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())) {
						if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
		        	canFlag= true;
						}
		    	} else {
						//modified by Joji at JP  on 12-Mar-08 bug 7069 and 7208

						Collection<MailResditVO> mailResditVOs =
									MailResdit.findResditFlightDetailsForMailbag(mailResditVO);
						  if(mailResditVOs == null ||  mailResditVOs.size() == 0){
		    	    	canFlag= true;
						  } else {
							  boolean hasMatch = false;
							  for(MailResditVO flightMalResditVO : mailResditVOs) {
								  if(mailResditVO.getEventAirport().equals(flightMalResditVO.getEventAirport())) {
									  hasMatch = true;
									  if(flightMalResditVO.getFlightNumber().equals(mailResditVO.getFlightNumber())&&
											  flightMalResditVO.getCarrierId()==mailResditVO.getCarrierId() &&
											  flightMalResditVO.getFlightSequenceNumber()==mailResditVO.getFlightSequenceNumber() &&
											  flightMalResditVO.getSegmentSerialNumber()== mailResditVO.getSegmentSerialNumber()){
										  	canFlag=false;
									  }else{
										  /*
										   * if already present for another flight then
										   * if that RESDIT already sent then send this resdit again
										   * or else if that RESDIT not sent, then delete that event
										   * and send again.
										   */
											  canFlag = true;
											  if(MailConstantsVO.FLAG_NO.equals(flightMalResditVO.getResditSentFlag())){
												  // flightMalResdit.remove();

												  flightMalResditVO.setCompanyCode(mailResditVO.getCompanyCode());
												  flightMalResditVO.setMailId(mailResditVO.getMailId());
												  flightMalResditVO.setEventCode(mailResditVO.getEventCode());

												  List<MailResdit> mailResditsForFlight  = MailResdit.findMailResdits(flightMalResditVO);
												  if(mailResditsForFlight != null && mailResditsForFlight.size() > 0) {
													  MailResdit mailResditForFlight = mailResditsForFlight.get(0);
													  if(!MailConstantsVO.FLAG_YES.equals(
															  mailResditForFlight.getProcessedStatus())) {
														  //otherwise this might be in timer table
														  mailResditForFlight.remove();
													  }
												  }
											   } else {
															   //for nca even if different flight if it is already sent
															   //then no need to sent again. That different flight may be the
															   //copied cardit flight
												   //canFlag = false;
											   }
									  	}
								  }
							  }
							  //modified by Joji at JP on 25-Mar-08 bug 7208 for Second flight
							  if(!hasMatch) {
								  canFlag = true;
							  }
							  //	modified by Joji at JP on 12-Mar-2008 ends
						  }
					}
			   } else {
				   boolean isResditExisting = MailResdit.checkResditExists(mailResditVO, false);

		       if(MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())){
			  	     log.log(Log.FINE,"THE STATUS IS RECEIVED");

			  	     //for bug 88789. 74 need to be sent only from orgin port.
			  	     // chnaged as part of hotfix, 74 will be stamped , rest all validation to be done on procedure.

				  	     	if(isResditExisting) {
					  	    		/* String lastResditEvent = MailResdit
							     .findLatestResditEvent(mailbagVO);
				  	    		if (MailConstantsVO.RESDIT_HANDOVER_OFFLINE
										.equals(lastResditEvent)
										|| MailConstantsVO.RESDIT_RETURNED
												.equals(lastResditEvent)) {
									canFlag = true;
								}*/
				  	    	} else {
				  	    		canFlag = true;
				  	    		 /*LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
							  	   	String eventPort = mailResditVO.getEventAirport();
							  	   	String companyCode = logonAttributes.getCompanyCode();
							  	  Collection<String> OesNearToAirport=null;

							  	  OesNearToAirport=new MailController().findOfficeOfExchangeNearToAirport( companyCode,  eventPort);
							  	   	if(OesNearToAirport.contains(mailbagVO.getOoe())){
							  	   	canFlag = true;
							  	   	}*/

				  	    	}

				   } else {



						  /*
					    * All the Checks Required for the Returned ,Delivered,Pending ,HandedOverOffline
					    * are Done here
						   */
						 if (!isResditExisting) {
								   canFlag=true;
						  }
				   	}
			   }
			log.exiting(CLASS, "canFlagResditForEvent"+ canFlag);

		        return canFlag;
			     }


	/**
	 * ********************************
	 * 			This method is used only in cases
	 * where the business is according to UPU rules itself
	 * 	No configuration check is done here
	 * ************************************
	 * @author A-1739
	 * This method is used to flagResditForMailbags
	 * @param eventCode
	 * @param eventPort
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagResditForMailbags(String eventCode, String eventPort,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		log.entering(CLASS, "flagResditForMailbags");
		if (RESDIT_RECEIVED.equals(eventCode)) {
			flagReceivedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
			flagUpliftedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_ASSIGNED.equals(eventCode)) {
			flagAssignedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_PENDING.equals(eventCode)) {
			flagPendingResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_RETURNED.equals(eventCode)) {
			/*
			 * Added By RENO K ABRAHAM
			 * This code will do the configuration check for Return Flagging,done in two scenarios
			 *
			 * 1. For Latest status = (ACP or ASG or TRA )  and Arrived flag is "N"
			 * 	  then "Returned Resdit" should be sent at "Accept Mail Milestone"
			 *
			 * 2. For Latest status = (OFL)
			 * 	  then "Returned Resdit" should be sent at "Accept Mail Milestone"
			 *
			 * 3. For Latest status = (ACP) and Arrived flag is "N"
			 * 	  then "Returned Resdit" should be sent at "Arrive Mail Milestone" .
			 */
			MailbagVO mailbagVO = new ArrayList<MailbagVO>(mailbagVOs).get(0);
			String transactionId = null;
			String arrivedFlag=null;
			if(mailbagVO.getArrivedFlag()==null || mailbagVO.getArrivedFlag().equals(MailConstantsVO.FLAG_NO)){
				arrivedFlag=MailConstantsVO.FLAG_NO;
			}else{
				arrivedFlag=MailConstantsVO.FLAG_YES;
			}
			if (checkForResditConfig()) {
				Map<String, Object> valuesMap = new HashMap<String, Object>();
				valuesMap.put(RETN_MAILS, mailbagVOs);
				//Return Mail Milestone
					transactionId = MailConstantsVO.TXN_RET;
				//Flagging the RESDIT for Diff. Milestones
				flagConfiguredResdits(mailbagVO.getCompanyCode(), mailbagVO
						.getCarrierId(), transactionId, eventPort, mailbagVOs,
						valuesMap);
			} else {
				/*
				 * If Configuration Check is not needed then
				 * "Returned Resdit" need to be send.
				 */
				flagReturnedResditForMailbags(mailbagVOs);
			}
		}

		log.exiting(CLASS, "flagResditForMailbags");
	}

	   /**
	    * This method is used to flag the Pending Resdits for the Mail Bags
	    * @param mailbagVOs
	    * @param eventPort
	    * @throws SystemException
	    */
		private void flagPendingResditForMailbags(Collection<MailbagVO> mailbagVOs,
				String eventPort) throws SystemException {
			log.entering(CLASS, "flagPendingResditForMailbags");

			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

			String eventCode=null;
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);//added by A-8353 for ICRD-346641
		      //Added by A-6991 for ICRD-229260	Starts	
				String mailboxIdFromConfig = null;
				MailBoxId mailBoxId =null;
                String companyCode=mailbagVO.getCompanyCode();
                String officeOfExchange=mailbagVO.getOoe();
                
				
				try{
					//paCode = OfficeOfExchange.findPAForOfficeOfExchange(
				//			companyCode, officeOfExchange);
			//	PostalAdministration postalAdministration = PostalAdministration
				//				.find(companyCode,paCode);
					

				 
				mailboxIdFromConfig= MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);//IASCB-27875
					if(mailboxIdFromConfig!=null) {
						mailBoxId = MailBoxId.find(companyCode, mailboxIdFromConfig);
					}
				// remove already flagged unsent assigned
				 eventCode = (mailBoxId!=null && !MailConstantsVO.M49_1_1.equals(mailBoxId.getResditversion()))?
						MailConstantsVO.RESDIT_PENDING:MailConstantsVO.RESDIT_PENDING_M49;

				}catch(FinderException exception){
				throw new SystemException(exception.getMessage(),exception);	
				}
			 //Added by A-6991 for ICRD-229260	Ends				
				MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
						eventPort, eventCode, false);
				//Done for BUG 49175
				mailResditVO.setCarrierId(mailbagVO.getCarrierId());
				mailResditVOs.add(mailResditVO);
//				if(canFlagResditForEvent(eventCode, mailResditVO, mailbagVO)){
//					removeFlaggedUnsentResdit(mailbagVO, eventPort,
//							MailConstantsVO.RESDIT_ASSIGNED);
//					new MailResdit(mailResditVO);
//				}

			}
			mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
			stampResdits(mailResditVOs);
			log.exiting(CLASS, "flagPendingResditForMailbags");

		}

	    /**
	     * @author A-1936
	     * This method is used to flagResditsForULDAcceptance
	     * @param containerDetails
	     * @param assignedPort
	     * @throws SystemException
	     */
		public void flagResditsForULDAcceptance(
				Collection<ContainerDetailsVO> containerDetails,
				String assignedPort) throws SystemException {
			log.exiting(CLASS, "flagResditForULD");
				flagReceivedResditForUlds(containerDetails, assignedPort);

				flagAssignedResditForUlds(containerDetails, assignedPort);

				Collection<ContainerDetailsVO> handoverContainers =
					new ArrayList<ContainerDetailsVO>();
				for(ContainerDetailsVO containerDetailsVO : containerDetails) {
					if (!(containerDetailsVO.getCarrierCode().equals(
							containerDetailsVO.getOwnAirlineCode())) &&
							!validateCarrierCodeFromPartner(
									containerDetailsVO.getCompanyCode(),
									containerDetailsVO.getOwnAirlineCode(),
									assignedPort, containerDetailsVO.getCarrierCode())) {
						handoverContainers.add(containerDetailsVO);
					}
				}

				if(handoverContainers.size() > 0) {
					flagHandedOverResditForUlds(handoverContainers, assignedPort);
				}

			log.exiting(CLASS, "flagResditForULD");
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
					log.log(Log.FINE, "The Carrier Code is " + carrierCode);
					log.log(Log.FINE, "The  Partner Carrier Code is "
							+ partnerCarrierVO.getPartnerCarrierCode());
					if (carrierCode
							.equals(partnerCarrierVO.getPartnerCarrierCode())) {
						isValid = true;
						break;
					}
				}
			}
			log.log(Log.FINE, "<<<<Can HandedOverResditFlagged>>>>" + isValid);
			return isValid;
		}



		/**
		 * @author A-1936
		 * This method is used to flagPendingResditForUlds
		 * @param acceptedUlds
		 * @param pol
		 * @throws SystemException
		 */

		public void flagPendingResditForUlds(
				Collection<ContainerDetailsVO> acceptedUlds, String pol)
				throws SystemException {
			log.entering(CLASS, "flagPendingResditForUlds");
			UldResditVO uldResditVo = null;
			if (acceptedUlds != null && acceptedUlds.size() > 0) {
				for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
					log.log(Log.FINE, "The ContainerDetailsvo"+ containerDetailsVO);
					     uldResditVo = findLastAssignedResditForUld(containerDetailsVO,
							MailConstantsVO.RESDIT_ASSIGNED);
					     if (uldResditVo != null
							&& UldResditVO.FLAG_NO.equals(uldResditVo
									.getResditSentFlag())) {
						  findRemoveLastAssignedResditForUld(uldResditVo);
						  new UldResdit(constructUldResditVO(containerDetailsVO, pol,
								MailConstantsVO.RESDIT_PENDING));
					     }else {
					 	     new UldResdit(constructUldResditVO(containerDetailsVO, pol,
								MailConstantsVO.RESDIT_PENDING));
					}
				}
			}
			log.exiting(CLASS, "flagPendingResditForUlds");
		}

		/**
		 * @author A-1936 This method is used to findLastAssignedResditForUld.
		 * @param containerDetailVo
		 * @param eventCode
		 * @return
		 * @throws SystemException
		 */
		private UldResditVO findLastAssignedResditForUld(
				ContainerDetailsVO containerDetailVo, String eventCode)
				throws SystemException {
			return UldResdit.findLastAssignedResditForUld(containerDetailVo,
					eventCode);
		}

		/**
		 * @author A-1936 This method is used to find the LastAssignedResditForUld
		 * @param uldResditVO
		 * @return
		 * @throws SystemException
		 */
		private void findRemoveLastAssignedResditForUld(UldResditVO uldResditVO)
				throws SystemException {
			UldResditPK uldResditPK = new UldResditPK();
			uldResditPK.setCompanyCode(   uldResditVO.getCompanyCode());
			uldResditPK.setUldNumber(   uldResditVO.getUldNumber());
			uldResditPK.setEventCode(   uldResditVO.getEventCode());
			uldResditPK.setSequenceNumber(   uldResditVO.getResditSequenceNum());

			UldResdit uldResdit = null;
			try {
				uldResdit = UldResdit.find(uldResditPK);
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex);
			}
			uldResdit.remove();
		}
		/**
		 * @author A-1739
		 * This method is used to flagResditsForMailbagReassign
		 * @param mailbags
		 * @param toContainerVO
		 * @throws SystemException
		 */
			public void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags,
					ContainerVO toContainerVO) throws SystemException {
				log.entering(CLASS, "flagResditsForMailbagReassign");
				log.log(Log.FINE,"THE  DETAILS OF THE CONTAINER"+toContainerVO);

				 String eventPort = toContainerVO.getAssignedPort();
				 boolean isDeparted = false;
				// Added by A-8527 for IASCB-59243 starts
				 if (toContainerVO.getFlightStatus() == null) {
					 isDeparted = new MailController()
					.checkForDepartedFlight_Atd(toContainerVO);
				 } else {
					 // Added by A-8527 for IASCB-59243 Ends
					 isDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED
					.equals(toContainerVO.getFlightStatus());
				 }
				 boolean isFlightAssgn = false;
				 if(toContainerVO.getFlightSequenceNumber() > 0) {
					isFlightAssgn = true;
				 }
				 boolean isHandover = false;
				 if (toContainerVO.getOwnAirlineCode()!=null && !(toContainerVO.getCarrierCode().equals(
						toContainerVO.getOwnAirlineCode())) &&
						!validateCarrierCodeFromPartner(
								toContainerVO.getCompanyCode(),
								toContainerVO.getOwnAirlineCode(),
								eventPort, toContainerVO.getCarrierCode())) {
					isHandover = true;
				 }
				 if(checkForResditConfig()) {
		             Map<String, Object> valuesMap = new HashMap<String, Object>();
					 //Added By Karthick V  Starts
					 Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
					 Collection<MailbagVO> carrMailbags = new ArrayList<MailbagVO>();
					 //Added By Karthick V ends

					if(isFlightAssgn) {
						valuesMap.put(IS_FLTASG, isFlightAssgn);
					}
					/*
					 *
					 * Added By Karthick V to flag the Uplifted Resdits For all the
					 * Mail Bags that has been  reassigned  to a flight which has been marked as a DEPARTED
					 *
					 */
					if(isDeparted){
						 log.log(Log.FINE,"THE DEPARED STATUS "+isDeparted);
						 valuesMap.put(HAS_DEP, isDeparted);
					}


					if(!isHandover) {
						//NEXT wat if toContaine's flight has departed?
					}

					   // inventory save resdits NCA-CR
		               // Added By Karthick V Starts
					   /*
					    * Added By Karthick V to group the mailBags whether if that
					    * Mailbag is received from the PA or if that MailBag is received from the
					    * Carrier ...
					    * Group the MailBags on the same ...
					    *
					    */
						if( mailbags!=null && mailbags.size()>0){
							   groupPACarrMailbags(mailbags, paMailbags, null, null, null, null, null);
							   valuesMap.put(PA_MAILS, paMailbags);
							   valuesMap.put(CARR_MAILS, carrMailbags);
						}
					    //Added By Karthick V Ends

				 	 flagConfiguredResdits(toContainerVO.getCompanyCode(),
							toContainerVO.getCarrierId(), MailConstantsVO.TXN_ASG,
							eventPort, mailbags, valuesMap);


				  }else {
					if(isFlightAssgn) {
		                //ADDED  by KARTHICK.V AS THE PART OF NCA-CR STARTS
						// flagReceivedResditForMailbags(mailbags, eventPort);
		                //ADDED BY KARTHICK V  AS THE PART OF NCA-CR ENDS
						flagAssignedResditForMailbags(mailbags, eventPort);
						//ADDED AS A PART OF ANZ CR
						flagLoadedResditForMailbags(mailbags, eventPort);
					}
					if (isHandover) {
						flagHandedOverResditForMailbags(mailbags, eventPort);
					}

				    if(isDeparted){
				    	flagUpliftedResditForMailbags(mailbags, eventPort);
				    }

				}

				log.exiting(CLASS, "flagResditsForMailbagReassign");
			}




			   /**
		     * This method is used to flagResditForContainerReassign
		     * @param mailbagVOs
		     * @param ulds
		     * @param toFlightVO
		     * @param hasFlightDeparted
		     * @throws SystemException
		     */
			public void flagResditForContainerReassign(Collection<MailbagVO> mailbagVOs,
					Collection<ContainerDetailsVO> ulds, OperationalFlightVO toFlightVO,
					boolean hasFlightDeparted) throws SystemException {

				log.entering(CLASS, "flagResditForContainerReassign");
				String eventPort = toFlightVO.getPol();

				boolean isFlightAsgn = false;
				if(toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
					isFlightAsgn = true;
				}
				boolean isHandover = false;
				if(!toFlightVO.getCarrierCode().equals(toFlightVO.getOwnAirlineCode()) &&
						!validateCarrierCodeFromPartner(toFlightVO.getCompanyCode(),
								toFlightVO.getOwnAirlineCode(), eventPort,
								toFlightVO.getCarrierCode())) {
					isHandover = true;
				}
				if(checkForResditConfig()) {
		  			 Map<String, Object> valuesMap = new HashMap<String, Object>();

		  			 //Added By Karthick V  Startsz
					 Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
					 Collection<MailbagVO> carrMailbags = new ArrayList<MailbagVO>();
					 //Added By Karthick V ends

					 valuesMap.put(ACP_ULDS, ulds);
					 if(!isHandover) {
						valuesMap.put(HAS_DEP, hasFlightDeparted);
					 }
					 valuesMap.put(IS_FLTASG, isFlightAsgn);
					    //Added By Karthick V Starts
					   /*
					    * Added By Karthick V to group the mailBags whether if that
					    * Mailbag is received from the PA or if that MailBag is received from the
					    * Carrier ...
					    * Group the MailBags on the same ...
					    *
					    */
						if( mailbagVOs!=null && mailbagVOs.size()>0){
							   groupPACarrMailbags(mailbagVOs, paMailbags, null, null, null, null, null);
							   valuesMap.put(PA_MAILS, paMailbags);
							   valuesMap.put(CARR_MAILS, carrMailbags);
						}
					    //Added By Karthick V Ends

					 flagConfiguredResdits(toFlightVO.getCompanyCode(),
							 toFlightVO.getCarrierId(), MailConstantsVO.TXN_ASG,
							 eventPort, mailbagVOs, valuesMap);

					if(isHandover) {
						//flag for own arl's config
					}
				} else {
					if(isFlightAsgn) {
						//ADDED AS THE PART OF NCA-CR STARTS
						//flagReceivedResditForMailbags(mailbagVOs,eventPort);
						//Added by A-5201 for ICRD-90701
						flagLoadedResditForMailbags(mailbagVOs, eventPort);
//						ADDED AS THE PART OF NCA-CR ENDS
						flagAssignedResditForUlds(ulds, eventPort);
						flagAssignedResditForMailbags(mailbagVOs, eventPort);
					}

					if(isHandover) {
						flagHandedOverResditForUlds(ulds, eventPort);
						flagHandedOverResditForMailbags(mailbagVOs, eventPort);
					} else {
						//send fligh
						if(hasFlightDeparted) {
							flagUpliftedResditForUlds(ulds, eventPort);
							if (toFlightVO.getActualTimeOfDeparture() != null) {
								for (MailbagVO mailbagVO : mailbagVOs) {
									mailbagVO.setResditEventDate(toFlightVO.getActualTimeOfDeparture());
								}
							}
							flagUpliftedResditForMailbags(mailbagVOs, eventPort);
						}
					}
				}
				log.exiting(CLASS, "flagResditForContainerReassign");
			}

			/**
			 * Flags RESDITs on flight departure
			 * NEXT ULDs are not flagged yet
			 * Feb 2, 2007, A-1739
			 * @param companyCode
			 * @param carrierId
			 * @param mailbagVOs
			 * @param eventPort
			 * @throws SystemException
			 */
			public void flagResditsForFlightDeparture(String companyCode, int carrierId,
					Collection<MailbagVO> mailbagVOs,Collection < ContainerDetailsVO> containerDetailsVOs,
						String eventPort) throws SystemException {
				log.entering(CLASS, "flagResditsForFlightDeparture");
				if(checkForResditConfig()) {
					Map<String, Object> valuesMap = new HashMap<String, Object>();
					//NEXT check for handover

					//NEXT put in asgnflag
					valuesMap.put(HAS_DEP, true);
					valuesMap.put(ACP_ULDS, containerDetailsVOs);
					flagConfiguredResdits(companyCode, carrierId, MailConstantsVO.TXN_DEP,
							eventPort, mailbagVOs, valuesMap);
				} else {
					flagUpliftedResditForMailbags(mailbagVOs, eventPort);
					flagUpliftedResditForUlds(containerDetailsVOs, eventPort);
				}
				log.exiting(CLASS, "flagResditsForFlightDeparture");
			}




			/**
			 * TODO Purpose
			 * Feb 6, 2007, A-1739
			 * @param transferredMails
			 * @param containerVOs
			 * @param operationalFlightVO
			 * @return
			 */
			private Collection<MailbagVO> groupOnlineMailbagsInULD(
					Collection<MailbagVO> transferredMails,
					Collection<ContainerVO> containerVOs,
					OperationalFlightVO operationalFlightVO) {
				log.entering(CLASS, "groupOnlineTransferMailbags");
				Collection<MailbagVO> mailbagsForOnline =
					new ArrayList<MailbagVO>();
				for(MailbagVO mailbagVO : transferredMails) {
					for(ContainerVO containerVO : containerVOs) {
						if(containerVO.getContainerNumber().equals(
								mailbagVO.getUldNumber())) {
							/*
							 * Commented For Sending Handedover Online RESDIT For Other Carrier
							 * Discusssed with Roopak
							 */

//							if(containerVO.getCarrierId() ==
//								operationalFlightVO.getCarrierId()) {
//								if(!containerVO.getFlightNumber().equals(
//										operationalFlightVO.getFlightNumber()) ||
//										(containerVO.getFlightSequenceNumber() !=
//											operationalFlightVO.getFlightSequenceNumber())) {
									mailbagsForOnline.add(mailbagVO);
//								}
//							} else {
//								//GHA mode //TODO more discussion needed
//								//handling other carriers
//							}
							break;
						}
					}
				}
				log.exiting(CLASS, "groupOnlineTransferMailbags");
				return mailbagsForOnline;
			}


			/**
			 * @author A-1876 This method is used to construct the ContainerDetailsVO from the
			 *         containerVO
			 * @param containerInInventoryListVO
			 * @param eventCode
			 * @return UldResditVO
			 */
			private ContainerDetailsVO constructContainerDetailsVO(ContainerVO containerVO) {
				log.entering(CLASS, "constructContainerDetailsVO");

				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();

				containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		        containerDetailsVO.setCarrierId(containerVO.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			    containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
				containerDetailsVO.setContainerType(containerVO.getType());
				containerDetailsVO.setContainerJnyId(containerVO.getContainerJnyID());
				containerDetailsVO.setPaCode(containerVO.getShipperBuiltCode());

				log.exiting(CLASS, "constructContainerDetailsVO");
				return containerDetailsVO;
			}

			/**
			 *
			 * 	Method		:	ResditController.flagArrivedResditForULDs
			 *	Added by 	:
			 * 	Used for 	:
			 *	Parameters	:	@param uldResditVOs
			 *	Parameters	:	@param airportCode
			 *	Parameters	:	@throws SystemException
			 *	Return type	: 	void
			 */
			private void flagArrivedResditForULDs(
				Collection<UldResditVO> uldResditVOs, String airportCode)
				throws SystemException {
				log.entering(CLASS, "flagReadyForDeliveryResditFor ULDs");
				String eventCode = MailConstantsVO.RESDIT_ARRIVED;
				if (uldResditVOs != null && uldResditVOs.size() > 0) {
					for (UldResditVO uldResditVO : uldResditVOs) {
						if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
							log.log(Log.INFO,"Flagging the RESDIT_ARRIVED");
							new UldResdit(uldResditVO);
						}
					}
				}
				log.exiting(CLASS, "flag ReadyForDeliveryResditForULDs");
			}

			/**
			 *
			 * 	Method		:	ResditController.flagArrivedResditForMailbags
			 *	Added by 	:
			 * 	Used for 	:
			 *	Parameters	:	@param arrivedMailbags
			 *	Parameters	:	@param airportCode
			 *	Parameters	:	@throws SystemException
			 *	Return type	: 	void
			 */
			public void flagArrivedResditForMailbags(
				Collection<MailbagVO> arrivedMailbags,
				String airportCode) throws SystemException {
				log.entering(CLASS, "flagArrivedResditForMailbags");
				HashMap<String, String> exgOfcPAMap = new HashMap<String, String> ();
				Collection<MailResditVO> r40MailResditVOs = new ArrayList<MailResditVO>();
				String eventCode = null;
				String paCode = null;
				MailResditVO mailResditVO = null;
				eventCode = MailConstantsVO.RESDIT_ARRIVED ;
				for (MailbagVO mailbagVO : arrivedMailbags) {
					paCode = exgOfcPAMap.get(mailbagVO.getOoe());
					if (paCode == null || paCode.length() <= 0){
						paCode = new MailController().findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(), mailbagVO.getOoe());
						exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
					}
					LocalDate scanDate=mailbagVO.getScannedDate();
					updateArrivalEventTimeForAA(mailbagVO);
					MailbagVO mailbagVo=null;
						try {
							mailbagVo = new MailController().constructOriginDestinationDetails(mailbagVO);
						} catch (SystemException e) {
							e.getMessage();
						}
						if(mailbagVo!=null){
						mailbagVO.setOrigin(mailbagVo.getOrigin());	 
						mailbagVO.setDestination(mailbagVo.getDestination());
						}
					mailResditVO =constructMailResditVO(mailbagVO, airportCode,eventCode, false);
					if(scanDate!=null){    
						mailbagVO.setScannedDate(scanDate);           
					}
					mailResditVO.setPaOrCarrierCode(paCode);
					r40MailResditVOs.add(mailResditVO);
				}
				if(r40MailResditVOs.size() >0){
					r40MailResditVOs = canFlagResditForEvents(eventCode, r40MailResditVOs, arrivedMailbags);
					stampResdits(r40MailResditVOs);
				}
				log.exiting(CLASS, "flagArrivedResditForMailbags");
			}



			/**
			 *
			 * 	Method		:	ResditController.flagArrivedResditResditForULD
			 *	Added by 	:
			 * 	Used for 	:
			 *	Parameters	:	@param arrivedContainers
			 *	Parameters	:	@param eventPort
			 *	Parameters	:	@throws SystemException
			 *	Return type	: 	void
			 */
			private void flagArrivedResditResditForULD(Collection<ContainerDetailsVO> arrivedContainers ,String eventPort)
			throws SystemException{
				Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
				if (arrivedContainers != null && arrivedContainers.size() > 0) {
					for (ContainerDetailsVO containerDetailsVO : arrivedContainers) {
						UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						eventPort, MailConstantsVO.RESDIT_ARRIVED);
						uldResditVOs.add(uldResditVO);
					}
					if (uldResditVOs.size() > 0) {
						flagArrivedResditForULDs(uldResditVOs,
						eventPort);
					}
				}
			}

			/**
			 * TODO Purpose
			 * Feb 6, 2007, A-1739
			 * @param transferredMails
			 * @param containerVOs
			 * @param operationalFlightVO
			 * @throws SystemException
			 */
			public void flagResditsForContainerTransfer(
					Collection<MailbagVO> transferredMails,
					Collection<ContainerVO> containerVOs,
					OperationalFlightVO operationalFlightVO) throws SystemException {
				log.entering(CLASS, "flagResditsForContainerTransfer");
				String carrierCode = operationalFlightVO.getCarrierCode();
				String ownAirlineCode = operationalFlightVO.getOwnAirlineCode();
				String eventPort = operationalFlightVO.getPol();
				String companyCode = operationalFlightVO.getCompanyCode();
				boolean isDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(operationalFlightVO.getFlightStatus())||operationalFlightVO.isAtdCaptured();
				boolean isOfflineHandover = false;
				Collection<MailbagVO> onlineTransferMails = null;
				if (!(carrierCode.equals(ownAirlineCode)) &&
						!validateCarrierCodeFromPartner(
								companyCode,
								ownAirlineCode, eventPort, carrierCode)) {
					isOfflineHandover = true;
				} else {
					onlineTransferMails = groupOnlineMailbagsInULD(
							transferredMails, containerVOs, operationalFlightVO);
				}

//				 Added By Roopak Starts
				Collection<ContainerDetailsVO> transferUlds = new ArrayList<ContainerDetailsVO>();
				if (containerVOs != null && containerVOs.size() > 0) {
					for (ContainerVO containerVO : containerVOs) {
						ContainerDetailsVO containerDetailsVO =
							constructContainerDetailsVO(containerVO);
						if (ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO
								.getPaBuiltFlag())
								&& MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
										.getContainerType())) {
							transferUlds.add(containerDetailsVO);
						}
					}

				}
				// Addded By Roopak Ends

				boolean isFlightAssigned = false;
				if(operationalFlightVO.getFlightSequenceNumber() > 0) {
					isFlightAssigned = true;
				}
				if(checkForResditConfig()) {
					Map<String, Object> valuesMap = new HashMap<String, Object>();
					valuesMap.put(ACP_ULDS, transferUlds);
					valuesMap.put(IS_FLTASG, isFlightAssigned);
					/*
					 * Added By Karthick V to add the Uplifted Resdits to be falgged ..
					 *
					 */
					if(isDeparted){
						log.log(Log.FINE,"THE DEPARTED STATUS"+isDeparted);
						valuesMap.put(HAS_DEP,isDeparted);
					}

					if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
						valuesMap.put(OLIN_MAILS, onlineTransferMails);
					}

					/**
					 * Added by Roopak for flagging resdit for PA ULD for
					 */
//					Collection<ContainerVO> paUlds = null;
//					if (containerVOs != null
//							&& containerVOs.size() > 0) {
//						paUlds = new ArrayList<ContainerVO>();
		//
//						for (ContainerVO containerVO : containerVOs) {
//							if (ContainerDetailsVO.FLAG_YES.equals(containerVO
//									.getPaBuiltFlag())
//									&& MailConstantsVO.ULD_TYPE.equals(containerVO
//											.getType())) {
//								paUlds.add(containerVO);
//							}
//						}
						//valuesMap.put(ACP_ULDS, paUlds);
//					}
					flagConfiguredResdits(companyCode,
							operationalFlightVO.getCarrierId(), MailConstantsVO.TXN_TSFR,
							eventPort, transferredMails, valuesMap);
					//TODO check for flightdeparture?

					if(isOfflineHandover) {
					    	flagConfiguredHandoverResdit(companyCode,
								operationalFlightVO.getOwnAirlineId(),
								MailConstantsVO.TXN_TSFR, eventPort, transferredMails, valuesMap);
					}

				} else {
					//Modified by A-7540 for ICRD-333458
					Collection<MailbagVO> updatedMailbagVOs=new ArrayList<MailbagVO>();
					
					try {							
						for(MailbagVO mailbagVO : transferredMails){
						MailbagVO copyMailbagVO = new MailbagVO();
						MailbagVO updMailbagVO=new MailbagVO();
						BeanHelper.copyProperties(copyMailbagVO, mailbagVO);			
						updMailbagVO = createMailbagVOsforArrivedResdit(copyMailbagVO);
						updatedMailbagVOs.add(updMailbagVO);
				    }
			      }
					catch (SystemException e) {
						log.log(Log.SEVERE, "System Exception Caught");
						e.getMessage();
					
				}
					flagArrivedResditForMailbags(updatedMailbagVOs, eventPort);
					flagArrivedResditResditForULD(transferUlds, eventPort);
					if(!operationalFlightVO.isTransferOutOperation()){
					if(isFlightAssigned) {
						flagAssignedResditForMailbags(transferredMails, eventPort);
						flagAssignedResditForUlds(transferUlds, eventPort);
						flagLoadedResditForMailbags(transferredMails, eventPort);
					}

					if (isOfflineHandover) {
						flagHandedOverResditForMailbags(transferredMails, eventPort);
						flagHandedOverResditForUlds(transferUlds, eventPort);
					}else{
						if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
							log.log(Log.FINEST, " flagging online h/ov ");
							flagOnlineHandedoverResditForMailbags(onlineTransferMails, eventPort);
						}
						//TODO Ulds
						/**
						 * Added By Roopak for sending Handover_Received Resdits for ULDs
						 */
						if(transferUlds != null && transferUlds.size() > 0) {
							flagOnlineHandedoverResditForUlds(transferUlds, eventPort);
						}
					}


					if(isDeparted){
						flagUpliftedResditForMailbags(transferredMails, eventPort);
						flagUpliftedResditForUlds(transferUlds, eventPort);
					}
				}
				}

				log.exiting(CLASS, "flagResditsForContainerTransfer");
			}


		     /**
		      * @author A-1936
		      *  This method  is used to construct the Mail Bag Vo with the latest assignments that is Required For the Flagging ...
		      * @param mailbagVO
		      * @param toContainerVo
		      * @return
		     * @throws SystemException 
		      */
			 private MailbagVO constructMailBagVoForResdits(MailbagVO mailbagVO,
			ContainerVO toContainerVo) throws SystemException {
				log.entering("Resdit Controoler", "constructMailBagVoForResdits");

		MailbagVO mailBagCopyVO = new MailbagVO();
		BeanHelper.copyProperties(mailBagCopyVO, mailbagVO);

		mailBagCopyVO.setScannedPort(toContainerVo.getAssignedPort());
		mailBagCopyVO.setCarrierCode(toContainerVo.getCarrierCode());
		mailBagCopyVO.setCarrierId(toContainerVo.getCarrierId());
		mailBagCopyVO.setFlightNumber(toContainerVo.getFlightNumber());
		mailBagCopyVO.setFlightSequenceNumber(toContainerVo.getFlightSequenceNumber());
		mailBagCopyVO.setSegmentSerialNumber(toContainerVo.getSegmentSerialNumber());
		mailBagCopyVO.setUldNumber(toContainerVo.getContainerNumber());
					 if(toContainerVo.getOperationTime()!=null){
			mailBagCopyVO.setScannedDate(toContainerVo.getOperationTime());
					 }
				 //Added by A-7540 for ICRD-345267 starts
		if (mailBagCopyVO.getOrigin() == null || mailBagCopyVO.getDestination() == null) {
					 MailbagVO mailBagVO = new MailbagVO();
						try {
				mailBagVO = new MailController().constructOriginDestinationDetails(mailBagCopyVO);
						} catch (SystemException e) {
							e.getMessage();
						}
			if (mailBagCopyVO.getOrigin() == null) {
				mailBagCopyVO.setOrigin(mailBagVO.getOrigin());
			}
			if (mailBagCopyVO.getDestination() == null) {
				mailBagCopyVO.setDestination(mailBagVO.getDestination());
			}
		}
					 
		log.log(Log.FINE, "THE CONSTRUCTED RESDITS FOR THE MAIL BAG " + mailBagCopyVO);
				 log.exiting(CLASS, "getMailResditVO");
		return mailBagCopyVO;
			 }

			





/**
			 * TODO Purpose  QF1410
			 * Dec 16, 2009, A-1876
			 * @param transferMailbags
			 * @param handoverRcvdMailbags
			 * @param handoverOnMailbags
			 * @param handoverOffMailbags
			 * @param ownAirlineCode
			 * @param transferCarrierCode
			 */
			private void identifyHandoverResditsForMailbags(Collection<MailbagVO> transferMailbags, Collection<MailbagVO> handoverRcvdMailbags,
					Collection<MailbagVO> handoverOnMailbags, Collection<MailbagVO> handoverOffMailbags, String ownAirlineCode,
					String transferCarrierCode, ContainerVO containerVO) throws SystemException {
				boolean checkflgForPartnerCarrier=false;
				boolean isPartnerCarrier=false;
				
				LogonAttributes logonAttributes=null;
				if(transferMailbags != null && transferMailbags.size() > 0){
					for(MailbagVO mailbagVO : transferMailbags) {

						if(mailbagVO.getCarrierCode() != null
							&& mailbagVO.getCarrierCode().trim().length() > 0
							&& ownAirlineCode != null && transferCarrierCode != null ){
					// Added as part of CRQ IASCB-44518 starts
					if (mailbagVO.getTransferFromCarrier() != null
							&& mailbagVO.getTransferFromCarrier().trim().length()>0
							&& !transferCarrierCode.equals(mailbagVO.getTransferFromCarrier())) {
						transferCarrierCode = mailbagVO.getTransferFromCarrier();
					}
					// Added as part of CRQ IASCB-44518 ends
								logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
								checkflgForPartnerCarrier=validateCarrierCodeFromPartner(mailbagVO.getCompanyCode(),ownAirlineCode,logonAttributes.getAirportCode(),transferCarrierCode);
				
					if (!ownAirlineCode.equals(mailbagVO.getCarrierCode())) {
						String carrierCode = mailbagVO.getCarrierCode();
						isPartnerCarrier = validateCarrierCodeFromPartner(mailbagVO.getCompanyCode(), ownAirlineCode,
								logonAttributes.getAirportCode(), carrierCode);
					}

							if(ownAirlineCode.equals(mailbagVO.getCarrierCode()) && transferCarrierCode.equals(mailbagVO.getCarrierCode())) {

								handoverOnMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
							}else if(ownAirlineCode.equals(transferCarrierCode) 
									&& !transferCarrierCode.equals(mailbagVO.getCarrierCode()) ) {   
//								Added for NAD Fix
								String fromCarrierCode = mailbagVO.getCarrierCode();
								mailbagVO = constructMailBagVoForResdits(mailbagVO,containerVO);
								mailbagVO.setTransferFromCarrier(fromCarrierCode);
								handoverRcvdMailbags.add(mailbagVO);


							}else if(!ownAirlineCode.equals(transferCarrierCode) &&  (ownAirlineCode.equals(mailbagVO.getCarrierCode()) || isPartnerCarrier)) {
								if(checkflgForPartnerCarrier)
								{
									handoverOnMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
								}
								 //Added as part of CRQ IASCB-44518 starts
								else if(!ownAirlineCode.equals(transferCarrierCode) && (ownAirlineCode.equals(mailbagVO.getCarrierCode())|| isPartnerCarrier)
										&& mailbagVO.getTransferFromCarrier() != null &&mailbagVO.getTransferFromCarrier().trim().length()>0){
									String fromCarrierCode = mailbagVO.getTransferFromCarrier();      
									mailbagVO = constructMailBagVoForResdits(mailbagVO,containerVO);
									mailbagVO.setTransferFromCarrier(fromCarrierCode);
									handoverRcvdMailbags.add(mailbagVO);
								} //Added as part of CRQ IASCB-44518 ends
								else
								{
								handoverOffMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
								}
							}else if(!ownAirlineCode.equals(transferCarrierCode) && !ownAirlineCode.equals(mailbagVO.getTransferFromCarrier())) {
								//Operation is between both OAL ; no resdit sent.
							}
						}
					}
				}
			}



			/**
			 *
			 * Feb 6, 2007, A-1739
			 * @param transferredMails
			 * @param containerVO
			 * @throws SystemException
			 */
			public void flagResditsForMailbagTransfer(
					Collection<MailbagVO> transferredMails, ContainerVO containerVO)
				throws SystemException {
				log.entering(CLASS, "flagResditsForTransfer");
				//NEXT flagOnlineHandoverResdits
				String carrierCode = containerVO.getCarrierCode();
				String ownAirlineCode = containerVO.getOwnAirlineCode();
				String eventPort = containerVO.getAssignedPort();
		        Collection<MailbagVO> newOnlineTransferredMails = null;
		        Collection<MailbagVO> newTransferredMails = null;
				boolean isFlightAssigned = false;
				if(containerVO.getFlightSequenceNumber() > 0) {
					isFlightAssigned = true;
				}
				// boolean isOfflineHandover = false;
				 boolean isDeparted = false;
				//Added by A-8527 for IASCB-54731 starts
				 if(containerVO.getFlightStatus()==null){
					 isDeparted=  new  MailController().checkForDepartedFlight_Atd( containerVO);	 
				 }else{
					//Added by A-8527 for IASCB-54731 Ends
				 isDeparted=	 MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(containerVO.getFlightStatus());
				 }
				//Collection<MailbagVO> onlineTransferMails = null;
				/*
				  * If the toFlight is a carrier other than the owncarrier or if it is not
				  * a partner of the ownairline then this an OFFLINE Transfer.
				  * If not then online transferred mails are to be checked which means
				  * whether mailbags are being move between different flights of this
				  * airline. Some mailbags may be transferred to the same flight..for these
				  * no onlinetransferRESDIT need to be flagged. For those moved to different
				  * flight we've to flagonlineRESDIT
				 */

				 Collection<MailbagVO> handoverRcvdMailbags = new ArrayList<MailbagVO>();
				 Collection<MailbagVO> handoverOnMailbags = new ArrayList<MailbagVO>();
				 Collection<MailbagVO> handoverOffMailbags = new ArrayList<MailbagVO>();

				 identifyHandoverResditsForMailbags(transferredMails, handoverRcvdMailbags,
							 handoverOnMailbags,  handoverOffMailbags,  ownAirlineCode, carrierCode, containerVO);


//				 if (!(carrierCode.equals(ownAirlineCode)) &&
//						!validateCarrierCodeFromPartner(containerVO.getCompanyCode(),
//								ownAirlineCode, eventPort, carrierCode)) {
//					isOfflineHandover = true;
//				} else {
//					onlineTransferMails =
//						groupOnlineTransferMailbags(transferredMails, containerVO);
//				}

				/*
				 * Added By Karthick V as the part of the NCA Mail Tracking Bug Fix ...
				 */
				if(transferredMails!=null  && transferredMails.size()>0){
					newTransferredMails = new ArrayList<MailbagVO>();
					for(MailbagVO mailBag : transferredMails){
						newTransferredMails.add(constructMailBagVoForResdits(mailBag,containerVO));
					}
				}

				if(checkForResditConfig()) {
					Map<String, Object> valuesMap = new HashMap<String, Object>();
					valuesMap.put(IS_FLTASG, isFlightAssigned);
				    	if(isDeparted){
				 		  valuesMap.put(HAS_DEP, isDeparted);
				 	   }

					if(handoverOnMailbags != null && handoverOnMailbags.size() > 0) {
						valuesMap.put(OLIN_MAILS, handoverOnMailbags);
					}
					flagConfiguredResdits(containerVO.getCompanyCode(),
							containerVO.getOwnAirlineId(), MailConstantsVO.TXN_TSFR,
							eventPort, newTransferredMails, valuesMap);
					//TODO check for flightdeparture?

					if(handoverOffMailbags != null && handoverOffMailbags.size() > 0) {
						flagConfiguredHandoverResdit(
								containerVO.getCompanyCode(),
								containerVO.getOwnAirlineId(),
								MailConstantsVO.TXN_TSFR, eventPort, handoverOffMailbags, valuesMap);
					}
				} else {


					// Changes for CR QF1410
					// Resdit 43
					if(handoverRcvdMailbags != null && handoverRcvdMailbags.size() > 0) {
						flagHandedoverReceivedForMailbags(handoverRcvdMailbags,eventPort);
					}
					//Resdit 41
					if(handoverOnMailbags != null && handoverOnMailbags.size() > 0 && !containerVO.isExportTransfer()) {
							flagOnlineHandedoverResditForMailbags(handoverOnMailbags, eventPort);
					}
					//Resdit 42
					if(handoverOffMailbags != null && handoverOffMailbags.size() > 0) {
						flagHandedOverResditForMailbags(handoverOffMailbags,eventPort);
					}

					if(isFlightAssigned &&!containerVO.isHandoverReceived()) {
						flagAssignedResditForMailbags(newTransferredMails,eventPort);
						//added for ICRD-82147
						flagLoadedResditForMailbags(newTransferredMails, eventPort);
					}

					if(isDeparted){
						flagUpliftedResditForMailbags(newTransferredMails, eventPort);
					}

				}
				log.exiting(CLASS, "flagResditsForTransfer");
			}


			/**
			 * TODO purpose
			 * @author A-1876
			 * @param companyCode
			 * @throws SystemException
			 */
			public void flagResditsForInventoryDelivery(
					Collection<ContainerInInventoryListVO> containerInInventoryListVOs,
					Collection<MailbagVO> mailbagVOs)
			throws SystemException {
			log.entering("ResditControler", "flagResditForInventoryDelivery");
			String eventPort = "";
			Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();

			if (containerInInventoryListVOs != null && containerInInventoryListVOs.size() > 0) {
				for (ContainerInInventoryListVO containerInInventoryListVO : containerInInventoryListVOs) {
					UldResditVO resditVO = constructUldResditVOForInventory(containerInInventoryListVO,
							MailConstantsVO.RESDIT_DELIVERED);
					uldResditVOs.add(resditVO);
					eventPort = containerInInventoryListVO.getCurrentAirport();
				}
			}

			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				flagDeliveredResditForULDs(uldResditVOs,eventPort);
			}

			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				MailbagVO mailbagVO = ((ArrayList<MailbagVO>)mailbagVOs).get(0);
				flagDeliveredResditForMailbags(mailbagVOs,
						mailbagVO.getScannedPort());
			}

			log.exiting("ResditControler", "flagResditForInventoryDelivery");
			}


			/**
			 * @author A-1876 This method is used to construct the UldResditVo from the
			 *         ContainerInInventoryListVO
			 * @param containerInInventoryListVO
			 * @param eventCode
			 * @return UldResditVO
			 */
			private UldResditVO constructUldResditVOForInventory
					(ContainerInInventoryListVO containerInInventoryListVO,String eventCode) {
				log.entering(CLASS, "constructUldResditVOForInventory");

				UldResditVO uldResditVO = new UldResditVO();
				uldResditVO.setCompanyCode(containerInInventoryListVO.getCompanyCode());
				uldResditVO.setEventAirport(containerInInventoryListVO.getCurrentAirport());
				uldResditVO.setEventCode(eventCode);
				uldResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
				uldResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
				uldResditVO.setUldNumber(containerInInventoryListVO.getUldNumber());
				uldResditVO.setEventDate(new LocalDate(containerInInventoryListVO.getCurrentAirport(), Location.ARP, true));

				log.exiting(CLASS, "constructUldResditVOForInventory");

				return uldResditVO;
			}



			/**
			 * @author A-1739
			 * This method is used to flagResditsForArrival
			 * @param mailArrivalVO
			 * @param arrivedMailbags
			 * @param arrivedContainers
			 * @throws SystemException
			 */
				public void flagResditsForArrival(MailArrivalVO mailArrivalVO,
						Collection<MailbagVO> arrivedMailbags,
						Collection<ContainerDetailsVO> arrivedContainers)
					throws SystemException {
			        log.entering(CLASS, "flagResditsForArrival");
			        log.entering(CLASS, "flagResditsForArrivedMailbags");
					Collection<MailbagVO> newMailbags = new ArrayList<MailbagVO>();
			        Collection<MailbagVO> deliveredMailbags = new ArrayList<MailbagVO>();
			        Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
			        Collection<ContainerDetailsVO> arrivedPABuiltContainers =new ArrayList<ContainerDetailsVO>();
			        Collection<ContainerDetailsVO> newContainers =
						new ArrayList<ContainerDetailsVO>();
			        Collection<ContainerDetailsVO> deliveredContainers =
						new ArrayList<ContainerDetailsVO>();
			        Collection<ContainerDetailsVO> readyForDeliveryContainers =
						new ArrayList<ContainerDetailsVO>();
			        Map<String, String> cityCache = new HashMap<String, String>();
			        LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
					for (MailbagVO mailbagVO : arrivedMailbags) {
						log.log(Log.FINE,"flagResditsForArrivedMailbags"+mailbagVO);
						if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
							//Commented for bug 85199. 74 need not be send for newly added mail bags.
							//newMailbags.add(mailbagVO);
						}
						
						Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
						String poaCode=null;
						MailbagPK mailbagPk = new MailbagPK();
						mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
						mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
								mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode())  );
						try {
							mailbagToFindPA = Mailbag.find(mailbagPk);
						} catch (FinderException e) {							
							e.getMessage();
						}
						if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
							poaCode=mailbagToFindPA.getPaCode();
						}
						else{
							OfficeOfExchangeVO originOfficeOfExchangeVO; 
							originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
							poaCode=originOfficeOfExchangeVO.getPoaCode();
						}//Added by A-8164 for ICRD-342541 ends
						
						if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO
								.getCompanyCode(), StringUtils.equals(mailArrivalVO.getMailSource(), MailConstantsVO.MRD)?mailArrivalVO.getAirportCode():
									logonAttributes.getAirportCode(), cityCache,poaCode,mailbagVO.getConsignmentDate())) {
							readyForDeliveryMailbags.add(mailbagVO);
						} else
						{
							//nothing to do
						}
						if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
							deliveredMailbags.add(mailbagVO);
						}
					 }
			        for (ContainerDetailsVO arrivedContainer : arrivedContainers) {
						/**
						 * added by roopak for SB ULD - sending resdit
						 */
						if (ContainerDetailsVO.FLAG_YES.equals(arrivedContainer.getPaBuiltFlag()) &&
							MailConstantsVO.ULD_TYPE.equals(arrivedContainer.getContainerType())) {
							arrivedPABuiltContainers.add(arrivedContainer);
							if (OPERATION_FLAG_INSERT.equals(arrivedContainer.getOperationFlag())) {
								newContainers.add(arrivedContainer);
							}

							if( logonAttributes.getAirportCode().equalsIgnoreCase(arrivedContainer.getDestination())){
								readyForDeliveryContainers.add(arrivedContainer);
							}

							if(arrivedContainer.getPou() != null){
								if(MailConstantsVO.FLAG_YES.equals(arrivedContainer.getDeliveredStatus())) {
									deliveredContainers.add(arrivedContainer);
								}
							}
						}
					}

					if(checkForResditConfig()) {
						Map<String, Object> valuesMap = new HashMap<String, Object>();
						if(newMailbags!=null && newMailbags.size()>0){
							valuesMap.put(NEWARR_MAILS, newMailbags);
						}
						if(deliveredMailbags!=null && deliveredMailbags.size()>0){
							valuesMap.put(DLVD_MAILS,deliveredMailbags);
						}
						valuesMap.put(IS_FLTASG, true);
						//since arrival was done, departure must have happened
						valuesMap.put(HAS_DEP, true);
						if(newContainers!=null && newContainers.size()>0){
							valuesMap.put(PA_ULDS, newContainers);
						}
						if(deliveredContainers!=null && deliveredContainers.size()>0){
							valuesMap.put(DLVD_ULDS, deliveredContainers);
						}
			           flagConfiguredResdits(mailArrivalVO.getCompanyCode(),
								mailArrivalVO.getCarrierId(), MailConstantsVO.TXN_ARR,
								mailArrivalVO.getAirportCode(), arrivedMailbags, valuesMap);
					 }else {
						  /*
							 * Added By RENO K ABRAHAM AS A PART OF AirNZ CR-504
							 * Earlier, " flagDeliveredResditForMailbags " was called
							 * without any priority to the Configuration.
							 */
						 //Commenting the below code,as resdit 23 needs to be triggered for IASCB-24072
						 //
						 if( !"AA".equals(logonAttributes.getCompanyCode())){    
								flagReadyForDeliveryResditForMailbags(readyForDeliveryMailbags,mailArrivalVO
										.getAirportCode());
									 flagReadyForDeliveryResditForULD(readyForDeliveryContainers,mailArrivalVO
												.getAirportCode());
								 }  					
						flagReceivedResditForMailbags(newMailbags, mailArrivalVO
								.getAirportCode());
						 flagArrivedResditForMailbags(arrivedMailbags,mailArrivalVO
						 .getAirportCode());
						/*flagReadyForDeliveryResditForMailbags(readyForDeliveryMailbags,mailArrivalVO
								.getAirportCode());*/
						flagDeliveredResditForMailbags(deliveredMailbags,
								mailArrivalVO.getAirportCode());
						flagReceivedResditForUlds(newContainers,mailArrivalVO
								.getAirportCode());
						 flagArrivedResditResditForULD(arrivedPABuiltContainers,mailArrivalVO
						 .getAirportCode());
						/*flagReadyForDeliveryResditForULD(readyForDeliveryContainers,mailArrivalVO
								.getAirportCode());*/
						flagDlvdResditsForUldFromArrival(deliveredContainers,mailArrivalVO
								.getAirportCode());
					}

					log.exiting(CLASS, "flagResditsForArrival");
				}

				/**
				 * @author A-3227
				 * @param doe
				 * @param companyCode
				 * @param deliveredPort
				 * @param cityCache
				 * @param poaCode 
				 * @return
				 * @throws SystemException
				 */
				public boolean isValidDeliveryAirport(String doe, String companyCode,
						String deliveredPort, Map<String, String> cityCache, String poaCode,LocalDate dspDate)
						throws SystemException{
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

					if (nearestAirport == null && nearestAirportToCity != null ) {// nearest arp not found in cache
						nearestAirport = nearestAirportToCity;
						if (nearestAirport != null) {
							cityCache.put(deliveryCityCode, nearestAirport);
						}
					}
					LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
					AirportValidationVO airportValidationVO = new SharedAreaProxy().validateAirportCode(
							logonAttributes.getCompanyCode(),deliveredPort);
					String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
                 //added by A-7371 as part of ICRD-273813 starts
				if(MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured)){
					Page<OfficeOfExchangeVO> destinationAirport=new MailController().findOfficeOfExchange(
							companyCode,doe,1);
					OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
					if(destinationAirport!=null && !destinationAirport.isEmpty()){
						officeOfExchangeVO = destinationAirport.iterator().next();
					}
					if(officeOfExchangeVO!=null && officeOfExchangeVO.getAirportCode()!=null && !officeOfExchangeVO.getAirportCode().isEmpty()){
						nearestAirport=officeOfExchangeVO.getAirportCode();
					}
					
					boolean coTerminusCheck= new MailController().validateCoterminusairports(nearestAirport, deliveredPort,MailConstantsVO.RESDIT_READYFOR_DELIVERY,poaCode,dspDate);
					if(coTerminusCheck){
						return true;
					}
                 }
				   //added by A-7371 as part of ICRD-273813 ends

					//null check for icrd-111914
					if(airportValidationVO != null && airportValidationVO.getCityCode()!= null){
						if(airportValidationVO.getCityCode().equals(deliveryCityCode)){
							log.log(Log.FINE, "inside city validation returning true");
							return true;
						}
						else
						{
							return false;
						}
					}else{
						return false;
					//return false;
					}
				}


				public void flagReadyForDeliveryResditForMailbags(
						Collection<MailbagVO> deliveredMailbags,
						String airportCode) throws SystemException {
					log.entering(CLASS, "flagReady ForDeliveryResditForMailbags");

					HashMap<String, String> exgOfcPAMap = new HashMap<String, String> ();
					Collection<MailResditVO> r23MailResditVOs = new ArrayList<MailResditVO>();
					String eventCode = null;
					String paCode = null;
					MailResditVO mailResditVO = null;
					eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY ;
					for (MailbagVO mailbagVO : deliveredMailbags) {
						paCode = exgOfcPAMap.get(mailbagVO.getOoe());
						 if (paCode == null || paCode.length() <= 0){
							 paCode = new MailController().findPAForOfficeOfExchange(
										mailbagVO.getCompanyCode(), mailbagVO.getOoe());
							 exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
						 }
						mailResditVO =constructMailResditVO(mailbagVO, airportCode,eventCode, false);
						    mailResditVO.setPaOrCarrierCode(paCode);
						    r23MailResditVOs.add(mailResditVO);
					}
					if(r23MailResditVOs!=null && r23MailResditVOs.size() >0){
					r23MailResditVOs = canFlagResditForEvents(eventCode, r23MailResditVOs, deliveredMailbags);
					stampResdits(r23MailResditVOs);
					}

					log.exiting(CLASS, "flagReadyFor DeliveryResditForMailbags");
				}

				private void flagReadyForDeliveryResditForULD(Collection<ContainerDetailsVO> deliveredContainers ,String eventPort)
						 throws SystemException{
							Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
							if (deliveredContainers != null && deliveredContainers.size() > 0) {
								for (ContainerDetailsVO containerDetailsVO : deliveredContainers) {
									UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
											eventPort, MailConstantsVO.RESDIT_READYFOR_DELIVERY);
									uldResditVOs.add(uldResditVO);
								}
								if (uldResditVOs != null && uldResditVOs.size() > 0) {
									flagReadyForDeliveryResditForULDs(uldResditVOs,
											eventPort);
								}
							}
						}

				private void flagReadyForDeliveryResditForULDs(
						Collection<UldResditVO> uldResditVOs, String airportCode)
					throws SystemException {
					log.entering(CLASS, "flagReadyForDeliveryResditForULDs");
					String eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY;
						if (uldResditVOs != null && uldResditVOs.size() > 0) {
							for (UldResditVO uldResditVO : uldResditVOs) {
								if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
									log.log(Log.INFO,"Flagging the RESDIT_READYFOR_DELIVERY");
									new UldResdit(uldResditVO);
								}
							}
						}
					log.exiting(CLASS, "flagReady ForDeliveryResditForULDs");
				}


				/**
				 * @author A-1876
				 * This method is used to flagReturnedResditForULDs
				 * @param unassignSBULDs
				 * @throws SystemException
				 */
				public void flagReturnedResditForULDs(
						Collection < ContainerDetailsVO> unassignSBULDs)
						throws SystemException {
					log.entering(CLASS, "flagReturnedResditForULDs");

					String eventCode = MailConstantsVO.RESDIT_RETURNED;
					if (unassignSBULDs != null && unassignSBULDs.size() > 0) {
						for (ContainerDetailsVO containerDetailsVO : unassignSBULDs) {
							UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
									containerDetailsVO.getPol(), eventCode);
							log.log(Log.FINE, " uldResditVO " + uldResditVO);
							if(canFlagResditForULDEvent(eventCode, uldResditVO)){
							     new UldResdit(uldResditVO);
							}
						}
					}

					log.exiting(CLASS, "flagReturnedResditForULDs");
				}

				/**
				 *  Feb 8, 2007, A-1739
				 *  Modified By Karthick V to incorporate  the Functionality
				 *  Sending the Resdits for  the  PA Built  ULd as Well.
				 *  Note:-
				 *   Currently the Resdits are not being flagged for the Ulds in the System as a  event of which
				 *   the following steps to be Done When Resdits to be Send for the ULD
				 *   1.Stamp  the Records in Mtkuldrdt  with the Prosta-Y and RDTSND--Y which
				 *   will be removed when the Resdits Flagging for theb ULD will be Done ...
				 * @param carditEnquiryVO
				 * @throws SystemException
				 * @throws ContainerAssignmentException
				 */
				public void sendResditMessages(CarditEnquiryVO carditEnquiryVO)
					throws SystemException,ContainerAssignmentException {
					 log.entering(CLASS, "send ResditMessage");
			          Collection<ContainerVO> containerVos = carditEnquiryVO.getContainerVos();
			            if(containerVos!=null && containerVos.size()>0){
			        	   updateSegmentDetailsForContainers(containerVos);
			        	   persistULDResdits(containerVos);
					    }
				      Collection<ResditMessageVO> resditMessageVOs =
						new Resdit().constructResditMessageVOs(carditEnquiryVO);
			          log.log(Log.FINEST, "constructed messages " + resditMessageVOs);
					  if(resditMessageVOs != null && resditMessageVOs.size() > 0) {
					 	  for(ResditMessageVO resditMessageVO : resditMessageVOs) {
					 		  
					 		//A-8061 Added for ICRD-82434 starts
							  /* sendResditMessage(resditMessageVO,
									   constructResditEventVOForMsg(resditMessageVO,
											carditEnquiryVO));*/
							   buildResdit(constructResditEventVOForMsg(resditMessageVO,
											carditEnquiryVO));
							 //A-8061 Added for ICRD-82434 ends   
							   
						}
					  }
					log.exiting(CLASS, "sendResdit Message");
				}


				private void sendResditMessage(ResditMessageVO resditMessageVO,
						Collection<ResditEventVO> resditEventVOs) throws SystemException {
					log.entering(CLASS, "send ResditMessage");
					log.log(Log.FINE, "resditMessage" + resditMessageVO);
					Collection<String> systemParameters = new ArrayList<String>();
					Collection<MessageVO>  messageVO = null;
					String msgTxt = null;
					systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
					systemParameters.add(USPS_INTERNATIONAL_PA);
					systemParameters.add(USPS_DOMESTIC_PA);
					
					HashMap<String, String> systemParameterMap =
							new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
					boolean isSendCCResditMessage =false;
					log.log(Log.FINE, "System Parameter For USPS ENHMNT is-->" + systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT));

					if (systemParameterMap != null
							&& (MailConstantsVO.FLAG_ACTIVE.equals (systemParameterMap.get (MailConstantsVO.SYSPAR_USPS_ENHMNT) )
									|| MailConstantsVO.FLAG_YES.equals (systemParameterMap.get (MailConstantsVO.SYSPAR_USPS_ENHMNT) ))
							&& RESDIT_TO_POST_US.equals (resditMessageVO.getRecipientID())
						) {
						log.log(Log.FINE, "Going to sendCCResditMessage" );
						 isSendCCResditMessage =sendCCResditMessage(resditMessageVO, resditEventVOs);
					}else{
		//to be changed by denny			//	resditMessageVO.setResditEventVOs(resditEventVOs);
						messageVO = new MsgBrokerMessageProxy().encodeAndSaveMessage(resditMessageVO);
						  //Modified as part of bug ICRD-138578 by A-5526-This is to handle mailbag history stampimg as per message sending status.
						//If message is sent successfully then only mailbag history will create for resdit else no need to insert resdit history.

				}
					if(messageVO!=null && !messageVO.isEmpty()){
						msgTxt = messageVO.iterator().next().getRawMessage();
					}























					
					if(resditEventVOs!=null && msgTxt!=null&& ( resditMessageVO.getRecipientID().equals(systemParameterMap.get (USPS_INTERNATIONAL_PA)) ||
							resditMessageVO.getRecipientID().equals(systemParameterMap.get (USPS_DOMESTIC_PA))) ){
					   	createMailResditMessage(resditEventVOs,messageVO,resditMessageVO);
					   	
					}


			        for(ResditEventVO resditEventVO : resditEventVOs) {
			        	/*resditEventVO.setMsgDetails(msgTxt);
			        	if(msgTxt!=null ){//Added as part of ICRD-334407
			        	if(msgTxt.length()>4000){
			        	resditEventVO.setMsgText(msgTxt.trim().substring(0, 3999));	
			        	}
			        	else{
			        	resditEventVO.setMsgText(msgTxt);
			        	}
			        	createMailResditMessage(resditEventVO,messageVO);
			        	}
			        	*/
						//remove from the RDTEVT table
						if(resditEventVO.getMessageSequenceNumber()>0){
							removeResditEvent(resditEventVO);
						}

					}
					log.exiting(CLASS, "sendResdit Message");
				}




				/**
				 * 	Method		:	ResditController.createMailResditMessage
				 *	Added by 	:	A-4809 on Jun 6, 2019
				 * 	Used for 	:
				 *	Parameters	:	@param resditEventVO 
				 *	Return type	: 	void
				 * @param resditMessageVO 
				 */
				private void createMailResditMessage(Collection<ResditEventVO> resditEventVOs,Collection<MessageVO> messageVOs, ResditMessageVO resditMessageVO) throws SystemException {
					/*MailResditMessage mailResditMessage = null;
					try {
						mailResditMessage = MailResditMessage.find(constructMailResditMessagePK(resditEventVOs,messageVOs));
					} catch (FinderException e) {
						log.log(Log.FINE, "Creating MailResditMessage");*/
						
					MailResditMessage mailResditMessage=new MailResditMessage(MailtrackingDefaultsVOConverter.populateMailResditMessageVO(resditEventVOs, messageVOs));
					if(mailResditMessage!=null && resditEventVOs!=null && resditEventVOs.size()>0){
						long messageIdentifier=mailResditMessage.getMailResditMessagePK().getMessageIdentifier()>0?mailResditMessage.getMailResditMessagePK().getMessageIdentifier():0;
						
							//new MailtrackingDefaultsProxy().updateMessageIdentifierOfResditMessage(messageIdentifier,resditEventVOs,resditMessageVO);
						updateMessageIdentifierOfResditMessage(messageIdentifier,resditEventVOs,resditMessageVO);
						
					
					}   
				}
					
				
				/**
				 * 	Method		:	ResditController.constructMailResditMessagePK
				 *	Added by 	:	A-4809 on Jun 6, 2019
				 * 	Used for 	:
				 *	Parameters	:	@param resditEventVO
				 *	Parameters	:	@return 
				 *	Return type	: 	MailResditMessagePK
				 */
				private MailResditMessagePK constructMailResditMessagePK(Collection<ResditEventVO> resditEventVOs,Collection<MessageVO> messageVOs) {
					MailResditMessagePK mailResditMessagePK = new MailResditMessagePK();
					mailResditMessagePK.setCompanyCode(resditEventVOs.iterator().next().getCompanyCode());
					//mailResditMessagePK.setMessageIdentifier(messageVOs.iterator().next().getMessageIdentifierTwo());
					return mailResditMessagePK;
				}
				
				
				/**
				 * TODO Purpose
				 * Sep 22, 2006, a-1739
				 * @param resditEventVO
				 * @return
				 */
				private ResditEventPK constructResditEventPK(ResditEventVO resditEventVO) {
					ResditEventPK resditEventPK = new ResditEventPK();
					resditEventPK.setCompanyCode(   resditEventVO.getCompanyCode());
					resditEventPK.setConsignmentDocumentNumber(
						resditEventVO.getConsignmentNumber());
					if(resditEventVO.getActualResditEvent() != null) {
						resditEventPK.setEventCode(   resditEventVO.getActualResditEvent());
					} else {
						resditEventPK.setEventCode(   resditEventVO.getResditEventCode());
					}
					resditEventPK.setEventPort(   resditEventVO.getEventPort());
					resditEventPK.setMessageSequenceNumber(
						resditEventVO.getMessageSequenceNumber());
					return resditEventPK;
				}


				/**
				 * TODO Purpose
				 * Sep 22, 2006, a-1739
				 * @param resditEventVO
				 * @throws SystemException
				 */
				private void removeResditEvent(ResditEventVO resditEventVO)
				throws SystemException {
					log.entering(CLASS, "removeResditEvent");
					try {
						ResditEvent resditEvent = ResditEvent.find(
								constructResditEventPK(resditEventVO));
						resditEvent.remove();
					} catch (FinderException ex) {
						//ignore might be deleted manual/job sending
//						throw new SystemException(ex.getMessage(), ex);
					}
					log.exiting(CLASS, "removeResditEvent");

				}



				/* * Method to identify recepient for  resdit messages to USPS depending on Contract reference number from cardit.
				 * If no cardit resdit send to USPS directly.
				 * @param resditMessageVO
				 * @param resditEventVOs
				 * @return
				 * @throws SystemException
				 */
				private boolean sendCCResditMessage (ResditMessageVO resditMessageVO,
						Collection<ResditEventVO> resditEventVOs) throws SystemException {

					log.entering(CLASS, "sendCCResditMessage");
					Collection<ConsignmentInformationVO> consignmentInformationVOsForPA
					= new ArrayList<ConsignmentInformationVO>();
					Collection<ConsignmentInformationVO> consignmentInformationVOsForOtherAirline
							= new ArrayList<ConsignmentInformationVO>();
					Collection<ConsignmentInformationVO> consignmentInformationVOsForRestrictedAirlines
										= new ArrayList<ConsignmentInformationVO>();
					Collection<ConsignmentInformationVO> consignmentInformationVOsForXX
					= new ArrayList<ConsignmentInformationVO>();

					HashMap<String, String> xxResditRecepientMap = null;
					Collection<String> oneTimeList = new ArrayList<String>();
					oneTimeList.add(RESDIT_EXD_AIRLINE_LIST);

					String contractReference = null;
					String contractedAirline = null;

					if (resditMessageVO.getConsignmentInformationVOs() != null
							&& resditMessageVO.getConsignmentInformationVOs().size() > 0) {

						Map oneTimesMap = null;
						try {

							oneTimesMap = new SharedDefaultsProxy().findOneTimeValues(resditMessageVO.getCompanyCode(),
									oneTimeList);
							log.log(Log.FINEST, "\n\n RESDIT_TO_AIRLINE hash map******************" + oneTimesMap);

						} catch (ProxyException proxyException) {
							throw new SystemException(proxyException.getMessage());
						}

						Collection<String> resditToExcludeAirlineList = new ArrayList<String>();
						if(oneTimesMap!=null){
							Collection<OneTimeVO> resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap.get(RESDIT_EXD_AIRLINE_LIST);
							for(OneTimeVO oneTimeVO : resditToAirlineConfigList){
								resditToExcludeAirlineList.add(oneTimeVO.getFieldValue());
							}
						}

						for (ConsignmentInformationVO consignmentInformationVO : resditMessageVO.getConsignmentInformationVOs()) {

							if(consignmentInformationVO.getConsignmentID().startsWith(MailConstantsVO.RESDIT_XX) ){
								//For mails without cardit sending the Resdit to US post Directly
								consignmentInformationVOsForXX.add(consignmentInformationVO);
							}else {

//								find the cardit reference number
								Collection<CarditReferenceInformationVO> carditRefInfoVos = Resdit.findCCForSendResdit(consignmentInformationVO);

								//IF the cardit refernce number ends with AA resdit need to be send to AA
								//iF it ends with QF or other airlines Resdit needs to be send to US post
								if(carditRefInfoVos !=null && carditRefInfoVos.size() >0 ){
									if((MailConstantsVO.RESDIT_DELIVERED.equals(consignmentInformationVO.getConsignmentEvent())
											|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(consignmentInformationVO.getConsignmentEvent()))
									){
										for(CarditReferenceInformationVO carditRefInfoVo :carditRefInfoVos ){

											if (consignmentInformationVO.getTransferLocation() != null
													&& consignmentInformationVO.getTransferLocation().equals(carditRefInfoVo.getDestination())){

												if(carditRefInfoVo.getContractRef() !=null && carditRefInfoVo.getContractRef().length() >0){
													contractReference = carditRefInfoVo.getContractRef();
												}else{
													//if legwise contract reffnum is null then contract ref or AHI is used
													contractReference = carditRefInfoVo.getRefNumber();
												}
												contractedAirline = contractReference == null ? null
														: contractReference.substring(contractReference.length() - 2, contractReference.length());
												log.log(Log.FINE, "Inside sendCCResditMessage-->contractedAirline is--" + contractedAirline);
												if(contractedAirline !=null && contractedAirline.length()>0 ){
													if(RESDIT_TO_AIRLINE_AA.equals(contractedAirline) ){
														consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
													}else if (resditToExcludeAirlineList.contains(contractedAirline)){
														consignmentInformationVOsForRestrictedAirlines.add(consignmentInformationVO);
													}else{
														consignmentInformationVOsForPA.add(consignmentInformationVO);
													}
												}
												break;
											}
										}
									}else {
										for(CarditReferenceInformationVO carditRefInfoVO :carditRefInfoVos ){
											if(consignmentInformationVO.getTransferLocation() != null
													&& consignmentInformationVO.getTransferLocation().equals(carditRefInfoVO.getOrgin())){
												if(carditRefInfoVO.getContractRef() !=null && carditRefInfoVO.getContractRef().length() >0){
													contractReference = carditRefInfoVO.getContractRef();
												}else{
													//if legwise contract reffnum is null then contract ref or AHI is used
													contractReference = carditRefInfoVO.getRefNumber();
												}
												contractReference = carditRefInfoVO.getRefNumber();
												contractedAirline = contractReference == null ? null
														: contractReference.substring(contractReference.length() - 2, contractReference.length());
												log.log(Log.FINE, "Inside sendCCResditMessage-->contractedAirline is--" + contractedAirline);
												if(contractedAirline !=null && contractedAirline.length()>0){
													if(RESDIT_TO_AIRLINE_AA.equals(contractedAirline) ){
														consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
													}else if (resditToExcludeAirlineList.contains(contractedAirline)){
														consignmentInformationVOsForRestrictedAirlines.add(consignmentInformationVO);
													}else {
														consignmentInformationVOsForPA.add(consignmentInformationVO);
													}
												}
												break;
											}
										}
									}
								}else {
									//if cardit ref number is null then sending the resdit to US post
									consignmentInformationVOsForPA.add(consignmentInformationVO);
								}
							}
						}
					}

					if (consignmentInformationVOsForXX !=null && consignmentInformationVOsForXX.size() > 0){

						xxResditRecepientMap = Resdit.findRecepientForXXResdits(consignmentInformationVOsForXX);
						String addrParty = null;

						for (ConsignmentInformationVO consignmentInformationVO : consignmentInformationVOsForXX){
							addrParty = xxResditRecepientMap.get(consignmentInformationVO.getConsignmentID());

							if (addrParty != null && RESDIT_TO_AIRLINE_AA.equals(addrParty)){
								consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
							}else{
								consignmentInformationVOsForPA.add(consignmentInformationVO);
							}
						}
					}


					if (consignmentInformationVOsForPA !=null && consignmentInformationVOsForPA.size()>0){

						log.log(Log.FINE, "Inside sendCCResditMessage-->consignmentInformationVOsForAirline is--"
								+ consignmentInformationVOsForPA);
						ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
						BeanHelper.copyProperties(resditMessageCopyVO, resditMessageVO);

						resditMessageCopyVO.setConsignmentInformationVOs (consignmentInformationVOsForPA);
						log.log(Log.FINE, "Inside sendCCResditMessage-->resdit is to airline"+resditMessageVO.getResditToAirlineCode());
						new MsgBrokerMessageProxy().encodeAndSaveMessage(resditMessageCopyVO);
						saveResditFileLogs(resditMessageCopyVO);
						postResditSendProcess (resditMessageCopyVO, resditEventVOs);
					}
					if (consignmentInformationVOsForOtherAirline !=null && consignmentInformationVOsForOtherAirline.size()>0){
						log.log(Log.FINE, "Inside sendCCResditMessage-->consignmentInformationVOsForOtherAirline is--"
								 + consignmentInformationVOsForOtherAirline);
						ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
						BeanHelper.copyProperties(resditMessageCopyVO, resditMessageVO);
						resditMessageCopyVO.setRecipientID(RESDIT_TO_AIRLINE_AA);
						/*generateSequencesForResdit(resditMessageCopyVO, resditMessageVO.getRecipientID()); */
						resditMessageCopyVO.setResditToAirlineCode(RESDIT_TO_AIRLINE_AA);
						resditMessageCopyVO.setConsignmentInformationVOs (consignmentInformationVOsForOtherAirline);
						log.log(Log.FINE, "Inside sendCCResditMessage-->resdit is to PA"+resditMessageVO.getRecipientID());
						new MsgBrokerMessageProxy().encodeAndSaveMessage(resditMessageCopyVO);
						saveResditFileLogs(resditMessageCopyVO);
						postResditSendProcess (resditMessageCopyVO, resditEventVOs);
					}

					if (consignmentInformationVOsForRestrictedAirlines !=null && consignmentInformationVOsForRestrictedAirlines.size()>0){
						log.log(Log.FINE, "Inside sendCCResditMessage-->consignmentInformationVOsForOtherAirline is--"
								 + consignmentInformationVOsForRestrictedAirlines);
						ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
						BeanHelper.copyProperties(resditMessageCopyVO, resditMessageVO);

						resditMessageCopyVO.setResditToAirlineCode(RESDIT_TO_AIRLINE_JL);
						resditMessageCopyVO.setRecipientID(RESDIT_TO_AIRLINE_JL);
						/*generateSequencesForResdit(resditMessageCopyVO, resditMessageVO.getRecipientID());*/
						resditMessageCopyVO.setConsignmentInformationVOs (consignmentInformationVOsForRestrictedAirlines);
						log.log(Log.FINE, "Inside sendCCResditMessage-->resdit is to PA"+resditMessageVO.getRecipientID());
						new MsgBrokerMessageProxy().encodeAndSaveMessage(resditMessageCopyVO);
						saveResditFileLogs(resditMessageCopyVO);
						postResditSendProcess (resditMessageCopyVO, resditEventVOs);
					}
					log.exiting(CLASS, "sendCCResditMessage");
					return true;
				}




				/**
				 *
				 * @param resditMessageVO
				 * @return
				 */
				private  boolean canCreateHistoryForMailResdits(ResditMessageVO resditMessageVO){
					 boolean isValid =false;
					 Collection<ConsignmentInformationVO> consignments= resditMessageVO.getConsignmentInformationVOs();
					 if(consignments!= null && consignments.size()>0){
						 for(ConsignmentInformationVO consignmentInformationVo :consignments ){
							 if(consignmentInformationVo.getReceptacleInformationVOs()!=null &&
									 consignmentInformationVo.getReceptacleInformationVOs().size()>0){
								 isValid=true;
								 break;
							 }
						 }
					 }
					 return isValid;

				 }



				private MailResditPK constructMailResditPK(
						ResditEventVO resditEventVO, ReceptacleInformationVO receptacleVO) throws SystemException {
					MailResditPK mailResditPK = new MailResditPK();
					mailResditPK.setCompanyCode(   resditEventVO.getCompanyCode());
					if(resditEventVO.getActualResditEvent() != null) {
						mailResditPK.setEventCode(   resditEventVO.getActualResditEvent());
					} else {
						mailResditPK.setEventCode(   resditEventVO.getResditEventCode());
					}
					if(receptacleVO.getMailSequenceNumber()>0) {
						mailResditPK.setMailSequenceNumber(receptacleVO.getMailSequenceNumber());
					} else {
					mailResditPK.setMailSequenceNumber(findMailSequenceNumber(receptacleVO.getReceptacleID(), resditEventVO.getCompanyCode()));
					}
					//	mailResditPK.setMailId(   receptacleVO.getReceptacleID());
					mailResditPK.setSequenceNumber(
						receptacleVO.getEventSequenceNumber());
					return mailResditPK;
				}
				private long findMailSequenceNumber(String mailIdr,String companyCode) throws SystemException{
					return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
				}


				/**
				 * TODO Purpose
				 * Sep 22, 2006, a-1739
				 * @param resditEventVO
				 * @param resditMessageVO
				 * @throws SystemException
				 */
				private void updateResditStatus(ResditEventVO resditEventVO,
						ResditMessageVO resditMessageVO) throws SystemException {
					log.entering(CLASS, "updateResditStatus");

					Collection<ConsignmentInformationVO> consignments =
						resditMessageVO.getConsignmentInformationVOs();
					

					if(consignments != null && consignments.size() > 0) {
						for(ConsignmentInformationVO consignVO : consignments) {
							/*if(MailConstantsVO.RESDIT_PENDING_M49.equals(consignVO.getConsignmentEvent())){//added by a-7871 for ICRD-227878
								consignVO.setConsignmentEvent(MailConstantsVO.RESDIT_PENDING);
							}*/
							if(String.valueOf(consignVO.getConsignmentEvent()).equals(
								resditEventVO.getResditEventCode())) {

								Collection<ReceptacleInformationVO> receptacles =
									consignVO.getReceptacleInformationVOs();
								if(receptacles != null && receptacles.size() > 0) {
									for(ReceptacleInformationVO receptacleVO : receptacles) {
										try {
										MailResdit mailResdit = MailResdit.find(
												constructMailResditPK(resditEventVO, receptacleVO));


										//mailResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_SENT);
										mailResdit.setProcessedStatus(MailConstantsVO.FLAG_YES);

										/**
										 * Modified by A-2135 for QF CR 1517
										 */

										mailResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_GENERATED);
										mailResdit.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
                                        //Added by A-7540
										LocalDate sendDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
										GMTDate gmt =  sendDate.toGMTDate();
										mailResdit.setResditSenttime(gmt.toCalendar());
										/*if(resditMessageVO.getMessageReferenceNumber()!=null && mailResdit.getMessageIdentifier()==0)
										mailResdit.setMessageIdentifier(Long.parseLong(resditMessageVO.getMessageReferenceNumber()));
										*/
										setSenderAndRecipientIdentifier(resditEventVO, mailResdit);

										} catch(FinderException ex) {
											//throw new SystemException(ex.getMessage(), ex);
											log.log(Log.FINE, "Trying to update status for DERIVED Receptacle which came in SB ULD....just ignore exception!!!!"+ receptacleVO.getReceptacleID());
										}
									}
								}

								Collection<ContainerInformationVO> containers =
									consignVO.getContainerInformationVOs();
								if(containers != null && containers.size() > 0) {
									for(ContainerInformationVO containerVO : containers) {

										try {
											UldResdit uldResdit = UldResdit.find(
													constructULDResditPK(resditEventVO, containerVO));


											//uldResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_SENT);
											uldResdit.setProcessedStatus(MailConstantsVO.FLAG_YES);

											/**
											 * Modified by A-2135 for QF CR 1517
											 */
											uldResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_GENERATED);
											uldResdit.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());


										} catch(FinderException exception) {
											throw new SystemException(exception.getMessage(),
													exception);
										}
									}
								}
							}
						}
					}
					
log.exiting(CLASS, "updateResditStatus");

				}
				public void setSenderAndRecipientIdentifier(ResditEventVO resditEventVO, MailResdit mailResdit) {
					mailResdit.setSenderIdentifier(resditEventVO.getSenderIdentifier());
					mailResdit.setRecipientIdentifier(resditEventVO.getRecipientIdentifier());
				}


				/**
				 * TODO Purpose
				 * Sep 22, 2006, a-1739
				 * @param resditEventVO
				 * @param containerVO
				 * @return
				 */
				private UldResditPK constructULDResditPK(ResditEventVO resditEventVO,
						ContainerInformationVO containerVO) {
					UldResditPK uldResditPK = new UldResditPK();
					uldResditPK.setCompanyCode(   resditEventVO.getCompanyCode());
					uldResditPK.setEventCode(  resditEventVO.getResditEventCode());
					uldResditPK.setUldNumber(   containerVO.getContainerNumber());
					uldResditPK.setSequenceNumber(   containerVO.getEventSequenceNumber());
					return uldResditPK;
				}

				/**
				 *
				 * @param resditMessageVO
				 * @param resditEventVOs
				 * @throws SystemException
				 */
				private void postResditSendProcess(ResditMessageVO resditMessageVO,
						Collection<ResditEventVO> resditEventVOs)throws SystemException {

					log.exiting(CLASS, "postResditSendProcess");
					boolean canFlagHisForMailResdit= canCreateHistoryForMailResdits(resditMessageVO);
					HashSet<String> consignKeySet=new HashSet<String>();
			        for(ResditEventVO resditEventVO : resditEventVOs) {
						//set RDTSND flag to S for all  sent receptacles in MTKMAL/ULDRDT
						updateResditStatus(resditEventVO,resditMessageVO);
						//Flag history for resditevent
					    /*
					     *
					     * Added this check  to ignore the History   being flagged
					     * if  the Resdit Message Vo Contains the  Container Information alone ..
					     *
					     */
						if(canFlagHisForMailResdit){
						   createMailbagHistoryForResdit(resditEventVO, resditMessageVO,consignKeySet);
						}


					}
					log.exiting(CLASS, "postResditSendProcess");
				}

				/**
				 * Added by A-2135 for QF CR 1517
				 * Oct 06, 2010, a-2135
				 * @param resditEventVO
				 * @return
				 */
				private MailResditFileLogPK constructMailResditFileLogPK(ResditMessageVO resditMessageVO) {

					MailResditFileLogPK mailResditFileLogPK = new MailResditFileLogPK();

					mailResditFileLogPK.setCompanyCode(resditMessageVO.getCompanyCode());
					mailResditFileLogPK.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
					mailResditFileLogPK.setRecipientID(resditMessageVO.getRecipientID());
					return mailResditFileLogPK;
				}

				/**
			 * Added by A-2135 for QF CR 1517
			 * @param resditMessageVO
			 * @throws SystemException
			 */
			private void saveResditFileLogs(
					 ResditMessageVO resditMessageVO) throws SystemException {
				log.entering(CLASS, "SaveResditFileLogs");

				MailResditFileLog mailResditFileLog = null;
				try{
					 mailResditFileLog = MailResditFileLog.find(constructMailResditFileLogPK(resditMessageVO));

					 if(mailResditFileLog != null){

						if(mailResditFileLog.getCCList()!= null){
							StringBuilder ccList =  new StringBuilder(mailResditFileLog.getCCList());
						 mailResditFileLog.setCCList(ccList.append(',').append(resditMessageVO.getResditToAirlineCode()).toString());
						}

					 }

				}catch(FinderException exception){

					 mailResditFileLog = new MailResditFileLog(resditMessageVO);
				}
				log.exiting(CLASS, "SaveResditFileLogs");
			}




			/**
			 * 	Method		:	ResditController.constructConsignmentKey
			 *	Added by 	:
			 * 	Used for 	: Constructing ConsignmentKey
			 *	Parameters	:	@param consignVO
			 *	Return type	: 	String
			 * @param resditEventVO -Added as part of Bug ICRD-151874 by A-5526
			 * @param receptacleInformationVO
			 */
			private String constructConsignmentKey(ConsignmentInformationVO consignVO, ReceptacleInformationVO receptacleInformationVO){

				StringBuilder key=new StringBuilder();
				//Modified 0as part of Bug ICRD-151874 by A-5526
				key.append(consignVO.getConsignmentEvent()).append(consignVO.getConsignmentID()).append(receptacleInformationVO.getReceptacleID());

				Collection<TransportInformationVO> transportInfoVOs = consignVO.getTransportInformationVOs();
				TransportInformationVO transportVO = null;
				if(transportInfoVOs != null && transportInfoVOs.size() > 0) {
					transportVO = ((ArrayList<TransportInformationVO>) transportInfoVOs).get(0);
					if(transportVO != null) {
						key.append(transportVO.getCarrierCode()).append(
								transportVO.getFlightNumber()).append(transportVO.getFlightSequenceNumber());
					}
				}
				return key.toString();
			}

			/**
			 * TODO Purpose
			 * Sep 26, 2006, a-1739
			 * @param resditEventVO
			 * @param resditMessageVO
			 * @throws SystemException
			 */
			private void createMailbagHistoryForResdit(ResditEventVO resditEventVO,
					ResditMessageVO resditMessageVO,HashSet<String> consignKeySet) throws SystemException {
				log.entering(CLASS, "createMailbagHistoryForResdit");
				Collection<ConsignmentInformationVO> consignments =
					resditMessageVO.getConsignmentInformationVOs();
		
				/**
				 * Added by A-2135 for QF CR 1517
				 */
				resditEventVO.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
				log.log(Log.FINE,"Interchange Control Reference Number to be flagged in MTKMALHIS"
						+resditEventVO.getInterchangeControlReference());
				/**
				 * Added by A-2135 for QF CR 1517 ends
				 */
				String key= null;

				for(ConsignmentInformationVO consignVO : consignments) {
					if(consignVO.getConsignmentEvent()!=null && consignVO.getConsignmentEvent().trim().length()>0
							&& consignVO.getConsignmentID()!=null && consignVO.getConsignmentID().trim().length()>0
							&& String.valueOf(consignVO.getConsignmentEvent()).equals(resditEventVO.getResditEventCode())
							&& consignVO.getConsignmentID().equals(resditEventVO.getConsignmentNumber()) ) {
						//To avoid fetching same flight details-ICRD-98146
						//Modified as part of Bug ICRD-155226 by A-5526

						for(ReceptacleInformationVO receptacleInformationVO:consignVO.getReceptacleInformationVOs()){


						key=constructConsignmentKey(consignVO,receptacleInformationVO);

						/*Commented as part of bug ICRD-150240 by A-5526 after discussed with Sreejith PC to
						solve history not stamping issue for sent resdits (in nested cardit accepted bags)*/
						//Reverted the fix done for ICRD-150240as part of Bug ICRD-151874 by A-5526
						if(!(consignKeySet.contains(key))){
							consignKeySet.add(key);
							//Modified as part of Bug ICRD-155226 by A-5526
							new Mailbag().insertMailbagHistoryForResdit(resditEventVO, consignVO,receptacleInformationVO);
							MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
							mailController.createMailbagAuditForResdit(resditEventVO,consignVO,receptacleInformationVO);
							//break;
							}
						}
					}
				}

				log.exiting(CLASS, "createMailbagHistoryForResdit");
			}
				 /**
				 * TODO Purpose
				 * Feb 12, 2007, A-1739
				 * @param resditMessageVO
				 * @param carditEnquiryVO
				 * @return
				 */
				private Collection<ResditEventVO> constructResditEventVOForMsg(
						ResditMessageVO resditMessageVO, CarditEnquiryVO carditEnquiryVO) {
					Collection<ResditEventVO> resditEvents =
						new ArrayList<ResditEventVO>();
					Collection<ConsignmentInformationVO> consignInfoVOs =
						resditMessageVO.getConsignmentInformationVOs();
					for(ConsignmentInformationVO consignInfoVO : consignInfoVOs) {
						//only if this event was matched by the proc already
						//TODO wat if the  SEND uLD rESDIT IS THE FUNCATIONALITY
							ResditEventVO resditEventVO = new ResditEventVO();
							resditEventVO.setCompanyCode(
									resditMessageVO.getCompanyCode());
							resditEventVO.setResditEventCode(
									consignInfoVO.getConsignmentEvent());
							resditEventVO.setEventPort(resditMessageVO.getStationCode());
							resditEventVO.setConsignmentNumber(
									consignInfoVO.getConsignmentID());
							resditEventVO.setMessageSequenceNumber(
									consignInfoVO.getMessageSequenceNumber());
							
							//A-8061 Added for ICRD-82434 starts
							//resditEventVO.setResditVersion("1.0");
							resditEventVO.setPaCode(resditMessageVO.getRecipientID());
							resditEventVO.setCarditExist(resditMessageVO.getCarditExist());
							resditEventVO.setEventDate(new LocalDate(consignInfoVO.getEventDate(), resditEventVO.getEventPort(), Location.ARP));	
							
							String resditVersion="";
							try{
								
							MailBoxId mailBoxId = MailBoxId.find(carditEnquiryVO.getCompanyCode(), resditMessageVO.getMailboxId());
							resditVersion=mailBoxId.getResditversion();
							
							}catch(FinderException exception){
								log.log(Log.INFO, " error msg \n\n ", exception.getMessage());
							}
							catch(SystemException exception){
								log.log(Log.INFO, " error msg \n\n ", exception.getMessage());
							}
							resditEventVO.setResditVersion(resditVersion);
							
							if(MailConstantsVO.M49_1_1.equals(resditVersion))
							{
							resditEventVO.setM49Resdit(true);
							resditEventVO.setResditVersion("1.1");
							}
							
							//A-8061 Added for ICRD-82434 ends
							resditEvents.add(resditEventVO);

					}
					return resditEvents;
				}

				 /**
				  * @author A-1936
				  * This method is used to Persist the  Resdit Events for  the ULDs
				  * @param uldResdits
				 * @throws SystemException
				  */
				 private void  persistULDResdits(Collection<ContainerVO> containerVos)
				   throws SystemException{
					  log.entering("Resdit Controller","persistULDResdits");
					  UldResdit uldResdit = null;
					  for(ContainerVO containerVo : containerVos ){
						 UldResditVO uldResditVO = new UldResditVO();
						 uldResditVO.setCompanyCode(containerVo.getCompanyCode());
						 uldResditVO.setEventAirport(containerVo.getAssignedPort());
						 uldResditVO.setEventCode(containerVo.getEventCode());
						 uldResditVO.setCarrierId(containerVo.getCarrierId());
						 uldResditVO.setFlightNumber(containerVo.getFlightNumber());
						 uldResditVO.setFlightSequenceNumber(containerVo
								.getFlightSequenceNumber());
						 uldResditVO.setSegmentSerialNumber(containerVo
									.getSegmentSerialNumber());
						 uldResditVO.setResditSentFlag(MailConstantsVO.FLAG_YES);
						 uldResditVO.setProcessedStatus(MailConstantsVO.FLAG_YES);
						 uldResditVO.setUldNumber(containerVo.getContainerNumber());
						 uldResditVO
									.setEventDate(containerVo.getEventTime());
						 uldResditVO.setCarditKey(containerVo.getCarditKey());
						 if(!MailConstantsVO.RESDIT_UPLIFTED.equals(containerVo.getEventCode())){
								uldResditVO.setPaOrCarrierCode(containerVo.getPaCode());
						 }
						 uldResdit=new UldResdit(uldResditVO);
						 containerVo.setEventSequenceNumber(uldResdit.getUldResditPK().getSequenceNumber());
						 log.exiting(CLASS, "constructUldResditVO");
					 }
				 }


			    /**
			     * @author a-1936
			     * Added By Karthick V as the part of the NCA Mail Tracking CR
			     * This method is used to Update the Segment Details For the Containers
			     * @param containerVo
			     * @throws ContainerAssignmentException
			     */
				private void updateSegmentDetailsForContainers(Collection<ContainerVO> containerVos)
				  throws SystemException,ContainerAssignmentException{
					  log.entering("Resdit Controller", "updateSegmentDetailsForContainers");
					  ContainerVO containerVo = new ArrayList<ContainerVO>(containerVos).get(0);
					   if(containerVo.getSegmentSerialNumber()==0 &&
							    containerVo.getFlightSequenceNumber()>0){
					     new MailController().validateFlightForSegment
					      (constructOprFltForULDResdits(containerVo), containerVos);
					   }
					  log.exiting("Resdit Controller", "updateSegmentDetailsForContainers");
				}



				 private  OperationalFlightVO constructOprFltForULDResdits(ContainerVO containerVo){
					  log.entering("Resdit Controller", "constructOprFltForULDResdits");
					  OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
					  operationalFlightVo.setCompanyCode(containerVo.getCompanyCode());
					  operationalFlightVo.setFlightNumber(containerVo.getFlightNumber());
					  operationalFlightVo.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
					  operationalFlightVo.setCarrierId(containerVo.getCarrierId());
					  log.exiting("Resdit Controller", "constructOprFltForULDResdits");
					  return operationalFlightVo;
				 }



					/**
					 * For saving Resditconfig
					 * Feb 1, 2007, A-1739
					 * @param resditConfigurationVO
					 * @throws SystemException
					 */
					public void saveResditConfiguration(
							ResditConfigurationVO resditConfigurationVO) throws SystemException {
						log.entering(CLASS, "saveResditConfiguration");

						Collection<ResditTransactionDetailVO> txnDetails =
							resditConfigurationVO.getResditTransactionDetails();
						if(txnDetails != null && txnDetails.size() > 0) {
							for(ResditTransactionDetailVO txnDetailsVO : txnDetails) {
								ResditConfigurationPK resditConfigurationPK =
									new ResditConfigurationPK();
								resditConfigurationPK.setCompanyCode(
									resditConfigurationVO.getCompanyCode());
								resditConfigurationPK.setCarrierId(
									resditConfigurationVO.getCarrierId());
								resditConfigurationPK.setTransactionId(   txnDetailsVO.getTransaction());

								if(OPERATION_FLAG_INSERT.equals(txnDetailsVO.getOperationFlag())) {
									new ResditConfiguration(resditConfigurationPK, txnDetailsVO);
								} else if(OPERATION_FLAG_UPDATE.equals(
										txnDetailsVO.getOperationFlag())) {
									updateResditConfiguration(resditConfigurationPK, txnDetailsVO);
								}
							}
						}
						log.exiting(CLASS, "saveResditConfiguration");
					}
					/**
					 * TODO Purpose
					 * Feb 1, 2007, A-1739
					 * @param resditConfigurationPK
					 * @param txnDetailsVO
					 * @throws SystemException
					 */
					private void updateResditConfiguration(
							ResditConfigurationPK resditConfigurationPK,
							ResditTransactionDetailVO txnDetailsVO) throws SystemException {
						ResditConfiguration resditConfig = null;
						try {
							resditConfig = ResditConfiguration.find(resditConfigurationPK);
						} catch(FinderException exception) {
							throw new SystemException(exception.getErrorCode(), exception);
						}
						resditConfig.update(txnDetailsVO);
					}

					/**
					 *
					 * @param companyCode
					 * @param carrierId
					 * @return
					 * @throws SystemException
					 */
					public ResditConfigurationVO findResditConfigurationForAirline(
							String companyCode, int carrierId) throws SystemException {
						log.entering(CLASS, "findAirlineResditConfiguration");
						return ResditConfiguration.findResditConfigurationForAirline (
								companyCode, carrierId);
					}

					/**
					 * Invokes the EVT_RCR proc
					 * A-1739
					 * @param companyCode
					 * @throws SystemException
					 */
					public void invokeResditReceiver(String companyCode)
					throws SystemException {
						log.entering(CLASS, "invokeResditReceiver");
						new Resdit().invokeResditReceiver(companyCode);
						log.exiting(CLASS, "invokeResditReceiver");
					}

					/**
					 * Starts the resditBuilding
					 * Sep 8, 2006, a-1739
					 * @param companyCode
					 * @throws SystemException
					 * @return Collection<ResditEventVO>
					 */
					public Collection<ResditEventVO> checkForResditEvents(String companyCode)
						throws SystemException {
						log.entering(CLASS, "checkForResditEvents");
						String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
						if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
							log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
						Collection<ResditEventVO> resditEvents =
							new Resdit().findResditEvents(companyCode);
						if(resditEvents != null && resditEvents.size() > 0) {
							updateResditReadTimes(resditEvents);
						}
						return resditEvents;
					}
						else {
							return null;
						}
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
						HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
								.findSystemParameterByCodes(systemParameters);
						log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
						if (systemParameterMap != null) {
							sysparValue = systemParameterMap.get(syspar);
						}
						return sysparValue;
					}

					private void updateResditReadTimes(Collection<ResditEventVO> resditEvents)
					throws SystemException {
						log.entering(CLASS, "updateResditReadTimes");
						try {
							for(ResditEventVO resditEventVO : resditEvents) {
								ResditEvent resditEvent = ResditEvent.find(
										constructResditEventPK(resditEventVO));
								String uniqueId = new StringBuilder().
													append(System.currentTimeMillis()).
													append(resditEventVO.getResditEventCode()).toString();
								resditEvent.setUniqueIdForResdit(uniqueId);
							}
						} catch(FinderException ex) {
							throw new SystemException(ex.getMessage(), ex);
						}
						log.exiting(CLASS, "updateResditReadTimes");

					}
					/**
					 *
					 * 	Method		:	ResditController.flagResditsForTransportCompleted
					 *	Added by 	:
					 * 	Used for 	:
					 *	Parameters	:	@param companyCode
					 *	Parameters	:	@param carrierId
					 *	Parameters	:	@param mailbagVOs
					 *	Parameters	:	@param containerDetailsVOs
					 *	Parameters	:	@param eventPort
					 *	Parameters	:	@param flightArrivedPort
					 *	Parameters	:	@throws SystemException
					 *	Return type	: 	void
					 */
					public void flagResditsForTransportCompleted(String companyCode, int carrierId,
						Collection<MailbagVO> mailbagVOs,Collection < ContainerDetailsVO> containerDetailsVOs,
						String eventPort,String flightArrivedPort) throws SystemException {
						log.entering("ResditController===========>>>", "flagResditsForTransportCompleted ");
						Map<String, String> cityCache = new HashMap<String, String>();
						Collection<ContainerDetailsVO> readyForDeliveryContainers = new ArrayList<ContainerDetailsVO>();
						if(checkForResditConfig()) {
							Map<String, Object> valuesMap = new HashMap<String, Object>();
							//NEXT check for handover
							//NEXT put in asgnflag
							valuesMap.put(HAS_DEP, true);
							valuesMap.put(ACP_ULDS, containerDetailsVOs);
							flagConfiguredResdits(companyCode, carrierId, MailConstantsVO.TXN_DEP,
							eventPort, mailbagVOs, valuesMap);
						} else{
							Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
							if(mailbagVOs != null && mailbagVOs.size() > 0){
								for(MailbagVO mailbagVO : mailbagVOs){
								//A-7938
									String destinationOfficeOfExchange = mailbagVO.getDoe();
									Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
									String poaCode=null;
									MailbagPK mailbagPk = new MailbagPK();
									mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
									mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
											mailbagVO.getMailSequenceNumber(): findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()) );
									try {
										mailbagToFindPA = Mailbag.find(mailbagPk);
									} catch (FinderException e) {							
										e.getMessage();
									}
									if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
										poaCode=mailbagToFindPA.getPaCode();
									}
									else{
										OfficeOfExchangeVO originOfficeOfExchangeVO; 
										originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
										poaCode=originOfficeOfExchangeVO.getPoaCode();
									}//Added by A-8164 for ICRD-342541 ends
										readyForDeliveryMailbags.add(mailbagVO);
								}
							}
							if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
								for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
									if( flightArrivedPort.equalsIgnoreCase(containerDetailsVO.getDestination())){
										readyForDeliveryContainers.add(containerDetailsVO);
									}
								}
							}
							if(readyForDeliveryMailbags.size()>0){
								flagTransportCompletedResditForMailbags(readyForDeliveryMailbags, eventPort);
							}
							if(readyForDeliveryContainers.size()>0){
								flagUpliftedResditForUlds(readyForDeliveryContainers, eventPort);
							}
						}
						log.exiting("ResditController ===========>>>", "flagResditsForTransportCompleted");
					}
					/**
					 *
					 * 	Method		:	ResditController.flagTransportCompletedResditForMailbags
					 *	Added by 	:
					 * 	Used for 	:
					 *	Parameters	:	@param mailbags
					 *	Parameters	:	@param pol
					 *	Parameters	:	@throws SystemException
					 *	Return type	: 	void
					 */
					public void flagTransportCompletedResditForMailbags(Collection<MailbagVO> mailbags,
						String pol) throws SystemException {
						log.entering(CLASS, "flagTransportCompletedResditForMailbags");
						if (mailbags != null && mailbags.size() > 0) {
							Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
							for (MailbagVO mailbagVO : mailbags) {
								MailResditVO mailResditVO = constructMailResditVO(mailbagVO, pol,
										MailConstantsVO.RESDIT_TRANSPORT_COMPLETED, false);
								mailResditVOs.add(mailResditVO);
							}
							mailResditVOs = canFlagResditForEvents(
							MailConstantsVO.RESDIT_TRANSPORT_COMPLETED, mailResditVOs, mailbags);
							stampResdits(mailResditVOs);
							LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
							//Added by A-8893 for IASCB-49931 starts
							MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
							
							for (MailbagVO mailbagVO : mailbags) {
								if(mailbagVO.getLastUpdateUser()==null) {
									mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
								}
								Collection<FlightValidationVO> flightVOs=null;
								FlightFilterVO	flightFilterVO=new FlightFilterVO();      
								flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
								flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);  
								flightFilterVO.setPageNumber(1);      
								flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
								flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
								flightFilterVO.setStation(pol); 
									flightVOs=new MailController().validateFlight(flightFilterVO);
									if(flightVOs!=null && !flightVOs.isEmpty() &&mailbagVO!=null){
									mailController.flagHistoryforFlightArrival(mailbagVO,flightVOs);
									mailController.flagAuditforFlightArrival(mailbagVO,flightVOs);
								}
							}
									
									//Added by A-8893 for IASCB-49931 ends
						}
						log.entering(CLASS, "flagTransportCompletedResditForMailbags");
					}
					/**
					 * Added by A-2135 for QF CR 1517
					 * updateResditSendStatus
					 * @param controlReferenceNumber
					 * @param sendDate
					 * @param fileName
					 * @throws SystemException
					 */
				 	public void updateResditSendStatus (ResditMessageVO resditMessageVO) throws SystemException {
						log.entering(CLASS, "updateResditSendStatus");
						LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
						String companyCode = logonAttributes.getCompanyCode();
						String mailBagid ="";
						String controlReferenceNumber = resditMessageVO
								.getInterchangeControlReference();
						String fileName = resditMessageVO.getResditFileName();
						LocalDate sendDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, true);
						resditMessageVO.setSendDate(sendDate);
						
						//boolean canUpdateHistoryForMailResdits = false;
						/*ArrayList<MailResdit> mailResdits
							= (ArrayList<MailResdit>)MailResdit.findMailResditForResditStatusUpd(companyCode ,controlReferenceNumber);
						if(mailResdits != null && ! mailResdits.isEmpty()){
							MailResdit  mailResditItr = mailResdits.iterator().next();
							mailBagid = mailResditItr.getMailIdr();
							canUpdateHistoryForMailResdits = true;
							//Commented as discussed with Santhi K.
							*//*for(MailResdit mailResdit : mailResdits){
								mailResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_SENT);
								//For backup in case of purging MailResdit
								MailResditVO mailResditVO = MailResdit.covertToMailResditVo(mailResdit);
								new MailResditHistory(mailResditVO);
							}*//*
							}*/
/*						ArrayList<UldResdit> uldResdits
						= (ArrayList<UldResdit>)UldResdit.findULDResditForResditStatusUpd(companyCode ,controlReferenceNumber);
						if(uldResdits != null && ! uldResdits.isEmpty()){
							for(UldResdit uldResdit : uldResdits){
								uldResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_SENT);
								//For backup in case of purging UldResdit
								UldResditVO uldResditVO = UldResdit.covertToUldResditVo(uldResdit);
								new UldResditHistory(uldResditVO);
							}
						}*/
						/**
						 * Constructing the MailResditFileLogVO for finding all instances of mailbagHistory
						 * and updating the sndDat
						 */
						MailResditFileLogVO mailResditFileLogVO = new MailResditFileLogVO();
						mailResditFileLogVO.setCompanyCode(companyCode);
						mailResditFileLogVO.setInterchangeControlReference(controlReferenceNumber);
						mailResditFileLogVO.setFileName(fileName);
						ArrayList<MailResditFileLog> mailResditFileLog
						= (ArrayList<MailResditFileLog>)MailResditFileLog.findMailResditFileLog(mailResditFileLogVO);
						if(mailResditFileLog != null && ! mailResditFileLog.isEmpty()){
							for(MailResditFileLog mailResditFilelog : mailResditFileLog){
								mailResditFilelog.setSendDate(sendDate);

							}
						}
						/**
						 * Constructing the mailbagHistoryVO for finding all instances of mailbagHistory
						 * and updating the messageTime
						 * To display the message generated date in ViewMailBagHistoryPopup
						 */
						
/*if(canUpdateHistoryForMailResdits){
							//if (mailBagid != null) {
							//	MailbagHistoryVO mailbagHistoryVO = constructMailbagPKForHistory(mailBagid);
							*//*	mailbagHistoryVO.setCompanyCode(companyCode);
								mailbagHistoryVO.setInterchangeControlReference(controlReferenceNumber);*//*
*//*								ArrayList<MailbagHistory> mailbagHistory = (ArrayList<MailbagHistory>) MailbagHistory
										.findMailBagHistory(mailbagHistoryVO);
								if (mailbagHistory != null && !mailbagHistory.isEmpty()) {
									for (MailbagHistory mailbagHistoryItr : mailbagHistory) {
										mailbagHistoryItr.setMessageTime(sendDate);
									}
								}*//*
							//}
						}*/
						 //Added as part of bug ICRD-138578 by A-5526-History will stamp for resdit sent case only
						saveResditFileLogsAndUpdateMailbagHistory(resditMessageVO);
						log.exiting(CLASS, "updateResditSendStatus");
					}
				 	/**
					 * @author A-5526
					 * @param resditMessageVO
					 * @throws SystemException
					 * Added as part of bug ICRD-138578 by A-5526
					 */
					private void saveResditFileLogsAndUpdateMailbagHistory(ResditMessageVO resditMessageVO) throws SystemException{
						log.entering("ResditController===========>>>", "saveResditFileLogsAndUpdateMailbagHistory ");
						Collection<String> systemParameters = new ArrayList<String>();
						systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
						HashMap<String, String> systemParameterMap =
								new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
						/**
						 * Added by A-2135 for QF CR 1517
						 */
						saveResditFileLogs(resditMessageVO);
						/**
						 * Added by A-2135 for QF CR 1517 ends
						 */
						


Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> mailResditEventVOs = resditMessageVO.getResditEventVOs();  
						
						boolean canFlagHisForMailResdit= canCreateHistoryForMailResdits(resditMessageVO);
						Map oneTimesMap = null;
						Collection<String> oneTimeList = new ArrayList<String>();
						Collection<String> resditToAirlineList = new ArrayList<String>();
						Collection<String> resditToExcludeAirlineList = new ArrayList<String>();
						Collection<ConsignmentInformationVO> consignmentInformationVOsToInclude
						= new ArrayList<ConsignmentInformationVO>();
						ResditMessageVO airlineResditMessageVO=null;
						String contractReference = null;
						String contractedAirline = null;
						String excludeAirline		= "JL";
						oneTimeList.add(RESDIT_TO_AIRLINE_LIST);
						oneTimeList.add(RESDIT_EXD_AIRLINE_LIST);
						try {
							oneTimesMap = new SharedDefaultsProxy().findOneTimeValues(resditMessageVO.getCompanyCode(),
									oneTimeList);
							log.log(Log.FINEST, "\n\n RESDIT_TO_AIRLINE hash map******************" + oneTimesMap);
						} catch (ProxyException proxyException) {
							throw new SystemException(proxyException.getMessage());
						}
						if(oneTimesMap!=null){
							Collection<OneTimeVO> resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap.get(RESDIT_TO_AIRLINE_LIST);
							if(resditToAirlineConfigList!=null){
								for(OneTimeVO oneTimeVO : resditToAirlineConfigList){
								resditToAirlineList.add(oneTimeVO.getFieldValue());
								}
							}
							resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap.get(RESDIT_EXD_AIRLINE_LIST);
							if(resditToAirlineConfigList!=null){
								for(OneTimeVO oneTimeVO : resditToAirlineConfigList){
									resditToExcludeAirlineList.add(oneTimeVO.getFieldValue());
								}
							}
						}
						if(resditToAirlineList!=null && resditToAirlineList.size()>0){
							log.log(Log.FINEST, "\n\n RESDIT_TO_AIRLINE PROCESS STARTS******************" + oneTimesMap);
//							String excludeAirli
							for(String ccAirline:resditToAirlineList){
								/** CR QF1473 - Roopak
							    As discussed with Naveen, if Resdit is sent to US post .  Same copy will be sent to AA.
				                "AA" need to be configured as OneTime parameter 'mailtracking.defaults.resdittoairlinelist'
				                While sending resdit, system will check, if AA is the airline and US101 is the post . It will send resdit to both.
				                else, is the previous logic - commented as per the CR.
								 **/
								if (RESDIT_TO_AIRLINE_AA.equals(ccAirline)) {
									if (systemParameterMap != null &&
										(MailConstantsVO.FLAG_ACTIVE.equals (systemParameterMap.get (MailConstantsVO.SYSPAR_USPS_ENHMNT) )||
										 MailConstantsVO.FLAG_YES.equals (systemParameterMap.get (MailConstantsVO.SYSPAR_USPS_ENHMNT) ))&&
										 RESDIT_TO_POST_US.equals (resditMessageVO.getRecipientID())
										) {
									ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
									BeanHelper.copyProperties(resditMessageCopyVO,
											resditMessageVO);
									resditMessageCopyVO.setResditToAirlineCode(ccAirline);
									HashMap<String, String> carditDetailsMap = Resdit
									.findCarditDetailsForResdit(mailResditEventVOs);
									if (carditDetailsMap != null) {
										Collection<ConsignmentInformationVO> consignmentInformationVOs = resditMessageVO
										.getConsignmentInformationVOs();
										consignmentInformationVOsToInclude = new ArrayList<ConsignmentInformationVO>();
										if (consignmentInformationVOs != null
												&& consignmentInformationVOs.size() > 0) {
											for (ConsignmentInformationVO consignmentInformationVO : consignmentInformationVOs) {
												contractReference = carditDetailsMap.get(consignmentInformationVO
														.getConsignmentID());
												contractedAirline = contractReference == null ? null
														: contractReference.substring(contractReference.length() - 2, contractReference.length());
												if (contractedAirline != null && resditToExcludeAirlineList.contains(contractedAirline)) {
													// It is having cardit and contract reference ends with 'JL' ie contracted to
													// JAL hence skipping from copying to AA
													continue;
												}
												// No cardit exists if contractReference is null Or Cardit exists but its not of JAL
												// contarcted so needs to be copied to AA
												consignmentInformationVOsToInclude.add(consignmentInformationVO);
											}
										}
									}
									if (consignmentInformationVOsToInclude.size() > 0){
										resditMessageCopyVO.setConsignmentInformationVOs (consignmentInformationVOsToInclude);
										new MsgBrokerMessageProxy().encodeAndSaveMessage(resditMessageCopyVO);
										/**
										 * Added by A-2135 for QF CR 1517
										 */
										saveResditFileLogs(resditMessageVO);
									}
									/**
									 * Added by A-2135 for QF CR 1517
									 */
								}
								}else{
									//No change
								}
							}
						}
						HashSet<String> consignKeySet=new HashSet<String>();
				        for(ResditEventVO resditEventVO : mailResditEventVOs) {
							//set RDTSND flag to S for all  sent receptacles in MTKMAL/ULDRDT
							updateResditStatus(resditEventVO,resditMessageVO);
							//Flag history for resditevent
						    /*
						     *
						     * Added this check  to ignore the History   being flagged
						     * if  the Resdit Message Vo Contains the  Container Information alone ..
						     *
						     */
							if(canFlagHisForMailResdit){
							   createMailbagHistoryForResdit(resditEventVO, resditMessageVO,consignKeySet);
							}
						}
				    	log.exiting("ResditController===========>>>", "saveResditFileLogsAndUpdateMailbagHistory ");
					}

					public void buildResdit(Collection<ResditEventVO> resditEvents) throws SystemException {
						log.entering(CLASS, "buildResdit");
						try {//moving entire code as a proxy call for IASCB-37022
							new MailtrackingDefaultsProxy().buildResditProxy(resditEvents);
						}catch(ProxyException e){
							throw new SystemException(e.getErrors());
						}
						log.exiting(CLASS, "buildResdit");
					}
					


/**
					 * Method to find Resdit Event date from flight info
					 * @param  mailbagVO
					 * @param  eventAirport
					 * @return resditEvtDate
					 * @throws SystemException
					 */
					public LocalDate findResditEvtDate(MailbagVO mailbagVO,String eventAirport){
						log.entering("ResditController===========>>>", "findResditEvtDate "); 
						LocalDate resditEvtDate=new LocalDate(eventAirport,Location.ARP,true);
						boolean isEvtDateSet=false;
						try{
						Collection<FlightSegmentSummaryVO> segmentSummaryVOs =
								new FlightOperationsProxy().findFlightSegments(
									mailbagVO.getCompanyCode(), mailbagVO.getCarrierId(),
									mailbagVO.getFlightNumber(), mailbagVO.getFlightSequenceNumber());

							FlightSegmentSummaryVO segmentSummaryVO = null;
							if(segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
								for(FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
									if(segmentSumaryVO.getSegmentSerialNumber() ==
											mailbagVO.getSegmentSerialNumber()) {
										segmentSummaryVO = segmentSumaryVO;
									}
								}
							}

							if(segmentSummaryVO != null) {
								FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
								segmentFilter.setCompanyCode(mailbagVO.getCompanyCode());
								segmentFilter.setFlightCarrierId(mailbagVO.getCarrierId());
								segmentFilter.setFlightNumber(mailbagVO.getFlightNumber());
								segmentFilter.setSequenceNumber(mailbagVO.getFlightSequenceNumber());
								segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
								segmentFilter.setDestination(
										segmentSummaryVO.getSegmentDestination());

								FlightSegmentValidationVO segmentValidationVO =
									new FlightOperationsProxy().validateFlightSegment(segmentFilter);

								

boolean isAtd = MailConstantsVO.FLAG_YES.equals(
									findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
								if(isAtd) {
									if(segmentValidationVO.getActualTimeOfDeparture() != null) {
										resditEvtDate=
											segmentValidationVO.getActualTimeOfDeparture();//ATD set
									} else {
										//not departed
										resditEvtDate=
											segmentValidationVO.getScheduleTimeOfDeparture();//STD set
									}
								} else {
									resditEvtDate=
											segmentValidationVO.getScheduleTimeOfDeparture();//STD set
								}
								isEvtDateSet=true;
								
								if("Y".equals(mailbagVO.getIsFromTruck()) && "STD".equals(mailbagVO.getStdOrStaTruckFlag())){
									resditEvtDate=	segmentValidationVO.getScheduleTimeOfDeparture();
								}
								if("Y".equals(mailbagVO.getIsFromTruck()) && "STA".equals(mailbagVO.getStdOrStaTruckFlag())){
									resditEvtDate=	segmentValidationVO.getScheduleTimeOfArrival();
								}
							 }
							}catch(SystemException e){
									log.log(Log.FINEST, "System Exception in findResditEvtDate");
							}
							if(resditEvtDate==null||!isEvtDateSet){
								resditEvtDate=mailbagVO.getResditEventDate()!=null?mailbagVO.getResditEventDate():new LocalDate(
										eventAirport,Location.ARP,true);
							}
							
						log.exiting("ResditController========>>>", "findResditEvtDate ");
						return resditEvtDate;
					}
					
					
					public LocalDate findResditEvtDateForULD(ContainerDetailsVO containervo,String eventAirport){
						log.entering("ResditController===========>>>", "findResditEvtDateForULD "); 
						LocalDate resditEvtDate=new LocalDate(eventAirport,Location.ARP,true);
						boolean isEvtDateSet=false;
						try{
						Collection<FlightSegmentSummaryVO> segmentSummaryVOs =
								new FlightOperationsProxy().findFlightSegments(
										containervo.getCompanyCode(), containervo.getCarrierId(),
										containervo.getFlightNumber(), containervo.getFlightSequenceNumber());
							FlightSegmentSummaryVO segmentSummaryVO = null;
							if(segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
								for(FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
									if(segmentSumaryVO.getSegmentSerialNumber() ==
											containervo.getSegmentSerialNumber()) {
										segmentSummaryVO = segmentSumaryVO;
									}
								}
							}
							if(segmentSummaryVO != null) {
								FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
								segmentFilter.setCompanyCode(containervo.getCompanyCode());
								segmentFilter.setFlightCarrierId(containervo.getCarrierId());
								segmentFilter.setFlightNumber(containervo.getFlightNumber());
								segmentFilter.setSequenceNumber(containervo.getFlightSequenceNumber());
								segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
								segmentFilter.setDestination(
										segmentSummaryVO.getSegmentDestination());
								FlightSegmentValidationVO segmentValidationVO =
									new FlightOperationsProxy().validateFlightSegment(segmentFilter);
								log.log(Log.FINEST, "segmentdata from flt " + segmentValidationVO);
								boolean isAtd = MailConstantsVO.FLAG_YES.equals(
									findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
								if(isAtd) {
									if(segmentValidationVO.getActualTimeOfDeparture() != null) {
										resditEvtDate=
											segmentValidationVO.getActualTimeOfDeparture();//ATD set
									} else {
										//not departed
										resditEvtDate=
											segmentValidationVO.getScheduleTimeOfDeparture();//STD set
									}
								} else {
									resditEvtDate=
											segmentValidationVO.getScheduleTimeOfDeparture();//STD set
								}
								isEvtDateSet=true;
								/*if("Y".equals(mailbagVO.getIsFromTruck()) && "STD".equals(mailbagVO.getStdOrStaTruckFlag())){
									resditEvtDate=	segmentValidationVO.getScheduleTimeOfDeparture();
								}
								if("Y".equals(mailbagVO.getIsFromTruck()) && "STA".equals(mailbagVO.getStdOrStaTruckFlag())){
									resditEvtDate=	segmentValidationVO.getScheduleTimeOfArrival();
								}*/
							 }
							}catch(SystemException e){
									log.log(Log.FINEST, "System Exception in findResditEvtDate");
							}
							if(resditEvtDate==null||!isEvtDateSet){
								/*resditEvtDate=mailbagVO.getResditEventDate()!=null?mailbagVO.getResditEventDate():new LocalDate(
										eventAirport,Location.ARP,true);*/
								resditEvtDate=new LocalDate(
										eventAirport,Location.ARP,true);
							}
						log.exiting("ResditController========>>>", "findResditEvtDateforUld ");
						return resditEvtDate;
					}
					/**
					 * @author A-5526
					 * Added for CRQ ICRD-233864 
					 * @param mailArrivalVO
					 * @param mailbagVOs
					 * @param containerDetailsVOs
					 * @throws SystemException 
					 */
					public void triggerReadyfordeliveryResdit(MailArrivalVO mailArrivalVO,
			Collection<MailbagVO> mailbagVOs, Collection<ContainerDetailsVO> containerDetailsVOs)
			throws SystemException {
		Map<String, String> cityCache = new HashMap<String, String>();
		Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> readyForDeliveryContainers = new ArrayList<ContainerDetailsVO>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		if (mailbagVOs != null && !mailbagVOs.isEmpty())
			for (MailbagVO mailbagVO : mailbagVOs) {
				Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
				String poaCode=null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
						 mailbagVO.getMailSequenceNumber(): findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
				try {
					mailbagToFindPA = Mailbag.find(mailbagPk);
				} catch (FinderException e) {							
					e.getMessage();
				}
				if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
					poaCode=mailbagToFindPA.getPaCode();
				}
				else{
					OfficeOfExchangeVO originOfficeOfExchangeVO; 
					originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
					poaCode=originOfficeOfExchangeVO.getPoaCode();  
				}//Added by A-8164 for ICRD-342541 ends
				if(mailbagVO.getMailbagId()!=null && mailbagVO.getMailbagId().length()==29){  
					AirlineValidationVO airlineValidationVO = null;
					if(mailbagVO.getCarrierCode()==null){     
						  try{
			     	        	airlineValidationVO= new SharedAirlineProxy()
					     .findAirline(mailbagVO.getCompanyCode(),mailbagVO.getCarrierId());  
			     	  }catch (SharedProxyException sharedProxyException) {
			     		sharedProxyException.getMessage();
					   } catch (SystemException ex) {
						ex.getMessage();
					   }
						  mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());  
					}
				mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
				}//added by A-8353  for ICRD-337333
				if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(),
						logonAttributes.getAirportCode(), cityCache,poaCode,mailbagVO.getConsignmentDate())) {
					// Perform RFD

					if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(),
							logonAttributes.getAirportCode(), cityCache,poaCode,mailbagVO.getConsignmentDate())) {
						readyForDeliveryMailbags.add(mailbagVO);
					} else{
						//nothing to do
					}

				}
			}

		for (ContainerDetailsVO arrivedContainer : containerDetailsVOs) {

			if (ContainerDetailsVO.FLAG_YES.equals(arrivedContainer.getPaBuiltFlag())
					&& MailConstantsVO.ULD_TYPE.equals(arrivedContainer.getContainerType())) {

				if (logonAttributes.getAirportCode().equalsIgnoreCase(arrivedContainer.getDestination())) {
					readyForDeliveryContainers.add(arrivedContainer);
				}

			}
		}

		flagReadyForDeliveryResditForMailbags(readyForDeliveryMailbags, mailArrivalVO.getAirportCode());
		flagReadyForDeliveryResditForULD(readyForDeliveryContainers, mailArrivalVO.getAirportCode());

	}
					private void updateArrivalEventTimeForAA(MailbagVO mailbagVO) {
						
						ArrayList<String> systemParameters = new ArrayList<String>();
						systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
						systemParameters.add(AUTOARRIVALOFFSET);
						String sysparfunpnts = null;
						String sysparoffset = null;
						Map<String, String> systemParameterMap;
						try {
							systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
							if (systemParameterMap != null) {
								sysparfunpnts= systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
								sysparoffset=systemParameterMap.get(AUTOARRIVALOFFSET);
							}
							
							
				  } catch (SystemException e) {
					  log.log(Log.SEVERE, "System Exception Caught");
					  e.getMessage();
						}
						
						if(sysparfunpnts!=null)
						{
				          if("0".equals(sysparoffset)  && MailConstantsVO.FLAG_YES.equals(mailbagVO.getAutoArriveMail()) )   
					        {
				        	  FlightValidationVO validVO =null;
				      		try {
				      			validVO = new MailController().validateFlightForBulk(mailbagVO);
				      		} catch (SystemException e) {
				      			validVO=null;
				      		}
				      		if(validVO!=null){
				      			mailbagVO.setResditEventDate(validVO.getAta());
				      			mailbagVO.setScannedDate(validVO.getAta());
				      		}
				      		
				        	  
					        }
						}
						
						

	}
					
					/**
					 * @author A-7540 for ICRD-333458
					 * @param mailbagVO
					 * @return
					 * @throws SystemException 
					 */
					private MailbagVO createMailbagVOsforArrivedResdit(MailbagVO mailbagVO) throws SystemException{
						Mailbag mailbag = null;
						//Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
							try {
								MailbagPK mailbagPK = new MailbagPK();
				 				  mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
								  mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
										  mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));							
			/*
			 * try { long mailsequenceNumber;
			 * 
			 * //mailsequenceNumber =mailbagVO.getMailSequenceNumber();//Added by A-8353 for
			 * ICRD-230449 mailsequenceNumber=new
			 * MailController().findMailSequenceNumber(mailbagVO.getMailbagId(),
			 * mailbagVO.getCompanyCode());
			 * mailbagPK.setMailSequenceNumber(mailsequenceNumber);
			 * 
			 * mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			 * mailbagPK.setMailSequenceNumber(mailsequenceNumber);
			 */
										mailbag = Mailbag.find(mailbagPK);							
									} catch (FinderException e) {							
										e.getMessage();
								}
							catch (SystemException e1) {
								log.log(Log.SEVERE, "SystemException caught");
							}
						
						if (mailbag != null) {
							/*Collection<MailbagHistory> existingMailbagHistories = mailbag IASCB-46569
									.getMailbagHistories();*/
							
							Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
							
							
							String airport = mailbag.getScannedPort();
							if (mailbagVO != null) {
								airport = mailbagVO.getScannedPort();
							}
							if (existingMailbagHistories != null
									&& existingMailbagHistories.size() > 0) {
								for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
									if((MailConstantsVO.MAIL_STATUS_ASSIGNED
											.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_DAMAGED
											.equals(mailbagHistory.getMailStatus()))){
										//MailbagVO mailbagVO = new MailbagVO();
										mailbagVO.setCarrierCode(mailbagHistory
												.getCarrierCode());
										mailbagVO.setFlightSequenceNumber(mailbagHistory.getFlightSequenceNumber());
										mailbagVO.setSegmentSerialNumber(mailbagHistory.getSegmentSerialNumber());
										mailbagVO.setFlightNumber(mailbagHistory
												.getFlightNumber());
										if(mailbagHistory.getFlightSequenceNumber() > 0){
										LocalDate ldate = new LocalDate(airport,
												Location.ARP,
												mailbagHistory.getFlightDate(), true);
										mailbagVO.setFlightDate(ldate);
									 }
										mailbagVO
												.setContainerNumber(mailbagHistory
														.getContainerNumber());
										mailbagVO
												.setPou(mailbagHistory.getPou());						
									}
								   }
								}
						 
						}
						return mailbagVO;
					}

	/**
	 * @author-U-1439
	 * @for IASCB-37022
	 * @param resditEvents
	 * @throws SystemException
	 */
	public void buildResditProxy(Collection<ResditEventVO> resditEvents) throws SystemException {

		log.entering("buildResditProxy","buildResditProxy");

		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
log.log(Log.FINE, "Resdit Enabled ", resditEnabled);        
		if(resditEvents!=null && resditEvents.size()>0){
			ResditMessageVO resditMessageVO = new Resdit().buildResditMessageVO(resditEvents);

// Commented for Mailtracking Module removal TODO in 4.10
//Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> eventVOs = MailtrackingDefaultsVOConverter.convertResditEventVOs(resditEvents);

resditMessageVO.setResditEventVOs(resditEvents);
if (resditMessageVO.getConsignmentInformationVOs() != null
		&& (resditMessageVO.getConsignmentInformationVOs().size() > 0)) {

				sendResditMessage(resditMessageVO, resditEvents);
			}else{
				log.log(Log.SEVERE,"ConsignmentInformationVOs null in resdit.");
}                   
			}
		}

		

		log.exiting("buildResditProxy","buildResditProxy");
					}

	public void populateAndSaveMailResditVO(MailUploadVO mailbagVO, long mailseqnum, long messageIdentifier,MailbagVO mailVO) throws SystemException {

		log.entering(CLASS, "construct MailResditVO");
		String partyIdentifier;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailTag());
		String mailboxIdFromConfig = MailMessageConfiguration.findMailboxIdFromConfig(mailVO);
		if (mailboxIdFromConfig!=null && !mailboxIdFromConfig.isEmpty() ) {
			mailResditVO.setMailboxID(mailboxIdFromConfig);
		}
		mailResditVO.setMailSequenceNumber(mailseqnum);
		mailResditVO.setMessageIdentifier(messageIdentifier);
		if (mailbagVO.getScannedPort() != null) {
			mailResditVO.setEventAirport(mailbagVO.getScannedPort());
		}
		String scanType = null;
		if (mailbagVO.getScanType() != null) {
			if ("ACP".equals(mailbagVO.getScanType())) {
				scanType = "74";
			} else if ("ASG".equals(mailbagVO.getScanType())) {
				scanType = "24";
			} else if ("DLV".equals(mailbagVO.getScanType())) {
				scanType = "21";
			}
		}
		mailResditVO.setEventCode(scanType);
		if (!(MailConstantsVO.RESDIT_PENDING.equals(scanType) || MailConstantsVO.RESDIT_PENDING_M49.equals(scanType))) {
			mailResditVO.setCarrierId(mailbagVO.getCarrierId());
			mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
			mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
			//Added as part of bug IASCB-65960 by A-5526 starts
			AirlineValidationVO airlineValidationVO=null;
			if(mailbagVO.getCompanyCode()!=null && mailbagVO.getCarrierCode()!=null){
				try {
					airlineValidationVO=new SharedAirlineProxy().validateAlphaCode(mailbagVO.getCompanyCode(),mailbagVO.getCarrierCode());
				} catch (SharedProxyException e) {
					airlineValidationVO=null;
				}
				mailResditVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());      
			}
			//Added as part of bug IASCB-65960 by A-5526 ends
			
		}
		//Modified as part of bug IASCB-61182 by A-5526 
		mailResditVO.setResditSentFlag("I");    

		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_YES);
		mailResditVO.setUldNumber(mailbagVO.getContainerNumber());

		

			mailResditVO.setEventDate(mailbagVO.getScannedDate() != null ? mailbagVO.getScannedDate()
					: new LocalDate(mailbagVO.getScannedPort(), Location.ARP, true));          

		

		if (mailbagVO.getMailTag() != null && mailbagVO.getMailTag().length() == 29) {
			String paCode = "";

			Mailbag mailbagToFindPA = null;
			String poaCode = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailTag(), mailbagVO.getCompanyCode()));
			try {
				mailbagToFindPA = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				e.getMessage();
			}
			if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
				poaCode = mailbagToFindPA.getPaCode();
			} else {
				poaCode = mailbagVO.getPaCode();
			}

			mailResditVO.setEventAirport(mailbagVO.getScannedPort());
			//Modified as part of bug IASCB-65960 by A-5526 
			if(mailResditVO.getFlightNumber()==null || mailResditVO.getFlightNumber().isEmpty()){
			mailResditVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
			}      

			if (scanType == null || (MailConstantsVO.RESDIT_DELIVERED.equals(scanType)
					|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(scanType))) {
				poaCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailTag().substring(6, 12));
			} else {
				paCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailTag().substring(0, 6));
			}

			if (paCode != null) {
				partyIdentifier = findPartyIdentifierForPA(mailbagVO.getCompanyCode(), paCode);
				mailResditVO.setPartyIdentifier(partyIdentifier);
			}

		}
		mailResditVO.setSenderIdentifier(mailbagVO.getMessageSenderIdentifier());
		mailResditVO.setRecipientIdentifier(mailbagVO.getMessageRecipientIdentifier());
		if (mailResditVO != null){   
			mailResditVOs.add(mailResditVO);
		stampResdits(mailResditVOs);
		}

	}
		

	
	
	public void updateMessageIdentifierOfResditMessage(long messageIdentifier,
			Collection<ResditEventVO> resditEventVOs, ResditMessageVO resditMessageVO) throws SystemException {

	
	for(ResditEventVO resditEventVO:resditEventVOs){      
		Collection<ConsignmentInformationVO> consignments =
				resditMessageVO.getConsignmentInformationVOs();
			

			if(consignments != null && consignments.size() > 0) {
				for(ConsignmentInformationVO consignVO : consignments) {
					/*if(MailConstantsVO.RESDIT_PENDING_M49.equals(consignVO.getConsignmentEvent())){//added by a-7871 for ICRD-227878
						consignVO.setConsignmentEvent(MailConstantsVO.RESDIT_PENDING);
					}*/
					if(String.valueOf(consignVO.getConsignmentEvent()).equals(
						resditEventVO.getResditEventCode())) {

						Collection<ReceptacleInformationVO> receptacles =
							consignVO.getReceptacleInformationVOs();
						if(receptacles != null && receptacles.size() > 0) {
							for(ReceptacleInformationVO receptacleVO : receptacles) {
								
								try {
									MailResdit mailResdit = MailResdit.find(
											constructMailResditPK(resditEventVO, receptacleVO));
									
										mailResdit.setMessageIdentifier(messageIdentifier);
										

										} catch(FinderException ex) {
											
											log.log(Log.FINE, "Trying to update Message Identifier....just ignore exception!!!!"+ receptacleVO.getReceptacleID());
										}
							}
							
						}
					}
				}
			}
					}
	
	
}
	/**
	 * @author A-8353
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 */
	 public  String findPartyIdentifierForPA(String companyCode,
	            String paCode)
	throws SystemException {
		 log.entering(CLASS, "findPartyIdentifierForPA");
			CacheFactory factory = CacheFactory.getInstance();
			PostalAdministrationCache cache = factory
				.getCache(PostalAdministrationCache.ENTITY_NAME);  
			return cache.findPartyIdentifierForPA(companyCode,paCode);
}
	 
		/**
		 * 
		 * 	Method		:	ResditController.canStampResdits
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailResditVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	boolean
		 */
		public boolean canStampResdits(MailResditVO mailResditVO) throws SystemException {
			boolean enabledFlag = false;
			MailBoxId mailboxId = findMailboxId(mailResditVO);
			if (Objects.nonNull(mailboxId)
					&& MailConstantsVO.MESSAGE_ENABLED_PARTIAL.equals(mailboxId.getMessagingEnabled())) {
				Collection<MailEventVO> mailEventVOs = findMailEventFromCache(mailboxId);
				Mailbag mailbag = findMailbag(mailResditVO);
				if (Objects.nonNull(mailbag) && Objects.nonNull(mailEventVOs) && !mailEventVOs.isEmpty()) {
					enabledFlag = canStampResditForMailBag(mailResditVO, mailEventVOs, mailbag);
				}
			} else if (Objects.nonNull(mailboxId)
					&& MailConstantsVO.MESSAGE_ENABLED_EXCLUDE.equals(mailboxId.getMessagingEnabled())
					&& (!MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(mailResditVO.getEventCode())
							&& !MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode()))) {
				enabledFlag = true;
			} else if (Objects.nonNull(mailboxId) && MailConstantsVO.FLAG_YES.equals(mailboxId.getMessagingEnabled())) {
				enabledFlag = true;
			}
			
			if(enabledFlag && mailResditVO.isFromDeviationList() &&  (MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode()) || 
					MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode()))){
				Mailbag mailbag = findMailbag(mailResditVO);
				if(mailbag!=null){
				enabledFlag=validateDeviationRestriction(mailbag);
				}
			}
			
			if(!"I".equals(mailResditVO.getResditSentFlag())  && MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode()) ){
				enabledFlag=checkIfATDCaptured(mailResditVO);
			}
			if(mailResditVO.isResditFromCarditEnquiry()) {
				enabledFlag = true;
			}
			return enabledFlag;

		}
		/**
		 * @author U-1532
		 * @param mailResditVO
		 * @param mailbag
		 * @return
		 * @throws SystemException
		 */
	private boolean validateDeviationRestriction(Mailbag mailbag) throws SystemException {
			boolean enabledFlag=true;
		  
		String restrcitedPAsfordeviationresdits = findSystemParameterValue(
				MailConstantsVO.RESTRICTED_PAS_FOR_DEVIATION_RESDITS);
		if (restrcitedPAsfordeviationresdits != null && ("*".equals(restrcitedPAsfordeviationresdits)
				|| restrcitedPAsfordeviationresdits.contains(mailbag.getPaCode()))) {
				enabledFlag=false;

			}
			
			return enabledFlag;
		}
		/**
		 * 
		 * 	Method		:	ResditController.findMailbag
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailResditVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	Mailbag
		 */
		private Mailbag findMailbag(MailResditVO mailResditVO) throws SystemException {
			Mailbag mailbag = null;
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailResditVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailResditVO.getMailSequenceNumber());
			try {
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException e) {
				log.log(Log.FINE, e.getMessage(), e);
			}
			return mailbag;
		}
		/**
		 * 
		 * 	Method		:	ResditController.findMailboxId
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailResditVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	MailBoxId
		 */
		private MailBoxId findMailboxId(MailResditVO mailResditVO) throws SystemException {
			MailBoxId mailboxId = null;
			MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
			mailBoxIdPk.setMailboxCode(mailResditVO.getMailboxID());
			mailBoxIdPk.setCompanyCode(mailResditVO.getCompanyCode());
			try {
				mailboxId = MailBoxId.find(mailBoxIdPk);
			} catch (FinderException e) {
				log.log(Log.FINE, e.getMessage(), e);
			}
			return mailboxId;
		}
		/**
		 * 
		 * 	Method		:	ResditController.findMailEventFromCache
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailboxId
		 *	Parameters	:	@return
		 *	Parameters	:	@throws CacheException 
		 *	Return type	: 	Collection<MailEventVO>
		 */
		private Collection<MailEventVO> findMailEventFromCache(MailBoxId mailboxId) throws CacheException {
			MailEventVO filterVO = new MailEventVO();
			filterVO.setCompanyCode(mailboxId.getMailboxIdPK().getCompanyCode());
			filterVO.setMailboxId(mailboxId.getMailboxIdPK().getMailboxCode());
			CacheFactory factory = CacheFactory.getInstance();
			MailEventCache cache = factory.getCache(MailEventCache.ENTITY_NAME);
			return cache.findMailEvent(filterVO);
		}
		/**
		 * 
		 * 	Method		:	ResditController.canStampResditForMailBag
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailResditVO
		 *	Parameters	:	@param mailEventVOs
		 *	Parameters	:	@param mailbag
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean canStampResditForMailBag(MailResditVO mailResditVO, Collection<MailEventVO> mailEventVOs,
				Mailbag mailbag) {
			boolean enabledFlag = false;
			for (MailEventVO mailEventVO : mailEventVOs) {
				if ((mailbag.getMailCategory().equals(mailEventVO.getMailCategory())
						&& mailbag.getMailSubClass().equals(mailEventVO.getMailClass()))
						|| (MailConstantsVO.ALL.equals(mailEventVO.getMailCategory())
								&& MailConstantsVO.ALL.equals(mailEventVO.getMailClass()))
						|| (mailbag.getMailCategory().equals(mailEventVO.getMailCategory())
								&& MailConstantsVO.ALL.equals(mailEventVO.getMailClass()))
						|| (MailConstantsVO.ALL.equals(mailEventVO.getMailCategory())
								&& mailbag.getMailSubClass().equals(mailEventVO.getMailClass()))) {
					enabledFlag = canStampResditForMailEvent(mailResditVO, mailEventVO);
				}
			}
			return enabledFlag;
		}
		


/**
		 * 
		 * 	Method		:	ResditController.canStampResditForMailEvent
		 *	Added by 	:	A-6245 on 07-Jan-2021
		 * 	Used for 	:	IASCB-87899
		 *	Parameters	:	@param mailResditVO
		 *	Parameters	:	@param mailEventVO
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean canStampResditForMailEvent(MailResditVO mailResditVO, MailEventVO mailEventVO) {
			boolean enabledFlag = false;
			if (MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isReceived();
			} else if (MailConstantsVO.RESDIT_RETURNED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isReturned();
			} else if (MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isUplifted();
			} else if (MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isHandedOver();
			} else if (MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isHandedOverReceivedResditFlag();
			} else if (MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isOnlineHandedOverResditFlag();
			} else if (MailConstantsVO.RESDIT_DELIVERED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isDelivered();
			} else if (MailConstantsVO.RESDIT_PENDING.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isPending();
			} else if (MailConstantsVO.RESDIT_ASSIGNED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isAssigned();
			} else if (MailConstantsVO.RESDIT_LOADED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isLoadedResditFlag();
			} else if (MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isReadyForDelivery();
			} else if (MailConstantsVO.RESDIT_ARRIVED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isArrived();
			} else if (MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(mailResditVO.getEventCode())) {
				enabledFlag = mailEventVO.isTransportationCompleted();
			} else if (MailConstantsVO.RESDIT_LOST.equals(mailResditVO.getEventCode())){
				enabledFlag = mailEventVO.isLostFlag();
			} else if (MailConstantsVO.RESDIT_FOUND.equals(mailResditVO.getEventCode())){
				enabledFlag = mailEventVO.isFoundFlag();
			}
			return enabledFlag;
		}
	
		/**
		 * @author U-1532
		 * @param mailResditVO
		 * @return
		 * @throws SystemException
		 */

		public boolean checkIfATDCaptured(MailResditVO mailResditVO)
			throws SystemException {
		Collection<FlightSegmentSummaryVO> segmentSummaryVOs = new FlightOperationsProxy().findFlightSegments(
				mailResditVO.getCompanyCode(), mailResditVO.getCarrierId(), mailResditVO.getFlightNumber(),
				mailResditVO.getFlightSequenceNumber());
		FlightSegmentSummaryVO segmentSummaryVO = null;
		if (segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
			for (FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
				if (segmentSumaryVO.getSegmentSerialNumber() == mailResditVO.getSegmentSerialNumber()) {
					segmentSummaryVO = segmentSumaryVO;
				}
			}
		}

		if (segmentSummaryVO != null) {
			FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
			segmentFilter.setCompanyCode(mailResditVO.getCompanyCode());
			segmentFilter.setFlightCarrierId(mailResditVO.getCarrierId());
			segmentFilter.setFlightNumber(mailResditVO.getFlightNumber());
			segmentFilter.setSequenceNumber(mailResditVO.getFlightSequenceNumber());
			segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
			segmentFilter.setDestination(segmentSummaryVO.getSegmentDestination());

			FlightSegmentValidationVO segmentValidationVO = new FlightOperationsProxy()
					.validateFlightSegment(segmentFilter);

			boolean isAtd = MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
			if (isAtd) {
				if (segmentValidationVO.getActualTimeOfDeparture() != null) {
					return true;
				}
			} else {
				return true;
			}

		}

		return false;
	}
	public void flagResditsForSendResditsFromCarditEnquiry(String messageAddressSequenceNumber, String selectedResdit,
				MailbagVO mailbagVO) throws SystemException {
			boolean resditFromCarditEnquiry=true;
			log.entering(CLASS, "flagResditsForSendResditsFromCarditEnquiry");
			MailResditVO mailResditVO= new MailResditVO();
			Collection<MailResditVO> mailResditVOs = new ArrayList<>();
			mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailResditVO.setEventCode(selectedResdit);
		    mailResditVO.setEventAirport(mailbagVO.getUpliftAirport());
			mailResditVO.setMailId(mailbagVO.getMailbagId());
			mailResditVO.setResditSentFlag("N");
			mailResditVO.setProcessedStatus("N");
			mailResditVO.setEventDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		    mailResditVO.setMessageAddressSequenceNumber(messageAddressSequenceNumber);
			mailResditVO.setResditFromCarditEnquiry(resditFromCarditEnquiry);
			mailResditVO.setCarrierId(mailbagVO.getCarrierId());
			mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
			mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			mailResditVO.setContainerNumber(mailbagVO.getContainerNumber());
			mailResditVO.setMailboxID(mailbagVO.getMailboxId());
			mailResditVOs.add(mailResditVO);
			stampResdits(mailResditVOs);
				log.exiting(CLASS, "flagResditsForSendResditsFromCarditEnquiry");

	}
}