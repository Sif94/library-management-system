package org.baouz.libraryapi.student;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.baouz.libraryapi.department.Department;
import org.baouz.libraryapi.department.DepartmentRepository;
import org.baouz.libraryapi.faculty.FacultyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentMapper mapper;


    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        repository.findByEmail(request.email())
                .ifPresent(e -> {
                    throw new EntityExistsException("Email already exists");
                });
        repository.findByPhone(request.phone())
                .ifPresent(e -> {
                    throw new EntityExistsException("Phone already exists");
                });
        repository.findByStudentCode(request.studentCode())
                .ifPresent(e -> {
                    throw new EntityExistsException("Student code already exists");
                });
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new EntityExistsException("Department not found"));

        var student = mapper.toStudent(request);
        student.setDepartment(department);
        return mapper.toStudentResponse(repository.save(student));
    }

    public PageResponse<StudentResponse> getStudents(int page, int size) {
        var students = repository.findAll(PageRequest.of(page, size));
        return getStudentResponsePageResponse(students);
    }

    public PageResponse<StudentResponse> getStudentsByDepartment(String departmentId, int page, int size) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityExistsException("Department not found"));
        var students = repository.findByDepartmentId(departmentId, PageRequest.of(page, size));
        return getStudentResponsePageResponse(students);
    }

    private PageResponse<StudentResponse> getStudentResponsePageResponse(Page<Student> students) {
        List<StudentResponse> studentResponses = students.getContent().stream()
                .map(mapper::toStudentResponse)
                .toList();
        return PageResponse.<StudentResponse>builder()
                .page(students.getNumber())
                .size(students.getSize())
                .totalElements(students.getTotalElements())
                .totalPages(students.getTotalPages())
                .content(studentResponses)
                .isFirst(students.isFirst())
                .isLast(students.isLast())
                .build();
    }

    public PageResponse<StudentResponse> getStudentsByFaculty(String facultyId, int page, int size) {
        facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityExistsException("Faculty not found"));
        var students = repository.findByFacultyId(facultyId, PageRequest.of(page, size));
        return getStudentResponsePageResponse(students);
    }
}
