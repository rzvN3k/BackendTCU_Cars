package tcu.backend.cars.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tcu.backend.cars.model.CarInformations;
import tcu.backend.cars.model.CarLinkToAccountInfo;
import tcu.backend.cars.service_impl.CarInformationsAPIResponse;
import tcu.backend.cars.service_impl.CarInformationsServiceImpl;

@RestController
public class CarInformationsController {

    @Autowired
    private CarInformationsServiceImpl carInformationsService;

    @PostMapping("/car/getCarInfo")
    public CarInformationsAPIResponse getCarInformations(@RequestBody CarInformations carInformations) {

        CarInformationsAPIResponse responseForGetCar = carInformationsService.getCarByVIN(carInformations.getCarVin());

        return responseForGetCar;
    }

    @PostMapping("/car/linkCarToAccount")
    public CarInformationsAPIResponse linkCarToAccount(@RequestBody CarLinkToAccountInfo carLinkInfo) {

        CarInformationsAPIResponse responseForLinkage = carInformationsService.linkCarToAccount(carLinkInfo);

        return responseForLinkage;
    }

    @PostMapping("/car/confirmCarLinkage")
    public CarInformationsAPIResponse validateConfirmationToken(@RequestParam("token")String confirmationToken) {

        CarInformationsAPIResponse responseForTokenValidation = carInformationsService.confirmLinkageToken(confirmationToken);

        return responseForTokenValidation;

    }
}
