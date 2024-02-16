package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.framework.core.async.AsyncInvoker;
import com.ibsplc.neoicargo.framework.event.EventBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(JUnitPlatform.class)
class CcaEventsProducerTest {

    private CcaEventsProducer ccaEventsProducer;

    @BeforeEach
    public void setup() {
        ccaEventsProducer = new CcaEventsProducer(spy(CcaEventsProducer.CcaEventsSource.class));
        ReflectionTestUtils.setField(ccaEventsProducer, "asyncInvoker", mock(AsyncInvoker.class));
        ReflectionTestUtils.setField(ccaEventsProducer, "eventBuilder", mock(EventBuilder.class));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void publishEventShouldNotThrowException() {
        // Given
        var key = "key";
        var event = new CCACreateEvent();

        //When + Then
        assertDoesNotThrow(() -> ccaEventsProducer.publishEvent(key, event));
    }
}