package org.baouz.libraryapi.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.baouz.libraryapi.borrower.Address;
import org.baouz.libraryapi.borrower.Gender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Address address;
    private LocalDate birthday;
    private Gender gender;
    private String studentCode;
    private int baccalaureateYear;
    private String baccalaureateName;
    private String departmentId;

}
