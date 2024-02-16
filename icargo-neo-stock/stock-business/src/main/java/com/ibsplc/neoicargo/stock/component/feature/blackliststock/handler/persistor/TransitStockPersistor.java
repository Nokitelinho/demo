package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.stock.dao.repository.TransitStockRepository;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransitStockPersistor {
  private final TransitStockRepository transitStockRepository;
  private final TransitStockMapper transitStockMapper;

  public void persist(List<TransitStockVO> transitStockVOs) {
    for (TransitStockVO transitStockVO : transitStockVOs) {
      if (TransitStockVO.OPERATION_FLAG_DELETE.equals(transitStockVO.getOperationFlag())) {
        log.info(
            "Remove Transit Stock by companyCode: {}, stockHolderCode: {}, "
                + "airlineIdentifier: {}, documentType: {}, documentSubType: {}",
            transitStockVO.getCompanyCode(),
            transitStockVO.getStockHolderCode(),
            transitStockVO.getAirlineIdentifier(),
            transitStockVO.getDocumentType(),
            transitStockVO.getDocumentSubType());
        var possibleTransitStock =
            transitStockRepository
                .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
                    transitStockVO.getCompanyCode(),
                    transitStockVO.getStockHolderCode(),
                    transitStockVO.getAirlineIdentifier(),
                    transitStockVO.getDocumentType(),
                    transitStockVO.getDocumentSubType());

        if (possibleTransitStock.isPresent()) {
          transitStockRepository.delete(possibleTransitStock.get());
        } else {
          throw new SystemException("OP_FAILED");
        }
      } else if (TransitStockVO.OPERATION_FLAG_INSERT.equals(transitStockVO.getOperationFlag())) {
        log.info(
            "Save Transit Stock by companyCode: {}, stockHolderCode: {}, "
                + "airlineIdentifier: {}, documentType: {}, documentSubType: {}",
            transitStockVO.getCompanyCode(),
            transitStockVO.getStockHolderCode(),
            transitStockVO.getAirlineIdentifier(),
            transitStockVO.getDocumentType(),
            transitStockVO.getDocumentSubType());
        transitStockRepository.save(transitStockMapper.mapVoToEntity(transitStockVO));
      }
    }
  }
}
