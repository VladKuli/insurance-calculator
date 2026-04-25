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
    private Integer age;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String city;

    private boolean smoker;
    private boolean hasChronicDiseases;

    public CustomerProfile(String firstName,
                           String lastName,
                           Integer age,
                           String email,
                           LocalDate dateOfBirth,
                           String address,
                           String phoneNumber,
                           String city,
                           boolean smoker,
                           boolean hasChronicDiseases) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.smoker = smoker;
        this.hasChronicDiseases = hasChronicDiseases;
    }

    public CustomerProfile(String firstName, String lastName, Integer age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

}
