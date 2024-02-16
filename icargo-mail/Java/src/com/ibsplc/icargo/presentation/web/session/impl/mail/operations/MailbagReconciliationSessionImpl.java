/*
 * MailbagReconciliationSessionImpl.java Created on Oct 12, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-3270
 *
 */
public class MailbagReconciliationSessionImpl  extends AbstractScreenSession implements MailbagReconciliationSession{
	

	/**
     * String KEY_SCREEN_ID
     */
    public static final String KEY_SCREEN_ID = "mailtracking.defaults.MailbagReconciliation";
    /**
     * String KEY_MODULE_NAME
     */
	public static final String KEY_MODULE_NAME = "mail.operations";
	/**
     * String KEY_FORWARDERAREAVOS
     */
	private static final String KEY_MAILRECONCILIATIONDETAILSVOS = "mailReconciliationDetailsVOs";
	
	/**
     * String KEY_FORWARDERAREAVOS
     */
	private static final String KEY_MAILRECONCILIATIONFILTERVO = "mailReconciliationFilterVO";
	
	 /**
     * String KEY_ONETIME_TYPE
     */
	public static final String KEY_ONETIME_TYPE = "oneTimeType";
	
	private static final String  KEY_DELAYPERIOD = "delayPeriod";
	
	private static final String KEY_DATA = "data";
	
	private static final String  TOTALRECORDS = "totalRecords";

	
	
	
	/**
	 * @return KEY_MODULE_NAME
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * @return KEY_SCREEN_ID
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * @return Returns the mailReconciliationVOs.
	 */
	public Page<MailReconciliationDetailsVO> getMailReconciliationDetailsVOs(){
			return (Page<MailReconciliationDetailsVO> ) getAttribute(KEY_MAILRECONCILIATIONDETAILSVOS);
	}
	
	/**
	 * @param mailReconciliationDetailsVOs 
	 */
	public  void setMailReconciliationDetailsVOs(Page<MailReconciliationDetailsVO> mailReconciliationDetailsVOs) {
		setAttribute(KEY_MAILRECONCILIATIONDETAILSVOS, (Page<MailReconciliationDetailsVO>) mailReconciliationDetailsVOs);
	}
	/**
     * This method removes the mailReconciliationDetailsVOs in session
     */
	public void removeMailReconciliationDetailsVOs() {
	 	removeAttribute(KEY_MAILRECONCILIATIONDETAILSVOS);
	}
	
	/**
	 * @return Returns the MailReconciliationFilterVO.
	 */
	public MailReconciliationFilterVO getMailReconciliationFilterVO(){
			return getAttribute(KEY_MAILRECONCILIATIONFILTERVO);
	}
	
	/**
	 * @param mailReconciliationFilterVO 
	 */
	public  void setMailReconciliationFilterVO(MailReconciliationFilterVO mailReconciliationFilterVO) {
		setAttribute(KEY_MAILRECONCILIATIONFILTERVO, mailReconciliationFilterVO);
	}
	/**
     * This method removes the mailReconciliationVOs in session
     */
	public void removeMailReconciliationFilterVO() {
	 	removeAttribute(KEY_MAILRECONCILIATIONFILTERVO);
	}
	/**
	 * @return Returns the OneTimeType.
	 */
	public Collection<OneTimeVO> getOneTimeType(){
		return (Collection<OneTimeVO>) getAttribute(KEY_ONETIME_TYPE);
	}
	
	/**
	 * @param oneTimeType 
	 */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType){
		setAttribute(KEY_ONETIME_TYPE, (ArrayList<OneTimeVO>) oneTimeType);
	}
	
	/**
     * This method removes the OneTimeType in session
     */
	public void removeOneTimeType() {
	 	removeAttribute(KEY_ONETIME_TYPE);
	}
	/**
	 * Method to get the delayPeriod
	 */
	public Integer getDelayPeriod() {
		return getAttribute(KEY_DELAYPERIOD);
	}
	/**
	 * @param delayPeriod 
	 */
	public void setDelayPeriod(int delayPeriod) {
		setAttribute(KEY_DELAYPERIOD,delayPeriod);
	}
	/**
	 * Method to remove the delayPeiod
	 */
	public void removeDelayPeriod() {
		removeAttribute(KEY_DELAYPERIOD);
	}

	/**
	 * @return data
	 */
	public String getData() {

		return getAttribute( KEY_DATA );
	}

	/**
	 * @param data
	 */
	public void setData(String data) {
		setAttribute(KEY_DATA, data);

	}

	/**
	 * removes data
	 */
	public void removeData() {
		removeAttribute(KEY_DATA);
	}
	/**
	 * @return totalRecords
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}


}
