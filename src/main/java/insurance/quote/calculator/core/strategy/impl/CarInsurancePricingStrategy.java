package insurance.quote.calculator.core.strategy.impl;

import insurance.quote.calculator.core.application.command.CarQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.RiskAssessmentService;
import insurance.quote.calculator.core.domain.PriceBreakdown;
import insurance.quote.calculator.core.domain.enums.Discounts;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static insurance.quote.calculator.core.domain.enums.Charges.ACCIDENT_CHARGE;
import static insurance.quote.calculator.core.domain.enums.Charges.OLD_CAR_CHARGE;

@Component
@RequiredArgsConstructor
public class CarInsurancePricingStrategy implements PricingStrategy {

    private final List<RiskAssessmentService> riskAssessmentServices;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    public InsuranceType getType() {
        return InsuranceType.CAR;
    }

    @Override
    public PriceBreakdown calculatePrice(QuoteCalculationCommand command) {
        if (!(command instanceof CarQuoteCalculationCommand carCommand)) {
            throw new IllegalArgumentException("Car pricing requires car quote command");
        }

        var risk = getRiskAssessmentService(command).assessRisk(carCommand);
        var basePrice = calculateBasePrice(carCommand);
        var riskMultiplier = risk.getMultiplier();
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
        var annualRate = BigDecimal.valueOf(0.03);
        var monthsInYear = BigDecimal.valueOf(12);

        return command.carValue()
                .multiply(annualRate)
                .add(fixedFee)
                .divide(monthsInYear, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateCoverageMultiplier(CarQuoteCalculationCommand command) {
        return command.coverageAmount()
                .getMultiplier()
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAdditionalCharges(CarQuoteCalculationCommand command) {
        var oldCarCharge = command.carAge() > 10
                ? OLD_CAR_CHARGE.getCharge()
                : ZERO;

        var accidentCharge = ACCIDENT_CHARGE.getCharge().multiply(
                BigDecimal.valueOf(command.accidentCount())
        );

        return oldCarCharge.add(accidentCharge);
    }

    private BigDecimal calculateDiscount(CarQuoteCalculationCommand command) {
        var experienceDiscount = calculateExperienceDiscount(command.drivingExperienceYears());

        var accidentFreeDiscount = command.accidentCount() == 0
                ? Discounts.ACCIDENT_FREE_DISCOUNT.getDiscount()
                : ZERO;

        return experienceDiscount.add(accidentFreeDiscount);
    }

    private BigDecimal calculateExperienceDiscount(Integer drivingExperienceYears) {
        if (drivingExperienceYears >= 10) {
            return Discounts.HIGH_EXPERIENCE_DISCOUNT.getDiscount();
        }

        if (drivingExperienceYears >= 5) {
            return Discounts.MEDIUM_EXPERIENCE_DISCOUNT.getDiscount();
        }

        return ZERO;
    }

    private RiskAssessmentService getRiskAssessmentService(QuoteCalculationCommand command) {
        return riskAssessmentServices.stream()
                .filter(service -> service.supports(command.getInsuranceType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No risk assessment service found for insurance type: "
                        + command.getInsuranceType()));
    }

}
