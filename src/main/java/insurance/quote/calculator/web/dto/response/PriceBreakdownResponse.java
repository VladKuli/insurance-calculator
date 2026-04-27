package insurance.quote.calculator.web.dto.response;

import java.math.BigDecimal;

public record PriceBreakdownResponse(
        BigDecimal basePrice,
        BigDecimal riskMultiplier,
        BigDecimal coverageMultiplier,
        BigDecimal additionalCharges,
        BigDecimal discount,
        BigDecimal finalPrice
) {
}