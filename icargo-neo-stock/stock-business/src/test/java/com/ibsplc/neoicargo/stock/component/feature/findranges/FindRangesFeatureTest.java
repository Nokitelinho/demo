package com.ibsplc.neoicargo.stock.component.feature.findranges;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DOCUMENT_TYPE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindRangesFeatureTest {
  @InjectMocks private FindRangesFeature findRangesFeature;
  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private RangeFilterVO populateRangeFilterVO() {
    var vo = new RangeFilterVO();
    vo.setStartRange("0");
    vo.setEndRange("9000000");
    vo.setAirlineIdentifier(1191);

    return vo;
  }

  private List<RangeVO> populateRangeVOs() {
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("2000000");
    rangeVO.setMasterDocumentNumbers(new ArrayList<>());

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("3000000");
    rangeVO2.setEndRange("4000000");
    rangeVO2.setMasterDocumentNumbers(new ArrayList<>());

    return List.of(rangeVO, rangeVO2);
  }

  @Test
  void shouldReturnRanges() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(populateRangeFilterVO());

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRangesComparingStartRanges() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setStartRange("8000000");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("8000000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRangesComparingEndRanges() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setEndRange("10000");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("10000")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRanges_If_EndRange_Is_Null() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setEndRange(null);

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRanges_If_StartRange_Is_Null() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setStartRange(null);

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRanges_If_NumberOfDocuments_Not_Empty() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("100");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldReturnRanges_If_NumberOfDocuments_Not_Empty2() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("1");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    // When
    doReturn(List.of(rangeVO)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001100")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldNotReturnRanges_If_NumberOfDocuments_Is_Zero() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("0");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");
    rangeVO.setNumberOfDocuments(10);

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("2001000");
    rangeVO2.setEndRange("2001100");
    rangeVO2.setNumberOfDocuments(10);

    // When
    doReturn(List.of(rangeVO, rangeVO2)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertEquals(0, rangeVOs.size());
  }

  @Test
  void shouldNotReturnRanges_If_NumberOfDocumentsINotZero() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("1");

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");
    rangeVO.setNumberOfDocuments(10);

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("2001000");
    rangeVO2.setEndRange("2001100");
    rangeVO2.setNumberOfDocuments(10);

    // When
    doReturn(List.of(rangeVO, rangeVO2)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);
    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1001000") && e.getEndRange().equals("1001091")));

    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldNotReturnRanges_If_DocumentType_Is_AWB() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("1");
    rangeFilterVO.setDocumentType(DOCUMENT_TYPE);

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("1001100");
    rangeVO.setNumberOfDocuments(10);

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("2000000");
    rangeVO2.setEndRange("3000000");
    rangeVO2.setNumberOfDocuments(10);

    // When
    doReturn(List.of(rangeVO, rangeVO2)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1000000") && e.getEndRange().equals("1001091")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void shouldNotReturnRanges_If_DocumentType_Not_AWB() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("1");
    rangeFilterVO.setDocumentType(null);

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("1001100");
    rangeVO.setNumberOfDocuments(10);

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("2000000");
    rangeVO2.setEndRange("3000000");
    rangeVO2.setNumberOfDocuments(10);

    // When
    doReturn(List.of(rangeVO, rangeVO2)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(populateRangeVOs()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1000000") && e.getEndRange().equals("1001091")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }

  @Test
  void enreachMasterDocumentNumberTest() {
    // Given
    var rangeFilterVO = populateRangeFilterVO();
    rangeFilterVO.setNumberOfDocuments("1");
    rangeFilterVO.setDocumentType(DOCUMENT_TYPE);

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("1001100");
    rangeVO.setNumberOfDocuments(10);

    var rangeVO2 = new RangeVO();
    rangeVO2.setStartRange("2000000");
    rangeVO2.setEndRange("3000000");
    rangeVO2.setNumberOfDocuments(10);

    // When
    doReturn(List.of(rangeVO, rangeVO2)).when(rangeDao).findRanges(any(RangeFilterVO.class));
    doReturn(new ArrayList<>()).when(rangeDao).findRangesForViewRange(any(StockFilterVO.class));

    // Then
    var rangeVOs = findRangesFeature.perform(rangeFilterVO);

    assertTrue(
        rangeVOs.stream()
            .anyMatch(
                e -> e.getStartRange().equals("1000000") && e.getEndRange().equals("1001091")));
    assertThat(rangeVOs.size()).isEqualTo(1);
  }
}
