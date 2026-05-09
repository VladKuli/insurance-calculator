package insurance.quote.calculator.web.mapper.request.impl;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.RealEstateQuoteCalculationCommand;
import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;
import insurance.quote.calculator.web.dto.form.RealEstateRequestForm;
import insurance.quote.calculator.web.mapper.request.QuoteRequestFormMapper;
import org.springframework.stereotype.Component;

@Component
public class RealEstateRequestFormMapper implements QuoteRequestFormMapper {

    @Override
    public InsuranceType getType() {
        return InsuranceType.REAL_ESTATE;
    }

    @Override
    public QuoteCalculationCommand toCommand(QuoteRequestForm form) {
        if (!(form instanceof RealEstateRequestForm realEstateForm)) {
            throw new IllegalArgumentException("Real Estate mapper requires QuoteRequestForm");
        }

        var customerProfile = new CustomerProfile(
                realEstateForm.getFirstName(),
                realEstateForm.getLastName(),
                realEstateForm.getEmail(),
                realEstateForm.getDateOfBirth(),
                realEstateForm.getPhoneNumber()
        );

        return new RealEstateQuoteCalculationCommand(
                customerProfile,
                realEstateForm.getCoverageLevel(),
                realEstateForm.getPropertyAge(),
                realEstateForm.getPropertyValue()
        );
    }

}
