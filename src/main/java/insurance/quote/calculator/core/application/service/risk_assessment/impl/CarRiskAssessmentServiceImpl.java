package insurance.quote.calculator.core.application.service.risk_assessment.impl;

import insurance.quote.calculator.core.application.command.CarQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.risk_assessment.RiskAssessmentService;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class CarRiskAssessmentServiceImpl implements RiskAssessmentService {

    @Override
    public Boolean supports(InsuranceType insuranceType) {
        return insuranceType == InsuranceType.CAR;
    }

    @Override
    public RiskLevel assessRisk(QuoteCalculationCommand command) {
        if (!(command instanceof CarQuoteCalculationCommand carCommand)) {
            throw new IllegalArgumentException("Car risk assessment requires car quote command");
        }

        var riskPoints = calculateRiskPoints(carCommand);

        return RiskLevel.fromRiskPoints(riskPoints);
    }

    private Long calculateRiskPoints(CarQuoteCalculationCommand command) {
        return Stream.of(
                isYoungDriver(command),
                isOldCar(command),
                hasAccidents(command)
        ).filter(Boolean::booleanValue).count();
    }

    private Boolean isYoungDriver(CarQuoteCalculationCommand command) {
        return command
                .customerProfile()
                .getDateOfBirth()
                .isAfter(command.customerProfile().getDateOfBirth().plusYears(25));
    }

    private Boolean isOldCar(CarQuoteCalculationCommand command) {
        return command.carAge() > 5;
    }

    private Boolean hasAccidents(CarQuoteCalculationCommand command) {
        return command.accidentCount() > 0;
    }

}
