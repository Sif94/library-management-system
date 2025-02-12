package org.baouz.libraryapi.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrower.Borrower;
import org.baouz.libraryapi.department.Department;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "students")
//@DiscriminatorValue(value = "STUDENT")
public class Student extends Borrower{
    @Column(unique = true, nullable = false)
    private String studentCode;
    private int baccalaureateYear;
    private String baccalaureateName;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
