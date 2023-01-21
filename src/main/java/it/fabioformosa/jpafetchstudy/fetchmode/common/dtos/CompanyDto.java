package it.fabioformosa.jpafetchstudy.fetchmode.common.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyDto {

    private Long id;

    private String name;

    private List<EmployeeDto> employees;

}
