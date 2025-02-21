package org.baouz.libraryapi.department;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
