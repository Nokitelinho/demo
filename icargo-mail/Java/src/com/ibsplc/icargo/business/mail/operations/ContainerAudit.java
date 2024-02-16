/*
 * ContainerAudit.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations;


import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAuditFilterVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.AbstractAudit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 * The entity for the AuditInformation..
 * 
 * @author a-3109
 * 
 */
@Table(name = "MALFLTCONAUD")
@Entity
public class ContainerAudit extends AbstractAudit{

	private static final String MODULE = "mail.operations";

	private ContainerAuditPK containerAuditPK;

	/**
	 * @return Returns the containerAudit.
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
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")) })
	
	public ContainerAuditPK getContainerAudit() {
		return containerAuditPK;
	}
	/**
	 * @param containerAuditPK
	 *            The containerAudist to set.
	 */
	public void setContainerAudit(ContainerAuditPK containerAuditPK) {
		this.containerAuditPK = containerAuditPK;
	}

	/**
	 * The default Constructor
	 * 
	 */
	public ContainerAudit() {

	}

	/**
	 * @author a-3109 This method is used to persist the entity instance.
	 * @param containerAuditVO
	 * @throws SystemException
	 * 
	 */
	public ContainerAudit(ContainerAuditVO containerAuditVO)
			throws SystemException {

		ContainerAuditPK auditPK = new ContainerAuditPK();
		auditPK.setCompanyCode(containerAuditVO.getCompanyCode());
		auditPK.setContainerNumber(containerAuditVO.getContainerNumber());
		auditPK.setAssignmentPort(containerAuditVO.getAssignedPort());
		auditPK.setCarrierId(containerAuditVO.getCarrierId());
		auditPK.setFlightNumber(containerAuditVO.getFlightNumber());
		auditPK.setFlightSequenceNumber(containerAuditVO
				.getFlightSequenceNumber());
		auditPK.setLegSerialNumber(containerAuditVO.getLegSerialNumber());
		this.setContainerAudit(auditPK);
		populateGenericAttributes(containerAuditVO);
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
	
	/**
	 * @author a-7794 This method is used to list the PartnerCarriers.
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * ICRD-229934
	 */
	public static Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO)
			throws SystemException {
		try {
			return constructDAO().findCONAuditDetails(mailAuditFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
