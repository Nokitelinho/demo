/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.builder;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.operations.MLDController;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.InboundFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class MessageBuilder extends AbstractActionBuilder {

	private Log log = LogFactory.getLogger("MessageBuilder");
	private static final String MRA_JOB_REQUIRED ="mailtracking.defaults.ismrajobrequired";
	private static final String IMPORTMRA_REQUIRED="mailtracking.defaults.importmailstomra";//added by A-7371 for ICRD-221516
	private static final String APPROVED = "A";
	private static final String CLASS = "MessageBuilder";

	/**
	 *
	 * @param mailAcceptanceVO
	 * @param hasFlightDeparted
	 * @param acceptedMailbags
	 * @param acceptedUlds
	 * @throws SystemException
	 */
	public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			boolean hasFlightDeparted, Collection<MailbagVO> acceptedMailbags,
			Collection<ContainerDetailsVO> acceptedUlds)throws SystemException{
		log.entering("MessageBuilder", "flagResditsForAcceptance");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
			new ResditController().flagResditsForAcceptance(mailAcceptanceVO,
					hasFlightDeparted, acceptedMailbags, acceptedUlds);
		}
		log.exiting("MessageBuilder", "flagResditsForAcceptance");

	}

	/**
	 *
	 * @param mailAcceptanceVO
	 * @param mailbags
	 * @throws SystemException
	 */
	public void flagMLDForMailAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO> mailbags)throws SystemException{
		log.entering("MessageBuilder", "flagMLDForMailAcceptance");
		new MLDController().flagMLDForMailAcceptance(mailAcceptanceVO, mailbags);
		log.exiting("MessageBuilder", "flagMLDForMailAcceptance");

	}

	/**
	 *
	 * @param companyCode
	 * @param carrierId
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @param eventPort
	 * @throws SystemException
	 */
	public void flagResditsForFlightDeparture(String companyCode, int carrierId,
			Collection<MailbagVO> mailbagVOs,Collection <ContainerDetailsVO> containerDetailsVOs,
			String eventPort)throws SystemException{
		log.entering("MessageBuilder", "flagResditsForFlightDeparture");
		new ResditController().flagResditsForFlightDeparture(
				companyCode, carrierId,	mailbagVOs, containerDetailsVOs, eventPort);
		log.exiting("MessageBuilder", "flagResditsForFlightDeparture");

	}

	/**
	 *
	 * @param mailbagVOs
	 * @param mode
	 * @throws SystemException
	 */
	public void flagMLDForMailOperations(Collection<MailbagVO> mailbagVOs,
			String mode)throws SystemException{
		log.entering("MessageBuilder", "flagMLDForMailOperations");
		new MLDController().flagMLDForMailOperations(mailbagVOs, mode);
		log.exiting("MessageBuilder", "flagMLDForMailOperations");

	}


	/**
	 *
	 * @param companyCode
	 * @param carrierId
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @param eventPort
	 * @param flightArrivedPort
	 * @throws SystemException
	 */
	public void flagResditsForTransportCompleted(String companyCode, int carrierId,
			Collection<MailbagVO> mailbagVOs,Collection <ContainerDetailsVO> containerDetailsVOs,
			String eventPort,String flightArrivedPort)throws SystemException{
		log.entering("MessageBuilder", "flagResditsForTransportCompleted");
		new ResditController().flagResditsForTransportCompleted(
				companyCode, carrierId, mailbagVOs, containerDetailsVOs, eventPort, flightArrivedPort);
		log.exiting("MessageBuilder", "flagResditsForTransportCompleted");

	}

	/**
	 *
	 * @param acceptedUlds
	 * @param pol
	 * @throws SystemException
	 */
	public void flagPendingResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)throws SystemException{
		log.entering("MessageBuilder", "flagPendingResditForUlds");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagPendingResditForUlds(
					acceptedUlds, pol);
		}
		log.exiting("MessageBuilder", "flagPendingResditForUlds");

	}


	/**
	 *
	 * @param mailbagsToFlag
	 * @param eventAirport
	 * @param eventCode
	 * @throws SystemException
	 */
	public void flagResditForMailbags(Collection<MailbagVO> mailbagsToFlag,
			String eventAirport, String eventCode)throws SystemException{
		log.entering("MessageBuilder", "flagResditForMailbags");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditForMailbags(eventCode, eventAirport,
					mailbagsToFlag);
		}
		log.exiting("MessageBuilder", "flagResditForMailbags");

	}

	/**
	 *
	 * @param containerInInventoryListVOs
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagResditsForInventoryDelivery(
			Collection<ContainerInInventoryListVO> containerInInventoryListVOs,
			Collection<MailbagVO> mailbagVOs)throws SystemException{
		log.entering("MessageBuilder", "flagResditsForInventoryDelivery");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditsForInventoryDelivery(
					containerInInventoryListVOs, mailbagVOs);
		}
		log.exiting("MessageBuilder", "flagResditsForInventoryDelivery");

	}


	/**
	 *
	 * @param unassignSBULDs
	 * @throws SystemException
	 */
	public void flagReturnedResditForULDs(
			Collection <ContainerDetailsVO> unassignSBULDs)throws SystemException{
		log.entering("MessageBuilder", "flagReturnedResditForULDs");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagReturnedResditForULDs(unassignSBULDs);
		}
		log.exiting("MessageBuilder", "flagReturnedResditForULDs");

	}

	/**
	 *
	 * @param containerDetails
	 * @param assignedPort
	 * @throws SystemException
	 */
	public void flagResditsForULDAcceptance(
			Collection<ContainerDetailsVO> containerDetails,
			String assignedPort)throws SystemException{
		log.entering("MessageBuilder", "flagResditsForULDAcceptance");
		new ResditController().flagResditsForULDAcceptance(containerDetails, assignedPort);
		log.exiting("MessageBuilder", "flagResditsForULDAcceptance");

	}

	/**
	 *
	 * @param mailArrivalVO
	 * @param arrivedMailbags
	 * @param arrivedContainers
	 * @throws SystemException
	 */
	public void flagResditsForArrival(MailArrivalVO mailArrivalVO,
			Collection<MailbagVO> arrivedMailbags,
			Collection<ContainerDetailsVO> arrivedContainers) throws SystemException{
		log.entering("MessageBuilder", "flagResditsForArrival");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditsForArrival(mailArrivalVO,arrivedMailbags, arrivedContainers);
		}
		log.exiting("MessageBuilder", "flagResditsForArrival");

	}

	/**
	 *
	 * @param transferredMails
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public void flagResditsForContainerTransfer(
			Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException{
		log.entering("MessageBuilder", "flagResditsForContainerTransfer");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditsForContainerTransfer(
					transferredMails, containerVOs, operationalFlightVO);
		}
		log.exiting("MessageBuilder", "flagResditsForContainerTransfer");

	}

	/**
	 *
	 * @param transferredMails
	 * @param containerVO
	 * @throws SystemException
	 */
	public void flagResditsForMailbagTransfer(
			Collection<MailbagVO> transferredMails, ContainerVO containerVO) throws SystemException{
		log.entering("MessageBuilder", "flagResditsForMailbagTransfer");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditsForMailbagTransfer(transferredMails,
					containerVO);
		}
		log.exiting("MessageBuilder", "flagResditsForMailbagTransfer");

	}

	/**
	 *
	 * @param eventCode
	 * @param eventPort
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagResditForMailbagsFromReassign(String eventCode, String eventPort,
			Collection<MailbagVO> mailbagVOs) throws SystemException{
		log.entering("MessageBuilder", "flagResditForMailbagsFromReassign");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().flagResditForMailbags(
					MailConstantsVO.RESDIT_PENDING, eventPort, mailbagVOs);
		}
		log.exiting("MessageBuilder", "flagResditForMailbagsFromReassign");

	}

	/**
	 *
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags,
			ContainerVO toContainerVO) throws SystemException{
		log.entering("MessageBuilder", "flagResditsForMailbagReassign");
			new ResditController().flagResditsForMailbagReassign(mailbags,
					toContainerVO);
		log.exiting("MessageBuilder", "flagResditsForMailbagReassign");

	}

	/**
	 *
	 * @param mailbagVOs
	 * @param ulds
	 * @param toFlightVO
	 * @param hasFlightDeparted
	 * @throws SystemException
	 */
	public void flagResditForContainerReassign(Collection<MailbagVO> mailbagVOs,
			Collection<ContainerDetailsVO> ulds, OperationalFlightVO toFlightVO,
			boolean hasFlightDeparted) throws SystemException{
		log.entering("MessageBuilder", "flagResditForContainerReassign");
			new ResditController().flagResditForContainerReassign(mailbagVOs,
					ulds, toFlightVO, hasFlightDeparted);
		log.exiting("MessageBuilder", "flagResditForContainerReassign");

	}

	/**
	 *
	 * @param mailbagVOs
	 * @param toContainerVO
	 * @param mode
	 * @throws SystemException
	 */
	public void flagMLDForMailReassignOperations(
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String mode) throws SystemException{
		log.entering(CLASS, "flagMLDForMailReassignOperations");
			new MLDController().flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,mode);
			//Added for CRQ ICRD-135130 by A-8061 starts
			if("B".equals(toContainerVO.getType())){
			new MLDController().flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,MailConstantsVO.MLD_STG);
			}
			new MLDController().flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,MailConstantsVO.MLD_NST);
			//Added for CRQ ICRD-135130 by A-8061 end
		log.exiting(CLASS, "flagMLDForMailReassignOperations");

	}

	/**
	 *
	 * @param operationalFlightVOs
	 * @throws SystemException
	 */
	public void flagMLDForUpliftedMailbags(
			Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException{
		log.entering("MessageBuilder", "flagMLDForUpliftedMailbags");
		new MLDController().flagMLDForUpliftedMailbags(operationalFlightVOs);
		log.exiting("MessageBuilder", "flagMLDForUpliftedMailbags");

	}

	/**
	 *
	 * @param mailbagVOs
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public void flagMLDForContainerTransfer(Collection<MailbagVO> mailbagVOs,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException{
		log.entering("MessageBuilder", "flagMLDForUpliftedMailbags");
		new MLDController().flagMLDForContainerTransfer(mailbagVOs, containerVOs, operationalFlightVO);
		log.exiting("MessageBuilder", "flagMLDForUpliftedMailbags");

	}

	/**
	 *
	 * @param mailbagVOs
	 * @param containerVO
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public void flagMLDForMailbagTransfer(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO, OperationalFlightVO operationalFlightVO) throws SystemException{
		log.entering("MessageBuilder", "flagMLDForUpliftedMailbags");
		

		
		if(containerVO.getContainerNumber()!=null){
		new MLDController().flagMLDForMailbagTransfer(mailbagVOs, containerVO,null,MailConstantsVO.MLD_HND);
		}
		//Added for CRQ ICRD-135130 by A-8061 starts
		new MLDController().flagMLDForMailbagTransfer(mailbagVOs, containerVO,null,MailConstantsVO.MLD_TFD);
		if("B".equals(containerVO.getType()))
		{
			new MLDController().flagMLDForMailReassignOperations(mailbagVOs,containerVO,MailConstantsVO.MLD_STG);
		}
		//Added for CRQ ICRD-135130 by A-8061 end
		new MLDController().flagMLDForMailReassignOperations(mailbagVOs,containerVO,MailConstantsVO.MLD_NST);
		log.exiting("MessageBuilder", "flagMLDForUpliftedMailbags");

	}

	/**
	 *
	 * @param mailbagVO
	 * @throws SystemException
	 */
	public void updateResditEventTimes(MailbagVO mailbagVO) throws SystemException{
		log.entering("MessageBuilder", "updateResditEventTimes");
		new ResditController().updateResditEventTimes(mailbagVO,
				MailConstantsVO.TXN_ARR);
		log.exiting("MessageBuilder", "updateResditEventTimes");
	}

	
	/**Added by A-7371 for ICRD-221516
	 * 	@importMailsToMRA
	 *  This Method will be called for Importing Mails to MRA Module
	 *  based on a system parameter("mailtracking.defaults.importmailstomra").
	 *
	 * @throws SystemException
	 */
	public void importMailsToMRA(OperationalFlightVO operationalFlightVO)
	throws SystemException{
		log.entering("MessageBuilder", "importMailsToMRA");
		String jobRequired = "";
		boolean isMRAJobRequired = false;
		jobRequired = findSystemParameterValue(MRA_JOB_REQUIRED);
		if (InboundFlightVO.FLAG_YES.equals(jobRequired)){
			isMRAJobRequired = true;
		}else {
			isMRAJobRequired = false;
		}
		if(!isMRAJobRequired){
			if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(IMPORTMRA_REQUIRED))) {
		Collection<String> sysParameters = new ArrayList<String>();
		sysParameters.add(MailConstantsVO.IMPORTMAILS_TO_MRA_SYSPAR);
		HashMap<String, String> sysParameterMap = null;
		sysParameterMap = new SharedDefaultsProxy()
		.findSystemParameterByCodes(sysParameters);
		log.log(Log.FINE, " systemParameterMap ", sysParameterMap);
		if (sysParameterMap != null
				&& OperationalFlightVO.FLAG_YES.equals(sysParameterMap
						.get(MailConstantsVO.IMPORTMAILS_TO_MRA_SYSPAR))) {
			FlownMailFilterVO filterVO = new FlownMailFilterVO();
			filterVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
			filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			filterVO.setFlightDate(operationalFlightVO.getFlightDate());
			filterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
			filterVO.setFlightSequenceNumber((int)operationalFlightVO.getFlightSequenceNumber());
			filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			log.log(Log.FINE, " MailtrackingMRAProxy--- findFlightDetails---");
			Collection<FlownMailSegmentVO> flownMailSegmentVOs;
			try {
				flownMailSegmentVOs = new MailtrackingMRAProxy().findFlightDetails(filterVO);
			} catch (ProxyException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
			log.log(Log.FINE,
					" MailtrackingMRAProxy--- flownMailSegmentVOs--->",
					flownMailSegmentVOs);
			if(flownMailSegmentVOs!=null && flownMailSegmentVOs.size()>0){
				FlightValidationVO flightValidationVO = new FlightValidationVO();
				flightValidationVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				flightValidationVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
				flightValidationVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				flightValidationVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				flightValidationVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				flightValidationVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
				flightValidationVO.setFlightRoute(operationalFlightVO.getFlightRoute());
				flightValidationVO.setDirection(operationalFlightVO.getDirection());
				flightValidationVO.setFlightDate(operationalFlightVO.getFlightDate());
				log.log(Log.FINE, " MailtrackingMRAProxy--- importFlownMails---");
				try {
					new MailtrackingMRAProxy().importFlownMails(flightValidationVO, flownMailSegmentVOs);
				} catch (ProxyException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
			}
		}
	  }	
	}		
		log.exiting("MessageBuilder", "importMailsToMRA");
	}

	/**
	 * Added by A-7371 for ICRD-221516
	 * @param syspar
	 * @return
	 * @throws SystemException
	 */
	public String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/**
	 *
	 * @param mailbagVOs
	 * @param toContainerVO
	 * @param mode
	 * @throws SystemException
	 */
	public void flagMLDForContainerReassign(
			Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO) throws SystemException{
		log.entering("MessageBuilder", "flagMLDForContainerReassign");
			new MLDController().flagMLDForContainerReassign(containerVOs,toFlightVO);
		log.exiting("MessageBuilder", "flagMLDForContainerReassign");

	}
	/**
	 * @author A-8061
	 * @param mailbags
	 * @throws SystemException
	 */
	public void flagMLDForMailbagReturn(Collection<MailbagVO> mailbags) throws SystemException{
		log.entering("MessageBuilder", "flagMLDForMailbagReturn");
		new MLDController().flagMLDForMailbagReturn(mailbags);
		log.exiting("MessageBuilder", "flagMLDForMailbagReturn");

	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.createJobforFlightRevenueInterface
	 *	Added by 	:	a-8061 on 28-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void createJobforFlightRevenueInterface() throws SystemException{
		log.entering("MessageBuilder", "createJobforFlightRevenueInterface");
	/*	
		try {
			new MailOperationsMRAProxy().createJobforFlightRevenueInterface();
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}*/
		
		log.exiting("MessageBuilder", "createJobforFlightRevenueInterface");

	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.importMRAData
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param rateAuditVos
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void importMRAData(Collection<RateAuditVO> rateAuditVos) throws SystemException {
		log.entering("MessageBuilder", "importMRAData");
		createJobforFlightRevenueInterface();
		log.exiting("MessageBuilder", "importMRAData");
	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.reProrateDSN
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param dsnRoutingVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException{
		log.entering("MessageBuilder", "reProrateDSN");
		createJobforFlightRevenueInterface();
		log.exiting("MessageBuilder", "reProrateDSN");
	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.prorateExceptionFlights
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param flightValidationVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
			throws SystemException{
		log.entering("MessageBuilder", "prorateExceptionFlights");
		createJobforFlightRevenueInterface();
		log.exiting("MessageBuilder", "prorateExceptionFlights");
		
	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.saveHistoryDetails
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param ccadetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveHistoryDetails(
			CCAdetailsVO ccadetailsVO)
	throws SystemException{
		log.entering("MessageBuilder", "saveHistoryDetails");
		if(APPROVED.equals(ccadetailsVO.getCcaStatus())){
		createJobforFlightRevenueInterface();
		}
		log.exiting("MessageBuilder", "saveHistoryDetails");
	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.importArrivedMailstoMRA
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void importArrivedMailstoMRA(String companyCode)throws SystemException{
		log.entering("MessageBuilder", "importArrivedMailstoMRA");
		createJobforFlightRevenueInterface();
		log.exiting("MessageBuilder", "importArrivedMailstoMRA");
		
	}
	/**
	 * 
	 * 	Method		:	MessageBuilder.saveMCAdetails
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param detailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	public String saveMCAdetails(CCAdetailsVO detailsVO)throws SystemException{
		log.entering("MessageBuilder", "saveMCAdetails");
		if(APPROVED.equals(detailsVO.getCcaStatus())){
		createJobforFlightRevenueInterface();
		}
		log.exiting("MessageBuilder", "saveMCAdetails");
		return null;
	}
	
	/**
	 * 
	 * 	Method		:	MessageBuilder.updateTruckCost
	 *	Added by 	:	a-8061 on 25-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param truckOrderMailVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void updateTruckCost(TruckOrderMailVO truckOrderMailVO)throws SystemException {
		
		log.entering("MessageBuilder", "updateTruckCost");
	
		createJobforFlightRevenueInterface();
	
		log.exiting("MessageBuilder", "updateTruckCost");
		
	}
	
	public void reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)
			throws SystemException{
		log.entering("reRateMailbags", "updateTruckCost");
		
		createJobforFlightRevenueInterface();
	
		log.exiting("reRateMailbags", "updateTruckCost");
	}
	
	/**
	 * 
	 * 	Method		:	MessageBuilder.voidMailbags
	 *	Added by 	:	A-8061 on 15-Oct-2019
	 * 	Used for 	:	ICRD-336689
	 *	Parameters	:	@param VOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws SystemException{
		saveVoidedInterfaceDetails(VOs);
	}
	
	
	/**
	 * 
	 * 	Method		:	MessageBuilder.saveVoidedInterfaceDetails
	 *	Added by 	:	A-8061 on 15-Oct-2019
	 * 	Used for 	:	ICRD-336689
	 *	Parameters	:	@param mailbagVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveVoidedInterfaceDetails(Collection<DocumentBillingDetailsVO> VOs)throws SystemException{
		
		try {
			new MailOperationsMRAProxy().saveVoidedInterfaceDetails(VOs);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		
		
	}
	
	public void flagMLDForMailOperationsInULD(ContainerVO containerVo, String mode)throws SystemException{
		new MLDController().flagMLDForMailOperationsInULD(containerVo,mode);
	}

}

