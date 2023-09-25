package com.oldteam.movienote.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
public class PageDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResponse {
        private int currentPage;
        private int totalPages;
        private int size;
        private long totalElements;
        private String sort;
        private Boolean last;

        public PageResponse(Page page) {
            this.currentPage = page.getNumber() + 1;
            this.totalPages = page.getTotalPages();
            this.size = page.getSize();
            this.totalElements = page.getTotalElements();
            this.sort = page.getSort().toString();
            this.last = page.isLast();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListResponse<T> {
        private PageResponse pageInfo;
        private List<T> list;

        public ListResponse(Page page, List<T> dtoList) {
            this.pageInfo = new PageResponse(page);
            this.list = dtoList;
        }
    }

}