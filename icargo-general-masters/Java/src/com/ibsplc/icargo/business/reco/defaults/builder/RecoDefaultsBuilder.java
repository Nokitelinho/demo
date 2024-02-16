/**
 * RecoDefaultsBuilder.java Created on Oct 31, 2014
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.builder;

import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RECORefreshJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.SchedulerAgent;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class RecoDefaultsBuilder.
 * 
 * @author A-5153
 */
public class RecoDefaultsBuilder extends AbstractActionBuilder {

	/** The Constant log. */
	private static final Log log = LogFactory.getLogger("RECO DEFAULTS");

	/**
	 * Update embargo view for embargo creation,modification
	 * 
	 * @author A-5153
	 * @param rulesVO
	 * @throws SystemException
	 */
	public void updateEmbargoView(EmbargoRulesVO rulesVO)
			throws SystemException {
		log.entering("RecoDefaultsBuilder", "updateEmbargoView");
			RECORefreshJobScheduleVO jobScheduleVO = new RECORefreshJobScheduleVO(); 
		  jobScheduleVO.setCompanyCode(rulesVO.getCompanyCode()); 
	      jobScheduleVO.setFrequency("0"); 
	      jobScheduleVO.setJobName("REC_VW_REFRESH"); 
	      jobScheduleVO.setRepeatCount(0); 
	      jobScheduleVO.setRepeatStrategy("ONE-TIME"); 
	      LocalDate startTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true); 
	      jobScheduleVO.setStartTime(startTime.addMinutes(2)); 
	      LocalDate endTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true); 
	      jobScheduleVO.setEndTime(endTime.addMinutes(20));
	       try 
	      { 
	        SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO); 
	      } catch (JobSchedulerException e) { 
	        throw new SystemException( 
	          "CON005", e); 
	      } 
		//new EmbargoRulesController().updateEmbargoView();
		log.exiting("RecoDefaultsBuilder", "updateEmbargoView");
	}
	/**
	 * 
	 * @param embargoDetailsVOs
	 * @throws SystemException
	 */
	public void updateEmbargoViewOnStatusUpdate(Collection<EmbargoDetailsVO> embargoDetailsVOs)
			throws SystemException {
		log.entering("RecoDefaultsBuilder", "updateEmbargoView");
		if(embargoDetailsVOs !=null){
			RECORefreshJobScheduleVO jobScheduleVO = new RECORefreshJobScheduleVO(); 
			jobScheduleVO.setCompanyCode(embargoDetailsVOs.iterator().next().getCompanyCode()); 
			jobScheduleVO.setFrequency("0"); 
			jobScheduleVO.setJobName("REC_VW_REFRESH"); 
			jobScheduleVO.setRepeatCount(0); 
			jobScheduleVO.setRepeatStrategy("ONE-TIME"); 
			LocalDate startTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true); 
			jobScheduleVO.setStartTime(startTime.addMinutes(2)); 
			LocalDate endTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true); 
			jobScheduleVO.setEndTime(endTime.addMinutes(20));
			try 
			{ 
				SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO); 
			} catch (JobSchedulerException e) { 
				throw new SystemException( 
						"CON005", e); 
			} 
		}
		log.exiting("RecoDefaultsBuilder", "updateEmbargoView");
	}

}