package it.fabioformosa.jpafetchstudy.fetchmode.join.entitites;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEES")
@Data
public class Employee {

    @Id
    private Long id;

    private String firstname;
    private String lastname;

    @ManyToOne
    @JoinColumn(name="fk_company", nullable = false)
    private Company company;
}
