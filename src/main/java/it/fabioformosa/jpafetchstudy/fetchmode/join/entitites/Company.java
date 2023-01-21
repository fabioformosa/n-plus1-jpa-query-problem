package it.fabioformosa.jpafetchstudy.fetchmode.join.entitites;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "Company.employees", attributeNodes = { @NamedAttributeNode("employees")})
})
@Entity
@Table(name = "COMPANIES")
@Data
public class Company {

    @Id
    private Long id;

    private String name;

    //Collections are lazy fetched by default
    @OneToMany(mappedBy="company")
    private List<Employee> employees;
}
