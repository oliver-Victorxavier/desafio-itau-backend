package desafio.itau.springboot.health;

import desafio.itau.springboot.model.Transaction;
import desafio.itau.springboot.service.TransactionService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TransactionHealthIndicator implements HealthIndicator {

    private final TransactionService service;

    public TransactionHealthIndicator(TransactionService service) {
        this.service = service;
    }
    @Override
    public Health health() {
        try {
            service.addTransaction(new Transaction(1.7, OffsetDateTime.now()));
            var stats = service.getStatistics();

            return Health.up()
                    .withDetail("lastMinuteTransactions", stats.getCount())
                    .withDetail("serviceStatus", "Serviço de transações operacional")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("serviceStatus", "Serviço de transações com falha")
                    .build();
        }
    }
}
