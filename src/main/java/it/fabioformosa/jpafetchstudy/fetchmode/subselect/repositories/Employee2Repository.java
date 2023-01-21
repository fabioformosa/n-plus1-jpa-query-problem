package it.fabioformosa.jpafetchstudy.fetchmode.subselect.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Employee2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface Employee2Repository extends JpaRepository<Employee2, Long>, JpaSpecificationExecutor<Employee2> {

    @Query(
            value = "from Employee2 e join fetch e.company as c",
            countQuery = "select count(e) from Employee2 e"
    )
    Page<Employee2> findAllWithCompany(Pageable pageable);

}
