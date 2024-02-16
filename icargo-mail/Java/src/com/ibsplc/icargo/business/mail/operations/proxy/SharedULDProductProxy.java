package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("shared")
@SubModule("uld")
public class SharedULDProductProxy extends ProductProxy {


	public Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO filterVO)
			throws ProxyException, SystemException {
		return despatchRequest("findULDTypes", filterVO);
	}
}
