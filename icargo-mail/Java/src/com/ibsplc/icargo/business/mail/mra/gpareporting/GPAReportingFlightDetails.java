/*
 * GPAReportingFlightDetails.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1453
 * 
 * 
 */
@Entity
@Table(name = "MTKGPARPTFLTDTL")
@Staleable
@Deprecated
public class GPAReportingFlightDetails {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");

	private GPAReportingFlightDetailsPK gpaReportingFlightDetailsPK;

	private String carriageFrom;

	private String carriageTo;

	private String flightNumber;

	private String flightCarrierCode;
	
	//private Calendar reportingFrom;
	
	//private Calendar reportingTo;

	/**
	 * 
	 * 
	 */
	public GPAReportingFlightDetails() {

	}

	/**
	 * @return Returns the carriageFrom.
	 */
	@Column(name = "CARFRM")	
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	@Column(name = "CARTOO")
	public String getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	
	/**
	 * @return Returns the gpaReportingFlightDetailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
        @AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS")),     
        @AttributeOverride(name = "billingIdentifier", column = @Column(name = "BILIDR")),
        @AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")),
        @AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM"))})
	public GPAReportingFlightDetailsPK getGpaReportingFlightDetailsPK() {
		return gpaReportingFlightDetailsPK;
	}

	/**
	 * @param gpaReportingFlightDetailsPK
	 *            The gpaReportingFlightDetailsPK to set.
	 */	
	public void setGpaReportingFlightDetailsPK(
			GPAReportingFlightDetailsPK gpaReportingFlightDetailsPK) {
		this.gpaReportingFlightDetailsPK = gpaReportingFlightDetailsPK;
	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("GPAReportingFlightDetails", "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		log.exiting("GPAReportingFlightDetails", "remove");
	}

	/**
	 * 
	 * @param gpaReportingFlightDetailsVO
	 */
	public void update(GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO) {
		log.entering("GPAReportingFlightDetails", "update");
		populateAttributes(gpaReportingFlightDetailsVO);
		log.exiting("GPAReportingFlightDetails", "update");
	}

	/**
	 * @param gpaReportingFlightDetailsVO
	 */
	private void populateAttributes(
			GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO) {
		log.entering("GPAReportingFlightDetails", "populateAttributes");
		this.setCarriageFrom(gpaReportingFlightDetailsVO.getCarriageFrom());
		this.setCarriageTo(gpaReportingFlightDetailsVO.getCarriageTo());
		this.setFlightNumber(gpaReportingFlightDetailsVO.getFlightNumber());
		this.setFlightCarrierCode(gpaReportingFlightDetailsVO
				.getFlightCarrierCode());
		//this.setReportingFrom(gpaReportingFlightDetailsVO.getReportingFrom());
		//this.setReportingTo(gpaReportingFlightDetailsVO.getReportingTo());
		log.exiting("GPAReportingFlightDetails", "populateAttributes");

	}

	/**
	 * 
	 * @param gpaReportingDetailsVO
	 * @throws SystemException 
	 * 	 
	 * 
	 */
	
	
	
	public GPAReportingFlightDetails(
			GPAReportingFlightDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		log.entering("GPAReportingFlightDetails", "GPAReportingFlightDetails");
		populatePK(gpaReportingDetailsVO);
		populateAttributes(gpaReportingDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		log.exiting("GPAReportingFlightDetails", "GPAReportingFlightDetails");
	}

	/**
	 * @param gpaReportingDetailsVO
	 */
	private void populatePK(GPAReportingFlightDetailsVO gpaReportingDetailsVO) {
		log.entering("GPAReportingFlightDetails", "populatePK");
		this.setGpaReportingFlightDetailsPK(new GPAReportingFlightDetailsPK(
				gpaReportingDetailsVO.getCompanyCode(), 
				gpaReportingDetailsVO.getPoaCode(), 
				gpaReportingDetailsVO.getBillingBasis(),
				gpaReportingDetailsVO.getBillingIdentifier(),		
				gpaReportingDetailsVO.getSequenceNumber(),
				gpaReportingDetailsVO.getFlightSequenceNumber()));
		log.exiting("GPAReportingFlightDetails", "populatePK");

	}
}
