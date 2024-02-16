package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.calculateQuantity;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.findNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_NORMAL;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RETURN;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_TRANSFER;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.util.LoggerUtil;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPersistor {

  private final StockDao stockDao;
  private final StockRepository stockRepository;
  private final ContextUtil contextUtil;

  public void addRange(StockVO stockVO, List<RangeVO> ranges, boolean isManual) {
    log.info("Invoked StockPersistor");
    LoginProfile loginProfile = contextUtil.callerLoginProfile();
    for (RangeVO rangeVo : ranges) {
      rangeVo.setNumberOfDocuments(
          findNumberOfDocuments(rangeVo.getEndRange(), rangeVo.getStartRange()));
      rangeVo.setAsciiStartRange(toLong(rangeVo.getStartRange()));
      rangeVo.setAsciiEndRange(toLong(rangeVo.getEndRange()));
      rangeVo.setManual(isManual);
      rangeVo.setLastUpdateUser(loginProfile.getUserId());
      rangeVo.setCompanyCode(stockVO.getCompanyCode());
      rangeVo.setStockHolderCode(stockVO.getStockHolderCode());
      rangeVo.setDocumentType(stockVO.getDocumentType());
      rangeVo.setDocumentSubType(stockVO.getDocumentSubType());
      rangeVo.setAirlineIdentifier(stockVO.getAirlineIdentifier());
      rangeVo.setLastUpdateTime(stockVO.getLastUpdateTime());
      if (rangeVo.getStockAcceptanceDate() != null) {
        rangeVo.setStockAcceptanceDate(rangeVo.getStockAcceptanceDate());
      } else {
        rangeVo.setStockAcceptanceDate(ZonedDateTime.now());
      }
    }
    stockDao.addRanges(stockVO, ranges);
    log.info("Saved ranges");
    LoggerUtil.logRanges(ranges);
  }

  public void updateStock(
      StockVO stockVO, StockAllocationVO stockAllocationVO, boolean isDepleteFlag) {

    stockRepository
        .findById(stockVO.getStockSerialNumber())
        .ifPresent(
            stock -> {
              updateStatus(stock, isDepleteFlag, stockAllocationVO);
              stockRepository.save(stock);
            });
  }

  private void updateStatus(
      Stock stock, boolean isDepleteFlag, StockAllocationVO stockAllocationVO) {

    var isManualFlag = stockAllocationVO.isManual();
    var flag = stockAllocationVO.getTransferMode();
    var quantity = calculateQuantity(stockAllocationVO);

    if (isManualFlag) {
      if (isDepleteFlag) {
        if (isModeFlag(flag)) {
          stock.setManualAvailableStock(stock.getManualAvailableStock() - quantity);
        }
        if (MODE_NORMAL.equals(flag)) {
          stock.setManualAllocatedStock(stock.getManualAllocatedStock() + quantity);
        }
      } else {
        if (isModeFlag(flag)) {
          stock.setManualAvailableStock(stock.getManualAvailableStock() + quantity);
        }
      }
    } else {
      if (isDepleteFlag) {
        if (isModeFlag(flag)) {
          stock.setPhysicalAvailableStock(stock.getPhysicalAvailableStock() - quantity);
        }
        if (MODE_NORMAL.equals(flag)) {
          stock.setPhysicalAllocatedStock(stock.getPhysicalAllocatedStock() + quantity);
        }
      } else {
        if (isModeFlag(flag)) {
          stock.setPhysicalAvailableStock(stock.getPhysicalAvailableStock() + quantity);
        }
      }
    }
  }

  private boolean isModeFlag(String flag) {
    return MODE_NORMAL.equals(flag) || MODE_TRANSFER.equals(flag) || MODE_RETURN.equals(flag);
  }
}
