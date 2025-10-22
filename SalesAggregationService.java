package com.oreo;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SalesAggregationService {

    private final ApplicationEventPublisher eventPublisher;

    public SalesAggregationService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void generateWeeklySummary() {
        String summary = "Resumen semanal generado correctamente.";
        eventPublisher.publishEvent(new WeeklySummaryEvent(summary));

        System.out.println("[Service] Evento WeeklySummaryEvent publicado.");
    }
}