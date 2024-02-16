package com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.persistor;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.entity.StockRangeUtilisation;
import com.ibsplc.neoicargo.stock.dao.repository.StockUtilisationRepository;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockUtilisationPersistorTest {

  @InjectMocks private StockUtilisationPersistor stockUtilisationPersistor;
  @Mock private StockUtilisationRepository stockUtilisationRepository;

  @Captor ArgumentCaptor<StockRangeUtilisation> stockRangeUtilisationArgumentCaptor;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldSave() {
    // Given
    var companyCode = "AV";
    var stockHolderCode = "HQ";
    var documentType = "AWB";
    var documentSubType = "S";
    var airlineId = 1134;
    var docNum = "0000001";

    var stockRangeUtilisation = new StockRangeUtilisation();

    // When
    doReturn(Optional.empty())
        .when(stockUtilisationRepository)
        .find(companyCode, stockHolderCode, documentType, documentSubType, airlineId, docNum);

    // Then
    Assertions.assertDoesNotThrow(() -> stockUtilisationPersistor.save(stockRangeUtilisation));

    verify(stockUtilisationRepository, times(1))
        .save(stockRangeUtilisationArgumentCaptor.capture());
  }

  @Test
  void shouldFind() {
    // Given
    var vo = new StockAllocationVO();
    vo.setCompanyCode("AV");
    vo.setStockHolderCode("HQ");
    vo.setDocumentType("AWB");
    vo.setDocumentSubType("S");
    vo.setAirlineIdentifier(1134);

    var docNum = "0000001";

    // When
    doReturn(Optional.empty())
        .when(stockUtilisationRepository)
        .find(
            vo.getCompanyCode(),
            vo.getStockHolderCode(),
            vo.getDocumentType(),
            vo.getDocumentSubType(),
            vo.getAirlineIdentifier(),
            docNum);

    // Then
    Assertions.assertDoesNotThrow(() -> stockUtilisationPersistor.find(vo, docNum));
    verify(stockUtilisationRepository, times(1))
        .find(
            vo.getCompanyCode(),
            vo.getStockHolderCode(),
            vo.getDocumentType(),
            vo.getDocumentSubType(),
            vo.getAirlineIdentifier(),
            docNum);
  }
}
