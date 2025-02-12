package org.baouz.libraryapi.borrower;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.common.BaseEntity;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "borrower_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public class Borrower extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true)
    private String phone;
//    @Column(nullable = false)
//    private String address;
    @Embedded
    private Address address;
    private LocalDate birthday;
    @Enumerated(STRING)
    private Gender gender;
}
