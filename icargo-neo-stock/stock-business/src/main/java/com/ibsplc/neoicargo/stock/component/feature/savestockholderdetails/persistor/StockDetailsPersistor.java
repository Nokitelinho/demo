package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_REMOVED_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_UPDATE_TRANSACTION;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.exception.StockErrors;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockDetailsPersistor")
@RequiredArgsConstructor
public class StockDetailsPersistor {
  private final AuditUtils<AuditVO> auditUtils;
  private final StockMapper stockMapper;

  public void processStocks(StockHolder stockHolder, Collection<StockVO> incomingStocks)
      throws StockBusinessException {
    for (StockVO stockVO : incomingStocks) {
      if (StockVO.OPERATION_FLAG_DELETE.equals(stockVO.getOperationFlag())) {
        log.info("Delete Stock");
        performAudit(stockVO, STOCK_AUDIT_REMOVED_TRANSACTION);
        removeStock(stockHolder, stockVO);
      }
      if (StockVO.OPERATION_FLAG_INSERT.equals(stockVO.getOperationFlag())) {
        log.info("Insert Stock");
        performAudit(stockVO, STOCK_AUDIT_CREATE_TRANSACTION);
        insertStock(stockHolder, stockVO);
      }
      if (StockVO.OPERATION_FLAG_UPDATE.equals(stockVO.getOperationFlag())) {
        log.info("Update Stock");
        performAudit(stockVO, STOCK_AUDIT_UPDATE_TRANSACTION);
        updateStock(stockHolder, stockVO);
      }
    }
  }

  private void insertStock(StockHolder stockHolder, StockVO stockVO) {
    final var stock = stockMapper.mapVoToEntity(stockVO);
    stockHolder.addStock(stock);
  }

  private void updateStock(StockHolder stockHolder, StockVO stockVO) throws StockBusinessException {
    for (Stock stock : stockHolder.getStock()) {
      if (isExists(stock, stockVO, stockHolder)) {
        stockMapper.update(stockVO, stock);
        return;
      }
    }
    throw new StockBusinessException(
        constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
  }

  private void removeStock(StockHolder stockHolder, StockVO stockVO) throws StockBusinessException {
    var toBeRemoved = new ArrayList<Stock>();
    for (Stock stock : stockHolder.getStock()) {
      if (isExists(stock, stockVO, stockHolder)) {
        if (!CollectionUtils.isEmpty(stock.getRanges())) {
          throw new StockBusinessException(
              constructErrorVO(
                  StockErrors.NEO_STOCK_017.getErrorCode(),
                  StockErrors.NEO_STOCK_017.getErrorMessage(),
                  ErrorType.ERROR));
        }
        toBeRemoved.add(stock);
      }
    }
    toBeRemoved.forEach(stockHolder.getStock()::remove);
  }

  private boolean isExists(Stock stock, StockVO stockVO, StockHolder stockHolder) {
    return stock.getAirlineIdentifier() == stockVO.getAirlineIdentifier()
        && stock.getDocumentType().equals(stockVO.getDocumentType())
        && stock.getDocumentSubType().equals(stockVO.getDocumentSubType())
        && stock.getCompanyCode().equals(stockHolder.getCompanyCode())
        && stock.getStockHolderCode().equals(stockHolder.getStockHolderCode());
  }

  private <T extends AbstractVO> void performAudit(T vo, String transaction) {
    auditUtils.performAudit(
        new AuditConfigurationBuilder()
            .withBusinessObject(vo)
            .withEventName(STOCK_AUDIT_EVENT_NAME)
            .withListener(STOCK_AUDIT_EVENTS_LISTENER_NAME)
            .withtransaction(transaction)
            .build());
  }
}
