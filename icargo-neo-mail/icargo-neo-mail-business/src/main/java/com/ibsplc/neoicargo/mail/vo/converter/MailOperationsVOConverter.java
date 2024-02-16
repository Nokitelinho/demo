package com.ibsplc.neoicargo.mail.vo.converter;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.vo.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	a-4809	:	Jul 31, 2014	:	Draft
 */
@Slf4j
public class MailOperationsVOConverter {
	private static final String FLAG_YES = "Y";
	private static final String FLAG_NO = "N";

	/** 
	* Method		:	MailtrackingDefaultsVOConverter.convertToMailbagInULDForSegmentVOs Added by 	:	a-4809 on Jul 31, 2014 Used for 	:   Vo convertion from MailbagInULDAtAirportVO to MailbagInULDForSegmentVO Parameters	:	@param mailbagInULDAtAirportVOs Parameters	:	@param airportCode Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<MailbagInULDForSegmentVO>
	*/
	public static Collection<MailbagInULDForSegmentVO> convertToMailbagInULDForSegmentVOs(
			Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs, String airportCode) throws SystemException {
		LocalDate localDate = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("MailtrackingDefaultsVOConverter" + " : " + "convertToMailbagInULDForSegmentVOs" + " Entering");
		Collection<MailbagInULDForSegmentVO> mailbagsInULDSeg = new ArrayList<MailbagInULDForSegmentVO>();
		for (MailbagInULDAtAirportVO mailbagInULDArp : mailbagInULDAtAirportVOs) {
			MailbagInULDForSegmentVO mailbagInULDSeg = new MailbagInULDForSegmentVO();
			mailbagInULDSeg.setMailId(mailbagInULDArp.getMailId());
			mailbagInULDSeg.setContainerNumber(mailbagInULDArp.getContainerNumber());
			mailbagInULDSeg.setDamageFlag(mailbagInULDArp.getDamageFlag());
			mailbagInULDSeg.setScannedDate(mailbagInULDArp.getScannedDate());
			mailbagInULDSeg.setWeight(mailbagInULDArp.getWeight());
			mailbagInULDSeg.setMailClass(mailbagInULDArp.getMailClass());
			mailbagInULDSeg.setMailSubclass(mailbagInULDArp.getMailSubclass());
			mailbagInULDSeg.setMailCategoryCode(mailbagInULDArp.getMailCategoryCode());
			mailbagInULDSeg.setTransferFromCarrier(mailbagInULDArp.getTransferFromCarrier());
			mailbagInULDSeg.setSealNumber(mailbagInULDArp.getSealNumber());
			mailbagInULDSeg.setAcceptanceFlag(FLAG_YES);
			mailbagInULDSeg.setArrivalFlag(FLAG_NO);
			mailbagInULDSeg.setDeliveredStatus(FLAG_NO);
			mailbagInULDSeg.setMraStatus(FLAG_NO);
			mailbagInULDSeg.setScannedPort(airportCode);
			mailbagInULDSeg.setScannedDate(localDate.getLocalDate(airportCode, true));
			mailbagInULDSeg.setMailClass(mailbagInULDArp.getMailClass());
			mailbagInULDSeg.setMailSequenceNumber(mailbagInULDArp.getMailSequenceNumber());
			mailbagsInULDSeg.add(mailbagInULDSeg);
		}
		log.debug("" + "MailbagInULDForSegmentVO constructed :-" + " " + mailbagsInULDSeg);
		log.debug("MailtrackingDefaultsVOConverter" + " : " + "convertToMailbagInULDForSegmentVOs" + " Exiting");
		return mailbagsInULDSeg;
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	public static ContainerDetailsVO convertToContainerDetails(ContainerVO containerVO) throws SystemException {
		log.debug("MailtrackingDefaultsVOConverter" + " : " + "convertToContainerDetails" + " Entering");
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setAcceptedFlag(containerVO.getAcceptanceFlag());
		containerDetailsVO.setArrivedStatus(containerVO.getArrivedStatus());
		containerDetailsVO.setAssignedUser(containerVO.getAssignedUser());
		containerDetailsVO.setAssignmentDate(containerVO.getAssignedDate());
		containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setContainerJnyId(containerVO.getContainerJnyID());
		containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
		containerDetailsVO.setContainerOperationFlag(containerVO.getOperationFlag());
		containerDetailsVO.setContainerType(containerVO.getType());
		containerDetailsVO.setDeliveredStatus(containerVO.getDeliveredStatus());
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setIntact(containerVO.getIntact());
		containerDetailsVO.setLastUpdateTime(containerVO.getLastUpdateTime());
		containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		containerDetailsVO.setLocation(containerVO.getLocationCode());
		containerDetailsVO.setOffloadFlag(containerVO.getOffloadFlag());
		containerDetailsVO.setOnwardFlights(containerVO.getOnwardFlights());
		containerDetailsVO.setOperationFlag(containerVO.getOperationFlag());
		containerDetailsVO.setOwnAirlineCode(containerVO.getOwnAirlineCode());
		containerDetailsVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		containerDetailsVO.setPaCode(containerVO.getPaCode());
		containerDetailsVO.setPou(containerVO.getPou());
		containerDetailsVO.setPreassignFlag(containerVO.isPreassignNeeded());
		containerDetailsVO.setReassignFlag(containerVO.isReassignFlag());
		containerDetailsVO.setReleasedFlag(containerVO.getReleasedFlag());
		containerDetailsVO.setRemarks(containerVO.getRemarks());
		containerDetailsVO.setScanDate(containerVO.getScannedDate());
		containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		containerDetailsVO.setTotalBags(containerVO.getBags());
		containerDetailsVO.setTotalWeight(containerVO.getWeight());
		containerDetailsVO.setTransactionCode(containerVO.getTransactionCode());
		containerDetailsVO.setTransferFlag(containerVO.getTransferFlag());
		containerDetailsVO.setTransitFlag(containerVO.getTransitFlag());
		containerDetailsVO.setUldLastUpdateTime(containerVO.getULDLastUpdateTime());
		containerDetailsVO.setWareHouse(containerVO.getWarehouseCode());
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		log.debug("MailtrackingDefaultsVOConverter" + " : " + "convertToContainerDetails" + " Entering");
		return containerDetailsVO;
	}

	public static MailbagVO convertToMailBagVO(DespatchDetailsVO despatchDetailsVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId(createMailBag(despatchDetailsVO));
		mailbagVO.setContainerNumber(despatchDetailsVO.getContainerNumber());
		mailbagVO.setCarrierId(despatchDetailsVO.getCarrierId());
		mailbagVO.setFlightNumber(despatchDetailsVO.getFlightNumber());
		mailbagVO.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
		mailbagVO.setUldNumber(despatchDetailsVO.getUldNumber());
		mailbagVO.setAcceptedWeight(despatchDetailsVO.getAcceptedWeight());
		mailbagVO.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
		mailbagVO.setMailClass(despatchDetailsVO.getMailClass());
		mailbagVO.setMailSubclass(despatchDetailsVO.getMailSubclass());
		mailbagVO.setSegmentSerialNumber(despatchDetailsVO.getSegmentSerialNumber());
		mailbagVO.setScannedDate(despatchDetailsVO.getAcceptedDate());
		mailbagVO.setScannedPort(despatchDetailsVO.getAirportCode());
		mailbagVO.setOperationalFlag(despatchDetailsVO.getOperationalFlag());
		mailbagVO.setPaBuiltFlag(despatchDetailsVO.getPaBuiltFlag());
		mailbagVO.setPou(despatchDetailsVO.getPou());
		mailbagVO.setScannedUser(despatchDetailsVO.getAcceptedUser());
		mailbagVO.setAcceptedBags(despatchDetailsVO.getAcceptedBags());
		mailbagVO.setDespatch(true);
		return mailbagVO;
	}

	public static String createMailBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder mailBagid = new StringBuilder();
		mailBagid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode()).append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear()).append(despatchDetailsVO.getDsn());
		return mailBagid.toString();
	}

	public static String createMailBag(DSNVO dsnvo) {
		StringBuilder mailBagid = new StringBuilder();
		mailBagid.append(dsnvo.getOriginExchangeOffice()).append(dsnvo.getDestinationExchangeOffice())
				.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass()).append(dsnvo.getYear())
				.append(dsnvo.getDsn());
		return mailBagid.toString();
	}

	public static FlightFilterVO constructFlightFilterVOForContainer(MailbagVO mailbagvo) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagvo.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagvo.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagvo.getFlightNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagvo.getFlightDate()));
		flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		flightFilterVO.setStation(mailbagvo.getPou());
		flightFilterVO.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
		return flightFilterVO;
	}

	/** 
	* Added for CRQ IASCB-74698
	* @author A-5526
	* @param mailUploadVO
	* @return
	* @throws SystemException 
	*/
	public static List<DocumentRepositoryAttachmentVO> convetToDocumentRepositoryAttachmentVOs(
			MailUploadVO mailUploadVO) throws SystemException {
		List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = new ArrayList<>();
		if (mailUploadVO.getAttachments() != null && !mailUploadVO.getAttachments().isEmpty()) {
			for (MailAttachmentVO mailAttachmentVO : mailUploadVO.getAttachments()) {
				documentRepositoryAttachmentVOs
						.add(convetToDocumentRepositoryAttachmentVO(mailAttachmentVO, mailUploadVO));
			}
		}
		return documentRepositoryAttachmentVOs;
	}

	/** 
	* Added for CRQ IASCB-74698
	* @author A-5526
	* @param mailAttachmentVO
	* @return
	* @throws SystemException
	*/
	public static DocumentRepositoryAttachmentVO convetToDocumentRepositoryAttachmentVO(
			MailAttachmentVO mailAttachmentVO, MailUploadVO mailUploadVO) throws SystemException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO = new DocumentRepositoryAttachmentVO();
		documentRepositoryAttachmentVO.setCompanyCode(mailAttachmentVO.getCompanyCode());
		documentRepositoryAttachmentVO.setFileName(mailAttachmentVO.getFileName());
		documentRepositoryAttachmentVO.setAttachmentData(mailAttachmentVO.getFileData());
		documentRepositoryAttachmentVO.setAttachmentType(mailAttachmentVO.getAttachmentType());
		documentRepositoryAttachmentVO.setAirportCode(logonAttributes.getAirportCode());
		documentRepositoryAttachmentVO.setOperationFlag(mailAttachmentVO.getAttachmentOpFlag());
		documentRepositoryAttachmentVO.setDocumentRepoSerialNumber(mailAttachmentVO.getDocSerialNumber());
		documentRepositoryAttachmentVO.setRemarks(mailAttachmentVO.getRemarks());
		documentRepositoryAttachmentVO.setTxnRefereceOverride(true);
		documentRepositoryAttachmentVO.setReference1("MALIDR");
		documentRepositoryAttachmentVO.setTransactionDataRef1(mailUploadVO.getMailTag());
		documentRepositoryAttachmentVO.setReference2("DMGCOD");
		documentRepositoryAttachmentVO.setTransactionDataRef2(mailUploadVO.getDamageCode());
		return documentRepositoryAttachmentVO;
	}

	public static MailResditMessageVO populateMailResditMessageVO(
			Collection<ResditEventVO> resditEventVOs, Collection<MessageVO> messageVOs){

		String msgTxt = null;
		MailResditMessageVO mailResditMessageVO = new MailResditMessageVO();
		ResditEventVO resditEventVO = resditEventVOs.iterator().next();
		MessageVO messageVO = messageVOs.iterator().next();
		StringBuilder evtCodes=new StringBuilder();
		/**
		 * Added HashSet of event codes as part of ICRD-344287
		 * Prevent column length exceeds exception if there are multiple events in the message
		 */
		HashSet<String> eventCodesSet = new HashSet<>();
		for(ResditEventVO rdtEventVO:resditEventVOs){
			if(!eventCodesSet.contains(rdtEventVO.getResditEventCode())) {
				eventCodesSet.add(rdtEventVO.getResditEventCode());
				evtCodes.append(rdtEventVO.getResditEventCode()).append(",");
			}
		}
		msgTxt = messageVOs.iterator().next().getRawMessageBlob();
		mailResditMessageVO.setMsgSequenceNumber(messageVOs.iterator().next().getSequenceNumber());
		mailResditMessageVO.setMsgDetails(msgTxt);
		if(msgTxt.length()>4000){
			mailResditMessageVO.setMsgText(msgTxt.trim().substring(0, 3500));
		}
		else{
			mailResditMessageVO.setMsgText(msgTxt);
		}
		mailResditMessageVO.setPoaCode(resditEventVO.getPaCode());
		mailResditMessageVO.setMsgVersionNumber(resditEventVO.getResditVersion());
		mailResditMessageVO.setCarditExist(resditEventVO.getCarditExist());
		mailResditMessageVO.setMessageType(messageVO.getMessageType());
		mailResditMessageVO.setCompanyCode(messageVO.getCompanyCode());
		//mailResditMessageVO.setMessageIdentifier(messageVO.getMessageIdentifierTwo());
		mailResditMessageVO.setEventCode(evtCodes.toString().substring(0, evtCodes.length()-1));
		mailResditMessageVO.setEventDate(resditEventVO.getEventDate());
		mailResditMessageVO.setEventPort(resditEventVO.getEventPort());



		return mailResditMessageVO;
	}

}
