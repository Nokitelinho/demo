/*
 * ListtempCustomerImpl.java Created on Apr 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.profile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-2135
 *
 */
public class ListtempCustomerImpl extends AbstractScreenSession
        implements ListtempCustomerSession {

	private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";
	private static final String MODULE = "customermanagement.defaults";
	private static final String KEY_LISTTEMPCUSTOMERREG="listtempcustregistration";
	private static final String KEY_LIST="listingcustomerregistration";
	private static final String KEY_ONETIMEVALUES="customer.RegisterCustomer.status";
	private static final String KEY_LISTTEMPCUSTREG="listtempcustomerregistration";
	private static final String KEY_TEMPCUSTOMERREG="tempcustomerregistration";
	private static final String KEY_TEMPID="tempids";
	private static final String KEY_PAGEURL="pageurl";
	private static final String KEY_CUSTCODE="custcode";
	private static final String COL_TEMP_ID_LOVS="tempIDVOLovVOs";
/**
 * 
 */
	public String getScreenID(){
		return SCREENID;
	}
/**
 * 
 */
	public String getModuleName(){
		return MODULE;
	}
	/**
	 * @return Page<TempCustomerVO>
	 */
	public Page<TempCustomerVO> getListCustomerRegistration() {
		return (Page<TempCustomerVO>) getAttribute(KEY_LIST);
	}
	/**
	 * @param listCustomerRegistration
	 */
	public void setListCustomerRegistration(Page<TempCustomerVO> listCustomerRegistration) {
		setAttribute(KEY_LIST, (Page<TempCustomerVO>) listCustomerRegistration);
	}
	/**
	 * 
	 */
	public void removeListCustomerRegistration() {
	 	removeAttribute(KEY_LIST);
	}
/**
 * 
 */
	public ListTempCustomerVO getListTempCustomerDetails() {
		return (ListTempCustomerVO) getAttribute(KEY_LISTTEMPCUSTOMERREG);
	}
	/**
	 * @param listTempCustomerDetails
	 */
	public void setListTempCustomerDetails(ListTempCustomerVO listTempCustomerDetails) {
		setAttribute(KEY_LISTTEMPCUSTOMERREG, (ListTempCustomerVO) listTempCustomerDetails);
	}
	/**
	 * 
	 */
	public void removeListTempCustomerDetails() {
	 	removeAttribute(KEY_LISTTEMPCUSTOMERREG);
	}
	/**
	 * @return 
	 */
	public Collection<OneTimeVO> getOneTimeValues() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ONETIMEVALUES);
	}
	/**
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(Collection<OneTimeVO> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, (ArrayList<OneTimeVO>) oneTimeValues);
	}
	/**
	 * 
	 */
	public void removeOneTimeValues() {
	 	removeAttribute(KEY_ONETIMEVALUES);
	}
	/**
	 * 
	 */
	
	public TempCustomerVO getTempCustomerDetails() {
		return (TempCustomerVO) getAttribute(KEY_TEMPCUSTOMERREG);
	}
	/**
	 * @param  tempCustomerDetails
	 */
	public void setTempCustomerDetails(TempCustomerVO tempCustomerDetails) {
		setAttribute(KEY_TEMPCUSTOMERREG, (TempCustomerVO) tempCustomerDetails);
	}
	/**
	 * 
	 */
	public void removeTempCustomerDetails() {
	 	removeAttribute(KEY_TEMPCUSTOMERREG);
	}	
	/**
	 *@return  Page<TempCustomerVO>
	 */
	public Page<TempCustomerVO> getListtempcustomerregistration() {
		return (Page<TempCustomerVO>) getAttribute(KEY_LISTTEMPCUSTREG);
	}
	/**
	 * @param listtempcustomerregistration
	 */
	public void setListtempcustomerregistration(Page<TempCustomerVO> listtempcustomerregistration) {
		setAttribute(KEY_LISTTEMPCUSTREG, (Page<TempCustomerVO>) listtempcustomerregistration);
	}
	/**
	 * 
	 */
	public void removeListtempcustomerregistration() {
	 	removeAttribute(KEY_LISTTEMPCUSTREG);
	}
	
	/**
	 * 
	 */
	public ArrayList<String> getTempIDs(){
		return getAttribute(KEY_TEMPID);
	}
	/**
	 * @param tempids
	 */
	public void setTempIDs(ArrayList<String> tempids){
		setAttribute(KEY_TEMPID,tempids);
	}
	/**
	 * 
	 */
	public String getPageURL(){
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl){
		setAttribute(KEY_PAGEURL,pageUrl);
	}
	/**
	 * @return String
	 */
	public String getCustCodeFlag(){
		return getAttribute(KEY_CUSTCODE);
	}
	/**
	 * @param custCodeFlag
	 */
	public void setCustCodeFlag(String custCodeFlag){
		setAttribute(KEY_CUSTCODE,custCodeFlag);
	}
	/**
	 * @return Page<TempCustomerVO>
	 */
	public Page<TempCustomerVO> getTempIdVOLovVOs() {
		return (Page<TempCustomerVO>) getAttribute(COL_TEMP_ID_LOVS);
	}
	/**
	 * @param tempIdVOLovVOs
	 */
	public void setTempIdVOLovVOs(Page<TempCustomerVO> tempIdVOLovVOs) {
		setAttribute(COL_TEMP_ID_LOVS, (Page<TempCustomerVO>) tempIdVOLovVOs);
	}
	/**
	 * 
	 */
	public void removeTempIdVOLovVOs() {
	 	removeAttribute(COL_TEMP_ID_LOVS);
	}
	
	//Added by A-5218 to enable last link pagination to start
	/**
	 * @return totalRecords
	 */
	public Integer getTotalRecords(){
	    return (Integer)getAttribute("totalRecords");
	}
    /**
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords){
	    setAttribute("totalRecords", Integer.valueOf(totalRecords));
	}
	 //Added by A-5218 to enable last link pagination to end
	
	
	
}