package it.fabioformosa.jpafetchstudy.services;

import it.fabioformosa.jpafetchstudy.converters.Converter;
import it.fabioformosa.jpafetchstudy.dtos.CompanyDto;
import it.fabioformosa.jpafetchstudy.dtos.PaginatedListDto;
import it.fabioformosa.jpafetchstudy.entitites.Company;
import it.fabioformosa.jpafetchstudy.repositories.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;

@Transactional
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public PaginatedListDto<CompanyDto> list(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
        return paginatedListDto;
    }

    public PaginatedListDto<CompanyDto> listWithFetch(int pageNum, int pageSize){
        Page<Company> companyPage = companyRepository.findAll(fetchEmployeesSpecification(), PageRequest.of(pageNum, pageSize, Sort.by("id")));
        PaginatedListDto<CompanyDto> paginatedListDto = Converter.fromPageToPaginatedListDto(companyPage, Converter::fromCompanyToCompanyDto);
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
