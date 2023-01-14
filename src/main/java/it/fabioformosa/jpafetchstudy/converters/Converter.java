package it.fabioformosa.jpafetchstudy.converters;

import it.fabioformosa.jpafetchstudy.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.entitites.Company;
import it.fabioformosa.jpafetchstudy.entitites.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class Converter {

    static public <ENTITY, DTO> PaginatedListDto fromPageToPaginatedListDto(Page<ENTITY> page, Function<ENTITY, DTO> conversion){
        return PaginatedListDto.builder()
                .items((List<Object>) page.getContent().stream().map(conversion).toList())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .build();
    }

    static public CompanyDto fromCompanyToCompanyDto(Company company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .employees(company.getEmployees().stream().map(Converter::fromEmployeeToEmployeeDto).toList())
                .build();
    }

    private static EmployeeDto fromEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .lastname(employee.getLastname())
                .firstname(employee.getFirstname())
                .companyName(employee.getCompany().getName())
                .build();
    }

}
