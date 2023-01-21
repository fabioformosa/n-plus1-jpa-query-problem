package it.fabioformosa.jpafetchstudy.fetchmode.subselect.services;

import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.fetchmode.common.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.converters.Converter;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Company2;
import it.fabioformosa.jpafetchstudy.fetchmode.subselect.repositories.Company2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class Company2Service {

    private final Company2Repository companyRepository;

    private static void printCompanies(PaginatedListDto<CompanyDto> paginatedListDto) {
        log.info("######### COMPANIES ({} of {}) ############", paginatedListDto.getItems().size(), paginatedListDto.getTotalItems());
        paginatedListDto.getItems().stream().forEach(companyDto -> {
            log.info("{} || {} || #empl. {}", companyDto.getId(), companyDto.getName(), companyDto.getEmployees().size());
        });
        log.info("######### /COMPANIES ############");
    }

    public PaginatedListDto<CompanyDto> list(int pageNum, int pageSize){
        Page<Company2> companyPage = companyRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompany2ToCompanyDto);
        return paginatedListDto;
    }

    public PaginatedListDto<CompanyDto> listWithFetchViaJQL(int pageNum, int pageSize){
        Page<Company2> companyPage = companyRepository.findAllWithEmployees(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompany2ToCompanyDto);
        printCompanies(paginatedListDto);
        return paginatedListDto;
    }

}
