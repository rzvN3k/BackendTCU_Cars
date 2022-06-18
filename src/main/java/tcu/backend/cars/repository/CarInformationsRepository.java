package tcu.backend.cars.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tcu.backend.cars.model.CarInformations;

@Repository
public interface CarInformationsRepository extends CrudRepository<CarInformations, String> {

    CarInformations findByCarVin(String carVin);

}
