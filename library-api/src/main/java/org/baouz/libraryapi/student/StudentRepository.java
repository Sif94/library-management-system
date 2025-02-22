package org.baouz.libraryapi.student;

import org.baouz.libraryapi.department.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    Page<Student> findByDepartmentId(String departmentId, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.department.faculty.id = :facultyId")
    Page<Student> findByFacultyId(@Param("facultyId")String facultyId, Pageable pageable);
}
