package com.ibsplc.neoicargo.stock.component.feature.deletestockholder;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_004;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_032;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_033;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_034;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_REMOVED_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_REMOVED_TRANSACTION;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.util.StockHolderUtil;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.Collection;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("stock/deletestockholder")
@RequiredArgsConstructor
public class DeleteStockHolderFeature extends AbstractFeature<StockHolderDetailsVO> {

  private final StockDao stockDao;
  private final StockHolderUtil stockHolderUtil;
  private final AuditUtils<AuditVO> auditUtils;

  @Override
  protected StockHolderDetailsVO perform(StockHolderDetailsVO stockHolderDetailsVO)
      throws BusinessException {
    boolean isMappingExisting =
        checkMappingsExist(
            stockHolderDetailsVO.getCompanyCode(), stockHolderDetailsVO.getStockHolderCode());
    if (isMappingExisting) {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_032.getErrorCode(),
              NEO_STOCK_032.getErrorMessage(),
              ERROR,
              new String[] {stockHolderDetailsVO.getStockHolderCode()}));
    }
    boolean isApprover =
        checkApprover(
            stockHolderDetailsVO.getCompanyCode(), stockHolderDetailsVO.getStockHolderCode());

    if (!isApprover) {
      var stockHolderVO =
          stockHolderUtil.findStockHolderDetails(
              stockHolderDetailsVO.getCompanyCode(), stockHolderDetailsVO.getStockHolderCode());

      if (Objects.nonNull(stockHolderVO)) {
        boolean hasRanges = checkForStockRange(stockHolderVO);
        if (hasRanges) {
          throw new StockBusinessException(
              constructErrorVO(
                  NEO_STOCK_034.getErrorCode(),
                  NEO_STOCK_034.getErrorMessage(),
                  ERROR,
                  new String[] {stockHolderDetailsVO.getStockHolderCode()}));
        }
        performAudit(
            STOCK_HOLDER_AUDIT_REMOVED_TRANSACTION, STOCK_HOLDER_AUDIT_EVENT_NAME, stockHolderVO);
        performAudit(
            STOCK_AUDIT_REMOVED_TRANSACTION, STOCK_AUDIT_EVENT_NAME, stockHolderVO.getStock());
        stockDao.remove(stockHolderVO);
      } else {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_004.getErrorCode(),
                NEO_STOCK_004.getErrorMessage(),
                ERROR,
                new String[] {stockHolderDetailsVO.getStockHolderCode()}));
      }
    } else {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_033.getErrorCode(),
              NEO_STOCK_033.getErrorMessage(),
              ERROR,
              new String[] {stockHolderDetailsVO.getStockHolderCode()}));
    }
    return null;
  }

  private boolean checkForStockRange(StockHolderVO stockHolderVO) {
    for (StockVO stockVO : CollectionUtils.emptyIfNull(stockHolderVO.getStock())) {
      if (CollectionUtils.isNotEmpty(stockVO.getRanges())) {
        return true;
      }
    }
    return false;
  }

  private boolean checkApprover(String companyCode, String stockHolderCode) {
    int count = stockDao.checkApprover(companyCode, stockHolderCode);
    return count > 0;
  }

  private boolean checkMappingsExist(String companyCode, String stockHolderCode) {
    var stockAgentFilterVO = getStockAgentFilterVO(companyCode, stockHolderCode);
    var stockAgents = stockDao.findStockAgentMappings(stockAgentFilterVO);
    return !CollectionUtils.isEmpty(stockAgents);
  }

  private StockAgentFilterVO getStockAgentFilterVO(String companyCode, String stockHolderCode) {
    var filterVO = new StockAgentFilterVO();
    filterVO.setCompanyCode(companyCode);
    filterVO.setStockHolderCode(stockHolderCode);
    filterVO.setPageNumber(1);
    return filterVO;
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

  private <T extends AbstractVO> void performAudit(
      String transaction, String eventName, Collection<T> vos) {
    vos.forEach(vo -> performAudit(transaction, eventName, vo));
  }
}
