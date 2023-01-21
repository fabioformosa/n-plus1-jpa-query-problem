package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepositoryWithEntityGraph extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @EntityGraph(
            value = "Company.employees",
            type = EntityGraph.EntityGraphType.LOAD
    )
    Page<Company> findAll(Pageable pageable);


}
