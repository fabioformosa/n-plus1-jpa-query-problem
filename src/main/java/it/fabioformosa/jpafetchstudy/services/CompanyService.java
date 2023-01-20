package it.fabioformosa.jpafetchstudy.services;

import it.fabioformosa.jpafetchstudy.converters.Converter;
import it.fabioformosa.jpafetchstudy.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.entitites.Company;
import it.fabioformosa.jpafetchstudy.repositories.CompanyDAO;
import it.fabioformosa.jpafetchstudy.repositories.CompanyRepository;
import it.fabioformosa.jpafetchstudy.repositories.CompanyRepositoryWithEntityGraph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRepositoryWithEntityGraph companyRepositoryWithEntityGraph;
    private final CompanyDAO companyDAO;

    private static void printCompanies(PaginatedListDto<CompanyDto> paginatedListDto) {
        log.info("######### COMPANIES ({} of {}) ############", paginatedListDto.getItems().size(), paginatedListDto.getTotalItems());
        paginatedListDto.getItems().stream().forEach(companyDto -> {
            log.info("{} || {} || #empl. {}", companyDto.getId(), companyDto.getName(), companyDto.getEmployees().size());
        });
        log.info("######### /COMPANIES ############");
    }

    public PaginatedListDto<CompanyDto> list(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        return paginatedListDto;
    }

    public PaginatedListDto<CompanyDto> listWithFetchViaJQL(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepository.findAllWithEmployees(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        printCompanies(paginatedListDto);
        return paginatedListDto;
    }

    public PaginatedListDto<CompanyDto> listWithFetchViaSpecification(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepository.findAll(fetchEmployeesSpecification(), PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        printCompanies(paginatedListDto);
        return paginatedListDto;
    }

    public PaginatedListDto<CompanyDto> listWithFetchViaCriteriaBuilder(int pageNum, int pageSize){
        Page<Company> companyPage = companyDAO.listCompanyWithEmployeesViaCriteria(pageNum, pageSize);
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        printCompanies(paginatedListDto);
        return paginatedListDto;
    }


    public PaginatedListDto<CompanyDto> listWithFetchViaEntityGraph(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepositoryWithEntityGraph.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        printCompanies(paginatedListDto);
        return paginatedListDto;
    }

    static private Specification<Company> fetchEmployeesSpecification() {
        return (root, q, cb) -> {
            if (Long.class != q.getResultType() && long.class != q.getResultType()) {
                root.fetch("employees", JoinType.LEFT);
            }
            return cb.equal(cb.literal(1), 1);
        };
    }

}
