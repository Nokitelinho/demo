package com.ibsplc.neoicargo.stock.events;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.ibsplc.neoicargo.stock.mapper.BookingEventMapper;
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
public class BookingCreatedEventListenerTest {

  @InjectMocks private BookingCreatedEventListener listener;
  @Mock private StockService service;
  @Spy private final BookingEventMapper mapper = Mappers.getMapper(BookingEventMapper.class);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void handleBookingEvents() {
    BookingCreatedEvent event = new BookingCreatedEvent();
    event.setShipmentPrefix("777");
    event.setMasterDocumentNumber("4532342");
    assertDoesNotThrow(() -> listener.handleBookingEvent(event));
  }
}
