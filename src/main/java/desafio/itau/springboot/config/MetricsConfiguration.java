package desafio.itau.springboot.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    private final MeterRegistry meterRegistry;

    public MetricsConfiguration(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public Counter transactionCounter() {
        return Counter.builder("transactions.count")
                .description("Número total de transações processadas")
                .register(meterRegistry);
    }
}