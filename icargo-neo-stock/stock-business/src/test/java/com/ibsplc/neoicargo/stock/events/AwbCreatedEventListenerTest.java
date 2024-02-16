package com.ibsplc.neoicargo.stock.events;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.ibsplc.neoicargo.stock.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.stock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
public class AwbCreatedEventListenerTest {

  @InjectMocks private AwbCreatedEventListener listener;
  @Mock private StockService service;
  @Spy private final AwbEventMapper mapper = Mappers.getMapper(AwbEventMapper.class);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void handleAwbEvents() {
    AWBCreatedEvent event = new AWBCreatedEvent();
    event.setShipmentPrefix("777");
    event.setMasterDocumentNumber("4532342");
    assertDoesNotThrow(() -> listener.handleAwbEvent(event));
  }
}
