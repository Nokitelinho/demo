package com.ibsplc.neoicargo.stock.proxy.impl;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_018;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.INVALID_RANGE_FORMAT_INDICATOR;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.proxy.DocumentTypeEProxy;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentTypeProxy {

  private final DocumentTypeEProxy documentTypeEProxy;

  public void validateRanges(DocumentVO documentVO) throws StockBusinessException {
    try {
      log.info("Request body: {}", documentVO);
      documentTypeEProxy.validateRange(documentVO);
    } catch (ServiceException businessException) {
      log.error("Validate ranges failed: {}", businessException.getMessage());
      if (INVALID_RANGE_FORMAT_INDICATOR.equals(businessException.getMessage())) {
        log.error("Invalid range format", businessException);
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_018.getErrorCode(), NEO_STOCK_018.getErrorMessage(), ErrorType.ERROR));
      }
      log.error("Document type proxy failed", businessException);
      throw new SystemException(
          businessException.getMessage(), businessException.getMessage(), businessException);
    }
  }

  public List<SharedRangeVO> mergeRanges(DocumentVO documentVO) {
    try {
      log.info("Request body: {}", documentVO);
      final var sharedRangeVOS = documentTypeEProxy.mergeRanges(documentVO);
      log.info("Response body: {}", sharedRangeVOS);
      return sharedRangeVOS;
    } catch (ServiceException serviceException) {
      log.error("Merge ranges failed", serviceException);
      throw new SystemException(
          serviceException.getMessage(), serviceException.getMessage(), serviceException);
    }
  }

  public List<SharedRangeVO> splitRanges(
      Collection<SharedRangeVO> originalRanges, Collection<SharedRangeVO> availableRanges) {
    try {
      log.info(
          "Request body: \noriginRanges: {}\navailableRanges: {}, ",
          originalRanges,
          availableRanges);
      final var splitRanges = documentTypeEProxy.splitRanges(originalRanges, availableRanges);
      log.info("Response body: {}", splitRanges);
      return splitRanges;
    } catch (ServiceException serviceException) {
      log.error("Split ranges failed", serviceException);
      throw new SystemException(
          serviceException.getMessage(), serviceException.getMessage(), serviceException);
    }
  }
}
