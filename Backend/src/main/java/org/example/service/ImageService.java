package org.example.service;

import org.example.entity.*;
import org.example.exception.GeneralException;
import org.example.model.ParkModel;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ParkRepository parkRepository;


    @Autowired
    private ParkRidesRepository parkRidesRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationImageRepository accommodationImageRepository;

    public String saveImage(Image imageModel, byte[] data, String parkName) {
        Park park = parkRepository.findByParkName(parkName.toLowerCase());
        if (park == null) {
            throw new GeneralException("No park with name " +parkName);
        }
        imageModel.setPicByte(compressBytes(data));
        imageModel.setParkId(park);
        imageRepository.save(imageModel);
        return "success";
    }

    public String saveAccommodaitonImage(AccommodationImage accommodationImage, byte[] data, String accommodationName) {
        Accommodation accommodation = accommodationRepository.findByAccommodationName(accommodationName);
        if (accommodation == null ) {
            throw new GeneralException("No accommodation found with name " + accommodationName);
        }
        accommodationImage.setAccommodationId(accommodation);
        accommodationImage.setPicByte(compressBytes(data));
        accommodationImageRepository.save(accommodationImage);
        return "success";
    }

    public ParkModel getImage(String parkName) throws IOException {
        Park park = parkRepository.findByParkName(parkName);
        if (park == null) {
            throw new GeneralException("No National Park present with name : " + parkName);
        }
        List<Image> image = imageRepository.findByParkId(park.getId());
        ParkModel parkModel = new ParkModel();
        parkModel.setAddress(park.getAddress());
        parkModel.setDescription(park.getDescription());
        parkModel.setParkName(parkName);
        parkModel.setPrice(park.getEntryPrice());
        if (image.size() != 0){
            List<ParkRides> parkRides = parkRidesRepository.findByParkId(park.getId());
            List<Rides> ridesList = new ArrayList<>();
            for (int i=0;i<parkRides.size();i++) {
                Rides ride = parkRides.get(i).getRides();
                ridesList.add(ride);
            }
            parkModel.setRideList(ridesList);
        }
        List<byte[]> bytes = new ArrayList<>();
        for(int i=0;i<image.size();i++){
            bytes.add(decompressBytes(image.get(i).getPicByte()));
        }
        parkModel.setImages(bytes);
        return parkModel;
    }

    public File getImage1(String name) throws IOException {
        Image image = imageRepository.findByName(name);
        if (image == null ) {
            throw new GeneralException("No image present for park " + name);
        }
        Image image1 = new Image();
        image1.setType(image.getType());
        image1.setName(image.getName());
        image1.setId(image.getId());
        image1.setPicByte(decompressBytes(image.getPicByte()));
        /*Path fileName = Path.of(
                "/Users/adarsha/Desktop/demo.jpg");*/
        File file = new File("/Users/adarsha/Desktop/demo.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(image1.getPicByte());
        return file;
    }

    private static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
