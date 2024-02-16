package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class RearrangeRangesHelperTest {

  private RearrangeRangesHelper rearrangeRangesHelper = new RearrangeRangesHelper();

  @Test
  void shouldRearrangeRanges() {
    // Given
    var blacklistStockVO = MockVOGenerator.getMockBlacklistStockVO();
    blacklistStockVO.setAsciiRangeFrom(1000008L);
    blacklistStockVO.setAsciiRangeTo(1000009L);
    var range1 = MockVOGenerator.getMockRangeVO("AV", "AWB", "S", 1000001, 1000100);
    var range2 = MockVOGenerator.getMockRangeVO("AV", "AWB", "S", 1000200, 1000300);
    var ranges1 = new ArrayList<RangeVO>();
    var ranges2 = new ArrayList<RangeVO>();
    ranges1.add(range1);
    ranges2.add(range2);
    var stock1 = StockVO.builder().ranges(ranges1).build();
    var stock2 = StockVO.builder().ranges(ranges2).build();
    var input = List.of(stock1, stock2);

    // When
    var result = rearrangeRangesHelper.rearrangeRanges(input, blacklistStockVO);
    var rearrangedRange1 = ((ArrayList<RangeVO>) result.get(0).getRanges()).get(0);
    var rearrangedRange2 = ((ArrayList<RangeVO>) result.get(1).getRanges()).get(0);

    // Then
    assertEquals("1000001", rearrangedRange1.getStartRange());
    assertEquals("1000100", rearrangedRange1.getEndRange());
    assertEquals("1000200", rearrangedRange2.getStartRange());
    assertEquals("1000002", rearrangedRange2.getEndRange());
  }
}
