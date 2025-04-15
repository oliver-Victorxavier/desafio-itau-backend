package desafio.itau.springboot.service;

import desafio.itau.springboot.model.Transaction;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {

    private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    private final Counter transactionCounter;
    private final Timer statsCalculationTimer;

    public TransactionService(Counter transactionCounter, MeterRegistry meterRegistry) {
        this.transactionCounter = transactionCounter;
        // Configura um timer com tags para melhor organização das métricas
        // e para facilitar a identificação de onde o tempo está sendo gasto
        this.statsCalculationTimer = Timer.builder("transactions.stats.calculation.time")
                .description("Tempo gasto no cálculo de estatísticas de transações")
                .tag("service", "transaction")
                .tag("operation", "statistics")
                .register(meterRegistry);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transactionCounter.increment();
    }

    public void clearTransactions() {
        transactions.clear();
    }

    public DoubleSummaryStatistics getStatistics() {
        // Usa o timer para medir o tempo de execução do cálculo de estatísticas
        return statsCalculationTimer.record(() -> {
            OffsetDateTime now = OffsetDateTime.now();
            return transactions.stream()
                    .filter(t -> t.getDataHora().isAfter(now.minusSeconds(60)))
                    .mapToDouble(Transaction::getValor)
                    .summaryStatistics();
        });
    }
}
