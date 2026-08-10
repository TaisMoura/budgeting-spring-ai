package dio.budgeting.application;

import dio.budgeting.application.output.FinancialSummaryOutput;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFinancialSummaryUseCase {

    private final TransactionRepository transactionRepository;

    public GetFinancialSummaryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(
            name = "get-financial-summary",
            description = "Calcula um resumo financeiro das transações registradas"
    )
    public FinancialSummaryOutput execute() {
        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty()) {
            return new FinancialSummaryOutput(
                    0,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );
        }

        double totalAmount = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .sum() / 100.0;

        double averageAmount = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .average()
                .orElse(0.0) / 100.0;

        double highestAmount = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .max()
                .orElse(0L) / 100.0;

        double lowestAmount = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .min()
                .orElse(0L) / 100.0;

        return new FinancialSummaryOutput(
                transactions.size(),
                totalAmount,
                averageAmount,
                highestAmount,
                lowestAmount
        );
    }
}
