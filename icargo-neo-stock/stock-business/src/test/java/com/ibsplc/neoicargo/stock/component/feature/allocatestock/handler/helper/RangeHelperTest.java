package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangesForMergeFilterVO;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class RangeHelperTest {

  @InjectMocks private RangeHelper rangeHelper;

  @Mock private DocumentTypeProxy documentTypeProxy;
  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldSplitRanges() {
    // When
    doReturn(List.of(new SharedRangeVO()))
        .when(documentTypeProxy)
        .splitRanges(anyList(), anyList());

    // Then
    var result =
        Assertions.assertDoesNotThrow(
            () -> rangeHelper.splitRanges(Set.of(new RangeVO()), List.of(new RangeVO())));
    assertNotNull(result);
  }

  @Test
  void shouldFindRangesForMerge() {
    // Given
    var inputRanges =
        List.of(
            RangeVO.builder()
                .startRange("1001000")
                .endRange("1001100")
                .operationFlag("E")
                .isManual(true)
                .build(),
            RangeVO.builder()
                .startRange("2000000")
                .endRange("1000999")
                .operationFlag("S")
                .isManual(false)
                .build());

    var rangesForMerge =
        List.of(
            RangeVO.builder().asciiStartRange(1001101).asciiEndRange(1001105).build(),
            RangeVO.builder().asciiStartRange(1000000).asciiEndRange(1000999).build());

    // When
    doReturn(rangesForMerge).when(rangeDao).findRangesForMerge(any(RangesForMergeFilterVO.class));

    // Then
    var result = rangeHelper.findRangesForMerge(inputRanges);

    assertEquals(2, result.size());
  }
}
