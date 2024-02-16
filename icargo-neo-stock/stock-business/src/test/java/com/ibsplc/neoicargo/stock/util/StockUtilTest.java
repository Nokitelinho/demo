package com.ibsplc.neoicargo.stock.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
public class StockUtilTest {

  @InjectMocks @Spy StockUtil stockUtil;
  @Mock private AirlineWebComponent airlineWebComponent;
  @Mock private StockDao stockDao;
  @Mock private StockAgentRepository stockAgentRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindAirkineIdentifierWithNull() throws BusinessException {
    when(airlineWebComponent.validateNumericCode(Mockito.anyString())).thenReturn(null);
    stockUtil.findAirlineIdentifier("134");
    Mockito.verify(airlineWebComponent, times(1)).validateNumericCode("134");
  }

  @Test
  void shouldFindAirkineIdentifierSuccess() throws BusinessException {

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(134);

    when(airlineWebComponent.validateNumericCode(Mockito.anyString())).thenReturn(airlineModel);
    stockUtil.findAirlineIdentifier("134");
    Mockito.verify(airlineWebComponent, times(1)).validateNumericCode("134");
  }

  @Test
  void shouldFindSubTypeForDocumentSuccessWhenDocumentTypeIsNotAWB() throws BusinessException {

    RangeVO rangeVo = new RangeVO();
    rangeVo.setDocumentSubType("S");

    when(stockDao.findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(rangeVo);
    stockUtil.findSubTypeForDocument(
        Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
    Mockito.verify(stockDao, times(1))
        .findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
  }

  @Test
  void shouldFindSubTypeForDocumentSuccessWhenDocumentTypeIsAWB() throws BusinessException {

    when(stockDao.findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(null);
    stockUtil.findSubTypeForDocument("IBS", 134, "AWB", "1234567");
    Mockito.verify(stockDao, times(1))
        .findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
  }

  @Test
  void shouldFindStockHolderCodeForAgentSuccess() throws BusinessException {

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");
    when(stockAgentRepository.findByCompanyCodeAndAgentCode(
            Mockito.anyString(), Mockito.anyString()))
        .thenReturn(Optional.of(stockAgent));
    stockUtil.findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString());
    Mockito.verify(stockAgentRepository, times(1))
        .findByCompanyCodeAndAgentCode(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  void shouldFindStockHolderCodeForAgentNull() throws BusinessException {

    doReturn(null)
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    assertDoesNotThrow(
        () -> stockUtil.findStockHolderCodeForAgent(any(String.class), any(String.class)));
  }

  @Test
  void shouldCheckDocumentExistsInAnyStock() throws BusinessException {

    RangeVO rangeVo = new RangeVO();
    rangeVo.setStockHolderCode("HQ");

    when(stockDao.findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(rangeVo);
    stockUtil.checkDocumentExistsInAnyStock(
        Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
    Mockito.verify(stockDao, times(1))
        .findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
  }

  @Test
  void shouldCheckDocumentExistsInAnyStockNull() {

    when(stockDao.findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(null);
    stockUtil.checkDocumentExistsInAnyStock(
        Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
    Mockito.verify(stockDao, times(1))
        .findStockRangeDetails(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong());
  }

  @Test
  void shouldCheckAwbExistsInAnyStockSuccessWithOrdino() throws StockBusinessException {

    doReturn("HQ").when(stockUtil).checkDocumentExistsInAnyStock("IBS", 134, "ORDINO", 1234567L);

    stockUtil.checkAwbExistsInAnyStock("IBS", 134, "ORDINO", "1234567", 7);
    Mockito.verify(stockUtil, times(1))
        .checkDocumentExistsInAnyStock("IBS", 134, "ORDINO", 1234567L);
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenStockHolderIsEmpty() throws StockBusinessException {

    doReturn("").when(stockUtil).checkDocumentExistsInAnyStock("IBS", 134, "XYZ", 1234567L);
    assertThrows(
        StockBusinessException.class,
        () -> stockUtil.checkAwbExistsInAnyStock("IBS", 134, "XYZ", "1234567", 7));
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenStockHolderIsEmptyWithAwb()
      throws StockBusinessException {

    doReturn("").when(stockUtil).checkDocumentExistsInAnyStock("IBS", 134, "AWB", 1234567L);
    assertThrows(
        StockBusinessException.class,
        () -> stockUtil.checkAwbExistsInAnyStock("IBS", 134, "AWB", "1234567", 7));
  }
}
