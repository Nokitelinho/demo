package com.ibsplc.icargo.business.mail.operations.worker;

import static org.mockito.Mockito.spy; 

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataJobScheduleVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class PublishMailOperationDataWorkerTest extends AbstractFeatureTest {

	private PublishMailOperationDataWorker publishMailOperationDataWorker;
	private MailOperationDataJobScheduleVO mailOperationDataJobScheduleVO;
	private WorkerContext context;
	
	

	@Override
	public void setup() throws Exception {
		publishMailOperationDataWorker = spy(new PublishMailOperationDataWorker());
		mailOperationDataJobScheduleVO = new MailOperationDataJobScheduleVO();
		mailOperationDataJobScheduleVO.setCarrierCode("QF");
		mailOperationDataJobScheduleVO.setMailbagOrigin("SYD");
		mailOperationDataJobScheduleVO.setNoOfDaysToConsider(1);
		mailOperationDataJobScheduleVO.setPostalAuthorityCode("AU101");
		mailOperationDataJobScheduleVO.setTriggerPoints("DLV");
		context = new WorkerContext();
		context.setJobScheduleVO(mailOperationDataJobScheduleVO);
		mockDespatchRequest(PublishMailOperationDataWorker.class);
	}
	
	@Test
	public void test_Execute() throws SystemException {
		publishMailOperationDataWorker.execute(context);
	}

	@Test
	public void test_InstantiateJobScheduleVO() throws SystemException {
		publishMailOperationDataWorker.instantiateJobScheduleVO();
	}
}
