package com.vasylieva.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vasylieva.model.type.TradeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Spot.class, name = "Spot"),
        @JsonSubTypes.Type(value = Forward.class, name = "Forward"),
        @JsonSubTypes.Type(value = BaseOption.class, name = "VanillaOption")
})
public abstract class Trade {
    private TradeType type;
    private String customer;
    private String ccyPair;
    private String direction;
    private LocalDate tradeDate;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private BigDecimal rate;
    private LocalDate valueDate;
    private String legalEntity;
    private String trader;

    @JsonCreator
    public Trade(@JsonProperty(required = true) TradeType type,
                 @JsonProperty(required = true) LocalDate tradeDate) {
        this.type = type;
        this.tradeDate = tradeDate;
    }

    public Trade() {
        //need
    }

    public TradeType getType() {
        return type;
    }

    public void setType(TradeType type) {
        this.type = type;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return type == trade.type &&
                Objects.equals(customer, trade.customer) &&
                Objects.equals(ccyPair, trade.ccyPair) &&
                Objects.equals(direction, trade.direction) &&
                Objects.equals(tradeDate, trade.tradeDate) &&
                Objects.equals(amount1, trade.amount1) &&
                Objects.equals(amount2, trade.amount2) &&
                Objects.equals(rate, trade.rate) &&
                Objects.equals(valueDate, trade.valueDate) &&
                Objects.equals(legalEntity, trade.legalEntity) &&
                Objects.equals(trader, trade.trader);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, customer, ccyPair, direction, tradeDate, amount1, amount2, rate, valueDate, legalEntity, trader);
    }
}
