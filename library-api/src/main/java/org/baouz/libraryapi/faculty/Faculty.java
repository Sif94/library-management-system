package org.baouz.libraryapi.faculty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.common.BaseEntity;
import org.baouz.libraryapi.department.Department;

import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "FACULTIES"
)
public class Faculty extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    @Column(name = "faculty_id")
    private String id;
    @Column(name = "faculty_name", nullable = false, unique = true)
    private String name;

    @OneToMany
    private Set<Department> departments;
}
