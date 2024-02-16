package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockEntity;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockHolderEntity;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockRequest;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.AllocateStockInvoker;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.ApproveStockRequestInvoker;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.FindRangesInvoker;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestRepository;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class StockRequestDetailsPersistorTest {

  private static final String COMPANY_CODE = "AV";
  private static final String AIRLINE_IDENTIFIER = "1234";
  private static final String APPROVAL_REMARKS = "remarks";
  private static final String REQUEST_REF_NUMBER = "123-1234567";
  private static final String STOCK_HOLDER_CODE = "HQ";
  private static final String DOC_TYPE = "AWB";
  private static final String DOC_SUB_TYPE = "S";
  @InjectMocks private StockRequestDetailsPersistor stockRequestDetailsPersistor;
  @Mock private StockDao stockDao;
  @Mock private StockRepository stockRepository;
  @Mock private AllocateStockInvoker allocateStockInvoker;
  @Mock private StockRequestRepository stockRequestRepository;
  @Mock private FindRangesInvoker findRangesInvoker;
  @Mock private ApproveStockRequestInvoker approveStockRequestInvoker;
  @Spy private StockRequestMapper stockRequestMapper = Mappers.getMapper(StockRequestMapper.class);
  private StockRequestVO stockRequestVO;
  private StockRequest stockRequestEntity;
  private Stock stockEntity;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    stockRequestVO =
        getMockStockRequestVO(
            COMPANY_CODE, AIRLINE_IDENTIFIER, APPROVAL_REMARKS, REQUEST_REF_NUMBER);
    stockRequestEntity =
        getMockStockRequest(
            COMPANY_CODE, Integer.parseInt(AIRLINE_IDENTIFIER), REQUEST_REF_NUMBER, "HQ");
    stockEntity =
        getMockStockEntity(
            getMockStockHolderEntity(COMPANY_CODE, STOCK_HOLDER_CODE),
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        stockDao,
        stockRepository,
        stockRequestRepository,
        findRangesInvoker,
        allocateStockInvoker,
        approveStockRequestInvoker);
  }

  @Test
  void shouldThrowStockRequestNotFoundExceptionWhileUpdateStockRequest() {
    // When
    doReturn(Optional.empty())
        .when(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumber(COMPANY_CODE, REQUEST_REF_NUMBER);

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> stockRequestDetailsPersistor.update(stockRequestVO));
    verify(stockRequestRepository, times(1))
        .findByCompanyCodeAndRequestRefNumber(COMPANY_CODE, REQUEST_REF_NUMBER);
    assertEquals(NEO_STOCK_007.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldUpdateStockRequest() {
    // When
    doReturn(Optional.of(stockRequestEntity))
        .when(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumber(COMPANY_CODE, REQUEST_REF_NUMBER);
    doReturn(Optional.of(stockEntity))
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);

    // Then
    Assertions.assertDoesNotThrow(() -> stockRequestDetailsPersistor.update(stockRequestVO));
    verify(stockRequestRepository, times(1))
        .findByCompanyCodeAndRequestRefNumber(COMPANY_CODE, REQUEST_REF_NUMBER);
    verify(stockRequestMapper, times(1)).update(stockRequestVO, stockRequestEntity);
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
  }

  @Test
  void shouldThrowStockNotFoundExceptionWhileSaveStockRequest() {
    // When
    doReturn(Optional.empty())
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> stockRequestDetailsPersistor.save(stockRequestVO));
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldSaveWithoutAutoProcessingAllocateStockStockRequest() {
    // When
    doReturn(Optional.of(stockEntity))
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    doReturn(null)
        .when(stockDao)
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());

    stockRequestVO.setRequestRefNumber("");

    // Then
    Assertions.assertDoesNotThrow(() -> stockRequestDetailsPersistor.save(stockRequestVO));
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    verify(stockRequestMapper, times(1)).mapVoToEntity(stockRequestVO);
    verify(stockDao, times(1))
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    verify(stockRequestRepository, times(1)).save(any(StockRequest.class));
    verify(stockRequestRepository).getNextValOfReqRefNumber();
  }

  @Test
  void shouldThrowStockHolderNotFoundExceptionWhileSaveStockRequest() {
    // When
    doReturn(Optional.of(stockEntity))
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    doReturn("ASDF")
        .when(stockDao)
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    doReturn(null).when(stockDao).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);

    stockRequestVO.setRequestRefNumber("");
    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> stockRequestDetailsPersistor.save(stockRequestVO));
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    verify(stockRequestMapper, times(1)).mapVoToEntity(stockRequestVO);
    verify(stockDao, times(1))
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    verify(stockDao, times(1)).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);
    verify(stockRequestRepository, times(1)).save(any(StockRequest.class));
    verify(stockRequestRepository).getNextValOfReqRefNumber();
    assertEquals(NEO_STOCK_011.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldApproveAndNotAllocateWhileSave() throws BusinessException {
    // Given
    var stockHolderVO = getMockStockHolderVO(COMPANY_CODE, STOCK_HOLDER_CODE);
    var stockVO1 =
        getMockStockVO(stockHolderVO, Integer.parseInt(AIRLINE_IDENTIFIER), DOC_TYPE, DOC_SUB_TYPE);
    stockVO1.setAutoprocessQuantity(10);
    var stockVO2 = getMockStockVO(stockHolderVO, Integer.parseInt("9999"), DOC_TYPE, DOC_SUB_TYPE);
    stockHolderVO.setStock(List.of(stockVO1, stockVO2));

    // When
    doReturn(Optional.of(stockEntity))
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    doReturn("ASDF")
        .when(stockDao)
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    doReturn(stockHolderVO).when(stockDao).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);
    doNothing().when(approveStockRequestInvoker).invoke(stockRequestVO, stockVO1);
    doReturn(List.of()).when(findRangesInvoker).invoke(stockRequestVO, stockVO1);

    stockRequestVO.setRequestRefNumber("");
    // Then
    assertDoesNotThrow(() -> stockRequestDetailsPersistor.save(stockRequestVO));
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    verify(stockRequestMapper, times(1)).mapVoToEntity(stockRequestVO);
    verify(stockDao, times(1))
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    verify(stockDao, times(1)).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);
    verify(approveStockRequestInvoker, times(1)).invoke(stockRequestVO, stockVO1);
    verify(findRangesInvoker, times(1)).invoke(stockRequestVO, stockVO1);
    verify(stockRequestRepository, times(1)).save(any(StockRequest.class));
    verify(stockRequestRepository).getNextValOfReqRefNumber();
  }

  @Test
  void shouldApproveAndAllocateWhileSave() throws BusinessException {
    // Given
    var stockHolderVO = getMockStockHolderVO(COMPANY_CODE, STOCK_HOLDER_CODE);
    var stockVO1 =
        getMockStockVO(stockHolderVO, Integer.parseInt(AIRLINE_IDENTIFIER), DOC_TYPE, DOC_SUB_TYPE);
    stockVO1.setAutoprocessQuantity(10);
    var stockVO2 = getMockStockVO(stockHolderVO, Integer.parseInt("9999"), DOC_TYPE, DOC_SUB_TYPE);
    stockHolderVO.setStock(List.of(stockVO1, stockVO2));
    var rangeVOS = List.of(getMockRangeVO(COMPANY_CODE, DOC_TYPE, DOC_SUB_TYPE, 0, 1));

    // When
    doReturn(Optional.of(stockEntity))
        .when(stockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    doReturn("ASDF")
        .when(stockDao)
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    doReturn(stockHolderVO).when(stockDao).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);
    doNothing().when(approveStockRequestInvoker).invoke(stockRequestVO, stockVO1);
    doReturn(rangeVOS).when(findRangesInvoker).invoke(stockRequestVO, stockVO1);

    // Then
    assertDoesNotThrow(() -> stockRequestDetailsPersistor.save(stockRequestVO));
    verify(stockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE,
            STOCK_HOLDER_CODE,
            Integer.parseInt(AIRLINE_IDENTIFIER),
            DOC_TYPE,
            DOC_SUB_TYPE);
    verify(stockRequestMapper, times(1)).mapVoToEntity(stockRequestVO);
    verify(stockDao, times(1))
        .findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType());
    verify(stockDao, times(1)).findStockHolderDetails(COMPANY_CODE, STOCK_HOLDER_CODE);
    verify(approveStockRequestInvoker, times(1)).invoke(stockRequestVO, stockVO1);
    verify(findRangesInvoker, times(1)).invoke(stockRequestVO, stockVO1);
    verify(allocateStockInvoker, times(1)).invoke(stockRequestVO, stockVO1, rangeVOS);
    verify(stockRequestRepository, times(1)).save(any(StockRequest.class));
  }
}
