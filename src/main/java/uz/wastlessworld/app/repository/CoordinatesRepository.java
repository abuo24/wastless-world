package uz.wastlessworld.app.repository;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.Coordinates;

import java.util.UUID;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, UUID> {

}
