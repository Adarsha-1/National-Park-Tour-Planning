package org.example.service;


import com.twilio.twiml.voice.Pay;
import org.example.entity.*;
import org.example.exception.CreditCardInvalid;
import org.example.exception.UserAlreadyPaid;
import org.example.model.AddParkModel;
import org.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private ItineraryParkRepository itineraryParkRepository;

    @Autowired
    private ItineraryParkRidesRepository itineraryParkRidesRepository;

    @Autowired
    private ItineraryParkAccommodationRepository itineraryParkAccommodationRepository;

    @Autowired
    private ItineraryParkTransportationRepository itineraryParkTransportationRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;




    public Payment addPayment(String email) throws Exception{
        Payment payment1 = paymentRepository.findPaymentByEmail(email);
        if(payment1 == null){
            Payment payment = new Payment();
            payment.setCreditCardId(creditCardRepository.findByCreditId(1));
            payment.setItineraryId(itineraryRepository.findByUserEmail(email));
            payment.setTotalCost(calculateItineraryTotalPrice(email));
            paymentRepository.save(payment);
            sendEmailReceipt(email);
            return payment;
        }
        else{
            throw new UserAlreadyPaid("You already paid");
        }


    }

    public Payment addPaymentUsingBody(AddParkModel addParkModel) throws Exception{
        if (addParkModel.isOauth2()) {
            Payment payment = addPayment(addParkModel.getEmail());
            return payment;
        }
        Payment payment1 = paymentRepository.findPaymentByUserName(addParkModel.getUserName());
        if(payment1 == null){
            Payment payment = new Payment();
            payment.setCreditCardId(creditCardRepository.findByCreditId(1));
            payment.setItineraryId(itineraryRepository.findByUserName(addParkModel.getUserName()));
            payment.setTotalCost(calculateItineraryTotalPriceUsingBody(addParkModel.getUserName()));
            paymentRepository.save(payment);
            sendEmailReceipt(addParkModel);
            return payment;
        }
        else{
            throw new UserAlreadyPaid("You already paid");
        }


    }

    private void sendEmailReceipt(AddParkModel addParkModel) throws Exception{
        Payment payment1 = paymentRepository.findPaymentByUserName(addParkModel.getUserName());
        String toAddress = payment1.getItineraryId().getUser().getEmail();
        String fromAddress = "adarsha.reddy98@gmail.com";
        String senderName = "National Camping Services";
        String totalPayment = Double.toString(payment1.getTotalCost());
        String subject = "National Park Tour Receipt";
        String content = "Dear [[name]],<br> <br>"
                + "Thank you for your payment. You paid a total of: $[[totalCost]] <br> <br><br>"
                + "Thank you,<br> <br>"
                + "[[companyName]].";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom(fromAddress);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        content = content.replace("[[name]]", payment1.getItineraryId().getUser().getFirstName() + " " + payment1.getItineraryId().getUser().getLastName());
        content = content.replace("[[totalCost]]", totalPayment);
        content = content.replace("[[companyName]]", senderName);
        mimeMessageHelper.setText(content, true);
        mailSender.send(message);
    }


    private void sendEmailReceipt(String email) throws Exception{
        Payment payment1 = paymentRepository.findPaymentByEmail(email);
        String toAddress = payment1.getItineraryId().getUser().getEmail();
        String fromAddress = "adarsha.reddy98@gmail.com";
        String senderName = "National Camping Services";
        String totalPayment = Double.toString(payment1.getTotalCost());
        String subject = "National Park Tour Receipt";
        String content = "Dear [[name]],<br> <br>"
                + "Thank you for your payment. You paid a total of: $[[totalCost]] <br> <br><br>"
                + "Thank you,<br> <br>"
                + "[[companyName]].";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom(fromAddress);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        content = content.replace("[[name]]", payment1.getItineraryId().getUser().getFirstName() + " " + payment1.getItineraryId().getUser().getLastName());
        content = content.replace("[[totalCost]]", totalPayment);
        content = content.replace("[[companyName]]", senderName);
        mimeMessageHelper.setText(content, true);
        mailSender.send(message);
    }

    public double calculateItineraryTotalPrice(String email){
        double output = 0.0;
        Itinerary itinerary = itineraryRepository.findByUserEmail(email);
        if(itinerary == null)
            return output;
        List<ItineraryPark> itineraryParkList = itineraryParkRepository.findByEmail(email);
        for (int i = 0; i <itineraryParkList.size() ; i++) {
            output += itineraryParkList.get(i).getNumOfDays()*((itineraryParkList.get(i).getParkId().getEntryPrice() * itineraryParkList.get(i).getNumOfAdults())
                    + ((itineraryParkList.get(i).getParkId().getEntryPrice() * itineraryParkList.get(i).getNumOfChildren())/2.0));
            ;
            List<ItineraryParkRides> itineraryParkRides = itineraryParkRidesRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkRides.size(); j++) {
                output += itineraryParkRides.get(j).getRideId().getPrice();
            }
            List<ItineraryParkTransportation> itineraryParkTransportations = itineraryParkTransportationRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkTransportations.size(); j++) {
                output += itineraryParkTransportations.get(j).getTransportationId().getPrice();
            }

            List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkAccommodationList.size(); j++) {
                output += itineraryParkAccommodationList.get(j).getAccommodationId().getPrice();

            }


        }



        return output;

    }

    public double calculateItineraryTotalPriceUsingBody(String userName) throws Exception{
        double output = 0.0;
        Itinerary itinerary = itineraryRepository.findByUserName(userName);
        if(itinerary == null)
            return output;
        List<ItineraryPark> itineraryParkList = itineraryParkRepository.findByUserName(userName);
        for (int i = 0; i <itineraryParkList.size() ; i++) {
            output += itineraryParkList.get(i).getNumOfDays()*((itineraryParkList.get(i).getParkId().getEntryPrice() * itineraryParkList.get(i).getNumOfAdults())
                    + ((itineraryParkList.get(i).getParkId().getEntryPrice() * itineraryParkList.get(i).getNumOfChildren())/2.0));
            ;
            List<ItineraryParkRides> itineraryParkRides = itineraryParkRidesRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkRides.size(); j++) {
                output += itineraryParkRides.get(j).getRideId().getPrice();
            }
            List<ItineraryParkTransportation> itineraryParkTransportations = itineraryParkTransportationRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkTransportations.size(); j++) {
                output += itineraryParkTransportations.get(j).getTransportationId().getPrice();
            }

            List<ItineraryParkAccommodation> itineraryParkAccommodationList = itineraryParkAccommodationRepository.searchById(itineraryParkList.get(i).getId());
            for (int j = 0; j < itineraryParkAccommodationList.size(); j++) {
                output += itineraryParkAccommodationList.get(j).getAccommodationId().getPrice();

            }


        }



        return output;

    }


}
