/*
 * MaintainULDSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.MaintainULDSession;


/**
 * @author A-1347
 * 
 */
public class MaintainULDSessionImpl extends AbstractScreenSession implements MaintainULDSession {
	

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.ux.maintainuld";
	private static final String KEY_LOCATION = "LocationVO";

	
	public String getScreenID() {
		return SCREENID;
	}

	public String getModuleName() {
		return MODULE;
	}
	public Collection<ULDAirportLocationVO> getFacilityTypes() {
		return (Collection<ULDAirportLocationVO>) getAttribute(KEY_LOCATION);
	}

	public void setFacilityTypes(
			Collection<ULDAirportLocationVO> uldStockConfigVOs) {
		setAttribute(KEY_LOCATION,(ArrayList<ULDAirportLocationVO>) uldStockConfigVOs);
	}

	public void removeFacilityTypes() {
		removeAttribute(KEY_LOCATION);
	}
	
}
