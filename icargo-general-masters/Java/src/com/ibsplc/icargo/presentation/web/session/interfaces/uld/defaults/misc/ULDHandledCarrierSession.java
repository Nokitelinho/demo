/*
 * ULDHandledCarrierSession.java Created on Dec 05, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2883
 *
 */
public interface ULDHandledCarrierSession extends ScreenSession{
	
	
	 /**
	 * Methods for getting VO
	 */
	public Collection<ULDHandledCarrierVO> getULDHandledCarrierVO();
	/**
	 * Methods for setting VO
	 */
	public void setULDHandledCarrierVO(Collection<ULDHandledCarrierVO> handledcarriervo);

}
