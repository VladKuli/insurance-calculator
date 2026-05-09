package insurance.quote.calculator.web.dto.form;

import insurance.quote.calculator.core.domain.enums.CoverageLevel;
import insurance.quote.calculator.core.domain.enums.InsuranceType;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public abstract class QuoteRequestForm {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Coverage level is required")
    private CoverageLevel coverageLevel;

    public abstract InsuranceType getInsuranceType();

}
