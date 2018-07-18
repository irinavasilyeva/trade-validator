package com.vasylieva.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BaseOption extends Trade {
    private String style;
    private String strategy;

    @NotNull
    private LocalDate deliveryDate;
    private LocalDate expiryDate;
    private LocalDate excerciseStartDate;
    private BigDecimal premium;
    private String premiumCcy;
    private String premiumType;
    private LocalDate premiumDate;

    public BaseOption() {

    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

/*    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }*/

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getExcerciseStartDate() {
        return excerciseStartDate;
    }

    public void setExcerciseStartDate(LocalDate excerciseStartDate) {
        this.excerciseStartDate = excerciseStartDate;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public String getPremiumCcy() {
        return premiumCcy;
    }

    public void setPremiumCcy(String premiumCcy) {
        this.premiumCcy = premiumCcy;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    public LocalDate getPremiumDate() {
        return premiumDate;
    }

    public void setPremiumDate(LocalDate premiumDate) {
        this.premiumDate = premiumDate;
    }
}
