package com.ibsplc.icargo.business.mail.mra.worker;

import static org.mockito.Mockito.spy;

import org.junit.Test;


import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;

public class USPSConcurrentInvoicProcessingWorkerTest extends AbstractFeatureTest {

	private USPSConcurrentInvoicProcessingWorker worker;
	private WorkerContext workerContext;
	
	
	@Override
	public void setup() throws Exception {
		worker= spy(USPSConcurrentInvoicProcessingWorker.class);
		workerContext=new WorkerContext();
		mockDespatchRequest(USPSConcurrentInvoicProcessingWorker.class);
		
	}
	@Test
	public void validUSPSConcurrentInvoicProcessingWorker() throws Exception {
		worker.instantiateJobScheduleVO();
		worker.execute(workerContext);
	}
}

