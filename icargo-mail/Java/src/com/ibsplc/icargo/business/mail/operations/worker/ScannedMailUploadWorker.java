
/*
 * ScannedMailUploadWorker.java Created on Aug 20, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  Worker class for MailHHT-Upload
 * @author A-5526
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 	Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		Aug 20, 2015		a-5526		Created
 */
@Module("mail")
@SubModule("operations")
public class ScannedMailUploadWorker extends RequestWorker {


	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 */
	@Override
	public void execute(WorkerContext workerContext) throws SystemException {
		log.entering("ScannedMailUploadWorker", "execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		log.entering("fetchMailScannedDetails", "execute"); 
		
		int uploadRecordCount = findUploadRecordCount();
		String realTimeuploadrequired=despatchRequest("findRealTimeuploadrequired",MailConstantsVO.REALTIME_UPLOAD_REQUIRED);
		if(MailConstantsVO.FLAG_NO.equals(realTimeuploadrequired)){
		Collection<MailScanDetailVO> mailScanDetailVOs =despatchRequest("fetchMailScannedDetails", logonAttributes.getCompanyCode(),uploadRecordCount);
		log.exiting("fetchMailScannedDetails", "execute");
		
		
		
//		log.log(Log.FINEST, "mailScanDetailVOs ", mailScanDetailVOs);
		if(mailScanDetailVOs != null && mailScanDetailVOs.size() > 0) {
			
				despatchRequest("saveMailUploadDetailsFromJob", mailScanDetailVOs,null);
		}
		}
		log.exiting("ScannedMailUploadWorker", "execute");
	}
	
	/**
	 * Method to fetch the upload record count from system parameter value
	 * @return
	 * @throws SystemException 
	 */
	private int findUploadRecordCount() throws SystemException {
		log.entering("findUploadRecordCount", "execute");
		int uploadCount = 0;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.UPLOAD_RECORDS_COUNT);
		HashMap<String, String> systemParameterMap = null;
		systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			uploadCount = Integer.parseInt(systemParameterMap
					.get(MailConstantsVO.UPLOAD_RECORDS_COUNT));
		}
		log.log(Log.FINE, " uploadCount :", uploadCount);
		
		log.exiting("findUploadRecordCount", "execute");
		return uploadCount;
	}


	
	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new JobScheduleVO(){

			@Override
			public int getPropertyCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getIndex(String arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getValue(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setValue(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

}
