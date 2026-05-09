package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum RiskLevel {

    HIGH("High", BigDecimal.valueOf(1.5)),
    MEDIUM("Medium", BigDecimal.valueOf(1.0)),
    LOW("Low", BigDecimal.valueOf(0.8));

    private final String value;
    private final BigDecimal multiplier;

    RiskLevel(String value, BigDecimal multiplier) {
        this.value = value;
        this.multiplier = multiplier;
    }
    public static RiskLevel fromRiskPoints(long points) {
        return switch ((int) points) {
            case 0, 1, 2 -> LOW;
            case 3, 4 -> MEDIUM;
            default -> HIGH;
        };
    }

}
