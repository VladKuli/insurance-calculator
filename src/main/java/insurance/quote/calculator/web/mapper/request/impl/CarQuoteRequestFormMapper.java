package insurance.quote.calculator.web.mapper.request.impl;

import insurance.quote.calculator.core.application.command.CarQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.CustomerProfile;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.web.dto.form.CarQuoteRequestForm;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;
import insurance.quote.calculator.web.mapper.request.QuoteRequestFormMapper;
import org.springframework.stereotype.Component;

@Component
public class CarQuoteRequestFormMapper implements QuoteRequestFormMapper {

    @Override
    public InsuranceType getType() {
        return InsuranceType.CAR;
    }

    @Override
    public QuoteCalculationCommand toCommand(QuoteRequestForm form) {
        if (!(form instanceof CarQuoteRequestForm carForm)) {
            throw new IllegalArgumentException("Car mapper requires CarQuoteRequestForm");
        }

        var customerProfile = new CustomerProfile(
                carForm.getFirstName(),
                carForm.getLastName(),
                carForm.getEmail(),
                carForm.getDateOfBirth(),
                carForm.getPhoneNumber()
        );

        return new CarQuoteCalculationCommand(
                customerProfile,
                carForm.getCoverageLevel(),
                carForm.getDrivingExperienceYears(),
                carForm.getCarValue(),
                carForm.getAccidentCount(),
                carForm.getCarAge()
        );
    }

}
