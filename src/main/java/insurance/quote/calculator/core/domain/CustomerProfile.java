package insurance.quote.calculator.core.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "customer_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    private boolean smoker;
    private boolean hasChronicDiseases;

    public CustomerProfile(String firstName,
                           String lastName,
                           String email,
                           LocalDate dateOfBirth,
                           String phoneNumber,
                           boolean smoker,
                           boolean hasChronicDiseases) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.smoker = smoker;
        this.hasChronicDiseases = hasChronicDiseases;
    }

    public CustomerProfile(String firstName, String lastName, String email, LocalDate dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

}
