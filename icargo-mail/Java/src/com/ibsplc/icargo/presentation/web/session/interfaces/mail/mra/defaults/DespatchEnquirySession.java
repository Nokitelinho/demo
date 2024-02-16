/*
 * DespatchEnquirySession.java Created on Feb 12, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailFlownVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;


/**
 * @author A-3229
 *
 */
public interface DespatchEnquirySession extends ScreenSession {
	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * To remove oneTimeVOs from session
	 */
	public void removeOneTimeVOs();
	
	/**
	 *
	 * @return DespatchVO
	 */
	
	public DespatchVO getDespatchDetails();
	/**
	 *
	 * @param DespatchVO
	 */
	public void setDespatchDetails(DespatchVO despatchVO);
	/**
	 *
	 * To remove DespatchVOs from session
	 */
	public void removeDespatchDetails();
	/**
	 *
	 * @return Collection<MailFlownVO>
	 */
	public Collection<MailFlownVO> getMailFlownDetails();
	/**
	 *
	 * @param Collection<MailFlownVO>
	 */
	public void setMailFlownDetails(Collection<MailFlownVO> mailFlownDetails);
	/**
	 *
	 * To remove MailFlownVOs from session
	 */
	public void removeMailFlownDetails();
	
	/**
	 *
	 * @return Collection<MailGPABillingVO>
	 *//*
	public Collection<MailGPABillingVO> getMailGPABillingDetails();
	*//**
	 *
	 * @param Collection<MailGPABillingVO>
	 *//*
	public void setMailGPABillingDetails(Collection<MailGPABillingVO> gpaBillingDetails);
	*//**
	 *
	 * To remove MailGPABillingVOs from session
	 *//*
	public void removeMailGPABillingDetails();
	*/
	
}

