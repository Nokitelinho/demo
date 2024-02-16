package com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.AddTransactionLockInvoker;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.ReleaseTransactionLockInvoker;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SaveBrokerMappingFeatureTest extends AbstractFeatureTest {

	private SaveBrokerMappingFeature feature;
	private AddTransactionLockInvoker addInvoker;
	private ReleaseTransactionLockInvoker releaseInvoker;
	private CustomerMgmntController controller;
	
	@Override
	public void setup() throws Exception {
		feature = (SaveBrokerMappingFeature) ICargoSproutAdapter.getBean("customermanagement.defaults.saveBrokerMappingFeature");
		addInvoker = mockBean("customermanagement.defaults.saveBrokerMappingFeature.invokeAddTransactionLock", AddTransactionLockInvoker.class);
		releaseInvoker = mockBean("customermanagement.defaults.saveBrokerMappingFeature.invokeReleaseTransactionLock", ReleaseTransactionLockInvoker.class);
		controller = mockBean("customerMgmntController", CustomerMgmntController.class);
	}
	
	@Test
	public void doVerifyAllInvokersAndBeanHasBeenInvoked1() throws SystemException, BusinessException {
		doNothing().when(addInvoker).invoke(any(CustomerVO.class));
		doReturn("CUSCOD").when(controller).registerCustomer(any(CustomerVO.class));
		doNothing().when(releaseInvoker).invoke(any(CustomerVO.class));
		feature.execute(new CustomerVO());

		verify(addInvoker, times(1)).invoke(any(CustomerVO.class));
		verify(controller, times(1)).registerCustomer(any(CustomerVO.class));
		verify(releaseInvoker, times(1)).invoke(any(CustomerVO.class));
	}
	
	@Test
	public void doVerifyAllInvokersAndBeanHasBeenInvoked2() throws SystemException, BusinessException {
		doNothing().when(addInvoker).invoke(any(CustomerVO.class));
		doReturn(null).when(controller).registerCustomer(any(CustomerVO.class));
		doNothing().when(releaseInvoker).invoke(any(CustomerVO.class));
		feature.execute(new CustomerVO());

		verify(addInvoker, times(1)).invoke(any(CustomerVO.class));
		verify(controller, times(1)).registerCustomer(any(CustomerVO.class));
		verify(releaseInvoker, times(1)).invoke(any(CustomerVO.class));
	}
	
}
