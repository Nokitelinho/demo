/*
 * ListFormOneSession.java Created on Nov 07, 2006 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-3434
 *
 */

public interface ListFormOneSession  extends ScreenSession {
	
	/**
	 * @return Page<FormOneVO>
	 */
	public Page<FormOneVO>  getFormOneVOs();
	
	/**
	 * @param formOneVOs
	 */
	public void setFormOneVOs(Page<FormOneVO> formOneVOs) ;
	
	/**
	 * Removes formOneVOs
	 */
	public void removeFormOneVOs();
	
	
}