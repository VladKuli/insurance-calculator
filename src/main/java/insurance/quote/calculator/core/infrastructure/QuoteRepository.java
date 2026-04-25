package insurance.quote.calculator.core.infrastructure;

import insurance.quote.calculator.core.domain.InsuranceQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<InsuranceQuote, Long> {


}
