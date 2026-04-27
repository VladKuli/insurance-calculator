package insurance.quote.calculator.web.mapper.request;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;

public interface QuoteRequestFormMapper {

    InsuranceType getType();

    QuoteCalculationCommand toCommand(QuoteRequestForm form);

}
