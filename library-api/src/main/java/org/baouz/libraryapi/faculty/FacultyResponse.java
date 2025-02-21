package org.baouz.libraryapi.faculty;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FacultyResponse {
    private String id;
    private String name;
    private String description;
}
