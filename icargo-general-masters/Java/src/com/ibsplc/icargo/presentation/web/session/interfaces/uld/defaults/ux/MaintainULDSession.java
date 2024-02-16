/*
 * MaintainULDSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;



public interface MaintainULDSession extends ScreenSession {
	 
  	public Collection<ULDAirportLocationVO> getFacilityTypes();
	public void setFacilityTypes(Collection<ULDAirportLocationVO> uldAirportLocationVOs);

}
