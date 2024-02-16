package com.ibsplc.neoicargo.stock.component.feature.findstockholders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineValidityDetailsModel;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockHolderDetailsModelMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class FindStockHoldersFeatureTest {

  @InjectMocks private FindStockHoldersFeature findStockHoldersFeature;
  @Mock private StockDao stockDao;
  @Mock private AirlineWebComponent airlineComponent;

  @Spy
  private final StockHolderDetailsModelMapper stockHolderDetailsModelMapper =
      Mappers.getMapper(StockHolderDetailsModelMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindStockHolders() {
    // Given
    var filterVO = new StockHolderFilterVO();
    filterVO.setStockHolderType("R");
    filterVO.setCompanyCode("IBS");

    var vos = new ArrayList<>();
    var detailsVO1 = new StockHolderDetailsVO();
    detailsVO1.setCompanyCode("IBS");
    detailsVO1.setStockHolderType("R");
    detailsVO1.setAirlineIdentifier(1134);
    var detailsVO2 = new StockHolderDetailsVO();
    detailsVO2.setCompanyCode("IBS");
    detailsVO2.setStockHolderType("R");
    detailsVO2.setAirlineIdentifier(1777);
    var detailsVO3 = new StockHolderDetailsVO();
    detailsVO3.setCompanyCode("IBS");
    detailsVO3.setStockHolderType("R");
    detailsVO3.setAirlineIdentifier(1777);
    var detailsVO4 = new StockHolderDetailsVO();
    detailsVO4.setCompanyCode("IBS");
    detailsVO4.setStockHolderType("R");
    detailsVO4.setAirlineIdentifier(1001);
    vos.add(detailsVO1);
    vos.add(detailsVO2);
    vos.add(detailsVO3);
    vos.add(detailsVO4);

    var arlMdls = new ArrayList<>();
    var mdl1 = new AirlineModel();
    var arlVldDtls1 = new AirlineValidityDetailsModel();
    arlVldDtls1.setThreeNumberCode("134");
    mdl1.setAirlineIdentifier(1134);
    mdl1.setAirlineValidityDetails(new HashSet<>());
    mdl1.getAirlineValidityDetails().add(arlVldDtls1);
    var mdl2 = new AirlineModel();
    var arlVldDtls2 = new AirlineValidityDetailsModel();
    arlVldDtls2.setThreeNumberCode("777");
    mdl2.setAirlineIdentifier(1777);
    mdl2.setAirlineValidityDetails(new HashSet<>());
    mdl2.getAirlineValidityDetails().add(arlVldDtls2);
    arlMdls.add(mdl1);
    arlMdls.add(mdl2);

    // When
    doReturn(vos).when(stockDao).findStockHolders(any(StockHolderFilterVO.class));
    doReturn(arlMdls).when(airlineComponent).findAirlineValidityDetails(any(List.class));

    // Then
    var result = findStockHoldersFeature.perform(filterVO);
    assertThat(result).hasSize(4);
    assertThat(result.get(0).getAwbPrefix()).isEqualTo("134");
    assertThat(result.get(1).getAwbPrefix()).isEqualTo("777");
    verify(stockDao).findStockHolders(any(StockHolderFilterVO.class));
    verify(airlineComponent).findAirlineValidityDetails(any(List.class));
  }

  @Test
  void shouldNotReturnStockHolders() {

    // When
    doReturn(List.of()).when(stockDao).findStockHolders(any(StockHolderFilterVO.class));
    doReturn(List.of()).when(airlineComponent).findAirlineValidityDetails(any(List.class));

    // Then
    var page = findStockHoldersFeature.perform(new StockHolderFilterVO());
    assertThat(page).isEmpty();
  }
}
