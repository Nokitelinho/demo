/*
 * ReportingPeriod.java Created on May 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2518
 * 
 */
public class ReportingPeriod {
	private static final String CLASS_NAME = "ReportingPeriod";

	private static final String MODULE_NAME = "mail.mra.defaults";

	/**
	 * default constructor
	 * 
	 */
	public ReportingPeriod() {
	}

	/**
	 * @author a-2518
	 * @param reportingPeriodFilterVo
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ReportingPeriodVO> validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "validateReportingPeriod");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "validateReportingPeriod");
			return mraDefaultsDao
					.validateReportingPeriod(reportingPeriodFilterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
