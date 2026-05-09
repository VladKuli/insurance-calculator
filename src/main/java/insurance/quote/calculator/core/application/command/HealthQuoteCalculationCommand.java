package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record HealthQuoteCalculationCommand(

        @NotNull
        CustomerProfile customerProfile,

        @NotNull
        CoverageLevel coverageLevel,

        @NotNull
        @PositiveOrZero
        Integer preExistingConditionCount,

        @NotNull
        @PositiveOrZero
        Integer chronicDiseaseCount,

        @NotNull
        Boolean smoker,

        @NotNull
        Boolean includesDental,

        @NotNull
        Boolean includesVision,

        @NotNull
        Boolean includesHospitalization,

        @NotNull
        @Positive
        BigDecimal annualCoverageLimit,

        @NotNull
        @PositiveOrZero
        Integer previousClaimsCount

) implements QuoteCalculationCommand {

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.HEALTH;
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