package insurance.quote.calculator.core.domain.enums;

public enum RiskLevel {

    HIGH,
    MEDIUM,
    LOW;

    public static RiskLevel fromRiskPoints(long points) {
        return switch ((int) points) {
            case 0, 1 -> LOW;
            case 2 -> MEDIUM;
            default -> HIGH;
        };
    }
}
