package it.fabioformosa.jpafetchstudy.fetchmode.join.controllers;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.EmployeeDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.join.services.CompanyService;
import it.fabioformosa.jpafetchstudy.fetchmode.join.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/employees")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/fast")
    public PaginatedListDto<EmployeeDto> listWithoutNPlus1Problem(){
        return employeeService.listWithCompany(0, 10);
    }

    @GetMapping("/slow")
    public PaginatedListDto<EmployeeDto> listWithNPlus1Problem(){
        return employeeService.list(0, 10);
    }

}
