package org.baouz.libraryapi.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Optional<Department> findByName(String name);
    @Query("SELECT COUNT(d) FROM Department d WHERE d.faculty.id = :facultyId")
    long countDepartmentsByFacultyId(String facultyId);
}
