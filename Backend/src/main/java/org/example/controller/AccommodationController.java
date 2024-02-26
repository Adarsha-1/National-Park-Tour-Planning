package org.example.controller;

import org.example.entity.Accommodation;
import org.example.entity.AccommodationReview;
import org.example.model.AccommodationModel;
import org.example.model.AddReviewModel;
import org.example.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.util.List;

@Controller
@RequestMapping(value = "/accommodation")
@CrossOrigin("*")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/{type}")
    public ResponseEntity<List<AccommodationModel>> searchByAccommodationType(@PathVariable("type") String type) {
        List<AccommodationModel> accommodationModelList = accommodationService.searchByType(type.toUpperCase());
        return new ResponseEntity<List<AccommodationModel>>(accommodationModelList, HttpStatus.OK);
    }

    @GetMapping(value = "/park/{parkName}")
    public ResponseEntity<List<AccommodationModel>> searchByParkName(@PathVariable("parkName") String parkName) {
        List<AccommodationModel> accommodationModelList = accommodationService.searchByParkName(parkName.toLowerCase());
        return new ResponseEntity<List<AccommodationModel>>(accommodationModelList, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodationName/{accommodationName}")
    public ResponseEntity<AccommodationModel> searchByAccommodationName(@PathVariable("accommodationName") String accommodationName) {
        AccommodationModel accommodation = accommodationService.searchByAccommodationName(accommodationName);
        return new ResponseEntity<AccommodationModel>(accommodation, HttpStatus.OK);
    }

    @PostMapping(value = "/review")
    public ResponseEntity<AccommodationReview> addReviewUsingRequestBody(@RequestBody AddReviewModel addReviewModel) {
        System.out.println("review details are " + addReviewModel);
        AccommodationReview newReview = accommodationService.addReviewUsingRequestBody(addReviewModel);

        return new ResponseEntity<>(newReview, HttpStatus.ACCEPTED);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
