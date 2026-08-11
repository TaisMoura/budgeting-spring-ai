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
        var transaction1 =
                new Transaction("Compra no mercado", 5000, Category.GROCERIES);

        var transaction2 =
                new Transaction("Medicamentos", 3000, Category.PHARMA);

        var transaction3 =
                new Transaction("Compras da semana", 8000, Category.GROCERIES);

        var transaction4 =
                new Transaction("Abastecimento do carro", 10000, Category.AUTO);

        when(transactionRepository.findAll())
                .thenReturn(List.of(
                        transaction1,
                        transaction2,
                        transaction3,
                        transaction4
                ));

        FinancialSummaryOutput output = useCase.execute();

        assertEquals(4, output.totalTransactions());
        assertEquals(260.0, output.totalAmount());
        assertEquals(65.0, output.averageAmount());
        assertEquals(100.0, output.highestAmount());
        assertEquals(30.0, output.lowestAmount());
    }

    @Test
    void shouldReturnZerosWhenThereAreNoTransactions() {
        when(transactionRepository.findAll())
                .thenReturn(List.of());

        FinancialSummaryOutput output = useCase.execute();

        assertEquals(0, output.totalTransactions());
        assertEquals(0.0, output.totalAmount());
        assertEquals(0.0, output.averageAmount());
        assertEquals(0.0, output.highestAmount());
        assertEquals(0.0, output.lowestAmount());
    }
}
