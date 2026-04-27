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
public class CarQuoteRequestForm extends QuoteRequestForm {

    @NotNull(message = "Driving experience years is required")
    @PositiveOrZero(message = "Driving experience years cannot be negative")
    private Integer drivingExperienceYears;

    @NotNull(message = "Car value is required")
    @Positive(message = "Car value must be positive")
    private BigDecimal carValue;

    @NotNull(message = "Accident count is required")
    @PositiveOrZero(message = "Accident count cannot be negative")
    private Integer accidentCount;

    @NotNull(message = "Car age is required")
    @PositiveOrZero(message = "Car age cannot be negative")
    private Integer carAge;

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.CAR;
    }

}
