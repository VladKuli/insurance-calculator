package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

@Getter
public enum InsuranceType {

    TRAVEL("Travel"),
    CAR("Car"),
    REAL_ESTATE("Real Estate");

    private final String value;

    InsuranceType(final String value) {
        this.value = value;
    }

}
