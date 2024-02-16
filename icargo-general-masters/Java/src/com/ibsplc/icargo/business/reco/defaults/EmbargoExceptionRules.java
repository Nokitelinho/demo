/*
 * 
 */
package com.ibsplc.icargo.business.reco.defaults;

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

import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesDAO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.GenerationFailedException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6843
 *
 */
@Table(name="RECEXPMST")
@Entity
@Staleable
public class EmbargoExceptionRules {
	
	private Calendar startDate;
	private Calendar endDate;
	private String remarks;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private EmbargoExceptionRulesPK embargoExceptionRulesPk;
	
	private static final Log log = LogFactory.getLogger("EMBARGO EXCEPTION RULES");
	
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name = "STRDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	@Column(name = "ENDDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
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
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "serialNumbers", column = @Column(name = "SERNUM"))
			})
	public EmbargoExceptionRulesPK getEmbargoExceptionRulesPk() {
		return embargoExceptionRulesPk;
	}
	public void setEmbargoExceptionRulesPk(
			EmbargoExceptionRulesPK embargoExceptionRulesPk) {
		this.embargoExceptionRulesPk = embargoExceptionRulesPk;
	}
	
	
	
	/**
	 * Default Constructor-This Constructor is Mandatory for while using
	 * Hibernate find
	 */
	public EmbargoExceptionRules(){
		
	}
	/**
	 * @param exceptionEmbargoVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public EmbargoExceptionRules(ExceptionEmbargoDetailsVO exceptionEmbargoVO) throws SystemException,
	CreateException {
		log.entering("Embargo Exception Rules", "Embargo Exception Rules");
		try {
		populatePk(exceptionEmbargoVO);
		populateAttributes(exceptionEmbargoVO);
			this.embargoExceptionRulesPk.getSerialNumbers();
		PersistenceController.getEntityManager().persist(this);
		}
		 catch (CreateException createException) {
			log.log(Log.SEVERE, "CreateException caught, SystemException thrown");
			throw new SystemException(createException.getErrorCode(),createException);
		}
		log.exiting("Embargo Exception Rules", "Embargo Exception Rules");

	}
	/**
	 * @param exceptionEmbargoVO
	 * @throws SystemException 
	 * @throws GenerationFailedException 
	 */
	private void populatePk(ExceptionEmbargoDetailsVO exceptionEmbargoVO) throws GenerationFailedException, SystemException{
		
		
		EmbargoExceptionRulesPK embargoExceptionPK= new EmbargoExceptionRulesPK();
		embargoExceptionPK.setCompanyCode(exceptionEmbargoVO.getCompanyCode());

		setEmbargoExceptionRulesPk(embargoExceptionPK);
		embargoExceptionPK.setSerialNumbers(this.embargoExceptionRulesPk.getSerialNumbers());
		
		
	}
	/**
	 * @param exceptionEmbargoVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	private void populateAttributes(ExceptionEmbargoDetailsVO exceptionEmbargoVO)throws CreateException, SystemException {
		
		setStartDate(exceptionEmbargoVO.getStartDate());
		setEndDate(exceptionEmbargoVO.getEndDate());
		setRemarks(exceptionEmbargoVO.getRemarks());
		setShipmentPrefix(exceptionEmbargoVO.getShipmentPrefix());
		setMasterDocumentNumber(exceptionEmbargoVO.getMasterDocumentNumber());

	}
	
	
	/**
	 * @param companyCode
	 * @param shipmentPrefix
	 * @param masterDocumentNumber
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static EmbargoExceptionRules find(String companyCode,String shipmentPrefix,String masterDocumentNumber,int serialNumbers)
	throws SystemException, FinderException {
log.entering("Embargo Exception Rules Find", "Embargo Exception Rules Find");
try {
	EmbargoExceptionRulesPK embargoExceptionPK = new EmbargoExceptionRulesPK();
	embargoExceptionPK.setCompanyCode(companyCode);

			embargoExceptionPK.setSerialNumbers(serialNumbers);
	
	
	return PersistenceController.getEntityManager().find(EmbargoExceptionRules.class,
			embargoExceptionPK);
} catch (FinderException finderException) {
	log.log(Log.SEVERE, "FinderException caught, FinderException thrown");
	throw new SystemException(finderException.getErrorCode(),finderException);
}
}
	
	/**
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		log.entering("Embargo Exception Rules Delete", "Embargo Exception Rules Delete");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			log.log(Log.SEVERE, "RemoveException caught, RemoveException thrown");
			throw new SystemException(removeException.getErrorCode(),removeException);
		}
		log.exiting("Embargo Exception Rules Delete", "Embargo Exception Rules Delete");
	}
	
	/**
	 * @param exceptionEmbargoVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public void update(ExceptionEmbargoDetailsVO exceptionEmbargoVO)throws CreateException, SystemException {
		log.entering("Embargo Exception Rules update", "Embargo Exception Rules update");
		populateAttributes(exceptionEmbargoVO);
		log.exiting("Embargo Exception Rules update", "Embargo Exception Rules update");
	}
	
	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO filterVO)
	throws SystemException{

		EmbargoRulesDAO exceptionDAO;
		try {
			exceptionDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));
			return exceptionDAO.findExceptionEmbargoDetails(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
		
	}
	/**
	 * This method returns exception awbs
	 *
	 * @param exceptionEmbargoFilterVOs
	 * @return Collection<String>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public static Collection<String> findExceptionEmbargos(
			Collection<ExceptionEmbargoFilterVO> exceptionEmbargoFilterVOs)
	throws SystemException{
		EmbargoRulesDAO embargoDAO;
		try {
			embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));
			return embargoDAO.findExceptionEmbargos(exceptionEmbargoFilterVOs);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
}
