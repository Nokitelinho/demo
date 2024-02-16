 /*
 * MailBagAuditBuilder.java Created on Nov 5 2015 by A-5945 for ICRD-119569
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.builder;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagAudit;
import com.ibsplc.icargo.business.mail.operations.MailbagHistory;
import com.ibsplc.icargo.business.mail.operations.MailbagHistoryPK;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalCalendarAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailBagAuditBuilder extends AbstractActionBuilder {

	private Log log = LogFactory.getLogger("MailBag Audit BUILDER");



	public void auditMailBagDeletion(MailbagVO mailbagvo,String actionCode)
			throws SystemException{
		 log.entering("auditMailBagDeletion", "auditMailBagDeletion");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		//mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,this,true);
		mailbagAuditVO.setActionCode(MailbagAuditVO.MAILBAG_DELETED);
		mailbagAuditVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		mailbagAuditVO.setCompanyCode(mailbagvo.getCompanyCode());
		mailbagAuditVO.setMailbagId(mailbagvo.getMailbagId());
		mailbagAuditVO.setDsn(mailbagvo.getDespatchSerialNumber());
		mailbagAuditVO.setOriginExchangeOffice(mailbagvo.getOoe());
		mailbagAuditVO.setDestinationExchangeOffice(mailbagvo.getDoe());
		mailbagAuditVO.setMailSubclass(mailbagvo.getMailSubclass());
		mailbagAuditVO.setMailCategoryCode(mailbagvo.getMailCategoryCode());
		mailbagAuditVO.setYear(mailbagvo.getYear());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("Mailbag ").append(mailbagvo.getMailbagId()).append(" deleted");
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());



         AuditUtils .performAudit(mailbagAuditVO);
         log.exiting("auditMailBagDeletion", "auditMailBagDeletion");
	}

	/**
	 *
	 * @param mailbagvo
	 * @param actionCode
	 * @throws SystemException
	 */
	public void auditMailbagUpdate(MailbagVO mailbagvo,String actionCode)throws SystemException{
		 log.entering("auditMailbagUpdate", "MailBagAuditBuilder");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		 MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		 mailbagAuditVO.setActionCode(MailbagAuditVO.MAILBAG_MODIFIED);
		 mailbagAuditVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		 mailbagAuditVO.setCompanyCode(mailbagvo.getCompanyCode());
		 mailbagAuditVO.setMailbagId(mailbagvo.getMailbagId());
		 StringBuilder additionalInfo = new StringBuilder();
		 if(MailbagAuditVO.MAILBAG_ORG_DEST_MODIFIED.equals(actionCode)){
			 mailbagAuditVO.setActionCode(MailbagAuditVO.MAILBAG_ORG_DEST_MODIFIED);
			 if(mailbagvo.isOriginUpdate()){
				 additionalInfo.append("Origin modified on ").append(new LocalDate(logonAttributes
				 .getAirportCode(), Location.ARP, true).toDisplayFormat()).append("; from ").append(mailbagvo.getOrigin()).append(";  to ").append(mailbagvo.getMailOrigin())
				 .append("; by user ").append(logonAttributes.getUserId()).append("; Source: ").append(mailbagvo.getMailbagSource()).append("<br>");	 
			 }
			 if (mailbagvo.isDestinationUpdate()){
				 additionalInfo.append("Destination modified on ").append(new LocalDate(logonAttributes
						 .getAirportCode(), Location.ARP, true).toDisplayFormat()).append("; from ").append(mailbagvo.getDestination()).append(";  to ").append(mailbagvo.getMailDestination())
						 .append("; by user ").append(logonAttributes.getUserId()).append("; Source: ").append(mailbagvo.getMailbagSource());	
			 }
		 }
		 else{
		 additionalInfo.append("Mailbag ").append(mailbagvo.getMailbagId()).append(" modified ");
		 }
		 mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		 AuditFieldVO fieldVO = new AuditFieldVO("Mailbag ID",mailbagvo.getMailbagId(),mailbagvo.getNewMailId());
		 Collection<AuditFieldVO> fields = new ArrayList<AuditFieldVO>();
		 fields.add(fieldVO);
		 mailbagAuditVO.setAuditFields(fields);
		 mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		 AuditUtils .performAudit(mailbagAuditVO);
		 log.exiting("auditMailbagUpdate", "MailBagAuditBuilder");
	}

	/**
	 *
	 * @param mailAcceptanceVO
	 * @throws SystemException
	 */
	public void auditContainer(MailAcceptanceVO mailAcceptanceVO)
	throws SystemException{
		log.entering("MailBagAuditBuilder", "auditContainer");
		Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO
		.getContainerDetails();
		if(containerDetails != null && !containerDetails.isEmpty()){
			StringBuilder additInfo= null;
			ContainerAuditVO containerAuditVO = new ContainerAuditVO(
					ContainerVO.MODULE, ContainerVO.SUBMODULE,
					ContainerVO.ENTITY);
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				if (OPERATION_FLAG_INSERT.equals(containerDetailsVO
						.getOperationFlag())) {
					containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_ACCEPTANCE);
					containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
					containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
					containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
					containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
					containerAuditVO.setFlightSequenceNumber(
							containerDetailsVO.getFlightSequenceNumber());
					containerAuditVO.setAssignedPort(containerDetailsVO.getPol());
					containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
					//added as part of IASCB-67053
					ContainerPK containerPk = new ContainerPK();
					Container container = null;
					containerPk.setContainerNumber(containerDetailsVO.getContainerNumber());
					if(containerDetailsVO.getAssignedPort()!=null) {
					containerPk.setAssignmentPort(containerDetailsVO.getAssignedPort());
					} else {
						containerPk.setAssignmentPort(containerDetailsVO.getPol());
					}
					containerPk.setCarrierId(containerDetailsVO.getCarrierId());
					containerPk.setFlightNumber(containerDetailsVO.getFlightNumber());
					containerPk.setFlightSequenceNumber(containerDetailsVO
							.getFlightSequenceNumber());
					containerPk.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
					containerPk.setCompanyCode(containerDetailsVO.getCompanyCode());
					try {
						container = Container.find(containerPk);
					} catch (FinderException ex) {
						log.log(log.FINE, "container is null");
					}
					if(container!=null) {
					additInfo = new StringBuilder();
					
					additInfo.append("Accepted to ");
					if(!"-1".equals(container.getContainerPK().getFlightNumber())){
						additInfo.append(container.getCarrierCode()).append(" ").append(container.getContainerPK().getFlightNumber()).append(", ");
					}else{
						additInfo.append(container.getCarrierCode()).append(", ");
					 }
					additInfo.append(new LocalDate(containerPk.getAssignmentPort(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
					if(!"-1".equals(container.getContainerPK().getFlightNumber())){
					additInfo.append(containerDetailsVO.getPol()).append(" - ").append(containerDetailsVO.getPou()).append(" ");
					}
					additInfo.append("in ").append(containerPk.getAssignmentPort());
					containerAuditVO.setAdditionalInformation(additInfo.toString());
					}
					if(mailAcceptanceVO.getMailSource()!=null){
						if(MailConstantsVO.MRD.equals(mailAcceptanceVO.getMailSource())){
							containerAuditVO.setTriggerPnt(MailConstantsVO.IPC_MRD);
						}else{
							containerAuditVO.setTriggerPnt(mailAcceptanceVO.getMailSource());
						}
					}
					containerAuditVO.setAuditRemarks("Mail ULD Accepted");
					AuditUtils.performAudit(containerAuditVO);
				}
			}
		}
		log.exiting("MailBagAuditBuilder", "auditContainer");
}


	public void auditInvoiceStatusMailbaglevel(InvoiceSettlementVO unSettledInvoiceVO) throws SystemException{
		 log.entering("auditInvoiceStatusMailbaglevel", "auditInvoiceStatusMailbaglevel");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_SETTLEMENT);
		mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_SETTLEMENT);
		mailbagAuditVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagAuditVO.setStationCode(logonAttributes.getAirportCode());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("Invoice settlement Mailbag level : Invoice number - ").append(unSettledInvoiceVO.getInvoiceNumber()).append(" SettlementRefernceNumber: -").append(unSettledInvoiceVO.getSettlementReferenceId()).append(" Amount:- ").append(unSettledInvoiceVO.getCurrentSettlingAmount()).append(unSettledInvoiceVO.getSettlementCurrencyCode()).append(" Source:- ").append(unSettledInvoiceVO.getFromScreen());
		mailbagAuditVO.setAdditionalInformation(additionalInfo
				.toString());
		mailbagAuditVO.setMailbagId(unSettledInvoiceVO.getMailbagID());
		mailbagAuditVO.setMailSequenceNumber(unSettledInvoiceVO.getMailsequenceNum());
		AuditUtils.performAudit(mailbagAuditVO);

         log.exiting("auditInvoiceStatusMailbaglevel", "auditInvoiceStatusMailbaglevel");
	}
	/**
	 *
	 * 	Method		:	MailBagAuditBuilder.auditCarditCancellation
	 *	Added by 	:	U-1467 on 18-Feb-2020
	 * 	Used for 	:	IASCB-36803
	 *	Parameters	:	@param carditVO
	 *	Parameters	:	@param actionCode
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void auditCarditCancellation(CarditVO carditVO, String actionCode)throws SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		if(carditVO.getReceptacleInformation()!=null && carditVO.getReceptacleInformation().size()>0){
			for(CarditReceptacleVO carditReceptacleVO:carditVO.getReceptacleInformation()){
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setMailSequenceNumber(carditReceptacleVO.getMailSeqNum());
				mailbagAuditVO.setCompanyCode(carditVO.getCompanyCode());
				mailbagAuditVO.setMailbagId(carditReceptacleVO.getReceptacleId());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append("Mailbag ").append(carditReceptacleVO.getReceptacleId()).append(" deleted");
				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				AuditUtils.performAudit(mailbagAuditVO);
			}
		}
	}
	/**
	 *
	 * 	Method		:	MailBagAuditBuilder.auditPostalCalendar
	 *	Added by 	:	A-5219 on 13-Aug-2020
	 * 	Used for 	:
	 *	Parameters	:	@param uSPSPostalCalendarVOs
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void auditPostalCalendar(Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs)
			throws SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		if (uSPSPostalCalendarVOs != null && uSPSPostalCalendarVOs.size() > 0) {
			for (USPSPostalCalendarVO uSPSPostalCalendarVO : uSPSPostalCalendarVOs) {
				PostalCalendarAuditVO auditVO = null;
				if("I".equals(uSPSPostalCalendarVO.getFilterCalender())){
				if (USPSPostalCalendarVO.OPERATION_FLAG_INSERT
						.equals(uSPSPostalCalendarVO.getCalOperationFlags())) {
					 auditVO = new PostalCalendarAuditVO(PostalCalendarAuditVO.AUDIT_MODULENAME,PostalCalendarAuditVO.AUDIT_SUBMODULENAME,PostalCalendarAuditVO.AUDIT_ENTITY);
					 auditVO.setCompanyCode(uSPSPostalCalendarVO.getCompanyCode());
					 auditVO.setActionCode(PostalCalendarAuditVO.POSTAL_CALENDAR_CREATED);
					 auditVO.setPostalCode(uSPSPostalCalendarVO.getGpacod());
					 auditVO.setPeriod(uSPSPostalCalendarVO.getPeriods());
					 auditVO.setAdditionalInformation(new StringBuilder("Postal Calendar created for the period ").append(uSPSPostalCalendarVO.getPeriods()).toString());
					 auditVO.setStationCode(logonAttributes.getStationCode());
					 auditVO.setUserId(uSPSPostalCalendarVO.getLstUpdUsr());
					 AuditUtils.performAudit(auditVO);
				}else if(USPSPostalCalendarVO.OPERATION_FLAG_DELETE
						.equals(uSPSPostalCalendarVO.getCalOperationFlags())){
					 auditVO = new PostalCalendarAuditVO(PostalCalendarAuditVO.AUDIT_MODULENAME,PostalCalendarAuditVO.AUDIT_SUBMODULENAME,PostalCalendarAuditVO.AUDIT_ENTITY);
					 auditVO.setCompanyCode(uSPSPostalCalendarVO.getCompanyCode());
					 auditVO.setActionCode(PostalCalendarAuditVO.POSTAL_CALENDAR_DELETED);
					 auditVO.setPostalCode(uSPSPostalCalendarVO.getGpacod());
					 auditVO.setPeriod(uSPSPostalCalendarVO.getPeriods());
					 auditVO.setAdditionalInformation(new StringBuilder("Postal Calendar deleted for the period ").append(uSPSPostalCalendarVO.getPeriods()).toString());
					 auditVO.setStationCode(logonAttributes.getStationCode());
					 auditVO.setUserId(uSPSPostalCalendarVO.getLstUpdUsr());
					 AuditUtils.performAudit(auditVO);
					}
				}
			}
		}
	}

	//IASCB-51842 starts
	public void flagAuditForReturnedMailbags(Collection<MailbagVO> mailbags) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagAuditForReturnedMailbags");

		for (MailbagVO mailbagVO : mailbags) {
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			String actionCode = mailbagAuditVO.MAILBAG_RETURNED;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
			}
			additionalInfo.append(" ; at airport ");
			if(mailbagVO.getScannedPort()!=null){
				additionalInfo.append(mailbagVO.getScannedPort());
			}else{
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(mailbagVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
			if(mailbagVO.getMailSource()!=null){
				additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());	
			}
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
			
		}
		log.exiting("MailbagAuditBuilder", "flagAuditForReturnedMailbags");
	}
	
	public void flagMailbagAuditForArrival(MailArrivalVO mailArrivalVO)
			throws SystemException {
		log.entering("MailbagAuditBuilder", "flagMailbagAuditForArrival");
		String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		Collection<ContainerDetailsVO> containerVOs = mailArrivalVO.getContainerDetails(); 
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>(); 
		
		for (ContainerDetailsVO containerVO : containerVOs) {
			Collection<MailbagVO> totalMails = containerVO.getMailDetails();
			if(totalMails!=null){
				mailbags = totalMails;
			}
		}

		for (MailbagVO mailbagVOs : mailbags) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVOs.getCompanyCode(), mailbagVOs));
			} catch (FinderException e) {
				mailbag = null;
			}
		
		boolean[] arrDlv = checkIfHistoryExists(mailbag, mailbagVOs);
		boolean isAlreadyArrived = arrDlv[0];
		boolean isAlreadyDelivered = arrDlv[1];
		
		if(!isAlreadyArrived){
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Object uldNumber= null;
		String mailbagID = null;
		Long mailSequenceNumber = null;
		for(ContainerDetailsVO container : mailArrivalVO.getContainerDetails()){
			uldNumber = container.getContainerNumber();
			for(MailbagVO mailbagVO : container.getMailDetails()){
				mailbagID = mailbagVO.getMailbagId();
				mailSequenceNumber = mailbagVO.getMailSequenceNumber();
			}
		}
		String actionCode= MailbagAuditVO.MAILBAG_ARRIVED;
		mailbagAuditVO.setActionCode(actionCode);
		mailbagAuditVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		mailbagAuditVO.setMailbagId(mailbagID);
		mailbagAuditVO.setMailSequenceNumber(mailSequenceNumber);
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(mailArrivalVO.getArrivalDate()!=null){
			additionalInfo.append(" on ").append(mailArrivalVO.getArrivalDate().toDisplayFormat());
		}
		additionalInfo.append("; to ").append(mailArrivalVO.getFlightCarrierCode()).append(mailArrivalVO.getFlightNumber());
			if(mailArrivalVO.getArrivalDate()!=null){
				additionalInfo.append(" ").append(mailArrivalVO.getArrivalDate().toDisplayFormat());
			}
		additionalInfo.append(" ; at airport ");
		if(mailbagVOs.getScannedPort()!=null){
			additionalInfo.append(mailbagVOs.getScannedPort());
		}else{
			additionalInfo.append(logonAttributes.getAirportCode());
		}
		if(uldNumber!=null){
		additionalInfo.append(" ; in container ").append(uldNumber);
		}
		if(mailArrivalVO.getPou()!=null){
		additionalInfo.append(" ; POU -").append(mailArrivalVO.getPou());
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
		additionalInfo.append(" ; Source -").append(triggeringPoint);	
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);
			}
		
		if(!isAlreadyDelivered && MailConstantsVO.FLAG_YES.equals(mailbagVOs.getDeliveredFlag())){
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			Object uldNumber= null;
			String mailbagID = null;
			Long mailSequenceNumber = null;
			for(ContainerDetailsVO container : mailArrivalVO.getContainerDetails()){
				uldNumber = container.getContainerNumber();
				for(MailbagVO mailbagVO : container.getMailDetails()){
					mailbagID = mailbagVO.getMailbagId();
					mailSequenceNumber = mailbagVO.getMailSequenceNumber();
				}
			}
			String actionCode= MailbagAuditVO.MAILBAG_DELIVERED;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setCompanyCode(mailArrivalVO.getCompanyCode());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			mailbagAuditVO.setMailbagId(mailbagID);
			mailbagAuditVO.setMailSequenceNumber(mailSequenceNumber);
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVOs.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVOs.getScannedDate().toDisplayFormat());
			}
			additionalInfo.append("; to ").append(mailArrivalVO.getFlightCarrierCode()).append(mailArrivalVO.getFlightNumber());
			if(mailbagVOs.getScannedDate()!=null){
				additionalInfo.append(" ").append(mailbagVOs.getScannedDate().toDisplayFormat());
			}
			additionalInfo.append(" ; at airport ");
			if(mailbagVOs.getScannedPort()!=null){
				additionalInfo.append(mailbagVOs.getScannedPort());
			}else{
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(uldNumber!=null){
			additionalInfo.append(" ; in container ").append(uldNumber);
			}
			if(mailbagVOs.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVOs.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
			additionalInfo.append(" ; Source -").append(triggeringPoint);		
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
				}
		log.exiting("HistoryBuilder", "flagMailbagHistoryForArrival");
		}
	}
	
	public void flagAuditForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) throws SystemException {
		log.entering("MailBagAuditBuilder", "flagAuditForTransfer");
		
		for(MailbagVO mailbagVO : mailbagVOs){
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			String actionCode= MailbagAuditVO.MAILBAG_TRANSFER;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
			}
			if(!("-1".equals(containerVO.getFlightNumber()))){
			additionalInfo.append("; to ").append(containerVO.getCarrierCode()).append(containerVO.getFlightNumber());
			if(mailbagVO.getFlightDate()!=null){
				additionalInfo.append(" ").append(containerVO.getFlightDate().toDisplayFormat());
				}
			}else{
				additionalInfo.append("; to ").append(containerVO.getCarrierCode());
			}
			additionalInfo.append(" ; at airport ");
			if(mailbagVO.getScannedPort()!=null){
				additionalInfo.append(mailbagVO.getScannedPort());
			}else{
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(containerVO.getContainerNumber()!=null){
			additionalInfo.append(" ; in container ").append(containerVO.getContainerNumber());
			}
			if(mailbagVO.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
			append(" ; Source -").append(mailbagVO.getMailSource());		
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);
		}
		

		log.exiting("MailBagAuditBuilder", "flagAuditForTransfer");
	}
	
	public void insertOrUpdateAuditDetailsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO> acceptedMailbags) throws SystemException {
		log.entering("MailBagAuditBuilder",
				"insertOrUpdateAuditDetailsForAcceptance");

		for (MailbagVO mailbagVO : acceptedMailbags) {
			if(!mailAcceptanceVO.isAssignedToFlight()){
			MailbagAuditVO mailbagAuditVO = constructAuditVO(mailbagVO,MailbagAuditVO. MAILBAG_ACCEPTANCE);
			AuditUtils .performAudit(mailbagAuditVO);
			}else{
			MailbagAuditVO mailbagAuditVO = constructAuditVO(mailbagVO,MailbagAuditVO. MAILBAG_ASSIGNED);	
			AuditUtils .performAudit(mailbagAuditVO);
			}
		}
		
		log.exiting("MailBagAuditBuilder",
				"insertOrUpdateAuditDetailsForAcceptance");

	}
	
	public void flagAuditDetailsForMailbagsFromReassign(
			Collection<MailbagVO> acceptedMailbags, ContainerVO toContainerVO)
			throws SystemException {
		log.entering("MailBagAuditBuilder",
				"flagAuditDetailsForMailbagsFromReassign");
		
		for (MailbagVO mailbagVO : acceptedMailbags) {
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			
			if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT && mailbagVO.isIsoffload()) {
					String actionCode= MailbagAuditVO.MAILBAG_OFFLOAD;
					mailbagAuditVO.setActionCode(actionCode);
					mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
					mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
					mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
					mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
					mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
					mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					mailbagAuditVO.setYear(mailbagVO.getYear());
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
					StringBuffer additionalInfo = new StringBuffer();
					additionalInfo.append(actionCode.toLowerCase());
					if(mailbagVO.getScannedDate()!=null){
						additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
					}
					additionalInfo.append(" ").append(mailbagVO.getCarrierCode());
					additionalInfo.append(" ; at airport ");
					if(mailbagVO.getScannedPort()!=null){
						additionalInfo.append(mailbagVO.getScannedPort());
					}else{
						additionalInfo.append(logonAttributes.getAirportCode());
					}
					if(toContainerVO.getPou()!=null){
					additionalInfo.append(" ; POU - ").append(toContainerVO.getPou());
					}
					additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
					append(" ; Source -").append(mailbagVO.getMailSource());
					if(mailbagVO.isRemove() && mailbagVO.getOffloadedRemarks()!=null) {
						additionalInfo.append(" ; Remarks - ").append(mailbagVO.getOffloadedRemarks());
					}
					mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
					AuditUtils .performAudit(mailbagAuditVO);
			}else{
			String actionCode= MailbagAuditVO.MAILBAG_REASSIGN;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(mailbagVO.getScannedDate()!=null){
					additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
				}
				if(!("-1".equals(toContainerVO.getFlightNumber()))){
					additionalInfo.append("; to ").append(toContainerVO.getCarrierCode()).append(toContainerVO.getFlightNumber());
					if(toContainerVO.getFlightDate()!=null){
						additionalInfo.append(" ").append(toContainerVO.getFlightDate().toDisplayFormat());
					}
				}else{
					additionalInfo.append(toContainerVO.getCarrierCode());
				}
				if(toContainerVO.getContainerNumber()!=null){
					additionalInfo.append(" ; to container ").append(toContainerVO.getContainerNumber());
				}
				additionalInfo.append(" ; at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(toContainerVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(toContainerVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
				append(" ; Source -").append(mailbagVO.getMailSource());		

			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
			}

		}
		
		log.exiting("MailBagAuditBuilder",
				"flagAuditDetailsForMailbagsFromReassign");

	}
	
	public void flagAuditforReadyForDelivery(Collection<MailbagVO> mailbagVOs,OperationalFlightVO operationFlightVO)
			throws SystemException {
		
		for(MailbagVO mailbagVO : mailbagVOs){
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String actionCode= MailbagAuditVO.MAILBAG_READYFORDELIVERY;
		mailbagAuditVO.setActionCode(actionCode);
		mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
		mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
		mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
		mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
		mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
		mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
		mailbagAuditVO.setYear(mailbagVO.getYear());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(mailbagVO.getScannedDate()!=null){
			additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
		}
		additionalInfo.append("; to ").append(operationFlightVO.getCarrierCode()).append(operationFlightVO.getFlightNumber());
		if(operationFlightVO.getFlightDate()!=null){
			additionalInfo.append(operationFlightVO.getFlightDate().toDisplayFormat());
		}
		additionalInfo.append(" ; at airport");
		if(mailbagVO.getScannedPort()!=null){
			additionalInfo.append(mailbagVO.getScannedPort());
		}else{
			additionalInfo.append(logonAttributes.getAirportCode());
		}
		if(mailbagVO.getUldNumber()!=null){
		additionalInfo.append(" ; in container ").append(mailbagVO.getUldNumber());
		}
		if(mailbagVO.getPou()!=null){
		additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
		append(" ; Source -").append(mailbagVO.getMailSource());		
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);
		}	
	}

	public void flagMailbagAuditForDelivery(Collection<MailbagVO> mailbags) // Acceptance through Resdit flow
			throws SystemException {
		log.entering("MailBagAuditBuilder", "flagMailbagAuditForDelivery");
		if (mailbags != null && mailbags.size() > 0) {

			for (MailbagVO mailBagVo : mailbags) {
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(createMailbagPK(
							mailBagVo.getCompanyCode(), mailBagVo));
				} catch (FinderException e) {
					mailbag = null;
				}
				if (mailbag != null) {

					MailbagAuditVO mailbagAuditVO = constructAuditVO(mailBagVo,MailbagAuditVO.MAILBAG_ACCEPTANCE);
					AuditUtils .performAudit(mailbagAuditVO);
				}
			}

		}
		log.exiting("MailBagAuditBuilder", "flagMailbagAuditForDelivery");

	}
	
			public void flagAuditForMailOperartions(Collection<MailbagVO> mailbags, String actionCode) // Acceptance through Resdit flow
					throws SystemException {
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailBagVo : mailbags) {
						Mailbag mailbag = null;
						try {
							mailbag = Mailbag.find(createMailbagPK(
									mailBagVo.getCompanyCode(), mailBagVo));
						} catch (FinderException e) {
							log.log(log.SEVERE, e);
							mailbag = null;
						}
						if (mailbag != null) {
							MailbagAuditVO mailbagAuditVO = constructAuditVO(mailBagVo,actionCode);
							AuditUtils .performAudit(mailbagAuditVO);
						}
					}
				}
			}
	public void flagAuditForDamagedMailbags(Collection<MailbagVO> mailbags)
			throws SystemException {
		log.entering("MailBagAuditBuilder", "flagAuditForDamagedMailbags");
		for (MailbagVO mailbagVO : mailbags) {
			MailbagAuditVO mailbagAuditVO = constructAuditVO(mailbagVO,MailbagAuditVO. MAILBAG_DAMAGED);
			AuditUtils .performAudit(mailbagAuditVO);
			}
		
		log.exiting("MailBagAuditBuilder", "flagAuditForDamagedMailbags");
	}
	
	public void insertOrUpdateAuditDetailsForCardit(CarditVO carditVO) throws SystemException {
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Object containerDetails = null;
		Object airport = null;
		Object pou = null;
		String actionCode = MailbagAuditVO.MAILBAG_CARDIT;
		
	if (carditVO.getReceptacleInformation() != null && carditVO.getReceptacleInformation().size() > 0) {
		for (CarditReceptacleVO carditReceptacleVO : carditVO.getReceptacleInformation()) {
				
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			
			MailbagPK mailbagPK = new MailbagPK();
			long mailSequenceNumber;
			Mailbag mailbag = null;
			mailSequenceNumber = new MailController().findMailSequenceNumber(carditReceptacleVO.getReceptacleId(),
					carditVO.getCompanyCode());
			if (mailSequenceNumber > 0) {
				mailbagPK.setMailSequenceNumber(mailSequenceNumber);
				mailbagPK.setCompanyCode(carditVO.getCompanyCode());				
				try {
					mailbag = Mailbag.find(mailbagPK);
				} catch (FinderException e) {
					mailbag = null;
				}
			}
				
				if (mailbag != null) {
					if(!mailbag.getLatestStatus().isEmpty()&& ("NEW").equals(mailbag.getLatestStatus())){
						containerDetails = mailbag.getUldNumber();
						pou= mailbag.getPou();
					}
				}
				
			StringBuffer flightDetails = new StringBuffer();
			List<CarditTransportationVO> transportVOs = (List<CarditTransportationVO>) carditVO.getTransportInformation();
			if (transportVOs != null && transportVOs.size() > 0) {
				CarditTransportationVO transportInformation = transportVOs.get(0);
				flightDetails.append(transportInformation.getCarrierCode()).append(transportInformation.getFlightNumber()).append(" ");
				if(transportInformation.getArrivalDate()!=null){
					flightDetails.append(transportInformation.getArrivalDate().toDisplayFormat()).append(";");
				}
				airport = transportInformation.getDeparturePort();
			}
		
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setMailSequenceNumber(carditReceptacleVO.getMailSeqNum());
				mailbagAuditVO.setMailbagId(carditReceptacleVO.getReceptacleId());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(carditVO.getCarditReceivedDate()!=null){
			additionalInfo.append(" on ").append(carditVO.getCarditReceivedDate().toDisplayFormat());
		}
		if(carditVO.getCarditReceivedDate()!=null){
		additionalInfo.append(" ; message processed on ").append(carditVO.getCarditReceivedDate().toDisplayTimeOnlyFormat());
		}
		if(flightDetails!=null){
			additionalInfo.append(" ").append(flightDetails);
		}
		if(airport!=null){
			additionalInfo.append(" at airport ").append(airport);
		}
		if(containerDetails!=null){
		additionalInfo.append(" ; in container ").append(containerDetails);
		}
		if(pou!=null){
		additionalInfo.append(" ; POU -").append(pou);
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).append(" ; Source - ").append(MailConstantsVO.CARDIT_EVENT);		
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);
			}
		}
	}
	
	public void flagMailbagAuditForResdit(ResditEventVO resditEventVO, ConsignmentInformationVO consignVO,
			ReceptacleInformationVO receptacleInformationVO) throws SystemException{
		
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		StringBuffer flightDetails = new StringBuffer();
		Object containerNumber = null;
		Object pou = null;
		
		Collection<TransportInformationVO> transportInfoVOs = consignVO.getTransportInformationVOs();
		Collection<ContainerInformationVO> containerInfoVOs = consignVO.getContainerInformationVOs();
		TransportInformationVO transportVO = null;
		ContainerInformationVO containerVO = null;
		String actionCode = null;
		
		if(transportInfoVOs != null && transportInfoVOs.size() > 0) {
			transportVO = ((ArrayList<TransportInformationVO>) transportInfoVOs).get(0);
			flightDetails.append(transportVO.getCarrierCode()).append(transportVO.getFlightNumber()).append(" ");
			if(transportVO.getDepartureDate()!=null){
				flightDetails.append(transportVO.getDepartureDate().toDisplayFormat()).append(";");
			}
			pou = transportVO.getArrivalPlace();
		}
			
		if(consignVO.getContainerInformationVOs()!=null && consignVO.getContainerInformationVOs().size() > 0){
			containerVO = ((ArrayList<ContainerInformationVO>) containerInfoVOs).get(0);
				containerNumber = containerVO.getContainerNumber();
		}
				
		if(MailConstantsVO.RESDIT_RECEIVED.equals(resditEventVO.getResditEventCode())){
		actionCode = MailbagAuditVO.RESDIT_RECEIVED;
		mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_ASSIGNED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_ASSIGNED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_LOADED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_LOADED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_UPLIFTED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_UPLIFTED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_DELIVERED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_DELIVERED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_HANDOVER_ONLINE;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_HANDOVER_OFFLINE;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_HANDOVER_RECEIVED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_PENDING.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_PENDING;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_RETURNED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_RETURNED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_READYFOR_DELIVERY;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_ARRIVED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_ARRIVED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(resditEventVO.getResditEventCode())){
			actionCode = MailbagAuditVO.RESDIT_TRANSPORT_COMPLETED;
			mailbagAuditVO.setActionCode(actionCode);
		}else if(MailConstantsVO.RESDIT_LOST.equals(resditEventVO.getResditEventCode())){
			actionCode = MailConstantsVO.RESDIT_LOST;
			mailbagAuditVO.setActionCode(actionCode);
		}
		mailbagAuditVO.setMailSequenceNumber(receptacleInformationVO.getMailSequenceNumber());
		mailbagAuditVO.setMailbagId(receptacleInformationVO.getReceptacleID());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuilder additionalInfo = new StringBuilder();
		additionalInfo.append(Objects.nonNull(actionCode) ? actionCode.toLowerCase() : "");
		if(resditEventVO.getEventDate()!=null){
			additionalInfo.append(" on ").append(resditEventVO.getEventDate().toDisplayFormat());
		}
		if(resditEventVO.getEventDate()!=null){
			additionalInfo.append(" ; message sent on ").append(resditEventVO.getEventDate().toDisplayTimeOnlyFormat());
		}
		if(flightDetails!=null){
		additionalInfo.append(" ").append(flightDetails);
		}
		additionalInfo.append(" at airport ").append(resditEventVO.getEventPort());
		if(containerNumber!=null){
		additionalInfo.append(" ; in container ").append(containerNumber);
		}
		if(pou!=null){
		additionalInfo.append(" ; POU -").append(pou);
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
		append(" ; Source - ").append(MailConstantsVO.FRM_JOB);			
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);
	}
	
	public void flagAuditforFlightDeparture(MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs)
			throws SystemException {
		log.entering("MailBagAuditBuilder", "flagAuditforFlightDeparture");
		String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		for (FlightValidationVO flightValidationVO : flightVOs) {
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
			Mailbag mailbag = null;
			try { 
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
				long mailsequenceNumber;
				if(mailbagVO.getMailSequenceNumber()>0) {
					mailsequenceNumber = mailbagVO.getMailSequenceNumber();
				} else { 
				mailsequenceNumber=mailController.findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				}
					mailbagPK.setMailSequenceNumber(mailsequenceNumber);
					mailbag = Mailbag.find(mailbagPK); 
				} catch (FinderException e) {
					mailbag = null;
			}
			if(mailbag!=null){
				String actionCode = MailbagAuditVO.MAILBAG_FLIGHT_DEPARTURE;
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(flightValidationVO.getAtd()!=null){
					additionalInfo.append(" on ").append(flightValidationVO.getAtd().toDisplayFormat());
				}
				if(flightValidationVO.getAtd()!=null){
					additionalInfo.append(" ; departed on ").append(flightValidationVO.getAtd().toDisplayFormat());
				}
				additionalInfo.append("; ").append(flightValidationVO.getCarrierCode()).append(flightValidationVO.getFlightNumber()).append(" at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(mailbagVO.getContainerNumber()!=null){
				additionalInfo.append(" ; in container ").append(mailbagVO.getContainerNumber());
				}
				if(mailbagVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
				if(mailbagVO.getMailSource()!=null){
					additionalInfo.append(" ; Source - ").append(mailbagVO.getMailSource());	
				}else{
					additionalInfo.append(" ; Source - ").append(triggeringPoint);
				}
				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
				
           Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
			boolean newHistory=true;
			
			if (existingMailbagHistories != null
					&& !existingMailbagHistories.isEmpty()) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					
					if (mailbagHistory.getScannedPort().equals(flightValidationVO.getLegOrigin()) &&mailbagHistory.getMailStatus().equals(
							MailConstantsVO.FLIGHT_DEPARTURE) &&mailbagHistory.getMailSequenceNumber()==mailbag.getMailbagPK().getMailSequenceNumber()&& 
							mailbagHistory.getFlightNumber().equals(flightValidationVO.getFlightNumber()) && 
							mailbagHistory.getFlightSequenceNumber()==flightValidationVO.getFlightSequenceNumber() ) {
						
						newHistory =false;
							
						}
					}}
			if(newHistory){
				AuditUtils .performAudit(mailbagAuditVO);	
			}
			
			}
		
	}
		log.exiting("MailBagAuditBuilder", "flagAuditforFlightDeparture");
	}
	
	public void flagAuditForMailTransferAtExport(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO) throws SystemException {
		log.entering("MailBagAuditBuilder", "flagAuditForMailTransferAtExport");
		
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			
			
			String actionCode= MailbagAuditVO.MAILBAG_TRANSFER;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
			}
			if(!("-1".equals(containerVO.getFlightNumber()))){
				additionalInfo.append("; to ").append(containerVO.getCarrierCode()).append(containerVO.getFlightNumber());
				if(containerVO.getFlightDate()!=null){
					additionalInfo.append(" ").append(containerVO.getFlightDate().toDisplayFormat());
				}
			}else{
				additionalInfo.append("; to  ").append(containerVO.getCarrierCode());
			}
				additionalInfo.append(" ; at airport");
			if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(containerVO.getContainerNumber()!=null){
			additionalInfo.append(" ; to container ").append(containerVO.getContainerNumber());
			}
			if(containerVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(containerVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
			append(" ; Source -").append(mailbagVO.getMailSource());
			
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		AuditUtils .performAudit(mailbagAuditVO);

		}

		log.exiting("MailBagAuditBuilder", "flagAuditForMailTransferAtExport");
	}

	public void flagAuditforFlightArrival(MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs)
			throws SystemException {
		log.entering("MailBagAuditBuilder", "flagAuditforFlightArrival");
		String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
			for (FlightValidationVO flightValidationVO : flightVOs) {
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			Mailbag mailbag = null;
			try {  
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(   mailbagVO.getCompanyCode());
				long mailsequenceNumber;
				if(mailbagVO.getMailSequenceNumber()>0) {
					mailsequenceNumber = mailbagVO.getMailSequenceNumber();
				} else { 
				mailsequenceNumber=mailController.findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				}
				mailbagPK.setMailSequenceNumber(mailsequenceNumber);
				mailbag = Mailbag.find(mailbagPK); 
			} catch (FinderException e) {
				mailbag = null;
			}
			if(mailbag!=null){
				String actionCode = MailbagAuditVO.MAILBAG_FLIGHT_ARRIVAL;
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(flightValidationVO.getAtd()!=null){
					additionalInfo.append(" on ").append(flightValidationVO.getAtd().toDisplayFormat());
				}
				if(flightValidationVO.getAtd()!=null){
					additionalInfo.append(" ; arrived on ").append(flightValidationVO.getAtd().toDisplayFormat());
				}
				additionalInfo.append("; ").append(flightValidationVO.getCarrierCode()).append(flightValidationVO.getFlightNumber()).append(" at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(mailbagVO.getContainerNumber()!=null){
				additionalInfo.append(" ; in container ").append(mailbagVO.getContainerNumber());
				}
				if(mailbagVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
				if(mailbagVO.getMailSource()!=null){
					additionalInfo.append(" ; Source - ").append(mailbagVO.getMailSource());		
				}else{
					additionalInfo.append(" ; Source - ").append(triggeringPoint);		
				}		
				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
				
           Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
			boolean newHistory=true;
			
			if (existingMailbagHistories != null
					&& !existingMailbagHistories.isEmpty()) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					
					if (mailbagHistory.getScannedPort().equals(flightValidationVO.getLegDestination()) &&mailbagHistory.getMailStatus().equals(
							MailConstantsVO.FLIGHT_ARRIVAL) &&mailbagHistory.getMailSequenceNumber()==mailbag.getMailbagPK().getMailSequenceNumber()&& 
							mailbagHistory.getFlightNumber().equals(flightValidationVO.getFlightNumber()) && 
							mailbagHistory.getFlightSequenceNumber()==flightValidationVO.getFlightSequenceNumber() ) {
						
						newHistory =false;
							
						}
					}}
			if(newHistory){
				AuditUtils .performAudit(mailbagAuditVO);	
			}
			
			}
			}
		log.exiting("MailBagAuditBuilder", "flagAuditforFlightArrival");
	}
	
	public void flagAuditForContainerTransfer(
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		
		log.entering("MailBagAuditBuilder", "flagAuditForContainerTransfer");
		
		for (MailbagVO mailbagVO : mailbagVOs) {
			
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}	

			boolean[] arrDlv = checkIfHistoryExists(mailbag, mailbagVO);
			boolean isAlreadyArrived = arrDlv[0];
			boolean isDelivered = arrDlv[1];
			
			if(!isAlreadyArrived && !isDelivered){
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
							
				String actionCode= MailbagAuditVO.MAILBAG_ARRIVED;
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
				mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(mailbagVO.getScannedDate()!=null){
					additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
				}
				if(!("-1".equals(mailbagVO.getFlightNumber()))){
					additionalInfo.append("; to ").append(mailbagVO.getCarrierCode()).append(mailbagVO.getFlightNumber());
					if(mailbagVO.getFlightDate()!=null){
						additionalInfo.append(" ").append(mailbagVO.getFlightDate().toDisplayFormat());
						}
					}else{
						additionalInfo.append("; to ").append(mailbagVO.getCarrierCode());
					}
				additionalInfo.append(" ; at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(mailbagVO.getUldNumber()!=null){
				additionalInfo.append(" ; in container ").append(mailbagVO.getUldNumber());
				}
				if(mailbagVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
				if(mailbagVO.getMailSource()!=null){
					additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());	
				}
				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
				AuditUtils .performAudit(mailbagAuditVO);
			}
			if(!isDelivered){
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			String actionCode= MailbagAuditVO.CONTAINER_TRANSFER;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
			}
			if(!("-1".equals(operationalFlightVO.getFlightNumber()))){
			additionalInfo.append("; to ").append(operationalFlightVO.getCarrierCode()).append(operationalFlightVO.getFlightNumber());
			if(mailbagVO.getFlightDate()!=null){
				additionalInfo.append(" ").append(operationalFlightVO.getFlightDate().toDisplayFormat());
				}
			}else{
				additionalInfo.append("; to ").append(operationalFlightVO.getCarrierCode());
			}
			additionalInfo.append(" ; at airport ");
			if(mailbagVO.getScannedPort()!=null){
				additionalInfo.append(mailbagVO.getScannedPort());
			}else{
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(operationalFlightVO.getContainerNumber()!=null){
			additionalInfo.append(" ; in container ").append(operationalFlightVO.getContainerNumber());
			}
			if(mailbagVO.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
			append(" ; Source -").append(mailbagVO.getMailSource());		
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
			}
		}
		log.exiting("MailBagauditBuilder", "flagAuditForContainerTransfer");

	}
	
	public void flagAuditForContainerReassignment(
			OperationalFlightVO toFlightVO, ContainerVO containerVO,
			Collection<MailbagVO> mailbagVOS) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForContainerReassignment");

		for (MailbagVO mailbagVO : mailbagVOS) {
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			
			if (containerVO.isOffload()) {
					String actionCode= MailbagAuditVO.MAILBAG_OFFLOAD;
					mailbagAuditVO.setActionCode(actionCode);
					mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
					mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
					mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
					mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
					mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
					mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					mailbagAuditVO.setYear(mailbagVO.getYear());
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
					StringBuffer additionalInfo = new StringBuffer();
					additionalInfo.append(actionCode.toLowerCase());
					if(mailbagVO.getScannedDate()!=null){
						additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
					}
					additionalInfo.append(" to ").append(mailbagVO.getCarrierCode());
					if(mailbagVO.getUldNumber()!=null){
						additionalInfo.append(" ; to container ").append(mailbagVO.getUldNumber());
					}
					additionalInfo.append(" ; at airport ");
					if(mailbagVO.getScannedPort()!=null){
						additionalInfo.append(mailbagVO.getScannedPort());
					}else{
						additionalInfo.append(logonAttributes.getAirportCode());
					}
					if(containerVO.getPou()!=null){
					additionalInfo.append(" ; POU - ").append(containerVO.getPou());
					}
					additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
					if(mailbagVO.isRemove() && mailbagVO.getOffloadedRemarks()!=null) {
						additionalInfo.append(" ; Remarks - ").append(mailbagVO.getOffloadedRemarks());
					}
					if(mailbagVO.getMailSource()!=null){
						additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());
					}
					mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
					AuditUtils .performAudit(mailbagAuditVO);
			}else{
			String actionCode= MailbagAuditVO.MAILBAG_REASSIGN;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(mailbagVO.getScannedDate()!=null){
					additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
				}
				if(!("-1".equals(toFlightVO.getFlightNumber()))){
					additionalInfo.append("; to ").append(toFlightVO.getCarrierCode()).append(toFlightVO.getFlightNumber());
					if(toFlightVO.getFlightDate()!=null){
						additionalInfo.append(" ").append(toFlightVO.getFlightDate().toDisplayFormat());
					}
				}else{
					additionalInfo.append(" to ").append(toFlightVO.getCarrierCode());
				}
				if(mailbagVO.getUldNumber()!=null){
					additionalInfo.append(" ; to container ").append(mailbagVO.getUldNumber());
				}
				additionalInfo.append(" ; at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(containerVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(containerVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
				if(containerVO.getMailSource()!=null){
					additionalInfo.append(" ; Source -").append(containerVO.getMailSource());
				}
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
			}
		}

		log.exiting("HistoryBuilder", "flagHistoryForContainerReassignment");
	}
	
	public void flagAuditForContainerTransferAtExport(
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		log.entering("MailBagAuditBuilder", "flagAuditForContainerTransferAtExport");
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			
			
			boolean[] arrDlv = checkIfHistoryExists(mailbag, mailbagVO);
			boolean isAlreadyArrived = arrDlv[0];
			boolean isDelivered = arrDlv[1];
			
			if(!isAlreadyArrived && !isDelivered){
				
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				
				String actionCode= MailbagAuditVO.MAILBAG_ARRIVED;
				mailbagAuditVO.setActionCode(actionCode);
				mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
				mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append(actionCode.toLowerCase());
				if(mailbagVO.getScannedDate()!=null){
					additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
				}
				if(!("-1".equals(mailbagVO.getFlightNumber()))){
					additionalInfo.append("; to ").append(mailbagVO.getCarrierCode()).append(mailbagVO.getFlightNumber());
					if(mailbagVO.getFlightDate()!=null){
						additionalInfo.append(" ").append(mailbagVO.getFlightDate().toDisplayFormat());
						}
					}else{
						additionalInfo.append("; to ").append(mailbagVO.getCarrierCode());
					}
				additionalInfo.append(" ; at airport ");
				if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
				}
				if(mailbagVO.getUldNumber()!=null){
				additionalInfo.append(" ; in container ").append(mailbagVO.getUldNumber());
				}
				if(mailbagVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
				}
				additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
				if(mailbagVO.getMailSource()!=null){
					additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());	
				}else{
					additionalInfo.append(" ; Source -").append(triggeringPoint);	
				}
				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
				AuditUtils .performAudit(mailbagAuditVO);
			}
			
			if(!isDelivered){
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				
			String actionCode= MailbagAuditVO.MAILBAG_TRANSFER;
			mailbagAuditVO.setActionCode(actionCode);
			mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
			mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagVO.getYear());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append(actionCode.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
			}
			if(!("-1".equals(operationalFlightVO.getFlightNumber()))){
				additionalInfo.append("; to ").append(operationalFlightVO.getCarrierCode()).append(operationalFlightVO.getFlightNumber());
				if(operationalFlightVO.getFlightDate()!=null){
					additionalInfo.append(" ").append(operationalFlightVO.getFlightDate().toDisplayFormat());
				}
			}else{
				additionalInfo.append("; to  ").append(operationalFlightVO.getCarrierCode());
			}
				additionalInfo.append(" ; at airport");
			if(mailbagVO.getScannedPort()!=null){
					additionalInfo.append(mailbagVO.getScannedPort());
				}else{
					additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(operationalFlightVO.getContainerNumber()!=null){
			additionalInfo.append(" ; to container ").append(operationalFlightVO.getContainerNumber());
			}
			if(operationalFlightVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(operationalFlightVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
			if(mailbagVO.getMailSource()!=null){
				additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());
			}else{
				additionalInfo.append(" ; Source -").append(triggeringPoint);	
			}
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils .performAudit(mailbagAuditVO);
			}
		}
		log.exiting("MailBagAuditBuilder", "flagAuditForContainerTransferAtExport");

	}
					
	public MailbagPK createMailbagPK(String companyCode, MailbagVO mailbagVO)
			throws SystemException {
		MailbagPK mailbagPK = new MailbagPK();
		long mailsequenceNumber;

		if(mailbagVO.getMailSequenceNumber()>0) { 
			mailsequenceNumber = mailbagVO.getMailSequenceNumber();
		} else { 
		mailsequenceNumber=new MailController().findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
		}

		mailbagPK.setCompanyCode(companyCode);
		mailbagPK.setMailSequenceNumber(mailsequenceNumber);
		return mailbagPK;
	}

	private MailbagAuditVO constructAuditVO(MailbagVO mailbagVO, String actionCode) throws SystemException {
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		mailbagAuditVO.setActionCode(actionCode);
		mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
		mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
		mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
		mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
		mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
		mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
		mailbagAuditVO.setYear(mailbagVO.getYear());
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(mailbagVO.getScannedDate()!=null){
			additionalInfo.append(" on ").append(mailbagVO.getScannedDate().toDisplayFormat());
		}
		if(!("-1".equals(mailbagVO.getFlightNumber()))){
		additionalInfo.append("; to ").append(mailbagVO.getCarrierCode()).append(mailbagVO.getFlightNumber()).append(" ");
		if(mailbagVO.getFlightDate()!=null){
			additionalInfo.append(mailbagVO.getFlightDate().toDisplayFormat());
			}
		}else{
			additionalInfo.append("; to ").append(mailbagVO.getCarrierCode());
		}
		additionalInfo.append(" ; at airport ");
		if(mailbagVO.getScannedPort()!=null){
			additionalInfo.append(mailbagVO.getScannedPort());
		}else{
			additionalInfo.append(logonAttributes.getAirportCode());
		}
		if(mailbagVO.getUldNumber()!=null || mailbagVO.getContainerNumber()!=null){
			if(mailbagVO.getUldNumber()!=null){
		additionalInfo.append(" ; in container ").append(mailbagVO.getUldNumber());
			}else{
				additionalInfo.append(" ; in container ").append(mailbagVO.getContainerNumber());		
			}
		}
		if(mailbagVO.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId());
		if(mailbagVO.getMailSource()!=null){
			additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());
		}
		mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
		
		return mailbagAuditVO;
	}
	
	private boolean[] checkIfHistoryExists(Mailbag mailbag, MailbagVO mailbagVO) throws SystemException {
		boolean[] arrDlv = new boolean[3];
			Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
			
			if(mailbag!=null){
			if (existingMailbagHistories != null && existingMailbagHistories.size() > 0) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					if((MailConstantsVO.MAIL_STATUS_ARRIVED).equals(mailbagHistory.getMailStatus())){
						arrDlv[0] = true;
					}
					
					if((MailConstantsVO.MAIL_STATUS_DELIVERED).equals(mailbagHistory.getMailStatus())){
						arrDlv[1] = true;
					}
				}
			}
		}
			return arrDlv;
	}
	
	public void flagContainerAuditForDeletion(ContainerDetailsVO containerDetailsVO) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagContainerAuditForDeletion");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		StringBuilder additInfo = new StringBuilder();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		String actionCode= MailConstantsVO.CONTAINER_DELETED;
		containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerAuditVO.setAssignedPort(containerDetailsVO.getAssignedPort());
		containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerDetailsVO
				.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerAuditVO.setActionCode(actionCode);
		additInfo.append("Deleted from ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
			additInfo.append(containerDetailsVO.getCarrierCode()).append(" ").append(containerDetailsVO.getFlightNumber()).append(", ");
		}else{
			additInfo.append(containerDetailsVO.getCarrierCode()).append(", ");
		}
		additInfo.append(new LocalDate(containerDetailsVO.getAssignedPort(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
		additInfo.append(containerDetailsVO.getAssignedPort()).append(" - ").append(containerDetailsVO.getPou()).append(" ");
		}
		additInfo.append("in ").append(containerDetailsVO.getAssignedPort());
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		containerAuditVO.setUserId(logonAttributes.getUserId());
		containerAuditVO.setAuditRemarks("Container Deleted");
		AuditUtils.performAudit(containerAuditVO);
	}
	
	public void flagContainerAuditForUnassignment(ContainerDetailsVO containerDetailsVO) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagContainerAuditForDeletion");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		StringBuilder additInfo = new StringBuilder();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		String actionCode= MailConstantsVO.CONTAINER_UNASSIGNED;
		containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerAuditVO.setAssignedPort(containerDetailsVO.getAssignedPort());
		containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerAuditVO.setActionCode(actionCode);
		additInfo.append("Unassigned from ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
			additInfo.append(containerDetailsVO.getCarrierCode()).append(" ").append(containerDetailsVO.getFlightNumber()).append(", ");
		}else{
			additInfo.append(containerDetailsVO.getCarrierCode()).append(", ");
		}
		additInfo.append(new LocalDate(containerDetailsVO.getAssignedPort(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
		additInfo.append(containerDetailsVO.getAssignedPort()).append(" - ").append(containerDetailsVO.getPou()).append(" ");
		}
		additInfo.append("in ").append(containerDetailsVO.getAssignedPort());
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		containerAuditVO.setUserId(logonAttributes.getUserId());
		containerAuditVO.setAuditRemarks("Container Unassigned");
		AuditUtils.performAudit(containerAuditVO);
	}

	public void flagContainerAuditForArrival(MailArrivalVO mailArrivalVO) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagContainerAuditForArrival");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		Collection<ContainerDetailsVO> containerVO =  mailArrivalVO.getContainerDetails();
		StringBuilder additInfo = new StringBuilder();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		for(ContainerDetailsVO containerDetailsVO : containerVO){
		String actionCode;
		containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerAuditVO.setAssignedPort(mailArrivalVO.getAirportCode());
		containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		if(mailArrivalVO.isDeliveryNeeded()){
			actionCode= MailConstantsVO.CONTAINER_DELIVERED;
			containerAuditVO.setActionCode(actionCode);
			additInfo.append("Delivered to ");
		}else{
			actionCode= MailConstantsVO.CONTAINER_ARRIVED;
			containerAuditVO.setActionCode(actionCode);
			additInfo.append("Arrived from ");
		}
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
			additInfo.append(containerDetailsVO.getCarrierCode()).append(" ").append(containerDetailsVO.getFlightNumber()).append(", ");
		}else{
			additInfo.append(containerDetailsVO.getCarrierCode()).append(", ");
		}
		additInfo.append(new LocalDate(mailArrivalVO.getAirportCode(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
		additInfo.append(containerDetailsVO.getPol()).append(" - ").append((mailArrivalVO.getAirportCode())).append(" ");
		}
		additInfo.append("in ").append(mailArrivalVO.getAirportCode());
		}
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		containerAuditVO.setUserId(logonAttributes.getUserId());
		containerAuditVO.setAuditRemarks("Container Arrived");
		AuditUtils.performAudit(containerAuditVO);
	}
	
	public void flagContainerAuditForAcquital(ContainerDetailsVO containerDetailsVO) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagContainerAuditForAcquital");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		StringBuilder additInfo = new StringBuilder();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		String actionCode= MailConstantsVO.CONTAINER_ACQUITED;
		containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerAuditVO.setAssignedPort(containerDetailsVO.getPol());
		containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerAuditVO.setActionCode(actionCode);
		additInfo.append("Acquited from ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
			additInfo.append(containerDetailsVO.getCarrierCode()).append(" ").append(containerDetailsVO.getFlightNumber()).append(", ");
		}else{
			additInfo.append(containerDetailsVO.getCarrierCode()).append(", ");
		}
		additInfo.append(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
		additInfo.append(containerDetailsVO.getPol()).append(" - ").append(containerDetailsVO.getPou()).append(" ");
		}
		additInfo.append("in ").append(logonAttributes.getAirportCode());
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		containerAuditVO.setUserId(logonAttributes.getUserId());
		containerAuditVO.setAuditRemarks("Container Acquited");
		AuditUtils.performAudit(containerAuditVO);
	}
	
	public void flagContainerAuditForRetaining(ContainerVO containerDetailsVO) throws SystemException{
		log.entering("MailBagAuditBuilder", "flagContainerAuditForRetaining");
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		StringBuilder additInfo = new StringBuilder();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		String actionCode= MailConstantsVO.CONTAINER_RETAINED;
		containerAuditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerAuditVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerAuditVO.setAssignedPort(containerDetailsVO.getPol());
		containerAuditVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerAuditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerAuditVO.setActionCode(actionCode);
		additInfo.append("Retained to ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
			additInfo.append(containerDetailsVO.getCarrierCode()).append(" ").append(containerDetailsVO.getFlightNumber()).append(", ");
		}else{
			additInfo.append(containerDetailsVO.getCarrierCode()).append(", ");
		}
		additInfo.append(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(containerDetailsVO.getFlightNumber())){
		additInfo.append(containerDetailsVO.getPol()).append(" - ").append(containerDetailsVO.getPou()).append(" ");
		}
		additInfo.append("in ").append(logonAttributes.getAirportCode());
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		containerAuditVO.setUserId(logonAttributes.getUserId());
		containerAuditVO.setAuditRemarks("Container Retained");
		AuditUtils.performAudit(containerAuditVO);
	}
	
}	



