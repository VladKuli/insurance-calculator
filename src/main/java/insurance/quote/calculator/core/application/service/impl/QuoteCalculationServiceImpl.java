package insurance.quote.calculator.core.application.service.impl;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.application.service.QuoteCalculationService;
import insurance.quote.calculator.core.application.service.RiskAssessmentService;
import insurance.quote.calculator.core.domain.InsuranceQuote;
import insurance.quote.calculator.core.domain.enums.QuoteStatus;
import insurance.quote.calculator.core.factory.PricingStrategyFactory;
import insurance.quote.calculator.core.infrastructure.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteCalculationServiceImpl implements QuoteCalculationService {

    private final PricingStrategyFactory pricingStrategyFactory;
    private final QuoteRepository quoteRepository;
    private final List<RiskAssessmentService> riskAssessmentServices;

    @Override
    public InsuranceQuote calculateQuote(QuoteCalculationCommand command) {
        var pricingStrategy = pricingStrategyFactory.getStrategy(command.getInsuranceType());
        var priceBreakdown = pricingStrategy.calculatePrice(command);
        var risk = getRiskAssessmentService(command).assessRisk(command);

        var quote = InsuranceQuote.builder()
                .insuranceType(command.getInsuranceType())
                .riskLevel(risk)
                .status(QuoteStatus.CALCULATED)
                .customerProfile(command.customerProfile())
                .priceBreakdown(priceBreakdown)
                .build();

        return quoteRepository.save(quote);
    }

    private RiskAssessmentService getRiskAssessmentService(QuoteCalculationCommand command) {
        return riskAssessmentServices.stream()
                .filter(service -> service.supports(command.getInsuranceType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No risk assessment service found for insurance type: "
                        + command.getInsuranceType()));
    }

}
