package tcu.backend.cars.service_impl;

import lombok.Getter;
import tcu.backend.cars.model.CarInformations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CarInformationsAPIResponse {

    private Boolean status;

    private String apiResponse;

    private String dateOfProcessing;

    private CarInformations carInformations;

    CarInformationsAPIResponse(Boolean status, String apiResponse, CarInformations carInformations) {

        this.status = status;

        this.apiResponse = apiResponse;

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateOfProcessing = dateTime.format(formatter);

        this.carInformations = new CarInformations();
        this.carInformations = carInformations;

    }
}
