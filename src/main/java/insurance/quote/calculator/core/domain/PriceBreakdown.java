package insurance.quote.calculator.core.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "price_breakdowns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceBreakdown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal basePrice;

    private BigDecimal riskMultiplier;

    private BigDecimal coverageMultiplier;

    private BigDecimal additionalCharges;

    private BigDecimal discount;

    private BigDecimal finalPrice;

    public PriceBreakdown(BigDecimal basePrice,
                          BigDecimal riskMultiplier,
                          BigDecimal coverageMultiplier,
                          BigDecimal additionalCharges,
                          BigDecimal discount,
                          BigDecimal finalPrice) {

        this.basePrice = basePrice;
        this.riskMultiplier = riskMultiplier;
        this.coverageMultiplier = coverageMultiplier;
        this.additionalCharges = additionalCharges;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

}
