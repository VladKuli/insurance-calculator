package insurance.quote.calculator.core.application.service.risk_assessment;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RiskAssessmentService {

    RiskLevel assessRisk(@Valid QuoteCalculationCommand command);

    Boolean supports(InsuranceType insuranceType);

}
