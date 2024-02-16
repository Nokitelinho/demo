/*
 * ProductFeedback.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
@Table(name="PRDFEDBCK")
@Entity
public class ProductFeedback {

	private Log log = LogFactory.getLogger("PRODUCTS");
	
	private ProductFeedbackPK productFeedbackPk ;
	private String name;
	private String emailId;
	private String address;
	private String remarks;
	private String lastupdatedUser;
	private Calendar lastupdatedTime;
	private Calendar feedbackDate;
	/**
	 * @return Returns the address.
	 */
	@Column(name="SNDADR")
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the emailId.
	 */
	@Column(name="SNDEMLADR")
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return Returns the lastupdatedTime.
	 */
	@Version
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastupdatedTime() {
		return lastupdatedTime;
	}

	/**
	 * @param lastupdatedTime The lastupdatedTime to set.
	 */
	public void setLastupdatedTime(Calendar lastupdatedTime) {
		this.lastupdatedTime = lastupdatedTime;
	}

	/**
	 * @return Returns the lastupdatedUser.
	 */
	
	@Column(name="LSTUPDUSR")
	public String getLastupdatedUser() {
		return lastupdatedUser;
	}

	/**
	 * @param lastupdatedUser The lastupdatedUser to set.
	 */
	public void setLastupdatedUser(String lastupdatedUser) {
		this.lastupdatedUser = lastupdatedUser;
	}

	/**
	 * @return Returns the name.
	 */
	@Column(name="SNDNAME")
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the remarks.
	 */
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the feedbackDate.
	 */
	@Column(name="FEDBCKDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFeedbackDate() {
		return feedbackDate;
	}

	/**
	 * @param feedbackDate The feedbackDate to set.
	 */
	public void setFeedbackDate(Calendar feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	/**
	 * @return Returns the productFeedbackPk.
	 */
	@EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			@AttributeOverride(name="feedbackId", column=@Column(name="FEDBCKIDR"))}
	)
	public ProductFeedbackPK getProductFeedbackPk() {
		return productFeedbackPk;
	}
	/**
	 * @param productFeedbackPk The productFeedbackPk to set.
	 */
	public void setProductFeedbackPk(ProductFeedbackPK productFeedbackPk) {
		this.productFeedbackPk = productFeedbackPk;
	}
/**
 * Default Constructor
 */
	public ProductFeedback(){
		
	}
	/**
	 * @param productFeedbackVO
	 * @throws SystemException
	 */
	public ProductFeedback(ProductFeedbackVO productFeedbackVO)throws SystemException {
		log.entering("ProductFeedback","ProductFeedback()");
		populateAttributes(productFeedbackVO);
		populatePk(productFeedbackVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("ProductFeedback","ProductFeedback()");
	}
	/**
	 * @param productFeedbackVO
	 * @throws SystemException
	 */
	private void populateAttributes(ProductFeedbackVO productFeedbackVO)throws SystemException {
		log.entering("ProductFeedback","populateAttributes()");
		this.setAddress(productFeedbackVO.getAddress());
		this.setEmailId(productFeedbackVO.getEmailId());
		this.setLastupdatedTime(productFeedbackVO.getLastupdatedTime());
		this.setLastupdatedUser(productFeedbackVO.getLastupdatedUser());
		this.setName(productFeedbackVO.getName());
		this.setRemarks(productFeedbackVO.getRemarks());
		this.setFeedbackDate(productFeedbackVO.getFeedbackDate());
		log.exiting("ProductFeedback","populateAttributes()");
	}
	/**
	 * @param productFeedbackVO
	 * @throws SystemException
	 */
   private void populatePk(ProductFeedbackVO productFeedbackVO)throws SystemException {
	   log.entering("ProductFeedback","populatePk()");
	   ProductFeedbackPK productFeedbackPK = new ProductFeedbackPK();
	   productFeedbackPK.setCompanyCode(productFeedbackVO.getCompanyCode());
	   productFeedbackPK.setProductCode(productFeedbackVO.getProductCode());
	   this.productFeedbackPk = productFeedbackPK;
	   log.entering("ProductFeedback","populatePk()");
   }
   /**
    * @author A-1883
    * @param productFeedbackFilterVO
    * @param displayPage
    * @return Page<ProductFeedbackVO>
    * @throws SystemException
    */
   public static Page<ProductFeedbackVO> listProductFeedback(
			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage) 
			throws SystemException {
	   try {
		   return constructDAO().listProductFeedback(productFeedbackFilterVO,displayPage);
	   }
	   catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
   }
   /**
	 * method will return object of type ProductDefaultsDAO
	 * @author A-1883
	 * @return ProductDefaultsDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static ProductDefaultsDAO constructDAO()
	throws SystemException, PersistenceException {
		EntityManager em = PersistenceController.getEntityManager();
		return ProductDefaultsDAO.class.cast
		(em.getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME));

	}
}
