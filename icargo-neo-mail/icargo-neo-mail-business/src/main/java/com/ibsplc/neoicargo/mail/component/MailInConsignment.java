package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailInConsignmentPK.class)
@Table(name = "MALCSGDTL")
public class MailInConsignment extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	@Id
	@Column(name = "POACOD")
	private String paCode;
	@Id
	@Column(name = "CSGSEQNUM")
	private int consignmentSequenceNumber;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	@Column(name = "BAGCNT")
	private int statedBags;
	@Column(name = "DCLVAL")
	private double declaredValue;
	@Column(name = "CONSELNUM")
	private String sealNumber;
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Column(name = "CURCOD")
	private String currencyCode;
	@Column(name = "MALJNRIDR")
	private String mailbagJnrIdr;

	public MailInConsignment(){

	}
	/** 
	* @param mailInConsignmentVO
	* @throws SystemException
	*/
	public MailInConsignment(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("MailInConsignment" + " : " + "MailInConsignment" + " Entering");
		populatePk(mailInConsignmentVO);
		populateAttributes(mailInConsignmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("MailInConsignment" + " : " + "MailInConsignment" + " Exiting");
	}

	/** 
	* @param mailInConsignmentVO
	*/
	private void populatePk(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("MailInConsignment" + " : " + "populatePk" + " Entering");
		this.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		this.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
		this.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
		this.setPaCode(mailInConsignmentVO.getPaCode());
		this.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		log.debug("MailInConsignment" + " : " + "populatePk" + " Exiting");
	}

	/** 
	* @param mailInConsignmentVO
	*/
	private void populateAttributes(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("MailInConsignment" + " : " + "populateAttributes" + " Entering");
		this.setStatedBags(mailInConsignmentVO.getStatedBags());
		this.setUldNumber(mailInConsignmentVO.getUldNumber());
		this.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
		this.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
		this.setMailbagJnrIdr(mailInConsignmentVO.getMailbagJrnIdr());
		this.setSealNumber(mailInConsignmentVO.getSealNumber());
		log.debug("MailInConsignment" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @param mailInConsignmentVO
	*/
	public void update(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("MailInConsignment" + " : " + "populateAttributes" + " Entering");
		populateAttributes(mailInConsignmentVO);
		log.debug("MailInConsignment" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* This method removes entity
	* @throws SystemException
	*/
	public void remove() {
		log.debug("MailInConsignment" + " : " + "remove" + " Entering");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
		log.debug("MailInConsignment" + " : " + "remove" + " Exiting");
	}

	/** 
	* @param mailInConsignmentVO
	* @return MailInConsignment
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailInConsignment find(MailInConsignmentVO mailInConsignmentVO) throws FinderException {
		log.debug("MailInConsignment" + " : " + "find" + " Entering");
		MailInConsignment mailInConsignment = null;
		MailInConsignmentPK mailInConsignmentPk = new MailInConsignmentPK();
		mailInConsignmentPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		mailInConsignmentPk.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
		mailInConsignmentPk.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
		mailInConsignmentPk.setPaCode(mailInConsignmentVO.getPaCode());
		mailInConsignmentPk.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		return PersistenceController.getEntityManager().find(MailInConsignment.class, mailInConsignmentPk);
	}

	/** 
	* @return MailTrackingDefaultsDAO
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		MailOperationsDAO mailOperationsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailOperationsDAO = MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailOperationsDAO;
	}

	/** 
	* This method finds mail sequence number
	* @param mailInConsignmentVO
	* @return int
	* @throws SystemException
	*/
	public static int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("MailInConsignment" + " : " + "findMailSequenceNumber" + " Entering");
		try {
			return constructDAO().findMailSequenceNumber(mailInConsignmentVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-2037
	* @param companyCode
	* @param mailId
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	public static MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode, String mailId,
			String airportCode) {
		log.debug("MailInConsignment" + " : " + "findConsignmentDetailsForMailbag" + " Entering");
		try {
			return constructDAO().findConsignmentDetailsForMailbag(companyCode, mailId, airportCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
