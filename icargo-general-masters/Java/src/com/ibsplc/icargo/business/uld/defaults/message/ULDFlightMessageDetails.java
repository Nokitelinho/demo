/*
 * ULDFlightMessageDetails.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-1945
 *
 */
@Staleable
@Table(name = "ULDFLTMSGDTL")
@Entity
public class ULDFlightMessageDetails {

    private ULDFlightMessageDetailsPK uldFlightMessageDetailsPK;

    private String content;

    private String pointOfUnloading;
    
    private double uldWeight;
    
    private String uldLocation;

  //  private String lastUpdateUser;

 //   private Calendar lastUpdateTime;

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
    @AttributeOverride(name = "flightCarrierId", column = @Column(name = "FLTCARIDR")),
    @AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM"))})
    public ULDFlightMessageDetailsPK getUldFlightMessageDetailsPK() {
        return uldFlightMessageDetailsPK;
    }
    /**
     *
     * @param uldFlightMessageDetailsPK
     */
    public void setUldFlightMessageDetailsPK(
            ULDFlightMessageDetailsPK uldFlightMessageDetailsPK) {
        this.uldFlightMessageDetailsPK = uldFlightMessageDetailsPK;
    }

    /**
     * @return
     */
    @Column(name = "POU")
    public String getPointOfUnloading() {
        return pointOfUnloading;
    }

    /**
     * @param pointOfUnloading
     */
    public void setPointOfUnloading(String pointOfUnloading) {
        this.pointOfUnloading = pointOfUnloading;
    }
    /**
     *
     * @return
     */
    @Column(name = "CNT")
    public String getContent() {
        return content;
    }
    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 
     * @return
     */
    @Column(name = "ULDLOC")
    public String getUldLocation() {
        return uldLocation;
    }
    /**
     *
     * @param uldLocation
     */
    public void setUldLocation(String uldLocation) {
        this.uldLocation = uldLocation;
    }

    /**
	 * @return the uldWeight
	 */
    @Column(name = "ULDWGT")
	public double getUldWeight() {
		return uldWeight;
	}
	/**
	 * @param uldWeight the uldWeight to set
	 */
	public void setUldWeight(double uldWeight) {
		this.uldWeight = uldWeight;
	}
	/**
     *
     */
    public ULDFlightMessageDetails() {
    }
    /**
     *
     * @param uldFlightMessageDetailsVO
     * @param uldFlightMessagePK
     * @throws SystemException
     */
    public ULDFlightMessageDetails(ULDFlightMessageDetailsVO
            uldFlightMessageDetailsVO, ULDFlightMessagePK uldFlightMessagePK)
            throws SystemException {
        populatePK(uldFlightMessageDetailsVO, uldFlightMessagePK);
        populateAttributes(uldFlightMessageDetailsVO);
        try {
            PersistenceController.getEntityManager().persist(this);
        } catch(CreateException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }

    private void populateAttributes(
            ULDFlightMessageDetailsVO uldFlightMessageDetailsVO)
            throws SystemException {
    	setUldLocation(uldFlightMessageDetailsVO.getUldLocation());
        setContent(uldFlightMessageDetailsVO.getContent());
        setPointOfUnloading(uldFlightMessageDetailsVO.getPointOfUnloading());
        setUldWeight(uldFlightMessageDetailsVO.getUldWeight()!=null?uldFlightMessageDetailsVO.getUldWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
    }

    /**
     *
     * @param uldFlightMessageDetailsVO
     * @param uldFlightMessagePK
     */
    private void populatePK(ULDFlightMessageDetailsVO uldFlightMessageDetailsVO,
                            ULDFlightMessagePK uldFlightMessagePK) {
        setUldFlightMessageDetailsPK(new ULDFlightMessageDetailsPK(
                uldFlightMessagePK.getCompanyCode(),
                uldFlightMessagePK.getStationCode(),
                uldFlightMessagePK.getLegSerialNumber(),
                uldFlightMessagePK.getFlightSequenceNumber(),
                uldFlightMessagePK.getFlightNumber(),
                uldFlightMessagePK.getFlightCarrierId(),
                uldFlightMessageDetailsVO.getUldNumber()));
    }
    
    
    
	/**
	 * 
	 * @param detailsVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageDetails find(
			ULDFlightMessageDetailsVO detailsVO)
	        throws SystemException {
		ULDFlightMessageDetailsPK uldFlightMessageDetailsnewPK = new  ULDFlightMessageDetailsPK();
		
		uldFlightMessageDetailsnewPK.setCompanyCode(  detailsVO.getCompanyCode());
		uldFlightMessageDetailsnewPK.setStationCode(   detailsVO.getStationCode());
		uldFlightMessageDetailsnewPK.setLegSerialNumber(   detailsVO.getLegSerialNumber());
		uldFlightMessageDetailsnewPK.setFlightSequenceNumber(   detailsVO.getFlightSequenceNumber());
		uldFlightMessageDetailsnewPK.setFlightNumber(   detailsVO.getFlightNumber());
		uldFlightMessageDetailsnewPK.setFlightCarrierId(   detailsVO.getFlightCarrierId());
		uldFlightMessageDetailsnewPK.setUldNumber(   detailsVO.getUldNumber());
		
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDFlightMessageDetails.class,
					uldFlightMessageDetailsnewPK);
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
