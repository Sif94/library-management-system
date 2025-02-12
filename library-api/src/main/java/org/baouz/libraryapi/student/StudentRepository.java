package org.baouz.libraryapi.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByPhone(String phone);

}
