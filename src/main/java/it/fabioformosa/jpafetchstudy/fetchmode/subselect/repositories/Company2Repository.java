package it.fabioformosa.jpafetchstudy.fetchmode.subselect.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities.Company2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface Company2Repository extends JpaRepository<Company2, Long>, JpaSpecificationExecutor<Company2> {

    @Query(
            value = "from Company2 c join fetch c.employees as e",
            countQuery = "select count(e) from Company2 e"
    )
    Page<Company2> findAllWithEmployees(Pageable pageable);

}
