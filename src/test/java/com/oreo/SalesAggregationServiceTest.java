package com.oreo;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SalesAggregationServiceTest {

    @Test
    void shouldPublishWeeklySummaryEventWhenGeneratingSummary() {

        ApplicationEventPublisher mockPublisher = mock(ApplicationEventPublisher.class);
        SalesAggregationService service = new SalesAggregationService(mockPublisher);

        service.generateWeeklySummary();

        ArgumentCaptor<WeeklySummaryEvent> eventCaptor = ArgumentCaptor.forClass(WeeklySummaryEvent.class);
        verify(mockPublisher, times(1)).publishEvent(eventCaptor.capture());

        WeeklySummaryEvent capturedEvent = eventCaptor.getValue();

        assertThat(capturedEvent).isNotNull();
        assertThat(capturedEvent.getMessage()).contains("Resumen semanal");
    }
}
