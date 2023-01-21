package it.fabioformosa.jpafetchstudy.fetchmode.subselect.converters;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class AbstractBaseConverter {
    static public <ENTITY, DTO> PaginatedListDto fromPageToPaginatedListDto(Page<ENTITY> page, Function<ENTITY, DTO> conversion){
        return PaginatedListDto.builder()
                .items((List<Object>) page.getContent().stream().map(conversion).toList())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .build();
    }
}
