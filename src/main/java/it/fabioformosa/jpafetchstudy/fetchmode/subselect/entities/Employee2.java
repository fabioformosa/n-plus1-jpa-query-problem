package it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEES2")
@Data
public class Employee2 {

    @Id
    private Long id;

    private String firstname;
    private String lastname;

    @ManyToOne
    @JoinColumn(name="fk_company", nullable = false)
    private Company2 company;
}
