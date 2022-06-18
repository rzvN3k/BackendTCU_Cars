package tcu.backend.cars.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tcu.backend.cars.model.CarLinkageToAccountToken;

import java.util.List;

public interface ConfirmationTokenRepository extends CrudRepository<CarLinkageToAccountToken, Long> {
    @Query("FROM CarLinkageToAccountToken tok WHERE tok.confirmationToken = :token")
    List<CarLinkageToAccountToken> findByConfirmationToken(@Param("token") String token);

}


