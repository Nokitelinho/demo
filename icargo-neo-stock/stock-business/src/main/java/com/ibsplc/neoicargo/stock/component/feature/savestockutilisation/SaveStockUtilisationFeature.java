package com.ibsplc.neoicargo.stock.component.feature.savestockutilisation;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_RANGE_UTILISATION_AUDIT_EVENT_NAME;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.persistor.StockUtilisationPersistor;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeUtilisation;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("saveStockUtilisationFeature")
@RequiredArgsConstructor
public class SaveStockUtilisationFeature {
  private final ContextUtil contextUtil;
  private final StockUtilisationPersistor persistor;
  private final AuditUtils<AuditVO> auditUtils;

  public void perform(StockAllocationVO stockAllocationVO, String status) {
    log.info("saveStockUtilisationFeature Invoked");
    var documentNumber = getDocumentNumber(stockAllocationVO);
    if (!"B".equalsIgnoreCase(status)) {
      stockAllocationVO.setOperationFlag(StockConstant.OPERATION_FLAG_UPDATE);
      updateStockUtilisation(stockAllocationVO, status, documentNumber);
    } else {
      stockAllocationVO.setOperationFlag(StockConstant.OPERATION_FLAG_INSERT);
      saveStockUtilisation(stockAllocationVO, status, documentNumber);
    }
  }

  private void saveStockUtilisation(
      StockAllocationVO stockAllocationVO, String status, String documentNumber) {
    log.info("Insert Stock Utilisation");
    var stockRangeUtilisation = new StockRangeUtilisation();
    stockRangeUtilisation.setCompanyCode(stockAllocationVO.getCompanyCode());
    stockRangeUtilisation.setStockHolderCode(stockAllocationVO.getStockHolderCode());
    stockRangeUtilisation.setDocumentType(stockAllocationVO.getDocumentType());
    stockRangeUtilisation.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    stockRangeUtilisation.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
    stockRangeUtilisation.setAsciiDocumentNumber(CalculationUtil.toLong(documentNumber));
    stockRangeUtilisation.setDocumentNumber(documentNumber);
    populateCommonFields(status, stockRangeUtilisation);

    performAudit(stockAllocationVO, "Stock Utilisation created");
    persistor.save(stockRangeUtilisation);
  }

  private void updateStockUtilisation(
      StockAllocationVO stockAllocationVO, String status, String documentNumber) {
    log.info("Update Stock Utilisation");
    var stockRangeUtilisation = persistor.find(stockAllocationVO, documentNumber);
    if (Objects.nonNull(stockRangeUtilisation)) {
      populateCommonFields(status, stockRangeUtilisation);

      performAudit(stockAllocationVO, "Stock Utilisation updated");
      persistor.save(stockRangeUtilisation);
    } else {
      saveStockUtilisation(stockAllocationVO, status, documentNumber);
    }
  }

  private String getDocumentNumber(StockAllocationVO stockAllocationVO) {
    return stockAllocationVO.getRanges().stream()
        .findFirst()
        .map(RangeVO::getStartRange)
        .orElse(null);
  }

  private void populateCommonFields(String status, StockRangeUtilisation stockRangeUtilisation) {
    var logonAttributes = contextUtil.callerLoginProfile();
    var lastUpdateTime =
        new LocalDate(logonAttributes.getStationCode(), Location.ARP, true).toGMTDate();
    stockRangeUtilisation.setStatus(status);
    stockRangeUtilisation.setLastUpdateTimeUTC(lastUpdateTime.toZonedDateTime());
    stockRangeUtilisation.setLastUpdatedTime(lastUpdateTime.toSqlTimeStamp());
    stockRangeUtilisation.setLastUpdatedUser(logonAttributes.getUserId());
  }

  private <T extends AbstractVO> void performAudit(T vo, String transaction) {
    auditUtils.performAudit(
        new AuditConfigurationBuilder()
            .withBusinessObject(vo)
            .withEventName(STOCK_RANGE_UTILISATION_AUDIT_EVENT_NAME)
            .withListener(STOCK_AUDIT_EVENTS_LISTENER_NAME)
            .withtransaction(transaction)
            .build());
  }
}
