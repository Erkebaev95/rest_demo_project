package org.erkebaev.controller;

import org.erkebaev.dto.MeasurementDTO;
import org.erkebaev.model.Measurement;
import org.erkebaev.service.MeasurementService;
import org.erkebaev.util.SensorNotValid;
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
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Measurement> index() {
        return measurementService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public String countRainyDays() {

        return "";
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

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
        measurementService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // Маппет все поля из дто в объект модели
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
