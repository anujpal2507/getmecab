package com.getmecab.customerapp.database;

import java.io.Serializable;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 4/10/16.
 */
public class Pricing implements Serializable {
    Double Final_Price;
    Double GMC_Price;
    Double Per_km_charge_user;
    int ac;
    Double da_charge;
    Double da_night_charge;
    Double distance;
    Double exclusive_estimate;
    Double exclusive_price;
    Double final_state_tax;
    String model_name;
    Double per_day_limit;
    Double price_per_km;
    int seats;
    Double service_tax;
    Double service_tax_percentage;
    Double total_DA;
    Double total_exclusive_price;
    Double total_night_DA;

    public Double getFinal_Price() {
        return Final_Price;
    }

    public void setFinal_Price(Double final_Price) {
        Final_Price = final_Price;
    }

    public Double getGMC_Price() {
        return GMC_Price;
    }

    public void setGMC_Price(Double GMC_Price) {
        this.GMC_Price = GMC_Price;
    }

    public Double getPer_km_charge_user() {
        return Per_km_charge_user;
    }

    public void setPer_km_charge_user(Double per_km_charge_user) {
        Per_km_charge_user = per_km_charge_user;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public Double getDa_charge() {
        return da_charge;
    }

    public void setDa_charge(Double da_charge) {
        this.da_charge = da_charge;
    }

    public Double getDa_night_charge() {
        return da_night_charge;
    }

    public void setDa_night_charge(Double da_night_charge) {
        this.da_night_charge = da_night_charge;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getExclusive_estimate() {
        return exclusive_estimate;
    }

    public void setExclusive_estimate(Double exclusive_estimate) {
        this.exclusive_estimate = exclusive_estimate;
    }

    public Double getExclusive_price() {
        return exclusive_price;
    }

    public void setExclusive_price(Double exclusive_price) {
        this.exclusive_price = exclusive_price;
    }

    public Double getFinal_state_tax() {
        return final_state_tax;
    }

    public void setFinal_state_tax(Double final_state_tax) {
        this.final_state_tax = final_state_tax;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public Double getPer_day_limit() {
        return per_day_limit;
    }

    public void setPer_day_limit(Double per_day_limit) {
        this.per_day_limit = per_day_limit;
    }

    public Double getPrice_per_km() {
        return price_per_km;
    }

    public void setPrice_per_km(Double price_per_km) {
        this.price_per_km = price_per_km;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Double getService_tax() {
        return service_tax;
    }

    public void setService_tax(Double service_tax) {
        this.service_tax = service_tax;
    }

    public Double getService_tax_percentage() {
        return service_tax_percentage;
    }

    public void setService_tax_percentage(Double service_tax_percentage) {
        this.service_tax_percentage = service_tax_percentage;
    }

    public Double getTotal_DA() {
        return total_DA;
    }

    public void setTotal_DA(Double total_DA) {
        this.total_DA = total_DA;
    }

    public Double getTotal_exclusive_price() {
        return total_exclusive_price;
    }

    public void setTotal_exclusive_price(Double total_exclusive_price) {
        this.total_exclusive_price = total_exclusive_price;
    }

    public Double getTotal_night_DA() {
        return total_night_DA;
    }

    public void setTotal_night_DA(Double total_night_DA) {
        this.total_night_DA = total_night_DA;
    }
}
