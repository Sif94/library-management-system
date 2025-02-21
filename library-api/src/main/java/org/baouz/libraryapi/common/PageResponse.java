package org.baouz.libraryapi.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageResponse<T> implements Serializable {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<T> content;
    private boolean isFirst;
    private boolean isLast;

}
