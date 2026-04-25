package insurance.quote.calculator.core.factory.impl;

import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import insurance.quote.calculator.core.factory.PricingStrategyFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PricingStrategyFactoryImpl implements PricingStrategyFactory {

    private final Map<InsuranceType, PricingStrategy> strategies = new HashMap<>();

    public PricingStrategyFactoryImpl(List<PricingStrategy> strategyList) {
        for (PricingStrategy strategy : strategyList) {
            strategies.put(strategy.getType(), strategy);
        }
    }

    @Override
    public PricingStrategy getStrategy(InsuranceType insuranceType) {
        var strategy = strategies.get(insuranceType);

        if (strategy == null) {
            throw new IllegalArgumentException("No pricing strategy found for type: " + insuranceType);
        }

        return strategy;
    }

}
