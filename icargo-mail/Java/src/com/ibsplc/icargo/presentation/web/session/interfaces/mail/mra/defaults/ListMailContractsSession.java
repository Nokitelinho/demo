/*
 * ListMailContractsSession.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1946
 *
 */
public interface ListMailContractsSession extends ScreenSession {

	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	/**
	 * @return
	 */
	public Collection<MailContractVO> getMailContractVOs();
	/**
	 * 
	 * @param exceptionInInvoiceVOs
	 */
	public void setMailContractVOs(Collection<MailContractVO> mailContractVOs);
	/**
	 * remove
	 */
	public void removeMailContractVOs();
	
	/**
	 * @return MailContractFilterVO
	 */
	public MailContractFilterVO getMailContractFilterVO();
	/**
	 * 
	 * @param MailContractFilterVO
	 */
	public void setMailContractFilterVO(MailContractFilterVO mailContractVO);
	/**
	 * remove MailContractFilterVO
	 */
	public void removMailContractFilterVO();	

}

