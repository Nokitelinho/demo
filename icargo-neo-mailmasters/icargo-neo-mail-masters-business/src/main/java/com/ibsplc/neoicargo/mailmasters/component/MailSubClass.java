package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailSubClassVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Entity
@IdClass(MailSubClassPK.class)
@Table(name = "MALSUBCLSMST")
public class MailSubClass  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";

	/**
	 * The subClassCode
	 */
	@Id
	@Column(name = "SUBCLSCOD")
	private String subClassCode;
	/** 
	* The description for the mail subclass code
	*/
	@Column(name = "DES")
	private String description;
	/** 
	* subClassgroup
	*/
	@Column(name = "SUBCLSGRP")
	private String subClassgroup;

	public MailSubClass() {
	}

	public MailSubClass(MailSubClassVO mailSubClassVO) {
		try {
			populatepk(mailSubClassVO);
			populateAttribute(mailSubClassVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populatepk(MailSubClassVO mailSubClassVO) {
		this.setCompanyCode(mailSubClassVO.getCompanyCode());
		this.setSubClassCode(mailSubClassVO.getCode());
	}

	private void populateAttribute(MailSubClassVO mailSubClassVO) {
		this.setDescription(mailSubClassVO.getDescription());
		this.setSubClassgroup(mailSubClassVO.getSubClassGroup());
		this.setLastUpdatedUser(mailSubClassVO.getLastUpdateUser());
	}

	/** 
	* @param companyCode
	* @param subClassCode
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailSubClass find(String companyCode, String subClassCode) throws FinderException {
		MailSubClassPK mailSubClassPk = new MailSubClassPK();
		mailSubClassPk.setCompanyCode(companyCode);
		mailSubClassPk.setSubClassCode(subClassCode);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailSubClass.class, mailSubClassPk);
	}

	/** 
	* @param mailSubClassVO
	* @throws SystemException
	*/
	public void update(MailSubClassVO mailSubClassVO) {
		populateAttribute(mailSubClassVO);
		this.setLastUpdatedTime(Timestamp.valueOf(mailSubClassVO.getLastUpdateTime().toLocalDateTime()));
	}

	/** 
	* Method to delete a row of MailSubClass
	* @throws RemoveException
	* @throws SystemException
	*/
	public void remove() throws RemoveException {
		PersistenceController.getEntityManager().remove(this);
	}

	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author a-2037 This method is used to find all the mail subclass codes
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	*/
	public static Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode) {
		try {
			return constructDAO().findMailSubClassCodes(companyCode, subclassCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-2037 Method for MailSubClassLOV containing code and description
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode, String code, String description,
			int pageNumber, int defaultSize) {
		try {
			return constructDAO().findMailSubClassCodeLov(companyCode, code, description, pageNumber, defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
