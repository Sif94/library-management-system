package org.baouz.libraryapi.student;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.libraryapi.common.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
@Tag(name = "Student")
public class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @RequestBody @Valid StudentRequest request
    ) {
        return ResponseEntity
                .status(CREATED)
                .body(service.createStudent(request));
    }
    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getStudents(page, size));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<PageResponse<StudentResponse>> getStudentsByDepartment(
            @PathVariable String departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getStudentsByDepartment(departmentId, page, size));
    }

    @GetMapping("/faculty/{faculty-id}")
    public ResponseEntity<PageResponse<StudentResponse>> getStudentsByFaculty(
            @PathVariable("faculty-id") String facultyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getStudentsByFaculty(facultyId, page, size));
    }

}
