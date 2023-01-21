package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @Query(
            value = "from Company c join fetch c.employees as e",
            countQuery = "select count(e) from Company e"
    )
    Page<Company> findAllWithEmployees(Pageable pageable);

}
