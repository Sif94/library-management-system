package org.baouz.libraryapi.department;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
@Tag(name = "Department")
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid DepartmentRequest request
    ) {
        return ResponseEntity
                .status(CREATED)
                .body(service.createDepartment(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<DepartmentResponse>> getDepartments(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getDepartments(page,size));
    }

    @GetMapping("/{department-id}")
    public ResponseEntity<DepartmentResponse> getDepartment(
            @PathVariable("department-id") String departmentId
    ) {
        return ResponseEntity.ok(service.getDepartment(departmentId));
    }

    @GetMapping("/faculty/{faculty-id}")
    public ResponseEntity<Set<DepartmentResponse>> getDepartmentsByFaculty(
            @PathVariable("faculty-id") String facultyId
    ) {
        return ResponseEntity.ok(service.getDepartmentsByFaculty(facultyId));
    }

    @PutMapping("/{department-id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable("department-id") String departmentId,
            @Valid DepartmentRequest request
    ) {
        return ResponseEntity.ok(service.updateDepartment(departmentId, request));
    }

    @DeleteMapping("/{department-id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable("department-id") String departmentId,
            @RequestParam(name = "confirm", defaultValue = "false") boolean confirm
    ) {
        service.deleteDepartment(departmentId, confirm);
        return ResponseEntity.noContent().build();
    }
}
