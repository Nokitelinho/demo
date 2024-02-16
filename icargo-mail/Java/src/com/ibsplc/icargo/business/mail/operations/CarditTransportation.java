/*
 * CarditTransportation.java Created on Jun 7, 2006
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

import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This entity contains the transportation information of a cardit
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
 *  		  July 16, 2007			A-1739		EJB3Final changes
 */
@Entity
@Table(name = "MALCDTTRT")
@Staleable
public class CarditTransportation {
	private static final String MAILTRACKING_DEFAULTS = "mailtracking.defaults";
	/**
	 * Departure place of flight
	 */
	private String departurePort;

	/**
	 * Departure time of flight
	 */
	private Calendar departureTime; 
	
	
	/**
	 * Departure time in UTC of flight
	 */
	private Calendar departureTimeUTC; 
	

	/**
	 * carrier identification
	 */
	private int carrierID;

	/**
	 * This field is a code list qualifier indicating carrier code
	 */
	private String carrierCode;
	
	/**
	 * carrier name
	 */
	private String carrierName;

	/**
	 * This field indicates code list responsible agency for place of arrival
	 */
	private String arrivalCodeListAgency;

	/**
	 * This field indicates code list responsible agency for place of departure
	 */
	private String departureCodeListAgency;

	/**
	 * This field indicates code list responsible agency for carrier eg: 3
	 * -IATA, 139 - UPU
	 */
	private String agencyForCarrierCodeList;

	/**
	 * This field indicates conveyance reference number
	 */
	private String conveyanceReference;

	/**
	 * This field indicates mode of transport eg: air, road , rail
	 */
	private String modeOfTransport;

	/**
	 * This field indicates transport stage qualifier eg: 10 - pre carriage
	 * transport eg: 20 - main carriage transport
	 */
	private String transportStageQualifier;

	/**
	 * The pk of this flight
	 */

	private String flightNumber;

	private long flightSequenceNumber;

	private int legSerialNumber;

	private int segmentSerialNumber;
	
	private Calendar arrivalDate;
	
	private CarditTransportationPK transportationPK;

	private String contractReference;



	/**
	 * Empty constr for hibernate
	 * 
	 */
	public CarditTransportation() {
	}

	/**
	 * @author A-1739
	 * @param carditPK
	 * @param transportationVO
	 * @throws SystemException
	 */
	public CarditTransportation(CarditTransportationPK transportPK,
			CarditTransportationVO transportationVO) throws SystemException {
		populatePk(transportPK);
		populateAttributes(transportationVO);

		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
	}

	/**
	 * @author A-1739
	 * @param carditPK
	 * @param transportationVO
	 */
	private void populatePk(CarditTransportationPK carditTransportPK) {	
		this.transportationPK = new CarditTransportationPK();
		this.transportationPK.setCompanyCode(carditTransportPK.getCompanyCode());
		this.transportationPK.setCarditKey(carditTransportPK.getCarditKey());
		this.transportationPK.setArrivalPort(carditTransportPK.getArrivalPort());		
	}

	/**
	 * @author A-1739
	 * @param transportationVO
	 */
	private void populateAttributes(CarditTransportationVO transportationVO) {
		setAgencyForCarrierCodeList(transportationVO
				.getAgencyForCarrierCodeList());
		setArrivalCodeListAgency(transportationVO.getArrivalCodeListAgency());
		setArrivalDate(transportationVO.getArrivalDate());
		setCarrierCode(transportationVO.getCarrierCode());
		setCarrierID(transportationVO.getCarrierID());
		setCarrierName(transportationVO.getCarrierName());
		setConveyanceReference(transportationVO.getConveyanceReference());
		setDepartureCodeListAgency(transportationVO
				.getDepartureCodeListAgency());
		setDeparturePort(transportationVO.getDeparturePort());
		if (departurePort != null && transportationVO.getDepartureTime() != null) { // departure information is present then
			setDepartureTime(transportationVO.getDepartureTime().toCalendar());
		}
		setModeOfTransport(transportationVO.getModeOfTransport());
		setTransportStageQualifier(transportationVO
				.getTransportStageQualifier());

		setFlightNumber(transportationVO.getFlightNumber());
		setFlightSequenceNumber(transportationVO.getFlightSequenceNumber());
		setLegSerialNumber(transportationVO.getLegSerialNumber());
		setSegmentSerialNumber(transportationVO.getSegmentSerialNum());
		/*
		 * Added By Karthick V to include the column DEPDATUTC
		 * 
		 */
		if(getDepartureTime()!=null && transportationVO.getDepartureTime() != null){
			setDepartureTimeUTC(transportationVO.getDepartureTime().toGMTDate());
		}
		
		setContractReference (transportationVO.getContractReference());
	}

	/**
	 * @author A-3227
	 * @param transportPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditTransportation find(CarditTransportationPK transportPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditTransportation.class, transportPK);
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
	/**
	 * @return Returns the agencyForCarrierCodeList.
	 */
	@Column(name = "CARCODLSTAGY")
	public String getAgencyForCarrierCodeList() {
		return agencyForCarrierCodeList;
	}

	/**
	 * @param agencyForCarrierCodeList
	 *            The agencyForCarrierCodeList to set.
	 */
	public void setAgencyForCarrierCodeList(String agencyForCarrierCodeList) {
		this.agencyForCarrierCodeList = agencyForCarrierCodeList;
	}

	/**
	 * @return Returns the arrivalCodeListAgency.
	 */
	@Column(name = "DSTCODLSTAGY")
	public String getArrivalCodeListAgency() {
		return arrivalCodeListAgency;
	}

	/**
	 * @param arrivalCodeListAgency
	 *            The arrivalCodeListAgency to set.
	 */
	public void setArrivalCodeListAgency(String arrivalCodeListAgency) {
		this.arrivalCodeListAgency = arrivalCodeListAgency;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	@Column(name = "CARCOD")
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
	 * @return Returns the carrierID.
	 */
	@Column(name = "CARIDR")
	public int getCarrierID() {
		return carrierID;
	}

	/**
	 * @param carrierID
	 *            The carrierID to set.
	 */
	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
	}

	/**
	 * @return Returns the conveyanceReference.
	 */
	@Column(name = "CNVREF")
	public String getConveyanceReference() {
		return conveyanceReference;
	}

	/**
	 * @param conveyanceReference
	 *            The conveyanceReference to set.
	 */
	public void setConveyanceReference(String conveyanceReference) {
		this.conveyanceReference = conveyanceReference;
	}

	/**
	 * @return Returns the departureCodeListAgency.
	 */
	@Column(name = "ORGCODLSTAGY")
	public String getDepartureCodeListAgency() {
		return departureCodeListAgency;
	}

	/**
	 * @param departureCodeListAgency
	 *            The departureCodeListAgency to set.
	 */
	public void setDepartureCodeListAgency(String departureCodeListAgency) {
		this.departureCodeListAgency = departureCodeListAgency;
	}

	/**
	 * @return Returns the departurePlace.
	 */
	@Column(name = "ORGCOD")
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePlace
	 *            The departurePlace to set.
	 */
	public void setDeparturePort(String departurePlace) {
		this.departurePort = departurePlace;
	}

	/**
	 * @return Returns the departureTime.
	 */
	@Column(name = "DEPTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime
	 *            The departureTime to set.
	 */
	public void setDepartureTime(Calendar departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return Returns the modeOfTransport.
	 */
	@Column(name = "TRTMOD")
	public String getModeOfTransport() {
		return modeOfTransport;
	}

	/**
	 * @param modeOfTransport
	 *            The modeOfTransport to set.
	 */
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	/**
	 * @return Returns the transportStageQualifier.
	 */
	@Column(name = "TRTSTGQLF")
	public String getTransportStageQualifier() {
		return transportStageQualifier;
	}

	/**
	 * @param transportStageQualifier
	 *            The transportStageQualifier to set.
	 */
	public void setTransportStageQualifier(String transportStageQualifier) {
		this.transportStageQualifier = transportStageQualifier;
	}

	/**
	 * @return Returns the transportationPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carditKey", column = @Column(name = "CDTKEY")),
			@AttributeOverride(name = "arrivalPort", column = @Column(name = "DSTCOD")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "TRTSERNUM")) })
	public CarditTransportationPK getTransportationPK() {
		return transportationPK;
	}

	/**
	 * @param transportationPK
	 *            The transportationPK to set.
	 */
	public void setTransportationPK(CarditTransportationPK transportationPK) {
		this.transportationPK = transportationPK;
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
	 * @return Returns the flightSequenceNumber.
	 */
	@Column(name = "FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name = "LEGSERNUM")
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setLegSerialNumber(int segmentSerialNumber) {
		this.legSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name="SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * This method  is used to find wether the  Transporation Details already Exists for  the Cardit 
	 * @param companyCode
	 * @param carditKey
	 * @param carditTransportationVO
	 * @return
	 * @throws SystemException
	 */
	public static  boolean  checkCarditTransportExists(String companyCode,String carditKey,CarditTransportationVO carditTransportationVO)
	  throws SystemException{
		 Log logger = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		 logger.log(Log.INFO, "checkCarditTransportExists");
		 try{
		 return  constructDAO().checkCarditTransportExists(companyCode,carditKey,carditTransportationVO);
		 }catch(PersistenceException ex){
			 throw  new SystemException(ex.getMessage(),ex);
		 }
		
	}
	
	
	/**
	 * This method  is used to get an instance of the DAO..
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAILTRACKING_DEFAULTS));
		} catch (PersistenceException exception) {
			throw new SystemException("Query DAO not found", exception);
		}
	}
	@Column(name="DEPDATUTC")
	public Calendar getDepartureTimeUTC() {
		return departureTimeUTC;
	}

	public void setDepartureTimeUTC(Calendar departureTimeUTC) {
		this.departureTimeUTC = departureTimeUTC;
	}

	/**
	 * @return the carrierName
	 */
	@Column(name="CARNAM")
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

/**
	 * @return Returns the arrivalDate.
	 */
	@Column(name="ARRDAT")
	public Calendar getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate The arrivalDate to set.
	 */
	
	public void setArrivalDate(Calendar arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	/**
	 * @return the contractReference
	 */
	@Column(name="CNTRFF")
	public String getContractReference() {
		return contractReference;
	}

	/**
	 * @param contractReference the contractReference to set
	 */
	public void setContractReference(String contractReference) {
		this.contractReference = contractReference;
	}
}

