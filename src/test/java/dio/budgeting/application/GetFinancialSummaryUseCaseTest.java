package dio.budgeting.application;

import dio.budgeting.application.output.FinancialSummaryOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetFinancialSummaryUseCaseTest {

    private final TransactionRepository transactionRepository =
            mock(TransactionRepository.class);

    private final GetFinancialSummaryUseCase useCase =
            new GetFinancialSummaryUseCase(transactionRepository);

    @Test
    void shouldCalculateFinancialSummary() {

        var transactions = List.of(
                new Transaction("Supermercado", 10000, Category.GROCERIES),
                new Transaction("Farmácia", 5000, Category.PHARMA),
                new Transaction("Combustível", 3000, Category.AUTO),
                new Transaction("Mercado", 10000, Category.GROCERIES)
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        FinancialSummaryOutput result = useCase.execute();

        assertEquals(4, result.totalTransactions());
        assertEquals(280.0, result.totalAmount());
        assertEquals(70.0, result.averageAmount());
        assertEquals(100.0, result.highestAmount());
        assertEquals(30.0, result.lowestAmount());
    }

    @Test
    void shouldReturnEmptySummaryWhenThereAreNoTransactions() {

        when(transactionRepository.findAll())
                .thenReturn(List.of());

        FinancialSummaryOutput result = useCase.execute();

        assertEquals(0, result.totalTransactions());
        assertEquals(0.0, result.totalAmount());
        assertEquals(0.0, result.averageAmount());
        assertEquals(0.0, result.highestAmount());
        assertEquals(0.0, result.lowestAmount());
    }
}
