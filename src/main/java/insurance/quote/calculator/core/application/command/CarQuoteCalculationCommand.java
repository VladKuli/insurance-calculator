package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CarQuoteCalculationCommand(
        @NotNull
        CustomerProfile customerProfile,

        @NotNull @Positive(message = "Coverage amount must be positive")
        BigDecimal coverageAmount,

        @NotNull @Positive(message = "Driving experience years must be positive")
        Integer drivingExperienceYears,

        @NotNull
        BigDecimal carValue,

        @NotNull
        Integer accidentCount,

        @NotNull
        Integer carAge) implements QuoteCalculationCommand {

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.CAR;
    }

    @Override
    public CustomerProfile customerProfile() {
        return customerProfile;
    }

    @Override
    public BigDecimal coverageAmount() {
        return coverageAmount;
    }

}
