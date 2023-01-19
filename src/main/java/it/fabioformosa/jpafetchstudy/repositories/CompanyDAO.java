package it.fabioformosa.jpafetchstudy.repositories;

import it.fabioformosa.jpafetchstudy.entitites.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDAO {
    Page<Company> listCompanyWithEmployeesViaCriteria(int pageNum, int pageSize);
}
