package it.fabioformosa.jpafetchstudy.dtos;

import it.fabioformosa.jpafetchstudy.entitites.Employee;
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
