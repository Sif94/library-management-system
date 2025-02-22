package org.baouz.libraryapi.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Optional<Department> findByName(String name);
    @Query("SELECT COUNT(d) FROM Department d WHERE d.faculty.id = :facultyId")
    long countDepartmentsByFacultyId(String facultyId);

    @Query(value = "SELECT COUNT(dt) FROM departments_teachers dt WHERE dt.department_id = :departmentId", nativeQuery = true)
    long countTeachersByDepartmentId(@Param("departmentId") String departmentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM departments_teachers WHERE department_id = :departmentId", nativeQuery = true)
    void deleteDepartmentTeachersByDepartmentId(String departmentId);

    @Query("SELECT d FROM Department d WHERE d.faculty.id = :facultyId")
    Set<Department> findAllByFacultyId(@Param("facultyId")String facultyId);

}
