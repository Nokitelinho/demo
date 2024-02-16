/*
 * AccountPostingJobSchedulerWorker.java Created on Jan 14, 2020
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker.aa;


import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountPostingJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author U-1393
 * Worker class implemented for daily night job to generate SAP files 
 * for the function point – MRA
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 	  Date 				Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Jan 14, 2020		U-1393		Created
 */
@Module("mail")
@SubModule("mra")
public class AccountPostingJobSchedulerWorker extends RequestWorker{

	private Log log = LogFactory.getLogger( "MAIL MRA" );
	private static final String CLASS_NAME = "AccountPostingJobSchedulerWorker";
	
	public void execute(WorkerContext context) throws SystemException {
		log.entering(CLASS_NAME,"execute");
		SAPInterfaceFilterVO interfaceFilterVO = new SAPInterfaceFilterVO();
		AccountPostingJobScheduleVO accountPostingJobScheduleVO =
			(AccountPostingJobScheduleVO)context.getJobScheduleVO();
		interfaceFilterVO.setCompanyCode(accountPostingJobScheduleVO.getAccountingInterface());
		interfaceFilterVO.setSubsystem(accountPostingJobScheduleVO.getSubSystem());
		interfaceFilterVO.setFileType(accountPostingJobScheduleVO.getFileType());
		interfaceFilterVO.setRegenerateFlag("N");
		interfaceFilterVO.setJob(true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(logonAttributes.getStationCode(), Location.STN, true);
		int interfacingFrequency = 0;
		if(accountPostingJobScheduleVO.getInterfacingFrequency()!= null && accountPostingJobScheduleVO.getInterfacingFrequency().trim().length() >0){
			interfacingFrequency = (Integer.parseInt(accountPostingJobScheduleVO.getInterfacingFrequency()));
		}
		LocalDate toDate = new LocalDate(currentDate.addDays(-interfacingFrequency),true);
		interfaceFilterVO.setToDate(toDate);
		LocalDate fromDate = new LocalDate(currentDate.addDays(-31),true);
		interfaceFilterVO.setFromDate(fromDate);
		log.log(Log.INFO, "toDate:"+interfaceFilterVO.getToDate());
		log.exiting(CLASS_NAME,"execute");
		despatchRequest("generateSAPInterfaceFile",interfaceFilterVO);
	}
	
	/**
	 *
	 * @return JobScheduleVO
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		log.entering(CLASS_NAME,"instantiateJobScheduleVO");
		log.exiting(CLASS_NAME,"instantiateJobScheduleVO");
		return new AccountPostingJobScheduleVO();
	}
}
