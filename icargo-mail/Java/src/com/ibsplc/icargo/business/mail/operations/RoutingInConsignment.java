/*
 * RoutingInConsignment.java Created on Jun 27, 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991
 */
@Entity
@Table(name = "MALCSGRTG")
@Staleable
public class RoutingInConsignment {
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	private RoutingInConsignmentPK routingInConsignmentPK;

	private String pou;

	private String onwardFlightNumber;

	private Calendar onwardFlightDate;

	private String onwardCarrierCode;

	private int onwardCarrierId;

	private String pol;
	
	private long onwardCarrierSeqNum;
	
	
	
	private String remarks;
	

	private Calendar legSta;
	
	private String transportStageQualifier;



	@Column(name = "LEGSTA")

	@Temporal(TemporalType.DATE)
	public Calendar getLegSta() {
		return legSta;
	}

	public void setLegSta(Calendar legSta) {
		this.legSta = legSta;
	}

	/**
	 * @return Returns the onwardCarrierCode.
	 */
	@Column(name = "FLTCARCOD")
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
	@Column(name = "FLTCARIDR")
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
	 * @return Returns the pol.
	 */
	@Column(name = "POL")
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the onwardFlightDate.
	 */
	@Column(name = "FLTDAT")

	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name = "FLTNUM")
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
	 * @return Returns the onwardCarrierSeqNum.
	 */
	@Column(name = "FLTSEQNUM")
	public long getOnwardCarrierSeqNum() {
		return onwardCarrierSeqNum;
	}

	public void setOnwardCarrierSeqNum(long onwardCarrierSeqNum) {
		this.onwardCarrierSeqNum = onwardCarrierSeqNum;
	}
	
	@Column(name = "TRSSTG")
	public String getTransportStageQualifier() {
		return transportStageQualifier;
	}

	public void setTransportStageQualifier(String transportStageQualifier) {
		this.transportStageQualifier = transportStageQualifier;
	}

	/**
	 * @return Returns the routingInConsignmentPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "consignmentNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "paCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "consignmentSequenceNumber", column = @Column(name = "CSGSEQNUM")),
			@AttributeOverride(name = "routingSerialNumber", column = @Column(name = "RTGSERNUM")) })
	public RoutingInConsignmentPK getRoutingInConsignmentPK() {
		return routingInConsignmentPK;
	}

	/**
	 * @param routingInConsignmentPK
	 *            The routingInConsignmentPK to set.
	 */
	public void setRoutingInConsignmentPK(
			RoutingInConsignmentPK routingInConsignmentPK) {
		this.routingInConsignmentPK = routingInConsignmentPK;
	}

	/**
	 * @return the remarks
	 */
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	/**
	 * Default Constructor
	 */
	public RoutingInConsignment() {
	}

	/**
	 * @author A-5991
	 * @param routingInConsignmentVO
	 * @throws SystemException
	 */
	public RoutingInConsignment(RoutingInConsignmentVO routingInConsignmentVO)
			throws SystemException {
		log.entering("RoutingInConsignment", "RoutingInConsignment");
		populatePk(routingInConsignmentVO);
		populateAttributes(routingInConsignmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("RoutingInConsignment", "RoutingInConsignment");
	}

	/**
	 * @author A-5991
	 * @param routingInConsignmentVO
	 */
	private void populatePk(RoutingInConsignmentVO routingInConsignmentVO) {
		log.entering("RoutingInConsignment", "populatePk");
		RoutingInConsignmentPK routingInConsignmentPk = new RoutingInConsignmentPK();
		routingInConsignmentPk.setCompanyCode(   routingInConsignmentVO
				.getCompanyCode());
		routingInConsignmentPk.setConsignmentNumber(   routingInConsignmentVO
				.getConsignmentNumber());
		routingInConsignmentPk.setConsignmentSequenceNumber(   routingInConsignmentVO
				.getConsignmentSequenceNumber());
		routingInConsignmentPk.setPaCode(   routingInConsignmentVO.getPaCode());
		this.setRoutingInConsignmentPK(routingInConsignmentPk);
		log.exiting("RoutingInConsignment", "populatePk");
	}

	/**
	 * @author A-5991
	 * @param routingInConsignmentVO
	 */
	private void populateAttributes(
			RoutingInConsignmentVO routingInConsignmentVO) {
		log.entering("RoutingInConsignment", "populateAttributes");
		this
				.setOnwardCarrierCode(routingInConsignmentVO
						.getOnwardCarrierCode());
		this.setOnwardCarrierId(routingInConsignmentVO.getOnwardCarrierId());
		if (routingInConsignmentVO.getOnwardFlightDate() != null) {
			this.setOnwardFlightDate(routingInConsignmentVO
					.getOnwardFlightDate().toCalendar());
		}
		this.setOnwardFlightNumber(routingInConsignmentVO
				.getOnwardFlightNumber());
		this.setPou(routingInConsignmentVO.getPou());
		this.setPol(routingInConsignmentVO.getPol());
		this.setOnwardCarrierSeqNum(routingInConsignmentVO.getOnwardCarrierSeqNum());
		if(routingInConsignmentVO.getRemarks()!=null){
			this.setRemarks(routingInConsignmentVO.getRemarks());	
		}		
		if (routingInConsignmentVO.getScheduledArrivalDate() != null) {
			this.setLegSta(routingInConsignmentVO
					.getScheduledArrivalDate().toCalendar());
		}
		if(routingInConsignmentVO.getTransportStageQualifier()!=null){
			this.setTransportStageQualifier(routingInConsignmentVO.
					getTransportStageQualifier());
		}
		log.exiting("RoutingInConsignment", "populateAttributes");
	}

	/**
	 * @author A-5991
	 * @param routingInConsignmentVO
	 */
	public void update(RoutingInConsignmentVO routingInConsignmentVO) {
		log.entering("RoutingInConsignment", "update");
		populateAttributes(routingInConsignmentVO);
		log.exiting("RoutingInConsignment", "update");
	}

	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("RoutingInConsignment", "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
		log.exiting("RoutingInConsignment", "remove");
	}

	/**
	 * @author A-5991
	 * @param routingInConsignmentVO
	 * @return RoutingInConsignment
	 * @throws SystemException
	 */
	public static RoutingInConsignment find(
			RoutingInConsignmentVO routingInConsignmentVO)
			throws SystemException {
		Log findLog = LogFactory.getLogger("MAIL_OPERATIONS");
		findLog.entering("RoutingInConsignment", "find");
		RoutingInConsignment routingInConsignment = null;
		RoutingInConsignmentPK routingInConsignmentPk = new RoutingInConsignmentPK();
		routingInConsignmentPk.setCompanyCode(   routingInConsignmentVO
				.getCompanyCode());
		routingInConsignmentPk.setConsignmentNumber(   routingInConsignmentVO
				.getConsignmentNumber());
		routingInConsignmentPk.setConsignmentSequenceNumber(   routingInConsignmentVO
				.getConsignmentSequenceNumber());
		routingInConsignmentPk.setPaCode(   routingInConsignmentVO.getPaCode());
		routingInConsignmentPk.setRoutingSerialNumber(   routingInConsignmentVO
				.getRoutingSerialNumber());
		try {
			routingInConsignment = PersistenceController.getEntityManager()
					.find(RoutingInConsignment.class, routingInConsignmentPk);
		} catch (FinderException finderException) {
			findLog.log(Log.SEVERE, "  Finder Exception ");
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}
		return routingInConsignment;
	}


}
