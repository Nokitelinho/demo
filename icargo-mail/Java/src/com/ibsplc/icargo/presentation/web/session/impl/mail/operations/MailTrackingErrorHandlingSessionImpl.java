package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailTrackingErrorHandlingSession;


public class MailTrackingErrorHandlingSessionImpl extends AbstractScreenSession 
implements MailTrackingErrorHandlingSession{
	
	
	private static final String SCREEN_ID = "mailtracking.defaults.errorhandligpopup";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMEHNI = "oneTimeHni";
	private static final String KEY_ONETIMERI = "oneTimeRi";
	private static final String KEY_SCANNEDDETAILS="scannedDetails";
	private static final String KEY_TXNID="transactionid";
	private static final String KEY_TXNIDS="transactionids";
	private static final String KEY_ONETIMEMAILCMPCODE = "oneTimeCompanyCode";
	private static final String KEY_SELECTED_SCANNEDDETAILS="selectedScannedDetails";
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}

	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}

	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}
	public void setScannedDetails(MailUploadVO mailUploadVO) {
		setAttribute(KEY_SCANNEDDETAILS,mailUploadVO);
	}

	/**
     * This method returns the ScannedMailDetailsVO vos
     * @return KEY_SCANNEDDETAILS - Collection<ScannedMailDetailsVO>
     */
	public MailUploadVO getScannedDetails() {
		return (MailUploadVO)getAttribute(KEY_SCANNEDDETAILS);
	}
	
	public void setTxnid(String txnid){
		setAttribute(KEY_TXNID,txnid);
	}
		
	public String getTxnid(){
		return (String)getAttribute(KEY_TXNID);
	}
	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHni() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	/**
     * This method is used to set onetime values for HNI to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeHni(Collection<OneTimeVO> oneTimeHni) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHni);
	}
	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRi() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERI);
	}
	/**
     * This method is used to set onetime values for RI to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeRi(Collection<OneTimeVO> oneTimeRi) {
		setAttribute(KEY_ONETIMERI,(ArrayList<OneTimeVO>)oneTimeRi);
	}
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCompanyCode() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILCMPCODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeCompanyCode(Collection<OneTimeVO> oneTimeCompanyCode) {
		setAttribute(KEY_ONETIMEMAILCMPCODE,(ArrayList<OneTimeVO>)oneTimeCompanyCode);
	}
	
	
	public void setTxnids(String[] txnids){
		setAttribute(KEY_TXNIDS,txnids);
	}
		
	public String[] getTxnids(){
		return (String[])getAttribute(KEY_TXNIDS);
	}
	
	
	
}
