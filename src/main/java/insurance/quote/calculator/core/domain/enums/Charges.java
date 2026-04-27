package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Charges {

    OLD_CAR_CHARGE(BigDecimal.valueOf(100)),
    ACCIDENT_CHARGE(BigDecimal.valueOf(75));

    private final BigDecimal charge;

    Charges(BigDecimal charge) {
        this.charge = charge;
    }

}
