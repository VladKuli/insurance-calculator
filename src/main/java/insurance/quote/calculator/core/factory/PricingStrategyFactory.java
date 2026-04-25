package insurance.quote.calculator.core.factory;

import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import jakarta.validation.constraints.NotNull;

public interface PricingStrategyFactory {

    PricingStrategy getStrategy(@NotNull InsuranceType insuranceType);

}
