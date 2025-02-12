package org.baouz.libraryapi.faculty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.libraryapi.common.BaseEntity;

import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "faculties",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "faculties_unique_constraint_faculty_name",
                        columnNames = {"faculty_name"}
                )
        }
)
public class Faculty extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    @Column(name = "faculty_id")
    private String id;
    @Column(name = "faculty_name", nullable = false, unique = true)
    private String name;
}
