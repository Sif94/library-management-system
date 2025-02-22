package org.baouz.libraryapi.student;

import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    public Student toStudent(StudentRequest request) {
        return Student.builder()
                .firstName(request.firstname())
                .lastName(request.lastname())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .birthday(request.birthday())
                .gender(request.gender())
                .studentCode(request.studentCode())
                .baccalaureateYear(request.baccalaureateYear())
                .baccalaureateName(request.baccalaureateName())
                .build();
    }

    public StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstname(student.getFirstName())
                .lastname(student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .address(student.getAddress())
                .gender(student.getGender())
                .birthday(student.getBirthday())
                .studentCode(student.getStudentCode())
                .baccalaureateYear(student.getBaccalaureateYear())
                .baccalaureateName(student.getBaccalaureateName())
                .departmentId(student.getDepartment().getId())
                .build();
    }
}
