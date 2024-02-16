/*
 * ULDAtAirport.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

import com.ibsplc.icargo.business.mail.operations.vo.ULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.MailbagInULDAtAirport;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
//import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.mail.operations.MailbagInULDAtAirportPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.ULDAtAirportPK;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3109
 *
 *         Stores ULD assigned to destination . Delete from this entity while
 *         assigning to flight
 *
 *
 */
@Entity
@Table(name = "MALARPULD")
@Staleable
public class ULDAtAirport {

	private Log log = LogFactory.getLogger("Mail_operations");

	private static final String MODULE = "mail.operations";
	
	 private static final String HYPHEN = "-";
	 private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	 private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";

	private ULDAtAirportPK uldAtAirportPK;

	/**
	 * finalDestination
	 */
	private String finalDestination;

	/**
	 * numberOfBags
	 */
	private int numberOfBags;

	/**
	 * totalWeight
	 */
	private double totalWeight;

	/**
	 * remarks
	 */
	private String remarks;

	/**
	 * carrierCode
	 */
	private String carrierCode;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * warehouseCode
	 */
	private String warehouseCode;

	/**
	 * locationCode
	 */
	private String locationCode;

	/**
	 * transferFromCarrier
	 */
	private String transferFromCarrier;

	/**
	 * @return Returns the finalDestination.
	 *
	 */

	/**
	 * mailbagInULDAtAirports
	 */
	private Set<MailbagInULDAtAirport> mailbagInULDAtAirports;

	@Column(name = "DSTCOD")
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *            The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the remarks.
	 *
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the acceptedBags.
	 *
	 */
	@Column(name = "ACPBAG")
	public int getNumberOfBags() {
		return numberOfBags;
	}

	/**
	 * @param acceptedBags
	 *            The acceptedBags to set.
	 */
	public void setNumberOfBags(int acceptedBags) {
		this.numberOfBags = acceptedBags;
	}

	/**
	 * @return Returns the acceptedWeight.
	 *
	 */
	@Column(name = "ACPWGT")
	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param acceptedWeight
	 *            The acceptedWeight to set.
	 */
	public void setTotalWeight(double acceptedWeight) {
		this.totalWeight = acceptedWeight;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 *
	 */

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
	 *
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
	 * @return Returns the carrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the locationCode.
	 */
	@Column(name = "LOCCOD")
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode
	 *            The locationCode to set.
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return Returns the warehouseCode.
	 */
	@Column(name = "WHSCOD")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	/**
	 * @param warehouseCode
	 *            The warehouseCode to set.
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * @return Returns the transferFromCarrier.
	 */
	@Column(name = "FRMCARCOD")
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param transferFromCarrier
	 *            The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}

	/**
	 * @return Returns the uldAtAirportPK.
	 *
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")) })
	public ULDAtAirportPK getUldAtAirportPK() {
		return uldAtAirportPK;
	}

	/**
	 * @param uldAtAirportPK
	 *            The uldAtAirportPK to set.
	 */
	public void setUldAtAirportPK(ULDAtAirportPK uldAtAirportPK) {
		this.uldAtAirportPK = uldAtAirportPK;
	}

	/**
	 * @return the mailbagInULDAtAirports
	 */

	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false) })
	public Set<MailbagInULDAtAirport> getMailbagInULDAtAirports() {
		return mailbagInULDAtAirports;
	}

	/**
	 * @param mailbagInULDAtAirports
	 *            the mailbagInULDAtAirports to set
	 */
	public void setMailbagInULDAtAirports(
			Set<MailbagInULDAtAirport> mailbagInULDAtAirports) {
		this.mailbagInULDAtAirports = mailbagInULDAtAirports;
	}

	public ULDAtAirport() {
	}

	public ULDAtAirport(ULDAtAirportVO uldAtAirportVO) throws SystemException {
		populatePK(uldAtAirportVO);
		populateAttributes(uldAtAirportVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		populateChildren(uldAtAirportVO);

	}

	/**
	 * A-3109
	 *
	 * @param uldAtAirportVO
	 * @throws SystemException
	 */
	private void populateChildren(ULDAtAirportVO uldAtAirportVO)
			throws SystemException {
		uldAtAirportPK = getUldAtAirportPK();
		Collection<MailbagInULDAtAirportVO> mailbagInUldVOs =
				uldAtAirportVO.getMailbagInULDAtAirportVOs();
			if(mailbagInUldVOs != null && mailbagInUldVOs.size() > 0) {
			    populateMailbags(mailbagInUldVOs);
	        }



	}

	/**
	 * A-3109
	 *
	 * @param uldAtAirportVO
	 */
	private void populatePK(ULDAtAirportVO uldAtAirportVO) {
		uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setCompanyCode(uldAtAirportVO.getCompanyCode());
		uldAtAirportPK.setUldNumber(uldAtAirportVO.getUldNumber());
		uldAtAirportPK.setCarrierId(uldAtAirportVO.getCarrierId());
		uldAtAirportPK.setAirportCode(uldAtAirportVO.getAirportCode());
	}

	/**
	 * A-3109
	 *
	 * @param uldAtAirportVO
	 */
	private void populateAttributes(ULDAtAirportVO uldAtAirportVO) {
		setNumberOfBags(uldAtAirportVO.getNumberOfBags());
		//setTotalWeight(uldAtAirportVO.getTotalWeight());
		if(uldAtAirportVO.getTotalWeight()!=null){
		setTotalWeight(uldAtAirportVO.getTotalWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
	}
		setFinalDestination(uldAtAirportVO.getFinalDestination());
		setRemarks(uldAtAirportVO.getRemarks());
		setCarrierCode(uldAtAirportVO.getCarrierCode());
		setWarehouseCode(uldAtAirportVO.getWarehouseCode());
		setLocationCode(uldAtAirportVO.getLocationCode());
		setTransferFromCarrier(uldAtAirportVO.getTransferFromCarrier());
	}

	/**
	 * @author A-3109
	 * @param uldAtAirportPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static ULDAtAirport find(ULDAtAirportPK uldAtAirportPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				ULDAtAirport.class, uldAtAirportPK);
	}

	/**
	 * This method is used to remove the Instance Of the Entity
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		removeChildren();
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}

	/**
	 * @author A-1739 This method is used to remove the Children
	 * @throws SystemException
	 */
	private void removeChildren() throws SystemException {

		if (getMailbagInULDAtAirports() != null) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				mailbagInULDAtAirport.remove();
			}
			mailbagInULDAtAirports.clear();
		}

	}

	/**
	 * @author a-3109 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * @author A-5991
	 * @param dsnVO
	 * @param isBulkContainer
	 * @param mailbagVOs
	 * @param despatchDetails
	 * @param containerDetailsVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	public void insertMailBagInULDAtArpAcceptanceDtls(
			ContainerDetailsVO containerDetailsVO,boolean isInventoryForArrival) throws SystemException, DuplicateMailBagsException {
		log.entering("MailAcceptance", "insertMailBagInULDAtArpAcceptanceDtls");

		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO     
				.getDesptachDetailsVOs();
		if (containerDetailsVO.getMailDetails() != null) {
			mailbagVOs.addAll(containerDetailsVO.getMailDetails());
		}
		if (despatchDetailsVOs != null) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				mailbagVOs.add(MailtrackingDefaultsVOConverter
						.convertToMailBagVO(despatchDetailsVO));
			}
		}
		if (mailbagVOs.size() > 0) {

			/*for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO.setFinalDestination(containerDetailsVO
						.getDestination());
				if(isInventoryForArrival){
					mailbagVO.setUldNumber(mailbagVO.getContainerNumber()); 
				}

				if(containerDetailsVO.isMailUpdateFlag()){
					mailbagVO.setMailUpdateFlag(true);
					}
			}*/
			addAcceptedMailbags(mailbagVOs);
		}

		log.exiting("MailAcceptance", "insertMailBagInULDAtArpAcceptanceDtls");
	}

	private String constructBulkULDNumber(String airport, String carrierCode) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(airport)
					.toString();
		} else {
			// This case comes during arrival
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(
					MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/**
	 * @author A-5991
	 * @param dsnInULDAtArp
	 * @param mailbagVOs
	 * @param dsnVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	public void addAcceptedMailbags(Collection<MailbagVO> mailbagVOs)
			throws SystemException, DuplicateMailBagsException { 
		log.entering("DSNInULDAtAirport", "addMailbagsToULDAtArp");

            Collection<MailbagInULDAtAirportVO> mailbagsInULD =
                new ArrayList<MailbagInULDAtAirportVO>();  

		/*
		 * Made Some changes to Handle the Inventory Save For Arrival too Where
		 * the Operational Flags are Not Very Specific in case if a Uld is
		 * already there..In case a New ULd everything within this ULD will have
		 * the Opeartional Flag as I else the Details from the Arrival Screen is
		 * No Way of Ensuring whether the MailBag or its Consignment is already
		 * attched with the ULD or NOt ..The Concept of the boolean
		 * isInventoryForArrival is to compromise the same..
		 */
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbags = null;
			//Modified for ICRD-126626
			
			// Added for ICRD-255189 starts
			mailbagVO =  new MailController().constructOriginDestinationDetails(mailbagVO);
			//Added for ICRD-255189 ends
			
			MailbagPK  mailbagPKs=constructMailbagPK(mailbagVO); 
    		try{
    			mailbags=Mailbag.find(mailbagPKs);
    		} catch (FinderException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    		boolean isDuplicate = false;
			if(mailbags!=null){//Changed by A-8164 for ICRD-328357
				if(!(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbags.getLatestStatus()))&&!MailConstantsVO.HHT_TRA.equals(mailbags.getLatestStatus())
    					&&!("ARRSCN").equals(mailbagVO.getMailbagDataSource())&&!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbags.getLatestStatus()))  {//Modified for ICRD-314759 to neglect duplicate check when transfer from flight is provided
        					
        				if(OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()))	//Added for ICRD-330350																												//in android outbound screen.
        					isDuplicate =  new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),mailbagVO.getPaCode(),mailbags);										
    			}

					mailbags.updateAcceptanceFlightDetails(mailbagVO);
					mailbags.updateAcceptanceDamage(mailbagVO); 
					mailbags.updatePrimaryAcceptanceDetails(mailbagVO);

			/*if(mailbagVO.isMailUpdateFlag()){
				mailbags.setContainerType(mailbagVO.getContainerType());
				mailbags.setUldNumber(mailbagVO.getContainerNumber());
			}
			mailbags.setLastUpdateTime(
				mailbagVO.getLastUpdateTime());
			mailbags.setScannedDate(mailbagVO.getScannedDate());*/
			}
			// Modified for ICRD-126624
			MailbagInULDAtAirport mailbagInULDAtArp = null;
			try {
				mailbagInULDAtArp = findMailbagInULD(constructMailbagInULDAtAirportPK(mailbagVO));
			} catch (FinderException e) {
				// TODO Auto-generated catch block
			}
			// findMailbagInULDFromCollection(
			// constructMailbagInULDAtAirportPK(mailbagVO));
			if (mailbagInULDAtArp != null) {
				if(mailbagVO.getDamageFlag()!=null){
				mailbagInULDAtArp.setDamageFlag(mailbagVO.getDamageFlag());
				}else{
					mailbagInULDAtArp.setDamageFlag(MailbagVO.FLAG_NO);
				}
                    mailbagInULDAtArp.setTransferFromCarrier(
                   		 mailbagVO.getTransferFromCarrier());
                    mailbagInULDAtArp.setScannedDate(
                    		mailbagVO.getScannedDate());
				// mailbagInULDAtArp.setSealNumber(mailbagVO.getSealNumber());
			} else {
                	if(OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {


                        Mailbag mailbag=null;
                        MailbagPK  mailbagPK=constructMailbagPK(mailbagVO);
                		try{
                			mailbag=Mailbag.find(mailbagPK);
                		}
                		catch(FinderException e){
                			//Added for ICRD-243469 starts
                		/*	String paCode = null;
								String companyCode = mailbagVO.getCompanyCode();
								String originOfFiceExchange = mailbagVO.getOoe();
								OfficeOfExchange OoE = new OfficeOfExchange();
								if(mailbagVO.getPaCode() == null){
									try{
										paCode = OoE.findPAForOfficeOfExchange(companyCode,originOfFiceExchange);
									}catch (SystemException ex) {
										
									}
									
									mailbagVO.setPaCode(paCode);
								}
								String serviceLevel = null;
								serviceLevel = findMailServiceLevel(mailbagVO);
								
								if(serviceLevel!=null){
									mailbagVO.setMailServiceLevel(serviceLevel);
								}
							//Added for ICRD-243469 ends
                			mailbag=new Mailbag(mailbagVO);*/
                		}
                
                		if (mailbag==null||isDuplicate){
                			String paCode = null;
								String companyCode = mailbagVO.getCompanyCode();
								String originOfFiceExchange = mailbagVO.getOoe();
								if(mailbagVO.getPaCode() == null){
									try{
										paCode = new MailController().findPAForOfficeOfExchange(companyCode,originOfFiceExchange);
									}catch (SystemException ex) {
										
									}
									
									mailbagVO.setPaCode(paCode);
								}
								/*String serviceLevel = null;
								serviceLevel = findMailServiceLevel(mailbagVO);
								
								if(serviceLevel!=null){
									mailbagVO.setMailServiceLevel(serviceLevel);
								}*/
							//Added for ICRD-243469 ends
					  mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());	// Added by A-8353 for ICRD-230449	
					  		//Added by A-7794 as part of ICRD-232299
					  		String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
					  		if(scanWaved != null){
					  			mailbagVO.setScanningWavedFlag(scanWaved);
					  		}
					  		
							//ICRD-341146 Begin 
							if(new MailController().isUSPSMailbag(mailbagVO)){
								mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
							}else{
								mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
							}
							//ICRD-341146 End
							
							
					  		mailbagVO.setMailbagDataSource(mailbagVO.getMailbagDataSource());
							MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
                            MailAcceptance.populatePrimaryAcceptanceDetails(mailbagVO);
                			mailbag=new Mailbag(mailbagVO);
            			//Added by A-8353 as part of ICRD-230449 ends	
                		}
                        if(mailbagVO!=null){
                        	mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
                        	mailbagsInULD.add(
                                constructMailbagInULDAtArp(mailbagVO));

                        }
                    } /*else if(OPERATION_FLAG_UPDATE.equals(
                            mailbagVO.getOperationalFlag())) {
					throw new SystemException("No such mailbag");
				}*/
			}
		}
		populateMailbags(mailbagsInULD);


		log.exiting("DSNInULDAtAirport", "addMailbagsToULDAtArp");
	}

	/**
	 * @author a-1936 This method is used to construct the
	 *         MailbagInULDAtAirportPK
	 * @param mailbagVo
	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(
			MailbagVO mailbagVo) throws SystemException {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		if(getUldAtAirportPK()!=null && getUldAtAirportPK().getCompanyCode()!=null){
		mailbagInULDAtAirportPK.setCompanyCode(getUldAtAirportPK()
				.getCompanyCode());
		mailbagInULDAtAirportPK
				.setUldNumber(getUldAtAirportPK().getUldNumber());
		mailbagInULDAtAirportPK.setAirportCode(getUldAtAirportPK()
				.getAirportCode());
		mailbagInULDAtAirportPK
				.setCarrierId(getUldAtAirportPK().getCarrierId());
		mailbagInULDAtAirportPK
				.setMailSequenceNumber(mailbagVo.getMailSequenceNumber()>0?mailbagVo.getMailSequenceNumber():findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
		}else{
		mailbagInULDAtAirportPK.setCompanyCode(mailbagVo
				.getCompanyCode());
		if(mailbagVo.getUldNumber()!=null){
			if(MailConstantsVO.BULK_TYPE.equals(mailbagVo.getContainerType())){
				mailbagInULDAtAirportPK
				.setUldNumber(constructBulkULDNumber(mailbagVo.getFinalDestination(),
						mailbagVo.getCarrierCode()));
			}else{
		mailbagInULDAtAirportPK
				.setUldNumber(mailbagVo.getUldNumber());
		}
		}
		if(MailConstantsVO.BULK_TYPE.equals(mailbagVo.getContainerType()) &&
				MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVo.getOperationalFlag()) &&
				(mailbagVo.getAcceptanceFlag() == null || "N".equals(mailbagVo.getAcceptanceFlag())) &&
				(mailbagVo.getArrivedFlag() == null || "N".equals(mailbagVo.getArrivedFlag())) &&
				"-1".equals(mailbagVo.getFlightNumber())){
			String bulkNumber = constructBulkULDNumber(mailbagVo.getPou());
			mailbagInULDAtAirportPK.setUldNumber(bulkNumber);
		}
		else{
			mailbagInULDAtAirportPK
			.setUldNumber(mailbagVo.getContainerNumber());
		}
		mailbagInULDAtAirportPK.setAirportCode(mailbagVo
				.getScannedPort());
		mailbagInULDAtAirportPK
				.setCarrierId(mailbagVo.getCarrierId());
		mailbagInULDAtAirportPK
				.setMailSequenceNumber(mailbagVo.getMailSequenceNumber()>0?mailbagVo.getMailSequenceNumber():findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
		}
		// Added to Include the DSN PK
		return mailbagInULDAtAirportPK;

	}

	/**
	 * @author A-5991
	 * @param mailBagId
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public long findMailSequenceNumber(String mailBagId, String companyCode)
			throws SystemException {

		return constructDAO().findMailSequenceNumber(mailBagId, companyCode);

	}
	/**
	 * @author A-5991
	 * @param mailBagId
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO)
			throws SystemException {
		Collection<MailbagVO> mailbagVos=null;
		try {
			mailbagVos= constructDAO().findMailBagForDespatch(mailbagVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			log.log(Log.SEVERE, "PersistenceException Caught");
		}
		return mailbagVos;

	}

	/**
	 * A-5991
	 *
	 * @param mailbagInAirportPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailbagInULDAtAirport findMailbagInULD(
			MailbagInULDAtAirportPK mailbagInAirportPK) throws FinderException,
			SystemException {
		return MailbagInULDAtAirport.find(mailbagInAirportPK);
	}

	/**
	 * A-5991
	 *
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportVO constructMailbagInULDAtArp(
			MailbagVO mailbagVO) throws SystemException {
        MailbagInULDAtAirportVO mailbagInULD =
            new MailbagInULDAtAirportVO();
		mailbagInULD.setMailId(mailbagVO.getMailbagId());
        mailbagInULD.setComapnyCode(mailbagVO.getCompanyCode());
        mailbagInULD.setCarrierId(mailbagVO.getCarrierId());
        mailbagInULD.setAirportCode(mailbagVO.getScannedPort());

		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			mailbagInULD.setUldNumber(constructBulkULDNumber(
					mailbagVO.getFinalDestination(),
					mailbagVO.getCarrierCode()));

		}else{
        if(mailbagVO.getUldNumber()!=null){
        	 mailbagInULD.setUldNumber(mailbagVO.getUldNumber());
			} else {
        	 mailbagInULD.setUldNumber(mailbagVO.getContainerNumber());
        }

		}
		if(MailConstantsVO.ULD_TYPE.equals(mailbagVO.getContainerType())&&mailbagVO.getCarrierCode()!=null
				&&MailConstantsVO.CONST_BULK_ARR_ARP.concat(
					MailConstantsVO.SEPARATOR).concat(mailbagVO.getCarrierCode()).equals(mailbagVO.getContainerNumber())){
			 mailbagInULD.setUldNumber(mailbagVO.getContainerNumber());
		}
		mailbagInULD.setContainerNumber(mailbagVO.getContainerNumber());

		mailbagInULD.setDamageFlag(mailbagVO.getDamageFlag());
        mailbagInULD.setAcceptedFlag(mailbagVO.getAcceptanceFlag());
        mailbagInULD.setArrivedFlag(mailbagVO.getArrivedFlag());
        mailbagInULD.setDeliveredFlag(mailbagVO.getDeliveredFlag());
		mailbagInULD.setScannedDate(mailbagVO.getScannedDate());
		mailbagInULD.setWeight(mailbagVO.getWeight());
		mailbagInULD.setMailClass(mailbagVO.getMailClass());
		mailbagInULD.setSealNumber(mailbagVO.getSealNumber());
        mailbagInULD.setTransferFromCarrier(
       		 mailbagVO.getTransferFromCarrier());
		if (mailbagVO.getMailSequenceNumber() > 0) {
        	mailbagInULD.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		}
		return mailbagInULD;
	}

	/**
	 * A-1739
	 *
	 * @param mailbagInUldVOs
	 * @throws SystemException
	 */
	private void populateMailbags(
			Collection<MailbagInULDAtAirportVO> mailbagInUldVOs)
			throws SystemException {
		uldAtAirportPK = getUldAtAirportPK();
		MailbagInULDAtAirport uldAtAirport = null;
		if (getMailbagInULDAtAirports() == null) {
			mailbagInULDAtAirports = new HashSet<MailbagInULDAtAirport>();
		}
		for (MailbagInULDAtAirportVO mailbagInULDVO : mailbagInUldVOs) {
			if(uldAtAirportPK!=null){
			MailbagInULDAtAirportPK mailbagInULDAtAiportPK = new MailbagInULDAtAirportPK();
			mailbagInULDAtAiportPK.setCompanyCode(uldAtAirportPK.getCompanyCode());
			mailbagInULDAtAiportPK.setAirportCode(uldAtAirportPK.getAirportCode());
			mailbagInULDAtAiportPK.setCarrierId(uldAtAirportPK.getCarrierId());
			mailbagInULDAtAiportPK.setMailSequenceNumber(mailbagInULDVO.getMailSequenceNumber());
			mailbagInULDAtAiportPK.setUldNumber(uldAtAirportPK.getUldNumber());
			try{
				uldAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAiportPK);
			}catch(FinderException exception){
				log.log(Log.FINE, exception.getMessage());
			}
			}
			else{
				MailbagInULDAtAirportPK mailbagInULDAtAiportPK = new MailbagInULDAtAirportPK();
				mailbagInULDAtAiportPK.setCompanyCode(mailbagInULDVO.getComapnyCode());
				mailbagInULDAtAiportPK.setAirportCode(mailbagInULDVO.getAirportCode());
				mailbagInULDAtAiportPK.setCarrierId(mailbagInULDVO.getCarrierId());
				mailbagInULDAtAiportPK.setMailSequenceNumber(mailbagInULDVO.getMailSequenceNumber());
				mailbagInULDAtAiportPK.setUldNumber(mailbagInULDVO.getUldNumber());
				try{
					uldAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAiportPK);
				}catch(FinderException exception){
					log.log(Log.FINE, exception.getMessage());
				}
			}
			if(uldAtAirport == null){
				if(!MailConstantsVO.ONLOAD_MESSAGE.equals(mailbagInULDVO.getMailSource())){
			 	new MailbagInULDAtAirport(
						uldAtAirportPK, mailbagInULDVO);
			}else{
			mailbagInULDAtAirports.add(new MailbagInULDAtAirport(
					uldAtAirportPK, mailbagInULDVO));
			}
			
			}
		}

	}

	/**
	 * @author A-1936 This method is used to reassign the MailBags To
	 *         Destination
	 * @param flightAssignedMailbags
	 * @param toDestinationContainerVO
	 * @param dsnAtAirportMap
	 * @throws SystemException
	 */
	public void reassignMailToDestination(
			Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO) throws SystemException {

		if (flightAssignedMailbags != null && flightAssignedMailbags.size() > 0) {
			for (MailbagVO mailbagVO : flightAssignedMailbags) {

				MailbagInULDAtAirportPK mailBagInUldAtAirportPK = constructMailbagInULDAtAirportPK(mailbagVO);
				MailbagInULDAtAirport mailbagInULDAtAirport = null;
				try {
					mailbagInULDAtAirport = MailbagInULDAtAirport
							.find(mailBagInUldAtAirportPK);
				} catch (FinderException ex) {
					MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailBagInULDAirport(
							mailbagVO, toDestinationContainerVO);
					// mailbagInULDAtAirportVO.setPltEnabledFlag(MailConstantsVO.FLAG_YES);
					mailbagInULDAtAirport = new MailbagInULDAtAirport(
							getUldAtAirportPK(), mailbagInULDAtAirportVO);
				}
			}

		}
	}

	public MailbagInULDAtAirportVO constructMailbagInULDAtAirportVO(
			MailbagInULDAtAirportPK mailbagInULDAtAirportPK) {
		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		mailbagInULDAtAirportVO.setComapnyCode(mailbagInULDAtAirportPK
				.getCompanyCode());
		mailbagInULDAtAirportVO.setAirportCode(mailbagInULDAtAirportPK
				.getAirportCode());
		mailbagInULDAtAirportVO.setCarrierId(mailbagInULDAtAirportPK
				.getCarrierId());
		mailbagInULDAtAirportVO.setUldNumber(mailbagInULDAtAirportPK
				.getUldNumber());
		mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagInULDAtAirportPK
				.getMailSequenceNumber());
		return mailbagInULDAtAirportVO;
	}

	/**
	 * @author a-1936 This method is used to reassign the MailFrom Destination
	 *         Group the Mailbags Based on their DSNS in a Particular ULD at the
	 *         Airport say DSN1-U1-ARP DSN2-UI-ARP DSN3-U1-ARP
	 * @param destinationAssignedMailbags
	 * @throws SystemException
	 */
	public void reassignMailFromDestination(
			Collection<MailbagVO> destinationAssignedMailbags)
			throws SystemException {
		log.entering("ULDAtAirport", "reassignMailFromDestination");
		for (MailbagVO mailbagVo : destinationAssignedMailbags) {
			MailbagInULDAtAirportPK mailbagInULDAtAirportPK = constructMailbagInULDAtAirportPK(mailbagVo);
			removeMailBag(mailbagInULDAtAirportPK, mailbagVo);

		}

	}

	/**
	 * @author a-1936 This method is used to remove the mailbagFromAirport
	 * @param mailbagInULDAtAirportPK
	 * @throws SystemException
	 */
	private void removeMailBag(MailbagInULDAtAirportPK mailbagInULDAtAirportPK,
			MailbagVO mailBagVo) throws SystemException {
		log.entering("DSNinuldAtAirport", "removeMailBag");
	//	String uldNumber = null;
		MailbagInULDAtAirport mailbagInULDAtAirport = null;
		try {
			mailbagInULDAtAirport = MailbagInULDAtAirport
					.find(mailbagInULDAtAirportPK);
		} catch (FinderException ex) {
			//throw new SystemException(ex.getErrorCode(), ex);
		}
		
/*		if(mailBagVo.getUldNumber()!=null){
			uldNumber = mailBagVo.getUldNumber();
		}else if (mailBagVo.getContainerNumber()!=null){
			uldNumber = mailBagVo.getContainerNumber();
		}
		if (mailBagVo.getContainerNumber() != null
				&& MailConstantsVO.BULK_TYPE.equals(mailBagVo.getContainerType())) {
			uldNumber = constructBulkULDNumber(
					mailBagVo.getFinalDestination(), mailBagVo.getCarrierCode());
		}*/
		//Set<MailbagInULDAtAirport> oldMailbagInULDDetails = this.getMailbagInULDAtAirports();
				//Collection<MailbagInULDAtAirport> removeMailbagInULD = null;
				if(mailbagInULDAtAirport!=null){
					//removeMailbagInULD = new ArrayList<MailbagInULDAtAirport>();
					//removeMailbagInULD.add(mailbagInULDAtAirport);
					if (!MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailBagVo.getMailStatus())) { 
					mailBagVo.setTransferFromCarrier(mailbagInULDAtAirport.getTransferFromCarrier()); 
					}
					//this.mailbagInULDAtAirports.removeAll(removeMailbagInULD); 
					mailbagInULDAtAirport.remove();  
		}
		 			   
	   
		 			/*
		if(oldMailbagInULDDetails!=null){
							removeMailbagInULD = new HashSet<>(this.getMailbagInULDAtAirports().size());
			 	for(MailbagInULDAtAirport mailDetails : oldMailbagInULDDetails){
				 if(mailBagVo.getCompanyCode().equals(mailDetails.getMailbagInULDAtAirportPK().getCompanyCode())&&
				(uldNumber.equals(mailDetails.getMailbagInULDAtAirportPK().getUldNumber()))&&
				 (mailBagVo.getCarrierId()==(mailDetails.getMailbagInULDAtAirportPK().getCarrierId()))&&
				 (mailBagVo.getMailSequenceNumber()==(mailDetails.getMailbagInULDAtAirportPK().getMailSequenceNumber()))&&
					(mailBagVo.getScannedPort().equals(mailDetails.getMailbagInULDAtAirportPK().getAirportCode()))){
					 
					 removeMailbagInULD.add(mailDetails);
				//}
			 }   
		} */
	/*	if(oldMailbagInULDDetails!=null){
		mailBagVo.setTransferFromCarrier(mailbagInULDAtAirport.getTransferFromCarrier()); 
		this.mailbagInULDAtAirports.removeAll(oldMailbagInULDDetails); 
		}
*/
		/*if(mailbagInULDAtAirport!=null){
		mailBagVo.setTransferFromCarrier(mailbagInULDAtAirport.getTransferFromCarrier()); 
		mailbagInULDAtAirport.remove();
		}*/
	}

	/**
	 * A-1739
	 *
	 * @return
	 */
	public ULDAtAirportVO retrieveVO() {
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		ULDAtAirportPK uldAtAirportPk = getUldAtAirportPK();
		uldAtAirportVO.setCompanyCode(uldAtAirportPk.getCompanyCode());
		uldAtAirportVO.setCarrierId(uldAtAirportPk.getCarrierId());
		uldAtAirportVO.setAirportCode(uldAtAirportPk.getAirportCode());
		uldAtAirportVO.setUldNumber(uldAtAirportPk.getUldNumber());
		uldAtAirportVO.setNumberOfBags(getNumberOfBags());
		//uldAtAirportVO.setTotalWeight(getTotalWeight());
		uldAtAirportVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,getTotalWeight()));//added by A-7371
		uldAtAirportVO.setFinalDestination(getFinalDestination());
		uldAtAirportVO.setCarrierCode(getCarrierCode());
		uldAtAirportVO.setRemarks(getRemarks());
		uldAtAirportVO.setWarehouseCode(getWarehouseCode());
		uldAtAirportVO.setLocationCode(getLocationCode());
		uldAtAirportVO.setTransferFromCarrier(getTransferFromCarrier());

		// get DSNs
		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs = new ArrayList<MailbagInULDAtAirportVO>();
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				mailbagInULDAtAirportVOs
						.add(mailbagInULDAtAirport.retrieveVO());
			}
			uldAtAirportVO
					.setMailbagInULDAtAirportVOs(mailbagInULDAtAirportVOs);
		}

		// get onward routing
		// Collection<OnwardRoutingAtAirport> onwardRoutes =
		// getOnwardRoutingsAtAirport();
		// if(onwardRoutes != null && onwardRoutes.size() > 0) {
		// Collection<OnwardRoutingAtAirportVO> onwardRouteVOs =
		// new ArrayList<OnwardRoutingAtAirportVO>();
		// for(OnwardRoutingAtAirport onwardRoute : onwardRoutes) {
		// onwardRouteVOs.add(onwardRoute.retrieveVO());
		// }
		// uldAtAirportVO.setOnwardRoutingAtAirportVOs(onwardRouteVOs);
		// }
		return uldAtAirportVO;
	}

	/**
	 * @author a-1936 This method is used to constructMailBagInULDAirport
	 * @param mailbagVo
	 * @param toDestinationVO
	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportVO constructMailBagInULDAirport(
			MailbagVO mailbagVo, ContainerVO toDestinationVO) throws SystemException {
		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		mailbagInULDAtAirportVO.setContainerNumber(toDestinationVO
				.getContainerNumber());
		mailbagInULDAtAirportVO.setMailId(mailbagVo.getMailbagId());

		if(mailbagVo.getMailSequenceNumber()>0){
		mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
		}else{
			mailbagInULDAtAirportVO
			.setMailSequenceNumber(findMailSequenceNumber(mailbagVo
					.getMailbagId(),mailbagVo.getCompanyCode()));
		}
		mailbagInULDAtAirportVO.setDamageFlag(mailbagVo.getDamageFlag());
		mailbagInULDAtAirportVO.setWeight(mailbagVo.getWeight());
		mailbagInULDAtAirportVO.setScannedDate(new LocalDate(toDestinationVO
				.getAssignedPort(), Location.ARP, true));
		mailbagInULDAtAirportVO.setMailClass(mailbagVo.getMailClass());
		mailbagInULDAtAirportVO.setTransferFromCarrier(mailbagVo
				.getTransferFromCarrier());
		return mailbagInULDAtAirportVO;
	}

	/**
	 * @author a-1936 This method is used to constructMailBagInULDAirport
	 * @param mailbagVo
	 * @param toDestinationVO
	 * @return
	 */
	private MailbagInULDAtAirportVO constructMailBagInULDAirportForDespatch(
			DespatchDetailsVO despatchDetailsVO, ContainerVO toDestinationVO,MailbagVO mailbagvo) {
		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		mailbagInULDAtAirportVO.setContainerNumber(toDestinationVO
				.getContainerNumber());
		mailbagInULDAtAirportVO.setMailId(mailbagvo.getMailbagId());
		mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		// mailbagInULDAtAirportVO.setDamageFlag(despatchDetailsVO.getDamageFlag());
		/*mailbagInULDAtAirportVO
				.setWeight(despatchDetailsVO.getAcceptedWeight());*/
		mailbagInULDAtAirportVO
				.setWeight(despatchDetailsVO.getAcceptedWeight());
		mailbagInULDAtAirportVO.setScannedDate(new LocalDate(toDestinationVO
				.getAssignedPort(), Location.ARP, true));
		mailbagInULDAtAirportVO.setMailClass(mailbagvo.getMailClass());
		/*
		 * Added By Karthick V The Transfer From Carrier has to be set up from
		 * the Reassigned Mail Bag Details ..
		 */
		// mailbagInULDAtAirportVO.setTransferFromCarrier(despatchDetailsVO.getTransferFromCarrier());
		return mailbagInULDAtAirportVO;
	}

	public static String createMailBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder mailBagid = new StringBuilder();
		mailBagid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode())
				.append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear())
				.append(despatchDetailsVO.getDsn());
		return mailBagid.toString();
	}
	public static String createDespatchBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode())
				.append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear())
				.append(despatchDetailsVO.getDsn());
		return dsnid.toString();
	}
	/**
	 * @author a-1936 This method is used to reassign the Despatches to the
	 *         Destination
	 * @param despatchDetailVos
	 * @param toContainerVO
	 * @param dsnAtAirportMap
	 * @throws SystemException
	 */
	public void reassignDSNsToDestination(
			Collection<DespatchDetailsVO> despatchDetailVos,
			ContainerVO toContainerVO) throws SystemException {

		if (despatchDetailVos != null && despatchDetailVos.size() > 0) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailVos) {


				MailbagVO mailbagVO  = constructMailbagInULDAtAirportvoFromDespatch(despatchDetailsVO);
				Collection<MailbagVO> mailbagVOs=findMailBagForDespatch(mailbagVO);
				if(mailbagVOs!=null && mailbagVOs.size()>0){
					for(MailbagVO mailbagvo : mailbagVOs ){
						MailbagInULDAtAirportPK mailBagInUldAtAirportPK = constructMailbagInULDAtAirportPKFromDespatch(mailbagvo);
						MailbagInULDAtAirport mailbagInULDAtAirport = null;
						try {
							mailbagInULDAtAirport = MailbagInULDAtAirport
									.find(mailBagInUldAtAirportPK);
						} catch (FinderException ex) {
							MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailBagInULDAirportForDespatch(
									despatchDetailsVO, toContainerVO,mailbagvo);
							// mailbagInULDAtAirportVO.setPltEnabledFlag(MailConstantsVO.FLAG_YES);
							mailbagInULDAtAirport = new MailbagInULDAtAirport(
									getUldAtAirportPK(), mailbagInULDAtAirportVO);
						}
					}
				}
			}

		}

		log.exiting("ULDAtAirport", "reassignDSNsToDestination");

	}

	/**
	 * @author a-1936 This method is used to construct the
	 *         MailbagInULDAtAirportPK
	 * @param mailbagVo
	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportPK constructMailbagInULDAtAirportPKFromDespatch(
			MailbagVO mailbagvo) throws SystemException {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(mailbagvo
				.getCompanyCode());
		mailbagInULDAtAirportPK
				.setUldNumber(mailbagvo.getUldNumber());
		mailbagInULDAtAirportPK.setAirportCode(mailbagvo
				.getScannedPort());
		mailbagInULDAtAirportPK
				.setCarrierId(getUldAtAirportPK().getCarrierId());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		// Added to Include the DSN PK

		return mailbagInULDAtAirportPK;

	}
	/**
	 * @author a-1936 This method is used to construct the
	 *         MailbagInULDAtAirportPK
	 * @param mailbagVo
	 * @return
	 * @throws SystemException
	 */
	private MailbagVO constructMailbagInULDAtAirportvoFromDespatch(
			DespatchDetailsVO despatchDetailsVO) throws SystemException {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getUldAtAirportPK()
				.getCompanyCode());
		mailbagVO
				.setUldNumber(getUldAtAirportPK().getUldNumber());
		mailbagVO.setScannedPort(getUldAtAirportPK()
				.getAirportCode());
		mailbagVO
				.setCarrierId(getUldAtAirportPK().getCarrierId());
		mailbagVO.setDespatchId(
				createDespatchBag(despatchDetailsVO));
		// Added to Include the DSN PK

		return mailbagVO;

	}

	/**
	 * A-1739
	 *
	 * @param containerNumber
	 * @throws SystemException
	 */
	public Collection<MailbagInULDAtAirportVO> reassignBulkContainer(
			String containerNumber) throws SystemException {

		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		Collection<MailbagInULDAtAirportVO> mailbagInULDVOs = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				if (containerNumber != null
						&& containerNumber
								.equalsIgnoreCase(mailbagInULDAtAirport
										.getContainerNumber())) {
					mailbagInULDVOs.add(mailbagInULDAtAirport.retrieveVO());
					mailbagInULDAtAirport.remove();
				}
			}

		}
//		Collection<DSNInULDAtAirportVO> dsnInULDAtAirportVOs = new ArrayList<DSNInULDAtAirportVO>();
//		DSNInULDAtAirportVO dsnInULDAtAirportVO = new DSNInULDAtAirportVO();
//		dsnInULDAtAirportVO.setMailbagInULDVOs(mailbagInULDVOs);
//		dsnInULDAtAirportVOs.add(dsnInULDAtAirportVO);

		return mailbagInULDVOs;
	}

	public Collection<MailbagInULDAtAirportVO> reassignBulkContainerFormail(
			String containerNumber) throws SystemException {

		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		Collection<MailbagInULDAtAirportVO> mailbagInULDVOs = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				if (containerNumber != null
						&& containerNumber
								.equalsIgnoreCase(mailbagInULDAtAirport
										.getContainerNumber())) {
					mailbagInULDVOs.add(mailbagInULDAtAirport.retrieveVO());
					mailbagInULDAtAirport.remove();
				}
			}

		}
		return mailbagInULDVOs;
	}

	/**
	 * @author A-1739
	 *
	 * @param dsnInULDAtArp
	 * @throws SystemException
	 */
	public void assignBulkContainer(
			Collection<MailbagInULDAtAirportVO> mailbags)
			throws SystemException {
		if (getMailbagInULDAtAirports() == null) {
			mailbagInULDAtAirports = new HashSet<MailbagInULDAtAirport>();
		}
		ULDAtAirportPK uldAtArpPK = getUldAtAirportPK();
		//for (MailbagInULDAtAirportVO dsnInULDVO : dsnInULDAtArp) {
//			Collection<MailbagInULDAtAirportVO> mailbags = dsnInULDVO
//					.getMailbagInULDVOs();
			for (MailbagInULDAtAirportVO mailbagInULDVO : mailbags) {

					mailbagInULDAtAirports.add(new MailbagInULDAtAirport(
							uldAtArpPK, mailbagInULDVO));


			}

		//}
	}

	/**
	 * @author a-1936 This method is used to construct the
	 *         MailbagInULDAtAirportPK
	 * @param mailbagVo
	 * @return
	 */
	/*private MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(
			ULDAtAirportPK uldAtArpPK, DSNInULDAtAirportVO dsnInULDVO) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(uldAtArpPK.getCompanyCode());
		mailbagInULDAtAirportPK.setUldNumber(uldAtArpPK.getUldNumber());
		mailbagInULDAtAirportPK.setAirportCode(uldAtArpPK.getAirportCode());
		mailbagInULDAtAirportPK.setCarrierId(uldAtArpPK.getCarrierId());
		/*
		 * mailbagInULDAtAirportPK.setMailSequenceNumber(uldAtArpPK
		 * .getMailSequenceNumber());
		 */
		// mailbagInULDAtAirportPK.setDsn( dsnInULDVO.getDsn());
		// Added to Include the DSN PK
		// mailbagInULDAtAirportPK.setMailSubclass(
		// dsnInULDVO.getMailSubclass());
		/*
		 * mailbagInULDAtAirportPK.setMailCategoryCode(
		 * dsnInULDVO.getMailCategoryCode());

		// mailbagInULDAtAirportPK.setOriginExchangeOffice(dsnInULDVO.getOriginOfficeOfExchange());
		// mailbagInULDAtAirportPK.setYear( dsnInULDVO.getYear());
		// mailbagInULDAtAirportPK.setMailId( dsnInULDVO.getMailbagId());
		return mailbagInULDAtAirportPK;

	}*/

	/**
	 * @author a-1883
	 * @param dSNInULDForSegmentVOs
	 * @throws SystemException
	 */
	public void saveDestAssignedDetailsForTransfer(
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs)
			throws SystemException {
		log.entering("ULDAtAirport", "saveDestAssignedDetailsForTransfer");

		// Null check added by A-5945 for ICRD-100733

				if (mailbagInULDForSegmentVOs != null
						&& mailbagInULDForSegmentVOs.size() > 0) {
					for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagInULDForSegmentVOs) {
						MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailbagArpVO(mailbagInULDForSegmentVO);
						MailbagInULDAtAirportPK mailbagInULDAtAirportPK = constructMailbagInULDAtAirportPK(
								getUldAtAirportPK(), mailbagInULDAtAirportVO);
						try {
							MailbagInULDAtAirport mailbagInUldAtAirport = MailbagInULDAtAirport
									.find(mailbagInULDAtAirportPK);
						} catch (FinderException e) {
							new MailbagInULDAtAirport(getUldAtAirportPK(),
									mailbagInULDAtAirportVO);

						}

					}
				}



		log.exiting("ULDAtAirport", "saveDestAssignedDetailsForTransfer");
	}

	/**
	 * @author A-5991
	 * @param uldAtAirportPK
	 * @param mailbagInULDAtAirportVO
	 * @return
	 */
	public MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(
			ULDAtAirportPK uldAtAirportPK,
			MailbagInULDAtAirportVO mailbagInULDAtAirportVO) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(uldAtAirportPK.getCompanyCode());
		mailbagInULDAtAirportPK.setCarrierId(uldAtAirportPK.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(uldAtAirportPK.getAirportCode());
		mailbagInULDAtAirportPK.setUldNumber(uldAtAirportPK.getUldNumber());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagInULDAtAirportVO
				.getMailSequenceNumber());
		return mailbagInULDAtAirportPK;
	}

	/**
	 * @param mailbagSegmentVO
	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportVO constructMailbagArpVO(
			MailbagInULDForSegmentVO mailbagSegmentVO) throws SystemException {
		log.entering("DSNInULDAtAirport", "constructMailbagVO");
		MailbagInULDAtAirportVO mailbagArpVO = new MailbagInULDAtAirportVO();
		mailbagArpVO.setContainerNumber(mailbagSegmentVO.getContainerNumber());
		mailbagArpVO.setDamageFlag(mailbagSegmentVO.getDamageFlag());
		mailbagArpVO.setMailId(mailbagSegmentVO.getMailId());
		mailbagArpVO.setScannedDate(mailbagSegmentVO.getScannedDate());
		mailbagArpVO.setWeight(mailbagSegmentVO.getWeight());
		mailbagArpVO.setMailClass(mailbagSegmentVO.getMailClass());
		mailbagArpVO.setTransferFromCarrier(mailbagSegmentVO
				.getTransferFromCarrier());
		mailbagArpVO.setMailSequenceNumber(mailbagSegmentVO
				.getMailSequenceNumber());
		log.exiting("DSNInULDAtAirport", "constructMailbagVO");
		return mailbagArpVO;
	}

	/**
	 *
	 * @param mailbagVOs
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void saveDestAssignedMailsForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO)
			throws SystemException {
		log.entering("ULDAtAirport", "saveDestAssignedDetailsForTransfer");
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailbagInULDAtAirport mailbagInULDAtAirport = new MailbagInULDAtAirport(
					getUldAtAirportPK(), constructMailBagInULDAirport(
							mailbagVO, toContainerVO));
           if(mailbagVO.getTransferFromCarrier()!=null&&
               mailbagVO.getTransferFromCarrier().trim().length()>0){
			mailbagInULDAtAirport.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
           }
           else{
        	   if(!toContainerVO.isFoundTransfer()){
        	   mailbagInULDAtAirport.setTransferFromCarrier(mailbagVO.getCarrierCode()); 
        	   }
           }

			if (getMailbagInULDAtAirports() == null) {
				setMailbagInULDAtAirports(new HashSet<MailbagInULDAtAirport>());
			}
			getMailbagInULDAtAirports().add(mailbagInULDAtAirport);
		}
	}

	/**
	 * @author A-1739
	 * @param despatchesToReassign
	 * @throws SystemException
	 */
	/*
	 * public void reassignDSNsFromDestination( Collection<DespatchDetailsVO>
	 * despatchesToReassign) throws SystemException {
	 * log.entering("ULDAtAirport", "reassignDSNsFromDestination");
	 * Map<DSNInULDAtAirportPK, Collection<DespatchDetailsVO>> dsnDespatchMap =
	 * groupDespatches(despatchesToReassign); DSNInULDAtAirport dsnInULDAtArp =
	 * null; try { for (Map.Entry<DSNInULDAtAirportPK,
	 * Collection<DespatchDetailsVO>> dsnDespatch : dsnDespatchMap .entrySet())
	 * { DSNInULDAtAirportPK dsnInULDPK = dsnDespatch.getKey();
	 * Collection<DespatchDetailsVO> despatches = dsnDespatch .getValue();
	 * dsnInULDAtArp = DSNInULDAtAirport.find(dsnInULDPK);
	 * updateDSNInULDAtArpCount(despatches, dsnInULDAtArp, false);
	 * dsnInULDAtArp.reassignDSNsFromDestination(despatches); if
	 * (dsnInULDAtArp.getAcceptedBags() == 0) { log.log(Log.FINE,
	 * "removing dsninuldarp coz cnt 0"); dsnInULDAtArp.remove(); } } } catch
	 * (FinderException exception) { log.log(Log.SEVERE, "dsnULDArp not found");
	 * // throw new SystemException(exception.getMessage(), exception); }
	 * log.exiting("ULDAtAirport", "reassignDSNsFromDestination"); }
	 */
	/**
	 * A-1739
	 *
	 * @param despatchesToReassign
	 * @return
	 */
	// mail revamp
	/*
	 * private Map<DSNInULDAtAirportPK, Collection<DespatchDetailsVO>>
	 * groupDespatches( Collection<DespatchDetailsVO> despatchesToReassign) {
	 * Map<DSNInULDAtAirportPK, Collection<DespatchDetailsVO>> dsnDespatchMap =
	 * new HashMap<DSNInULDAtAirportPK, Collection<DespatchDetailsVO>>(); for
	 * (DespatchDetailsVO despatchDetailsVO : despatchesToReassign) {
	 * DSNInULDAtAirportPK dsnInULDPK =
	 * constructDSNInULDPKFromDespatch(despatchDetailsVO);
	 * Collection<DespatchDetailsVO> dsnDespatches = dsnDespatchMap
	 * .get(dsnInULDPK); if (dsnDespatches == null) { dsnDespatches = new
	 * ArrayList<DespatchDetailsVO>(); dsnDespatchMap.put(dsnInULDPK,
	 * dsnDespatches); } dsnDespatches.add(despatchDetailsVO); } return
	 * dsnDespatchMap; }
	 */
	/**
	 * A-1739
	 *
	 * @param despatchDetailsVO
	 * @return
	 */
	/*
	 * private DSNInULDAtAirportPK constructDSNInULDPKFromDespatch(
	 * DespatchDetailsVO despatchDetailsVO) { log.entering("ULDAtAirport",
	 * "constructDSNInULDPKFromDespatch"); DSNInULDAtAirportPK dsnInULDArpPK =
	 * new DSNInULDAtAirportPK(); uldAtAirportPK = getUldAtAirportPK();
	 * dsnInULDArpPK.setCompanyCode( uldAtAirportPK.getCompanyCode());
	 * dsnInULDArpPK.setAirportCode( uldAtAirportPK.getAirportCode());
	 * dsnInULDArpPK.setCarrierId( uldAtAirportPK.getCarrierId());
	 * dsnInULDArpPK.setUldNumber( uldAtAirportPK.getUldNumber());
	 * dsnInULDArpPK.setDsn(despatchDetailsVO.getDsn());
	 * dsnInULDArpPK.setOriginExchangeOffice( despatchDetailsVO
	 * .getOriginOfficeOfExchange());
	 * dsnInULDArpPK.setDestinationExchangeOffice( despatchDetailsVO
	 * .getDestinationOfficeOfExchange()); //Added to include the DSN PK
	 * dsnInULDArpPK.setMailSubclass( despatchDetailsVO.getMailSubclass());
	 * dsnInULDArpPK.setMailCategoryCode(
	 * despatchDetailsVO.getMailCategoryCode()); dsnInULDArpPK.setYear(
	 * despatchDetailsVO.getYear()); log.exiting("ULDAtAirport",
	 * "constructDSNInULDPKFromDespatch"); return dsnInULDArpPK; }
	 */
	/**
	 * A-1739
	 *
	 * @param despatches
	 * @param dsnInULDAtArp
	 * @param isAddition
	 *            TODO
	 */
	/*
	 * private void updateDSNInULDAtArpCount( Collection<DespatchDetailsVO>
	 * despatches, DSNInULDAtAirport dsnInULDAtArp, boolean isAddition) { int
	 * noAcpBags = 0; int noStatedBags = 0; double wtAcpBags = 0; double
	 * wtStatedBags = 0; for (DespatchDetailsVO despatchDetailsVO : despatches)
	 * { noAcpBags += despatchDetailsVO.getAcceptedBags(); wtAcpBags +=
	 * despatchDetailsVO.getAcceptedWeight(); noStatedBags +=
	 * despatchDetailsVO.getStatedBags(); wtStatedBags +=
	 * despatchDetailsVO.getStatedWeight(); } if (isAddition) {
	 * dsnInULDAtArp.setAcceptedBags(dsnInULDAtArp.getAcceptedBags() +
	 * noAcpBags);
	 * dsnInULDAtArp.setAcceptedWeight(dsnInULDAtArp.getAcceptedWeight() +
	 * wtAcpBags); dsnInULDAtArp.setStatedBags(dsnInULDAtArp.getStatedBags() +
	 * noStatedBags);
	 * dsnInULDAtArp.setStatedWeight(dsnInULDAtArp.getStatedWeight() +
	 * wtStatedBags); } else {
	 * dsnInULDAtArp.setAcceptedBags(dsnInULDAtArp.getAcceptedBags() -
	 * noAcpBags);
	 * dsnInULDAtArp.setAcceptedWeight(dsnInULDAtArp.getAcceptedWeight() -
	 * wtAcpBags); dsnInULDAtArp.setStatedBags(dsnInULDAtArp.getStatedBags() -
	 * noStatedBags);
	 * dsnInULDAtArp.setStatedWeight(dsnInULDAtArp.getStatedWeight() -
	 * wtStatedBags); } }
	 */

	/**
	 * @author a-1883
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void saveDamageDetailsForMailbags(Collection<MailbagVO> mailbagVOs)
			throws SystemException {
		log.entering("ULDAtAirport", "saveDamageDetailsForMailbags");
		for (MailbagVO mailbagVO : mailbagVOs) {
			updateMailbagInULDAtAirport(mailbagVO);
		}
		log.exiting("ULDAtAirport", "saveDamageDetailsForMailbags");
	}

	/**
	 * @author a-1883
	 * @param mailbagVO
	 * @throws SystemException
	 */
	private void updateMailbagInULDAtAirport(MailbagVO mailbagVO)
			throws SystemException {
		log.entering("DSNInULDAtAirport", "updateMailbagInULDAtAirport");
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDAtAirportPK.setCompanyCode(mailbagVO.getCompanyCode());
	    	 mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber()>0?mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		mailbagInULDAtAirportPK.setAirportCode(mailbagVO.getScannedPort());
		mailbagInULDAtAirportPK.setUldNumber(mailbagVO.getUldNumber());
		// Added By Karthick V to construct the BULK-POU formation based on the
		// Container Type ..
		mailbagInULDAtAirportPK.setUldNumber(mailbagVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			log.log(Log.INFO, "THE MAL BAG IS  ASSOCIATED WITH A BARROW");
				mailbagInULDAtAirportPK.setUldNumber(   constructBulkULDNumber(mailbagVO
							.getFinalDestination()));
			log.log(Log.INFO, "THE MAL BAG IS  ASSOCIATED WITH A BARROW",
					mailbagInULDAtAirportPK.getUldNumber());
		}
		try {
	    		MailbagInULDAtAirport mailbagInULDAtAirport = MailbagInULDAtAirport.
	    		find(mailbagInULDAtAirportPK);
			mailbagInULDAtAirport.setDamageFlag(MailbagVO.FLAG_YES);
		} catch (FinderException finderException) {
			log.log(Log.FINE, " MailbagInULDAtAirport Not existing ");
		}
		log.exiting("DSNInULDAtAirport", "updateMailbagInULDAtAirport");
	}

	/**
	 * A-1739
	 * @param pou
	 * @return
	 */
	private String constructBulkULDNumber(String pou) {
	        return new StringBuilder().
	        append(MailConstantsVO.CONST_BULK).
	        append(MailConstantsVO.SEPARATOR).
	        append(pou).toString();
	    }
	    /**
	     * @author A-5991
	     * @param mailbagVO
	     * @return
	     * @throws SystemException
	     */
		public MailbagPK constructMailbagPK(MailbagVO  mailbagVO) throws SystemException{
			MailbagPK  mailbagPK=new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber()>0?mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			return mailbagPK;
	}


	    /**
	     * @author a-1936
	     * This method is used to construct the MailbagInULDAtAirportPK
	     * @param mailbagVo
	     * @return
	     * @throws SystemException
	     */
	    /*private MailbagInULDAtAirportPK  constructMailbagInULDAtAirportPKFromDespatch(DespatchDetailsVO despatchDetailsVO) throws SystemException{
	   	 MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
	   	 mailbagInULDAtAirportPK.setCompanyCode(getUldAtAirportPK().getCompanyCode() );
	   	 mailbagInULDAtAirportPK.setUldNumber(getUldAtAirportPK().getUldNumber());
	   	 mailbagInULDAtAirportPK.setAirportCode(getUldAtAirportPK().getAirportCode() );
	   	 mailbagInULDAtAirportPK.setCarrierId(getUldAtAirportPK().getCarrierId());
	   	 mailbagInULDAtAirportPK.setMailSequenceNumber(findMailSequenceNumber(createMailBag(despatchDetailsVO),getUldAtAirportPK().getCompanyCode()));
	   	 //Added to Include the DSN PK

	   	 return  mailbagInULDAtAirportPK;

	   	 }*/
	
		/**
		 * @author A-8353
		 * @param mailbagVO
		 * @param mailbag
		 * @return
		 * @throws SystemException
		 * @throws DuplicateMailBagsException
		 */
	/*	private boolean checkForDuplicateMailbag(MailbagVO mailbagVO,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
	        PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailbagVO.getCompanyCode(), mailbagVO.getPaCode());
	        LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	        LocalDate dspDate = new LocalDate(
	                    LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);
			long seconds=currentDate.findDifference(dspDate);
	        float days=seconds/86400000;
	        if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
	        	throw new DuplicateMailBagsException(
	        			DuplicateMailBagsException.
	        			DUPLICATEMAILBAGS_EXCEPTION,
	        			new Object[] {mailbagVO.getMailbagId()});
	        }
	        return true;
	  }*/
	        
}
