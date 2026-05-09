package insurance.quote.calculator.web.dto.form;

import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HealthQuoteRequestForm extends QuoteRequestForm {

    @NotNull(message = "Pre-existing condition count is required")
    @PositiveOrZero(message = "Pre-existing condition count cannot be negative")
    private Integer preExistingConditionCount;

    @NotNull(message = "Chronic disease count is required")
    @PositiveOrZero(message = "Chronic disease count cannot be negative")
    private Integer chronicDiseaseCount;

    @NotNull(message = "Smoker field is required")
    private Boolean smoker;

    @NotNull(message = "Dental coverage field is required")
    private Boolean includesDental;

    @NotNull(message = "Vision coverage field is required")
    private Boolean includesVision;

    @NotNull(message = "Hospitalization coverage field is required")
    private Boolean includesHospitalization;

    @NotNull(message = "Annual coverage limit is required")
    @Positive(message = "Annual coverage limit must be positive")
    private BigDecimal annualCoverageLimit;

    @NotNull(message = "Previous claims count is required")
    @PositiveOrZero(message = "Previous claims count cannot be negative")
    private Integer previousClaimsCount;

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.HEALTH;
    }

}