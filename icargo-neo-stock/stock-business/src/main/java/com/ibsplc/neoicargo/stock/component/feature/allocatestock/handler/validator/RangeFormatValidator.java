package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RangeFormatValidator {

  private final DocumentTypeProxy documentTypeProxy;

  public void validate(StockAllocationVO stockAllocationVO) throws BusinessException {
    log.info("Invoked RangeFormatValidator");
    documentTypeProxy.validateRanges(build(stockAllocationVO));
  }

  @NotNull
  private DocumentVO build(StockAllocationVO stockAllocationVO) {
    final var documentVO = new DocumentVO();
    documentVO.setCompanyCode(stockAllocationVO.getCompanyCode());
    documentVO.setDocumentType(stockAllocationVO.getDocumentType());
    documentVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    documentVO.setRange(
        stockAllocationVO.getRanges().stream().map(this::convertTo).collect(Collectors.toList()));
    return documentVO;
  }

  @NotNull
  private SharedRangeVO convertTo(RangeVO rangeVO) {
    final var sharedRangeVO = new SharedRangeVO();
    sharedRangeVO.setFromrange(rangeVO.getStartRange());
    sharedRangeVO.setToRange(rangeVO.getEndRange());
    return sharedRangeVO;
  }
}
