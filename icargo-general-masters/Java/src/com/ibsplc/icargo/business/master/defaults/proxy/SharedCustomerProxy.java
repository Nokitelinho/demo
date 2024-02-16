package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.customer.CustomerBI;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SharedCustomerProxy extends SubSystemProxy {
	public SharedCustomerProxy() {
	}
	public Collection<CustomerVO> findCustomerMasters(CustomerFilterVO filterVO)
	throws SystemException {
		try {
			CustomerBI customerBI = (CustomerBI) getService("SHARED_CUSTOMER");
			return customerBI.findCustomers(filterVO);
			} catch (ServiceNotAccessibleException e) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
			} catch (RemoteException e) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
			}
	}
}
