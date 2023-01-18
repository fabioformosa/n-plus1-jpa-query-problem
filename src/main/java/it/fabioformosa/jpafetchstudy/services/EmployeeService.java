package it.fabioformosa.jpafetchstudy.services;

import it.fabioformosa.jpafetchstudy.converters.Converter;
import it.fabioformosa.jpafetchstudy.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.entitites.Employee;
import it.fabioformosa.jpafetchstudy.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;

@Transactional
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    static public Specification<Employee> fetchCompanySpecification() {
        return (root, q, cb) -> {
            if (Long.class != q.getResultType() && long.class != q.getResultType()) {
                root.fetch("company", JoinType.LEFT);
            }
            return cb.equal(cb.literal(1), 1);
        };
    }

    public PaginatedListDto<EmployeeDto> list(int pageNum, int pageSize) {
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<EmployeeDto> paginatedListDto = Converter.fromPageToPaginatedListDto(employeePage, Converter::fromEmployeeToEmployeeDto);
        return paginatedListDto;
    }

    public PaginatedListDto<EmployeeDto> listWithCompanySpecification(int pageNum, int pageSize){
        Page<Employee> employeePage = employeeRepository.findAll(fetchCompanySpecification(), PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<EmployeeDto> paginatedListDto = Converter.fromPageToPaginatedListDto(employeePage, Converter::fromEmployeeToEmployeeDto);
        return paginatedListDto;
    }

    public PaginatedListDto<EmployeeDto> listWithCompany(int pageNum, int pageSize){
        Page<Employee> employeePage = employeeRepository.findAllWithCompany(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<EmployeeDto> paginatedListDto = Converter.fromPageToPaginatedListDto(employeePage, Converter::fromEmployeeToEmployeeDto);
        return paginatedListDto;
    }

}
