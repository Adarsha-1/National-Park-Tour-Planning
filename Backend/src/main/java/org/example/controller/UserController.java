package org.example.controller;

import jakarta.ws.rs.QueryParam;
import org.example.entity.User;
import org.example.model.*;
import org.example.service.OtpService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping(value = "/register")
    public ResponseEntity<User> add(@RequestBody User user, HttpServletRequest request) throws Exception{
        System.out.println("User details are : "+ user);
        System.out.println("site url is : " + getSiteURL(request));
        User newUser = userService.add(user, getSiteURL(request));
        //otpService.generateOtp(user);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/verifyEmail", params = "code")
    public ResponseEntity<String> emailVerificationCode(@QueryParam(value = "code") String code) throws Exception{
        String result = userService.verifyEmailCode(code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/verifyOtp", params = "code")
    public ResponseEntity<String> otpVerification(@QueryParam(value = "code") Integer code) throws Exception {
        String result = otpService.verifyOtp(code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginModel> login(@RequestBody Login login) throws Exception{
        LoginModel response = userService.loginUser(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/create/oauth2/sign")
    public ResponseEntity<LoginModel> loginOauth2(@RequestBody Oauth2 oauth2) throws Exception{
        System.out.println("entered oauth");
        LoginModel loginModel = userService.addUserFromGoogleAccount(oauth2.getEmail(), oauth2.getName());
        return new ResponseEntity<>(loginModel, HttpStatus.OK);
    }


    /*@PostMapping(value = "/resend/email")
    public ResponseEntity<String> emailReVerification(@RequestBody String email, HttpServletRequest httpServletRequest) throws Exception {
        String result = userService.emailReVerification(email, getSiteURL(httpServletRequest));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/

    @PostMapping(value = "/resend/email")
    public ResponseEntity<String> emailReVerification(@RequestBody User user) throws Exception {
        String result = userService.emailReVerification(user.getEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/resend/phone")
    public ResponseEntity<String> resendOtpForgotPassword(@RequestBody User user) throws Exception {
        String result = userService.resendOtpForgot(user.getContactNo());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/forgot/password", params = {"providerSelected"})
    public ResponseEntity<String> forgotPasswordVerification(@RequestBody User user, @QueryParam("providerSelected") String providerSelected) throws Exception {
        userService.forgotPassword(user, providerSelected);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/forgot/password/code/verify", params = {"providerSelected", "code"})
    public ResponseEntity<String> forgotPasswordOtpVerification(@QueryParam("code") String code, @QueryParam("providerSelected") String providerSelected) throws Exception {
        userService.verifyForgotPasswordCode(code, providerSelected);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping(value = "/resetPassword", params = {"providerSelected"})
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword, @QueryParam("providerSelected") String providerSelected) throws Exception{
        String response = userService.resetPassword(resetPassword, providerSelected);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
