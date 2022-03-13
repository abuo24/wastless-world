package uz.wastlessworld.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.MeasurementWeight;

import java.util.UUID;

@Repository
public interface MeasurementWeightRepository extends JpaRepository<MeasurementWeight, UUID> {
}
