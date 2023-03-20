package org.erkebaev.service;

import org.erkebaev.model.Sensor;
import org.erkebaev.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    // Сохраняем в ДБ
    @Transactional
    public void add(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
