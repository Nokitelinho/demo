/*
 * Cardit.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPreAdviseReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Entity for a Cardit Message
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 7, 2006 A-1739 First Draft
 *   		  July 16, 2007			A-1739		EJB3 Final changes
 */
@Entity
@Table(name = "MALCDTMST")
public class Cardit {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String MODEOFTRANSPORT_ROAD="3";
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/**
	 * The Default Constructor
	 * 
	 */
	public Cardit() {

	}

	/**
	 * Interchange reference
	 */
	private String interchangeCtrlReference;

	/**
	 * Syntax id of this interchange
	 */
	private String interchangeSyntaxId;

	/**
	 * Syntax version
	 */
	private int interchangeSyntaxVer;

	/**
	 * Recipient idr
	 */
	private String recipientId;

	/**
	 * Sender id
	 */
	private String senderId;
	
	/**
	 * Sender id
	 */
	private String actualSenderId;

	/**
	 * consignment completion date
	 */
	private Calendar consignmentDate;

	/**
	 * Interchange preparation date
	 */
	private Calendar preparationDate;

	/**
	 * Consignment doc num
	 */
	private String consignmentNumber;

	/**
	 * message name
	 */
	private String messageName;

	/**
	 * mail catergory code
	 */
	private String mailCategoryCode;

	/**
	 * Msg reference num
	 */
	private String messageRefNum;

	/**
	 * message version
	 */
	private String messageVersion;

	/**
	 * message segment number
	 */
	private int messageSegmentNum;

	/**
	 * message release number
	 */
	private String messageReleaseNum;

	/**
	 * Message type identifier
	 */
	private String messageTypeId;

	/**
	 * testIndicator
	 */
	private int tstIndicator;

	/**
	 * Controlling agency
	 */
	private String controlAgency;

	/**
	 * Interchange control count
	 */
	private int interchangeControlCnt;

	/**
	 * cardit
	 */
	private Calendar carditReceivedDate;

	/**
	 * last update time
	 */
	private Calendar lastUpdateTime;

	/**
	 * last update user
	 */
	private String lastUpdateUser;

	private Calendar utcReceivedTime;
	
	/**
	 * PK class
	 */
	private CarditPK carditPK;
	
	/**
	 * The sequenceNum in the MSGMSGMST table
	 */
	private long messageSequenceNum;
	
	/**
	 * The STNCOD in MSGMSGMST table for finding this msg again
	 */
	private String stationCode;
	
	/**
	 * The CDTTYP : CarditType (Message Function) 
	 */
	private String carditType;

	private Set<CarditTransportation> transportInformation;

	private Set<CarditReceptacle> receptacleInformation;

	private Set<CarditContainer> containerInformation;

	private Set<CarditTotal> totalsInformation;
	
	private Set<CarditReference> carditReferenceInformation;
	
	private Set<CarditHandover> carditHandoverInformation;

	/**
	 * 
	 * @param carditVO
	 * @throws SystemException
	 */
	public Cardit(CarditVO carditVO) throws SystemException {
		log.log(Log.FINE,"Persisting Cardit***");	
		populatePk(carditVO);
		populateAttributes(carditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception);
		}
		populateChildren(carditVO);
		log.log(Log.FINE,"Cardit Persisted---");	
				
	}

	/**
	 * Populates the primaryKeyValues
	 * 
	 * @param carditVO
	 */
	private void populatePk(CarditVO carditVO) {
		carditPK = new CarditPK();
		carditPK.setCompanyCode(   carditVO.getCompanyCode());
		carditPK.setCarditKey(   carditVO.getCarditKey());
	}

	/**
	 * populates the attributes
	 * 
	 * @param carditVO
	 * @throws SystemException 
	 */
	private void populateAttributes(CarditVO carditVO) throws SystemException {
		LogonAttributes logonVO = 
			ContextUtils.getSecurityContext().getLogonAttributesVO();
		carditVO.setLastUpdateUser(logonVO.getUserId().toUpperCase());
		
		setCarditReceivedDate(carditVO.getCarditReceivedDate().toCalendar());
		setUtcReceivedTime(
				carditVO.getCarditReceivedDate().toGMTDate().toCalendar());
		if(carditVO.getConsignmentDate() != null) {
			setConsignmentDate(
					carditVO.getConsignmentDate().toCalendar());
		}
		setConsignmentNumber(carditVO.getConsignmentNumber());
		setControlAgency(carditVO.getControlAgency());
		setInterchangeCtrlReference(carditVO.getInterchangeCtrlReference());
		setInterchangeSyntaxId(carditVO.getInterchangeSyntaxId());
		setInterchangeSyntaxVer(carditVO.getInterchangeSyntaxVer());
		setInterchangeControlCnt(carditVO.getInterchangeControlCnt());
		setMailCategoryCode(carditVO.getMailCategoryCode());
		setMessageName(carditVO.getMessageName());
		setMessageRefNum(carditVO.getMessageRefNum());
		setMessageReleaseNum(carditVO.getMessageReleaseNum());
		setMessageSegmentNum(carditVO.getMessageSegmentNum());
		setMessageTypeId(carditVO.getMessageTypeId());
		setMessageVersion(carditVO.getMessageVersion());
		if(carditVO.getPreparationDate() != null) {
			setPreparationDate(carditVO.getPreparationDate().toCalendar());
		}
		setRecipientId(carditVO.getRecipientId());
		setSenderId(carditVO.getSenderId());
		setActualSenderId(carditVO.getActualSenderId());
		setMessageSequenceNum(carditVO.getMsgSeqNum());
		setStationCode(carditVO.getStationCode());
		setLastUpdateUser(carditVO.getLastUpdateUser());
		/*
		 * Added For M39
		 */
		if(carditVO.getCarditType() != null && carditVO.getCarditType().length() > 0) {
			setCarditType(carditVO.getCarditType());
		}else {
			setCarditType(MailConstantsVO.FLAG_NO);
		}
	}

	/**
	 * @author A-1739
	 * @param carditVO
	 * @throws SystemException
	 */
	private void populateChildren(CarditVO carditVO) throws SystemException {
		
		/*
		 * TRANSPORT INFORMAITON
		 */
		Collection<CarditTransportationVO> transportInformationVOs = carditVO
		.getTransportInformation();
		if(transportInformationVOs != null && transportInformationVOs.size() > 0) { 
			removeChildCarditTransportation();
			for (CarditTransportationVO transportInformationVO : transportInformationVOs) {
//				if(!CarditTransportation.checkCarditTransportExists(carditPK.getCompanyCode(), carditPK.getCarditKey(), transportInformationVO)){
				log.log(Log.FINE,"Cardit Transportation  Not Found  So Persisting");
				if(  !(transportInformationVO.getFlightNumber()==null && MODEOFTRANSPORT_ROAD.equalsIgnoreCase(transportInformationVO.getModeOfTransport()) ) ){//IASCB-46802
				populateTransportation(transportInformationVO);	
				}
//				}
			}
		}
		 
		/*
		 * RECEPTACLE INFORMAITON
		 */
		Collection<CarditReceptacleVO> receptacleInformationVOs = carditVO
		.getReceptacleInformation();
		Collection<CarditReceptacleVO> receptacleInformationToRemoveVOs= new ArrayList<CarditReceptacleVO>();
		boolean isDuplicate = false;
		if(receptacleInformationVOs != null && receptacleInformationVOs.size() > 0) {
			for (CarditReceptacleVO carditReceptacleVO : receptacleInformationVOs) {

//				String carditKey = findCarditForMailbag(carditPK.getCompanyCode(), 
//				carditReceptacleVO.getReceptacleId());
//				if(carditKey == null || (carditKey != null && carditKey.length()==0)) {
//				new CarditReceptacle(carditPK, carditReceptacleVO);
//				} else if(carditKey != null && carditKey.length() > 0){
//				//cardit already present for mailbag
//				log.log(Log.INFO, "cardit already present..skipping save for " + 
//				carditReceptacleVO.getReceptacleId());
//				}
			int found=0;
			   
			String oldCarditKey="";
			String carditKey = findCarditReceptacle(carditReceptacleVO.getReceptacleId());
			if(carditKey != null){
			String[] keys = carditKey.split("~");
			oldCarditKey = keys[0];
			 found = Integer.parseInt(keys[1]); 
			}
			    //Added by A-7540 for ICRD-330056 starts
			    String orgPaCod = null;
	             Mailbag mailbag = null;
	     		MailbagPK mailbagPk = new MailbagPK();
	     		mailbagPk.setCompanyCode(carditVO.getCompanyCode());
	     		long mailSeqnum = Mailbag.findMailSequenceNumber(carditReceptacleVO.getReceptacleId(), carditVO.getCompanyCode());
	     		mailbagPk.setMailSequenceNumber(mailSeqnum);     		
	     		try {
	     			mailbag = Mailbag.find(mailbagPk);
	     		} catch (FinderException e) {
	     			mailbag = null;
	     		}
	     		if (mailbag != null) {
	             MailbagVO mailbagVO = mailbag.retrieveVO();
	             String OOE = mailbagVO.getOoe();
	             PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
	             try {
	                 orgPaCod = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(), OOE);
					} catch (SystemException e2) {
						e2.getMessage();
					} 

					try {
						postalAdministrationVO = new MailController().findPACode(mailbagVO.getCompanyCode(), orgPaCod);
					}  catch (SystemException e) {
						e.getMessage();
					}
				  try {
					isDuplicate = checkForDuplicateMailbag(mailbagVO.getCompanyCode(),postalAdministrationVO.getPaCode(), mailbag);
				} catch (DuplicateMailBagsException e) {
					e.getMessage();
				  }
				    if(!isDuplicate){
				    	   Integer consignmentSeqNumber = 1;
	     			      mailbag.setConsignmentNumber(carditVO.getConsignmentNumber());
	     			      mailbag.setConsignmentSequenceNumber(consignmentSeqNumber.intValue());
	     			     mailbag.setDespatchDate(carditVO.getConsignmentDate());
	     			    /* if(carditVO.getTransportInformation()!=null){
	     			        for(CarditTransportationVO carditvo : carditVO.getTransportInformation()){
	     			    	  mailbag.setFlightNumber(carditvo.getFlightNumber());
	     			    	  mailbag.setFlightSequenceNumber(carditvo.getFlightSequenceNumber());
	     			        }
	     			      }*/	     			     
	     			      //if(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())){	     			 
	     			    		mailbag.setOrginOfficeOfExchange(carditReceptacleVO.getOriginExchangeOffice());
	     			    		mailbag.setDestinationOfficeOfExchange(carditReceptacleVO.getDestinationExchangeOffice());
	     			    		if(carditReceptacleVO.getMailOrigin()!=null && carditReceptacleVO.getMailOrigin().length() <=3) {
	     			    		mailbag.setOrigin(carditReceptacleVO.getMailOrigin());	
	     			    		}
	     			    		if(carditReceptacleVO.getMailDestination()!=null && carditReceptacleVO.getMailDestination().length() <=3) {
	     			    		mailbag.setDestination(carditReceptacleVO.getMailDestination());     			     
	     			    		}
	     			    //  }
	     			    		
				    }
				    else{
				    	 found=0;
				    }
	     		}
	     		//Added by A-7540 for ICRD-330056 ends
			if(found == 0 || carditVO.isSenderIdChanged()) {//ICRD-321917
					log.log(Log.FINE,"Cardit Receptacle Not Found  So Persisting ");
				log.log(Log.INFO, "Persisting cardit Receptacle------"+carditReceptacleVO.getReceptacleId());
					populateReceptacleDetails(carditReceptacleVO);
			}else{
				receptacleInformationToRemoveVOs.add(carditReceptacleVO);
				CarditReceptacle carditRcp = new CarditReceptacle();
				if(oldCarditKey != "" || oldCarditKey != null){
				CarditReceptaclePK	carditRcpPK = new CarditReceptaclePK();;
				carditRcpPK.setCarditKey(oldCarditKey);
				carditRcpPK.setCompanyCode(carditVO.getCompanyCode());
				carditRcpPK.setReceptacleId(carditReceptacleVO.getReceptacleId());
				try {
					carditRcp = CarditReceptacle.find(carditRcpPK);
				} catch (FinderException e) {
					e.getMessage();
				}
				if(carditRcp != null){
				carditRcp.remove();
				carditReceptacleVO.setCarditKey(carditVO.getCarditKey());
				populateReceptacleDetails(carditReceptacleVO);
				}
			}
				
			}
				}
		   log.log(Log.FINE,"\nRemoving Duplicate Receptacles from cardit VO......\n");
			if(receptacleInformationToRemoveVOs!=null && receptacleInformationToRemoveVOs.size()>0
			     && isDuplicate){
				receptacleInformationVOs.removeAll(receptacleInformationToRemoveVOs);
			}
		}
		carditVO.setReceptacleInformation(receptacleInformationVOs);
		log.log(Log.FINE,"\n Cardit VO after duplicate validation......\n");
		
		/*
		 * CONTAINER INFORMAITON
		 */
		Collection<CarditContainerVO> containerInformationVOs = carditVO.getContainerInformation();
		// since Container information is optional do null check
		if (containerInformationVOs != null	&& containerInformationVOs.size() > 0) {
			for (CarditContainerVO carditContainerVO : containerInformationVOs) {
				
				log.log(Log.FINE, "the Cardit Container Vo", carditContainerVO);
				CarditContainer carditCont = findCarditContainer(carditContainerVO.getContainerNumber());
				if ((carditCont == null) && (carditContainerVO.getContainerNumber() != null)
						&& (carditContainerVO.getContainerNumber().trim().length() > 0)) {	
					log.log(Log.FINE,"Cardit Container Not Found  So Persisting");
					populateContainerDetails(carditContainerVO);
				}
			}
		}
		/*
		 * TOTAL INFORMATION
		 */
		Collection<CarditTotalVO> totalsInformationVOs = carditVO.getTotalsInformation();
		if(totalsInformationVOs != null && totalsInformationVOs.size() > 0) {
			removeChildCarditTotal();
			for (CarditTotalVO carditTotalVO : totalsInformationVOs) {
				log.log(Log.FINE,"Cardit Total Persisting");
//				
//				if(!canPersistCarditTotal(carditPK.getCompanyCode(), carditPK.getCarditKey(), carditTotalVO.getMailClassCode())){
//					log.log(Log.FINE,"Cardit Total  Not Found  So Persisting");
//					new CarditTotal(carditPK, carditTotalVO);
//				}
				 
				populateTotalDetails(carditTotalVO);
			}
		}
		/*
		 * HANDOVER INFORMATION
		 */
		Collection<CarditHandoverInformationVO> carditHandoverInformationVOs = carditVO.getHandoverInformation();
		if(carditHandoverInformationVOs != null && carditHandoverInformationVOs.size() > 0) {
			removeChildCarditHandover();
			for (CarditHandoverInformationVO carditHandoverInfoVO : carditHandoverInformationVOs) {
				log.log(Log.FINE,"Cardit Handover Persisting");
				populateHandoverDetails(carditHandoverInfoVO);
			}
		}
		/*
		 * REFERENCE INFORMATION
		 */
		Collection<CarditReferenceInformationVO> carditReferenceInformationVOs = carditVO.getReferenceInformation();
		if(carditReferenceInformationVOs != null && carditReferenceInformationVOs.size() > 0) {
			removeChildCarditReference();
			for (CarditReferenceInformationVO carditReferenceInfoVO : carditReferenceInformationVOs) {
				log.log(Log.FINE,"Cardit Reference Persisting");
				populateReferenceDetails(carditReferenceInfoVO);
			}
		}	
	}
	
	
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditTransportation() throws SystemException {
		Collection<CarditTransportation> carditTransportationInfo =
			getTransportInformation();
		if(carditTransportationInfo != null && carditTransportationInfo.size() > 0) {
			log.log(Log.FINE,"Cardit Transport Child Removing");
			do {
				CarditTransportation carditTransport = null;
				for(CarditTransportation carditTransportInfo : carditTransportationInfo) {
					carditTransport = carditTransportInfo;
					break;
				}
				carditTransportationInfo.remove(carditTransport);
				carditTransport.remove();
				log.log(Log.FINE,"Cardit Transport Child Removed");

			}while(carditTransportationInfo.iterator().hasNext());
		}
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditTotal() throws SystemException {
		Collection<CarditTotal> totalsInfo =
			getTotalsInformation();
		if(totalsInfo != null && totalsInfo.size() > 0) {
			log.log(Log.FINE,"Cardit Total Child Removing");
			do {
				CarditTotal carditTotl = null;
				for(CarditTotal carditTot : totalsInfo) {
					carditTotl = carditTot;
					break;
				}
				totalsInfo.remove(carditTotl);
				carditTotl.remove();
				log.log(Log.FINE,"Cardit Total Child Removed");
			}while(totalsInfo.iterator().hasNext());
		}
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditHandover() throws SystemException {
		Collection<CarditHandover> carditHandoverInfo =
			getCarditHandoverInformation();
		if(carditHandoverInfo != null && carditHandoverInfo.size() > 0) {
			log.log(Log.FINE,"Cardit Handover Child Removing");
			do {
				CarditHandover carditHndovr = null;
				for(CarditHandover carditHandovr : carditHandoverInfo) {
					carditHndovr = carditHandovr;
					break;
				}
				carditHandoverInfo.remove(carditHndovr);
				carditHndovr.remove();
				log.log(Log.FINE,"Cardit Handover Child Removed");
			}while(carditHandoverInfo.iterator().hasNext());
		}
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditReference() throws SystemException {
		Collection<CarditReference> carditReferenceInfo =
			getCarditReferenceInformation();
		if(carditReferenceInfo != null && carditReferenceInfo.size() > 0) {
			log.log(Log.FINE,"Cardit Reference Child Removing");
			do {
				CarditReference carditRefer = null;
				for(CarditReference carditRef : carditReferenceInfo) {
					carditRefer = carditRef;
					break;
				}
				carditReferenceInfo.remove(carditRefer);
				carditRefer.remove();
				log.log(Log.FINE,"Cardit Reference Child Removed");
			}while(carditReferenceInfo.iterator().hasNext());
		}
	}
	/**
     *@author A-3227
     * @param transportInformationVO
     * @throws SystemException
     */
   private void populateTransportation(CarditTransportationVO transportInformationVO)
   throws SystemException {
	   carditPK = getCarditPK();
	   CarditTransportationPK carditTransportPK = new CarditTransportationPK();
	   carditTransportPK.setCompanyCode(carditPK.getCompanyCode());
	   carditTransportPK.setCarditKey(carditPK.getCarditKey());	   
	   if(transportInformationVO != null) {
	   carditTransportPK.setArrivalPort(transportInformationVO.getArrivalPort());
		   if(transportInformation == null )  {
			   transportInformation = new HashSet<CarditTransportation>();
		   }
		   transportInformation.add(
				   new CarditTransportation(carditTransportPK, transportInformationVO));      
	   }
   }
	/**
    *@author A-3227
    * @param carditReceptacleVO
    * @throws SystemException
    */ 
  private void populateReceptacleDetails(CarditReceptacleVO carditReceptacleVO)
  throws SystemException {
	   carditPK = getCarditPK();
	   if(carditReceptacleVO != null) {
		   if(receptacleInformation == null )  {
			   receptacleInformation = new HashSet<CarditReceptacle>();
		   }
 		   
 		   try {
 			   CarditReceptacle.find (new CarditReceptacle ().populatePK (carditPK, carditReceptacleVO));
 		   } catch (FinderException e) {
		   receptacleInformation.add(
				   new CarditReceptacle(carditPK, carditReceptacleVO));      
	   }
 		   
 	   }
   }  
 
  /**
   * 
   * 	Method		:	Cardit.populateCarditDOMReceptacleDetails
   *	Added by 	:	A-8061 on 07-Aug-2020
   * 	Used for 	:
   *	Parameters	:	@param carditReceptacleVO
   *	Parameters	:	@throws SystemException 
   *	Return type	: 	void
   */
  private void populateCarditDOMReceptacleDetails(CarditReceptacleVO carditReceptacleVO)
  throws SystemException {
	   carditPK = getCarditPK();
	   if(carditReceptacleVO != null) {
		   if(receptacleInformation == null )  {
			   receptacleInformation = new HashSet<CarditReceptacle>();
		   }
 		   try {
 			 CarditReceptacle carditReceptacle=   CarditReceptacle.find (new CarditReceptacle ().populatePK (carditPK, carditReceptacleVO));
 			 carditReceptacle.setCarditType(carditReceptacleVO.getCarditType()); 
 		   } catch (FinderException e) {
 			  log.log(Log.SEVERE,"exception raised", e  );
		   receptacleInformation.add(
				   new CarditReceptacle(carditPK, carditReceptacleVO));      
	   }
 		   
 	   }
   }  
  
 
	/**
     *@author A-3227
     * @param carditContainerVO
     * @throws SystemException
     */
   private void populateContainerDetails(CarditContainerVO carditContainerVO)
   throws SystemException {
	   carditPK = getCarditPK();
	   if(carditContainerVO != null) {
		   if(containerInformation == null )  {
			   containerInformation = new HashSet<CarditContainer>();
		   }
		   containerInformation.add(
				   new CarditContainer(carditPK, carditContainerVO));      
	   }
   }
	/**
     *@author A-3227
     * @param carditTotalVO
     * @throws SystemException
     */
   private void populateTotalDetails(CarditTotalVO carditTotalVO)
   throws SystemException {
	   carditPK = getCarditPK();
	   if(carditTotalVO != null) {
		   if(totalsInformation == null )  {
			   totalsInformation = new HashSet<CarditTotal>();
		   }
		   totalsInformation.add(
				   new CarditTotal(carditPK, carditTotalVO));      
	   }
   }
    /**
     *@author A-3227
     * @param carditHandoverInfoVO
     * @throws SystemException
     */
   private void populateHandoverDetails(CarditHandoverInformationVO carditHandoverInfoVO)
   throws SystemException {
	   carditPK = getCarditPK();
	   CarditHandoverPK carditHndovrPK = new CarditHandoverPK();
	   carditHndovrPK.setCarditKey(carditPK.getCarditKey());
	   carditHndovrPK.setCompanyCode(carditPK.getCompanyCode());
	   if(carditHandoverInfoVO != null) {
		   if(carditHandoverInformation == null )  {
			   carditHandoverInformation = new HashSet<CarditHandover>();
		   }
		   carditHandoverInformation.add(
				   new CarditHandover(carditHndovrPK, carditHandoverInfoVO));      
	   }
   }
    /**
     *@author A-3227
     * @param carditReferenceInfoVO
     * @throws SystemException
     */
   private void populateReferenceDetails(CarditReferenceInformationVO carditReferenceInfoVO)
   throws SystemException {
	   carditPK = getCarditPK();
	   CarditReferencePK carditReferPK = new CarditReferencePK();
	   carditReferPK.setCarditKey(carditPK.getCarditKey());
	   carditReferPK.setCompanyCode(carditPK.getCompanyCode());
	   if(carditReferenceInfoVO != null) {
		   if(carditReferenceInformation == null )  {
			   carditReferenceInformation = new HashSet<CarditReference>();
		   }
		   carditReferenceInformation.add(
				   new CarditReference(carditReferPK, carditReferenceInfoVO));      
	   }
   }
   
	/**
	 * @return Returns the carditReceivedDate.
	 */
	@Column(name = "CDTRCVDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCarditReceivedDate() {
		return carditReceivedDate;
	}

	/**
	 * @param carditReceivedDate
	 *            The carditReceivedDate to set.
	 */
	public void setCarditReceivedDate(Calendar carditReceivedDate) {
		this.carditReceivedDate = carditReceivedDate;
	}

	/**
	 * @return Returns the consignmentDate.
	 */
	@Column(name = "CSGCMPDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate
	 *            The consignmentDate to set.
	 */
	public void setConsignmentDate(Calendar consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return Returns the consignmentNumber.
	 */
	@Column(name = "CSGDOCNUM")
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the controlAgency.
	 */
	@Column(name = "CTLAGY")
	public String getControlAgency() {
		return controlAgency;
	}

	/**
	 * @param controlAgency
	 *            The controlAgency to set.
	 */
	public void setControlAgency(String controlAgency) {
		this.controlAgency = controlAgency;
	}

	/**
	 * @return Returns the interchangeControlCnt.
	 */
	@Column(name = "INTCTLNUM")
	public int getInterchangeControlCnt() {
		return interchangeControlCnt;
	}

	/**
	 * @param interchangeControlCnt
	 *            The interchangeControlCnt to set.
	 */
	public void setInterchangeControlCnt(int interchangeControlCnt) {
		this.interchangeControlCnt = interchangeControlCnt;
	}

	/**
	 * @return Returns the interchangeCtrlReference.
	 */
	@Column(name = "INTCTLREF")
	public String getInterchangeCtrlReference() {
		return interchangeCtrlReference;
	}

	/**
	 * @param interchangeCtrlReference
	 *            The interchangeCtrlReference to set.
	 */
	public void setInterchangeCtrlReference(String interchangeCtrlReference) {
		this.interchangeCtrlReference = interchangeCtrlReference;
	}

	/**
	 * @return Returns the interchangeSyntaxId.
	 */
	@Column(name = "STXIDR")
	public String getInterchangeSyntaxId() {
		return interchangeSyntaxId;
	}

	/**
	 * @param interchangeSyntaxId
	 *            The interchangeSyntaxId to set.
	 */
	public void setInterchangeSyntaxId(String interchangeSyntaxId) {
		this.interchangeSyntaxId = interchangeSyntaxId;
	}

	/**
	 * @return Returns the interchangeSyntaxVer.
	 */
	@Column(name = "STXVER")
	public int getInterchangeSyntaxVer() {
		return interchangeSyntaxVer;
	}

	/**
	 * @param interchangeSyntaxVer
	 *            The interchangeSyntaxVer to set.
	 */
	public void setInterchangeSyntaxVer(int interchangeSyntaxVer) {
		this.interchangeSyntaxVer = interchangeSyntaxVer;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	@Column(name = "MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the messageName.
	 */
	@Column(name = "MSGNAM")
	public String getMessageName() {
		return messageName;
	}

	/**
	 * @param messageName
	 *            The messageName to set.
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	/**
	 * @return Returns the messageRefNum.
	 */
	@Column(name = "MSGREFNUM")
	public String getMessageRefNum() {
		return messageRefNum;
	}

	/**
	 * @param messageRefNum
	 *            The messageRefNum to set.
	 */
	public void setMessageRefNum(String messageRefNum) {
		this.messageRefNum = messageRefNum;
	}

	/**
	 * @return Returns the messageReleaseNum.
	 */
	@Column(name = "MSGRLSNUM")
	public String getMessageReleaseNum() {
		return messageReleaseNum;
	}

	/**
	 * @param messageReleaseNum
	 *            The messageReleaseNum to set.
	 */
	public void setMessageReleaseNum(String messageReleaseNum) {
		this.messageReleaseNum = messageReleaseNum;
	}

	/**
	 * @return Returns the messageSegmentNum.
	 */
	@Column(name = "MSGSEGNUM")
	public int getMessageSegmentNum() {
		return messageSegmentNum;
	}

	/**
	 * @param messageSegmentNum
	 *            The messageSegmentNum to set.
	 */
	public void setMessageSegmentNum(int messageSegmentNum) {
		this.messageSegmentNum = messageSegmentNum;
	}

	/**
	 * @return Returns the messageTypeId.
	 */
	@Column(name = "MSGTYPIDR")
	public String getMessageTypeId() {
		return messageTypeId;
	}

	/**
	 * @param messageTypeId
	 *            The messageTypeId to set.
	 */
	public void setMessageTypeId(String messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	/**
	 * @return Returns the messageVersion.
	 */
	@Column(name = "MSGVERNUM")
	public String getMessageVersion() {
		return messageVersion;
	}

	/**
	 * @param messageVersion
	 *            The messageVersion to set.
	 */
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	/**
	 * @return Returns the preparationDate.
	 */
	@Column(name = "PRPDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPreparationDate() {
		return preparationDate;
	}

	/**
	 * @param preparationDate
	 *            The preparationDate to set.
	 */
	public void setPreparationDate(Calendar preparationDate) {
		this.preparationDate = preparationDate;
	}

	/**
	 * @return Returns the recipientId.
	 */
	@Column(name = "RCTIDR")
	public String getRecipientId() {
		return recipientId;
	}

	/**
	 * @param recipientId
	 *            The recipientId to set.
	 */
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	/**
	 * @return Returns the senderId.
	 */
	@Column(name = "SDRIDR")
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId
	 *            The senderId to set.
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return Returns the tstIndicator.
	 */
	@Column(name = "TSTIND")
	public int getTstIndicator() {
		return tstIndicator;
	}

	/**
	 * @param tstIndicator
	 *            The tstIndicator to set.
	 */
	public void setTstIndicator(int tstIndicator) {
		this.tstIndicator = tstIndicator;
	}

	/**
	 * @return Returns the carditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "carditKey", column = @Column(name = "CDTKEY"))})
	public CarditPK getCarditPK() {
		return carditPK;
	}

	/**
	 * @param carditPK
	 *            The carditPK to set.
	 */
	public void setCarditPK(CarditPK carditPK) {
		this.carditPK = carditPK;
	}

	/**
	 * @return Returns the containerInformation.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	public Set<CarditContainer> getContainerInformation() {
		return containerInformation;
	}

	/**
	 * @param containerInformation
	 *            The containerInformation to set.
	 */
	public void setContainerInformation(
			Set<CarditContainer> containerInformation) {
		this.containerInformation = containerInformation;
	}

	/**
	 * @return Returns the receptacleInformation.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	public Set<CarditReceptacle> getReceptacleInformation() {
		return receptacleInformation;
	}

	/**
	 * @param receptacleInformation
	 *            The receptacleInformation to set.
	 */
	public void setReceptacleInformation(
			Set<CarditReceptacle> receptacleInformation) {
		this.receptacleInformation = receptacleInformation;
	}

	/**
	 * @return Returns the totalsInformation.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	public Set<CarditTotal> getTotalsInformation() {
		return totalsInformation;
	}

	/**
	 * @param totalsInformation
	 *            The totalsInformation to set.
	 */
	public void setTotalsInformation(Set<CarditTotal> totalsInformation) {
		this.totalsInformation = totalsInformation;
	}

	/**
	 * @return Returns the transportInformation.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	public Set<CarditTransportation> getTransportInformation() {
		return transportInformation;
	}

	/**
	 * @param transportInformation
	 *            The transportInformation to set.
	 */
	public void setTransportInformation(
			Set<CarditTransportation> transportInformation) {
		this.transportInformation = transportInformation;
	}

	/**
	 * @author A-1739
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * 
	 */
	public static String findCarditForMailbag(String companyCode,
			String mailbagId) throws SystemException { 
		try{
			return constructDAO().findCarditForMailbag(companyCode, mailbagId);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}
	}	
	/**
	 * @author A-1739
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("Query DAO not found", exception);
		}
	}

	/**
	 * @author A-1739
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * 
	 * 
	 */
	public static boolean checkIsFlightSameForReceptacle(MailbagVO mailbagVO)
			throws SystemException {
		try{
		return constructDAO().isFlightSameForReceptacle(mailbagVO);
	}catch(PersistenceException ex){
		throw new SystemException(ex.getErrorCode(),ex);
	}
	}

	/**
	 * @author A-2037 This method is used to find Preadvice for outbound mail
	 *         and it gives the details of the ULDs and the receptacles based on
	 *         CARDIT
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static PreAdviceVO findPreAdvice(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		
		
		Cardit cardit = new Cardit();
		//Commented by A-5201 for ICRD-95502
		 /*Collection<PreAdviceDetailsVO> preAdviceDetailsVOForULD = cardit
				.findULDInCARDIT(operationalFlightVO);*/
		 Collection<PreAdviceDetailsVO> preAdviceDetailsVOForMailbag = cardit
				.findMailbagsInCARDIT(operationalFlightVO);
		 Collection<PreAdviceDetailsVO> preAdviceDetailsVOs = new ArrayList<PreAdviceDetailsVO>();
		 //preAdviceDetailsVOs.addAll(preAdviceDetailsVOForULD);
		 preAdviceDetailsVOs.addAll(preAdviceDetailsVOForMailbag);
		 PreAdviceVO preAdviceVO = new PreAdviceVO();
		 preAdviceVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		 preAdviceVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		 preAdviceVO.setCarrierId(operationalFlightVO.getCarrierId());
		 preAdviceVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		 preAdviceVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		 preAdviceVO.setFlightDate(operationalFlightVO.getFlightDate());
		 preAdviceVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		 /*
		  *  Added By Karthick V as the part of the NCA Mail Tracking Cr to include  the 
		  *  total No of Bags and the Total Weight ...
		  * 
		  */
		 if(preAdviceDetailsVOs!= null && preAdviceDetailsVOs.size()>0){
			 for(PreAdviceDetailsVO details:preAdviceDetailsVOs ){
				 if((details.getUldNumbr() == null) || 
						 (details.getUldNumbr() != null && details.getUldNumbr().trim().length() > 0)) {
					 preAdviceVO.setTotalBags(preAdviceVO.getTotalBags()+details.getTotalbags());
					 //preAdviceVO.setTotalWeight(preAdviceVO.getTotalWeight()+details.getTotalWeight());
					 
					 try {
						preAdviceVO.setTotalWeight(Measure.addMeasureValues(preAdviceVO.getTotalWeight(), details.getTotalWeight()));//added by A-7371
					} catch (UnitException e) {
						throw new SystemException(e.getErrorCode());
					}
				 }
			 }
		 }
		 preAdviceVO.setPreAdviceDetails(preAdviceDetailsVOs);
		 return preAdviceVO;
	}

	/**
	 * @author A-1936
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<PreAdviceDetailsVO> findULDInCARDIT(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findULDInCARDIT(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1739
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findMailbagsInCARDIT(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1936
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkCarditPresentForUld(String companyCode,
			String uldNumber) throws SystemException {
		try {
			return constructDAO().checkCarditPresentForUld(companyCode,
					uldNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * @author A-1936
	 * @param containerDetailsVO
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkIsFlightSameForUld(
			ContainerDetailsVO containerDetailsVO) throws SystemException {
		try {
			return constructDAO().isFlightSameForUld(containerDetailsVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * Finds an existing cardit
	 * Sep 8, 2006, a-1739
	 * @param carditPK
	 * @return Cardit
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static Cardit find(CarditPK carditPK)throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(Cardit.class, carditPK);
	}

	/**
	 * finds the carditdetails
	 * Sep 11, 2006, a-1739
	 * @param companyCode TODO
	 * @param consignmentId
	 * @return the CarditVO
	 * @throws SystemException 
	 */
	public static CarditVO findCarditDetailsForResdit(
			String companyCode, String consignmentId) 
	throws SystemException {
		try {
			return constructDAO().findCarditDetailsForResdit(
					companyCode, consignmentId);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}	
	}

	/**
	 * Returns the UTC time of saving
	 * Sep 14, 2006, a-1739
	 * @return time in UTC
	 */
	@Column(name="CDTDATUTC")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUtcReceivedTime() {
		return utcReceivedTime;
	}


	/**
	 * Sets the UTC time of saving
	 * Sep 14, 2006, a-1739
	 * @param utcSaveTime
	 */
	public void setUtcReceivedTime(Calendar utcSaveTime) {
		this.utcReceivedTime = utcSaveTime;
	}

	/**
	 * TODO Purpose
	 * Sep 15, 2006, a-1739
	 * @param companyCode
	 * @param consignmentNumber
	 * @return
	 * @throws SystemException 
	 */
	public static int findCarditContainerCount(
			String companyCode, String consignmentNumber) throws SystemException {
		try {
			return constructDAO().findCarditContainerCount(
					companyCode, consignmentNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * TODO Purpose
	 * Sep 15, 2006, a-1739
	 * @param companyCode
	 * @param consignmentNumber
	 * @return
	 * @throws SystemException 
	 */
	public static int findCarditReceptacleCount(String companyCode, 
			String consignmentNumber) throws SystemException {
		try {
			return constructDAO().findCarditReceptacleCount(
					companyCode, consignmentNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * TODO Purpose
	 * Jan 24, 2007, A-1739
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException 
	 */
	public static CarditEnquiryVO findCarditDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
		String srchMod = carditEnquiryFilterVO.getSearchMode();
		CarditEnquiryVO carditEnquiryVO = null;
		try {
			if(MailConstantsVO.CARDITENQ_MODE_MAL.equals(srchMod)) {
				carditEnquiryVO = 
					constructDAO().findCarditMailDetails(carditEnquiryFilterVO);
			} else if(MailConstantsVO.CARDITENQ_MODE_DOC.equals(srchMod)) {
				carditEnquiryVO = 
					constructDAO().findCarditDocumentDetails(carditEnquiryFilterVO);
			} else if(MailConstantsVO.CARDITENQ_MODE_DESP.equals(srchMod)) {
				carditEnquiryVO = 
					constructDAO().findCarditDespatchDetails(carditEnquiryFilterVO);
			}
		} catch(PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		if(carditEnquiryVO != null) {
			carditEnquiryVO.setSearchMode(carditEnquiryFilterVO.getSearchMode());
		}
		return carditEnquiryVO;
	}

	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param companyCode
	 * @param carditKey
	 * @return
	 * @throws SystemException 
	 */
	public static Collection<CarditTransportationVO> findCarditTransportationDetails(
			String companyCode, String carditKey) throws SystemException {
		try {
			return constructDAO().findCarditTransportationDetails(
					companyCode, carditKey);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	} 
	 
	/**
	  * @author A-1936
	  * This method is used to find the Cardit Details for the MailBags
	  * namely CarditSequenceNumber,ConsignmentNumber,CarditKey.
	  * ADDED AS THE PART OF NCA-CR
	  * @param companyCode
	  * @param mailID
	  * @return
	  */
	public static  MailbagVO  findCarditDetailsForAllMailBags(String companyCode,
			long mailID)throws SystemException{
		try {
			 return constructDAO().findCarditDetailsForAllMailBags(companyCode,mailID);
		 }catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		 }
	}
	
    //TODO
	//The Implementation of this method has to be done..
	public  void sendCarditForTransfer(){
		
	}

	/**
	 * @return Returns the messageSequenceNum.
	 */
	@Column(name="CDTSEQNUM")	
	public long getMessageSequenceNum() {
		return messageSequenceNum;
	}

	/**
	 * @param messageSequenceNum The messageSequenceNum to set.
	 */
	public void setMessageSequenceNum(long messageSequenceNum) {
		this.messageSequenceNum = messageSequenceNum;
	}

	/**
	 * @return Returns the stationCode.
	 */
	@Column(name="STNCOD")
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	
	/**
	 * TODO Purpose
	 * Apr 11, 2007, a-1739
	 * @param carditVO
	 * @throws SystemException 
	 */
	public void update(CarditVO carditVO) throws SystemException {
		log.entering("Cardit", "update");
		populateChildren(carditVO);
		log.exiting("Cardit", "update");
		
	}
	
	/**
	 * @author A-8061
	 *  @throws SystemException 
	 */
	public void remove()throws SystemException{
		
		removeChildCarditTransportation();
		removeReceptacleDetailsFromCardit(this.receptacleInformation);
		removeChildCarditTotal();
		removeChildCarditHandover();
		removeChildCarditReference();
		
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

	}
	
	
	
	/**
	 * @author a-1936
	 * This method is used to chekc wether that Cardit Container is already present
	 * @param companyCode
	 * @param carditKey
	 * @param containerNumber
	 * @return
	 * @throws SystemException
	 */
	private CarditContainer findCarditContainer(String containerNumber)
	throws SystemException{
		log.entering("Cardit", "findCarditContainer");
		CarditContainer carditContainer = null;
		try{
			carditContainer = CarditContainer.find(carditPK.getCompanyCode(),carditPK.getCarditKey(), containerNumber);
		}catch(FinderException ex){
			log.log(Log.INFO,"FINDER EXCEPTION CAUGHT SO CAN CREATE CARDIT CONTAINER");
		}
		return  carditContainer;
	}
	
	
	/**
	 * @author A-3227
	 * @param mailSubClassCode
	 * @return
	 * @throws SystemException
	  
	 */
	
	private String findCarditReceptacle(String mailbagId)
			 throws SystemException{
	   log.entering("Cardit", "findCarditTotal");
		CarditReceptaclePK carditRcpPK = new CarditReceptaclePK();
		carditRcpPK.setCarditKey(carditPK.getCarditKey());
		carditRcpPK.setCompanyCode(carditPK.getCompanyCode());
		carditRcpPK.setReceptacleId(mailbagId);
		CarditReceptacle carditRcp = null;
		String carditKey="";	
		String oldCarditKey="";
		int found=0;
		try{
			carditRcp = CarditReceptacle.find(carditRcpPK);
			found=1;
			log.log(Log.INFO,"\n\nMAILBAG IS ALREADY ASSOCIATED WITH THE CARDIT BEING PROCESSED....!!!!\n\n\n");
		 }catch(FinderException ex){
			 log.log(Log.INFO,"\n\nMAILBAG IS ALREADY NOT ASSOCIATED WITH THE CARDIT BEING PROCESSED.... SO CHECKING FOR DUPLICATE IN OLD CARDITS!!!!!\n\n");
			 CarditReceptacleVO carditreceptacleVO = new CarditReceptacleVO();
			 carditreceptacleVO=CarditReceptacle.findDuplicateMailbagsInCardit(carditPK.getCompanyCode(),mailbagId);
			 if(carditreceptacleVO!= null){
				 oldCarditKey = carditreceptacleVO.getCarditKey();
			 if(oldCarditKey!=null&&oldCarditKey.trim().length()>0){
				 if(!(carditreceptacleVO.getCarditType()!= null  && MailConstantsVO.CDT_TYP_CANCEL.equals(carditreceptacleVO.getCarditType()))){
					 found=1;
				 }
			   log.log(Log.INFO,"\n\n\n\n\t\t MAILBAG IS ALREADY ASSOCIATED WITH THE OLD CARDIT ::"+oldCarditKey);
			  log.log(Log.INFO,"\n\n\n\n\t\t SKIPING DUPLICATE MAILBAG FROM PROCESSING........."+oldCarditKey);
		 }
		 }
		 }
		StringBuilder carditKeys = new StringBuilder().append(
				 oldCarditKey).append("~").append(
						 found);
		 carditKey=carditKeys.toString();
		 return  carditKey;
	}
	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailbagVO> findCarditMails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException {
		return  CarditReceptacle.findCarditMails(
				carditEnquiryFilterVO, pageNumber);

	}

	/**
	 * @return the carditHandoverInformation
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false)})
	public Set<CarditHandover> getCarditHandoverInformation() {
		return carditHandoverInformation;
	}

	/**
	 * @param carditHandoverInformation the carditHandoverInformation to set
	 */
	public void setCarditHandoverInformation(
			Set<CarditHandover> carditHandoverInformation) {
		this.carditHandoverInformation = carditHandoverInformation;
	}

	/**
	 * @return the carditReferenceInformation
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	public Set<CarditReference> getCarditReferenceInformation() {
		return carditReferenceInformation;
	}

	/**
	 * @param carditReferenceInformation the carditReferenceInformation to set
	 */
	public void setCarditReferenceInformation(
			Set<CarditReference> carditReferenceInformation) {
		this.carditReferenceInformation = carditReferenceInformation;
	}	
	/**
	 * @return the carditType
	 */
	@Column(name="CDTTYP")
	public String getCarditType() {
		return carditType;
	}

	/**
	 * @param carditType the carditType to set
	 */
	public void setCarditType(String carditType) {
		this.carditType = carditType;
	}

	/**
	 * @return the actualSenderId
	 */
	@Column(name="ACTSDRIDR")
	public String getActualSenderId() {
		return actualSenderId;
	}

	/**
	 * @param actualSenderId the actualSenderId to set
	 */
	public void setActualSenderId(String actualSenderId) {
		this.actualSenderId = actualSenderId;
	}


	/**
	 * @author A-3227
	 * @param mailSubClassCode
	 * @return
	 * @throws SystemException
	 */
	public static MailbagVO findTransferFromInfoFromCarditForMailbags(MailbagVO mailbagVO)
	 throws SystemException{
		try{
			return constructDAO().findTransferFromInfoFromCarditForMailbags(mailbagVO);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}	
	}

	/**
	 * 
	 * @param reportVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CarditPreAdviseReportVO> generateCarditPreAdviceReport (CarditPreAdviseReportVO reportVO)
	throws SystemException {
		return constructDAO().generateCarditPreAdviceReport(reportVO);
	}

	/**
	 * 
	 * @param carditVO
	 * @throws SystemException
	 * @author A-2572
	 */
	public void updateReceptacleDetailsForUpdation(CarditVO carditVO) throws SystemException {

		/*
		 * TRANSPORT INFORMAITON
		 */
		Collection<CarditTransportationVO> transportInformationVOs = carditVO
		.getTransportInformation();
		if(transportInformationVOs != null && transportInformationVOs.size() > 0) {
			removeChildCarditTransportation();
			for (CarditTransportationVO transportInformationVO : transportInformationVOs) {
				//if(!CarditTransportation.checkCarditTransportExists(carditPK.getCompanyCode(), carditPK.getCarditKey(), transportInformationVO)){
				log.log(Log.FINE,"Cardit Transportation  Not Found  So Persisting");
				if(  !(transportInformationVO.getFlightNumber()==null && MODEOFTRANSPORT_ROAD.equalsIgnoreCase(transportInformationVO.getModeOfTransport()) ) ){
				populateTransportation(transportInformationVO);
				}
				//}
			}
		}

		/*
		 * RECEPTACLE INFORMAITON
		 */
		Collection<CarditReceptacleVO> receptacleInformationVOs = carditVO
		.getReceptacleInformation();
		Collection<CarditReceptacleVO> receptacleInformationToRemoveVOs= new ArrayList<CarditReceptacleVO>();
		if(receptacleInformationVOs!=null && receptacleInformationVOs.size()>0){
			
			if (!MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType()) && !"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())){
				removeReceptacleDetailsFromCardit(this.getReceptacleInformation());
			}
		
			//IASCB-67051 BEG
			if (MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())
					&& this.getReceptacleInformation() != null && !this.getReceptacleInformation().isEmpty()
					&& carditVO.getReceptacleInformation() != null && !carditVO.getReceptacleInformation().isEmpty()
					&& !"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())) {
				Map<String, String> mailInCarditMap = carditVO.getReceptacleInformation().stream().collect(
						Collectors.toMap(CarditReceptacleVO::getReceptacleId, CarditReceptacleVO::getCarditType));
				this.getReceptacleInformation().stream().forEach(rcpInformation -> {
					if (!mailInCarditMap.containsKey(rcpInformation.getCarditReceptaclePK().getReceptacleId())) {
						rcpInformation.setCarditType(MailConstantsVO.CDT_TYP_CANCEL);
					}
				});
			}
			//IASCB-67051 END
			
		for (CarditReceptacleVO carditReceptacleVO : receptacleInformationVOs) {

			
			if(!"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())){
			if (!MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())){
				populateReceptacleDetails(carditReceptacleVO);
			}else{
			int found=0;
			    found = findCarditReceptacleForUpdation(carditReceptacleVO);
			if(found == 0) {
				log.log(Log.FINE,"Cardit Receptacle Not Found  So Persisting ");
				log.log(Log.INFO, "Persisting cardit Receptacle------",
						carditReceptacleVO.getReceptacleId());
				populateReceptacleDetails(carditReceptacleVO);
			}else{
				receptacleInformationToRemoveVOs.add(carditReceptacleVO);
			}
			}
			}else{
				populateCarditDOMReceptacleDetails(carditReceptacleVO);
			}
			
			
		 }
		   /*log.log(Log.FINE,"\nRemoving Duplicate Receptacles from cardit VO......\n");
			if(receptacleInformationToRemoveVOs!=null && receptacleInformationToRemoveVOs.size()>0){
				receptacleInformationVOs.removeAll(receptacleInformationToRemoveVOs);
			}*/	
		}
		//carditVO.setReceptacleInformation(receptacleInformationVOs);
		log.log(Log.FINE,"\n Cardit VO after duplicate validation......\n");
		
		/*
		 * CONTAINER INFORMAITON
		 */
		Collection<CarditContainerVO> containerInformationVOs = carditVO.getContainerInformation();
		// since Container information is optional do null check
		if (containerInformationVOs != null	&& containerInformationVOs.size() > 0) {
			for (CarditContainerVO carditContainerVO : containerInformationVOs) {

				log.log(Log.FINE, "the Cardit Container Vo", carditContainerVO);
				CarditContainer carditCont = findCarditContainer(carditContainerVO.getContainerNumber());
				if ((carditCont == null) && (carditContainerVO.getContainerNumber() != null)
						&& (carditContainerVO.getContainerNumber().trim().length() > 0)) {
					log.log(Log.FINE,"Cardit Container Not Found  So Persisting");
					populateContainerDetails(carditContainerVO);
				}
			}
		}
		/*
		 * TOTAL INFORMATION
		 */
		Collection<CarditTotalVO> totalsInformationVOs = carditVO.getTotalsInformation();
		if(totalsInformationVOs != null && totalsInformationVOs.size() > 0) {
			removeChildCarditTotal();
			for (CarditTotalVO carditTotalVO : totalsInformationVOs) {
				log.log(Log.FINE,"Cardit Total Persisting");
				/*if(!canPersistCarditTotal(carditPK.getCompanyCode(), carditPK.getCarditKey(), carditTotalVO.getMailClassCode())){
					log.log(Log.FINE,"Cardit Total  Not Found  So Persisting");
					new CarditTotal(carditPK, carditTotalVO);
				}*/

				populateTotalDetails(carditTotalVO);
			}
		}
		/*
		 * HANDOVER INFORMATION
		 */
		Collection<CarditHandoverInformationVO> carditHandoverInformationVOs = carditVO.getHandoverInformation();
		if(carditHandoverInformationVOs != null && carditHandoverInformationVOs.size() > 0) {
			removeChildCarditHandover();
			for (CarditHandoverInformationVO carditHandoverInfoVO : carditHandoverInformationVOs) {
				log.log(Log.FINE,"Cardit Handover Persisting");
				populateHandoverDetails(carditHandoverInfoVO);
			}
		}
		/*
		 * REFERENCE INFORMATION
		 */
		Collection<CarditReferenceInformationVO> carditReferenceInformationVOs = carditVO.getReferenceInformation();
		if(carditReferenceInformationVOs != null && carditReferenceInformationVOs.size() > 0) {
			removeChildCarditReference();
			for (CarditReferenceInformationVO carditReferenceInfoVO : carditReferenceInformationVOs) {
				log.log(Log.FINE,"Cardit Reference Persisting");
				populateReferenceDetails(carditReferenceInfoVO);
			}
		}
	}
	
	/**
	 * @author A-2572
	 * @param mailSubClassCode
	 * @return
	 * @throws SystemException
	 */
	private int findCarditReceptacleForUpdation(CarditReceptacleVO carditReceptacleVO)
	 throws SystemException{
	   log.entering("Cardit", "findCarditTotal");
		CarditReceptaclePK carditRcpPK = new CarditReceptaclePK();
		carditRcpPK.setCarditKey(carditPK.getCarditKey());
		carditRcpPK.setCompanyCode(carditPK.getCompanyCode());
		carditRcpPK.setReceptacleId(carditReceptacleVO.getReceptacleId());
		CarditReceptacle carditRcp = null;
		int found=0;
		try{
			carditRcp = CarditReceptacle.find(carditRcpPK);
			carditRcp.setCarditType(carditReceptacleVO.getCarditType());
			found=1;
			log.log(Log.INFO,"\n\nMAILBAG IS ALREADY ASSOCIATED WITH THE CARDIT BEING PROCESSED....!!!!\n\n\n");
		 }catch(FinderException ex){
			 log.log(Log.FINE,"FinderException");
		 }
		 return  found;
	}
	/**
	 * 
	 * @param carditVO
	 * @throws SystemException
	 * @author A-2572
	 */
	public void updateReceptacleDetailsForCorrection(CarditVO carditVO) throws SystemException{
		log.entering("Cardit", "updateReceptacleDetailsForCorrection");
		/*
		 * RECEPTACLE INFORMAITON UPDATION
		 */
		Collection<CarditReceptacleVO> receptacleInformationVOs = carditVO.getReceptacleInformation();
		Collection<CarditReceptacleVO> receptacleInformationToRemoveVOs= new ArrayList<CarditReceptacleVO>();
		Collection<CarditReceptacleVO> receptacleInformationToAdd = new ArrayList<CarditReceptacleVO>();
		if(receptacleInformationVOs!=null && receptacleInformationVOs.size()>0){
			Collection<CarditReceptacle> carditReceptacleDetailsToRemove = new ArrayList<CarditReceptacle>();
			CarditReceptacle carditReceptacleToRemove = null;
			for (CarditReceptacleVO carditReceptacleVO : receptacleInformationVOs) {				
				carditReceptacleToRemove = null;
				carditReceptacleToRemove = getReceptacleDetailsFromCardit(carditReceptacleVO);
				if(carditReceptacleToRemove != null){
					carditReceptacleDetailsToRemove.add(carditReceptacleToRemove);
					receptacleInformationToRemoveVOs.add(carditReceptacleVO);
				}else{
					receptacleInformationToAdd.add(carditReceptacleVO);
					populateReceptacleDetails(carditReceptacleVO);
				}
			}
			if(carditReceptacleDetailsToRemove != null && carditReceptacleDetailsToRemove.size() > 0){
				removeReceptacleDetailsFromCardit(carditReceptacleDetailsToRemove);
				if(receptacleInformationToRemoveVOs != null && receptacleInformationToRemoveVOs.size() > 0){
					receptacleInformationVOs.removeAll(receptacleInformationToRemoveVOs);
					for(CarditReceptacleVO carditReceptacleVO : receptacleInformationToRemoveVOs){
						carditReceptacleVO.setReceptacleStatus(MailConstantsVO.CARDIT_STATUS_CORRECTION_DELETED);
					}
				}
			}
			if(receptacleInformationToAdd != null && receptacleInformationToAdd.size() > 0){
				for(CarditReceptacleVO carditReceptacleVOToAdd : receptacleInformationToAdd){
					carditReceptacleVOToAdd.setCarditType(MailConstantsVO.CARDIT_STATUS_CORRECTION_ADDED);
				}
				carditVO.setReceptacleInformation(receptacleInformationToAdd);
				carditVO.getReceptacleInformation().addAll(receptacleInformationToRemoveVOs);
			}else if(receptacleInformationToRemoveVOs != null && receptacleInformationToRemoveVOs.size() > 0){
				carditVO.setReceptacleInformation(receptacleInformationToRemoveVOs);
			}
		}
		
		log.exiting("Cardit", "updateReceptacleDetailsForCorrection");
	}
	  /**
	   * @author A-3429
	   * getReceptacleDetailsFromCardit
	   *  This method will find the Exact Recptacle from the corresponding Consigment and return the same back.
	   */
	  private CarditReceptacle getReceptacleDetailsFromCardit(CarditReceptacleVO carditReceptacleVO){
		  Collection<CarditReceptacle> carditReceptacleDetails = getReceptacleInformation();
		  for(CarditReceptacle carditRcp : carditReceptacleDetails) {
			  if(carditReceptacleVO.getReceptacleId().equals(carditRcp.getCarditReceptaclePK().getReceptacleId())){
				  return carditRcp;
			  }
		  } 
		  return null;
	  }
	  /**
		 * <pre>
		 * This is to remove the remove Receptacle Details From Cardit
		 * </pre>
		 * 
		 * @author A-3429
		 * @param carditReceptaclesTobeRemoved
		 * @throws SystemException
		 */
		private void removeReceptacleDetailsFromCardit(
				Collection<CarditReceptacle> carditReceptaclesTobeRemoved)
		throws SystemException {
			Collection<CarditReceptacle> carditReceptacleDetails = getReceptacleInformation();
			if (carditReceptacleDetails != null
					&& carditReceptacleDetails.size() > 0
					&& carditReceptaclesTobeRemoved != null
					&& carditReceptaclesTobeRemoved.size() > 0) {
				log.log(Log.FINE, "Cardit Receptacle Child Removing");
				CarditReceptacle carditReceptacle = null;
				while (carditReceptaclesTobeRemoved.iterator().hasNext()) {
					carditReceptacle = carditReceptaclesTobeRemoved.iterator().next();
					carditReceptacle.remove();
					carditReceptaclesTobeRemoved.remove(carditReceptacle);
				}
			}

		}
		/**
		 * 
		 * @param carditVO
		 * @throws SystemException
		 * @author A-2572
		 */
		public void updateReceptacleDetailsForConfirmation(CarditVO carditVO)
		throws SystemException {
			log.entering("Cardit", "updateReceptacleDetailsForConfirmation");
			/*
			 * RECEPTACLE INFORMAITON UPDATION
			 */
			Collection<CarditReceptacleVO> carditReceptacleVOsTobeRemoved = updateReceptacleForHistory(carditVO);
			removeReceptacleDetailsFromCardit(getReceptacleInformation());
			for (CarditReceptacleVO receptacleVO : carditVO.getReceptacleInformation()) {
				populateReceptacleDetails(receptacleVO);
			}
			if (carditReceptacleVOsTobeRemoved != null&& carditReceptacleVOsTobeRemoved.size() > 0) {
				carditVO.getReceptacleInformation().addAll(carditReceptacleVOsTobeRemoved);
			}
			log.exiting("Cardit", "updateReceptacleDetailsForConfirmation");
		} 
		/**
		 * 
		 * @param carditVO
		 * @return
		 * @throws SystemException
		 * @author A-2572
		 */
		public Collection<CarditReceptacleVO> updateReceptacleForHistory(CarditVO carditVO) throws SystemException{
			log.entering("Cardit", "updateReceptacleDetailsForConfirmation");
			Collection<CarditReceptacleVO> carditReceptacleVOsRemoved = new ArrayList<CarditReceptacleVO>();
			CarditReceptacleVO carditReceptacleVOForUpd = null;
			for(CarditReceptacle carditReceptacle: getReceptacleInformation()){
			carditReceptacleVOForUpd = getReceptacleDetailsForHisUpdation(carditReceptacle,carditVO.getReceptacleInformation());
			if(carditReceptacleVOForUpd == null){
				carditReceptacleVOsRemoved.add(constructVoFromEntity(carditReceptacle));
			}
			}
			log.exiting("Cardit", "updateReceptacleDetailsForConfirmation");
			return carditReceptacleVOsRemoved;
		} 
		 /**
		   * @author A-3429
		   * getReceptacleDetailsFromCardit
		   *  This method will find the Exact Recptacle from the corresponding Consigment and return the same back.
		   */
		private CarditReceptacleVO getReceptacleDetailsForHisUpdation(CarditReceptacle carditReceptacle,Collection<CarditReceptacleVO> receptacleInformations){
			for(CarditReceptacleVO carditReceptacleVO : receptacleInformations) {
				if(carditReceptacle.getCarditReceptaclePK().getReceptacleId().equals(carditReceptacleVO.getReceptacleId())){
					return carditReceptacleVO;
				}
			} 
			return null;
		}
		 /**
		   * @author A-3429
		   * getReceptacleDetailsFromCardit
		   *  This method will find the Exact Recptacle from the corresponding Consigment and return the same back.
		   */
		private CarditReceptacleVO constructVoFromEntity(CarditReceptacle carditReceptacle){
			CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
			carditReceptacleVO.setReceptacleId(carditReceptacle.getCarditReceptaclePK().getReceptacleId());
			carditReceptacleVO.setReceptacleStatus(MailConstantsVO.CARDIT_STATUS_CONFIRMATION_DELETED);
			return carditReceptacleVO;
		}
		/**
		 * 
		 * @param companyCode
		 * @param mailbagId
		 * @return
		 * @throws SystemException
		 * @author A-2572
		 */
		public static String findCarditOriginForResditGeneration(String companyCode,long mailbagId)
		 throws SystemException{
			try{
				return constructDAO().findCarditOriginForResditGeneration(companyCode, mailbagId);
			}catch(PersistenceException ex){
				throw new SystemException(ex.getErrorCode(),ex);
			}	
		}
        /**
         * @author A-7929
         * @param mailbagEnquiryFilterVO
         * @param pageNumber
         * @return
         * @throws SystemException 
         */
		public static Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
				int pageNumber) throws SystemException {
			return  CarditReceptacle.findMailbagsForTruckFlight(
					mailbagEnquiryFilterVO, pageNumber);
		}
		
		
		/**
		 * 
		 * @param mailInConsignmentVO
		 * @param mailbag
		 * @return
		 * @throws SystemException
		 * @throws DuplicateMailBagsException
		 */
		private boolean checkForDuplicateMailbag(String companyCode,String paCode,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
            PostalAdministrationVO postalAdministrationVO = new MailController().findPACode(companyCode, paCode);
            LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
            LocalDate dspDate = new LocalDate(
                        LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);

            long seconds=currentDate.findDifference(dspDate);
            long days=seconds/86400000;
            if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
            	return false;
            }
            return true;
      }

		/**
		 * ICRD-346447
		 * @param receptacleVOs
		 * @param companyCode
		 * @return
		 * @author A-8061
		 * @throws SystemException
		 */
		public Object[] findDuplicateCarditReceptacle(Collection<CarditReceptacleVO> receptacleVOs,String companyCode)
				 throws SystemException{
			
						ArrayList<String> dupList=new ArrayList<String>();
						HashMap<String,String> dupCheckMap=new HashMap<String,String>();
						if(receptacleVOs!=null && !receptacleVOs.isEmpty()){
							for(CarditReceptacleVO carditReceptacleVO : receptacleVOs){
								 CarditReceptacleVO carditreceptacleVO = new CarditReceptacleVO();
								 carditreceptacleVO=CarditReceptacle.findDuplicateMailbagsInCardit(companyCode,carditReceptacleVO.getReceptacleId());
								 if(carditreceptacleVO!= null){
								 if(carditreceptacleVO.getCarditKey()!=null&&carditreceptacleVO.getCarditKey().trim().length()>0){
									 if(!(carditreceptacleVO.getCarditType()!= null  && MailConstantsVO.CDT_TYP_CANCEL.equals(carditreceptacleVO.getCarditType()))){
										 dupList.add(new StringBuilder().append(carditReceptacleVO.getReceptacleId()).append(" ").append(carditreceptacleVO.getCarditKey()).toString());
									 }
								 }
							 }
							if(dupCheckMap.containsKey(carditReceptacleVO.getReceptacleId())){
								 dupList.add(new StringBuilder().append(carditReceptacleVO.getReceptacleId()).append(" ").append(carditReceptacleVO.getCarditKey()).toString());
							}else{
								dupCheckMap.put(carditReceptacleVO.getReceptacleId(),carditReceptacleVO.getReceptacleId());
							}
							}
						}
						if(dupList!=null && !dupList.isEmpty()){
							return dupList.toArray();
						}
						return null;
		}
		
}
