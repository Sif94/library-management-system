package org.baouz.libraryapi.department;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.faculty.Faculty;
import org.baouz.libraryapi.faculty.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    public final FacultyRepository facultyRepository;
    private final DepartmentMapper mapper;

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


}
