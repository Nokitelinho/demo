package com.ibsplc.icargo.business.mail.mra.worker;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProvisionalRateJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;

public class CalculateUpfrontRateWorkerTest extends AbstractFeatureTest {
	
	private CalculateUpfrontRateWorker worker;
	private WorkerContext workerContext;
	private ProvisionalRateJobScheduleVO jobSchedulerVO;
	//InvoicVO invoicVO;
	ArgumentCaptor<ProvisionalRateJobScheduleVO> captor;

	@Override
	public void setup() throws Exception {
		worker= spy(CalculateUpfrontRateWorker.class);
		workerContext=new WorkerContext();
		jobSchedulerVO=new ProvisionalRateJobScheduleVO();
		mockDespatchRequest(USPSInvoicProcessingWorker.class);
		
		//invoicVO =new InvoicVO();
	}
	
	@Test
	public void validUSPSInvoicProcessingWorker() throws Exception {
		jobSchedulerVO.setnoOfRecords(1000l);
		worker.instantiateJobScheduleVO();
		workerContext.setJobScheduleVO(jobSchedulerVO);
		worker.execute(workerContext);
		captor = ArgumentCaptor.forClass(ProvisionalRateJobScheduleVO.class);
		verify(worker,times(1)).setExecutionSuccess(true);
	}

}
