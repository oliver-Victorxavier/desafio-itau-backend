package desafio.itau.springboot.config;

import desafio.itau.springboot.service.TransactionService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TransactionsMetricsConfiguration {

    private final TransactionService service;
    private final MeterRegistry meterRegistry;

    public TransactionsMetricsConfiguration(TransactionService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }
    @Bean
    public Counter transactionCounter() {
        return Counter.builder("transactions.count")
                .description("Número total de transações processadas")
                .register(meterRegistry);
    }
    @Bean
    public void registerTransactionsMetrics(){
        Gauge.builder("transactions.last_minute.count", service,
                service -> service.getStatistics().getCount())
                .description("Total de transações no último minuto")
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


