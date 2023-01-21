package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Company;
import org.springframework.data.domain.Page;

public interface CompanyDAO {
    Page<Company> listCompanyWithEmployeesViaCriteria(int pageNum, int pageSize);
}
