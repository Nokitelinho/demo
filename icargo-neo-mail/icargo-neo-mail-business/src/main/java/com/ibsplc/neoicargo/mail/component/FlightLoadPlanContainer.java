package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.FlightLoadPlanContainerVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@Slf4j
@Entity
@Table(name = "MALLODPLNFLTCON")
@IdClass(FlightLoadPlanContainerPK.class)
public class FlightLoadPlanContainer extends BaseEntity implements Serializable {
private static final String MODULE = "mail.operations";

	@Column(name = "ULDREFNUM")
	private long uldReferenceNo;
	@Column(name = "CONTYP")
	private String containerType;
	@Column(name = "SEGSERNUM")
	private int segSerialNumber;
	@Column(name = "PLNPCS")
	private int plannedPieces;
	@Column(name = "PLNWGT")
	private double plannedWeight;
	@Column(name = "LODPLNSTA")
	private String planStatus;
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Column(name = "PLNPOS")
	private String plannedPosition;
	@Column(name = "PLNVOL")
	private double plannedVolume;
	@Column(name = "ULDFULIND")
	private String uldFullIndicator;
	@Column(name = "SUBCLSGRP")
	private String subClassGroup;
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CONNUM")
	private String containerNumber;
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Id
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Id
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Id
	@Column(name = "SEGORG")
	private String segOrigin;
	@Id
	@Column(name = "SEGDST")
	private String segDestination;
	@Id
	@Column(name = "LODPLNVER")
	private int loadPlanVersion;



	public FlightLoadPlanContainer() {

	}
	/**
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
		this.setCompanyCode(flightLoadPlanContainerVO.getCompanyCode());
		this.setFlightNumber(flightLoadPlanContainerVO.getFlightNumber());
		this.setFlightSequenceNumber(flightLoadPlanContainerVO.getFlightSequenceNumber());
		this.setSegOrigin(flightLoadPlanContainerVO.getSegOrigin());
		this.setSegDestination(flightLoadPlanContainerVO.getSegDestination());
		this.setLoadPlanVersion(flightLoadPlanContainerVO.getLoadPlanVersion());
		this.setCarrierId(flightLoadPlanContainerVO.getCarrierId());
		this.setContainerNumber(flightLoadPlanContainerVO.getContainerNumber());

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
		//this.setLastUpdateUser(flightLoadPlanContainerVO.getLastUpdatedUser());
		this.setSubClassGroup(flightLoadPlanContainerVO.getSubClassGroup());
		this.setUldFullIndicator(flightLoadPlanContainerVO.getUldFullIndicator());


	}

	/**
	 * @author a-3429
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
	 * @throws SystemException
	 */
	public void update(FlightLoadPlanContainerVO flightLoadPlanContainerVO) throws SystemException {
		populateAttributes(flightLoadPlanContainerVO);
	}

	/**
	 * @author a-3429 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailOperationsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
	}
	//TODO:Neo to implement method - refer classic
	public static Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO)
			throws SystemException {
		//return constructDAO().findPreviousLoadPlanVersionsForContainer(loadPlanVO);
		return null;
	}
}
