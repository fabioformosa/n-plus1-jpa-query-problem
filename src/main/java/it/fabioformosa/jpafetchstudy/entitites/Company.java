package it.fabioformosa.jpafetchstudy.entitites;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
@Data
public class Company {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy="company")
    private List<Employee> employees;
}
