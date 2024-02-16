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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;

public class USPSInvoicProcessingWorkerTest extends AbstractFeatureTest {

	private USPSInvoicProcessingWorker worker;
	private WorkerContext workerContext;
	private InvoicJobScheduleVO jobSchedulerVO;
	InvoicVO invoicVO;
	ArgumentCaptor<InvoicVO> captor;
	
	@Override
	public void setup() throws Exception {
		worker= spy(USPSInvoicProcessingWorker.class);
		workerContext=new WorkerContext();
		jobSchedulerVO=new InvoicJobScheduleVO();
		mockDespatchRequest(USPSInvoicProcessingWorker.class);
		
		invoicVO =new InvoicVO();
	}
	
	@Test
	public void validUSPSInvoicProcessingWorker() throws Exception {
		jobSchedulerVO.setCompanyCode("AV");	
		jobSchedulerVO.setStartBatchnum(1);	
		jobSchedulerVO.setEndBatchnum(100);	
		//invoicVO.setFileName("");	
		//invoicVO.setPoaCode("");	
		//invoicVO.setTxnCode(invoicJobScheduleVO.getTxnCode());	
		//invoicVO.setActionCode(invoicJobScheduleVO.getActionCode());	
		worker.instantiateJobScheduleVO();
		workerContext.setJobScheduleVO(jobSchedulerVO);
		worker.execute(workerContext);
		captor = ArgumentCaptor.forClass(InvoicVO.class);
		verify(worker,times(1)).setExecutionSuccess(true);
	}
	

}
