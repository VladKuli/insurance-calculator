package insurance.quote.calculator.web.mapper.response.impl;

import insurance.quote.calculator.core.domain.InsuranceQuote;
import insurance.quote.calculator.web.dto.response.PriceBreakdownResponse;
import insurance.quote.calculator.web.dto.response.QuoteResponse;
import insurance.quote.calculator.web.mapper.response.QuoteResponseMapper;
import org.springframework.stereotype.Component;

@Component
public class QuoteResponseMapperImpl implements QuoteResponseMapper {

    public QuoteResponse toResponse(InsuranceQuote quote) {
        var customer = quote.getCustomerProfile();
        var priceBreakdown = quote.getPriceBreakdown();

        var customerName = customer.getFirstName() + " " + customer.getLastName();

        var priceResponse = new PriceBreakdownResponse(
                priceBreakdown.getBasePrice(),
                priceBreakdown.getRiskMultiplier(),
                priceBreakdown.getCoverageMultiplier(),
                priceBreakdown.getAdditionalCharges(),
                priceBreakdown.getDiscount(),
                priceBreakdown.getFinalPrice()
        );

        return new QuoteResponse(
                quote.getId(),
                quote.getInsuranceType().name(),
                quote.getRiskLevel().getValue(),
                quote.getStatus().getValue(),
                customerName,
                priceResponse
        );
    }

}
