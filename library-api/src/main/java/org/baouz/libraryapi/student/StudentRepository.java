package org.baouz.libraryapi.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByPhone(String phone);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :departmentId")
    long countStudentsByDepartmentId(@Param("departmentId") String departmentId);

}
