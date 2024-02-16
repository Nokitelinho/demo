package com.ibsplc.neoicargo.stock.inttest.proxy;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.proxy.OperationsShipmentEProxy;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class OperationsShipmentMockEProxy implements OperationsShipmentEProxy {

  @Override
  public void blacklistRange(String companyCode, int airlineIdentifier, List<RangeVO> ranges)
      throws BusinessException {}
}
