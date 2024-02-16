package com.ibsplc.icargo.business.mail.mra.worker;

import static org.mockito.Matchers.any;
import java.util.Objects;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveJobSchedulerVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;

public class USPSIncentiveCalculationWorkerTest extends AbstractFeatureTest {
	private USPSIncentiveCalculationWorker worker;
	private WorkerContext workerContext;
	private USPSIncentiveJobSchedulerVO jobSchedulerVO;
	USPSIncentiveVO uspsIncentiveVO;
	ArgumentCaptor<USPSIncentiveVO> captor;
	@Override
	public void setup() throws Exception {
		worker= spy(USPSIncentiveCalculationWorker.class);
		workerContext=new WorkerContext();
		jobSchedulerVO=new USPSIncentiveJobSchedulerVO();
		mockDespatchRequest(USPSInvoicProcessingWorker.class);
		
		uspsIncentiveVO =new USPSIncentiveVO();
	}
	@Test
	public void validUSPSIncentiveCalculationWorker() throws Exception {
		jobSchedulerVO.setExcludeAmot("Y");	
		
		
		worker.instantiateJobScheduleVO();
		workerContext.setJobScheduleVO(jobSchedulerVO);
		worker.execute(workerContext);
		verify(worker,times(1)).setExecutionSuccess(true);
	}
}
