package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

@Entity
@Table(name = "MALLODPLNFLTCON")

public class FlightLoadPlanContainer {
private static final String MODULE = "mail.operations";
	
	private FlightLoadPlanContainerPK flightLoadPlanContainerPK;	
	private long uldReferenceNo;
	private String containerType; 
	private int segSerialNumber;
	private int plannedPieces;
	private double plannedWeight;
	private String planStatus;
	private String uldNumber;
	private String plannedPosition;
	private double plannedVolume;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String uldFullIndicator;
	private String subClassGroup;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "containerNumber", column = @Column(name = "CONNUM")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "segOrigin", column = @Column(name = "SEGORG")),
			@AttributeOverride(name = "segDestination", column = @Column(name = "SEGDST")),
			@AttributeOverride(name = "loadPlanVersion", column = @Column(name = "LODPLNVER")) })
	
	public FlightLoadPlanContainerPK getFlightLoadPlanContainerPK() {
		return flightLoadPlanContainerPK;
	}
	public void setFlightLoadPlanContainerPK(FlightLoadPlanContainerPK flightLoadPlanContainerPK) {
		this.flightLoadPlanContainerPK = flightLoadPlanContainerPK;
	}
	
	public FlightLoadPlanContainer() {
		
	}
	
	/**
	 * 
	 * @author a-3429
	 * @param flightLoadPlanContainerVO
	 * @throws SystemException
	 */
	public FlightLoadPlanContainer(FlightLoadPlanContainerVO flightLoadPlanContainerVO) throws SystemException {
		populatePK(flightLoadPlanContainerVO);
		populateAttributes(flightLoadPlanContainerVO);
	}
	
	/**
	 * This method is used to populate the pk for the FlightLoanPlanContainer
	 * 
	 * @param flightLoadPlanContainerVO
	 * @throws SystemException
	 */
	private void populatePK(FlightLoadPlanContainerVO flightLoadPlanContainerVO) throws SystemException {
		FlightLoadPlanContainerPK loanPlanContainerPK = new FlightLoadPlanContainerPK();
		loanPlanContainerPK.setCompanyCode(flightLoadPlanContainerVO.getCompanyCode());
		loanPlanContainerPK.setFlightNumber(flightLoadPlanContainerVO.getFlightNumber());
		loanPlanContainerPK.setFlightSequenceNumber(flightLoadPlanContainerVO.getFlightSequenceNumber());
		loanPlanContainerPK.setSegOrigin(flightLoadPlanContainerVO.getSegOrigin());
		loanPlanContainerPK.setSegDestination(flightLoadPlanContainerVO.getSegDestination());
		loanPlanContainerPK.setLoadPlanVersion(flightLoadPlanContainerVO.getLoadPlanVersion());
		loanPlanContainerPK.setCarrierId(flightLoadPlanContainerVO.getCarrierId());
		loanPlanContainerPK.setContainerNumber(flightLoadPlanContainerVO.getContainerNumber());
		this.setFlightLoadPlanContainerPK(loanPlanContainerPK);

	}
	
	/**
	 * @author a-3429 This method is used
	 * @param flightLoadPlanContainerVO
	 */
	private void populateAttributes(FlightLoadPlanContainerVO flightLoadPlanContainerVO) {
		this.setContainerType(flightLoadPlanContainerVO.getContainerType());		
		this.setUldReferenceNo(flightLoadPlanContainerVO.getUldReferenceNo());
		this.setSegSerialNumber(flightLoadPlanContainerVO.getSegSerialNumber());
		this.setPlannedPieces(flightLoadPlanContainerVO.getPlannedPieces());
		this.setPlannedWeight(flightLoadPlanContainerVO.getPlannedWeight());		
		this.setPlanStatus(flightLoadPlanContainerVO.getPlanStatus());
		this.setUldNumber(flightLoadPlanContainerVO.getUldNumber());
		this.setPlannedPosition(flightLoadPlanContainerVO.getPlannedPosition());
		this.setPlannedVolume(flightLoadPlanContainerVO.getPlannedVolume());
		this.setLastUpdateUser(flightLoadPlanContainerVO.getLastUpdatedUser());
		this.setSubClassGroup(flightLoadPlanContainerVO.getSubClassGroup());
		this.setUldFullIndicator(flightLoadPlanContainerVO.getUldFullIndicator());

		
	}
	
	/**
	 * @author a-3429
	 * @param containerPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static FlightLoadPlanContainer find(FlightLoadPlanContainerPK flightLoadPlanContainerPK)
			throws SystemException, FinderException {		
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(FlightLoadPlanContainer.class, flightLoadPlanContainerPK);
	}
	
	/**
	 * @author a-3429 This method is used to update the BusinessObject
	 * @param containerVO
	 * @throws SystemException
	 */
	public void update(FlightLoadPlanContainerVO flightLoadPlanContainerVO) throws SystemException {		
		populateAttributes(flightLoadPlanContainerVO);	
	}

	
	@Column(name = "ULDREFNUM")
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}
	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
	@Column(name = "CONTYP")
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	@Column(name = "SEGSERNUM")
	public int getSegSerialNumber() {
		return segSerialNumber;
	}
	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
	}
	@Column(name = "PLNPCS")
	public int getPlannedPieces() {
		return plannedPieces;
	}
	public void setPlannedPieces(int plannedPieces) {
		this.plannedPieces = plannedPieces;
	}
	@Column(name = "PLNWGT")	
	public double getPlannedWeight() {
		return plannedWeight;
	}
	public void setPlannedWeight(double plannedWeight) {
		this.plannedWeight = plannedWeight;
	}
	@Column(name = "LODPLNSTA")	
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	@Column(name = "ULDNUM")	
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	@Column(name = "PLNPOS")
	public String getPlannedPosition() {
		return plannedPosition;
	}
	public void setPlannedPosition(String plannedPosition) {
		this.plannedPosition = plannedPosition;
	}
	@Column(name = "PLNVOL")
	public double getPlannedVolume() {
		return plannedVolume;
	}
	public void setPlannedVolume(double plannedVolume) {
		this.plannedVolume = plannedVolume;
	}
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	@Column(name = "ULDFULIND")
	public String getUldFullIndicator() {
		return uldFullIndicator;
	}
	public void setUldFullIndicator(String uldFullIndicator) {
		this.uldFullIndicator = uldFullIndicator;
	}
	@Column(name = "SUBCLSGRP")
	public String getSubClassGroup() {
		return subClassGroup;
	}
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	/**
	 * @author a-3429 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
	}
	public static Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO)
			throws SystemException {
		return constructDAO().findPreviousLoadPlanVersionsForContainer(loadPlanVO);
	}
}
