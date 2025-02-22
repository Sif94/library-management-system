package org.baouz.libraryapi.department;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.baouz.libraryapi.faculty.Faculty;
import org.baouz.libraryapi.faculty.FacultyRepository;
import org.baouz.libraryapi.student.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    public final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final DepartmentMapper mapper;

    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        repository.findByName(request.name())
                .ifPresent(department -> {
                    throw new EntityExistsException("Department with name:: " + request.name() + " already exists");
                });
        Faculty faculty = facultyRepository.findById(request.facultyId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Faculty with id:: " + request.facultyId() + " not found")
                );
        Department department = mapper.toDepartment(request);
        department.setFaculty(faculty);
        return mapper.toDepartmentResponse(repository.save(department));
    }


    public PageResponse<DepartmentResponse> getDepartments(int page, int size) {
        var departmentsPage = repository.findAll(PageRequest.of(page, size));
        var departments = departmentsPage
                .getContent()
                .stream()
                .map(mapper::toDepartmentResponse)
                .toList();
        return PageResponse.<DepartmentResponse>builder()
                .page(departmentsPage.getNumber())
                .size(departmentsPage.getSize())
                .totalElements(departmentsPage.getTotalElements())
                .totalPages(departmentsPage.getTotalPages())
                .content(departments)
                .isFirst(departmentsPage.isFirst())
                .isLast(departmentsPage.isLast())
                .build();
    }


    @Transactional
    public DepartmentResponse updateDepartment(String departmentId, DepartmentRequest request) {
        repository.findById(departmentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id:: " + departmentId + " not found")
                );
        var faculty = facultyRepository.findById(request.facultyId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Faculty with id:: " + request.facultyId() + " not found")
                );
        var updatedDepartment = mapper.toDepartment(request);
        updatedDepartment.setId(departmentId);
        updatedDepartment.setFaculty(faculty);
        return mapper.toDepartmentResponse(repository.save(updatedDepartment));
    }

    @Transactional
    public void deleteDepartment(String departmentId, boolean confirm) {
        long studentsCount = studentRepository.countStudentsByDepartmentId(departmentId);
        long teachersCount = repository.countTeachersByDepartmentId(departmentId);
        final List<String> warnings = new ArrayList<>();
        if (studentsCount > 0) {
            warnings.add("Students count is greater than 0");
            System.out.println("The current department has students: " + studentsCount);
        }
        if (teachersCount > 0) {
            warnings.add("Teachers count is greater than 0");
            System.out.println("The current department has teachers: " + teachersCount);
        }
        if (!warnings.isEmpty() && !confirm) {
            throw new RuntimeException("One or more warnings");
        }
        repository.deleteDepartmentTeachersByDepartmentId(departmentId);
        repository.deleteById(departmentId);
    }

    public DepartmentResponse getDepartment(String departmentId) {
        return repository.findById(departmentId)
                .map(mapper::toDepartmentResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id:: " + departmentId + " not found")
                );
    }

    public Set<DepartmentResponse> getDepartmentsByFaculty(String facultyId) {
        return repository.findAllByFacultyId(facultyId)
                .stream()
                .map(mapper::toDepartmentResponse)
                .collect(Collectors.toSet());
    }
}
