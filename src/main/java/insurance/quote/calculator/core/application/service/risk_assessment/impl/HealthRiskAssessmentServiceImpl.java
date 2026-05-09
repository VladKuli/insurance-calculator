package insurance.quote.calculator.core.application.service.risk_assessment.impl;

import insurance.quote.calculator.core.application.command.HealthQuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.risk_assessment.RiskAssessmentService;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class HealthRiskAssessmentServiceImpl implements RiskAssessmentService {

    @Override
    public Boolean supports(InsuranceType insuranceType) {
        return insuranceType == InsuranceType.HEALTH;
    }

    @Override
    public RiskLevel assessRisk(QuoteCalculationCommand command) {
        if (!(command instanceof HealthQuoteCalculationCommand healthCommand)) {
            throw new IllegalArgumentException("Health risk assessment requires health quote command");
        }

        var riskPoints = calculateRiskPoints(healthCommand);

        return RiskLevel.fromRiskPoints(riskPoints);
    }

    private Long calculateRiskPoints(HealthQuoteCalculationCommand command) {
        return Stream.of(
                isOlderCustomer(command),
                hasPreExistingConditions(command),
                hasChronicDiseases(command),
                isSmoker(command),
                hasPreviousClaims(command)
        ).filter(Boolean::booleanValue).count();
    }

    private Boolean isOlderCustomer(HealthQuoteCalculationCommand command) {
        return command
                .customerProfile()
                .getDateOfBirth()
                .isBefore(LocalDate.now().minusYears(60));
    }

    private Boolean hasPreExistingConditions(HealthQuoteCalculationCommand command) {
        return command.preExistingConditionCount() > 0;
    }

    private Boolean hasChronicDiseases(HealthQuoteCalculationCommand command) {
        return command.chronicDiseaseCount() > 0;
    }

    private Boolean isSmoker(HealthQuoteCalculationCommand command) {
        return Boolean.TRUE.equals(command.smoker());
    }

    private Boolean hasPreviousClaims(HealthQuoteCalculationCommand command) {
        return command.previousClaimsCount() > 0;
    }

}