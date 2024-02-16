package com.ibsplc.icargo.business.reco.defaults;

import java.util.Calendar;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesDAO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Table(name="RECMSG")
@Entity
@Staleable
public class RegulatoryComposeMessage {

	private RegulatoryComposeMessagePK composeMessagePK;
	
	private String rolGroup;
	
	private String message;
	
	private Calendar startDate;
	
	private Calendar endDate;

	private Calendar lastUpdateTime;
	
	private String lastUpdateUser;
	
	private Calendar updatedTransactionTime; 

	private static final Log log = LogFactory.getLogger("REGULATORY_COMPOSE_MESSAGE");
	
	public RegulatoryComposeMessage() {

	}
	
	@Column(name = "ROLGRPCOD")
	public String getRolGroup() {
		return rolGroup;
	}

	public void setRolGroup(String rolGroup) {
		this.rolGroup = rolGroup;
	}

	@Column(name = "MSGDES")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "STRDAT")
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@Column(name = "ENDDAT")
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Column(name = "LSTUPDTIM")
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
	
	public void setComposeMessagePK(RegulatoryComposeMessagePK composeMessagePK) {
		this.composeMessagePK = composeMessagePK;
	}
	
	public void setUpdatedTransactionTime(Calendar updatedTransactionTime) {
		this.updatedTransactionTime = updatedTransactionTime;
	}
	@Column(name = "UPDTXNTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedTransactionTime() {
		return updatedTransactionTime;
	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))
			})
	public RegulatoryComposeMessagePK getComposeMessagePK() {
		return composeMessagePK;
	}
	
	
	public RegulatoryComposeMessage(RegulatoryMessageVO regulatoryMessageVO) throws SystemException,
	CreateException {
		log.entering("Regulatory Compose Message", "Regulatory Compose Message");
		try {
			populatePk(regulatoryMessageVO);
			populateAttributes(regulatoryMessageVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			log.log(Log.SEVERE, "CreateException caught, SystemException thrown");
			throw new SystemException(createException.getErrorCode(),createException);
		}
		log.exiting("Regulatory Compose Message", "Regulatory Compose Message");
	}

	private void populateAttributes(RegulatoryMessageVO regulatoryMessageVO) {
		setRolGroup(regulatoryMessageVO.getRolGroup());
		setMessage(regulatoryMessageVO.getMessage());
		setStartDate(regulatoryMessageVO.getStartDate());
		setEndDate(regulatoryMessageVO.getEndDate());
		setLastUpdateUser(regulatoryMessageVO.getLastUpdateUser());
		setLastUpdateTime(regulatoryMessageVO.getLastUpdateTime());
		setUpdatedTransactionTime(regulatoryMessageVO.getUpdatedTransactionTime().toCalendar());
	}

	private void populatePk(RegulatoryMessageVO regulatoryMessageVO) {
		RegulatoryComposeMessagePK composeMessagePK= new RegulatoryComposeMessagePK(
				regulatoryMessageVO.getCompanyCode(),regulatoryMessageVO.getSerialNumber());
		setComposeMessagePK(composeMessagePK);
	}

	
	/**
	 * Method to find Embargo Compose Message
	 *
	 * @param companyCode
	 * @param serialNumber
	 * @return Embargo
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static RegulatoryComposeMessage find(String companyCode,int serialNumber)
			throws SystemException, FinderException {
		log.entering("Regulatory Compose Message Find", "Regulatory Compose Message Find");
		try {
			RegulatoryComposeMessagePK composeMessagePK = new RegulatoryComposeMessagePK();
			composeMessagePK.setCompanyCode(companyCode);
			composeMessagePK.setSerialNumber(serialNumber);
			return PersistenceController.getEntityManager().find(RegulatoryComposeMessage.class,
					composeMessagePK);
		} catch (FinderException finderException) {
			log.log(Log.SEVERE, "FinderException caught, FinderException thrown");
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
	}
	
	/**
	 * This method Removes the particular entry from the Database 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		log.entering("Regulatory Compose Message Delete", "Regulatory Compose Message Delete");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			log.log(Log.SEVERE, "RemoveException caught, RemoveException thrown");
			throw new SystemException(removeException.getErrorCode(),removeException);
		}
		log.exiting("Regulatory Compose Message Delete", "Regulatory Compose Message Delete");
	}
	
	/**
	 *This method updates Regulatory Compose Message 
	 * @param RegulatoryMessageVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	
	public void update(RegulatoryMessageVO regulatoryMessageVO){
		log.entering("Regulatory Compose Message update", "Regulatory Compose Message update");
		populateAttributes(regulatoryMessageVO);
		log.exiting("Regulatory Compose Message update", "Regulatory Compose Message update");
	}
	
	/**
	 * This method finds Regulatory Compose Message which meet the filter
	 *
	 * @param regulatoryMessageFilter
	 * @return 
	 * @throws SystemException
	 */
	
	public static Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException {
		log.entering("Regulatory Compose Message Listing", "Regulatory Compose Message Listing");
		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO("reco.defaults"));
			return embargoDAO.findRegulatoryMessages(regulatoryMessageFilter);	
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
	}
	
	/**
	 * This method finds All Regulatory Compose Message which meet the filter
	 *
	 * @param regulatoryMessageFilter
	 * @param pageNumber
	 * @return 
	 * @throws SystemException
	 */
	
	public static  List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException {
		log.entering("Regulatory Compose Message Listing", "Regulatory Compose Message Listing");
		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO("reco.defaults"));
			return embargoDAO.findAllRegulatoryMessages(regulatoryMessageFilter);	
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
	}

}
