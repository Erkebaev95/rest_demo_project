package org.erkebaev.controller;

import org.erkebaev.dto.SensorDTO;
import org.erkebaev.model.Sensor;
import org.erkebaev.service.SensorService;
import org.erkebaev.util.MeasurementErrorResponse;
import org.erkebaev.util.MeasurementException;
import org.erkebaev.util.SensorNotValid;
import org.erkebaev.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    //Регистрирует новый сенсор в системе.
    //Другими словами, просто добавляет новый
    //сенсор в таблицу сенсоров в БД.
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensor,
                                                   BindingResult bindingResult) {
        Sensor sensorToAdd = convertToSensor(sensor);

        sensorValidator.validate(sensorToAdd, bindingResult);

        //мы должны валидировать то, что сенсора с таким названием еще нет в БД
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotValid(errorMsg.toString());
        }

        sensorService.add(sensorToAdd);

        // отправляем http ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        // Маппет все поля из дто в объект модели
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
