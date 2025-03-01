package it.fabioformosa.jpafetchstudy.fetchmode.join.converters;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Company;
import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Employee;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.converters.AbstractBaseConverter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class Converter extends AbstractBaseConverter {

    public static CompanyDto fromCompanyToCompanyDto(Company company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .employees(company.getEmployees().stream().map(Converter::fromEmployeeToEmployeeDto).toList())
                .build();
    }

    public static EmployeeDto fromEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .lastname(employee.getLastname())
                .firstname(employee.getFirstname())
                .companyName(employee.getCompany().getName())
                .build();
    }

}
