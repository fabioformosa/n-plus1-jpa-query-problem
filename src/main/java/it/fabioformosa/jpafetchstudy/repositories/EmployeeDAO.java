package it.fabioformosa.jpafetchstudy.repositories;

import it.fabioformosa.jpafetchstudy.entitites.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeDAO {
    Page<Employee> listEmployeesWithCompanyByCriteriaBuilder(int pageNum, int pageSize);
}
