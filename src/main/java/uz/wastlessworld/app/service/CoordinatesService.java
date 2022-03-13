package uz.wastlessworld.app.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.Coordinates;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.CoordinatesPayload;
import uz.wastlessworld.app.repository.CoordinatesRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;
    private final Result result;
    private final Logger logger= LoggerFactory.getLogger(CoordinatesService.class);

    public Coordinates saveCoordinates(CoordinatesPayload coordinatesPayload){
        Coordinates coordinates=new Coordinates();
        coordinates.setLatitude(coordinatesPayload.getLatitude());
        coordinates.setLongitude(coordinatesPayload.getLongitude());
        coordinates.setAddress(coordinatesPayload.getAddress());
        coordinates.setComment(coordinatesPayload.getComment());
        coordinatesRepository.save(coordinates);
        return coordinates;
    }

    public Result editCoordinates(UUID coordinatesId,CoordinatesPayload coordinatesPayload){
        try {
            Coordinates coordinates=coordinatesRepository.findById(coordinatesId).orElseThrow(()->new ResourceNotFoundException("coordinates not found: "+coordinatesId));
            coordinates.setLongitude(coordinatesPayload.getLongitude());
            coordinates.setLatitude(coordinatesPayload.getLatitude());
            coordinates.setComment(coordinatesPayload.getComment());
            coordinatesRepository.save(coordinates);
            return result.success(coordinates);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result deleteCoordinates(UUID coordinatesId){
        try {
            coordinatesRepository.deleteById(coordinatesId);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllCoordinates(){
        return result.success(coordinatesRepository.findAll());
    }

}
