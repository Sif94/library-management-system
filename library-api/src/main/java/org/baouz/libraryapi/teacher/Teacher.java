package org.baouz.libraryapi.teacher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.borrower.Borrower;
import org.baouz.libraryapi.department.Department;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
//@DiscriminatorValue(value = "TEACHER")
public class Teacher extends Borrower {
    @Column(unique = true, nullable = false, name = "teacher_code")
    private String teacherCode;
    private String fieldOfStudy;

    @ManyToMany(mappedBy = "teachers")
    private Set<Department> departments;
}
