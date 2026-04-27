package insurance.quote.calculator.web.mapper.response;

import insurance.quote.calculator.core.domain.InsuranceQuote;
import insurance.quote.calculator.web.dto.response.QuoteResponse;

public interface QuoteResponseMapper {

    QuoteResponse toResponse(InsuranceQuote quote);
}
