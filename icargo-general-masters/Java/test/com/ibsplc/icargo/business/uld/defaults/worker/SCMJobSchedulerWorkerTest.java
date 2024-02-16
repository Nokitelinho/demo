package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.SCMJobSchedulerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class SCMJobSchedulerWorkerTest extends AbstractFeatureTest {
	private SCMJobSchedulerWorker scmJobSchedulerWorker; 
	private WorkerContext workerContext;
	private SCMJobSchedulerVO scmJobSchedulerVO;
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy ;
	private static final String FROMJOB="findAirportsforSCMJob";

	@Override
	public void setup() throws Exception {
		workerContext=new WorkerContext();
		scmJobSchedulerWorker=spy(SCMJobSchedulerWorker.class);
		scmJobSchedulerVO=new SCMJobSchedulerVO();
		workerContext.setJobScheduleVO(scmJobSchedulerVO);
		sharedGeneralMasterGroupingProxy = mockProxy(SharedGeneralMasterGroupingProxy.class);
		mockDespatchRequest(SCMJobSchedulerWorker.class);
	} 
	
	@Test
	public void shouldVerifySCMJobSchedulerWorkerisTriggered() throws SystemException{

		Collection<String> handlingUsersFromGroup = new ArrayList<>();
		handlingUsersFromGroup.add("ICOADMIN");
		scmJobSchedulerVO.setCompanyCode("SQ");
		scmJobSchedulerVO.setNoOfDays("7");
		scmJobSchedulerVO.setAirportGroup("TEST");
		workerContext.setJobScheduleVO(scmJobSchedulerVO);
		scmJobSchedulerWorker.execute(workerContext);
		doReturn(handlingUsersFromGroup).when(sharedGeneralMasterGroupingProxy).findEntitiesForGroups(any(GeneralMasterGroupFilterVO.class));
		assertTrue(Objects.nonNull(scmJobSchedulerWorker));
	}
	
	@Test
	public void shouldVerifySCMJobSchedulerWorkerisTriggeredIsNull() throws SystemException{

		Collection<String> handlingUsersFromGroup = new ArrayList<>();
		scmJobSchedulerVO.setCompanyCode("SQ");
		scmJobSchedulerVO.setNoOfDays("7");
		scmJobSchedulerVO.setAirportGroup("TEST");
		workerContext.setJobScheduleVO(scmJobSchedulerVO);
		scmJobSchedulerWorker.execute(workerContext);
		handlingUsersFromGroup = doReturn(null).when(sharedGeneralMasterGroupingProxy).findEntitiesForGroups(any(GeneralMasterGroupFilterVO.class));
		assertFalse(Objects.nonNull(handlingUsersFromGroup));
	}
	
	@Test
	public void shouldVerifySCMJobSchedulerIsReturningValues() throws SystemException{

		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DXB");
		airportCodes.add("CDG");
		scmJobSchedulerVO.setCompanyCode("SQ");
		scmJobSchedulerVO.setNoOfDays("7");
		scmJobSchedulerVO.setAirportGroup("TEST");
		workerContext.setJobScheduleVO(scmJobSchedulerVO);
		scmJobSchedulerWorker.execute(workerContext);
		assertThat(Objects.nonNull(scmJobSchedulerVO), is(true));
	}
	
	@Test
	public void shouldInvokeSCMJobSchedulerWorkerExecute() throws SystemException{
		scmJobSchedulerVO.setCompanyCode("SQ");
		scmJobSchedulerVO.setNoOfDays("7");
		scmJobSchedulerVO.setAirportGroup("TEST");
		workerContext.setJobScheduleVO(scmJobSchedulerVO);
		scmJobSchedulerWorker.execute(workerContext);
		assertThat(Objects.nonNull(scmJobSchedulerVO), is(true));
		verify(scmJobSchedulerWorker, atLeast(1)).execute(workerContext);
	}
	

	@Test
	public void jobScheduleVOShouldNotBeNullOnceInstantiateJobScheduleVOInvoked(){
		
		JobScheduleVO jobScheduleVO = null;
		jobScheduleVO = scmJobSchedulerWorker.instantiateJobScheduleVO();
		assertTrue(Objects.nonNull(jobScheduleVO));
	}
}


