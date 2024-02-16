package com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;

import com.ibsplc.icargo.business.customermanagement.defaults.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

public class AddTransactionLockInvokerTest extends AbstractFeatureTest {

	private AddTransactionLockInvoker invoker;
	private FrameworkLockProxy proxy;
	private CustomerVO featureVO;

	@Override
	public void setup() throws Exception {
		invoker = (AddTransactionLockInvoker) ICargoSproutAdapter.getBean("customermanagement.defaults.saveBrokerMappingFeature.invokeAddTransactionLock");
		proxy = mockProxy(FrameworkLockProxy.class);
		featureVO = new CustomerVO();
	}

	@Test
	public void shouldInvoke() throws SystemException, BusinessException {
		doReturn(new ArrayList<LockVO>()).when(proxy).addLocks(anyCollectionOf(LockVO.class));
		invoker.invoke(featureVO);
		verify(proxy, times(1)).addLocks(anyCollectionOf(LockVO.class));
	}

}
