package tcu.backend.cars.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CarLinkToAccountInfo {

    private String accountEmail;

    private String carVin;

    private String carSecurityCode;

}
