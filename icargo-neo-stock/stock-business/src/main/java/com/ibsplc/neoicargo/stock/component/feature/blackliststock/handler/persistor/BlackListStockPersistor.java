package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.RangeFormatValidator;
import com.ibsplc.neoicargo.stock.dao.repository.BlackListStockRepository;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlackListStockPersistor {

  private final BlackListStockRepository blackListStockRepository;
  private final RangeFormatValidator formatValidator;
  private final BlackListStockMapper blacklistStockMapper;

  public void persist(BlacklistStockVO blacklistStockVO) throws BusinessException {
    log.info("Invoke BlackListStockPersistor");
    formatValidator.validate(createStockAllocationFromBlacklistStock(blacklistStockVO));
    log.info(
        "Save Black List Stock by companyCode: {}, stockHolderCode: {}, "
            + "airlineIdentifier: {}, documentType: {}, documentSubType: {}",
        blacklistStockVO.getCompanyCode(),
        blacklistStockVO.getStockHolderCode(),
        blacklistStockVO.getAirlineIdentifier(),
        blacklistStockVO.getDocumentType(),
        blacklistStockVO.getDocumentSubType());
    blackListStockRepository.save(blacklistStockMapper.mapVoToEntity(blacklistStockVO));
  }

  private StockAllocationVO createStockAllocationFromBlacklistStock(
      BlacklistStockVO blacklistStockVO) {
    return StockAllocationVO.builder()
        .companyCode(blacklistStockVO.getCompanyCode())
        .documentType(blacklistStockVO.getDocumentType())
        .documentSubType(blacklistStockVO.getDocumentSubType())
        .ranges(
            List.of(
                RangeVO.builder()
                    .startRange(blacklistStockVO.getRangeFrom())
                    .endRange(blacklistStockVO.getRangeTo())
                    .build()))
        .build();
  }
}
