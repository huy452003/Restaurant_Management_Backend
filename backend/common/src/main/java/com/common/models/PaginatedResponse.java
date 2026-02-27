package com.common.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaginatedResponse<T>(
    List<T> content,           // Data của trang hiện tại
    Integer page,              // Số trang hiện tại (0-indexed)
    Integer size,              // Số phần tử mỗi trang
    Long totalElements,        // Tổng số phần tử
    Integer totalPages,        // Tổng số trang
    Boolean first,             // Có phải trang đầu tiên không
    Boolean last,              // Có phải trang cuối cùng không
    Boolean hasNext,           // Có trang tiếp theo không
    Boolean hasPrevious        // Có trang trước không
) {
    public static <T> PaginatedResponse<T> of(Page<T> page) {
        return new PaginatedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious()
        );
    }
}
