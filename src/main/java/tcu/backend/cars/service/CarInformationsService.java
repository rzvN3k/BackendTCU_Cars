package tcu.backend.cars.service;

import org.springframework.stereotype.Component;
import tcu.backend.cars.model.CarLinkToAccountInfo;
import tcu.backend.cars.service_impl.CarInformationsAPIResponse;

@Component
public interface CarInformationsService {

    CarInformationsAPIResponse getCarByVIN(String carVIN);

    CarInformationsAPIResponse linkCarToAccount(CarLinkToAccountInfo linkageInformations);

    CarInformationsAPIResponse confirmLinkageToken(String confirmationToken);
}
