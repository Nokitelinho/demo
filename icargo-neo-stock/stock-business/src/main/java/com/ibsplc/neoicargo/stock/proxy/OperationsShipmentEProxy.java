package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import java.util.List;

@EProductProxy(module = "operations", submodule = "shipment", name = "operationsShipmentEProxy")
public interface OperationsShipmentEProxy {

  void blacklistRange(String companyCode, int airlineIdentifier, List<RangeVO> ranges)
      throws BusinessException;
}
