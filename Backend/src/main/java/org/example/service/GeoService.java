package org.example.service;

import org.example.entity.ItineraryPark;
import org.example.model.AddParkModel;
import org.example.model.GoogleMapModel;
import org.example.model.Position;
import org.example.repo.ItineraryParkRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeoService {

    WebClient client = WebClient.create();

    @Autowired
    private ItineraryParkRepository itineraryParkRepository;

    public List<GoogleMapModel> reverseGeoCoding(AddParkModel addParkModel) {
        List<ItineraryPark> itineraryParkList = new ArrayList<>();
        if (addParkModel.isOauth2()) {
            itineraryParkList = itineraryParkRepository.findByEmail(addParkModel.getEmail());
        } else {
            itineraryParkList = itineraryParkRepository.findByUserName(addParkModel.getUserName());
        }
        List<GoogleMapModel> googleMapModelList = new ArrayList<>();
        String baseUrl = "https://geocode.search.hereapi.com/v1/geocode?apiKey=BOkt5fqnEu71l776QBkTluDmBTlwmnrOch1IwxhqZhE&q=";

        for (int i =0;i< itineraryParkList.size();i++) {
            String finalUrl = baseUrl + itineraryParkList.get(i).getParkId().getParkName();
            ResponseEntity<String> response = client.get()
                    .uri(URI.create(finalUrl.replace(" ", "%20")))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray ja = (JSONArray) jsonObject.get("items");
            JSONObject h = (JSONObject) ja.get(0);
            JSONObject l = (JSONObject) h.get("position");
            GoogleMapModel googleMapModel = new GoogleMapModel();
            googleMapModel.setId(i+1);
            googleMapModel.setName(itineraryParkList.get(i).getParkId().getParkName());
            Position position = new Position();
            position.setLat((BigDecimal) l.get("lat"));
            position.setLng((BigDecimal) l.get("lng"));
            googleMapModel.setPosition(position);
            googleMapModelList.add(googleMapModel);


        }

        return googleMapModelList;

    }
}
