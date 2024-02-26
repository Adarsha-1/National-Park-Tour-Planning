package org.example.controller;


import org.example.entity.Payment;
import org.example.model.AddParkModel;
import org.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/pay")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

/*
    @PostMapping("/{Email}")
    public ResponseEntity payItinerary(@PathVariable("Email") String email) throws Exception {
        paymentService.addPayment(email);
        return new ResponseEntity<>("You paid for your itinerary", HttpStatus.OK);
    }


 */

    @PostMapping()
    public ResponseEntity payItineraryUsingBody(@RequestBody AddParkModel addParkModel) throws Exception {
        paymentService.addPaymentUsingBody(addParkModel);
        return new ResponseEntity<>("You paid for your itinerary", HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<Double> getUserItineraryCost(@RequestBody AddParkModel addParkModel) throws Exception {
        double cost = paymentService.calculateItineraryTotalPriceUsingBody(addParkModel.getUserName());
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }


    @PostMapping("/test2")
    public ResponseEntity<Double> getUserItineraryCost2(@RequestBody AddParkModel addParkModel) throws Exception {
        double cost = paymentService.calculateItineraryTotalPrice(addParkModel.getEmail());
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }



}
