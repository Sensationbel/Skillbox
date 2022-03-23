package main.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String breed;
}
