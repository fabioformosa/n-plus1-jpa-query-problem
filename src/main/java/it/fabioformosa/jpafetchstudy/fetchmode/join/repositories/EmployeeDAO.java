package it.fabioformosa.jpafetchstudy.fetchmode.join.repositories;

import it.fabioformosa.jpafetchstudy.fetchmode.join.entitites.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeDAO {
    Page<Employee> listEmployeesWithCompanyByCriteriaBuilder(int pageNum, int pageSize);
}
