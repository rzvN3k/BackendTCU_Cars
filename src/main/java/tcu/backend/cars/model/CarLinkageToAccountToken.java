package tcu.backend.cars.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarLinkageToAccountToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirmation_token_id")
    private long confirmationTokenId;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "token_creation_date")
    private Date tokenCreationDate;

    @Transient
    private CarLinkToAccountInfo carLinkInformations;

    public CarLinkageToAccountToken(CarLinkToAccountInfo carLinkInformations) {
        this.carLinkInformations = carLinkInformations;
        tokenCreationDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}

