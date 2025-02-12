package org.baouz.libraryapi.department;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.common.BaseEntity;
import org.baouz.libraryapi.teacher.Teacher;

import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(name = "departments_unique_constraint_name",
                        columnNames = {"department_name"}
                )
        }
)
public class Department extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    @Column(name = "department_id")
    private String id;
    @Column(nullable = false, unique = true, name = "department_name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "departments_teachers",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers;
}
