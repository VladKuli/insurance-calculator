package insurance.quote.calculator.core.domain.enums;

import lombok.Getter;

@Getter
public enum QuoteStatus {
    CALCULATED("Calculated"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    QuoteStatus(String value) {
        this.value = value;
    }

}
