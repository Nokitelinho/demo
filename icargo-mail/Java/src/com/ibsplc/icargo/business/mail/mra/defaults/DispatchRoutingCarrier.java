package com.ibsplc.icargo.business.mail.mra.defaults;
/**
 * DispatchRoutingCarrier
 * 
 * @author A-4452
 * 
 */
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Entity
@Table(name = "MALMRARTGCFG")
@Staleable
public class DispatchRoutingCarrier {

	private static final String CLASS_NAME = "DispatchRoutingCarrier";
	private static final String MODULE_NAME = "mail.mra.defaults";
	
	
	private Calendar validFrom;
	private Calendar validToo;
	private String poaCode;
	private String mailOriginLevel;
	private String mailOriginVal;
	private String mailDestLevel;
	private String mailDestVal;
	private String secOneOriginLvl;
	private String secOneOriginValue;
	private String secOneDestLvl;
	private String secOneDestValue;
	private int secOneCarrierIdr;
	private String secOneCarrierCode;
	private String secTwoOriginLevel;
	private String secTwoOriginValue;
	private String secTwoDestLevel;
	private String secTwoDestValue;
	private int secTwoCarrierIdr;
	private String secTwoCarrierCode;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	
	private DispatchRoutingCarrierPK dispatchRoutingCarrierPK;
	
	
	
	
	
	/**
	 * @return the validFrom
	 */
	@Column(name = "VALFRM")
	public Calendar getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return the validToo
	 */
	@Column(name = "VALTOO")
	public Calendar getValidToo() {
		return validToo;
	}

	/**
	 * @param validToo the validToo to set
	 */
	public void setValidToo(Calendar validToo) {
		this.validToo = validToo;
	}

	/**
	 * @return the poaCode
	 */
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the mailOriginLevel
	 */
	@Column(name = "MALORGLVL")
	public String getMailOriginLevel() {
		return mailOriginLevel;
	}

	/**
	 * @param mailOriginLevel the mailOriginLevel to set
	 */
	public void setMailOriginLevel(String mailOriginLevel) {
		this.mailOriginLevel = mailOriginLevel;
	}

	/**
	 * @return the mailOriginVal
	 */
	@Column(name = "MALORGVAL")
	public String getMailOriginVal() {
		return mailOriginVal;
	}

	/**
	 * @param mailOriginVal the mailOriginVal to set
	 */
	public void setMailOriginVal(String mailOriginVal) {
		this.mailOriginVal = mailOriginVal;
	}

	/**
	 * @return the mailDestLevel
	 */
	@Column(name = "MALDSTLVL")
	public String getMailDestLevel() {
		return mailDestLevel;
	}

	/**
	 * @param mailDestLevel the mailDestLevel to set
	 */
	public void setMailDestLevel(String mailDestLevel) {
		this.mailDestLevel = mailDestLevel;
	}

	/**
	 * @return the mailDestVal
	 */
	@Column(name = "MALDSTVAL")
	public String getMailDestVal() {
		return mailDestVal;
	}

	/**
	 * @param mailDestVal the mailDestVal to set
	 */
	public void setMailDestVal(String mailDestVal) {
		this.mailDestVal = mailDestVal;
	}

	/**
	 * @return the secOneOriginLvl
	 */
	@Column(name = "SECONEORGLVL")
	public String getSecOneOriginLvl() {
		return secOneOriginLvl;
	}

	/**
	 * @param secOneOriginLvl the secOneOriginLvl to set
	 */
	public void setSecOneOriginLvl(String secOneOriginLvl) {
		this.secOneOriginLvl = secOneOriginLvl;
	}

	/**
	 * @return the secOneOriginValue
	 */
	@Column(name = "SECONEORGVAL")
	public String getSecOneOriginValue() {
		return secOneOriginValue;
	}

	/**
	 * @param secOneOriginValue the secOneOriginValue to set
	 */
	public void setSecOneOriginValue(String secOneOriginValue) {
		this.secOneOriginValue = secOneOriginValue;
	}

	/**
	 * @return the secOneDestLvl
	 */
	@Column(name = "SECONEDSTLVL")
	public String getSecOneDestLvl() {
		return secOneDestLvl;
	}

	/**
	 * @param secOneDestLvl the secOneDestLvl to set
	 */
	public void setSecOneDestLvl(String secOneDestLvl) {
		this.secOneDestLvl = secOneDestLvl;
	}

	/**
	 * @return the secOneDestValue
	 */
	@Column(name = "SECONEDSTVAL")
	public String getSecOneDestValue() {
		return secOneDestValue;
	}

	/**
	 * @param secOneDestValue the secOneDestValue to set
	 */
	public void setSecOneDestValue(String secOneDestValue) {
		this.secOneDestValue = secOneDestValue;
	}

	/**
	 * @return the secOneCarrierIdr
	 */
	@Column(name = "SECTONECARIDR")
	public int getSecOneCarrierIdr() {
		return secOneCarrierIdr;
	}

	/**
	 * @param secOneCarrierIdr the secOneCarrierIdr to set
	 */
	public void setSecOneCarrierIdr(int secOneCarrierIdr) {
		this.secOneCarrierIdr = secOneCarrierIdr;
	}

	/**
	 * @return the secOneCarrierCode
	 */
	@Column(name = "SECONECARCOD")
	public String getSecOneCarrierCode() {
		return secOneCarrierCode;
	}

	/**
	 * @param secOneCarrierCode the secOneCarrierCode to set
	 */
	public void setSecOneCarrierCode(String secOneCarrierCode) {
		this.secOneCarrierCode = secOneCarrierCode;
	}

	/**
	 * @return the secTwoOriginLevel
	 */
	@Column(name = "SECTWOORGLVL")
	public String getSecTwoOriginLevel() {
		return secTwoOriginLevel;
	}

	/**
	 * @param secTwoOriginLevel the secTwoOriginLevel to set
	 */
	public void setSecTwoOriginLevel(String secTwoOriginLevel) {
		this.secTwoOriginLevel = secTwoOriginLevel;
	}

	/**
	 * @return the secTwoOriginValue
	 */
	@Column(name = "SECTWOORGVAL")
	public String getSecTwoOriginValue() {
		return secTwoOriginValue;
	}

	/**
	 * @param secTwoOriginValue the secTwoOriginValue to set
	 */
	public void setSecTwoOriginValue(String secTwoOriginValue) {
		this.secTwoOriginValue = secTwoOriginValue;
	}

	/**
	 * @return the secTwoDestLevel
	 */
	@Column(name = "SECTWODSTLVL")
	public String getSecTwoDestLevel() {
		return secTwoDestLevel;
	}

	/**
	 * @param secTwoDestLevel the secTwoDestLevel to set
	 */
	public void setSecTwoDestLevel(String secTwoDestLevel) {
		this.secTwoDestLevel = secTwoDestLevel;
	}

	/**
	 * @return the secTwoDestValue
	 */
	@Column(name = "SECTWODSTVAL")
	public String getSecTwoDestValue() {
		return secTwoDestValue;
	}

	/**
	 * @param secTwoDestValue the secTwoDestValue to set
	 */
	public void setSecTwoDestValue(String secTwoDestValue) {
		this.secTwoDestValue = secTwoDestValue;
	}

	/**
	 * @return the secTwoCarrierIdr
	 */
	@Column(name = "SECTWOCARIDR")
	public int getSecTwoCarrierIdr() {
		return secTwoCarrierIdr;
	}

	/**
	 * @param secTwoCarrierIdr the secTwoCarrierIdr to set
	 */
	public void setSecTwoCarrierIdr(int secTwoCarrierIdr) {
		this.secTwoCarrierIdr = secTwoCarrierIdr;
	}

	/**
	 * @return the secTwoCarrierCode
	 */
	@Column(name = "SECTWOCARCOD")
	public String getSecTwoCarrierCode() {
		return secTwoCarrierCode;
	}

	/**
	 * @param secTwoCarrierCode the secTwoCarrierCode to set
	 */
	public void setSecTwoCarrierCode(String secTwoCarrierCode) {
		this.secTwoCarrierCode = secTwoCarrierCode;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	
	public DispatchRoutingCarrierPK getDispatchRoutingCarrierPK() {
		return dispatchRoutingCarrierPK;
	}

	public void setDispatchRoutingCarrierPK(
			DispatchRoutingCarrierPK dispatchRoutingCarrierPK) {
		this.dispatchRoutingCarrierPK = dispatchRoutingCarrierPK;
	}

	
	/**
	 * 
	 * @log
	 */
	
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA DSNROUTING");
	}
	
	/**
	 * @param RoutingCarrierVO
	 */
	private void populatePK(RoutingCarrierVO routingCarrierVO) {		
		returnLogger().entering(CLASS_NAME,"populatePK");		
		DispatchRoutingCarrierPK dispatchRoutingCarrierPK=new DispatchRoutingCarrierPK(
				routingCarrierVO.getCompanyCode(),
				routingCarrierVO.getSequenceNumber());		
		this.setDispatchRoutingCarrierPK(dispatchRoutingCarrierPK);		
		returnLogger().exiting(CLASS_NAME,"populatePK");
	}
	/**
	 * @param RoutingCarrierVO
	 */
	private void populateAttributes(RoutingCarrierVO routingCarrierVO) {		
		returnLogger().entering(CLASS_NAME,"populateAttributes");	
		this.validFrom=routingCarrierVO.getValidFrom();		
		this.validToo=routingCarrierVO.getValidTo();
		//Modified by A-8236 as part of ICRD-252154 -starts
		this.mailOriginVal=routingCarrierVO.getOriginCity();
		this.mailDestVal=routingCarrierVO.getDestCity();
		this.secOneOriginValue=routingCarrierVO.getOwnSectorFrm();
		this.secOneDestValue=routingCarrierVO.getOwnSectorTo();
		this.secTwoOriginValue=routingCarrierVO.getOalSectorFrm();
		this.secTwoDestValue=routingCarrierVO.getOalSectorTo();
		this.secTwoCarrierCode=routingCarrierVO.getCarrier();
		//Modified by A-7794 as part of ICRD-285543
		this.secTwoCarrierIdr= routingCarrierVO.getCarrierIdr();
		////Modified by A-8236 as part of ICRD-252154 -end
		returnLogger().exiting(CLASS_NAME,"populateAttributes");		
	}
	

	/**
	 * The default Constructor
	 *
	 */
	public DispatchRoutingCarrier() {
	}

	
	public DispatchRoutingCarrier(RoutingCarrierVO routingCarrierVO)throws SystemException{
		returnLogger().entering(CLASS_NAME,"DispatchRoutingCarrier-Constructor");
		populatePK(routingCarrierVO);
		populateAttributes(routingCarrierVO);		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {			
			
			throw new SystemException(e.getMessage());
		} 		
		returnLogger().exiting(CLASS_NAME,"MRABillingDetails-Constructor");
	}
	
	
	
	/**
	 * @param companyCode
	 * @param originCity
	 * @param destCity
	 * @param ownSectorFrm
	 * @param ownSectorTo
	 * @param oalSectorFrm
	 * @param oalSectorTo
	 * @param sequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static DispatchRoutingCarrier find(String companyCode,String originCity,String destCity,String ownSectorFrm,String ownSectorTo,String oalSectorFrm,String oalSectorTo,int sequenceNumber)
								throws SystemException,FinderException{
		returnLogger().entering(CLASS_NAME,"find");
		DispatchRoutingCarrier dispatchRoutingCarrier=null;
		DispatchRoutingCarrierPK dispatchRoutingCarrierPK=new DispatchRoutingCarrierPK(companyCode,sequenceNumber);
		try {
			dispatchRoutingCarrier=PersistenceController.getEntityManager().find(
					DispatchRoutingCarrier.class,dispatchRoutingCarrierPK);
		} catch (FinderException e) {		
			
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"find");
		return dispatchRoutingCarrier;
		
	}
	
	
	/**
	 * 
	 * @param dsnRoutingVO
	 * @throws SystemException
	 */
	public void update(RoutingCarrierVO routingCarrierVO)
	throws SystemException{
		returnLogger().entering(CLASS_NAME,"update");
		populateAttributes(routingCarrierVO);		
		returnLogger().exiting(CLASS_NAME,"update");		
	}
	
	
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering(CLASS_NAME,"remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"remove");
	}
	
	
	 /**
	 * This method constructs
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO()
	throws PersistenceException, SystemException{
		Log log = LogFactory.getLogger("MRA DEFAULT");
		log.entering("DispatchRoutingCarrier","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRADefaultsDAO.class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}
	
	 /**
	 * @author a-4452
	 * @param carrierFilterVO
	 * @return DSNRoutingVO
	 * @throws SystemException	
	*/
	public static  Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO) 
	throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("DispatchRoutingCarrier","entity");
			return constructDAO().findRoutingCarrierDetails(carrierFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	/**
	 * @author a-7794 as part of ICRD-285543
	 * @param carrierFilterVO
	 * @return DSNRoutingVO
	 * @throws SystemException	
	*/
	public static  RoutingCarrierVO findCarrierDetails(RoutingCarrierVO carrierVO) 
	throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("findCarrierDetails","DispatchRoutingCarrier-entity");
			return constructDAO().findCarrierDetails(carrierVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	
	
	
	
}
