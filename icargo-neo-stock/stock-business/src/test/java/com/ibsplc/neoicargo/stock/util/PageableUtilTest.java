package com.ibsplc.neoicargo.stock.util;

import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PageableUtilTest {

  @Test
  void getTotalRecordCountTest() {
    // Given
    var stockAgentVO = new StockAgentVO();
    stockAgentVO.setTotalRecordCount(100);
    var stockAgentVOs = List.of(stockAgentVO);

    // When
    var result = PageableUtil.getTotalRecordCount(stockAgentVOs);

    // Then
    Assertions.assertThat(result).isEqualTo(100);

    // When
    result = PageableUtil.getTotalRecordCount(null);

    // Then
    Assertions.assertThat(result).isEqualTo(0);
  }

  @Test
  void getPageOffsetTest() {
    // Given
    var pageNumber = 2;
    var pageSize = 25;

    // When
    var result = PageableUtil.getPageOffset(pageNumber, pageSize);

    // Then
    Assertions.assertThat(result).isEqualTo(25);
  }
}
