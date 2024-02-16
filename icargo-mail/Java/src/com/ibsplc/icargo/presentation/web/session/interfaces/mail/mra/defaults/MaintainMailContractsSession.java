/*
 * MaintainMailContractsSession.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


	/**
	 * @author A-2408
	 *
	 */
	public interface MaintainMailContractsSession extends ScreenSession {

	
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
	public MailContractVO getMailContractVO();
	/**
	 * @param vo
	 */
	public void setMailContractVO(MailContractVO vo);
	/**
	 * 
	 */
	public void removeMailContractVO();
	
	/**
	 * @return
	 */
	public Collection<MailContractDetailsVO> getMailContractDetails();
	/**
	 * @param vos
	 */
	public void setMailContractDetails(Collection<MailContractDetailsVO> vos);
	/**
	 * 
	 */
	public void removeMailContractDetails();
	

	
}
