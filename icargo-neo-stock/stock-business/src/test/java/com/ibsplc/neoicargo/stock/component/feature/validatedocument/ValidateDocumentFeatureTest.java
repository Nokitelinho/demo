package com.ibsplc.neoicargo.stock.component.feature.validatedocument;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_COURIER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_EBT;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_INV;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.component.feature.checkforblacklisteddocument.CheckForBlacklistedDocumentFeature;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
public class ValidateDocumentFeatureTest {

  @InjectMocks private ValidateDocumentFeature validateDocumentFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private LoginProfile loginProfile;
  @Mock private StockDao stockDao;
  @Mock private AirlineWebComponent airlineWebComponent;
  @Mock private StockAgentRepository stockAgentRepository;
  @Mock private LocalDate localDate;
  @Mock private CustomerComponent customerComponent;
  @Mock private StockHolderRepository stockHolderRepository;
  @Mock private CheckForBlacklistedDocumentFeature feature;

  @Spy private StockHolderMapper stockHolderMapper = Mappers.getMapper(StockHolderMapper.class);
  @Spy private StockMapper stockMapper = Mappers.getMapper(StockMapper.class);
  @Spy private RangeMapper rangeMapper = Mappers.getMapper(RangeMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(stockMapper, "rangeMapper", rangeMapper);
    ReflectionTestUtils.setField(stockHolderMapper, "stockMapper", stockMapper);
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doReturn("AV").when(loginProfile).getCompanyCode();
  }

  @Test
  void shouldCheckForBlacklistedDocumentTrue() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554556");

    // When
    doReturn(true).when(feature).perform(any(String.class), any(String.class), any(String.class));
    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldValidateAwbWithoutAgentCode() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554556");

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockHolderVO stockHolderVO = new StockHolderVO();
    StockVO stockVO = new StockVO();
    RangeVO vo = new RangeVO();
    vo.setAsciiEndRange(4554548);
    vo.setAsciiStartRange(4554543);
    stockVO.setDocumentType(DOC_TYP_AWB);
    stockVO.setAirlineIdentifier(1134);
    stockHolderVO.setStockHolderType(StockHolderType.A);
    stockHolderVO.setStockHolderCode("HQ");
    stockVO.setRanges(new HashSet<>());
    stockVO.getRanges().add(vo);
    stockHolderVO.setStock(new HashSet<>());
    stockHolderVO.getStock().add(stockVO);

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(airlineModel).when(airlineWebComponent).validateNumericCode(any(String.class));

    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(stockHolderVO)
        .when(stockDao)
        .findStockHolderDetails(any(String.class), any(String.class));
    doReturn(Arrays.asList("A1001", "test"))
        .when(stockDao)
        .findAgentsForStockHolder(any(String.class), any(String.class));
    doReturn(model).when(customerComponent).validateAgent(any(CustomerModel.class));
    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldValidateAwbForAgentCode() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554545");
    documentFilterVO.setStockOwner("A1001");
    documentFilterVO.setPrefix("134");

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockHolder stockHolder = new StockHolder();
    Stock stock = new Stock();
    Range range = new Range();
    range.setAsciiEndRange(4554548);
    range.setAsciiStartRange(4554543);
    stock.setDocumentType(DOC_TYP_AWB);
    stock.setAirlineIdentifier(1134);
    stockHolder.setStockHolderType(StockHolderType.A);
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    range.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setRanges(new HashSet<>());
    stock.getRanges().add(range);
    stockHolder.setStock(new HashSet<>());
    stockHolder.getStock().add(stock);

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(airlineModel).when(airlineWebComponent).validateNumericCode(any(String.class));

    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    doReturn(model).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(Optional.of(stockHolder))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));

    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenStockHolderNotFoundForAgent() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554545");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.empty())
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenStockHolderDoesnotExist()
      throws StockBusinessException, CustomerBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554545");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    doReturn(model).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(Optional.ofNullable(null))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenStockDoesnotExist()
      throws StockBusinessException, CustomerBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554545");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockHolder stockHolder = new StockHolder();
    stockHolder.setStockHolderType(StockHolderType.A);
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stockHolder.setStock(new HashSet<>());

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    doReturn(model).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(Optional.of(stockHolder))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenAWBstockDoesnotExist()
      throws StockBusinessException, CustomerBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554545");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockHolder stockHolder = new StockHolder();
    Stock stock = new Stock();
    Range range = new Range();
    range.setAsciiEndRange(4554548);
    range.setAsciiStartRange(4554543);
    stock.setDocumentType(DOC_TYP_AWB);
    stock.setAirlineIdentifier(1234);
    stockHolder.setStockHolderType(StockHolderType.A);
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    range.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setRanges(new HashSet<>());
    stock.getRanges().add(range);
    stockHolder.setStock(new HashSet<>());
    stockHolder.getStock().add(stock);

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    doReturn(model).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(Optional.of(stockHolder))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenAWBdoesnotExistForAnyRangeInStock()
      throws StockBusinessException, CustomerBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    StockHolder stockHolder = new StockHolder();
    Stock stock = new Stock();
    Range range = new Range();
    range.setAsciiEndRange(4554548);
    range.setAsciiStartRange(4554543);
    stock.setDocumentType(DOC_TYP_AWB);
    stock.setAirlineIdentifier(1134);
    stockHolder.setStockHolderType(StockHolderType.A);
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    range.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setRanges(new HashSet<>());
    stock.getRanges().add(range);
    stockHolder.setStock(new HashSet<>());
    stockHolder.getStock().add(stock);

    StockAgent stockAgent = new StockAgent();
    stockAgent.setStockHolderCode("test");

    CustomerModel model = new CustomerModel();
    model.setCustomerCode("test");
    model.setAgentCode("test");
    model.setCustomerName("test");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(any(String.class), any(String.class));
    doReturn(null).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(Optional.of(stockHolder))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenAWBdoesnotExistForAnyStock() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554556");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(null)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenDocumentdoesnotExistForAnyStock() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType("ORDINO");
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("45L9556");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode(StringUtils.EMPTY);
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class,
        () ->
            validateDocumentFeature.checkAwbExistsInAnyStock(
                documentFilterVO.getCompanyCode(),
                documentFilterVO.getAirlineIdentifier(),
                documentFilterVO.getDocumentType(),
                documentFilterVO.getDocumentNumber(),
                7));
  }

  @Test
  void shouldValidateCourier() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_COURIER);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("A1001");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenCourierNotFoundInAnyStock() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_COURIER);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenCourierNotAvailableWithStockHolder() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_COURIER);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(airlineModel).when(airlineWebComponent).validateNumericCode(any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldValidateEBT() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_EBT);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("A1001");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldValidateInvoice() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_INV);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("A1001");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(airlineModel).when(airlineWebComponent).validateNumericCode(any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenEBTNotFoundInAnyStock() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_EBT);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowExceptionWhenEBTNotAvailableWithStockHolder() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_EBT);
    documentFilterVO.setAirlineIdentifier(0);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(airlineModel).when(airlineWebComponent).validateNumericCode(any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void testUnIdentifiedDoc() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType("ORDINO");

    // Then
    assertNull(validateDocumentFeature.perform(documentFilterVO));
  }

  @Test
  void shouldFindSubTypeForDocument() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType("ORDINO");
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");
    documentFilterVO.setStockOwner("A1001");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(null)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));

    // Then
    assertDoesNotThrow(
        () ->
            validateDocumentFeature.findSubTypeForDocument(
                documentFilterVO.getCompanyCode(),
                documentFilterVO.getAirlineIdentifier(),
                documentFilterVO.getDocumentType(),
                documentFilterVO.getDocumentNumber()));
  }

  @Test
  void shouldCheckAWBRangeExists() throws StockBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setDocumentNumber("45545K5");

    // Then
    assertDoesNotThrow(
        () ->
            validateDocumentFeature.checkAWBRangeExists(
                documentFilterVO.getAirlineIdentifier(),
                toLong(documentFilterVO.getDocumentNumber()),
                new StockHolderVO()));
  }

  @Test
  void shouldfindAirkineIdentifier() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(0);
    documentFilterVO.setDocumentNumber("45545K5");
    documentFilterVO.setPrefix("134");

    doReturn(null).when(airlineWebComponent).validateNumericCode(any(String.class));
    // Then
    assertDoesNotThrow(() -> validateDocumentFeature.findAirlineIdentifier("134"));
  }

  @Test
  void shouldThrowExceptionWhenStockHolderisNull()
      throws StockBusinessException, CustomerBusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentType(DOC_TYP_AWB);
    documentFilterVO.setAirlineIdentifier(1134);
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setDocumentNumber("4554550");

    RangeVO rangeVO = new RangeVO();
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setDocumentSubType("S");

    // When
    doReturn(false).when(feature).perform(any(String.class), any(String.class), any(String.class));
    doReturn(rangeVO)
        .when(stockDao)
        .findStockRangeDetails(
            any(String.class), any(Integer.class), any(String.class), any(Long.class));
    doReturn(null).when(stockDao).findStockHolderDetails(any(String.class), any(String.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> validateDocumentFeature.perform(documentFilterVO));
  }
}
