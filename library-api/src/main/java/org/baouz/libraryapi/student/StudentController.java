package org.baouz.libraryapi.student;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
@Tag(name = "Student")
public class StudentController {

    private final StudentService service;

}
