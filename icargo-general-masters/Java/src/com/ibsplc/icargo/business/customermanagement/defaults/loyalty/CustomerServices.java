/*
 * CustomerServices.java Created on jun 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

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

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 *
 */

@Table(name="CUSSRVMST")
@Entity
public class CustomerServices {

   private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
   private static final String MODULE = "customermanagement.defaults";
   
   private CustomerServicesPK servicePK;
   
   private double points;
   private String serviceDescription;
   private String keyContactFlag;
   private Calendar lastUpdatedTime;
   private String lastUpdatedUser;
   
	/**
	 * @return String Returns the keyContactFlag.
	 */
   @Column(name = "KEYCNTFLG")
	public String getKeyContactFlag() {
		return this.keyContactFlag;
	}
	/**
	 * @param keyContactFlag The keyContactFlag to set.
	 */
	public void setKeyContactFlag(String keyContactFlag) {
		this.keyContactFlag = keyContactFlag;
	}
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	 @Column(name = "LSTUPDTIM")
	 @Version

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	 @Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return double Returns the points.
	 */
	@Column(name = "PNT")
	public double getPoints() {
		return this.points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return String Returns the serviceDescription.
	 */
	@Column(name = "SRVDES")
	public String getServiceDescription() {
		return this.serviceDescription;
	}
	/**
	 * @param serviceDescription The serviceDescription to set.
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	/**
	 * @return CustomerServicesPK Returns the servicePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="serviceCode", column=@Column(name="SRVCOD"))}	)
	public CustomerServicesPK getServicePK() {
		return this.servicePK;
	}
	/**
	 * @param servicePK The servicePK to set.
	 */
	public void setServicePK(CustomerServicesPK servicePK) {
		this.servicePK = servicePK;
	}
   /**
    * 
    *
    */
   public CustomerServices() {
	   
   }
   /**
    * 
    * @param serviceVO
    * @throws SystemException
    */
   public CustomerServices(CustomerServicesVO serviceVO)
   throws SystemException {
	   log.entering("CustomerServices","CustomerServices Constructor");
		populatePK(serviceVO);
		populateAttributes(serviceVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			log.log(Log.SEVERE,"CreateException");
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("CustomerServices","CustomerServices Constructor");
   }
   /**
    * 
    * @param serviceVO
    */
   public void populatePK(CustomerServicesVO serviceVO)  {
	   log.entering("CustomerServices","populatePK");
	   CustomerServicesPK customerServicesPK = new CustomerServicesPK();
	   customerServicesPK.setCompanyCode(   serviceVO.getCompanyCode());
	   customerServicesPK.setServiceCode(   serviceVO.getServiceCode());
	   this.servicePK =customerServicesPK ;   
	   log.exiting("CustomerServices","populatePK");
   }
   /**
    * 
    * @param serviceVO
    */
   public void populateAttributes(CustomerServicesVO serviceVO) {
	   log.entering("CustomerServices","populateAttributes");
	   
	   setPoints(serviceVO.getPoints());
	   setServiceDescription(serviceVO.getServiceDescription());
	   setKeyContactFlag(serviceVO.getKeyContactFlag());
	   setLastUpdatedUser(serviceVO.getLastUpdatedUser());
	   
	   log.exiting("CustomerServices","populateAttributes");
   }
   /**
    * 
    * @param serviceVO
    * @return
    * @throws SystemException
    */
   public static CustomerServices find(CustomerServicesVO serviceVO)
   throws SystemException {
	   CustomerServices customerServices = null;
	   CustomerServicesPK customerServicesPK = new CustomerServicesPK();
	   customerServicesPK.setCompanyCode(   serviceVO.getCompanyCode());
	   customerServicesPK.setServiceCode(   serviceVO.getServiceCode());
	   EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			customerServices = entityManager.find(CustomerServices.class,
					customerServicesPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
		return customerServices;
   }
   /**
    * 
    * @throws SystemException
    */
   public void remove() throws SystemException {
		log.entering("CustomerServices","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting("CustomerServices","remove");
	}
   /**
    * 
    * @param serviceVO
    * @throws SystemException
    */
   public void update(CustomerServicesVO serviceVO)
	throws SystemException {
		log.entering("CustomerServices","update");
		this.setLastUpdatedTime(serviceVO.getLastUpdatedTime());
		populateAttributes(serviceVO);
		log.exiting("CustomerServices","update");
	}
   /**
    * 
    * @param serviceVO 
 * @return
 * @throws SystemException 
    */
   public static String checkForService(CustomerServicesVO serviceVO)
		   throws SystemException {
	   EntityManager em = PersistenceController.getEntityManager();
   	   try {
   			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
   				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
   			return customerMgmntDefaultsDAO.checkForService(serviceVO);
   		  					
   	       } catch (PersistenceException e) {
//printStackTraccee()();
   			throw new SystemException(e.getErrorCode());
   	       }
   }
   /**
    * 
    * @param companyCode
    * @param serviceCode
    * @return
    * @throws SystemException
    */
   public static CustomerServicesVO listCustomerServices(String companyCode,
     		String serviceCode)throws SystemException{
	   EntityManager em = PersistenceController.getEntityManager();
   	   try {
   			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
   				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
   			return customerMgmntDefaultsDAO.listCustomerServices(
   					companyCode,serviceCode);
   		  					
   	       } catch (PersistenceException e) {
//printStackTraccee()();
   			throw new SystemException(e.getErrorCode());
   	       }
   }
   /**
    * 
    * @param companyCode
    * @param serviceCode
 * @param pageNumber 
    * @return
    * @throws SystemException
    */
   public static Page<CustomerServicesVO> customerServicesLOV(String companyCode,
     		String serviceCode,int pageNumber)throws SystemException{
	   EntityManager em = PersistenceController.getEntityManager();
   	   try {
   			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
   				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
   			return customerMgmntDefaultsDAO.customerServicesLOV(
   					companyCode,serviceCode,pageNumber);
   		  					
   	       } catch (PersistenceException e) {
//printStackTraccee()();
   			throw new SystemException(e.getErrorCode());
   	       }
   }
	
   
}
