package desafio.itau.springboot.config;

import desafio.itau.springboot.service.TransactionService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TransactionsMetricsRegister {

    private final TransactionService service;
    private final MeterRegistry meterRegistry;

    public TransactionsMetricsRegister(TransactionService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerMetrics() {
        Gauge.builder("transactions.last_minute.count", service,
                        service -> service.getStatistics().getCount())
                .description("Número de transações no último minuto")
                .register(meterRegistry);

        Gauge.builder("transactions.last_minute.sum", service,
                        service -> service.getStatistics().getSum())
                .description("Valor total das transações no último minuto")
                .register(meterRegistry);

        Gauge.builder("transactions.last_minute.avg", service,
                        service -> service.getStatistics().getAverage())
                .description("Valor médio das transações no último minuto")
                .register(meterRegistry);
    }
}
