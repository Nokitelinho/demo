/*
 * OnwardRouteForSegment.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

// import com.ibsplc.xibase.util.log.Log;
// import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991
 * 
 */
@Entity
@Table(name = "MALONWRTGSEG")
@Staleable
public class OnwardRouteForSegment {

	// private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	
	private OnwardRouteForSegmentPK onwardRouteForSegmentPk;

	private String onwardCarrierCode;

	private Calendar onwardFlightDate;

	private String pou;

	private int onwardCarrierId;

	private String onwardFlightNumber;


	/**
	 * @return Returns the carrierCode.
	 */
	@Column(name = "ONWCARCOD")
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setOnwardCarrierCode(String carrierCode) {
		this.onwardCarrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierId.
	 */
	@Column(name = "ONWCARIDR")
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}

	/**
	 * @param onwardCarrierId
	 *            The carrierId to set.
	 */
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}

	/**
	 * @return Returns the flightDate.
	 */
	@Column(name = "ONWFLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getOnwardFlightDate() {
		return onwardFlightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setOnwardFlightDate(Calendar flightDate) {
		this.onwardFlightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 * 
	 */
	@Column(name = "ONWFLTNUM")
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	/**
	 * @param onwardFlightNumber
	 *            The flightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
	}

	/**
	 * @return Returns the onwardRouteForSegmentPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "segmentSerialNumber", column = @Column(name = "SEGSERNUM")),		
			@AttributeOverride(name = "routingSerialNumber", column = @Column(name = "RTGSERNUM")) })
	public OnwardRouteForSegmentPK getOnwardRouteForSegmentPk() {
		return onwardRouteForSegmentPk;
	}

	/**
	 * @param onwardRouteForSegmentPk
	 *            The onwardRouteForSegmentPk to set.
	 */
	public void setOnwardRouteForSegmentPk(
			OnwardRouteForSegmentPK onwardRouteForSegmentPk) {
		this.onwardRouteForSegmentPk = onwardRouteForSegmentPk;
	}

	/**
	 * @return Returns the pou.
	 */
	@Column(name = "POU")
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @author A-5991
	 * @return
	 */
	public OnwardRouteForSegmentVO retrieveVO() {
		OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
		OnwardRouteForSegmentPK onwardPk = getOnwardRouteForSegmentPk();
		onwardRouteForSegmentVO
				.setRoutingSerialNumber(onwardPk.getRoutingSerialNumber());

		onwardRouteForSegmentVO.setOnwardCarrierCode(getOnwardCarrierCode());
		onwardRouteForSegmentVO.setOnwardCarrierId(getOnwardCarrierId());
		onwardRouteForSegmentVO.setOnwardFlightDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, getOnwardFlightDate(),
				true));
		onwardRouteForSegmentVO.setOnwardFlightNumber(getOnwardFlightNumber());
		onwardRouteForSegmentVO.setPou(getPou());
		return onwardRouteForSegmentVO;
	}

	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

	}

	/**
	 * A-5991
	 * 
	 * @param onwardRouteForSegmentVO
	 */
	public void update(OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		populateAttributes(onwardRouteForSegmentVO);
	}

	


    /**
    *@author A-5991
    * @return
    * @throws SystemException
    */
   private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
       try {
           return MailTrackingDefaultsDAO.class.cast(
                   PersistenceController.getEntityManager().getQueryDAO(
                   MODULE_NAME));
       } catch (PersistenceException ex) {
           throw new SystemException(ex.getMessage(), ex);
       }
   }
   
   
   public OnwardRouteForSegment() {

	}

   /**
    * @author A-5991
    * @param uldForSegmentPK
    * @param onwardRouteForSegmentVO
    * @throws SystemException
    */
	public OnwardRouteForSegment(ULDForSegmentPK uldForSegmentPK,
			OnwardRouteForSegmentVO onwardRouteForSegmentVO)
			throws SystemException {
		populatePK(uldForSegmentPK, onwardRouteForSegmentVO);
		populateAttributes(onwardRouteForSegmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
	}

	/**
	 * A-5991
	 * 
	 * @param onwardRouteForSegmentVO
	 * @param uldForSegmentPK
	 */
	private void populatePK(ULDForSegmentPK uldForSegmentPK,
			OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		onwardRouteForSegmentPk = new OnwardRouteForSegmentPK();
		onwardRouteForSegmentPk.setCompanyCode(   uldForSegmentPK.getCompanyCode());
		onwardRouteForSegmentPk.setCarrierId(   uldForSegmentPK.getCarrierId());
		onwardRouteForSegmentPk.setFlightNumber(   uldForSegmentPK.getFlightNumber());
		onwardRouteForSegmentPk.setFlightSequenceNumber(   uldForSegmentPK.getFlightSequenceNumber());
		onwardRouteForSegmentPk.setSegmentSerialNumber(   uldForSegmentPK.getSegmentSerialNumber());
		onwardRouteForSegmentPk.setUldNumber(   uldForSegmentPK.getUldNumber());
	}

	/**
	 * A-5991
	 * 
	 * @param onwardRouteForSegmentVO
	 */
	private void populateAttributes(
			OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		onwardCarrierCode = onwardRouteForSegmentVO.getOnwardCarrierCode();
		onwardCarrierId = onwardRouteForSegmentVO.getOnwardCarrierId();
		onwardFlightNumber = onwardRouteForSegmentVO.getOnwardFlightNumber();
		onwardFlightDate = onwardRouteForSegmentVO.getOnwardFlightDate()
				.toCalendar();
		pou = onwardRouteForSegmentVO.getPou();
	}

}
