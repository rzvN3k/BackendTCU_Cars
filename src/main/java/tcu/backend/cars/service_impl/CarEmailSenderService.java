package tcu.backend.cars.service_impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CarEmailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    public CarEmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendMail(SimpleMailMessage emailToSend) {
        javaMailSender.send(emailToSend);
    }


}
