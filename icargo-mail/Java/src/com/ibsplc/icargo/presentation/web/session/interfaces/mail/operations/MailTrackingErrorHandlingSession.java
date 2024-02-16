package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


public interface MailTrackingErrorHandlingSession extends ScreenSession {
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public MailUploadVO getScannedDetails();

	public void setScannedDetails(MailUploadVO mailUploadVO);
	
	public void setTxnid(String txnid);
	
	public String getTxnid();
	/**
	 * This method returns the onetime vos for HNI
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getOneTimeHni();
	/**
     * This method is used to set onetime values for HNI to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeHni(Collection<OneTimeVO> oneTimeHni);
	/**
	 * This method returns the onetime vos for RI
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getOneTimeRi();
	/**
     * This method is used to set onetime values for RI to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeRi(Collection<OneTimeVO> oneTimeRi);
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeCompanyCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCompanyCode();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCompanyCode - Collection<OneTimeVO>
     */
	public void setOneTimeCompanyCode(Collection<OneTimeVO> oneTimeCompanyCode);
	
	
public void setTxnids(String[] txnid);
	
	public String[] getTxnids();
	
	
}
