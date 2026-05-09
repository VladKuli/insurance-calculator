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
public class RealEstateRequestForm extends QuoteRequestForm {

    @NotNull(message = "Property age is required")
    @PositiveOrZero(message = "Property age cannot be negative")
    private Integer propertyAge;

    @NotNull
    @Positive(message = "Property value must be positive")
    private BigDecimal propertyValue;

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.REAL_ESTATE;
    }

}
