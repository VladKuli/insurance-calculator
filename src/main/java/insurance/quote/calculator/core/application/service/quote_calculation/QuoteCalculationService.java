package insurance.quote.calculator.core.application.service.quote_calculation;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.InsuranceQuote;
import jakarta.validation.Valid;

public interface QuoteCalculationService {

    InsuranceQuote calculateQuote(@Valid QuoteCalculationCommand command);

}
