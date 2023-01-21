package it.fabioformosa.jpafetchstudy.fetchmode.subselect.services;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.converters.Converter;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Employee2;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.repositories.Employee2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class Employee2Service {

    private final Employee2Repository employeeRepository;


    public PaginatedListDto<EmployeeDto> list(int pageNum, int pageSize) {
        Page<Employee2> employeePage = employeeRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<EmployeeDto> paginatedListDto = Converter.fromPageToPaginatedListDto(employeePage, Converter::fromEmployee2ToEmployeeDto);
        return paginatedListDto;
    }

    public PaginatedListDto<EmployeeDto> listWithCompany(int pageNum, int pageSize){
        Page<Employee2> employeePage = employeeRepository.findAllWithCompany(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<EmployeeDto> paginatedListDto = Converter.fromPageToPaginatedListDto(employeePage, Converter::fromEmployee2ToEmployeeDto);
        return paginatedListDto;
    }

}
