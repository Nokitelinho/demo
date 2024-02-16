package com.ibsplc.neoicargo.stock.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.StockRequestOALDao;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestOALRepository;
import com.ibsplc.neoicargo.stock.mapper.StockRequestOALMapper;
import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.time.ZonedDateTime;
import java.util.List;
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
class StockRequestOALDaoImplTest {

  @Autowired private StockRequestOALRepository repository;
  @Autowired private StockRequestOALMapper mapper;

  private StockRequestOALDao stockRequestOALDao;

  @BeforeEach
  void setUp() {
    stockRequestOALDao = new StockRequestOALDaoImpl(repository, mapper);
  }

  @Test
  @Order(1)
  void shouldSaveAllStockRequestOAL() {
    var stockRequestOALVO =
        List.of(
            StockRequestOALVO.builder()
                .companyCode("IBS")
                .airportCode("HQ")
                .airlineIdentifier(1172)
                .documentType("AWB")
                .documentSubType("S")
                .serialNumber(1)
                .lastUpdateUser("ICO")
                .lastUpdateTime(ZonedDateTime.now())
                .build(),
            StockRequestOALVO.builder()
                .companyCode("IBS")
                .airportCode("HQ")
                .airlineIdentifier(1172)
                .documentType("AWB")
                .documentSubType("S")
                .serialNumber(2)
                .lastUpdateUser("ICO")
                .lastUpdateTime(ZonedDateTime.now())
                .build());

    // Then
    Assertions.assertDoesNotThrow(() -> stockRequestOALDao.saveAll(stockRequestOALVO));
  }

  @Test
  @Order(2)
  void shouldFindAllStockRequestOAL() {
    var result = stockRequestOALDao.find("IBS", "HQ", "AWB", "S", 1172, 1);
    assertNotNull(result);
  }

  @Test
  @Order(3)
  void shouldNotFindAllStockRequestOAL() {
    var result = stockRequestOALDao.find("IBS", "wrong", "AWB", "S", 1172, 1);
    assertNull(result);
  }
}
