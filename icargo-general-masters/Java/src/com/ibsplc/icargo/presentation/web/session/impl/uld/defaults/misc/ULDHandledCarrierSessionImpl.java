/*
 * ULDHandledCarrierSessionImpl.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHandledCarrierSession;

/**
 * @author A-2883
 *
 */

public class ULDHandledCarrierSessionImpl extends AbstractScreenSession
implements ULDHandledCarrierSession{

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.misc.handledcarriersetup";

	private static final String CARRIERVO = "uldHandledCarrierVO";
	
	
	
	public String getModuleName() {
		
		return MODULE;
	}

	
	public String getScreenID() {
	
		return SCREENID;
	}

	
	public Collection<ULDHandledCarrierVO>  getULDHandledCarrierVO(){
		return (Collection<ULDHandledCarrierVO>)getAttribute(CARRIERVO);
	}
	public void setULDHandledCarrierVO(Collection<ULDHandledCarrierVO> uldHandledCarrierVO){
		setAttribute(CARRIERVO, (ArrayList<ULDHandledCarrierVO>)uldHandledCarrierVO);
	}
	
	
	
	
	
	

}
