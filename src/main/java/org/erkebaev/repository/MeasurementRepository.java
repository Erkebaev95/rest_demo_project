package org.erkebaev.repository;

import org.erkebaev.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
