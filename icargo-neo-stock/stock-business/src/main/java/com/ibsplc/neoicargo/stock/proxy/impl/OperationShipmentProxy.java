package com.ibsplc.neoicargo.stock.proxy.impl;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.proxy.OperationsShipmentEProxy;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationShipmentProxy {
  private final OperationsShipmentEProxy operationsShipmentEProxy;

  public void blacklistRange(BlacklistStockVO blacklistStockVO) throws BusinessException {
    var rangeVO = new com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO();
    rangeVO.setStartRange(blacklistStockVO.getRangeFrom());
    rangeVO.setEndRange(blacklistStockVO.getRangeTo());
    rangeVO.setBlackList(true);
    operationsShipmentEProxy.blacklistRange(
        blacklistStockVO.getCompanyCode(),
        blacklistStockVO.getAirlineIdentifier(),
        List.of(rangeVO));
  }
}
