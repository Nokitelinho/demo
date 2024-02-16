											 /*
 * Container.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

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

import com.ibsplc.icargo.business.mail.operations.OnwardRouting;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

/**
 * @author a-3109 This class represents a Container (ULD/Barrow) assigned to a
 *         flight or carrier.
 * 
 */
@Entity
@Table(name = "MALFLTCON")
public class Container {

	private static final String MODULE = "mail.operations";

	private static final String ENTITY = "Container";

	private static final String CONTAINER_TYPE_BULK = "B";

	private static final int SEQUENCE_NUMBERS = -1;

	private static final String FLIGHT_NUMBER = "-1";
	private static final Log LOG = LogFactory.getLogger(MODULE);
	private static final String CONTAINER_ID = "CONTAINER_ID";

	private ContainerPK containerPK;
	private static final String MAL_FLTCON_ULDREFNUM="MAL_FLTCON_ULDREFNUM";
	private static final String CONTAINERS = "CONTAINER";

	/**
	 * containerType
	 */
	private String containerType;

	/**
	 * final Destination
	 */
	private String finalDestination;

	/**
	 * remarks
	 */
	private String remarks;

	/**
	 * paBuiltFlag
	 */
	private String paBuiltFlag;

	/**
	 * onward Routes
	 */
	private Set<OnwardRouting> onwardRoutes;

	/**
	 * pou
	 */
	private String pou;

	/**
	 * segment SerialNumber
	 */
	private int segmentSerialNumber;

	/**
	 * assignedOn
	 */
	private Calendar assignedOn;

	/**
	 * assignedDateUTC
	 */
	private Calendar assignedDateUTC;

	/**
	 * assignedBy
	 */
	private String assignedBy;

	/**
	 * acceptanceFlag
	 */
	private String acceptanceFlag;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * carrierCode
	 */
	private String carrierCode;

	/**
	 * offload Flag
	 */
	private String offloadFlag;

	/**
	 * Arrival flag
	 */
	private String arrivedStatus;

	/**
	 * transfer Flag
	 */
	private String transferFlag;

	/**
	 * paBuiltOpened Flag
	 */
	private String paBuiltOpenedFlag;

	/**
	 * delivered Flag
	 */
	private String deliveredFlag;

	/**
	 * actualWeight
	 */
	private double actualWeight;

	/**
	 * intact
	 */
	private String intact;

	/**
	 * transaction Code
	 */
	private String transactionCode;

	/**
	 * containerJnyIdr
	 */
	private String containerJnyIdr;

	/**
	 * shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB
	 * ULD.
	 */
	private String shipperBuiltCode;

	/**
	 * Transit Flag
	 */
	private String transitFlag;
	
	private String contentId;//Added for ICRD-239331
	
	private double uldHeight;

	//Added by A-7540 for IASCB-25432
 	private String actualWeightDisplayUnit;
 	private double actualWeightDisplayValue;
 	private String retainFlag; //Added by A-8672 for IASCB-46064
 	private Calendar firstMalbagAsgDat;//Added by A-8353 
 	private String uldFulIndFlag;
 	private long uldReferenceNo;
 	private String actWgtSta;
	private String containerPosition;
	
 	@Column(name = "ULDREFNUM")
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}

	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}

	/**
	 * @return Returns the containerType.
	 * 
	 */
	@Column(name = "CONTYP")
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	
	@Column(name = "ULDFULIND")
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}

	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}

	/**
	 * @return Returns the finalDestination.
	 * 
	 */
	@Column(name = "DSTCOD")
	@Audit(name = "finalDestination")
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *            The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the paBuiltFlag.@Column(name="POAFLG")
	 */
	@Column(name = "POAFLG")
	@Audit(name = "paBuiltFlag")
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	/**
	 * @param paBuiltFlag
	 *            The paBuiltFlag to set.
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	/**
	 * @return Returns the remarks.
	 * 
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the transitFlag
	 */
	@Column(name = "TRNFLG")
	public String getTransitFlag() {
		return transitFlag;
	}

	/**
	 * @param transitFlag
	 *            the transitFlag to set
	 */
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	/**
	 * @return Returns the assignedBy.
	 */
	@Column(name = "USRCOD")
	public String getAssignedBy() {
		return assignedBy;
	}

	/**
	 * @param assignedBy
	 *            The assignedBy to set.
	 */
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	/**
	 * @return Returns the assignedOn.
	 */
	@Column(name = "ASGDAT")
	@Audit(name = "assignedOn")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getAssignedOn() {
		return assignedOn;
	}

	/**
	 * @param assignedOn
	 *            The assignedOn to set.
	 */
	public void setAssignedOn(Calendar assignedOn) {
		this.assignedOn = assignedOn;
	}

	/**
	 * @return Returns the pou.
	 */
	@Column(name = "POU")
	@Audit(name = "pou")
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
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the acceptanceFlag.
	 */
	@Column(name = "ACPFLG")
	public String getAcceptanceFlag() {
		return acceptanceFlag;
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
	 * @param acceptanceFlag
	 *            The acceptanceFlag to set.
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	/**
	 * @return Returns the offloadFlag.
	 */
	@Column(name = "OFLFLG")
	public String getOffloadFlag() {
		return offloadFlag;
	}

	/**
	 * @param offloadFlag
	 *            The offloadFlag to set.
	 */
	public void setOffloadFlag(String offloadFlag) {
		this.offloadFlag = offloadFlag;
	}

	/**
	 * @return Returns the arrivedFlag.
	 */
	@Column(name = "ARRSTA")
	public String getArrivedStatus() {
		return this.arrivedStatus;
	}

	/**
	 * @param arrivedFlag
	 *            The arrivedFlag to set.
	 */
	public void setArrivedStatus(String arrivedFlag) {
		this.arrivedStatus = arrivedFlag;
	}

	/**
	 * @return Returns the transferFlag.
	 */
	@Column(name = "TRAFLG")
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param transferFlag
	 *            The transferFlag to set.
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * @return Returns the paBuiltOpenedFlag.
	 */
	@Column(name = "POAOPN")
	public String getPaBuiltOpenedFlag() {
		return paBuiltOpenedFlag;
	}

	/**
	 * @param paBuiltOpenedFlag
	 *            The paBuiltOpenedFlag to set.
	 */
	public void setPaBuiltOpenedFlag(String paBuiltOpenedFlag) {
		this.paBuiltOpenedFlag = paBuiltOpenedFlag;
	}

	/**
	 * @return Returns the deliveredFlag.
	 */
	@Column(name = "DLVFLG")
	public String getDeliveredFlag() {
		return deliveredFlag;
	}

	/**
	 * @param deliveredFlag
	 *            The deliveredFlag to set.
	 */
	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}

	/**
	 * @return the intact
	 */
	@Column(name = "INTFLG")
	public String getIntact() {
		return intact;
	}

	/**
	 * @param intact
	 *            the intact to set
	 */
	public void setIntact(String intact) {
		this.intact = intact;
	}

	/**
	 * @return the transactionCode
	 */
	@Column(name = "TXNCOD")
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param transactionCode
	 *            the transactionCode to set
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * AirNZ - 985 Saving PACODE and Container Jaourney ID
	 */

	@Column(name = "CONJRNIDR")
	public String getContainerJnyIdr() {
		return containerJnyIdr;
	}

	public void setContainerJnyIdr(String containerJnyIdr) {
		this.containerJnyIdr = containerJnyIdr;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	@Column(name = "POACOD")
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode
	 *            the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
	}

	/**
	 * @return the assignedDateUTC
	 */
	@Column(name = "ASGDATUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getAssignedDateUTC() {
		return assignedDateUTC;
	}

	/**
	 * @param assignedDateUTC
	 *            the assignedDateUTC to set
	 */
	public void setAssignedDateUTC(Calendar assignedDateUTC) {
		this.assignedDateUTC = assignedDateUTC;
	}

	@Column(name = "ACTULDWGT")
	@Audit(name = "actualWeight")
	public double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return the actualWeightDisplayUnit
	 */
	@Column(name = "ACTULDWGTDSPUNT")
	public String getActualWeightDisplayUnit() {
		return actualWeightDisplayUnit;
	}
	/**
	 * @param actualWeightDisplayUnit the actualWeightDisplayUnit to set
	 */
	public void setActualWeightDisplayUnit(String actualWeightDisplayUnit) {
		this.actualWeightDisplayUnit = actualWeightDisplayUnit;
	}
	/**
	 * @return the actualWeightDisplayValue
	 */
	@Column(name = "ACTULDWGTDSP")
	public double getActualWeightDisplayValue() {
		return actualWeightDisplayValue;
	}
	/**
	 * @param actualWeightDisplayValue the actualWeightDisplayValue to set
	 */
	public void setActualWeightDisplayValue(double actualWeightDisplayValue) {
		this.actualWeightDisplayValue = actualWeightDisplayValue;
	}
	/**
	 * 
	 * @param onwardRoutes
	 */
	public void setOnwardRoutes(Set<OnwardRouting> onwardRoutes) {
		this.onwardRoutes = onwardRoutes;
	}
	
	@Column(name = "CNTIDR")
	@Audit(name = "contentId")
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@Column(name = "ULDHGT")
	public double getUldHeight() {
		return uldHeight;
	}

	public void setUldHeight(double uldHeight) {
		this.uldHeight = uldHeight;
	}

	@Column(name = "RETFLG")
	public String getRetainFlag() {
		return retainFlag;
	}
	public void setRetainFlag(String retainFlag) {
		this.retainFlag = retainFlag;
	}
	@Column(name = "FSTMALASGDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFirstMalbagAsgDat() {
		return firstMalbagAsgDat;
	}

	public void setFirstMalbagAsgDat(Calendar firstMalbagAsgDat) {
		this.firstMalbagAsgDat = firstMalbagAsgDat;
	}
	@Column(name = "ACTWGTSTA")
	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
	@Column(name = "CONPOS")
	public String getContainerPosition() {
		return containerPosition;
	}
	public void setContainerPosition(String containerPosition) {
		this.containerPosition = containerPosition;
	}
	/**
	 * The embedded Id for the PK
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
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")) })
	public ContainerPK getContainerPK() {
		return containerPK;
	}

	/**
	 * @param containerPK
	 *            The containerPK to set.
	 */
	public void setContainerPK(ContainerPK containerPK) {
		this.containerPK = containerPK;
	}

	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CONNUM", referencedColumnName = "CONNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ASGPRT", referencedColumnName = "ASGPRT", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "LEGSERNUM", referencedColumnName = "LEGSERNUM", insertable = false, updatable = false) })
	public Set<OnwardRouting> getOnwardRoutes() {
		return onwardRoutes;
	}

	/**
	 * The defaultConstructor
	 * 
	 */
	public Container() {
	}

	/**
	 * 
	 * @author a-3109 The constructor called when the instance is to be
	 *         persisted
	 * @param containerVO
	 * @throws SystemException
	 */
	public Container(ContainerVO containerVO) throws SystemException {
		populatePK(containerVO);
		populateAttributes(containerVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		populateChildren(containerVO);

	}

	/**
	 * This method is used to populate the Routing Details
	 * 
	 * @author a-3109
	 * @param containerVO
	 * @throws SystemException
	 */
	public void populateChildren(ContainerVO containerVO)
			throws SystemException {
		LOG.log(Log.INFO, "INSIDE THE POPULATE CHILDREN");
		Collection<OnwardRoutingVO> onwardRoutinVos = null;
		if (containerVO.getOnwardRoutings() != null
				&& containerVO.getOnwardRoutings().size() > 0) {
			LOG.log(Log.INFO, "populateRoutingDetails called");
			onwardRoutinVos = containerVO.getOnwardRoutings();
			populateRoutingDetails(onwardRoutinVos);
		}

	}

	/**
	 * 
	 * @author a-3109 This method is used to populate the RoutingDetails
	 * @param onwardRoutingVos
	 * @throws SystemException
	 */
	private void populateRoutingDetails(
			Collection<OnwardRoutingVO> onwardRoutingVos)
			throws SystemException {
		for (OnwardRoutingVO onwardRoutingVo : onwardRoutingVos) {
			if (OPERATION_FLAG_INSERT
					.equals(onwardRoutingVo.getOperationFlag())) {
				LOG.log(Log.INFO, "CREATE ROUTING DETAILS CALLED");
				createRoutingDetails(onwardRoutingVo);
			}
			if (OPERATION_FLAG_UPDATE
					.equals(onwardRoutingVo.getOperationFlag())) {
				LOG.log(Log.INFO, "MODIFY  ROUTING DETAILS CALLED");

			}

			if (OPERATION_FLAG_DELETE
					.equals(onwardRoutingVo.getOperationFlag())) {
				LOG.log(Log.INFO, "MODIFY  ROUTING  DETAILS CALLED");

			}
		}

	}

	/**
	 * @author a-3109 1.This method is used to append the Prefix say 00 to
	 *         Criterion generated Key if the key generated is from 0 to 9
	 *         2.else if the key is from 10 to 99 append the prefix 0 . so it
	 *         will formulate a string of always size 3. can be reused to get
	 *         theprefix of any length.
	 * @param key
	 */
	private String formulatePrefix(String key) {
		/*
		 * can be reused to get the prefix of any length by changing the size
		 */

		int size = 3;
		int difference = size - key.length();
		StringBuilder prefixBuilder = new StringBuilder();
		for (int i = 0; i < difference; i++) {
			prefixBuilder.append(0);
		}

		return prefixBuilder.append(key).toString();

	}

	/**
	 * @author a-3109 This method is used to create the RoutingDetail
	 * @param onwardRoutingVo
	 * @throws SystemException
	 */
	private void createRoutingDetails(OnwardRoutingVO onwardRoutingVo)
			throws SystemException {
		LOG.log(Log.FINE, "", this.getContainerPK().getAssignmentPort());
		LOG.log(Log.FINE, "", this.getContainerPK().getContainerNumber());
		LOG.log(Log.FINE, "", this.getContainerPK().getCompanyCode());
		onwardRoutingVo.setContainerNumber(this.getContainerPK()
				.getContainerNumber());
		onwardRoutingVo.setCompanyCode(this.getContainerPK().getCompanyCode());
		onwardRoutingVo.setAssignmenrPort(this.getContainerPK()
				.getAssignmentPort());
		onwardRoutingVo.setCarrierId(this.getContainerPK().getCarrierId());
		onwardRoutingVo
				.setFlightNumber(this.getContainerPK().getFlightNumber());
		onwardRoutingVo.setFlightSequenceNumber(this.getContainerPK()
				.getFlightSequenceNumber());
		onwardRoutingVo.setLegSerialNumber(this.getContainerPK()
				.getLegSerialNumber());

		OnwardRouting onwardRouting = new OnwardRouting(onwardRoutingVo);
		if (getOnwardRoutes() == null) {
			setOnwardRoutes(new HashSet<OnwardRouting>());
		}
		getOnwardRoutes().add(onwardRouting);
		LOG.log(Log.FINE, "THE SIZE OF THE CONTAINERDETAILS IN THE SET IS",
				getOnwardRoutes().size());

	}

	/**
	 * 
	 * @author a-1936 This method is used to remove all the children instances
	 *         when the Parent is requested for delete ..
	 * @throws SystemException
	 */
	private void removeChildren() throws SystemException {
		LOG.log(Log.INFO, "INSIDE THE REMOVE METHOD FOR THE CHILDREN");
		if (getOnwardRoutes() != null && getOnwardRoutes().size() > 0) {
			LOG.log(Log.INFO, "THE SIZE OF THE CONTAINERDETAILS",
					getOnwardRoutes().size());
			for (OnwardRouting onwardRouting : getOnwardRoutes()) {
				onwardRouting.remove();
			}
			LOG.log(Log.INFO, "CHILD REMOVED");
		}

	}

	/**
	 * This method is used to populate the pk for the container
	 * 
	 * @param containerVO
	 * @throws SystemException
	 */
	private void populatePK(ContainerVO containerVO) throws SystemException {
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(containerVO.getCompanyCode());
		// if (containerVO.isPreassignNeeded() &&
		// CONTAINER_TYPE_BULK.equals(containerVO.getType()) &&
		// !containerVO.isReassignFlag()) {
		if ((containerVO.getContainerNumber() == null || containerVO
				.getContainerNumber().trim().length() == 0)
				&& !containerVO.isReassignFlag()) {
			String assignedPort = containerVO.getAssignedPort();
			LocalDate localDate = new LocalDate(assignedPort, Location.ARP,
					false);
			String date = localDate.toDisplayFormat("dd-MM-yy");
			StringBuilder keyBuilder = new StringBuilder(CONTAINER_TYPE_BULK);
			keyBuilder.append(assignedPort);
			StringTokenizer tokenizer = new StringTokenizer(date, "-");
			while (tokenizer.hasMoreTokens()) {
				keyBuilder.append(tokenizer.nextToken());
			}

			String keyCondition = keyBuilder.toString();
			Criterion criterion = KeyUtils.getCriterion(
					containerPk.getCompanyCode(), CONTAINER_ID, keyCondition);

			String key = KeyUtils.getKey(criterion);
			if (key.length() < 3) {
				key = formulatePrefix(key);
			}
			containerPk.setContainerNumber(new StringBuilder(keyCondition)
					.append(key).toString());
		} else {
			containerPk.setContainerNumber(containerVO.getContainerNumber());
		}
		containerPk.setAssignmentPort(containerVO.getAssignedPort());
		if (containerVO.getFlightNumber() == null
				|| containerVO.getFlightNumber().trim().length() == 0) {

			containerPk.setFlightNumber(FLIGHT_NUMBER);
			containerPk.setFlightSequenceNumber(SEQUENCE_NUMBERS);
			containerPk.setLegSerialNumber(SEQUENCE_NUMBERS);
		} else {

			containerPk.setFlightNumber(containerVO.getFlightNumber());
			containerPk.setFlightSequenceNumber(containerVO
					.getFlightSequenceNumber());
			containerPk.setLegSerialNumber(containerVO.getLegSerialNumber());

		}
		containerPk.setCarrierId(containerVO.getCarrierId());
		this.setContainerPK(containerPk);

	}

	/**
	 * @author a-1936 This method is used
	 * @param containerVO
	 */
	private void populateAttributes(ContainerVO containerVO) throws SystemException{
		LOG.entering("Inside the Contailner", "populateAttributes");
		LOG.log(Log.FINE, "The Container Vo in populateAttributes", containerVO);
		this.setAssignedBy(containerVO.getAssignedUser());
		if (containerVO.getAssignedDate() != null) {
			this.setAssignedOn(new LocalDate(containerVO.getAssignedPort(),
					Location.ARP, containerVO.getAssignedDate(), true));
			this.setAssignedDateUTC(containerVO.getAssignedDate().toGMTDate());
		}
		this.setContainerType(containerVO.getType());
		this.setFinalDestination(containerVO.getFinalDestination());
		this.setLastUpdateUser(containerVO.getLastUpdateUser());
		this.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		this.setDeliveredFlag(MailConstantsVO.FLAG_NO);
		if(containerVO.getTransferFlag()!=null){
		this.setTransferFlag(containerVO.getTransferFlag());
		}else{
			this.setTransferFlag(MailConstantsVO.FLAG_NO);
		}
		if(containerVO.getPaBuiltFlag()!=null && containerVO.getPaBuiltFlag().length()>0){
		this.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		}
		else{
			this.setPaBuiltFlag(ContainerVO.FLAG_NO);
		}
		this.setPou(containerVO.getPou());
		this.setRemarks(containerVO.getRemarks());
		this.setCarrierCode(containerVO.getCarrierCode());
		if(containerVO.getAcceptanceFlag()!=null){
		this.setAcceptanceFlag(containerVO.getAcceptanceFlag());
		}else{
			this.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
		}
		
		if (containerVO.getOffloadFlag() != null) {
			setOffloadFlag(containerVO.getOffloadFlag());
		} else {
			setOffloadFlag(MailConstantsVO.FLAG_NO);
		}
		if(containerVO.getArrivedStatus()!=null){
		setArrivedStatus(containerVO.getArrivedStatus());
		}else{
			setArrivedStatus(MailConstantsVO.FLAG_NO);
		}
		setTransferFlag(MailConstantsVO.FLAG_NO);
		if(containerVO.getPaBuiltOpenedFlag()!=null){
		setPaBuiltOpenedFlag(containerVO.getPaBuiltOpenedFlag());
		}else{
			setPaBuiltOpenedFlag(MailConstantsVO.FLAG_NO);
		}
		setLastUpdateTime(containerVO.getLastUpdateTime());
		setLastUpdateUser(containerVO.getLastUpdateUser());
		setTransactionCode(containerVO.getTransactionCode());
		setContainerJnyIdr(containerVO.getContainerJnyID());
		setShipperBuiltCode(containerVO.getShipperBuiltCode());
		if (containerVO.getIntact() == null) {
			setIntact(MailConstantsVO.FLAG_NO);
		} else {
			setIntact(containerVO.getIntact());
			
		}
		this.setDeliveredFlag(MailConstantsVO.FLAG_NO);
		//this.setActualWeight(containerVO.getActualWeight());
		if(containerVO.getActualWeight()!=null){
		this.setActualWeight(containerVO.getActualWeight().getSystemValue());//added by A-7371
		this.setActualWeightDisplayValue(containerVO.getActualWeight().getDisplayValue());
		this.setActualWeightDisplayUnit(containerVO.getActualWeight().getDisplayUnit());		
		}
		if(containerVO.getTransitFlag()!= null){
		setTransitFlag(containerVO.getTransitFlag());
			
		}else{
			setTransitFlag(MailConstantsVO.FLAG_NO);
		}
		/*if (containerVO.getFlightSequenceNumber() > 0) {
			setContentId(containerVO.getContentId());
		} else {
			setContentId(null);
		}*/  // Commented as part of IASCB-388
		
		if (containerVO.getUldFulIndFlag() != null) {
			setUldFulIndFlag(containerVO.getUldFulIndFlag());
		} else {
			setUldFulIndFlag(MailConstantsVO.FLAG_NO);
		}
		setContentId(containerVO.getContentId()); //Added as part of IASCB-388
		setRetainFlag(MailConstantsVO.FLAG_NO);
		if(getFirstMalbagAsgDat()==null&&containerVO.isMailbagPresent()){
			if(containerVO.getAssignedDate()!=null&&containerVO.getFirstAssignDate()==null ){
			setFirstMalbagAsgDat(new LocalDate(containerVO.getAssignedPort(),
						Location.ARP, containerVO.getAssignedDate(), true));
			 }
			else if (containerVO.getFirstAssignDate()!=null){
				setFirstMalbagAsgDat(new LocalDate(containerVO.getAssignedPort(),
						Location.ARP, containerVO.getFirstAssignDate(), true));  
			}
			else{
			setFirstMalbagAsgDat(new LocalDate(containerVO.getAssignedPort(),
						Location.ARP, true));
			 }
			}
		if(containerVO.getUldReferenceNo() > 0){
			this.setUldReferenceNo(containerVO.getUldReferenceNo());
		}
		else
		{
			
			this.setUldReferenceNo(generateULDReferenceNumber(containerVO));
		}
		if(containerVO.getActWgtSta()!=null){
			this.setActWgtSta(containerVO.getActWgtSta());
		}
		LOG.log(Log.FINE, "The Container Vo in populateAttributes", containerVO);
		LOG.exiting(CONTAINERS, "populateAttributes");
		}
		

	/**
	 * @author a-1936 This method is used to update the BusinessObject
	 * @param containerVO
	 * @throws SystemException
	 */
	public void update(ContainerVO containerVO) throws SystemException {
		LOG.entering("Inside the update method", "For Container");
		populateAttributes(containerVO);
		populateChildren(containerVO);

	}

	/**
	 * @author a-1936 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		LOG.entering("CONTAINER", "REMOVE METHOD CALLED");
		try {
			removeChildren();
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		LOG.exiting("CONTAINER", "REMOVE");
	}

	/**
	 * @author a-1936
	 * @param containerPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static Container find(ContainerPK containerPK)
			throws SystemException, FinderException {
		/*try {
			PersistenceController.getEntityManager().flush();//Added by a-7871 for ICRD-240184
			PersistenceController.getEntityManager().clear();	
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getMessage(), e);
		}*/
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(Container.class, containerPK);
	}

	/**
	 * @author A-3227 This method fetches the latest Container Assignment
	 *         irrespective of the PORT to which it is assigned. This to know
	 *         the current assignment of the Container.
	 * @param containerNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static ContainerAssignmentVO findLatestContainerAssignment(
			String companyCode, String containerNumber) throws SystemException {
		try {
			return constructDAO().findLatestContainerAssignment(companyCode,
					containerNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * 
	 * @author a-1936 This method is used to retrieve the
	 *         currentContainerDetails.If the ContainerAssigmentVo is null
	 *         Assignments can be made
	 * @param companyCode
	 * @param containerNumber
	 * @param pol
	 * @return
	 * @throws SystemException
	 */
	public static ContainerAssignmentVO findContainerAssignment(
			String companyCode, String containerNumber, String pol)
			throws SystemException {
		try {
			return constructDAO().findContainerAssignment(companyCode,
					containerNumber, pol);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	public static ContainerAssignmentVO findArrivalDetailsForULD(
			ContainerVO containerVO) throws SystemException {
		try {
			return constructDAO().findArrivalDetailsForULD(containerVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	/**
	 * A-1739
	 * 
	 * @return
	 */
	public ContainerVO retrieveVO() {
		ContainerVO containerVO = new ContainerVO();
		ContainerPK containerPk = getContainerPK();
		containerVO.setCompanyCode(containerPk.getCompanyCode());
		containerVO.setContainerNumber(containerPk.getContainerNumber());
		containerVO.setCarrierId(containerPk.getCarrierId());
		containerVO.setFlightNumber(containerPk.getFlightNumber());
		containerVO.setFlightSequenceNumber(containerPk
				.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(containerPk.getLegSerialNumber());
		containerVO.setAssignedPort(containerPk.getAssignmentPort());
		containerVO.setAcceptanceFlag(getAcceptanceFlag());
		containerVO.setAssignedUser(getAssignedBy());
		containerVO.setAssignedDate(new LocalDate(containerPk
				.getAssignmentPort(), Location.ARP, getAssignedOn(), true));
		containerVO.setType(getContainerType());
		containerVO.setFinalDestination(getFinalDestination());
		containerVO.setPaBuiltFlag(getPaBuiltFlag());
		containerVO.setPou(getPou());
		containerVO.setRemarks(getRemarks());
		containerVO.setSegmentSerialNumber(getSegmentSerialNumber());
		containerVO.setOffloadFlag(getOffloadFlag());
		containerVO.setArrivedStatus(getArrivedStatus());
		containerVO.setPaBuiltOpenedFlag(getPaBuiltOpenedFlag());
		containerVO.setTransactionCode(getTransactionCode());
		containerVO.setContainerJnyID(getContainerJnyIdr());
		containerVO.setShipperBuiltCode(getShipperBuiltCode());
		containerVO.setContentId(getContentId());//Added as part of ICRD-239331
		containerVO.setUldFulIndFlag(getUldFulIndFlag());//Added by A-9998 for IASCB-140729
		if(getUldReferenceNo() != 0){
		containerVO.setUldReferenceNo(getUldReferenceNo());
		}
		onwardRoutes = getOnwardRoutes();
		if (onwardRoutes != null && onwardRoutes.size() > 0) {
			Collection<OnwardRoutingVO> onwardRouteVOs = new ArrayList<OnwardRoutingVO>();
			for (OnwardRouting onwardRouting : onwardRoutes) {
				OnwardRoutingVO onwardRoutingVO = onwardRouting.retrieveVO();
				onwardRouteVOs.add(onwardRoutingVO);
			}
			containerVO.setOnwardRoutings(onwardRouteVOs);
		}
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,getActualWeight()));
		if(getFirstMalbagAsgDat()!=null){
		containerVO.setFirstAssignDate(new LocalDate(containerPk
				.getAssignmentPort(), Location.ARP, getFirstMalbagAsgDat(), true));
		containerVO.setMailbagPresent(true);  
		}
		return containerVO;
	}

	/**
	 * 
	 * Method : Container.findAlreadyAssignedTrolleyNumberForMLD Added by :
	 * A-4803 on 28-Oct-2014 Used for : To find whether a container is already
	 * presnet for the mail bag Parameters : @param mldMasterVO Parameters : @return
	 * Parameters : @throws SystemException Return type : String
	 */
	public static String findAlreadyAssignedTrolleyNumberForMLD(
			MLDMasterVO mldMasterVO) throws SystemException {

		try {
			return constructDAO().findAlreadyAssignedTrolleyNumberForMLD(
					mldMasterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	/**
	 * @author A-1739
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 */
	public static int findFlightLegSerialNumber(ContainerVO containerVO)
			throws SystemException {
		try {
			return constructDAO().findFlightLegSerialNumber(containerVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * @author A-2037 This method is used to find the accepted ULDs
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerDetailsVO> findAcceptedULDs(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		try {

			return constructDAO().findAcceptedULDs(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	/**
	 * @author a-1936 This method returns all the ULDs assigned to a particular
	 *         flight from the given airport are returned
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 */

	public static Collection<ContainerVO> findFlightAssignedContainers(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		
		LOG.entering(ENTITY, "findFlightAssignedContainers");
		try {
			return constructDAO().findFlightAssignedContainers(
					operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * @author a-1936 This method returns all the ULDs that are assigned to
	 *         destination from the given airport are returned
	 * @param destinationFilterVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 */

	public static Collection<ContainerVO> findDestinationAssignedContainers(
			DestinationFilterVO destinationFilterVO) throws SystemException {
		
		try {
			return constructDAO().findDestinationAssignedContainers(
					destinationFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}		 
	/**
	 * @author a-1936 This method is used to find the conrainerDetails
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<ContainerVO> findContainers(
			SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
			throws SystemException {
		try {
			return constructDAO().findContainers(searchContainerFilterVO,
					pageNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	public static ContainerAssignmentVO findContainerAssignmentForArrival(
			String companyCode, String containerNumber, String pol)
			throws SystemException {
		try {
			return constructDAO().findContainerAssignment(companyCode,
					containerNumber, pol);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
   private static ContainerPK constructContainerPK(ContainerVO containerDetails) throws SystemException{
		
	   ContainerPK containerPK = new ContainerPK();
	   containerPK.setCompanyCode(containerDetails.getCompanyCode());
	   containerPK.setAssignmentPort(containerDetails.getAssignedPort());
	   containerPK.setCarrierId(containerDetails.getCarrierId());
	   containerPK.setContainerNumber(containerDetails.getContainerNumber());
	   containerPK.setFlightNumber(containerDetails.getFlightNumber());
	   containerPK.setFlightSequenceNumber(containerDetails.getFlightSequenceNumber());
	   containerPK.setLegSerialNumber(containerDetails.getLegSerialNumber());
	return containerPK;
	   
	}
	/**
	 * @author a-7929 
	 * @param containerVOs
	 * @param 
	 * @return
	 * @throws SystemException
	 */
	public static void saveContentID(Collection<ContainerVO> containerVOs) throws SystemException {
		Container container = null;
		if(containerVOs != null && !containerVOs.isEmpty()){
			for(ContainerVO containerVO:containerVOs){
				try {
					container = Container.find(constructContainerPK(containerVO));
					container.setContentId(containerVO.getContentId());
				} catch (FinderException e) {
					throw new SystemException(e.getErrorCode(), e);
				
				}
			}
		}
		
		
	}
    /**
     * @author A-7929
     * @param dwsMasterVO
     * @param assignedAirport 
     * @param containerNumber 
     * @return 
     * @throws SystemException 
     * @throws FinderException 
     */
	public static  String fetchMailContentIDs(DWSMasterVO dwsMasterVO, String containerNumber, String assignedAirport) throws  SystemException {
		Container container = null;
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(dwsMasterVO.getCompanyCode());
		containerVO.setCarrierId(dwsMasterVO.getCarrierId());
		containerVO.setAssignedPort(assignedAirport);  //AssignmentPort
		containerVO.setContainerNumber(containerNumber);
		containerVO.setFlightNumber(dwsMasterVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(dwsMasterVO.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(dwsMasterVO.getLegSerialNumber());
		
		try {
			container =  Container.find(constructContainerPK(containerVO));
		} catch (FinderException e) {
			LOG.log(Log.INFO, "System exception",e);
			return null;
		}
	   return container.getContentId();			
	}
	/**
     * @author A-8672
     * @param ContainerVO
     * @return 
     * @throws SystemException 
     * @throws FinderException 
     */
	public static  void updateRetainFlag(ContainerVO containerVo) throws SystemException, FinderException {
		Container container = null;		
		container =  Container.find(constructContainerPK(containerVo));
		if(container != null){
			container.setRetainFlag(containerVo.getRetainFlag());
			if(container.getTransitFlag().equals(MailConstantsVO.FLAG_NO)){
				container.setTransitFlag(MailConstantsVO.FLAG_YES);			}
		}
		
	}

	public static void markUnmarkUldIndicator(ContainerVO containerVo) throws SystemException, FinderException {
		Container container = null;		
		container =  Container.find(constructContainerPK(containerVo));
		if(container != null){
			container.setUldFulIndFlag(containerVo.getUldFulIndFlag());
		}
	}

/**
 * @author U-1532
 * findLatestContainerAssignmentForUldDelivery
 * @param scannedMailDetailsVO
 * @return
 * @throws SystemException 
 */
	public static ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {

		try {
			return constructDAO().findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}	
public long generateULDReferenceNumber(ContainerVO containerVO)
			throws SystemException {
		Criterion criterion = KeyUtils.getCriterion(containerVO.getCompanyCode(),
				MAL_FLTCON_ULDREFNUM);
		criterion.setStartAt("1");
		return Long.parseLong(KeyUtilInstance.getInstance().getKey(criterion));
	}

/**
 * 
 * @author a-10383 This method is used to retrieve the
 *         currentContainerDetails.If the ContainerAssigmentVo is null
 *         Assignments can be made
 * @param companyCode
 * @param containerNumber
 * @param pol
 * @return
 * @throws SystemException
 */
public static ContainerAssignmentVO findContainerWeightCapture(
		String companyCode, String containerNumber)
		throws SystemException {
	try {
		return constructDAO().findContainerWeightCapture(companyCode,
				containerNumber);
	} catch (PersistenceException ex) {
		throw new SystemException(ex.getMessage(), ex);
	}
}
}