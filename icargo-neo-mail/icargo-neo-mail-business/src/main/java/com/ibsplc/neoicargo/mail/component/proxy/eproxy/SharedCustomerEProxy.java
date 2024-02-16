package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "customer", name = "sharedCustomerEProxy")
public interface SharedCustomerEProxy {
	Collection<CustomerLovVO> findCustomerLov(CustomerFilterVO customerFilterVO);

	Collection<CustomerVO> getAllCustomerDetails(CustomerFilterVO customerFilterVO);
}
