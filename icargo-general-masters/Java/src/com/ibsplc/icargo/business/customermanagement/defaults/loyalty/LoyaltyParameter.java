/*
 * LoyaltyParameter.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
@Staleable
@Table(name="CUSLTYPARMST")
@Entity
public class LoyaltyParameter {
	
	private Log log=LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private LoyaltyParameterPK loyaltyParameterPK;
	private String parameterValue;
	
	
	/**
	 * @return Returns the parameterValue.
	 */
	@Column(name = "PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}
	
	/**
	 * @param parameterValue The parameterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	/**
	 * Constructor
	 */
	public LoyaltyParameter() {
	}
	
/**
 * 
 * @param loyaltyParameterVO
 * @throws SystemException
 */
	public LoyaltyParameter(LoyaltyParameterVO loyaltyParameterVO)
	throws SystemException {
		log.entering("LoyaltyParameter","LoyaltyParameter Constructor");
		populatePk(loyaltyParameterVO);
		populateAttributes(loyaltyParameterVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			log.log(Log.SEVERE,"CreateException");
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("LoyaltyParameter","LoyaltyParameter Constructor");
	}
	
	/**
	 * private method to populate PK
	 *
	 * @param loyaltyParameterVO
	 */
	public void populatePk(LoyaltyParameterVO loyaltyParameterVO) {
		log.entering("LoyaltyParameter","populatePk");
		LoyaltyParameterPK loyaltyParameterPk = new LoyaltyParameterPK();
		loyaltyParameterPk.setCompanyCode(   loyaltyParameterVO.getCompanyCode());
		loyaltyParameterPk.setLoyaltyProgrammeCode(   loyaltyParameterVO.getLoyaltyProgrammeCode());
		loyaltyParameterPk.setParameterCode(   loyaltyParameterVO.getParameterCode());
		this.loyaltyParameterPK = loyaltyParameterPk ;
		log.exiting("LoyaltyParameter","populatePk");
	}
	
	/**
	 * private method to populate attributes
	 *
	 * @param loyaltyParameterVO
	 * @throws SystemException
	 */
	public void populateAttributes(LoyaltyParameterVO loyaltyParameterVO)
	throws SystemException {
		log.entering("LoyaltyParameter","populateAttributes");
		this.setParameterValue(loyaltyParameterVO.getParameterValue());
		log.exiting("LoyaltyParameter","populateAttributes");
	}
	
	/**
	 * This method finds the LoyaltyParameter instance based on the TempCustomerPK
	 *
	 * @param loyaltyParameterVO
	 * @return
	 * @throws SystemException
	 */
	public static LoyaltyParameter find(LoyaltyParameterVO loyaltyParameterVO)
	throws SystemException {
		LoyaltyParameter loyaltyParameter = null;
		LoyaltyParameterPK loyaltyParameterPk = new LoyaltyParameterPK();
		loyaltyParameterPk.setCompanyCode(   loyaltyParameterVO.getCompanyCode());
		loyaltyParameterPk.setLoyaltyProgrammeCode(   loyaltyParameterVO.getLoyaltyProgrammeCode());
		loyaltyParameterPk.setParameterCode(   loyaltyParameterVO.getParameterCode());
		loyaltyParameterPk.setSequenceNumber(   loyaltyParameterVO.getSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			loyaltyParameter = entityManager.find(LoyaltyParameter.class,loyaltyParameterPk);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return loyaltyParameter;
	}
	/**
	 * @param loyaltyParameterVO
	 * @throws SystemException
	 */
	public void update(LoyaltyParameterVO loyaltyParameterVO)
	throws SystemException {
		log.entering("LoyaltyParameter","update");
		populateAttributes(loyaltyParameterVO);
		log.exiting("LoyaltyParameter","update");
	}
	/**
	 * This method is used to remove the business object.
	 * It interally calls the remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("LoyaltyParameter","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting("LoyaltyParameter","remove");
	}
	
	
	//To be reviewed
	/**
	 * @OneToMany
	 * @JoinColumns( {
	 * @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 * @JoinColumn(name = "LTYPRGCOD", referencedColumnName = "LTYPRGCOD", insertable=false, updatable=false),
	 * @JoinColumn(name = "LTYPRGPAR", referencedColumnName = "LTYPRGPAR", insertable=false, updatable=false),
	 * @JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable=false, updatable=false))}
	 */
	/**
	 * @return Returns the loyaltyParameterPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="loyaltyProgrammeCode", column=@Column(name="LTYPRGCOD")),
		@AttributeOverride(name="parameterCode", column=@Column(name="LTYPRGPAR")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
	)
	public LoyaltyParameterPK getLoyaltyParameterPK() {
		return loyaltyParameterPK;
	}
	/**
	 * @param loyaltyParameterPK The loyaltyParameterPK to set.
	 */
	public void setLoyaltyParameterPK(LoyaltyParameterPK loyaltyParameterPK) {
		this.loyaltyParameterPK = loyaltyParameterPK;
	}
	
	
}
