package insurance.quote.calculator.core.strategy.impl;

import insurance.quote.calculator.core.application.command.HealthQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.risk_assessment.RiskAssessmentService;
import insurance.quote.calculator.core.domain.PriceBreakdown;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HealthInsurancePricingStrategy implements PricingStrategy {

    private final List<RiskAssessmentService> riskAssessmentServices;

    @Override
    public InsuranceType getType() {
        return InsuranceType.HEALTH;
    }

    @Override
    public PriceBreakdown calculatePrice(QuoteCalculationCommand command) {
        if (!(command instanceof HealthQuoteCalculationCommand healthCommand)) {
            throw new IllegalArgumentException("Health pricing requires health quote command");
        }

        //need to fix duplicate
        var risk = getRiskAssessmentService(command).assessRisk(healthCommand);
        var basePrice = calculateBasePrice(healthCommand);
        var riskMultiplier = risk.getMultiplier();
        var coverageMultiplier = calculateCoverageMultiplier(healthCommand);
        var additionalCharge = additionalCharge(healthCommand);
        var discount = calculateDiscount(healthCommand);

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

    private BigDecimal calculateBasePrice(HealthQuoteCalculationCommand command) {
        var fixedFee = BigDecimal.valueOf(30);
        var annualRate = BigDecimal.valueOf(0.015);
        var monthsInYear = BigDecimal.valueOf(12);

        return fixedFee
                .add(command.annualCoverageLimit()
                        .multiply(annualRate)
                        .divide(monthsInYear, 2, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateCoverageMultiplier(HealthQuoteCalculationCommand command) {
        return command.coverageLevel()
                .getMultiplier()
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal additionalCharge(HealthQuoteCalculationCommand command) {
        var additionalCharge = BigDecimal.ZERO;

        if (isOlderCustomer(command)) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(25));
        }

        if (Boolean.TRUE.equals(command.smoker())) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(20));
        }

        if (command.preExistingConditionCount() > 0) {
            additionalCharge = additionalCharge.add(
                    BigDecimal.valueOf(command.preExistingConditionCount())
                            .multiply(BigDecimal.valueOf(15))
            );
        }

        if (command.chronicDiseaseCount() > 0) {
            additionalCharge = additionalCharge.add(
                    BigDecimal.valueOf(command.chronicDiseaseCount())
                            .multiply(BigDecimal.valueOf(20))
            );
        }

        if (Boolean.TRUE.equals(command.includesDental())) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(10));
        }

        if (Boolean.TRUE.equals(command.includesVision())) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(7));
        }

        if (Boolean.TRUE.equals(command.includesHospitalization())) {
            additionalCharge = additionalCharge.add(BigDecimal.valueOf(20));
        }

        if (command.previousClaimsCount() > 0) {
            additionalCharge = additionalCharge.add(
                    BigDecimal.valueOf(command.previousClaimsCount())
                            .multiply(BigDecimal.valueOf(5))
            );
        }

        return additionalCharge.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscount(HealthQuoteCalculationCommand command) {
        if (command.previousClaimsCount() == 0 && command.coverageLevel() == CoverageLevel.PREMIUM) {
            return BigDecimal.valueOf(10);
        }

        return BigDecimal.ZERO;
    }

    private Boolean isOlderCustomer(HealthQuoteCalculationCommand command) {
        var dateOfBirth = command.customerProfile().getDateOfBirth();
        var age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        return age > 60;
    }
}