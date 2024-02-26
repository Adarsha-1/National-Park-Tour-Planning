package org.example.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.example.entity.OtpVerification;
import org.example.entity.User;
import org.example.exception.EmailVerificationException;
import org.example.repo.OtpVerificationRepository;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    //@Value("${twilio.accountId}")
    private static String twilioAccountSid = "AC8deb1452551cdd56ceb1f562f9bfa405";

    //@Value("${twilio.authToken}")
    private static String twilioToken = "bc808c1900d6b82fec91be507ad0396a";

    public OtpService(OtpVerificationRepository otpVerificationRepository, UserRepository userRepository) {
        this.otpVerificationRepository = otpVerificationRepository;
        this.userRepository = userRepository;
    }

    public void generateOtp(User user) {
        Twilio.init(twilioAccountSid, twilioToken);
        int min = 100000;
        int max = 999999;
        Integer randomCode = (int) (Math.random()*(max-min + 1) + min);
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setToken(randomCode);
        otpVerification.setExpiryDate(calculateExpiryDate());
        otpVerification.setUser(user);
        String body = "Hello " + user.getFirstName() + "\n" + "Your camping service verification code is : " + randomCode
                + "\n" + "It is valid only for 4 minutes. Do not share it with others";
        Message.creator(new PhoneNumber(String.valueOf(user.getContactNo())), new PhoneNumber("+12055284215"), body).create();
        otpVerificationRepository.save(otpVerification);
    }

    private Date calculateExpiryDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime tomorrow = LocalDateTime.now().plusMinutes(4);
        return new Date(dateTimeFormatter.format(tomorrow));
    }

    @Transactional
    public String verifyOtp(Integer token) throws EmailVerificationException {
        OtpVerification otpVerification = otpVerificationRepository.findByToken(token);
        if (otpVerification == null) {
            throw new EmailVerificationException("Wrong Otp. Retry again.");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        Date now = new Date(dateTimeFormatter.format(today));
        if (otpVerification.getExpiryDate().compareTo(now) >= 0) {
            User user = userRepository.getOne(otpVerification.getUser().getId());
            user.setPhoneEnabled(true);
            userRepository.save(user);
            //otpVerification.setToken(null);
            otpVerificationRepository.delete(otpVerification);
            List<OtpVerification> otpVerificationList = otpVerificationRepository.findByUserId(user.getId());
            for (int i=0;i<otpVerificationList.size();i++) {
                otpVerificationRepository.delete(otpVerificationList.get(i));
            }
            return "Success";
        } else {
            otpVerificationRepository.delete(otpVerification);
            throw new EmailVerificationException("Otp has expired");
        }
    }

    private String getPropertyValue(String key) {
        return environment.getProperty(key);
    }
}
