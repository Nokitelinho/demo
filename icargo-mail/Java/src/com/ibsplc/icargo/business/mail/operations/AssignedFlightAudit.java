/*
 * AssignedFlightAudit.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightAuditVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.AbstractAudit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3109
 * 
 */
@Table(name = "MALFLTAUD")
@Entity
public class AssignedFlightAudit extends AbstractAudit{

	private Log log = LogFactory.getLogger("Mail_operations");

	private static final String MODULE = "mail.operations";

	private AssignedFlightAuditPK assignedFlightAuditPK;

	/**
	 * @return Returns the assignedFlightAuditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")) })
	public AssignedFlightAuditPK getAssignedFlightAuditPK() {
		return assignedFlightAuditPK;
	}

	/**
	 * @param assignedFlightAuditPK
	 *            The assignedFlightAuditPK to set.
	 */
	public void setAssignedFlightAuditPK(
			AssignedFlightAuditPK assignedFlightAuditPK) {
		this.assignedFlightAuditPK = assignedFlightAuditPK;
	}
	
	public AssignedFlightAudit() {

	}

	/**
	 * The constructor for Audit Entity
	 * 
	 * @param assignedFlightAuditVO
	 * @throws SystemException
	 */
	public AssignedFlightAudit(AssignedFlightAuditVO assignedFlightAuditVO)
			throws SystemException {
		log.log(Log.FINE, "The assignedFlightAuditVO is ",
				assignedFlightAuditVO);
		AssignedFlightAuditPK auditPK = new AssignedFlightAuditPK();
		auditPK.setCompanyCode(assignedFlightAuditVO.getCompanyCode());
		auditPK.setCarrierId(assignedFlightAuditVO.getCarrierId());
		auditPK.setFlightNumber(assignedFlightAuditVO.getFlightNumber());
		auditPK.setFlightSequenceNumber(assignedFlightAuditVO
				.getFlightSequenceNumber());
		auditPK.setLegSerialNumber(assignedFlightAuditVO.getLegSerialNumber());
		auditPK.setAirportCode(assignedFlightAuditVO.getAirportCode());
		this.setAssignedFlightAuditPK(auditPK);
		populateGenericAttributes(assignedFlightAuditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}

	}

	/**
	 * @author a-3109 methods the DAO instance ..
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

}
