package insurance.quote.calculator.web.dto.response;

public record QuoteResponse(
        Long id,
        String insuranceType,
        String riskLevel,
        String status,
        String customerName,
        PriceBreakdownResponse priceBreakdown
) {
}
