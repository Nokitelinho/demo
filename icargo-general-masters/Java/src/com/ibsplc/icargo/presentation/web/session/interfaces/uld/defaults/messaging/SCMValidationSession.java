/*
 * SCMValidationSession.java Created on Jan 2, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3459
 *
 */
public interface SCMValidationSession extends ScreenSession{

	/**
	 *
	 * @return screenID
	 */
	public String getScreenID();
	/**
	 *
	 * @return modulename
	 */
	public String getModuleName();
	 /**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getFacilityType();
	/**
	 * Methods for setting status
	 */
	public void setFacilityType(Collection<OneTimeVO> facilityType);

	 /**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getScmStatus();
	/**
	 * Methods for setting status
	 */
	public void setScmStatus(Collection<OneTimeVO> scmStatus);

	/**
	 *
	 * @return
	 */
	public Page<SCMValidationVO> getSCMValidationVOs();
	/**
	 *
	 * @param scmValidationVO
	 */
	public void setSCMValidationVOs(Page<SCMValidationVO>scmValidationVO);

	/**
	 *
	 */
	public void removeSCMValidationVOs();

}
