package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.exception.StockErrors;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import java.sql.Timestamp;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockHolderDetailsPersistor")
@RequiredArgsConstructor
public class StockHolderDetailsPersistor {
  private final AuditUtils<AuditVO> auditUtils;
  private final StockDetailsPersistor stockDetailsPersistor;
  private final StockHolderMapper stockHolderMapper;
  private final StockHolderRepository stockHolderRepository;

  public void save(StockHolderVO stockHolderVO) throws StockBusinessException {
    log.info("Save Stock Holder");
    var possibleStockHolder =
        stockHolderRepository.findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    if (possibleStockHolder.isPresent()) {
      throw new StockBusinessException(
          constructErrorVO(StockErrors.NEO_STOCK_016, ErrorType.ERROR));
    } else {
      performAudit(
          STOCK_HOLDER_AUDIT_CREATE_TRANSACTION, STOCK_HOLDER_AUDIT_EVENT_NAME, stockHolderVO);
      performAudit(
          STOCK_AUDIT_CREATE_TRANSACTION, STOCK_AUDIT_EVENT_NAME, stockHolderVO.getStock());
      enrichStock(stockHolderVO);
      stockHolderRepository.save(stockHolderMapper.mapVoToEntity(stockHolderVO));
    }
  }

  private void enrichStock(StockHolderVO stockHolderVO) {
    stockHolderVO
        .getStock()
        .forEach(
            stock -> {
              stock.setCompanyCode(stockHolderVO.getCompanyCode());
              stock.setStockHolderCode(stockHolderVO.getStockHolderCode());
            });
  }

  public void update(StockHolderVO stockHolderVO) throws StockBusinessException {
    log.info("Update Stock Holder");
    var possibleStockHolder =
        stockHolderRepository.findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    if (possibleStockHolder.isPresent()) {
      var stockHolder = possibleStockHolder.get();
      stockHolder.setStockHolderName(stockHolderVO.getStockHolderName());
      stockHolder.setControlPrivilege(stockHolderVO.getControlPrivilege());
      stockHolder.setDescription(stockHolderVO.getDescription());
      stockHolder.setStockHolderType(stockHolderVO.getStockHolderType());
      stockHolder.setStockHolderContactDetails(stockHolderVO.getStockHolderContactDetails());
      stockHolder.setLastUpdatedTime(
          Timestamp.valueOf(stockHolderVO.getLastUpdateTime().toLocalDateTime()));
      performAudit(
          STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION, STOCK_HOLDER_AUDIT_EVENT_NAME, stockHolderVO);
      enrichStock(stockHolderVO);
      stockDetailsPersistor.processStocks(stockHolder, stockHolderVO.getStock());
    } else {
      throw new StockBusinessException(
          constructErrorVO(StockErrors.NEO_STOCK_011, ErrorType.ERROR));
    }
  }

  private <T extends AbstractVO> void performAudit(
      String transaction, String eventName, Collection<T> vos) {
    vos.forEach(vo -> performAudit(transaction, eventName, vo));
  }

  private <T extends AbstractVO> void performAudit(String transaction, String eventName, T vo) {
    auditUtils.performAudit(
        new AuditConfigurationBuilder()
            .withBusinessObject(vo)
            .withEventName(eventName)
            .withListener(STOCK_AUDIT_EVENTS_LISTENER_NAME)
            .withtransaction(transaction)
            .build());
  }
}
