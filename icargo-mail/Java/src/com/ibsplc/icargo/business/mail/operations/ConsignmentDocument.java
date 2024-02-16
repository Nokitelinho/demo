/*
 * ConsignmentDocument.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Version;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
//import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
//import com.ibsplc.icargo.framework.util.time.LocalDate;
//import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-5991
 * 
 * 
 */
@Table(name = "MALCSGMST")
@Entity
public class ConsignmentDocument {

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	private static final String MODULE = "mail.operations";
	//Added For Bug-ICRD-262534 starts
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String PASS = "P";
	private static final String FAIL = "F";
	private static final String SCREENING_RESULT_PASS = "SPX,SCO";
	private static final String SCREENING_RESULT_FAIL = "NSC";
	private static final String COMMA = ",";
	//Added For Bug-ICRD-262534 ends
	private ConsignmentDocumentPK consignmentDocumentPK;

	private Calendar consignmentDate;

	/*
	 * CN38 or CN41
	 */
	private String type;

	private String subType;
	/*
	 * I - Inbound , O -outbound
	 */
	private String operation;

	/*
	 * Set<RoutingInConsignment>
	 */
	private Set<RoutingInConsignment> routingsInConsignments;

	/*
	 * Set<MailInConsignment>
	 */
	private Set<MailInConsignment> mailsInConsignments;

	private Set<ConsignmentScreeningDetails> consignmentScreeningDetails;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private Calendar securityStatusDate;
	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	private String remarks;

	private int statedBags;

	private double statedWeight;

	private String airportCode;
	
	private String consignmentOrigin;
	
	private String consignmentDestination;
	//Added as part of CRQ ICRD-103713 starts
	private String operatorOrigin; 
	private  String operatorDestination; 
	private String OOEDescription; 
	private String DOEDescription; 
	private String consignmentPriority ;
	private String transportationMeans; 
	private String flightDetails; 
	private String flightRoute ;
	private Calendar firstFlightDepartureDate ;
	private String airlineCode; 
	//Added as part of CRQ ICRD-103713 ends
	private String paName;
	private String consignmentIssuerName;
  private String shipmentPrefix;
	private String masterDocumentNumber;
    private String shipperUpuCode;
	private String consigneeUpuCode;
    private String originUpuCode;
	private String destinationUpuCode;
	@Column(name="SECSTAPTY")
	public String getSecurityStatusParty() {
		return securityStatusParty;
	}
	public void setSecurityStatusParty(String securityStatusParty) {
		this.securityStatusParty = securityStatusParty;
	}
	@Column(name="SECSTACOD")
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	@Column(name="ADLSECINF")
	public String getAdditionalSecurityInfo() {
		return additionalSecurityInfo;
	}
	public void setAdditionalSecurityInfo(String additionalSecurityInfo) {
		this.additionalSecurityInfo = additionalSecurityInfo;
	}
	@Column(name="SECSTADAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getSecurityStatusDate() {
		return securityStatusDate;
	}
	public void setSecurityStatusDate(Calendar securityStatusDate) {
		this.securityStatusDate = securityStatusDate;
	}
	
	/**
	 * 
	 * @return operatorOrigin
	 */
	@Column(name="OPRORG")
	public String getOperatorOrigin() {
		return operatorOrigin;
	}
	/**
	 * 
	 * @param operatorOrigin
	 */
	public void setOperatorOrigin(String operatorOrigin) {
		this.operatorOrigin = operatorOrigin;
	}
	/**
	 * 
	 * @return operatorDestination
	 */
	@Column(name="OPRDST")
	public String getOperatorDestination() {
		return operatorDestination;
	}
	/**
	 * 
	 * @param operatorDestination
	 */
	public void setOperatorDestination(String operatorDestination) {
		this.operatorDestination = operatorDestination;
	}
	/**
	 * 
	 * @return OOEDescription
	 */
	@Column(name="ORGEXGOFCDES")
	public String getOOEDescription() {
		return OOEDescription;
	}
	/**
	 * 
	 * @param oOEDescription
	 */
	public void setOOEDescription(String oOEDescription) {
		OOEDescription = oOEDescription;
	}
	/**
	 * 
	 * @return DOEDescription
	 */
	@Column(name="DSTEXGOFCDES")
	public String getDOEDescription() {
		return DOEDescription;
	}
	/**
	 * 
	 * @param dOEDescription
	 */
	public void setDOEDescription(String dOEDescription) {
		DOEDescription = dOEDescription;
	}
	/**
	 * 
	 * @return consignmentPriority
	 */
	@Column(name="CSGPRI")
	public String getConsignmentPriority() {
		return consignmentPriority;
	}
	/**
	 * 
	 * @param consignmentPriority
	 */
	public void setConsignmentPriority(String consignmentPriority) {
		this.consignmentPriority = consignmentPriority;
	}
	/**
	 * 
	 * @return transportationMeans
	 */
	@Column(name="TRPMNS")
	public String getTransportationMeans() {
		return transportationMeans;
	}
	/**
	 * 
	 * @param transportationMeans
	 */
	public void setTransportationMeans(String transportationMeans) {
		this.transportationMeans = transportationMeans;
	}
	/**
	 * 
	 * @return flightDetails
	 */
	@Column(name="FLTDTL")
	public String getFlightDetails() {
		return flightDetails;
	}
	/**
	 * 
	 * @param flightDetails
	 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}
	/**
	 * 
	 * @return flightRoute
	 */
	@Column(name="FLTRUT")
	public String getFlightRoute() {
		return flightRoute;
	}
	/**
	 * 
	 * @param flightRoute
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	/**
	 * 
	 * @return firstFlightDepartureDate
	 */
	@Column(name="FSTFLTDEPDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFirstFlightDepartureDate() {
		return firstFlightDepartureDate;
	}
	/** 
	 * 
	 * @param firstFlightDepartureDate
	 */
	public void setFirstFlightDepartureDate(Calendar firstFlightDepartureDate) {
		this.firstFlightDepartureDate = firstFlightDepartureDate;
	}
	/**
	 * 
	 * @return airlineCode
	 */
	@Column(name="ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}
	/** 
	 * 
	 * @param airlineCode
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * 
	 * @return
	 */
	@Column(name="CSGORG")
	public String getConsignmentOrigin() {
		return consignmentOrigin;
	}
    /**
     * 
     * @param consignmentOrigin
     */
	public void setConsignmentOrigin(String consignmentOrigin) {
		this.consignmentOrigin = consignmentOrigin;
	}
	/**
	 * 
	 * @return
	 */
	@Column(name="CSGDST")
	public String getConsignmentDestination() {
		return consignmentDestination;
	}
    /**
     * 
     * @param consignmentDestination
     */
	public void setConsignmentDestination(String consignmentDestination) {
		this.consignmentDestination = consignmentDestination;
	}
	/**
	 * @return Returns the consignmentDate.
	 */
	@Column(name = "CSGDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate
	 *            The consignmentDate to set.
	 */
	public void setConsignmentDate(Calendar consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return Returns the consignmentDocumentPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "consignmentNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "paCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "consignmentSequenceNumber", column = @Column(name = "CSGSEQNUM")) })
	public ConsignmentDocumentPK getConsignmentDocumentPK() {
		return consignmentDocumentPK;
	}

	/**
	 * @param consignmentDocumentPK
	 *            The consignmentDocumentPK to set.
	 */
	public void setConsignmentDocumentPK(
			ConsignmentDocumentPK consignmentDocumentPK) {
		this.consignmentDocumentPK = consignmentDocumentPK;
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
	 * @return Returns the mailsInConsignment.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	public Set<MailInConsignment> getMailsInConsignments() {
		return mailsInConsignments;
	}

	/**
	 * 
	 * @param mailsInConsignments
	 */
	public void setMailsInConsignments(
			Set<MailInConsignment> mailsInConsignments) {
		this.mailsInConsignments = mailsInConsignments;
	}

	/**
	 * @return Returns the operation.
	 */
	@Column(name = "OPRTYP")
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            The operation to set.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return Returns the routingsInConsignment.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	public Set<RoutingInConsignment> getRoutingsInConsignments() {
		return routingsInConsignments;
	}

	/**
	 * @param routingsInConsignments
	 *            The routingsInConsignment to set.
	 * 
	 */
	public void setRoutingsInConsignments(
			Set<RoutingInConsignment> routingsInConsignments) {
		this.routingsInConsignments = routingsInConsignments;
	}

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	public Set<ConsignmentScreeningDetails> getConsignmentScreeningDetails() {
		return consignmentScreeningDetails;
	}
	public void setConsignmentScreeningDetails(Set<ConsignmentScreeningDetails> consignmentScreeningDetails) {
		this.consignmentScreeningDetails = consignmentScreeningDetails;
	}
	/**
	 * @return Returns the type.
	 */
	@Column(name = "CSGTYP")
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "SUBTYP")
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * @return Returns the type.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 
	 * @param remarks
	 *            The Remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the statedBags.
	 */
	@Column(name = "STDBAG")
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 *            The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return Returns the airportCode.
	 */
	@Column(name = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode =  airportCode;
	}

	/**
	 * @return Returns the statedWeight.
	 */
	@Column(name = "STDWGT")
	public double getStatedWeight() {
		return statedWeight;
	}

	/**
	 * @param statedWeight
	 *            The statedWeight to set.
	 */
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}


	@Column(name = "POANAM")
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
	@Column(name="CSGISRNAM")
	public String getConsignmentIssuerName() {
		return consignmentIssuerName;
	}
	public void setConsignmentIssuerName(String consignmentIssuerName) {
		this.consignmentIssuerName = consignmentIssuerName;
	}  
//added as part of IASCB-139842
	@Column(name = "SHPPFX")
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	@Column(name = "MSTDOCNUM")
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
    @Column(name = "SHPUPUCOD")
	public String getShipperUpuCode() {
		return shipperUpuCode;
	}
	public void setShipperUpuCode(String shipperUpuCode) {
		this.shipperUpuCode = shipperUpuCode;
	}
	@Column(name = "CNSUPUCOD")
	public String getConsigneeUpuCode() {
		return consigneeUpuCode;
	}
	public void setConsigneeUpuCode(String consigneeUpuCode) {
		this.consigneeUpuCode = consigneeUpuCode;
	}
	@Column(name = "CSGORGEXGOFCCOD")
	public String getOriginUpuCode() {
		return originUpuCode;
	}
	public void setOriginUpuCode(String originUpuCode) {
		this.originUpuCode = originUpuCode;
	}
	@Column(name = "CSGDSTEXGOFCCOD")
	public String getDestinationUpuCode() {
		return destinationUpuCode;
	}
	public void setDestinationUpuCode(String destinationUpuCode) {
		this.destinationUpuCode = destinationUpuCode;
	}
	/**
	 * Default Constructor
	 */
	public ConsignmentDocument() {

	}
	/**
	 * @author a-5991
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	public ConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, DuplicateMailBagsException {
		log.entering("ConsignmentDocument", "ConsignmentDocument");
		populatePk(consignmentDocumentVO);
		populateAttributes(consignmentDocumentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception);
		}
		consignmentDocumentVO
				.setConsignmentSequenceNumber(this.consignmentDocumentPK.getConsignmentSequenceNumber());
		populateChildren(consignmentDocumentVO);
		log.exiting("ConsignmentDocument", "ConsignmentDocument");
	}

	/**
	 * @author a-5991
	 * @param consignmentDocumentVO
	 */
	private void populatePk(ConsignmentDocumentVO consignmentDocumentVO) {
		log.entering("ConsignmentDocument", "populatePk");
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode(   consignmentDocumentVO
				.getCompanyCode());
		consignmentDocumentPk.setConsignmentNumber(   consignmentDocumentVO
				.getConsignmentNumber());
		consignmentDocumentPk.setPaCode(   consignmentDocumentVO.getPaCode());
		this.setConsignmentDocumentPK(consignmentDocumentPk);
		log.exiting("ConsignmentDocument", "populatePk");
	}
	
	
	/**
	 * @author a-1883
	 * @return MailTrackingDefaultsDAO
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		MailTrackingDefaultsDAO mailTrackingDefaultsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailTrackingDefaultsDAO = MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailTrackingDefaultsDAO;
	}

	
	/**
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 */
	public ConsignmentDocumentVO checkConsignmentDocumentExists(
			ConsignmentDocumentVO consignmentDocumentVO) throws SystemException {
		log.entering("ConsignmentDocument", "checkConsignmentDocumentExists");
		try {
			return constructDAO().checkConsignmentDocumentExists(
					consignmentDocumentVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * @author  a-5991
	 * @param consignmentDocumentVO
	 */
	public void populateAttributes(ConsignmentDocumentVO consignmentDocumentVO) {
		log.entering("ConsignmentDocument", "populateAttributes");
		if (consignmentDocumentVO.getConsignmentDate() != null) {
			this.setConsignmentDate(consignmentDocumentVO.getConsignmentDate()
					.toCalendar());
		}
		if(consignmentDocumentVO.getConsignmentPriority()== null){
			consignmentDocumentVO.setConsignmentPriority(ConsignmentDocumentVO.FLAG_NO);
		}
		
		this.setOperation(consignmentDocumentVO.getOperation());
		this.setRemarks(consignmentDocumentVO.getRemarks());
		this.setStatedBags(consignmentDocumentVO.getStatedBags());
		//this.setStatedWeight(consignmentDocumentVO.getStatedWeight());
		this.setStatedWeight(consignmentDocumentVO.getStatedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		this.setType(consignmentDocumentVO.getType());
		this.setSubType(consignmentDocumentVO.getSubType());
		this.setAirportCode(consignmentDocumentVO.getAirportCode());
		/*
		 * Added By Karthick V as the part of the Optimistic Concurrency Changes 
		 * 
		 * 
		 */
		if (consignmentDocumentVO.getLastUpdateTime() != null) {
			this.setLastUpdateTime(consignmentDocumentVO.getLastUpdateTime()
					.toCalendar());
		}
		this.setLastUpdateUser(consignmentDocumentVO.getLastUpdateUser());
		this.setConsignmentOrigin(consignmentDocumentVO.getOrgin());
		this.setConsignmentDestination(consignmentDocumentVO.getDestination());
		this.setOperatorOrigin(consignmentDocumentVO.getOperatorOrigin());
		this.setOperatorDestination(consignmentDocumentVO.getOperatorDestination());
		this.setOOEDescription(consignmentDocumentVO.getOoeDescription());
		this.setDOEDescription(consignmentDocumentVO.getDoeDescription());
		this.setConsignmentPriority(consignmentDocumentVO.getConsignmentPriority());
		this.setTransportationMeans(consignmentDocumentVO.getTransportationMeans());
		this.setFlightDetails(consignmentDocumentVO.getFlightDetails());
		this.setFlightRoute(consignmentDocumentVO.getFlightRoute());
		this.setFirstFlightDepartureDate(consignmentDocumentVO.getFirstFlightDepartureDate());
		this.setAirlineCode(consignmentDocumentVO.getAirlineCode());
		this.setSecurityStatusParty(consignmentDocumentVO.getSecurityStatusParty());
		this.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
		if(consignmentDocumentVO.getSecurityStatusDate()!=null){
			this.setSecurityStatusDate(consignmentDocumentVO.getSecurityStatusDate().toCalendar());
		}
		this.setAdditionalSecurityInfo(consignmentDocumentVO.getAdditionalSecurityInfo());
		this.setPaName(consignmentDocumentVO.getPaName());
		this.setConsignmentIssuerName(consignmentDocumentVO.getConsignmentIssuerName());
		this.setShipmentPrefix(consignmentDocumentVO.getShipmentPrefix());
		this.setMasterDocumentNumber(consignmentDocumentVO.getMasterDocumentNumber());
		this.setShipperUpuCode(consignmentDocumentVO.getShipperUpuCode());
		this.setConsigneeUpuCode(consignmentDocumentVO.getConsigneeUpuCode());
		this.setOriginUpuCode(consignmentDocumentVO.getOriginUpuCode());
		this.setDestinationUpuCode(consignmentDocumentVO.getDestinationUpuCode());
		log.exiting("ConsignmentDocument", "populateAttributes");
	}

	/**
	 * @author  a-5991
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	private void populateChildren(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, DuplicateMailBagsException {
		log.entering("ConsignmentDocument", "populateChildren");
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO
				.getRoutingInConsignmentVOs();
		int carrierId=0;
		if (routingInConsignmentVOs != null
				&& routingInConsignmentVOs.size() > 0) {
			log.log(Log.FINE, " Going to create RoutingInConsignment ==>> ");
			Set<RoutingInConsignment> routingInConsignments = new HashSet<RoutingInConsignment>();
			for (RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs) {
//Added if check as part of CRQ ICRD-100406 by A-5526 
				if(routingInConsignmentVO.getOnwardFlightDate()!=null){  
				routingInConsignmentVO
						.setConsignmentSequenceNumber(consignmentDocumentVO
								.getConsignmentSequenceNumber());
				RoutingInConsignment routingInConsignment = new RoutingInConsignment(
						routingInConsignmentVO);
				carrierId=routingInConsignmentVO.getOnwardCarrierId();
				routingInConsignments.add(routingInConsignment);
			}
			}
			this.setRoutingsInConsignments(routingInConsignments);
		}
		if(consignmentDocumentVO.getConsignementScreeningVOs() !=null
				&& !consignmentDocumentVO.getConsignementScreeningVOs().isEmpty()){
			int bags = 0;
			double weight = 0;
			String airportCode = null;
			Collection<MailInConsignmentVO> mailInConsignment = consignmentDocumentVO.getMailInConsignmentVOs();
			
			for(MailInConsignmentVO mailInConsign : mailInConsignment){
				bags+= mailInConsign.getStatedBags();
				weight+= mailInConsign.getStatedWeight().getSystemValue();
				airportCode = mailInConsign.getMailOrigin();
			}
		
			Set<ConsignmentScreeningDetails> consignmentScreeningDetails = new HashSet<>();
			for(ConsignmentScreeningVO screeningVO :consignmentDocumentVO.getConsignementScreeningVOs()){
				screeningVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
				screeningVO.setPaCode(consignmentDocumentVO.getPaCode());
				if("CARDIT".equals(screeningVO.getSource())||"FILUPL".equals(screeningVO.getSource())){
					screeningVO.setStatedBags(bags);
					screeningVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, weight));
					screeningVO.setAirportCode(airportCode);
					if(airportCode!=null) {
					screeningVO.setScreeningLocation(airportCode);
					}else {
						screeningVO.setScreeningLocation(consignmentDocumentVO.getOrgin());
					}
					screeningVO.setResult(getScreeningResult(consignmentDocumentVO.getSecurityStatusCode()));
				}
				
			}
			new MailController().saveSecurityDetails(consignmentDocumentVO.getConsignementScreeningVOs());
		}
		Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO
				.getMailInConsignmentVOs();
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			log.log(Log.FINE, " Going to create MailInConsignment ==>> ");
			MailInConsignment mailInConsignment = null;
			int consignmentSeqNumber = consignmentDocumentVO
					.getConsignmentSequenceNumber();
			Set<MailInConsignment> mailInConsignments = new HashSet<MailInConsignment>();
			HashMap<String,Long> flightSeqNumMap=new HashMap<>();
			 HashMap<String,String> destOfficeExchangeMap=new HashMap<>();
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (mailInConsignmentVO.getMailId() != null) {
					mailInConsignmentVO.setCarrierId(carrierId);
					Mailbag mailbag = findMailBag(mailInConsignmentVO);
					boolean isDuplicate = false;
					if (mailbag != null) {
						// Modified by A-7540 starts
						isDuplicate = new MailController().checkForDuplicateMailbag(mailInConsignmentVO.getCompanyCode(),mailInConsignmentVO.getPaCode(),mailbag);
						if(!isDuplicate){
							mailbag.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
							mailbag.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
							mailbag.setPaCode(consignmentDocumentVO.getPaCode());     
							mailbag.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
							mailbag.setDespatchDate(consignmentDocumentVO.getConsignmentDate());
							//Added as part of bug IASCB-68156 by A-5526 starts
							 if(mailInConsignmentVO.getMailDestination()!=null){	
							mailbag.setDestination(mailInConsignmentVO.getMailDestination());
							 }   
							setSecurityStatusCode(consignmentDocumentVO, mailbag);
							//Added as part of bug IASCB-68156 by A-5526 ends
							mailbag.setDestinationOfficeOfExchange(mailInConsignmentVO.getDestinationExchangeOffice());
							if (mailInConsignmentVO.getStatedWeight() != null
									&& mailInConsignmentVO.getStatedWeight().getSystemValue() != mailbag.getWeight()
									&& MailConstantsVO.CARDIT_PROCESS.equals(mailInConsignmentVO.getMailSource())) {
								String paCode = new MailController().findSystemParameterValue(USPS_DOMESTIC_PA);
								if (paCode != null && paCode.equals(mailbag.getPaCode())) {
									mailbag.setWeight(mailInConsignmentVO.getStatedWeight().getSystemValue());
									mailbag.setDisplayValue(mailInConsignmentVO.getStatedWeight().getDisplayValue());
									mailbag.setDisplayUnit(mailInConsignmentVO.getStatedWeight().getDisplayUnit());
								}
							}							
						mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
						
						if(MailInConsignmentVO.FLAG_YES.equals(mailInConsignmentVO.getPaBuiltFlag())){
							mailbag.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
						}
						}
					    else{
		                        isDuplicate= true;	                 
							 }
						new DocumentController().modifyExistingMailInConsignment(mailInConsignmentVO);
						if(mailInConsignmentVO.getContractIDNumber()!=null&&mailInConsignmentVO.getContractIDNumber().trim().length()>0){
							mailbag.setContractIDNumber(mailInConsignmentVO.getContractIDNumber());
						}
						
						if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())){
							new MailController().updatemailperformanceDetails(mailbag);	
						}
						
						
					/*	if(MailConstantsVO.CARDIT_PROCESS.equals(mailInConsignmentVO.getMailSource())){
							           
                    	PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getPaCode());
                        LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
                        LocalDate dspDate = new LocalDate(
                                    LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);

                        long seconds=currentDate.findDifference(dspDate);
                        long days=seconds/86400000;
                        if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
                        	isDuplicate = false;
                        	mailbag.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
							mailbag.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
							mailbag.setPaCode(consignmentDocumentVO.getPaCode());
							mailbag.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
						mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
                        }
                        else{
                        isDuplicate= true;	                 
					 }
				  
				}
					else{
							 isDuplicate = checkForDuplicateMailbag(mailInConsignmentVO,mailbag);
						}	*/					
					}
                    if(mailbag == null || isDuplicate){
							if (MailInConsignmentVO.OPERATION_FLAG_INSERT
									.equals(mailInConsignmentVO.getOperationFlag())) {
								
								
											
										
								if (mailInConsignmentVO.getMailId() != null) {
									MailbagVO mailbagVO = new MailbagVO();
									mailbagVO
											.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
									mailbagVO.setCompanyCode(mailInConsignmentVO
											.getCompanyCode());
									mailbagVO.setDoe(mailInConsignmentVO
											.getDestinationExchangeOffice());
									mailbagVO.setOoe(mailInConsignmentVO
											.getOriginExchangeOffice());
									mailbagVO.setMailSubclass(mailInConsignmentVO
											.getMailSubclass());
			                        mailbagVO.setMailCategoryCode(mailInConsignmentVO
											.getMailCategoryCode());
									mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
									mailbagVO.setDespatchSerialNumber(mailInConsignmentVO
											.getDsn());
									mailbagVO.setDespatchId(createDespatchBag(mailInConsignmentVO));
									mailbagVO.setMailClass(mailInConsignmentVO
											.getMailClass());
									mailbagVO.setYear(mailInConsignmentVO.getYear());
									mailbagVO.setUldNumber(mailInConsignmentVO
											.getUldNumber());
									mailbagVO.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
									//Modified by A-7794 as part of ICRD-232299
									if(mailInConsignmentVO.getConsignmentDate() != null){
										mailbagVO.setConsignmentDate(mailInConsignmentVO.getConsignmentDate());
									}else{
									mailbagVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());//added by A-8353 for ICRD-230449
									}
									mailbagVO.setHighestNumberedReceptacle(mailInConsignmentVO.getHighestNumberedReceptacle());
									mailbagVO.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
									mailbagVO.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getRegisteredOrInsuredIndicator());
									mailbagVO.setScannedPort(consignmentDocumentVO.getAirportCode());
									mailbagVO.setScannedDate(consignmentDocumentVO.getConsignmentDate());
									mailbagVO.setCarrierId(mailInConsignmentVO.getCarrierId());
									mailbagVO.setWeight(mailInConsignmentVO.getStatedWeight());
									mailbagVO.setVolume(mailInConsignmentVO.getVolume());
									mailbagVO.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
									mailbagVO.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
									mailbagVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
									mailbagVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
									mailbagVO.setPaCode(consignmentDocumentVO.getPaCode());
									mailbagVO.setLatestStatus("NEW");
									//Added by A-7540
									mailbagVO.setLastUpdateUser(consignmentDocumentVO.getLastUpdateUser());
									mailbagVO.setScannedUser(consignmentDocumentVO.getLastUpdateUser());
									mailbagVO.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());//Added for ICRD-214795
									/*if(routingInConsignmentVOs!=null && routingInConsignmentVOs.size()>0){
										RoutingInConsignmentVO routingInConsignmentVO=routingInConsignmentVOs.iterator().next();
										mailbagVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
										mailbagVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
										mailbagVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
										mailbagVO.setFlightSequenceNumber(routingInConsignmentVO.getOnwardCarrierSeqNum());
										mailbagVO.setPou(routingInConsignmentVO.getPou());  
									}else{
										mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
										mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
									}*/		// commented as part of IASCB-32332
									
									mailbagVO.setDisplayUnit(mailInConsignmentVO.getDisplayUnit());//added by A-7371 for ICRD-234919
									//Added for ICRD-243469 starts
									
									
									if (mailInConsignmentVO.getMailServiceLevel() != null && !mailInConsignmentVO.getMailServiceLevel().isEmpty()) { 
										mailbagVO.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
									} /*else {
									String serviceLevel = null;
									serviceLevel = findMailServiceLevel(mailbagVO);
									if(serviceLevel!=null){
										mailbagVO.setMailServiceLevel(serviceLevel);
									}
									}*/
								    //Added as part of IASCb-32332 starts																	
									String destination=null;
									if(!destOfficeExchangeMap.isEmpty()&&destOfficeExchangeMap.containsKey(mailInConsignmentVO.getDestinationExchangeOffice())){
										destination=destOfficeExchangeMap.get(mailInConsignmentVO.getDestinationExchangeOffice());
									}
									else{
									try {
										 destination = constructDAO().findCityForOfficeOfExchange(mailInConsignmentVO.getCompanyCode(),mailInConsignmentVO.getDestinationExchangeOffice());
									} catch (PersistenceException e) {
									
									}
									destOfficeExchangeMap.put(mailInConsignmentVO.getDestinationExchangeOffice(), destination);  
									}
									if(routingInConsignmentVOs!=null && routingInConsignmentVOs.size()>0){
										for (RoutingInConsignmentVO routingVO : routingInConsignmentVOs) {
											if (routingVO.getPou()!=null){
											if (routingVO.getPou().equals(destination)) {
												mailbagVO.setFlightNumber(routingVO.getOnwardFlightNumber());
												mailbagVO.setFlightDate(routingVO.getOnwardFlightDate());
												 if(flightSeqNumMap!=null &&!flightSeqNumMap.isEmpty() && flightSeqNumMap.containsKey(destination)){
                                                 	mailbagVO.setFlightSequenceNumber(flightSeqNumMap.get(destination));
                                                }
												else{
												FlightFilterVO flightFilterVO = new FlightFilterVO();
												flightFilterVO.setCompanyCode(routingVO.getCompanyCode());
												flightFilterVO.setFlightNumber(routingVO.getOnwardFlightNumber().toUpperCase());
												flightFilterVO.setStation(routingVO.getPol());
												flightFilterVO.setDirection(mailInConsignmentVO.getOperation());
												flightFilterVO.setActiveAlone(false);
												flightFilterVO.setFlightDate(routingVO.getOnwardFlightDate());
												Collection<FlightValidationVO> flightValidationVOs = null;
												flightValidationVOs = new MailController().validateFlight(flightFilterVO);
												if (flightValidationVOs != null && flightValidationVOs.size() == 1) {
													for (FlightValidationVO vo : flightValidationVOs){
														mailbagVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
													   flightSeqNumMap.put(destination,vo.getFlightSequenceNumber());
													}
												 }
												else{
													flightSeqNumMap.put(destination,0L);
												}
												}
												break;
											}}else{
												if(routingInConsignmentVOs!=null && routingInConsignmentVOs.size()>0){
													RoutingInConsignmentVO routingInConsignmentVO=routingInConsignmentVOs.iterator().next();
													mailbagVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
													mailbagVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
													mailbagVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
													mailbagVO.setFlightSequenceNumber(routingInConsignmentVO.getOnwardCarrierSeqNum());
													mailbagVO.setPou(routingInConsignmentVO.getPou());  
													break;//else case of multi-seg can break after the first flight is populated
												}else{
													mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
													mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
												}
											}
										}
									}
									//Added as part of IASCb-32332 ends
										
									//added for ICRD-255189. Modified by A-8176 for IASCB-123250 
									  if(mailInConsignmentVO.getMailOrigin() == null || mailInConsignmentVO.getMailOrigin().isEmpty() ||  mailInConsignmentVO.getMailDestination() ==null || mailInConsignmentVO.getMailDestination().isEmpty()){
										   new MailController().constructOriginDestinationDetailsForConsignment(mailInConsignmentVO, mailbagVO); 
										}
									   else{
												mailbagVO.setOrigin(mailInConsignmentVO.getMailOrigin());
												mailbagVO.setDestination(mailInConsignmentVO.getMailDestination());					
										}
										//Added by A-7540 for IASCB-30647.Modified by A-8176 for for IASCB-123250 
										if((mailbagVO.getDestination() == null || mailbagVO.getDestination().isEmpty()) && mailbagVO.getFinalDestination()==null){
											String dest = findAirportCity(mailbagVO);						
											mailbagVO.setDestination(dest);
										}
									//Added by A-4809 for IASCB-137
									mailbagVO.setMailbagSource(mailInConsignmentVO.getMailSource()); 
									mailbagVO.setMailbagDataSource(mailInConsignmentVO.getMailSource()); 
									//Added for ICRD-243469 ends
									//Added by A-7794 as part of ICRD-232299
									String scanWavedDestn = constructDAO().checkScanningWavedDest(mailbagVO);
									if(scanWavedDestn != null){
										mailbagVO.setScanningWavedFlag(scanWavedDestn);
									}
									
									//ICRD-341146 Begin 
									if(new MailController().isUSPSMailbag(mailbagVO)){
										mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
									}else{
										mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
									}
									//ICRD-341146 End
									mailbagVO.setContractIDNumber(mailInConsignmentVO.getContractIDNumber());  //Added by A-7929 as part of IASCB-28260
									mailbagVO.setTransWindowEndTime(mailInConsignmentVO.getTransWindowEndTime());
									mailbagVO.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
									MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
									mailbag=new Mailbag(mailbagVO);
									
									
									
									
									if(mailbag!=null){
										//Added by A-8527 for IASCB-37531 starts
										//Removed as part of IASCB-48400
										//MailbagHistoryVO mailbagHistoryVO = mailbag.constructMailHistoryVO(mailbagVO);
										//mailbag.insertHistoryDetails(mailbagHistoryVO);
										//Added by A-8527 for IASCB-37531 Ends
										mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
									}
								}
							}
					}
				mailInConsignmentVO
						.setConsignmentSequenceNumber(consignmentSeqNumber);
				mailInConsignment = new MailInConsignment(mailInConsignmentVO);
				mailInConsignments.add(mailInConsignment);
				this.statedBags += mailInConsignmentVO.getStatedBags();
				//this.statedWeight += mailInConsignmentVO.getStatedWeight();
				this.statedWeight += mailInConsignmentVO.getStatedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */;//added by A-7371
			}
			this.setMailsInConsignments(mailInConsignments);
		}
		log.exiting("ConsignmentDocument", "populateChildren");
	}
	}
		public static String createDespatchBag(MailInConsignmentVO mailInConsignmentVO) {
			StringBuilder dsnid = new StringBuilder();
			dsnid.append(mailInConsignmentVO.getOriginExchangeOffice())
					.append(mailInConsignmentVO.getDestinationExchangeOffice())
					.append(mailInConsignmentVO.getMailCategoryCode())
					.append(mailInConsignmentVO.getMailSubclass())
					.append(mailInConsignmentVO.getYear())
					.append(mailInConsignmentVO.getDsn());
			return dsnid.toString();
		}
		/**
		 * @author a-1883
		 * @param mailInConsignmentVO
		 * @return
		 * @throws SystemException
		 */
		private Mailbag findMailBag(MailInConsignmentVO mailInConsignmentVO)
				throws SystemException {
			Mailbag mailbag = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
//			mailbagPk.setDestinationExchangeOffice(mailInConsignmentVO
//					.getDestinationExchangeOffice());
//			mailbagPk.setDsn(mailInConsignmentVO.getDsn());
//			mailbagPk.setMailbagId(mailInConsignmentVO.getMailId());
//			mailbagPk.setMailSubclass(mailInConsignmentVO.getMailSubclass());
//			mailbagPk
//					.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
//			mailbagPk.setOriginExchangeOffice(mailInConsignmentVO
//					.getOriginExchangeOffice());
//			mailbagPk.setYear(mailInConsignmentVO.getYear());
			try {
				mailbag = Mailbag.find(mailbagPk);
			} catch (FinderException finderException) {
				log.log(Log.FINE, " ++++  Finder Exception  +++");
				log.log(Log.FINE, " <===  Mailbag is Not accepted ===>");
				// throw new
				// SystemException(finderException.getErrorCode(),finderException);
			}
			return mailbag;
		}
		/**
		 * @author A-5991	
		 * @param mailIdr
		 * @param companyCode
		 * @return
		 * @throws SystemException 
		 */
		private long findMailSequenceNumber(String mailIdr,String companyCode) throws SystemException{
			return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
	}

	/**
	 * @author  a-5991
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 */
	public void update(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException {
		log.entering("ConsignmentDocument", "update");
		populateAttributes(consignmentDocumentVO);
		if(consignmentDocumentVO.getConsignementScreeningVOs() !=null
				&& !consignmentDocumentVO.getConsignementScreeningVOs().isEmpty()){
			Set<ConsignmentScreeningDetails> consignmentScreeningDetails = new HashSet<>();
			for(ConsignmentScreeningVO screeningVO :consignmentDocumentVO.getConsignementScreeningVOs()){
				screeningVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
				consignmentScreeningDetails.add(new ConsignmentScreeningDetails(screeningVO));
			}
			this.setConsignmentScreeningDetails(consignmentScreeningDetails);
		}
		log.exiting("ConsignmentDocument", "update");
	}

	/**
	 * @author  a-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("RoutingInConsignment", "remove");
		try {
			Collection<RoutingInConsignment> routingInConsignments = this
					.getRoutingsInConsignments();
			Collection<MailInConsignment> mailInConsignments = this
					.getMailsInConsignments();
			Collection<ConsignmentScreeningDetails> screeningDetails =
					this.getConsignmentScreeningDetails();
			if (routingInConsignments != null
					&& routingInConsignments.size() > 0) {
				for (RoutingInConsignment routingInConsignment : routingInConsignments) {
					routingInConsignment.remove();
				}
				this.routingsInConsignments.removeAll(routingInConsignments);
			}
			if (mailInConsignments != null && mailInConsignments.size() > 0) {
				for (MailInConsignment mailInConsignment : mailInConsignments) {
					mailInConsignment.remove();
				}
				this.mailsInConsignments.removeAll(mailInConsignments);
			}
			if (screeningDetails != null && screeningDetails.size() > 0) {
				for (ConsignmentScreeningDetails screeningDetail : screeningDetails) {
					screeningDetail.remove();
				}
				this.consignmentScreeningDetails.removeAll(screeningDetails);
			}
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
		log.exiting("RoutingInConsignment", "remove");
	}

	/**
	 * @author  a-5991
	 * @param consignmentDocumentVO
	 * @return
	 * @throws SystemException
	 */
	public static ConsignmentDocument find(
			ConsignmentDocumentVO consignmentDocumentVO) throws SystemException {
		Log findLog = LogFactory.getLogger("MAIL_OPERATIONS");
		findLog.entering("ConsignmentDocument", "find");
		ConsignmentDocument consignmentDocument = null;
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode(   consignmentDocumentVO
				.getCompanyCode());
		consignmentDocumentPk.setConsignmentNumber(   consignmentDocumentVO
				.getConsignmentNumber());
		consignmentDocumentPk.setPaCode(   consignmentDocumentVO.getPaCode());
		consignmentDocumentPk.setConsignmentSequenceNumber(   consignmentDocumentVO
				.getConsignmentSequenceNumber());
		try {
			consignmentDocument = PersistenceController.getEntityManager()
					.find(ConsignmentDocument.class, consignmentDocumentPk);
		} catch (FinderException finderException) {
			findLog.log(Log.SEVERE, "  Finder Exception ");
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}
		return consignmentDocument;
	}
	
	
	/**
	 * This method finds mail sequence number
	 * 
	 * @param mailInConsignmentVO
	 * @return int
	 * @throws SystemException
	 */
	public static int findMailSequenceNumber(
			MailInConsignmentVO mailInConsignmentVO) throws SystemException {
		Log findLog = LogFactory.getLogger("mail.operations");
		findLog.entering("ConsignmentDocument", "findMailSequenceNumber");
		return MailInConsignment.findMailSequenceNumber(mailInConsignmentVO);
	}
	
	/**
	 * @author A-2037
	 * @param companyCode
	 * @param mailId
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static MailInConsignmentVO findConsignmentDetailsForMailbag(
			String companyCode, String mailId, String airportCode)
			throws SystemException {
		Log findLog = LogFactory.getLogger("mail.operations");
		findLog.entering("ConsignmentDocument",
				"findConsignmentDetailsForMailbag");
		return MailInConsignment.findConsignmentDetailsForMailbag(companyCode,
				mailId, airportCode);
	}
	
	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException {
		try {
			return constructDAO().findConsignmentDocumentDetails(
					consignmentFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	
	/**
	    * @author A-3227  - FEB 10, 2009
	    * @param companyCode
	    * @param despatchDetailsVOs
	    * @return
	    * @throws SystemException
	    */
	   public static DespatchDetailsVO findConsignmentDetailsForDespatch(String companyCode,DespatchDetailsVO despatchDetailsVO)
	   throws SystemException{ 		   
		   try {
			   return constructDAO().findConsignmentDetailsForDespatch(companyCode,despatchDetailsVO);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
	   }
	   
	   /**
		 * @author a-3429 This method returns Consignment Details for Print
		 * @param consignmentFilterVO
		 * @return ConsignmentDocumentVO
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public static ConsignmentDocumentVO generateConsignmentReport(
				ConsignmentFilterVO consignmentFilterVO) throws SystemException {
			try {
				return constructDAO().generateConsignmentReport(
						consignmentFilterVO);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}
		
		/**
		 * @author a-2391 This method returns Consignment Details
		 * @param consignmentFilterVO
		 * @return ConsignmentDocumentVO
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public static ConsignmentDocumentVO findConsignmentDocumentDetailsForHHT(
				ConsignmentFilterVO consignmentFilterVO) throws SystemException {
			try {
				return constructDAO().findConsignmentDocumentDetailsForHHT(
						consignmentFilterVO);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}
		
		
		public Collection<MailInConsignmentVO> findConsignmentDetailsForDsn(String companyCode, DSNVO dsnVO)
				   throws SystemException{ 		   
					   try {
						   return constructDAO().findConsignmentDetailsForDsn(companyCode,dsnVO);
					} catch (PersistenceException persistenceException) {
						persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}

		/**
		 * @author A-2667
		 * @param carditEnquiryFilterVO
		 * @return
		 * @throws SystemException
		 */
		public static Page<MailbagVO> findConsignmentDetails(CarditEnquiryFilterVO carditEnquiryFilterVO,int pageNumber) throws SystemException
		{
			try {
				return constructDAO().findConsignmentDetails(
						carditEnquiryFilterVO,pageNumber);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		} 		
			
			
			
				
				
					
				
				
				
				
				
				
			
		
	
		
		/**
		 * @author A-8353
		 * @param mailInConsignmentVO
		 * @param mailbag
		 * @return
		 * @throws SystemException
		 * @throws DuplicateMailBagsException
		 */
		/*private boolean checkForDuplicateMailbag(MailInConsignmentVO mailInConsignmentVO,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
            PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getPaCode());
            LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
            LocalDate dspDate = new LocalDate(
                        LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);

            long seconds=currentDate.findDifference(dspDate);
            long days=seconds/86400000;
            if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
            	throw new DuplicateMailBagsException(
            			DuplicateMailBagsException.
            			DUPLICATEMAILBAGS_EXCEPTION,
            			new Object[] {mailInConsignmentVO.getMailId()});
            }
            return true;
      }*/
		/**
		 * @author A-7540
		 * @param mailbagVO
		 * @return
		 */
		private String findAirportCity(MailbagVO mailbagVO) throws SystemException{
			String dest = mailbagVO.getDoe().substring(2,5);
			String city=null;
			//findAirportCityForMLD method is used to find the airport from SHRARPMST
			try {
				city =constructDAO().findAirportCityForMLD(mailbagVO.getCompanyCode(), dest);
			} catch (PersistenceException e) {
				e.getMessage();
			}
			return city;
		}
		
		public void updateScreeningDetails(ConsignmentDocumentVO consignmentDocumentVO) throws FinderException, SystemException {
			log.entering("ConsignmentDocument", "update");
			
			
			
			if(consignmentDocumentVO.getConsignementScreeningVOs() !=null
					&& !consignmentDocumentVO.getConsignementScreeningVOs().isEmpty()){
				
				for(ConsignmentScreeningVO conScreening : consignmentDocumentVO.getConsignementScreeningVOs() ){
				if((ConsignmentDocumentVO.OPERATION_FLAG_UPDATE).equals(conScreening.getOpFlag())){
					ConsignmentScreeningDetails consignmentScreeningDetails = null;
					
					consignmentScreeningDetails = ConsignmentScreeningDetails.find(
							conScreening.getCompanyCode(),
							conScreening.getSerialNumber());
					consignmentScreeningDetails.update(conScreening);
				}else if((ConsignmentDocumentVO.OPERATION_FLAG_INSERT).equals(conScreening.getOpFlag())){
					
					new ConsignmentScreeningDetails(conScreening);
					
				}else if((ConsignmentDocumentVO.OPERATION_FLAG_DELETE).equals(conScreening.getOpFlag())){
					ConsignmentScreeningDetails consignmentScreeningDetails = null;
					consignmentScreeningDetails = ConsignmentScreeningDetails.find(
							conScreening.getCompanyCode(),
							conScreening.getSerialNumber());
					consignmentScreeningDetails.remove();
				}
			}
				
			}
		}
		/**
		 * 	Method		:	ConsignmentDocument.generateConsignmentSummaryReport
		 *	Added by 	:	A-9084 on 12-Nov-2020
		 * 	Used for 	:
		 *	Parameters	:	@param consignmentFilterVO
		 *	Parameters	:	@return 
		 *	Return type	: 	ConsignmentDocumentVO
		 * @throws SystemException 
		 */
		public static ConsignmentDocumentVO generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO) throws SystemException, PersistenceException {
			return constructDAO().generateConsignmentSummaryReport(
					consignmentFilterVO);
		}
	/**
	 * Method returns screening result as pass or fail
	 * Screening result value depends on the security status code
	 * @param consignmentScreeningVO
	 * @return String
	 */
	private String getScreeningResult(String securityStatusCode) {
		String screenResultPass = SCREENING_RESULT_PASS;
		String screenResultFail = SCREENING_RESULT_FAIL;
		List<String> screenResultPassList = Arrays.asList(screenResultPass.split(COMMA));
		List<String> screenResultFailList = Arrays.asList(screenResultFail.split(COMMA));
		if (Objects.nonNull(securityStatusCode) && 
				screenResultPassList.stream().anyMatch(securityStatusCode::equals)) {
			return PASS;
		}
		if (Objects.nonNull(securityStatusCode) && 
				screenResultFailList.stream().anyMatch(securityStatusCode::equals)) {
			return FAIL;
		}
		return null;
	}
	/**
	 * @author A-8353
	 * @param consignmentDocumentVO
	 * @param mailbag
	 */
	private void setSecurityStatusCode(ConsignmentDocumentVO consignmentDocumentVO, Mailbag mailbag) {
		if (consignmentDocumentVO.getSecurityStatusCode()!=null){
		mailbag.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
		}
	}
}
