package insurance.quote.calculator.core.strategy.impl;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.RealEstateQuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.risk_assessment.RiskAssessmentService;
import insurance.quote.calculator.core.domain.PriceBreakdown;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RealEstateInsurancePricingStrategy implements PricingStrategy {

    private final List<RiskAssessmentService> riskAssessmentServices;

    @Override
    public InsuranceType getType() {
        return InsuranceType.REAL_ESTATE;
    }

    @Override
    public PriceBreakdown calculatePrice(QuoteCalculationCommand command) {
        if (!(command instanceof RealEstateQuoteCalculationCommand realEstateCommand)) {
            throw new IllegalArgumentException("Real estate pricing requires real estate quote command");
        }

        var risk = getRiskAssessmentService(command).assessRisk(realEstateCommand);
        var basePrice = calculateBasePrice(realEstateCommand);
        var riskMultiplier = risk.getMultiplier();
        var coverageMultiplier = calculateCoverageMultiplier(realEstateCommand);
        var additionalCharge = additionalCharge(realEstateCommand);
        var discount = calculateDiscount(realEstateCommand);

        var finalPrice = basePrice
                .multiply(riskMultiplier)
                .multiply(coverageMultiplier)
                .add(additionalCharge)
                .subtract(discount);

        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO;
        }

        finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

        return new PriceBreakdown(
                basePrice,
                riskMultiplier,
                coverageMultiplier,
                additionalCharge,
                discount,
                finalPrice
        );

    }

    private RiskAssessmentService getRiskAssessmentService(QuoteCalculationCommand command) {
        return riskAssessmentServices.stream()
                .filter(service -> service.supports(command.getInsuranceType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No risk assessment service found for insurance type: "
                        + command.getInsuranceType()));
    }

    private BigDecimal calculateBasePrice(RealEstateQuoteCalculationCommand command) {
        var fixedFee = BigDecimal.valueOf(50);
        var annualRate = BigDecimal.valueOf(0.03);
        var monthsInYear = BigDecimal.valueOf(12);

        return fixedFee
                .add(command.propertyValue().multiply(annualRate)
                        .divide(monthsInYear, 2, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateCoverageMultiplier(RealEstateQuoteCalculationCommand command) {
       return command.coverageLevel()
               .getMultiplier()
               .setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal additionalCharge(RealEstateQuoteCalculationCommand command) {
        var additionalCharge = BigDecimal.ZERO;

        if (command.ageOfProperty() > 30) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(20));
        }

        return additionalCharge.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscount(RealEstateQuoteCalculationCommand command) {
        if (command.ageOfProperty() < 10 && command.coverageLevel() == CoverageLevel.PREMIUM) {
            return BigDecimal.valueOf(15);
        }
        return BigDecimal.ZERO;
    }

}
