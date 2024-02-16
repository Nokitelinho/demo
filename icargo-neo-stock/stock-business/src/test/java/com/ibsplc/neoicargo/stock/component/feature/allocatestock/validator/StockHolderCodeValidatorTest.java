package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_HOLDER_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportWebAPI;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportParameterModel;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockHolderCodeValidatorTest {

  @InjectMocks private StockHolderCodeValidator validator;

  @Mock private AirportWebAPI airportWebAPI;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void isRelatedToStockControlFor_ReturnsTrue() throws BusinessException {
    // Arrange
    String airportCode = "ABC";
    String stockControlFor = "STOCK";

    AirportParameterModel parameterModel = new AirportParameterModel();
    parameterModel.setParameterCode(STOCK_DEFAULTS_STOCK_HOLDER_CODE);
    parameterModel.setParameterValue("STOCKHOLDER");

    Set<AirportParameterModel> parameterModels = new HashSet<>();
    parameterModels.add(parameterModel);

    when(airportWebAPI.findAirportParametersByCode(any(String.class), anyList()))
        .thenReturn(parameterModels);

    // Act
    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);

    // Assert
    assertTrue(result);
  }

  @Test
  void isRelatedToStockControlFor_ReturnsFalse_WhenNonMatchingStockControlFor()
      throws BusinessException {
    // Arrange
    String airportCode = "ABC";
    String stockControlFor = "NON_STOCK";

    AirportParameterModel parameterModel = new AirportParameterModel();
    parameterModel.setParameterCode(STOCK_DEFAULTS_STOCK_HOLDER_CODE);
    parameterModel.setParameterValue("STOCKHOLDER");

    Set<AirportParameterModel> parameterModels = new HashSet<>();
    parameterModels.add(parameterModel);

    when(airportWebAPI.findAirportParametersByCode(any(String.class), anyList()))
        .thenReturn(parameterModels);

    // Act
    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);

    // Assert
    assertFalse(result);
  }

  @Test
  void isRelatedToStockControlFor_NullStockHolderCode_ReturnsFalse() throws BusinessException {
    String airportCode = "XYZ";
    String stockControlFor = "DEF";
    AirportParameterModel parameterModel = new AirportParameterModel();
    parameterModel.setParameterCode(STOCK_DEFAULTS_STOCK_HOLDER_CODE);
    parameterModel.setParameterValue(null);

    Set<AirportParameterModel> parameterModels = new HashSet<>();
    parameterModels.add(parameterModel);
    when(airportWebAPI.findAirportParametersByCode(any(String.class), anyList()))
        .thenReturn(parameterModels);

    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);
    Assert.assertFalse(result);
  }

  @Test
  void isRelatedToStockControlFor_NullStockHolderCode_Returnsnull() throws BusinessException {
    String airportCode = "XYZ";
    String stockControlFor = "DEF";
    Set<AirportParameterModel> parameterModels = null;
    when(airportWebAPI.findAirportParametersByCode(any(String.class), anyList()))
        .thenReturn(parameterModels);

    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsRelatedToStockControlFor_NonMatchingStockHolderCode_ReturnsFalse()
      throws BusinessException {

    String airportCode = "AIRPORT";
    String stockControlFor = "STOCK";
    String stockHolderCode = "OTHER";

    Set<AirportParameterModel> parameterModels = new HashSet<>();
    AirportParameterModel airportParameterModel = new AirportParameterModel();
    airportParameterModel.setParameterCode(STOCK_DEFAULTS_STOCK_HOLDER_CODE);
    airportParameterModel.setParameterValue("OTHER");
    parameterModels.add(airportParameterModel);

    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);

    Assert.assertFalse(result);
  }

  @Test
  void isRelatedToStockControlFor_StockHolderCode_ReturnsFalse1() throws BusinessException {
    String airportCode = "XYZ";
    String stockControlFor = "DEF";
    AirportParameterModel parameterModel = new AirportParameterModel();
    parameterModel.setParameterCode("parameter");
    parameterModel.setParameterValue("Stock");

    Set<AirportParameterModel> parameterModels = new HashSet<>();
    parameterModels.add(parameterModel);
    when(airportWebAPI.findAirportParametersByCode(any(String.class), anyList())).thenReturn(null);

    boolean result = validator.isRelatedToStockControlFor(airportCode, stockControlFor);
    Assert.assertFalse(result);
  }
}
