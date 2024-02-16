/*
 * OnwardRouting.java Created on Jun 27, 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991
@Entity
 * @Table(name = "MALTRKRTG")
 * @Staleable
 */
@Entity
@Table(name = "MALFLTCONRTG")
@Staleable
public class OnwardRouting {
	private OnwardRoutingPK onwardRoutingPK;

	private String pou;

	private String onwardFlightNumber;

	private Calendar onwardFlightDate;

	private String onwardCarrierCode;

	private int onwardCarrierId;

	private Log logger = LogFactory.getLogger("MAIL_OPERATIONS");


	/**
	 * @return Returns the pou.
	 * 
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
	 * The Embedded Id for the PK
	 * 
	 * @return
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "containerNumber", column = @Column(name = "CONNUM")),
			@AttributeOverride(name = "assignmentPort", column = @Column(name = "ASGPRT")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")),
			@AttributeOverride(name = "routingSerialNumber", column = @Column(name = "RTGSERNUM")) })
	public OnwardRoutingPK getOnwardRoutingPK() {
		return onwardRoutingPK;
	}

	/**
	 * @param onwardRoutingPK
	 *            The onwardRoutingPK to set.
	 */
	public void setOnwardRoutingPK(OnwardRoutingPK onwardRoutingPK) {
		this.onwardRoutingPK = onwardRoutingPK;
	}


	/**
	 * @return Returns the onwardCarrierCode.
	 */
	@Column(name = "ONWFLTCARCOD")
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}

	/**
	 * @param onwardCarrierCode
	 *            The onwardCarrierCode to set.
	 */
	public void setOnwardCarrierCode(String onwardCarrierCode) {
		this.onwardCarrierCode = onwardCarrierCode;
	}

	/**
	 * @return Returns the onwardCarrierId.
	 */
	@Column(name = "ONWFLTCARIDR")
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}

	/**
	 * @param onwardCarrierId
	 *            The onwardCarrierId to set.
	 */
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}

	/**
	 * @return Returns the onwardFlightDate.
	 */
	@Column(name = "ONWFLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getOnwardFlightDate() {
		return onwardFlightDate;
	}

	/**
	 * @param onwardFlightDate
	 *            The onwardFlightDate to set.
	 */
	public void setOnwardFlightDate(Calendar onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}

	/**
	 * @return Returns the onwardFlightNumber.
	 */
	@Column(name = "ONWFLTNUM")
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	/**
	 * @param onwardFlightNumber
	 *            The onwardFlightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
	}

	/**
	 * @author A-5991
	 * @return
	 */
	public OnwardRoutingVO retrieveVO() {
		logger.entering("OnwardRouting", "retrieveVO");
		OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
		OnwardRoutingPK onwardPk = getOnwardRoutingPK();
		onwardRoutingVO.setCompanyCode(onwardPk.getCompanyCode());
		onwardRoutingVO.setContainerNumber(onwardPk.getContainerNumber());
		onwardRoutingVO.setCarrierId(onwardPk.getCarrierId());
		onwardRoutingVO.setFlightNumber(onwardPk.getFlightNumber());
		onwardRoutingVO.setFlightSequenceNumber(onwardPk.getFlightSequenceNumber());
		onwardRoutingVO.setLegSerialNumber(onwardPk.getLegSerialNumber());
		onwardRoutingVO.setRoutingSerialNumber(onwardPk.getRoutingSerialNumber());
		onwardRoutingVO.setAssignmenrPort(onwardPk.getAssignmentPort());

		onwardRoutingVO.setOnwardCarrierCode(getOnwardCarrierCode());
		onwardRoutingVO.setOnwardCarrierId(getOnwardCarrierId());
		if(getOnwardFlightDate()!=null){
		onwardRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, getOnwardFlightDate(), true));
		}
		onwardRoutingVO.setOnwardFlightNumber(getOnwardFlightNumber());
		onwardRoutingVO.setPou(getPou());
		logger.exiting("OnwardRouting", "retrieveVO");
		return onwardRoutingVO;
	}
	
	/**
	 * The DefaultConstructor
	 * 
	 */

	public OnwardRouting() {

	}

	/**
	 * @author a-5991 This method is used to populate the attributes into the
	 *         Entity
	 * @param onwardRoutingVO
	 * @throws SystemException
	 */
	public OnwardRouting(OnwardRoutingVO onwardRoutingVO)
			throws SystemException {
		populatePK(onwardRoutingVO);
		populateAttributes(onwardRoutingVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}

	}

	/**
	 * @author a-5991 This method is used to populate the pk details
	 * @param onwardRoutingVO
	 * @throws SystemException
	 */
	private void populatePK(OnwardRoutingVO onwardRoutingVO)
			throws SystemException {
		logger.entering("INSIDE THE POPULATE PK", "ROUTINGPK");
		logger.entering("INSIDE THE POPULATE PK", "ROUTINGPK");
		OnwardRoutingPK routingPK = new OnwardRoutingPK();
		routingPK.setAssignmentPort(   onwardRoutingVO.getAssignmenrPort());
		routingPK.setCompanyCode(   onwardRoutingVO.getCompanyCode());
		routingPK.setContainerNumber(   onwardRoutingVO.getContainerNumber());
		routingPK.setCarrierId(   onwardRoutingVO.getCarrierId());
		routingPK.setFlightNumber(   onwardRoutingVO.getFlightNumber());
		routingPK.setFlightSequenceNumber(   onwardRoutingVO
				.getFlightSequenceNumber());
		routingPK.setLegSerialNumber(   onwardRoutingVO.getLegSerialNumber());
		routingPK.setRoutingSerialNumber(   onwardRoutingVO
				.getRoutingSerialNumber());
		this.setOnwardRoutingPK(routingPK);
	}

	/**
	 * 
	 * @author a-5991 This method is used to populate the Attributes
	 * @param onwardRoutingVO
	 * @throws SystemException
	 */
	private void populateAttributes(OnwardRoutingVO onwardRoutingVO)
			throws SystemException {
		logger.entering("INSIDE THE POPULATE ATTRIBUTES", "ROUTINGPK");
		this.setOnwardCarrierCode(onwardRoutingVO.getOnwardCarrierCode());

		this.setOnwardCarrierId(onwardRoutingVO.getOnwardCarrierId());
		if (onwardRoutingVO.getOnwardFlightDate() != null) {
			this.setOnwardFlightDate(onwardRoutingVO.getOnwardFlightDate()
					.toCalendar());
		}
		this.setOnwardFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
		this.setPou(onwardRoutingVO.getPou());
	}

	
	/**
	 * @author a-5991 This method is used to remove the business Objects
	 *         instance
	 * @throws SystemException
	 */
	public void remove() throws SystemException {

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}

	}

	/**
	 * @author a-5991 This method is called from the Parent during modify or
	 *         when the details of the Routing has to be modified...
	 * @param onwardRoutingVO
	 * @throws SystemException
	 */
	public void update(OnwardRoutingVO onwardRoutingVO) throws SystemException {
		populateAttributes(onwardRoutingVO);

	}

}
