/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.ImportMailstoMRAWorker.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Oct 8, 2015
 *
 *  Copyright 2015 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker;

import java.util.ArrayList;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.flown.vo.ImportFlownMailJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.ImportMailstoMRAWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Oct 8, 2015	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class ImportMailstoMRAWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS ="ImportMailstoMRAWorker";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void execute(WorkerContext context) throws SystemException {
		log.entering(CLASS, "execute");
		ImportFlownMailJobScheduleVO jobscheduleVO =
				(ImportFlownMailJobScheduleVO)context.getJobScheduleVO();
		//Added by A-7794 as part of ICRD-232299
		String importEnabled;
		try {
			importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if(importEnabled!=null && importEnabled.contains("D")){
				despatchRequest("forceImportScannedMailbags", jobscheduleVO.getCompanyCode(),jobscheduleVO.getStartDate(),jobscheduleVO.getEndDate());
			}
		} catch (ProxyException e) {
		}
		
			despatchRequest("importArrivedMailstoMRA", jobscheduleVO.getCompanyCode());
		log.exiting(CLASS, "execute");
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ImportFlownMailJobScheduleVO();
	}
/***
 * @author A-7794
 * @param syspar
 * @return
 * @throws SystemException
 * @throws ProxyException 
 */
	private String findSystemParameterValue(String syspar)
			throws SystemException, ProxyException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
}
