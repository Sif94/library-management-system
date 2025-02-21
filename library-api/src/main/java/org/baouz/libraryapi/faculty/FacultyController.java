package org.baouz.libraryapi.faculty;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/faculties")
@RequiredArgsConstructor
@Tag(name = "Faculty", description = "Faculty API")
public class FacultyController {

    private final FacultyService service;

    @PostMapping
    public ResponseEntity<FacultyResponse> create(
            @Valid FacultyRequest request
    ) {
        return ResponseEntity
                .status(CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponse>> getFaculties() {
        return ResponseEntity
                .ok(service.getFaculties());
    }
    @GetMapping("/{faculty-id}")
    public ResponseEntity<FacultyResponse> getFaculty(
            @PathVariable("faculty-id") String facultyId
    ) {
        return ResponseEntity
                .ok(service.getFaculty(facultyId));
    }
    @PutMapping("/{faculty-id}")
    public ResponseEntity<FacultyResponse> updateFaculty(
            @PathVariable("faculty-id") String facultyId,
            @Valid FacultyRequest request
    ) {
        return ResponseEntity
                .ok(service.updateFaculty(facultyId, request));
    }
    @DeleteMapping("/{faculty-id}")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable("faculty-id") String facultyId,
            @RequestParam(value = "confirm", defaultValue = "false") boolean confirm
    ) {
        service.deleteFaculty(facultyId, confirm);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
