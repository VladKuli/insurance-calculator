package insurance.quote.calculator.core.strategy;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.PriceBreakdown;
import insurance.quote.calculator.core.domain.enums.InsuranceType;

public interface PricingStrategy {

    InsuranceType getType();

    PriceBreakdown calculatePrice(QuoteCalculationCommand request);

}
