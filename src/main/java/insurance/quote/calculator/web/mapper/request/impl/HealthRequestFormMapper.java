package insurance.quote.calculator.web.mapper.request.impl;

import insurance.quote.calculator.core.application.command.HealthQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.web.dto.form.HealthQuoteRequestForm;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;
import insurance.quote.calculator.web.mapper.request.QuoteRequestFormMapper;
import org.springframework.stereotype.Component;

@Component
public class HealthRequestFormMapper implements QuoteRequestFormMapper {

    @Override
    public InsuranceType getType() {
        return InsuranceType.HEALTH;
    }

    @Override
    public QuoteCalculationCommand toCommand(QuoteRequestForm form) {
        if (!(form instanceof HealthQuoteRequestForm healthForm)) {
            throw new IllegalArgumentException("Health mapper requires QuoteRequestForm");
        }

        var customerProfile = new CustomerProfile(
                healthForm.getFirstName(),
                healthForm.getLastName(),
                healthForm.getEmail(),
                healthForm.getDateOfBirth(),
                healthForm.getPhoneNumber()
        );

        return new HealthQuoteCalculationCommand(
                customerProfile,
                healthForm.getCoverageLevel(),
                healthForm.getPreExistingConditionCount(),
                healthForm.getChronicDiseaseCount(),
                healthForm.getSmoker(),
                healthForm.getIncludesDental(),
                healthForm.getIncludesVision(),
                healthForm.getIncludesHospitalization(),
                healthForm.getAnnualCoverageLimit(),
                healthForm.getPreviousClaimsCount()
        );
    }

}
