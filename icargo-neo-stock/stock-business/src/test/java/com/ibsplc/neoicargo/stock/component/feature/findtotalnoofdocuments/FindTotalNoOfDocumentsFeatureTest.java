package com.ibsplc.neoicargo.stock.component.feature.findtotalnoofdocuments;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getStockFilterVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindTotalNoOfDocumentsFeatureTest {
  @InjectMocks private FindTotalNoOfDocumentsFeature findTotalNoOfDocumentsFeature;

  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnTotalNoOfDocuments() {
    // Given
    var stockFilterVO = getStockFilterVO(false, "N", "S", "AWB", 1234, "HQ", "AV");

    // When
    doReturn(2).when(rangeDao).findTotalNoOfDocuments(stockFilterVO);

    // Then
    var result = findTotalNoOfDocumentsFeature.perform(stockFilterVO);
    assertEquals(2, result);
  }
}
