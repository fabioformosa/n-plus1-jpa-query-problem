package it.fabioformosa.jpafetchstudy.fetchmode.subselect.converters;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Company2;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Employee2;

public class Converter extends AbstractBaseConverter {

    static public CompanyDto fromCompany2ToCompanyDto(Company2 company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .employees(company.getEmployees().stream().map(Converter::fromEmployee2ToEmployeeDto).toList())
                .build();
    }

    public static EmployeeDto fromEmployee2ToEmployeeDto(Employee2 employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .lastname(employee.getLastname())
                .firstname(employee.getFirstname())
                .companyName(employee.getCompany().getName())
                .build();
    }

}
