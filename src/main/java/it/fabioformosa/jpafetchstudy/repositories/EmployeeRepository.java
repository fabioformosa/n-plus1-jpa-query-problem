package it.fabioformosa.jpafetchstudy.repositories;

import it.fabioformosa.jpafetchstudy.entitites.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query(
            value = "from Employee e join fetch e.company as c",
            countQuery = "select count(e) from Employee e"
    )
    Page<Employee> findAllWithCompany(Pageable pageable);

}
