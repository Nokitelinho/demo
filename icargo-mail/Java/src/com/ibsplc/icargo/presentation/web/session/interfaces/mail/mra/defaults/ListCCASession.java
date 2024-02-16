/*
 * ListCCASession.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2391
 * 
 */
public interface ListCCASession extends ScreenSession {
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
	 * 
	 * @return
	 */
	public Page<CCAdetailsVO> getCCADetailsVOs();

	/**
	 * 
	 * @param ccaDetails
	 */
	public void setCCADetailsVOs(Page<CCAdetailsVO> ccaDetails);

	/**
	 * @return Returns the CCAFilterVO.
	 */
	ListCCAFilterVO getCCAFilterVO();

	/**
	 * @param CCAFilterVO
	 * The CCAFilterVO to set.
	 */
	void setCCAFilterVO(ListCCAFilterVO listFilterVO);

	/**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus
	 * The listStatus to set.
	 */
	void setListStatus(String listStatus);
	
	/**
	 * @return Returns the listStatus.
	 */
	String getCloseStatus();

	/**
	 * @param listStatus
	 * The listStatus to set.
	 */
	void setCloseStatus(String closeStatus);
	
	public abstract Integer getTotalRecords(); //added by A-5201 for CR ICRD-21098

	public abstract void setTotalRecords(int i); //added by A-5201 for CR ICRD-21098

	/**
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getSystemparametres();
	/**
	 * 
	 * @param sysparameters sysparameters
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters);
}
