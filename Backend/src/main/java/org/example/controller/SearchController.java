package org.example.controller;

import org.example.model.ParkModel;
import org.example.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/search")
@CrossOrigin(origins="*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/{state}")
    public ResponseEntity<List<ParkModel>> searchByState(@PathVariable("state") String state) {
        List<ParkModel> parkModelList = searchService.searchBasedOnState(state.toLowerCase());
        return new ResponseEntity<>(parkModelList, HttpStatus.OK);
    }

    @GetMapping(value = "/park/{parkName}")
    public ResponseEntity<ParkModel> searchByPark(@PathVariable("parkName") String parkName) {
        ParkModel parkModel = searchService.searchBasedOnParkName(parkName.toLowerCase());
        return new ResponseEntity<>(parkModel, HttpStatus.OK);
    }

    @GetMapping(value = "/ride/{ride}")
    public ResponseEntity<List<ParkModel>> searchByRide(@PathVariable("ride") String ride) {
        List<ParkModel> parkModelList = searchService.searchByRide(ride.toLowerCase());
        return new ResponseEntity<>(parkModelList, HttpStatus.OK);
    }
}

