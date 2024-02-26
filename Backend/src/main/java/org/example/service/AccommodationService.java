package org.example.service;

import org.example.entity.*;
import org.example.exception.GeneralException;
import org.example.model.AccommodationModel;
import org.example.model.AddReviewModel;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationTypeRepository accommodationTypeRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private AccommodationLocationRepo accommodationLocationRepo;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private AccommodationImageRepository accommodationImageRepository;

    @Autowired
    private ImageService imageService;

    public List<AccommodationModel> searchByType(String type) {
        AccommodationType accommodationType = accommodationTypeRepository.findByType(type);
        if (accommodationType == null) {
            throw new GeneralException("No accommodation with type " + type);
        }
        List<Accommodation> accommodationList = accommodationRepository.findByType((accommodationType.getId()));
        if (accommodationList.size() == 0) {
            throw new GeneralException("No accommodations exists for any park with type " +type);
        }
        List<AccommodationModel> accommodationModelList = new ArrayList<>();
        for (int i=0;i<accommodationList.size();i++) {
            AccommodationModel accommodationModel = new AccommodationModel();
            accommodationModel.setType(type);
            accommodationModel.setDescription(accommodationList.get(i).getDescription());
            accommodationModel.setName(accommodationList.get(i).getAccommodationName());
            accommodationModel.setPrice(accommodationList.get(i).getPrice());
            accommodationModel.setAccommodationLocation(accommodationLocationRepo.findByAId(accommodationList.get(i).getId()));
            accommodationModel.setReviewList(accommodationReviewRepository.findByAccommodationId(accommodationList.get(i).getId()));
            accommodationModelList.add(accommodationModel);
        }
        return accommodationModelList;
    }

    public List<AccommodationModel> searchByParkName(String parkName) {
        Park park = parkRepository.findByParkName(parkName);
        if (park == null) {
            throw new GeneralException("No park with name " + parkName + " is found");
        }
        List<AccommodationLocation> accommodationLocationList = accommodationLocationRepo.findByParkId(park.getId());
        if (accommodationLocationList.size() == 0) {
            throw new GeneralException("No accommodations found near park " + parkName);
        }
        List<AccommodationModel> accommodationModelList = new ArrayList<>();
        for (int i=0;i<accommodationLocationList.size();i++) {
            System.out.print(accommodationLocationList.get(i));
            Accommodation accommodation = accommodationLocationList.get(i).getAccommodationId();
            AccommodationModel accommodationModel = new AccommodationModel();
            accommodationModel.setAccommodationLocation(accommodationLocationList.get(i));
            accommodationModel.setName(accommodationLocationList.get(i).getAccommodationId().getAccommodationName());
            accommodationModel.setPrice(accommodationLocationList.get(i).getAccommodationId().getPrice());
            accommodationModel.setReviewList(reviewsForAccommodation(accommodationLocationList.get(i).getAccommodationId().getId()));
            accommodationModel.setType(accommodationLocationList.get(i).getAccommodationId().getAccommodationTypeId().getType());
            accommodationModel.setDescription(accommodationLocationList.get(i).getAccommodationId().getDescription());
            accommodationModel.setImages(getImages(accommodation));
            accommodationModelList.add(accommodationModel);

        }
        return accommodationModelList;
    }

    public AccommodationModel searchByAccommodationName(String accommodationName) {
        Accommodation accommodation = accommodationRepository.findByAccommodationName(accommodationName);
        if (accommodation == null) {
            throw new GeneralException("No accommodation with name " + accommodationName + " is found");
        }
        AccommodationLocation accommodationLocation = accommodationLocationRepo.findByAId(accommodation.getId());
        AccommodationModel accommodationModel = new AccommodationModel();
        accommodationModel.setAccommodationLocation(accommodationLocation);
        accommodationModel.setReviewList(reviewsForAccommodation(accommodationLocation.getAccommodationId().getId()));
        accommodationModel.setName(accommodationLocation.getAccommodationId().getAccommodationName());
        accommodationModel.setPrice(accommodationLocation.getAccommodationId().getPrice());
        accommodationModel.setType(accommodationLocation.getAccommodationId().getAccommodationTypeId().getType());
        accommodationModel.setDescription(accommodationLocation.getAccommodationId().getDescription());
        accommodationModel.setImages(getImages(accommodation));
        return accommodationModel;
    }

    public AccommodationReview addReview(AccommodationReview review) {
        accommodationReviewRepository.save(review);
        return review;
    }

    public AccommodationReview addReviewUsingRequestBody(AddReviewModel addReviewModel) {
        AccommodationReview review = new AccommodationReview();
        Accommodation accommodationId = accommodationRepository.findByAId(addReviewModel.getAccommodationId());
        review.setAccommodationId(accommodationId);
        review.setReview(addReviewModel.getReview());
        review.setRating(addReviewModel.getRating());
        review.setUserName(addReviewModel.getUserName());

        accommodationReviewRepository.save(review);

        return review;
    }

    private List<AccommodationReview> reviewsForAccommodation(Long accommodationId) {
        List<AccommodationReview> accommodationReviewList = new ArrayList<>();
        accommodationReviewList = accommodationReviewRepository.findByAccommodationId(accommodationId);
        return accommodationReviewList;
    }

    private List<byte[]> getImages(Accommodation accommodation) {
        List<AccommodationImage> accommodationImageList = accommodationImageRepository.findByAId(accommodation.getId());
        List<byte[]> bytes = new ArrayList<>();
        for (int i =0;i<accommodationImageList.size();i++) {
            bytes.add(imageService.decompressBytes(accommodationImageList.get(i).getPicByte()));
        }
        return bytes;
    }

}
