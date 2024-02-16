package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.getNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_ALLOCATE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST_REVOKE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RECEIVE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RETURN;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_TRANSFER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STRING_DATE_FORMAT;

import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.dao.StockDetailsDao;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockDetailsHandler {
  private static final Map<String, BiConsumer<StockDetailsVO, Integer>> POPULATE_BY_STATUS =
      new HashMap<>();
  private final StockDetailsDao stockDetailsDao;
  private final LocalDate localDateUtil;

  static {
    POPULATE_BY_STATUS.put(MODE_RECEIVE, StockDetailsVO::setReceivedStock);
    POPULATE_BY_STATUS.put(MODE_ALLOCATE, StockDetailsVO::setAllocatedStock);
    POPULATE_BY_STATUS.put(MODE_TRANSFER, StockDetailsVO::setTransferredStock);
    POPULATE_BY_STATUS.put(MODE_RETURN, StockDetailsVO::setReturnStock);
    POPULATE_BY_STATUS.put(MODE_BLACKLIST, StockDetailsVO::setBlackListedStock);
  }

  public void createStockHolderStockDetails(
      StockAllocationVO stockAllocationVO, String accountingFlag, String txncode) {
    if (FLAG_YES.equals(accountingFlag)) {
      if (MODE_BLACKLIST.equals(txncode)) {
        createBlackListMode(stockAllocationVO);
      } else if (MODE_BLACKLIST_REVOKE.equals(txncode)) {
        createBlackListRevokedMode(stockAllocationVO);
      } else if (stockAllocationVO.isNewStockFlag()) {
        createNewStockFlag(stockAllocationVO);
      } else if (MODE_TRANSFER.equals(stockAllocationVO.getTransferMode())) {
        createCustomMode(stockAllocationVO, MODE_TRANSFER);
      } else if (MODE_RETURN.equals(stockAllocationVO.getTransferMode())) {
        createCustomMode(stockAllocationVO, MODE_RETURN);
      } else {
        createCustomMode(stockAllocationVO, MODE_ALLOCATE);
      }
    }
  }

  private void createBlackListMode(StockAllocationVO stockAllocationVO) {
    var stockDetailsByStockControlFor =
        findStockDetails(stockAllocationVO, stockAllocationVO.getStockHolderCode());
    if (stockDetailsByStockControlFor == null) {
      createNewStockHolderDetail(
          stockAllocationVO, stockAllocationVO.getStockHolderCode(), MODE_BLACKLIST);
    } else {
      stockDetailsByStockControlFor.setBlackListedStock(
          stockDetailsByStockControlFor.getBlackListedStock()
              + getNumberOfDocuments(stockAllocationVO.getRanges()));
      stockDetailsDao.save(stockDetailsByStockControlFor);
    }
  }

  private void createBlackListRevokedMode(StockAllocationVO stockAllocationVO) {
    var stockDetailsByStockHolderCode =
        findStockDetails(stockAllocationVO, stockAllocationVO.getStockHolderCode());
    if (stockDetailsByStockHolderCode == null) {
      createNewStockHolderDetail(
          stockAllocationVO, stockAllocationVO.getStockHolderCode(), MODE_BLACKLIST_REVOKE);
    } else {
      stockDetailsByStockHolderCode.setReturnUtilisedStock(
          stockDetailsByStockHolderCode.getReturnUtilisedStock()
              + getNumberOfDocuments(stockAllocationVO.getRanges()));
      stockDetailsDao.save(stockDetailsByStockHolderCode);
    }
  }

  private void createNewStockFlag(StockAllocationVO stockAllocationVO) {
    var stockDetailsByStockHolderCode =
        findStockDetails(stockAllocationVO, stockAllocationVO.getStockHolderCode());
    if (stockDetailsByStockHolderCode == null) {
      createNewStockHolderDetail(
          stockAllocationVO, stockAllocationVO.getStockHolderCode(), MODE_RECEIVE);
    } else {
      stockDetailsByStockHolderCode.setReceivedStock(
          stockDetailsByStockHolderCode.getReceivedStock()
              + getNumberOfDocuments(stockAllocationVO.getRanges()));
      stockDetailsDao.save(stockDetailsByStockHolderCode);
    }
  }

  private void createCustomMode(StockAllocationVO stockAllocationVO, String mode) {
    var stockDetailsByStockHolderCode =
        findStockDetails(stockAllocationVO, stockAllocationVO.getStockHolderCode());
    if (stockDetailsByStockHolderCode == null) {
      createNewStockHolderDetail(
          stockAllocationVO, stockAllocationVO.getStockHolderCode(), MODE_RECEIVE);
    } else {
      stockDetailsByStockHolderCode.setReceivedStock(
          stockDetailsByStockHolderCode.getReceivedStock()
              + getNumberOfDocuments(stockAllocationVO.getRanges()));
      stockDetailsDao.save(stockDetailsByStockHolderCode);
    }

    var stockDetails = findStockDetails(stockAllocationVO, stockAllocationVO.getStockControlFor());
    if (stockDetails == null) {
      createNewStockHolderDetail(stockAllocationVO, stockAllocationVO.getStockControlFor(), mode);
    } else {
      var docNumber = getNumberOfDocuments(stockAllocationVO.getRanges());
      if (MODE_TRANSFER.equals(mode)) {
        stockDetails.setTransferredStock(stockDetails.getTransferredStock() + docNumber);
      } else if (MODE_RETURN.equals(mode)) {
        stockDetails.setReturnStock(stockDetails.getReturnStock() + docNumber);
      } else {
        stockDetails.setAllocatedStock(stockDetails.getAllocatedStock() + docNumber);
      }

      stockDetailsDao.save(stockDetails);
    }
  }

  private void createNewStockHolderDetail(
      StockAllocationVO stockAllocationVO, String stockHolderCode, String type) {
    final var stockDetailsVO =
        StockDetailsVO.builder()
            .companyCode(stockAllocationVO.getCompanyCode())
            .stockHolderCode(stockHolderCode)
            .documentType(stockAllocationVO.getDocumentType())
            .documentSubType(stockAllocationVO.getDocumentSubType())
            .transactionDate(localDateUtil.getLocalDate(null, true))
            .lastUpdateUser(stockAllocationVO.getLastUpdateUser())
            .lastUpdateTime(localDateUtil.getLocalDate(null, true))
            .build();
    POPULATE_BY_STATUS
        .getOrDefault(type, StockDetailsVO::setReturnUtilisedStock)
        .accept(stockDetailsVO, getNumberOfDocuments(stockAllocationVO.getRanges()));
    stockDetailsDao.save(stockDetailsVO);
  }

  private StockDetailsVO findStockDetails(
      StockAllocationVO stockAllocationVO, String stockHolderCode) {
    final var txnDate =
        Integer.parseInt(
            ZonedDateTime.now().format(DateTimeFormatter.ofPattern(STRING_DATE_FORMAT)));
    return stockDetailsDao.find(
        stockAllocationVO.getCompanyCode(),
        stockHolderCode,
        stockAllocationVO.getDocumentType(),
        stockAllocationVO.getDocumentSubType(),
        txnDate);
  }
}
