package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.Test;

public class ResditBuilderWorkerTest extends AbstractFeatureTest  {

	private ResditBuilderWorker worker;
	private WorkerContext workerContext;
	private Collection<ResditEventVO> resditEvents;
	private ResditEventVO resditEventVO;
	private SharedDefaultsProxy sharedDefaultsProxy;
	
	
	@Override
	public void setup() throws Exception {
		worker = spy(ResditBuilderWorker.class);
		workerContext = new WorkerContext();
		mockDespatchRequest(ResditBuilderWorker.class);
		resditEventVO=new ResditEventVO();
		resditEventVO.setEventPort("54");
		resditEventVO.setPaCode("FR001");
		resditEventVO.setEventPort("ABC");
		resditEventVO.setCarrierCode("Av");
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
	}
	

	@Test
	public void shouldExecuteResditBuilderWorker() throws Exception {
		resditEvents =new ArrayList<>();
		resditEvents.add(resditEventVO);
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		ArrayList<String> systemParams = new ArrayList<>();
		systemParams.add(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		HashMap<String, String> systemParamMap =new HashMap<>();
		systemParamMap.put(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS, "HDLCAR,POACOD,RDTEVT,STN");
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParams);
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	
	@Test
	public void shouldExecuteResditBuilderWorkerNoSTNGrouping() throws Exception {
		resditEvents =new ArrayList<>();
		resditEvents.add(resditEventVO);
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		ArrayList<String> systemParameters = new ArrayList<>();
		ArrayList<String> systemParams = new ArrayList<>();
		systemParams.add(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		HashMap<String, String> systemParamMap =new HashMap<>();
		systemParamMap.put(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS, "HDLCAR,POACOD,RDTEVT");
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParams);
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	
	@Test
	public void shouldExecuteResditBuilderWorkerNoPOACODAndHDLCARAndRDTEVTGrouping() throws Exception {
		resditEvents =new ArrayList<>();
		resditEvents.add(resditEventVO);
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		ArrayList<String> systemParams = new ArrayList<>();
		systemParams.add(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		HashMap<String, String> systemParamMap =new HashMap<>();
		systemParamMap.put(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS, "STN");
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParams);
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	@Test
	public void shouldExecuteResditBuilderWorkerResditGroupDisable() throws Exception {
		resditEvents =new ArrayList<>();
		resditEvents.add(resditEventVO);
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		ArrayList<String> systemParams = new ArrayList<>();
		systemParams.add(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		HashMap<String, String> systemParamMap =new HashMap<>();
		systemParamMap.put(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS, "HDLCAR,POACOD,RDTEVT,STN");
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParams);
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	
	@Test
	public void shouldExecuteResditBuilderWorkerThrowSystemException() throws Exception {
		resditEvents =new ArrayList<>();
		resditEvents.add(resditEventVO);
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		ArrayList<String> systemParams = new ArrayList<>();
		systemParams.add(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParams);
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	
	
	@Test
	public void shouldExecuteResditBuilderWorkerResditEventIsNull() throws Exception {
		doReturn(null).when(worker).despatchcheckForResditEventsRequest(any());
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
	@Test
	public void shouldExecuteResditBuilderWorkerResditEventIsEmpty() throws Exception {
		resditEvents =new ArrayList<>();
		doReturn(resditEvents).when(worker).despatchcheckForResditEventsRequest(any());
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
		
	}
}
