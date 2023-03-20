package org.erkebaev.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Названия не должен быть пустым!")
    @Size(min = 3, max = 30, message = "Название должен быть в диапазоне от 3 до 30 символов!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
