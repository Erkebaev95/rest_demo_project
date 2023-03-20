package org.erkebaev.repository;

import org.erkebaev.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
