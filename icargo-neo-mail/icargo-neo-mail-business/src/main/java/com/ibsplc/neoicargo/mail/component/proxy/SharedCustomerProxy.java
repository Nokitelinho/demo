package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerValidationVO;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedCustomerEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
@Component
public class SharedCustomerProxy {
	@Autowired
	private SharedCustomerEProxy sharedCustomerEProxy;

	/** 
	* Added for IASCB-35031
	* @param customerFilterVO
	* @return
	* @throws SystemException
	*/
	public Collection<CustomerLovVO> findCustomers(CustomerFilterVO customerFilterVO) throws BusinessException {
		return sharedCustomerEProxy.findCustomerLov(customerFilterVO);
	}
	public Collection<CustomerVO> getAllCustomerDetails(CustomerFilterVO customerFilterVO)
			throws SystemException {
		return sharedCustomerEProxy.getAllCustomerDetails(customerFilterVO);
	}

}
