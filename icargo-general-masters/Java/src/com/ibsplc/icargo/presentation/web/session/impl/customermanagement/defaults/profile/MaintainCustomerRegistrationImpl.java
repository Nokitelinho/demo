/*
 * MaintainCustomerRegistrationImpl.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingDetailVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.customer.vo.AdditionalContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 * @author A-2046
 * 
 */
public class MaintainCustomerRegistrationImpl extends AbstractScreenSession
		implements MaintainCustomerRegistrationSession {

	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String KEY_CURRENCY = "currency";

	private static final String KEY_CUSTOMERSTATUS = "customerstatus";

	private static final String KEY_CREDITPERIOD = "creditperiod";

	private static final String KEY_CUSTOMERVO = "customervo";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_CODESFROMLISTING = "codesfromlisting";

	private static final String KEY_DEF_NOTIFY_MODE = "defaultNotificationModes";
	
    private static final String KEY_FRW_TYP ="forwarderTypes";
	
	private static final String KEY_BLL_PRD ="billingPeriods";
	private static final String KEY_ONETIMEVO = "ONETIMEVO";
	private static final String KEY_BLL_CODE ="billingCode";
	
	//Added for ICRD-67442 by A-5163 starts
	private static final String KEY_CUSDTL_HIS = "customerdetailhistory";
	private static final String KEY_CUSDTL_SRC = "customerdetailhistorysourcepage";
	private static final String KEY_VALIDATION_ERRORS = "customerDetailValidationErrors";
	//Added for ICRD-67442 by A-5163 ends
	
	
	// Added by A-5198 for ICRD-56507
	private static final String KEY_CUSTOMERIDGENERATIONREQUIRED = "customerIDGenerationRequired";
	
	//Added By A-6055 for ICRD-88346
	private static final String KEY_CUSTOMER_MISC_DETAILS_VOS = "customermiscdetailsvos";
	
	//Added for ICRD-58628 by A-5163
	private static final String KEY_CUSTOMER_CERTIFICATE_TYPES = "customerCertificateTypes";
	
	private static final String KEY_NOTIFICATION_PREFERNCE_VO = "notificationpreferencevo";
	private static final String KEY_ADDITIONAL_CONTACT_VO = "additionalcontactvo";
	
	// Added by A-7137 as part of the BUG ICRD-229549
	private static final String KEY_LANGUAGE_EBK_QA = "languageForEbkAndQa";
	private static final String KEY_GROUPDETAILS = "customerGroupDetails";
	
	/**
	 * @return
	 */
	public String getCustomerIDGenerationRequired() {
		return getAttribute(KEY_CUSTOMERIDGENERATIONREQUIRED);
	}
	/**
	 * @param customerIDGenerationRequired
	 */
	public void setCustomerIDGenerationRequired(String customerIDGenerationRequired) {
		setAttribute(KEY_CUSTOMERIDGENERATIONREQUIRED, customerIDGenerationRequired);
	}

	/**
	 * return screen id
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * get module name
	 */
	public String getModuleName() {
		return MODULENAME;
	}

	/**
	 * @return
	 */
	public ArrayList<CurrencyVO> getCurrency() {
		return getAttribute(KEY_CURRENCY);
	}

	/**
	 * @param currency
	 */
	public void setCurrency(ArrayList<CurrencyVO> currency) {
		setAttribute(KEY_CURRENCY, currency);
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getCustomerStatus() {
		return getAttribute(KEY_CUSTOMERSTATUS);
	}

	/**
	 * @param customerStatus
	 */
	public void setCustomerStatus(ArrayList<OneTimeVO> customerStatus) {
		setAttribute(KEY_CUSTOMERSTATUS, customerStatus);
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getCreditPeriod() {
		return getAttribute(KEY_CREDITPERIOD);
	}

	/**
	 * @param creditPeriod
	 */
	public void setCreditPeriod(ArrayList<OneTimeVO> creditPeriod) {
		setAttribute(KEY_CREDITPERIOD, creditPeriod);
	}
/**
 * 
 */
	public CustomerVO getCustomerVO() {
		return getAttribute(KEY_CUSTOMERVO);
	}
/**
 * @param customerVO
 */
	public void setCustomerVO(CustomerVO customerVO) {
		setAttribute(KEY_CUSTOMERVO, customerVO);
	}
/**
 * 
 */
	public String getPageURL() {
		return getAttribute(KEY_PAGEURL);
	}
/**
 * @param pageUrl
 */
	public void setPageURL(String pageUrl) {
		setAttribute(KEY_PAGEURL, pageUrl);
	}
	/**
	 * @param codes
	 */
	public void setCustomerCodesFromListing(ArrayList<String> codes){
		setAttribute(KEY_CODESFROMLISTING,codes);
	}
	/**
	 * 
	 */
	public ArrayList<String> getCustomerCodesFromListing(){
		return getAttribute(KEY_CODESFROMLISTING);
	}
	/**
	 * @param codes
	 */
	public void setDefaultNotifyModes(ArrayList<OneTimeVO> modes){
		setAttribute(KEY_DEF_NOTIFY_MODE,modes);
	}
	/**
	 * 
	 */
	public ArrayList<OneTimeVO> getDefaultNotifyModes(){
		return getAttribute(KEY_DEF_NOTIFY_MODE);
	}	
	/**
	 * @param codes
	 */
	public void setForwarderTypes(ArrayList<OneTimeVO> forwarderTypes){
		setAttribute(KEY_FRW_TYP,forwarderTypes);
	}
	/**
	 * 
	 */
	public ArrayList<OneTimeVO> getForwarderTypes(){
		return getAttribute(KEY_FRW_TYP);
	}	
	
	/**
	 * @param codes
	 */
	public void setBillingPeriods(ArrayList<OneTimeVO> billingPeriods){
		setAttribute(KEY_BLL_PRD,billingPeriods);
	}
	/**
	 * 
	 */
	public ArrayList<OneTimeVO> getBillingPeriods(){
		return getAttribute(KEY_BLL_PRD);
	}
	
	// Added by A-5183 For ICRD-18283
	
	public void setOneTimeValues(
    		HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
    	setAttribute(KEY_ONETIMEVO, oneTimeValues);
    }


	/* (non-Javadoc)
	 * @see #getOneTimeVOs()
	 */

	 /** (non-Javadoc)
     * @return oneTimeValues(HashMap<String, Collection<OneTimeVO>>)
     */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
        return getAttribute(KEY_ONETIMEVO);
	}
    
    /**
	 * @param codes
	 */
	public void setBillingCode(String billingcode){
		setAttribute(KEY_BLL_CODE,billingcode);
	}
	/**
	 * 
	 */
	public String getBillingCode(){
		return getAttribute(KEY_BLL_CODE);
	}
    
	//Added for ICRD-67442 by A-5163 starts
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationImpl.getCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Get Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	HashMap<Integer,CustomerVO>
	 */
	public HashMap<Integer, CustomerVO> getCustomerDetailVOsInMap(){
		 return getAttribute(KEY_CUSDTL_HIS);
	}
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationImpl.setCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Set Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	@param customerDetailVOs 
	 *	Return type	: 	void
	 */
	public void setCustomerDetailVOsInMap(HashMap<Integer, CustomerVO> customerDetailVOs){
		setAttribute(KEY_CUSDTL_HIS, customerDetailVOs);
	}
	
	/**
	 * 
	 * 	Method		:	MaintainCustomerRegistrationImpl.removeCustomerDetailVOsInMap
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Remove Customer Detail VOs In Map.Added for ICRD-67442.
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	public void removeCustomerDetailVOsInMap(){
		removeAttribute(KEY_CUSDTL_HIS);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see MaintainCustomerRegistrationSession#getSourcePage()
	 *	Added by 			: A-5163 on September 4, 2014
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public String getSourcePage() {	
		return getAttribute(KEY_CUSDTL_SRC);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see MaintainCustomerRegistrationSession#setSourcePage(java.lang.String)
	 *	Added by 			: A-5163 on September 4, 2014
	 * 	Used for 	:
	 *	Parameters	:	@param sourcePage
	 */
	public void setSourcePage(String sourcePage) {	
		setAttribute(KEY_CUSDTL_SRC, sourcePage);
	}

	/**
	 * 
	 *	Overriding Method	:	@see MaintainCustomerRegistrationSession#getValidationErrors()
	 *	Added by 			: 	A-5163 on October 15, 2014
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public ArrayList<ErrorVO> getValidationErrors() {		
		return getAttribute(KEY_VALIDATION_ERRORS);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see MaintainCustomerRegistrationSession#removeValidationErrors()
	 *	Added by 			: 	A-5163 on October 15, 2014
	 * 	Used for 	:
	 *	Parameters	:
	 */
	public void removeValidationErrors() {
		removeAttribute(KEY_VALIDATION_ERRORS);
	}    
	
	/**
	 * 
	 *	Overriding Method	:	@see MaintainCustomerRegistrationSession#setValidationErrors(java.util.Collection)
	 *	Added by 			: 	A-5163 on October 15, 2014
	 * 	Used for 	:
	 *	Parameters	:	@param errors
	 */
	public void setValidationErrors(ArrayList<ErrorVO> errors) {
		setAttribute(KEY_VALIDATION_ERRORS, errors);
	}
	//Added for ICRD-67442 by A-5163 ends	
	
	/**
	 * Method to get CustomerMiscDetailsVOs for validating bank and other details
	 * ICRD-88346
     * @author A-6055
     * @return ArrayList<CustomerMiscDetailsVO>
     */
	public ArrayList<CustomerMiscDetailsVO> getCustomerMiscDetailsVOs() {
		return getAttribute(KEY_CUSTOMER_MISC_DETAILS_VOS);
	}
	/**
     * Method to set CustomerMiscDetailsVOs for validating bank and other details
     * ICRD-88346
     * @author A-6055
     * @param ArrayList<CustomerMiscDetailsVO>
     */
	public void setCustomerMiscDetailsVOs(ArrayList<CustomerMiscDetailsVO> customerMiscDetailsVOs) {
		setAttribute(KEY_CUSTOMER_MISC_DETAILS_VOS, customerMiscDetailsVOs);
	}
	
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationImpl.getCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	@return 
     *	Return type	: 	ArrayList<OneTimeVO>
     */
    public ArrayList<OneTimeVO> getCertificateTypes() {
    	return getAttribute(KEY_CUSTOMER_CERTIFICATE_TYPES);
    }
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationImpl.setCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	@param certificateTypes 
     *	Return type	: 	void
     */
    public void setCertificateTypes(ArrayList<OneTimeVO> certificateTypes) {
    	setAttribute(KEY_CUSTOMER_CERTIFICATE_TYPES, certificateTypes);
    }
    
    /**
     * 
     * 	Method		:	MaintainCustomerRegistrationImpl.removeCertificateTypes
     *	Added by 	:	A-5163 on January 2, 2015
     * 	Used for 	:	ICRD-58628.
     *	Parameters	:	 
     *	Return type	: 	void
     */
    public void removeCertificateTypes() {
    	removeAttribute(KEY_CUSTOMER_CERTIFICATE_TYPES);
	}
    
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.getNotificationPreferences
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param 
	*	Return type	: 	Collection
	*/
	@Override
	public ArrayList<NotificationPreferenceVO> getNotificationPreferences() {
		return getAttribute(KEY_NOTIFICATION_PREFERNCE_VO);
	}
	
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.setNotificationPreferences
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param notificationPreferences
	*	Return type	: 	void
	*/
	@Override
	public void setNotificationPreferences(ArrayList<NotificationPreferenceVO> notificationPreferences) {
		setAttribute(KEY_NOTIFICATION_PREFERNCE_VO, notificationPreferences);
	}
	
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.removeNotificationPreferences
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param 
	*	Return type	: 	void
	*/
	@Override
	public void removeNotificationPreferences() {
		removeAttribute(KEY_NOTIFICATION_PREFERNCE_VO);
	}
	
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.getAdditionalContacts
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param 
	*	Return type	: 	Collection
	*/
	@Override
	public ArrayList<AdditionalContactVO> getAdditionalContacts() {
		return getAttribute(KEY_ADDITIONAL_CONTACT_VO);
	}
	
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.setAdditionalContacts
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param additionalContacts
	*	Return type	: 	void
	*/
	@Override
	public void setAdditionalContacts(ArrayList<AdditionalContactVO> additionalContacts) {
		setAttribute(KEY_ADDITIONAL_CONTACT_VO, additionalContacts);
	}
	
	/**
	* 
	* 	Method		:	MaintainCustomerRegistrationSession.removeAdditionalContacts
	*	Added by 	:	A-7137 on 18-May-2016
	* 	Used for 	:	ICRD-152555.
	*	Parameters	:	@param 
	*	Return type	: 	void
	*/
	@Override
	public void removeAdditionalContacts() {
		removeAttribute(KEY_ADDITIONAL_CONTACT_VO);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile
	 *	.MaintainCustomerRegistrationSession#setLanguagesForEbkAndQa(java.util.ArrayList)
	 *	Added by 			: a-7137 on 10-Nov-2017
	 * 	Used for 	:	ICRD-229549
	 *	Parameters	:	@param languagesForEbkAndQa
	 */
	@Override
	public void setLanguagesForEbkAndQa(
			ArrayList<OneTimeVO> languagesForEbkAndQa) {
		setAttribute(KEY_LANGUAGE_EBK_QA, languagesForEbkAndQa);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile
	 *	.MaintainCustomerRegistrationSession#getLanguagesForEbkAndQa()
	 *	Added by 			: a-7137 on 10-Nov-2017
	 * 	Used for 	:	ICRD-229549
	 *	Parameters	:	@return
	 */
	@Override
	public ArrayList<OneTimeVO> getLanguagesForEbkAndQa() {
		return getAttribute(KEY_LANGUAGE_EBK_QA);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile
	 *	.MaintainCustomerRegistrationSession#removeLanguagesForEbkAndQa()
	 *	Added by 			: a-7137 on 10-Nov-2017
	 * 	Used for 	:	ICRD-229549
	 *	Parameters	:
	 */
	@Override
	public void removeLanguagesForEbkAndQa() {
		removeAttribute(KEY_LANGUAGE_EBK_QA);
	}
	public Collection<GeneralMasterGroupVO>  getCustomerGroupDetails() {
		return(Collection<GeneralMasterGroupVO>) getAttribute(KEY_GROUPDETAILS);
	}
	public void setCustomerGroupDetails(
			Collection<GeneralMasterGroupVO> groupDetails) {
		setAttribute(KEY_GROUPDETAILS,(ArrayList<GeneralMasterGroupVO>) groupDetails);
	}
	public void removeCustomerGroupDetails() {
		removeAttribute(KEY_GROUPDETAILS);
	}
}
