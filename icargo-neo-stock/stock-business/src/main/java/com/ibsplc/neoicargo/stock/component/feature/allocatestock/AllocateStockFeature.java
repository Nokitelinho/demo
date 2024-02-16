package com.ibsplc.neoicargo.stock.component.feature.allocatestock;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockDetailsHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockHolderHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper.SystemParameterHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockRequestOALPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockRequestPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockHolderCodeValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("stock/allocatestock")
@RequiredArgsConstructor
public class AllocateStockFeature extends AbstractFeature<StockAllocationVO> {

  private final CreateHistoryInvoker createHistoryInvoker;
  private final ContextUtil contextUtil;
  private final StockDao stockDao;
  private final StockAllocationMapper stockAllocationMapper;
  private final StockRequestPersistor stockRequestPersistor;
  private final StockRequestOALPersistor stockRequestOALPersistor;
  private final StockHolderCodeValidator stockHolderCodeValidator;

  private final StockHolderHandler stockHolderHandler;
  private final StockDetailsHandler stockDetailsHandler;

  private final SystemParameterHelper systemParameterHelper;

  @Override
  public StockAllocationModel perform(StockAllocationVO stockAllocationVO)
      throws BusinessException {
    final var systemParameters = systemParameterHelper.getSystemParameters();

    var isDepleteOnly =
        stockHolderCodeValidator.isRelatedToStockControlFor(
            contextUtil.callerLoginProfile().getAirportCode(),
            stockAllocationVO.getStockControlFor());

    if (!stockAllocationVO.isNewStockFlag() && !stockAllocationVO.isFromConfirmStock()) {
      stockHolderHandler.deplete(stockAllocationVO, false);

      if (stockAllocationVO.isApproverDeleted()) {
        return stockAllocationMapper.mapVoToModel(stockAllocationVO);
      }

      if (isDepleteOnly) {
        final var stockVO = stockDao.findStockForStockHolder(stockAllocationVO);
        if (stockVO != null && stockVO.getTotalStock() < stockVO.getReorderLevel()) {
          stockAllocationVO.setHasMinReorderLevel(true);
          return stockAllocationMapper.mapVoToModel(stockAllocationVO);
        }
      }
    }

    if (!isDepleteOnly) {
      stockHolderHandler.allocate(stockAllocationVO, systemParameters);
    }
    if (stockAllocationVO.isAllocate()) {
      stockRequestPersistor.update(stockAllocationVO);
    }
    if (stockAllocationVO.getStockForOtherAirlines() != null) {
      stockRequestOALPersistor.update(stockAllocationVO.getStockForOtherAirlines());
    }
    if (stockAllocationVO.isBlacklist() && stockAllocationVO.isNewStockFlag()) {
      stockDetailsHandler.createStockHolderStockDetails(
          stockAllocationVO, systemParameters.getAccountingFlag(), "BR");
    } else {
      stockDetailsHandler.createStockHolderStockDetails(
          stockAllocationVO, systemParameters.getAccountingFlag(), null);
    }
    log.info(
        "TransactionCode: {}, isEnableHistory: {}",
        stockAllocationVO.getTransactionCode(),
        systemParameters.isEnableStockHistory());
    if (systemParameters.isEnableStockHistory() && stockAllocationVO.getTransactionCode() != null) {
      createHistoryInvoker.invoke(stockAllocationVO);
    }
    return stockAllocationMapper.mapVoToModel(stockAllocationVO);
  }
}
