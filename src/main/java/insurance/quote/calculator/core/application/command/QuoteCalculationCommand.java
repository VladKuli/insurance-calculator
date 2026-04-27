package insurance.quote.calculator.core.application.command;

import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;

public interface QuoteCalculationCommand {

    InsuranceType getInsuranceType();

    CustomerProfile customerProfile();

    CoverageLevel coverageAmount();

}
