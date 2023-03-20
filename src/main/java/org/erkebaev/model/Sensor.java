package org.erkebaev.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Названия не должен быть пустым!")
    @Size(min = 3, max = 30, message = "Название должен быть в диапазоне от 3 до 30 символов!")
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
