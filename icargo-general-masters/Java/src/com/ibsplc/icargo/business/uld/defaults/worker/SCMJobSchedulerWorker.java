package com.ibsplc.icargo.business.uld.defaults.worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.uld.defaults.vo.SCMJobSchedulerVO;
import com.ibsplc.icargo.framework.feature.Proxy;
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

@Module("uld")
@SubModule("defaults")
public class SCMJobSchedulerWorker extends RequestWorker {
	 private Log logs = LogFactory.getLogger( "ULD" );
	 
	 @Override
		public void execute(WorkerContext context) throws SystemException { 
			logs.entering("SCMJobSchedulerWorker", "execute");

			SCMJobSchedulerVO scmJobSchedulerVO = context.getJobScheduleVO();
			String companyCode = scmJobSchedulerVO.getCompanyCode();
			String airportGroup = scmJobSchedulerVO.getAirportGroup();
			String noOfDays = scmJobSchedulerVO.getNoOfDays();
			
			Collection<String> detailsForSCMJob =
					findUsersInGeneralMasterUserGroup(airportGroup);
			
			Collection<String>  airportCodes = null;
			
			if(Objects.nonNull(detailsForSCMJob) && !detailsForSCMJob.isEmpty()){
				airportCodes = despatchRequest("findAirportsforSCMJob", companyCode,detailsForSCMJob,noOfDays);
			}
			if(Objects.nonNull(airportCodes) && !airportCodes.isEmpty()){
			despatchRequest("sendSCMReminderNotifications", airportCodes);
			}
			logs.exiting("SCMJobSchedulerWorker", "execute");	

		}
	 
	private Collection<String> findUsersInGeneralMasterUserGroup(String airportGroupName) throws SystemException{
		Collection<String>  airportGroupIdentity = new ArrayList<>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		generalMasterGroupFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		generalMasterGroupFilterVO.setGroupType("ARPGRP");
		generalMasterGroupFilterVO.setGroupCategory("SCMMISARP");
		generalMasterGroupFilterVO.setGroupName(airportGroupName);
		
		try {
			airportGroupIdentity = Proxy.getInstance().get(SharedGeneralMasterGroupingProxy.class).findEntitiesForGroups(generalMasterGroupFilterVO);
		} catch (SystemException e) {
			throw new SystemException(e.getMessage(),e);
		}
		return airportGroupIdentity;
	}
	
	/**
	 * 
	 * @return JobScheduleVO
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new SCMJobSchedulerVO();
	}

}

