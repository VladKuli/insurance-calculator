package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.InsuranceType;

import java.math.BigDecimal;

public interface QuoteCalculationCommand {

    InsuranceType getInsuranceType();

    CustomerProfile customerProfile();

    BigDecimal coverageAmount();

}
