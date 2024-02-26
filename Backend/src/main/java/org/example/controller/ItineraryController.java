package org.example.controller;


import org.example.entity.*;
import org.example.model.AddParkModel;
import org.example.model.ItineraryModel;
import org.example.repo.ItineraryParkRepository;
import org.example.repo.ItineraryParkRidesRepository;
import org.example.repo.ItineraryRepository;
import org.example.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/itinerary")
@CrossOrigin(value = "*")
public class ItineraryController {


    // ask about autowired

   @Autowired
   private ItineraryService itineraryService;

   @Autowired
   private ItineraryParkRidesRepository itineraryParkRidesRepository;

   @Autowired
   private ItineraryParkRepository itineraryParkRepository;

   @PostMapping (value = "/full")
    public ResponseEntity<List<ItineraryModel>> getEntireItinerary(@RequestBody AddParkModel addParkModel) {
        List<ItineraryModel> itineraryModelList = itineraryService.getItinerary(addParkModel);
        return new ResponseEntity<>(itineraryModelList, HttpStatus.OK);
    }
/*
   //pass park name
    @PostMapping("/add/{userName}/{Park}")
    public ResponseEntity addPark(@PathVariable("userName") String email, @PathVariable("Park") String park) throws Exception{
        itineraryService.addPark(email, park);
        return new ResponseEntity<>("You added a park to your itinerary", HttpStatus.OK);
    }


 */
    @PostMapping("/add/park")
    public ResponseEntity addParkUsingRequestBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.addParkUsingRequestBody(addParkModel);
        return new ResponseEntity<>("You added a park to your itinerary", HttpStatus.OK);
    }

    @PostMapping("/add/{Email}/{Park}/{ParkRide}")
    public ResponseEntity addParkRide(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkRide") String parkRide) throws Exception{
        itineraryService.addParkRide(email, park, parkRide);
        return new ResponseEntity<>("You added a park ride to your itinerary", HttpStatus.OK);
    }

    @PostMapping("/add/parkRide")
    public ResponseEntity addParkRideUsingRequestBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.addParkRideUsingBody(addParkModel);
        return new ResponseEntity<>("You added a park ride to your itinerary", HttpStatus.OK);
    }

/*
    @PostMapping("/{ParkRide}")
    public ResponseEntity addParkRide(Itinerary itinerary, @PathVariable("ParkRide")ParkRides ParkRide) throws Exception{
        itineraryService.addParkRide(itinerary, ParkRide);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


 */

    @PostMapping("/delete/{Email}/{Park}")
    public ResponseEntity removePark(@PathVariable("Email") String email, @PathVariable("Park") String park) throws Exception{
        itineraryService.removePark(email, park);
        return new ResponseEntity<>("You deleted a park and the associated park rides, transportation, and accommodations", HttpStatus.OK);
    }

    @PutMapping("/delete/park")
    public ResponseEntity<List<ItineraryModel>> removeParkUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.removeParkUsingBody(addParkModel);
        List<ItineraryModel> itineraryModelList = itineraryService.getItinerary(addParkModel);
        return new ResponseEntity<>(itineraryModelList, HttpStatus.OK);
    }



    @PostMapping("/delete/{Email}/{Park}/{ParkRide}")
    public ResponseEntity removeParkRide(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkRide") String parkRide ) throws Exception{
        itineraryService.deleteParkRide(email, park, parkRide);
        return new ResponseEntity<>("You deleted a park ride", HttpStatus.OK);
    }

    @DeleteMapping("/delete/parkRide")
    public ResponseEntity removeParkRideUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.deleteParkRideUsingBody(addParkModel);
        return new ResponseEntity<>("You deleted a park ride", HttpStatus.OK);
    }

    @PostMapping("/add/Transportation/{Email}/{Park}/{ParkTransportation}")
    public ResponseEntity addParkTransportation(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkTransportation") String parkTransportation ) throws Exception{
        itineraryService.addParkTransportation(email, park, parkTransportation);
        return new ResponseEntity<>("You added transportation", HttpStatus.OK);
    }

    @PostMapping("/add/transportation")
    public ResponseEntity addParkTransportationUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.addParkTransportationUsingBody(addParkModel);
        return new ResponseEntity<>("You added transportation", HttpStatus.OK);
    }

    @PostMapping("/delete/Transportation/{Email}/{Park}/{ParkTransportation}")
    public ResponseEntity removeParkTransportation(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkTransportation") String parkTransportation ) throws Exception{
        itineraryService.deleteParkTransportation(email, park, parkTransportation);
        return new ResponseEntity<>("You deleted transportation", HttpStatus.OK);
    }

    @DeleteMapping("/delete/transportation")
    public ResponseEntity removeParkTransportationUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.deleteParkTransportationUsingBody(addParkModel);
        return new ResponseEntity<>("You deleted transportation", HttpStatus.OK);
    }


    @PostMapping("/add/Accommodation/{Email}/{Park}/{ParkAccommodation}")
    public ResponseEntity addParkAccommodation(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkAccommodation") String parkAccommodation ) throws Exception{
        itineraryService.addParkAccommodation(email, park, parkAccommodation);
        return new ResponseEntity<>("You added an accommodation", HttpStatus.OK);
    }

    @PostMapping("/add/accommodation")
    public ResponseEntity addParkAccommodationUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.addParkAccommodationUsingBody(addParkModel);
        return new ResponseEntity<>("You added an accommodation", HttpStatus.OK);
    }

    @PostMapping("/delete/Accommodation/{Email}/{Park}/{ParkAccommodation}")
    public ResponseEntity removeParkAccommodation(@PathVariable("Email") String email, @PathVariable("Park") String park, @PathVariable("ParkAccommodation") String parkAccommodation ) throws Exception{
        itineraryService.deleteParkAccommodation(email, park, parkAccommodation);
        return new ResponseEntity<>("You deleted an accommodation", HttpStatus.OK);
    }

    @DeleteMapping("/delete/accommodation")
    public ResponseEntity removeParkAccommodationUsingBody(@RequestBody AddParkModel addParkModel) throws Exception{
        itineraryService.deleteParkAccommodationUsingBody(addParkModel);
        return new ResponseEntity<>("You deleted an accommodation", HttpStatus.OK);
    }



    @GetMapping(value = "sample")
    public ResponseEntity<List<ItineraryParkRides>> sampleQueryCheck() {
        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchById(113);
        return new ResponseEntity<>(parkRidesList,HttpStatus.OK);
    }

    @GetMapping(value = "sample2")
    public ResponseEntity<List<ItineraryPark>> sampleQueryCheck2() {
        List<ItineraryPark> parkList = itineraryParkRepository.findByItinerary(3);
        return new ResponseEntity<>(parkList,HttpStatus.OK);
    }
/*
    @PostMapping("/delete/{ParkRide}")
    public ResponseEntity deleteParkRide(Itinerary itinerary, @PathVariable("ParkRide")ParkRides ParkRide) throws Exception{
        itineraryService.deleteParkRide(itinerary, ParkRide);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }



*/

}
