package org.example.service;

import org.example.entity.*;
import org.example.exception.GeneralException;
import org.example.exception.InvalidItinerary;
import org.example.model.AddParkModel;
import org.example.model.ItineraryModel;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class ItineraryService {


    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private ParkRepository parkRepository;


    @Autowired
    private ItineraryParkRepository itineraryParkRepository;


    @Autowired
    private ItineraryParkRidesRepository itineraryParkRidesRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItineraryParkTransportationRepository itineraryParkTransportationRepository;

    @Autowired
    private TransportationRepository transportationRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationTypeRepository accommodationTypeRepository;

    @Autowired
    private AccommodationLocationRepo accommodationLocationRepo;

    @Autowired
    private ItineraryParkAccommodationRepository itineraryParkAccommodationRepository;







    public void addPark(String email, String parkName, int adults, int children, int days, Date fromDate, Date toDate) throws InvalidItinerary {
        Itinerary itinerary1 = itineraryRepository.findByUserEmail(email);
        if (itinerary1 == null) {
            Itinerary itinerary = new Itinerary();
            itinerary.setUser(userRepository.findByEmail(email));
            itineraryRepository.save(itinerary);
            ItineraryPark itineraryPark = new ItineraryPark();
            Park park = parkRepository.findByParkName(parkName);
            itineraryPark.setParkId(parkRepository.findByParkName(parkName));
            itineraryPark.setItineraryId(itinerary);
            itineraryPark.setNumOfAdults(adults);
            itineraryPark.setNumOfChildren(children);
            itineraryPark.setNumOfDays(days);
            itineraryPark.setParkFromDate(fromDate);
            itineraryPark.setParkToDate(toDate);

            itineraryParkRepository.save(itineraryPark);


        } else {
            if (isParkPresent(email, parkName))
                throw new InvalidItinerary("This park has already been added");
            else {
                ItineraryPark itineraryPark = new ItineraryPark();
                Park park = parkRepository.findByParkName(parkName);
                itineraryPark.setParkId(parkRepository.findByParkName(parkName));
                itineraryPark.setItineraryId(itineraryRepository.findByUserEmail(email));
                itineraryPark.setNumOfAdults(adults);
                itineraryPark.setNumOfChildren(children);
                itineraryPark.setNumOfDays(days);
                itineraryPark.setParkFromDate(fromDate);
                itineraryPark.setParkToDate(toDate);
                itineraryParkRepository.save(itineraryPark);
            }
        }


    }

    public void addParkUsingRequestBody(AddParkModel addParkModel) throws InvalidItinerary {
        /**
         * There are 2 types of users logged into the system.
         * 1. Users signed up using registration page
         * 2. Logged into the system using oauth2
         */
        if (addParkModel.isOauth2()) {
            /**
             * User has logged using oauth2 then everything should queried using email
             */
            addPark(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getNumOfAdults()
                    , addParkModel.getNumOfChildren(), addParkModel.getNumOfDays(), addParkModel.getParkFromDate(), addParkModel.getParkToDate());
            return ;
        }
        Itinerary itinerary1 = itineraryRepository.findByUserName(addParkModel.getUserName());
        if (itinerary1 == null) {
            Itinerary itinerary = new Itinerary();
            itinerary.setUser(userRepository.findByUserName(addParkModel.getUserName()));
            itineraryRepository.save(itinerary);
            ItineraryPark itineraryPark = new ItineraryPark();
            Park park = parkRepository.findByParkName(addParkModel.getParkName().toLowerCase());
            itineraryPark.setParkId(parkRepository.findByParkName(addParkModel.getParkName().toLowerCase()));
            itineraryPark.setItineraryId(itinerary);
            itineraryPark.setNumOfAdults(addParkModel.getNumOfAdults());
            itineraryPark.setNumOfChildren(addParkModel.getNumOfChildren());
            itineraryPark.setNumOfDays(addParkModel.getNumOfDays());
            itineraryPark.setParkFromDate(addParkModel.getParkFromDate());
            itineraryPark.setParkToDate(addParkModel.getParkToDate());


            itineraryParkRepository.save(itineraryPark);


        } else {
            if (isParkPresentUsingUserName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase()))
                throw new InvalidItinerary("This park has already been added");
            else {
                ItineraryPark itineraryPark = new ItineraryPark();
                //Park park = parkRepository.findByParkName(parkName);
                itineraryPark.setParkId(parkRepository.findByParkName(addParkModel.getParkName().toLowerCase().replace("%20", " ")));
                itineraryPark.setItineraryId(itineraryRepository.findByUserName(addParkModel.getUserName().toLowerCase()));
                itineraryPark.setNumOfDays(addParkModel.getNumOfDays());
                itineraryPark.setNumOfAdults(addParkModel.getNumOfAdults());
                itineraryPark.setNumOfChildren(addParkModel.getNumOfChildren());
                itineraryPark.setNumOfDays(addParkModel.getNumOfDays());
                itineraryPark.setParkFromDate(addParkModel.getParkFromDate());
                itineraryPark.setParkToDate(addParkModel.getParkToDate());
                itineraryParkRepository.save(itineraryPark);
            }
        }


    }

    public void removePark(String email, String parkName) throws InvalidItinerary {
        Itinerary itinerary1 = itineraryRepository.findByUserEmail(email);
        if (itinerary1 == null) {
            throw new InvalidItinerary("You cannot remove from an empty inventory");
        } else {
            if (!isParkPresent(email, parkName))
                throw new InvalidItinerary("This park is not in your itinerary");
            else {
                ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
                long millis=System.currentTimeMillis();

                // creating a new object of the class Date
                java.sql.Date date = new java.sql.Date(millis);
                if (date.compareTo(itineraryPark.getParkFromDate()) > 0){
                    throw new GeneralException("The Itinerary is completed");
                }
                deleteAllParkRides(email, parkName);
                deleteAllParkTransportation(email, parkName);
                deleteAllParkAccommodations(email, parkName);
                itineraryParkRepository.delete(itineraryPark);
            }
        }

    }

    public void removeParkUsingBody(AddParkModel addParkModel) throws InvalidItinerary {
        if (addParkModel.isOauth2()) {
            removePark(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase());
            return ;
        }
        Itinerary itinerary1 = itineraryRepository.findByUserName(addParkModel.getUserName());
        if (itinerary1 == null) {
            throw new InvalidItinerary("You cannot remove from an empty inventory");
        } else {
            if (!isParkPresentUsingUserName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase()))
                throw new InvalidItinerary("This park is not in your itinerary");
            else {
                ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
                long millis=System.currentTimeMillis();

                // creating a new object of the class Date
                java.sql.Date date = new java.sql.Date(millis);
                if (date.compareTo(itineraryPark.getParkFromDate()) > 0){
                    throw new GeneralException("The Itinerary is completed");
                }
                deleteAllParkRidesUsingBody(addParkModel);
                deleteAllParkTransportationUsingBody(addParkModel);
                deleteAllParkAccommodationsUsingBody(addParkModel);
                itineraryParkRepository.delete(itineraryPark);
            }
        }

    }


    public boolean isParkPresent(String email, String parkName) {
        List<ItineraryPark> itineraryParkList = itineraryParkRepository.findByEmail(email);
        for (int i = 0; i < itineraryParkList.size(); i++) {
            if (itineraryParkList.get(i).getParkId().getParkName().equals(parkName))
                return true;
        }
        return false;
    }

    public boolean isParkPresentUsingUserName(String userName, String parkName) {
        List<ItineraryPark> itineraryParkList = itineraryParkRepository.findByUserName(userName);
        for (int i = 0; i < itineraryParkList.size(); i++) {
            if (itineraryParkList.get(i).getParkId().getParkName().equals(parkName))
                return true;
        }
        return false;
    }


    public boolean isParkRidePresent(String email, String parkName, String parkRide) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Rides rides = rideRepository.findByName(parkRide);
        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchByIdAndName(itineraryPark.getId(), rides.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            if (parkRidesList.get(i).getRideId().getName().equals(parkRide))
                return true;

        }
        return false;
    }

    public boolean isParkRidePresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Rides rides = rideRepository.findByName(addParkModel.getParkRide().toLowerCase());
        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchByIdAndName(itineraryPark.getId(), rides.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            if (parkRidesList.get(i).getRideId().getName().equals(addParkModel.getParkRide().toLowerCase()))
                return true;

        }
        return false;
    }

    public void deleteParkRidePresent(String email, String parkName, String parkRide) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Rides rides = rideRepository.findByName(parkRide);
        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchByIdAndName(itineraryPark.getId(), rides.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            if (parkRidesList.get(i).getRideId().getName().equals(parkRide))
                itineraryParkRidesRepository.delete(parkRidesList.get(i));

        }

    }

    public void deleteParkRidePresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Rides rides = rideRepository.findByName(addParkModel.getParkRide().toLowerCase());
        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchByIdAndName(itineraryPark.getId(), rides.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            if (parkRidesList.get(i).getRideId().getName().equals(addParkModel.getParkRide().toLowerCase()))
                itineraryParkRidesRepository.delete(parkRidesList.get(i));

        }

    }


    public void deleteAllParkRides(String email, String parkName) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);

        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            itineraryParkRidesRepository.delete(parkRidesList.get(i));
        }

    }

    public void deleteAllParkRidesUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName());

        List<ItineraryParkRides> parkRidesList = itineraryParkRidesRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < parkRidesList.size(); i++) {
            itineraryParkRidesRepository.delete(parkRidesList.get(i));
        }

    }


    public void addParkRide(String email, String parkName, String parkRide) {
        // get itinerary park id
        //get ride id

        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Rides rides = rideRepository.findByName(parkRide);

        ItineraryParkRides itineraryParkRides = new ItineraryParkRides();
        itineraryParkRides.setParkId(itineraryPark);
        itineraryParkRides.setRideId(rides);
        itineraryParkRidesRepository.save(itineraryParkRides);
    }

    public void addParkRideUsingBody(AddParkModel addParkModel) {
        // get itinerary park id
        //get ride id

        if (addParkModel.isOauth2()) {
            addParkRide(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getParkRide().toLowerCase());
            return;
        }

        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Rides rides = rideRepository.findByName(addParkModel.getParkRide().toLowerCase());

        ItineraryParkRides itineraryParkRides = new ItineraryParkRides();
        itineraryParkRides.setParkId(itineraryPark);
        itineraryParkRides.setRideId(rides);
        itineraryParkRidesRepository.save(itineraryParkRides);
    }


    //check for null case
    public void deleteParkRide(String email, String parkName, String parkRide) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (isParkRidePresent(email, parkName, parkRide)) {
            deleteParkRidePresent(email, parkName, parkRide);
        } else {
            throw new InvalidItinerary("Park Ride not present");
        }

    }

    public void deleteParkRideUsingBody(AddParkModel addParkModel) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (addParkModel.isOauth2()) {
            deleteParkRide(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getParkRide().toLowerCase());
            return ;
        }
        if (isParkRidePresentUsingBody(addParkModel)) {
            deleteParkRidePresentUsingBody(addParkModel);
        } else {
            throw new InvalidItinerary("Park Ride not present");
        }

    }

    public boolean isParkTransportationPresent(String email, String parkName, String parkTransportation) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Transportation transportation = transportationRepository.findByName(parkTransportation);
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchByIdAndName(itineraryPark.getId(), transportation.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            if (parkTransportationList.get(i).getTransportationId().getName().equals(parkTransportation))
                return true;



        }
        return false;
    }

    public boolean isParkTransportationPresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Transportation transportation = transportationRepository.findByName(addParkModel.getParkTransportation().toLowerCase());
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchByIdAndName(itineraryPark.getId(), transportation.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            if (parkTransportationList.get(i).getTransportationId().getName().equals(addParkModel.getParkTransportation().toLowerCase()))
                return true;



        }
        return false;
    }

    public void deleteParkTransportationPresent(String email, String parkName, String parkTransportation) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Transportation transportation = transportationRepository.findByName(parkTransportation);
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchByIdAndName(itineraryPark.getId(), transportation.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            if (parkTransportationList.get(i).getTransportationId().getName().equals(parkTransportation))
                itineraryParkTransportationRepository.delete(parkTransportationList.get(i));
        }
    }

    public void deleteParkTransportationPresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Transportation transportation = transportationRepository.findByName(addParkModel.getParkTransportation().toLowerCase());
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchByIdAndName(itineraryPark.getId(), transportation.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            if (parkTransportationList.get(i).getTransportationId().getName().equals(addParkModel.getParkTransportation().toLowerCase()))
                itineraryParkTransportationRepository.delete(parkTransportationList.get(i));
        }
    }


    public void deleteAllParkTransportation(String email, String parkName) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            itineraryParkTransportationRepository.delete(parkTransportationList.get(i));
        }

    }

    public void deleteAllParkTransportationUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName());
        List<ItineraryParkTransportation> parkTransportationList = itineraryParkTransportationRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < parkTransportationList.size(); i++) {
            itineraryParkTransportationRepository.delete(parkTransportationList.get(i));
        }

    }


    public void addParkTransportation(String email, String parkName, String parkTransportation) {
        // get itinerary park id
        //get ride id

        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Transportation transportation = transportationRepository.findByName(parkTransportation);

        ItineraryParkTransportation itineraryParkTransportation = new ItineraryParkTransportation();
        itineraryParkTransportation.setParkId(itineraryPark);
        itineraryParkTransportation.setTransportationId(transportation);
        itineraryParkTransportationRepository.save(itineraryParkTransportation);
    }

    public void addParkTransportationUsingBody(AddParkModel addParkModel) {
        // get itinerary park id
        //get ride id
        if (addParkModel.isOauth2()) {
            addParkTransportation(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getParkTransportation().toLowerCase());
            return;
        }

        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Transportation transportation = transportationRepository.findByName(addParkModel.getParkTransportation().toLowerCase());

        ItineraryParkTransportation itineraryParkTransportation = new ItineraryParkTransportation();
        itineraryParkTransportation.setParkId(itineraryPark);
        itineraryParkTransportation.setTransportationId(transportation);
        itineraryParkTransportationRepository.save(itineraryParkTransportation);
    }


    //check for null case
    public void deleteParkTransportation(String email, String parkName, String parkTransportation) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (isParkTransportationPresent(email, parkName, parkTransportation)) {
            deleteParkTransportationPresent(email, parkName, parkTransportation);
        } else {
            throw new InvalidItinerary("Park Transportation not present");
        }

    }

    public void deleteParkTransportationUsingBody(AddParkModel addParkModel) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (addParkModel.isOauth2()) {
            deleteParkTransportation(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getParkTransportation().toLowerCase());
            return;
        }

        if (isParkTransportationPresentUsingBody(addParkModel)) {
            deleteParkTransportationPresentUsingBody(addParkModel);
        } else {
            throw new InvalidItinerary("Park Transportation not present");
        }

    }


    public boolean isParkAccommodationPresent(String email, String parkName, String parkAccommodation) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Accommodation accommodation = accommodationRepository.findByAccommodationName(parkAccommodation);
        List<ItineraryParkAccommodation> accommodationLocationsList = itineraryParkAccommodationRepository.searchByIdAndName(itineraryPark.getId(), accommodation.getId());
        for (int i = 0; i < accommodationLocationsList.size(); i++) {
            if (accommodationLocationsList.get(i).getAccommodationId().getAccommodationName().equals(parkAccommodation))
                return true;
        }
        return false;
    }

    public boolean isParkAccommodationPresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Accommodation accommodation = accommodationRepository.findByAccommodationName(addParkModel.getAccommodation());
        List<ItineraryParkAccommodation> accommodationLocationsList = itineraryParkAccommodationRepository.searchByIdAndName(itineraryPark.getId(), accommodation.getId());
        for (int i = 0; i < accommodationLocationsList.size(); i++) {
            if (accommodationLocationsList.get(i).getAccommodationId().getAccommodationName().equals(addParkModel.getAccommodation()))
                return true;
        }
        return false;
    }


    public void deleteParkAccommodationPresent(String email, String parkName, String parkAccommodation) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Accommodation accommodation = accommodationRepository.findByAccommodationName(parkAccommodation);
        List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchByIdAndName(itineraryPark.getId(), accommodation.getId());
        for (int i = 0; i < itineraryParkAccommodationList.size(); i++) {
            if (itineraryParkAccommodationList.get(i).getAccommodationId().getAccommodationName().equals(parkAccommodation))
                itineraryParkAccommodationRepository.delete(itineraryParkAccommodationList.get(i));
        }
    }

    public void deleteParkAccommodationPresentUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Accommodation accommodation = accommodationRepository.findByAccommodationName(addParkModel.getAccommodation());
        List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchByIdAndName(itineraryPark.getId(), accommodation.getId());
        for (int i = 0; i < itineraryParkAccommodationList.size(); i++) {
            if (itineraryParkAccommodationList.get(i).getAccommodationId().getAccommodationName().equals(addParkModel.getAccommodation()))
                itineraryParkAccommodationRepository.delete(itineraryParkAccommodationList.get(i));
        }
    }

    public void deleteAllParkAccommodations(String email, String parkName) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < itineraryParkAccommodationList.size(); i++) {
            itineraryParkAccommodationRepository.delete(itineraryParkAccommodationList.get(i));
        }

    }

    public void deleteAllParkAccommodationsUsingBody(AddParkModel addParkModel) {
        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName());
        List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchById(itineraryPark.getId());
        for (int i = 0; i < itineraryParkAccommodationList.size(); i++) {
            itineraryParkAccommodationRepository.delete(itineraryParkAccommodationList.get(i));
        }

    }



    public void addParkAccommodation(String email, String parkName, String parkAccommodation) {
        // get itinerary park id
        //get ride id

        ItineraryPark itineraryPark = itineraryParkRepository.findByEmailAndParkName(email, parkName);
        Accommodation accommodation = accommodationRepository.findByAccommodationName(parkAccommodation);

        ItineraryParkAccommodation itineraryParkAccommodation = new ItineraryParkAccommodation();
        itineraryParkAccommodation.setParkId(itineraryPark);
        itineraryParkAccommodation.setAccommodationId(accommodation);
        itineraryParkAccommodationRepository.save(itineraryParkAccommodation);
    }

    public void addParkAccommodationUsingBody(AddParkModel addParkModel) {
        // get itinerary park id
        //get ride id

        if (addParkModel.isOauth2()) {
            addParkAccommodation(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getAccommodation());
            return;
        }

        ItineraryPark itineraryPark = itineraryParkRepository.findByUserNameAndParkName(addParkModel.getUserName(), addParkModel.getParkName().toLowerCase());
        Accommodation accommodation = accommodationRepository.findByAccommodationName(addParkModel.getAccommodation());

        ItineraryParkAccommodation itineraryParkAccommodation = new ItineraryParkAccommodation();
        itineraryParkAccommodation.setParkId(itineraryPark);
        itineraryParkAccommodation.setAccommodationId(accommodation);
        itineraryParkAccommodation.setFromDate(Date.valueOf(addParkModel.getAccFromDate().toLocalDate().plusDays(1)));
        itineraryParkAccommodation.setToDate(Date.valueOf(addParkModel.getAccToDate().toLocalDate().plusDays(1)));
        itineraryParkAccommodationRepository.save(itineraryParkAccommodation);
    }


    public void deleteParkAccommodation(String email, String parkName, String parkAccommodation) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (isParkAccommodationPresent(email, parkName, parkAccommodation)) {
            deleteParkAccommodationPresent(email, parkName, parkAccommodation);
        } else {
            throw new InvalidItinerary("Park Accommodation not present");
        }

    }

    public void deleteParkAccommodationUsingBody(AddParkModel addParkModel) throws InvalidItinerary {
        // get itinerary park id
        //get ride id

        if (addParkModel.isOauth2()) {
            deleteParkAccommodation(addParkModel.getEmail(), addParkModel.getParkName().toLowerCase(), addParkModel.getAccommodation());
            return;
        }

        if (isParkAccommodationPresentUsingBody(addParkModel)) {
            deleteParkAccommodationPresentUsingBody(addParkModel);
        } else {
            throw new InvalidItinerary("Park Accommodation not present");
        }

    }

    public List<ItineraryModel> getItinerary(AddParkModel addParkModel) {
        Itinerary itinerary;
        if (addParkModel.isOauth2()) {
            itinerary= itineraryRepository.findByUserEmail(addParkModel.getEmail());
        }
        else {
            itinerary = itineraryRepository.findByUserName(addParkModel.getUserName());
        }
        if (itinerary == null) {
            return new ArrayList<>();
        }
        List<ItineraryModel> itineraryModelList = new ArrayList<>();
        List<ItineraryPark> itineraryList = itineraryParkRepository.findByItinerary(itinerary.getId());
        if (itineraryList.size() == 0) {
            return new ArrayList<>();
        }
        for (int i=0;i<itineraryList.size();i++){
            ItineraryModel itineraryModel = new ItineraryModel();
            itineraryModel.setId(i);
            itineraryModel.setParkName(itineraryList.get(i).getParkId().getParkName());
            itineraryModel.setParkFromDate(itineraryList.get(i).getParkFromDate());
            itineraryModel.setParkToDate(itineraryList.get(i).getParkToDate());
            itineraryModel.setParkLocation(itineraryList.get(i).getParkId().getStateId().getName());
            ItineraryParkAccommodation itineraryParkAccommodation = itineraryParkAccommodationRepository.findByParkId(itineraryList.get(i).getId());
            if (itineraryParkAccommodation != null ) {
                itineraryModel.setAccommodationName(itineraryParkAccommodation.getAccommodationId().getAccommodationName());
                itineraryModel.setAccFromDate(itineraryParkAccommodation.getFromDate().toString());
                itineraryModel.setAccToDate(itineraryParkAccommodation.getToDate().toString());
                itineraryModel.setAccPrice(String.valueOf(itineraryParkAccommodation.getAccommodationId().getPrice()));
            }
            ItineraryParkTransportation itineraryParkTransportation = itineraryParkTransportationRepository.findByItineraryId(itineraryList.get(i).getId());
            if (itineraryParkTransportation != null ){
                itineraryModel.setTransportation(itineraryParkTransportation.getTransportationId().getName());
                itineraryModel.setTransStartDate(itineraryParkTransportation.getFromDate().toString());
            }

            itineraryModelList.add(itineraryModel);
        }
        return itineraryModelList;
    }




}
