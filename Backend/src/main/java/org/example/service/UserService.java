package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.entity.AuthenticationProvider;
import org.example.entity.OtpVerification;
import org.example.entity.User;
import org.example.entity.VerificationToken;
import org.example.exception.EmailVerificationException;
import org.example.exception.GeneralException;
import org.example.exception.UserAlreadyExistsException;
import org.example.model.Login;
import org.example.model.LoginModel;
import org.example.model.ResetPassword;
import org.example.repo.OtpVerificationRepository;
import org.example.repo.UserRepository;
import org.example.repo.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OtpService otpService;

    @Autowired
    private Environment environment;

    @Autowired
    private Encryption encryption;

    public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository,
                       JavaMailSender mailSender, OtpService otpService,
                       Environment environment, Encryption encryption) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailSender = mailSender;
        this.otpService = otpService;
        this.environment = environment;
        this.encryption = encryption;
    }

    public User add(User user, String siteUrl) throws Exception{
        System.out.println("service user value is : " + user);
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user.getContactNo() != null) {
            User user2 = userRepository.findByContactNumber(user.getContactNo());
            if (user2 != null) {
                throw new GeneralException("User already exists with the same contact number");
            }
        }
        if (user1 == null){
            user.setEmailEnabled(false);
            user.setPhoneEnabled(false);
            user.setDateOfBirth(java.sql.Date.valueOf(user.getDateOfBirth().toLocalDate().plusDays(1)));
            user.setUserName(generateUserName(user));
            user.setAuthProvider(AuthenticationProvider.LOCAL);
            String password = user.getPassword();
            user.setPassword(encryption.encrypt(password));
            userRepository.save(user);
            sendEmailVerification(user, siteUrl, user.getUserName());
            if (user.getContactNo() != null) {
                otpService.generateOtp(user);
            }
            return user;
        } else {
            throw new UserAlreadyExistsException("User already exists with the same email address: " + user.getEmail());
        }

    }

    public LoginModel addUserFromGoogleAccount(String email, String name) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            User user1 = new User();
            user1.setFirstName(name);
            user1.setEmail(email);
            user1.setEmailEnabled(true);
            user1.setAuthProvider(AuthenticationProvider.GOOGLE);
            userRepository.save(user1);
        }
        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(email);
        loginModel.setOauth2(true);
        loginModel.setUserName(name);
        return loginModel;
    }

    private String generateUserName(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String dob = user.getDateOfBirth().toString();
        int num1 = (Integer.parseInt(dob.substring(8))+Integer.parseInt(dob.substring(5,7))/Integer.parseInt(dob.substring(0,4)));
        //day + month / year in order to give unique digit
        int num2 = Integer.parseInt(dob.substring(1,2));
        String username = lastName.substring(0,(lastName.length()-1)) + firstName.charAt(0) + num1 + num2;
        return username;
    }

    public String emailReVerification(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new GeneralException("No user found with email " + email);
        }
        List<VerificationToken> verificationToken = verificationTokenRepository.findByUserId(user.getId());
        for (int i=0;i<verificationToken.size();i++) {
            verificationTokenRepository.delete(verificationToken.get(i));
        }
        forgotPasswordViaEmail(user);
        return "success";
    }

    private void sendEmailVerification(User user, String siteUrl, String userName) throws Exception {
        User user1 = userRepository.findByEmail(user.getEmail());
        String randomCode = RandomString.make(64);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user1);
        verificationToken.setToken(randomCode);
        verificationToken.setExpiryDate(calculateExpiryDate());
        verificationTokenRepository.save(verificationToken);
        String toAddress = user.getEmail();
        String fromAddress = "adarsha.reddy98@gmail.com";
        String senderName = "National Camping Services";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Your username is: " + userName
                + "Please use the userName mentioned above to login into the website."
                +"\n"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "[[companyName]].";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom(fromAddress);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        content = content.replace("[[name]]", user.getFirstName());
        content = content.replace("[[companyName]]", senderName);
        String verifyUrl = siteUrl + "/user/verifyEmail?code=" + randomCode;
        content = content.replace("[[URL]]", verifyUrl);
        mimeMessageHelper.setText(content, true);
        mailSender.send(message);
    }

    @Transactional
    public String verifyEmailCode(String code) throws EmailVerificationException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(code);
        if (verificationToken == null) {
            throw new EmailVerificationException("Email has already been verified or the link has expired");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        Date now = new Date(dateTimeFormatter.format(today));
        if (verificationToken.getExpiryDate().compareTo(now) >= 0) {
            User user = userRepository.getOne(verificationToken.getUser().getId());
            user.setEmailEnabled(true);
            userRepository.save(user);
            /*verificationToken.setToken(null);
            verificationToken.setExpiryDate(null);*/
            verificationTokenRepository.delete(verificationToken);
            List<VerificationToken> verificationTokenList = verificationTokenRepository.findByUserId(user.getId());
            for (int i=0;i<verificationTokenList.size();i++) {
                verificationTokenRepository.delete(verificationTokenList.get(i));
            }
            return "success";
        } else {
            throw new EmailVerificationException("Link/Code has expired");
        }

    }

    @Transactional
    public String verifyEmailCodeForforgot(String code) throws EmailVerificationException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(code);
        if (verificationToken == null) {
            throw new EmailVerificationException("Invalid code");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        Date now = new Date(dateTimeFormatter.format(today));
        if (verificationToken.getExpiryDate().compareTo(now) >= 0) {
            User user = userRepository.getOne(verificationToken.getUser().getId());
            user.setEmailEnabled(true);
            userRepository.save(user);
            /*verificationToken.setToken(null);
            verificationToken.setExpiryDate(null);*/
            verificationTokenRepository.delete(verificationToken);
            List<VerificationToken> verificationTokenList = verificationTokenRepository.findByUserId(user.getId());
            for (int i=0;i<verificationTokenList.size();i++) {
                verificationTokenRepository.delete(verificationTokenList.get(i));
            }
            return "success";
        } else {
            throw new EmailVerificationException("Link/Code has expired");
        }

    }

    public LoginModel loginUser(Login login) throws GeneralException{
        User user = userRepository.findByUserName(login.getUserName());
        if (user == null) {
            throw new GeneralException("User not found Exception");
        }
        if (!user.isEmailEnabled()) {
            throw new GeneralException("Account is not yet activated");
        }
        String decodePassword = encryption.decrypt(user.getPassword());
        if (decodePassword.equals(login.getPassword())) {
            LoginModel loginModel = new LoginModel();
            loginModel.setUserName(user.getUserName());
            loginModel.setOauth2(false);
            return loginModel;
        }
        throw new GeneralException("Incorrect Password");
    }

    private Date calculateExpiryDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        return new Date(dateTimeFormatter.format(tomorrow));
    }

    private String passwordEncoder(String password) {
        String encodedString = "";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(getPropertyValue("encrypt.key").getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted=cipher.doFinal(password.getBytes());
            encodedString = new String(encrypted);

        } catch (Exception e) {
            throw new GeneralException(e.getMessage());
        }
        return encodedString;
    }

    private String passwordDecode(String password) {
        String decodedString="";

        try {
            System.out.println(getPropertyValue("encrypt.key"));
            SecretKeySpec skeyspec=new SecretKeySpec(getPropertyValue("encrypt.key").getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted=cipher.doFinal(password.getBytes());
            decodedString = new String(decrypted);

        } catch (Exception e) {
            throw new GeneralException(e.getMessage());
        }
        return decodedString;
    }

    public String forgotPassword(User user, String providerSelected) throws Exception{
        if (providerSelected.toLowerCase().equalsIgnoreCase("email")) {
            return forgotPasswordViaEmail(user);
        } else if (providerSelected.toLowerCase().equalsIgnoreCase( "phone")) {
            return forgotPasswordViaPhone(user);
        } else if (providerSelected.toLowerCase().equalsIgnoreCase( "security")) {
            return forgotPasswordViaSecurity(user);
        } else {
            throw new GeneralException("Invalid selection");
        }
    }

    private String forgotPasswordViaSecurity(User user) throws Exception {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null ) {
            throw new GeneralException("No Email found");
        }
        if (existingUser.getSecurityQuestion() == null) {
            throw new GeneralException("No security question found");
        }
        if (!user.getSecurityQuestion().equalsIgnoreCase(existingUser.getSecurityQuestion())) {
            throw new GeneralException("This question is invalid with your account");
        } else {
            if (user.getSecurityAnswer().equalsIgnoreCase(existingUser.getSecurityAnswer())) {
                return "success";
            } else {
                throw  new GeneralException("Answer didn't match");
            }
        }
    }

    public String resendOtpForgot(Long contactNo) throws Exception {
        User user = userRepository.findByContactNumber(contactNo);
        if (user == null ){
            throw new GeneralException("No Email found");
        }
        List<OtpVerification> otpVerifications = otpVerificationRepository.findByUserId(user.getId());
        for (int i=0;i<otpVerifications.size();i++) {
            otpVerificationRepository.delete(otpVerifications.get(i));
        }
        return forgotPasswordViaPhone(user);
    }

    private String forgotPasswordViaPhone(User user) throws Exception {
        User existingUser = userRepository.findByContactNumber(user.getContactNo());
        if (existingUser == null ) {
            throw new GeneralException("No Account found with contact no");
        }
        /*if (!existingUser.isPhoneEnabled()) {
            throw new GeneralException("Your contact number is not yet verified/valid");
        }*/
        otpService.generateOtp(existingUser);
        return "success";
    }

    public String verifyForgotPasswordCode(String code, String providedSelector) throws Exception{
        if (providedSelector.toLowerCase().equalsIgnoreCase("email")) {
            return verifyEmailCodeForforgot(code);
        } else if (providedSelector.toLowerCase().equalsIgnoreCase("phone")) {
            return otpService.verifyOtp(Integer.valueOf(code));
        } else {
            throw new GeneralException("Invalid option");
        }
    }

    private String forgotPasswordViaEmail(User user) throws Exception{
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null ) {
            throw new GeneralException("No Email found");
        } else if (existingUser.getAuthProvider().equals(AuthenticationProvider.GOOGLE)) {
            throw new GeneralException("No account found with the email");
        }
        String randomCode = RandomString.make(6);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(existingUser);
        verificationToken.setToken(randomCode);
        verificationToken.setExpiryDate(calculateExpiryTime());
        verificationTokenRepository.save(verificationToken);
        String toAddress = existingUser.getEmail();
        String fromAddress = "adarsha.reddy98@gmail.com";
        String senderName = "National Camping Services";
        String subject = "Reset Password";
        String content = "Dear [[name]],<br>"
                +"\n Your account : [[userName]]"
                + "\n<b>Your code is: [[randomCode]]</b>"
                + "\nPlease enter [[randomCode]] within the next 10 minutes to finish reset your password."
                +"\n"
                + "Thank you,<br>"
                + "[[companyName]].";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom(fromAddress);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        content = content.replace("[[name]]", existingUser.getFirstName());
        content = content.replace("[[companyName]]", senderName);
        content = content.replace("[[randomCode]]", randomCode);
        content = content.replace("[[userName]]", existingUser.getUserName());
        mimeMessageHelper.setText(content, true);
        mailSender.send(message);
        return "success";
    }

    private Date calculateExpiryTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime tomorrow = LocalDateTime.now().plusMinutes(10);
        return new Date(dateTimeFormatter.format(tomorrow));
    }

    private String getPropertyValue(String key) {
        return environment.getProperty(key);
    }

    public String resetPassword(ResetPassword resetPassword, String providerSelected) throws GeneralException{
        if (!resetPassword.getNewPassword().equals(resetPassword.getOldPassword())) {
            throw new GeneralException("Confirm Password doesn't match with new password");
        }
        User user = new User() ;
        if (providerSelected.toLowerCase().equals("email") | providerSelected.toLowerCase().equals("security")) {
            user = userRepository.findByEmail(resetPassword.getEmail());
            user.setEmailEnabled(true);
        } else if (providerSelected.toLowerCase().equals("phone")) {
            user = userRepository.findByContactNumber(resetPassword.getContactNo());
            user.setPhoneEnabled(true);
        }
        if (user == null) {
            throw new GeneralException("User not found Exception");
        }
        if (user.getPassword().equals(resetPassword.getNewPassword())) {
            throw new GeneralException("Your new password is the same as your old password");
        }
        user.setPassword(encryption.encrypt(resetPassword.getNewPassword()));
        userRepository.save(user);
        return  "success";
    }
}
