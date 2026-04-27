package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Discounts {

    HIGH_EXPERIENCE_DISCOUNT(BigDecimal.valueOf(100)),
    MEDIUM_EXPERIENCE_DISCOUNT(BigDecimal.valueOf(50)),
    ACCIDENT_FREE_DISCOUNT(BigDecimal.valueOf(50));

    private final BigDecimal discount;

    Discounts(BigDecimal discount) {
        this.discount = discount;
    }

}
