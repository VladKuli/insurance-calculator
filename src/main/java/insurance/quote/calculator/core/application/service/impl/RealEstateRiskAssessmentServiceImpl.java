package insurance.quote.calculator.core.application.service.impl;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.command.RealEstateQuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.RiskAssessmentService;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RealEstateRiskAssessmentServiceImpl implements RiskAssessmentService {

    @Override
    public Boolean supports(InsuranceType insuranceType) {
        return insuranceType == InsuranceType.REAL_ESTATE;
    }

    @Override
    public RiskLevel assessRisk(QuoteCalculationCommand command) {
        if (!(command instanceof RealEstateQuoteCalculationCommand estateQuoteCalculationCommand)) {
            throw new IllegalArgumentException("Invalid command type for Real Estate Risk Assessment");
        }

        var riskPoints = calculateRiskPoints(estateQuoteCalculationCommand);

        return RiskLevel.fromRiskPoints(riskPoints);
    }

    private Long calculateRiskPoints(RealEstateQuoteCalculationCommand command) {
        return isHighValueProperty(command.propertyValue())
                + (isOldProperty(command.ageOfProperty()) ? 1L : 0L);
    }

    private Long isHighValueProperty(BigDecimal propertyValue) {
        return propertyValue.compareTo(BigDecimal.valueOf(500000)) > 0L
                ? 3L
                : propertyValue.compareTo(BigDecimal.valueOf(200000)) > 0L
                ? 2L
                : 1L;
    }

    private Boolean isOldProperty(Integer ageOfProperty) {
        return ageOfProperty > 20;
    }

}
