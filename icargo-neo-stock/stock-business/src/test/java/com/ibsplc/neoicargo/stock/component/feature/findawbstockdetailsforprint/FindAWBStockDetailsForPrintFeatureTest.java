package com.ibsplc.neoicargo.stock.component.feature.findawbstockdetailsforprint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineValidityDetailsModel;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class FindAWBStockDetailsForPrintFeatureTest {

  @InjectMocks @Spy FindAWBStockDetailsForPrintFeature findAWBStockDetailsForPrintFeature;

  @Mock private StockDao stockDao;

  @Mock private ContextUtil contextUtil;

  @Mock private LoginProfile loginProfile;

  @Mock private AirlineWebComponent airlineWebcomponent;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenStockVOisNull() throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(stockDao.findStockHolderDetails(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(null);

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setCompanyCode("AV");

    assertThrows(
        StockBusinessException.class,
        () -> findAWBStockDetailsForPrintFeature.perform(stockFilterVO));
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenStockHolderCodeisNull() throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(stockDao.findAWBStockDetailsForPrint(any(StockFilterVO.class))).thenReturn(null);

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setCompanyCode("AV");

    assertThrows(
        StockBusinessException.class,
        () -> findAWBStockDetailsForPrintFeature.perform(stockFilterVO));
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenStockHolderCodeisEmpty() throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(stockDao.findAWBStockDetailsForPrint(any(StockFilterVO.class))).thenReturn(null);

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setStockHolderCode("");
    stockFilterVO.setCompanyCode("AV");

    assertThrows(
        StockBusinessException.class,
        () -> findAWBStockDetailsForPrintFeature.perform(stockFilterVO));
  }

  @Test
  void shouldThrowStockBusinessExceptionWhenRangeVosisEmpty() throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(loginProfile.getCompanyCode()).thenReturn("AV");

    when(stockDao.findStockHolderDetails(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(new StockHolderVO());

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setCompanyCode("AV");
    List<RangeVO> rngVOs = new ArrayList<RangeVO>();

    when(stockDao.findAWBStockDetailsForPrint(any(StockFilterVO.class))).thenReturn(rngVOs);

    assertThrows(
        StockBusinessException.class,
        () -> findAWBStockDetailsForPrintFeature.perform(stockFilterVO));
  }

  @Test
  void shouldReturnRangeVoOnPerformSuccess() throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(loginProfile.getCompanyCode()).thenReturn("AV");
    doReturn(new StockHolderVO())
        .when(stockDao)
        .findStockHolderDetails(any(String.class), any(String.class));

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setCompanyCode("AV");
    List<RangeVO> rngVOs = new ArrayList<RangeVO>();
    RangeVO rangeVo = new RangeVO();
    rangeVo.setAirlineIdentifier(12345);
    rangeVo.setStartRange("123");
    rangeVo.setEndRange("345");

    rngVOs.add(rangeVo);
    doReturn(rngVOs).when(stockDao).findAWBStockDetailsForPrint(stockFilterVO);
    List<AirlineModel> airlineModels = new ArrayList<>();
    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(12345);
    airlineModel.setAwbCheckDigit(1234567);
    AirlineValidityDetailsModel airlineValidityDetailsModel = new AirlineValidityDetailsModel();
    airlineValidityDetailsModel.setThreeNumberCode("1234567890");
    Set<AirlineValidityDetailsModel> airlineValidityDetailsModels = new HashSet<>();
    airlineValidityDetailsModels.add(airlineValidityDetailsModel);
    airlineModel.airlineValidityDetails(airlineValidityDetailsModels);
    airlineModels.add(airlineModel);
    doReturn(airlineModels).when(findAWBStockDetailsForPrintFeature).findAirlineModels(rngVOs);

    Collection<RangeVO> rangeVosRes = findAWBStockDetailsForPrintFeature.perform(stockFilterVO);
    assertEquals(1, rangeVosRes.size());
  }

  @Test
  void shouldReturnRangeVoOnPerformSuccessWhenAirlineIndentifierIsZero()
      throws StockBusinessException {

    when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
    when(loginProfile.getCompanyCode()).thenReturn("AV");
    doReturn(new StockHolderVO())
        .when(stockDao)
        .findStockHolderDetails(any(String.class), any(String.class));

    StockFilterVO stockFilterVO = new StockFilterVO();
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setCompanyCode("AV");
    List<RangeVO> rngVOs = new ArrayList<RangeVO>();
    RangeVO rangeVo = new RangeVO();
    rangeVo.setAirlineIdentifier(0);
    rangeVo.setStartRange("123");
    rangeVo.setEndRange("345");

    rngVOs.add(rangeVo);
    doReturn(rngVOs).when(stockDao).findAWBStockDetailsForPrint(stockFilterVO);
    List<AirlineModel> airlineModels = new ArrayList<>();
    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(12345);
    airlineModel.setAwbCheckDigit(1234567);
    AirlineValidityDetailsModel airlineValidityDetailsModel = new AirlineValidityDetailsModel();
    airlineValidityDetailsModel.setThreeNumberCode("1234567890");
    Set<AirlineValidityDetailsModel> airlineValidityDetailsModels = new HashSet<>();
    airlineValidityDetailsModels.add(airlineValidityDetailsModel);
    airlineModel.airlineValidityDetails(airlineValidityDetailsModels);
    airlineModels.add(airlineModel);
    doReturn(airlineModels).when(findAWBStockDetailsForPrintFeature).findAirlineModels(rngVOs);

    Collection<RangeVO> rangeVosRes = findAWBStockDetailsForPrintFeature.perform(stockFilterVO);
    assertEquals(1, rangeVosRes.size());
  }

  @Test
  void shouldFindAirlineModelsFromAirlineValidityService() {

    List<RangeVO> rngVOs = new ArrayList<RangeVO>();
    RangeVO rangeVo = new RangeVO();
    rangeVo.setAirlineIdentifier(12345);
    rngVOs.add(rangeVo);

    List<AirlineModel> airlineModelList = new ArrayList<>();
    AirlineModel airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(12345);
    airlineModelList.add(airlineModel);

    when(airlineWebcomponent.findAirlineValidityDetails(any(List.class)))
        .thenReturn(airlineModelList);

    Collection<AirlineModel> rangeVosRes =
        findAWBStockDetailsForPrintFeature.findAirlineModels(rngVOs);
    assertEquals(1, rangeVosRes.size());
  }

  @Test
  void shouldReturnEmptyListFromAirlineValidityService() {

    List<RangeVO> rngVOs = new ArrayList<RangeVO>();
    Collection<AirlineModel> rangeVosRes =
        findAWBStockDetailsForPrintFeature.findAirlineModels(rngVOs);
    assertEquals(0, rangeVosRes.size());
  }
}
