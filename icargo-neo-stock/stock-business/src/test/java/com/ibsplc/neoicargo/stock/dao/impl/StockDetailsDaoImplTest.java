package com.ibsplc.neoicargo.stock.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.StockDetailsDao;
import com.ibsplc.neoicargo.stock.dao.repository.StockDetailsRepository;
import com.ibsplc.neoicargo.stock.mapper.StockDetailsMapper;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class StockDetailsDaoImplTest {

  @Autowired private StockDetailsMapper mapper;
  @Autowired private StockDetailsRepository repository;

  private StockDetailsDao stockDetailsDao;

  @BeforeEach
  void setUp() {
    stockDetailsDao = new StockDetailsDaoImpl(mapper, repository);
  }

  @Test
  @Order(1)
  void shouldSaveStockDetails() {
    // Given
    var stockDetails =
        StockDetailsVO.builder()
            .companyCode("IBS")
            .stockHolderCode("HQ")
            .documentType("AWB")
            .documentSubType("S")
            .transactionDate(ZonedDateTime.now())
            .lastUpdateUser("ICO")
            .lastUpdateTime(ZonedDateTime.now())
            .build();

    // Then
    Assertions.assertDoesNotThrow(() -> stockDetailsDao.save(stockDetails));
  }

  @Test
  @Order(2)
  void shouldFindStockDetails() {
    var result =
        stockDetailsDao.find(
            "IBS", "HQ", "AWB", "S", BaseMapper.convertZonedDateTimeToInt(ZonedDateTime.now()));
    assertNotNull(result);
  }

  @Test
  @Order(3)
  void shouldNotFindStockDetails() {
    var result = stockDetailsDao.find("IBS", "Wrong", "AWB", "S", 20230113);
    assertNull(result);
  }
}
