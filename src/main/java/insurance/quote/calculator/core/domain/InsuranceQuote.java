package insurance.quote.calculator.core.domain;

import insurance.quote.calculator.core.domain.enums.InsuranceType;
import insurance.quote.calculator.core.domain.enums.QuoteStatus;
import insurance.quote.calculator.core.domain.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_quote")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InsuranceQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_profile_id")
    private CustomerProfile customerProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_breakdown_id")
    private PriceBreakdown priceBreakdown;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public InsuranceQuote(
            InsuranceType insuranceType,
            RiskLevel riskLevel,
            QuoteStatus status,
            CustomerProfile customerProfile,
            PriceBreakdown priceBreakdown
    ) {
        this.insuranceType = insuranceType;
        this.riskLevel = riskLevel;
        this.status = status;
        this.customerProfile = customerProfile;
        this.priceBreakdown = priceBreakdown;
        this.createdAt = LocalDateTime.now();
    }

    public void changeStatus(QuoteStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

}
