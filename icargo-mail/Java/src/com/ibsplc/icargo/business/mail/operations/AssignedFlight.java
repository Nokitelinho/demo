/*
 * AssignedFlight.java Created on June 27, 2016
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
import javax.persistence.Version;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegment;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.entity.DefaultValue;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3109 This class represents the flight to which mail is accepted.
 *         This is at a higher level.Segment details are not stored here.
 */

@Entity
@Table(name = "MALFLT")
public class AssignedFlight {

	private static final String PRODUCT_NAME = "mail.operations";
	private AssignedFlightPK assignedFlightPk;

	/**
	 * Carrier Code
	 */
	private String carrierCode;

	/**
	 * Flight Date
	 */
	private Calendar flightDate;

	/**
	 * Operationally closed/finalised etc
	 */
	private String flightStatus;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;
	
	private String exportClosingFlag;
	
	private String importClosingFlag;
	
	private String flownAccountingStatus;

	private String gateClearanceStatus;
	/**
	 * @return the flownStatus
	 */
	@Column(name = "FLNACCSTA")
	public String getFlownStatus() {
		return flownAccountingStatus;
	}
	/**
	 * @param flownStatus the flownStatus to set
	 */
	public void setFlownStatus(String flownStatus) {
		this.flownAccountingStatus = flownStatus;
	}

	/**
	 * @return the exportClosingFlag
	 */
	@Column(name = "EXPCLSFLG")
	@Audit(name = "exportClosingFlag")
	public String getExportClosingFlag() {
		return exportClosingFlag;
	}

	/**
	 * @param exportClosingFlag the exportClosingFlag to set
	 */
	public void setExportClosingFlag(String exportClosingFlag) {
		this.exportClosingFlag = exportClosingFlag;
	}

	/**
	 * @return the importClosingFlag
	 */
	@Column(name = "IMPCLSFLG")
	public String getImportClosingFlag() {
		return importClosingFlag;
	}

	/**
	 * @param importClosingFlag the importClosingFlag to set
	 */
	public void setImportClosingFlag(String importClosingFlag) {
		this.importClosingFlag = importClosingFlag;
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
	@Column(name = "GTECLRSTA")
	@DefaultValue("N")
	public String getGateClearanceStatus() {
		return gateClearanceStatus;
	}

	public void setGateClearanceStatus(String gateClearanceStatus) {
		this.gateClearanceStatus = gateClearanceStatus;
	}

/*	*//**
	 * @return Returns the flightStatus.
	 *//*
	@Column(name = "CLSFLG")
	@Audit(name = "flightStatus")
	public String getFlightStatus() {
		return flightStatus;
	}

	*//**
	 * @param flightStatus
	 *            The flightStatus to set.
	 *//*
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
*/
	/**
	 * The default constructor
	 * 
	 */
	public AssignedFlight() {
	}

	/**
	 * @return Returns the assignedFlightPk.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")) })
	public AssignedFlightPK getAssignedFlightPk() {
		return assignedFlightPk;
	}

	/**
	 * @param assignedFlightPk
	 *            The assignedFlightPk to set.
	 */
	public void setAssignedFlightPk(AssignedFlightPK assignedFlightPk) {
		this.assignedFlightPk = assignedFlightPk;
	}

	/**
	 * @author a-3109 This method is used to insert the new record in the Entity
	 * @param assignedFlightVO
	 * @throws SystemException
	 */
	public AssignedFlight(AssignedFlightVO assignedFlightVO)
			throws SystemException {
		populatePK(assignedFlightVO);
		populateAttributes(assignedFlightVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}

	}

	/**
	 * @author a-3109 This method is used to populate the PK
	 * @param assignedFlightVO
	 * @throws SystemException
	 */
	private void populatePK(AssignedFlightVO assignedFlightVO)
			throws SystemException {

		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setAirportCode(assignedFlightVO.getAirportCode());
		assignedFlightPK.setCompanyCode(assignedFlightVO.getCompanyCode());
		assignedFlightPK.setCarrierId(assignedFlightVO.getCarrierId());
		assignedFlightPK.setFlightNumber(assignedFlightVO.getFlightNumber());
		assignedFlightPK.setLegSerialNumber(assignedFlightVO
				.getLegSerialNumber());
		assignedFlightPK.setFlightSequenceNumber(assignedFlightVO
				.getFlightSequenceNumber());
		this.setAssignedFlightPk(assignedFlightPK);

	}

	/**
	 * @author a-3109 This method is used to populate the Attributes
	 * @param assignedFlightVO
	 * @throws SystemException
	 */

	private void populateAttributes(AssignedFlightVO assignedFlightVO)
			throws SystemException {
		this.setCarrierCode(assignedFlightVO.getCarrierCode());
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		if (assignedFlightVO.getFlightDate() != null) {
			this.setFlightDate(assignedFlightVO.getFlightDate().toCalendar());
		}
		if(this.getCarrierCode() == null){
			this.setCarrierCode(logonAttributes.getOwnAirlineCode());
		}
		//this.setExportClosingFlag(assignedFlightVO.getFlightStatus());
		if(assignedFlightVO.getFlightStatus()!=null){
		this.setExportClosingFlag(assignedFlightVO.getFlightStatus());
		}else{
		this.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_OPEN);	
		}
		this.setImportClosingFlag(assignedFlightVO.getImportFlightStatus());
		this.setFlownStatus("N");
		if(assignedFlightVO.getLastUpdateUser()!=null){
		this.lastUpdateUser = assignedFlightVO.getLastUpdateUser();
		}
		else{
			this.lastUpdateUser=logonAttributes.getUserName();
		}
		setGateClearanceStatus(MailConstantsVO.FLAG_NO);
	}

	/**
	 * 
	 * @author a-3109 This method is used to find the instance of this Entity
	 * @param assignedFlightPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static AssignedFlight find(AssignedFlightPK assignedFlightPK)
			throws SystemException, FinderException {

		Log looger = LogFactory.getLogger("mail.operations");
		looger.log(Log.FINE, "airport code" + assignedFlightPK.getAirportCode());
		looger.log(Log.FINE, "companyCode" + assignedFlightPK.getCompanyCode());
		looger.log(Log.FINE,
				"flightNumber" + assignedFlightPK.getFlightNumber());
		looger.log(
				Log.FINE,
				"flightSequenceNumber"
						+ assignedFlightPK.getFlightSequenceNumber());
		looger.log(Log.FINE,
				"legSerialNumber" + assignedFlightPK.getLegSerialNumber());
		looger.log(Log.FINE, "carrierId" + assignedFlightPK.getCarrierId());
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(AssignedFlight.class, assignedFlightPK);

	}

	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * @author A-1883
	 * @param operationalFlightVO
	 * @param consignmentKey
	 *            TODO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailDetailVO> findMailbagDetailsForReport(
			OperationalFlightVO operationalFlightVO, String consignmentKey)
			throws SystemException {
		return MailbagInULDForSegment.findMailbagDetailsForReport(
				operationalFlightVO, consignmentKey);
	}

	/**
	 * 
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<OperationalFlightVO> findMailFlightDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findMailFlightDetails(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-5160 Added for ICRD-90823
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<FlightValidationVO> validateMailFlight(
			FlightFilterVO flightFilterVO) throws SystemException {
		try {
			return constructDAO().validateMailFlight(flightFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-3227
	 * @param operationalFlightVO
	 * @return
	 */
	public static boolean checkRoutingsForMails(
			OperationalFlightVO operationalFlightVO, DSNVO dSNVO, String type)
			throws SystemException {
		try {
			return constructDAO().checkRoutingsForMails(operationalFlightVO,
					dSNVO, type);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Finds the ULDs in an Assigned Flight
	 * 
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findULDsInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findULDsInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
     * Finds the Containers in an Assigned Flight
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
    public static Collection<ContainerVO> findContainersInAssignedFlight(OperationalFlightVO
    		operationalFlightVO)throws SystemException{
    	try{
    		return constructDAO().findContainersInAssignedFlight(operationalFlightVO);
    	 }
    	 catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	 }
    }
	
	/**
	 *
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public static Collection<AWBDetailVO> findAWBDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findAWBDetailsForFlight(operationalFlightVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getErrorCode(), exception);
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
        	  else {
        		  new MailbagInULDForSegment().updateTransferFlagForPreviousSegment(mailbagVO);
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
    }
    /**
     * Finds if any containers in a Assigned Flight
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
    public static String findAnyContainerInAssignedFlight(OperationalFlightVO operationalFlightVO)throws SystemException{
    	try{
    		return constructDAO().findAnyContainerInAssignedFlight(operationalFlightVO);
    	 }
    	 catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	 }

    }
    /**
	  * @author A-5166
	  * Added for ICRD-36146 on 07-Mar-2013
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode)
	 throws SystemException{
			//staticLogger().entering("AssignedFlight","findImportFlghtsForArrival");		
			return AssignedFlightSegment.findImportFlghtsForArrival(companyCode);
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
     * @author A-5526
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     */
	public static Collection<ContainerVO> findEmptyULDsInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
	
    		return constructDAO().findEmptyULDsInAssignedFlight(operationalFlightVO);
    	
    	
    }
    
    /**
     * 
     * 	Method		:	AssignedFlight.listFlightDetails
     *	Added by 	:	A-8164 on 25-Sep-2018
     * 	Used for 	:
     *	Parameters	:	@param mailArrivalVO
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws PersistenceException 
     *	Return type	: 	Collection<MailArrivalVO>
     */
    public static Page<MailArrivalVO> listFlightDetails(
    		MailArrivalVO mailArrivalVO) throws SystemException, PersistenceException {
		return constructDAO().listFlightDetails(mailArrivalVO);
	}
    
    public static Collection<MailArrivalVO> listManifestDetails(
    	MailArrivalVO mailArrivalVO) throws SystemException, PersistenceException {
    		return constructDAO().listManifestDetails(mailArrivalVO);
    	
    }
    /**
     * 
     * @param containerVO
     */
	public void releaseContainer(ContainerVO containerVO) {
		Container con = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(this.getAssignedFlightPk().getCompanyCode());
		containerPK.setAssignmentPort(this.getAssignedFlightPk().getAirportCode());
		containerPK.setCarrierId(this.getAssignedFlightPk().getCarrierId());
		containerPK.setFlightNumber(this.getAssignedFlightPk().getFlightNumber());
		containerPK.setFlightSequenceNumber(this.getAssignedFlightPk().getFlightSequenceNumber());
		containerPK.setLegSerialNumber(this.getAssignedFlightPk().getLegSerialNumber());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		try {
			con = Container.find(containerPK);
		} catch (FinderException | SystemException e) {
			Log looger = LogFactory.getLogger("mail.operations");
			looger.log(Log.FINE, "Exception: while updating transit flag");
		}
		if (con != null) {
			con.setTransitFlag(MailConstantsVO.FLAG_NO);
		}
	}
    /**
	 * Finds all the containers in an Assigned Flight
	 * 
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findAllContainersInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {
			return constructDAO().findAllContainersInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
