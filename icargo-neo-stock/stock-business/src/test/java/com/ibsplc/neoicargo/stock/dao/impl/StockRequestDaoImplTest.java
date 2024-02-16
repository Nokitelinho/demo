package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockRequest;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
public class StockRequestDaoImplTest {
  @Autowired private StockRequestDao stockRequestDao;
  @Autowired private StockRepository stockRepository;

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: rejectStockRequests tests")
  class RejectStockRequests {
    @Test
    @Transactional
    void shouldRejectStockRequests() {
      // Given
      var stockRequestVO1 = getMockStockRequestVO("XX", "1111", "update1", "127-1231231");
      var stockRequestVO2 = getMockStockRequestVO("RR", "1111", "update2", "127-1231231");

      // When
      stockRequestDao.rejectStockRequests(List.of(stockRequestVO1, stockRequestVO2));

      // Then
      var stockRequest2 =
          stockRequestDao.findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
              stockRequestVO2.getCompanyCode(),
              stockRequestVO2.getRequestRefNumber(),
              Integer.parseInt(stockRequestVO2.getAirlineIdentifier()));
      assertTrue(stockRequest2.isEmpty());
      var stockRequest1 =
          stockRequestDao.findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
              stockRequestVO1.getCompanyCode(),
              stockRequestVO1.getRequestRefNumber(),
              Integer.parseInt(stockRequestVO1.getAirlineIdentifier()));
      assertFalse(stockRequest1.isEmpty());
      var updated = stockRequest1.get();
      assertEquals(stockRequestVO1.getApprovalRemarks(), updated.getApprovalRemarks());
      assertEquals(StockConstant.STATUS_REJECTED, updated.getStatus());
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: rejectStockRequests tests")
  class SaveStockRequests {
    @Test
    @Transactional
    void shouldSaveStockRequests() {
      // Given
      var stockRequest = getMockStockRequest("AV", 1000, "125-12344321", "PERIOD");
      var stock =
          stockRepository
              .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
                  "AV", "PERIOD", 1000, "AWB", "S");
      stockRequest.setStock(stock.get());
      // When
      stockRequestDao.save(stockRequest);

      // Then

      var possibleStockRequest =
          stockRequestDao.findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
              stockRequest.getCompanyCode(),
              stockRequest.getRequestRefNumber(),
              stockRequest.getAirlineIdentifier());
      assertFalse(possibleStockRequest.isEmpty());
    }
  }
}
