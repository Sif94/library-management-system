package org.baouz.libraryapi.faculty;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.department.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository repository;
    private final DepartmentRepository departmentRepository;
    private final FacultyMapper mapper;
    public FacultyResponse create(FacultyRequest request) {
        repository.findByName(request.name())
                .ifPresent(faculty -> {
                    throw new EntityExistsException("Faculty already exists");
                });
        var faculty = mapper.toFaculty(request);
        return mapper.toFacultyResponse(repository.save(faculty));
    }

    public List<FacultyResponse> getFaculties() {
        return repository.findAll().stream()
                .map(mapper::toFacultyResponse)
                .toList();
    }

    public FacultyResponse getFaculty(String facultyId) {
        return repository.findById(facultyId)
                .map(mapper::toFacultyResponse)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
    }

    public FacultyResponse updateFaculty(String facultyId, FacultyRequest request) {
        repository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found"));
        Faculty updatedFaculty = mapper.toFaculty(request);
        updatedFaculty.setId(facultyId);
        return mapper.toFacultyResponse(repository.save(updatedFaculty));
    }

    public void deleteFaculty(String facultyId, boolean confirm) {
        long departmentsCount = departmentRepository.countDepartmentsByFacultyId(facultyId);
        final List<String> warnings = new ArrayList<>();
        if (departmentsCount > 0) {
            warnings.add("Departments count is greater than 0");
            System.out.println("The current faculty has departments: " + departmentsCount);
        }
        if (!warnings.isEmpty() && !confirm) {
            throw new RuntimeException("One or more warnings");
        }

        repository.deleteById(facultyId);
    }
}
