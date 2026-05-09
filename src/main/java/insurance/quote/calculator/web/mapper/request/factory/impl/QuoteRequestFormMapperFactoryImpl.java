package insurance.quote.calculator.web.mapper.request.factory.impl;

import insurance.quote.calculator.core.application.command.QuoteCalculationCommand;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.web.dto.form.QuoteRequestForm;
import insurance.quote.calculator.web.mapper.request.QuoteRequestFormMapper;
import insurance.quote.calculator.web.mapper.request.factory.QuoteRequestFormMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QuoteRequestFormMapperFactoryImpl implements QuoteRequestFormMapperFactory {

    private final Map<InsuranceType, QuoteRequestFormMapper> mappers;

    public QuoteRequestFormMapperFactoryImpl(List<QuoteRequestFormMapper> mapperList) {
        this.mappers = mapperList.stream()
                .collect(Collectors.toMap(
                        QuoteRequestFormMapper::getType,
                        Function.identity()
                ));
    }

    public QuoteCalculationCommand toCommand(QuoteRequestForm form) {
        var mapper = mappers.get(form.getInsuranceType());

        if (mapper == null) {
            throw new IllegalArgumentException(
                    "No mapper found for insurance type: " + form.getInsuranceType()
            );
        }

        return mapper.toCommand(form);
    }

}
