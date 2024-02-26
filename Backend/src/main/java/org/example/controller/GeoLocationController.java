package org.example.controller;


import org.example.model.AddParkModel;
import org.example.model.GoogleMapModel;
import org.example.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/location")
@CrossOrigin(value = "*")
public class GeoLocationController {

    @Autowired
    GeoService geoService;


    @PostMapping("/reverse-geocoder")
    public ResponseEntity<List<GoogleMapModel>> reverseGeocoding(@RequestBody AddParkModel address) throws IOException, InterruptedException {
		/*Coordinates coordinates = null;
		try {
			coordinates = geolocationService.reverseGeocoding(address);
			log.debug("reverseGeocoding()", address);
		} catch (IOException | InterruptedException e) {
			log.error("reverseGeocoding()", e);
			return new ResponseEntity<>(coordinates, HttpStatus.NOT_FOUND);
		}*/
        List<GoogleMapModel> googleMapModelList = geoService.reverseGeoCoding(address);
        return new ResponseEntity<>(googleMapModelList, HttpStatus.OK);
    }
}
