package dio.budgeting.application.output;

public record FinancialSummaryOutput(
        int totalTransactions,
        double totalAmount,
        double averageAmount,
        double highestAmount,
        double lowestAmount
) {
}
