package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RealEstateQuoteCalculationCommand(

        @NotNull
        CustomerProfile customerProfile,

        @NotNull
        CoverageLevel coverageLevel,

        @NotNull
        @Positive
        Integer ageOfProperty,

        @NotNull
        @Positive
        BigDecimal propertyValue) implements QuoteCalculationCommand {

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.REAL_ESTATE;
    }

    @Override
    public CustomerProfile customerProfile() {
        return customerProfile;
    }

    @Override
    public CoverageLevel coverageLevel() {
        return coverageLevel;
    }

}
