/*
 * MaintainCustomerRegistrationSession.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.customer.vo.AdditionalContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;



/**
 * 
 * @author A-2046
 *
 */
/**
 * @author a-7137
 * 18-May-2016
 */
public interface MaintainCustomerRegistrationSession extends ScreenSession {

	
	/**
	 * 
	 * @return
	 */
	public String getScreenID();
	/**
	 * 
	 * @return
	 */
    public String getModuleName();
    
    /**
     * 
     * @return
     */
    public ArrayList<CurrencyVO> getCurrency();
    /**
     * 
     * @param currency
     */
    public void setCurrency(ArrayList<CurrencyVO> currency);
    
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getCustomerStatus();
    /**
     * 
     * @param agreementStatus
     */
    public void setCustomerStatus(ArrayList<OneTimeVO> customerStatus);
    /**
     * 
     * @return
     */
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getCreditPeriod();
    /**
     * 
     * @param agreementStatus
     */
    public void setCreditPeriod(ArrayList<OneTimeVO> creditPeriod);
    /**
     * 
     * @return
     */
    public CustomerVO getCustomerVO();
    /**
     * 
     * @param customerVO
     */
    public void setCustomerVO(CustomerVO customerVO);
    
    public String getPageURL();

    public void setPageURL(String pageURL);
    /**
     * 
     * @return
     */
    public ArrayList<String> getCustomerCodesFromListing();
    /**
     * 
     * @param customerCodes
     */
    public void setCustomerCodesFromListing(ArrayList<String> customerCodes);
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getDefaultNotifyModes();
    /**
     * 
     * @param modes
     */
    public void setDefaultNotifyModes(ArrayList<OneTimeVO> modes);
    
    /**
	 * @param codes
	 */
	public void setForwarderTypes(ArrayList<OneTimeVO> forwarderTypes);
	
	public ArrayList<OneTimeVO> getForwarderTypes();
	
	public void setBillingPeriods(ArrayList<OneTimeVO> billingPeriods);
	
	public ArrayList<OneTimeVO> getBillingPeriods();
	
	// Added By A-5183 For icard 18283
	
	/**
	 * @return Collection<OneTimeVO>
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();
	
	/**
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues);
	
	public void setBillingCode(String billingCode);
	
	public String getBillingCode();
	
	public String getCustomerIDGenerationRequired();
	//Added for ICRD-67442 by A-5163 starts
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.getCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Get Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	HashMap<Integer,CustomerVO>
	 */
	public HashMap<Integer, CustomerVO> getCustomerDetailVOsInMap();
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.setCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Set Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	@param customerDetailVOs 
	 *	Return type	: 	void
	 */
	public void setCustomerDetailVOsInMap(HashMap<Integer, CustomerVO> customerDetailVOs);
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.removeCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Remove Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	public void removeCustomerDetailVOsInMap();
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.getSourcePage
	 *	Added by 	:	A-5163 on September 4, 2014
	 * 	Used for 	:	ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getSourcePage();

	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.setSourcePage
	 *	Added by 	:	A-5163 on September 4, 2014
	 * 	Used for 	:	ICRD-67442.
	 *	Parameters	:	@param sourcePage 
	 *	Return type	: 	void
	 */
	public void setSourcePage(String sourcePage);
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.setValidationErrors
	 *	Added by 	:	A-5163 on October 15, 2014
	 * 	Used for 	:	ICRD-67442.
	 *	Parameters	:	@param errors 
	 *	Return type	: 	void
	 */
	public void setValidationErrors(ArrayList<ErrorVO> errors);
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.getValidationErrors
	 *	Added by 	:	A-5163 on October 15, 2014
	 * 	Used for 	:	ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	public ArrayList<ErrorVO> getValidationErrors();
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.removeValidationErrors
	 *	Added by 	:	A-5163 on October 15, 2014
	 * 	Used for 	:	ICRD-67442.
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	public void removeValidationErrors();
	//Added for ICRD-67442 by A-5163 ends
	
	public void setCustomerIDGenerationRequired(String customerIDGenerationRequired);
	
	/**
	 * Method to get CustomerMiscDetailsVOs for validating bank and other details
	 * ICRD-88346
     * @author A-6055
     * @return 
     */
    public Collection<CustomerMiscDetailsVO> getCustomerMiscDetailsVOs();
    /**
     * Method to set CustomerMiscDetailsVOs for validating bank and other details
     * ICRD-88346
     * @author A-6055
     * @param 
     */
    public void setCustomerMiscDetailsVOs(ArrayList<CustomerMiscDetailsVO> customerMiscDetailsVOs);
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationSession.getCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	@return 
     *	Return type	: 	ArrayList<OneTimeVO>
     */
    public ArrayList<OneTimeVO> getCertificateTypes();
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationSession.setCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	@param certificateTypes 
     *	Return type	: 	void
     */
    public void setCertificateTypes(ArrayList<OneTimeVO> certificateTypes);
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationSession.removeCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	 
     *	Return type	: 	void
     */
    public void removeCertificateTypes();
    
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.getNotificationPreferences
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param 
    *	Return type	: 	Collection
    */
    public abstract ArrayList<NotificationPreferenceVO> getNotificationPreferences();
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.setNotificationPreferences
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param notificationPreferences
    *	Return type	: 	void
    */
    public abstract void setNotificationPreferences(ArrayList<NotificationPreferenceVO> notificationPreferences); 
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.removeNotificationPreferences
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param 
    *	Return type	: 	void
    */
    public abstract void removeNotificationPreferences(); 
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.getAdditionalContacts
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param 
    *	Return type	: 	Collection
    */
    public abstract ArrayList<AdditionalContactVO> getAdditionalContacts();
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.setAdditionalContacts
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param additionalContacts
    *	Return type	: 	void
    */
    public abstract void setAdditionalContacts(ArrayList<AdditionalContactVO> additionalContacts); 
    
    /**
    * 
    * 	Method		:	MaintainCustomerRegistrationSession.removeAdditionalContacts
    *	Added by 	:	A-7137 on 17-May-2016
    * 	Used for 	:	ICRD-152555.
    *	Parameters	:	@param 
    *	Return type	: 	void
    */
    public abstract void removeAdditionalContacts(); 
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationSession.setLanguagesForEbkAndQa
     *	Added by 	:	a-7137 on 10-Nov-2017
     * 	Used for 	:	ICRD-229549
     *	Parameters	:	@param languagesForEbkAndQa 
     *	Return type	: 	void
     */
    public void setLanguagesForEbkAndQa(ArrayList<OneTimeVO> languagesForEbkAndQa);
	
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationSession.getLanguagesForEbkAndQa
     *	Added by 	:	a-7137 on 10-Nov-2017
     * 	Used for 	:	ICRD-229549
     *	Parameters	:	@return 
     *	Return type	: 	ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getLanguagesForEbkAndQa();
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationSession.removeLanguagesForEbkAndQa
	 *	Added by 	:	a-7137 on 10-Nov-2017
	 * 	Used for 	:	ICRD-229549
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	public void removeLanguagesForEbkAndQa();
   
   public Collection<GeneralMasterGroupVO> getCustomerGroupDetails();
   public void setCustomerGroupDetails(Collection<GeneralMasterGroupVO> customerGroupDetails); 
   public void removeCustomerGroupDetails(); 
    
    
}
