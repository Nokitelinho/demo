package com.ibsplc.neoicargo.stock.dao.impl;

import com.ibsplc.neoicargo.stock.dao.StockRequestOALDao;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestOALRepository;
import com.ibsplc.neoicargo.stock.mapper.StockRequestOALMapper;
import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockRequestOAL")
@RequiredArgsConstructor
public class StockRequestOALDaoImpl implements StockRequestOALDao {

  private final StockRequestOALRepository repository;
  private final StockRequestOALMapper mapper;

  @Override
  public StockRequestOALVO find(
      String companyCode,
      String airportCode,
      String documentType,
      String documentSubType,
      int airlineIdentifier,
      int serialNumber) {
    return repository
        .findByCompanyCodeAndAirportCodeAndDocumentTypeAndDocumentSubTypeAndAirlineIdentifierAndSerialNumber(
            companyCode,
            airportCode,
            documentType,
            documentSubType,
            airlineIdentifier,
            serialNumber)
        .map(mapper::mapEntityToVo)
        .orElse(null);
  }

  @Override
  public void saveAll(List<StockRequestOALVO> stockRequestOALVOS) {
    repository.saveAll(
        stockRequestOALVOS.stream().map(mapper::mapVoToEntity).collect(Collectors.toList()));
  }
}
