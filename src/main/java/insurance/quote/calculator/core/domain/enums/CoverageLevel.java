package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum CoverageLevel {

    BASIC("Basic", BigDecimal.valueOf(0.85)),
    STANDARD("Standard", BigDecimal.valueOf(1.00)),
    PREMIUM("Premium", BigDecimal.valueOf(1.35));

    private final String displayName;
    private final BigDecimal multiplier;

    CoverageLevel(String displayName, BigDecimal multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

}
