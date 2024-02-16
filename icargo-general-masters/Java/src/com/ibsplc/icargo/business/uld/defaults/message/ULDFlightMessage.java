/*
 * ULDFlightMessage.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;

import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageFilterVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;


/**  
 * @author A-1945
 *
 */
@Table(name = "ULDFLTMSG")
@Entity
public class ULDFlightMessage {

    private ULDFlightMessagePK uldFlightMessagePK;

    private Calendar flightDate;

    private String lastUpdateUser;

    private Calendar lastUpdateTime;

    private String legOrigin;

    private String legDestination;

    private String aircraftRegistration;

    private Set<ULDFlightMessageDetails> uldFlightMessageDetails;
    
    private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger(" ULD DEFAULTS");
    /**
     *
     * @return
     */
    @EmbeddedId
	@AttributeOverrides({
    @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
    @AttributeOverride(name = "stationCode", column = @Column(name = "ARPCOD")),
    @AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")),
    @AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
    @AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
    @AttributeOverride(name = "flightCarrierId", column = @Column(name = "FLTCARIDR"))})
    public ULDFlightMessagePK getUldFlightMessagePK() {
        return uldFlightMessagePK;
    }
    /**
     *
     * @param uldFlightMessagePK
     */
    public void setUldFlightMessagePK(ULDFlightMessagePK uldFlightMessagePK) {
        this.uldFlightMessagePK = uldFlightMessagePK;
    }
    /**
     *
     * @return
     */
    @Column(name = "FLTDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getFlightDate() {
        return flightDate;
    }
    /**
     *
     * @param flightDate
     */
    public void setFlightDate(Calendar flightDate) {
        this.flightDate = flightDate;
    }

    /**
     *
     * @return
     */
    @Column(name = "LEGDST")
    public String getLegDestination() {
        return legDestination;
    }
    /**
     *
     * @param legDestination
     */
    public void setLegDestination(String legDestination) {
        this.legDestination = legDestination;
    }

    /**
     *
     * @return
     */
    @Column(name = "LEGORG")
    public String getLegOrigin() {
        return legOrigin;
    }

    /**
     *
     * @param legOrigin
     */
    public void setLegOrigin(String legOrigin) {
        this.legOrigin = legOrigin;
    }

    /**
     *
     * @return
     */
    @Column(name = "ACRREG")
    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    /**
     *
     * @param aircraftRegistration
     */
    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    /**
     *
     * @return
     */
    @Column(name = "LSTUPDUSR")
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    /**
     *
     * @param lastUpdateUser
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    /**
     *
     * @return
     */
    @Version
    @Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     *
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    /**
     *
     * @return
     */
    @OneToMany
    @JoinColumns({
    @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
    @JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable = false, updatable = false),
    @JoinColumn(name = "LEGSERNUM", referencedColumnName = "LEGSERNUM", insertable = false, updatable = false),
    @JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
    @JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
    @JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false)})
    public Set<ULDFlightMessageDetails> getUldFlightMessageDetails() {
        return uldFlightMessageDetails;
    }
    /**
     *
     * @param uldFlightMessageDetails
     */
    public void setUldFlightMessageDetails(
            Set<ULDFlightMessageDetails> uldFlightMessageDetails) {
        this.uldFlightMessageDetails = uldFlightMessageDetails;
    }

    /**
     *
     *
     */
    public ULDFlightMessage() {
    }
/***
 * 
 * @param uldFlightMessageVO
 * @throws SystemException
 */
    public ULDFlightMessage(ULDFlightMessageVO uldFlightMessageVO)
            throws SystemException {
        populatePK(uldFlightMessageVO);
        populateAttributes(uldFlightMessageVO);
        try {
            PersistenceController.getEntityManager().persist(this);
        } catch(CreateException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
        populateChildren(uldFlightMessageVO);
    }

    private void populateChildren(ULDFlightMessageVO uldFlightMessageVO)
            throws SystemException {
        if(uldFlightMessageVO.getUldFlightMessageDetailsVOs() != null &&
                !uldFlightMessageVO.getUldFlightMessageDetailsVOs().isEmpty()) {
            populateULDFlightMessageDetails(uldFlightMessageVO);
        }
    }

    private void populateULDFlightMessageDetails(
            ULDFlightMessageVO uldFlightMessageVO) throws SystemException {
        for(ULDFlightMessageDetailsVO uldFlightMessageDetailsVO :
                uldFlightMessageVO.getUldFlightMessageDetailsVOs()) {
            ULDFlightMessageDetails flightMessageDetails =
                    new ULDFlightMessageDetails(uldFlightMessageDetailsVO,
                            uldFlightMessagePK);
            if(getUldFlightMessageDetails() == null) {
                setUldFlightMessageDetails(new HashSet<ULDFlightMessageDetails>());
            }
            getUldFlightMessageDetails().add(flightMessageDetails);
        }
    }

    private void populateAttributes(ULDFlightMessageVO uldFlightMessageVO)
            throws SystemException {
        LogonAttributes logonAttributes =
                ContextUtils.getSecurityContext().getLogonAttributesVO();
        setLastUpdateUser(logonAttributes.getUserId());
        setLegDestination(uldFlightMessageVO.getLegDestination());
        setLegOrigin(uldFlightMessageVO.getLegOrigin());
        setFlightDate(uldFlightMessageVO.getFlightDate().toCalendar());
        setAircraftRegistration(uldFlightMessageVO.getAircraftRegistration());
    }

    private void populatePK(ULDFlightMessageVO uldFlightMessageVO) {
        setUldFlightMessagePK(new ULDFlightMessagePK(
                uldFlightMessageVO.getCompanyCode(),
                uldFlightMessageVO.getStationCode(),
                uldFlightMessageVO.getLegSerialNumber(),
                uldFlightMessageVO.getFlightSequenceNumber(),
                uldFlightMessageVO.getFlightNumber(),
                uldFlightMessageVO.getFlightCarrierId()));
    }
    
    /**
	 * This method returns the ULDDefaultsDAO.
	 * 
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     */
    public static UCMMessageVO generateUCMMessageVO(FlightMessageFilterVO filterVO)
	 throws SystemException{
    	try {
			return constructDAO().generateUCMMessageVO(filterVO);
					
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
    }

    /**
     *
     * @param uldFlightMessageFilterVO
     * @return
     * @throws SystemException
     */
    public static boolean validateULDFlightMessageDetails(
            ULDFlightMessageFilterVO uldFlightMessageFilterVO)
            throws SystemException {
        try {
            return constructDAO().validateULDFlightMessageDetails(uldFlightMessageFilterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    /**
     * 
     * @param flightMessageFilterVO
     * @return
     * @throws SystemException
     */
    public static FlightDetailsVO findUCMFlightDetails(FlightMessageFilterVO 
			flightMessageFilterVO)throws SystemException{
    	try {
            return constructDAO().findUCMFlightDetails(flightMessageFilterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    
    /**
     * @author A-2667
     * @param flightMessageFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<FlightDetailsVO> findCPMULDDetails(FlightMessageFilterVO 
			flightMessageFilterVO)throws SystemException{
    	try {
            return constructDAO().findCPMULDDetails(flightMessageFilterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    
    
/**
 * 
 * @param vo
 * @return
 * @throws SystemException
 */
	public static ULDFlightMessage find(
			ULDFlightMessageVO vo)
	        throws SystemException {
		ULDFlightMessagePK uldFlightMessagenewPK = new  ULDFlightMessagePK();
		
		uldFlightMessagenewPK.setCompanyCode(  vo.getCompanyCode());
		uldFlightMessagenewPK.setStationCode(   vo.getStationCode());
		uldFlightMessagenewPK.setLegSerialNumber(   vo.getLegSerialNumber());
		uldFlightMessagenewPK.setFlightSequenceNumber(   vo.getFlightSequenceNumber());
		uldFlightMessagenewPK.setFlightNumber(   vo.getFlightNumber());
		uldFlightMessagenewPK.setFlightCarrierId(   vo.getFlightCarrierId());

		
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDFlightMessage.class,
					uldFlightMessagenewPK);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode());
			
		}
	}
	
	
	
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode());
		} catch (OptimisticConcurrencyException e) {
			throw new SystemException(e.getMessage());
		}
	}	

    
}
