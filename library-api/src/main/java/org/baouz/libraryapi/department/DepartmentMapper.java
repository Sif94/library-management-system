package org.baouz.libraryapi.department;

import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {
    public Department toDepartment(DepartmentRequest request) {
        return Department.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .facultyId(department.getFaculty().getId())
                .build();
    }
}
