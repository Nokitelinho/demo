package com.ibsplc.neoicargo.stock.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.BlacklistStockQueryMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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
public class BlacklistStockDaoImplTest {

  @Autowired private BlacklistStockQueryMapper blacklistStockQueryMapper;

  private BlacklistStockDao blacklistStockDao;

  @BeforeEach
  void setUp() {
    blacklistStockDao = new BlacklistStockDaoImpl(blacklistStockQueryMapper);
  }

  @Test
  void shouldFindRangeForTransfer() {
    // Given
    var vo =
        BlacklistStockVO.builder()
            .companyCode("IBS")
            .stockHolderCode("HQ")
            .airlineIdentifier(1171)
            .documentType("AWB")
            .documentSubType("S")
            .rangeFrom("6867676")
            .rangeTo("6867678")
            .build();

    // When
    var result = blacklistStockDao.findBlackListRangesFromTransit(vo);

    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldFindAlreadyBlackListed() {
    // Given
    var vo =
        BlacklistStockVO.builder()
            .companyCode("AV")
            .documentType("AWB")
            .airlineIdentifier(1000)
            .rangeFrom("4005020")
            .rangeTo("4005060")
            .build();

    // When
    var result = blacklistStockDao.alreadyBlackListed(vo);

    // Then
    assertTrue(result);
  }

  @Test
  void shouldFindNotAlreadyBlackListed() {
    // Given
    var vo =
        BlacklistStockVO.builder()
            .companyCode("XX")
            .documentType("AWB")
            .airlineIdentifier(1000)
            .rangeFrom("5004999")
            .rangeTo("5005011")
            .build();

    // When
    var result = blacklistStockDao.alreadyBlackListed(vo);

    // Then
    assertFalse(result);
  }
}
