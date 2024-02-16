package com.ibsplc.neoicargo.stock.audit;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.CREATED_STOCK_HOLDER_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.CREATED_STOCK_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.REMOVED_STOCK_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_REMOVED_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_UPDATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.UPDATED_STOCK_HOLDER_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.UPDATED_STOCK_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAudit;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderAudit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditService;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class StockAuditServiceTest {
  @InjectMocks private StockAuditService stockAuditService;
  @Mock private Mustache.Compiler mustacheCompiler;
  @Mock private Template template;
  @Mock private AuditService auditService;
  @Captor ArgumentCaptor<AuditEvent> auditEvent;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private static Stream<Arguments> provideTestDataForStockEvents() {
    return Stream.of(
        Arguments.of(STOCK_AUDIT_CREATE_TRANSACTION, CREATED_STOCK_TEMPLATE),
        Arguments.of(STOCK_AUDIT_UPDATE_TRANSACTION, UPDATED_STOCK_TEMPLATE),
        Arguments.of(STOCK_AUDIT_REMOVED_TRANSACTION, REMOVED_STOCK_TEMPLATE));
  }

  private static Stream<Arguments> provideTestDataForStockHolderEvents() {
    return Stream.of(
        Arguments.of(STOCK_HOLDER_AUDIT_CREATE_TRANSACTION, CREATED_STOCK_HOLDER_TEMPLATE),
        Arguments.of(STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION, UPDATED_STOCK_HOLDER_TEMPLATE));
  }

  @ParameterizedTest
  @MethodSource("provideTestDataForStockEvents")
  void shouldSaveStockEvents(String transaction, String templateName)
      throws JsonProcessingException {
    // Given
    var event = ((List<AuditEvent>) getMockStockAudit(transaction).getMessage()).get(0);

    // When
    doReturn(template).when(mustacheCompiler).loadTemplate(templateName);
    doReturn("").when(template).execute(anyMap());
    doNothing().when(auditService).saveAuditEvent(event);

    // Then
    Assertions.assertDoesNotThrow(() -> stockAuditService.saveEvent(event));
    verify(auditService, times(1)).saveAuditEvent(auditEvent.capture());
    assertEquals(transaction, auditEvent.getValue().getTransaction());
  }

  @ParameterizedTest
  @MethodSource("provideTestDataForStockHolderEvents")
  void shouldSaveStockHolderEvents(String transaction, String templateName)
      throws JsonProcessingException {
    // Given
    var event = ((List<AuditEvent>) getMockStockHolderAudit(transaction).getMessage()).get(0);

    // When
    doReturn(template).when(mustacheCompiler).loadTemplate(templateName);
    doReturn("").when(template).execute(anyMap());
    doNothing().when(auditService).saveAuditEvent(event);

    // Then
    Assertions.assertDoesNotThrow(() -> stockAuditService.saveEvent(event));
    verify(auditService, times(1)).saveAuditEvent(auditEvent.capture());
    assertEquals(transaction, auditEvent.getValue().getTransaction());
  }

  @Test
  void shouldFailsWhileSave() throws JsonProcessingException {
    // Given
    var event =
        ((List<AuditEvent>) getMockStockAudit(STOCK_AUDIT_CREATE_TRANSACTION).getMessage()).get(0);

    // When
    doReturn(template).when(mustacheCompiler).loadTemplate(CREATED_STOCK_TEMPLATE);
    doReturn("").when(template).execute(anyMap());
    doThrow(RuntimeException.class).when(auditService).saveAuditEvent(event);

    // Then
    Assertions.assertDoesNotThrow(() -> stockAuditService.saveEvent(event));
    verify(auditService, times(1)).saveAuditEvent(auditEvent.capture());
    assertEquals(STOCK_AUDIT_CREATE_TRANSACTION, auditEvent.getValue().getTransaction());
  }
}
