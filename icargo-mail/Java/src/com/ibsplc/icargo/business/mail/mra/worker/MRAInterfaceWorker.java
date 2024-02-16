
/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.MRAInterfaceWorker.java
 *
 *	Created by	:	A-6245
 *	Created on	:	18-08-2016
 * 	This software is the proprietary information of IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.MRAInterfaceWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6245	:	18-08-2016	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class MRAInterfaceWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAIL MRA");
	
	@Override
	public void execute(WorkerContext arg0) throws SystemException {
		log.entering("MRAInterfaceWorker","execute");
		LocalDate uploadTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		String uploadTimeStr=uploadTime.toDisplayFormat("ddMMyyyyHHmmss");
		despatchRequest("generateInterfaceFile", uploadTime,uploadTimeStr);
		log.exiting("MRAInterfaceWorker", "execute");
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}

}
