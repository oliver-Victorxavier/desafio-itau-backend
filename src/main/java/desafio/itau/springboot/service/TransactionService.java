package desafio.itau.springboot.service;

import desafio.itau.springboot.model.Transaction;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {

    private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    private final Counter transactionCounter;

    public TransactionService(Counter transactionCounter) {
        this.transactionCounter = transactionCounter;
    }
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transactionCounter.increment();
    }
    public void clearTransactions() {
        transactions.clear();
    }
    public DoubleSummaryStatistics getStatistics() {
        OffsetDateTime now = OffsetDateTime.now();
        return transactions.stream()
                .filter(t -> t.getDataHora().isAfter(now.minusSeconds(60)))
                .mapToDouble(Transaction::getValor)
                .summaryStatistics();
    }
}
