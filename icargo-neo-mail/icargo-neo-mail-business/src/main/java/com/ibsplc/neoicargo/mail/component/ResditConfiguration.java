package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.ResditTransactionDetailVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import com.ibsplc.icargo.business.mail.operations.ResditConfigurationPK;

/** 
 * @author A-1739
 */
@Setter
@Getter
@Entity
@IdClass(ResditConfigurationPK.class)
@Table(name = "MALRDTCFG")
public class ResditConfiguration extends BaseEntity implements Serializable {
	private static final String MAILTRACKING_DEFAULTS = "mailtracking.defaults";
	private static final String MODULENAME = "mail.operations";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CARIDR")
	private int carrierId;
	@Id
	@Column(name = "TXNCOD")
	private String transactionId;

	@Column(name = "RCVRDTFLG")
	private String receivedResditFlag;
	@Column(name = "ASGRDTFLG")
	private String assignedResditFlag;
	@Column(name = "UPLRDTFLG")
	private String upliftedResditFlag;
	@Column(name = "HNDOVRFLG")
	private String handedOverResditFlag;
	@Column(name = "DEPRDTFLG")
	private String loadedResditFlag;
	@Column(name = "HNDOVRRCVFLG")
	private String handedOverReceivedFlag;
	@Column(name = "DLVRDTFLG")
	private String deliveredResditFlag;
	@Column(name = "RETRDTFLG")
	private String returnedResditFlag;
	@Column(name = "RDYDLVRDTFLG")
	private String readyForDeliveryResditFlag;
	@Column(name = "TRTCPLRDTFLG")
	private String transportationCompletedResditFlag;
	@Column(name = "ARRRDTFLG")
	private String arrivedResditFlag;

	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	* TODO Purpose Feb 5, 2007, A-1739
	* @param companyCode
	* @param carrierId
	* @param txnId
	* @return
	* @throws SystemException 
	*/
	public static ResditTransactionDetailVO findResditConfigurationForTxn(String companyCode, int carrierId,
			String txnId) {
		try {
			return constructDAO().findResditConfurationForTxn(companyCode, carrierId, txnId);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

}
