package org.example.entity;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(schema = "dop")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "expirationDate")
    private String expirationDate;

    @Column(name = "cardCVC")
    private String cardCVC;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardCVC() {
        return cardCVC;
    }

    public void setCardCVC(String cardCVC) {
        this.cardCVC = cardCVC;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cardCVC='" + cardCVC + '\'' +
                '}';
    }






}
