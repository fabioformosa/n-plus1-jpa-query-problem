package it.fabioformosa.jpafetchstudy.fetchmode.subselect.entities;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMPANIES2")
@Data
public class Company2 {

    @Id
    private Long id;

    private String name;

    //Collections are lazy fetched by default
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy="company")
    private List<Employee2> employees;
}
