package org.example.service;


import org.example.entity.*;
import org.example.exception.GeneralException;
import org.example.model.ParkModel;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkRidesRepository parkRidesRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RideRepository rideRepository;

    public List<ParkModel> searchBasedOnState(String stateName) {

        State state = stateRepository.findByName(stateName);

        if (state == null) {
            throw new GeneralException("No state found with name " + stateName);
        }

        List<Park> parkList = parkRepository.findByState(state.getId());

        List<ParkModel> parkModelList = new ArrayList<>();

        for (int i=0; i< parkList.size();i++) {
            ParkModel parkModel = new ParkModel();
            parkModel.setPrice(parkList.get(i).getEntryPrice());
            parkModel.setAddress(parkList.get(i).getAddress());
            parkModel.setParkName(parkList.get(i).getParkName());
            parkModel.setDescription(parkList.get(i).getDescription());
            List<ParkRides> parkRidesList = parkRidesRepository.findByParkId(parkList.get(i).getId());
            List<Rides> ridesList = new ArrayList<>();
            for (int j=0;j<parkRidesList.size();j++) {
                ridesList.add(parkRidesList.get(j).getRides());
            }
            parkModel.setRideList(ridesList);
            parkModel.setImages(extractImages(parkList.get(i).getId()));
            parkModelList.add(parkModel);
        }

        return parkModelList;
    }

    public ParkModel searchBasedOnParkName(String parkName) {

        Park park = parkRepository.findByParkName(parkName.toLowerCase());

        if (park == null) {
            throw new GeneralException("No park found with name " + parkName);
        }

        //List<Park> parkList = parkRepository.findByState(state.getId());

        ParkModel parkModel = new ParkModel();
        parkModel.setImages(extractImages(park.getId()));
        parkModel.setDescription(park.getDescription());
        parkModel.setAddress(park.getAddress());
        parkModel.setPrice(park.getEntryPrice());
        parkModel.setRideList(extractRides(park));
        parkModel.setParkName(parkName);

        return parkModel;
    }

    public List<ParkModel> searchByRide(String ride) {
        Rides rides = rideRepository.findByName(ride);
        if (rides == null) {
            throw new GeneralException("No rides present with name " + ride);
        }
        List<ParkRides> parkRidesList = parkRidesRepository.findByRideId(rides.getId());
        if (parkRidesList.size() == 0) {
            throw new GeneralException("No park have the ride " + ride);
        }
        List<ParkModel> parkModelList = new ArrayList<>();

        for (int i=0;i<parkRidesList.size();i++) {
            Park park = parkRepository.findByParkId(parkRidesList.get(i).getParkId().getId());
            ParkModel parkModel = new ParkModel();
            parkModel.setAddress(park.getAddress());
            parkModel.setDescription(park.getDescription());
            parkModel.setParkName(park.getParkName());
            parkModel.setPrice(park.getEntryPrice());
            parkModel.setImages(extractImages(park.getId()));
            parkModel.setRideList(extractRides(park));
            parkModelList.add(parkModel);
        }
        return parkModelList;
    }

    private List<byte[]> extractImages(Integer parkId) {
        List<Image> image = imageRepository.findByParkId(parkId);
        List<byte[]> bytes = new ArrayList<>();
        for(int i=0;i<image.size();i++){
            bytes.add(imageService.decompressBytes(image.get(i).getPicByte()));
        }
        return bytes;

    }

    private List<Rides> extractRides(Park park) {
        List<ParkRides> parkRidesList = parkRidesRepository.findByParkId(park.getId());
        List<Rides> ridesList = new ArrayList<>();
        for (int j=0;j<parkRidesList.size();j++) {
            ridesList.add(parkRidesList.get(j).getRides());
        }
        return ridesList;
    }

}
