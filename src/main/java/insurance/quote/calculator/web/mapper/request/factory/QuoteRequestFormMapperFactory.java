package insurance.quote.calculator.web.mapper.request.factory;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;

public interface QuoteRequestFormMapperFactory {

    QuoteCalculationCommand toCommand(QuoteRequestForm form);
}
