package org.baouz.libraryapi.faculty;

import org.springframework.stereotype.Service;

@Service
public class FacultyMapper {

    public Faculty toFaculty(FacultyRequest request) {
        return Faculty.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public FacultyResponse toFacultyResponse(Faculty faculty) {
        return FacultyResponse.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .description(faculty.getDescription())
                .build();
    }
}
