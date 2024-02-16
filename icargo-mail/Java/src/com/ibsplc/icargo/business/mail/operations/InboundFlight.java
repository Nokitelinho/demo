/*
 * InboundFlight.java Created on Aug 3, 2006
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
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.InboundFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegment;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegmentPK;

import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 * 
 */
@Entity
@Table(name = "MTKINBFLT")
@Staleable
public class InboundFlight {

	private static final String MODULE_NAME = "mail.operations";
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private InboundFlightPK inboundFlightPK;

	private String carrierCode;

	private Calendar flightDate;

	private String closedFlag;

	//Added by A-5945 for ICRD-118205 starts
	 private Calendar lastUpdateTime;
		private String lastUpdateUser;
//Added by A-5945 for ICRD-118205 ends
	/**
	 * @return Returns the flightStatus.
	 */
	@Column(name = "CLSFLG")
	public String getClosedFlag() {
		return closedFlag;
	}

	/**
	 * @param flightStatus
	 *            The flightStatus to set.
	 */
	public void setClosedFlag(String flightStatus) {
		this.closedFlag = flightStatus;
	}

	public InboundFlight() {
	}

	public InboundFlight(InboundFlightVO inboundFlightVO)
			throws SystemException {
		log.entering("InboundFlight", "init");
		populatePK(inboundFlightVO);
		populateAttributes(inboundFlightVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("InboundFlight", "init");
	}

	/**
	 * A-1739
	 * 
	 * @param inboundFlightVO
	 */
	private void populatePK(InboundFlightVO inboundFlightVO) {
		inboundFlightPK = new InboundFlightPK();
		inboundFlightPK.setCompanyCode(   inboundFlightVO.getCompanyCode());
		inboundFlightPK.setCarrierId(   inboundFlightVO.getCarrierId());
		inboundFlightPK.setFlightNumber(   inboundFlightVO.getFlightNumber());
		inboundFlightPK.setFlightSequenceNumber(   inboundFlightVO
				.getFlightSequenceNumber());
		inboundFlightPK.setLegSerialNumber(   inboundFlightVO.getLegSerialNumber());
		inboundFlightPK.setAirportCode(   inboundFlightVO.getAirportCode());
	}

	/**
	 * A-1739
	 * 
	 * @param inboundFlightVO
	 */
	private void populateAttributes(InboundFlightVO inboundFlightVO) {
		setFlightDate(inboundFlightVO.getFlightDate().toCalendar());
		setCarrierCode(inboundFlightVO.getCarrierCode());
		setClosedFlag(inboundFlightVO.getClosedFlag());
	setLastUpdateTime(inboundFlightVO.getLastUpdateTime());
	setLastUpdateUser(inboundFlightVO.getLastUpdateUser());
	}

	/**
	 * @author A-1739
	 * @param inboundFlightPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static InboundFlight find(InboundFlightPK inboundFlightPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				InboundFlight.class, inboundFlightPK);
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
	 * @return Returns the flightDate.
	 */
	@Column(name = "FLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the inboundFlightPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD  ")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")) })
	public InboundFlightPK getInboundFlightPK() {
		return inboundFlightPK;
	}

	/**
	 * @param inboundFlightPk
	 *            The inboundFlightPk to set.
	 */
	public void setInboundFlightPK(InboundFlightPK inboundFlightPk) {
		this.inboundFlightPK = inboundFlightPk;
	}
    /**
     * @author a-1883
     * @param containerVOs
     * @param dSNMap
     * @param toFlightVO TODO
     * @return Collection<ULDForSegmentVO>
     * @throws SystemException
     */
    public Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(
            Collection<ContainerVO> containerVOs, 
            OperationalFlightVO toFlightVO,Map<MailbagPK,MailbagVO>mailMap) throws SystemException {
      log.entering("InboundFlight","saveArrivalDetailsForTransfer");  
      Collection<ULDForSegmentVO> allULDForSegmentVOs = new 
                      ArrayList<ULDForSegmentVO>(); 
      Map<AssignedFlightSegmentPK,Collection<ContainerVO>> assignedFlightMap =
          new HashMap<AssignedFlightSegmentPK,Collection<ContainerVO>>(); 
      
      Collection<ContainerVO> containers = null;
      /* Groups containers based on Flight segment */
        for(ContainerVO containerVO:containerVOs){
            AssignedFlightSegmentPK assignedFlightSegmentPK = new 
                            AssignedFlightSegmentPK();
            assignedFlightSegmentPK.setCarrierId(   containerVO.getCarrierId());
            assignedFlightSegmentPK.setCompanyCode(   containerVO.getCompanyCode());
            assignedFlightSegmentPK.setFlightNumber(   containerVO.getFlightNumber());
            assignedFlightSegmentPK.setFlightSequenceNumber(   containerVO.
                        getFlightSequenceNumber());
            assignedFlightSegmentPK.setSegmentSerialNumber(   containerVO.
                        getSegmentSerialNumber());
            containers = assignedFlightMap.get(assignedFlightSegmentPK);
            if( containers == null){
                containers = new ArrayList<ContainerVO>();
                assignedFlightMap.put(assignedFlightSegmentPK,containers);
            }
            containers.add(containerVO);
        }
        for(AssignedFlightSegmentPK assignedFlightSegmentPK:
                                    assignedFlightMap.keySet()){
            AssignedFlightSegment  assignedFlightSegment = null;
            try {
                assignedFlightSegment =  AssignedFlightSegment.find(assignedFlightSegmentPK);
            }catch(FinderException finderException){
                throw new SystemException(finderException.getErrorCode());
            }
            Collection<ULDForSegmentVO> ULDForSegmentVOs = 
            	assignedFlightSegment.saveArrivalDetailsForTransfer(
                    assignedFlightMap.get(assignedFlightSegmentPK), toFlightVO,mailMap);
            allULDForSegmentVOs.addAll(ULDForSegmentVOs);
        }
        log.exiting("InboundFlight","saveArrivalDetailsForTransfer");
        return allULDForSegmentVOs;
    }

	/**
     * @author a-1883
     * @param mailbagVOs
	 * @param containerVO
	 * @param dsnAtAirportMap 
     * @throws SystemException
     */
    public void saveMailArrivalDetailsForTransfer(
            Collection<MailbagVO> mailbagVOs,
            ContainerVO containerVO)
    throws SystemException {
        log.entering("InboundFlight","saveMailArrivalDetailsForTransfer");  
        Map<AssignedFlightSegmentPK,Collection<MailbagVO>> assignedFlightMap =
            new HashMap<AssignedFlightSegmentPK,Collection<MailbagVO>>(); 
        Collection<MailbagVO> mailbags = null;
        /* Groups mailbags based on Flight segment */
          for(MailbagVO mailbagVO:mailbagVOs){
        	  if(!MailbagVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){
        		  AssignedFlightSegmentPK assignedFlightSegmentPK = new 
                  AssignedFlightSegmentPK();
				  assignedFlightSegmentPK.setCarrierId(   mailbagVO.getCarrierId());
				  assignedFlightSegmentPK.setCompanyCode(   mailbagVO.getCompanyCode());
				  assignedFlightSegmentPK.setFlightNumber(   mailbagVO.getFlightNumber());
				  assignedFlightSegmentPK.setFlightSequenceNumber(   mailbagVO.
				              getFlightSequenceNumber());
				  assignedFlightSegmentPK.setSegmentSerialNumber(   mailbagVO.
				              getSegmentSerialNumber());
				  mailbags = assignedFlightMap.get(assignedFlightSegmentPK);
				  if(mailbags == null){
				      mailbags = new ArrayList<MailbagVO>();
				      assignedFlightMap.put(assignedFlightSegmentPK,mailbags);
				  }
				  mailbags.add(mailbagVO);
        	  }
              
          }
          for(AssignedFlightSegmentPK assignedFlightSegmentPK:
                  assignedFlightMap.keySet()){
              AssignedFlightSegment  assignedFlightSegment = null;
              try {
                  assignedFlightSegment =  AssignedFlightSegment.find(assignedFlightSegmentPK);
              }catch(FinderException finderException){
                  throw new SystemException(finderException.getErrorCode());
              }
               assignedFlightSegment.saveMailArrivalDetailsForTransfer(
                      assignedFlightMap.get(assignedFlightSegmentPK), containerVO);
          }
          log.exiting("InboundFlight","saveMailArrivalDetailsForTransfer");
    }
    
    /**
     * This method returns the Instance of the DAO
     * @return
     * @throws SystemException
     */
    private static MailTrackingDefaultsDAO constructDAO()
    throws SystemException {
    	try {
    		EntityManager em = PersistenceController.getEntityManager();
    		return MailTrackingDefaultsDAO.class.cast(em.
    				getQueryDAO(MODULE_NAME));
    	}
    	catch(PersistenceException persistenceException) {
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    /**
     * @author a-1883
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
    public static Collection<MailDiscrepancyVO> findMailDiscrepancies(
    		OperationalFlightVO operationalFlightVO)throws SystemException{
    	try{
    		return constructDAO().findMailDiscrepancies(operationalFlightVO);
    	 }
    	 catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	 }
    }
    /**
     * Finds the ULDs in a InboundFlight
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
    public static Collection<ContainerVO> findULDsInInboundFlight(OperationalFlightVO 
    		operationalFlightVO)throws SystemException{
    	try{
    		return constructDAO().findULDsInInboundFlight(operationalFlightVO);
    	 }
    	 catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	 }
    }
    /**
     * 
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
    public static Page<OperationalFlightVO> findMailFlightDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException{
    	try{
    		return constructDAO().findMailFlightDetails(operationalFlightVO);
    	}
    	catch (PersistenceException persistenceException) {
    		throw new SystemException(persistenceException.getErrorCode());
		}
    }
    /**
     * For Reconciliation
     * @param flag
     * @throws SystemException
     */
    public void updateForReconciliation(String flag,OperationalFlightVO operationalFlightVO) 
    	throws SystemException{
    	this.setClosedFlag(flag);
    }
    
    /**
     * 
     * @param operationalFlightVO
     * @return
     */
    public static MailArrivalFilterVO constructMailArrivalFilterVO(
			OperationalFlightVO operationalFlightVO) {
    	
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(
				operationalFlightVO.getFlightSequenceNumber());
		filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		filterVO.setPou(operationalFlightVO.getPou());
		//setting ALL for default work. change to appropirate
		filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		
		return filterVO;
	}
    

	/**
	 * @return Returns the lastUpdateTime.
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
}
