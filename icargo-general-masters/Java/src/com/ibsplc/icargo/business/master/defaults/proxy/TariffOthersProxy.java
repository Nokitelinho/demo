package com.ibsplc.icargo.business.master.defaults.proxy;


import java.util.Collection;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovFilterVO;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceLovFilterVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceLovVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
@Module("tariff")
@SubModule("others")
public class TariffOthersProxy extends ProductProxy {
	public TariffOthersProxy() {
	}
	Log log = LogFactory.getLogger("TARIFF");
	public Collection<ChargeHeadLovVO> findAllChargeHeads(ChargeHeadLovFilterVO filterVO)
	throws SystemException, ProxyException {
			return despatchRequest("findAllChargeHeads",filterVO);
		
	}
	public Collection<ServiceLovVO>  findAllServices(ServiceLovFilterVO serviceLovFilterVO)
	throws SystemException, ProxyException {
			return despatchRequest("findAllServices",serviceLovFilterVO);
		
	}
}
