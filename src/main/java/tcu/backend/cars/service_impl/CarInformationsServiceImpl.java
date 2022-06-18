package tcu.backend.cars.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import tcu.backend.cars.model.CarInformations;
import tcu.backend.cars.model.CarLinkToAccountInfo;
import tcu.backend.cars.model.CarLinkageToAccountToken;
import tcu.backend.cars.repository.CarInformationsRepository;
import tcu.backend.cars.repository.ConfirmationTokenRepository;
import tcu.backend.cars.service.CarInformationsService;

import java.util.HashMap;
import java.util.List;

@Component
public class CarInformationsServiceImpl implements CarInformationsService {

    @Autowired
    private CarInformationsRepository carInformationsRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private CarEmailSenderService carEmailSenderService;

    private CarLinkToAccountInfo currentUserProcessing;

    private static final HashMap<CarAPIResponseForClient, String> responsesForClient;

    static {
        responsesForClient = new HashMap<>();
        responsesForClient.put(CarAPIResponseForClient.GET_CAR_BY_VIN_SUCCESS, "Car found in database and retrieved with success");
        responsesForClient.put(CarAPIResponseForClient.GET_CAR_BY_VIN_NOT_FOUND, "Car not found in database, operation failed");
        responsesForClient.put(CarAPIResponseForClient.SECURITY_KEY_NOT_MATCH, "Security code provided for this car is incorrect!");
        responsesForClient.put(CarAPIResponseForClient.RETRIEVE_CAR_FROM_DATABASE_FAILED, "Error occurred when was tried to retrieve the car informations!");
        responsesForClient.put(CarAPIResponseForClient.CAR_ALREADY_LINKED_TO_AN_ACCOUNT, "Car is already linked to another account!");
        responsesForClient.put(CarAPIResponseForClient.LINKAGE_TO_ACCOUNT_SUCCESS, "Car linked to the account with success!");
        responsesForClient.put(CarAPIResponseForClient.TOKEN_SUCCESSFULLY_VALIDATED, "Car linkage token verification successfully terminated");
        responsesForClient.put(CarAPIResponseForClient.TOKEN_VALIDATION_FAILED, "Car linkage token verification failed");
    }

    @Override
    public CarInformationsAPIResponse getCarByVIN(String carVin) {

        CarInformations responseFromDB = carInformationsRepository.findByCarVin(carVin);

        Boolean isResponseOK = responseFromDB != null;
        CarInformationsAPIResponse apiResponse = new CarInformationsAPIResponse(isResponseOK, (isResponseOK == true) ? responsesForClient.get(CarAPIResponseForClient.GET_CAR_BY_VIN_SUCCESS)
                : responsesForClient.get((CarAPIResponseForClient.GET_CAR_BY_VIN_NOT_FOUND)), responseFromDB);
        return apiResponse;

    }

    @Override
    public CarInformationsAPIResponse linkCarToAccount(CarLinkToAccountInfo linkageInformations) {

        //store the current processing informations
        currentUserProcessing = linkageInformations;

        //check if it's not already mapped to another account
        String carVin = linkageInformations.getCarVin();
        Boolean apiResponseStatus = false;
        CarAPIResponseForClient apiResponseReason = CarAPIResponseForClient.NO_RESPONSE;

        CarInformations responseFromDB = carInformationsRepository.findByCarVin(carVin);
        if (responseFromDB != null) {
            if (responseFromDB.getCarMappedToAccount() == false) {
                //check security code
                if (linkageInformations.getCarSecurityCode().equals(responseFromDB.getCarSecurityMappingKey())) {
                    //here we will send mail with the activation link

                    //prepare the confirmation token and save it in DB
                    CarLinkageToAccountToken confirmationToken = new CarLinkageToAccountToken(linkageInformations);
                    confirmationTokenRepository.save(confirmationToken);

                    //create the email to be sent to the client
                    SimpleMailMessage mailToSend = new SimpleMailMessage();
                    mailToSend.setTo(linkageInformations.getAccountEmail());
                    mailToSend.setSubject("Confirmation of the car linking to the account");
                    mailToSend.setFrom("tcubackend@gmail.com");
                    mailToSend.setText("In order to complete the process of the car linkage to your account, please click " +
                            "on the following link: " + "http://localhost:8082/car/confirmCarLinkage?token=" + confirmationToken.getConfirmationToken());

                    //send mail
                    carEmailSenderService.sendMail(mailToSend);

                    //prepare the response for the operation
                    apiResponseStatus= true;
                    apiResponseReason = CarAPIResponseForClient.LINKAGE_TO_ACCOUNT_SUCCESS;

                } else {
                    apiResponseReason = CarAPIResponseForClient.SECURITY_KEY_NOT_MATCH;
                }

            } else {
                //it is already mapped, send response back
                apiResponseReason = CarAPIResponseForClient.CAR_ALREADY_LINKED_TO_AN_ACCOUNT;
            }
        } else {
            //something went wrong with the retrieve from database
            apiResponseReason = CarAPIResponseForClient.RETRIEVE_CAR_FROM_DATABASE_FAILED;
        }

        CarInformationsAPIResponse responseToClient = new CarInformationsAPIResponse(apiResponseStatus, responsesForClient.get(apiResponseReason), null);

        return responseToClient;
    }

    @Override
    public CarInformationsAPIResponse confirmLinkageToken(String confirmationToken) {

        CarAPIResponseForClient response = CarAPIResponseForClient.TOKEN_SUCCESSFULLY_VALIDATED;
        Boolean status = true;

        System.out.println("Confirmation token received: " + confirmationToken);
        List<CarLinkageToAccountToken> token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(!token.isEmpty()) {
            CarInformations car = carInformationsRepository.findByCarVin(currentUserProcessing.getCarVin());

            car.setCarMappedToAccount(true);
            car.setCarOwnerEmail(currentUserProcessing.getAccountEmail());

            carInformationsRepository.save(car);

        } else {
            response = CarAPIResponseForClient.TOKEN_VALIDATION_FAILED;
            status = false;
        }

        CarInformationsAPIResponse responseToClient = new CarInformationsAPIResponse(status, responsesForClient.get(response), null);

        return responseToClient;
    }
}
