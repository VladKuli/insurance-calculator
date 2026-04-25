package insurance.quote.calculator.core.strategy.impl;

import insurance.quote.calculator.core.application.command.CarQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.RiskAssessmentService;
import insurance.quote.calculator.core.domain.PriceBreakdown;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class CarInsurancePricingStrategy implements PricingStrategy {

    private final RiskAssessmentService riskAssessmentService;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private static final BigDecimal HIGH_COVERAGE_MULTIPLIER = BigDecimal.valueOf(1.2);
    private static final BigDecimal NORMAL_COVERAGE_MULTIPLIER = BigDecimal.valueOf(1.0);
    private static final BigDecimal LOW_COVERAGE_MULTIPLIER = BigDecimal.valueOf(0.85);

    private static final BigDecimal OLD_CAR_CHARGE = BigDecimal.valueOf(100);
    private static final BigDecimal ACCIDENT_CHARGE = BigDecimal.valueOf(75);

    private static final BigDecimal HIGH_EXPERIENCE_DISCOUNT = BigDecimal.valueOf(100);
    private static final BigDecimal MEDIUM_EXPERIENCE_DISCOUNT = BigDecimal.valueOf(50);
    private static final BigDecimal ACCIDENT_FREE_DISCOUNT = BigDecimal.valueOf(50);

    @Override
    public InsuranceType getType() {
        return InsuranceType.CAR;
    }

    @Override
    public PriceBreakdown calculatePrice(QuoteCalculationCommand command) {
        if (!(command instanceof CarQuoteCalculationCommand carCommand)) {
            throw new IllegalArgumentException("Car pricing requires car quote command");
        }

        var risk = riskAssessmentService.assessRisk(carCommand);

        var basePrice = calculateBasePrice(carCommand);
        var riskMultiplier = calculateRiskMultiplier(risk);
        var coverageMultiplier = calculateCoverageMultiplier(carCommand);
        var additionalCharges = calculateAdditionalCharges(carCommand);
        var discount = calculateDiscount(carCommand);

        var finalPrice = basePrice
                .multiply(riskMultiplier)
                .multiply(coverageMultiplier)
                .add(additionalCharges)
                .subtract(discount);

        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO;
        }

        finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

        return new PriceBreakdown(
                basePrice,
                riskMultiplier,
                coverageMultiplier,
                additionalCharges,
                discount,
                finalPrice
        );
    }

    private BigDecimal calculateBasePrice(CarQuoteCalculationCommand command) {
        var fixedFee = BigDecimal.valueOf(50);
        var coverageRate = BigDecimal.valueOf(0.02);

        return fixedFee.add(command.coverageAmount().multiply(coverageRate));
    }

    private BigDecimal calculateRiskMultiplier(RiskLevel riskLevel) {
        return switch (riskLevel) {
            case LOW -> BigDecimal.valueOf(0.8);
            case MEDIUM -> BigDecimal.valueOf(1.0);
            case HIGH -> BigDecimal.valueOf(1.5);
        };
    }

    private BigDecimal calculateCoverageMultiplier(CarQuoteCalculationCommand command) {
        var coverageAmount = command.coverageAmount();
        var carValue = command.carValue();

        var coverageRatio = coverageAmount.divide(carValue, 2, RoundingMode.HALF_UP);

        return getCoverageMultiplierByRatio(coverageRatio);
    }

    private BigDecimal getCoverageMultiplierByRatio(BigDecimal coverageRatio) {
        if (coverageRatio.compareTo(BigDecimal.valueOf(0.9)) >= 0) {
            return HIGH_COVERAGE_MULTIPLIER;
        }

        if (coverageRatio.compareTo(BigDecimal.valueOf(0.5)) >= 0) {
            return NORMAL_COVERAGE_MULTIPLIER;
        }

        return LOW_COVERAGE_MULTIPLIER;
    }

    private BigDecimal calculateAdditionalCharges(CarQuoteCalculationCommand command) {
        var oldCarCharge = command.carAge() > 10
                ? OLD_CAR_CHARGE
                : ZERO;

        var accidentCharge = ACCIDENT_CHARGE.multiply(
                BigDecimal.valueOf(command.accidentCount())
        );

        return oldCarCharge.add(accidentCharge);
    }

    private BigDecimal calculateDiscount(CarQuoteCalculationCommand command) {
        var experienceDiscount = calculateExperienceDiscount(command.drivingExperienceYears());

        var accidentFreeDiscount = command.accidentCount() == 0
                ? ACCIDENT_FREE_DISCOUNT
                : ZERO;

        return experienceDiscount.add(accidentFreeDiscount);
    }

    private BigDecimal calculateExperienceDiscount(Integer drivingExperienceYears) {
        if (drivingExperienceYears >= 10) {
            return HIGH_EXPERIENCE_DISCOUNT;
        }

        if (drivingExperienceYears >= 5) {
            return MEDIUM_EXPERIENCE_DISCOUNT;
        }

        return ZERO;
    }

}
