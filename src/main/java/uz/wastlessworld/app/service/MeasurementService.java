package uz.wastlessworld.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.Measurement;
import uz.wastlessworld.app.payload.MeasurementPayload;
import uz.wastlessworld.app.repository.MeasurementRepository;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;


    public Measurement saveMeasurement(MeasurementPayload measurementPayload){
        try {
            Measurement measurement=new Measurement();
            measurement.setPrice(measurementPayload.getPrice());
            measurement.setTypes(measurementPayload.getTypes());

            measurementRepository.save(measurement);
            return measurement;
        }catch (Exception e){
            e.printStackTrace();
            return new Measurement();
        }
    }
}
