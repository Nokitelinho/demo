package com.ibsplc.icargo.business.mail.operations.feature.publishmailoperationsdata;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.any;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PublishRapidMailOperationsDataFeatureVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class PublishRapidMailOperationsDataFeatureTest extends AbstractFeatureTest{
	
	private SharedDefaultsProxy sharedDefaultsProxy;
	private PublishRapidMailOperationsDataFeature publishRapidMailOperationsDataFeature;
	private PublishRapidMailOperationsDataFeatureVO publishRapidMailOperationsDataFeatureVO;

	@Override
	public void setup() throws Exception {
		sharedDefaultsProxy=mockProxy(SharedDefaultsProxy.class); 
		publishRapidMailOperationsDataFeature = spy((PublishRapidMailOperationsDataFeature) ICargoSproutAdapter
				.getBean("mail.operations.publishRapidMailOperationsDataFeature"));
		publishRapidMailOperationsDataFeatureVO = new PublishRapidMailOperationsDataFeatureVO();
		MailOperationDataFilterVO mailOperationDataFilterVO = new MailOperationDataFilterVO();
		mailOperationDataFilterVO.setCompanyCode("QF");
		mailOperationDataFilterVO.setNoOfDaysToConsider(1);
		mailOperationDataFilterVO.setTriggerPoints("DLV");
		mailOperationDataFilterVO.setPostalAuthorityCode("AU101");
		mailOperationDataFilterVO.setTolerance(1);
		publishRapidMailOperationsDataFeatureVO.setMailOperationDataFilterVO(mailOperationDataFilterVO);
	}
	
	@Test
	public void test_Perform() throws SystemException, BusinessException {
		doNothing().when(sharedDefaultsProxy).doGenerate(any(FileGenerateVO.class));
		publishRapidMailOperationsDataFeature.perform(publishRapidMailOperationsDataFeatureVO);
	}
	
	@Test
	public void test_fetchFeatureConfig() {
		publishRapidMailOperationsDataFeature.fetchFeatureConfig(publishRapidMailOperationsDataFeatureVO);
	}
}