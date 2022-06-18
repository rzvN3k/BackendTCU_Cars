package tcu.backend.cars.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
public class CarInformations {

    @Column(name = "car_vin")
    @Id
    private String carVin;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_release_date")
    private Date carReleaseDate;

    @Column(name = "car_colour")
    private String carColour;

    @Column(name = "car_number_of_doors")
    private Short carNumberOfDoors;

    @Column(name = "car_engine_power")
    private Integer carEnginePower;

    @Column(name = "car_cylindrical_capacity")
    private Integer carCylindricalCapacity;

    @Column(name = "car_mapped_to_account")
    private Boolean carMappedToAccount;

    @Column(name = "car_security_mapping_key")
    private String carSecurityMappingKey;

    @Column(name = "car_owner_email")
    private String carOwnerEmail;

    @Column(name = "car_registration_status")
    private Boolean carRegistrationStatus;

}
