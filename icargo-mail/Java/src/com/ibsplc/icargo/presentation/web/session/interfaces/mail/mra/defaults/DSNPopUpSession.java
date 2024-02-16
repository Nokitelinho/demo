/* DSNPopUpSession.java Created on AUG 28,2008
 * 
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2391
 * 
 */
public interface DSNPopUpSession extends ScreenSession {
	/**
	 * 
	 * @return Page<DSNPopUpVO>
	 */
	public Page<DSNPopUpVO> getDespatchDetails();

	/**
	 * 
	 * @param dsnPopUpVos
	 */
	public void setDespatchDetails(Page<DSNPopUpVO> dsnPopUpVos);

	/**
	 * 
	 * 
	 */
	public void removeDespatchDetails();
	/**
	 * 
	 * @return DSNPopUpVO
	 */
	public DSNPopUpVO getSelectedDespatchDetails();

	/**
	 * 
	 * @param dsnPopUpVos
	 */
	public void setSelectedDespatchDetails(DSNPopUpVO dsnPopUpVos);

	/**
	 * 
	 * 
	 */
	public void removeSelectedDespatchDetails();
	
	/**
	 * 
	 * @return DSNPopUpFilterVO
	 */
	public DSNPopUpFilterVO getDsnPopUpFilterDetails();

	/**
	 * 
	 * @param dsnPopUpFilterVOs
	 */
	public void setDsnPopUpFilterDetails(DSNPopUpFilterVO dsnPopUpFilterVO);

	/**
	 * 
	 * 
	 */
	public void removeDsnPopUpFilterDetails();
	
	

}