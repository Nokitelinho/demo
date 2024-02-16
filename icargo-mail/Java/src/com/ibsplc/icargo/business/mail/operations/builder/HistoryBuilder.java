/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagHistory;
import com.ibsplc.icargo.business.mail.operations.MailbagHistoryPK;
import com.ibsplc.icargo.business.mail.operations.MailbagInULDForSegment;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class HistoryBuilder extends AbstractActionBuilder {

	private Log log = LogFactory.getLogger("MessageBuilder");
	 private static final String AUTOARRIVALOFFSET="mail.operations.autoarrivaloffset";
	 private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
	/**
	 *
	 * @param mailAcceptanceVO
	 * @param hasFlightDeparted
	 * @param acceptedMailbags
	 * @param acceptedUlds
	 * @throws SystemException
	 */
	public void insertOrUpdateHistoryDetailsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO> acceptedMailbags) throws SystemException {
		log.entering("HistoryBuilder",
				"insertOrUpdateHistoryDetailsForAcceptance");

		
		for (MailbagVO mailbagVO : acceptedMailbags) {
			if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO
					.getOperationalFlag()) || MailConstantsVO.FLAG_YES.equals(mailbagVO.getReassignFlag())) {
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(createMailbagPK(
							mailbagVO.getCompanyCode(), mailbagVO));
				} catch (FinderException e) {
					mailbag = null;
				}
				if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO
					.getOperationalFlag())){
				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
				if(mailbag==null) {
					if(mailbagVO.getMailSequenceNumber()!=0) {
					  mailbagHistoryVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
					}
					if(mailbagVO.getCompanyCode()!=null) {
						mailbagHistoryVO.setCompanyCode(mailbagVO.getCompanyCode());
					}
				}
				if(mailAcceptanceVO.getIsFromTruck()!=null){//Added by a-7871 for ICRD-240184
					mailbagHistoryVO.setAdditionalInfo("RFT");
					mailbagVO.setIsFromTruck("Y"); 
					mailbagVO.setStdOrStaTruckFlag("STD");
					mailbagHistoryVO.setScanDate(new ResditController().findResditEvtDate(mailbagVO,mailbagVO.getScannedPort()));
				} 
				//removed condition mailbagVO.isFromDeviationList() ||"CARDIT".equals(mailbagVO.getFromPanel())
				//if(true){//added by A-8353 for IASCB-52564 
					if(!mailAcceptanceVO.isAssignedToFlight()){
						mailbagHistoryVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);	
						mailbagHistoryVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
						mailbagHistoryVO.setFlightDate(null);
						mailbagHistoryVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
						mailbagHistoryVO.setPou(null);
						//if flow from deviation panel reassign flow, and mail already accepted to container/barrow, then no need to stamp ACP
						if(mailbagVO.isFromDeviationList()) {
						if( MailConstantsVO.FLAG_YES.equals(mailbagVO.getReassignFlag())){
							return;
						}
							boolean[] eventStats = checkIfHistoryExists(null, mailbag,
									mailbagVO, MailConstantsVO.MAIL_STATUS_ACCEPTED);
							boolean isAlreadyAccepted = eventStats[0];
							if(isAlreadyAccepted){
								return;
							}
						}
					}
					else {
						mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						 LocalDate scanDate = mailbagHistoryVO.getScanDate();
					     mailbagHistoryVO.setScanDate(scanDate);
					}
					mailbagHistoryVO.setFomDeviationList(mailbagVO.isFromDeviationList());
				//}
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
				}
				
				else if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getReassignFlag()) && 
						(!mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())) ||
						mailbagVO.getFlightSequenceNumber()!=mailbag.getFlightSequenceNumber() ||
						mailbagVO.getSegmentSerialNumber()!=mailbag.getSegmentSerialNumber()){      
					
					mailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
					mailbagVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
					
					MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVOForReassignFromAcceptance(mailbag,mailbagVO);   
					mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);               
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
							mailbag);
				}
				Collection<DamagedMailbagVO> damageMails = mailbagVO
						.getDamagedMailbags();

				if (damageMails != null && damageMails.size() > 0) {
					updateMailbagHistoryForDamage(mailbagVO);
				}

			} else if (MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO
					.getOperationalFlag())) {
				MailbagHistory mailbagHistory = 
						new Mailbag()
						.findMailbagHistoryForEvent(mailbagVO);

				if (mailbagHistory != null) {
					mailbagHistory.setScanDate(mailbagVO.getScannedDate());
				}
				Collection<DamagedMailbagVO> damageMails = mailbagVO
						.getDamagedMailbags();

				if (damageMails != null && damageMails.size() > 0) {
					updateMailbagHistoryForDamage(mailbagVO);
				}

			}

		}
		log.exiting("HistoryBuilder",
				"insertOrUpdateHistoryDetailsForAcceptance");

	}

	private void updateMailbagHistoryForDamage(MailbagVO mailbagVO)
			throws SystemException {
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(createMailbagPK(mailbagVO.getCompanyCode(),
					mailbagVO));
		} catch (FinderException e) {
			mailbag = null;
		}
		Collection<DamagedMailbagVO> damagedMailbagVOs = mailbagVO
				.getDamagedMailbags();

		for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
			boolean newDamage = false;

			if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO
					.getOperationFlag())) {

				newDamage = true;
			}
			if (newDamage) {
				MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
				mailHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
				if ((damagedMailbagVO.getPaCode() != null && damagedMailbagVO
						.getPaCode().trim().length() > 0)
						&& (mailbagVO.getPaCode() != null && mailbagVO
								.getPaCode().trim().length() > 0)) {
					mailHistoryVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				}
				if(damagedMailbagVO.getDamageDate()!=null){
					mailHistoryVO.setScanDate(damagedMailbagVO.getDamageDate());
				}
				if (mailHistoryVO.getCarrierCode() == null) {
					log.log(Log.FINE, "setting the Carrier Code",
							mailbagVO.getCarrierCode());
					mailHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
				}

				new Mailbag().insertMailbagHistoryDetails(mailHistoryVO,
						mailbag);

			}
		}

	}

	public void flagHistoryDetailsForMailbagsFromReassign(
			Collection<MailbagVO> acceptedMailbags, ContainerVO toContainerVO)
			throws SystemException {
		log.entering("HistoryBuilder",
				"flagHistoryDetailsForMailbagsFromReassign");
		String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
		for (MailbagVO mailbagVO : acceptedMailbags) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
				MailbagHistoryVO mailbagHistoryVO = new Mailbag()
						.constructMailbagHistoryForDestn(mailbagVO,
								toContainerVO);
				if (mailbagVO.isIsoffload()) {
					mailbagHistoryVO
							.setMailStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					mailbagHistoryVO.setCarrierId(mailbagVO.getCarrierId());
					mailbagHistoryVO.setFlightNumber(mailbag
							.getFlightNumber());
					mailbagHistoryVO.setFlightSequenceNumber(mailbag
							.getFlightSequenceNumber());
					mailbagHistoryVO.setContainerNumber(toContainerVO
							.getContainerNumber());
					mailbagHistoryVO.setSegmentSerialNumber(mailbag
							.getSegmentSerialNumber());
					mailbagHistoryVO.setFlightDate(toContainerVO.getFlightDate());
					//added as part of IASCB-75512
					if(mailbagVO.isRemove() && mailbagVO.getOffloadedRemarks()!=null) {
						if(mailbagVO.getOffloadedRemarks().length()<=25) {
						 mailbagHistoryVO.setAdditionalInfo(mailbagVO.getOffloadedRemarks());
						} else {
							mailbagHistoryVO.setAdditionalInfo(mailbagVO.getOffloadedRemarks().substring(0, 24));
						}
					}
				}

				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
			} else {
				log.log(Log.INFO, " calling >>>>>>>>>>>>>>updateFlightDetails");
				Collection<MailbagHistoryVO> mailbagHistoryVOs = mailbagVO
						.getMailbagHistories();
				if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
					//new Mailbag().populateHistoryDetails(mailbagHistoryVOs);IASCB-46569
					 for(MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
						 
						if(mailbagHistoryVO.getMailSource()!=null && mailbagHistoryVO.getMailSource().startsWith(MailConstantsVO.SCAN+":")){
					    	mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(MailConstantsVO.SCAN+":", ""));
					    }else{
					    	mailbagHistoryVO.setMailSource(triggeringPoint);
					    }
						mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
						 new MailbagHistory(mailbag.getMailbagPK(), mailbagHistoryVO);
			            }
					 
				} else {
					MailbagHistoryVO mailbagHistoryVO = new Mailbag()
							.constructFltReassignHistoryVO(mailbagVO,
									toContainerVO);
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
							mailbag);
				}
			}
		}
		log.exiting("HistoryBuilder",
				"flagHistoryDetailsForMailbagsFromReassign");

	}

	public void flagHistoryForContainerReassignment(
			OperationalFlightVO toFlightVO, ContainerVO containerVO,
			Collection<MailbagVO> mailbagVOS) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForContainerReassignment");

		for (MailbagVO mailbagVO : mailbagVOS) {
			// if(containerVO.getContainerNumber().equals(mailbagVO.getUldNumber()))
			// {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			if(mailbag == null) {
			MailbagHistoryVO historyVO = new Mailbag()
					.constructMailHistVOForReassign(toFlightVO, containerVO,
							mailbagVO);
			new Mailbag().insertMailbagHistoryDetails(historyVO, mailbag);
			}else{
				MailbagHistoryVO historyVO = mailbag
				.constructMailHistVOForReassign(toFlightVO, containerVO,
						mailbagVO);
				//added as part of  IASCB-75512
				if(mailbagVO.isIsoffload()&& mailbagVO.isRemove()) {
					if(mailbagVO.getOffloadedRemarks().length()<=25) {
						historyVO.setAdditionalInfo(mailbagVO.getOffloadedRemarks());
						} else {
							historyVO.setAdditionalInfo(mailbagVO.getOffloadedRemarks().substring(0, 24));
					}
				}
				mailbag.insertMailbagHistoryDetails(historyVO, mailbag);
			}
			// }
		}

		log.exiting("HistoryBuilder", "flagHistoryForContainerReassignment");
	}

	public MailbagPK createMailbagPK(String companyCode, MailbagVO mailbagVO)
			throws SystemException {
		MailbagPK mailbagPK = new MailbagPK();
		long mailsequenceNumber;

		//mailsequenceNumber =mailbagVO.getMailSequenceNumber();//Added by A-8353 for ICRD-230449 
		if(mailbagVO.getMailSequenceNumber()>0) { 
			mailsequenceNumber = mailbagVO.getMailSequenceNumber();
		} else { 
		mailsequenceNumber=new MailController().findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
		}

		mailbagPK.setCompanyCode(companyCode);
		mailbagPK.setMailSequenceNumber(mailsequenceNumber);
		return mailbagPK;
	}

	public void flagMailbagHistoryForArrival(MailArrivalVO mailArrivalVO)
			throws SystemException {
		log.entering("HistoryBuilder", "flagMailbagHistoryForArrival");
		Collection<ContainerDetailsVO> containerVOs = mailArrivalVO
				.getContainerDetails(); 
		Collection<MailbagVO> mailbagsForHistory = new ArrayList<MailbagVO>(); 
		for (ContainerDetailsVO containerVO : containerVOs) {
			Collection<MailbagVO> totalMails = containerVO.getMailDetails();
			if(null!=totalMails)//Added by A-8164 
			for (MailbagVO mail : totalMails) {
				if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail
						.getOperationalFlag())
						|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mail
								.getOperationalFlag())) {
					if(mail.isFromDeviationList() && "B".equals(containerVO.getContainerType())) {
						mail.setUldNumber("BULK-"+mail.getScannedPort());
					}
					mailbagsForHistory.add(mail);
				}
			}

		}

		for (MailbagVO mailbagVO : mailbagsForHistory) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			boolean[] arrDlv = updateIfHistoryExists(mailbag, mailbagVO);
			boolean isAlreadyArrived = arrDlv[0];
			boolean isAlreadyDelivered = arrDlv[1];
			mailbagVO.setMessageVersion(mailArrivalVO.getMessageVersion());
			if (!isAlreadyArrived || !isAlreadyDelivered) {

				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
				mailbagHistoryVO.setFomDeviationList(mailbagVO.isFromDeviationList());
				if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
						&& !isAlreadyArrived) {
					mailbagHistoryVO
							.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
					LocalDate scanDate=new LocalDate(LocalDate.NO_STATION, Location.NONE, false); 
					if(mailbagVO.getScannedDate()!=null && mailbagVO.getScannedPort()!=null) {
						scanDate = new LocalDate(mailbagVO.getScannedPort(), Location.ARP,true);
						scanDate.setDateAndTime(mailbagVO.getScannedDate().toDisplayFormat());
					}
					//Modified by A-8893 for IASCB-60686 starts
					LocalDate autoarrivaldate=updateArrivalEventTimeForAA(mailbagVO);
					if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getAutoArriveMail())&& autoarrivaldate!=null){
					mailbagHistoryVO.setScanDate(autoarrivaldate);
					}
					else if(autoarrivaldate==null){
						mailbagHistoryVO.setScanDate(scanDate);
					}
					//Modified by A-8893 for IASCB-60686 ends
					if(mailArrivalVO.getIsFromTruck()!=null){//Added by a-7871 for ICRD-240184
						mailbagHistoryVO.setAdditionalInfo("RFT");
						mailbagVO.setIsFromTruck("Y");
						mailbagVO.setStdOrStaTruckFlag("STA");
						mailbagHistoryVO.setScanDate(new ResditController().findResditEvtDate(mailbagVO,mailbagVO.getScannedPort()));	
					}
					if(mailbagVO.isFromDeviationList()) {
						mailbagHistoryVO.setContainerNumber(mailbagVO.getUldNumber());
						LocalDate scanDateTemp = mailbagHistoryVO.getScanDate();
					    mailbagHistoryVO.setScanDate(scanDateTemp);
					}
					
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO, 
							mailbag);
					if(scanDate!=null){
						mailbagVO.setScannedDate(scanDate);
						mailbagHistoryVO.setScanDate(scanDate);
					}    
				}

				// modified for icrd-129225 as damage needs to be captured
				// for nondelivered non arrived mailbags also
				if (mailbagVO.getDamageFlag() != null
						&& mailbagVO.getDamageFlag().equals(
								MailConstantsVO.FLAG_YES)
						&& !isAlreadyDelivered) {
					boolean newDamage = false;
					Collection<DamagedMailbagVO> damageVOs = mailbagVO
							.getDamagedMailbags();

					if (damageVOs != null && damageVOs.size() > 0) {
						for (DamagedMailbagVO damagedMailbagVO : damageVOs) {
							if (MailConstantsVO.OPERATION_FLAG_INSERT
									.equals(damagedMailbagVO.getOperationFlag())) {

								newDamage = true;
							}
						}
					}
					if (newDamage) {
						mailbagVO
								.setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
						MailbagHistoryVO mailbagHistryVO = constructMailHistoryVO(mailbagVO);
						new Mailbag().insertMailbagHistoryDetails(
								mailbagHistryVO, mailbag);
					}

				}

				if (MailConstantsVO.FLAG_YES.equals(mailbagVO
						.getDeliveredFlag()) && !isAlreadyDelivered) {
					mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());
					mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
					mailbagHistoryVO
							.setMailStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
					
					if(mailArrivalVO.getDeliveryRemarks()!=null && 
							mailArrivalVO.getDeliveryRemarks().trim().length()>0) {
						if(mailbagHistoryVO.getAdditionalInfo()!=null && !mailbagHistoryVO.getAdditionalInfo().isEmpty()){
						mailbagHistoryVO.setAdditionalInfo(new StringBuilder(mailbagHistoryVO.getAdditionalInfo()).append(",").append(mailArrivalVO.getDeliveryRemarks()).toString());
						}else{
							mailbagHistoryVO.setAdditionalInfo(mailArrivalVO.getDeliveryRemarks());	
						}
						
					}
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
							mailbag);
				}

			} else if (isAlreadyDelivered || isAlreadyArrived) {

				if (mailbagVO.getDamageFlag() != null
						&& mailbagVO.getDamageFlag().equals(
								MailConstantsVO.FLAG_YES)) {
					boolean newDamage = false;
					Collection<DamagedMailbagVO> damageVOs = mailbagVO
							.getDamagedMailbags();

					if (damageVOs != null && damageVOs.size() > 0) {
						for (DamagedMailbagVO damagedMailbagVO : damageVOs) {
							if (MailConstantsVO.OPERATION_FLAG_INSERT
									.equals(damagedMailbagVO.getOperationFlag())) {

								newDamage = true;
							}
						}
					}
					if (newDamage) {
						mailbagVO
								.setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
						MailbagHistoryVO mailbagHistryVO = constructMailHistoryVO(mailbagVO);
						new Mailbag().insertMailbagHistoryDetails(
								mailbagHistryVO, mailbag);
					}
				}

			}

		}

		log.exiting("HistoryBuilder", "flagMailbagHistoryForArrival");

	}

	private boolean[] updateIfHistoryExists(Mailbag mailbag, MailbagVO mailbagVO) throws SystemException {
		boolean[] arrDlv = new boolean[3];
		if (mailbag != null) {
			/*Collection<MailbagHistory> existingMailbagHistories = mailbag IASCB-46569
					.getMailbagHistories();*/
			
			Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
			
			// Modified for Bug ICRD-97039 by A-5526 starts

			// Added for Bug ICRD-97039 by A-5526 ends
			if (existingMailbagHistories != null
					&& existingMailbagHistories.size() > 0) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					if (mailbagHistory.getScannedPort().equals(
							mailbagVO.getScannedPort())) {
						if (mailbagHistory.getMailStatus().equals(
								MailConstantsVO.MAIL_STATUS_ARRIVED)) {
								if(MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())&&//Added For ICRD-140584
								!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){
									
									MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
									MailbagHistory mailhistory = null;
									 try {
			                             mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
			                             mailhistory.setScanDate(mailbagVO.getScannedDate());
			                     		LocalDate utcDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
			                    		GMTDate gmt =  utcDate.toGMTDate();
			                             mailhistory.setUtcScanDate(gmt.toCalendar());
			                             mailhistory.setLastUpdateUser(mailbagVO.getLastUpdateUser());
			                             mailhistory.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
			                         } catch (FinderException e) {
										 log.log(Log.SEVERE, "Finder Exception Caught");
									}
							  
								}
							arrDlv[0] = true;
						}
						if (mailbagHistory.getMailStatus().equals(
								MailConstantsVO.MAIL_STATUS_DELIVERED)) {
						 if(!mailbagVO.isFromDeviationList()) {
							MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
							MailbagHistory mailhistory = null;
							 try {
	                             mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
	                             mailhistory.setScanDate(mailbagVO.getScannedDate());
	                             mailhistory.setUtcScanDate(mailbagVO.getScannedDate().toGMTDate());
	                             mailhistory.setLastUpdateUser(mailbagVO.getLastUpdateUser());
	                             mailhistory.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
	                         } catch (FinderException e) {
								 log.log(Log.SEVERE, "Finder Exception Caught");
							}
						 }
							arrDlv[1] = true;
						}
					}
					// Added for Bug ICRD-97039 by A-5526 starts
					if (mailbagHistory.getMailStatus().equals(
							MailConstantsVO.MAIL_STATUS_DAMAGED)) {
						arrDlv[2] = true;
					}
					// Added for Bug ICRD-97039 by A-5526 ends
				}
			}
		}
		return arrDlv;

	}

	public void flagMailbagHistoryForDelivery(Collection<MailbagVO> mailbags)
			throws SystemException {
		log.entering("HistoryBuilder", "flagMailbagHistoryForDelivery");
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

					MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailBagVo);
					mailbagHistoryVO.setPaBuiltFlag(mailBagVo.getPaBuiltFlag());
					mailbagHistoryVO.setMailSource(mailBagVo.getMailSource());// Added
																				// for
																				// ICRD-156218
					mailbagHistoryVO.setMessageVersion(mailBagVo.getMessageVersion());
					if (mailBagVo.getScannedDate() != null) {
						mailbagHistoryVO
								.setScanDate(mailBagVo.getScannedDate());
					}
					populateSenderAndRecipient(mailBagVo, mailbagHistoryVO);
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
							mailbag);

				}
			}

		}
		log.exiting("HistoryBuilder", "flagMailbagHistoryForDelivery");

	}

	private void populateSenderAndRecipient(MailbagVO mailBagVo, MailbagHistoryVO mailbagHistoryVO) {
		if (Objects.nonNull(mailbagHistoryVO.getAdditionalInfo())) {
			StringBuilder sb = new StringBuilder();
			sb.append(mailbagHistoryVO.getAdditionalInfo()).append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR).append(MailConstantsVO.SENDER_ID_LEADING_VALUE)
					.append(mailBagVo.getMessageSenderIdentifier()).append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR)
					.append(MailConstantsVO.RECIPIENT_ID_LEADING_VALUE)
					.append(mailBagVo.getMessageRecipientIdentifier());
			mailbagHistoryVO.setAdditionalInfo(sb.toString());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(MailConstantsVO.SENDER_ID_LEADING_VALUE).append(mailBagVo.getMessageSenderIdentifier())
					.append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR)
					.append(MailConstantsVO.RECIPIENT_ID_LEADING_VALUE)
					.append(mailBagVo.getMessageRecipientIdentifier());
			mailbagHistoryVO.setAdditionalInfo(sb.toString()); 
		}
	}

	public void flagHistoryForReturnedMailbags(Collection<MailbagVO> mailbags)
			throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForReturnedMailbags");

		for (MailbagVO mailbagVO : mailbags) {
			Mailbag mailbag = null;
			try {  //Added by A-8353 for ICRD-230449 starts 
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(   mailbagVO.getCompanyCode());
				long mailsequenceNumber;
				if(mailbagVO.getMailSequenceNumber()>0) {
					mailsequenceNumber = mailbagVO.getMailSequenceNumber();
				} else { 
				mailsequenceNumber=mailController.findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				}
				mailbagPK.setMailSequenceNumber(mailsequenceNumber);//Changed by A-8164 for ICRD-333716
				// Added by A-8353 for ICRD-230449 ends
				mailbag = Mailbag.find(mailbagPK); 
			} catch (FinderException e) {
				mailbag = null;
			}

			Collection<DamagedMailbagVO> damagedMailbagVOs = mailbagVO
					.getDamagedMailbags();
			if (damagedMailbagVOs != null && damagedMailbagVOs.size() > 0) {

				updateMailbagHistoryForDamage(mailbagVO);

			}

			MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
			mailHistoryVO.setMailSource(mailbagVO.getMailSource());
			mailHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			mailHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_RETURNED);
			mailHistoryVO.setCarrierCode(null);
			mailHistoryVO.setCarrierId(0);
			mailHistoryVO.setFlightNumber(null);
			mailHistoryVO.setFlightSequenceNumber(0);
			mailHistoryVO.setFlightDate(null);
			mailHistoryVO.setContainerNumber(null);
			mailHistoryVO.setContainerType(null);
			
			new Mailbag().insertMailbagHistoryDetails(mailHistoryVO, mailbag);
			log.exiting("Mailbag", "updateReturnDetails");

		}
		log.exiting("HistoryBuilder", "flagHistoryForReturnedMailbags");
	}

	private MailbagHistoryVO constructMailHistoryVO(MailbagVO mailbagVO) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setMailStatus(mailbagVO.getLatestStatus());
		mailbagHistoryVO.setScannedPort(mailbagVO.getScannedPort());
		mailbagHistoryVO.setScanUser(mailbagVO.getScannedUser());
		mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
		// mailbagHistoryVO.setScanDate(
		// new LocalDate(getScannedPort(), Location.ARP, getScannedDate(),
		// true));
		if(mailbagVO.getScannedDate()!=null){
			mailbagHistoryVO.setScanDate(mailbagVO.getScannedDate());
		}else{
		mailbagHistoryVO.setScanDate(new LocalDate(mailbagVO.getScannedPort(),
				Location.ARP, true));
		}
		mailbagHistoryVO.setCarrierId(mailbagVO.getCarrierId());
		mailbagHistoryVO.setFlightNumber(mailbagVO.getFlightNumber());
		mailbagHistoryVO.setFlightSequenceNumber(mailbagVO
				.getFlightSequenceNumber());
		// Added by A-5945 for ICRD-96316 starts
		if (mailbagVO != null
				&& (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO
						.getLatestStatus()))
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO
						.getLatestStatus())) {
			mailbagHistoryVO.setContainerNumber(mailbagVO.getContainerNumber());
		}
		else
		{
			// Added by A-5945 for ICRD-96316 ends
			//mailbagHistoryVO.setContainerNumber(mailbagVO.getUldNumber());

			//Added by A-7540 for ICRD-206691 starts
			if(mailbagVO.getUldNumber()!=null){
			mailbagHistoryVO.setContainerNumber(mailbagVO.getUldNumber());
			}
			else{
				mailbagHistoryVO.setContainerNumber(mailbagVO.getContainerNumber());
			}

		}

		   //Added by A-7540 for ICRD-206691 ends


		mailbagHistoryVO.setSegmentSerialNumber(mailbagVO
				.getSegmentSerialNumber());
		if (mailbagVO != null) {
			if ((mailbagVO.getScannedDate() != null)) {
				mailbagHistoryVO.setScanDate(mailbagVO.getScannedDate());
			}
			mailbagHistoryVO.setScanUser(mailbagVO.getScannedUser());
			mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());// Added
																		// for
																		// ICRD-156218
			//Added by A-8527 for IASCB-58918
			mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
		}

		mailbagHistoryVO.setContainerType(mailbagVO.getContainerType());
		if(mailbagHistoryVO.getFlightSequenceNumber() >0 || MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagVO.getMailbagSource())){       
		mailbagHistoryVO.setPou(mailbagVO.getPou());
		} //Modified by A-6991 for ICRD-209424
		// mailbagHistoryVO.setContainerNumber(getUldNumber());
		mailbagHistoryVO.setContainerType(mailbagVO.getContainerType());
		// temporary code for identify the root cause of bug ICRD-160796 starts
		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO
				.getLatestStatus())) {

			if (mailbagVO.getSegmentSerialNumber() == 0
					&& mailbagVO.getFlightNumber() != null

		//Modified as part of code quality work by A-7531
					&& !("-1").equals(mailbagVO.getFlightNumber())) {
				String mailSource = mailbagHistoryVO.getMailSource();
				if (mailSource != null && !mailSource.isEmpty()) {
					mailSource = mailSource.concat("CMH");
				}
				mailbagHistoryVO.setMailSource(mailSource);
			}

		}
		// temporary code for bug ICRD-160796 by A-5526 as issue is not getting
		// replicated ends

		mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
		mailbagHistoryVO.setMailClass(mailbagVO.getMailClass());

////Modified as part of bug IASCB-61182 by A-5526 
		if ((mailbagVO.getFlightSequenceNumber() > 0 || MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagVO.getMailSource())) && mailbagVO != null) {

			mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
			mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
			mailbagHistoryVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			log.log(Log.FINE, "##########mailbagVO.getPaBuiltFlag()####",
					mailbagVO.getPaBuiltFlag());
		}
	
		if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())){
			mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
		}


		//added by A-9529 for IASCB-44567
		mailbagHistoryVO.setScreeningUser(mailbagVO.getScreeningUser());//Added by A-9498 as part of IASCB-44577
		mailbagHistoryVO.setStorageUnit(mailbagVO.getStorageUnit());
		return mailbagHistoryVO;
	}

	public void flagHistoryForDamagedMailbags(Collection<MailbagVO> mailbags)
			throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForDamagedMailbags");
		for (MailbagVO mailbagVO : mailbags) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}

			Collection<DamagedMailbagVO> damagedMailbagVOs = mailbagVO
					.getDamagedMailbags();

			for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
				// boolean newDamage=false;

				/*
				 * if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO
				 * .getOperationFlag())){
				 *
				 * newDamage=true; }
				 */
				// if(newDamage){
				MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
				mailHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
				if ((damagedMailbagVO.getPaCode() != null && damagedMailbagVO
						.getPaCode().trim().length() > 0)
						&& (mailbagVO.getPaCode() != null && mailbagVO
								.getPaCode().trim().length() > 0)) {
					mailHistoryVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				}

				if (mailHistoryVO.getCarrierCode() == null) {
					log.log(Log.FINE, "setting the Carrier Code",
							mailbagVO.getCarrierCode());
					mailHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
				}

				new Mailbag().insertMailbagHistoryDetails(mailHistoryVO,
						mailbag);

				// }
			}

		}
		log.exiting("HistoryBuilder", "flagHistoryForDamagedMailbags");
	}

	public void flagHistoryForTransfer(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForTransfer");
		for (MailbagVO mailbagToUpdate : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagToUpdate.getCompanyCode(), mailbagToUpdate));
			} catch (FinderException e) {
				mailbag = null;
			}

			// check if already arrived else create history for arrival befor
			// acceptance hsitroy
			boolean[] eventStats = checkIfHistoryExists(null, mailbag,
					mailbagToUpdate, MailConstantsVO.MAIL_STATUS_ARRIVED,
					MailConstantsVO.MAIL_STATUS_DELIVERED);
			boolean isArrived = eventStats[0];
			boolean isDelivered = eventStats[1];
			if (!isArrived && !isDelivered && !"CARDIT".equals(mailbagToUpdate.getFromPanel()) &&
					!("Y".equals(mailbagToUpdate.getIsFromTruck()))&&
						!containerVO.isFoundTransfer() &&
						!("Y".equals(mailbagToUpdate.getAutoArriveMail()) &&
								mailbagToUpdate.getMailSource()!=null&&(mailbagToUpdate.getMailSource().startsWith(MailConstantsVO.SCAN+":")||MailConstantsVO.WS.equals(mailbagToUpdate.getMailSource())))) {
				//Added isFoundTransfer by A-8164 for IASCB-34167
				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
				mailbagHistoryVO.setFomDeviationList(mailbagToUpdate.isFromDeviationList());
				mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
				// find prev flight details
				MailbagVO mailbagVO = constructMailbagVO(mailbag);
				// find prev flight details
				mailbagVO = MailbagHistory
						.findLatestFlightDetailsOfMailbag(mailbagVO);
				LocalDate scanDate=new LocalDate(LocalDate.NO_STATION, Location.NONE, false); 
				if(mailbagToUpdate.getScannedDate()!=null)
					scanDate=mailbagToUpdate.getScannedDate();
				//Modified by A-8893 for IASCB-60686 starts
				mailbagToUpdate.setAutoArriveMail(MailConstantsVO.FLAG_YES);
				LocalDate autoarrivaldate=updateArrivalEventTimeForAA(mailbagToUpdate);       
				//Modified by A-8893 for IASCB-60686 ends
				// Modified for ICRD-156218
				if (!"MTK_IMP_FLT".equals(mailbagToUpdate.getMailSource())
						&& !"MTK_INB_ONLINEFLT_CLOSURE".equals(mailbagToUpdate
								.getMailSource())&&mailbagVO!=null) {
					mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
				} else {
					mailbagHistoryVO.setFlightDate(mailbagToUpdate
							.getFlightDate());
				}
				mailbagHistoryVO.setCarrierCode(mailbagToUpdate.getCarrierCode());
				mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());// Added
																				// for
				//Modified by A-8893 for IASCB-60686 starts															// ICRD-156218
				mailbagHistoryVO.setMessageVersion(mailbagToUpdate.getMessageVersion());
				if(autoarrivaldate!=null){
					mailbagHistoryVO.setScanDate(autoarrivaldate);
				}else{
					mailbagHistoryVO.setScanDate(mailbagToUpdate.getScannedDate());
				}
				//Modified by A-8893 for IASCB-60686 ends
				if("Y".equals(mailbagToUpdate.getIsFromTruck())){
					mailbagHistoryVO.setAdditionalInfo("RFT");
					mailbagToUpdate.setStdOrStaTruckFlag("STA");
					mailbagHistoryVO.setScanDate(new ResditController().findResditEvtDate(mailbagToUpdate, mailbagToUpdate.getScannedPort()));
				}
				
				if(mailbagToUpdate.isFromDeviationList()) {
					//stamp arrival flight based on the previous flights
					updateHistoryWithPreviousFlightDetails(mailbagHistoryVO,containerVO, mailbagToUpdate);
				}
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
				if(scanDate!=null){
					mailbagToUpdate.setScannedDate(scanDate);           
				}
			}
			

			// insert history for outbound
			MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
			mailbagHistoryVO.setFomDeviationList(mailbagToUpdate.isFromDeviationList());
			if(containerVO!=null){
			mailbagHistoryVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			mailbagHistoryVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			if(mailbagHistoryVO.getFlightSequenceNumber()>0){
			mailbagHistoryVO.setFlightDate(containerVO.getFlightDate());
			}
			else {
				mailbagHistoryVO.setFlightDate(null);	
			}
			mailbagHistoryVO.setCarrierCode(containerVO.getCarrierCode());
			mailbagHistoryVO.setCarrierId(containerVO.getCarrierId());
			}
			mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());// Added
																			// for
																			// ICRD-156218
			// temporary code for identify the root cause of bug ICRD-160796
			// starts
		/*	if (mailbagToUpdate.getSegmentSerialNumber() == 0
					&& mailbagToUpdate.getFlightNumber() != null

		//Modified as part of code quality work by A-7531
					&& !("-1").equals(mailbagToUpdate.getFlightNumber()) ) {
				String mailSource = mailbagHistoryVO.getMailSource();
				if (mailSource != null && !mailSource.isEmpty()) {
					mailSource = mailSource.concat("UDFT");
				}
				mailbagHistoryVO.setMailSource(mailSource);
			}*/
			
	
			 if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagToUpdate.getMailStatus())){
			mailbagHistoryVO
					.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				 mailbagHistoryVO
					.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+containerVO.getCarrierCode());
			        }
			 else if(MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagToUpdate.getMailStatus())){
				 mailbagHistoryVO
					.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
				 String transferCarrier=mailbagToUpdate.getTransferFromCarrier()!=null?mailbagToUpdate.getTransferFromCarrier():mailbagToUpdate.getCarrierCode();
					 mailbagHistoryVO
						.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+transferCarrier);
			        }
			  else{
			        	mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			  }
			if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag
					.getLatestStatus())||MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag
					.getLatestStatus())||MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbag
					.getLatestStatus())) {	  
				mailbagHistoryVO.setFlightNumber(mailbag.getFlightNumber());
				mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
				mailbagHistoryVO.setPou(mailbag.getPou());
				if("Y".equals(mailbagToUpdate.getIsFromTruck())){//clearing for Transfer Transaction
						//mailbagHistoryVO.setAdditionalInfo("  RFT");//RFT change
						mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);//RFT change
				}
			}
			if("WS".equals(mailbagToUpdate.getMailSource()) && mailbagToUpdate.getScannedDate() != null){
				mailbagHistoryVO.setScanDate(mailbagToUpdate.getScannedDate());
			}
			// temporary code for bug ICRD-160796 by A-5526 as issue is not
			// getting replicated ends
			new Mailbag()
					.insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
			log.exiting("Mailbag", "updateDetailsForTransfer");

		}

		log.exiting("HistoryBuilder", "flagHistoryForTransfer");
	}

	private void updateHistoryWithPreviousFlightDetails(MailbagHistoryVO mailbagHistoryVO, ContainerVO containerVO, MailbagVO mailbagVO)
			throws SystemException {
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		Collection<MailbagVO> mailbagVosTemp = null;
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		mailbagVosTemp = new ArrayList<MailbagVO>();
		mailbagVosTemp.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailbagVosTemp);
		scannedMailDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		scannedMailDetailsVO.setAirportCode(containerVO.getPol());
		try {
			mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
		} catch (PersistenceException e) {
			log.log(Log.SEVERE, "updateHistoryWithPreviousFlightDetails-->", e);
		}
		if (mailbagInULDForSegmentVO != null) {
			mailbagHistoryVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
			mailbagHistoryVO.setScannedPort(containerVO.getPol());
			mailbagHistoryVO.setAirportCode(mailbagInULDForSegmentVO.getAssignedPort());
			mailbagHistoryVO.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
			mailbagHistoryVO.setPou(containerVO.getPol());
			mailbagHistoryVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
			mailbagHistoryVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
			mailbagHistoryVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
			mailbagHistoryVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
		}
	}

	private boolean[] checkIfHistoryExists(MailbagHistoryVO mailbagHistoryVO,
			Mailbag mailbag, MailbagVO mailbagVO, String... events) throws SystemException {
		boolean[] evtStats = new boolean[events.length];
		if (mailbag != null) {
			/*Collection<MailbagHistory> existingMailbagHistories = mailbag IASCB-46569
					.getMailbagHistories();*/
			Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getMailbagPK().getCompanyCode(),"", mailbag.getMailbagPK().getMailSequenceNumber());
			
			int idx = 0;
			int hisSeqTmp=0;
			String airport = mailbag.getScannedPort();
			if (mailbagVO != null) {
				airport = mailbagVO.getScannedPort();
			}
			if (existingMailbagHistories != null
					&& existingMailbagHistories.size() > 0) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					for (String mailEvent : events) {
						if (mailbagHistory.getScannedPort().equals(airport)
								&& mailbagHistory.getMailStatus().equals(
										mailEvent)) {
							evtStats[idx++] = true;
							break;
						} else if (MailConstantsVO.CONTAINER_STATUS_TRANSFER
								.equals(mailEvent)) {
							if ((MailConstantsVO.MAIL_STATUS_ASSIGNED
									.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_DAMAGED
									.equals(mailbagHistory.getMailStatus()))) {
								
								if(mailbagHistory.getHistorySequenceNumber() > hisSeqTmp){
								hisSeqTmp=mailbagHistory.getHistorySequenceNumber();
								mailbagHistoryVO.setCarrierCode(mailbagHistory
										.getCarrierCode());
								if(mailbagHistory.getFlightSequenceNumber() > 1 &&
										!("-1").equals(mailbagHistory.getFlightNumber())){
								mailbagHistoryVO.setFlightSequenceNumber(mailbagHistory.getFlightSequenceNumber());
								mailbagHistoryVO.setSegmentSerialNumber(mailbagHistory.getSegmentSerialNumber());
								mailbagHistoryVO.setFlightNumber(mailbagHistory
										.getFlightNumber());
								LocalDate ldate = new LocalDate(airport,
										Location.ARP,
										mailbagHistory.getFlightDate(), true);
								mailbagHistoryVO.setFlightDate(ldate);
								}
								mailbagHistoryVO
										.setContainerNumber(mailbagHistory
												.getContainerNumber());
								mailbagHistoryVO
										.setPou(mailbagHistory.getPou());
								if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagHistoryVO.getMailStatus())){
								MailbagVO tempMailVO=new MailbagVO();
								tempMailVO.setCompanyCode(mailbagVO.getCompanyCode());        
								tempMailVO.setCarrierId(mailbagHistoryVO.getCarrierId());
								tempMailVO.setFlightNumber(mailbagHistoryVO.getFlightNumber());
								tempMailVO.setFlightSequenceNumber(mailbagHistoryVO.getFlightSequenceNumber());
								tempMailVO.setFlightDate(mailbagHistoryVO.getFlightDate());
								tempMailVO.setPou(mailbagHistoryVO.getPou());    
								tempMailVO.setScannedDate(mailbagHistoryVO.getScanDate());
								tempMailVO.setAutoArriveMail(MailConstantsVO.FLAG_YES);
								//Modified by A-8893 for IASCB-60686 starts
								LocalDate autoarrivaldate=updateArrivalEventTimeForAA(tempMailVO);
								if(autoarrivaldate!=null){
								mailbagHistoryVO.setScanDate(autoarrivaldate);
								}
								else{
									LocalDate scanDate=new LocalDate(LocalDate.NO_STATION, Location.NONE, false); 
									if(tempMailVO.getScannedDate()!=null)
										scanDate=tempMailVO.getScannedDate();
								
									mailbagHistoryVO.setScanDate(scanDate);
								}
								//Modified by A-8893 for IASCB-60686 ends
								}
								
								}
								

							}
						}
					}
				}
			}
		}
		return evtStats;
	}

	private MailbagVO constructMailbagVO(Mailbag mailbag) {
		MailbagVO mailbagVO = new MailbagVO();
		MailbagPK mailbagPK = mailbag.getMailbagPK();
		mailbagVO.setCompanyCode(mailbagPK.getCompanyCode());
		mailbagVO.setMailbagId(mailbag.getMailIdr());
		mailbagVO.setDespatchSerialNumber(mailbag.getDespatchSerialNumber());
		mailbagVO.setOoe(mailbag.getOrginOfficeOfExchange());
		mailbagVO.setDoe(mailbag.getDestinationOfficeOfExchange());
		mailbagVO.setMailSubclass(mailbag.getMailSubClass());
		mailbagVO.setMailClass(mailbag.getMailSubClass().substring(0, 1));
		mailbagVO.setMailCategoryCode(mailbag.getMailCategory());
		mailbagVO.setYear(mailbag.getYear());
		mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());

		return mailbagVO;
	}

	public void flagHistoryForContainerTransfer(
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForContainerTransfer");
		for (MailbagVO mailbagToUpdate : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagToUpdate.getCompanyCode(), mailbagToUpdate));
			} catch (FinderException e) {
				mailbag = null;
			}

			// check if already arrived else create history for arrival befor
			// acceptance hsitroy
			boolean[] eventStats = checkIfHistoryExists(null, mailbag,
					mailbagToUpdate, MailConstantsVO.MAIL_STATUS_ARRIVED,
					MailConstantsVO.MAIL_STATUS_DELIVERED);
			boolean isArrived = eventStats[0];
			boolean isDelivered = eventStats[1];
				LocalDate scanDate=mailbagToUpdate.getScannedDate();
			if (!isArrived && !isDelivered) {		
				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
				mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
				checkIfHistoryExists(mailbagHistoryVO, mailbag,
						mailbagToUpdate,
						MailConstantsVO.CONTAINER_STATUS_TRANSFER,
						MailConstantsVO.CONTAINER_STATUS_TRANSFER);
				/*
				 * MailbagVO mailbagVO = constructMailbagVO(mailbag); //find
				 * prev flight details mailbagVO =
				 * MailbagHistory.findLatestFlightDetailsOfMailbag(mailbagVO);
				 */

				// Added for ICRD-156218 starts
				if (operationalFlightVO.isScanned()) {
					mailbagHistoryVO.setMailSource(MailConstantsVO.SCAN);
				} else {
					mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());
				}
				// Added for ICRD-156218 ends
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
				if(scanDate!=null){
					mailbagToUpdate.setScannedDate(scanDate);
				}
			}

			if (!isDelivered &&(!operationalFlightVO.isTransferOutOperation()||operationalFlightVO.isTransferStatus())) {

				// insert history for outbound
				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
				mailbagHistoryVO.setFlightDate(operationalFlightVO
						.getFlightDate());
				mailbagHistoryVO.setCarrierCode(operationalFlightVO
						.getCarrierCode());
				mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
				// temporary code for identify the root cause of bug ICRD-160796
				// starts
				if (mailbag.getSegmentSerialNumber() == 0
						&& mailbag.getFlightNumber() != null
						&& !"-1".equals(mailbag.getFlightNumber())) {
					String mailSource = mailbagHistoryVO.getMailSource();
					if (mailSource != null && !mailSource.isEmpty()) {
						mailSource = mailSource.concat("UUTD");
					}
					mailbagHistoryVO.setMailSource(mailSource);
				}
				// temporary code for bug ICRD-160796 by A-5526 as issue is not
				// getting replicated ends
				mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);    
				if(operationalFlightVO.isTransferStatus()){
					mailbagHistoryVO.setMailStatus(MailConstantsVO.TRANSFER_INITIATED); 
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,mailbag);
					mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				}else {
					mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED); 
				}
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
			}

		}
		log.exiting("HistoryBuilder", "flagHistoryForContainerTransfer");

	}
	
	private MailbagHistoryVO constructMailHistoryVOForReassignFromAcceptance(Mailbag mailbag,MailbagVO mailbagVO) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setMailStatus(mailbag.getLatestStatus());
		mailbagHistoryVO.setScannedPort(mailbag.getScannedPort());
		mailbagHistoryVO.setScanUser(mailbag.getScannedUser());
		mailbagHistoryVO.setScanDate(new LocalDate(mailbag.getScannedPort(),
				Location.ARP, true));
		mailbagHistoryVO.setCarrierId(mailbag.getCarrierId());
		mailbagHistoryVO.setFlightNumber(mailbagVO.getFlightNumber());        
		mailbagHistoryVO.setFlightSequenceNumber(mailbagVO
				.getFlightSequenceNumber());
	
		
			mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
		mailbagHistoryVO.setSegmentSerialNumber(mailbag
				.getSegmentSerialNumber());
		mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
		if (mailbag != null) {
			
			mailbagHistoryVO.setScanUser(mailbag.getScannedUser());
			mailbagHistoryVO.setMailSource("MTK002_EXTRSG");
		}

		mailbagHistoryVO.setContainerType(mailbag.getContainerType());
		mailbagHistoryVO.setPou(mailbag.getPou());
		mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
		mailbagHistoryVO.setMailClass(mailbag.getMailClass());

		if (mailbag.getFlightSequenceNumber() > 0 && mailbag != null) {
			mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
			
		}
		return mailbagHistoryVO;
	}
   /**
	 * 
	 * 	Method		:	HistoryBuilder.flagHistoryForMailAwbAttachment
	 *	Added by 	:	a-7779 on 28-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbags
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void flagHistoryForMailAwbAttachment(Collection<MailbagVO> mailbags) throws SystemException{
		
		log.entering("HistoryBuilder", "flagHistoryForMailAwbAttachment");
		MailbagHistoryVO mailbagHistoryVO = null;
		if(mailbags!=null && mailbags.size()>0){
			for(MailbagVO mailbagVO : mailbags){
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(createMailbagPK(
							mailbagVO.getCompanyCode(), mailbagVO));
				} catch (FinderException e) {
					mailbag = null;
				}
				mailbagHistoryVO = new MailbagHistoryVO();
				mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
				mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
				mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
			}
		}
		
		log.exiting("HistoryBuilder", "flagHistoryForMailAwbAttachment");
	}
	/**
	 * Method		:	HistoryBuilder.transferContainersAtExport
	 * Added by 	:	A-7371 on 05-Jan-2018
	 * Used for 	:	ICRD-133987
	 * @param operationalFlightVO
	 * @param toFlightSegSerNum
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagHistoryForContainerTransferAtExport(
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForContainerTransferAtExport");
		for (MailbagVO mailbagToUpdate : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagToUpdate.getCompanyCode(), mailbagToUpdate));
			} catch (FinderException e) {
				mailbag = null;
			}
				MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
				mailbagHistoryVO.setFlightDate(operationalFlightVO
						.getFlightDate());
				mailbagHistoryVO.setCarrierCode(operationalFlightVO
						.getCarrierCode());
				mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
				
				if (mailbag.getSegmentSerialNumber() == 0
						&& mailbag.getFlightNumber() != null
						&& !"-1".equals(mailbag.getFlightNumber())) {
					String mailSource = mailbagHistoryVO.getMailSource();
					if (mailSource != null && !mailSource.isEmpty()) {
						mailSource = mailSource.concat("UUTD");
					}
					mailbagHistoryVO.setMailSource(mailSource);
				}
				mailbagHistoryVO.setMailStatus(MailConstantsVO.TRANSFER_INITIATED);
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
				mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
		}
		log.exiting("HistoryBuilder", "flagHistoryForContainerTransferAtExport");

	}
	/**
	 * Method		:	HistoryBuilder.flagHistoryForMailTransferAtExport
	 * Added by 	:	A-7371 on 05-Jan-2018
	 * Used for 	:	ICRD-133987
	 * @param operationalFlightVO
	 * @param toFlightSegSerNum
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagHistoryForMailTransferAtExport(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO) throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryForMailTransferAtExport");
		for (MailbagVO mailbagToUpdate : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagToUpdate.getCompanyCode(), mailbagToUpdate));
			} catch (FinderException e) {
				mailbag = null;
			}

	
			// insert history for outbound
			MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagToUpdate);
			if(containerVO!=null){
			mailbagHistoryVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			mailbagHistoryVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			mailbagHistoryVO.setFlightNumber(containerVO.getFlightNumber());
			mailbagHistoryVO.setFlightDate(containerVO.getFlightDate());
			mailbagHistoryVO.setCarrierCode(containerVO.getCarrierCode());
			mailbagHistoryVO.setCarrierId(containerVO.getCarrierId());
			}
			mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());
			mailbagHistoryVO.setMessageVersion(mailbagToUpdate.getMessageVersion());
			/*if (mailbagToUpdate.getSegmentSerialNumber() == 0
					&& mailbagToUpdate.getFlightNumber() != null	
					&& !("-1").equals(mailbagToUpdate.getFlightNumber()) ) {
				String mailSource = mailbagHistoryVO.getMailSource();
				if (mailSource != null && !mailSource.isEmpty()) {
					mailSource = mailSource.concat("UDFT");
				}
				mailbagHistoryVO.setMailSource(mailSource);
			}*/
			

			if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagToUpdate.getMailStatus())){
			mailbagHistoryVO.setMailStatus(MailConstantsVO.TRANSFER_INITIATED);
			new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
			mailbagHistoryVO
					.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			
		
				 mailbagHistoryVO
					.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+containerVO.getCarrierCode());

			        }
			else if(MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagToUpdate.getMailStatus())){
				 mailbagHistoryVO
					.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
				 String transferCarrier=mailbagToUpdate.getTransferFromCarrier()!=null?mailbagToUpdate.getTransferFromCarrier():mailbagToUpdate.getCarrierCode();
			
					 mailbagHistoryVO
						.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+transferCarrier);
				 
			        }
			  else{
			        	mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			  }
			if(containerVO.isHandoverReceived()){
				mailbagHistoryVO.setFlightNumber(mailbagToUpdate.getFlightNumber());
				mailbagHistoryVO.setContainerNumber(mailbagToUpdate.getContainerNumber());
			}
			else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getLatestStatus())
				||MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getLatestStatus())
				||MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbag.getLatestStatus())){
				mailbagHistoryVO.setFlightNumber(mailbag.getFlightNumber());
				mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
				mailbagHistoryVO.setPou(mailbag.getPou());
			}
			if("WS".equals(mailbagToUpdate.getMailSource()) && mailbagToUpdate.getScannedDate() != null){
				mailbagHistoryVO.setScanDate(mailbagToUpdate.getScannedDate());
			}
			new Mailbag()
					.insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
			log.exiting("Mailbag", "updateDetailsForTransfer");

		}

		log.exiting("HistoryBuilder", "flagHistoryForMailTransferAtExport");
	}
	
	private LocalDate updateArrivalEventTimeForAA(MailbagVO mailbagVO) {
		//Modified by A-8893 for IASCB-60686 
		LocalDate autoarrivaldate=null;
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
		
		if(sysparfunpnts!=null && !sysparfunpnts.equals("NA"))
		{
          if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getAutoArriveMail()) )       
	        {
        	  FlightValidationVO validVO =null;
      		try {
      			validVO = new MailController().validateFlightForBulk(mailbagVO);
      		} catch (SystemException e) {
      			validVO=null;
      		}
      		if(validVO!=null){
      			if(validVO.getAta() != null){
      				autoarrivaldate=validVO.getAta().addMinutes(sysparoffset!=null && !sysparoffset.equals("N")?Integer.parseInt(sysparoffset):0);
      			}
      		}
      		
        	  
	        }
		}
		
	return autoarrivaldate;	
		
	}
	/**
	 * 
	 * 	Method		:	HistoryBuilder.insertOrUpdateHistoryDetailsForCardit
	 *	Added by 	:	U-1467 on 18-Feb-2020
	 * 	Used for 	:	IASCB-36803
	 *	Parameters	:	@param carditVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void insertOrUpdateHistoryDetailsForCardit(CarditVO carditVO) throws SystemException {
		if (carditVO.getReceptacleInformation() != null && carditVO.getReceptacleInformation().size() > 0) {
			for (CarditReceptacleVO carditReceptacleVO : carditVO.getReceptacleInformation()) {
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
					if (mailbag != null) {
						MailbagHistoryVO mailbagHistoryVO = constructMailbagHistoryVO(carditVO, mailbag);
						new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
					}
				}

			}
		}
	}
	/**
	 * 
	 * 	Method		:	HistoryBuilder.constructMailbagHistoryVO
	 *	Added by 	:	U-1467 on 18-Feb-2020
	 * 	Used for 	:	IASCB-36803
	 *	Parameters	:	@param carditReceptacleVO
	 *	Parameters	:	@param mailbag
	 *	Parameters	:	@return 
	 *	Return type	: 	MailbagHistoryVO
	 */
	private MailbagHistoryVO constructMailbagHistoryVO(CarditVO carditVO, Mailbag mailbag) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();		
		mailbagHistoryVO.setMailStatus(MailConstantsVO.CARDIT_EVENT);
		mailbagHistoryVO.setScannedPort(mailbag.getScannedPort());
		mailbagHistoryVO.setScanDate(new LocalDate(mailbag.getScannedPort(), Location.ARP, true));
		//Added by A-8893 for IASCB-65069 starts
		if(!mailbag.getLatestStatus().isEmpty()&& ("NEW").equals(mailbag.getLatestStatus())){
			mailbagHistoryVO.setScanUser(mailbag.getScannedUser());
		mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
		mailbagHistoryVO.setMailSource(mailbag.getMailbagSource());
		mailbagHistoryVO.setContainerType(mailbag.getContainerType());
		mailbagHistoryVO.setPou(mailbag.getPou());
			}
			else{
				mailbagHistoryVO.setScanUser(carditVO.getLastUpdateUser());	
			}
		//Added by A-8893 for IASCB-65069 ends
		mailbagHistoryVO.setMailClass(mailbag.getMailClass());
		/*if (mailbag.getConsignmentNumber() != null && mailbag.getConsignmentNumber().length() > 25) {
			mailbagHistoryVO.setAdditionalInfo(mailbag.getConsignmentNumber().substring(0, 25));//Additional info max length is 25
		} else {
			mailbagHistoryVO.setAdditionalInfo(mailbag.getConsignmentNumber());
		}*/
		StringBuilder sb = new StringBuilder();
		sb.append(MailConstantsVO.SENDER_ID_LEADING_VALUE).append(carditVO.getSenderId())
				.append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR).append(MailConstantsVO.RECIPIENT_ID_LEADING_VALUE)
				.append(carditVO.getRecipientId());
		mailbagHistoryVO.setAdditionalInfo(sb.toString());
		List<CarditTransportationVO> transportVOs = (List<CarditTransportationVO>) carditVO.getTransportInformation();
		if (transportVOs != null && transportVOs.size() > 0) {
			CarditTransportationVO transportDetails = transportVOs.get(0);
			mailbagHistoryVO.setCarrierId(transportDetails.getCarrierID());
			mailbagHistoryVO.setFlightNumber(transportDetails.getFlightNumber());
			mailbagHistoryVO.setFlightSequenceNumber(transportDetails.getFlightSequenceNumber());
			mailbagHistoryVO.setSegmentSerialNumber(transportDetails.getSegmentSerialNum());
			mailbagHistoryVO.setCarrierCode(transportDetails.getCarrierCode());
			mailbagHistoryVO.setFlightDate(transportDetails.getDepartureTime());
		}
		return mailbagHistoryVO;

	} 
	/**
	 * 
	 * 	Method		:	HistoryBuilder.insertHistoryDetailsForExcelUpload
	 *	Added by 	:	U-1467 on 20-Apr-2020
	 * 	Used for 	:	IASCB-48400
	 *	Parameters	:	@param consignmentDocumentVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void insertHistoryDetailsForExcelUpload(Collection<ConsignmentDocumentVO> consignmentDocumentVOs) throws SystemException {
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			for (ConsignmentDocumentVO consignmentDocumentVO : consignmentDocumentVOs) {
				if (consignmentDocumentVO.getMailInConsignmentVOs() != null && consignmentDocumentVO.getMailInConsignmentVOs().size() > 0) {
					for (MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()) {
						MailbagPK mailbagPK = new MailbagPK();
						long mailSequenceNumber;
						Mailbag mailbag = null;
						mailSequenceNumber = new MailController().findMailSequenceNumber(mailInConsignmentVO.getMailId(),
								mailInConsignmentVO.getCompanyCode());
						if (mailSequenceNumber > 0) {
							mailbagPK.setMailSequenceNumber(mailSequenceNumber);
							mailbagPK.setCompanyCode(mailInConsignmentVO.getCompanyCode());
							try {
								mailbag = Mailbag.find(mailbagPK);
							} catch (FinderException e) {
								mailbag = null;
							}
							if (mailbag != null) {
								MailbagHistoryVO mailbagHistoryVO = constructMailbagHistoryVO(consignmentDocumentVO, mailInConsignmentVO);
								new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO, mailbag);
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * 	Method		:	HistoryBuilder.constructMailbagHistoryVO
	 *	Added by 	:	U-1467 on 20-Apr-2020
	 * 	Used for 	:	IASCB-48400
	 *	Parameters	:	@param consignmentDocumentVO
	 *	Parameters	:	@param mailInConsignmentVO
	 *	Parameters	:	@return 
	 *	Return type	: 	MailbagHistoryVO
	 */
	private MailbagHistoryVO constructMailbagHistoryVO(ConsignmentDocumentVO consignmentDocumentVO, MailInConsignmentVO mailInConsignmentVO) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_NEW);
		mailbagHistoryVO.setScanDate(consignmentDocumentVO.getConsignmentDate());
		mailbagHistoryVO.setMailSource(mailInConsignmentVO.getMailSource());
		mailbagHistoryVO.setContainerNumber(consignmentDocumentVO.getUldNumber());
		mailbagHistoryVO.setScanUser(consignmentDocumentVO.getLastUpdateUser());
		mailbagHistoryVO.setContainerType(consignmentDocumentVO.getType());
		mailbagHistoryVO.setScannedPort(consignmentDocumentVO.getAirportCode());
		mailbagHistoryVO.setMailbagId(mailInConsignmentVO.getMailId());
		mailbagHistoryVO.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		mailbagHistoryVO.setScanDate(consignmentDocumentVO.getConsignmentDate());
		mailbagHistoryVO.setPoacod(mailInConsignmentVO.getPaCode());
		mailbagHistoryVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		mailbagHistoryVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
		//Flight details
		if(consignmentDocumentVO.getRoutingInConsignmentVOs()!=null && consignmentDocumentVO.getRoutingInConsignmentVOs().size()>0){
			RoutingInConsignmentVO routingInConsignmentVO=consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next();
			mailbagHistoryVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			mailbagHistoryVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
			mailbagHistoryVO.setCarrierId(routingInConsignmentVO.getOnwardCarrierId());
			mailbagHistoryVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
			mailbagHistoryVO.setFlightSequenceNumber(routingInConsignmentVO.getOnwardCarrierSeqNum());
			mailbagHistoryVO.setSegmentSerialNumber(routingInConsignmentVO.getSegmentSerialNumber());
			mailbagHistoryVO.setPou(routingInConsignmentVO.getPou());
		}
		return mailbagHistoryVO;
	}
	
	public void flagHistoryforFlightDeparture(MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs)
			throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryforFlightDeparture");
		
			boolean isOrigin=false; 
			for (FlightValidationVO flightValidationVO : flightVOs) {
			if(flightValidationVO.getAtd()!=null && (isOrigin||mailbagVO.getScannedPort().equals(flightValidationVO.getLegOrigin()))){
				isOrigin=true;
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
			MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
			mailHistoryVO.setFomDeviationList(mailbagVO.isFromDeviationList());
			mailHistoryVO.setMailSource(mailbagVO.getMailSource());
			mailHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			mailHistoryVO.setMailStatus(MailConstantsVO.FLIGHT_DEPARTURE);
			mailHistoryVO.setCarrierCode(flightValidationVO.getCarrierCode());
			mailHistoryVO.setFlightNumber(flightValidationVO.getFlightNumber());
			mailHistoryVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			mailHistoryVO.setFlightDate(flightValidationVO.getFlightDate());
			mailHistoryVO.setContainerNumber(mailbagVO.getContainerNumber());
			mailHistoryVO.setContainerType(mailbagVO.getContainerType());
			mailHistoryVO.setScanDate(flightValidationVO.getAtd());
			if(flightValidationVO.getMvtStatus()!=null ){
				mailHistoryVO.setScanUser("SYSTEM");
			} else if(mailbagVO.getLastUpdateUser()!=null) {
				//modified as part of IASCB-60184 
				mailHistoryVO.setScanUser(mailbagVO.getLastUpdateUser());
			} else {
				mailHistoryVO.setScanUser(mailbag.getLastUpdateUser());
			}
			mailHistoryVO.setScannedPort(flightValidationVO.getLegOrigin());
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
				new Mailbag().insertMailbagHistoryDetails(mailHistoryVO, mailbag);	
			}
			
			
		}
		
		}
			}
		
		log.exiting("HistoryBuilder", "flagHistoryforFlightDeparture");
	}
	
	public void flagHistoryforFlightArrival(MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs)
			throws SystemException {
		log.entering("HistoryBuilder", "flagHistoryforFlightArrival");
			boolean isOrigin=false; 
			for (FlightValidationVO flightValidationVO : flightVOs) {
			if(flightValidationVO.getAta()!=null && (isOrigin||mailbagVO.getScannedPort().equals(flightValidationVO.getLegOrigin()))){
				isOrigin=true;
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
			MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
			mailHistoryVO.setFomDeviationList(mailbagVO.isFromDeviationList());
			mailHistoryVO.setMailSource(mailbagVO.getMailSource());
			mailHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			mailHistoryVO.setMailStatus(MailConstantsVO.FLIGHT_ARRIVAL);
			mailHistoryVO.setCarrierCode(flightValidationVO.getCarrierCode());
			mailHistoryVO.setFlightNumber(flightValidationVO.getFlightNumber());
			mailHistoryVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			mailHistoryVO.setFlightDate(flightValidationVO.getFlightDate());
			mailHistoryVO.setContainerNumber(mailbagVO.getContainerNumber());
			mailHistoryVO.setContainerType(mailbagVO.getContainerType());
			mailHistoryVO.setScanDate(flightValidationVO.getAta());
			//mailHistoryVO.setScanUser(mailbag.getLastUpdateUser());
			if(flightValidationVO.getMvtStatus()!=null ){
				mailHistoryVO.setScanUser("SYSTEM");
			} else if(mailbagVO.getLastUpdateUser()!=null) {
				//modified as part of IASCB-60184 
				mailHistoryVO.setScanUser(mailbagVO.getLastUpdateUser());
			} else {
				mailHistoryVO.setScanUser(mailbag.getLastUpdateUser());
			}
			mailHistoryVO.setScannedPort(flightValidationVO.getLegDestination());
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
				new Mailbag().insertMailbagHistoryDetails(mailHistoryVO, mailbag);
			}
		}
			}
		log.exiting("HistoryBuilder", "flagHistoryforFlightArrival");
	}
			
		
	}
	public void flagHistoryforReadyForDelivery(Collection<MailbagVO> mailbagVOs,OperationalFlightVO operationFlightVO)
			throws SystemException {
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailBagVo : mailbagVOs) {
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(createMailbagPK(
							mailBagVo.getCompanyCode(), mailBagVo));
				} catch (FinderException e) {
					mailbag = null;
				}
				if (mailbag != null) {
					MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailBagVo);
					mailbagHistoryVO.setPaBuiltFlag(mailBagVo.getPaBuiltFlag());
					mailbagHistoryVO.setMailSource(mailBagVo.getMailSource());
					mailbagHistoryVO.setMessageVersion(mailBagVo.getMessageVersion());
					mailbagHistoryVO.setMailStatus(MailConstantsVO.READY_FOR_DELIVERY);	
					mailbagHistoryVO.setFlightNumber(operationFlightVO.getFlightNumber());
					mailbagHistoryVO.setFlightDate(operationFlightVO.getFlightDate());
					mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
					mailbagHistoryVO.setPou(mailbag.getPou());															
					if (mailBagVo.getScannedDate() != null) {
						mailbagHistoryVO
								.setScanDate(mailBagVo.getScannedDate());
					}
					new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
							mailbag);
				}
			}
		}
	}
	
	public void flagHistoryforTransferRejection(MailbagVO mailbagVO){
		Mailbag mailbag = null;
	
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (Exception e) {
				e.getMessage();
			} 
		 
		
		if (mailbag != null) {
			MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
			mailbagHistoryVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());
			mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			mailbagHistoryVO.setMailStatus(MailConstantsVO.TRANSFER_REJECTED);	
			mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
			mailbagHistoryVO.setPou(mailbag.getPou());	
			LocalDate scanDate=new LocalDate(mailbag.getScannedPort(), Location.ARP, true); 
			mailbagHistoryVO
						.setScanDate(scanDate);
			
			try {
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
			} catch (SystemException e) {
				e.getMessage();
			}
		}
		
		
		
		
		
	}
	
	public void flagHistoryforTransferInitiation(Collection<MailbagVO> mailbagVOs){
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
			 if(!("MLD".equals(mailbagVO.getMailSource()))){
		Mailbag mailbag = null;
	
			try {
				mailbag = Mailbag.find(createMailbagPK(
						mailbagVO.getCompanyCode(), mailbagVO));
			} catch (Exception e) {
				e.getMessage();
			} 
		 
		
		if (mailbag != null) {
			MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
			mailbagHistoryVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());
			mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			mailbagHistoryVO.setMailStatus(MailConstantsVO.TRANSFER_INITIATED);	
			mailbagHistoryVO.setContainerNumber(mailbag.getUldNumber());
			if(mailbagVO.getPou()!=null) {
				mailbagHistoryVO.setPou(mailbagVO.getPou());
			} else {
			mailbagHistoryVO.setPou(mailbag.getPou());
			}
			LocalDate scanDate=new LocalDate(mailbag.getScannedPort(), Location.ARP, true); 
			mailbagHistoryVO
						.setScanDate(scanDate);
			
			try {
				new Mailbag().insertMailbagHistoryDetails(mailbagHistoryVO,
						mailbag);
			} catch (SystemException e) {
				e.getMessage();
			}
		}
		
		
			}}
		}
		
		
	}
}
