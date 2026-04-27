package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

@Getter
public enum InsuranceType {

    TRAVEL("Travel"),
    CAR("Car"),
    HEALTH("Health");

    private final String value;

    InsuranceType(final String value) {
        this.value = value;
    }

}
