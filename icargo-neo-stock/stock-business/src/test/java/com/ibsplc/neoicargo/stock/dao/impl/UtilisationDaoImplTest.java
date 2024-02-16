package com.ibsplc.neoicargo.stock.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.UtilisationQueryMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class UtilisationDaoImplTest {

  @Autowired private UtilisationQueryMapper queryMapper;

  private UtilisationDao dao;

  @BeforeEach
  void setUp() {
    dao = new UtilisationDaoImpl(queryMapper);
  }

  @Test
  void shouldValidateStockPeriod() {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .companyCode("AV")
            .airlineIdentifier(1729)
            .documentType("AWB")
            .ranges(
                List.of(
                    RangeVO.builder().startRange("1000000").endRange("4000000").build(),
                    RangeVO.builder().startRange("5000000").endRange("6000000").build()))
            .build();

    // When
    var result = dao.validateStockPeriod(stockAllocationVO, 1);

    // Then
    assertEquals(1, result.size());
    assertEquals(1005000, result.get(0));
  }

  @Test
  void shouldFindStockUtilisationForRange() {
    // Given
    var filterVO =
        UtilisationFilterVO.builder()
            .companyCode("AV")
            .stockHolderCode("UTILIZATION")
            .airlineIdentifier(9999)
            .documentType("AWB")
            .documentSubType("S")
            .ranges(
                List.of(
                    RangeFilterVO.builder()
                        .asciiStartRange(1004000L)
                        .asciiEndRange(1006000L)
                        .build()))
            .build();

    // When
    var result = dao.findStockUtilisationForRange(filterVO);

    // Then
    assertEquals(1L, result);
  }
}
