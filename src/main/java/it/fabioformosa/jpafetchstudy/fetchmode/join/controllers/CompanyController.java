package it.fabioformosa.jpafetchstudy.fetchmode.join.controllers;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.join.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/fast")
    public PaginatedListDto<CompanyDto> listWithoutNPlus1Problem(){
        return companyService.listWithFetchViaJQL(0, 10);
    }

    @GetMapping("/slow")
    public PaginatedListDto<CompanyDto> listWithNPlus1Problem(){
        return companyService.list(0, 10);
    }

}
