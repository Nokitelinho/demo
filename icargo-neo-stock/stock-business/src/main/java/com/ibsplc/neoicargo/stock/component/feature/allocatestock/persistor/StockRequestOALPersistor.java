package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.stock.dao.StockRequestOALDao;
import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockRequestOALPersistor {

  private final StockRequestOALDao stockRequestOALDao;

  public void update(List<StockRequestOALVO> stockRequestOALVOs) {
    final var updatedStockRequestOALVOS =
        stockRequestOALVOs.stream()
            .filter(
                incomingStockRequestOALVO ->
                    StockRequestOALVO.OPERATION_FLAG_UPDATE.equals(
                        incomingStockRequestOALVO.getOperationFlag()))
            .map(
                incomingStockRequestOALVO -> {
                  final var databaseStockRequestOALVO =
                      stockRequestOALDao.find(
                          incomingStockRequestOALVO.getCompanyCode(),
                          incomingStockRequestOALVO.getAirportCode(),
                          incomingStockRequestOALVO.getDocumentType(),
                          incomingStockRequestOALVO.getDocumentSubType(),
                          incomingStockRequestOALVO.getAirlineIdentifier(),
                          incomingStockRequestOALVO.getSerialNumber());
                  if (databaseStockRequestOALVO == null) {
                    throw new SystemException("OP_FAILED");
                  }
                  databaseStockRequestOALVO.setActionTime(
                      incomingStockRequestOALVO.getActionTime());
                  databaseStockRequestOALVO.setRequestCompleted(true);
                  return databaseStockRequestOALVO;
                })
            .collect(Collectors.toList());

    stockRequestOALDao.saveAll(updatedStockRequestOALVOS);
  }
}
