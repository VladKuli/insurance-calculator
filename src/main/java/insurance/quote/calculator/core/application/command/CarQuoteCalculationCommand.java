package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CarQuoteCalculationCommand(

        @NotNull
        CustomerProfile customerProfile,

        @NotNull
        CoverageLevel coverageAmount,

        @NotNull
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
    public CoverageLevel coverageAmount() {
        return coverageAmount;
    }

}
