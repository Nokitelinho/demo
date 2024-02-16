package com.ibsplc.neoicargo.stock.component.feature.findcustomerstockdetailsfeature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineValidityDetailsModel;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindCustomerStockDetailsFeatureTest {

  @InjectMocks private FindCustomerStockDetailsFeature findCustomerStockDetailsFeature;

  @Mock private CustomerComponent customerComponent;
  @Mock private AirlineWebComponent airlineWebComponent;

  @Mock private StockDao stockDao;
  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldPerform() throws StockBusinessException, CustomerBusinessException {
    var customerModel = new CustomerModel();
    var stockAgentVO = new StockAgentVO();
    var stockHolderVO = new StockHolderVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setCustomerCode("ROCKS");
    customerModel.setAgentCode("ROCKS");
    stockAgentVO.setStockHolderCode("CARGO");
    doReturn(customerModel).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(stockAgentVO).when(stockDao).findStockAgent(any(String.class), any(String.class));
    var result = findCustomerStockDetailsFeature.findStockHolderForCustomer("IBS", "ROCKS");
    assertEquals("CARGO", result);
    doReturn(stockHolderVO)
        .when(stockDao)
        .findStockHolderDetails(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.perform(stockDetailsFilterVO));
  }

  @Test
  public void shouldPerformNull() throws CustomerBusinessException, StockBusinessException {
    var customerModel = new CustomerModel();
    var stockAgentVO = new StockAgentVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setCustomerCode("ROCKS");
    customerModel.setAgentCode("ROCKS");
    stockAgentVO.setStockHolderCode("CARGO");
    doReturn(customerModel).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(null).when(stockDao).findStockAgent(any(String.class), any(String.class));
    var result = findCustomerStockDetailsFeature.findStockHolderForCustomer("IBS", "ROCKS");
    assertNull(result);
  }

  @Test
  public void shouldHolderCodeNull() throws CustomerBusinessException {
    var customerModel = new CustomerModel();
    var stockAgentVO = new StockAgentVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setCustomerCode("ROCKS");
    customerModel.setAgentCode("ROCKS");
    stockAgentVO.setStockHolderCode("CARGO");
    doReturn(customerModel).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(null).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findStockHolderForCustomer("IBS", "ROCKS"));
  }

  @Test
  public void shouldHolderNull() throws CustomerBusinessException {
    var customerModel = new CustomerModel();
    var stockAgentVO = new StockAgentVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setCustomerCode("ROCKS");
    customerModel.setAgentCode("ROCKS");
    stockAgentVO.setStockHolderCode("CARGO");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(customerModel).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(stockAgentVO).when(stockDao).findStockAgent(any(String.class), any(String.class));
    doReturn(null).when(stockDao).findStockHolderDetails(any(String.class), any(String.class));
    assertThrows(
        StockBusinessException.class,
        () -> findCustomerStockDetailsFeature.perform(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindCustomerStockDetails() {
    var stockDetailsVO = new StockDetailsVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(stockDetailsVO)
        .when(stockDao)
        .findCustomerStockDetails(any(StockDetailsFilterVO.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findCustomerStockDetails(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindCustomerStockDetailsNull() {
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(null).when(stockDao).findCustomerStockDetails(any(StockDetailsFilterVO.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findCustomerStockDetails(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindAvailableRanges() {
    var stockDetailsVO = new StockDetailsVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(stockDetailsVO)
        .when(stockDao)
        .findCustomerStockDetails(any(StockDetailsFilterVO.class));
    var rangeVo1 = new RangeVO();
    var rangeVo2 = new RangeVO();
    var rangeVo3 = new RangeVO();
    var rangeVo4 = new RangeVO();
    var rngLst1 = new ArrayList<RangeVO>();
    var rngLst2 = new ArrayList<RangeVO>();
    var rngLst3 = new ArrayList<RangeVO>();
    rangeVo1.setAirlineIdentifier(1234);
    rangeVo2.setAirlineIdentifier(1235);
    rangeVo3.setAirlineIdentifier(1236);
    rangeVo4.setAirlineIdentifier(1237);
    rngLst1.add(rangeVo1);
    rngLst1.add(rangeVo2);
    rngLst2.add(rangeVo3);
    rngLst3.add(rangeVo4);
    doReturn(rngLst1)
        .when(rangeDao)
        .findAvailableRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst2)
        .when(rangeDao)
        .findAllocatedRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst3).when(rangeDao).findUsedRangesForCustomer(any(StockDetailsFilterVO.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findCustomerStockDetails(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindAllocatedRangesForCustomer() {
    var stockDetailsVO = new StockDetailsVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(stockDetailsVO)
        .when(stockDao)
        .findCustomerStockDetails(any(StockDetailsFilterVO.class));
    var rangeVo1 = new RangeVO();
    var rangeVo2 = new RangeVO();
    var rangeVo3 = new RangeVO();
    var rangeVo4 = new RangeVO();
    var rngLst1 = new ArrayList<RangeVO>();
    var rngLst2 = new ArrayList<RangeVO>();
    var rngLst3 = new ArrayList<RangeVO>();
    rangeVo1.setAirlineIdentifier(1234);
    rangeVo2.setAirlineIdentifier(1235);
    rangeVo3.setAirlineIdentifier(1236);
    rangeVo4.setAirlineIdentifier(1237);
    rngLst1.add(rangeVo1);
    rngLst1.add(rangeVo2);
    rngLst2.add(rangeVo3);
    rngLst3.add(rangeVo4);
    doReturn(rngLst2)
        .when(rangeDao)
        .findAvailableRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst1)
        .when(rangeDao)
        .findAllocatedRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst3).when(rangeDao).findUsedRangesForCustomer(any(StockDetailsFilterVO.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findCustomerStockDetails(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindUsedRangesForCustomer() {
    var stockDetailsVO = new StockDetailsVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("CARGO");
    doReturn(stockDetailsVO)
        .when(stockDao)
        .findCustomerStockDetails(any(StockDetailsFilterVO.class));
    var rangeVo1 = new RangeVO();
    var rangeVo2 = new RangeVO();
    var rangeVo3 = new RangeVO();
    var rangeVo4 = new RangeVO();
    var rangeVo5 = new RangeVO();
    var rangeVo6 = new RangeVO();
    var rngLst1 = new ArrayList<RangeVO>();
    var rngLst2 = new ArrayList<RangeVO>();
    var rngLst3 = new ArrayList<RangeVO>();
    rangeVo1.setAirlineIdentifier(1234);
    rangeVo1.setTransactionDate(LocalDate.parse("2023-05-31"));
    rangeVo2.setAirlineIdentifier(1235);
    rangeVo2.setTransactionDate(LocalDate.parse("2023-05-23"));
    rangeVo3.setAirlineIdentifier(1236);
    rangeVo3.setTransactionDate(LocalDate.parse("2023-05-30"));
    rangeVo4.setAirlineIdentifier(1237);
    rangeVo4.setTransactionDate(LocalDate.parse("2023-05-19"));
    rangeVo5.setAirlineIdentifier(1238);
    rangeVo5.setTransactionDate(LocalDate.parse("2023-05-22"));
    rngLst1.add(rangeVo1);
    rngLst1.add(rangeVo2);
    rngLst1.add(rangeVo5);
    rngLst2.add(rangeVo3);
    rngLst3.add(rangeVo4);

    var arlMdlLst = new ArrayList<AirlineModel>();
    var arlMdl1 = new AirlineModel();
    arlMdl1.setAirlineIdentifier(1234);
    var arlVldDtl1 = new AirlineValidityDetailsModel();
    arlVldDtl1.setThreeNumberCode("777");
    arlVldDtl1.setValidFrom("2023-05-20");
    arlVldDtl1.setValidTo("2023-05-30");
    arlMdl1.setAirlineValidityDetails(new HashSet<>());
    arlMdl1.getAirlineValidityDetails().add(arlVldDtl1);
    arlMdlLst.add(arlMdl1);

    var arlMdl2 = new AirlineModel();
    arlMdl2.setAirlineIdentifier(1235);
    var arlVldDtl2 = new AirlineValidityDetailsModel();
    arlVldDtl2.setThreeNumberCode("777");
    arlVldDtl2.setValidFrom("2023-05-20");
    arlVldDtl2.setValidTo("2023-05-30");
    arlMdl2.setAirlineValidityDetails(new HashSet<>());
    arlMdl2.getAirlineValidityDetails().add(arlVldDtl2);
    arlMdlLst.add(arlMdl2);

    var arlMdl3 = new AirlineModel();
    arlMdl3.setAirlineIdentifier(1236);
    var arlVldDtl3 = new AirlineValidityDetailsModel();
    arlVldDtl3.setThreeNumberCode("777");
    arlVldDtl3.setValidFrom("2023-05-20");
    arlVldDtl3.setValidTo("2023-05-30");
    arlMdl3.setAirlineValidityDetails(new HashSet<>());
    arlMdl3.getAirlineValidityDetails().add(arlVldDtl3);
    arlMdlLst.add(arlMdl3);

    var arlMdl4 = new AirlineModel();
    arlMdl4.setAirlineIdentifier(1237);
    var arlVldDtl4 = new AirlineValidityDetailsModel();
    arlVldDtl4.setThreeNumberCode("777");
    arlVldDtl4.setValidFrom("2023-05-20");
    arlVldDtl4.setValidTo("2023-05-30");
    arlMdl4.setAirlineValidityDetails(new HashSet<>());
    arlMdl4.getAirlineValidityDetails().add(arlVldDtl4);
    arlMdlLst.add(arlMdl4);

    var arlMdl5 = new AirlineModel();
    arlMdl5.setAirlineIdentifier(1238);
    var arlVldDtl5 = new AirlineValidityDetailsModel();
    arlVldDtl5.setThreeNumberCode("777");
    arlVldDtl5.setValidFrom("2023-05-20");
    arlVldDtl5.setValidTo("2023-05-30");
    arlMdl5.setAirlineValidityDetails(new HashSet<>());
    arlMdl5.getAirlineValidityDetails().add(arlVldDtl5);
    arlMdlLst.add(arlMdl5);

    doReturn(arlMdlLst).when(airlineWebComponent).findAirlineValidityDetails(any(List.class));

    doReturn(rngLst3)
        .when(rangeDao)
        .findAvailableRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst2)
        .when(rangeDao)
        .findAllocatedRangesForCustomer(any(StockDetailsFilterVO.class));
    doReturn(rngLst1).when(rangeDao).findUsedRangesForCustomer(any(StockDetailsFilterVO.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findCustomerStockDetails(stockDetailsFilterVO));
  }

  @Test
  public void shouldFindTxnDate() {
    var rngLst = new ArrayList<RangeVO>();
    var rangeVO1 = new RangeVO();
    rangeVO1.setTransactionDate(LocalDate.now());
    rangeVO1.setAirlineIdentifier(1234);
    var rangeVO2 = new RangeVO();
    var rangeVO3 = new RangeVO();
    rangeVO2.setTransactionDate(LocalDate.parse("2023-05-23"));
    rangeVO3.setTransactionDate(LocalDate.parse("2023-05-31"));
    rangeVO2.setAirlineIdentifier(1235);
    rangeVO3.setAirlineIdentifier(1236);
    rngLst.add(rangeVO1);
    rngLst.add(rangeVO2);
    rngLst.add(rangeVO3);
    var arlMdlLst = new ArrayList<AirlineModel>();
    var arlMdl = new AirlineModel();
    var arlVldDtl = new AirlineValidityDetailsModel();
    arlVldDtl.setValidFrom("2023-05-20");
    arlVldDtl.setValidTo("2023-05-30");
    arlMdl.setAirlineIdentifier(1235);
    arlMdl.setAirlineValidityDetails(new HashSet<>());
    arlMdl.getAirlineValidityDetails().add(arlVldDtl);
    arlMdlLst.add(arlMdl);
    doReturn(arlMdlLst).when(airlineWebComponent).findAirlineValidityDetails(any(List.class));
    var result = findCustomerStockDetailsFeature.checkTxnDat(rngLst);
    assertThat(result).hasSize(1);
  }

  @Test
  public void shouldFindAirlineModelsNull() {
    var rngLst = new ArrayList<RangeVO>();
    var result = findCustomerStockDetailsFeature.findAirlineModels(rngLst);
    assertThat(result).hasSize(0);
  }

  @Test
  public void shouldConstructMap() {
    var rangeVO = new RangeVO();
    rangeVO.setAirlineIdentifier(1777);
    var rngLst = new ArrayList<RangeVO>();
    rngLst.add(rangeVO);
    var arlMdl = new AirlineModel();
    arlMdl.setAirlineIdentifier(1777);
    var arlVldDtl = new AirlineValidityDetailsModel();
    arlVldDtl.setThreeNumberCode("777");
    arlMdl.setAirlineValidityDetails(new HashSet<>());
    arlMdl.getAirlineValidityDetails().add(arlVldDtl);
    var arlMdlLst = new ArrayList<AirlineModel>();
    arlMdlLst.add(arlMdl);
    doReturn(arlMdlLst).when(airlineWebComponent).findAirlineValidityDetails(any(List.class));
    var result = findCustomerStockDetailsFeature.constructMap(rngLst);
    assertThat(result.get(1777)).isEqualTo("777");
  }

  @Test
  public void shouldStockHolderCodeIsNull() throws BusinessException {
    var customerModel = new CustomerModel();
    var stockAgentVO = new StockAgentVO();
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setCustomerCode("ROCKS");
    customerModel.setAgentCode("ROCKS");
    //	    stockAgentVO.setStockHolderCode("CARGO");
    doReturn(customerModel).when(customerComponent).validateAgent(any(CustomerModel.class));
    doReturn(null).when(stockDao).findStockAgent(any(String.class), any(String.class));
    assertThrows(
        StockBusinessException.class,
        () -> findCustomerStockDetailsFeature.perform(stockDetailsFilterVO));
  }

  @Test
  public void customerNull() throws BusinessException {
    doReturn(null).when(customerComponent).validateAgent(any(CustomerModel.class));
    Assertions.assertDoesNotThrow(
        () -> findCustomerStockDetailsFeature.findStockHolderForCustomer("IBS", "ROCKS"));
  }
}
