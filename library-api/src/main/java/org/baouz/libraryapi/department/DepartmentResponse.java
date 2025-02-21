package org.baouz.libraryapi.department;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DepartmentResponse {
    private String id;
    private String name;
    private String description;
    private String facultyId;
}
