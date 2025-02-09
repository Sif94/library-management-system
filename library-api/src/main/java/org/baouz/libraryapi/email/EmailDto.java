package org.baouz.libraryapi.email;

import java.util.Map;

public record EmailDto(
        String to,
        String from,
        String subject,
        Map<String, Object> properties
) {
}
